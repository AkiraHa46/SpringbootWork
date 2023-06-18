import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Manage.vue'


Vue.use(VueRouter)

const routes = [
  // {
  //   path: '/',
  //   name: 'Manage',
  //   component: () => import( '../views/Manage.vue'),
  //   redirect:"/login",
  //   children: [
  //     {path: '/home',name: 'Home', component: () => import( '../views/Home.vue')},
  //     {path: '/user',name: '用户信息', component: () => import( '../views/User.vue') },
  //     {path: '/role',name: '角色管理', component: () => import( '../views/Role.vue') },
  //     {path: '/menu',name: '菜单管理', component: () => import( '../views/Menu.vue') },
  //
  //   ]
  // },

  {
    path: '/login',
    name: '登录',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue'),
  },
  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue'),
  },
  {
    path: '/person',
    name: '个人信息',
    component: () => import(/* webpackChunkName: "about" */ '../views/Person.vue'),
  },

  {
    path: '/register',
    name: '注册',
    component: () => import(/* webpackChunkName: "about" */ '../views/Register.vue'),
  },

  {
    path: '/front',
    name: '用户前端界面',
    component: () => import( '../views/Front.vue'),
  },
  {
    path: '/userfront',
    name: '用户前端界面',
    component: () => import( '../views/Userfront.vue'),
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

//动态添加路由
//刷新页面会导致页面路由重置
export const setRoutes =()=>{  //导出给登陆用
 const storeMenus= localStorage.getItem("menus");  //取出menu集合
 if (storeMenus){
   //如果有就拼装路由
   const  manageRoute={
     path: '/',
     name: 'Manage',  component: () => import( '../views/Manage.vue'), redirect:"/front", children: [] }
     const menus =JSON.parse(storeMenus)

   menus.forEach(item =>{
     if(item.path){  //仅当path不为空的时候才去设置路由
       let itemMenu ={path: item.path.replace("/",""), name: item.name, component: () => import( '../views/'+item.pagePath+'.vue')}
       manageRoute.children.push(itemMenu)
     }

    else if(item.children.length){
       item.children.forEach(item =>{
         if(item.path){
           let itemMenu ={path: item.path.replace("/",""), name: item.name, component: () => import( '../views/'+item.pagePath+'.vue')}
           manageRoute.children.push(itemMenu)
         }
       })
     }

   })
   //获取当前路由对象
    const currentRoutesNames =  router.getRoutes().map(v => v.name)
    if(!currentRoutesNames.includes('Manage')){
    //动态添加到现在的路由对象中去
     router.addRoute(manageRoute)
   }

 }
}

//重置我就set一次路由
setRoutes()


router.beforeEach((to, from, next) => {
  // 创建loginType获取localStorage中的用户信息userJsonconst loginType = Storage.prototype.getCanExpireLocal('userJson');
  // 进入路由守卫后，首先判断meta中携带的hidden参数如果为false则属于白名单直接放行，否则进入判断if (to.meta.hidden)
  // {// 判断登录状态，如果true则放行，false则把路由定向到登录页
  // invalid：无效的  在localStorage中if (loginType !== 'invalid' && loginType !== undefined) {next();}
  // else {next({path: '/login'});}} else {next();}
  localStorage.setItem("currnPathname",to.name)//设置路由名字
  next(0)
});




export default router

