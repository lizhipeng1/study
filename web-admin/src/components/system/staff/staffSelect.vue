<template>
  <el-select
    v-model="staffId"
    v-select-load-more="loadStaff"
    filterable
    :clearable="clearable"
    :placeholder="placeholder"
    default-first-option
    size="small"
    @change="handleSelect"
  >
    <el-option
      v-for="item in staffList"
      :key="item.staffId"
      :label="item.staffName"
      :value="item.staffId"
    >
      <div>
        <div class="inline-block item">
          <span class="title">姓名:</span>
          <span class="option">{{ item.staffName }}</span>
        </div>
        <div class="inline-block item">
          <span class="title">联系电话:</span>
          <span class="option">{{ item.phone }}</span>
        </div>
      </div>
    </el-option>
  </el-select>
</template>
<script>
import { listStaff } from '@/api/system/staff'
export default {
  props: {
    clearable: {
      type: Boolean,
      default: false
    },
    teacher: {
      type: String,
      default: undefined
    },
    placeholder: {
      type: String,
      default: '选择员工'
    },
    value: {
      type: String,
      default: undefined
    }
  },
  data() {
    return {
      staffId: this.value,
      staffList: [],
      pageNum: 1,
      hasMoreData: false
    }
  },
  watch: {
    value: {
      handler(newValue, oldValue) {
        this.staffId = newValue
      },
      immediate: true
    }
  },
  created() {
    this.loadStaff()
  },
  methods: {
    loadStaff: function() {
      if (this.pageNum === 1) {
        listStaff({
          pageNum: this.pageNum,
          teacher: this.teacher
        }).then(response => {
          if (response.respCode === '0000') {
            this.staffList = response.data.rows
            this.hasMoreData = response.data.rows.length > 0
            this.pageNum = this.pageNum + 1
          } else {
            this.msgError(response.respMsg)
          }
        })
      } else if (this.hasMoreData) {
        listStaff({
          pageNum: this.pageNum
        }).then(response => {
          if (response.respCode === '0000') {
            this.staffList = this.staffList.concat(response.data.rows)
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
<style ref="stylesheet/scss" lang="scss" scoped>
  .option{
    padding-right: 15px;
    color: #333;
    font-weight: normal;
  }
</style>
