<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            ref="tree"
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
          <el-form-item label="用户名:" prop="username">
            <el-input
              v-model="queryParams.username"
              placeholder="请输入用户名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="姓名:" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="联系电话" prop="phone">
            <el-input
              v-model="queryParams.phone"
              placeholder="请输入联系电话"
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
              v-hasPermi="['system:user:add']"
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
            >新增</el-button>
          </el-col>
        </el-row>

        <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column prop="username" width="100" label="用户名" show-overflow-tooltip fixed="left" />
          <el-table-column prop="tenantNames" width="100" label="所属租户" show-overflow-tooltip />
          <el-table-column prop="name" label="姓名" show-overflow-tooltip />
          <el-table-column prop="phone" label="联系电话" width="130" :show-overflow-tooltip="true" />
          <el-table-column prop="emailAddress" label="邮箱" width="130" :show-overflow-tooltip="true" />
          <el-table-column prop="deptName" label="部门" width="130" />
          <el-table-column label="状态" align="center">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.enable"
                active-value="1"
                inactive-value="0"
                @change="handleEnableChange(scope.row)"
              />
            </template>
          </el-table-column>
          <el-table-column fixed="right" width="150" label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-dropdown trigger="click">
                <span style="cursor: pointer;color: #409EFF;outline: none;">
                  操作<i class="el-icon-arrow-down el-icon--right" style="font-size: 12px;" />
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item v-if="scope.row.username !== 'superManXluobo'" v-hasPermi="['system:user:update']" icon="el-icon-edit-outline" @click.native="handleUpdate(scope.row)">修改</el-dropdown-item>
                  <el-dropdown-item v-hasPermi="['system:user:resetPwd']" icon="el-icon-key" @click.native="handleResetPwd(scope.row)">重置密码</el-dropdown-item>
                  <el-dropdown-item v-hasPermi="['system:user:updateTenant']" icon="el-icon-s-shop" @click.native="handleUpdateTenant(scope.row)">分配租户</el-dropdown-item>
                  <el-dropdown-item v-hasPermi="['system:user:updateRole']" icon="el-icon-paperclip" @click.native="handleUpdateRole(scope.row)">分配角色</el-dropdown-item>
                  <el-dropdown-item v-if="scope.row.username !== 'superManXluobo'" v-hasPermi="['system:user:delete']" icon="el-icon-delete" @click.native="handleDelete(scope.row)">删除</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <!-- 添加 -->
    <el-dialog :title="title" :visible.sync="open" width="600px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" placeholder="请输入用户名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属部门" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" placeholder="请选择归属部门" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" placeholder="请输入密码" show-password />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="确认密码" prop="checkPass" show-password>
              <el-input v-model="form.checkPass" placeholder="请输入密码" show-password />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="emailAddress">
              <el-input v-model="form.emailAddress" placeholder="请输入邮箱" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 修改 -->
    <el-dialog :title="title" :visible.sync="openUpdate" width="600px">
      <el-form ref="updateForm" :model="updateForm" :rules="updateRules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="updateForm.username" placeholder="请输入用户名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属部门" prop="deptId">
              <treeselect v-model="updateForm.deptId" :options="deptOptions" placeholder="请选择归属部门" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="updateForm.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="updateForm.phone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="emailAddress">
              <el-input v-model="updateForm.emailAddress" placeholder="请输入邮箱" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormForUpdate">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--变更用户角色-->
    <el-dialog title="修改用户角色" :visible.sync="openUpdateRole" width="300px">
      <el-form ref="updateRoleForm" :model="updateRoleForm" label-width="40px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="租户" prop="tenantId">
              <el-select v-model="updateRoleForm.tenantId" filterable placeholder="请选择租户" @change="tenantChange">
                <el-option
                  v-for="item in tenantOptions"
                  :key="item.tenantId"
                  :label="item.tenantName"
                  :value="item.tenantId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col v-if="updateRoleForm.tenantId !== undefined" :span="24">
            <el-form-item label="角色">
              <el-tree
                ref="role"
                :data="roleOptions"
                show-checkbox
                node-key="id"
                empty-text="加载中，请稍后"
                :props="defaultProps"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
          :disabled="updateRoleForm.userId === undefined || updateRoleForm.tenantId === undefined"
          type="primary"
          @click="submitFormForRoleUpdate"
        >确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--变更用户租户-->
    <el-dialog title="修改用户租户" :visible.sync="openUpdateTenant" width="300px">
      <el-form ref="updateTenantForm" :model="updateTenantForm" label-width="40px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="租户" prop="tenantId">
              <el-select v-model="updateTenantForm.tenantIds" multiple filterable placeholder="请选择租户">
                <el-option
                  v-for="item in allTenantOptions"
                  :key="item.tenantId"
                  :label="item.tenantName"
                  :value="item.tenantId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
          :disabled="updateTenantForm.userId === undefined || updateTenantForm.tenantIds === undefined"
          type="primary"
          @click="submitFormForTenantUpdate"
        >确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUser, getUser, delUser, addUser, updateUser, changeUserEnable, resetUserPwd, changeUserRole, changeUserTenant } from '@/api/system/user/index'
import { treeSelect as roleTreeSelect, userRoleIdList } from '@/api/system/role/index'
import { userTenantSelectLimitSelf, treeSelect as tenantTreeSelect, userTenantSelect } from '@/api/system/tenant/index'
import { treeSelect } from '@/api/system/dept/index'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { isUsername, isPass, isSerialNumber } from '@/utils/validate'

export default {
  name: 'User',
  components: { Treeselect },
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === undefined || value === '') {
        callback(new Error('请输入密码'))
      } else if (!isPass(value)) {
        callback(new Error('最少6位,包含大小写字母和特殊字符'))
      } else {
        if (this.form.checkPass !== '' && this.form.checkPass !== undefined) {
          this.$refs.form.validateField('checkPass')
        }
        callback()
      }
    }
    const validateCheckPass = (rule, value, callback) => {
      if (value === undefined || value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    const validateNumber = (rule, value, callback) => {
      if (!isSerialNumber(value)) {
        callback(new Error('请输入正确的手机号码'))
      } else {
        callback()
      }
    }
    const validateUsername = (rule, value, callback) => {
      if (!isUsername(value)) {
        callback(new Error('4到16位(字母，数字，下划线)'))
      } else {
        callback()
      }
    }
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
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      openUpdate: false,
      openUpdateRole: false,
      openUpdateTenant: false,
      // 部门名称
      deptName: undefined,
      // 部门树选项
      deptOptions: undefined,
      roleOptions: undefined,
      tenantOptions: undefined,
      allTenantOptions: undefined,
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        username: undefined,
        name: undefined,
        phone: undefined
      },
      // 表单参数
      form: {},
      updateForm: {},
      updateRoleForm: {},
      updateTenantForm: {},
      // 表单校验
      rules: {
        username: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { validator: validateUsername, trigger: 'blur' }
        ],
        password: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validateCheckPass, trigger: 'blur' }
        ],
        name: [
          { required: true, message: '姓名不能为空', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '联系电话不能为空', trigger: 'blur' },
          { validator: validateNumber, trigger: 'blur' }
        ],
        emailAddress: [
          { required: true, message: '邮箱不能为空', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱', trigger: 'blur' }
        ]
      },
      updateRules: {
        username: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { validator: validateUsername, trigger: 'blur' }
        ],
        name: [
          { required: true, message: '姓名不能为空', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '联系电话不能为空', trigger: 'blur' },
          { validator: validateNumber, trigger: 'blur' }
        ],
        emailAddress: [
          { required: true, message: '邮箱不能为空', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val)
    }
  },
  created() {
    this.getList()
    this.getDeptTreeSelect()
  },
  methods: {
    /** 查询部门列表 */
    getList() {
      this.loading = true
      listUser(this.queryParams).then(response => {
        this.dataList = response.data.rows
        this.total = response.data.total
        this.loading = false
      })
    },
    /** 查询部门下拉树结构 */
    getDeptTreeSelect() {
      treeSelect().then(response => {
        this.deptOptions = response.data
      })
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id
      this.getList()
    },
    // 用户状态修改
    handleEnableChange(row) {
      const text = row.enable === '1' ? '启用' : '停用'
      this.$confirm('确认要"' + text + '""' + row.username + '"用户吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return changeUserEnable(row.userId, row.enable)
      }).then(() => {
        this.msgSuccess(text + '成功')
      }).catch(function() {
        row.enable = row.enable === '0' ? '1' : '0'
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openUpdate = false
      this.openUpdateRole = false
      this.openUpdateTenant = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        username: undefined,
        password: undefined,
        name: undefined,
        phone: undefined,
        emailAddress: undefined
      }
      this.updateForm = {
        username: undefined,
        name: undefined,
        phone: undefined,
        emailAddress: undefined
      }
      this.updateRoleForm = {
        tenantId: undefined,
        userId: undefined,
        roleIds: undefined
      }
      this.updateTenantForm = {
        tenantIds: undefined,
        userId: undefined
      }
      this.resetForm('form')
      this.resetForm('updateForm')
      this.resetForm('updateRoleForm')
      this.resetForm('updateTenantForm')
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
      this.title = '添加用户'
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      getUser(row.userId || this.ids).then(response => {
        this.updateForm = response.data
        this.openUpdate = true
        this.title = '修改用户'
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          addUser(this.form).then(response => {
            if (response.respCode === '0000') {
              this.msgSuccess('新增成功')
              this.open = false
              this.getList()
            } else {
              this.msgError(response.respMsg)
            }
          })
        }
      })
    },
    submitFormForUpdate: function() {
      this.$refs['updateForm'].validate(valid => {
        if (valid) {
          updateUser(this.updateForm).then(response => {
            if (response.respCode === '0000') {
              this.msgSuccess('修改成功')
              this.openUpdate = false
              this.getList()
            } else {
              this.msgError(response.respMsg)
            }
          })
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.userId || this.ids
      this.$confirm('是否确认删除?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delUser(id)
      }).then((response) => {
        if (response.respCode === '0000') {
          this.getList()
          this.msgSuccess('删除成功')
        } else {
          this.msgError(response.respMsg)
        }
      }).catch(function() {})
    },
    handleResetPwd(row) {
      this.$prompt('请输入"' + row.username + '"的新密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^.*(?=.{6,16})(?=.*\d)(?=.*[A-Z]{1,})(?=.*[a-z]{1,})(?=.*[!@#$%^&*?\(\)]).*$/,
        inputErrorMessage: '密码最少6位,包含大小写字母和特殊字符'
      }).then(({ value }) => {
        resetUserPwd(row.userId, value).then(response => {
          if (response.respCode === '0000') {
            this.msgSuccess('修改成功，新密码是：' + value)
          } else {
            this.msgError(response.msg)
          }
        })
      }).catch(() => {})
    },
    /**
       * 变更角色
       * @param row
       */
    handleUpdateRole(row) {
      this.reset()
      this.updateRoleForm.userId = row.userId
      userTenantSelectLimitSelf(row.userId).then(response => {
        this.tenantOptions = response.data
        this.openUpdateRole = true
        this.title = '修改用户角色'
        this.$nextTick(() => {
          if (this.tenantOptions.length > 0) {
            this.updateRoleForm.tenantId = this.tenantOptions[0].tenantId
            this.tenantChange(this.updateRoleForm.tenantId)
          }
        })
      })
    },
    tenantChange(tenant) {
      roleTreeSelect().then(response => {
        this.roleOptions = response.data
        userRoleIdList(this.updateRoleForm.userId, tenant).then(res => {
          this.$refs.role.setCheckedKeys(res.data)
        })
      })
    },
    // 所有菜单节点数据
    getRoleAllCheckedKeys() {
      // 半选中的菜单节点
      const halfCheckedKeys = this.$refs.role.getHalfCheckedKeys()
      // 目前被选中的菜单节点
      const checkedKeys = this.$refs.role.getCheckedKeys()
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys)
      return checkedKeys
    },
    submitFormForRoleUpdate() {
      this.updateRoleForm.roleIds = this.getRoleAllCheckedKeys()
      changeUserRole(this.updateRoleForm).then(response => {
        if (response.respCode === '0000') {
          this.msgSuccess('操作成功')
          if (this.tenantOptions.length === 1) {
            this.openUpdateRole = false
          }
        } else {
          this.msgError(response.msg)
        }
      })
    },
    /**
     * 变更租户
     * @param row
     */
    handleUpdateTenant(row) {
      this.reset()
      this.updateTenantForm.userId = row.userId
      tenantTreeSelect().then(response => {
        this.allTenantOptions = response.data
        this.title = '修改用户角色'
        this.openUpdateTenant = true
      })
      userTenantSelect(row.userId).then(res => {
        const userTenant = res.data
        const tenantIds = []
        for (let i = 0; i < userTenant.length; i++) {
          tenantIds.push(userTenant[i].tenantId)
        }
        this.updateTenantForm.tenantIds = tenantIds
      })
    },
    submitFormForTenantUpdate() {
      changeUserTenant(this.updateTenantForm).then(res => {
        if (res.respCode === '0000') {
          this.msgSuccess('操作成功')
          this.openUpdateTenant = false
        } else {
          this.msgError(res.msg)
        }
      })
    }
  }
}
</script>
