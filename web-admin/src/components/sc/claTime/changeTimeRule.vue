<template>
  <el-dialog :title="title" :visible.sync="open" width="600px">
    <el-form ref="form" v-loading="loadingChange" class="add-form auto-width" :model="form" :rules="rules" label-width="90px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="校区:" prop="deptId">
            <dept-select v-model="form.deptId" :disabled="!canChangeCla" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="班级:" prop="claId">
            <cla-select v-model="form.claId" :dept-id="form.deptId" :disabled="!canChangeCla" mounted-load-all />
          </el-form-item>
        </el-col>
        <el-col :span="24" style="text-align: left">
          <el-form-item label="规则类型:">
            <el-radio-group v-model="form.ruleType" @change="handleRuleTypeChange">
              <el-radio
                v-for="dict in ruleTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictValue"
              >{{ dict.dictLabel }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <div v-if="form.ruleType === '1'">
          <el-col :span="12">
            <el-form-item label="开始日期:" prop="beginDate">
              <el-date-picker
                v-model="form.beginDate"
                clearable
                size="small"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择开始日期"
                :picker-options="beginDatePickerOptions"
                @change="beginDateChange"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束日期:" prop="endDate">
              <el-date-picker
                v-model="form.endDate"
                clearable
                size="small"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择结束日期"
                :picker-options="endDatePickerOptions"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24" style="text-align: left">
            <el-form-item label="重复方式:">
              <el-radio-group v-model="form.repeatType">
                <el-radio
                  v-for="dict in repeatTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{ dict.dictLabel }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col v-if="form.repeatType !== '2'" :span="24" style="text-align: left">
            <el-form-item label="上课星期:">
              <el-checkbox v-model="checkAllWeekDay" :indeterminate="isIndeterminate" @change="handleCheckAllChange">全选</el-checkbox>
              <el-checkbox-group v-model="form.weekDays" @change="handleCheckedChange">
                <el-checkbox v-for="dict in weekDayOptions" :key="dict.dictValue" :label="dict.dictValue" name="weekDays">{{ dict.dictLabel }}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
          <el-col :span="24" style="text-align: left">
            <el-form-item label="节假日:">
              <el-radio-group v-model="form.filterHoliday">
                <el-radio
                  v-for="dict in filterHolidayOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{ dict.dictLabel }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </div>
        <div v-if="form.ruleType !== '1'">
          <el-col :span="24" style="text-align: left">
            <el-form-item label="上课日期:" prop="chooseDate">
              <el-date-picker
                v-model="form.chooseDate"
                clearable
                size="small"
                type="dates"
                value-format="yyyy-MM-dd"
                placeholder="选择开始日期"
              />
            </el-form-item>
          </el-col>
        </div>
        <el-col :span="12">
          <el-form-item label="上课时间:" prop="startTime">
            <el-time-select
              v-model="form.startTime"
              size="small"
              :picker-options="{
                start: '08:00',
                step: '00:30',
                end: '19:00'
              }"
              placeholder="选择上课时间"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="下课时间:" prop="endTime">
            <el-time-select
              v-model="form.endTime"
              size="small"
              :picker-options="{
                start: '08:00',
                step: '00:30',
                end: '20:00'
              }"
              placeholder="选择下课时间"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="任课教师:" prop="teacherId">
            <staff-select v-model="form.teacherId" teacher="1" placeholder="请选择任课教师" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="教室:" prop="roomId">
            <room-select v-model="form.roomId" :dept-id="form.deptId" placeholder="请选择教师" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="上课主题:" prop="classTheme">
            <el-input v-model="form.classTheme" size="small" placeholder="请输入上课主题" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button :loading="loadingChange" type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import claSelect from '@/components/sc/course/cla/claSelect'
import deptSelect from '@/components/system/dept/deptSelect'
import staffSelect from '@/components/system/staff/staffSelect'
import roomSelect from '@/components/sc/base/roomSelect'
import moment from 'moment'
import { addRule, getRule, updateRule } from '@/api/sc/cla/claTimeRule'
let that
export default {
  components: {
    claSelect,
    deptSelect,
    staffSelect,
    roomSelect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      loadingChange: false,
      title: '排课',
      // 是否显示弹出层
      open: false,
      // 规则类型数据字典
      ruleTypeOptions: [],
      // 重复方式数据字典
      repeatTypeOptions: [],
      // 上课星期数据字典
      weekDayOptions: [],
      // 是否过滤节假日数据字典
      filterHolidayOptions: [{
        dictLabel: '过滤',
        dictValue: true
      }, {
        dictLabel: '不过滤',
        dictValue: false
      }],
      // 表单参数
      form: {
      },
      isIndeterminate: true,
      checkAllWeekDay: false,
      beginDatePickerOptions: {
        disabledDate(time) {
          return moment(time).add(1, 'days').valueOf() < Date.now()
        }
      },
      endDatePickerOptions: {
        disabledDate(time) {
          return time.valueOf() < moment(that.form.beginDate).valueOf()
        }
      },
      canChangeCla: true
    }
  },
  computed: {
    rules() {
      const tempRules = {
        deptId: [
          { required: true, message: '请选择校区', trigger: 'blur' }
        ],
        claId: [
          { required: true, message: '班级不能为空', trigger: 'blur' }
        ],
        ruleType: [
          { required: true, message: '规则类型不能为空', trigger: 'blur' }
        ],
        beginDate: [
        ],
        endDate: [
        ],
        chooseDate: [
        ],
        repeatType: [
          { required: true, message: '重复方式不能为空', trigger: 'blur' }
        ],
        weekDays: [
          { required: true, message: '上课星期不能为空', trigger: 'blur' }
        ],
        filterHoliday: [
          { required: true, message: '是否过滤节假日', trigger: 'blur' }
        ],
        startTime: [
          { required: true, message: '上课时间不能为空', trigger: 'blur' }
        ],
        endTime: [
          { required: true, message: '下课时间不能为空', trigger: 'blur' }
        ],
        teacherId: [
          { required: true, message: '任课教师不能为空', trigger: 'blur' }
        ]
      }
      if (this.form.ruleType === '1') {
        tempRules['beginDate'] = [
          { required: true, message: '开始日期不能为空', trigger: 'blur' }
        ]
        tempRules['endDate'] = [
          { required: true, message: '结束日期不能为空', trigger: 'blur' }
        ]
      } else if (this.form.ruleType === '2') {
        tempRules['chooseDate'] = [
          { required: true, message: '请选择上课日期', trigger: 'blur' }
        ]
      }
      return tempRules
    }
  },
  watch: {
    'form.ruleType': {
      handler(newValue, oldValue) {
        if (newValue === '1') {
          // 重复
          this.form.repeatType = '1'
          this.form.filterHoliday = true
        } else {
          // 单次
          this.form.repeatType = undefined
          this.form.beginDate = undefined
          this.form.endDate = undefined
          this.form.filterHoliday = false
        }
      }
    },
    'form.repeatType': {
      handler(newValue, oldValue) {
        if (newValue === '2') {
          // 隔日重复
          this.form.weekDays = []
        }
      }
    }
  },
  created() {
    this.getDictListByDictType('cla_time_rule_type').then(response => {
      this.ruleTypeOptions = response.data
    })
    this.getDictListByDictType('cla_time_repeat_type').then(response => {
      this.repeatTypeOptions = response.data
    })
    this.getDictListByDictType('week_day').then(response => {
      this.weekDayOptions = response.data
    })
    that = this
  },
  methods: {
    // 重复方式
    repeatTypeFormat(row, column) {
      return this.selectDictLabel(this.repeatTypeOptions, row.repeatType)
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        deptId: undefined,
        claId: undefined,
        ruleType: '1',
        beginDate: undefined,
        endDate: undefined,
        repeatType: '1',
        weekDays: [],
        filterHoliday: true,
        startTime: '',
        endTime: '',
        teacherId: undefined,
        classTheme: undefined,
        chooseDate: undefined
      }
      this.resetForm('form')
      this.canChangeCla = true
    },
    loadInfo(id) {
      this.loadingChange = true
      getRule(id).then(response => {
        this.loadingChange = false
        const date = response.data
        date.weekDays = date.weekDay.split(',')
        if (date.startTime !== undefined && date.startTime.length > 5) {
          date.startTime = date.startTime.substr(0, 5)
        }
        if (date.endTime !== undefined && date.endTime.length > 5) {
          date.endTime = date.endTime.substr(0, 5)
        }
        if (date.onceDate !== undefined) {
          date.chooseDate = date.onceDate.split(',')
        }
        this.form = date
        this.$refs['form'].resetFields()
        this.open = true
      }).catch(() => {
        this.loadingChange = false
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.loadingChange = true
          this.form.weekDay = this.form.weekDays.toString()
          if (this.form.chooseDate !== undefined) {
            this.form.onceDate = this.form.chooseDate.toString()
          }
          if (this.form.ruleId !== undefined) {
            updateRule(this.form).then(response => {
              this.loadingChange = false
              if (response.respCode === '0000') {
                this.msgSuccess('修改成功')
                this.open = false
                this.$emit('success')
              } else {
                this.msgError(response.respMsg)
              }
            }).catch(() => {
              this.loadingChange = false
            })
          } else {
            addRule(this.form).then(response => {
              this.loadingChange = false
              if (response.respCode === '0000') {
                this.msgSuccess('新增成功')
                this.open = false
                this.$emit('success')
              } else {
                this.msgError(response.respMsg)
              }
            }).catch(() => {
              this.loadingChange = false
            })
          }
        }
      })
    },
    beginDateChange(value) {
      if (moment(value).valueOf() > moment(this.form.endDate).valueOf()) {
        this.form.endDate = undefined
      }
    },
    handleCheckAllChange(val) {
      this.form.weekDays = val ? this.weekDayOptions.map(item => {
        return item.dictValue
      }) : []
      this.isIndeterminate = false
    },
    handleCheckedChange(value) {
      const checkedCount = value.length
      this.checkAllWeekDay = checkedCount === this.form.weekDays.length
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.weekDayOptions.length
    },
    // 规则类型变更
    handleRuleTypeChange(ruleType) {
      if (ruleType === '2') {
        // 单次排课
      } else if (ruleType === '1') {
        // 重复排课
      }
    }
  }
}
</script>
