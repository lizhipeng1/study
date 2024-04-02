<!-- 修改上课 -->
<template>
  <el-dialog :title="'修改上课记录:  ' + claInfo.claName + '(' + claInfo.deptName + ')'" :visible.sync="open" class="compact" width="850px">
    <el-row v-loading="loadingClaDetail" class="cla-detail">
      <div class="cla-base-info" style="border-bottom-width: 2px;">
        <div class="item">
          <div class="item-name">所属课程:</div>
          <div class="item-value">{{ claCourseInfo.courseName }}</div>
        </div>
        <div class="item">
          <div class="item-name required">上课教师:</div>
          <div class="item-value">
            <staff-select v-model="form.teacherId" teacher="1" placeholder="请选择上课教师" />
          </div>
        </div>
        <div class="item">
          <div class="item-name required">上课教室:</div>
          <div class="item-value">
            <room-select v-model="form.roomId" :dept-id="claInfo.departId" placeholder="请选择上课教室" />
          </div>
        </div>
        <div class="item">
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
            <span>上课时间:</span>
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
            <span>下课时间:</span>
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
          <div class="item-name required">
            <span>备注:</span>
          </div>
          <div class="item-value">
            <el-input v-model="form.memo" size="small" placeholder="备注" />
          </div>
        </div>
        <div class="item">
          <div class="item-name required">上课主题:</div>
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
    <el-table ref="table" v-loading="loading" class="add-cla-time-attend-table" :data="claStudentList" @selection-change="handleSelectionChange">
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
      <el-button v-if="claStudentList.length > 0" :loading="loading" type="primary" @click="handleClaAttend">确定修改</el-button>
      <el-button @click="open = false">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import staffSelect from '@/components/system/staff/staffSelect'
import roomSelect from '@/components/sc/base/roomSelect'
import { allDetailInfoById } from '@/api/sc/cla'
import { claTimeInfo as loadClaTimeInfo, changeHadClaTimeAttend } from '@/api/sc/cla/claTime'
import { hadClaTimeAttendDetail } from '@/api/sc/cla/claTimeAttend'
import moment from 'moment'
import { searchCourseClaStudent } from '@/api/sc/student/course'
export default {
  components: {
    staffSelect,
    roomSelect
  },
  props: {
    claId: {
      type: String,
      default: undefined
    },
    // 上课记录
    courseTimeId: {
      type: String,
      default: undefined
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
        deptId: undefined,
        claId: undefined,
        claDate: '',
        startTime: '',
        endTime: '',
        teacherId: undefined,
        roomId: undefined,
        classTheme: undefined
      },
      beginDatePickerOptions: {
        disabledDate(time) {
          return moment(time).add(0, 'days').valueOf() > Date.now()
        }
      },
      claStudentList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 200,
        claId: this.claId,
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

      // 班级上课出席列表
      claTimeAttendList: []
    }
  },
  computed: {
    // 已记录出席情况
    claTimeAttendMap() {
      const claTimeAttendMap = {}
      this.claTimeAttendList.forEach(item => {
        claTimeAttendMap[item.studentCourseId] = item
      })
      return claTimeAttendMap
    }
  },
  watch: {
    claId: {
      handler(newValue) {
        this.queryParams.claId = newValue
      },
      immediate: true
    },
    open: {
      handler(newValue) {
        if (newValue === true) {
          this.resetData()
          this.loadClaAndClaTimeInfo()
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
      this.claTimeInfo = {}
      this.claStudentList = []
      this.claTimeAttendList = []
      this.studentAttendStatusForm = {}
    },
    // 加载班级 及 已上课信息
    loadClaAndClaTimeInfo() {
      if (this.claId) {
        this.loadingClaDetail = true
        loadClaTimeInfo(this.courseTimeId).then(response => {
          this.claTimeInfo = response.data
          // 自动设置表单信息
          this.autoSetFormByClaTimeInfo()
          return allDetailInfoById(this.claId)
        }).then(response => {
          this.loadingClaDetail = false
          this.claInfo = response.data.courseCla
          this.claCourseInfo = response.data.course
          this.loadCourseClaStudentAndAttendDetail()
        }).catch(() => {
          this.loadingClaDetail = false
        })
      }
    },
    // 自动设置 表单信息
    autoSetFormByClaTimeInfo() {
      this.form.teacherId = this.claTimeInfo.teacherId
      this.form.roomId = this.claTimeInfo.roomId
      this.form.claDate = this.claTimeInfo.realClaDate
      this.form.startTime = this.claTimeInfo.realStartTime.substr(0, 5)
      this.form.endTime = this.claTimeInfo.realEndTime.substr(0, 5)
      this.form.memo = this.claTimeInfo.memo
      this.form.classTheme = this.claTimeInfo.classTheme
    },
    // 选择计划排课日期变化
    handleClaDateChange(claTimeInfo) {
      this.claTimeInfo = claTimeInfo
      this.form.startTime = claTimeInfo.startTime.substr(0, 5)
      this.form.endTime = claTimeInfo.endTime.substr(0, 5)
      this.form.classTheme = claTimeInfo.classTheme
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
    // 班级所有学员,及到课状态
    loadCourseClaStudentAndAttendDetail() {
      this.loading = true
      let claStudentList = []
      searchCourseClaStudent(this.queryParams).then(response => {
        claStudentList = response.data.rows
        this.total = response.data.total
        return hadClaTimeAttendDetail(this.courseTimeId)
      }).then(response => {
        this.claTimeAttendList = response.data

        // 设置studentAttendStatusForm
        claStudentList.forEach(item => {
          const itemObj = {}
          if (this.claTimeAttendMap.hasOwnProperty(item.studentCourseId)) {
            itemObj.attendStatus = this.claTimeAttendMap[item.studentCourseId].attendStatus
            itemObj.memo = this.claTimeAttendMap[item.studentCourseId].memo
            itemObj.stuLoseHour = this.claTimeAttendMap[item.studentCourseId].payHour
            itemObj.disable = item.chargeType === 'date'
          } else {
            itemObj.attendStatus = '1'
            itemObj.memo = ''
            itemObj.stuLoseHour = item.chargeType === 'date' ? 0 : this.claInfo.everyStuLoseHour
            itemObj.disable = item.chargeType === 'date'
          }
          this.$set(this.studentAttendStatusForm, item.studentCourseId, itemObj)
        })
        this.claStudentList = claStudentList

        // 设置选中
        this.$nextTick(() => {
          this.claStudentList.forEach(item => {
            if (this.claTimeAttendMap.hasOwnProperty(item.studentCourseId)) {
              this.$refs.table.toggleRowSelection(item, true)
            }
          })
        })
        this.loading = false
      })
    },
    // 自动设置到课状态
    handleAutoDealStudentAttendStatusInfo(attendStatus) {
      this.$nextTick(() => {
        this.chooseStudentCourseIds = []
        this.claStudentList.forEach(row => {
          this.chooseStudentCourseIds.push(row.studentCourseId)
          this.$refs.table.toggleRowSelection(row, true)
        })
      })
      this.claStudentList.forEach(item => {
        this.$set(this.studentAttendStatusForm, item.studentCourseId, {
          attendStatus: attendStatus,
          memo: '',
          stuLoseHour: item.chargeType === 'date' ? 0 : this.claInfo.everyStuLoseHour,
          disable: item.chargeType === 'date'
        })
      })
    },
    // 修改
    handleClaAttend() {
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
      let checkResult = true
      const studentAttendList = []
      try {
        this.claStudentList.forEach(item => {
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
      this.form.claId = this.claId
      this.form.courseTimeId = this.courseTimeId
      this.form.attendType = 'change'
      this.loading = true
      changeHadClaTimeAttend(this.form).then(response => {
        this.loading = false
        if (response.respCode === '0000') {
          this.$emit('success')
          this.open = false
          this.msgSuccess('修改成功')
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
          this.studentAttendStatusForm[row.studentCourseId].stuLoseHour = this.claInfo.everyStuLoseHour
          this.studentAttendStatusForm[row.studentCourseId].disable = false
        }
      }
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
