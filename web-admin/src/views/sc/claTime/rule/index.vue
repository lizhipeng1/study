<template>
  <div class="app-container">
    <el-form ref="queryForm" class="compact" :model="queryParams" :inline="true" label-width="80px">
      <el-form-item label="校区:" prop="deptId">
        <dept-select v-model="queryParams.deptId" placeholder="选择校区" @change="handleChangeDept" />
      </el-form-item>
      <el-form-item label="班级:" prop="claId">
        <cla-select v-model="queryParams.claId" :dept-id="queryParams.deptId" clearable placeholder="选择班级" @change="handleChangeCla" />
      </el-form-item>
      <el-form-item label="任课教师:" prop="teacherId">
        <staff-select v-model="queryParams.teacherId" teacher="1" clearable placeholder="选择任课教师" @change="handleChangeTeacher" />
      </el-form-item>
      <el-form-item v-if="activeTab === 'table'" label="节假日:" prop="filterHoliday">
        <el-radio-group v-model="queryParams.filterHoliday">
          <el-radio
            v-for="dict in filterHolidayOptions"
            :key="dict.dictValue"
            :label="dict.dictValue"
          >{{ dict.dictLabel }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="activeTab === 'table'" label="上课时间:" prop="claDateArray">
        <el-date-picker
          v-model="claDateArray"
          clearable
          size="small"
          type="daterange"
          style="width: 230px;"
          value-format="yyyy-MM-dd"
          placeholder="选择开始日期"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-tabs ref="tab" v-model="activeTab" tab-position="top" style="margin-top: 10px;" @tab-click="handleTabChange">
      <el-tab-pane name="table" label="列表">
        <span slot="label" style="font-size: 16px;"><i class="el-icon-s-unfold" /> 列表</span>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              v-hasPermi="['sc:claTimeRule:add']"
              type="primary"
              icon="el-icon-document-copy"
              size="mini"
              @click="handleAdd"
            >排课</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              v-hasPermi="['sc:claTimeRule:update']"
              type="primary"
              icon="el-icon-edit-outline"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
            >变更</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              v-hasPermi="['sc:claTimeRule:delete']"
              type="danger"
              icon="el-icon-delete"
              size="mini"
              :disabled="single"
              @click="handleDelete"
            >删除</el-button>
          </el-col>
        </el-row>
        <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column prop="claName" label="班级" width="120" align="center" show-overflow-tooltip fixed="left" />
          <el-table-column prop="courseName" label="课程" width="120" align="center" show-overflow-tooltip />
          <el-table-column label="规则类型" align="center" prop="ruleType" width="90">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.ruleType === '1'">重复排课</el-tag>
              <el-tag v-if="scope.row.ruleType === '2'" effect="plain" size="medium">单次排课</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="上课日期" align="center" prop="beginDate" width="200" show-overflow-tooltip>
            <template slot-scope="scope">
              <span v-if="scope.row.ruleType === '1'">{{ parseTime(scope.row.beginDate, '{y}-{m}-{d}') }}~{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
              <span v-if="scope.row.ruleType === '2'">{{ scope.row.onceDate }}</span>
            </template>
          </el-table-column>
          <el-table-column label="上课时间" align="center" prop="startTime" width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.startTime.substr(0,5) }}~{{ scope.row.endTime.substr(0,5) }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="repeatType" label="重复方式" align="center">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.repeatType === '1'" effect="plain" type="danger" size="medium">每周重复</el-tag>
              <el-tag v-else-if="scope.row.repeatType === '2'" effect="plain" size="medium">隔天重复</el-tag>
              <el-tag v-else-if="scope.row.repeatType === '3'" effect="plain" size="medium">隔周重复</el-tag>
            </template>
          </el-table-column>
          <el-table-column align="center" width="130" prop="weekDayName" label="上课星期" show-overflow-tooltip />
          <el-table-column align="center" prop="filterHoliday" label="节假日">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.filterHoliday" size="medium">过滤</el-tag>
              <el-tag v-else type="danger" size="medium">不过滤</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="staffName" align="center" label="任课教师" show-overflow-tooltip />
          <el-table-column prop="classTheme" align="center" label="上课主题" show-overflow-tooltip />
          <el-table-column width="150" label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
            <template slot-scope="scope">
              <el-button
                v-hasPermi="['sc:claTimeRule:update']"
                size="mini"
                type="text"
                icon="el-icon-edit-outline"
                @click="handleUpdate(scope.row)"
              >变更</el-button>
              <el-button
                v-hasPermi="['sc:claTimeRule:delete']"
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
      </el-tab-pane>
      <el-tab-pane name="calendar" label="课表">
        <span slot="label" style="font-size: 16px;"><i class="el-icon-date" /> 课表</span>
        <cla-time-calendar ref="claTimeCalendar" :dept-id="queryParams.deptId" :cla-id="queryParams.claId" :teacher-id="queryParams.teacherId" />
      </el-tab-pane>
    </el-tabs>

    <change-time-rule ref="changeTimeRule" @success="getList" />

  </div>
</template>

<script>
import claSelect from '@/components/sc/course/cla/claSelect'
import deptSelect from '@/components/system/dept/deptSelect'
import staffSelect from '@/components/system/staff/staffSelect'
import changeTimeRule from '@/components/sc/claTime/changeTimeRule'
import claTimeCalendar from '@/components/sc/claTime/claTimeCalendar'
import { listRule, delRule } from '@/api/sc/cla/claTimeRule'
export default {
  name: 'Rule',
  components: {
    changeTimeRule,
    claSelect,
    deptSelect,
    staffSelect,
    claTimeCalendar
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      loadingChange: false,
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
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 规则类型数据字典
      ruleTypeOptions: [],
      // 重复方式数据字典
      repeatTypeOptions: [],
      // 上课星期数据字典
      weekDayOptions: [],
      // 是否过滤节假日数据字典
      filterHolidayOptions: [{
        dictLabel: '过滤',
        dictValue: true
      }, {
        dictLabel: '不过滤',
        dictValue: false
      }],
      claDateArray: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: undefined,
        claId: undefined,
        teacherId: undefined,
        filterHoliday: undefined,
        claDate: undefined
      },
      activeTab: 'table'
    }
  },
  computed: {
  },
  watch: {
  },
  created() {
    const activeTab = this.$route.query.activeTab
    if (activeTab !== undefined) {
      this.activeTab = activeTab
      this.handleTabChange({ name: activeTab })
    } else {
      this.handleTabChange({ name: 'table' })
    }
    this.getDictListByDictType('cla_time_rule_type').then(response => {
      this.ruleTypeOptions = response.data
    })
    this.getDictListByDictType('cla_time_repeat_type').then(response => {
      this.repeatTypeOptions = response.data
    })
    this.getDictListByDictType('week_day').then(response => {
      this.weekDayOptions = response.data
    })
  },
  methods: {
    getList() {
      this.loading = true
      listRule(this.queryParams).then(response => {
        this.dataList = response.data.rows
        this.total = response.data.total
        this.loading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      if (this.claDateArray !== undefined && this.claDateArray !== null && this.claDateArray.length === 2) {
        this.queryParams.claDate = this.claDateArray.toString()
      } else {
        this.queryParams.claDate = undefined
      }
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.$refs['changeTimeRule'].reset()
      this.$refs['changeTimeRule'].open = true
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.ruleId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.$refs['changeTimeRule'].reset()
      this.$refs['changeTimeRule'].open = true
      this.$refs['changeTimeRule'].openUpdateByOldInfo(row.ruleId || this.ids)
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.ruleId || this.ids
      this.$confirm('是否确认删除?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delRule(id)
      }).then((response) => {
        if (response.respCode === '0000') {
          this.getList()
          this.msgSuccess('删除成功')
        } else {
          this.msgError(response.respMsg)
        }
      }).catch(function() {})
    },
    handleTabChange(tab) {
      if (tab.name === 'table') {
        this.getList()
      } else if (tab.name === 'calendar') {
        this.$nextTick(() => {
          this.$refs.claTimeCalendar.loadData()
        })
      }
    },
    // 变更重新加载 课表
    handleChangeDept(deptId) {
      if (this.$refs.tab.currentName === 'calendar') {
        this.$refs.claTimeCalendar.deptId = deptId
        this.$refs.claTimeCalendar.loadData()
      }
    },
    // 变更重新加载 课表
    handleChangeCla(claId) {
      if (this.$refs.tab.currentName === 'calendar') {
        this.$refs.claTimeCalendar.claId = claId
        this.$refs.claTimeCalendar.loadData()
      }
    },
    // 变更重新加载 课表
    handleChangeTeacher(teacherId) {
      if (this.$refs.tab.currentName === 'calendar') {
        this.$refs.claTimeCalendar.teacherId = teacherId
        this.$refs.claTimeCalendar.loadData()
      }
    }
  }
}
</script>
