<template>
  <div>
    <el-table v-loading="loading" :data="dataList">
      <el-table-column prop="courseName" width="170" align="center" label="课程" fixed="left" show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ scope.row.courseName }} ({{ scope.row.deptName }})</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="claName" label="班级" show-overflow-tooltip />
      <el-table-column align="center" prop="studentName" label="学生" show-overflow-tooltip />
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
    <el-dialog title="上课明细" :visible.sync="attendDetailOpen" width="700px">
      <!--明细-->
      <cla-time-attend-detail-table ref="claTimeAttendDetailTable" />
    </el-dialog>
  </div>
</template>
<script>
import { atClaStudentCourseStatus, searchStudentCourse, stopStudentCourseStatus } from '@/api/sc/student/course'
import claTimeAttendDetailTable from '@/components/sc/claTime/claTimeAttendDetailTable'
export default {
  components: {
    claTimeAttendDetailTable
  },
  props: {
  },
  data() {
    return {
      attendDetailOpen: false,
      loading: false,
      dataList: [],
      chargeTypeOptions: [],
      studentCourseStatusOptions: []
    }
  },
  created() {
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
    getList(studentId) {
      this.loading = true
      searchStudentCourse({
        studentId: studentId
      }).then(response => {
        this.dataList = response.data
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
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
