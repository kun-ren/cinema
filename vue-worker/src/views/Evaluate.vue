<template>
  <div style="padding: 40px">
    <el-table :data="evaluateList">
      <el-table-column property="createAt" label="Date" width="250"></el-table-column>
      <el-table-column property="content" label="Review"></el-table-column>
      <el-table-column property="type" width="150">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type==='Very satisfied'" type="success">Very satisfied</el-tag>
          <el-tag v-if="scope.row.type==='Satisfied'" type="info">Satisfied</el-tag>
          <el-tag v-if="scope.row.type==='Dissatisfied'" type="danger">Dissatisfied</el-tag>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {ListWorkerEvaluate} from "@/api/leaving";

export default {
  name: "Evaluate",

  data() {
    return {
      evaluateList: []
    }
  },

  mounted() {
    ListWorkerEvaluate(localStorage.getItem("wid")).then(res => {
      this.evaluateList = res.data
    })
  },

}
</script>

<style scoped>

</style>