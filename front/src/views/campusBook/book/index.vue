<template>
  <div>
    <div style="margin: 35px 100px;">
      <div style="float: left">
        <span style="font-size: 36px" >校园图书</span>
      </div>
      <el-button type="primary" v-if="addIf" style="margin-left: 15px" @click="addBook">新增图书</el-button>
      <el-input v-if="addIf" v-model="selectBookName" clearable placeholder="请输入图书名称" maxlength="20" style="width: 160px;margin-left: 690px;"/>
      <el-input v-else v-model="selectBookName" clearable placeholder="请输入图书名称" maxlength="20" style="width: 160px;margin-left: 800px;"/>
      <el-select v-model="selectBookCategory" filterable clearable style="width: 160px;margin-left: 15px" placeholder="请选择图书分类">
        <el-option
          v-for="item in this.bookCategory"
          :key="item.bookCategory"
          :label="item.bookCategory"
          :value="item.bookCategory"
        />
      </el-select>
      <el-button type="success" style="margin-left: 15px" @click="selectBookByConditions">查询</el-button>
      <el-button style="margin-left: 15px" @click="resetSelectBook">重置</el-button>
    </div>

    <div style="width: 1600px; top: 0;bottom: 0;left: 0;right: 0;margin: 35px auto auto;">
      <!--管理员登录表单-->
      <el-table v-if="adminBookForm" :data="books.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="659px" border style="width: 100%">
        <el-table-column align="center" fixed prop="bookId" sortable label="图书ID"/>
        <el-table-column align="center" fixed prop="bookName" sortable label="图书名称"/>
        <el-table-column align="center" fixed prop="bookCategory" sortable label="图书分类"/>
        <el-table-column align="center" fixed prop="bookNumber" sortable label="图书数量"/>
        <el-table-column align="center" fixed prop="bookBorrowed" sortable label="已被借阅数量"/>
        <el-table-column align="center" fixed prop="bookAppointment" sortable label="预约借阅数量"/>
        <el-table-column align="center" fixed prop="bookAvailable" sortable label="可预约借阅数量"/>
        <el-table-column align="center" fixed="right" label="操作">
          <template slot-scope="scope">
            <!--传入的scope.row参数为当前行的数据-->
            <el-button @click="openIntroduction(scope.row)" type="text" size="small">简介</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button @click="updateBook(scope.row)" type="text" size="small">修改</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button v-if="scope.row.bookNumber === scope.row.bookAvailable" @click="deleteBook(scope.row)" type="text" size="small">删除</el-button>
            <el-button disabled v-else type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--用户登录表单-->
      <el-table v-if="userBookForm" :data="books.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="659px" border style="width: 100%">
        <el-table-column align="center" fixed prop="bookId" sortable label="图书ID"/>
        <el-table-column align="center" fixed prop="bookName" sortable label="图书名称"/>
        <el-table-column align="center" fixed prop="bookCategory" sortable label="图书分类"/>
        <el-table-column align="center" fixed prop="bookNumber" sortable label="图书数量"/>
        <el-table-column align="center" fixed prop="bookBorrowed" sortable label="已被借阅数量"/>
        <el-table-column align="center" fixed prop="bookAppointment" sortable label="预约借阅数量"/>
        <el-table-column align="center" fixed prop="bookAvailable" sortable label="可预约借阅数量"/>
        <el-table-column align="center" fixed="right" label="操作" >
          <template slot-scope="scope">
            <!--传入的scope.row参数为当前行的数据-->
            <el-button @click="openIntroduction(scope.row)" type="text" size="small">简介</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button  disabled v-if="scope.row.bookAvailable === 0" type="text"size="small">不可借阅</el-button>
            <el-button  v-else @click="appointmentBook(scope.row)" type="text"size="small">预约借阅</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--游客登录表单-->
      <el-table v-if="touristsBookForm" :data="books.slice((currentPage-1)*pageSize,currentPage*pageSize)" height="659px" border style="width: 100%">
        <el-table-column align="center" fixed prop="bookId" sortable label="图书ID"/>
        <el-table-column align="center" fixed prop="bookName" sortable label="图书名称"/>
        <el-table-column align="center" fixed prop="bookCategory" sortable label="图书分类"/>
        <el-table-column align="center" fixed prop="bookNumber" sortable label="图书数量"/>
        <el-table-column align="center" fixed prop="bookBorrowed" sortable label="已被借阅数量"/>
        <el-table-column align="center" fixed prop="bookAppointment" sortable label="预约借阅数量"/>
        <el-table-column align="center" fixed prop="bookAvailable" sortable label="可预约借阅数量"/>
        <el-table-column align="center" fixed="right" label="操作" >
          <template slot-scope="scope">
            <!--传入的scope.row参数为当前行的数据-->
            <el-button @click="openIntroduction(scope.row)" type="text" style="width: 80px" size="small">简介</el-button>
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
                       :total="books.length" >
        </el-pagination>
      </div>
    </div>

<!--简介弹窗-->
    <el-dialog :title="bookIntroductionDialogTitle" width="50%" :visible.sync="bookIntroductionDialog">
      <div style="margin: 10px">
        <span style="font-size: 24px">简介:</span>
        <span style="font-size: 18px">{{this.bookIntroduction}}</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button @click="bookIntroductionDialog = false">关闭</el-button>
      </span>
    </el-dialog>

<!--新增/修改弹窗-->
    <el-dialog :title="addOrUpdateBookDialogTitle" width="50%" :visible.sync="addOrUpdateBookDialog" @close="cancel('ruleForm')" @cancel="cancel('ruleForm')">

      <div style="margin: 10px">
        <el-form label-width="10%" :rules="bookFormRules" :model="bookForm" ref="ruleForm">
          <el-form-item label="图书名称"  prop="bookName">
            <el-input v-model="bookForm.bookName" maxlength="20" show-word-limit style="width: 90%" filterable clearable placeholder="请输入图书名称"/>
          </el-form-item>

          <el-form-item label="图书类型">
            <el-select v-model="bookForm.bookCategory" style="width: 90%" filterable clearable placeholder="请选择图书分类">
              <el-option
                v-for="item in this.bookCategory"
                :key="item.bookCategory"
                :label="item.bookCategory"
                :value="item.bookCategory"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="图书简介" prop="bookIntroduction">
            <el-input v-model="bookForm.bookIntroduction" type="textarea" maxlength="60" show-word-limit :rows="2" style="width: 90%"  filterable clearable placeholder="请输入图书简介"/>
          </el-form-item>

          <el-form-item label="图书数量" prop="bookNumber">
            <el-input-number v-model="bookForm.bookNumber" min="1" max="2147483646" style="width: 90%" :controls="false" filterable clearable placeholder="请输入图书数量"/>
          </el-form-item>
        </el-form>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addOrUpdateBook">确定</el-button>
        <el-button @click="cancel('ruleForm')">取消</el-button>
      </span>
    </el-dialog>

<!--删除图书信息弹窗-->
    <el-dialog title="提示" width="35%" :visible.sync="deleteBookDialog">
      <div>
        <span style="font-size: 24px">即将执行删除图书《{{bookName}}》操作，请确定是否执行</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="deletesBook">确定</el-button>
        <el-button @click="deleteBookDialog = false">取消</el-button>
      </span>
    </el-dialog>

<!--借阅图书-->
    <el-dialog title="提示" width="35%" :visible.sync="appointmentBookDialog">
      <div>
        <span style="font-size: 24px">即将执行预约借阅图书《{{bookName}}》操作，请确定是否执行</span>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="appointmentsBook">确定</el-button>
        <el-button @click="appointmentBookDialog = false">取消</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
import {mapGetters} from "vuex";
import {addBook, addUserBook, deleteByIdForBook, queryAllBook, updateBook} from "@/api/book";

export default {
  name: "book",
  computed: {
    ...mapGetters([
      'bookCategory',
      'role',
      'userId'
    ])
  },
  // 页面参数
  data(){
    return {
      books: [],
      // 图书分类
      selectBookCategory: '',
      // 图书名称
      selectBookName: '',
      // 分页参数
      currentPage: 1,
      pageSize: 10,
      title: '',
      // 按钮控制参数
      addIf: false,
      adminBookForm: false,
      userBookForm: false,
      touristsBookForm: false,
      // 弹窗控制参数
      bookIntroductionDialog: false,
      addOrUpdateBookDialog: false,
      deleteBookDialog: false,
      appointmentBookDialog: false,
      // 表单变量参数
      bookName:'',
      bookIntroduction: '',
      bookIntroductionDialogTitle:'',
      addOrUpdateBookDialogTitle:'',
      bookForm: {
        bookId: '',
        bookName: '',
        bookCategory: '',
        bookIntroduction: '',
        bookNumber: undefined,
        bookBorrowed: undefined,
        bookAppointment: undefined,
        bookAvailable: undefined,
      },
      // 表单验证参数
      bookFormRules: {
        bookName:[
          {required: true, trigger: 'blur', message: '图书名称不能为空'},
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        bookIntroduction:[
          {required: true, trigger: 'blur', message: '图书简介不能为空'},
          { min: 1, max: 60, message: '长度在 1 到 60 个字符', trigger: 'blur' }
        ],
        bookNumber:[
          {required: true, trigger: 'blur',type: 'number', message: '图书数量不能为空'},
          { min: 1, max: 2147483646,type: 'number', message: '大小应在 1 到 2147483647 个字符', trigger: 'blur' }
        ]
      },
    }
  },
  // 页面打开时加载
  mounted() {
    this.selectBookByConditions()
    this.buttonController()
  },
  // 方法
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
        this.addIf = true
        this.adminBookForm = true

      }else {
        if(this.role === '用户'){
          this.addIf = false
          this.userBookForm = true

        }else {
          this.addIf = false
          this.touristsBookForm = true

        }
      }
    },
    // 条件查询
    selectBookByConditions(){
      queryAllBook({
        bookName: this.selectBookName,
        bookCategory: this.selectBookCategory
      }).then(
        async response => {
          const {data} = response
          this.books = data
        }
      )
    },
    // 重置
    resetSelectBook(){
      this.selectBookName = ''
      this.selectBookCategory = ''
      this.selectBookByConditions()
    },
    // 打开简介
    openIntroduction(row){
      this.bookIntroductionDialog = true
      this.bookIntroduction = row.bookIntroduction
      this.bookIntroductionDialogTitle = "图书名称："+row.bookName
    },
    // 按钮 —— 新增图书信息
    addBook(){
      this.addOrUpdateBookDialogTitle = '新增图书'
      this.bookForm = {
        bookName:'',
        bookCategory:'',
        bookIntroduction:'',
        bookNumber:undefined,
      }
      this.addOrUpdateBookDialog = true
    },
    // 按钮 —— 修改图书信息
    updateBook(row){
      if(row.bookNumber >= row.bookBorrowed + row.bookAppointment){
        this.addOrUpdateBookDialogTitle = '修改图书信息'
        this.bookForm = {
          bookId: row.bookId,
          bookName: row.bookName,
          bookCategory: row.bookCategory,
          bookIntroduction: row.bookIntroduction,
          bookNumber: row.bookNumber,
          bookBorrowed: row.bookBorrowed,
          bookAppointment: row.bookAppointment,
          bookAvailable: row.bookAvailable,
        }
        this.addOrUpdateBookDialog = true
      }else {
        this.$message.error('修改后的图书数量不能小于已预约借阅的数量和已借阅的数量之和');
      }
    },
    // 事件——新增/修改图书信息
    addOrUpdateBook(){
      if(this.addOrUpdateBookDialogTitle === '新增图书'){
        addBook({
          bookName: this.bookForm.bookName,
          bookCategory: this.bookForm.bookCategory,
          bookIntroduction: this.bookForm.bookIntroduction,
          bookNumber: this.bookForm.bookNumber
        }).then(
          async response => {
            const {data} = response
            this.selectBookByConditions()
            this.addOrUpdateBookDialog= false
          }
        )
      }else {
        updateBook({
          bookId: this.bookForm.bookId,
          bookName: this.bookForm.bookName,
          bookCategory: this.bookForm.bookCategory,
          bookIntroduction: this.bookForm.bookIntroduction,
          bookNumber: this.bookForm.bookNumber,
          bookBorrowed: this.bookForm.bookBorrowed,
          bookAppointment: this.bookForm.bookAppointment,
          bookAvailable:this.bookForm.bookAvailable
        }).then(
          async response => {
            const {data} = response
            this.selectBookByConditions()
            this.addOrUpdateBookDialog= false
          }
        )

      }
    },
    // 按钮 —— 删除图书
    deleteBook(row){
      if(row.bookNumber === row.bookAvailable){
        this.bookName = row.bookName
        this.bookForm.bookId = row.bookId
        this.deleteBookDialog = true
      }else {
        this.$message.error('书籍未全部回收，无法执行删除');
      }
    },
    // 事件 —— 删除图书
    deletesBook(){
      deleteByIdForBook({
        id: this.bookForm.bookId,
      }).then(
        async response => {
          const {data} = response
          this.selectBookByConditions()
          this.deleteBookDialog = false
        }
      )
    },
    // 按钮 —— 借阅预约
    appointmentBook(row){
      this.bookName = row.bookName
      this.bookForm.bookId = row.bookId
      this.appointmentBookDialog = true
    },
    // 事件 —— 借阅预约
    appointmentsBook(){
      addUserBook({
        userId: this.userId,
        bookId: this.bookForm.bookId
      }).then(
        async response => {
          const {data} = response
          this.selectBookByConditions()
          this.appointmentBookDialog = false
        }
      )
    },
    // 事件——弹窗关闭
    cancel(formName){
      this.bookIntroductionDialog= false
      this.addOrUpdateBookDialog= false
      this.deleteBookDialog= false
      this.appointmentBookDialog= false
      this.$refs[formName].resetFields();
    },
  }
}
</script>

<style scoped>

/deep/ .el-input-number .el-input__inner {
  text-align: left;
}

</style>
