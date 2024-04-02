<!-- 报读列表 -->
<template>
  <div>
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="80px">
      <el-form-item label="校区:" prop="departId">
        <dept-select v-model="queryParams.departId" @change="getList" />
      </el-form-item>
      <el-form-item label="课程:" prop="courseId">
        <course-select v-model="queryParams.courseId" :dept-id="queryParams.departId" clearable mounted-load-all @change="getList" />
      </el-form-item>
      <el-form-item label="班级:" prop="claId">
        <cla-select v-model="queryParams.claId" :dept-id="queryParams.departId" :course-id="queryParams.courseId" clearable mounted-load-all @change="getList" />
      </el-form-item>
      <el-form-item label="剩余天数:" prop="minBalanceDay">
        <el-input-number v-model="queryParams.minBalanceDay" controls-position="right" :min="0" placeholder="输入最大剩余天数进行查询" />
      </el-form-item>
      <el-form-item label="剩余课时:" prop="minBalanceHour">
        <el-input-number v-model="queryParams.minBalanceHour" controls-position="right" :min="0" placeholder="输入最大剩余课时进行查询" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="dataList">
      <el-table-column align="center" prop="studentName" label="学生" fixed="left">
        <template slot-scope="scope">
          <router-link :to="'/edu/student/detail/' + scope.row.studentId" class="link-type">
            <span>{{ scope.row.studentName }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column align="center" width="120" prop="phone" label="联系电话">
        <template slot-scope="scope">
          <el-tooltip effect="dark" :content="scope.row.contactInfo" placement="top">
            <span>{{ scope.row.phone }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column prop="courseName" width="170" align="center" label="课程" show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ scope.row.courseName }} ({{ scope.row.deptName }})</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="claName" label="班级" show-overflow-tooltip />
      <el-table-column align="center" prop="chargeType" width="120" label="收费方式">
        <template slot-scope="scope">
          <span>{{ chargeTypeFormatter(scope.row) }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="status" label="状态" :formatter="studentCourseStatusFormatter" />
      <el-table-column align="center" prop="chargeType" label="总课时/天">
        <template slot-scope="scope">
          <el-tooltip effect="dark" placement="left">
            <template slot="content">
              <div v-for="item in scope.row.orderDetail.split(';')" :key="item">
                <div v-if="scope.row.chargeType === 'date'">
                  <span>{{ item.split(',')[0] }}天</span>
                  <span>({{ item.split(',')[1] }})</span>
                </div>
                <div v-else>
                  <span>{{ item.split(',')[0] }}课时</span>
                  <span>({{ item.split(',')[1] }}缴费)</span>
                </div>
              </div>
            </template>
            <span v-if="scope.row.chargeType === 'date'" style="text-decoration: underline;">
              {{ scope.row.totalDay }}天
            </span>
            <span v-else style="text-decoration: underline;">
              {{ scope.row.totalHour }}课时
            </span>
          </el-tooltip>
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
      <el-table-column align="center" prop="chargeType" label="总学费" width="100">
        <template slot-scope="scope">
          {{ scope.row.totalFee }} 元
        </template>
      </el-table-column>
      <el-table-column align="center" prop="chargeType" label="剩余学费" width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.chargeType === 'date'" />
          <span v-else>
            <el-tooltip effect="dark" :content="'过期学费:' + scope.row.expireFee + '元'" placement="top">
              <span>{{ scope.row.balanceFee - scope.row.expireFee }} 元</span>
            </el-tooltip>
          </span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown trigger="click">
            <span style="cursor: pointer;color: #409EFF;outline: none;">
              操作<i class="el-icon-arrow-down el-icon--right" style="font-size: 12px;" />
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-if="scope.row.status === '1'" icon="el-icon-document-add" @click.native="handleStopStudentCourseStatus(scope.row.studentCourseId)">停课</el-dropdown-item>
              <el-dropdown-item v-if="scope.row.status === '2'" icon="el-icon-document-add" @click.native="handleAtClaStudentCourseStatus(scope.row.studentCourseId)">在读</el-dropdown-item>
              <el-dropdown-item icon="el-icon-document-add" @click.native="handleAttendDetail(scope.row.studentCourseId)">上课记录</el-dropdown-item>
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

    <el-dialog title="上课明细" :visible.sync="attendDetailOpen" width="700px">
      <!--明细-->
      <cla-time-attend-detail-table ref="claTimeAttendDetailTable" />
    </el-dialog>
  </div>
</template>
<script>
import { searchStuCourseSignUpList, stopStudentCourseStatus, atClaStudentCourseStatus } from '@/api/sc/student/course'
import claTimeAttendDetailTable from '@/components/sc/claTime/claTimeAttendDetailTable'
import deptSelect from '@/components/system/dept/deptSelect'
import claSelect from '@/components/sc/course/cla/claSelect'
import courseSelect from '@/components/sc/course/courseSelect'
export default {
  components: {
    claTimeAttendDetailTable,
    deptSelect,
    claSelect,
    courseSelect
  },
  props: {
  },
  data() {
    return {
      attendDetailOpen: false,
      loading: false,
      // 总条数
      total: 0,
      dataList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      chargeTypeOptions: [],
      studentCourseStatusOptions: []
    }
  },
  created() {
    const params = this.$route.params
    if (typeof params === 'object') {
      if (params.minBalanceDay) {
        this.queryParams.minBalanceDay = params.minBalanceDay
        this.getList()
      }
      if (params.minBalanceHour) {
        this.queryParams.minBalanceHour = params.minBalanceHour
        this.getList()
      }
    }
    this.getDictListByDictType('student_course_status').then(response => {
      this.studentCourseStatusOptions = response.data
    })
    this.getDictListByDictType('charge_type').then(response => {
      this.chargeTypeOptions = response.data
    })
  },
  methods: {
    chargeTypeFormatter(row, column) {
      if (row.chargeType === 'date' && row.effect === false && row.balanceDays > 0) {
        // 即将生效 未生效 并且 剩余课时 > 0。
        return '按时间(未生效)'
      } else {
        return this.selectDictLabel(this.chargeTypeOptions, row.chargeType)
      }
    },
    studentCourseStatusFormatter(row, column) {
      return this.selectDictLabel(this.studentCourseStatusOptions, row.status)
    },
    getList() {
      this.loading = true
      searchStuCourseSignUpList(this.queryParams).then(response => {
        this.dataList = response.data.rows
        this.total = response.data.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
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
