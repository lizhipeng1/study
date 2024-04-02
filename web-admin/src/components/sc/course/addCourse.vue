<template>
  <div>
    <el-dialog title="新设课程" :visible.sync="open" width="700px">
      <el-form ref="form" v-loading="loadingChange" :model="form" :rules="rules" label-width="120px">
        <div class="title top">
          <div class="title-content">课程基础信息</div>
        </div>
        <el-row>
          <el-col :span="12">
            <el-form-item label="课程名称:" prop="courseName">
              <el-input v-model="form.courseName" placeholder="请输入课程名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="课程类别:" prop="courseTypeId">
              <div class="select-with-btn-container">
                <el-select
                  v-model="form.courseTypeId"
                  placeholder="请选择课程类别"
                  clearable
                  filterable
                  default-first-option
                  :loading="loadingSelect"
                >
                  <el-option
                    v-for="item in courseTypeOptions"
                    :key="item.courseTypeId"
                    :label="item.courseType"
                    :value="item.courseTypeId"
                  />
                </el-select>
                <el-button type="primary" icon="el-icon-plus" size="small" @click="handleAddCourseType" />
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="上课模式:" prop="teachingMode" class="align-left">
              <el-radio-group v-model="form.teachingMode">
                <el-radio label="1">班课</el-radio>
                <el-radio disabled label="2">一对一</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="上课校区:" prop="courseCampus" class="align-left">
              <el-radio-group v-model="form.courseCampus">
                <el-radio
                  v-for="item in courseCampus"
                  :key="item.id"
                  :label="item.id"
                  :value="item.id"
                >{{ item.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="form.courseCampus === 'part'">
          <el-form-item label="选择校区:" prop="partCampus" class="align-left">
            <el-checkbox-group v-model="form.partCampus">
              <el-checkbox v-for="(item) in campusOptions" :key="item.id" :label="item.id" name="partCampus">{{ item.label }}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="课程简介:" prop="courseIntro">
              <el-input v-model="form.courseIntro" type="textarea" placeholder="请输入课程简介" />
            </el-form-item>
          </el-col>
        </el-row>
        <div class="title">
          <div class="title-content">课程收费模式</div>
        </div>
        <div class="fee-mode-content">
          <fee-mode-hour ref="feeModeHour" :campus-options="campusOptions" :had-choose-campus="form.partCampus" :course-campus="form.courseCampus" />
          <fee-mode-date ref="feeModeDate" :campus-options="campusOptions" :had-choose-campus="form.partCampus" :course-campus="form.courseCampus" />
          <fee-mode-cycle ref="feeModeCycle" :campus-options="campusOptions" :had-choose-campus="form.partCampus" :course-campus="form.courseCampus" />
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="loadingChange" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <add-course-type ref="addCourseType" @success="courseTypeList" />
  </div>
</template>
<script>
import { addCourse, updateCourse } from '@/api/sc/course'
import { select as courseTypeSelect } from '@/api/sc/course/courseType'
import { campusList, campusSelect } from '@/api/system/dept'
import feeModeHour from '@/components/sc/course/feeModeHour'
import feeModeDate from '@/components/sc/course/feeModeDate'
import feeModeCycle from '@/components/sc/course/feeModeCycle'
import addCourseType from '@/components/sc/course/type/addCourseType'
export default {
  components: {
    feeModeHour,
    feeModeDate,
    feeModeCycle,
    addCourseType
  },
  data() {
    return {
      // 是否显示弹出层
      open: false,
      loadingChange: false,
      // 表单参数
      form: {
        courseCampus: undefined,
        teachingMode: '1',
        partCampus: []
      },
      // 表单校验
      rules: {
        courseName: [
          { required: true, message: '课程名称不能为空', trigger: 'blur' }
        ],
        teachingMode: [
          { required: true, message: '请选择上课模式', trigger: 'blur' }
        ],
        courseCampus: [
          { required: true, message: '请选择上课校区', trigger: 'blur' }
        ]
      },
      // 可选校区类型
      courseCampus: [],
      courseTypeOptions: [],
      campusOptions: [],
      loadingSelect: false
    }
  },
  created() {
  },
  methods: {
    init: function() {
      this.courseTypeList()
      this.campusList()
      this.campusSelect()
    },
    // 初始化资费配置
    initFeeModeDate(data) {
      this.$nextTick(() => {
        this.$refs.feeModeHour.feeModeTableData = data.feeModeHourList
        this.$refs.feeModeHour.feeModeHour = data.feeModeHour

        this.$refs.feeModeDate.feeModeTableData = data.feeModeDateList
        this.$refs.feeModeDate.feeModeDate = data.feeModeDate

        this.$refs.feeModeCycle.feeModeTableData = data.feeModeCycleList
        this.$refs.feeModeCycle.feeModeCycle = data.feeModeCycle
      })
    },
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.loadingChange = true

          const feeModeHourList = this.$refs.feeModeHour.feeModeTableData
          const feeModeHour = this.$refs.feeModeHour.feeModeHour

          const feeModeDateList = this.$refs.feeModeDate.feeModeTableData
          const feeModeDate = this.$refs.feeModeDate.feeModeDate

          const feeModeCycleList = this.$refs.feeModeCycle.feeModeTableData
          const feeModeCycle = this.$refs.feeModeCycle.feeModeCycle

          this.form.feeModeHourList = feeModeHourList
          this.form.feeModeHour = feeModeHour

          this.form.feeModeDateList = feeModeDateList
          this.form.feeModeDate = feeModeDate

          this.form.feeModeCycleList = feeModeCycleList
          this.form.feeModeCycle = feeModeCycle

          if (this.form.courseId !== undefined) {
            updateCourse(this.form).then(response => {
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
            addCourse(this.form).then(response => {
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
    campusSelect() {
      campusSelect().then(response => {
        this.courseCampus = response.data
      })
    },
    campusList() {
      campusList().then(response => {
        this.campusOptions = response.data
      })
    },
    courseTypeList() {
      courseTypeSelect({}).then(response => {
        this.courseTypeOptions = response.data
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        courseCampus: undefined,
        teachingMode: '1',
        partCampus: []
      }
      this.resetForm('form')
      this.$nextTick(() => {
        this.$refs.feeModeHour.feeModeTableData = [{
          campusId: -1,
          campusName: '全部校区',
          cnt: 1,
          totalFee: 0
        }]
        this.$refs.feeModeHour.feeModeHour = true

        this.$refs.feeModeDate.feeModeTableData = [{
          campusId: -1,
          campusName: '全部校区',
          cnt: 1,
          dateType: 'month',
          totalFee: 0
        }]
        this.$refs.feeModeDate.feeModeDate = false

        this.$refs.feeModeCycle.feeModeTableData = [{
          campusId: -1,
          campusName: '全部校区',
          cnt: 1,
          totalFee: 0
        }]
        this.$refs.feeModeCycle.feeModeCycle = false
      })
    },
    // 新增课程类别
    handleAddCourseType() {
      this.$refs.addCourseType.open = true
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss">
  .align-left {
    text-align: left;
  }
  .second-title{
    text-align: left;
    padding-left: 15px;
  }
  .none-margin-bottom{
    margin-bottom: 0px;
  }
  .margin-top-20{
    margin-top: 20px;
  }
  .header-need-input:before{
    content: '*';
    color: #F56C6C;
    margin-right: 4px;
  }
  .fee-table{
    width: calc(100% - 25px);
    margin-left: 25px;
  }
</style>
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
