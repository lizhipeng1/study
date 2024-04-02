<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="场景" prop="scene">
        <el-select v-model="queryParams.scene" placeholder="请选择场景">
          <el-option
            v-for="dict in sceneOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['wechat:cp:contactWay:add']"
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['wechat:cp:contactWay:update']"
          type="primary"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['wechat:cp:contactWay:delete']"
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column align="center" prop="contactWayName" label="名称" show-overflow-tooltip fixed />
      <el-table-column align="center" prop="type" label="单人/多人">
        <template slot-scope="{row}">
          <el-tag v-if="row.type === 1" effect="plain">单人</el-tag>
          <el-tag v-if="row.type === 2" effect="plain" type="success">多人</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="scene" label="添加好友方式">
        <template slot-scope="{row}">
          <el-tag v-if="row.scene === 1" effect="plain">小程序</el-tag>
          <el-tag v-if="row.scene === 2" effect="plain" type="success" @click.native="handleQrcode(row)" style="cursor: pointer;">二维码</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="state" label="添加渠道" />
      <el-table-column align="center" prop="skipVerify" label="无需验证">
        <template slot-scope="{row}">
          <el-tag v-if="row.skipVerify" effect="plain" type="success">是</el-tag>
          <el-tag v-else effect="plain" type="info">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="userNames" label="关联员工" show-overflow-tooltip />
      <el-table-column align="center" prop="party" label="关联部门" />
      <el-table-column align="center" prop="addTags" label="自动添加标签">
        <template slot-scope="{row}">
          <div v-if="row.addTagNames">
            <el-tag v-for="item in row.addTagNames.split(',')" :key="item" effect="plain" size="mini">{{ item }}</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="isTemp" label="临时会话">
        <template slot-scope="{row}">
          <el-tag v-if="row.isTemp" effect="plain" type="success">是</el-tag>
          <el-tag v-else effect="plain" type="info">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column width="150" label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-dropdown trigger="click">
            <span style="cursor: pointer;color: #409EFF;outline: none;">
              操作<i class="el-icon-arrow-down el-icon--right" style="font-size: 12px;" />
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-hasPermi="['wechat:cp:contactWay:update']" icon="el-icon-edit" @click.native="handleUpdate(scope.row)">修改</el-dropdown-item>
              <el-dropdown-item v-hasPermi="['wechat:cp:contactWay:list']" icon="el-icon-picture-outline" @click.native="handleQrcode(scope.row)">获取添加码</el-dropdown-item>
              <el-dropdown-item v-hasPermi="['wechat:cp:contactWay:delete']" icon="el-icon-delete" @click.native="handleDelete(scope.row)">删除</el-dropdown-item>
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

    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px">
      <el-form ref="form" v-loading="loadingChange" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="场景名称:" prop="contactWayName">
              <el-input v-model="form.contactWayName" placeholder="请输入联系方式的名称信息，不超过30个字符" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="state:" prop="state">
              <el-input v-model="form.state" placeholder="请输入企业自定义的state参数，用于区分不同的添加渠道" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="场景:" prop="scene">
              <el-select v-model="form.scene" placeholder="请选择场景">
                <el-option
                  v-for="dict in sceneOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单人/多人:">
              <el-select v-model="form.type" placeholder="请选择联系方式类型">
                <el-option
                  v-for="dict in typeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col v-if="form.scene === '1'" :span="12">
            <el-form-item label="小程序样式:" prop="style">
              <el-select v-model="form.style" placeholder="请选择小程序中控件样式">
                <el-option
                  v-for="dict in styleOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="添加时无需验证:" prop="skipVerify" style="text-align: left">
              <el-radio-group v-model="form.skipVerify">
                <el-radio :label="true">是</el-radio>
                <el-radio :label="false">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="同企业同跟进人:" prop="unionidisExclusive" style="text-align: left">
              <el-radio-group v-model="form.unionidisExclusive">
                <el-radio :label="true">是</el-radio>
                <el-radio :label="false">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="可选用户:" prop="user">
              <cp-user-select v-model="form.users" multiple :multiple-limit="form.type === '2' ? 0 : 1" placeholder="请输入使用该联系方式的用户userID列表，在type为1时为必填，且只能有一个" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="form.type === '2'">
          <el-col :span="12">
            <el-form-item label="可选部门:" prop="party">
              <el-input v-model="form.party" readonly placeholder="暂不支持 请输入使用该联系方式的部门id列表，只在type为2时有效" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="添加标签:" prop="addTag">
              <cp-tag-select v-model="form.addTags" multiple placeholder="请输入需要自动添加的标签id,可多选" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="临时会话:" prop="isTemp" style="text-align: left">
              <el-radio-group v-model="form.isTemp">
                <el-radio :label="true">是</el-radio>
                <el-radio :label="false">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="form.isTemp">
          <el-col :span="12">
            <el-form-item label="二维码有效期:" prop="expiresIn">
              <el-input v-model="form.expiresIn" placeholder="请输入临时会话二维码有效期，以秒为单位。该参数仅在is_temp为true时有效，默认7天，最多为14天" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="会话有效期:" prop="chatExpiresIn">
              <el-input v-model="form.chatExpiresIn" placeholder="请输入临时会话有效期，以秒为单位。该参数仅在is_temp为true时有效，默认为添加好友后24小时，最多为14天" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="限定unionid:" prop="unionid">
              <el-input v-model="form.unionid" placeholder="请输入可进行临时会话的客户unionid，该参数仅在is_temp为true时有效，如不指定则不进行限制" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束语:" prop="conclusions">
              <el-input v-model="form.conclusions" placeholder="请输入结束语，会话结束时自动发送给客户，可参考“结束语定义”，仅在is_temp为true时有效" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="loadingChange" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <dialog-img ref="dialogImg" />
  </div>
</template>

<script>
import { listCpContactWay, getCpContactWay, delCpContactWay, addCpContactWay, updateCpContactWay } from '@/api/wechat/cp/contactWay'
import cpUserSelect from '@/components/wechat/cp/user/cpUserSelect'
import cpTagSelect from '@/components/wechat/cp/tag/cpTagSelect'
import dialogImg from '@/components/system/file/dialogImg'
export default {
  components: {
    cpUserSelect,
    cpTagSelect,
    dialogImg
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
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 联系方式类型,1-单人, 2-多人数据字典
      typeOptions: [],
      // 场景，1-在小程序中联系，2-通过二维码联系
      sceneOptions: [],
      // 在小程序中联系时使用的控件样式
      styleOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        scene: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        contactWayName: [
          { required: true, message: '请填写备注', trigger: 'blur' },
          { max: 30, message: '最多30个字符', trigger: 'blur' }
        ],
        state: [
          { required: true, message: '请填写自定义state参数', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择单人/多人', trigger: 'blur' }
        ],
        scene: [
          { required: true, message: '请选择场景', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    'form.type': {
      handler(newValue) {
        this.loadStyleDict(newValue)
        this.form.users = undefined
      },
      immediate: true
    }
  },
  created() {
    this.getList()
    this.getDictListByDictType('cp_contact_way_type').then(response => {
      this.typeOptions = response.data
    })
    this.getDictListByDictType('cp_contact_way_scene').then(response => {
      this.sceneOptions = response.data
    })
  },
  methods: {
    loadStyleDict(parentValue) {
      if (!parentValue) {
        return
      }
      this.dictTypeDataListByParentValue('cp_contact_way_style', parentValue).then(response => {
        this.styleOptions = response.data
      })
    },
    getList() {
      this.loading = true
      listCpContactWay(this.queryParams).then(response => {
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
        type: '1',
        scene: '2',
        style: undefined,
        contactWayName: undefined,
        skipVerify: true,
        state: undefined,
        users: undefined,
        party: undefined,
        addTags: undefined,
        isTemp: false,
        expiresIn: undefined,
        chatExpiresIn: undefined,
        unionid: undefined,
        unionidisExclusive: true,
        conclusions: undefined
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset()
      this.open = true
      this.title = '添加客户联系我方式'
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.configId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      getCpContactWay(row.configId || this.ids).then(response => {
        this.form = response.data
        this.form.type = response.data.type + ''
        this.form.scene = response.data.scene + ''
        this.open = true
        this.title = '修改客户联系我方式'
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.form.user = undefined
          this.form.addTag = undefined
          this.loadingChange = true
          if (this.form.configId !== undefined) {
            updateCpContactWay(this.form).then(response => {
              this.loadingChange = false
              if (response.respCode === '0000') {
                this.msgSuccess('修改成功')
                this.open = false
                this.getList()
              } else {
                this.msgError(response.respMsg)
              }
            }).catch(() => {
              this.loadingChange = false
            })
          } else {
            addCpContactWay(this.form).then(response => {
              this.loadingChange = false
              if (response.respCode === '0000') {
                this.msgSuccess('新增成功')
                this.open = false
                this.getList()
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
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.configId || this.ids
      this.$confirm('是否确认删除?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delCpContactWay(id)
      }).then((response) => {
        if (response.respCode === '0000') {
          this.getList()
          this.msgSuccess('删除成功')
        } else {
          this.msgError(response.respMsg)
        }
      }).catch(function() {})
    },
    // 获取添加码
    handleQrcode(row) {
      this.$refs.dialogImg.showDialog(row.qrCode)
    }
  }
}
</script>
