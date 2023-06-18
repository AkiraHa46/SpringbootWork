<template>
<div>
  <div class="line">
    <el-menu

        class="el-menu-demo"
        mode="horizontal"

        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
        router
    >
      <el-menu-item index="/register">加入我们</el-menu-item>


      <el-menu-item index="3" disabled>消息中心</el-menu-item>
      <el-menu-item index="/Userfront">活动申请
      </el-menu-item>
      <el-menu-item index="/front" >主页</el-menu-item>

      <el-submenu index="2" style="float: right">
        <template slot="title">{{currnPathname}}</template>

        <el-menu-item index="/person">个人中心</el-menu-item>
        <el-menu-item index="/login">退出</el-menu-item>
      </el-submenu>

    </el-menu>

</div>


  <el-row :gutter="3" >
    <el-col :span="8" v-for="(item, index) in files" :key="item.id" :offset="index > 0 ? 2 : 0">
      <el-card >
        <img :src="item.url" class="image">
        <div style="padding: 14px;">
          <span><h3>{{item.name}}</h3>
            </span>
          <div class="bottom clearfix">
<!--            <time class="time">{{ currentDate }}</time>-->
            <el-button type="text" class="button" style="float: right" @click="handleEdit()">报名参加志愿</el-button>
          </div>
        </div>
      </el-card>
    </el-col>


  </el-row>

  <!--          表单事件-->
  <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
    <el-form label-width="80px" size="small">
      <el-form-item label="参与的活动名字" >
        <el-input v-model="form.vname" autocomplete="off">  </el-input>
      </el-form-item>
      <el-form-item label="用户名" >
        <el-input v-model="form.username" autocomplete="off">  </el-input>
      </el-form-item>
      <el-form-item label="电话" >
        <el-input v-model="form.phone" autocomplete="off">  </el-input>
      </el-form-item>


    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>

</div>

</template>

<script>
export default {
  name: "Userfront",
  watch:{ //监听路由变化
    '$route':function () {
      this.currnPathname=localStorage.getItem("currnPathname")
    }
  },
  data() {
    return {
      dialogFormVisible:false,
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},//解析对象
      currnPathname: '首页',
      currentDate: new Date(),
      files: [],
      form:{},
      tableData:[],
      vname:'',

    };
  },
  created() {

    this.request.get("http://localhost:9090/file/front/all").then(res =>{
      console.log(res.data)
      //对获取道德数据进行筛选
     this.files= res.data

    })
  },
  methods:{
    //保存功能
    save: function () {
      this.user.vo =this.files.vo;
      this.request.post("/Vo/post", this.form).then(res => {
        if (res) {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("保存失败")
        }
      })
    },

    //新增用户弹窗
    handleAdd(){
      this.dialogFormVisible=true

    },
    //编辑功能
    handleEdit(row){
      this.dialogFormVisible = true
    },
  }

}
</script>

<style scoped>

</style>