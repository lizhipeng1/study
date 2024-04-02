<!-- 未选班级 选择班级-->
<template>
  <div>
    <el-dialog title="未选班级学员" :visible.sync="open" width="700px" class="compact">
      <div v-if="dataList.length === 0" style="padding: 0 0 10px;">
        <span style="color: red;padding-right: 5px;">*</span>
        <span>暂无已报名未选班学员</span>
        <router-link to="/order/handle/signUp" class="link-type">
          <span style="text-decoration: underline;">去报名</span>
        </router-link>
      </div>
      <el-table v-else v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column align="center" prop="studentName" label="学生" fixed="left" />
        <el-table-column align="center" prop="sex" label="性别" :formatter="sexFormatter" />
        <el-table-column align="center" prop="phone" label="联系电话" width="120">
          <template slot-scope="scope">
            <el-tooltip effect="dark" :content="scope.row.contactInfo" placement="top">
              <span>{{ scope.row.phone }}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="deptName" label="报读校区" />
        <el-table-column align="center" prop="status" label="状态" :formatter="studentCourseStatusFormatter" />
        <el-table-column align="center" prop="chargeType" label="收费方式">
          <template slot-scope="scope">
            <span>{{ chargeTypeFormatter(scope.row) }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="chargeType" label="剩余课时/天" width="100">
          <template slot-scope="scope">
            <span v-if="scope.row.chargeType === 'date'">
              {{ scope.row.balanceDays }}天
            </span>
            <span v-else>
              <el-tooltip effect="dark" :content="'过期:' + scope.row.expireHour + '课时'" placement="top">
                <span>{{ scope.row.balanceHour - scope.row.expireHour }}课时</span>
              </el-tooltip>
            </span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="status" label="首次报名时间" width="110">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.firstSignTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="status" label="最后续费时间" width="110">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.lastSignTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
      <div slot="footer" class="dialog-footer">
        <el-button v-if="dataList.length > 0" :loading="loading" type="primary" @click="handleChooseStudent">确定选择</el-button>
        <el-button @click="open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { searchCourseClaStudent, studentCourseChooseCla } from '@/api/sc/student/course'
export default {
  props: {
    // 所选择班级编号
    chooseClaId: {
      type: String,
      default: undefined
    },
    // 报读课程
    courseId: {
      type: String,
      default: undefined
    },
    // 报读校区
    departId: {
      type: String,
      default: undefined
    },
    studentCourseStatusOptions: {
      type: Array,
      default: function() {
        return []
      }
    },
    chargeTypeOptions: {
      type: Array,
      default: function() {
        return []
      }
    },
    sexOptions: {
      type: Array,
      default: function() {
        return []
      }
    }
  },
  data() {
    return {
      open: false,
      loading: true,
      total: 0,
      // 表格树数据
      dataList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        courseId: undefined,
        departId: undefined,
        unChooseCla: true
      },
      chooseStudentCourseIds: []
    }
  },
  watch: {
    courseId: {
      handler(newValue, oldValue) {
        this.queryParams.courseId = newValue
      },
      immediate: true
    },
    departId: {
      handler(newValue, oldValue) {
        this.queryParams.departId = newValue
      },
      immediate: true
    }
  },
  created() {
  },
  methods: {
    sexFormatter(row, column) {
      return this.selectDictLabel(this.sexOptions, row.sex)
    },
    chargeTypeFormatter(row, column) {
      return this.selectDictLabel(this.chargeTypeOptions, row.chargeType)
    },
    studentCourseStatusFormatter(row, column) {
      return this.selectDictLabel(this.studentCourseStatusOptions, row.status)
    },
    handleSelectionChange(selection) {
      this.chooseStudentCourseIds = selection.map(item => item.studentCourseId)
    },
    getList() {
      this.loading = true
      searchCourseClaStudent(this.queryParams).then(response => {
        this.dataList = response.data.rows
        this.total = response.data.total
        this.loading = false
      })
    },
    /** 重置按钮操作 */
    reset() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        courseId: this.courseId,
        departId: this.departId,
        unChooseCla: true
      }
      this.resetForm('queryParams')
    },
    handleOpen() {
      this.reset()
      this.open = true
      this.getList()
    },
    // 选择
    handleChooseStudent() {
      console.log(this.chooseStudentCourseIds)
      this.loading = true
      studentCourseChooseCla({
        chooseStudentCourseIds: this.chooseStudentCourseIds,
        claId: this.chooseClaId,
        courseId: this.courseId
      }).then(response => {
        this.loading = false
        if (response.respCode === '0000') {
          this.msgSuccess('操作成功')
          this.open = false
          this.$emit('success')
        } else {
          this.msgError(response.respMsg)
        }
      }).catch(() => {
        this.loading = false
      })
    }
  }
}
</script>
