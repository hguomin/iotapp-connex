<script setup lang="ts">
import { onMounted, reactive, ref } from "vue";
import SockJS  from "sockjs-client/dist/sockjs.js";
//import SockJS from "sockjs-client"
import Chart from "chart.js/auto";
import { Config } from "@/config";

let ws: any;
let message = ref("");
const dataPointCount = 40;
onMounted(() => {
  
  let chartEl: HTMLCanvasElement = document.getElementById("telemetry-chart") as HTMLCanvasElement;
  let telemetryChart = new Chart(chartEl, {
    type: "line",
    data: {
      labels: new Array<string>(),
      datasets: new Array<any>()
    },
    options: {
      responsive: true,
      plugins: {
        legend: {
          position: "top",
        },
        title: {
          display: true,
          text: "Datapoints Telemetry",
        },
      },
      scales: {
        x: {
          display: true,
          title: {
            display: true,
            text: "Timestamp",
          },
        },
        y: {
          display: true,
          title: {
            display: true,
            text: "Value",
          }
        }
      }
    }
  });

  let dataNameToIdMap: Map<string, number> = new Map();

  ws = new SockJS(Config.host + "/ws_sockjs");
  ws.onopen = () => {
    console.log("connected.");
    ws.send("hello");
  };

  ws.onmessage = (msg: any) => {
    //message.value = JSON.stringify(msg.data);
    //msg.data.forEach((el:any) => {
    //  console.log(el);
    //});
    //console.log(msg.data);
    let dataPoints:any[] = JSON.parse(msg.data);
    dataPoints.forEach((el:any) => {
      if(!dataNameToIdMap.has(el.NodeId)) {

        dataNameToIdMap.set(el.NodeId, telemetryChart.data.datasets.length);
        
        let newData:any = {
          label: el.NodeId,
          data: new Array<number>(),
          borderColor: "#4dccf6",
          backgroundColor: "#eeeeee",
          //fill: false,
          //cubicInterpolationMode: 'monotone',
          //tension: 0.3
        };
        
        telemetryChart.data.datasets.push(newData)
        
      }

      let index: number = dataNameToIdMap.get(el.NodeId) as number;
      let dataset = telemetryChart.data.datasets[index];

      telemetryChart.data.labels?.push("");
      dataset.data.push(el.Value.Value);
      if(dataset.data.length > dataPointCount) {
        telemetryChart.data.labels?.shift();
        dataset.data.shift();
      }

      telemetryChart.update();

    });
  };

  ws.onclose = () => {
    console.log("closed");
  };
});
</script>

<template>
  <div class="flex flex-col flex-auto p-10" >
    <div calss="flex"> 
      <canvas id="telemetry-chart"></canvas>
    </div>
  </div>
</template>
