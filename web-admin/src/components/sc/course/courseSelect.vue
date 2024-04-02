<template>
  <el-select
    v-model="courseId"
    v-select-load-more="loadData"
    filterable
    :clearable="clearable"
    :disabled="disabled"
    :multiple="multiple"
    :placeholder="placeholder"
    default-first-option
    style="width: 180px"
    size="small"
    @change="handleSelect"
  >
    <el-option
      v-for="item in courseList"
      :key="item.courseId"
      :label="item.courseName"
      :value="item.courseId"
    />
  </el-select>
</template>
<script>
import { listCourse } from '@/api/sc/course'
export default {
  props: {
    clearable: {
      type: Boolean,
      default: false
    },
    deptId: {
      type: String,
      default: undefined
    },
    value: {
      default: function() {
        if (this.multiple) {
          return []
        } else {
          return undefined
        }
      },
      validator(value) {
        return true
      }
    },
    disabled: {
      type: Boolean,
      default: false
    },
    multiple: {
      type: Boolean,
      default: false
    },
    // 初始化加载全部
    mountedLoadAll: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      placeholder: '请选择课程',
      courseId: this.value,
      courseList: [],
      pageNum: 1,
      hasMoreData: false
    }
  },
  computed: {
    queryParam() {
      return {
        departId: this.deptId
      }
    }
  },
  watch: {
    queryParam: {
      handler(newValue, oldValue) {
        this.pageNum = 1
        this.loadData()
      },
      deep: true
    },
    value: {
      handler(newValue, oldValue) {
        this.courseId = newValue
      },
      immediate: true
    },
    courseId: {
      handler(newValue, oldValue) {
        this.$emit('input', newValue)
      },
      immediate: true
    }
  },
  mounted() {
    if (this.mountedLoadAll && this.courseList.length === 0) {
      this.pageNum = 1
      this.loadData()
    }
  },
  methods: {
    loadData: function() {
      if (this.pageNum === 1) {
        this.courseList = []
        if (this.multiple) {
          this.courseId = []
        } else {
          this.courseId = undefined
        }

        listCourse({
          departId: this.deptId,
          pageNum: this.pageNum
        }).then(response => {
          if (response.respCode === '0000') {
            this.courseList = response.data.rows
            this.hasMoreData = response.data.rows.length > 0
            this.pageNum = this.pageNum + 1
          } else {
            this.msgError(response.respMsg)
          }
        })
      } else if (this.hasMoreData) {
        listCourse({
          departId: this.deptId,
          pageNum: this.pageNum
        }).then(response => {
          if (response.respCode === '0000') {
            this.courseList = this.courseList.concat(response.data.rows)
            this.hasMoreData = response.data.rows.length > 0
            this.pageNum = this.pageNum + 1
          } else {
            this.msgError(response.respMsg)
          }
        })
      }
    },
    handleSelect: function(val) {
      this.$emit('input', val)
      this.$emit('change', val)
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
  .title{
    color: #98a0a7;
    width: 50px;
  }
  .claName{
    max-width: 260px;
    overflow:hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    -o-text-overflow:ellipsis;
  }
  .staffName{
    width: 100px;
    overflow:hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    -o-text-overflow:ellipsis;
  }
  .recruitStatus {
    width: 120px;
    overflow:hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    -o-text-overflow:ellipsis;
  }
  .openDate {
    width: 160px;
    overflow:hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    -o-text-overflow:ellipsis;
  }
  .option{
    padding-right: 15px;
    color: #333;
    font-weight: normal;
  }
</style>
