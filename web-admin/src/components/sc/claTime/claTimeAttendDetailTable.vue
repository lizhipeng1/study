<!--上课记录明细-->
<template>
  <div>
    <el-table v-loading="loading" :data="dataList">
      <el-table-column align="center" prop="studentName" label="学生" fixed="left" />
      <el-table-column align="center" prop="claName" label="班级" show-overflow-tooltip />
      <el-table-column align="center" prop="teacherName" label="教师" show-overflow-tooltip />
      <el-table-column align="center" prop="realClaDate" label="上课日期" width="120">
        <template slot-scope="scope">
          <el-tooltip effect="dark" :content="scope.row.realClaDate + ' ('+scope.row.realStartTime.substr(0,5) + '~' + scope.row.realEndTime.substr(0,5)+')'" placement="top">
            <span>{{ scope.row.realClaDate }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="attendStatus" label="到课状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.attendStatus === '1'" size="medium">到课</el-tag>
          <el-tag v-if="scope.row.attendStatus === '2'" size="medium" type="warning">请假</el-tag>
          <el-tag v-if="scope.row.attendStatus === '3'" size="medium" type="danger">缺勤</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="chargeType" label="收费方式">
        <template slot-scope="scope">
          <span>{{ chargeTypeFormatter(scope.row) }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="payHour" label="消耗课时">
        <template slot-scope="scope">
          <span v-if="scope.row.chargeType === 'date'"> - </span>
          <span v-else> {{ scope.row.payHour }} </span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="payFee" width="100" label="消耗学费(元)" />
      <el-table-column align="center" prop="createTime" width="140" label="记录时间" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}') }}
        </template>
      </el-table-column>
      <el-table-column align="center" prop="memo" label="备注" show-overflow-tooltip />
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
import { listClaTimeAttend } from '@/api/sc/cla/claTimeAttend'
export default {
  components: {
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
      // 表格树数据
      dataList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        courseTimeId: undefined,
        studentId: undefined,
        studentCourseId: undefined
      },
      chargeTypeOptions: []
    }
  },
  watch: {
    studentId: {
      handler(newValue) {
        this.queryParams.studentId = newValue
      },
      immediate: true
    }
  },
  created() {
    this.getDictListByDictType('charge_type').then(response => {
      this.chargeTypeOptions = response.data
    })
  },
  methods: {
    getList() {
      this.loading = true
      listClaTimeAttend(this.queryParams).then(response => {
        this.dataList = response.data.rows
        this.total = response.data.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    chargeTypeFormatter(row, column) {
      return this.selectDictLabel(this.chargeTypeOptions, row.chargeType)
    }
  }
}
</script>
