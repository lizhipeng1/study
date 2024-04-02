<!--
 记上课
 claId: 记上课班级
 diffNowDay: 排课记上课 可记间隔今天几天的排课
 appointClaTime: 选定排课 记上课
 appointCourseTimeId: 选定排课的 编号
 needChooseCla: 是否需选择班级
 -->
<template>
  <el-dialog :title="'记上课:  ' + claInfo.claName + '(' + claInfo.deptName + ')'" :visible.sync="open" class="compact" width="850px">
    <el-row v-loading="loadingClaDetail" class="cla-detail">
      <div v-if="needChooseCla" class="top-name" style="display: flex;justify-content: space-between;">
        <div>
          <label class="el-form-item__label required" style="width: 90px;">选择校区:</label>
          <dept-select v-model="chooseDeptId" placeholder="选择校区" />
        </div>
        <div>
          <label class="el-form-item__label required" style="width: 90px;">选择班级:</label>
          <cla-select v-model="chooseClaId" :dept-id="chooseDeptId" @change="handleChooseCla" />
        </div>
      </div>
      <div class="cla-base-info" style="border-bottom-width: 2px;">
        <div class="item" style="width: 100%;">
          <div class="item-name">上课方式:</div>
          <div class="item-value">
            <el-radio-group v-model="form.attendType">
              <el-radio
                v-for="dict in claTimeAttendTypeOptions"
                :key="dict.dictValue"
                :disabled="appointClaTime"
                :label="dict.dictValue"
              >{{ dict.dictLabel }}</el-radio>
            </el-radio-group>
          </div>
        </div>
        <div class="item">
          <div class="item-name">所属课程:</div>
          <div class="item-value">{{ claCourseInfo.courseName }}</div>
        </div>
        <div v-if="form.attendType === 'rule'" class="item">
          <div class="item-name required">上课教师:</div>
          <div class="item-value">{{ claTimeInfo.staffName }}</div>
        </div>
        <div v-else class="item">
          <div class="item-name required">上课教师:</div>
          <div class="item-value">
            <staff-select v-model="form.teacherId" teacher="1" placeholder="请选择上课教师" />
          </div>
        </div>
        <div v-if="form.attendType === 'rule'" class="item">
          <div class="item-name required">上课教室:</div>
          <div class="item-value">{{ claTimeInfo.roomName }}</div>
        </div>
        <div v-else class="item">
          <div class="item-name">上课教室:</div>
          <div class="item-value">
            <room-select v-model="form.roomId" :dept-id="claInfo.departId" placeholder="请选择上课教室" />
          </div>
        </div>
        <div v-if="form.attendType === 'rule'" class="item">
          <div class="item-name required">
            <el-tooltip v-if="!appointClaTime" effect="dark" :content="'展示今天及前后'+(diffNowDay)+'天的计划排课日期'" placement="top">
              <span>上课日期<svg-icon icon-class="question" />:</span>
            </el-tooltip>
            <span v-else>上课日期:</span>
          </div>
          <div class="item-value">
            <cla-time-select
              ref="claTimeSelect"
              v-model="form.courseTimeId"
              :cla-id="chooseClaId"
              :diff-now-day="diffNowDay"
              :appoint-cla-time="appointClaTime"
              :appoint-course-time-id="appointCourseTimeId"
              @change="handleClaDateChange"
              @noPlan="handleNoPlanClaTime"
            />
          </div>
        </div>
        <div v-else class="item">
          <div class="item-name required">上课日期:</div>
          <div class="item-value">
            <el-date-picker
              v-model="form.claDate"
              clearable
              size="small"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="上课日期"
              style="width: 135px;"
              :picker-options="beginDatePickerOptions"
            />
          </div>
        </div>
        <div class="item">
          <div class="item-name required">
            <el-tooltip v-if="form.attendType === 'rule'" effect="dark" content="默认为计划上课时间,需填写实际上课时间" placement="top">
              <span>上课时间<svg-icon icon-class="question" />:</span>
            </el-tooltip>
            <span v-else>上课时间:</span>
          </div>
          <div class="item-value">
            <el-time-select
              v-model="form.startTime"
              size="small"
              :picker-options="{
                start: '08:00',
                step: '00:30',
                end: '19:00'
              }"
              style="width: 135px;"
              placeholder="上课时间"
            />
          </div>
        </div>
        <div class="item">
          <div class="item-name required">
            <el-tooltip v-if="form.attendType === 'rule'" effect="dark" content="默认为计划下课时间,需填写实际下课时间" placement="top">
              <span>下课时间<svg-icon icon-class="question" />:</span>
            </el-tooltip>
            <span v-else>下课时间:</span>
          </div>
          <div class="item-value">
            <el-time-select
              v-model="form.endTime"
              size="small"
              :picker-options="{
                start: '08:00',
                step: '00:30',
                end: '20:00'
              }"
              style="width: 135px;"
              placeholder="下课时间"
            />
          </div>
        </div>
        <div class="item">
          <div class="item-name">
            <span>备注:</span>
          </div>
          <div class="item-value">
            <el-input v-model="form.memo" size="small" placeholder="备注" />
          </div>
        </div>
        <div v-if="form.attendType === 'rule'" class="item">
          <div class="item-name">上课主题:</div>
          <div class="item-value">
            <el-input v-model="form.classTheme" size="small" placeholder="输入上课主题" />
          </div>
        </div>
        <div v-else class="item">
          <div class="item-name">上课主题:</div>
          <div class="item-value">
            <el-input v-model="form.classTheme" size="small" placeholder="输入上课主题" />
          </div>
        </div>
      </div>
    </el-row>
    <el-row :gutter="10" class="mb8" style="margin-top: 10px;">
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-collection"
          size="mini"
          @click="handleAutoDealStudentAttendStatusInfo('1')"
        >全到课</el-button>
        <el-button
          type="info"
          icon="el-icon-switch-button"
          size="mini"
          @click="handleAutoDealStudentAttendStatusInfo('3')"
        >全缺勤</el-button>
      </el-col>
    </el-row>
    <el-table ref="table" v-loading="loading" class="add-cla-time-attend-table" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column align="center" prop="studentName" label="学生" fixed="left" />
      <el-table-column align="center" prop="phone" label="是否到课" width="150" fixed="left">
        <template slot-scope="scope">
          <el-radio-group v-model="studentAttendStatusForm[scope.row.studentCourseId].attendStatus" size="mini" @change="attendStatus => handleAttendStatusChange(attendStatus, scope.row)">
            <el-radio-button
              v-for="dict in attendStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{ dict.dictLabel }}</el-radio-button>
          </el-radio-group>
        </template>
      </el-table-column>
      <el-table-column align="center" label="扣减课时" width="100" fixed="left">
        <template slot-scope="scope">
          <el-input-number
            v-model="studentAttendStatusForm[scope.row.studentCourseId].stuLoseHour"
            :disabled="studentAttendStatusForm[scope.row.studentCourseId].disable"
            style="width: 80px"
            controls-position="right"
            :min="0"
          />
        </template>
      </el-table-column>
      <el-table-column align="center" prop="sex" label="性别" :formatter="sexFormatter" />
      <el-table-column align="center" prop="phone" label="联系电话" width="120">
        <template slot-scope="scope">
          <el-tooltip effect="dark" :content="scope.row.contactInfo" placement="top">
            <span>{{ scope.row.phone }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="status" label="状态" :formatter="studentCourseStatusFormatter" />
      <el-table-column align="center" prop="chargeType" label="收费方式">
        <template slot-scope="scope">
          <span>{{ chargeTypeFormatter(scope.row) }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="chargeType" label="剩余课时/天" width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.chargeType === 'date'">
            {{ scope.row.balanceDays }}天
          </span>
          <span v-else>
            <el-tooltip effect="dark" :content="'过期:' + scope.row.expireHour + '课时'" placement="top">
              <span>{{ scope.row.balanceHour - scope.row.expireHour }}课时</span>
            </el-tooltip>
          </span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="status" label="最后续费时间" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastSignTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="备注" width="150">
        <template slot-scope="scope">
          <el-input v-model="studentAttendStatusForm[scope.row.studentCourseId].memo" size="small" placeholder="备注" />
        </template>
      </el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button v-if="dataList.length > 0" :loading="loading" type="primary" @click="handleClaAttend">上课</el-button>
      <el-button @click="open = false">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import staffSelect from '@/components/system/staff/staffSelect'
import claTimeSelect from '@/components/sc/claTime/claTimeSelect'
import roomSelect from '@/components/sc/base/roomSelect'
import claSelect from '@/components/sc/course/cla/claSelect'
import deptSelect from '@/components/system/dept/deptSelect'
import { allDetailInfoById } from '@/api/sc/cla'
import moment from 'moment'
import { searchCourseClaStudent } from '@/api/sc/student/course'
import { claTimeAttend } from '@/api/sc/student/course'
export default {
  components: {
    staffSelect,
    claTimeSelect,
    roomSelect,
    claSelect,
    deptSelect
  },
  props: {
    // 班级
    claId: {
      type: String,
      default: undefined
    },
    // 排课记上课 可记间隔今天几天的排课
    diffNowDay: {
      type: Number,
      default: 0
    },
    // 选定排课 记上课
    appointClaTime: {
      type: Boolean,
      default: false
    },
    // 选定排课 记上课 的 编号
    appointCourseTimeId: {
      type: String,
      default: undefined
    },
    // 是否需选择班级
    needChooseCla: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      title: '记上课',
      open: false,
      loading: false,
      loadingClaDetail: false,
      // 班级信息
      claInfo: {},
      // 课程信息
      claCourseInfo: {},
      // 排课信息
      claTimeInfo: {},
      form: {
        attendType: 'rule',
        deptId: undefined,
        claId: undefined,
        claDate: '',
        startTime: '',
        endTime: '',
        teacherId: undefined,
        roomId: undefined,
        classTheme: undefined
      },
      // 排课记上课方式
      claTimeAttendTypeOptions: [{
        dictValue: 'rule',
        dictLabel: '排课记上课'
      }, {
        dictValue: 'custom',
        dictLabel: '自定义上课'
      }],
      // 自定义上课可选日期
      beginDatePickerOptions: {
        disabledDate(time) {
          return moment(time).add(0, 'days').valueOf() > Date.now()
        }
      },
      dataList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 200,
        claId: this.chooseClaId,
        effect: true
      },
      studentCourseStatusOptions: [],
      chargeTypeOptions: [],
      sexOptions: [],
      // 选择上课的学生
      chooseStudentCourseIds: [],
      // 到课状态
      attendStatusOptions: [],
      // 每个学生 的到课状态
      studentAttendStatusForm: {},

      // 选择的校区
      chooseDeptId: undefined,
      // 选择的班级
      chooseClaId: undefined
    }
  },
  computed: {
  },
  watch: {
    claId: {
      handler(newValue) {
        this.chooseClaId = newValue
      },
      immediate: true
    },
    chooseClaId: {
      handler(newValue) {
        this.queryParams.claId = newValue
      },
      immediate: true
    },
    open: {
      handler(newValue) {
        if (newValue === true) {
          this.resetData()
          if (this.appointClaTime === false && this.needChooseCla === false && this.claId) {
            // 如果非选定排课记上课 不需要选择班级，初始化班级课程信息
            this.loadClaInfo()
          }
          if (this.needChooseCla === false) {
            // 不需要选择班级时 加载排课
            this.$nextTick(() => {
              this.$refs.claTimeSelect.loadRecentDayTime()
            })
          }
        }
      },
      immediate: true
    },
    // 如果指定排课日期，上课方式为排课记上课，且不可变更
    appointClaTime: {
      handler(newValue) {
        if (newValue === true) {
          this.form.attendType = 'rule'
        } else {
          this.form.attendType = 'custom'
        }
      },
      immediate: true
    },
    // 如果需要选择班级 默认自定义记上课
    needChooseCla: {
      handler(newValue) {
        if (newValue === true) {
          this.form.attendType = 'custom'
        } else {
          this.form.attendType = 'rule'
        }
      },
      immediate: true
    }
  },
  created() {
    this.getDictListByDictType('student_course_status').then(response => {
      this.studentCourseStatusOptions = response.data
    })
    this.getDictListByDictType('charge_type').then(response => {
      this.chargeTypeOptions = response.data
    })
    this.getDictListByDictType('sex').then(response => {
      this.sexOptions = response.data
    })
    this.getDictListByDictType('attend_status').then(response => {
      this.attendStatusOptions = response.data
    })
  },
  methods: {
    resetData() {
      this.claInfo = {
        claName: '',
        deptName: '',
        departId: undefined
      }
      this.claCourseInfo = {
        courseName: ''
      }
      this.dataList = []

      if (this.needChooseCla) {
        // 需要选择班级，则已选择班级置空
        this.chooseDeptId = undefined
        this.chooseClaId = undefined
      }
    },
    loadClaInfo() {
      if (this.chooseClaId) {
        this.loadingClaDetail = true
        allDetailInfoById(this.chooseClaId).then(response => {
          this.loadingClaDetail = false
          this.claInfo = response.data.courseCla
          this.claCourseInfo = response.data.course
          this.getList()
        }).catch(() => {
          this.loadingClaDetail = false
        })
      }
    },
    // 选择计划排课日期变化
    handleClaDateChange(claTimeInfo) {
      this.claTimeInfo = claTimeInfo
      this.form.startTime = claTimeInfo.startTime.substr(0, 5)
      this.form.endTime = claTimeInfo.endTime.substr(0, 5)
      this.form.classTheme = claTimeInfo.classTheme
      // 如果是指定排课
      if (this.appointClaTime) {
        this.chooseClaId = claTimeInfo.claId
        // 加载班级 课程信息
        this.loadClaInfo()
      }
    },
    // 无计划排课
    handleNoPlanClaTime() {
      this.msgError('近日无计划排课,无法排课记上课')
    },
    sexFormatter(row, column) {
      return this.selectDictLabel(this.sexOptions, row.sex)
    },
    chargeTypeFormatter(row, column) {
      return this.selectDictLabel(this.chargeTypeOptions, row.chargeType)
    },
    studentCourseStatusFormatter(row, column) {
      return this.selectDictLabel(this.studentCourseStatusOptions, row.status)
    },
    getList() {
      searchCourseClaStudent(this.queryParams).then(response => {
        this.dataList = response.data.rows
        this.total = response.data.total
        this.loading = false
        this.handleAutoDealStudentAttendStatusInfo('1')
      })
    },
    // 自动设置到课状态
    handleAutoDealStudentAttendStatusInfo(attendStatus) {
      this.$nextTick(() => {
        this.chooseStudentCourseIds = []
        this.dataList.forEach(row => {
          this.chooseStudentCourseIds.push(row.studentCourseId)
          this.$refs.table.toggleRowSelection(row, true)
        })
      })
      this.dataList.forEach(item => {
        this.$set(this.studentAttendStatusForm, item.studentCourseId, {
          attendStatus: attendStatus,
          memo: '',
          stuLoseHour: item.chargeType === 'date' ? 0 : (this.form.attendType === 'rule' ? this.claTimeInfo.payHour : this.claInfo.everyStuLoseHour),
          disable: item.chargeType === 'date'
        })
      })
    },
    // 上课
    handleClaAttend() {
      console.log('上课')
      if (this.form.attendType === 'custom') {
        // 自定义上课
        if (this.form.teacherId === undefined || this.form.teacherId === '' || this.form.teacherId === null) {
          this.msgError('请选择上课教师')
          return
        } else if (this.form.claDate === undefined || this.form.claDate === '' || this.form.claDate === null) {
          this.msgError('请选择上课日期')
          return
        } else if (this.form.startTime === undefined || this.form.startTime === '' || this.form.startTime === null) {
          this.msgError('请选择上上课时间')
          return
        } else if (this.form.endTime === undefined || this.form.endTime === '' || this.form.endTime === null) {
          this.msgError('请选择上下课时间')
          return
        }
      } else if (this.form.attendType === 'rule') {
        if (this.form.courseTimeId === undefined || this.form.courseTimeId === '' || this.form.courseTimeId === null) {
          this.msgError('请选择上课日期')
          return
        }
      }
      let checkResult = true
      const studentAttendList = []
      try {
        this.dataList.forEach(item => {
          if (this.chooseStudentCourseIds.indexOf(item.studentCourseId) !== -1) {
            if (item.chargeType !== 'date') {
              const balanceHour = item.balanceHour - item.expireHour
              if (balanceHour < this.studentAttendStatusForm[item.studentCourseId].stuLoseHour) {
                this.msgError(item.studentName + ',课时不足,无法上课!')
                checkResult = false
                throw new Error('课时不足,无法上课!')
              }
            }
            studentAttendList.push({
              attendStatus: this.studentAttendStatusForm[item.studentCourseId].attendStatus,
              studentCourseId: item.studentCourseId,
              memo: this.studentAttendStatusForm[item.studentCourseId].memo,
              stuLoseHour: this.studentAttendStatusForm[item.studentCourseId].stuLoseHour
            })
          }
        })
      } catch (e) {
        console.log('balance hour error')
      }

      if (!checkResult) {
        return
      }
      this.form.studentAttendList = studentAttendList
      this.form.claId = this.chooseClaId
      this.loading = true
      claTimeAttend(this.form).then(response => {
        this.loading = false
        if (response.respCode === '0000') {
          this.$emit('success')
          this.open = false
          this.msgSuccess('记上课成功')
        } else {
          this.msgError(response.respMsg)
        }
      }).catch(() => {
        this.loading = false
      })
    },
    handleSelectionChange(selection) {
      this.chooseStudentCourseIds = selection.map(item => item.studentCourseId)
    },
    handleAttendStatusChange(attendStatus, row) {
      if (row.chargeType !== 'date') {
        if (attendStatus === '2') {
          this.studentAttendStatusForm[row.studentCourseId].stuLoseHour = 0
          this.studentAttendStatusForm[row.studentCourseId].disable = true
        } else {
          this.studentAttendStatusForm[row.studentCourseId].stuLoseHour = this.form.attendType === 'rule' ? this.claTimeInfo.payHour : this.claInfo.everyStuLoseHour
          this.studentAttendStatusForm[row.studentCourseId].disable = false
        }
      }
    },
    // 选择班级
    handleChooseCla(claId) {
      this.chooseClaId = claId
      this.loadClaInfo()
      this.getList()
    }
  }
}
</script>
<style lang="scss" scoped src="@/styles/sc/cla/cla-detail.scss"></style>
<style rel="stylesheet/scss" lang="scss" scoped>
  .cla-detail .cla-base-info .item .item-name{
    width: 90px;
  }
</style>
<style rel="stylesheet/scss" lang="scss">
  .add-cla-time-attend-table .el-radio-button--mini .el-radio-button__inner {
    padding: 7px;
  }
</style>
