<template>
<div >
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



    <img :src="user.avatar" alt=""
         style="width: 50px; border-radius: 50%; position: relative; top: 10px; right: 10px;margin-left: 1200px">
    <el-submenu index="2" style="float: right">


      <el-menu-item index="/login">登录</el-menu-item>
      <el-menu-item index="/person">个人中心</el-menu-item>
      <el-menu-item index="/login">退出</el-menu-item>
    </el-submenu>

  </el-menu>

</div>

  <div class="block" style=" ">

    <el-row>
      <el-col  :span="12">
        <el-carousel height="450px" autoplay>
          <el-carousel-item v-for="item in imgs" :key="imgs.id">
                  <img :src="item.name" >
          </el-carousel-item>
        </el-carousel>
      </el-col>

      <el-col  :span="12" style="text-align: center">
        <h1>活动展示</h1>
        <ul style="text-decoration: none;float: left">
          <li><a href="https://sh.zhiyuanyun.com/app/opp/view.php?id=4UNHXPUq7dIIp" target="_blank">风筝节集市摆摊志愿服务</a></li>
          <li><a href="https://sh.zhiyuanyun.com/app/opp/view.php?id=4UNHXPUq7dIIp" target="_blank">风筝节集市摆摊志愿服务</a></li>
          <li><a href="https://sh.zhiyuanyun.com/app/opp/view.php?id=4UNHXPUq7dIIp" target="_blank">风筝节集市摆摊志愿服务</a></li>
          <li><a href="https://sh.zhiyuanyun.com/app/opp/view.php?id=4UNHXPUq7dIIp" target="_blank">风筝节集市摆摊志愿服务</a></li>
        </ul>
      </el-col>
    </el-row>

    <div>

    </div>



  </div>

</div>


</template>

<script>
export default {
  name:"Front",
  watch:{ //监听路由变化
    '$route':function () {
      this.currnPathname=localStorage.getItem("currnPathname")
    }
  },
  data() {
    return {
      activeIndex: '1',
      activeIndex2: '1',
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},//解析对象
      currnPathname: '首页',
      imgs:[
        {name:'http://zgzyz.cyol.com/images/attachement/jpg/site42/20220923/IMGe454e8d6b8e561392234949.jpg',id:1},
        {name:'http://zgzyz.cyol.com/images/attachement/jpg/site42/20200429/IMGb083fe71cace5381515743.jpg',id:2},
        {name: 'http://zgzyz.cyol.com/images/attachement/jpg/site42/20200429/IMGb083fe71cace5381532229.jpg',id:3}
      ],
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    logout(){
      this.$router.push("/login")
      localStorage.removeItem("user")
      this.$message.success("退出成功")
    }
  }
}
</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>