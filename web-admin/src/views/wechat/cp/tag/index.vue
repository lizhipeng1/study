<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="标签组id" prop="groupId">
        <el-input
          v-model="queryParams.groupId"
          placeholder="请输入标签组id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="标签名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入标签名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="标签组排序的次序值，order值大的排序靠前,有效的值范围是[0, 2^32)" prop="tagOrder">
        <el-input
          v-model="queryParams.tagOrder"
          placeholder="请输入标签组排序的次序值，order值大的排序靠前,有效的值范围是[0, 2^32)"
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
          v-hasPermi="['wechat:cp:tag:add']"
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['wechat:cp:tag:update']"
          type="primary"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['wechat:cp:tag:delete']"
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
      <el-table-column align="center" prop="groupId" label="标签组id" />
      <el-table-column align="center" prop="name" label="标签名" />
      <el-table-column align="center" prop="tagOrder" label="标签组排序的次序值，order值大的排序靠前,有效的值范围是[0, 2^32)" />
      <el-table-column width="150" label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['wechat:cp:tag:update']"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-hasPermi="['wechat:cp:tag:delete']"
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
            <el-form-item label="标签组id:" prop="groupId">
              <el-input v-model="form.groupId" placeholder="请输入标签组id" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标签名:" prop="name">
              <el-input v-model="form.name" placeholder="请输入标签名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标签组排序的次序值，order值大的排序靠前,有效的值范围是[0, 2^32):" prop="tagOrder">
              <el-input v-model="form.tagOrder" placeholder="请输入标签组排序的次序值，order值大的排序靠前,有效的值范围是[0, 2^32)" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标签组是否已经被删除，只在指定tag_id进行查询时返回:" prop="deleteFlag">
              <el-input v-model="form.deleteFlag" placeholder="请输入标签组是否已经被删除，只在指定tag_id进行查询时返回" />
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
import { listCpTag, getCpTag, delCpTag, addCpTag, updateCpTag } from '@/api/wechat/cp/tag'

export default {
  name: 'CpTag',
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
        groupId: undefined,
        name: undefined,
        tagOrder: undefined
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
  },
  methods: {
    getList() {
      this.loading = true
      listCpTag(this.queryParams).then(response => {
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
        groupId: undefined,
        name: undefined,
        tagOrder: undefined,
        deleteFlag: undefined,
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
      this.title = '添加企业微信标签'
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tagId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      getCpTag(row.tagId || this.ids).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改企业微信标签'
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.loadingChange = true
          if (this.form.tagId !== undefined) {
            updateCpTag(this.form).then(response => {
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
            addCpTag(this.form).then(response => {
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
      const id = row.tagId || this.ids
      this.$confirm('是否确认删除?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delCpTag(id)
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
