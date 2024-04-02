<!--新增学生-->
<template>
  <div>
    <el-dialog title="新增学生" :visible.sync="open" width="600px">
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="学生姓名:" prop="studentName">
              <el-input v-model="form.studentName" placeholder="请输入学生姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学校:" prop="schoolName">
              <el-select
                v-model="form.schoolName"
                placeholder="请选择/输入所属学校"
                clearable
                filterable
                default-first-option
                allow-create
                remote
                :remote-method="schoolSelect"
                :loading="loadingSelect"
              >
                <el-option
                  v-for="item in schoolOptions"
                  :key="item.schoolId"
                  :label="item.schoolName"
                  :value="item.schoolName"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="出生日期:" prop="birthDay">
              <el-date-picker
                v-model="form.birthDay"
                clearable
                style="width: 200px"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择出生日期"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别:">
              <el-radio-group v-model="form.sex" placeholder="请选择性别">
                <el-radio
                  v-for="dict in sexOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                  :value="dict.dictValue"
                >{{ dict.dictLabel }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入校时间:" prop="inTime">
              <el-date-picker
                v-model="form.inTime"
                clearable
                style="width: 200px"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择入校时间"
              />
            </el-form-item>
          </el-col>
          <el-col v-for="(item, index) in contactArray" :key="index" :span="24" style="text-align: left;">
            <el-form-item label="联系人:">
              <el-select
                v-model="contactArray[index].contactRelation"
                filterable
                placeholder="关系"
                default-first-option
                style="width: 80px"
              >
                <el-option
                  v-for="dict in contactRelationOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
              <el-input v-model="contactArray[index].contactNick" style="width: 100px;" placeholder="称呼" />
              <el-input v-model="contactArray[index].contactPhone" style="width: 150px;" placeholder="联系电话" />
              <el-button v-if="index === 0 && contactArray.length > 0" style="padding: 3px 5px;margin-left: 5px;" type="primary" icon="el-icon-plus" size="mini" @click="handleAddContactInfo()" />
              <el-button v-if="index > 0" style="padding: 3px 5px;margin-left: 5px;" type="danger" icon="el-icon-minus" size="mini" @click="handleDeleteContactInfo(index)" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addStudent, getStudent, updateStudent } from '@/api/sc/student'
import { listSelect as listSchoolSelect } from '@/api/sc/school'
import moment from 'moment'

export default {
  name: 'AddStudent',
  data() {
    return {
      open: false,
      // 表单参数
      form: {
        sex: 'M'
      },
      // 所属学校数据字典
      schoolOptions: [],
      // 性别数据字典
      sexOptions: [],
      rules: {
        studentName: [
          { required: true, message: '学生姓名不能为空', trigger: 'blur' }
        ],
        birthDay: [
          { required: true, message: '出生日期不能为空', trigger: 'blur' }
        ],
        sex: [
          { required: true, message: '性别不能为空', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '联系电话不能为空', trigger: 'blur' }
        ]
      },
      loadingSelect: false,
      // 联系人
      contactArray: [{
        contactRelation: 'daddy',
        contactNick: '',
        contactPhone: ''
      }],
      contactRelationOptions: []
    }
  },
  created() {
  },
  methods: {
    loadDictInfo() {
      this.getDictListByDictType('sex').then(response => {
        this.sexOptions = response.data
      })
      this.getDictListByDictType('contact_relation').then(response => {
        this.contactRelationOptions = response.data
      })
      this.schoolSelect('')
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        schoolId: undefined,
        schoolName: undefined,
        studentName: undefined,
        birthDay: undefined,
        sex: 'M',
        phone: undefined,
        inTime: moment(new Date()).format('YYYY-MM-DD')
      }
      this.resetForm('form')
      this.contactArray = [{
        contactRelation: 'daddy',
        contactNick: '',
        contactPhone: ''
      }]
    },
    handleAdd() {
      this.loadDictInfo()
      this.reset()
      this.open = true
      this.title = '添加学生基本信息'
    },
    handleUpdate(studentId) {
      this.loadDictInfo()
      this.reset()
      getStudent(studentId).then(response => {
        this.form = response.data
        if (response.data.contactList && response.data.contactList.length > 0) {
          this.contactArray = response.data.contactList
        }
        this.open = true
        this.title = '修改学生基本信息'
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.contactArray.forEach(item => {
            if (item.contactRelation === undefined || item.contactRelation === null || item.contactRelation.trim() === '') {
              this.msgError('请选择联系人与学生关系')
              valid = false
            } else if (item.contactPhone === undefined || item.contactPhone === null || item.contactPhone.trim() === '') {
              this.msgError('请填写联系人电话')
              valid = false
            }
          })
          if (!valid) {
            return
          }
          this.form.contactList = this.contactArray
          if (this.form.studentId !== undefined) {
            updateStudent(this.form).then(response => {
              if (response.respCode === '0000') {
                this.msgSuccess('修改成功')
                this.open = false
                this.$emit('success')
              } else {
                this.msgError(response.respMsg)
              }
            })
          } else {
            addStudent(this.form).then(response => {
              if (response.respCode === '0000') {
                this.msgSuccess('操作成功')
                this.open = false
                this.$emit('success')
              } else {
                this.msgError(response.respMsg)
              }
            })
          }
        }
      })
    },
    schoolSelect(query) {
      listSchoolSelect({ search: query.trim(), maxRecord: 1000 }).then(response => {
        if (response.respCode === '0000') {
          this.schoolOptions = response.data
        } else {
          this.schoolOptions = []
        }
      })
    },
    handleAddContactInfo() {
      this.$set(this.contactArray, this.contactArray.length, {
        contactRelation: 'daddy',
        contactNick: '',
        contactPhone: ''
      })
    },
    // 删除
    handleDeleteContactInfo(index) {
      this.contactArray.splice(index, 1)
    }
  }
}
</script>
