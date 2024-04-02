<!-- 学生视图 -->
<template>
  <div class="container">
    <el-row v-loading="loading" class="cla-detail">
      <div class="top-name" style="display: flex;justify-content: space-between;">
        <div v-if="canSearchStudent">
          <label class="el-form-item__label" style="width: 80px;">选择学生:</label>
          <student-select @change="handleStudentChange" />
        </div>
        <div v-if="studentId !== undefined">
          <span>{{ studentInfo.studentName }}</span>
        </div>
      </div>
      <div class="cla-base-info">
        <div class="item">
          <div class="item-name">学校:</div>
          <div class="item-value">{{ studentInfo.schoolName }}</div>
        </div>
        <div class="item">
          <div class="item-name">年龄:</div>
          <div class="item-value">{{ studentInfo.age }}</div>
        </div>
        <div class="item">
          <div class="item-name">性别:</div>
          <div v-if="studentInfo.sex === 'M'" class="item-value">男</div>
          <div v-else-if="studentInfo.sex === 'F'" class="item-value">女</div>
          <div v-else class="item-value">未知</div>
        </div>
        <div class="item">
          <div class="item-name">联系电话:</div>
          <div class="item-value">
            <el-tooltip effect="dark" :content="contactInfo" placement="top">
              <span>{{ studentInfo.phone }}</span>
            </el-tooltip>
          </div>
        </div>
        <div class="item">
          <div class="item-name">入校时间:</div>
          <div class="item-value">{{ studentInfo.inTime }}</div>
        </div>
      </div>
    </el-row>
    <div class="tab-container">
      <el-tabs v-model="activeTab" tab-position="top" @tab-click="handleTabChange">
        <el-tab-pane name="studentCourse" label="课程">
          <span slot="label" style="font-size: 16px;"><i class="el-icon-notebook-2" /> 课程</span>
          <student-course-table ref="studentCourseTable" />
        </el-tab-pane>
        <el-tab-pane name="studentOrder" label="订单">
          <span slot="label" style="font-size: 16px;"><i class="el-icon-notebook-2" /> 订单</span>
          <student-order-table ref="studentOrderTable" :student-id="studentId" />
        </el-tab-pane>
        <el-tab-pane name="claTimeCalendar" label="课表">
          <span slot="label" style="font-size: 16px;"><i class="el-icon-date" /> 课表</span>
          <cla-time-calendar ref="claTimeCalendar" :student-id="studentId" :readonly="true" :add-time-rule="false" :need-student-id-param="true" />
        </el-tab-pane>
        <el-tab-pane name="claTimeAttend" label="上课记录">
          <span slot="label" style="font-size: 16px;"><i class="el-icon-document-copy" /> 上课记录</span>
          <cla-time-attend-detail-table ref="claTimeAttendDetailTable" :student-id="studentId" />
        </el-tab-pane>
        <el-tab-pane name="courseLog" label="日志">
          <span slot="label" style="font-size: 16px;"><i class="el-icon-document" /> 日志</span>
          <student-log-table ref="studentLogTable" :student-id="studentId" />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
import { getStudent } from '@/api/sc/student'
import studentOrderTable from '@/views/sc/student/components/studentOrderTable'
import studentLogTable from '@/views/sc/student/components/studentLogTable'
import claTimeAttendDetailTable from '@/components/sc/claTime/claTimeAttendDetailTable'
import claTimeCalendar from '@/components/sc/claTime/claTimeCalendar'
import studentCourseTable from '@/views/sc/student/components/studentCourseTable'
import studentSelect from '@/components/sc/student/studentSelect'
export default {
  components: {
    studentOrderTable,
    claTimeAttendDetailTable,
    claTimeCalendar,
    studentCourseTable,
    studentLogTable,
    studentSelect
  },
  data() {
    return {
      loading: false,
      activeTab: 'studentCourse',
      // 联系人数组
      contactArray: [],
      studentInfo: {
      },
      studentId: undefined,
      // 是否可查询学生
      canSearchStudent: true
    }
  },
  computed: {
    contactInfo() {
      let contactStr = ''
      if (this.studentInfo.contactList && this.studentInfo.contactList.length > 0) {
        this.studentInfo.contactList.forEach(item => {
          if (contactStr === '') {
            contactStr = item.contactPhone + '(' + item.contactNick + ')'
          } else {
            contactStr = contactStr + ';' + item.contactPhone + '(' + item.contactNick + ')'
          }
        })
      }
      return contactStr
    }
  },
  created() {
    const params = this.$route.params
    if (params.studentId) {
      this.studentId = params.studentId
      this.canSearchStudent = false
      this.handleStudentChange(this.studentId)
    }
  },
  methods: {
    // 选择学生变更
    handleStudentChange(studentId) {
      if (!studentId) {
        return
      }
      this.loading = true
      getStudent(studentId).then(response => {
        this.studentInfo = response.data
        this.loading = false
        if (this.canSearchStudent === false) {
          this.$store.dispatch('tagsView/updateVisitedViewName', {
            view: this.$route,
            name: '学生:' + this.studentInfo.studentName
          })
        }
      }).catch(() => {
        this.loading = false
      })
      this.handleTabChange({
        name: this.activeTab
      })
    },
    handleTabChange(tab) {
      if (!this.studentId) {
        return
      }
      this.$nextTick(() => {
        if (tab.name === 'studentCourse') {
          this.$refs.studentCourseTable.getList(this.studentId)
        } else if (tab.name === 'studentOrder') {
          this.$refs.studentOrderTable.getList()
        } else if (tab.name === 'claTimeAttend') {
          this.$refs.claTimeAttendDetailTable.getList()
        } else if (tab.name === 'claTimeCalendar') {
          this.$refs.claTimeCalendar.loadData()
        } else if (tab.name === 'courseLog') {
          this.$refs.studentLogTable.getList()
        }
      })
    },
    reset() {
      this.studentInfo = {
      }
    }
  }
}
</script>
<style lang="scss" scoped src="@/styles/sc/cla/cla-detail.scss"></style>
<style rel="stylesheet/scss" lang="scss" scoped>
  .tab-container{
    padding: 5px 15px;
  }
</style>
