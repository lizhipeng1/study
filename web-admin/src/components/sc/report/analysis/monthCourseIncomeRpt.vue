<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="75px">
      <el-form-item label="月份周期:" prop="monthArray">
        <el-date-picker
          v-model="monthArray"
          clearable
          size="small"
          style="width: 230px"
          type="monthrange"
          value-format="yyyyMM"
          start-placeholder="开始月份"
          end-placeholder="结束月份"
          @change="monthRangeChange"
        />
      </el-form-item>
      <el-form-item label="校区:" prop="deptId">
        <dept-select v-model="queryParams.deptId" />
      </el-form-item>
      <el-form-item label="课程:">
        <course-select v-model="courseIdArray" :dept-id="queryParams.deptId" clearable multiple mounted-load-all />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div style="margin-top: 20px;">
      <div id="monthCourseFeeBarChart" style="width: 100%;height: 350px;" />
    </div>
    <el-table
      v-loading="loading"
      :data="dataList"
      size="mini"
      max-height="250"
      row-key="id"
      border
      show-summary
      :default-expand-all="false"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column align="center" prop="month" label="月份" />
      <el-table-column align="center" prop="courseName" label="课程" />
      <el-table-column align="center" prop="income" label="收入">
        <template slot-scope="scope">
          {{ scope.row.income }} 元
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import { monthCourseOrderFeeReport } from '@/api/report/analysis'
import deptSelect from '@/components/system/dept/deptSelect'
import courseSelect from '@/components/sc/course/courseSelect'
import moment from 'moment'
export default {
  components: {
    deptSelect,
    courseSelect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      dataList: [],
      // 月份
      monthArray: [],
      queryParams: {
      },
      courseIdArray: [],
      echart1: undefined
    }
  },
  computed: {
    // 课程收入情况
    courseInfo() {
      const courseObj = {}
      this.dataList.forEach(monthData => {
        monthData.children.forEach(item => {
          if (!courseObj.hasOwnProperty(item.courseId)) {
            courseObj[item.courseId] = item
            courseObj[item.courseId].monthIncome = {}
          }
          courseObj[item.courseId].monthIncome[monthData.month] = item.income
        })
      })
      return courseObj
    },
    // 课程名数组
    courseNameArray() {
      const courseName = []
      for (const course in this.courseInfo) {
        courseName.push(this.courseInfo[course].courseName)
      }
      return courseName
    },
    // 月份
    monthNameArray() {
      const monthName = []
      this.dataList.forEach(monthData => {
        monthName.push(monthData.month)
      })
      return monthName
    },
    // 表格数据
    chartServices() {
      const services = []
      for (const course in this.courseInfo) {
        const servicesItem = {
          name: this.courseInfo[course].courseName,
          type: 'bar',
          data: []
        }
        this.monthNameArray.forEach(month => {
          servicesItem.data.push(this.courseInfo[course].monthIncome[month] || 0)
        })
        services.push(servicesItem)
      }
      return services
    },
    // 报表options
    monthCourseFeeBarChartOptions() {
      return {
        title: {
          text: '各月课程营收报表'
        },
        grid: {
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            crossStyle: {
              color: '#999'
            }
          }
        },
        toolbox: {
          feature: {
            dataView: { show: true, readOnly: false },
            magicType: { show: true, type: ['line', 'bar'] },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        legend: {
          data: this.courseNameArray,
          padding: [
            5,
            160,
            5,
            160
          ]
        },
        xAxis: [
          {
            name: '月份',
            type: 'category',
            data: this.monthNameArray,
            axisPointer: {
              type: 'shadow'
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            axisLabel: {
              formatter: '{value} 元'
            }
          }
        ],
        series: this.chartServices
      }
    }
  },
  watch: {
    monthCourseFeeBarChartOptions: {
      handler(val) {
        if (this.echart1) {
          this.echart1.dispose()
        }
        this.echart1 = this.$echarts.init(document.getElementById('monthCourseFeeBarChart'))
        // 绘制图表
        this.echart1.setOption(this.monthCourseFeeBarChartOptions)
      }
    }
  },
  mounted() {
    const that = this
    window.onresize = function() {
      that.echart1.resize()
    }
  },
  created() {
    const startDate = moment().month(moment().month()).startOf('month').add(-11, 'months').format('YYYYMM')
    const endDate = moment().month(moment().month()).endOf('month').format('YYYYMM')
    this.monthArray = [startDate, endDate]
    this.handleQuery()
  },
  methods: {
    handleQuery() {
      if (this.monthArray !== undefined && this.monthArray !== null && this.monthArray.length === 2) {
        this.queryParams.beginMonth = this.monthArray[0]
        this.queryParams.endMonth = this.monthArray[1]
      } else {
        this.queryParams.beginMonth = undefined
        this.queryParams.endMonth = undefined
      }
      if (this.courseIdArray && this.courseIdArray.length !== 0) {
        this.queryParams.courseIds = this.courseIdArray.toString()
      } else {
        this.queryParams.courseIds = undefined
      }
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    getList() {
      this.loading = true
      monthCourseOrderFeeReport(this.queryParams).then(response => {
        this.dataList = response.data
        this.loading = false
      }).catch(e => {
        this.loading = false
      })
    },
    monthRangeChange(value) {
      this.handleQuery()
    }
  }
}
</script>
