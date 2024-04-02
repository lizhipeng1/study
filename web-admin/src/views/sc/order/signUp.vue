<!--新报-->
<template>
  <div class="app-container signUp">
    <div class="top-container">
      <div>
        <div class="title top">
          <div class="title-content">选择学员</div>
        </div>
        <student-select v-model="orderForm.studentId" @change="handleStudentChange" placeholder="查询学生" />
        <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddStudent">新建学员</el-button>
      </div>
      <div class="stu-info">
        <div class="info-item">
          <span class="un-important">电话:</span>
          <span>
            <el-tooltip effect="dark" :content="studentCourseInfo.contactPhoneDetail" placement="top">
              <span>{{ studentCourseInfo.contactPhone||'无' }}</span>
            </el-tooltip>
          </span>
        </div>
        <div class="info-item">
          <span class="un-important">报读校区:</span>
          <span>
            {{ studentCourseInfo.deptNames||'无' }}
          </span>
        </div>
        <div class="info-item">
          <span class="un-important">报读课程:</span>
          <span>
            {{ studentCourseInfo.courseNames||'无' }}
          </span>
        </div>
      </div>
    </div>
    <div class="order-detail-container">
      <div class="title top">
        <div class="title-content">报名信息</div>
      </div>
      <div class="choose-btn">
        <el-button type="primary" icon="el-icon-notebook-2" size="mini" @click="chooseCourse">选择课程</el-button>
        <!-- <el-button type="success" disabled icon="el-icon-s-grid" size="mini">选择班级</el-button>-->
      </div>
      <div class="choose-course-container">
        <el-row>
          <el-col :span="24">
            <el-card
              v-for="(item, index) in chooseCourseList"
              :key="item.courseId"
              shadow="hover"
              class="course-item compact"
            >
              <div class="header flex space-between">
                <div>
                  <div class="inline-block">{{ index + 1 }}.{{ item.courseName }}（{{ item.deptName }}）</div>
                  <div class="inline-block">
                    <el-select
                      v-model="orderForm[item.courseId].detailTag"
                      filterable
                      placeholder=""
                      default-first-option
                      style="width: 80px"
                      size="small"
                    >
                      <el-option
                        v-for="dict in detailTagOptions"
                        :key="dict.dictValue"
                        :label="dict.dictLabel"
                        :value="dict.dictValue"
                      />
                    </el-select>
                  </div>
                  <div class="inline-block">
                    <span style="padding: 0 5px 0 10px;">班级:</span>
                    <cla-select
                      v-model="orderForm[item.courseId].claId"
                      :course-id="item.courseId"
                      :clearable="true"
                      :dept-id="orderForm[item.courseId].deptId"
                      mounted-load-all
                    />
                  </div>
                </div>
                <div class="flex">
                  <div class="inline-block sum-hour">
                    <span class="un-important">共:</span>
                    <span class="important">{{ calcItemCount(item).toFixed(2) }}</span>
                    <span class="un-important">{{ calcItemCountUnit(item) }}</span>
                  </div>
                  <div class="inline-block fee">
                    <span class="un-important">小计</span>
                    <span class="important">¥ {{ calcItemActualFee(item).toFixed(2) }}</span>
                    <span class="un-important">元</span>
                  </div>
                  <div class="inline-block">
                    <i class="el-icon-close close-btn" @click="removeCourseItem(item.courseId)" />
                  </div>
                </div>
              </div>
              <div class="content">
                <el-row>
                  <el-col :span="12" class="item">
                    <el-col :span="12" class="fee-info">
                      <div class="un-important align-left f14 black">价格</div>
                    </el-col>
                    <el-col :span="12" class="fee-info">
                      <div class="align-right">
                        <span class="important normal">{{ calcItemFee(item).toFixed(2) }}</span>
                        <span class="un-important">元</span>
                      </div>
                    </el-col>
                    <!--收费模式-->
                    <el-col :span="24" style="text-align: left;margin-top: 8px;">
                      <div class="inline-block form-title need-input">
                        <span class="un-important f14">收费模式：</span>
                      </div>
                      <div class="inline-block">
                        <el-select
                          v-model="orderForm[item.courseId].chargeId"
                          filterable
                          placeholder=""
                          default-first-option
                          style="width: 160px"
                          size="small"
                        >
                          <el-option
                            v-for="chargeItem in item.courseChargeList"
                            :key="chargeItem.chargeId"
                            :label="chargeItem.label"
                            :value="chargeItem.chargeId"
                          />
                        </el-select>
                      </div>
                      <div class="inline-block" style="padding: 0 10px;">X</div>
                      <div class="inline-block">
                        <el-input-number
                          v-model="orderForm[item.courseId].buyCount"
                          style="width: 110px;"
                          placeholder="数量"
                          controls-position="right"
                          :min="1"
                          :step="1"
                          size="small"
                        />
                      </div>
                    </el-col>
                    <!--有效期-->
                    <el-col
                      v-if="calcItemChargeInfo(item).chargeType === 'hour'"
                      :span="24"
                      style="text-align: left;margin-top: 8px;"
                    >
                      <div class="inline-block form-title">
                        <span class="un-important f14">
                          <el-checkbox
                            v-model="orderForm[item.courseId].openExpire"
                          >有效期：</el-checkbox>
                        </span>
                      </div>
                      <div class="inline-block">
                        <el-date-picker
                          v-model="orderForm[item.courseId].expireDate"
                          clearable
                          :readonly="!orderForm[item.courseId].openExpire"
                          :disabled="!orderForm[item.courseId].openExpire"
                          size="small"
                          style="width: 160px"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择失效时间"
                        />
                      </div>
                    </el-col>
                    <!--按时间收费 周期-->
                    <el-col
                      v-if="calcItemChargeInfo(item).chargeType === 'date'"
                      :span="24"
                      style="text-align: left;margin-top: 8px;"
                    >
                      <div class="inline-block form-title need-input">
                        <span class="un-important f14">生效时间: </span>
                      </div>
                      <div class="inline-block">
                        <el-date-picker
                          v-model="orderForm[item.courseId].beginDate"
                          size="small"
                          style="width: 160px"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择生效时间"
                        />
                      </div>
                      <div class="inline-block" style="padding: 0 10px"> -</div>
                      <div class="inline-block">
                        <el-date-picker
                          v-model="orderForm[item.courseId].endDate"
                          size="small"
                          style="width: 160px"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择失效时间"
                        />
                      </div>
                    </el-col>
                  </el-col>
                  <el-col :span="12" class="item">
                    <el-col :span="12" class="fee-info">
                      <div class="un-important align-left f14 black">优惠</div>
                    </el-col>
                    <el-col :span="12" class="fee-info">
                      <div class="align-right">
                        <span class="important green"> {{ calcItemReduceFee(item).toFixed(2) }} </span>
                        <span class="un-important">元</span>
                      </div>
                    </el-col>
                    <!--折扣/优惠金额-->
                    <el-col :span="24" style="text-align: left;margin-top: 8px;height: 32px;line-height: 32px;">
                      <div class="inline-block form-title">
                        <span class="un-important f14"><el-checkbox
                          v-model="orderForm[item.courseId].openDiscount"
                        >学费折扣</el-checkbox></span>
                      </div>
                      <div class="inline-block form-title" style="margin-left: 15px;">
                        <span class="un-important f14"><el-checkbox v-model="orderForm[item.courseId].openDiscountFee">学费优惠</el-checkbox></span>
                      </div>
                    </el-col>
                    <el-col
                      v-if="orderForm[item.courseId].openDiscountFee || orderForm[item.courseId].openDiscount"
                      :span="24"
                      style="text-align: left;margin-top: 8px;"
                    >
                      <div class="inline-block form-title need-input" style="width: 85px;">
                        <span class="un-important f14">优惠/折扣：</span>
                      </div>
                      <div class="inline-block">
                        <el-select
                          v-if="orderForm[item.courseId].openDiscount"
                          v-model="orderForm[item.courseId].discount"
                          filterable
                          placeholder=""
                          default-first-option
                          style="width: 100px"
                          size="small"
                        >
                          <el-option
                            v-for="discountItem in discountList"
                            :key="discountItem.discount"
                            :label="discountItem.label"
                            :value="discountItem.discount"
                          />
                        </el-select>
                        <el-input
                          v-if="orderForm[item.courseId].openDiscountFee"
                          v-model="orderForm[item.courseId].discountFee"
                          style="width: 180px;"
                          placeholder="输入优惠金额"
                          controls-position="right"
                          :min="0"
                          size="small"
                        >
                          <template slot="append">元</template>
                        </el-input>
                      </div>
                    </el-col>
                  </el-col>
                </el-row>
                <el-row class="bottom">
                  <el-col :span="12" class="item" style="text-align: left;">
                    <div class="inline-block form-title">
                      <span class="un-important f14">内部备注：</span>
                    </div>
                    <div class="inline-block">
                      <el-input
                        v-model="orderForm[item.courseId].insideMemo"
                        size="small"
                        style="width: 360px;"
                        placeholder="仅机构内部可见的备注"
                      />
                    </div>
                  </el-col>
                  <el-col :span="12" class="item" style="text-align: left;">
                    <div class="inline-block form-title">
                      <span class="un-important f14">外部备注：</span>
                    </div>
                    <div class="inline-block">
                      <el-input
                        v-model="orderForm[item.courseId].outsideMemo"
                        size="small"
                        style="width: 360px;"
                        placeholder="客户可见的备注"
                      />
                    </div>
                  </el-col>
                </el-row>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </div>
    <div v-if="chooseCourseList.length > 0" class="order-pay-container">
      <div class="title top">
        <div class="title-content">支付信息</div>
      </div>
      <div class="dept-input">
        <div class="inline-block form-title">
          <span class="un-important f14 normal need-input">经办校区：</span>
        </div>
        <el-select
          v-model="orderForm.handleDepartId"
          placeholder="请选择经办校区"
          size="small"
          filterable
          default-first-option
          :loading="loadingSelect"
        >
          <el-option
            v-for="item in campusOptions"
            :key="item.id"
            :label="item.label"
            :value="item.id"
          />
        </el-select>
      </div>
      <el-card shadow="hover" class="order-info compact">
        <el-row>
          <el-col :span="12" class="item">
            <el-col :span="12" class="fee-info">
              <div class="un-important align-left f14 black need-input">收款账户</div>
            </el-col>
            <el-col :span="12" class="fee-info">
              <div class="align-right">
                <span class="un-important">收款合计:</span>
                <span class="important normal">{{ receiptWaySumMoney.toFixed(2) }}</span>
                <span class="un-important">元</span>
              </div>
            </el-col>
            <el-col v-for="(item, index) in receiptAccountArray" :key="index" :span="24" style="text-align: left;margin-top: 8px;">
              <div class="inline-block form-title" style="width: 110px;">
                <span class="un-important f14">收款方式/金额：</span>
              </div>
              <div class="inline-block">
                <el-select
                  v-model="receiptAccountArray[index].accountId"
                  filterable
                  placeholder=""
                  default-first-option
                  style="width: 130px"
                  size="small"
                >
                  <el-option
                    v-for="dict in receiptAccountOptions"
                    :key="dict.accountId"
                    :label="dict.accountName"
                    :value="dict.accountId"
                  />
                </el-select>
              </div>
              <div class="inline-block">
                <el-input-number
                  v-model="receiptAccountArray[index].receiptMoney"
                  style="width: 120px;"
                  placeholder="收款金额"
                  controls-position="right"
                  :min="0"
                  size="small"
                />
              </div>
              <div class="inline-block">
                <el-button v-if="index === 0 && receiptAccountArray.length > 0" style="padding: 3px 5px;margin-left: 5px;" type="primary" icon="el-icon-plus" size="mini" @click="handleAddReceiptAccount()" />
                <el-button v-if="index > 0" style="padding: 3px 5px;margin-left: 5px;" type="danger" icon="el-icon-minus" size="mini" @click="handleDeleteReceiptAccount(index)" />
              </div>
            </el-col>
          </el-col>
          <el-col :span="12" class="item">
            <el-col :span="12" class="fee-info">
              <div class="un-important align-left f14 black">当前账户余额</div>
            </el-col>
            <el-col :span="12" class="fee-info">
              <div class="align-right">
                <span class="un-important">共:</span>
                <span class="important normal">{{ studentAccountBalance }}</span>
                <span class="un-important">元</span>
              </div>
            </el-col>
            <el-col :span="24" style="text-align: left;margin-top: 8px;">
              <div class="inline-block form-title" style="width: 100px;">
                <span class="un-important f14"><el-checkbox v-model="orderForm.useBalancePay">使用余额：</el-checkbox></span>
              </div>
              <div class="inline-block">
                <el-input-number
                  v-model="orderForm.balancePayValue"
                  :readonly="!orderForm.useBalancePay"
                  :disabled="!orderForm.useBalancePay"
                  :max="studentAccountBalance"
                  :min="0"
                  style="width: 180px"
                  controls-position="right"
                  size="small"
                  placeholder="使用余额支付金额"
                />
              </div>
            </el-col>
          </el-col>
        </el-row>
      </el-card>
    </div>
    <div v-if="chooseCourseList.length > 0" class="order-handle-container">
      <div class="title top">
        <div class="title-content">经办信息</div>
      </div>
      <el-card shadow="hover" class="order-info compact">
        <el-row>
          <el-col :span="24" class="item un-right-border" style="text-align: left;">
            <div class="inline-block mr20 mb20">
              <div class="inline-block form-title">
                <span class="un-important f14 normal">订单标签：</span>
              </div>
              <div class="inline-block">
                <tag-select v-model="orderForm.orderTag" tag-type="2" />
              </div>
            </div>
            <div class="inline-block mr20 mb20">
              <div class="inline-block form-title">
                <span class="un-important f14 normal">销售员工：</span>
              </div>
              <div class="inline-block">
                <staff-select v-model="orderForm.saleStaffId" style="width: 180px" />
              </div>
            </div>
            <div class="inline-block mr20 mb20">
              <div class="inline-block form-title need-input">
                <span class="un-important f14 normal">经办日期：</span>
              </div>
              <div class="inline-block">
                <el-date-picker
                  v-model="orderForm.handleDate"
                  size="small"
                  type="date"
                  style="width: 180px;"
                  value-format="yyyy-MM-dd"
                  placeholder="选择经办日期"
                />
              </div>
            </div>
            <div class="inline-block mr20 mb20">
              <div class="inline-block form-title">
                <span class="un-important f14 normal">办理备注：</span>
              </div>
              <div class="inline-block">
                <el-input
                  v-model="orderForm.memo"
                  size="small"
                  placeholder="办理备注"
                />
              </div>
            </div>
            <div class="inline-block mr20 mb20">
              <div class="inline-block form-title">
                <span class="un-important f14 normal">销售来源：</span>
              </div>
              <div class="inline-block">
                <tag-select v-model="orderForm.saleSourceTag" tag-type="1" />
              </div>
            </div>
          </el-col>
        </el-row>
      </el-card>
    </div>
    <div v-if="chooseCourseList.length > 0" class="order-bottom-container">
      <div class="inline-block">
        <div class="height-half mr15">
          <span class="un-important">原价:</span>
          <span class="un-important f18 through">¥{{ originalTotalFee.toFixed(2) }}</span>
          <span class="un-important">元</span>
        </div>
        <div class="height-half mr15">
          <span class="un-important">减免:</span>
          <span class="un-important">¥{{ (originalTotalFee - actualTotalFee).toFixed(2) }}</span>
          <span class="un-important">元</span>
        </div>
      </div>
      <div class="inline-block">
        <div class="height-half mr15">
          <span class="un-important">实际价格:</span>
          <span class="un-important f18">¥{{ actualTotalFee.toFixed(2) }}</span>
          <span class="un-important">元</span>
          <el-tooltip effect="dark" :content="actualTotalFeeMemo" placement="right">
            <svg-icon icon-class="question" style="height: 15px;width: 15px;top: 17.5px;margin-left: 5px;" />
          </el-tooltip>
        </div>
        <div class="height-half mr15">
          <span class="un-important">余额支付:</span>
          <span class="un-important">¥{{ orderForm.balancePayValue.toFixed(2) }}</span>
          <span class="un-important">元</span>
        </div>
      </div>
      <div class="inline-block">
        <div class="height-half mr15">
          <span class="un-important">应收:</span>
          <span class="important f18">¥{{ needReceiptFee.toFixed(2) }}</span>
          <span class="un-important">元</span>
          <el-tooltip effect="dark" :content="needReceiptFeeMemo" placement="right">
            <svg-icon icon-class="question" style="height: 15px;width: 15px;top: 17.5px;margin-left: 5px;" />
          </el-tooltip>
        </div>
        <div class="height-half mr15">
          <span class="un-important">实收:</span>
          <span class="important blue f18">¥{{ receiptWaySumMoney.toFixed(2) }}</span>
          <span class="un-important">元</span>
          <el-tooltip effect="dark" :content="receiptFeeMemo" placement="right">
            <svg-icon icon-class="question" style="height: 15px;width: 15px;top: 17.5px;margin-left: 5px;" />
          </el-tooltip>
        </div>
      </div>
      <div class="inline-block" style="float: right;padding-top: 6px;padding-right: 10px;">
        <el-button :loading="loading" type="primary" @click="submitOrder">确认办理</el-button>
      </div>
      <div v-if="receiptWaySumMoney > needReceiptFee" class="inline-block" style="float: right;padding-top: 6px;padding-right: 30px;line-height: 36px;">
        <span class="un-important">账户余额增加:</span>
        <span class="important f18 green">¥{{ (receiptWaySumMoney - needReceiptFee).toFixed(2) }}</span>
        <span class="un-important">元</span>
      </div>
      <div v-if="receiptWaySumMoney < needReceiptFee" class="inline-block" style="float: right;padding-top: 6px;padding-right: 30px;line-height: 36px;">
        <span class="un-important">欠费:</span>
        <span class="important f18 red">¥{{ (needReceiptFee - receiptWaySumMoney).toFixed(2) }}</span>
        <span class="un-important">元</span>
      </div>
    </div>
    <order-choose-course ref="orderChooseCourse" @chooseComplete="chooseCourseComplete" />
    <change-student ref="changeStudent" />
  </div>
</template>
<script>
import { studentAccountBalance } from '@/api/sc/student/account'
import { studentCourseInfo } from '@/api/sc/student/course'
import { orderCourseDetail } from '@/api/sc/course'
import { signUp } from '@/api/sc/order'
import { select as receiptAccountSelect } from '@/api/system/receipt'
import claSelect from '@/components/sc/course/cla/claSelect'
import orderChooseCourse from '@/components/sc/course/orderChooseCourse'
import staffSelect from '@/components/system/staff/staffSelect'
import tagSelect from '@/components/system/tag/tagSelect'
import moment from 'moment'
import { campusList } from '@/api/system/dept'
import { parseTime } from '@/utils/commonUtils'
import changeStudent from '@/components/sc/student/changeStudent'
import studentSelect from '@/components/sc/student/studentSelect'

export default {
  components: {
    orderChooseCourse,
    claSelect,
    staffSelect,
    tagSelect,
    changeStudent,
    studentSelect
  },
  data() {
    return {
      loading: false,
      loadingSelect: false,
      chooseCourseIdList: [],
      // 已选择课程详情
      chooseCourseList: [],
      orderForm: {
        balancePayValue: 0,
        orderTag: [],
        saleSourceTag: [],
        handleDate: ''
      },
      discountList: [{
        discount: 9.8,
        label: '9.8折'
      }, {
        discount: 9.7,
        label: '9.7折'
      }, {
        discount: 9.6,
        label: '9.6折'
      }, {
        discount: 9.5,
        label: '9.5折'
      }],
      // 学生账户余额
      studentAccountBalance: 0,
      studentCourseInfo: {},
      // 可选支付方式
      receiptAccountOptions: [],
      // 已添加列表
      receiptAccountArray: [],
      // 经办校区
      campusOptions: [],
      // 报读类型
      detailTagOptions: []
    }
  },
  computed: {
    // 收款方式详情
    receiptWayInfo: function() {
      const receiptWayInfo = {}
      this.receiptAccountOptions.forEach(item => {
        receiptWayInfo[item.accountId] = item.accountName
      })
      return receiptWayInfo
    },
    // 资费详情
    chargeInfo: function() {
      const chargeInfo = {}
      this.chooseCourseList.forEach(item => {
        item.courseChargeList.forEach(chargeItem => {
          chargeInfo[chargeItem.chargeId] = {
            totalFee: chargeItem.totalFee,
            chargeType: chargeItem.chargeType,
            count: chargeItem.count,
            dateUnit: chargeItem.dateUnit
          }
        })
      })
      return chargeInfo
    },
    // 原价
    originalTotalFee: function() {
      let totalFee = 0
      this.chooseCourseList.forEach((course, index) => {
        totalFee += this.calcItemFee(course)
      })
      return totalFee
    },
    // 实际价格 折扣后价格
    actualTotalFee: function() {
      let actualTotalFee = 0
      this.chooseCourseList.forEach((course, index) => {
        actualTotalFee = actualTotalFee + this.calcItemFee(course) - this.calcItemReduceFee(course)
      })
      return actualTotalFee
    },
    // 实际价格 说明
    actualTotalFeeMemo: function() {
      return this.originalTotalFee.toFixed(2) + '(原价)-' + (this.originalTotalFee - this.actualTotalFee).toFixed(2) + '(累计优惠)=' + this.actualTotalFee.toFixed(2) + '(应收)'
    },
    // 应收价格 实际价格-余额支付
    needReceiptFee: function() {
      return this.actualTotalFee - this.orderForm.balancePayValue
    },
    // 应收价格 说明
    needReceiptFeeMemo: function() {
      return this.actualTotalFee.toFixed(2) + '(实际价格)-' + this.orderForm.balancePayValue.toFixed(2) + '(余额支付)=' + this.needReceiptFee.toFixed(2) + '(应收)'
    },
    // 实收价格说明
    receiptFeeMemo: function() {
      const memoArray = []
      this.receiptAccountArray.forEach(item => {
        memoArray.push(this.receiptWayInfo[item.accountId] + ':' + item.receiptMoney + '元')
      })
      return memoArray.toString()
    },
    // 单个课程小计
    calcItemActualFee() {
      return function(course) {
        return this.calcItemFee(course) - this.calcItemReduceFee(course)
      }
    },
    // 每个课程选择的 收费模式信息
    calcItemChargeInfo() {
      return function(course) {
        const chargeId = this.orderForm[course.courseId].chargeId
        return this.chargeInfo[chargeId]
      }
    },
    // 单个课程count
    calcItemCount() {
      return function(course) {
        const chargeId = this.orderForm[course.courseId].chargeId
        // 购买数量
        const buyCount = this.orderForm[course.courseId].buyCount || 0

        const chargeType = this.chargeInfo[chargeId].chargeType
        const count = this.chargeInfo[chargeId].count
        if (chargeType === 'hour' || chargeType === 'count') {
          return count * buyCount
        } else {
          const beginDate = this.orderForm[course.courseId].beginDate
          const endDate = this.orderForm[course.courseId].endDate
          if (beginDate !== undefined && endDate !== undefined) {
            return moment(endDate).diff(moment(beginDate), 'days') + 1
          } else {
            return 0
          }
        }
      }
    },
    // 单个课程count 单位
    calcItemCountUnit() {
      return function(course) {
        const chargeId = this.orderForm[course.courseId].chargeId
        const chargeType = this.chargeInfo[chargeId].chargeType
        if (chargeType === 'hour' || chargeType === 'count') {
          return '课时'
        } else {
          return '天'
        }
      }
    },
    // 单个课程金额
    calcItemFee() {
      return function(course) {
        // 购买数量
        const buyCount = this.orderForm[course.courseId].buyCount || 0
        // 已选课程费用信息
        const chargeId = this.orderForm[course.courseId].chargeId
        if (chargeId === undefined) {
          return 0
        }
        const chargeFee = this.chargeInfo[chargeId].totalFee
        // 总额
        const courseTotalFee = chargeFee * buyCount
        return courseTotalFee
      }
    },
    // 单个课程优惠金额
    calcItemReduceFee() {
      return function(course) {
        // 优惠金额
        const openDiscountFee = this.orderForm[course.courseId].openDiscountFee
        const discountFee = this.orderForm[course.courseId].discountFee
        // 折扣
        const openDiscount = this.orderForm[course.courseId].openDiscount
        const discount = this.orderForm[course.courseId].discount
        // 购买数量
        const buyCount = this.orderForm[course.courseId].buyCount || 0
        // 已选课程费用信息
        const chargeId = this.orderForm[course.courseId].chargeId
        if (chargeId === undefined) {
          return 0
        }
        const chargeFee = this.chargeInfo[chargeId].totalFee
        // 总额
        const courseTotalFee = chargeFee * buyCount
        let reduceFee = 0
        if (openDiscount && discount !== undefined) {
          reduceFee = courseTotalFee * (10 - discount) / 10
        }
        if (openDiscountFee && discountFee !== undefined && discountFee !== '') {
          reduceFee = reduceFee + parseFloat(discountFee)
        }
        return reduceFee
      }
    },
    // 各收款账户累计收款金额
    receiptWaySumMoney() {
      let sumMoney = 0
      this.receiptAccountArray.forEach(item => {
        sumMoney += parseFloat(item.receiptMoney)
      })
      return sumMoney
    }
  },
  watch: {
    needReceiptFee(newValue) {
      if (newValue !== undefined && this.receiptAccountArray.length === 1) {
        this.receiptAccountArray[0].receiptMoney = newValue.toFixed(2)
      }
    }
  },
  created() {
    campusList().then(response => {
      this.campusOptions = response.data
      if (this.campusOptions.length === 1) {
        this.orderForm.handleDepartId = this.campusOptions[0].id
      }
    })
    receiptAccountSelect().then(response => {
      this.receiptAccountOptions = response.data
      if (this.receiptAccountOptions.length > 0) {
        this.$set(this.receiptAccountArray, 0, {
          accountId: this.receiptAccountOptions[0].accountId,
          accountName: this.receiptAccountOptions[0].accountName,
          receiptMoney: 0
        })
      }
    })
    this.getDictListByDictType('order_detail_tag').then(response => {
      this.detailTagOptions = response.data
    })
  },
  methods: {
    // 选择学生变更
    handleStudentChange(studentId) {
      if (!studentId) {
        return
      }
      // 获取账户余额
      studentAccountBalance(studentId).then(response => {
        if (response.respCode === '0000') {
          this.studentAccountBalance = response.data
        }
      })
      // 获取学生报课详细信息
      studentCourseInfo(studentId).then(response => {
        if (response.respCode === '0000') {
          this.studentCourseInfo = response.data
        }
      })
      // 切换学生，清空选择课程列表
      this.chooseCourseIdList = []
      this.chooseCourseList = []
      /* // 校验是否允许报读课程
      studentCanSignUpCourse({
        courseIds: this.chooseCourseIdList.toString(),
        studentId: this.orderForm.studentId
      }).then(response => {
        if (response.respCode !== '0000') {
          this.msgError(response.respMsg)
          this.chooseCourseIdList = []
          this.chooseCourseList = []
        }
      })*/
    },
    // 选择课程
    chooseCourse() {
      this.$refs.orderChooseCourse.open = true
      this.$refs.orderChooseCourse.chooseCourseIdList = this.chooseCourseIdList
      this.$refs.orderChooseCourse.initData()
    },
    // 选择课程完毕
    chooseCourseComplete({ chooseCourseIdList, departId }) {
      // 经办日期默认为当天
      this.orderForm.handleDate = parseTime(new Date(), '{y}-{m}-{d}')
      this.chooseCourseIdList = chooseCourseIdList
      this.chooseCourseIdList.forEach((item, index) => {
        if (this.orderForm[item] === undefined) {
          this.$set(this.orderForm, item, {
            openDiscount: false,
            openDiscountFee: false,
            discount: undefined,
            discountFee: undefined,
            buyCount: 1,
            chargeId: undefined,
            // 按时间收费 生效时间
            beginDate: undefined,
            // 非时间收费 失效时间
            expireDate: undefined,
            // 按时间收费 失效时间
            endDate: undefined,
            deptId: departId,
            detailTag: undefined
          })
        }
      })
      this.loadChooseCourseInfo(chooseCourseIdList, departId)
    },
    // 获取已选择课程信息
    loadChooseCourseInfo(coursesIdArray, deptId) {
      orderCourseDetail({
        courseIds: coursesIdArray.toString(),
        deptId: deptId,
        studentId: this.orderForm.studentId
      }).then(response => {
        if (response.respCode === '0000') {
          this.chooseCourseList = response.data
          // 按时间收费 增加watch 计算 结束时间
          this.addEndDateWatch()
          // 自动选择第一个 收费模式；自动选择报读类型
          this.autoChooseFirstCourseCharge()
        } else {
          this.chooseCourseList = []
        }
      })
    },
    // 按时间收费 增加watch 计算 结束时间
    addEndDateWatch() {
      this.chooseCourseList.forEach(course => {
        const courseId = course.courseId
        // 动态增加watch 开始时间变更
        this.$watch('orderForm.' + courseId + '.beginDate', (newVal, oldVal) => {
          this.handleBeginDateChange(newVal, oldVal, course)
        })
        // 动态增加watch 收费模式变更
        this.$watch('orderForm.' + courseId + '.chargeId', (newVal, oldVal) => {
          const chargeId = this.orderForm[courseId].chargeId
          const beginDate = this.orderForm[courseId].beginDate
          if (chargeId !== undefined && this.chargeInfo[chargeId].chargeType === 'date') {
            this.handleBeginDateChange(beginDate, beginDate, course)
          }
        })
        // 动态增加watch 数量变更
        this.$watch('orderForm.' + courseId + '.buyCount', (newVal, oldVal) => {
          const chargeId = this.orderForm[courseId].chargeId
          const beginDate = this.orderForm[courseId].beginDate
          if (chargeId !== undefined && this.chargeInfo[chargeId].chargeType === 'date') {
            this.handleBeginDateChange(beginDate, beginDate, course)
          }
        })
      })
    },
    // 自动选择第一个 收费模式；自动选择报读类型
    autoChooseFirstCourseCharge() {
      this.chooseCourseList.forEach(course => {
        if (course.courseChargeList.length > 0) {
          this.orderForm[course.courseId].chargeId = course.courseChargeList[0].chargeId
        }
        this.orderForm[course.courseId].detailTag = course.continueCourse ? '2' : '1'
      })
    },
    // 删除已选课程
    removeCourseItem(courseId) {
      let courseListIdx = -1
      this.chooseCourseList.forEach((item, idx) => {
        if (courseId === item.courseId) {
          courseListIdx = idx
          return
        }
      })
      const courseIdIdx = this.chooseCourseIdList.indexOf(courseId)
      this.chooseCourseList.splice(courseListIdx, 1)
      this.chooseCourseIdList.splice(courseIdIdx, 1)
    },
    // 按时间收费 生效时间变更
    handleBeginDateChange(newBeginDate, oldBeginDate, course) {
      if (newBeginDate === undefined) {
        return undefined
      }
      const courseId = course.courseId
      // 周期单位
      const dateUnit = this.calcItemChargeInfo(course).dateUnit
      const buyCount = this.orderForm[courseId].buyCount
      if (dateUnit === 'day') {
        // 计算结束日期
        this.orderForm[courseId].endDate = moment(newBeginDate).add(buyCount, 'days').subtract(1, 'days').format('YYYY-MM-DD')
      } else if (dateUnit === 'month') {
        // 计算结束日期
        this.orderForm[courseId].endDate = moment(newBeginDate).add(buyCount, 'months').subtract(1, 'days').format('YYYY-MM-DD')
      } else if (dateUnit === 'season') {
        // 计算结束日期
        this.orderForm[courseId].endDate = moment(newBeginDate).add(buyCount, 'quarters').subtract(1, 'days').format('YYYY-MM-DD')
      } else if (dateUnit === 'year') {
        // 计算结束日期
        this.orderForm[courseId].endDate = moment(newBeginDate).add(buyCount, 'years').subtract(1, 'days').format('YYYY-MM-DD')
      }
    },
    // 增加收费方式
    handleAddReceiptAccount() {
      this.$set(this.receiptAccountArray, this.receiptAccountArray.length, {
        accountId: this.receiptAccountOptions[0].accountId,
        accountName: this.receiptAccountOptions[0].accountName,
        receiptMoney: 0
      })
    },
    // 删除收费方式
    handleDeleteReceiptAccount(index) {
      this.receiptAccountArray.splice(index, 1)
    },
    // 确认办理
    submitOrder() {
      const reqParam = {
        studentId: this.orderForm.studentId,
        handleDepartId: this.orderForm.handleDepartId,
        useBalancePay: this.orderForm.useBalancePay,
        balancePayValue: this.orderForm.balancePayValue,
        orderTag: this.orderForm.orderTag,
        saleStaffId: this.orderForm.saleStaffId,
        handleDate: this.orderForm.handleDate,
        memo: this.orderForm.memo,
        saleSourceTag: this.orderForm.saleSourceTag,
        receiptWaySumMoney: this.receiptWaySumMoney,
        needReceiptFee: this.needReceiptFee,
        signUpItemList: [],
        signUpReceiptList: this.receiptAccountArray
      }
      this.chooseCourseIdList.forEach(courseId => {
        const chargeId = this.orderForm[courseId].chargeId
        reqParam.signUpItemList.push({
          courseId: courseId,
          claId: this.orderForm[courseId].claId,
          departId: this.orderForm[courseId].deptId,
          chargeId: this.orderForm[courseId].chargeId,
          chargeType: this.chargeInfo[chargeId].chargeType,
          buyCount: this.orderForm[courseId].buyCount,
          openExpire: this.orderForm[courseId].openExpire,
          expireDate: this.orderForm[courseId].expireDate,
          beginDate: this.orderForm[courseId].beginDate,
          endDate: this.orderForm[courseId].endDate,
          openDiscount: this.orderForm[courseId].openDiscount,
          openDiscountFee: this.orderForm[courseId].openDiscountFee,
          discount: this.orderForm[courseId].discount,
          discountFee: this.orderForm[courseId].discountFee,
          insideMemo: this.orderForm[courseId].insideMemo,
          outsideMemo: this.orderForm[courseId].outsideMemo,
          detailTag: this.orderForm[courseId].detailTag
        })
      })
      for (let i = 0; i < reqParam.signUpItemList.length; i++) {
        const item = reqParam.signUpItemList[i]
        if (item.courseId === undefined) {
          this.msgError('第' + (i + 1) + '个报读课程无课程Id')
          return
        } else if (item.chargeId === undefined) {
          this.msgError('第' + (i + 1) + '个报读课程未选择收费方式')
          return
        } else if (item.buyCount === undefined) {
          this.msgError('第' + (i + 1) + '个报读课程未填写购买数量')
          return
        } else if (item.openExpire === true && (item.expireDate === undefined || item.expireDate === '')) {
          this.msgError('第' + (i + 1) + '个报读课程未填写有效期')
          return
        } else if (item.chargeType === 'date' && (item.beginDate === undefined || item.beginDate === '')) {
          this.msgError('第' + (i + 1) + '个报读课程未填写生失效时间')
          return
        } else if (item.openDiscount === true && (item.discount === undefined || item.discount === '')) {
          this.msgError('第' + (i + 1) + '个报读课程开启学费折扣,但未填写折扣')
          return
        } else if (item.openDiscountFee === true && (item.discountFee === undefined || item.discountFee === '')) {
          this.msgError('第' + (i + 1) + '个报读课程开启学费优惠,但未填写优惠金额')
          return
        } else if (item.detailTag === undefined || item.detailTag === '') {
          this.msgError('第' + (i + 1) + '个报读课程开未选择类型(新报、续报、扩科)')
          return
        }
      }
      if (reqParam.studentId === undefined) {
        this.msgError('请选择学生')
        return
      } else if (reqParam.handleDepartId === undefined) {
        this.msgError('请选择经办校区')
        return
      } else if (reqParam.useBalancePay === true && (reqParam.balancePayValue === undefined || reqParam.balancePayValue === '')) {
        this.msgError('请填写使用余额支付金额')
        return
      } else if (reqParam.handleDate === undefined) {
        this.msgError('请填写经办日期')
        return
      } else if (reqParam.signUpItemList.length === 0) {
        this.msgError('请选择报读课程')
        return
      }
      this.loading = true
      signUp(reqParam).then(response => {
        this.loading = false
        if (response.respCode === '0000') {
          this.msgSuccess('报读成功')
          this.$router.push({
            path: '/order/handle/index'
          })
        } else {
          this.msgError(response.respMsg)
        }
      }).catch(() => {
        this.loading = false
      })
    },
    // 新建学员信息
    handleAddStudent() {
      this.$refs.changeStudent.handleAdd()
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss">
  @import "src/styles/sc/order/signUp.scss";
  .un-important.f14 {
    .el-checkbox__label {
      font-weight: normal;
      color: #98a0a7;
      font-size: 14px;
    }
  }
</style>
