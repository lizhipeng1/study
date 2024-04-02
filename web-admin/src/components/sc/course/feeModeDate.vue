<template>
  <el-row>
    <el-col :span="12">
      <el-form-item prop="feeModeDate" class="align-left none-margin-bottom margin-top-20">
        <template slot="label">
          <div class="second-title">2. 按时间收费:</div>
        </template>
        <el-switch v-model="feeModeDate" />
      </el-form-item>
    </el-col>
    <el-col v-if="feeModeDate" :span="24">
      <label class="el-form-item__label" style="width: 100px;">定价标准:</label>
      <el-table
        :data="feeModeTableData"
        :span-method="spanMethod"
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
          prop="dateType"
          label="时间段"
          width="160"
        >
          <template slot="header"><span class="header-need-input">时间段</span></template>
          <template slot-scope="scope">
            <el-select
              v-model="feeModeTableData[scope.$index].dateType"
              placeholder="请选择时间段"
              clearable
              size="small"
              filterable
              default-first-option
              :loading="loadingSelect"
            >
              <el-option
                v-for="item in dateTypeOptions"
                :key="item.id"
                :label="item.label"
                :value="item.id"
              />
            </el-select>
          </template>
        </el-table-column>
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
        <el-table-column
          label="操作"
          width="80"
          align="center"
        >
          <template slot-scope="scope">
            <el-button v-if="lastCampus(scope)" style="padding: 3px 5px;" type="primary" icon="el-icon-plus" size="mini" @click="handleAddFeeMode(scope)" />
            <el-button v-if="canDel(scope)" style="padding: 3px 5px;margin-left: 5px;" type="danger" icon="el-icon-minus" size="mini" @click="handleDeleteFeeMode(scope)" />
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
      loadingSelect: false,
      feeModeDate: false,
      feeModeTableData: [{
        campusId: -1,
        campusName: '全部校区',
        cnt: 1,
        dateType: undefined,
        totalFee: 0
      }],
      // key value形式存储 所有校区
      campusOptionsMap: {},
      dateTypeOptions: [{
        id: 'day',
        label: '天'
      }, {
        id: 'month',
        label: '月'
      }, {
        id: 'season',
        label: '季'
      }, {
        id: 'year',
        label: '年'
      }]
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
      if (!this.feeModeDate) {
        this.feeModeTableData = []
        return
      }
      if (newValue === 'all') {
        this.feeModeTableData = [{
          campusId: -1,
          campusName: '全部校区',
          cnt: 1,
          dateType: 'month',
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
              dateType: 'month',
              totalFee: 0
            })
          }
        })
        this.feeModeTableData = campusArray
      }
    },
    // 已选择校区变更
    hadChooseCampus(newValue, oldValue) {
      if (!this.feeModeDate) {
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
          dateType: 'month',
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
    feeModeDate(newValue, oldValue) {
      if (newValue && this.feeModeTableData.length === 0) {
        if (this.courseCampus === 'all') {
          this.feeModeTableData = [{
            campusId: -1,
            campusName: '全部校区',
            cnt: 1,
            dateType: 'month',
            totalFee: 0
          }]
        } else {
          this.hadChooseCampus.forEach((value, index, array) => {
            const campusName = this.campusOptionsMap[value]
            this.feeModeTableData.push({
              campusId: value,
              campusName: campusName,
              cnt: 1,
              dateType: 'month',
              totalFee: 0
            })
          })
        }
      }
    }
  },
  methods: {
    // 新增课时 收费标准
    handleAddFeeMode(item) {
      this.feeModeTableData.splice(item.$index + 1, 0, {
        campusId: item.row.campusId,
        campusName: item.row.campusName,
        cnt: 1,
        dateType: 'month',
        totalFee: 0
      })
    },
    // 删除
    handleDeleteFeeMode(item) {
      const index = item.$index
      this.feeModeTableData.splice(index, 1)
    },
    spanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex !== 0) {
        return
      }
      // 上一个是否一致
      let preEqual = true
      // 下一个是否一致
      let nextEqual = false

      if (rowIndex > 0) {
        preEqual = this.feeModeTableData[rowIndex - 1].campusId === row.campusId
      } else {
        preEqual = false
      }
      if (this.feeModeTableData.length > rowIndex + 1) {
        // 有下一个
        nextEqual = this.feeModeTableData[rowIndex + 1].campusId === row.campusId
      }

      // 上一个不一致 下一个一致
      if (!preEqual && nextEqual) {
        let rowspan = 1
        for (let i = rowIndex + 1; i < this.feeModeTableData.length; i++) {
          if (row.campusId === this.feeModeTableData[i].campusId) {
            rowspan++
          }
        }
        return {
          rowspan: rowspan,
          colspan: 1
        }
      } else if (preEqual) {
        // 如果上一个一致
        return {
          rowspan: 0,
          colspan: 0
        }
      }
    },
    // 是否显示+号 可点击新增校区收费 配置
    lastCampus(item) {
      const index = item.$index
      const campusId = this.feeModeTableData[index].campusId
      // 最后一个
      if (this.feeModeTableData.length === (index + 1)) {
        return true
      }
      // 下一个校区和本校区 不一致 展示 + 号
      return this.feeModeTableData[index + 1].campusId !== campusId
    },
    // 是否可删除
    canDel(item) {
      const index = item.$index
      const campusId = this.feeModeTableData[index].campusId
      // 就一个
      if (this.feeModeTableData.length === 1) {
        return false
      }

      // 上一个与本个相同
      if (this.feeModeTableData.length > 1 && index > 0) {
        if (this.feeModeTableData[index - 1].campusId === campusId) {
          return true
        }
      }
      // 后一个与本个相同
      if (this.feeModeTableData.length > index + 1) {
        return this.feeModeTableData[index + 1].campusId === campusId
      }
    }
  }
}
</script>
