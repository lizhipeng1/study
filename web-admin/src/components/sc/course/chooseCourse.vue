<template>
  <div>
    <el-dialog title="选择课程" :visible.sync="open" width="700px" class="compact">
      <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="75px" class="compact">
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
            size="small"
            filterable
            default-first-option
            :loading="loadingSelect"
            @change="getList()"
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
            @change="getList()"
          >
            <el-option
              v-for="item in courseTypeOptions"
              :key="item.courseTypeId"
              :label="item.courseType"
              :value="item.courseTypeId"
            />
          </el-select>
        </el-form-item>
        <!--<el-form-item label="授课模式:" prop="teachingMode">
          <el-select
            v-model="queryParams.teachingMode"
            placeholder="请选择授课模式"
            clearable
            size="small"
            filterable
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
            :loading="loadingSelect"
          >
            <el-option
              v-for="item in chargeTypeOptions"
              :key="item.chargeType"
              :label="item.chargeTypeName"
              :value="item.chargeType"
            />
          </el-select>
        </el-form-item>-->
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="loading" :data="dataList" height="300" highlight-current-row @current-change="handleCurrentChange">
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
        <el-button :loading="loading" type="primary" @click="chooseCourse">确定选择</el-button>
        <el-button @click="open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
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
      // 选择的课程ID
      selectedCourseInfo: null
    }
  },
  created() {
    this.campusList()
    this.courseTypeList()
  },
  methods: {
    courseTypeList() {
      courseTypeSelect({}).then(response => {
        this.courseTypeOptions = response.data
      })
    },
    async campusList() {
      const response = await campusList()
      this.campusOptions = response.data
      if (this.campusOptions.length > 0) {
        this.queryParams.departId = this.campusOptions[0].id
      }
      this.getList()
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
    handleCurrentChange(val) {
      if (val) {
        this.selectedCourseInfo = val
      }
    },
    chooseCourse() {
      this.$emit('chooseComplete', {
        courseInfo: this.selectedCourseInfo,
        departId: this.queryParams.departId
      })
      this.open = false
    }
  }
}
</script>
