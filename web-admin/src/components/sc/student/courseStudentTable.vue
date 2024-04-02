<!-- 班级学员 -->
<template>
  <div>
    <el-row v-if="!readonly" :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleChooseStudent"
        >添加学员</el-button>
      </el-col>
    </el-row>
    <el-table v-loading="loading" :data="dataList">
      <el-table-column align="center" prop="studentName" label="学生" fixed="left">
        <template slot-scope="scope">
          <router-link :to="'/edu/student/detail/' + scope.row.studentId" class="link-type">
            <span>{{ scope.row.studentName }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="sex" label="性别" :formatter="sexFormatter" />
      <el-table-column align="center" prop="phone" label="联系电话" width="120">
        <template slot-scope="scope">
          <el-tooltip effect="dark" :content="scope.row.contactInfo" placement="top">
            <span>{{ scope.row.phone }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="status" label="状态" :formatter="studentCourseStatusFormatter" />
      <el-table-column align="center" prop="chargeType" width="120" label="收费方式">
        <template slot-scope="scope">
          <span>{{ chargeTypeFormatter(scope.row) }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="chargeType" label="总课时/天">
        <template slot-scope="scope">
          {{ totalCount(scope.row) }}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="chargeType" label="剩余课时/天" width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.chargeType === 'date'">
            {{ scope.row.balanceDays }}天
          </span>
          <span v-else>
            <el-tooltip effect="dark" :content="'过期:' + scope.row.expireHour + '课时'" placement="top">
              <span>{{ scope.row.balanceHour - scope.row.expireHour }}课时</span>
            </el-tooltip>
          </span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="status" label="首次报名时间" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.firstSignTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="status" label="最后续费时间" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastSignTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown trigger="click">
            <span style="cursor: pointer;color: #409EFF;outline: none;">
              操作<i class="el-icon-arrow-down el-icon--right" style="font-size: 12px;" />
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-if="scope.row.status === '1'" icon="el-icon-video-pause" @click.native="handleStopStudentCourseStatus(scope.row.studentCourseId)">停课</el-dropdown-item>
              <el-dropdown-item v-if="scope.row.status === '2'" icon="el-icon-video-play" @click.native="handleAtClaStudentCourseStatus(scope.row.studentCourseId)">在读</el-dropdown-item>
              <el-dropdown-item icon="el-icon-delete" @click.native="handleRemoveStuFromCla(scope.row.studentCourseId)">删除</el-dropdown-item>
              <el-dropdown-item icon="el-icon-document" @click.native="handleAttendDetail(scope.row.studentCourseId)">上课记录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 报名 未选班 -->
    <course-student-choose-cla
      ref="courseStudentChooseCla"
      :course-id="belongCourseId"
      :depart-id="belongDepartId"
      :choose-cla-id="claId"
      :sex-options="sexOptions"
      :charge-type-options="chargeTypeOptions"
      :student-course-status-options="studentCourseStatusOptions"
      @success="getList"
    />
    <el-dialog title="上课明细" :visible.sync="attendDetailOpen" width="700px">
      <!--明细-->
      <cla-time-attend-detail-table ref="claTimeAttendDetailTable" />
    </el-dialog>
  </div>
</template>
<script>
import { searchCourseClaStudent, stopStudentCourseStatus, atClaStudentCourseStatus, removeStuFromCla } from '@/api/sc/student/course'
import courseStudentChooseCla from '@/components/sc/student/courseStudentChooseCla'
import claTimeAttendDetailTable from '@/components/sc/claTime/claTimeAttendDetailTable'
export default {
  components: {
    courseStudentChooseCla,
    claTimeAttendDetailTable
  },
  props: {
    // 查询条件 班级
    claId: {
      type: String,
      default: undefined
    },
    // 查询条件 课程
    courseId: {
      type: String,
      default: undefined
    },
    // 所属课程
    belongCourseId: {
      type: String,
      default: undefined
    },
    // 所属校区
    belongDepartId: {
      type: String,
      default: undefined
    },
    // 是否只读(只能看 不能改)
    readonly: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      loading: false,
      total: 0,
      attendDetailOpen: false,
      // 表格树数据
      dataList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        claId: undefined,
        courseId: undefined
      },
      studentCourseStatusOptions: [],
      chargeTypeOptions: [],
      sexOptions: []
    }
  },
  watch: {
    claId: {
      handler(newValue, oldValue) {
        this.queryParams.claId = newValue
        this.getList()
      },
      immediate: true
    }
  },
  created() {
    this.getDictListByDictType('student_course_status').then(response => {
      this.studentCourseStatusOptions = response.data
    })
    this.getDictListByDictType('charge_type').then(response => {
      this.chargeTypeOptions = response.data
    })
    this.getDictListByDictType('sex').then(response => {
      this.sexOptions = response.data
    })
  },
  methods: {
    sexFormatter(row, column) {
      return this.selectDictLabel(this.sexOptions, row.sex)
    },
    chargeTypeFormatter(row, column) {
      if (row.chargeType === 'date' && row.effect === false && row.balanceDays > 0) {
        // 即将生效 未生效 并且 剩余课时 > 0。
        return '按课时(未生效)'
      } else {
        return this.selectDictLabel(this.chargeTypeOptions, row.chargeType)
      }
    },
    studentCourseStatusFormatter(row, column) {
      return this.selectDictLabel(this.studentCourseStatusOptions, row.status)
    },
    // 总课时/天
    totalCount(row) {
      if (row.chargeType === 'date') {
        return row.totalDay + '天'
      } else {
        return row.totalHour + '课时'
      }
    },
    getList() {
      this.loading = true
      searchCourseClaStudent(this.queryParams).then(response => {
        this.dataList = response.data.rows
        this.total = response.data.total
        this.loading = false
      })
    },
    // 选择学员
    handleChooseStudent() {
      this.$refs.courseStudentChooseCla.handleOpen()
    },
    // 停课
    handleStopStudentCourseStatus(studentCourseId) {
      const that = this
      this.confirm('确定将本学员停课?', function() {
        stopStudentCourseStatus(studentCourseId).then(response => {
          if (response.respCode === '0000') {
            that.msgSuccess('操作成功')
            that.getList()
          } else {
            that.msgError(response.respMsg)
          }
        })
      })
    },
    // 在读
    handleAtClaStudentCourseStatus(studentCourseId) {
      const that = this
      this.confirm('确定将本学员状态设置为在读?', function() {
        atClaStudentCourseStatus(studentCourseId).then(response => {
          if (response.respCode === '0000') {
            that.msgSuccess('操作成功')
            that.getList()
          } else {
            that.msgError(response.respMsg)
          }
        })
      })
    },
    // 报读信息， 将学员 班级置空
    handleRemoveStuFromCla(studentCourseId) {
      const that = this
      this.confirm('确定将学员从本班中移除?', function() {
        removeStuFromCla(studentCourseId).then(response => {
          if (response.respCode === '0000') {
            that.msgSuccess('操作成功')
            that.getList()
          } else {
            that.msgError(response.respMsg)
          }
        })
      })
    },
    // 上课记录明细
    handleAttendDetail(studentCourseId) {
      this.attendDetailOpen = true
      this.$nextTick(() => {
        this.$refs.claTimeAttendDetailTable.queryParams.studentCourseId = studentCourseId
        this.$refs.claTimeAttendDetailTable.getList()
      })
    }
  }
}
</script>
