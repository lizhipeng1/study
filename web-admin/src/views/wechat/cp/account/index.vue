<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="企业名称" prop="companyName">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请输入企业名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="企业ID" prop="corpId">
        <el-input
          v-model="queryParams.corpId"
          placeholder="请输入企业ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="应用id" prop="agentId">
        <el-input
          v-model="queryParams.agentId"
          placeholder="请输入应用id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
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
          v-hasPermi="['wechat:cp:account:add']"
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['wechat:cp:account:update']"
          type="primary"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['wechat:cp:account:delete']"
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['::export']"
          type="primary"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column align="center" prop="tenantId" label="所属租户" />
      <el-table-column align="center" prop="companyName" label="企业名称" />
      <el-table-column align="center" prop="corpId" label="企业ID" />
      <el-table-column align="center" prop="agentId" label="应用id" />
      <el-table-column align="center" prop="callBackUrl" label="回调url" />
      <el-table-column align="center" prop="callBackToken" label="回调token" />
      <el-table-column align="center" prop="callBackKey" label="回调Key" />
      <el-table-column width="150" label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['wechat:cp:account:update']"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-hasPermi="['wechat:cp:account:delete']"
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

    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px">
      <el-form ref="form" v-loading="loadingChange" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="所属租户:" prop="tenantId">
              <el-input v-model="form.tenantId" placeholder="请输入所属租户" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="企业名称:" prop="companyName">
              <el-input v-model="form.companyName" placeholder="请输入企业名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="企业ID:" prop="corpId">
              <el-input v-model="form.corpId" placeholder="请输入企业ID" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="通讯录密钥:" prop="corpSecret">
              <el-input v-model="form.corpSecret" placeholder="请输入通讯录密钥" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="外部联系人密钥:" prop="contactSecret">
              <el-input v-model="form.contactSecret" placeholder="请输入外部联系人密钥" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应用id:" prop="agentId">
              <el-input v-model="form.agentId" placeholder="请输入应用id" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应用密钥:" prop="agentSecret">
              <el-input v-model="form.agentSecret" placeholder="请输入应用密钥" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="回调url:" prop="callBackUrl">
              <el-input v-model="form.callBackUrl" placeholder="请输入回调url" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="回调token:" prop="callBackToken">
              <el-input v-model="form.callBackToken" placeholder="请输入回调token" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="回调Key:" prop="callBackKey">
              <el-input v-model="form.callBackKey" placeholder="请输入回调Key" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="loadingChange" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCpAccount, getCpAccount, delCpAccount, addCpAccount, updateCpAccount } from '@/api/wechat/cp/account'

export default {
  name: 'CpAccount',
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyName: undefined,
        corpId: undefined,
        agentId: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        tenantId: [
          { required: true, message: '所属租户不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listCpAccount(this.queryParams).then(response => {
        this.dataList = response.data.rows
        this.total = response.data.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        tenantId: undefined,
        companyName: undefined,
        corpId: undefined,
        corpSecret: undefined,
        contactSecret: undefined,
        agentId: undefined,
        agentSecret: undefined,
        callBackUrl: undefined,
        callBackToken: undefined,
        callBackKey: undefined
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset()
      this.open = true
      this.title = '添加企业应用信息'
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.cpAccountId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      getCpAccount(row.cpAccountId || this.ids).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改企业应用信息'
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.loadingChange = true
          if (this.form.cpAccountId !== undefined) {
            updateCpAccount(this.form).then(response => {
              this.loadingChange = false
              if (response.respCode === '0000') {
                this.msgSuccess('修改成功')
                this.open = false
                this.getList()
              } else {
                this.msgError(response.respMsg)
              }
            }).catch(() => {
              this.loadingChange = false
            })
          } else {
            addCpAccount(this.form).then(response => {
              this.loadingChange = false
              if (response.respCode === '0000') {
                this.msgSuccess('新增成功')
                this.open = false
                this.getList()
              } else {
                this.msgError(response.respMsg)
              }
            }).catch(() => {
              this.loadingChange = false
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.cpAccountId || this.ids
      this.$confirm('是否确认删除?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delCpAccount(id)
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
