<template>
  <div class="main">

    <el-table
        v-loading="loading"
        :data="list"
        style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="ID: ">
              <span>{{ props.row.id }}</span>
            </el-form-item>
            <el-form-item label="Order ID: ">
              <span>{{ props.row.oid }}</span>
            </el-form-item>
            <el-form-item label="Issue reason: ">
              <span>{{ props.row.reason }}</span>
            </el-form-item>
            <el-form-item label="Reported by: ">
              <span>{{ props.row.reviewer }}</span>
            </el-form-item>
            <el-form-item label="Resolved: ">
              <span>{{ props.row.status }}</span>
            </el-form-item>
            <el-form-item label="Resolution: ">
              <span>{{ props.row.result }}</span>
            </el-form-item>
            <el-form-item label="Reported at: ">
              <span>{{ props.row.createAt }}</span>
            </el-form-item>
            <el-form-item label="Resolved at: ">
              <span>{{ props.row.endAt }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
          label="Order ID"
          width="400"
          prop="oid">
      </el-table-column>
      <el-table-column
          width="200"
          label="Reported by"
          prop="reviewer">
      </el-table-column>
      <el-table-column label="Issue status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status" type="success">Issue resolved successfully</el-tag>
          <el-tag v-if="!scope.row.status" type="danger">Pending</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Actions">
        <template slot-scope="scope">
          <el-button @click="handle(scope.$index)" size="small" type="primary"
                     icon="el-icon-s-check"
                     plain>Handle issue
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="Resolve order issue" :visible.sync="dialogFormVisible">
      <el-form :model="form" style="width: 400px">
        <el-form-item label="Resolution" label-width="150px">
          <el-input v-model="form.result" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Issue status" label-width="150px">
          <el-select v-model="form.status" placeholder="Select an issue status">
            <el-option label="Resolved" value="true"></el-option>
            <el-option label="Not completed" value="false"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="submit">Confirm</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {CreateOrderException, HandleOrderException, ListOrderException, UpdateOrder} from "@/api/order";

export default {

  data() {
    return {
      loading: false,
      dialogFormVisible: false,
      list: [],
      form: {
        status: '',
        result: '',
      },
    }
  },

  mounted() {
    this.loadList()
  },

  methods: {

    loadList() {
      this.loading = true
      ListOrderException().then(res => {
        if (res.success) {
          setTimeout(() => {
            this.list = res.data
            this.loading = false
          }, 700)
        }
      })
    },

    handle(index) {
      this.dialogFormVisible = true
      this.list[index].status = this.form.status
      this.list[index].result = this.form.result
      this.form = this.list[index]
    },

    submit() {
      HandleOrderException(this.form).then(res => {
        if (res.success) {
          this.$message({
            type: 'success',
            message: 'Order resolved successfully!'
          });
          this.dialogFormVisible = false
          this.loadList()
        }
      })
    }

  }

}
</script>

<style scoped>
.main {
  padding: 30px;
}

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
  width: 100%;
}
</style>