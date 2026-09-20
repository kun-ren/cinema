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
          label="Film title"
          prop="film.name">
      </el-table-column>
      <el-table-column
          width="200"
          label="Booked seats"
          prop="order.seats">
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
    </el-table>

  </div>
</template>

<script>
import {CreateOrderException, FindAllOrder, UpdateOrder} from "@/api/order";

export default {

  data() {
    return {
      loading: false,
      orders: [],
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