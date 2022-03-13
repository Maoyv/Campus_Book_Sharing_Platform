<template>
  <div>
    <div style="margin: 35px 100px;">
      <div style="float: left">
        <span style="font-size: 36px" >审核列表</span>
      </div>
      <el-input  v-model="ebookName" clearable placeholder="请输入电子书名称" maxlength="20" style="width: 170px;margin-left: 780px;"/>
      <el-select v-model="ebookCategory" filterable clearable style="width: 170px;margin-left: 15px" placeholder="请选择电子书分类">
        <el-option
          v-for="item in this.bookCategory"
          :key="item.bookCategory"
          :label="item.bookCategory"
          :value="item.bookCategory"
        />
      </el-select>
      <el-button type="success" style="margin-left: 15px" @click="selectEbookByConditions">查询</el-button>
      <el-button style="margin-left: 15px" @click="resetSelectEbook">重置</el-button>
    </div>

    <div style="width: 1600px; top: 0;bottom: 0;left: 0;right: 0;margin: 35px auto auto;">
      <!--登录表单-->
      <el-table  :data="ebooks.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="659px" border style="width: 100%">
        <el-table-column align="center" fixed prop="ebookId" sortable label="电子书ID"/>
        <el-table-column align="center" fixed prop="ebookName" sortable label="电子书名称"/>
        <el-table-column align="center" fixed prop="ebookCategory" sortable label="电子书分类"/>
        <el-table-column align="center" fixed prop="ebookSize" sortable label="电子书大小"/>
        <el-table-column align="center" fixed prop="ebookState" sortable label="审核状态"/>
        <el-table-column align="center" fixed="right" label="操作">
          <template slot-scope="scope">
            <!--传入的scope.row参数为当前行的数据-->
            <el-button @click="openIntroduction(scope.row)" type="text" size="small">简介</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button   type="text" size="small" @click="openEbookContent(scope.row)">审核</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button   type="text" size="small" @click="ebookAdopts(scope.row)">通过</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button   type="text" size="small" @click="ebookRejects(scope.row)">驳回</el-button>
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

    <!--审核列表-上传成功-->
    <el-dialog title="提示" width="50%" :visible.sync="ebookAdopt">
      <div style="margin: 10px">
        <span style="font-size: 24px">即将执行通过电子书《{{ebookNames}}》审核的操作，请确定是否执行</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="ebooksAdopt">确定</el-button>
        <el-button @click="ebookAdopt = false">关闭</el-button>
      </span>
    </el-dialog>

    <!--审核列表-驳回-->
    <el-dialog title="提示" width="50%" :visible.sync="ebookReject">
      <div style="margin: 10px">
        <span style="font-size: 24px">即将执行驳回上传电子书《{{ebookNames}}》上传的操作，请确定是否执行</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="ebooksReject">确定</el-button>
        <el-button @click="ebookReject = false">关闭</el-button>
      </span>
    </el-dialog>

    <!--审核-->
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
import {auditListQueryAll,auditListAdopt,auditListReject,read} from "@/api/ebook";


export default {
  name: 'auditList',
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
      ebookNames: '',
      ebookId: '',
      ebookBriefInformation: '',
      // 电子书列表
      ebooks: [],
      // 表单变量参数
      ebookBriefInformationDialogTitle:'',
      ebookContentDialogTitle: '',
      // 弹窗控制参数
      ebookBriefInformationDialog: false,
      ebookAdopt: false,
      ebookReject: false,
      deleteEbookDialog: false,
      cancelEbookDialog: false,
      insertEbookDialog: false,
      ebookContentDialog: false,
      // 阅读参数
      readPage: 0,
      readPages: undefined,
      ebookContent: '',
      readState: '审核',
      row: '',
    }
  },
  mounted() {
    this.selectEbookByConditions()
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
    // 条件查询
    selectEbookByConditions() {
      auditListQueryAll({
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
      this.selectEbookByConditions()
    },
    // 打开简介
    openIntroduction(row) {
      this.ebookBriefInformationDialog = true
      this.ebookBriefInformation = row.ebookBriefInformation
      this.ebookBriefInformationDialogTitle = "电子书名称：" + row.ebookName
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
        console.log("1")
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
    // 按钮——审核通过
    ebookAdopts(row){
      this.ebookNames = row.ebookName
      this.ebookId = row.ebookId
      this.ebookAdopt = true
    },
    // 按钮——审核通过
    ebooksAdopt(){
      auditListAdopt({
        ebookId: this.ebookId,
        ebookName: this.ebookNames
      }).then(
        async response =>{
          const {data} = response
          this.ebookAdopt = false
          this.selectEbookByConditions()
        })
    },
    // 按钮——审核驳回
    ebookRejects(row){
      this.ebookNames = row.ebookName
      this.ebookId = row.ebookId
      this.ebookReject = true
    },
    // 按钮——审核驳回
    ebooksReject(){
      auditListReject({
        ebookId: this.ebookId,
        ebookName: this.ebookNames
      }).then(
        async response =>{
          const {data} = response
          this.ebookReject = false
          this.selectEbookByConditions()
        })
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
