<!--计划排课上课日期选择-->
<template>
  <el-select
    v-model="courseTimeId"
    filterable
    :placeholder="placeholder"
    default-first-option
    size="small"
    @change="handleSelect"
  >
    <el-option
      v-for="item in planClaTimeOptions"
      :key="item.courseTimeId"
      :label="item.claDate"
      :value="item.courseTimeId"
    >
      <div>
        <div class="inline-block item">
          <span class="title">日期:</span>
          <span class="option">{{ item.claDate }}</span>
        </div>
        <div class="inline-block item">
          <span class="title">时间:</span>
          <span class="option">{{ item.startTime.substr(0, 5) }} ~ {{ item.endTime.substr(0, 5) }}</span>
        </div>
        <div class="inline-block item">
          <span class="title">教室:</span>
          <span class="option">{{ item.roomName }}</span>
        </div>
      </div>
    </el-option>
  </el-select>
</template>
<script>
import { searchRecentDayTimeList } from '@/api/sc/cla/claTime'
export default {
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
    placeholder: {
      type: String,
      default: '选择上课日期'
    },
    // 指定排课
    appointClaTime: {
      type: Boolean,
      default: false
    },
    // 指定排课 编号
    appointCourseTimeId: {
      type: String,
      default: undefined
    },
    value: {
      type: String,
      default: undefined
    }
  },
  data() {
    return {
      courseTimeId: this.value,
      planClaTimeOptions: []
    }
  },
  watch: {
    value: {
      handler(newValue, oldValue) {
        this.courseTimeId = newValue
      },
      immediate: true
    }
  },
  created() {
  },
  methods: {
    loadRecentDayTime: function() {
      const queryParam = {
        attended: false
      }
      if (this.appointClaTime) {
        // 指定排课
        queryParam.courseTimeId = this.appointCourseTimeId
      } else {
        queryParam.claId = this.claId
        queryParam.diffNowDay = this.diffNowDay
      }
      searchRecentDayTimeList(queryParam).then(response => {
        this.planClaTimeOptions = response.data
        if (this.planClaTimeOptions.length > 0) {
          this.courseTimeId = this.planClaTimeOptions[0].courseTimeId
          this.handleSelect(this.courseTimeId)
        } else {
          this.$emit('noPlan')
        }
      })
    },
    handleSelect: function(val) {
      this.$emit('input', val)
      this.planClaTimeOptions.forEach(item => {
        if (item.courseTimeId === val) {
          this.$emit('change', item)
        }
      })
    }
  }
}
</script>
<style ref="stylesheet/scss" lang="scss" scoped>
  .option{
    padding-right: 15px;
    color: #333;
    font-weight: normal;
  }
</style>
