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
      <!--员工数据-->
      <el-col :span="20" :xs="24">
        <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px" class="align-left">
          <el-form-item label="员工姓名" prop="staffName">
            <el-input
              v-model="queryParams.staffName"
              placeholder="请输入员工姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="任课教师" prop="teacher">
            <el-select v-model="queryParams.teacher" placeholder="是否为任课教师" clearable size="small">
              <el-option label="是" value="1" />
              <el-option label="否" value="0" />
            </el-select>
          </el-form-item>
          <el-form-item label="人事状态" prop="personnelStatus">
            <el-select v-model="queryParams.personnelStatus" placeholder="人事状态" clearable size="small">
              <el-option
                v-for="dict in personnelStatusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-select v-model="queryParams.sex" placeholder="请选择性别" clearable size="small">
              <el-option
                v-for="dict in sexOptions"
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
              v-hasPermi="['system:staff:add']"
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
            >新增
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              v-hasPermi="['system:staff:update']"
              type="primary"
              icon="el-icon-edit-outline"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
            >修改
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              v-hasPermi="['system:staff:delete']"
              type="danger"
              icon="el-icon-delete"
              :disabled="multiple"
              size="mini"
              @click="handleDelete"
            >删除</el-button>
          </el-col>
        </el-row>

        <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column align="center" width="110" prop="staffName" label="员工姓名" fixed="left" />
          <el-table-column align="center" prop="phone" label="联系电话" :show-overflow-tooltip="true" />
          <el-table-column align="center" prop="sex" label="性别" :formatter="sexFormat" />
          <el-table-column align="center" prop="personnelStatusName" label="人事状态" />
          <el-table-column label="入职日期" align="center" prop="entryDate" width="110">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.entryDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" prop="deptName" label="所属部门" :show-overflow-tooltip="true" />
          <el-table-column align="center" prop="teacher" label="任课教师">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.teacher">是</el-tag>
              <el-tag v-else type="info">否</el-tag>
            </template>
          </el-table-column>
          <el-table-column align="center" prop="locked" label="登录系统">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.locked === '0'">允许</el-tag>
              <el-tag v-else-if="scope.row.locked === '1'" type="danger">不允许</el-tag>
              <el-tag v-else type="info">无账号</el-tag>
            </template>
          </el-table-column>
          <el-table-column align="center" prop="username" label="用户名" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <span v-if="scope.row.username">{{ scope.row.username }}</span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column width="150" label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                v-hasPermi="['system:staff:update']"
                size="mini"
                type="text"
                icon="el-icon-edit-outline"
                @click="handleUpdate(scope.row)"
              >修改
              </el-button>
              <el-button
                v-hasPermi="['system:staff:delete']"
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
      </el-col>
    </el-row>
    <change-staff ref="changeStaff" @ok="getList" />
  </div>
</template>

<script>
import { listStaff, getStaff, delStaff } from '@/api/system/staff'
import { treeSelect } from '@/api/system/dept'
import changeStaff from '@/components/system/staff/changeStaff'
export default {
  components: {
    changeStaff
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        teacherName: undefined,
        sex: undefined,
        deptId: undefined
      },
      // 性别 M男 F女数据字典
      sexOptions: [],
      // 人事状态
      personnelStatusOptions: [],
      // 部门名称
      deptName: undefined,
      deptOptions: [],
      defaultProps: {
        children: 'children',
        label: 'label'
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
    this.getDictListByDictType('sex').then(response => {
      this.sexOptions = response.data
    })
    this.getDictListByDictType('personnel_status').then(response => {
      this.personnelStatusOptions = response.data
    })
  },
  methods: {
    /** 查询部门列表 */
    getList() {
      this.loading = true
      listStaff(this.queryParams).then(response => {
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
    // 性别 M男 F女字典翻译
    sexFormat(row, column) {
      return this.selectDictLabel(this.sexOptions, row.sex)
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.queryParams.deptId = undefined
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.$refs.changeStaff.reset()
      this.$refs.changeStaff.title = '添加员工信息'
      this.$refs.changeStaff.getRoleTreeSelect()
      this.$refs.changeStaff.open = true
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.staffId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      getStaff(row.staffId || this.ids).then(response => {
        this.$refs.changeStaff.reset()
        const staffInfo = response.data.staffInfo
        const checkRoleIds = response.data.roleTreeIdList
        const userInfo = response.data.userInfo
        if (userInfo !== undefined) {
          staffInfo.loginUser = true
          staffInfo.username = userInfo.username
          staffInfo.locked = userInfo.locked
        } else {
          staffInfo.loginUser = false
          staffInfo.locked = '0'
        }
        staffInfo.belongCampus = response.data.belongCampus
        staffInfo.partCampus = response.data.partCampus

        this.$refs.changeStaff.form = staffInfo
        this.$refs.changeStaff.checkRoleIds = checkRoleIds
        this.$refs.changeStaff.title = '修改员工信息'
        this.$refs.changeStaff.getRoleTreeSelect()
        this.$refs.changeStaff.open = true
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.staffId || this.ids
      this.$confirm('是否确认删除?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delStaff(id)
      }).then((response) => {
        if (response.respCode === '0000') {
          this.getList()
          this.msgSuccess('删除成功')
        } else {
          this.msgError(response.respMsg)
        }
      }).catch(function() {
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
    }
  }
}
</script>
