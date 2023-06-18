<template>
<div style="">

  <el-header style="font-size: 12px;" >

    <div style="flex: 1; font-size: 20px;width: 1500px;text-align: left">
    <el-breadcrumb separator="/" style="display: inline-block;">
     <el-breadcrumb-item :to="'/home'">主页</el-breadcrumb-item>
    <el-breadcrumb-item :to="'/user'">{{currnPathname}}</el-breadcrumb-item>
    </el-breadcrumb>



      <el-dropdown style="width: 170px;cursor: pointer;margin-left: 1200px" >
        <div>
          <img :src="user.avatar" alt=""
               style="width: 50px; border-radius: 50%; position: relative; top: 10px; right: 10px">
          <span style="font-size: large"> {{user.nickname}} </span>
        </div>

        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <router-link to="/person" style="text-decoration: none;color: black" >个人信息</router-link>
          </el-dropdown-item>
          <el-dropdown-item>
            <router-link to="/front" style="text-decoration: none;color: black" >回到主页</router-link>
          </el-dropdown-item>
          <el-dropdown-item >
            <span   style="text-decoration: none" @click="logout">退出</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>





  </el-header>




</div>
</template>

<script>
export default {
  name: "Header",
  nickname:"昵称",
  props:{

  },
  watch:{ //监听路由变化
    '$route':function () {
      this.currnPathname=localStorage.getItem("currnPathname")
    }
  },
  data(){
    return {
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},//解析对象
      currnPathname: '首页'
    }
  },
  methods:{
    logout(){
      this.$router.push("/login")
      localStorage.removeItem("user")
       localStorage.removeItem("menus")
      this.$message.success("退出成功")
    }
  }

}
</script>

<style scoped>

</style>