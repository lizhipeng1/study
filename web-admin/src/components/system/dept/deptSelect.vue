<template>
  <el-select
    v-model="departId"
    :placeholder="placeholder"
    clearable
    :size="size"
    filterable
    default-first-option
    :disabled="disabled"
    :loading="loadingSelect"
    @change="handleSelect"
  >
    <el-option
      v-for="item in campusOptions"
      :key="item.id"
      :label="item.label"
      :value="item.id"
    />
  </el-select>
</template>
<script>
import { campusList } from '@/api/system/dept'

export default {
  props: {
    size: {
      type: String,
      default: undefined
    },
    value: {
      type: String,
      default: undefined
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      // 开课校区
      campusOptions: [],
      departId: this.value,
      loadingSelect: false,
      placeholder: '请选择上课校区'
    }
  },
  watch: {
    value: {
      handler(newValue, oldValue) {
        this.departId = newValue
      },
      immediate: true
    }
  },
  created() {
    this.campusList()
  },
  methods: {
    campusList() {
      campusList().then(response => {
        this.campusOptions = response.data
      })
    },
    handleSelect: function(val) {
      this.$emit('input', val)
      this.$emit('change', val)
    }
  }
}
</script>
