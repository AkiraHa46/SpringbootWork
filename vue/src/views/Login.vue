<template>

  <div class="wrapper">



    <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border-radius: 20px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px">{{$t("name")}}</div>
<!--      {{ $("app.title") }}-->
      <el-form
          :rules="rules"
          :model="user"
          ref="userForm"
          @submit.native.prevent
      >
        <el-form-item prop="username">
          <el-input  size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password  v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">

            <el-button @click="changeLocale('')">{{language === 'zh' ? 'English' : '简体中文'}}</el-button>


          <el-button native-type="submit" type="primary" size="small" onautocomplete="off" @click="login">{{$t("name")}}</el-button>
          <el-button type="warning" size="small" onautocomplete="off" @click="$router.push('/register')">{{$t("re")}}</el-button>
        </el-form-item>
      </el-form>

    </div>

  </div>
</template>

<script>


import {setRoutes} from "@/router";

export default {
  name: "login",
  data(){
    return{

      language: 'zh',
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
      },
    }
  },
  created() {

  },
  methods:{
    login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {//表单校验合法
          this.request.post("/login",this.user).then(res => {
            if(res.code === '200'){
              if(res.data.username==='admin'){
                localStorage.setItem("user",JSON.stringify(res.data)) //储存用户信息到浏览器
                localStorage.setItem("menus",JSON.stringify(res.data.menus)) //储存菜单信息到浏览器
                //动态设置当前用户的路由
                setRoutes();
                this.$router.push("/home")
                this.$message.success("管理员登录成功")
              }
             else{
                localStorage.setItem("user",JSON.stringify(res.data)) //储存用户信息到浏览器
                this.$router.push("/front")
                this.$message.success("登录成功")
              }
            }else{
              this.$message.error(res.msg)
            }
          })
        }
      });
    },

    //页面国际化
    changeLocale() {
      this.language = this.language === 'zh' ? 'en' : 'zh'
      // 切换语言
      this.$i18n.locale = this.language
      console.log(this.$t('language.zh'))
    }
    //  这里写下一个方法
  }


}
</script>

<style s>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #3F5EFB,#FFC0CB);
  /*background: #2ba3f6;*/
  overflow: hidden;
}
</style>