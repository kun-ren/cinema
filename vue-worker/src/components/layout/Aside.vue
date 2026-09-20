<template>
  <div class="aside">
    <div class="aside-header" style="padding-top: 30px">
      <img style="width: 40px; height: 40px;float: left" src="../../assets/img/logo.png" alt=""/>
      <div style="padding-left: 15px;padding-top: 8px;float: left">Little Cinema - Support</div>
    </div>
    <div>
      <el-col style="padding-top: 30px">
        <el-menu
            class="menu"
            background-color="#242930"
            text-color="#FFFFFF"
            active-text-color="#409EFF">

          <router-link to="/leaving">
            <el-menu-item style="padding-left: 65px" index="1">
              <i class="el-icon-s-promotion"></i>
              <span slot="title">Cinema messages</span>
            </el-menu-item>
          </router-link>

          <router-link to="/phone">
            <el-menu-item style="padding-left: 65px" index="2">
              <i class="el-icon-phone-outline"></i>
              <span slot="title">Phone follow-up</span>
            </el-menu-item>
          </router-link>

          <router-link to="/activity">
            <el-menu-item style="padding-left: 65px" index="3">
              <i class="el-icon-s-flag"></i>
              <span slot="title">Activities</span>
            </el-menu-item>
          </router-link>

          <router-link to="/info">
            <el-menu-item style="padding-left: 65px" index="4">
              <i class="el-icon-s-data"></i>
              <span slot="title">Order statistics</span>
            </el-menu-item>
          </router-link>

          <router-link to="/evaluate">
            <el-menu-item style="padding-left: 65px" index="5">
              <i class="el-icon-s-management"></i>
              <span slot="title">My reviews</span>
            </el-menu-item>
          </router-link>

          <router-link to="/setting">
            <el-menu-item style="padding-left: 65px" index="6">
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
  name: "Aside"
}
</script>

<style scoped>
.aside {
  color: #FFFFFF;
}

.item {
  padding-left: 100px;
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
