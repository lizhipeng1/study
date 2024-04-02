<!-- cla_time 列表，可记上课 -->
<template>
  <div>
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="75px">
      <el-form-item label="上课时间:" prop="claDateArray">
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-s-promotion"
          size="mini"
          @click="handleAddClaTime"
        >记上课</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column align="center" width="180" prop="realClaDate" label="上课时间" fixed="left">
        <template slot-scope="scope">
          <span v-if="scope.row.status === '2'">{{ scope.row.realClaDate }} {{ scope.row.realStartTime.substr(0,5) }}~{{ scope.row.realEndTime.substr(0,5) }}</span>
          <span v-else-if="scope.row.status === '1'">{{ scope.row.claDate }} {{ scope.row.startTime.substr(0,5) }}~{{ scope.row.endTime.substr(0,5) }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="claName" label="上课班级" show-overflow-tooltip />
      <el-table-column align="center" prop="courseName" label="所属课程" show-overflow-tooltip />
      <el-table-column align="center" prop="status" label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '1'" type="danger">待上课</el-tag>
          <el-tag v-else-if="scope.row.status === '2'">已上课</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" width="100" prop="staffName" label="上课教师" />
      <el-table-column width="150" label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status === '1'"
            size="mini"
            type="text"
            icon="el-icon-s-promotion"
            @click="handleClaTimeAttend(scope.row)"
          >记上课</el-button>
          <el-button
            v-if="scope.row.status === '2'"
            size="mini"
            type="text"
            icon="el-icon-edit-outline"
            @click="handleUpdateHadAttend(scope.row)"
          >编辑</el-button>
          <el-button
            v-if="scope.row.status === '1'"
            size="mini"
            type="text"
            icon="el-icon-edit-outline"
            @click="handleUpdateWaitAttend(scope.row)"
          >编辑</el-button>
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

    <add-cla-time-attend
      ref="addClaTimeAttend"
      :appoint-course-time-id="chooseCourseTimeId"
      appoint-cla-time
      :need-choose-cla="needChooseCla"
      @success="getList"
    />

    <change-time ref="changeTime" @success="getList()" />
    <change-cla-time-attend ref="changeClaTimeAttend" :cla-id="chooseClaId" :course-time-id="chooseCourseTimeId" @success="getList" />
  </div>
</template>

<script>
import { selectListForAttend } from '@/api/sc/cla/claTime'
import deptSelect from '@/components/system/dept/deptSelect'
import claSelect from '@/components/sc/course/cla/claSelect'
import addClaTimeAttend from '@/components/sc/claTime/addClaTimeAttend'
import changeTime from '@/components/sc/claTime/changeTime'
import changeClaTimeAttend from '@/components/sc/claTime/changeClaTimeAttend'
import moment from 'moment'
export default {
  components: {
    deptSelect,
    claSelect,
    addClaTimeAttend,
    changeClaTimeAttend,
    changeTime
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
        orderByType: 'claTimeAttend'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },

      // 已选择排课
      chooseCourseTimeId: undefined,
      // 已选班级
      chooseClaId: undefined,
      // 记上课是否需要选择班级
      needChooseCla: false
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
    // 排课记上课
    handleClaTimeAttend(claTime) {
      this.needChooseCla = false
      this.chooseCourseTimeId = claTime.courseTimeId
      this.$refs.addClaTimeAttend.open = true
    },
    /** 已上课编辑 */
    handleUpdateHadAttend(row) {
      this.chooseCourseTimeId = row.courseTimeId
      this.chooseClaId = row.claId
      this.$refs.changeClaTimeAttend.open = true
    },
    /** 待上课编辑 */
    handleUpdateWaitAttend(claTime) {
      this.$refs.changeTime.open = true
      this.$refs.changeTime.title = '调课'
      this.$refs.changeTime.reset()
      this.$refs.changeTime.openUpdateByOldInfo(claTime, claTime.courseTimeId)
    },
    /** 记上课 */
    handleAddClaTime() {
      this.needChooseCla = true
      this.chooseCourseTimeId = undefined
      this.$refs.addClaTimeAttend.open = true
    }
  }
}
</script>
