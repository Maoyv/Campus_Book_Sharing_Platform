<template>
  <div>
    <div style="margin: 35px 100px;">
      <div style="float: left">
        <span style="font-size: 36px" >上传列表</span>
      </div>
      <el-button type="primary"  style="margin-left: 15px" @click="insertEbook">上传电子书</el-button>
      <el-input  v-model="ebookName" clearable placeholder="请输入电子书名称" maxlength="20" style="width: 170px;margin-left: 480px;"/>
      <el-select v-model="ebookCategory" filterable clearable style="width: 170px;margin-left: 15px" placeholder="请选择电子书分类">
        <el-option
          v-for="item in this.bookCategory"
          :key="item.bookCategory"
          :label="item.bookCategory"
          :value="item.bookCategory"
        />
      </el-select>
      <el-select v-model="ebookState" filterable clearable style="width: 160px;margin-left: 15px" placeholder="请选择审核状态">
        <el-option
          v-for="item in ebookStates"
          :key="item"
          :label="item"
          :value="item"
        />
      </el-select>
      <el-button type="success" style="margin-left: 15px" @click="selectEbookByConditions">查询</el-button>
      <el-button style="margin-left: 15px" @click="resetSelectEbook">重置</el-button>
    </div>

    <div style="width: 1600px; top: 0;bottom: 0;left: 0;right: 0;margin: 35px auto auto;">
      <!--管理员登录表单-->
      <el-table v-if="adminBookForm" :data="ebooks.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="659px" border style="width: 100%">
        <el-table-column align="center" fixed prop="ebookId" sortable label="电子书ID"/>
        <el-table-column align="center" fixed prop="ebookName" sortable label="电子名称"/>
        <el-table-column align="center" fixed prop="ebookCategory" sortable label="电子书分类"/>
        <el-table-column align="center" fixed prop="ebookSize" sortable label="电子书大小"/>
        <el-table-column align="center" fixed prop="ebookState" sortable label="审核状态"/>
        <el-table-column align="center" fixed="right" label="操作">
          <template slot-scope="scope">
            <!--传入的scope.row参数为当前行的数据-->
            <el-button @click="openIntroduction(scope.row)" type="text" size="small">简介</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button  @click="deleteEbook(scope.row)" type="text" size="small">删除记录</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--用户登录表单-->
      <el-table v-if="userBookForm" :data="ebooks.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="659px" border style="width: 100%">
        <el-table-column align="center" fixed prop="ebookId" sortable label="电子书ID"/>
        <el-table-column align="center" fixed prop="ebookName" sortable label="电子名称"/>
        <el-table-column align="center" fixed prop="ebookCategory" sortable label="电子书分类"/>
        <el-table-column align="center" fixed prop="ebookSize" sortable label="电子书大小"/>
        <el-table-column align="center" fixed prop="ebookState" sortable label="审核状态"/>
        <el-table-column align="center" fixed="right" label="操作">
          <template slot-scope="scope">
            <!--传入的scope.row参数为当前行的数据-->
            <el-button @click="openIntroduction(scope.row)" type="text" size="small">简介</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button v-if="scope.row.ebookState === '审核中'" @click="cancelEbook(scope.row)" type="text" size="small">取消上传</el-button>
            <el-button v-else disabled type="text" size="small">取消上传</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button  @click="deleteEbook(scope.row)" type="text" size="small">删除记录</el-button>
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

    <!--删除电子书信息弹窗-->
    <el-dialog title="提示" width="35%" :visible.sync="deleteEbookDialog">
      <div>
        <span style="font-size: 24px">即将执行取消上传电子书《{{ebookName}}》的操作，请确定是否执行</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="deletesEbook">确定</el-button>
        <el-button @click="deleteEbookDialog = false">取消</el-button>
      </span>
    </el-dialog>

    <!--取消上传-->
    <el-dialog title="提示" width="35%" :visible.sync="cancelEbookDialog">
      <div>
        <span style="font-size: 24px">即将执行删除电子书《{{ebookName}}》的电子书上传记录操作，请确定是否执行</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="cancelsEbook">确定</el-button>
        <el-button @click="cancelEbookDialog = false">取消</el-button>
      </span>
    </el-dialog>

    <!--上传电子书-->
    <el-dialog title="上传电子书" width="50%" :visible.sync="insertEbookDialog" @cancel="cancelInsertEbook" @close="cancelInsertEbook">
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
        style="margin-left: 90px"
      >
        <el-button slot="trigger" size="small"  type="primary">选取文件</el-button>

        <el-select v-model="insertEbookCategory" filterable clearable style="width: 25%;margin-left: 90px;margin-top: 20px"  placeholder="请选择电子书分类">
          <el-option
            v-for="item in this.bookCategory"
            :key="item.bookCategory"
            :label="item.bookCategory"
            :value="item.bookCategory"
          />
        </el-select>
        <span slot="tip" class="el-upload__tip" style="margin-left: 90px;margin-top: 20px">只能上传txt文件，且不超过15MB</span>
      </el-upload>

      <el-input v-model="ebookBriefInformation" maxlength="60" show-word-limit type="textarea" clearable placeholder="请输入电子书简介" style="width:90%;margin-left: 40px;margin-top: 20px" />


      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="insertsEbook">确定</el-button>
        <el-button @click="cancelInsertEbook">取消</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import {uploadListQueryAll, uploadListDelete, uploadListCancel, uploadListInsert} from "@/api/ebook";


export default {
  name: 'uploadList',
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
      // 电子书名称
      ebookName: '',
      // 电子书状态
      ebookState: '',
      ebookStates: [
        '审核中','已驳回','上传成功','已取消'
      ],
      ebookId: '',
      action: '',
      fileList: [],
      ebookBriefInformation: '',
      ebookSize: '',
      insertEbookCategory: '',
      // 电子书列表
      ebooks: [],
      // 按钮控制参数
      adminBookForm: false,
      userBookForm: false,
      // 表单变量参数
      ebookBriefInformationDialogTitle:'',
      // 弹窗控制参数
      ebookBriefInformationDialog: false,
      deleteEbookDialog: false,
      cancelEbookDialog: false,
      insertEbookDialog: false,
    }
  },
  mounted() {
    this.buttonController()
    this.selectEbookByConditions()
    this.onprogress()
  },
  methods:{
    onprogress(){
      this.action = process.env.VUE_APP_BASE_API+"/file/upload"
    },
    // 文件上传成功后的事件
    uploadSuccess(response){
      if(response.code === -1){
        this.$message.error(response.message)
      }else {
        this.ebookSize = response.data.ebookSize
        this.ebookName = response.data.ebookName
        uploadListInsert({
          ebookName: this.ebookName,
          ebookCategory: this.insertEbookCategory,
          ebookSize: this.ebookSize,
          ebookBriefInformation: this.ebookBriefInformation
        }).then(
          async response => {
            const {data} = response
            this.insertEbookDialog = false
            this.selectEbookByConditions()
          }
        )
      }
      this.fileList = []
    },
    // 文件上传失败后的事件
    uploadError(){
      this.$message.error('上传失败')
      this.fileList = []
    },
    // 控制变量刷新页面(刷新页面会重载路由，降低用户体验，慎用)
    reload(){
      return this.$store.commit('increment')
    },
    // 翻页
    handleSizeChange:function(size){
      this.pageSize=size;
    },
    // 翻页
    handleCurrentChange:function(currentPage){
      this.currentPage=currentPage;
    },
    // 按钮控制
    buttonController(){
      if(this.role === '管理员'){
        this.adminBookForm = true
      }else {
        if(this.role === '用户'){
          this.userBookForm = true
        }
      }
    },
    // 条件查询
    selectEbookByConditions(){
      uploadListQueryAll({
        ebookState: this.ebookState,
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
    resetSelectEbook(){
      this.ebookState = ''
      this.ebookCategory = ''
      this.ebookName = ''
      this.selectEbookByConditions()
    },
    // 打开简介
    openIntroduction(row){
      this.ebookBriefInformationDialog = true
      this.ebookBriefInformation = row.ebookBriefInformation
      this.ebookBriefInformationDialogTitle = "电子书名称："+row.bookName
    },
    // 按钮 —— 删除电子书
    deleteEbook(row){
      this.ebookId = row.ebookId
      this.ebookName = row.ebookName
      this.deleteEbookDialog = true
    },
    // 事件 —— 删除电子书
    deletesEbook(){
      uploadListDelete({
        ebookId: this.ebookId
      }).then(
        async response =>{
          const {data} = response
          this.selectEbookByConditions()
          this.deleteEbookDialog = false
        }
      )
    },
    // 按钮 —— 取消上传电子书
    cancelEbook(row){
      this.ebookId = row.ebookId
      this.ebookName = row.ebookName
      this.cancelEbookDialog = true
    },
    // 事件 —— 取消上传电子书
    cancelsEbook(){
      uploadListCancel({
        ebookId: this.ebookId,
        ebookName: this.ebookName,
      }).then(
        async response =>{
          const {data} = response
          this.selectEbookByConditions()
          this.cancelEbookDialog = false
        }
      )
    },
    // 按钮 —— 上传电子书
    insertEbook(){
      this.insertEbookDialog = true
    },
    // 事件 —— 上传电子书
    insertsEbook(){
      if(this.fileList === []){
        this.$message.error('请先输入选取需要上传的电子书')
      }else {
        if(this.ebookBriefInformation === '' || this.ebookBriefInformation === null || this.ebookBriefInformation === undefined){
          this.$message.error('请先输入电子书简介')
        }else {
          this.$refs.upload.submit();
        }
      }
    },
    // 按钮 —— 取消上传电子书弹窗
    cancelInsertEbook(){
      this.fileList = []
      this.ebookName = ''
      this.ebookSize = ''
      this.insertEbookCategory = ''
      this.ebookBriefInformation = ''
      this.insertEbookDialog = false
    }
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
