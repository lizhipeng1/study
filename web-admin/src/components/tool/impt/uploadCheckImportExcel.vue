<template>
  <div>
    <el-dialog :title="title" :visible.sync="open" width="700px">
      <el-steps :active="activeSteps" align-center finish-status="success" :process-status="stepsProcessStatus">
        <el-step title="上传导入文件" description="请严格按照模板填写" />
        <el-step title="核对数据" description="核对校验成功数据;查看校验失败原因" />
        <el-step title="入库完成导入" description="入库后实际生效" />
      </el-steps>
      <div v-if="activeSteps === 0" class="content">
        <div class="upload-container center">
          <el-upload
            ref="upload"
            drag
            :action="uploadUrl"
            :data="uploadExtendData"
            :headers="uploadHeaders"
            :accept="accept"
            :before-upload="beforeUpload"
            :on-success="uploadSuccess"
            :on-error="uploadError"
          >
            <i class="el-icon-upload" />
            <div class="el-upload__text">点击上传导入文件</div>
            <div slot="tip" class="el-upload__tip">请严格按照导入模板填写信息,否则将无法顺利完成导入</div>
          </el-upload>
        </div>
        <div class="download-template">点击
          <el-link type="primary" @click="downloadImportTemplate">下载导入模板</el-link>
        </div>
        <el-divider />
        <div class="node-title">注意事项</div>
        <div class="node-item">1. 导入模板中红色为必填项</div>
        <div class="node-item">2. 如无法完成导入,请认真核查文件内容</div>
        <div class="node-item">3. 如导导入速度不理想,可适当减少单次导入数据,以便加快导入速度</div>
      </div>
      <div v-if="activeSteps === 1" class="content">
        <el-tabs v-model="activeTab" tab-position="top">
          <el-tab-pane name="successTable" label="校验成功列表">
            <span slot="label" style="font-size: 16px;"><i class="el-icon-finished" /> 校验成功({{ successList.length }}条)</span>
            <slot name="successTable" :data="successList" />
          </el-tab-pane>
          <el-tab-pane name="failTable" label="校验失败列表">
            <span slot="label" style="font-size: 16px;"><i class="el-icon-refresh" /> 校验失败({{ failList.length }}条)</span>
            <slot name="failTable" :data="failList" />
          </el-tab-pane>
        </el-tabs>
      </div>
      <div v-if="activeSteps === 2" class="content">
        <el-row v-loading="loading">
          <el-col v-if="!importComplete && loading" :span="24">
            导入中
          </el-col>
          <el-col v-else-if="!importComplete && !loading" :span="24">
            导入失败 {{ resultMsg }}
          </el-col>
          <el-col v-else-if="importComplete && !loading" :span="24">
            导入成功
          </el-col>
        </el-row>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open=false">关闭</el-button>
        <el-button v-if="activeSteps !== 0 && !importComplete" type="primary" @click="preStep">上一步</el-button>
        <el-button v-if="activeSteps === 1 && successList.length > 0 && !importComplete" type="primary" @click="nextStep">下一步</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { downImportTemplate, uploadForImportDataByFileId } from '@/api/tool/impt'
import { getToken } from '@/utils/auth'

export default {
  props: {
    // 标题
    title: {
      type: String,
      default: '批量导入'
    },
    // 批量导入模板文件名
    importTemplateName: {
      type: String,
      default: ''
    },
    // 下载导入模板名称
    downloadTemplateName: {
      type: String,
      default: '导入模板'
    }
  },
  data() {
    return {
      open: false,
      loading: false,
      notifyInstance: null,
      accept: '.xlsx',
      // 上传时的额外参数
      uploadExtendData: {},
      // 上传时的请求头
      uploadHeaders: {},
      // 当前步骤
      activeSteps: 0,
      // 当前步骤状态
      stepsProcessStatus: 'process',
      activeTab: 'successTable',
      // 校验成功
      successList: [],
      // 校验成功
      failList: [],
      // 待导入fileId
      fileId: undefined,
      importComplete: false,
      resultMsg: undefined
    }
  },
  computed: {
    uploadUrl() {
      return process.env.VUE_APP_BASE_API + '/tool/import/uploadForCheckData'
    }
  },
  watch: {
    open(newValue, oldValue) {
      if (newValue) {
        this.uploadExtendData = {
          templateName: this.importTemplateName
        }
        this.uploadHeaders = {
          'Authorization': 'Bearer ' + getToken()
        }
      }
    }
  },
  methods: {
    // 下载导入模板
    downloadImportTemplate() {
      this.notifyInstance = this.$notify.info({
        title: '提示',
        message: '导入模板下载中,请稍后',
        duration: 0
      })
      downImportTemplate({
        templateName: this.importTemplateName,
        logicFileName: this.downloadTemplateName
      }).then(response => {
        const that = this
        const content = response.data
        if ('download' in document.createElement('a')) {
          // 非IE下载
          const elink = document.createElement('a')
          elink.download = this.downloadTemplateName + '.xlsx'
          elink.style.display = 'none'
          elink.href = URL.createObjectURL(content)
          document.body.appendChild(elink)
          elink.click()
          URL.revokeObjectURL(elink.href) // 释放URL 对象
          document.body.removeChild(elink)
        } else {
          // IE10+下载
          navigator.msSaveBlob(content)
        }
        setTimeout(function() {
          that.notifyInstance.close()
        }, 2000)
      })
    },
    beforeUpload(file) {
      // 如果文件大于2M，不允许上传
      if (file.size > 1024 * 1024 * 2) {
        this.$notify.error('导入文件需小于2M')
      }
      return true
    },
    // 上传成功
    uploadSuccess(response, file, fileList) {
      if (response.respCode === '0000') {
        this.$notify.success('上传成功')
        this.successList = response.data.successList
        this.failList = response.data.failList
        this.fileId = response.data.fileId
        this.activeSteps = 1
      } else {
        this.$notify.error('操作失败,' + response.respMsg)
      }
    },
    // 上传失败
    uploadError(err, file, fileList) {
      this.$notify.error('上传失败,' + err)
      console.log(err)
    },
    nextStep() {
      this.activeSteps++
      if (this.activeSteps === 2) {
        // 导入数据
        this.loading = true
        uploadForImportDataByFileId({
          templateName: this.importTemplateName,
          fileId: this.fileId
        }).then(response => {
          this.loading = false
          if (response.respCode === '0000') {
            this.importComplete = true
            this.activeSteps++
            this.$notify.success('操作成功,' + response.data)
            this.resultMsg = response.respMsg
          } else {
            this.$notify.error('操作失败,' + response.respMsg)
            this.resultMsg = response.respMsg
          }
        }).catch((error) => {
          this.loading = false
          this.resultMsg = error
        })
      }
    },
    preStep() {
      this.activeSteps--
    },
    openImport() {
      this.successList = []
      this.failList = []
      this.fileId = undefined
      this.importComplete = false
      this.resultMsg = undefined
      this.activeSteps = 0
      this.open = true
      this.$nextTick(() => {
        this.$refs.upload.clearFiles()
      })
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
  .content {
    margin-top: 8px;
  }
  .download-template {
    text-align: center;
    padding: 10px;
  }

  .node-title {
    text-align: center;
    font-weight: 500;
    padding-bottom: 10px;
  }

  .node-item {
    text-indent: 2em;
    font-size: 13px;
    line-height: 1.5;
    color: #666;
  }
</style>
<style rel="stylesheet" lang="scss">
  // refine element ui upload
  .upload-container.center {
    text-align: center;

    .el-upload {
      width: 300px;
      text-align: center;

      .el-upload-dragger {
        width: 300px;
        height: 160px;
        margin: auto;
      }
    }
  }
</style>
