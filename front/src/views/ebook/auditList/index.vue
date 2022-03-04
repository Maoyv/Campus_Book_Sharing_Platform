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
      <!--管理员登录表单-->
      <el-table  :data="ebooks.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="659px" border style="width: 100%">
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
            <el-button   type="text" size="small">审核</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button   type="text" size="small">通过</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button   type="text" size="small">驳回</el-button>
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

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import {auditListQueryAll} from "@/api/ebook";


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
      ebookBriefInformation: '',
      // 电子书列表
      ebooks: [],
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
      this.ebookBriefInformationDialogTitle = "电子书名称：" + row.bookName
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
