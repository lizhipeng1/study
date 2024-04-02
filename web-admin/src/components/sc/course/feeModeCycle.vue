<template>
  <el-row>
    <el-col :span="12">
      <el-form-item prop="feeModeCycle" class="align-left none-margin-bottom margin-top-20">
        <template slot="label">
          <div class="second-title">3. 按期收费:</div>
        </template>
        <el-switch v-model="feeModeCycle" />
        <el-tooltip class="item" effect="dark" content="适合按期课程,例如暑假班、寒假班" placement="right">
          <svg-icon icon-class="question" style="height: 22px;width: 22px;top: 8px;position: absolute;left: 48px;" />
        </el-tooltip>
      </el-form-item>
    </el-col>
    <el-col v-if="feeModeCycle" :span="24">
      <label class="el-form-item__label" style="width: 100px;">定价标准:</label>
      <el-table
        :data="feeModeTableData"
        border
        class="fee-table"
      >
        <el-table-column
          prop="campusName"
          label="开课校区"
        />
        <el-table-column
          prop="cnt"
          label="数量"
        />
        <el-table-column
          prop="totalFee"
          label="总价(元)"
          width="160"
        >
          <template slot="header"><span class="header-need-input">总价(元)</span></template>
          <template slot-scope="scope">
            <el-input-number v-model="feeModeTableData[scope.$index].totalFee" size="small" controls-position="right" :min="0" label="请输入总金额" />
          </template>
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>
</template>
<script>
export default {
  props: {
    // 全部校区
    campusOptions: {
      type: Array,
      default: function() {
        return []
      }
    },
    // 已选择校区
    hadChooseCampus: {
      type: Array,
      default: function() {
        return []
      }
    },
    // 全部校区/部分校区
    courseCampus: {
      type: String,
      default: 'all'
    }
  },
  data() {
    return {
      feeModeCycle: false,
      feeModeTableData: [{
        campusId: -1,
        campusName: '全部校区',
        cnt: 1,
        totalFee: 10
      }],
      // key value形式存储 所有校区
      campusOptionsMap: {}
    }
  },
  watch: {
    // key value形式存储 所有校区
    campusOptions(newValue, oldValue) {
      newValue.forEach((value, index, array) => {
        this.campusOptionsMap[value.id] = value.label
      })
    },
    // 全部校区、部分校区 变更时
    courseCampus(newValue, oldValue) {
      if (!this.feeModeCycle) {
        this.feeModeTableData = []
        return
      }
      if (newValue === 'all') {
        this.feeModeTableData = [{
          campusId: -1,
          campusName: '全部校区',
          cnt: 1,
          totalFee: 0
        }]
      } else if (newValue === 'part') {
        const campusArray = []
        this.campusOptions.forEach((value, index, array) => {
          const id = value.id
          const label = value.label
          if (this.hadChooseCampus.indexOf(id) !== -1) {
            campusArray.push({
              campusId: id,
              campusName: label,
              cnt: 1,
              totalFee: 0
            })
          }
        })
        this.feeModeTableData = campusArray
      }
    },
    // 已选择校区变更
    hadChooseCampus(newValue, oldValue) {
      if (!this.feeModeCycle) {
        this.feeModeTableData = []
        return
      }
      const addCampusArray = []
      const subCampusArray = []
      // 计算需要新增、删除的校区
      if (newValue.length > oldValue.length) {
        // 新增校区
        newValue.forEach((value, index, array) => {
          if (oldValue.indexOf(value) === -1) {
            addCampusArray.push(value)
          }
        })
      } else {
        // 删除校区
        oldValue.forEach((value, index, array) => {
          if (newValue.indexOf(value) === -1) {
            subCampusArray.push(value)
          }
        })
      }
      // 新增校区
      addCampusArray.forEach((value, index, array) => {
        const campusName = this.campusOptionsMap[value]
        this.feeModeTableData.push({
          campusId: value,
          campusName: campusName,
          cnt: 1,
          totalFee: 0
        })
      })
      // 删除校区
      const tempArray = this.feeModeTableData
      subCampusArray.forEach((value, index, array) => {
        for (let i = tempArray.length - 1; i >= 0; i--) {
          if (tempArray[i].campusId + '' === value) {
            this.feeModeTableData.splice(i, 1)
          }
        }
      })
    },
    // 是否配置本收费模式
    feeModeCycle(newValue, oldValue) {
      if (newValue && this.feeModeTableData.length === 0) {
        if (this.courseCampus === 'all') {
          this.feeModeTableData = [{
            campusId: -1,
            campusName: '全部校区',
            cnt: 1,
            totalFee: 0
          }]
        } else {
          this.hadChooseCampus.forEach((value, index, array) => {
            const campusName = this.campusOptionsMap[value]
            this.feeModeTableData.push({
              campusId: value,
              campusName: campusName,
              cnt: 1,
              totalFee: 0
            })
          })
        }
      }
    }
  },
  methods: {
  }
}
</script>
