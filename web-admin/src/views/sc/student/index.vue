<template>
  <div class="app-container">
    <el-tabs v-model="activeTab" tab-position="top" @tab-click="handleTabChange">
      <el-tab-pane name="studentList" label="学生列表">
        <span slot="label" style="font-size: 16px;"><i class="el-icon-s-order" /> 学生列表</span>
        <student-table ref="studentTable" />
      </el-tab-pane>
      <el-tab-pane name="studentSignUpList" label="报读列表">
        <span slot="label" style="font-size: 16px;"><i class="el-icon-s-promotion" /> 报读列表</span>
        <student-course-sign-up-table ref="studentCourseSignUpTable" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import studentCourseSignUpTable from '@/components/sc/student/studentCourseSignUpTable'
import studentTable from '@/components/sc/student/studentTable'

export default {
  name: 'Student',
  components: {
    studentCourseSignUpTable,
    studentTable
  },
  data() {
    return {
      activeTab: 'studentList'
    }
  },
  created() {
    const params = this.$route.params
    if (typeof params === 'object') {
      if (params.activeTab) {
        this.activeTab = params.activeTab
      }
    }
  },
  methods: {
    handleTabChange(tab) {
      if (tab.name === 'studentList') {
        // 学生列表
      } else if (tab.name === 'studentSignUpList') {
        // 报读列表
        this.$refs.studentCourseSignUpTable.getList()
      }
    }
  }
}
</script>
