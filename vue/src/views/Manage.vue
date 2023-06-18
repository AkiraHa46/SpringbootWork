<template>
  <div>
    <el-container style="height: 100vh; border: 1px solid #eee">

      <el-aside width="200px" style="overflow: hidden" >
         <Aside/>
<!--        <Aside/>-->
      </el-aside>

      <el-container>

      <el-header style="text-align: right; font-size: 12px">
      <Header/>
      </el-header>

        <el-main>
<!--          表示当前页面的子路由会在  <router-view/>里面展示-->
          <router-view/>

        </el-main>

       </el-container>

       </el-container>
  </div>


</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name:"User",
  data() {
   return  {
     user: {},
     total:0,
      date: '',
      name: '',
      username: '',
      password:'',
      pageNum:1,
      pageSize:5,
      tableData:[],
      form:{},
      dialogFormVisible:false,
     multipleSelection:[],

    }

  },
  created(){
    this.getUser()
  },
  components: {Aside,
  Header
  },
  methods:{
    getUser(){
     let username =localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).name:""
      if(username){
        this.request.get("/user/username"+username).then(res=>{
          //
          this.user=res.data
        })
      }
    }
  }

};
</script>

<style>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>

