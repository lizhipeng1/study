<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="角色名称" prop="roleName">
        <el-input
          v-model="queryParams.roleName"
          placeholder="请输入角色名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="inUse">
        <el-select v-model="queryParams.inUse" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in inUseOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:role:add']"
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="role"
      row-key="roleId"
      default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column prop="roleName" label="角色名称" />
      <el-table-column prop="roleCode" label="角色编码" />
      <el-table-column prop="sort" label="显示顺序" />
      <el-table-column align="center" prop="inUse" label="状态" :formatter="inUseFormat" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="200">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['system:role:update']"
            size="mini"
            type="text"
            icon="el-icon-edit-outline"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-hasPermi="['system:role:add']"
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
          >新增</el-button>
          <el-button
            v-if="scope.row.parentId !== -1"
            v-hasPermi="['system:role:delete']"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级角色" prop="parentId">
              <treeselect v-model="form.parentId" :options="parentOptions" placeholder="选择上级角色" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色编码" prop="roleCode">
              <el-input v-model="form.roleCode" placeholder="请输入角色编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色名称" prop="roleName">
              <el-input v-model="form.roleName" placeholder="请输入角色名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示顺序" prop="sort">
              <el-input-number v-model="form.sort" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.inUse">
                <el-radio
                  v-for="dict in inUseOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{ dict.dictLabel }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="菜单权限">
              <el-tree
                ref="menu"
                :data="menuOptions"
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
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRole, getRole, treeSelect, delRole, addRole, updateRole } from '@/api/system/role/index'
import { treeSelectIncludeHide, roleMenuTreeIdList } from '@/api/system/menu/index'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  name: 'Role',
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 表格树数据
      role: [],
      // 树选项
      parentOptions: undefined,
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 状态数据字典
      inUseOptions: [],
      // 查询参数
      queryParams: {
        roleName: undefined,
        inUse: undefined
      },
      // 角色菜单
      menuOptions: [],
      // 表单参数
      form: {},
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      // 表单校验
      rules: {
        parentId: [
          { required: true, message: '上级不能为空', trigger: 'blur' }
        ],
        roleCode: [
          { required: true, message: '请输入角色编码', trigger: 'blur' }
        ],
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '请输入顺序', trigger: 'blur' }
        ],
        inUse: [
          { required: true, message: '请选择是否在用', trigger: 'blur' }
        ]
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
    /** 查询列表 */
    getList() {
      this.loading = true
      listRole(this.queryParams).then(response => {
        this.role = response.data
        this.loading = false
      })
    },
    /** 查询下拉树结构 */
    getTreeSelect() {
      treeSelect().then(response => {
        this.parentOptions = []
        const role = { id: -1, label: '根目录', children: [] }
        role.children = response.data
        this.parentOptions.push(role)
      })
    },
    /** 查询菜单树结构 */
    getMenuTreeSelect() {
      treeSelectIncludeHide().then(response => {
        this.menuOptions = response.data
        this.$refs.menu.setCheckedKeys([])
      })
    },
    /** 根据角色ID查询菜单树结构 */
    getRoleMenuTreeSelect(roleId) {
      treeSelectIncludeHide().then(response => {
        this.menuOptions = response.data
        roleMenuTreeIdList(roleId).then(roleMenuTreeIdResponse => {
          this.$refs.menu.setCheckedKeys(roleMenuTreeIdResponse.data)
        })
      })
    },
    // 所有菜单节点数据
    getMenuAllCheckedKeys() {
      // 半选中的菜单节点
      const halfCheckedKeys = this.$refs.menu.getHalfCheckedKeys()
      // 目前被选中的菜单节点
      const checkedKeys = this.$refs.menu.getCheckedKeys()
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys)
      return checkedKeys
    },
    // 状态字典翻译
    inUseFormat(row, column) {
      return this.selectDictLabel(this.inUseOptions, row.inUse)
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        roleCode: undefined,
        roleName: undefined,
        sort: undefined,
        inUse: '1',
        deleteFlag: undefined,
        createUser: undefined,
        createTime: undefined,
        lastUpdateUser: undefined,
        lastUpdateTime: undefined,
        parentId: -1
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
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
      this.getTreeSelect()
      this.getMenuTreeSelect()
      if (row !== undefined) {
        this.form.parentId = row.roleId
      }
      this.open = true
      this.title = '添加'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.getTreeSelect()
      this.$nextTick(() => {
        this.getRoleMenuTreeSelect(row.roleId)
      })
      getRole(row.roleId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改'
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.roleId !== undefined) {
            this.form.menuIds = this.getMenuAllCheckedKeys()
            updateRole(this.form).then(response => {
              if (response.respCode === '0000') {
                this.msgSuccess('修改成功')
                this.open = false
                this.getList()
              } else {
                this.msgError(response.respMsg)
              }
            })
          } else {
            this.form.menuIds = this.getMenuAllCheckedKeys()
            addRole(this.form).then(response => {
              if (response.respCode === '0000') {
                this.msgSuccess('新增成功')
                this.open = false
                this.getList()
              } else {
                this.msgError(response.respMsg)
              }
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delRole(row.roleId)
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
