<template>
  <el-select
    v-model="tagIds"
    v-select-load-more="loadTag"
    multiple
    filterable
    allow-create
    :placeholder="placeholder"
    default-first-option
    style="width: 180px"
    size="small"
    @change="handleSelect"
  >
    <el-option
      v-for="item in tagList"
      :key="item.tagName"
      :label="item.tagName"
      :value="item.tagName"
    />
  </el-select>
</template>
<script>
import { listTag } from '@/api/system/tag'
export default {
  props: {
    tagType: {
      type: String,
      default: '1'
    }
  },
  data() {
    return {
      placeholder: '选择标签',
      tagIds: [],
      tagList: [],
      pageNum: 1,
      hasMoreData: false
    }
  },
  watch: {
  },
  created() {
    this.loadTag()
  },
  methods: {
    loadTag: function() {
      if (this.pageNum === 1) {
        listTag({
          pageNum: this.pageNum,
          tagType: this.tagType
        }).then(response => {
          if (response.respCode === '0000') {
            this.tagList = response.data.rows
            this.hasMoreData = response.data.rows.length > 0
            this.pageNum = this.pageNum + 1
          } else {
            this.msgError(response.respMsg)
          }
        })
      } else if (this.hasMoreData) {
        listTag({
          pageNum: this.pageNum,
          tagType: this.tagType
        }).then(response => {
          if (response.respCode === '0000') {
            this.tagList = this.tagList.concat(response.data.rows)
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
