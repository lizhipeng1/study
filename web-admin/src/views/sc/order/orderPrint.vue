<template>
  <div ref="print" class="print-container">
    <div class="print-title">
      <span>{{ tenantInfo.tenantName }}</span>
      <span>业务凭证</span>
    </div>
    <div class="header-info">
      <div class="left-info">
        <div class="header-item">学员姓名: {{ orderInfo.studentName }}</div>
        <div class="header-item">经办日期: {{ orderInfo.handleDate }}</div>
        <div v-if="orderInfo.orderType === '1'" class="header-item">类型: 新办</div>
        <div v-else class="header-item">类型: {{ orderInfo.orderType }}</div>
      </div>
      <div class="right-info">订单: {{ orderInfo.orderId }}</div>
    </div>
    <div class="print-content">
      <div v-for="item in orderDetailArray" :key="item.orderDetailId">
        <div class="header">
          <div class="top-title w150">课程信息</div>
          <div class="top-title w150">班级</div>
          <div class="top-title w150">收费方式</div>
          <div class="top-title w50">数量</div>
          <div class="top-title w80">原价</div>
          <div class="top-title w150">折扣/减免</div>
          <div class="top-title w80">实际价格</div>
          <div class="top-title flex1">备注</div>
        </div>
        <div class="content">
          <div class="value w150">{{ item.courseName }}({{ item.deptName }})</div>
          <div class="value w150">{{ item.claName||'' }}</div>
          <div class="value w150">{{ item.chargeName }}</div>
          <div class="value w50">{{ item.buyCount }}</div>
          <div class="value w80">¥{{ item.originalFee }}</div>
          <div class="value w150">
            <span v-if="item.directDiscount && item.directDiscount !== 10">{{ item.directDiscount }}折 </span>
            <span v-if="item.directReduceFee && item.directReduceFee !== 0"> 减免:¥{{ item.directReduceFee }}</span>
          </div>
          <div class="value w80">¥{{ item.actualFee }}</div>
          <div class="value flex1">{{ item.outsideMemo }}</div>
        </div>
      </div>
      <div class="content">
        <div class="value flex1">备注: {{ orderInfo.memo||'' }}</div>
      </div>
      <div class="content">
        <div class="value flex1">
          <span class="span-title">原价总计:</span>
          <span>¥{{ orderInfo.originalTotalFee }}</span>
        </div>
        <div class="value flex1">
          <span class="span-title">实际价格:</span>
          <span>¥{{ orderInfo.actualTotalFee }}</span>
        </div>
        <div class="value flex1">
          <span class="span-title">实收:</span>
          <span>¥{{ orderInfo.receiptFee }}</span>
        </div>
        <div class="value flex1">
          <span class="span-title">余额支付:</span>
          <span>¥{{ orderInfo.balanceFee }}</span>
        </div>
      </div>
      <div class="content">
        <div class="value flex1">
          <span class="span-title">收款方式:</span>
          <span v-for="(item, index) in orderAccountArray" :key="index">
            <span style="margin-right: 15px;">{{ item.accountName }}: {{ item.fee }}元</span>
          </span>
        </div>
      </div>
      <div class="content">
        <div class="value flex1">
          <div>地址:</div>
          <div>{{ tenantInfo.contactAddress }}</div>
        </div>
        <div class="value flex1">
          <div>联系电话:</div>
          <div>{{ tenantInfo.contactPhone }}</div>
        </div>
        <div class="value flex1">
          <div>经办人:</div>
          <div>{{ orderInfo.handleStaffName }}</div>
        </div>
        <div class="value flex1">
          <div>打印时间:</div>
          <div>{{ now }}</div>
        </div>
        <div class="value flex1">
          <div>经办签名:</div>
          <div>&nbsp;</div>
        </div>
        <div class="value flex1">
          <div>客户签名:</div>
          <div>&nbsp;</div>
        </div>
      </div>
    </div>
    <div class="bottom-memo">开课后不予办理退费。请妥善保管您的收据,丢失不予补办。</div>

    <div class="no-print bottom-btn-container">
      <el-button v-if="canPrint" type="primary" @click="handlePrint">打 印</el-button>
      <el-button v-else type="info">订单已作废,无法打印</el-button>
    </div>
  </div>
</template>
<script>
import { getOrder } from '@/api/sc/order'
import { nowTenantInfo } from '@/api/system/tenant'
import moment from 'moment'
export default {
  data() {
    return {
      orderId: undefined,
      loading: false,
      orderInfo: {},
      orderDetailArray: [],
      orderAccountArray: [],
      tenantInfo: {}
    }
  },
  computed: {
    now() {
      return moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
    },
    // 是否可打印
    canPrint() {
      // 非作废
      return this.orderInfo.orderStatus && this.orderInfo.orderStatus !== '3'
    }
  },
  watch: {
    orderId: {
      handler(newValue) {
        if (newValue) {
          this.loadOrderDetail(newValue)
          this.loadTenantInfo()
        }
      },
      immediate: true
    }
  },
  created(param) {
    const orderId = this.$route.params && this.$route.params.orderId
    if (orderId) {
      this.orderId = orderId
    }
  },
  methods: {
    loadOrderDetail(orderId) {
      this.loading = true
      getOrder(orderId).then(response => {
        this.loading = false
        this.orderInfo = response.data.orderInfo
        this.orderDetailArray = response.data.orderDetail
        this.orderAccountArray = response.data.orderAccountList
      }).catch(() => {
        this.loading = false
      })
    },
    loadTenantInfo() {
      nowTenantInfo().then(response => {
        this.tenantInfo = response.data
      }).catch(() => {
        this.loading = false
      })
    },
    handlePrint() {
      this.$print(this.$refs.print)
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
  .main-container.print {
    .print-container {
      zoom: 0.7;
    }
  }
  .print-container {
    width: 900px;
    margin: auto;

    .print-title {
      margin-top: 25px;
      font-size: 16px;
      color: #000;
      font-weight: 800;
      text-align: center;
    }

    .header-info {
      padding: 10px 0;
      font-size: 12px;
      display: flex;
      justify-content: space-between;
      align-items: center;

      .left-info {
        display: flex;
        justify-content: flex-start;
        align-items: center;

        .header-item {
          margin-right: 15px;
        }
      }
    }

    .print-content {
      border-top: 1px solid #737373;
      border-left: 1px solid #737373;
      font-size: 14px;
      text-align: left;

      .flex1 {
        flex: 1;
      }

      .w150 {
        width: 150px;
      }
      .w180 {
        width: 180px;
      }
      .w50 {
        width: 50px;
      }
      .w100 {
        width: 100px;
      }
      .w80 {
        width: 80px;
      }

      .header {
        display: flex;
        justify-content: flex-start;
        align-items: inherit;

        .top-title {
          border-right: 1px solid #737373;
          border-bottom: 1px solid #737373;
          font-weight: 600;
          color: #000;
          padding: 5px;
        }
      }

      .content {
        display: flex;
        justify-content: flex-start;
        align-items: inherit;

        .value {
          border-right: 1px solid #737373;
          border-bottom: 1px solid #737373;
          font-weight: normal;
          color: #000;
          padding: 5px;

          .span-title {
            font-weight: 600;
            color: #000;
          }
        }
      }
    }

    .bottom-memo {
      margin-top: 5px;
      font-size: 12px;
    }

    /*底部按钮*/
    .bottom-btn-container {
      padding: 15px 20px;
      text-align: center;
    }
  }
</style>
