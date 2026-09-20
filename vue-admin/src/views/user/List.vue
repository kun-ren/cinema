<template>
  <div class="main">
    <el-table
        :data="userList"
        style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="Username">
              <span>{{ props.row.username }}</span>
            </el-form-item>
            <el-form-item label="Nickname">
              <span>{{ props.row.nickname }}</span>
            </el-form-item>
            <el-form-item label="Password">
              <el-button type="text" @click="openUpdatePassword(props.row)">
                <i class="el-icon-edit"></i>Change password
              </el-button>
            </el-form-item>
            <el-form-item label="Email">
              <span>{{ props.row.email }}</span>
            </el-form-item>
            <el-form-item label="Gender">
              <span>{{ props.row.gender }}</span>
            </el-form-item>
            <el-form-item label="Birthday">
              <span>{{ props.row.birthday }}</span>
            </el-form-item>
            <el-form-item label="Biography">
              <span>{{ props.row.info }}</span>
            </el-form-item>
            <el-form-item label="Updated at">
              <span>{{ props.row.updateAt }}</span>
            </el-form-item>
            <el-form-item label="User avatar URL">
              <a target="_blank" :href="props.row.avatar">{{ props.row.avatar }}</a>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
          label="User ID"
          width="430"
          prop="id">
      </el-table-column>
      <el-table-column
          label="Username"
          width="200"
          prop="username">
      </el-table-column>
      <el-table-column
          label="Nickname"
          width="200"
          prop="nickname">
      </el-table-column>
      <el-table-column
          label="Registered at"
          prop="createAt">
      </el-table-column>

    </el-table>
  </div>
</template>

<script>
import {FindAllUser, UpdateUser} from "@/api/user";

export default {


  data() {
    return {
      userList: [],
    }
  },

  mounted() {
    FindAllUser().then(res => {
      this.userList = res.data
    })
  },

  methods: {

    openUpdatePassword(user) {
      this.$prompt('Enter a new password', 'Notice', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
      }).then(({value}) => {
        if (!value) {
          this.$message({
            type: 'danger',
            message: 'Password is required'
          });
          return
        }
        if (value.length < 6) {
          this.$message({
            type: 'danger',
            message: 'Enter a password with at least 6 characters'
          });
          return
        }
        user.password = value
        UpdateUser(user).then(res => {
          this.$message({
            message: 'Password changed successfully',
            type: 'success',
          });
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: 'Changes canceled'
        });
      });
    }

  },

}
</script>

<style scoped>
.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 150px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>