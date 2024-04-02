<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="75px">
      <el-form-item label="班级名称:" prop="claName">
        <el-input
          v-model="queryParams.claName"
          placeholder="请输入班级名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程:" prop="courseId">
        <el-select
          v-model="queryParams.courseId"
          filterable
          placeholder="选择课程"
          clearable
          default-first-option
          size="small"
        >
          <el-option
            v-for="course in courseOptions"
            :key="course.courseId"
            :label="course.courseName"
            :value="course.courseId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="教师:" prop="teacherId">
        <el-select
          v-model="queryParams.teacherId"
          filterable
          placeholder="选择上课教师"
          clearable
          default-first-option
          size="small"
        >
          <el-option
            v-for="teacher in teacherOptions"
            :key="teacher.staffId"
            :label="teacher.staffName"
            :value="teacher.staffId"
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sc:cla:add']"
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新设班级</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sc:cla:update']"
          type="primary"
          icon="el-icon-edit-outline"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >变更信息</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sc:cla:delete']"
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >取消班级</el-button>
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
      <el-table-column align="center" width="120" prop="claName" label="班级" fixed="left" show-overflow-tooltip>
        <template slot-scope="scope">
          <router-link :to="'/edu/cla/detail/' + scope.row.claId" class="link-type">
            <span>{{ scope.row.claName }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="courseName" label="课程" show-overflow-tooltip />
      <el-table-column align="center" width="110" prop="staffName" label="任课教师" />
      <el-table-column align="center" prop="capacity" label="人数">
        <template slot-scope="scope">
          <span>{{ scope.row.studentCnt }}/{{ scope.row.capacity }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="recruitStatus" label="招生状态" width="120">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.recruitStatus === '1'">开放招生</el-tag>
          <el-tag v-if="scope.row.recruitStatus === '2'" type="info">满班后停止</el-tag>
          <el-tag v-if="scope.row.recruitStatus === '0'" type="danger">停止招生</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="weekDay" width="180" label="上课时间">
        <template slot-scope="scope">
          <div v-for="item in tableFormatterWeekDay(scope.row)" :key="item" class="overflow-ellipsis">
            <el-tooltip :content="item" placement="left-start">
              <span>
                {{ item }}
              </span>
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="deptName" label="开班校区" :show-overflow-tooltip="true" />
      <el-table-column align="center" prop="openDate" width="110" label="开班日期" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-dropdown trigger="click">
            <span style="cursor: pointer;color: #409EFF;outline: none;">
              操作<i class="el-icon-arrow-down el-icon--right" style="font-size: 12px;" />
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-hasPermi="['sc:cla:update']" icon="el-icon-document-add" @click.native="handleAddRule(scope.row)">排课</el-dropdown-item>
              <el-dropdown-item v-hasPermi="['sc:cla:update']" icon="el-icon-date" @click.native="handleShowClaCalendar(scope.row)">课表</el-dropdown-item>
              <el-dropdown-item v-hasPermi="['sc:cla:update']" icon="el-icon-data-line" @click.native="handleClaTimeAttend(scope.row)">记上课</el-dropdown-item>
              <el-dropdown-item v-hasPermi="['sc:cla:update']" icon="el-icon-edit-outline" @click.native="handleUpdate(scope.row)">变更信息</el-dropdown-item>
              <el-dropdown-item v-hasPermi="['sc:cla:delete']" icon="el-icon-delete" @click.native="handleDelete(scope.row)">取消班级</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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

    <!-- 添加对话框 -->
    <change-cla ref="changeCla" :color-array="colorArray" @ok="handleOk" />

    <!-- 排课-->
    <change-time-rule ref="changeTimeRule" @success="getList" />

    <!-- 记上课-->
    <add-cla-time-attend ref="addClaTimeAttend" :cla-id="chooseClaId" :diff-now-day="0" @success="handleClaTimeAttendSuccess" />

    <!-- 课表 -->
    <el-dialog title="课表" :visible.sync="claCalendarOpen" width="1200px" style="text-align: center;">
      <cla-time-calendar ref="claTimeCalendar" readonly :dept-id="chooseClaInfo.deptId" :cla-id="chooseClaInfo.claId" />
    </el-dialog>

    <upload-check-import-excel ref="uploadCheckImportExcel" title="班级批量导入" import-template-name="import_course_cla" download-template-name="班级批量导入模板">
      <template slot="successTable" slot-scope="scope">
        <el-table :data="scope.data">
          <el-table-column align="center" prop="claName" label="班级名称" fixed="left" />
          <el-table-column align="center" prop="courseName" label="课程" />
          <el-table-column align="center" prop="deptName" label="校区" />
          <el-table-column align="center" prop="teacherName" label="任课教师" />
          <el-table-column align="center" prop="capacity" label="满班人数" />
          <el-table-column align="center" prop="recruitStatus" label="招生状态" />
          <el-table-column align="center" prop="everyStuLoseHour" label="每次上课学员扣除课时" />
          <el-table-column align="center" prop="openDate" label="结班日期" />
          <el-table-column align="center" prop="closeDate" label="开班日期" />
          <el-table-column align="center" prop="memo" label="备注" show-overflow-tooltip />
          <el-table-column align="center" prop="beginDate" label="排课开始日期" />
          <el-table-column align="center" prop="endDate" label="排课结束日期" />
          <el-table-column align="center" prop="repeatType" label="排课重复方式" />
          <el-table-column align="center" prop="weekDay" label="上课星期" show-overflow-tooltip />
          <el-table-column align="center" prop="filterHoliday" label="是否过滤节假日" />
          <el-table-column align="center" prop="startTime" label="上课时间" show-overflow-tooltip>
            <template slot-scope="data">
              {{ data.row.startTime }} ~ {{ data.row.endTime }}
            </template>
          </el-table-column>
          <el-table-column align="center" prop="roomName" label="上课教室" />
        </el-table>
      </template>
      <template slot="failTable" slot-scope="scope">
        <el-table :data="scope.data">
          <el-table-column align="center" prop="claName" label="班级名称" fixed="left" />
          <el-table-column align="center" prop="courseName" label="课程" />
          <el-table-column align="center" prop="failMsg" label="失败原因" show-overflow-tooltip />
          <el-table-column align="center" prop="deptName" label="校区" />
          <el-table-column align="center" prop="teacherName" label="任课教师" />
          <el-table-column align="center" prop="capacity" label="满班人数" />
          <el-table-column align="center" prop="recruitStatus" label="招生状态" />
          <el-table-column align="center" prop="everyStuLoseHour" label="每次上课学员扣除课时" />
          <el-table-column align="center" prop="openDate" label="结班日期" />
          <el-table-column align="center" prop="closeDate" label="开班日期" />
          <el-table-column align="center" prop="memo" label="备注" show-overflow-tooltip />
          <el-table-column align="center" prop="beginDate" label="排课开始日期" />
          <el-table-column align="center" prop="endDate" label="排课结束日期" />
          <el-table-column align="center" prop="repeatType" label="排课重复方式" />
          <el-table-column align="center" prop="weekDay" label="上课星期" show-overflow-tooltip />
          <el-table-column align="center" prop="filterHoliday" label="是否过滤节假日" />
          <el-table-column align="center" prop="startTime" label="上课时间" show-overflow-tooltip>
            <template slot-scope="data">
              {{ data.row.startTime }} ~ {{ scopedataendTime }}
            </template>
          </el-table-column>
          <el-table-column align="center" prop="roomName" label="上课教室" />
        </el-table>
      </template>
    </upload-check-import-excel>
  </div>
</template>

<script>
import { delCla, getCla, listCla } from '@/api/sc/cla'
import changeCla from '@/components/sc/course/cla/changeCla'
import { select as teacherSelect } from '@/api/system/staff'
import { select as courseSelect } from '@/api/sc/course'
import changeTimeRule from '@/components/sc/claTime/changeTimeRule'
import claTimeCalendar from '@/components/sc/claTime/claTimeCalendar'
import addClaTimeAttend from '@/components/sc/claTime/addClaTimeAttend'
import uploadCheckImportExcel from '@/components/tool/impt/uploadCheckImportExcel'

export default {
  name: 'Cla',
  components: {
    changeCla,
    changeTimeRule,
    claTimeCalendar,
    addClaTimeAttend,
    uploadCheckImportExcel
  },
  data() {
    return {
      // 遮罩层
      loading: true,
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
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 课程
      courseOptions: [],
      // 教师
      teacherOptions: [],
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
      // 上课时间 如,一,三 代表周一、周三上课数据字典
      courseTimeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        courseId: undefined,
        teacherId: undefined,
        chargeId: '',
        claName: undefined,
        courseTime: undefined
      },
      // 表单参数
      form: {},
      colorArray: ['#409EFF', '#67C23A', '#E6A23C', '#F56C6C'],
      // 表单校验
      rules: {
        courseId: [
          { required: true, message: '课程id不能为空', trigger: 'blur' }
        ],
        teacherId: [
          { required: true, message: '教师id不能为空', trigger: 'blur' }
        ],
        chargeId: [
          { required: true, message: '收费方式id不能为空', trigger: 'blur' }
        ],
        claName: [
          { required: true, message: '班级名称不能为空', trigger: 'blur' }
        ],
        claColor: [
          { required: true, message: '请选择班级颜色', trigger: 'blur' }
        ],
        courseTime: [
          { required: true, message: '上课时间 如,一,三 代表周一、周三上课不能为空', trigger: 'blur' }
        ]
      },
      // 选择的班级
      chooseClaInfo: {
        deptId: undefined,
        claId: undefined
      },
      claCalendarOpen: false,
      // 已选择班级
      chooseClaId: undefined
    }
  },
  created() {
    this.getList()
    this.getDictListByDictType('week_day').then(response => {
      this.courseTimeOptions = response.data
    })
    this.getCourseOptions()
    this.getTeacherOptions()
  },
  methods: {
    getCourseOptions() {
      courseSelect().then(response => {
        this.courseOptions = response.data
      })
    },
    getTeacherOptions() {
      teacherSelect().then(response => {
        this.teacherOptions = response.data
      })
    },
    /** 查询部门列表 */
    getList() {
      this.loading = true
      listCla(this.queryParams).then(response => {
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
      this.$refs['changeCla'].reset()
      this.$refs['changeCla'].open = true
      this.title = '添加课程班级信息'
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.claId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      getCla(row.claId || this.ids).then(response => {
        const claInfo = response.data.claInfo
        const claCourseInfo = response.data.claCourseInfo
        const canChangeCourse = response.data.canChangeCourse
        this.$refs['changeCla'].reset()
        this.$refs['changeCla'].form = claInfo
        this.$refs['changeCla'].chooseCourseInfo = claCourseInfo
        this.$refs['changeCla'].canChangeCourse = canChangeCourse
        this.title = '修改课程班级信息'
        this.$refs['changeCla'].open = true
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.claId || this.ids
      this.$confirm('确认取消选中班级?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delCla(id)
      }).then((response) => {
        if (response.respCode === '0000') {
          this.getList()
          this.msgSuccess('取消成功')
        } else {
          this.msgError(response.respMsg)
        }
      }).catch(function() {})
    },
    handleOk() {
      this.getList()
    },
    // 排课
    handleAddRule(row) {
      const claId = row.claId
      this.loading = true
      getCla(claId).then(response => {
        this.loading = false
        const claInfo = response.data.claInfo

        this.$refs['changeTimeRule'].reset()
        this.$refs['changeTimeRule'].canChangeCla = false
        this.$refs['changeTimeRule'].form.deptId = claInfo.departId
        this.$refs['changeTimeRule'].form.claId = claInfo.claId
        this.$refs['changeTimeRule'].open = true
      }).catch(() => {
        this.loading = false
      })
    },
    // 展示课表
    handleShowClaCalendar(row) {
      const claId = row.claId
      this.loading = true
      getCla(claId).then(response => {
        this.loading = false
        const claInfo = response.data.claInfo
        this.chooseClaInfo = {
          deptId: claInfo.departId,
          claId: claInfo.claId
        }
        this.claCalendarOpen = true
        this.$nextTick(() => {
          this.$refs.claTimeCalendar.deptId = claInfo.departId
          this.$refs.claTimeCalendar.claId = claInfo.claId
          this.$refs.claTimeCalendar.loadData()
        })
      }).catch(() => {
        this.loading = false
      })
    },
    // 记上课
    handleClaTimeAttend(row) {
      this.chooseClaId = row.claId
      this.$refs.addClaTimeAttend.open = true
    },
    // 记上课成功
    handleClaTimeAttendSuccess() {
    },
    // 表格上课日期格式化
    tableFormatterWeekDay(row) {
      if (row.weekDay) {
        const weekDay = row.weekDay
        return weekDay.split(';')
      } else {
        return '-'
      }
    },
    handleImport() {
      this.$refs.uploadCheckImportExcel.openImport()
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
  .overflow-ellipsis {
    overflow:hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    -o-text-overflow:ellipsis;
  }
</style>
