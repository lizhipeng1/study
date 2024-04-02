<template>
  <el-dialog :title="title" :visible.sync="open" width="700px">
    <el-form ref="form" v-loading="loadingChange" :model="form" :rules="rules" label-width="100px">
      <div class="title top">
        <div class="title-content">租户基本信息</div>
      </div>
      <el-row>
        <el-col :span="12">
          <el-form-item label="租户名称:" prop="tenantName">
            <el-input v-model="form.tenantName" placeholder="请输入租户名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联系人:" prop="contactName">
            <el-input v-model="form.contactName" placeholder="请输入联系人" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联系电话:" prop="contactPhone">
            <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="地址:" prop="contactAddress">
            <el-input v-model="form.contactAddress" placeholder="请输入租户地址" />
          </el-form-item>
        </el-col>
        <el-col :span="24" style="text-align: left;">
          <el-form-item label="状态:" prop="inUse">
            <el-radio-group v-model="form.inUse">
              <el-radio
                v-for="dict in inUseOptions"
                :key="dict.dictValue"
                :label="dict.dictValue"
              >{{ dict.dictLabel }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="24" style="text-align: left;">
          <el-form-item label="生失效时间:" prop="useDateRange">
            <el-date-picker
              v-model="form.useDateRange"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="生效日期(00:00:00)"
              end-placeholder="结束日期(23:59:59)"
              :picker-options="useDateRangeOptions"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="备注:" prop="memo">
            <el-input v-model="form.memo" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-col>
      </el-row>
      <div v-if="form.tenantId === undefined" class="title top">
        <div class="title-content">管理账号</div>
      </div>
      <el-row v-if="form.tenantId === undefined">
        <el-col :span="12">
          <el-form-item label="邮箱:" prop="emailAddress">
            <el-input v-model="form.emailAddress" placeholder="请输入邮箱" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="性别:" prop="sex">
            <el-select v-model="form.sex" placeholder="请选择性别">
              <el-option
                v-for="dict in sexOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="用户名:" prop="username">
            <el-input v-model="form.username" placeholder="请输入用户名" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="密码:" prop="password">
            <el-input v-model="form.password" placeholder="请输入密码" show-password />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="确认密码:" prop="checkPass" show-password>
            <el-input v-model="form.checkPass" placeholder="请输入密码" show-password />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" :loading="loadingChange" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { addTenant, updateTenant } from '@/api/system/tenant'
import { isPass, isSerialNumber, isUsername } from '@/utils/validate'

export default {
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === undefined || value === '') {
        callback(new Error('请输入密码'))
      } else if (!isPass(value)) {
        callback(new Error('最少6位,包含大小写字母和特殊字符'))
      } else {
        if (this.form.checkPass !== '' && this.form.checkPass !== undefined) {
          this.$refs.form.validateField('checkPass')
        }
        callback()
      }
    }
    const validateCheckPass = (rule, value, callback) => {
      if (value === undefined || value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    const validateNumber = (rule, value, callback) => {
      if (!isSerialNumber(value)) {
        callback(new Error('请输入正确的手机号码'))
      } else {
        callback()
      }
    }
    const validateUsername = (rule, value, callback) => {
      if (!isUsername(value)) {
        callback(new Error('4到16位(字母，数字，下划线)'))
      } else {
        callback()
      }
    }
    return {
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      loadingChange: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        tenantName: [
          { required: true, message: '租户名称不能为空', trigger: 'blur' }
        ],
        contactName: [
          { required: true, message: '联系人不能为空', trigger: 'blur' }
        ],
        contactPhone: [
          { required: true, message: '联系电话不能为空', trigger: 'blur' },
          { validator: validateNumber, trigger: 'blur' }
        ],
        useDateRange: [
          { required: true, message: '生失效时间不能为空', trigger: 'blur' }
        ],
        emailAddress: [
          { required: true, message: '邮箱不能为空', trigger: 'blur' }
        ],
        sex: [
          { required: true, message: '性别不能为空', trigger: 'blur' }
        ],
        username: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { validator: validateUsername, trigger: 'blur' }
        ],
        password: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validateCheckPass, trigger: 'blur' }
        ]
      },
      useDateRangeOptions: {
        shortcuts: [{
          text: '1年',
          onClick(picker) {
            const start = new Date()
            const end = new Date()
            end.setFullYear(end.getFullYear() + 1)
            end.setDate(end.getDate() - 1)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '2年',
          onClick(picker) {
            const start = new Date()
            const end = new Date()
            end.setFullYear(end.getFullYear() + 2)
            end.setDate(end.getDate() - 1)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '3年',
          onClick(picker) {
            const start = new Date()
            const end = new Date()
            end.setFullYear(end.getFullYear() + 3)
            end.setDate(end.getDate() - 1)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '5年',
          onClick(picker) {
            const start = new Date()
            const end = new Date()
            end.setFullYear(end.getFullYear() + 5)
            end.setDate(end.getDate() - 1)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      // 状态数据租户
      inUseOptions: [],
      sexOptions: []
    }
  },
  created() {
    this.getDictListByDictType('in_use').then(response => {
      this.inUseOptions = response.data
    })
    this.getDictListByDictType('sex').then(response => {
      this.sexOptions = response.data
    })
  },
  methods: {
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.tenantId !== undefined) {
            this.loadingChange = true
            const formData = this.addDateRange(this.form, this.form.useDateRange)
            updateTenant(formData).then(response => {
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
            const formData = this.addDateRange(this.form, this.form.useDateRange)
            addTenant(formData).then(response => {
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
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        tenantName: undefined,
        contactName: undefined,
        inUse: '1'
      }
      this.resetForm('form')
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .title {
    padding: 15px 0px;
    color: rgba(0,0,0,.85);
    font-weight: 500;
    font-size: 16px;
    &.top {
      padding-top: 0px;
    }
    .title-content{
      border-left: 3px solid #409EFF;
      padding-left: 10px;
    }
  }
</style>
