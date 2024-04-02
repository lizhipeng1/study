<template>
  <div>
    <el-row style="margin-bottom: 15px;">
      <el-col v-if="!readonly" :span="6">
        <div style="height: 40px;align-items: center;display: flex;justify-content: start;">
          <el-button
            size="small"
            type="info"
            icon="el-icon-edit-outline"
            @click="handleCalendarAddClaTime"
          >课表排课</el-button>
          <el-button
            v-if="!readonly && addTimeRule"
            v-hasPermi="['sc:claTimeRule:add']"
            type="info"
            icon="el-icon-document-copy"
            size="small"
            @click="handleAddTimeRule"
          >规则排课</el-button>
        </div>
      </el-col>
      <el-col v-if="!readonly" :span="12">
        <div style="height: 40px;align-items: center;display: flex;justify-content: center;">
          <div style="padding: 0 15px;cursor: pointer;" @click="calendarBeginDateClick(-7)">
            <el-icon class="el-icon-arrow-left" style="font-size: 30px;" />
          </div>
          <div>
            <el-date-picker
              v-model="calendarBeginDate"
              size="small"
              type="date"
              style="width: 150px;"
              value-format="yyyy-MM-dd"
              :picker-options="weekBeginDatePickerOptions"
              placeholder="开始日期"
              @change="handlerCalendarBeginDateChange"
            />
            -
            <el-date-picker
              v-model="calendarEndDate"
              clearable
              size="small"
              type="date"
              style="width: 150px;"
              value-format="yyyy-MM-dd"
              placeholder="结束日期"
              disabled
            />
          </div>
          <div style="padding: 0 15px;cursor: pointer;" @click="calendarBeginDateClick(7)">
            <el-icon class="el-icon-arrow-right" style="font-size: 30px" />
          </div>
        </div>
      </el-col>
      <el-col v-if="!readonly" :span="6">&nbsp;</el-col>
      <el-col v-if="readonly" :span="24">
        <div style="height: 40px;align-items: center;display: flex;justify-content: center;">
          <div style="padding: 0 15px;cursor: pointer;" @click="calendarBeginDateClick(-7)">
            <el-icon class="el-icon-arrow-left" style="font-size: 30px;" />
          </div>
          <div>
            <el-date-picker
              v-model="calendarBeginDate"
              size="small"
              type="date"
              style="width: 150px;"
              value-format="yyyy-MM-dd"
              :picker-options="weekBeginDatePickerOptions"
              placeholder="开始日期"
              @change="handlerCalendarBeginDateChange"
            />
            -
            <el-date-picker
              v-model="calendarEndDate"
              clearable
              size="small"
              type="date"
              style="width: 150px;"
              value-format="yyyy-MM-dd"
              placeholder="结束日期"
              disabled
            />
          </div>
          <div style="padding: 0 15px;cursor: pointer;" @click="calendarBeginDateClick(7)">
            <el-icon class="el-icon-arrow-right" style="font-size: 30px" />
          </div>
        </div>
      </el-col>
    </el-row>
    <div style="overflow-x: auto;">
      <table v-if="columnTitles.length > 0" v-loading="loading" cellspacing="0" cellpadding="0" border="0" class="cla-time-table">
        <thead>
          <tr>
            <th>
              <div class="time">时间</div>
            </th>
            <th v-for="item in columnTitles" :key="item.day">
              <div class="week">{{ item.weekName }} ({{ item.day }}) {{ item.count }}课次</div>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in claTimeContainer" :key="item.time">
            <td class="cell">
              <div class="time">{{ item.time }}</div>
            </td>
            <td v-for="(claTimeArray, claTimeArrayIdx) in item.claTimeWeekDayMap" :key="claTimeArrayIdx" class="cell">
              <div v-for="(claTime, claTimeIdx) in claTimeArray" :key="claTime.courseTimeId" class="cla-time-item-container">
                <div v-if="claTimeIdx < 2" class="cla-time-item success" :style="'background-color: ' + claTime.claColor + ';'">
                  <div class="overflow-ellipsis">{{ claTime.claName }}</div>
                  <div class="overflow-ellipsis">{{ claTime.staffName }} {{ claTime.startTime }} ~ {{ claTime.endTime }}</div>
                  <div class="right-top-tag">
                    <div v-if="claTime.claTimeStatus === '2'" class="claTimeStatus success"><span>已上课</span></div>
                    <div v-if="claTime.claTimeStatus === '1'" class="claTimeStatus"><span>未上课</span></div>
                  </div>
                  <div class="tooltip">
                    <div class="content">
                      <div class="title">{{ claTime.claName }}</div>
                      <div class="item"><i class="el-icon-collection" /> {{ claTime.courseName }}</div>
                      <div class="item"><i class="el-icon-notebook-1" /> {{ claTime.claDate }}</div>
                      <div class="item"><i class="el-icon-mouse" /> {{ claTime.weekDay }}</div>
                      <div class="item"><i class="el-icon-paperclip" /> {{ claTime.startTime }} ~ {{ claTime.endTime }}</div>
                      <div class="item"><i class="el-icon-data-analysis" /> {{ claTime.staffName }}</div>
                      <div class="item"><i class="el-icon-place" /> {{ claTime.studentCount }}人</div>
                      <div class="item"><i class="el-icon-guide" /> {{ claTime.roomName }}</div>
                    </div>
                  </div>
                  <div class="mask">
                    <div v-if="canClaTimeAttend || !readonly" class="options">
                      <span v-if="canClaTimeAttend && claTime.claTimeStatus === '1'" @click="handleClaTimeAttend(claTime)">记上课</span>
                      <span v-if="!readonly && !afterNow(claTime.claDate) && claTime.claTimeStatus === '1'" @click="changeClaTime(claTime)">调课</span>
                      <span v-if="!readonly && claTime.claTimeStatus !== '2'" @click="deleteClaTime(claTime)">删除</span>
                    </div>
                  </div>
                </div>
              </div>
              <div v-if="claTimeArray.length > 2" class="show-more" @click="showMore(claTimeArray)">
                查看更多
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <el-dialog title="查看更多" :visible.sync="showMoreData" :modal="false" width="190px" style="text-align: center;">
      <div class="cell">
        <div v-for="(claTime) in moreData" :key="claTime.courseTimeId" class="cla-time-item-container">
          <div class="cla-time-item success" :style="'background-color: ' + claTime.claColor + ';'">
            <div class="overflow-ellipsis">{{ claTime.claName }}</div>
            <div class="overflow-ellipsis">{{ claTime.staffName }} {{ claTime.startTime }} ~ {{ claTime.endTime }}</div>
            <div class="right-top-tag">
              <div v-if="claTime.claTimeStatus === '2'" class="claTimeStatus success"><span>已上课</span></div>
              <div v-if="claTime.claTimeStatus === '1'" class="claTimeStatus"><span>未上课</span></div>
            </div>
            <div class="tooltip">
              <div class="content">
                <div class="title">{{ claTime.claName }}</div>
                <div class="item"><i class="el-icon-collection" /> {{ claTime.courseName }}</div>
                <div class="item"><i class="el-icon-notebook-1" /> {{ claTime.claDate }}</div>
                <div class="item"><i class="el-icon-mouse" /> {{ claTime.weekDay }}</div>
                <div class="item"><i class="el-icon-paperclip" /> {{ claTime.startTime }} ~ {{ claTime.endTime }}</div>
                <div class="item"><i class="el-icon-data-analysis" /> {{ claTime.staffName }}</div>
                <div class="item"><i class="el-icon-place" /> {{ claTime.studentCount }}人</div>
                <div class="item"><i class="el-icon-guide" /> {{ claTime.roomName }}</div>
              </div>
            </div>
            <div class="mask">
              <div v-if="canClaTimeAttend || !readonly" class="options">
                <span v-if="canClaTimeAttend && claTime.claTimeStatus === '1'" @click="handleClaTimeAttend(claTime)">记上课</span>
                <span v-if="!readonly && !afterNow(claTime.claDate) && claTime.claTimeStatus === '1'" @click="changeClaTime(claTime)">调课</span>
                <span v-if="!readonly && claTime.claTimeStatus !== '2'" @click="deleteClaTime(claTime)">删除</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>
    <change-time ref="changeTime" @success="loadData()" />
    <change-time-rule ref="changeTimeRule" @success="loadData" />
    <add-cla-time-attend
      ref="addClaTimeAttend"
      :appoint-course-time-id="chooseCourseTimeId"
      appoint-cla-time
      @success="handleClaTimeAttendSuccess"
    />
  </div>
</template>
<script>
import { searchListForCalendar, delTime } from '@/api/sc/cla/claTime'
import changeTime from '@/components/sc/claTime/changeTime'
import changeTimeRule from '@/components/sc/claTime/changeTimeRule'
import moment from 'moment'
import { getCla } from '@/api/sc/cla'
import addClaTimeAttend from '@/components/sc/claTime/addClaTimeAttend'
export default {
  components: {
    changeTime,
    changeTimeRule,
    addClaTimeAttend
  },
  props: {
    beginDate: {
      type: String,
      default: undefined
    },
    endDate: {
      type: String,
      default: undefined
    },
    deptId: {
      type: String,
      default: undefined
    },
    claId: {
      type: String,
      default: undefined
    },
    studentId: {
      type: String,
      default: undefined
    },
    teacherId: {
      type: String,
      default: undefined
    },
    // 是否只读(只能看 不能改)
    readonly: {
      type: Boolean,
      default: false
    },
    // 是否允许记上课
    canClaTimeAttend: {
      type: Boolean,
      default: false
    },
    // 是否允许添加排课规则
    addTimeRule: {
      type: Boolean,
      default: false
    },
    // 是否需要输入 学生编号 参数
    needStudentIdParam: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      columnTitles: [],
      claTimeContainer: [],
      showMoreData: false,
      moreData: [],
      loading: false,
      weekBeginDatePickerOptions: {
        disabledDate(time) {
          return moment(time).weekday() !== 1
        }
      },
      calendarBeginDate: '',
      calendarEndDate: '',

      // 已选择排课
      chooseCourseTimeId: undefined
    }
  },
  watch: {
    beginDate: {
      handler(newValue, oldValue) {
        this.calendarBeginDate = this.beginDate
      },
      immediate: true
    },
    calendarBeginDate: {
      handler(newValue, oldValue) {
      },
      immediate: true
    },
    claId: {
      handler(newValue, oldValue) {
      },
      immediate: true
    },
    studentId: {
      handler(newValue, oldValue) {
      },
      immediate: true
    },
    teacherId: {
      handler(newValue, oldValue) {
      },
      immediate: true
    },
    deptId: {
      handler(newValue, oldValue) {
      },
      immediate: true
    }
  },
  created() {
    this.calendarBeginDate = moment(new Date()).weekday(1).format('YYYY-MM-DD')
    this.changeCalendarBeginDate(this.calendarBeginDate)
  },
  methods: {
    afterNow(date) {
      return moment(date).add(1, 'days').toDate().getTime() < new Date().getTime()
    },
    loadData() {
      if (this.needStudentIdParam && !this.studentId) {
        return
      }
      this.loading = true
      searchListForCalendar({
        beginDate: this.calendarBeginDate,
        endDate: this.calendarEndDate,
        claId: this.claId,
        studentId: this.studentId,
        teacherId: this.teacherId,
        deptId: this.deptId
      }).then(response => {
        this.loading = false
        if (response.respCode === '0000') {
          if (response.data.columnTitles && response.data.claTimeContainer) {
            this.columnTitles = response.data.columnTitles
            this.claTimeContainer = response.data.claTimeContainer
          }
        }
      }).catch(() => {
        this.loading = false
      })
    },
    showMore(claTimeArray) {
      this.showMoreData = true
      this.moreData = claTimeArray
    },
    // 调课
    changeClaTime(claTime) {
      this.$refs.changeTime.open = true
      this.$refs.changeTime.title = '调课'
      this.$refs.changeTime.reset()
      this.$refs.changeTime.openUpdateByOldInfo(claTime, claTime.courseTimeId)
    },
    // 排课
    addClaTme() {
      this.$refs.changeTime.reset()
      this.$refs.changeTime.title = '排课'
      this.$refs.changeTime.oldTimeInfo = {}
      this.$refs.changeTime.open = true
      this.$refs.changeTime.setDefaultClaInfo(this.claId)
    },
    // 删除单次排课
    deleteClaTime(claTime) {
      this.$confirm('是否确认删除本排课?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delTime(claTime.courseTimeId)
      }).then((response) => {
        if (response.respCode === '0000') {
          this.msgSuccess('删除成功')
          this.loadData()
        } else {
          this.msgError(response.respMsg)
        }
      }).catch(function() {})
    },
    // 课表排课
    handleCalendarAddClaTime() {
      this.addClaTme()
    },
    // 规则排课
    handleAddTimeRule() {
      this.loading = true
      getCla(this.claId).then(response => {
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
    // 开始时间变更
    calendarBeginDateClick(days) {
      this.calendarBeginDate = moment(this.calendarBeginDate).add(days, 'days').format('YYYY-MM-DD')
      this.calendarEndDate = moment(this.calendarBeginDate).add(6, 'days').format('YYYY-MM-DD')
      this.loadData()
    },
    // 开始时间变更 计算结束时间
    changeCalendarBeginDate(calendarBeginDate) {
      if (calendarBeginDate) {
        this.calendarEndDate = moment(calendarBeginDate).add(6, 'days').format('YYYY-MM-DD')
      } else {
        this.calendarEndDate = undefined
      }
    },
    // 开始时间变更 计算结束时间
    handlerCalendarBeginDateChange(calendarBeginDate) {
      this.changeCalendarBeginDate(calendarBeginDate)
      this.loadData()
    },
    // 记上课
    handleClaTimeAttend(claTime) {
      this.chooseCourseTimeId = claTime.courseTimeId
      this.$refs.addClaTimeAttend.open = true
    },
    // 记上课成功
    handleClaTimeAttendSuccess() {
      this.loadData()
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  $claTimeColumnWidth: 125px;
  $claTimeColumnHeight: 46px;
  $Padding8: 8px;
  .cla-time-table {
    font-size: 12px;
    border: 1px solid #ebeef5;
    border-right: none;
    border-bottom: none;

    th {
      border-right: 1px solid #ebeef5;
      border-bottom: 1px solid #ebeef5;
      padding: $Padding8;
      text-overflow: ellipsis;
      color: #606266;
      font-size: 12px;
      .week{
        width: $claTimeColumnWidth;
        line-height: 35px;
      }
      .time{
        width: 80px;
        line-height: 35px;
      }
    }
  }
  .overflow-ellipsis {
    overflow:hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    -o-text-overflow:ellipsis;
  }

  .cell {
    border-right: 1px solid #ebeef5;
    border-bottom: 1px solid #ebeef5;
    text-overflow: ellipsis;
    text-align: center;
    color: #606266;
    .time{
      font-weight: 500;
      min-height: 80px;
      align-items: center;
      display: flex;
      justify-content: center;
    }

    &:last-child .cla-time-item-container .cla-time-item{
      .tooltip {
        left: - ($claTimeColumnWidth + $Padding8 * 2);
      }
    }

    // 每个课程
    .cla-time-item-container {
      margin-top: 1px;
      width: $claTimeColumnWidth + $Padding8 * 2;
      &:first-child {
        margin-top: 0px;
      }

      .cla-time-item {
        height: $claTimeColumnHeight;
        cursor: pointer;
        position: relative;
        font-size: 12px;
        padding: 5px $Padding8;
        line-height: 1.5;
        text-align: left;
        &.success {
          background-color: #67C23A;
          color: #fff;;
        }

        // 右上角 角标
        .right-top-tag {
          width: 100%;
          height: 100%;
          overflow: hidden;
          position: absolute;
          top: 0px;
          right: 0px;
          bottom: 0px;
          left: 0px;

          .claTimeStatus{
            transform: rotate(45deg);
            height: 16px;
            font-size: 12px;
            line-height: 16px;
            position: absolute;
            right: -14px;
            top: 3px;
            width: 50px;
            text-align: center;
            background-color: #F56C6C;
            color: #fff;

            span{
              display: inline-block;
              transform: scale(0.7, 0.7);
            }

            &.success {
              background-color: #67C23A;
              color: #fff;
            }
          }
        }

        .tooltip {
          z-index: 2;
          position: absolute;
          top: 0;
          left: $claTimeColumnWidth + $Padding8 * 2;
          background-color: rgb(73,87,105);
          color: #fff;
          width: $claTimeColumnWidth + $Padding8 * 2;
          border-radius: 2px;
          display: none;
          .content {
            line-height: 1.5;
            padding: 10px;
            .title {
              font-size: 14px;
              font-weight: bolder;
              padding: 8px 0;
            }
            .item {
              margin-top: 5px;
            }
          }
        }
        .mask {
          z-index: 2;
          position: absolute;
          top: 0;
          left: 0;
          display: none;
          background-color: #333;
          opacity: 0.6;
          height: $claTimeColumnHeight;
          width: 100%;
          .options {
            align-items: center;
            display: flex;
            justify-content: center;
            color: #fff;
            height: 100%;

            span{
              padding: 0 5px;
            }
            span:hover {
              text-decoration: underline;
            }
          }
        }

        &:hover {
          .tooltip,.mask {
            display: block;
          }
        }
      }
    }

    // 查看更多
    .show-more {
      font-size: 12px;
      padding: 2px;
      text-align: center;
      width: 90px;
      margin: auto;
      border-radius: 5px;
      cursor: pointer;
    }
  }
</style>
