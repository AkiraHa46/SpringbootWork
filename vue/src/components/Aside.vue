<template>
  <el-menu
            style="overflow-x:hidden;min-height: 100%; "
            background-color="rgb(48,65,86)"
            text-color="#fff"
            router
  >

    <div style="height:60px;line-height: 60px;text-align: center">
      <b style="color: #eeeeee ">后台管理系统</b>
    </div>

    <div v-for="item in menus " :key="item.id ">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
       <i :class="item.icon"></i>
          <span slot="title">{{item.name}}</span>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="item.id+ ''">
          <template>
            <i :class="item.icon"></i>
            <span slot="title">{{item.name}}</span>
         </template>
          <div v-for="subItem in item.children" :key="subItem.id ">
            <el-menu-item :index="subItem.path">
              <i :class="subItem.icon"></i>
              <span slot="title">{{subItem.name}}</span>
            </el-menu-item>
          </div>

        </el-submenu>
      </div>

    </div>




  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props:{
  },
  data(){
    return{
      menus:localStorage.getItem("menus")?JSON.parse(localStorage.getItem("menus")):[],

    }
  }
}
</script>

<style scoped>

</style>


