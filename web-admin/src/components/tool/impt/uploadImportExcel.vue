<template>
  <div>
    <el-dialog :title="title" :visible.sync="open" width="700px">
      <div class="upload-container center">
        <el-upload
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
      <div slot="footer" class="dialog-footer">
        <el-button @click="open=false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { downImportTemplate } from '@/api/tool/impt'
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
      notifyInstance: null,
      accept: '.xlsx',
      // 上传时的额外参数
      uploadExtendData: {},
      // 上传时的请求头
      uploadHeaders: {}
    }
  },
  computed: {
    uploadUrl() {
      return process.env.VUE_APP_BASE_API + '/tool/import/uploadForImportData'
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
        this.$notify.success('操作成功,' + response.data)
      } else {
        this.$notify.error('操作失败,' + response.respMsg)
      }
    },
    // 上传失败
    uploadError(err, file, fileList) {
      this.$notify.error('上传失败,' + err)
      console.log(err)
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
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
