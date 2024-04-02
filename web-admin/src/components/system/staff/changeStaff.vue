<!-- 添加员工 -->
<template>
  <el-dialog :title="title" :visible.sync="open" width="700px">
    <el-form ref="form" v-loading="loadingChange" :model="form" :rules="rules" label-width="120px" class="add-form">
      <div class="title top">
        <div class="title-content">员工基本信息</div>
      </div>
      <el-row>
        <el-col :span="12">
          <el-form-item label="员工姓名:" prop="staffName">
            <el-input v-model="form.staffName" placeholder="请输入员工姓名" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联系电话:" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入联系电话" />
          </el-form-item>
        </el-col>
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
          <el-form-item label="入职日期:" prop="entryDate">
            <el-date-picker
              v-model="form.entryDate"
              clearable
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择入职日期"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="所属部门:" prop="deptId">
            <treeselect v-model="form.deptId" :options="deptOptions" placeholder="请选择归属部门" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="人事状态:" prop="personnelStatus">
            <el-select
              v-model="form.personnelStatus"
              filterable
              allow-create
              placeholder="选择人事状态:"
              clearable
              default-first-option
            >
              <el-option
                v-for="item in personnelStatusOptions"
                :key="item.dictValue"
                :label="item.dictLabel"
                :value="item.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="任课教师:" class="align-left">
            <el-switch v-model="form.teacher" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="系统账号:" class="align-left">
            <el-switch v-model="form.loginUser" :disabled="form.userId !== undefined" />
          </el-form-item>
        </el-col>
      </el-row>
      <div class="title top">
        <div class="title-content">登录账号</div>
      </div>
      <el-row v-if="form.loginUser">
        <el-col :span="24">
          <el-form-item label="允许登录系统" prop="locked" class="align-left">
            <el-radio-group v-model="form.locked">
              <el-radio label="0">允许</el-radio>
              <el-radio label="1">不允许</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" :readonly="form.userId !== undefined" :disabled="form.userId !== undefined" placeholder="请输入用户名" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="密码" prop="password">
            <el-input v-if="form.userId === undefined" v-model="form.password" placeholder="请输入密码" show-password />
            <el-button v-else icon="el-icon-refresh" @click="handleResetPwd">重置密码</el-button>
          </el-form-item>
        </el-col>
        <el-col v-if="form.userId === undefined" :span="12">
          <el-form-item label="确认密码" prop="checkPass" show-password>
            <el-input v-model="form.checkPass" placeholder="请输入密码" show-password />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="角色">
            <el-tree
              ref="role"
              :data="roleOptions"
              show-checkbox
              node-key="id"
              default-expand-all
              :default-checked-keys="checkRoleIds"
              empty-text="加载中，请稍后"
              :props="defaultProps"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="所属校区:" prop="belongCampus" class="align-left">
            <el-radio-group v-model="form.belongCampus">
              <el-radio
                v-for="item in belongCampus"
                :key="item.id"
                :label="item.id"
                :value="item.id"
              >{{ item.label }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col v-if="form.belongCampus === 'part'" :span="24">
          <el-form-item label="选择校区:" prop="partCampus" class="align-left">
            <el-checkbox-group v-model="form.partCampus">
              <el-checkbox v-for="(item) in campusOptions" :key="item.id" :label="item.id" name="partCampus">{{ item.label }}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-else>
        <el-col :span="24">
          暂未创建登录系统的账号,如需创建,请打开上方'系统账号'进行创建。
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
import { addStaff, updateStaff } from '@/api/system/staff'
import { checkUsernameUnique, resetUserPwd } from '@/api/system/user'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { campusList, campusSelect, treeSelect } from '@/api/system/dept'
import { treeSelectLimitUserHasRole as roleTreeSelect } from '@/api/system/role/index'
import { isUsername, isPass, isSerialNumber } from '@/utils/validate'
export default {
  components: { Treeselect },
  data() {
    const validateUsernameUnique = (rule, value, callback) => {
      if (this.form.loginUser && this.form.userId === undefined) {
        checkUsernameUnique(value).then(response => {
          const unique = response.data
          if (!unique) {
            callback(new Error('该用户名已被注册,请更换'))
          } else {
            callback()
          }
        })
      } else {
        callback()
      }
    }
    const validateSerialNumber = (rule, value, callback) => {
      if (!isSerialNumber(value)) {
        callback(new Error('请输入正确的手机号码'))
      } else {
        callback()
      }
    }
    return {
      title: '',
      open: false,
      loadingChange: false,
      // 性别 M男 F女数据字典
      sexOptions: [],
      // 人事状态
      personnelStatusOptions: [],
      // 所属部门
      deptOptions: [],
      // 用户角色
      roleOptions: undefined,
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      // 表单参数
      form: {},
      baseRule: {
        staffName: [
          { required: true, message: '员工姓名不能为空', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '联系电话不能为空', trigger: 'blur' },
          { validator: validateSerialNumber, trigger: 'blur' }
        ],
        emailAddress: [
          { required: true, message: '邮箱不能为空', trigger: 'blur' }
        ],
        sex: [
          { required: true, message: '性别 M男 F女不能为空', trigger: 'blur' }
        ],
        entryDate: [
          { required: true, message: '入职日期不能为空', trigger: 'blur' }
        ],
        personnelStatus: [
          { required: true, message: '请选择人事状态', trigger: 'blur' }
        ]
      },
      checkRoleIds: [],
      validateUsernameUnique: validateUsernameUnique,
      campusOptions: [],
      belongCampus: []
    }
  },
  computed: {
    rules() {
      const validatePass = (rule, value, callback) => {
        if (!this.form.loginUser) {
          callback()
          return
        }
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
        if (!this.form.loginUser) {
          callback()
          return
        }
        if (value === undefined || value === '') {
          callback(new Error('请再次输入密码'))
        } else if (value !== this.form.password) {
          callback(new Error('两次输入密码不一致!'))
        } else {
          callback()
        }
      }
      const validateUsername = (rule, value, callback) => {
        if (!this.form.loginUser) {
          callback()
          return
        }
        if (value === undefined || value === '') {
          callback(new Error('请输入用户名'))
        } else if (!isUsername(value)) {
          callback(new Error('4到16位(字母，数字，下划线)'))
        } else {
          callback()
        }
      }
      if (this.form.userId === undefined && this.form.loginUser) {
        return Object.assign({}, this.baseRule, {
          locked: [
            { required: true, message: '请选择是否允许登录系统', trigger: 'blur' }
          ],
          username: [
            { validator: validateUsername, trigger: 'blur' },
            { validator: this.validateUsernameUnique, trigger: 'blur' }
          ],
          password: [
            { validator: validatePass, trigger: 'blur' }
          ],
          checkPass: [
            { validator: validateCheckPass, trigger: 'blur' }
          ]
        })
      } else {
        return this.baseRule
      }
    }
  },
  mounted() {
    this.getDeptTreeSelect()
    this.getRoleTreeSelect()
    this.getDictListByDictType('sex').then(response => {
      this.sexOptions = response.data
    })
    this.getDictListByDictType('personnel_status').then(response => {
      this.personnelStatusOptions = response.data
    })
    campusList().then(response => {
      this.campusOptions = response.data
    })
    campusSelect().then(response => {
      this.belongCampus = response.data
    })
  },
  methods: {
    /** 查询部门下拉树结构 */
    getDeptTreeSelect() {
      treeSelect().then(response => {
        this.deptOptions = response.data
      })
    },
    getRoleTreeSelect() {
      roleTreeSelect().then(response => {
        this.roleOptions = response.data
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.loginUser) {
            this.form.roleIds = this.getRoleAllCheckedKeys()
          }
          if (this.form.staffId !== undefined) {
            this.loadingChange = true
            updateStaff(this.form).then(response => {
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
            addStaff(this.form).then(response => {
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
        locked: '0',
        belongCampus: undefined,
        partCampus: []
      }
      this.resetForm('form')
    },
    // 已选择角色id
    getRoleAllCheckedKeys() {
      // 半选中的菜单节点
      const halfCheckedKeys = this.$refs.role.getHalfCheckedKeys()
      // 目前被选中的菜单节点
      const checkedKeys = this.$refs.role.getCheckedKeys()
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys)
      return checkedKeys
    },
    // 重置密码
    handleResetPwd() {
      this.$prompt('请输入"' + this.form.username + '"的新密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^.*(?=.{6,16})(?=.*\d)(?=.*[A-Z]{1,})(?=.*[a-z]{1,})(?=.*[!@#$%^&*?\(\)]).*$/,
        inputErrorMessage: '密码最少6位,包含大小写字母和特殊字符'
      }).then(({ value }) => {
        resetUserPwd(this.form.userId, value).then(response => {
          if (response.respCode === '0000') {
            this.msgSuccess('修改成功，新密码是：' + value)
          } else {
            this.msgError(response.msg)
          }
        })
      }).catch(() => {})
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
