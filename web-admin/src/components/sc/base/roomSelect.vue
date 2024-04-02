<template>
  <el-select
    v-model="roomId"
    :placeholder="placeholder"
    clearable
    size="small"
    filterable
    default-first-option
    :loading="loadingSelect"
    @change="handleSelect"
  >
    <el-option
      v-for="item in roomOptions"
      :key="item.roomId"
      :label="item.roomName"
      :value="item.roomId"
    />
  </el-select>
</template>
<script>
import { select as roomSelect } from '@/api/sc/room'

export default {
  props: {
    value: {
      type: String,
      default: undefined
    },
    deptId: {
      type: String,
      default: undefined
    }
  },
  data() {
    return {
      // 教室
      roomOptions: [],
      roomId: this.value,
      loadingSelect: false,
      placeholder: '请选择教室'
    }
  },
  watch: {
    value: {
      handler(newValue, oldValue) {
        this.roomId = newValue
      },
      immediate: true
    },
    deptId: {
      handler(newValue, oldValue) {
        if (newValue === oldValue && newValue === undefined) {
          return
        }
        this.roomList()
      },
      immediate: true
    }
  },
  created() {
    this.roomList()
  },
  methods: {
    roomList() {
      roomSelect({
        deptId: this.deptId
      }).then(response => {
        this.roomOptions = response.data
      })
    },
    handleSelect: function(val) {
      this.$emit('input', val)
    }
  }
}
</script>
