<template>
<div>
  <el-row>
    <el-col :span="12">
      <div id="main" style="width: 500px ;height: 400px">  </div>
    </el-col>
    <el-col :span="12">
      <div id="pie" style="width: 500px ;height: 400px">  </div>
    </el-col>
  </el-row>



</div>
</template>

<script>
import * as echarts from 'echarts';


export default {
  name: "Home",
  // data(){
  //
  // },
  mounted() {  //页面元素渲染好之后再触发
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    var option;

    option = {
      xAxis: {
        type: 'category',
        data: []
        // 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [150, 230, 224, 218, 135, 147, 260],
          type: 'line'
        },
        {
          data: [150, 230, 224, 218, 135, 147, 260],
          type: 'bar'
        }
      ]
    };
    this.request.get("/echarts/example").then(res =>{
      //填空
      option.xAxis.data=res.data.x
      option.series[0].data=res.data.y
      option.series[1].data=res.data.y
      option && myChart.setOption(option);
    })


    //饼图
   var pieOption = {
      title: {
        text: 'Referer of a Website',
        subtext: 'Fake Data',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: 'Access From',
          type: 'pie',
          radius: '50%',
          data: [
            { value: 1048, name: 'Search Engine' },
            { value: 735, name: 'Direct' },
            { value: 580, name: 'Email' },
            { value: 484, name: 'Union Ads' },
            { value: 300, name: 'Video Ads' }
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };
    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);
    pieChart.setOption(pieOption);

  }
}
</script>

<style scoped>

</style>