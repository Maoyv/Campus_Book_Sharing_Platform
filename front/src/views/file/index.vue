<template>
  <div>
    文件操作

    <el-upload
      class="upload-demo"
      ref="upload"
      :action="action"
      :on-success="uploadSuccess"
      :on-error="uploadError"
      :file-list="fileList"
      accept=".txt"
      :show-file-list="false"
      :auto-upload="true"
    >
      <el-button slot="trigger" size="small" type="primary">上传文件</el-button>
      <div slot="tip" class="el-upload__tip">只能上传txt文件，且不超过15Mb</div>
    </el-upload>

    <el-upload
      class="upload-demo"
      ref="upload"
      :action="action"
      :file-list="fileList"
      :on-success="uploadSuccess"
      :on-error="uploadError"
      :limit="1"
      accept=".txt"
      :show-file-list="true"
      :auto-upload="false"
      :multiple="false"
    >
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>

    <div style="margin-left: 200px">
      <el-input v-model="file" style="width: 20%;"></el-input>
      <el-button @click="downloads">下载</el-button>
    </div>

  </div>
</template>

<script>
import {downloads} from "@/api/file";

export default {
  name: "file",
  data(){
    return{
      files:undefined,
      file:undefined,
      fileList:[],
      action:"",
    }
  },
  mounted() {
    this.onprogress()
  },
  methods:{
    onprogress(){
      this.action = process.env.VUE_APP_BASE_API+"/file/upload"
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    uploadSuccess(response){
      if(response.code === -1){
        this.$message.error(response.message)
      }
      this.fileList = []
    },
    uploadError(){
      this.$message.error('上传失败')
      this.fileList = []
    },
    downloads(){
      if(this.file === null || this.file === undefined){
        this.$message.error('无法下载空文件')
      }else {
        let files = this.file.split(".")
        if(files[1] === null || files[1] === undefined || files[1] !== "txt"){
          this.$message.error('下载文件类型错误')
        }else {
          downloads({
            fileName:this.file
          }).then(
            async response => {
              // 接受返回的data 数据
              const {data} = response

              let DownloadLink = document.createElement('a');
              DownloadLink.style = 'display: none'; // 创建一个隐藏的a标签
              DownloadLink.download = this.file;
              DownloadLink.href = process.env.VUE_APP_BASE_API+'/file/download?fileName=';
              document.body.appendChild(DownloadLink);
              DownloadLink.click(); // 触发a标签的click事件
              document.body.removeChild(DownloadLink);

            })
        }
      }
    },
  }
}
</script>

<style scoped>

</style>
