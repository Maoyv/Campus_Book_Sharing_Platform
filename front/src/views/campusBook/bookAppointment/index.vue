<template>
  <div v-if="indexIf">

    <div style="margin: 35px 100px;">
      <div style="float: left">
        <span style="font-size: 36px" >借阅管理</span>
      </div>
      <el-input v-model="userIds1" v-if="addIf" clearable placeholder="请输入用户ID" maxlength="20" style="width: 160px;margin-left: 800px;"/>
      <el-select v-model="state" v-if="addIf" filterable clearable style="width: 160px;margin-left: 15px" placeholder="请选择借阅状态">
        <el-option
          v-for="item in bookState"
          :key="item.state"
          :label="item.state"
          :value="item.state"
        />
      </el-select>
      <el-select v-model="state" v-else filterable clearable style="width: 160px;margin-left: 975px" placeholder="请选择借阅状态">
        <el-option
          v-for="item in bookState"
          :key="item.state"
          :label="item.state"
          :value="item.state"
        />
      </el-select>
      <el-button type="success" style="margin-left: 15px" @click="selectUserBook">查询</el-button>
      <el-button style="margin-left: 15px" @click="resetSelectUserBook">重置</el-button>

    </div>

    <div style="width: 1600px; top: 0;bottom: 0;left: 0;right: 0;margin: 35px auto auto;">
      <!--管理员登录表单-->
      <el-table v-if="formIf" :data="userBook.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="659px" border style="width: 100%">
        <el-table-column align="center" fixed prop="userId" sortable label="用户ID"/>
        <el-table-column align="center" fixed prop="bookId" sortable label="图书ID"/>
        <el-table-column align="center" fixed prop="appointmentTime" sortable label="预约时间"/>
        <el-table-column align="center" fixed prop="expirationTime" sortable label="预约过期时间"/>
        <el-table-column align="center" fixed prop="state" sortable label="状态"/>
        <el-table-column align="center" fixed="right" label="操作">
          <template slot-scope="scope">
            <!--传入的scope.row参数为当前行的数据-->
            <el-button v-if="scope.row.state === '申请中'" @click="borrowingUserBook(scope.row)" type="text" size="small">借阅</el-button>
            <el-button v-if="scope.row.state === '已借阅'" @click="restoreUserBook(scope.row)" type="text" size="small">归还</el-button>
            <el-button v-if="scope.row.state === '已归还' || scope.row.state === '已过期'" disabled type="text" size="small">不可操作</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--用户登录表单-->
      <el-table v-else :data="userBook.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="659px" border style="width: 100%">
        <el-table-column align="center" fixed prop="userId" sortable label="用户ID"/>
        <el-table-column align="center" fixed prop="bookId" sortable label="图书ID"/>
        <el-table-column align="center" fixed prop="appointmentTime" sortable label="预约时间"/>
        <el-table-column align="center" fixed prop="expirationTime" sortable label="预约过期时间"/>
        <el-table-column align="center" fixed prop="state" sortable label="状态"/>
        <el-table-column align="center" fixed="right" label="操作" >
          <template slot-scope="scope">
            <!--传入的scope.row参数为当前行的数据-->
            <el-button v-if="scope.row.state === '申请中'" @click="deleteUserBook(scope.row)" type="text" size="small">取消借阅</el-button>
            <el-button disabled v-else type="text"size="small">不可操作</el-button>
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
                       :total="userBook.length" >
        </el-pagination>
      </div>
    </div>

    <!--借阅图书-->
    <el-dialog title="提示" width="35%" :visible.sync="borrowingUserBookDialog">
      <div>
        <span style="font-size: 24px">即将执行借阅图书ID为：{{bookId}} 的图书给用户ID为：{{userIds2}}的用户操作，请确定是否执行</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="borrowingsUserBook">确定</el-button>
        <el-button @click="borrowingUserBookDialog = false">取消</el-button>
      </span>
    </el-dialog>

    <!--归还图书-->
    <el-dialog title="提示" width="35%" :visible.sync="restoreUserBookDialog">
      <div>
        <span style="font-size: 24px">即将执行归还用户ID为：{{userIds2}}的用户借阅的图书ID为：{{bookId}} 的图书操作，请确定是否执行</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="restoresUserBook">确定</el-button>
        <el-button @click="restoreUserBookDialog = false">取消</el-button>
      </span>
    </el-dialog>

    <!--取消借阅-->
    <el-dialog title="提示" width="35%" :visible.sync="deleteUserBookDialog">
      <div>
        <span style="font-size: 24px">即将执行取消预约借阅图书ID为：{{bookId}} 的图书操作，请确定是否执行</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="deletesUserBook">确定</el-button>
        <el-button @click="deleteUserBookDialog = false">取消</el-button>
      </span>
    </el-dialog>

  </div>
  <div v-else>
    <span style="font-size: 100PX">404</span>
  </div>
</template>

<script>
import {mapGetters} from "vuex";
import {borrowingUserBook, deleteUserBook, findAllUserBook, restoreUserBook} from "@/api/bookAppointment";

export default {
  name: "bookAppointmentAdmin",
  computed: {
    ...mapGetters([
      'role',
      'userId'
    ])
  },
  data() {
    return {
      // 分页参数
      currentPage: 1,
      pageSize: 10,
      title: '',
      // 按钮控制参数
      indexIf: true,
      formIf: true,
      addIf: true,
      // 弹窗控制参数
      borrowingUserBookDialog: false,
      restoreUserBookDialog: false,
      deleteUserBookDialog: false,
      // 页面参数
      userBook: [],
      userIds1: null,
      userIds2: null,
      state: null,
      bookState: [
        {state:'申请中'},
        {state:'已借阅'},
        {state:'已归还'},
        {state:'已过期'},
        {state:'全部'},
      ],
      bookId:'',
    }
  },
  mounted() {
    this.buttonController()
    this.selectUserBook()
  },
  methods: {
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
        this.indexIf = true
        this.formIf = true
        this.addIf = true
        this.state = '全部'

      }else {
        if(this.role === '用户'){
          this.indexIf = true
          this.formIf = false
          this.addIf = false
          this.userIds1 = this.userId

        }else {
          this.indexIf = false
        }
      }
    },
    // 查询
    selectUserBook(){
      findAllUserBook({
        userId: this.userIds1,
        state: this.state
      }).then(
        async response => {
          const {data} = response
          this.userBook = data
        }
      )
    },
    // 重置
    resetSelectUserBook(){
      this.state = null
      if(this.role === '管理员'){
        this.userIds1 = null
      }
      this.selectUserBook()
    },
    // 按钮 —— 借阅
    borrowingUserBook(row){
      this.bookId = row.bookId
      this.userIds2 = row.userId
      this.borrowingUserBookDialog = true
    },
    // 事件 —— 借阅
    borrowingsUserBook(){
      borrowingUserBook({
        userId: this.userIds2,
        bookId: this.bookId
      }).then(
        async response => {
          const {data} = response
          this.selectUserBook()
          this.borrowingUserBookDialog = false
        }
      )
    },
    // 按钮 —— 归还
    restoreUserBook(row){
      this.bookId = row.bookId
      this.userIds2 = row.userId
      this.restoreUserBookDialog = true
    },
    // 事件 —— 归还
    restoresUserBook(){
      restoreUserBook({
        userId: this.userIds2,
        bookId: this.bookId
      }).then(
        async response => {
          const {data} = response
          this.selectUserBook()
          this.restoreUserBookDialog = false
        }
      )
    },
    // 按钮 —— 取消借阅
    deleteUserBook(row){
      if(row.userId === this.userId){
        this.bookId = row.bookId
        this.userIds2 = row.userId
        this.deleteUserBookDialog = true
      }else {
        this.$message.error('信息异常，请重新登录后操作');
      }
    },
    // 事件 —— 取消借阅
    deletesUserBook(){
      deleteUserBook({
        userId: this.userIds2,
        bookId: this.bookId
      }).then(
        async response => {
          const {data} = response
          this.selectUserBook()
          this.deleteUserBookDialog = false
        }
      )
    },
  },
}
</script>

<style scoped>

</style>
