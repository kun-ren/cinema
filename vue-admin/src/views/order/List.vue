<template>
  <div class="main">

    <el-table
        v-loading="loading"
        :data="orders"
        style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="Order ID: ">
              <span>{{ props.row.order.id }}</span>
            </el-form-item>
            <el-form-item label="User ID: ">
              <span>{{ props.row.user.id }}</span>
            </el-form-item>
            <el-form-item label="Film ID: ">
              <span>{{ props.row.film.id }}</span>
            </el-form-item>
            <el-form-item label="Screening ID: ">
              <span>{{ props.row.arrangement.id }}</span>
            </el-form-item>
            <el-form-item label="Film title: ">
              <span> {{ props.row.film.name }} </span>
            </el-form-item>
            <el-form-item label="Seat number: ">
              <span>{{ props.row.order.seats }}</span>
            </el-form-item>
            <el-form-item label="Order total: ">
              <span>{{ props.row.order.price }}</span>
            </el-form-item>
            <el-form-item label="Ordered at: ">
              <span>{{ props.row.order.createAt }}</span>
            </el-form-item>
            <el-form-item label="Paid at: ">
              <span>{{ props.row.order.payAt }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
          label="Order ID"
          width="400"
          prop="order.id">
      </el-table-column>
      <el-table-column
          width="200"
          label="Order total"
          prop="order.price">
      </el-table-column>
      <el-table-column label="Order status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.order.status === 2" type="success">Paid</el-tag>
          <el-tag v-if="scope.row.order.status === 0" type="info">Awaiting payment</el-tag>
          <el-tag v-if="scope.row.order.status === 3" type="warning">Canceled</el-tag>
          <el-tag v-if="scope.row.order.status === 1" type="danger">Payment failed</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Actions" width="250">
        <template slot-scope="scope">
          <el-button @click="handle1(scope.$index, scope.row.order)" size="small" type="warning"
                     icon="el-icon-refresh-right"
                     :disabled="scope.row.order.status === 3"
                     plain>Cancel order
          </el-button>
          <el-button @click="handle2(scope.$index, scope.row.order)" size="small" type="danger"
                     icon="el-icon-circle-close"
                     plain>Report issue
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="Report an order issue" :visible.sync="dialogFormVisible">
      <el-form :model="form" style="width: 400px">
        <el-form-item style="width: 100%" label="Reported by" label-width="150px">
          <el-input v-model="form.reason" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item style="width: 100%" label="Reason" label-width="150px">
          <el-input v-model="form.reviewer" autocomplete="off"></el-input>
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
import {CreateOrderException, FindAllOrder, UpdateOrder} from "@/api/order";

export default {

  data() {
    return {
      loading: false,
      dialogFormVisible: false,
      orders: [],
      form: {
        oid: '',
        reason: '',
        reviewer: '',
      },
    }
  },

  mounted() {
    this.loadOrderList()
  },

  methods: {

    loadOrderList() {
      this.loading = true
      FindAllOrder().then(res => {
        if (res.success) {
          setTimeout(() => {
            this.orders = res.data
            this.loading = false
          }, 700)
        }
      })
    },

    handle1(index, order) {
      this.orders[index].order.status = 3
      UpdateOrder(this.orders[index].order).then(res => {
        console.log(res)
        this.loadOrderList();
        this.$message({
          type: 'success',
          message: 'Order canceled successfully!'
        });
      })
    },

    handle2(index, order) {
      this.form.oid = order.id
      this.dialogFormVisible = true
    },

    submit() {
      CreateOrderException(this.form).then(res => {
        if (res.success) {
          this.$message({
            type: 'success',
            message: 'Order issue reported successfully!'
          });
          this.dialogFormVisible = false
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