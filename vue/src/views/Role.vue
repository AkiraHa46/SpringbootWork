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
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
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
      <!--            <el-upload action="http://localhost:9999/user/import"-->
      <!--                       style="display: inline-block"-->
      <!--                       class="ml-5"-->
      <!--                       :show-file-list="false"-->
      <!--                       accept="'xlsx'"-->
      <!--                       :on-success="handleExcelImportSuccess"-->
      <!--            >-->
      <!--                <el-button type="primary">-->
      <!--                    导入 <i class="el-icon-el-icon-bottom"></i>-->
      <!--                </el-button>-->
      <!--            </el-upload>-->
      <!--            <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>-->
    </div>

    <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="70"></el-table-column>
      <el-table-column prop="id" label="ID"width="100" ></el-table-column>
      <el-table-column prop="name" label="名称" width="230"></el-table-column>
      <el-table-column prop="flag" label="唯一标识" ></el-table-column>
      <el-table-column prop="description" label="描述" ></el-table-column>

      <!--            <el-table-column prop="email" label="邮箱" width="120"></el-table-column>-->
      <!--            <el-table-column prop="phone" label="手机" width="120"></el-table-column>-->
      <!--            <el-table-column prop="address" label="地址"></el-table-column>-->
      <el-table-column label="操作" width="280">
        <template slot-scope="scpoe">
          <el-button type="info" @click="selectMenu(scpoe.row)">分盘菜单 <i class="el-icon-menu"></i></el-button>
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
    <div style="padding: 10px 0">
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

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">

        <el-form-item label="名称" >
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="唯一标识" >
          <el-input v-model="form.flag" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" >
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
        <!--                <el-form-item label="邮箱" >-->
        <!--                    <el-input v-model="form.email" autocomplete="off"></el-input>-->
        <!--                </el-form-item>-->
        <!--                <el-form-item label="电话" >-->
        <!--                    <el-input v-model="form.phone" autocomplete="off"></el-input>-->
        <!--                </el-form-item>-->
        <!--                <el-form-item label="地址" >-->
        <!--                    <el-input v-model="form.address" autocomplete="off"></el-input>-->
        <!--                </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="菜单分配" :visible.sync="menuDialogVisible" width="30%">
      <el-tree
          :props="props"
          :data="menuData"
          show-checkbox
          default-expand-all
          highlight-current
          ref="tree"
          node-key="id"
          :default-expanded-keys="expends"
          :default-checked-keys="checks"
          :check-strictly="true"
      >
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
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
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: "",

      dialogFormVisible: false,
      menuDialogVisible: false,
      multipleSelection: [],
      form: {},
      headerBg: 'headerBg',
      menuData: [

      ],
      props: {
        label: 'name'
      },
      expends: [],
      checks: [],
      roleId: 0,
      roleFlag: ''
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
      this.request.get("/role/findPage", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data;
        this.total = res.total;
      })
    },
    /**
     * 存储数据方法
     */
    save() {
      this.request.post("/role", this.form).then(res => {
        if (res) {
          this.$message.success("保存成功");
          this.dialogFormVisible = false;
          this.load();
        } else {
          this.$message.error("保存失败");
        }
      })
    },
    saveRoleMenu() {
      // console.log(this.$refs.tree.getCheckedNodes());
      // console.log(this.$refs.tree.getCheckedKeys());
      this.request.post("/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys()).then(res => {
        if (res.code === '200') {
          this.$message.success("绑定成功");
          this.menuDialogVisible = false;

          //操作管理员角色后需要重新登录
          if (this.roleFlag === 'ROLE_ADMIN') {
            this.$store.commit("logout")
          }


          console.log(res);
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    /**
     * 删除数据方法
     * @param id
     */
    del(id) {
      this.request.delete("/role/"+id).then(res=>{
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
    selectMenu(role) {
      this.menuDialogVisible = true;
      this.roleId = role.id;
      console.log("roleId: "+role.id)
      this.roleFlag = role.flag;
      // this.form = {}

      //请求菜单数据
      this.request.get("/menu").then(res => {
        console.log(res)
        this.menuData = res.data
        // this.tableData = res.data;
        // this.total = res.total;
        //把后台返回的菜单数据处理成id数组
        this.expends = this.menuData.map(v => v.id)
      })

      //请求菜单数据
      this.request.get("/role/roleMenu/" + this.roleId).then(res => {

        this.checks = res.data

        this.request.get("/menu/ids").then(r => {
          const ids = r.data
          ids.forEach(id => {
            if (!this.checks.includes(id)) {
              this.$nextTick(() => {
                this.$refs.tree.setChecked(id, false)
              })
            }
          })
          this.menuDialogVisible = true;
        })
      })
    },
    /**
     * 点击新增时页面出现的框
     */
    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {};
    },
    /**
     * 点击修改时页面出现的框
     * @param row
     */
    handleEdit(row) {
      this.form = row
      this.dialogFormVisible = true
    },
    // exp() {
    //     window.open("http://localhost:9090/role/export")
    // },
    // handleExcelImportSuccess() {
    //     this.$message.success("文件导入成功");
    //     this.load()
    // }
    // handleCheckChange(data, checked, indeterminate) {
    //     console.log(data, checked, indeterminate);
    // }
  }
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>