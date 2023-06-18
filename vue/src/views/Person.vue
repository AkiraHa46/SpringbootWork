<template>
  <div>

    <div class="line">
      <el-menu
          :default-active="activeIndex2"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          router
      >
        <el-menu-item index="/register">加入我们</el-menu-item>

        <!--    <el-submenu index="2">-->
        <!--      <template slot="title">我的工作台</template>-->
        <!--      <el-menu-item index="2-1">选项1</el-menu-item>-->
        <!--      <el-menu-item index="2-2">选项2</el-menu-item>-->
        <!--      <el-menu-item index="2-3">选项3</el-menu-item>-->
        <!--    </el-submenu>-->

        <el-menu-item index="3" disabled>消息中心</el-menu-item>
        <el-menu-item index="/Userfront">活动申请
        </el-menu-item>

        <el-menu-item index="/front" >主页</el-menu-item>

        <el-submenu index="2" style="float: right">
          <template slot="title">{{currnPathname}}</template>

          <el-menu-item index="/person">个人中心</el-menu-item>
          <el-menu-item index="/login">退出</el-menu-item>
        </el-submenu>

      </el-menu></div>






    <div style="width: 500px">

      <h1>个人信息</h1>
      <el-card>
        <el-form label-width="80px" size="small">

          <el-form-item label="用户名" >
            <el-input v-model="form.username"disabled  autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="姓名" >
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="状态" >
            <el-input v-model="form.mark" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item  >
            <el-button type="primary" @click="save">保存</el-button>
          </el-form-item>
        </el-form>
      </el-card>

    </div>
  </div>

</template>

<script>
export default {
  name: "Person",
  data(){
    return{
      form:{},
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},//解析对象
    }
  },
  created() {
    this.request.get("/user/username/"+this.user.username).then(res =>{
      if(res.code ==='200'){
        this.form =res.data
      }
    })
  },
  methods:{
    //保存功能
    save(){
      this.request.post("/user/post",this.form).then(res => {
        if (res) {
          this.$message.success("保存成功")

          this.load()
        } else {
          this.$message.error("保存失败")
        }
      })
    },
    //

  }

}


</script>


<style scoped>

</style>