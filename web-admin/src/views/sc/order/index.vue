<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="订单类型" prop="orderType">
        <el-select v-model="queryParams.orderType" placeholder="请选择订单类型" clearable size="small">
          <el-option
            v-for="dict in orderTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="校区:" prop="deptId">
        <dept-select v-model="queryParams.deptId" />
      </el-form-item>
      <el-form-item label="课程:" prop="courseId">
        <course-select v-model="queryParams.courseId" :dept-id="queryParams.deptId" clearable mounted-load-all />
      </el-form-item>
      <el-form-item label="订单状态" prop="orderStatus">
        <el-select v-model="queryParams.orderStatus" placeholder="请选择订单状态" clearable size="small">
          <el-option
            v-for="dict in orderStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="经办日期" prop="handleDateArray">
        <el-date-picker
          v-model="handleDateArray"
          clearable
          size="small"
          style="width: 230px"
          type="daterange"
          value-format="yyyy-MM-dd"
          placeholder="选择经办日期"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-dropdown>
          <el-button type="primary" size="mini">
            业务办理 <i class="el-icon-arrow-down el-icon--right" />
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item v-has-permi="['sc:order:handleSignUp']" icon="el-icon-plus" @click.native="signUp">报名</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['sc:order:batchInvalid']"
          v-loading="loadingChange"
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="batchInvalidOrder"
        >批量作废订单
        </el-button>
      </el-col>
    </el-row>
    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="orderId" width="170" align="center" label="订单编号" fixed="left">
        <template slot-scope="scope">
          <span style="text-decoration: underline;cursor: pointer;" @click="handleOrderDetail(scope.row)">{{ scope.row.orderId }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="orderType" align="center" label="订单类型" :formatter="orderTypeFormat" fixed="left" />
      <el-table-column prop="orderStatus" align="center" label="订单状态" fixed="left">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.orderStatus === '1'" size="medium" type="info">未支付</el-tag>
          <el-tag v-if="scope.row.orderStatus === '2'" size="medium">已支付</el-tag>
          <el-tag v-if="scope.row.orderStatus === '3'" size="medium" type="danger">已作废</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="studentName" align="center" label="学生" />
      <el-table-column prop="phone" width="110" align="center" label="联系电话" />
      <el-table-column prop="orderDetail" width="200" align="center" label="销售内容">
        <template slot-scope="scope">
          <el-tooltip effect="dark" placement="left">
            <template slot="content">
              <div v-for="item in scope.row.orderDetail.split(';')" :key="item">
                <span>{{ item }}</span>
              </div>
            </template>
            <span style="display:inline-block;width: 180px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;"> {{ scope.row.orderDetail }} </span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column prop="actualTotalFee" align="center" label="应付(元)" />
      <el-table-column prop="receiptFee" align="center" label="实收(元)" />
      <el-table-column prop="balanceFee" width="120" align="center" label="余额支付(元)" />
      <el-table-column prop="saleStaffName" align="center" label="销售员" />
      <el-table-column prop="orderTag" align="center" label="订单标签" show-overflow-tooltip />
      <el-table-column prop="handleDeptName" align="center" label="经办校区" show-overflow-tooltip />
      <el-table-column prop="handleDate" width="100" align="center" label="经办日期" />
      <el-table-column prop="createUserName" width="100" align="center" label="经办人" />
      <el-table-column prop="memo" align="center" label="办理备注" show-overflow-tooltip />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-dropdown trigger="click">
            <span style="cursor: pointer;color: #409EFF;outline: none;">
              操作<i class="el-icon-arrow-down el-icon--right" style="font-size: 12px;" />
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-document" @click.native="handleOrderDetail(scope.row)">详情</el-dropdown-item>
              <el-dropdown-item icon="el-icon-delete" @click.native="invalidOrder(scope.row)">作废</el-dropdown-item>
              <el-dropdown-item v-has-permi="['sc:order:print']" icon="el-icon-printer" @click.native="handlePrintOrder(scope.row)">打印</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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

    <order-detail ref="orderDetail" />
  </div>
</template>

<script>
import { listOrder, invalidById } from '@/api/sc/order'
import orderDetail from '@/components/sc/order/orderDetail'
import deptSelect from '@/components/system/dept/deptSelect'
import courseSelect from '@/components/sc/course/courseSelect'
import moment from 'moment'

export default {
  name: 'Order',
  components: {
    orderDetail,
    deptSelect,
    courseSelect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      loadingChange: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 表格树数据
      dataList: [],
      // 是否显示弹出层
      open: false,
      // 学员编号数据字典
      studentIdOptions: [],
      // 订单类型 1报名数据字典
      orderTypeOptions: [],
      // 订单状态
      orderStatusOptions: [],
      handleDateArray: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        studentId: undefined,
        orderType: undefined,
        handleDate: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {

      }
    }
  },
  created() {
    const handleDate = this.$route.query.handleDate
    if (handleDate !== undefined && handleDate === 'thisMonth') {
      // 查询本月订单
      const startDate = moment().month(moment().month()).startOf('month').format('YYYY-MM-DD')
      const endDate = moment().month(moment().month()).endOf('month').format('YYYY-MM-DD')
      this.handleDateArray = [startDate, endDate]
    }
    this.getList()
    this.getDictListByDictType('order_type').then(response => {
      this.orderTypeOptions = response.data
    })
    this.getDictListByDictType('order_status').then(response => {
      this.orderStatusOptions = response.data
    })
  },
  methods: {
    getList() {
      this.loading = true
      listOrder(this.queryParams).then(response => {
        this.dataList = response.data.rows
        this.total = response.data.total
        this.loading = false
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
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      if (this.handleDateArray !== undefined && this.handleDateArray !== null && this.handleDateArray.length === 2) {
        this.queryParams.handleDate = this.handleDateArray.toString()
      } else {
        this.queryParams.handleDate = undefined
      }
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 报名
    signUp() {
      this.$router.push({
        path: '/order/handle/signUp'
      })
    },
    orderTypeFormat(row, column) {
      return this.selectDictLabel(this.orderTypeOptions, row.orderType)
    },
    // 作废订单
    invalidOrder(row) {
      const that = this
      this.confirm('确定作废本订单?', function() {
        that.loading = true
        invalidById(row.orderId).then(response => {
          that.loading = false
          if (response.respCode === '0000') {
            that.msgSuccess('作废成功')
            that.getList()
          } else {
            that.msgError(response.respMsg)
          }
        }).catch(() => {
          that.loading = false
        })
      })
    },
    // 批量作废订单
    batchInvalidOrder(row) {
      const that = this
      const id = this.ids
      this.confirm('确定作废已选择订单?', function() {
        that.loading = true
        invalidById(id).then(response => {
          that.loading = false
          if (response.respCode === '0000') {
            that.msgSuccess('作废成功')
            that.getList()
          } else {
            that.msgError(response.respMsg)
          }
        }).catch(() => {
          that.loading = false
          that.getList()
        })
      })
    },
    // 订单详情
    handleOrderDetail(row) {
      this.$refs.orderDetail.loadOrderDetail(row.orderId)
    },
    // 打印
    handlePrintOrder(row) {
      this.$router.push({
        path: '/order/handle/print/' + row.orderId
      })
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
</style>
