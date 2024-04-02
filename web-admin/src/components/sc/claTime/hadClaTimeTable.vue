<!-- 已上课记录 -->
<template>
  <div>
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="上课时间" prop="claDateArray">
        <el-date-picker
          v-model="claDateArray"
          clearable
          size="small"
          style="width: 230px"
          type="daterange"
          value-format="yyyy-MM-dd"
          placeholder="选择上课时间进行查询"
        />
      </el-form-item>
      <el-form-item label="校区:" prop="deptId">
        <dept-select v-model="queryParams.deptId" />
      </el-form-item>
      <el-form-item label="班级:" prop="claId">
        <cla-select v-model="queryParams.claId" :dept-id="queryParams.deptId" clearable mounted-load-all />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column align="center" width="180" prop="realClaDate" label="上课时间" fixed="left">
        <template slot-scope="scope">
          <span>{{ scope.row.realClaDate }} {{ scope.row.realStartTime.substr(0,5) }}~{{ scope.row.realEndTime.substr(0,5) }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="claName" label="上课班级" show-overflow-tooltip>
        <template slot-scope="scope">
          <router-link :to="'/edu/cla/detail/' + scope.row.claId" class="link-type">
            <span>{{ scope.row.claName }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="courseName" label="所属课程" show-overflow-tooltip />
      <el-table-column align="center" width="100" prop="staffName" label="上课教师" />
      <el-table-column align="center" width="80" prop="needAttendCnt" label="应到/实到">
        <template slot-scope="scope">
          <el-tooltip effect="dark" placement="top">
            <template slot="content">
              <div>
                <div>到课: {{ scope.row.realAttendCnt }}人</div>
                <div>请假: {{ scope.row.leaveCnt }}人</div>
                <div>缺勤: {{ scope.row.outCnt }}人</div>
              </div>
            </template>
            <span>{{ scope.row.realAttendCnt }}/{{ scope.row.needAttendCnt }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column align="center" width="100" prop="" label="课时消耗">
        <template slot-scope="scope">
          <span>{{ scope.row.payTotalHour }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" width="120" prop="" label="学费消耗(元)">
        <template slot-scope="scope">
          <span>{{ scope.row.payTotalFee }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="lastUpdateUserName" label="记录人" />
      <el-table-column align="center" width="140" prop="lastUpdateTime" label="记录时间">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastUpdateTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column width="150" label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['sc:claTime:update']"
            size="mini"
            type="text"
            icon="el-icon-edit-outline"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-hasPermi="['sc:claTime:delete']"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
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

    <change-cla-time-attend ref="changeClaTimeAttend" :cla-id="chooseClaId" :course-time-id="chooseCourseTimeId" @success="getList" />
  </div>
</template>

<script>
import { selectListForAttend, deleteHadClaTimeAttend } from '@/api/sc/cla/claTime'
import changeClaTimeAttend from '@/components/sc/claTime/changeClaTimeAttend'
import deptSelect from '@/components/system/dept/deptSelect'
import claSelect from '@/components/sc/course/cla/claSelect'
import moment from 'moment'
export default {
  components: {
    deptSelect,
    claSelect,
    changeClaTimeAttend
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 表格树数据
      dataList: [],
      // 上课时间
      claDateArray: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        attended: true
      },

      // 选择的上课记录
      chooseCourseTimeId: undefined,
      chooseClaId: undefined
    }
  },
  watch: {
    claDateArray: {
      handler(newValue, oldValue) {
        if (newValue && newValue.length === 2) {
          this.handleQuery()
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
    if (this.dataList.length === 0) {
      this.handleQuery()
    }
  },
  methods: {
    getList() {
      this.loading = true
      selectListForAttend(this.queryParams).then(response => {
        this.dataList = response.data.rows
        this.total = response.data.total
        this.loading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      if (this.claDateArray !== undefined && this.claDateArray !== null && this.claDateArray.length === 2) {
        this.queryParams.beginDate = this.claDateArray[0]
        this.queryParams.endDate = this.claDateArray[1]
      } else {
        this.queryParams.beginDate = undefined
        this.queryParams.endDate = undefined
      }
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.schoolId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleUpdate(row) {
      this.chooseCourseTimeId = row.courseTimeId
      this.chooseClaId = row.claId
      this.$refs.changeClaTimeAttend.open = true
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除上课记录?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return deleteHadClaTimeAttend(row.courseTimeId)
      }).then((response) => {
        if (response.respCode === '0000') {
          this.getList()
          this.msgSuccess('删除成功')
        } else {
          this.msgError(response.respMsg)
        }
      }).catch(function() {})
    }
  }
}
</script>
