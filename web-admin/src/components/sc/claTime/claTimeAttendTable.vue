<!-- 班级上课记录 -->
<template>
  <div>
    <el-row v-if="!readonly" :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAddTimeAttend"
        >记上课</el-button>
      </el-col>
    </el-row>
    <el-table v-loading="loading" :data="dataList">
      <el-table-column align="center" prop="staffName" label="教师" fixed="left" />
      <el-table-column align="center" prop="roomName" label="教室" />
      <el-table-column align="center" prop="realClaDate" label="上课日期" width="120">
        <template slot-scope="scope">
          <el-tooltip effect="dark" :content="scope.row.realClaDate + ' ('+scope.row.realStartTime.substr(0,5) + '~' + scope.row.realEndTime.substr(0,5)+')'" placement="top">
            <span>{{ scope.row.realClaDate }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="needAttendCnt" label="实到/应到">
        <template slot-scope="scope">
          <span>{{ scope.row.realAttendCnt }}/{{ scope.row.needAttendCnt }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="classTheme" label="上课主题" />
      <el-table-column align="center" prop="lastUpdateUserName" label="记录人" />
      <el-table-column align="center" width="140" prop="lastUpdateTime" label="记录时间">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastUpdateTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="memo" label="备注" show-overflow-tooltip />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document-copy"
            @click="handleShowAttendDetail(scope.row.courseTimeId)"
          >查看明细</el-button>
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
    <!-- 记上课-->
    <add-cla-time-attend ref="addClaTimeAttend" :cla-id="claId" :diff-now-day="0" @success="handleClaTimeAttendSuccess" />
    <el-dialog title="上课明细" :visible.sync="attendDetailOpen" width="700px">
      <!--明细-->
      <cla-time-attend-detail-table ref="claTimeAttendDetailTable" />
    </el-dialog>
  </div>
</template>
<script>
import { selectListForAttend } from '@/api/sc/cla/claTime'
import addClaTimeAttend from '@/components/sc/claTime/addClaTimeAttend'
import claTimeAttendDetailTable from '@/components/sc/claTime/claTimeAttendDetailTable'
export default {
  components: {
    addClaTimeAttend,
    claTimeAttendDetailTable
  },
  props: {
    // 查询条件 班级
    claId: {
      type: String,
      default: undefined
    },
    // 查询条件 班级
    studentId: {
      type: String,
      default: undefined
    },
    // 是否只读(只能看 不能改)
    readonly: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      loading: false,
      total: 0,
      attendDetailOpen: false,
      // 表格树数据
      dataList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        claId: undefined,
        studentId: undefined,
        attended: true
      }
    }
  },
  watch: {
    claId: {
      handler(newValue) {
        this.queryParams.claId = newValue
      },
      immediate: true
    },
    studentId: {
      handler(newValue) {
        this.queryParams.studentId = newValue
      },
      immediate: true
    }
  },
  created() {
  },
  methods: {
    getList() {
      this.loading = true
      selectListForAttend(this.queryParams).then(response => {
        this.dataList = response.data.rows
        this.total = response.data.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    // 记上课
    handleAddTimeAttend() {
      this.$refs.addClaTimeAttend.open = true
    },
    // 记上课成功
    handleClaTimeAttendSuccess() {
      this.getList()
    },
    // 上课明细
    handleShowAttendDetail(courseTimeId) {
      this.attendDetailOpen = true
      this.$nextTick(() => {
        this.$refs.claTimeAttendDetailTable.queryParams.courseTimeId = courseTimeId
        this.$refs.claTimeAttendDetailTable.getList()
      })
    }
  }
}
</script>
