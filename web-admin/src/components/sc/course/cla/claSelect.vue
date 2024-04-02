<template>
  <el-select
    v-model="claId"
    v-select-load-more="loadCla"
    filterable
    :clearable="clearable"
    :disabled="disabled"
    :placeholder="placeholder"
    default-first-option
    style="width: 180px"
    size="small"
    @change="handleSelect"
  >
    <el-option
      v-for="item in claList"
      :key="item.claId"
      :label="item.claName"
      :value="item.claId"
    >
      <div>
        <div class="inline-block claName">
          <span class="title">班名:</span>
          <span class="option">{{ item.claName }}({{ item.deptName }})</span>
        </div>
        <div class="inline-block staffName">
          <span class="title">教师:</span>
          <span class="option">{{ item.staffName }}</span>
        </div>
        <div class="inline-block recruitStatus">
          <span class="title">状态:</span>
          <span class="option">{{ recruitStatusFormatter(item.recruitStatus) }}({{ item.capacity }})</span>
        </div>
        <div class="inline-block openDate">
          <span class="title">开班时间:</span>
          <span class="option">{{ item.openDate }}</span>
        </div>
      </div>
    </el-option>
  </el-select>
</template>
<script>
import { listCla } from '@/api/sc/cla'
export default {
  props: {
    clearable: {
      type: Boolean,
      default: false
    },
    courseId: {
      type: String,
      default: undefined
    },
    deptId: {
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
    },
    // 初始化加载全部
    mountedLoadAll: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      placeholder: '请选择班级',
      claId: this.value,
      claList: [],
      pageNum: 1,
      hasMoreData: false,
      recruitStatusOptions: [{
        id: '1',
        label: '开放'
      }, {
        id: '2',
        label: '满班后停止'
      }, {
        id: '0',
        label: '停止'
      }]
    }
  },
  computed: {
    queryParam() {
      return {
        courseId: this.courseId,
        deptId: this.deptId
      }
    }
  },
  watch: {
    queryParam: {
      handler(newValue, oldValue) {
        this.pageNum = 1
        this.loadCla()
      },
      deep: true
    },
    value: {
      handler(newValue, oldValue) {
        this.claId = newValue
      },
      immediate: true
    },
    claId: {
      handler(newValue, oldValue) {
        this.$emit('input', newValue)
      },
      immediate: true
    }
  },
  mounted() {
    if (this.mountedLoadAll && this.claList.length === 0) {
      this.pageNum = 1
      this.loadCla()
    }
  },
  methods: {
    loadCla: function() {
      if (this.pageNum === 1) {
        this.claList = []
        this.claId = this.value

        listCla({
          courseId: this.courseId,
          departId: this.deptId,
          pageNum: this.pageNum
        }).then(response => {
          if (response.respCode === '0000') {
            this.claList = response.data.rows
            this.hasMoreData = response.data.rows.length > 0
            this.pageNum = this.pageNum + 1
          } else {
            this.msgError(response.respMsg)
          }
        })
      } else if (this.hasMoreData) {
        listCla({
          courseId: this.courseId,
          departId: this.deptId,
          pageNum: this.pageNum
        }).then(response => {
          if (response.respCode === '0000') {
            this.claList = this.claList.concat(response.data.rows)
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
    },
    // 招生状态中文
    recruitStatusFormatter: function(recruitStatus) {
      let recruitStatusTxt = ''
      this.recruitStatusOptions.forEach(item => {
        if (item.id === recruitStatus) {
          recruitStatusTxt = item.label
          return
        }
      })
      return recruitStatusTxt
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
