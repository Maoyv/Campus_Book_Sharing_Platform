<template>
  <div>
    <div style="margin: 35px 100px;">
      <div style="float: left">
        <span style="font-size: 36px" >书库</span>
      </div>
      <el-input  v-model="ebookName" clearable placeholder="请输入电子书名称" maxlength="20" style="width: 170px;margin-left: 870px;"/>
      <el-select v-model="ebookCategory" filterable clearable style="width: 170px;margin-left: 15px" placeholder="请选择电子书分类">
        <el-option
          v-for="item in this.bookCategory"
          :key="item.bookCategory"
          :label="item.bookCategory"
          :value="item.bookCategory"
        />
      </el-select>
      <el-button type="success" style="margin-left: 15px" @click="selectEbookLibrary">查询</el-button>
      <el-button style="margin-left: 15px" @click="resetSelectEbook">重置</el-button>
    </div>

    <div style="width: 1600px; top: 0;bottom: 0;left: 0;right: 0;margin: 35px auto auto;">
      <!--管理员登录表单-->
      <el-table v-if="adminEbookForm" :data="ebooks.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="659px" border style="width: 100%">
        <el-table-column align="center" fixed prop="ebookId" sortable label="电子书ID"/>
        <el-table-column align="center" fixed prop="ebookName" sortable label="电子名称"/>
        <el-table-column align="center" fixed prop="ebookCategory" sortable label="电子书分类"/>
        <el-table-column align="center" fixed prop="collectionTimes" sortable label="收藏次数"/>
        <el-table-column align="center" fixed prop="downloadTimes" sortable label="下载次数"/>
        <el-table-column align="center" fixed prop="downloadUserName" sortable label="上传者"/>
        <el-table-column align="center" fixed prop="ebookSize" sortable label="电子书大小"/>
        <el-table-column align="center" fixed="right" label="操作" width="300px">
          <template slot-scope="scope">
            <!--传入的scope.row参数为当前行的数据-->
            <el-button  type="text" size="small" @click="openIntroduction(scope.row)">简介</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button   type="text" size="small" @click="collection(scope.row)">收藏</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button   type="text" size="small" @click="openEbookContent(scope.row)">阅读</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button   type="text" size="small" @click="download(scope.row)">下载</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button   type="text" size="small" @click="update(scope.row)">修改</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--用户登录表单-->
      <el-table v-if="userEbookForm" :data="ebooks.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="659px" border style="width: 100%">
        <el-table-column align="center" fixed prop="ebookId" sortable label="电子书ID"/>
        <el-table-column align="center" fixed prop="ebookName" sortable label="电子名称"/>
        <el-table-column align="center" fixed prop="ebookCategory" sortable label="电子书分类"/>
        <el-table-column align="center" fixed prop="collectionTimes" sortable label="收藏次数"/>
        <el-table-column align="center" fixed prop="downloadTimes" sortable label="下载次数"/>
        <el-table-column align="center" fixed prop="downloadUserName" sortable label="上传者"/>
        <el-table-column align="center" fixed prop="ebookSize" sortable label="电子书大小"/>
        <el-table-column align="center" fixed="right" label="操作" width="240px">
          <template slot-scope="scope">
            <!--传入的scope.row参数为当前行的数据-->
            <el-button  type="text" size="small" @click="openIntroduction(scope.row)">简介</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button   type="text" size="small" @click="collection(scope.row)">收藏</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button   type="text" size="small" @click="openEbookContent(scope.row)">阅读</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button   type="text" size="small" @click="download(scope.row)">下载</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--游客登录表单-->
      <el-table v-if="touristsEbookForm" :data="ebooks.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="659px" border style="width: 100%">
        <el-table-column align="center" fixed prop="ebookId" sortable label="电子书ID"/>
        <el-table-column align="center" fixed prop="ebookName" sortable label="电子名称"/>
        <el-table-column align="center" fixed prop="ebookCategory" sortable label="电子书分类"/>
        <el-table-column align="center" fixed prop="collectionTimes" sortable label="收藏次数"/>
        <el-table-column align="center" fixed prop="downloadTimes" sortable label="下载次数"/>
        <el-table-column align="center" fixed prop="downloadUserName" sortable label="上传者"/>
        <el-table-column align="center" fixed prop="ebookSize" sortable label="电子书大小"/>
        <el-table-column align="center" fixed="right" label="操作" width="200px">
          <template slot-scope="scope">
            <!--传入的scope.row参数为当前行的数据-->
            <el-button  type="text" size="small" @click="openIntroduction(scope.row)">简介</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button   type="text" size="small" @click="openEbookContent(scope.row)">阅读</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button   type="text" size="small" @click="download(scope.row)">下载</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="float: right">
        <el-pagination background
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="currentPage"
                       :page-sizes="[10,15,20]"
                       :page-size="pageSize"
                       layout="total,jumper,prev, pager, next,sizes"
                       :total="ebooks.length" >
        </el-pagination>
      </div>
    </div>

    <!--简介弹窗-->
    <el-dialog :title="ebookBriefInformationDialogTitle" width="50%" :visible.sync="ebookBriefInformationDialog">
      <div style="margin: 10px">
        <span style="font-size: 24px">简介:</span>
        <span style="font-size: 18px">{{this.ebookBriefInformation}}</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="ebookBriefInformationDialog = false">关闭</el-button>
      </span>
    </el-dialog>

    <!--下载-->
    <el-dialog title="提示" width="50%" :visible.sync="downloadDialog">
      <div style="margin: 10px">
        <span style="font-size: 24px">即将执行下载电子书《{{ebookNames}}》审核的操作，请确定是否执行</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="downloads">确定</el-button>
        <el-button @click="downloadDialog = false">关闭</el-button>
      </span>
    </el-dialog>

    <!--收藏-->
    <el-dialog title="提示" width="50%" :visible.sync="collectionDialog">
      <div style="margin: 10px">
        <span style="font-size: 24px">即将收藏电子书《{{ebookNames}}》的操作，请确定是否执行</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="collections">确定</el-button>
        <el-button @click="collectionDialog = false">关闭</el-button>
      </span>
    </el-dialog>

    <!--修改-->
    <el-dialog title="修改电子书" width="50%" :visible.sync="updateDialog" @cancel="cancelUpdate" @close="cancelUpdate">

      <el-select v-model="ebooksCategory" filterable clearable style="width: 90%;margin-left: 40px" placeholder="请选择电子书分类">
        <el-option
          v-for="item in this.bookCategory"
          :key="item.bookCategory"
          :label="item.bookCategory"
          :value="item.bookCategory"
        />
      </el-select>

      <el-input v-model="ebookBriefInformation" maxlength="60" show-word-limit type="textarea" clearable placeholder="请输入电子书简介" style="width:90%;margin-left: 40px;margin-top: 20px" />

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updates">确定</el-button>
        <el-button @click="cancelUpdate">关闭</el-button>
      </span>
    </el-dialog>

    <!--删除-->
    <el-dialog title="提示" width="50%" :visible.sync="deleteDialog">
      <div style="margin: 10px">
        <span style="font-size: 24px">即将删除电子书《{{ebookNames}}》的操作，请确定是否执行</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="deletes">确定</el-button>
        <el-button @click="deleteDialog = false">关闭</el-button>
      </span>
    </el-dialog>

    <!--阅读-->
    <el-dialog :title="ebookContentDialogTitle" width="50%" :visible.sync="ebookContentDialog" @close="cancel" @cancel="cancel">
      <div style="text-align: center">
        <textarea disabled style="resize:none;border:0;border-radius:5px;background-color:rgba(241,241,241,.98);width: 100%;height: 450px;font-size: 24px">{{this.ebookContent}}</textarea>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button :disabled="this.readPage === 1" @click="upper" style="margin-right: 15px" type="primary" >上一页</el-button>
        <span style="font-size: 22px">第 </span>
        <el-input-number  v-model="readPages" :min=1 :max=2147483646 style="width: 5%" :controls="false" filterable clearable/>
        <span style="font-size: 22px;margin-right: 15px"> 页</span>
        <el-button @click="jump" style="margin-right: 15px" type="success" >跳转</el-button>
        <el-button @click="lower" type="primary" style="margin-right: 15px">下一页</el-button>
        <el-button @click="ebookContentDialog = false" >关闭</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import {downloads} from "@/api/file";
import {libraryQueryAll, libraryCollection, libraryUpdate, libraryDelete, read} from "@/api/ebook";


export default {
  name: 'library',
  computed: {
    ...mapGetters([
      'bookCategory',
      'name',
      'role'
    ])
  },
  data(){
    return {
      // 分页参数
      currentPage: 1,
      pageSize: 10,
      title: '',
      // 电子分类
      ebookCategory: '',
      ebooksCategory: '',
      ebookBriefInformation: '',
      // 电子书名称
      ebookName: '',
      ebookNames: '',
      // 电子书列表
      ebooks: [],
      // 表单变量参数
      ebookBriefInformationDialogTitle:'',
      ebookContentDialogTitle: '',
      // 弹窗控制参数
      ebookBriefInformationDialog: false,
      downloadDialog: false,
      collectionDialog: false,
      updateDialog: false,
      deleteDialog: false,
      ebookContentDialog: false,
      // 按钮控制参数
      adminEbookForm: false,
      userEbookForm: false,
      touristsEbookForm: false,
      // 阅读参数
      readPage: 0,
      readPages: undefined,
      ebookContent: '',
      readState: '审核',
      row: '',
    }
  },
  mounted() {
    this.buttonController()
    this.selectEbookLibrary()
  },
  methods: {
    // 控制变量刷新页面(刷新页面会重载路由，降低用户体验，慎用)
    reload() {
      return this.$store.commit('increment')
    },
    // 翻页
    handleSizeChange: function (size) {
      this.pageSize = size;
    },
    // 翻页
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage;
    },
    // 打开审核阅读页面
    openEbookContent(row) {
      this.row = row
      this.readPage = 1
      this.readPages = 1
      this.ebookContentDialog = true
      this.ebookContentDialogTitle = "电子书审核：" + row.ebookName
      read({
        ebookRead: this.readPage,
        ebookName: row.ebookName,
        readState: this.readState
      }).then(
        async response =>{
          if(response.code === -1){
            this.$message.error(response.message)
          }else {
            this.ebookContent = response.data
          }
        })
    },
    upper(){
      if(this.readPage === 1){
        this.$message.error("已经是首页了，无法跳转")
      }else {
        let a = this.readPage
        a --
        read({
          ebookRead: a,
          ebookName: this.row.ebookName,
          readState: this.readState
        }).then(
          async response =>{
            if(response.code === -1){
              this.$message.error(response.message)
            }else {
              this.ebookContent = response.data
              this.readPage = a
              this.readPages = a
            }
          })
      }
    },
    lower(){
      let a = this.readPage
      a ++
      read({
        ebookRead: a,
        ebookName: this.row.ebookName,
        readState: this.readState
      }).then(
        async response =>{
          if(response.code === -1){
            this.$message.error(response.message)
          }else {
            this.ebookContent = response.data
            this.readPage = a
            this.readPages = a
          }
        })
    },
    jump(){
      if(this.readPage === this.readPages){

      }else {
        read({
          ebookRead: this.readPages,
          ebookName: this.row.ebookName,
          readState: this.readState
        }).then(
          async response =>{
            if(response.code === -1){
              this.$message.error(response.message)
            }else {
              this.ebookContent = response.data
              this.readPage = this.readPages
            }
          })
      }
    },
    cancel(){
      this.ebookContent = ''
    },
    // 按钮控制
    buttonController(){
      if(this.role === '管理员'){
        this.adminEbookForm = true
      }else {
        if(this.role === '用户'){
          this.userEbookForm = true
        }else {
          this.touristsEbookForm = true
        }
      }
    },
    // 条件查询
    selectEbookLibrary() {
      libraryQueryAll({
        ebookCategory: this.ebookCategory,
        ebookName: this.ebookName
      }).then(
        async response => {
          const {data} = response
          this.ebooks = data
        }
      )
    },
    // 重置
    resetSelectEbook() {
      this.ebookCategory = ''
      this.ebookName = ''
      this.selectEbookLibrary()
    },
    // 打开简介
    openIntroduction(row) {
      this.ebookBriefInformationDialog = true
      this.ebookBriefInformation = row.ebookBriefInformation
      this.ebookBriefInformationDialogTitle = "电子书名称：" + row.ebookName
    },
    // 按钮——下载
    download(row){
      this.ebookNames = row.ebookName
      this.downloadDialog = true
    },
    // 事件——下载
    downloads(){
      if(this.ebookNames === null || this.ebookNames === undefined){
        this.$message.error('无法下载空文件')
      }else {
        let files = this.ebookNames.split(".")
        if(files[1] === null || files[1] === undefined || files[1] !== "txt"){
          this.$message.error('下载文件类型错误')
        }else {
          downloads({
            fileName: this.ebookNames
          }).then(
            async response => {
              // 接受返回的data 数据
              const {data} = response

              let DownloadLink = document.createElement('a');
              DownloadLink.style = 'display: none'; // 创建一个隐藏的a标签
              DownloadLink.download = this.ebookNames;
              DownloadLink.href = process.env.VUE_APP_BASE_API+'/file/download?fileName=';
              document.body.appendChild(DownloadLink);
              DownloadLink.click(); // 触发a标签的click事件
              document.body.removeChild(DownloadLink);

              this.downloadDialog = false
              this.selectEbookLibrary()
            })
        }
      }
    },
    // 按钮——收藏
    collection(row){
      this.collectionDialog = true
      this.ebookNames = row.ebookName
    },
    // 事件——收藏
    collections(){
      libraryCollection({
        ebookName: this.ebookNames
      }).then(
        async response => {
          this.selectEbookLibrary()
        })
      this.collectionDialog = false
    },
    // 按钮——修改
    update(row){
      this.ebookNames = row.ebookName
      this.updateDialog = true
    },
    // 事件——修改
    updates(){
      if(this.ebookBriefInformation === '' || this.ebookBriefInformation === null || this.ebookBriefInformation === undefined){
        this.$message.error('请先输入电子书简介')
      }else {
        libraryUpdate({
          ebookName: this.ebookNames,
          ebookBriefInformation: this.ebookBriefInformation,
          ebooksCategory: this.ebooksCategory
        }).then(async response => {
          this.updateDialog = false
          this.selectEbookLibrary()
        })
      }
    },
    // 事件——取消修改
    cancelUpdate(){
      this.updateDialog = false
      this.ebookBriefInformation = ''
      this.ebooksCategory = ''
    },
    // 按钮——删除
    libraryDelete(row){
      this.deleteDialog = true
      this.ebookNames = row.ebookName
    },
    // 事件——删除
    deletes(){
      libraryDelete({
        ebookName: this.ebookNames
      }).then(
        async response => {
          this.deleteDialog = false
          this.selectEbookLibrary()
        })
    },
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
