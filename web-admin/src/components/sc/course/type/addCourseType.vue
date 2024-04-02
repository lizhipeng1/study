<!--新增课程类型-->
<template>
  <el-dialog :title="title" :visible.sync="open" width="600px">
    <el-form ref="form" v-loading="loadingChange" :model="form" :rules="rules" label-width="80px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="类型名称" prop="courseType">
            <el-input v-model="form.courseType" placeholder="请填写课程类型名" />
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
import { addType, updateType } from '@/api/sc/course/courseType'
export default {
  data() {
    return {
      title: '添加课程类型',
      open: false,
      // 遮罩层
      loading: false,
      loadingChange: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        courseType: [
          { required: true, message: '课程类型名不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        courseType: undefined,
        sort: undefined
      }
      this.resetForm('form')
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.loadingChange = true
          if (this.form.courseTypeId !== undefined) {
            updateType(this.form).then(response => {
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
            addType(this.form).then(response => {
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
