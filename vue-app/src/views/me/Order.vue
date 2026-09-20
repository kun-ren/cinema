<template>
  <div style="width: 100%">
    <h3 style="letter-spacing: 1px;font-weight: 400;padding-bottom: 20px">My orders</h3>

    <div v-loading="loading">
      <el-card v-for="(item, index) in orderList" :key="index" class="box-card">
        <div>
          <img class="item-film-img" alt="" :src="item.film.cover"/>
          <div style="float: left;">
            <div class="item-film-name">{{ item.film.name }}</div>
            <div class="item-film-seat">Booked seats : {{ item.order.seats }}</div>
            <div class="item-film-time">Ordered at : {{ item.order.createAt }}</div>
            <div v-if="item.order.status === 2" class="item-film-time">Paid at : {{ item.order.payAt }}</div>
          </div>
          <el-button type="text"
                     @click="handlePay(item.order, index)"
                     v-if="item.order.status === 0"
                     style="line-height: 75px"
                     class="o1">
            Awaiting payment
          </el-button>
          <el-tooltip class="item" effect="dark" content="Contact staff for help with your order" placement="top">
            <div v-if="item.order.status === 3" style="color: #E6A23C" class="o1">Order issue</div>
          </el-tooltip>
          <div v-if="item.order.status === 2" style="color: #67C23A" class="o1">Paid</div>
          <div v-if="item.order.status === 1" style="color: #F56C6C" class="o1">Payment timed out</div>
          <div class="o2">￥{{ item.order.price }}</div>
        </div>
      </el-card>
    </div>

    <el-dialog
        title="Scan to pay"
        :show-close="false"
        width="30%"
        :visible.sync="centerDialogVisible"
    >
      <div>
        <img class="c-img" src="../../assets/img/c.jpeg" alt="">
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="submitPay">Payment completed</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>
import {FindOrderByUser, PayForOrder} from "@/api/order";

export default {
  name: "Order",

  data() {
    return {
      centerDialogVisible: false,
      loading: false,
      orderList: [],
      payOrderId: '',
    }
  },

  mounted() {
    this.loadOrder()
  },

  methods: {

    loadOrder() {
      this.loading = true
      FindOrderByUser(localStorage.getItem("uid")).then(res => {
        setTimeout(() => {
          this.orderList = res.data
          this.loading = false
        }, 700)
      })
    },

    handlePay(order, index) {
      this.open(order, index);
    },

    open(order, index) {
      this.$confirm('Please confirm the order total of ' + order.price + ' CNY. Continue?', 'Notice', {
        confirmButtonText: 'Confirm payment',
        cancelButtonText: 'Cancel payment',
        type: 'success',
        center: true
      }).then(() => {
        this.loading = true
        this.centerDialogVisible = true
        this.payOrderId = order.id
      }).catch(() => {
        this.$message({
          type: 'warning',
          message: 'Payment canceled'
        });
      });
    },

    submitPay() {
      PayForOrder(this.payOrderId).then(res => {
        this.loadOrder();
        this.centerDialogVisible = false
        if (res.success) {
          this.$message({
            type: 'success',
            message: 'Payment successful!'
          });
        }
      })
    },

  },

}
</script>

<style scoped>

.box-card {
  margin-bottom: 10px;
}

>>> .el-card__header {
  background: #C0C4CC;
}

.item-film-img {
  float: left;
  width: 80px;
  height: 100px;
  padding-bottom: 10px;
  padding-right: 10px;
}

.item-film-name {
  letter-spacing: 2px;
  font-weight: 500;
  font-size: 18px;
  padding-bottom: 15px;
  padding-top: 1px;
}

.c-img {
  width: 100%;
  height: 100%;
}

.item-film-seat {
  letter-spacing: 1px;
  font-size: 12px;
  padding-bottom: 5px;
  padding-left: 10px;
  color: #91949c;
}


.item-film-time {
  letter-spacing: 1px;
  font-size: 12px;
  padding-left: 10px;
  padding-bottom: 5px;
  color: #91949c;
}

.o1 {
  float: right;
  line-height: 100px;
  padding-right: 20px
}

.o2 {
  font-size: 20px;
  float: right;
  line-height: 100px;
  padding-right: 100px
}
</style>