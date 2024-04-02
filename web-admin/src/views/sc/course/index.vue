<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="75px">
      <el-form-item label="课程名称:" prop="courseName">
        <el-input
          v-model="queryParams.courseName"
          placeholder="请输入课程名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上课校区:" prop="departId">
        <el-select
          v-model="queryParams.departId"
          placeholder="请选择上课校区"
          clearable
          size="small"
          filterable
          default-first-option
          :loading="loadingSelect"
        >
          <el-option
            v-for="item in campusOptions"
            :key="item.id"
            :label="item.label"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="课程类型:" prop="courseTypeId">
        <el-select
          v-model="queryParams.courseTypeId"
          placeholder="请选择课程类别"
          clearable
          size="small"
          filterable
          default-first-option
          :loading="loadingSelect"
        >
          <el-option
            v-for="item in courseTypeOptions"
            :key="item.courseTypeId"
            :label="item.courseType"
            :value="item.courseTypeId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="授课模式:" prop="teachingMode">
        <el-select
          v-model="queryParams.teachingMode"
          placeholder="请选择授课模式"
          clearable
          size="small"
          filterable
          default-first-option
          :loading="loadingSelect"
        >
          <el-option
            v-for="item in teachingModeOptions"
            :key="item.teachingMode"
            :label="item.teachingModeName"
            :value="item.teachingMode"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="收费模式:" prop="chargeType">
        <el-select
          v-model="queryParams.chargeType"
          placeholder="请选择收费模式"
          clearable
          size="small"
          filterable
          default-first-option
          :loading="loadingSelect"
        >
          <el-option
            v-for="item in chargeTypeOptions"
            :key="item.chargeType"
            :label="item.chargeTypeName"
            :value="item.chargeType"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="开售:" prop="sale">
        <el-switch
          v-model="queryParams.sale"
          active-value="1"
          inactive-value="0"
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
          v-hasPermi="['sc:course:add']"
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新设课程
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sc:course:update']"
          type="primary"
          icon="el-icon-edit-outline"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改课程
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sc:course:delete']"
          v-loading="loadingChange"
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除课程
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sc:course:list']"
          type="primary"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出当前结果
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sc:course:list']"
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
      <el-table-column prop="courseName" width="120" align="center" label="课程名称" />
      <el-table-column prop="courseTypeName" align="center" label="课程类型" />
      <el-table-column prop="teachingMode" align="center" label="授课模式">
        <template slot-scope="scope">
          <div v-if="scope.row.teachingMode === '1'">
            <el-tooltip class="item" effect="dark" content="班课" placement="right">
              <svg-icon icon-class="one-to-many" style="height: 25px;width: 25px;" />
            </el-tooltip>
          </div>
          <div v-if="scope.row.teachingMode === '2'">
            <el-tooltip class="item" effect="dark" content="一对一" placement="right">
              <svg-icon icon-class="one-to-one" style="height: 25px;width: 25px;" />
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="claCount" align="center" label="开班数" />
      <el-table-column prop="chargeNames" align="center" width="120" label="收费模式" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ chargeNameByCodes(scope.row.chargeNames) }}
        </template>
      </el-table-column>
      <el-table-column prop="campusIds" align="center" label="开课校区">
        <template slot-scope="scope">
          <div v-if="scope.row.campusIds">
            <span v-if="scope.row.campusIds === '-1'">全部校区</span>
            <span v-else>{{ scope.row.campusIds.split(',').length }}个校区</span>
          </div>
          <div v-else> - </div>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" width="100" align="center" label="创建时间">
        <template slot-scope="scope">{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</template>
      </el-table-column>
      <el-table-column prop="sale" align="center" label="开售">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.sale"
            active-value="1"
            inactive-value="0"
            @change="handleSaleChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column prop="courseIntro" align="center" label="课程简介" :show-overflow-tooltip="true" />
      <el-table-column width="170" label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['sc:course:update']"
            size="mini"
            type="text"
            icon="el-icon-edit-outline"
            @click="handleUpdate(scope.row)"
          >变更信息
          </el-button>
          <el-button
            v-hasPermi="['sc:course:delete']"
            v-loading="loadingChange"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除课程
          </el-button>
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

    <add-course-components ref="addCourseComponents" @success="getList" />
    <upload-import-excel ref="uploadImportExcel" title="课程批量导入" import-template-name="import_course" download-template-name="课程批量导入模板" />
  </div>
</template>

<script>
import { listCourse, getCourse, delCourse, changeCourseSale, exportCourse } from '@/api/sc/course'
import addCourseComponents from '@/components/sc/course/addCourse'
import uploadImportExcel from '@/components/tool/impt/uploadImportExcel'
import { select as courseTypeSelect } from '@/api/sc/course/courseType'
import { campusList } from '@/api/system/dept'

export default {
  name: 'Course',
  components: {
    addCourseComponents,
    uploadImportExcel
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      loadingChange: false,
      loadingSelect: false,
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
        courseName: undefined,
        departId: undefined,
        courseTypeId: undefined,
        teachingMode: undefined,
        chargeType: undefined,
        sale: '1'
      },
      // 课程类型
      courseTypeOptions: [],
      // 教学模式
      teachingModeOptions: [{
        teachingMode: '1',
        teachingModeName: '班课'
      }, {
        teachingMode: '2',
        teachingModeName: '一对一'
      }],
      // 开课校区
      campusOptions: [],
      // 收费模式
      chargeTypeOptions: [{
        chargeType: 'hour',
        chargeTypeName: '按课时'
      }, {
        chargeType: 'date',
        chargeTypeName: '按时间'
      }, {
        chargeType: 'cycle',
        chargeTypeName: '按周期'
      }],
      notifyInstance: null
    }
  },
  created() {
    this.getList()
    this.courseTypeList()
    this.campusList()
  },
  methods: {
    courseTypeList() {
      courseTypeSelect({}).then(response => {
        this.courseTypeOptions = response.data
      })
    },
    campusList() {
      campusList().then(response => {
        this.campusOptions = response.data
      })
    },
    /** 查询列表 */
    getList() {
      this.loading = true
      listCourse(this.queryParams).then(response => {
        this.dataList = response.data.rows
        this.total = response.data.total
        this.loading = false
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
    /** 新增按钮操作 */
    handleAdd(row) {
      this.$refs.addCourseComponents.init()
      this.$refs.addCourseComponents.reset()
      this.$refs.addCourseComponents.open = true
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.courseId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      getCourse(row.courseId || this.ids).then(response => {
        this.$refs.addCourseComponents.init()
        this.$refs.addCourseComponents.form = response.data
        this.$refs.addCourseComponents.open = true
        this.$refs.addCourseComponents.title = '修改课程信息'
        this.$refs.addCourseComponents.initFeeModeDate(response.data)
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const that = this
      const id = row.courseId || this.ids
      this.$confirm('是否确认删除?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        that.loadingChange = true
        return delCourse(id)
      }).then((response) => {
        that.loadingChange = false
        if (response.respCode === '0000') {
          this.getList()
          this.msgSuccess('删除成功')
        } else {
          this.msgError(response.respMsg)
        }
      }).catch(function() {
        that.loadingChange = false
      })
    },
    handleExport() {
      const queryParams = this.queryParams
      const that = this
      this.$confirm('是否确认导出当前查询结果?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        that.notifyInstance = that.$notify.info({
          title: '提示',
          message: '文件下载中,请稍后',
          duration: 0
        })
        return exportCourse(queryParams)
      }).then(response => {
        this.downExportFile(response.data, '课程导出.xlsx', that.notifyInstance)
      }).catch(function() {})
    },
    handleImport() {
      this.$refs.uploadImportExcel.open = true
    },
    chargeNameByCodes(chargeNames) {
      if (chargeNames) {
        const chargeNameArray = []
        chargeNames.split(',').forEach((value, index, array) => {
          if (value === 'hour') {
            chargeNameArray.push('按课时')
          } else if (value === 'date') {
            chargeNameArray.push('按时间')
          } else if (value === 'cycle') {
            chargeNameArray.push('按周期')
          }
        })
        return chargeNameArray.toString()
      } else {
        return '-'
      }
    },
    // 用户状态修改
    handleSaleChange(row) {
      const text = row.sale === '1' ? '开售' : '停售'
      this.$confirm('确认要"' + text + '""' + row.courseName + '"课程吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return changeCourseSale(row.courseId, row.sale)
      }).then(() => {
        this.msgSuccess(text + '成功')
      }).catch(function() {
        row.sale = row.sale === '0' ? '1' : '0'
      })
    }
  }
}
</script>
