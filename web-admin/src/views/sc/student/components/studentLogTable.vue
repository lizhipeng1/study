<template>
  <div>
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="80px">
      <el-form-item label="记录时间:" prop="claDateArray">
        <el-date-picker
          v-model="claDateArray"
          clearable
          size="small"
          style="width: 230px"
          type="daterange"
          value-format="yyyy-MM-dd"
          placeholder="选择间进行查询"
        />
      </el-form-item>
      <el-form-item label="校区:" prop="deptId">
        <dept-select v-model="queryParams.deptId" @change="getList" />
      </el-form-item>
      <el-form-item label="班级:" prop="claId">
        <cla-select v-model="queryParams.claId" :dept-id="queryParams.deptId" mounted-load-all clearable @change="getList" />
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="dataList">
      <el-table-column prop="studentName" align="center" label="学生" fixed="left" />
      <el-table-column prop="logType" align="center" label="日志类型" fixed="left">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.logType === '1'">缴费</el-tag>
          <el-tag v-if="scope.row.logType === '2'" type="info">上课</el-tag>
          <el-tag v-if="scope.row.logType === '3'" type="danger">变更</el-tag>
          <el-tag v-if="scope.row.logType === '4'" type="danger">作废</el-tag>
          <el-tag v-if="scope.row.logType === '5'" type="danger">退出班级</el-tag>
          <el-tag v-if="scope.row.logType === '6'" type="info">进入班级</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="deptName" align="center" label="校区" />
      <el-table-column prop="courseName" align="center" label="课程" show-overflow-tooltip />
      <el-table-column prop="claName" align="center" label="班级" show-overflow-tooltip />
      <el-table-column prop="changeHour" align="center" label="变更课时" />
      <el-table-column prop="afterBalanceHour" align="center" label="剩余课时" />
      <el-table-column prop="changeFee" width="100" align="center" label="变更金额(元)" />
      <el-table-column prop="memo" align="center" label="备注" show-overflow-tooltip />
      <el-table-column prop="createTime" width="140" align="center" label="记录时间">
        <template slot-scope="scope">
          {{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}') }}
        </template>
      </el-table-column>
      <el-table-column prop="createUserName" align="center" label="记录人" />
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>
<script>
import { listStudentCourseLog } from '@/api/sc/log/studentLog'
import deptSelect from '@/components/system/dept/deptSelect'
import claSelect from '@/components/sc/course/cla/claSelect'
import moment from 'moment'
export default {
  components: {
    deptSelect,
    claSelect
  },
  props: {
    studentId: {
      type: String,
      default: undefined
    }
  },
  data() {
    return {
      loading: false,
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        studentId: undefined
      },
      logTypeOptions: [{
        dictValue: '1',
        dictLabel: '缴费'
      }, {
        dictValue: '2',
        dictLabel: '上课'
      }, {
        dictValue: '3',
        dictLabel: '删除上课记录'
      }],
      dataList: [],
      // 上课时间
      claDateArray: []
    }
  },
  watch: {
    studentId: {
      handler(newValue) {
        this.queryParams.studentId = newValue
      },
      immediate: true
    },
    claDateArray: {
      handler(newValue, oldValue) {
        if (newValue && newValue.length === 2) {
          this.getList()
        }
      },
      immediate: true
    }
  },
  created() {
    if (this.claDateArray.length === 0) {
      this.claDateArray[0] = moment(new Date()).add(-7, 'days').format('YYYY-MM-DD')
      this.claDateArray[1] = moment(new Date()).format('YYYY-MM-DD')
    }
  },
  methods: {
    getList() {
      if (this.claDateArray !== undefined && this.claDateArray !== null && this.claDateArray.length === 2) {
        this.queryParams.beginDate = this.claDateArray[0]
        this.queryParams.endDate = this.claDateArray[1]
      } else {
        this.queryParams.beginDate = undefined
        this.queryParams.endDate = undefined
      }
      this.loading = true
      listStudentCourseLog(this.queryParams).then(response => {
        this.dataList = response.data.rows
        this.total = response.data.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    orderTypeFormat(row, column) {
      return this.selectDictLabel(this.logTypeOptions, row.orderType)
    }
  }
}
</script>
