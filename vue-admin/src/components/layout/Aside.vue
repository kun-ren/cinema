<template>
  <div class="aside">
    <div class="aside-header" style="padding-top: 30px">
      <img style="width: 40px; height: 40px;float: left" src="../../assets/img/logo.png" alt=""/>
      <div style="padding-left: 15px;padding-top: 8px;float: left">Administration</div>
    </div>
    <div>
      <el-col style="padding-top: 30px">
        <el-menu
            class="menu"
            background-color="#242930"
            text-color="#FFFFFF"
            active-text-color="#409EFF">

          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-film"></i>
              <span>Film management</span>
            </template>
            <el-menu-item-group>
              <router-link to="/film/list">
                <el-menu-item style="padding-left: 70px" index="2-1">Films</el-menu-item>
              </router-link>
              <router-link to="/film/arrange">
                <el-menu-item style="padding-left: 70px" index="2-4">Screenings</el-menu-item>
              </router-link>
              <router-link to="/film/add">
                <el-menu-item style="padding-left: 70px" index="2-2">Add film</el-menu-item>
              </router-link>
              <router-link to="/film/poster">
                <el-menu-item style="padding-left: 70px" index="2-5">Carousel posters</el-menu-item>
              </router-link>
            </el-menu-item-group>
          </el-submenu>

          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-user-solid"></i>
              <span>User management</span>
            </template>
            <el-menu-item-group>
              <router-link to="/user/list">
                <el-menu-item style="padding-left: 70px" index="1-1">Users</el-menu-item>
              </router-link>
            </el-menu-item-group>
          </el-submenu>

          <el-submenu index="6">
            <template slot="title">
              <i class="el-icon-s-order"></i>
              <span>Order management</span>
            </template>
            <el-menu-item-group>
              <router-link to="/order/list">
                <el-menu-item style="padding-left: 70px" index="6-1">Orders</el-menu-item>
              </router-link>
              <router-link to="/order/exception">
                <el-menu-item style="padding-left: 70px" index="6-2">Order issues</el-menu-item>
              </router-link>
            </el-menu-item-group>
          </el-submenu>

          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-s-opportunity"></i>
              <span>Staff management</span>
            </template>
            <el-menu-item-group>
              <router-link to="/worker/list">
                <el-menu-item style="padding-left: 70px" index="3-1">Workers</el-menu-item>
              </router-link>
              <router-link to="/worker/daily">
                <el-menu-item style="padding-left: 70px" index="3-2">Daily work</el-menu-item>
              </router-link>
              <router-link to="/worker/add">
                <el-menu-item style="padding-left: 70px" index="3-3">Add worker</el-menu-item>
              </router-link>
            </el-menu-item-group>
          </el-submenu>

          <router-link to="/api">
            <el-menu-item index="4">
              <i class="el-icon-document"></i>
              <span slot="title">API documentation</span>
            </el-menu-item>
          </router-link>

          <router-link v-if="role==='worker'" to="/setting">
            <el-menu-item index="7">
              <i class="el-icon-setting"></i>
              <span slot="title">Settings</span>
            </el-menu-item>
          </router-link>

        </el-menu>
      </el-col>
    </div>
  </div>
</template>

<script>
export default {

  data() {
    return {
      role: localStorage.getItem('role')
    }
  }

}
</script>

<style scoped>
.aside {
  color: #FFFFFF;
}

.aside-header {
  padding: 21px;
  font-size: 18px;
  font-weight: 500;
}

.menu {
  font-size: 100px;
  border-right: 0;
  letter-spacing: 1px;
  font-weight: bold;
  font-family: "Helvetica Neue", Helvetica, Arial, "Microsoft Yahei", "Hiragino Sans GB", "Heiti SC", "WenQuanYi Micro Hei", sans-serif;
}

</style>
