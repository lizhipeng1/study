<template>
  <div>
    <el-dialog title="订单详情" :visible.sync="open" width="780px" class="compact top0">
      <el-row v-loading="loading" class="cla-detail">
        <div class="title">
          <div class="title-content">订单信息</div>
        </div>
        <div class="cla-base-info">
          <div class="item">
            <div class="item-name">订单编号:</div>
            <div class="item-value">
              <el-tooltip effect="dark" :content="orderInfo.orderId" placement="top">
                <span>{{ orderInfo.orderId }}</span>
              </el-tooltip>
            </div>
          </div>
          <div class="item">
            <div class="item-name">经办校区:</div>
            <div class="item-value">{{ orderInfo.handleDeptName }}</div>
          </div>
          <div class="item">
            <div class="item-name">经办人:</div>
            <div class="item-value">{{ orderInfo.handleStaffName }}</div>
          </div>
          <div class="item">
            <div class="item-name">经办日期:</div>
            <div class="item-value">{{ orderInfo.handleDate }}</div>
          </div>
          <div class="item">
            <div class="item-name">学生:</div>
            <div class="item-value">{{ orderInfo.studentName }}</div>
          </div>
          <div class="item">
            <div class="item-name">订单类型:</div>
            <div v-if="orderInfo.orderType === '1'" class="item-value">新办</div>
            <div v-else class="item-value">{{ orderInfo.orderType }}</div>
          </div>
          <div class="item">
            <div class="item-name">原价:</div>
            <div class="item-value">¥{{ orderInfo.originalTotalFee }}</div>
          </div>
          <div class="item">
            <div class="item-name">实际价格:</div>
            <div class="item-value">¥{{ orderInfo.actualTotalFee }}</div>
          </div>
          <div class="item">
            <div class="item-name">实收:</div>
            <div class="item-value">¥{{ orderInfo.receiptFee }}</div>
          </div>
          <div v-if="orderInfo.balanceFee !== 0" class="item">
            <div class="item-name">余额支付:</div>
            <div class="item-value">¥{{ orderInfo.balanceFee }}</div>
          </div>
          <div class="item">
            <div class="item-name">订单标签:</div>
            <div class="item-value">{{ orderInfo.orderTag }}</div>
          </div>
          <div class="item">
            <div class="item-name">销售来源:</div>
            <div class="item-value">{{ orderInfo.saleSourceTag }}</div>
          </div>
          <div class="item">
            <div class="item-name">销售员工:</div>
            <div class="item-value">{{ orderInfo.saleStaffName }}</div>
          </div>
          <div class="item">
            <div class="item-name">订单状态:</div>
            <div class="item-value">
              <el-tag v-if="orderInfo.orderStatus === '1'" size="medium" type="info">未支付</el-tag>
              <el-tag v-if="orderInfo.orderStatus === '2'" size="medium">已支付</el-tag>
              <el-tag v-if="orderInfo.orderStatus === '3'" size="medium" type="danger">已作废</el-tag>
            </div>
          </div>
          <div class="item">
            <div class="item-name">备注:</div>
            <div class="item-value">{{ orderInfo.memo }}</div>
          </div>
        </div>
        <div class="title">
          <div class="title-content">收款账户</div>
        </div>
        <div v-for="(item, index) in orderAccountArray" :key="index" class="cla-base-info">
          <div class="item">
            <div class="item-name">收款账户:</div>
            <div class="item-value">{{ item.accountName }}</div>
          </div>
          <div class="item">
            <div class="item-name">收款金额:</div>
            <div class="item-value">{{ item.fee }}元</div>
          </div>
        </div>
        <div v-for="item in orderDetailArray" :key="item.orderDetailId" class="top-border">
          <div class="title">
            <div class="title-content">报读课程({{ item.courseName }})</div>
          </div>
          <div class="cla-base-info">
            <div class="item">
              <div class="item-name">课程:</div>
              <div class="item-value">{{ item.courseName }}</div>
            </div>
            <div class="item">
              <div class="item-name">报读校区:</div>
              <div class="item-value">{{ item.deptName }}</div>
            </div>
            <div class="item">
              <div class="item-name">类型:</div>
              <div class="item-value">
                <el-tag v-if="item.detailTag === '1'" size="medium" type="info">新报</el-tag>
                <el-tag v-else-if="item.detailTag === '2'" size="medium">续报</el-tag>
                <el-tag v-else-if="item.detailTag === '3'" size="medium">扩科</el-tag>
                <el-tag v-else size="medium">{{ item.detailTag }}</el-tag>
              </div>
            </div>
            <div v-if="item.claName" class="item">
              <div class="item-name">班级:</div>
              <div class="item-value">{{ item.claName }}</div>
            </div>
            <div class="item">
              <div class="item-name">状态:</div>
              <div class="item-value">
                <el-tag v-if="item.orderDetailStatus === '1'" size="medium" type="info">未支付</el-tag>
                <el-tag v-if="item.orderDetailStatus === '2'" size="medium">已支付</el-tag>
                <el-tag v-if="item.orderDetailStatus === '3'" size="medium" type="danger">已作废</el-tag>
              </div>
            </div>
            <div class="item">
              <div class="item-name">收费方式:</div>
              <div class="item-value">
                <el-tooltip effect="dark" :content="item.chargeName" placement="top">
                  <span>{{ item.chargeName }}</span>
                </el-tooltip>
              </div>
            </div>
            <div v-if="item.chargeType === 'date'" class="item">
              <div class="item-name">周期:</div>
              <div class="item-value">
                <el-tooltip effect="dark" :content="item.beginDate + '~' + item.endDate" placement="top">
                  <span>{{ item.beginDate }} ~ {{ item.endDate }}</span>
                </el-tooltip>
              </div>
            </div>
            <div v-else-if="item.chargeType !== 'date' && item.expireDate" class="item">
              <div class="item-name">过期时间:</div>
              <div class="item-value">{{ item.expireDate }}</div>
            </div>
            <div class="item">
              <div class="item-name">购买数量:</div>
              <div class="item-value">{{ item.buyCount }}</div>
            </div>
            <div class="item">
              <div class="item-name">原价:</div>
              <div class="item-value">¥{{ item.originalFee }}</div>
            </div>
            <div v-if="item.directDiscount !== 10" class="item">
              <div class="item-name">折扣:</div>
              <div class="item-value">{{ item.directDiscount }}折</div>
            </div>
            <div v-if="item.directReduceFee" class="item">
              <div class="item-name">减免:</div>
              <div class="item-value">¥{{ item.directReduceFee }}</div>
            </div>
            <div class="item">
              <div class="item-name">实际价格:</div>
              <div class="item-value">¥{{ item.actualFee }}</div>
            </div>
            <div class="item">
              <div class="item-name">内部备注:</div>
              <div class="item-value">{{ item.insideMemo }}</div>
            </div>
            <div class="item">
              <div class="item-name">外部备注:</div>
              <div class="item-value">{{ item.outsideMemo }}</div>
            </div>
          </div>
        </div>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getOrder } from '@/api/sc/order'
export default {
  data() {
    return {
      open: false,
      loading: false,
      orderInfo: {},
      orderDetailArray: [],
      orderAccountArray: []
    }
  },
  methods: {
    loadOrderDetail(orderId) {
      this.open = true
      this.loading = true
      getOrder(orderId).then(response => {
        this.loading = false
        this.orderInfo = response.data.orderInfo
        this.orderDetailArray = response.data.orderDetail
        this.orderAccountArray = response.data.orderAccountList
      }).catch(() => {
        this.loading = false
      })
    }
  }
}
</script>
<style lang="scss" scoped src="@/styles/sc/cla/cla-detail.scss"></style>
<style rel="stylesheet/scss" lang="scss" scoped>
  .cla-detail .cla-base-info{
    border-bottom: none;
  }
  .top-border {
    border-top: 2px solid #f5f7f9;
  }
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
