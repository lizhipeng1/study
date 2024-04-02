<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="租户名称" prop="tenantName">
        <el-input
          v-model="queryParams.tenantName"
          placeholder="请输入租户名称"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系人" prop="contactName">
        <el-input
          v-model="queryParams.contactName"
          placeholder="请输入联系人"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="inUse">
        <el-select
          v-model="queryParams.inUse"
          placeholder="租户状态"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in inUseOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="到期日期">
        <el-date-picker
          v-model="dateRange"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="useEndRangeOptions"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:tenant:add']"
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >租户入驻</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:tenant:update']"
          type="primary"
          icon="el-icon-edit-outline"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >信息变更</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:tenant:delete']"
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:tenant:export']"
          type="primary"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="租户名称" align="center" prop="tenantName" :show-overflow-tooltip="true" />
      <el-table-column label="联系人" align="center" prop="contactName" :show-overflow-tooltip="true" />
      <el-table-column label="联系电话" align="center" prop="contactPhone" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center" prop="inUse" :formatter="inUseFormat">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.endTime < new Date().getTime()" size="medium" type="danger">过期</el-tag>
          <el-tag v-else-if="scope.row.inUse === '1'" size="medium">在用</el-tag>
          <el-tag v-else-if="scope.row.inUse === '0'" size="medium" type="warning">停用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="入驻时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="生效时间" align="center" prop="beginTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.beginTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="失效时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="memo" :show-overflow-tooltip="true" />
      <el-table-column fixed="right" width="150" label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['system:tenant:update']"
            size="mini"
            type="text"
            icon="el-icon-edit-outline"
            @click="handleUpdate(scope.row)"
          >信息变更</el-button>
          <el-button
            v-hasPermi="['system:tenant:delete']"
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

    <change-tenant ref="changeTenant" @ok="getList" />
  </div>
</template>

<script>
import { listTenant, getTenant, delTenant, exportTenant } from '@/api/system/tenant/index'
import changeTenant from '@/components/system/tenant/changeTenant'

export default {
  components: {
    changeTenant
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
      // 租户表格数据
      dataList: [],
      // 状态数据租户
      inUseOptions: [],
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tenantName: undefined,
        contactName: undefined,
        inUse: undefined
      },
      useEndRangeOptions: {
        shortcuts: [{
          text: '7天',
          onClick(picker) {
            const start = new Date()
            const end = new Date()
            end.setTime(start.getTime() + 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '15天',
          onClick(picker) {
            const start = new Date()
            const end = new Date()
            end.setTime(start.getTime() + 3600 * 1000 * 24 * 15)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '1个月',
          onClick(picker) {
            const start = new Date()
            const end = new Date()
            end.setTime(start.getTime() + 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '3个月',
          onClick(picker) {
            const start = new Date()
            const end = new Date()
            end.setTime(start.getTime() + 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      }
    }
  },
  created() {
    this.getList()
    this.getDictListByDictType('in_use').then(response => {
      this.inUseOptions = response.data
    })
  },
  methods: {
    /** 查询租户类型列表 */
    getList() {
      this.loading = true
      listTenant(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.dataList = response.data.rows
        this.total = response.data.total
        this.loading = false
      })
    },
    // 租户状态租户翻译
    inUseFormat(row, column) {
      return this.selectDictLabel(this.inUseOptions, row.inUse)
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$refs.changeTenant.reset()
      this.$refs.changeTenant.title = '添加租户信息'
      this.$refs.changeTenant.open = true
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tenantId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const tenantId = row.tenantId || this.ids
      getTenant(tenantId).then(response => {
        this.$refs.changeTenant.reset()
        this.$refs.changeTenant.form = response.data
        this.$refs.changeTenant.title = '修改租户信息'
        this.$refs.changeTenant.open = true
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const tenantIds = row.tenantId || this.ids
      this.$confirm('是否确认删除?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delTenant(tenantIds)
      }).then((response) => {
        if (response.respCode === '0000') {
          this.getList()
          this.msgSuccess('删除成功')
        } else {
          this.msgError(response.respMsg)
        }
      }).catch(function() {})
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出所有数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return exportTenant(queryParams)
      }).then(response => {
        this.download(response.respMsg)
      }).catch(function() {})
    }
  }
}
</script>
