<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 500px; padding: 20px; border-radius: 20px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>注 册</b></div>
      <el-form
          :rules="rules"
          :model="user"
          ref="userForm"
          @submit.native.prevent
      >
        <el-form-item prop="username">
          <el-input placeholder="账号"  size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请确认密码"  size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password  v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input placeholder="请再次确认密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password  v-model="user.confirmPassword"></el-input>
        </el-form-item>
        <el-form-item prop="nickname">
          <el-input placeholder="请输入你的昵称"  size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.nickname"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button native-type="submit" type="primary" size="small" onautocomplete="off" @click="login">注册</el-button>
          <el-button type="warning" size="small" onautocomplete="off" @click="$router.push('/login')">返回登陆</el-button>
        </el-form-item>
      </el-form>

    </div>
  </div>
</template>

<script>


export default {
  name: "login",
  data(){
    return{
      user:{},
      //表单校验
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 5, max: 10, message: '长度在 5 到 10 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 5, max: 10, message: '长度在 5 到 10 个字符', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 5, max: 10, message: '长度在 5 到 10 个字符', trigger: 'blur' }
        ],


      },
    }
  },
  methods:{
    login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {//表单校验合法
          if(this.user.password!== this.user.confirmPassword){
            this.$message.error("两次输入密码不一致")
            return false;
          }
          this.request.post("/login/register",this.user).then(res => {
            if(res.code === '200'){
              this.$message.success("注册成功")
              this.$router.push("/login")
            }else{
              this.$message.error(res.msg)
            }
          })
        }
      });
    },
  //  这里写下一个方法
  },



}
</script>

<style s>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, 	#7FFFD4,#FFC0CB);
  /*background: #2ba3f6;*/
  overflow: hidden;
}
</style>