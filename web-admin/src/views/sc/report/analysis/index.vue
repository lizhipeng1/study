<template>
  <div class="app-container">
    <div class="top-search-report-container">
      <el-form ref="queryForm" :inline="true" label-width="75px">
        <el-form-item label="报表类型" style="margin-bottom: 0;">
          <el-radio-group v-model="reportType">
            <el-radio label="monthCourseIncomeRpt">各月课程营收情况</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </div>
    <div class="report-content">
      <month-course-income-rpt v-if="reportType === 'monthCourseIncomeRpt'" ref="monthCourseIncomeRpt" />
    </div>
  </div>
</template>
<script>
import monthCourseIncomeRpt from '@/components/sc/report/analysis/monthCourseIncomeRpt'
export default {
  components: {
    monthCourseIncomeRpt
  },
  data() {
    return {
      reportType: 'monthCourseIncomeRpt'
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
  .top-search-report-container {
    border-bottom: 1px solid #ebeef5;
    padding-bottom: 10px;
  }
  .report-content {
    margin-top: 10px;
  }
</style>
