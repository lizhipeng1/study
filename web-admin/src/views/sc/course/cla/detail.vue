<template>
  <div>
    <el-row v-loading="loading" class="cla-detail">
      <div class="top-name">
        <span>{{ claInfo.claName }}</span>
        <span>({{ claInfo.deptName }})</span>
      </div>
      <div class="cla-base-info">
        <div class="item">
          <div class="item-name">上课校区:</div>
          <div class="item-value">{{ claInfo.deptName }}</div>
        </div>
        <div class="item">
          <div class="item-name">所属课程:</div>
          <div class="item-value">{{ claCourseInfo.courseName }}</div>
        </div>
        <div class="item">
          <div class="item-name">任课教师:</div>
          <div class="item-value">{{ claInfo.teacherName }}</div>
        </div>
        <div class="item">
          <div class="item-name">满班人数:</div>
          <div class="item-value">{{ claInfo.capacity }}</div>
        </div>
        <div class="item">
          <div class="item-name">招生状态:</div>
          <div class="item-value">
            <el-tag v-if="claInfo.recruitStatus === '1'">开放招生</el-tag>
            <el-tag v-if="claInfo.recruitStatus === '2'" type="info">满班后停止</el-tag>
            <el-tag v-if="claInfo.recruitStatus === '0'" type="danger">停止招生</el-tag>
          </div>
        </div>
        <div class="item">
          <div class="item-name">满班人数:</div>
          <div class="item-value">{{ claInfo.capacity }}</div>
        </div>
        <div class="item">
          <div class="item-name">开班日期:</div>
          <div class="item-value">{{ claInfo.openDate }}</div>
        </div>
        <div class="item">
          <div class="item-name">结班日期:</div>
          <div class="item-value">{{ claInfo.closeDate }}</div>
        </div>
        <div class="item">
          <div class="item-name">收费方式:</div>
          <div class="item-value">
            <el-tooltip effect="dark" placement="left-start">
              <span>{{ courseChargeStr }}</span>
              <template slot="content">
                <div v-for="item in courseChargeList" :key="item.label">{{ item.label }}</div>
              </template>
            </el-tooltip>
          </div>
        </div>
        <div class="item">
          <div class="item-name">上课时间:</div>
          <div class="item-value">
            <el-tooltip effect="dark" placement="left-start">
              <span>{{ claTimeStr }}</span>
              <template slot="content">
                <div v-for="item in claTimeList" :key="item">{{ item }}</div>
              </template>
            </el-tooltip>
          </div>
        </div>
        <div class="item">
          <div class="item-name">备注:</div>
          <div class="item-value">{{ claInfo.memo||'暂无' }}</div>
        </div>
      </div>
    </el-row>
    <div class="tab-container">
      <el-tabs v-model="activeTab" tab-position="top" @tab-click="handleTabChange">
        <el-tab-pane name="claStudent" label="学员">
          <span slot="label" style="font-size: 16px;"><i class="el-icon-user" /> 学员</span>
          <course-student-table ref="courseStudentTable" :cla-id="claId" :belong-course-id="claCourseInfo.courseId" :belong-depart-id="claInfo.departId" />
        </el-tab-pane>
        <el-tab-pane name="claTimeRule" label="排课">
          <span slot="label" style="font-size: 16px;"><i class="el-icon-date" /> 课表</span>
          <cla-time-calendar ref="claTimeCalendar" :dept-id="claInfo.deptId" :cla-id="claInfo.claId" :readonly="false" :add-time-rule="true" can-cla-time-attend />
        </el-tab-pane>
        <el-tab-pane name="claTimeAttend" label="上课记录">
          <span slot="label" style="font-size: 16px;"><i class="el-icon-document-copy" /> 上课记录 / 记上课</span>
          <cla-time-attend-table ref="claTimeAttendTable" :cla-id="claInfo.claId" :readonly="false" />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
import { allDetailInfoById } from '@/api/sc/cla'
import courseStudentTable from '@/components/sc/student/courseStudentTable'
import claTimeCalendar from '@/components/sc/claTime/claTimeCalendar'
import claTimeAttendTable from '@/components/sc/claTime/claTimeAttendTable'
export default {
  components: {
    courseStudentTable,
    claTimeCalendar,
    claTimeAttendTable
  },
  data() {
    return {
      claId: undefined,
      claInfo: {},
      claCourseInfo: {},
      courseChargeList: [],
      claTimeList: [],
      activeTab: 'claStudent',
      loading: false
    }
  },
  computed: {
    courseChargeStr() {
      const chargeStrArray = []
      this.courseChargeList.forEach(item => {
        chargeStrArray.push(item.label)
      })
      return chargeStrArray.toString()
    },
    claTimeStr() {
      const timeStrArray = []
      this.claTimeList.forEach(item => {
        timeStrArray.push(item)
      })
      return timeStrArray.toString()
    }
  },
  created() {
    const params = this.$route.params
    this.claId = params.claId
    this.loadClaInfo()
  },
  methods: {
    loadClaInfo() {
      this.loading = true
      allDetailInfoById(this.claId).then(response => {
        this.loading = false
        this.claInfo = response.data.courseCla
        this.claCourseInfo = response.data.course
        this.courseChargeList = response.data.courseChargeList
        this.claTimeList = response.data.claTimeList
      }).catch(() => {
        this.loading = false
      })
    },
    handleTabChange(tab) {
      if (tab.name === 'claStudent') {
        this.$refs.courseStudentTable.getList()
      } else if (tab.name === 'claTimeRule') {
        this.$refs.claTimeCalendar.loadData()
      } else if (tab.name === 'claTimeAttend') {
        this.$refs.claTimeAttendTable.getList()
      }
    }
  }
}
</script>
<style lang="scss" scoped src="@/styles/sc/cla/cla-detail.scss"></style>
<style rel="stylesheet/scss" lang="scss" scoped>
  .tab-container{
    padding: 5px 15px;
  }
</style>
