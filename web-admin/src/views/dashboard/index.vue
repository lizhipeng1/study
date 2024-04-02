<template>
  <div class="dashboard-container">
    <div class="top-container">
      <el-row v-loading="loading">
        <div class="header">
          <div class="title">总览</div>
        </div>
        <div class="content-container">
          <router-link to="/edu/claTimeRule?activeTab=calendar">
            <div class="content-item">
              <div class="icon">
                <svg-icon icon-class="dashboard-course" style="height: 48px;width: 28px;" />
              </div>
              <div class="txt">
                <div class="value">{{ dashboardData.completeTodayClaTimeCnt }}/{{ dashboardData.todayClaTimeCnt }}节</div>
                <div class="name">已上课/今日排课</div>
              </div>
            </div>
          </router-link>
          <router-link to="/order/handle/index?handleDate=thisMonth">
            <div class="content-item">
              <div class="icon">
                <svg-icon icon-class="dashboard-order" style="height: 48px;width: 28px;" />
              </div>
              <div class="txt">
                <div class="value">{{ dashboardData.todayOrderCnt }}/{{ dashboardData.thisMonthOrderCnt }}笔</div>
                <div class="name">今日报名/本月报名</div>
              </div>
            </div>
          </router-link>
          <div class="content-item">
            <div class="icon">
              <svg-icon icon-class="dashboard-student" style="height: 48px;width: 36px;" />
            </div>
            <div class="txt" style="padding-left: 8px;">
              <div class="value">{{ dashboardData.todayRealAttendCnt }}/{{ dashboardData.todayNeedAttendCnt }}人</div>
              <div class="name">实到/今日应到人次</div>
            </div>
          </div>
          <div class="content-item">
            <div class="icon">
              <svg-icon icon-class="dashboard-cla-time" style="height: 48px;width: 32px;" />
            </div>
            <div class="txt" style="padding-left: 12px;">
              <div class="value">{{ dashboardData.todayRealCostHour }}/{{ dashboardData.todayNeedCostHour }}课时</div>
              <div class="name">实消/今日应消课时</div>
            </div>
          </div>
          <router-link to="/edu/student">
            <div class="content-item">
              <div class="icon">
                <svg-icon icon-class="dashboard-student-cnt" style="height: 48px;width: 28px;" />
              </div>
              <div class="txt">
                <div class="value">{{ dashboardData.studentCnt }}人</div>
                <div class="name">总学员人数</div>
              </div>
            </div>
          </router-link>
        </div>
      </el-row>
    </div>
    <!-- <div class="center-container">
      <div class="left-container">
        <div class="header">
          <div class="title">常用功能</div>
        </div>
        <div class="content-container">
        </div>
      </div>
      <div class="right-container">
        <div class="header">
          <div class="title">通知提醒</div>
        </div>
        <div class="content-container">
        </div>
      </div>
    </div>-->
    <div class="calendar-container">
      <div class="left-container">
        <div class="header">
          <div class="title">课表</div>
        </div>
        <div class="content-container">
          <cla-time-calendar ref="claTimeCalendar" readonly can-cla-time-attend class="calendar" />
        </div>
      </div>
      <div class="right-container">
        <el-row v-loading="loading">
          <div class="top">
            <div class="header">
              <div class="title">快捷入口</div>
            </div>
            <div class="content-container">
              <router-link to="/edu/cla">
                <div class="content-item">
                  <div class="icon">
                    <svg-icon icon-class="dashboard-cla" style="height: 33px;width: 28px;" />
                  </div>
                  <div class="txt">
                    <div class="value">班级</div>
                    <div class="name">班级数 <span class="cnt">{{ dashboardData.claCnt }}</span></div>
                  </div>
                </div>
              </router-link>
              <router-link to="/edu/course">
                <div class="content-item">
                  <div class="icon">
                    <svg-icon icon-class="dashboard-course" style="height: 33px;width: 28px;" />
                  </div>
                  <div class="txt">
                    <div class="value">课程</div>
                    <div class="name">课程数 <span class="cnt">{{ dashboardData.courseCnt }}</span></div>
                  </div>
                </div>
              </router-link>
              <router-link to="/order/handle/index">
                <div class="content-item">
                  <div class="icon">
                    <svg-icon icon-class="dashboard-sign-up" style="height: 33px;width: 28px;" />
                  </div>
                  <div class="txt">
                    <div class="value single">报名</div>
                  </div>
                </div>
              </router-link>
              <router-link :to="{name: '上课记录',params: {activeTab: 'attendClaTime'}}">
                <div class="content-item">
                  <div class="icon">
                    <svg-icon icon-class="dashboard-record" style="height: 33px;width: 28px;" />
                  </div>
                  <div class="txt">
                    <div class="value single">记上课</div>
                  </div>
                </div>
              </router-link>
              <router-link to="/edu/student">
                <div class="content-item">
                  <div class="icon">
                    <svg-icon icon-class="dashboard-student-cnt" style="height: 33px;width: 28px;" />
                  </div>
                  <div class="txt">
                    <div class="value">学员</div>
                    <div class="name">学员 <span class="cnt">{{ dashboardData.studentCnt }}</span>人</div>
                  </div>
                </div>
              </router-link>
            </div>
          </div>
          <div class="bottom">
            <div class="header">
              <div class="title">通知提醒</div>
            </div>
            <div class="content-container">
              <div class="content-item">
                <div class="icon">
                  <svg-icon icon-class="dashboard-notice" style="height: 65px;width: 35px;" />
                </div>
                <div class="txt item-circle" style="line-height: 20px;">
                  <router-link :to="{name: '学生信息',params: {activeTab: 'studentSignUpList', minBalanceDay: 10}}">
                    <div class="name">10日内即将到期 <span class="cnt">{{ dashboardData.dateWillExpireCnt }}</span>人</div>
                  </router-link>
                  <router-link :to="{name: '学生信息',params: {activeTab: 'studentSignUpList', minBalanceHour: 5}}">
                    <div class="name">剩余课时小于5课时 <span class="cnt">{{ dashboardData.hourWillExpireCnt }}</span>人</div>
                  </router-link>
                  <div class="name">欠费学员 <span class="cnt">{{ dashboardData.arrearsStudentCnt }}</span>人</div>
                </div>
              </div>
            </div>
          </div>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import claTimeCalendar from '@/components/sc/claTime/claTimeCalendar'
import { dashboardData } from '@/api/report/dashboard'

export default {
  name: 'Dashboard',
  components: {
    claTimeCalendar
  },
  data() {
    return {
      dashboardData: {},
      loading: false
    }
  },
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  created() {
    this.$nextTick(() => {
      this.$refs.claTimeCalendar.loadData()
    })
    this.loading = true
    dashboardData().then(response => {
      this.loading = false
      this.dashboardData = response.data
    }).catch(() => {
      this.loading = false
    })
  },
  methods: {
  }
}
</script>

<style lang="scss" scoped src="@/styles/dashboard/index.scss"></style>
