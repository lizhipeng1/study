<template>
  <div>
    <el-table v-loading="loading" :data="dataList">
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
      <el-table-column prop="studentName" align="center" label="学生" fixed="left" />
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
import { listOrder } from '@/api/sc/order'
import orderDetail from '@/components/sc/order/orderDetail'
export default {
  components: {
    orderDetail
  },
  props: {
    studentId: {
      type: String,
      default: undefined
    }
  },
  data() {
    return {
      loading: false,
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        studentId: undefined
      },
      orderTypeOptions: [],
      dataList: []
    }
  },
  watch: {
    studentId: {
      handler(newValue) {
        this.queryParams.studentId = newValue
      },
      immediate: true
    }
  },
  created() {
    this.getDictListByDictType('order_type').then(response => {
      this.orderTypeOptions = response.data
    })
  },
  methods: {
    getList() {
      this.loading = true
      listOrder(this.queryParams).then(response => {
        this.dataList = response.data.rows
        this.total = response.data.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    orderTypeFormat(row, column) {
      return this.selectDictLabel(this.orderTypeOptions, row.orderType)
    },
    // 订单详情
    handleOrderDetail(row) {
      this.$refs.orderDetail.loadOrderDetail(row.orderId)
    }
  }
}
</script>
