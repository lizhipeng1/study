<template>
  <div class="app-container">
    <el-tabs v-model="activeTab" tab-position="top" @tab-click="handleTabChange">
      <el-tab-pane name="hadClaTimeList" label="上课记录">
        <span slot="label" style="font-size: 16px;"><i class="el-icon-s-order" /> 上课记录</span>
        <had-cla-time-table ref="hadClaTimeTable" />
      </el-tab-pane>
      <el-tab-pane name="attendClaTime" label="记上课">
        <span slot="label" style="font-size: 16px;"><i class="el-icon-s-promotion" /> 记上课</span>
        <cla-time-table ref="claTimeTable" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import hadClaTimeTable from '@/components/sc/claTime/hadClaTimeTable'
import claTimeTable from '@/components/sc/claTime/claTimeTable'
export default {
  components: {
    hadClaTimeTable,
    claTimeTable
  },
  data() {
    return {
      activeTab: 'hadClaTimeList'
    }
  },
  watch: {
  },
  created() {
    const params = this.$route.params
    if (typeof params === 'object') {
      if (params.activeTab) {
        this.activeTab = params.activeTab
      }
    }
  },
  methods: {
    handleTabChange(tab) {
      if (tab.name === 'hadClaTimeList') {
        // 已上课 记录
      } else if (tab.name === 'attendClaTime') {
        // 记录
      }
    }
  }
}
</script>
