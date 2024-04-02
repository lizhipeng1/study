<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="企业ID" prop="corpId">
        <el-input
          v-model="queryParams.corpId"
          placeholder="请输入企业ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="外部联系人的名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入外部联系人的名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="外部联系人头像" prop="avatar">
        <el-input
          v-model="queryParams.avatar"
          placeholder="请输入外部联系人头像"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="外部联系人的类型，1表示该外部联系人是微信用户，2表示该外部联系人是企业微信用户" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择外部联系人的类型，1表示该外部联系人是微信用户，2表示该外部联系人是企业微信用户" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="外部联系人性别 0-未知 1-男性 2-女性" prop="gender">
        <el-input
          v-model="queryParams.gender"
          placeholder="请输入外部联系人性别 0-未知 1-男性 2-女性"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="外部联系人在微信开放平台的唯一身份标识（微信unionid）" prop="unionid">
        <el-input
          v-model="queryParams.unionid"
          placeholder="请输入外部联系人在微信开放平台的唯一身份标识（微信unionid）"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="外部联系人的职位" prop="position">
        <el-input
          v-model="queryParams.position"
          placeholder="请输入外部联系人的职位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="外部联系人所在企业的简称" prop="corpName">
        <el-input
          v-model="queryParams.corpName"
          placeholder="请输入外部联系人所在企业的简称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="外部联系人所在企业的主体名称" prop="corpFullName">
        <el-input
          v-model="queryParams.corpFullName"
          placeholder="请输入外部联系人所在企业的主体名称"
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
          v-hasPermi="['wechat:cp:contact:add']"
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['wechat:cp:contact:update']"
          type="primary"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['wechat:cp:contact:delete']"
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
      <el-table-column align="center" prop="corpId" label="企业ID" />
      <el-table-column align="center" prop="name" label="外部联系人的名称" />
      <el-table-column align="center" prop="avatar" label="外部联系人头像" />
      <el-table-column align="center" prop="type" label="外部联系人的类型，1表示该外部联系人是微信用户，2表示该外部联系人是企业微信用户" />
      <el-table-column align="center" prop="gender" label="外部联系人性别 0-未知 1-男性 2-女性" />
      <el-table-column align="center" prop="unionid" label="外部联系人在微信开放平台的唯一身份标识（微信unionid）" />
      <el-table-column align="center" prop="position" label="外部联系人的职位" />
      <el-table-column align="center" prop="corpName" label="外部联系人所在企业的简称" />
      <el-table-column align="center" prop="corpFullName" label="外部联系人所在企业的主体名称" />
      <el-table-column width="150" label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['wechat:cp:contact:update']"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-hasPermi="['wechat:cp:contact:delete']"
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
            <el-form-item label="企业ID:" prop="corpId">
              <el-input v-model="form.corpId" placeholder="请输入企业ID" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="外部联系人的名称:" prop="name">
              <el-input v-model="form.name" placeholder="请输入外部联系人的名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="外部联系人头像:" prop="avatar">
              <el-input v-model="form.avatar" placeholder="请输入外部联系人头像" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="外部联系人的类型，1表示该外部联系人是微信用户，2表示该外部联系人是企业微信用户:">
              <el-select v-model="form.type" placeholder="请选择外部联系人的类型，1表示该外部联系人是微信用户，2表示该外部联系人是企业微信用户">
                <el-option
                  v-for="dict in typeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="外部联系人性别 0-未知 1-男性 2-女性:" prop="gender">
              <el-input v-model="form.gender" placeholder="请输入外部联系人性别 0-未知 1-男性 2-女性" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="外部联系人在微信开放平台的唯一身份标识（微信unionid）:" prop="unionid">
              <el-input v-model="form.unionid" placeholder="请输入外部联系人在微信开放平台的唯一身份标识（微信unionid）" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="外部联系人的职位:" prop="position">
              <el-input v-model="form.position" placeholder="请输入外部联系人的职位" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="外部联系人所在企业的简称:" prop="corpName">
              <el-input v-model="form.corpName" placeholder="请输入外部联系人所在企业的简称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="外部联系人所在企业的主体名称:" prop="corpFullName">
              <el-input v-model="form.corpFullName" placeholder="请输入外部联系人所在企业的主体名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建者:" prop="createUser">
              <el-input v-model="form.createUser" placeholder="请输入创建者" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建时间:" prop="createTime">
              <el-date-picker
                v-model="form.createTime"
                clearable
                size="small"
                style="width: 200px"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择创建时间"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="更新者:" prop="lastUpdateUser">
              <el-input v-model="form.lastUpdateUser" placeholder="请输入更新者" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="更新时间:" prop="lastUpdateTime">
              <el-date-picker
                v-model="form.lastUpdateTime"
                clearable
                size="small"
                style="width: 200px"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择更新时间"
              />
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
import { listCpContact, getCpContact, delCpContact, addCpContact, updateCpContact } from '@/api/wechat/cp/contact'

export default {
  name: 'CpContact',
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
      // 外部联系人的类型，1表示该外部联系人是微信用户，2表示该外部联系人是企业微信用户数据字典
      typeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        corpId: undefined,
        name: undefined,
        avatar: undefined,
        type: undefined,
        gender: undefined,
        unionid: undefined,
        position: undefined,
        corpName: undefined,
        corpFullName: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    }
  },
  created() {
    this.getList()
    this.getDictListByDictType('').then(response => {
      this.typeOptions = response.data
    })
  },
  methods: {
    getList() {
      this.loading = true
      listCpContact(this.queryParams).then(response => {
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
        corpId: undefined,
        name: undefined,
        avatar: undefined,
        type: undefined,
        gender: undefined,
        unionid: undefined,
        position: undefined,
        corpName: undefined,
        corpFullName: undefined,
        createUser: undefined,
        createTime: undefined,
        lastUpdateUser: undefined,
        lastUpdateTime: undefined
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
      this.title = '添加企业微信客户信息'
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.externalUserId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      getCpContact(row.externalUserId || this.ids).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改企业微信客户信息'
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.loadingChange = true
          if (this.form.externalUserId !== undefined) {
            updateCpContact(this.form).then(response => {
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
            addCpContact(this.form).then(response => {
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
      const id = row.externalUserId || this.ids
      this.$confirm('是否确认删除?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delCpContact(id)
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
