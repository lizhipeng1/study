<template>
  <el-card>
    <el-tabs v-model="activeName">
      <el-tab-pane label="基本信息" name="basic">
        <basic-info-form ref="basicInfo" :info="tableInfo" />
      </el-tab-pane>
      <el-tab-pane label="字段信息" name="cloum">
        <el-table :data="tableColumnList" :max-height="tableHeight" :height="tableHeight">
          <el-table-column label="序号" type="index" min-width="5%" />
          <el-table-column
            label="字段列名"
            prop="columnName"
            min-width="10%"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="字段描述"
            prop="columnComment"
            min-width="10%"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="物理类型"
            prop="columnType"
            min-width="10%"
            :show-overflow-tooltip="true"
          />
          <el-table-column label="Java类型" min-width="11%">
            <template slot-scope="scope">
              <el-select v-model="scope.row.javaType">
                <el-option label="Long" value="Long" />
                <el-option label="String" value="String" />
                <el-option label="Integer" value="Integer" />
                <el-option label="Double" value="Double" />
                <el-option label="BigDecimal" value="BigDecimal" />
                <el-option label="Date" value="Date" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="java属性" min-width="10%">
            <template slot-scope="scope">
              <el-input v-model="scope.row.javaField" />
            </template>
          </el-table-column>

          <el-table-column label="插入" min-width="5%">
            <template slot-scope="scope">
              <el-checkbox v-model="scope.row.isInsert" true-label="1" false-label="0" />
            </template>
          </el-table-column>
          <el-table-column label="编辑" min-width="5%">
            <template slot-scope="scope">
              <el-checkbox v-model="scope.row.isEdit" true-label="1" false-label="0" />
            </template>
          </el-table-column>
          <el-table-column label="列表" min-width="5%">
            <template slot-scope="scope">
              <el-checkbox v-model="scope.row.isList" true-label="1" false-label="0" />
            </template>
          </el-table-column>
          <el-table-column label="查询" min-width="5%">
            <template slot-scope="scope">
              <el-checkbox v-model="scope.row.isQuery" true-label="1" false-label="0" />
            </template>
          </el-table-column>
          <el-table-column label="查询方式" min-width="10%">
            <template slot-scope="scope">
              <el-select v-model="scope.row.queryType">
                <el-option label="=" value="EQ" />
                <el-option label="!=" value="NE" />
                <el-option label=">" value="GT" />
                <el-option label=">=" value="GTE" />
                <el-option label="<" value="LT" />
                <el-option label="<=" value="LTE" />
                <el-option label="LIKE" value="LIKE" />
                <el-option label="BETWEEN" value="BETWEEN" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="必填" min-width="5%">
            <template slot-scope="scope">
              <el-checkbox v-model="scope.row.isRequired" true-label="1" false-label="0" />
            </template>
          </el-table-column>
          <el-table-column label="显示类型" min-width="12%">
            <template slot-scope="scope">
              <el-select v-model="scope.row.htmlType">
                <el-option label="文本框" value="input" />
                <el-option label="数字" value="number" />
                <el-option label="文本域" value="textarea" />
                <el-option label="下拉框" value="select" />
                <el-option label="单选框" value="radio" />
                <el-option label="复选框" value="checkbox" />
                <el-option label="日期控件" value="datetime" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="字典类型" min-width="12%">
            <template slot-scope="scope">
              <el-select v-model="scope.row.dictType" clearable filterable placeholder="请选择">
                <el-option
                  v-for="dict in dictOptions"
                  :key="dict.dictType"
                  :label="dict.dictName"
                  :value="dict.dictType"
                >
                  <span style="float: left">{{ dict.dictName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ dict.dictType }}</span>
                </el-option>
              </el-select>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="生成信息" name="genInfo">
        <gen-info-form ref="genInfo" :info="tableInfo" :column-list="tableColumnList" />
      </el-tab-pane>
    </el-tabs>
    <el-form label-width="100px">
      <el-form-item style="text-align: center;margin-left:-100px;margin-top:10px;">
        <el-button type="primary" @click="submitForm()">提交</el-button>
        <el-button @click="close()">返回</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
import { getGenTable, updateGenTable } from '@/api/tool/gen'
import { optionSelect as getDictOptionSelect } from '@/api/system/dict/type'
import basicInfoForm from './basicInfoForm'
import genInfoForm from './genInfoForm'
export default {
  name: 'GenEdit',
  components: {
    basicInfoForm,
    genInfoForm
  },
  data() {
    return {
      // 选中选项卡的 name
      activeName: 'cloum',
      // 表格的高度
      tableHeight: document.documentElement.scrollHeight - 252 + 'px',
      // 表列信息
      tableColumnList: [],
      // 字典信息
      dictOptions: [],
      // 表详细信息
      tableInfo: {}
    }
  },
  beforeCreate() {
    const { tableId } = this.$route.query
    if (tableId) {
      // 获取表详细信息
      getGenTable(tableId).then(res => {
        this.tableColumnList = res.data.tableColumnList
        this.tableInfo = res.data.tableInfo
      })
      /** 查询字典下拉列表 */
      getDictOptionSelect().then(response => {
        this.dictOptions = response.data
      })
    }
  },
  methods: {
    /** 提交按钮 */
    submitForm() {
      const basicForm = this.$refs.basicInfo.$refs.basicInfoForm
      const genForm = this.$refs.genInfo.$refs.genInfoForm
      Promise.all([basicForm, genForm].map(this.getFormPromise)).then(res => {
        const validateResult = res.every(item => !!item)
        if (validateResult) {
          const genTable = Object.assign({}, basicForm.model, genForm.model)
          genTable.columns = this.tableColumnList
          genTable.params = {
            treeCode: genTable.treeCode,
            treeName: genTable.treeName,
            treeParentCode: genTable.treeParentCode
          }
          updateGenTable(genTable).then(res => {
            this.msgSuccess(res.respMsg)
            if (res.respCode === '0000') {
              this.close()
            }
          })
        } else {
          this.msgError('表单校验未通过，请重新检查提交内容')
        }
      })
    },
    getFormPromise(form) {
      return new Promise(resolve => {
        form.validate(res => {
          resolve(res)
        })
      })
    },
    /** 关闭按钮 */
    close() {
      this.$store.dispatch('tagsView/delView', this.$route)
      this.$router.push({ path: '/tool/gen/index', query: { t: Date.now() }})
    }
  }
}
</script>
