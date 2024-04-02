<template>
  <el-select
    v-model="chooseValue"
    :placeholder="placeholder"
    filterable
    clearable
    default-first-option
    :multiple="multiple"
    @change="handleSelectChange"
  >
    <el-option-group
      v-for="group in groupTagArray"
      :key="group.groupName"
      :label="group.groupName"
    >
      <el-option
        v-for="item in group.itemList"
        :key="item.itemId"
        :label="item.itemName"
        :value="item.itemId"
      />
    </el-option-group>
  </el-select>
</template>
<script>
import { groupSelect } from '@/api/wechat/cp/tag'

export default {
  props: {
    placeholder: {
      type: String,
      default: undefined
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
    }
  },
  data() {
    return {
      chooseValue: this.value,
      groupTagArray: []
    }
  },
  watch: {
    value: {
      handler(newValue, oldValue) {
        this.chooseValue = newValue
      },
      immediate: true
    }
  },
  created() {
    groupSelect().then(response => {
      if (response.respCode === '0000') {
        this.groupTagArray = response.data
      } else {
        this.groupTagArray = []
      }
    })
  },
  methods: {
    handleSelectChange(itemId) {
      this.$emit('change', itemId)
      this.$emit('input', itemId)
    }
  }
}
</script>
