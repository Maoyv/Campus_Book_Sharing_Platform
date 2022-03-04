<template>
  <div class="account-container" >
    <br/>
    <div class="account-div0">
      <div class="account-text">用户名: {{ this.name }}</div>
    </div>
    <br/>
    <br/>
    <div style="float: left;width: 510px" class="account-div1">
      <div class="account-div1">
        <div class="account-text">修改用户名</div>
      </div>
      <div class="account-div2">
        <div class="account-font">

          <el-form  style="width: 500px">

            <div >
              <el-form-item label="新用户名" style="width: 500px">
                <el-input clearable v-model="userName"
                          type="text"
                          style="width: 70%"
                          @keyup.enter.native="nameChange"
                          maxlength="20"
                ></el-input>
              </el-form-item>
            </div>

            <div style="text-align: center">
              <el-button type="primary" :loading="nameLoad" @click="nameChange" style="margin-left: 10px" size="small">
                提交
              </el-button>
            </div>

          </el-form>
        </div>
      </div>
    </div>


    <div style="float: left;width: 510px" class="account-div1">
      <div class="account-div1">
        <div class="account-text">修改密码</div>
      </div>
      <div class="account-div2">
        <div class="account-font">

          <el-form :model="password"  style="width: 500px">
            <el-form-item label="原密码" style="width: 500px">
              <el-input v-model="password.oldPassword"
                        type="password"
                        clearable
                        style="width: 70%"
                        maxlength="20"
              ></el-input>
            </el-form-item>

            <el-form-item label="新密码" style="width: 500px">
              <el-input v-model="password.newPassword"
                        type="password"
                        clearable
                        style="width: 70%"
                        @keyup.enter.native="passwordChange"
                        maxlength="20"
              ></el-input>
            </el-form-item>

            <div style="text-align: center">
              <el-button type="primary" @click="passwordChange" :loading="passwordLoad" style="margin-left: 10px" size="small">
                提交
              </el-button>
            </div>
          </el-form>

        </div>
      </div>
    </div>

  </div>
</template>



<script>
// 导入全局变量
import {mapGetters} from "vuex";
import {updateUserName,updatePassword} from "@/api/user"

export default {
  name: "Account",
  // 重载路由刷新页面,导入后在页面直接调用 this.reload()
  // inject: ['reload'],
  computed: {
    ...mapGetters([
      'name',
    ]),
  },
  // 页面参数
  data() {
    return {
      userName:'',
      password:{
        oldPassword:'',
        newPassword:'',
      },
      nameLoad:false,
      passwordLoad:false,
      nameDialogVisible:false,
      passwordDialogVisible:false,
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
    // 修改用户名事件
    nameChange(){
      this.nameLoad = true
      try{
        if(this.userName === ''){
          this.$message.error('用户名不能为空');
        }else {
          updateUserName({
            userName:this.userName
          }).then(async response => {
            // 接受返回的名称
            const {data} = response
            // 更改全局变量中的名称
            await this.$store.dispatch('user/setUserName', data)
            this.userName = ''
            await this.$alert('操作成功', '修改用户名', {
              confirmButtonText: '确定',
            });
          }).catch(error => {
          })
        }
      }finally {
        this.nameLoad = false
      }
    },
    // 修改密码事件
    passwordChange(){
      this.passwordLoad = true
      try{
        if(this.password.oldPassword === ''){
          this.$message.error('原密码不能为空');
        }else {
          const oldLength = this.password.oldPassword.split('').length
          if(oldLength <6 || oldLength >12){
            this.$message.error('原密码应为6-12位');
          }else {
            if(this.password.newPassword === ''){
              this.$message.error('新密码不能为空');
            }else {
              const newLength = this.password.newPassword.split('').length
              if(newLength <6 || newLength >12){
                this.$message.error('新密码应为6-12位');
              }else {
                if(this.password.oldPassword === this.password.newPassword){
                  this.$message.error('原密码和新密码应为不同密码');
                }else {
                  updatePassword({
                    oldPassword:this.password.oldPassword,
                    newPassword:this.password.newPassword
                  }).then(async response => {
                    // 接受返回的data 数据
                    const {data} = response
                    this.password = {
                      oldPassword:'',
                      newPassword:'',
                    }
                    await this.$alert('操作成功', '修改用户名', {
                      confirmButtonText: '确定',
                    });
                  }).catch(error => {
                  })
                }
              }
            }
          }
        }
      }finally {
        this.passwordLoad = false
      }
    },
  },
}
</script>

<style lang="scss" scoped>

.account {
  &-container {
   margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 100px;
  }
  &-font {
    font-size: 24px;
  }
  &-div0 {
    margin-left: 100px;
  }
  &-div1 {
    margin-left: 80px;
  }
  &-div2 {
    margin-left: 120px;
  }

}
</style>
