<template>
  <el-select
    v-model="studentId"
    v-select-load-more="loadMoreStudent"
    placeholder="选择学生查询相关信息"
    clearable
    filterable
    default-first-option
    remote
    :remote-method="searchStudent"
    :loading="loadingSelect"
    @change="handleStudentChange"
  >
    <el-option
      v-for="item in studentOptions"
      :key="item.studentId"
      :label="item.studentName"
      :value="item.studentId"
    />
  </el-select>
</template>
<script>
import { listSelect as listStudentSelect } from '@/api/sc/student'

export default {
  data() {
    return {
      loadingSelect: false,
      studentId: undefined,
      studentOptions: [],
      searchStudentParam: {
        // 通过什么查询
        searchType: undefined,
        // 查询具体值
        searchValue: undefined,
        pageNum: 1
      }
    }
  },
  created() {
    this.searchStudent('')
  },
  methods: {
    // 查询学生
    searchStudent(query) {
      // 查询值变更时 重置页码
      if (this.searchStudentParam.searchValue !== query) {
        this.searchStudentParam.searchValue = query.trim()
        this.searchStudentParam.pageNum = 1
        this.studentOptions = []
      }
      listStudentSelect({
        search: this.searchStudentParam.searchValue.trim(),
        searchStudentType: this.searchStudentParam.searchType,
        pageNum: this.searchStudentParam.pageNum,
        pageSize: 10
      }).then(response => {
        if (response.respCode === '0000') {
          this.studentOptions = this.studentOptions.concat(response.data)
          this.hasMoreData = response.data.length > 0
        } else {
          this.studentOptions = []
        }
      })
    },
    // 加载更多
    loadMoreStudent() {
      if (this.hasMoreData) {
        this.searchStudentParam.pageNum = this.searchStudentParam.pageNum + 1
        this.searchStudent(this.searchStudentParam.searchValue)
      } else {
        this.msgInfo('无更多学生数据')
      }
    },
    handleStudentChange(val) {
      this.$emit('change', val)
      this.$emit('input', val)
    }
  }
}
</script>
