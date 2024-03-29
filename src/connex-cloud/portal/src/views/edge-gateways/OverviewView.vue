<script setup lang="ts">
import axios from "axios";
import {reactive, ref, onMounted } from "vue";
import {useRoute} from "vue-router";
import { Config } from "@/config";

const route = useRoute();
const deviceId: string = route.params.id as string; 
let open = ref(false);

interface EdgeDataConnector {
    deviceId: string;
    type: string;
    id: string;
    status?: string;
    image?: string;
    version?: string;
};

interface IoTEdgeDevice {
    id: string;
    displayName: string;
    key: string;
    status: string;
    connectionState: string;
}

let device: IoTEdgeDevice = reactive({
    id: deviceId,
    displayName: deviceId,
    key: "--",
    status: "--",
    connectionState: "--"
});

let connectors: EdgeDataConnector[] = reactive(new Array<EdgeDataConnector>());
let connectorAddViewModel: EdgeDataConnector = reactive({
    id: "",
    type: "",
    deviceId: deviceId,
});

onMounted(() => {
    fetchDevice();
    fetchDataConnectors();
})

function toggle() {
    open.value = !open.value;
    initConnectorAddViewModel();
}

function initConnectorAddViewModel() {
    connectorAddViewModel.id = "";
    connectorAddViewModel.deviceId = deviceId;
    connectorAddViewModel.type = "";
}

function deployDataConnector() {
    const url: string = Config.host + "/api/devices/" + deviceId + "/modules";
    let response = axios.post(url, 
                            JSON.stringify(connectorAddViewModel), 
                            {
                                headers: {
                                    'Content-Type': 'application/json;charset=UTF-8'
                                }
                            })
                        .then(r => {
                            if(null != r.data && '' != r.data) {
                                connectors.push(r.data);
                            }
                            else {
                                alert( connectorAddViewModel.type + " currently not support. ");
                            }
                            console.log("deployDataConnector:")
                            console.log(r.data)
                        })
                        .catch(e => console.log("Error: " + e));

    toggle();
}

function fetchDataConnectors() {
    const url: string = Config.host + "/api/devices/" + deviceId + "/modules";
    let response = axios.get(url).then(r => {
                            console.log("fetchDataConnectors:");
                            console.log(r.data);
                            connectors.length = 0;
                            for(let c of r.data) {
                                connectors.push(c);
                            }
                        })
                        .catch(e => console.log("fetchDataConnectors - error: " + e));
}

function fetchDevice() {
    const url = Config.host + "/api/devices/" + deviceId;
    axios.get(url)
        .then(r => {
            console.log("fetchDevice:");
            console.log(r.data);

            Object.assign(device, r.data);
        })
        .catch(e => console.log("fetchDevices - error: " + e));
}
</script>

<template>
<!--relative is for reference height for the right side-editor-panel-->
  <div class="relative flex flex-col flex-auto">
    <div class="flex flex-col  space-y-5 px-5 pt-5">
        <!--card: overview-->
        <div class="flex flex-col bg-slate-50 shadow ">
            <div class="font-semibold border-b border-gray-500 py-1.5">
                <span class="px-3">Device details</span>
            </div>
            <div class="relative flex px-3 py-2">
                <div class="flex flex-col w-1/2 space-y-3.5 py-2">
                    <div class="flex flex-col">
                        <span class="font-semibold">Device Id</span>
                        <span>{{ device.id }}</span>
                    </div>
                    <div class="flex flex-col">
                        <span class="font-semibold">Primary key</span>
                        <span>{{ device.key }}</span>
                    </div>
                </div>
                <div class="grow flex flex-col space-y-3 py-2">
                    <div class="flex flex-col">
                        <span class="font-semibold">Status</span>
                        <span>{{ device.status }}</span>
                    </div>
                    <div class="flex flex-col">
                        <span class="font-semibold">Connection state</span>
                        <span>{{ device.connectionState }}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="flex flex-col flex-auto px-5 mt-5 ">
        <!--div class="flex flex-col bg-slate-50 shadow "-->
            <div class="grow flex flex-col flex-auto">
                <span class="border-b border-gray-500  font-semibold  px-3 py-1.5 bg-slate-50 shadow">Connectors on device</span>
                        <!--h1 class="font-medium pt-3 pb-0.5">Data Sources</h1-->
                <div class="flex space-x-2 px-1 pt-2 bg-slate-50 shadow">
                    <button @click="toggle()" class="flex items-center pl-2 pr-3 py-1.5 rounded-sm hover:bg-gray-200">
                        <span class="mr-1"><i class="las la-plus la-lg"></i></span>
                        <span>Add</span>
                    </button>
                    <!--button @click="getDataConnectors()" class="flex items-center pl-2 pr-3 py-1.5 rounded-sm hover:bg-gray-200">
                        <span class="mr-1"><i class="lar la-edit la-lg"></i></span>
                        <span>Edit</span>
                    </button-->
                    <button class="flex items-center pl-2 pr-3 py-1.5 rounded-sm hover:bg-gray-200">
                        <span class="mr-1"><i class="las la-trash la-lg"></i></span>
                        <span>Delete</span>
                    </button>
                    <button @click="fetchDataConnectors()"  class="flex items-center pl-2 pr-3 py-1.5 rounded-sm hover:bg-gray-200">
                        <span class="mr-1"><i class="las la-sync la-lg"></i></span>
                        <span>Refresh</span>
                    </button>
                    <span class="grow"></span>
                    <button class="flex items-center pl-2 pr-3 py-1.5 rounded-sm hover:bg-gray-200">
                        <span class="mr-1"><i class="las la-filter la-lg"></i></span>
                        <span>Filter</span>
                    </button>
                    <button class="flex items-center pl-2 pr-3 py-1.5 rounded-sm hover:bg-gray-200">
                        <span class="mr-1"><i class="las la-times la-lg"></i></span>
                        <span>Clear all</span>
                    </button>
                </div>
                <div class="grow flex flex-col flex-auto bg-slate-50 px-1 py-2 mb-5 shadow">
                    <table class="table-auto">
                        <tr class="border-b-2 h-12">
                            <th class="w-6">
                                <span class="flex justify-center">
                                    <input type="checkbox" class="checkbox" />
                                </span>
                            </th>
                            <th class="text-left px-5 min-w-max">Connector Name</th>
                            <th class="text-left px-5">Connector Type</th>
                            <th class="text-left px-5">Status</th>
                            <th class="text-left px-5">Image</th>
                            <th class="text-left px-5">Version</th>
                        </tr>
                        <tr v-for="conn in connectors" :key="conn.id" class="border-b h-10 hover:bg-slate-200 ">
                            <td class="w-6">
                                <span class="flex justify-center">
                                    <input type="checkbox" class="checkbox" />
                                </span>
                            </td>
                            <td class="px-5">
                                <router-link :to="route.fullPath" class="text-blue-600 underline decoration-blue-600 ">{{ conn.id }}</router-link>
                            </td>
                            <td class="px-5">{{ conn.type }}</td>
                            <td class="px-5">{{ conn.status }}</td>
                            <td class="px-5">{{ conn.image }}</td>
                            <td class="px-5">{{ conn.version }}</td>
                        </tr>
                    </table>
                </div>
            </div>
        <!--/div-->

    </div>
    <!--Side panel-->
    <div id="side-editor-panel" :class="['absolute h-full w-2/5 right-0 overflow-hidder bg-slate-50 shadow-2xl transition duration-1000', open? 'flex' : 'hidden']">
        <div class="flex flex-col flex-auto">
            <!--form class="flex flex-col flex-auto" @submit.prevent="deployDataConnector()"-->
                <div class="flex flex-col border-b border-gray-400 px-5 py-1">
                    <div class="flex flex-auto">
                        <span class="grow"></span>
                        <button @click="toggle()" class="justify-items-end hover:bg-slate-100 p-1">
                            <i class="las la-times la-lg"></i>
                        </button>
                    </div>
                    <h1 class="grow text-lg font-semibold ">Add data connector</h1>    
                    <p class="text-sm text-gray-600">Add a new data connector and deploy it to this device.</p>
                </div>
                <div class="grow flex flex-col px-5 py-5 space-y-5">
                    <div class="flex flex-col">
                        <label for="name" class="font-semibold pb-1.5">Connector name <span class="text-red-600">*</span></label>
                        <input id="name" v-model="connectorAddViewModel.id" class="p-1 border border-gray-400 outline-none" />
                    </div>
                    <div class="flex flex-col">
                        <label for="driver-type" class="font-semibold pb-1.5">Connector type <span class="text-red-600">*</span></label>
                        <select id="driver-type" v-model="connectorAddViewModel.type" class="p-1 border border-gray-400 outline-none">
                            <option value="opc-ua" class="outline-none">OpcUa</option>
                            <option value="modbus" class="outline-none">Modbus</option>
                            <option value="ethernet-ip" class="outline-none">EthernetIP</option>
                            <option value="bacnet" class="outline-none">BacNet</option>
                        </select>
                    </div>          
                    <span class="grow"></span>
                </div>
                <div class="flex">
                    <button @click="deployDataConnector()" class="w-20  py-1 border border-gray-600 bg-slate-800 text-white mb-5 ml-5">Deploy</button>
                </div>
            <!--/form-->
        </div>
    </div>
  </div>
    
</template>