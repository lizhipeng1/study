<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogVisible"
  >
    <div style="text-align: center;">
      <img :src="imgSrc" width="200px" alt="">
    </div>
  </el-dialog>
</template>
<script>
export default {
  data() {
    return {
      dialogVisible: false,
      imgSrc: '',
      title: '图片'
    }
  },
  methods: {
    showDialog(imgSrc) {
      this.imgSrc = imgSrc
      this.dialogVisible = true
    }
  }
}
</script>
