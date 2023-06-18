<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <!--            <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-message" class="ml-5"></el-input>-->
      <!--            <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position" class="ml-5"></el-input>-->
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd()">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <template>
        <el-popconfirm
            confirm-button-text='好的'
            cancel-button-text='不用了'
            icon="el-icon-info"
            icon-color="red"
            title="这是一段内容确定删除吗？"
            @confirm="deleteBatch"
        >
          <el-button type="danger" slot="reference" class="ml-5 mr-5">
            批量删除 <i class="el-icon-remove-outline"></i>
          </el-button>
        </el-popconfirm>
      </template>

    </div>

    <el-table :data="tableData" border stripe header-cell-class-name="headerBg"
              row-key="id" default-expand-all @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="60"></el-table-column>
      <el-table-column prop="name" label="名称" width="180"></el-table-column>
      <el-table-column prop="path" label="路径"></el-table-column>
      <el-table-column prop="pagePath" label="页面路径"></el-table-column>
      <el-table-column prop="icon" label="图标" width="200" align="center" class-name="fontSize18" label-class-name="fontSize12">
        <template slot-scope="scope">
          <i :class="scope.row.icon"/>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述" ></el-table-column>

      <el-table-column label="操作" width="310">
        <template slot-scope="scpoe">
          <el-button type="primary" @click="handleAdd(scpoe.row.id)" v-if="!scpoe.row.pid && !scpoe.row.path">新增子菜单 <i class="el-icon-plus"></i></el-button>
          <el-button type="success" @click="handleEdit(scpoe.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <template>
            <el-popconfirm
                confirm-button-text='好的'
                cancel-button-text='不用了'
                icon="el-icon-info"
                icon-color="red"
                title="这是一段内容确定删除吗？"
                @confirm="del(scpoe.row.id)"
            >
              <el-button type="danger" slot="reference" class="ml-5">
                删除 <i class="el-icon-remove-outline"></i>
              </el-button>
            </el-popconfirm>
          </template>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="名称" >
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径" >
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="页面路径" >
          <el-input v-model="form.pagePath" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标" >
          <template slot-scope="scope">
            <el-select clearable v-model="form.icon" placeholder="请选择" style="width: 100%">
              <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
                <i :class="item.value"/>
                <span class="ml-5">{{item.name}}</span>
              </el-option>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="描述" >
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
        <!--                <el-form-item label="地址" >-->
        <!--                    <el-input v-model="form.address" autocomplete="off"></el-input>-->
        <!--                </el-form-item>-->
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
    name: "User",
    data() {
      return {
        tableData: [],
        name: "",
        dialogFormVisible: false,
        multipleSelection: [],
        form: {},
        headerBg: 'headerBg',
        options: []
      }
    },
    created() {
      this.load()
    },
    methods: {
      /**
       * 请求查询数据方法
       */
      load() {
        //  请求分页查询数据
        // fetch("http://localhost:9090/user/findPage?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&username="+this.username).then(res => res.json()).then(res => {
        //   console.log(res)
        //   this.tableData = res.data;
        //   this.total = res.total;
        // })
        this.request.get("/menu", {
          params: {
            name: this.name
          }
        }).then(res => {
          console.log(res)
          this.tableData = res.data;
        })
      },
      /**
       * 存储数据方法
       */
      save() {
        this.request.post("/menu", this.form).then(res => {
          if (res) {
            this.$message.success("保存成功");
            this.dialogFormVisible = false;
            this.load();
          } else {
            this.$message.error("保存失败");
          }
        })
      },
      /**
       * 删除数据方法
       * @param id
       */
      del(id) {
        this.request.delete("/menu/"+id).then(res=>{
          if (res) {
            this.$message.success("删除成功");
            this.load();
          } else {
            this.$message.error("删除失败");
          }
        })
      },
      /**
       * 批量删除数据方法
       */
      deleteBatch() {
        let ids = this.multipleSelection.map(v => v.id); //[{},{},{}] => [1,2,3]
        this.request.post("/role/del/batch", ids).then(res=>{
          if (res) {
            this.$message.success("批量删除成功");
            this.load();
          } else {
            this.$message.error("批量删除失败");
          }
        })
      },
      /**
       * 获取选中批量删除数据的方法
       * @param val
       */
      handleSelectionChange(val) {
        this.multipleSelection = val
      },
      /**
       * 重置方法
       */
      reset() {
        this.name = "";
        this.load();
      },
      /**
       * size动态刷新数据方法
       * @param pageSize
       */
      handleSizeChange(pageSize) {
        this.pageSize = pageSize;
        this.load();
      },
      /**
       * current动态刷新数据方法
       * @param pageNum
       */
      handleCurrentChange(pageNum) {
        this.pageNum = pageNum;
        this.load();
      },
      /**
       * 点击新增时页面出现的框
       */
      handleAdd(pid) {
        //请求图标的数据
        this.request.get("/menu/icons").then(res=>{
          this.options = res.data
        })
        this.dialogFormVisible = true;
        this.form = {};
        if (pid) {
          this.form.pid = pid;
        }
      },
      /**
       * 点击修改时页面出现的框
       * @param row
       */
      handleEdit(row) {
        this.form = row
        this.dialogFormVisible = true

        //请求图标的数据
        this.request.get("/menu/icons").then(res=>{
          this.options = res.data
        })

      },
      // exp() {
      //     window.open("http://localhost:9090/role/export")
      // },
      // handleExcelImportSuccess() {
      //     this.$message.success("文件导入成功");
      //     this.load()
      // }
    }
  }
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>