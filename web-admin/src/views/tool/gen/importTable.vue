<template>
  <!-- 导入表 -->
  <el-dialog title="导入表" :visible.sync="visible" width="800px" top="5vh">
    <el-form ref="queryForm" :model="queryParams" :inline="true">
      <el-form-item label="表名称" prop="tableName">
        <el-input
          v-model="queryParams.tableName"
          placeholder="请输入表名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="表描述" prop="tableComment">
        <el-input
          v-model="queryParams.tableComment"
          placeholder="请输入表描述"
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
    <el-row>
      <el-table :data="dbTableList" height="260px" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="tableName" label="表名称" />
        <el-table-column prop="tableComment" label="表描述" />
        <el-table-column label="创建时间" align="center" prop="createTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="更新时间" align="center" prop="lastUpdateTime">
          <template slot-scope="scope">
            <span v-if="scope.row.lastUpdateTime != null">{{ parseTime(scope.row.lastUpdateTime) }}</span>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleImportTable">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listDbTable, importTable } from '@/api/tool/gen'
export default {
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中数组值
      tables: [],
      // 总条数
      total: 0,
      // 表数据
      dbTableList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tableName: undefined,
        tableComment: undefined
      }
    }
  },
  methods: {
    // 显示弹框
    show() {
      this.getList()
      this.visible = true
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.tables = selection.map(item => item.tableName)
    },
    // 查询表数据
    getList() {
      listDbTable(this.queryParams).then(res => {
        if (res.respCode === '0000') {
          this.dbTableList = res.data.rows
          this.total = res.data.total
        }
      })
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
    /** 导入按钮操作 */
    handleImportTable() {
      importTable({ tables: this.tables.join(',') }).then(res => {
        this.msgSuccess(res.respMsg)
        if (res.respCode === '0000') {
          this.visible = false
          this.$emit('ok')
        }
      })
    }
  }
}
</script>
