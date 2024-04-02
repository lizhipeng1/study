<template>
  <el-dialog title="查询课程" :visible.sync="open" width="600px">
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
          placeholder="请选择课程类别"
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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="dataList" highlight-current-row @selection-change="handleSelectionChange">
      <el-table-column width="55" align="center">
        <template slot-scope="scope">
          <el-radio v-model="id[0]" :label="scope.row.courseId">&nbsp;</el-radio>
        </template>
      </el-table-column>
      <el-table-column prop="courseName" align="center" label="课程名称" />
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
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <div slot="footer" class="dialog-footer">
      <el-button :loading="loadingChange" type="primary" @click="chooseRecord">确 定</el-button>
      <el-button @click="open=false">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { listCourse } from '@/api/sc/course'
import { select as courseTypeSelect } from '@/api/sc/course/courseType'
import { campusList } from '@/api/system/dept'

export default {
  data() {
    return {
      open: false,
      loading: true,
      loadingSelect: false,
      loadingChange: false,
      // 选中数组
      id: [],
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        courseName: undefined,
        departId: undefined,
        courseTypeId: undefined,
        teachingMode: '1',
        chargeType: undefined,
        sale: '1'
      },
      // 课程类型
      courseTypeOptions: [],
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
      }]
    }
  },
  created() {
    this.getList()
    this.courseTypeList()
    this.campusList()
  },
  methods: {
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.id = selection.map(item => item.courseId)
    },
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
    /** 查询部门列表 */
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
    chooseRecord() {
      if (this.id.length === 0) {
        this.msgError('请选择课程')
      }
      this.$emit('chooseOk', this.id[0])
    }
  }
}
</script>
