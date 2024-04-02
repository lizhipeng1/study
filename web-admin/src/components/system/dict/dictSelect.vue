<template>
  <el-select
    v-model="chooseValue"
    v-select-load-more="loadMoreData"
    :placeholder="placeholder"
    filterable
    clearable
    default-first-option
    size="small"
    remote
    :remote-method="searchData"
    @change="handleSelectChange"
  >
    <el-option
      v-for="dict in dictArrayConcatDefault"
      :key="dict.dictValue"
      :label="dict.dictLabel"
      :value="dict.dictValue"
    />
  </el-select>
</template>
<script>
import { getDictPageListByDictType } from '@/api/system/dict/data'

export default {
  props: {
    placeholder: {
      type: String,
      default: undefined
    },
    dictType: {
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
      chooseValue: this.value,
      dictArray: [],
      // 默认信息
      defaultDictObject: undefined,
      searchParam: {
        // 查询具体值
        searchValue: undefined,
        pageNum: 1
      }
    }
  },
  computed: {
    // 包含默认值的 dictArray
    dictArrayConcatDefault() {
      let containDefault = false
      if (this.defaultDictObject !== undefined) {
        this.dictArray.forEach(item => {
          if (item.dictValue === this.defaultDictObject.dictValue) {
            containDefault = true
          }
        })
        if (containDefault) {
          return this.dictArray
        } else {
          return this.dictArray.concat(this.defaultDictObject)
        }
      } else {
        return this.dictArray
      }
    }
  },
  watch: {
    value: {
      handler(newValue, oldValue) {
        this.chooseValue = newValue
        // 获取默认值
        if (newValue) {
          getDictPageListByDictType(this.dictType, {
            pageNum: 1,
            defaultValue: newValue
          }).then(response => {
            if (response.data.rows.length === 1) {
              this.defaultDictObject = response.data.rows[0]
            }
          })
        }
      },
      immediate: true
    },
    dictType: {
      handler(newValue, oldValue) {
        this.searchData('')
      },
      immediate: true
    }
  },
  methods: {
    searchData(query) {
      if (this.searchParam.searchValue !== query) {
        this.searchParam.searchValue = query.trim()
        this.searchParam.pageNum = 1
        this.dictArray = []
      }
      getDictPageListByDictType(this.dictType, this.searchParam).then(response => {
        if (response.respCode === '0000') {
          this.dictArray = this.dictArray.concat(response.data.rows)
          this.hasMoreData = parseInt(response.data.current) < parseInt(response.data.pages)
        } else {
          this.dictArray = []
        }
      })
    },
    loadMoreData() {
      if (this.hasMoreData) {
        this.searchParam.pageNum = this.searchParam.pageNum + 1
        this.searchData(this.searchParam.searchValue)
      } else {
        this.msgInfo('无更多数据')
      }
    },
    handleSelectChange(dictDataId) {
      this.$emit('change', dictDataId)
      this.$emit('input', dictDataId)
    }
  }
}
</script>
