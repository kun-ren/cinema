<template>
  <div class="main">

    <el-table
        :data="workerList"
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
            <el-form-item label="Contact phone">
              <span>{{ props.row.phone }}</span>
            </el-form-item>
            <el-form-item label="Gender">
              <span>{{ props.row.gender }}</span>
            </el-form-item>
            <el-form-item label="Department">
              <span>{{ props.row.department }}</span>
            </el-form-item>
            <el-form-item label="Status">
              <span v-if="props.row.entry">Employed
              <el-button @click="changeEntry(props.$index, props.row)" type="text">Worker has left?</el-button></span>
              <span style="color: #f56c6c" v-else>Departed</span>
            </el-form-item>
            <el-form-item label="Updated at">
              <span>{{ props.row.updateAt }}</span>
            </el-form-item>
            <el-form-item label="Worker avatar">
              <a v-if="props.row.avatar" target="_blank" :href="props.row.avatar">
                <i class="el-icon-view"></i>View
              </a>
              <span v-else>No avatar uploaded</span>
            </el-form-item>
            <el-form-item>
              <el-button :disabled="!props.row.entry"
                         @click="handleListRole(props.row)"
                         type="info"
                         size="small"
                         plain>View permissions
              </el-button>
              <el-button :disabled="!props.row.entry"
                         type="primary"
                         @click="handleAddRole(props.row)"
                         size="small"
                         plain>Add permission
              </el-button>
              <el-button :disabled="props.row.entry"
                         @click="handleDeleteWork(props.$index, props.row.id)"
                         size="small"
                         type="danger"
                         plain>Delete worker
              </el-button>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>

      <el-table-column
          label="Worker ID"
          width="430"
          prop="id">
      </el-table-column>
      <el-table-column
          label="Username"
          width="200"
          prop="username">
      </el-table-column>
      <el-table-column
          label="Department"
          width="150">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.department }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          label="Contact phone"
          width="240"
          prop="phone">
      </el-table-column>
      <el-table-column
          label="Start date"
          prop="createAt">
      </el-table-column>

    </el-table>

    <el-dialog title="Worker permissions" :visible.sync="roleDialogVisible">
      <el-table v-loading="loading" :data="roleList">
        <el-table-column property="value" label="Permission" width="300"></el-table-column>
        <el-table-column property="createAt" label="Created at" width="300"></el-table-column>
        <el-table-column label="Actions">
          <template slot-scope="scope">
            <el-popconfirm
                @confirm="handleDeleteRole(scope.$index, scope.row.id)"
                :title="'Remove permission < ' + scope.row.value +' > ?'"
            >
              <el-button slot="reference" size="small" type="danger" icon="el-icon-delete" plain>Delete</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <el-dialog width="450px" title="Add permission" :visible.sync="dialogFormVisible">
      <el-form :model="addRoleForm">
        <el-form-item label="Permissions" label-width="150px">
          <el-select v-model="addRoleForm.value" placeholder="Select a permission to add">
            <el-option v-for="(item, index) in systemRoles"
                       :key="index"
                       :label="item"
                       :value="item">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="submitAddRole">Confirm</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {
  CreateWorkerRole,
  DeleteRoleByWorkerId,
  DeleteWorker,
  FindAllWorker,
  FindWorkerRoles,
  ListSystemRoles,
  UpdateWorker
} from "@/api/worker";

export default {


  data() {
    return {
      loading: false,
      addRoleForm: {
        wid: '',
        value: '',
      },
      workerList: [],
      systemRoles: [],
      roleDialogVisible: false,
      roleList: [],
      dialogFormVisible: false,
    }
  },

  mounted() {
    FindAllWorker().then(res => {
      this.workerList = res.data
    })
    ListSystemRoles().then(res => {
      this.systemRoles = res.data
    })
  },

  methods: {

    openUpdatePassword(worker) {
      this.$prompt('Enter a new password', 'Notice', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
      }).then(({value}) => {
        if (!value) {
          this.$message({
            message: 'Password is required',
            type: 'danger',
          });
          return
        }
        if (value.length < 6) {
          this.$message({
            message: 'Enter a password with at least 6 characters',
            type: 'danger',
          });
          return
        }
        worker.password = value
        UpdateWorker(worker).then(res => {
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
    },

    changeEntry(index, worker) {
      this.$confirm('This will permanently mark the worker as departed. Continue?', 'Notice', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        worker.entry = false
        UpdateWorker(worker).then(res => {
          this.$message({
            type: 'success',
            message: 'Worker marked as departed!'
          });
        })
      })
    },

    handleDeleteWork(index, id) {
      DeleteWorker(id).then(res => {
        this.workerList.splice(index, 1)
        this.$message({
          type: 'success',
          message: 'Worker deleted successfully!'
        });
      })
    },

    handleListRole(worker) {
      this.roleDialogVisible = true;
      this.loading = true;
      FindWorkerRoles(worker.id).then(res => {
        setTimeout(() => {
          this.roleList = res.data
          this.loading = false;
        }, 2000)
      })
    },

    handleDeleteRole(index, id) {
      DeleteRoleByWorkerId(id).then(res => {
        if (res.success === true) {
          this.roleList.splice(index, 1)
          this.$message({
            type: 'success',
            message: 'Worker permission removed successfully!'
          });
        }
      })
    },

    handleAddRole(worker) {
      this.dialogFormVisible = true;
      this.addRoleForm.wid = worker.id;
    },

    submitAddRole() {
      CreateWorkerRole(this.addRoleForm).then(res => {
        if (res.success) {
          this.dialogFormVisible = false;
          this.addRoleForm.wid = '';
          this.addRoleForm.value = '';
          this.$message({
            type: 'success',
            message: 'Worker permission added successfully!'
          });
        }
      })
    },

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