<!-- 学生列表 -->
<template>
  <div>
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="80px">
      <el-form-item label="所属学校:" prop="schoolId">
        <el-select
          v-model="queryParams.schoolId"
          placeholder="请选择所属学校"
          clearable
          size="small"
          filterable
          default-first-option
          remote
          :remote-method="schoolSelect"
          :loading="loadingSelect"
        >
          <el-option
            v-for="item in schoolOptions"
            :key="item.schoolId"
            :label="item.schoolName"
            :value="item.schoolId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="学生姓名:" prop="studentName">
        <el-input
          v-model="queryParams.studentName"
          placeholder="请输入学生姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别:" prop="sex">
        <el-select v-model="queryParams.sex" placeholder="请选择性别" clearable size="small">
          <el-option
            v-for="dict in sexOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="联系电话:" prop="phone">
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
          v-hasPermi="['sc:student:add']"
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sc:student:update']"
          type="primary"
          icon="el-icon-edit-outline"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sc:student:delete']"
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sc:student:list']"
          type="primary"
          icon="el-icon-upload"
          size="mini"
          @click="handleImport"
        >批量导入
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="studentName" label="学生姓名">
        <template slot-scope="scope">
          <router-link :to="'/edu/student/detail/' + scope.row.studentId" class="link-type">
            <span>{{ scope.row.studentName }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column prop="schoolName" label="所属学校" />
      <el-table-column prop="birthDay" label="生日">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthDay, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="age" label="年龄" />
      <el-table-column prop="inTime" label="首次入校时间">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="sex" label="性别" :formatter="sexFormat" />
      <el-table-column align="center" prop="phone" label="联系电话">
        <template slot-scope="scope">
          <el-tooltip effect="dark" :content="scope.row.contactInfo" placement="top">
            <span>{{ scope.row.phone }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column width="150" label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['sc:student:update']"
            size="mini"
            type="text"
            icon="el-icon-edit-outline"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-hasPermi="['sc:student:delete']"
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

    <change-student ref="changeStudent" @success="getList" />

    <upload-check-import-excel ref="uploadCheckImportExcel" title="学生批量导入" import-template-name="import_student_order" download-template-name="学生批量导入模板">
      <template slot="successTable" slot-scope="scope">
        <el-table :data="scope.data">
          <el-table-column align="center" prop="studentName" label="学生姓名" fixed="left" />
          <el-table-column align="center" prop="contactRelation" label="主要联系人" show-overflow-tooltip />
          <el-table-column align="center" prop="contactPhone" label="联系电话" show-overflow-tooltip />
          <el-table-column align="center" prop="sex" label="性别" show-overflow-tooltip />
          <el-table-column align="center" prop="schoolName" label="学校" show-overflow-tooltip />
          <el-table-column align="center" prop="inTime" label="入校时间" show-overflow-tooltip />
          <el-table-column align="center" prop="orderDetailTag" label="订单类型" show-overflow-tooltip />
          <el-table-column align="center" prop="deptName" label="报读校区" show-overflow-tooltip />
          <el-table-column align="center" prop="courseName" label="报读课程" show-overflow-tooltip />
          <el-table-column align="center" prop="claName" label="报读班级" show-overflow-tooltip />
          <el-table-column align="center" prop="buyHour" label="购买课时" show-overflow-tooltip />
          <el-table-column align="center" prop="balanceHour" label="剩余课时" show-overflow-tooltip />
          <el-table-column align="center" prop="beginDate" label="按时间缴费开始日期" show-overflow-tooltip />
          <el-table-column align="center" prop="receiptFee" label="实缴学费（收款金额）" show-overflow-tooltip />
          <el-table-column align="center" prop="actualFee" label="应收学费" show-overflow-tooltip />
          <el-table-column align="center" prop="handleDeptName" label="经办校区" show-overflow-tooltip />
          <el-table-column align="center" prop="handleDate" label="经办日期" show-overflow-tooltip />
          <el-table-column align="center" prop="expireDate" label="课程到期日期" show-overflow-tooltip />
          <el-table-column align="center" prop="accountName" label="收款账户" show-overflow-tooltip />
          <el-table-column align="center" prop="saleStaffName" label="销售员工" show-overflow-tooltip />
          <el-table-column align="center" prop="memo" label="订单备注" show-overflow-tooltip />
        </el-table>
      </template>
      <template slot="failTable" slot-scope="scope">
        <el-table :data="scope.data">
          <el-table-column align="center" prop="studentName" label="学生姓名" fixed="left" />
          <el-table-column align="center" prop="failMsg" label="失败原因" show-overflow-tooltip fixed="left" />
          <el-table-column align="center" prop="contactRelation" label="主要联系人" show-overflow-tooltip />
          <el-table-column align="center" prop="contactPhone" label="联系电话" show-overflow-tooltip />
          <el-table-column align="center" prop="sex" label="性别" show-overflow-tooltip />
          <el-table-column align="center" prop="schoolName" label="学校" show-overflow-tooltip />
          <el-table-column align="center" prop="inTime" label="入校时间" show-overflow-tooltip />
          <el-table-column align="center" prop="orderDetailTag" label="订单类型" show-overflow-tooltip />
          <el-table-column align="center" prop="deptName" label="报读校区" show-overflow-tooltip />
          <el-table-column align="center" prop="courseName" label="报读课程" show-overflow-tooltip />
          <el-table-column align="center" prop="claName" label="报读班级" show-overflow-tooltip />
          <el-table-column align="center" prop="buyHour" label="购买课时" show-overflow-tooltip />
          <el-table-column align="center" prop="balanceHour" label="剩余课时" show-overflow-tooltip />
          <el-table-column align="center" prop="beginDate" label="按时间缴费开始日期" show-overflow-tooltip />
          <el-table-column align="center" prop="receiptFee" label="实缴学费（收款金额）" show-overflow-tooltip />
          <el-table-column align="center" prop="actualFee" label="应收学费" show-overflow-tooltip />
          <el-table-column align="center" prop="handleDeptName" label="经办校区" show-overflow-tooltip />
          <el-table-column align="center" prop="handleDate" label="经办日期" show-overflow-tooltip />
          <el-table-column align="center" prop="expireDate" label="课程到期日期" show-overflow-tooltip />
          <el-table-column align="center" prop="accountName" label="收款账户" show-overflow-tooltip />
          <el-table-column align="center" prop="saleStaffName" label="销售员工" show-overflow-tooltip />
          <el-table-column align="center" prop="memo" label="订单备注" show-overflow-tooltip />
        </el-table>
      </template>
    </upload-check-import-excel>
  </div>
</template>

<script>
import { listStudent, delStudent } from '@/api/sc/student'
import { listSelect as listSchoolSelect } from '@/api/sc/school'
import changeStudent from '@/components/sc/student/changeStudent'
import uploadCheckImportExcel from '@/components/tool/impt/uploadCheckImportExcel'

export default {
  name: 'Student',
  components: {
    changeStudent,
    uploadCheckImportExcel
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
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 所属学校数据字典
      schoolOptions: [],
      // 性别数据字典
      sexOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        schoolId: undefined,
        studentName: undefined,
        sex: undefined,
        phone: undefined
      },
      loadingSelect: false
    }
  },
  created() {
    this.getList()
    this.getDictListByDictType('sex').then(response => {
      this.sexOptions = response.data
    })
    this.schoolSelect('')
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true
      listStudent(this.queryParams).then(response => {
        this.dataList = response.data.rows
        this.total = response.data.total
        this.loading = false
      })
    },
    // 性别字典翻译
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
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$refs.changeStudent.handleAdd()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.studentId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.$refs.changeStudent.handleUpdate(row.studentId || this.ids)
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.studentId || this.ids
      this.$confirm('是否确认删除?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delStudent(id)
      }).then((response) => {
        if (response.respCode === '0000') {
          this.getList()
          this.msgSuccess('删除成功')
        } else {
          this.msgError(response.respMsg)
        }
      }).catch(function() {})
    },
    schoolSelect(query) {
      listSchoolSelect({ search: query.trim(), maxRecord: 1000 }).then(response => {
        if (response.respCode === '0000') {
          this.schoolOptions = response.data
        } else {
          this.schoolOptions = []
        }
      })
    },
    handleImport() {
      this.$refs.uploadCheckImportExcel.openImport()
    }
  }
}
</script>
