<template>
<div>
  <div style="padding: 10px 0">
    <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name" ></el-input>
    <el-button style="margin-left:5px " type="primary" @click="load" >搜索</el-button>
  </div>

  <div style="margin:10px 0 ">
    <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-plus"></i>   </el-button>
    <el-button type="danger" @click="delBatch">批量删除 <i class="el-icon-delete-solid"></i>  </el-button>
    <el-upload
        action="http://localhost:9090/user/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block; margin-left: 5px"
       >
      <el-button type="primary">导入 <i class="el-icon-top"></i>    </el-button>
    </el-upload>


    <el-button type="primary"@click="exp" style="margin-left: 5px">导出   <i class="el-icon-bottom"></i>  </el-button>
  </div>


  <el-table :data="tableData" border stripe header-cell-class-name="headerBg"  @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55"></el-table-column>
    <el-table-column prop="id" label="志愿id" width="150"></el-table-column>
    <el-table-column prop="username" label="用户名" width="150"></el-table-column>
    <el-table-column prop="phone" label="电话" width="150"></el-table-column>
    <el-table-column prop="vname" label="报名参加的志愿活动" width="120"></el-table-column>


    <!--              操作行-->
    <el-table-column label="操作">
      <template slot-scope="scope" >
        <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>

        <el-popover
            placement="top"
            width="160"
        >
          <p>这是一段内容这是一段内容确定删除吗？</p>
          <div style="text-align: right; margin: 0">
            <el-button size="mini" type="text" @click="visible = false">取消</el-button>
            <el-button type="primary" size="mini" @click="del(scope.row.id)">确定</el-button>
          </div>
          <el-button slot="reference" class="ml-5" type="danger" >删除</el-button>
        </el-popover>

      </template>
    </el-table-column>

  </el-table>

  <div>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

  </div>

  <!--          表单事件-->
  <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
    <el-form label-width="80px" size="small">
      <el-form-item label="用户名" >
        <el-input v-model="form.username" autocomplete="off" disabled>  </el-input>
      </el-form-item>
      <el-form-item label="角色" >
        <el-select clearable v-model="form.role" placeholder="请选择角色" style="width: 100%">
          <el-option
              v-for="item in roles"
              :key="item.name"
              :label="item.name"
              :value="item.flag">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="姓名" >
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="志愿名字" >
        <el-input v-model="form.vname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话" >
        <el-input v-model="form.phone" autocomplete="off"></el-input>
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
import  Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: "Zhiyuan",
  data() {
    return  {
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
      roles:[]

    }

  },
  created(){
    //请求分页查询数据
    this.load()
  },
  components: {Aside,
    Header
  },
  methods:{
    //请求数据
    load(){
      this.request.get("/Vo/page",{
        params: {
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username
        }
      }).then (res =>{
        console.log(res)
        this.tableData= res.records;
        this.total=res.total

      })

    },
    //分页方法
    handleSizeChange(pageSize){
      this.pageSize =pageSize;
      this.load();
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum;
      this.load();
    },

    //保存功能
    save(){
      this.request.post("/user/post",this.form).then(res => {
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
      this.form={}
    },
    //编辑功能
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
      this.load()

    },
    //   删除功能
    del(id){
      this.request.delete("/user/"+id).then (res => {
        if (res) {
          this.$message.success("删除成功")
          this.dialogFormVisible = false
          this.load()

        } else {
          this.$message.error("删除失败")

        }
      })
    },
    //批量删除
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val;
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)  // [{},{},{}} 变成[1,2,3]
      this.request.post("/user/del/batch",ids).then (res => {
        if (res) {
          this.$message.success("删除成功")
          this.load()

        } else {
          this.$message.error("删除失败")

        }
      })

    },
    exp(){
      window.open("http://localhost:9090/user/export")
    },
    handleExcelImportSuccess(){
      this.$message.success("导入成功")
      this.load()
    }
  }

}
</script>

<style scoped>

</style>