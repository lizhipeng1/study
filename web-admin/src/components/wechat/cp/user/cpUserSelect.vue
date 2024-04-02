<template>
  <el-select
    v-model="userid"
    v-select-load-more="loadCpUser"
    filterable
    :multiple="multiple"
    :multiple-limit="multipleLimit"
    :clearable="clearable"
    :placeholder="placeholder"
    default-first-option
    @change="handleSelect"
  >
    <el-option
      v-for="item in cpUserList"
      :key="item.userid"
      :label="item.name + '-' + item.userid"
      :value="item.userid"
    />
  </el-select>
</template>
<script>
import { select } from '@/api/wechat/cp/user'
export default {
  props: {
    clearable: {
      type: Boolean,
      default: false
    },
    placeholder: {
      type: String,
      default: '选择员工'
    },
    value: {
      type: Array,
      default: function() {
        return []
      }
    },
    multiple: {
      type: Boolean,
      default: false
    },
    multipleLimit: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      userid: this.value,
      cpUserList: [],
      pageNum: 1,
      hasMoreData: false
    }
  },
  watch: {
    value: {
      handler(newValue, oldValue) {
        this.userid = newValue
      },
      immediate: true
    }
  },
  created() {
    this.loadCpUser()
  },
  methods: {
    loadCpUser: function() {
      if (this.pageNum === 1) {
        select({
          pageNum: this.pageNum
        }).then(response => {
          if (response.respCode === '0000') {
            this.cpUserList = response.data.rows
            this.hasMoreData = response.data.rows.length > 0
            this.pageNum = this.pageNum + 1
          } else {
            this.msgError(response.respMsg)
          }
        })
      } else if (this.hasMoreData) {
        select({
          pageNum: this.pageNum
        }).then(response => {
          if (response.respCode === '0000') {
            this.cpUserList = this.cpUserList.concat(response.data.rows)
            this.hasMoreData = response.data.rows.length > 0
            this.pageNum = this.pageNum + 1
          } else {
            this.msgError(response.respMsg)
          }
        })
      }
    },
    handleSelect: function(val) {
      this.$emit('change', val)
      this.$emit('input', val)
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
