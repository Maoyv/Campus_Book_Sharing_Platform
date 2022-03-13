<template>

  <div>
    <div style="margin: 35px 100px;">
      <span style="font-size: 36px;float: left">图书类别管理</span>

      <el-button style="margin-left: 35px" @click="insertBookCategory" type="primary">新增类别</el-button>
    </div>

    <div style="width: 1600px; top: 0;bottom: 0;left: 0;right: 0;margin: 35px auto;">
      <el-table :data="this.bookCategory.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="659px" border style="width: 100%">

        <el-table-column align="center" fixed prop="bookCategory" sortable label="类别"/>

        <el-table-column align="center" fixed="right" label="操作">
          <template slot-scope="scope">
            <!--传入的scope.row参数为当前行的数据-->
            <el-button v-if="scope.row.bookCategory === '无类别'" disabled type="text" size="small">不可操作</el-button>
            <el-button v-if="scope.row.bookCategory !== '无类别'" @click="updateBookCategory(scope.row)" type="text"  size="small">修改</el-button>
            <el-divider v-if="scope.row.bookCategory !== '无类别'" direction="vertical"></el-divider>
            <el-button v-if="scope.row.bookCategory !== '无类别'" @click="deleteBookCategory(scope.row)" type="text" size="small">删除</el-button>
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
                       :total="this.bookCategory.length" >
        </el-pagination>
      </div>
    </div>

    <el-dialog :title="title" width="35%" :visible.sync="insertOrUpdate">
      <el-form>
        <el-form-item label="请输入新的图书类别" label-width="35%">
          <el-input v-model="newBookCategory" maxlength="10" clearable style="width: 90%"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitBookCategory">确定</el-button>
        <el-button @click="insertOrUpdate = false">取消</el-button>
      </span>
    </el-dialog>

    <el-dialog title="提示" width="35%" :visible.sync="deletes">
      <div>
        <span style="font-size: 24px">即将执行删除图书类别"{{bookCategoryName}}"操作，删除该图书类别后，所有该类别图书的图书类别将被修改为无类别，请确定是否执行</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="deletesBookCateGory">确定</el-button>
        <el-button @click="deletes = false">取消</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import {mapGetters} from "vuex";
import {deleteBookCategory, insertBookCategory, updateBookCategory} from "@/api/user";

export default {
  name: "bookCategory",
  computed: {
    ...mapGetters([
      'bookCategory',
    ]),
  },
  // 页面参数
  data(){
    return {
      currentPage: 1,
      pageSize: 10,
      title: '',
      // 新增或修改弹窗控制按钮
      insertOrUpdate: false,
      // 删除提示弹窗控制按钮
      deletes: false,
      // 旧分类，用于存储某条信息点击后它的分类名称
      oldBookCategory: '',
      // 新分类，用于存储输入的分类
      newBookCategory: '',
      bookCategoryName:'',
    }
  },
  // 页面打开时加载
  mounted() {
  },
  // 方法
  methods:{
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
    // 按钮——新增图书类别
    insertBookCategory(){
      this.newBookCategory = ''
      this.title= '新增图书类别'
      this.insertOrUpdate = true
    },
    // 按钮-修改图书类别
    updateBookCategory(s){
      this.oldBookCategory = s.bookCategory
      this.newBookCategory = ''
      this.title= '修改图书类别'
      this.insertOrUpdate = true
    },
    // 按钮-删除图书类别
    deleteBookCategory(s){
      this.bookCategoryName = s.bookCategory
      this.oldBookCategory = s.bookCategory
      this.deletes = true
    },
    // 事件——新增/修改图书类别
    submitBookCategory(){
      if(this.newBookCategory === ''){
        this.$message.error('图书类别不能为空');
      }else {
        let number = this.newBookCategory.split("").length
        if(number > 6 ){
          this.$message.error('图书类别请勿超过6位');
        }else {
          if(this.title === '新增图书类别'){
            insertBookCategory({
              bookCategory: this.newBookCategory
            }).then(async response => {
              const {data} = response
              await this.$store.dispatch('user/setBookCategory', data)
              this.insertOrUpdate = false
            }).catch(error => {
            })
          }else {
            updateBookCategory({
              oldBookCategory: this.oldBookCategory,
              newBookCategory: this.newBookCategory
            }).then(async response => {
              const {data} = response
              await this.$store.dispatch('user/setBookCategory', data)
              this.insertOrUpdate = false
            }).catch(error => {
            })
          }
        }
      }

    },
    // 事件 —— 删除图书类别
    deletesBookCateGory(){
      if(this.oldBookCategory === ''){
        this.$message.error('图书类别为空');
      }else {
        deleteBookCategory({
          bookCategory: this.oldBookCategory
        }).then(async response => {
          const {data} = response
          await this.$store.dispatch('user/setBookCategory', data)
          this.deletes = false
        }).catch(error => {
        })
      }
    },
  }
}
</script>

<style scoped>

</style>
