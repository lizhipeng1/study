<template>
  <div>
    <el-dialog title="新建班级" :visible.sync="open" width="700px">
      <el-form ref="form" v-loading="loadingChange" :model="form" :rules="rules" label-width="120px" class="add-form">
        <el-row>
          <el-col :span="12">
            <el-form-item label="选择课程:" prop="courseId">
              <el-button v-if="chooseCourseInfo.courseId === undefined" type="primary" plain icon="el-icon-reading" size="small" @click="chooseCourse">选择课程</el-button>
              <el-input v-else v-model="chooseCourseInfo.courseName" disabled readonly>
                <el-button v-if="chooseCourseInfo.courseId !== undefined && canChangeCourse" slot="append" icon="el-icon-reading" @click="chooseCourse">重选</el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="班级名称:" prop="claName">
              <div class="input-with-color-picker-container">
                <el-input v-model="form.claName" placeholder="请输入班级名称" />
                <el-tooltip class="item" effect="dark" content="选择班级颜色,便于课表中区分各班级" placement="right">
                  <el-color-picker v-model="form.claColor" :predefine="colorArray" />
                </el-tooltip>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="任课教师:" prop="staffId">
              <el-select
                v-model="form.staffId"
                filterable
                allow-create
                placeholder="选择上课教师"
                clearable
                default-first-option
              >
                <el-option
                  v-for="teacher in teacherOptions"
                  :key="teacher.staffId"
                  :label="teacher.staffName"
                  :value="teacher.staffId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="满班人数:" prop="capacity">
              <el-input-number v-model="form.capacity" controls-position="right" :min="0" placeholder="请输入满班人数" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="招生状态:" prop="recruitStatus">
              <el-select
                v-model="form.recruitStatus"
                placeholder="请选择招生状态"
                clearable
                filterable
                default-first-option
                :loading="loadingSelect"
              >
                <el-option
                  v-for="dict in recruitStatusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="每次上课:" prop="everyStuLoseHour">
              学生扣除 <el-input-number v-model="form.everyStuLoseHour" class="auto-width" style="width: 100px" controls-position="right" :min="0" /> 课时
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开班日期:" prop="openDate">
              <el-date-picker
                v-model="form.openDate"
                clearable
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择开班日期"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结班日期:" prop="closeDate">
              <el-date-picker
                v-model="form.closeDate"
                clearable
                size="medium"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择结班日期"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="备注" prop="memo">
              <el-input v-model="form.memo" type="textarea" placeholder="请输入备注内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="loadingChange" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open = false">取 消</el-button>
      </div>
    </el-dialog>
    <choose-course ref="chooseCourse" @chooseComplete="chooseCourseComplete" />
  </div>
</template>
<script>
import chooseCourse from '@/components/sc/course/chooseCourse'
import { addCla, updateCla } from '@/api/sc/cla'
import { select as teacherSelect } from '@/api/system/staff'
import moment from 'moment'
export default {
  components: {
    chooseCourse
  },
  props: {
    colorArray: {
      type: Array,
      default: function() {
        return ['#409EFF', '#67C23A', '#E6A23C', '#F56C6C']
      }
    }
  },
  data() {
    return {
      open: false,
      loadingChange: false,
      loadingSelect: false,
      form: {
        openDate: undefined,
        everyStuLoseHour: 1,
        claColor: '#409EFF'
      },
      rules: {
        courseId: [
          { required: true, message: '请选择课程', trigger: 'blur' }
        ],
        claName: [
          { required: true, message: '请输入班级名称', trigger: 'blur' }
        ],
        staffId: [
          { required: true, message: '请选择任课教师', trigger: 'blur' }
        ],
        capacity: [
          { required: true, message: '请填写满班人数', trigger: 'blur' }
        ],
        recruitStatus: [
          { required: true, message: '请选择招生状态', trigger: 'blur' }
        ],
        everyStuLoseHour: [
          { required: true, message: '请输入每次上课学生扣除课时', trigger: 'blur' }
        ],
        openDate: [
          { required: true, message: '请输入开班日期', trigger: 'blur' }
        ]
      },
      recruitStatusOptions: [],
      // 教师
      teacherOptions: [],
      // 已选择的课程信息
      chooseCourseInfo: {},
      // 是否允许变更所属课程
      canChangeCourse: true,
      // 已选择的校区
      chooseDepartId: undefined
    }
  },
  created() {
    this.getTeacherOptions()
    this.getDictListByDictType('recruit_status').then(response => {
      this.recruitStatusOptions = response.data
    })
  },
  methods: {
    getTeacherOptions() {
      teacherSelect().then(response => {
        this.teacherOptions = response.data
      })
    },
    submitForm: function() {
      this.form.courseId = this.chooseCourseInfo.courseId
      this.form.departId = this.chooseDepartId
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.claId !== undefined) {
            updateCla(this.form).then(response => {
              this.loadingChange = false
              if (response.respCode === '0000') {
                this.msgSuccess('修改成功')
                this.open = false
                this.$emit('ok')
              } else {
                this.msgError(response.respMsg)
              }
            }).catch(() => {
              this.loadingChange = false
            })
          } else {
            this.loadingChange = true
            addCla(this.form).then(response => {
              this.loadingChange = false
              if (response.respCode === '0000') {
                this.msgSuccess('新增成功')
                this.open = false
                this.$emit('ok')
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
    // 选择课程
    chooseCourse: function() {
      this.$refs.chooseCourse.open = true
    },
    chooseCourseComplete({ courseInfo, departId }) {
      this.chooseCourseInfo = courseInfo
      this.chooseDepartId = departId
    },
    reset() {
      this.chooseCourseInfo = {}
      this.chooseDepartId = undefined
      this.form = {
        openDate: moment(new Date()).format('YYYY-MM-DD'),
        everyStuLoseHour: 1,
        claColor: '#409EFF'
      }
      this.resetForm('form')
    }
  }
}
</script>
