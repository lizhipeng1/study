<template>
  <el-dialog :title="title" :visible.sync="open" width="600px">
    <el-form ref="form" v-loading="loadingChange" class="add-form auto-width" :model="form" :rules="rules" label-width="90px">
      <el-row v-if="form.courseTimeId !== undefined && form.courseTimeId !== ''">
        <el-col :span="12">
          <el-form-item label="班级:">
            <el-input :value="oldTimeInfo.claName" readonly disabled size="small" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="原日期:">
            <el-input :value="oldTimeInfo.claDate" readonly disabled size="small" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="原时间:">
            <el-input :value="oldTimeInfo.startTime + '~' + oldTimeInfo.endTime" readonly disabled size="small" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="原教师:">
            <el-input :value="oldTimeInfo.staffName" readonly disabled size="small" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="原教室:">
            <el-input :value="oldTimeInfo.roomName" readonly disabled size="small" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col v-if="form.courseTimeId === undefined || form.courseTimeId === ''" :span="12">
          <el-form-item label="校区:" prop="deptId">
            <dept-select v-model="form.deptId" :disabled="!canChangeCla" />
          </el-form-item>
        </el-col>
        <el-col v-if="form.courseTimeId === undefined || form.courseTimeId === ''" :span="12">
          <el-form-item label="班级:" prop="claId">
            <cla-select v-model="form.claId" :dept-id="form.deptId" :disabled="!canChangeCla" mounted-load-all />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="上课日期:" prop="claDate">
            <el-date-picker
              v-model="form.claDate"
              clearable
              :disabled="afterNow(form.claDate)"
              size="small"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择上课日期"
              :picker-options="beginDatePickerOptions"
            />
          </el-form-item>
        </el-col>
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
import staffSelect from '@/components/system/staff/staffSelect'
import roomSelect from '@/components/sc/base/roomSelect'
import claSelect from '@/components/sc/course/cla/claSelect'
import deptSelect from '@/components/system/dept/deptSelect'
import moment from 'moment'
import { addTime, claTimeInfo, updateTime } from '@/api/sc/cla/claTime'
import { getCla } from '@/api/sc/cla'

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
      loadingChange: false,
      title: '调课',
      // 是否显示弹出层
      open: false,
      // 原排课信息
      oldTimeInfo: [],
      // 表单参数
      form: {
      },
      beginDatePickerOptions: {
        disabledDate(time) {
          return moment(time).add(1, 'days').valueOf() < Date.now()
        }
      },
      // 是否可重新选择课程
      canChangeCla: true
    }
  },
  computed: {
    rules() {
      return {
        claDate: [
          { required: true, message: '开始日期不能为空', trigger: 'blur' }
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
    }
  },
  watch: {
  },
  created() {
  },
  methods: {
    afterNow(date) {
      return moment(date).add(1, 'days').toDate().getTime() < new Date().getTime()
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        courseTimeId: undefined,
        deptId: undefined,
        claId: undefined,
        beginDate: undefined,
        startTime: '',
        endTime: '',
        teacherId: undefined,
        roomId: undefined,
        classTheme: ''
      }
      this.resetForm('form')
    },
    // 设置默认选择班级
    setDefaultClaInfo(claId) {
      if (claId) {
        this.loadingChange = true
        getCla(claId).then(response => {
          this.loadingChange = false
          const claInfo = response.data.claInfo
          this.form.deptId = claInfo.departId
          this.form.claId = claInfo.claId
          this.canChangeCla = false
        }).catch(() => {
          this.loadingChange = false
        })
      } else {
        this.canChangeCla = true
      }
    },
    // 调课
    openUpdateByOldInfo(oldTimeInfo, id) {
      this.oldTimeInfo = oldTimeInfo
      this.loadingChange = true
      claTimeInfo(id).then(response => {
        this.loadingChange = false
        const date = response.data
        date.startTime = date.startTime.substr(0, 5)
        date.endTime = date.endTime.substr(0, 5)
        this.form = date
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
          if (this.form.courseTimeId !== undefined) {
            updateTime(this.form).then(response => {
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
            addTime(this.form).then(response => {
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
    }
  }
}
</script>
