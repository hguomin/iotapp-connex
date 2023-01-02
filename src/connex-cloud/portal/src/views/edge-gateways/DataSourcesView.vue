<script setup lang="ts">
import { ref, reactive, useAttrs, onMounted, onActivated } from 'vue'
import { RouterLink, RouterView, useRoute } from 'vue-router'
import axios from 'axios'
import { Config } from '@/config'

//General data interfaces
interface EdgeDataConnector {
    deviceId: string;
    type: string;
    id: string;
    status?: string;
    image?: string;
    version?: string;
};

interface EdgeDataSource<TConf> {
    name: string;
    type: string;
    connector: string,
    tags: string;
    description: string;
    configuration: TConf
};

interface DataViewModel<TData> {
    isSelected?: boolean;
    name?: string;
    data: TData
};

interface DataListViewModel<TData> {
    [key: string]: DataViewModel<TData>
};


const route = useRoute();
const deviceId:string = route.params.id as string;

let connectorsViewModel: DataListViewModel<EdgeDataConnector> = reactive({});
let dataSrcAddViewModel: DataViewModel<EdgeDataSource<Object>> = reactive({
    data: initDataSrc()
});
let dataSrcListViewModel: DataListViewModel<EdgeDataSource<Object>> = reactive({});

function initDataSrc() {
    return {
        name: "",
        type: "",
        connector: "",
        tags: "",
        description: "",
        configuration: {}
    }
}

const ds = {
    "dataSources":[
        {
            "name":"ComServerWrapper",
            "driver": "OPC UA",
            "tags": "tag1",
            "description": "This is the data sources for geting OPC DA data",
            "EndpointUrl":"opc.tcp://10.2.0.4:48400/UA/ComServerWrapper",
        },
        {
            "name":"ComServerWrapper2",
            "driver": "OPC UA",
            "tags": "tag1",
            "description": "This is the data sources for geting OPC DA data",
            "EndpointUrl":"opc.tcp://10.2.0.6:48400/UA/ComServerWrapper",
        }
    ]
};

let open = ref(false);
let datas: any[] = [{}];
let dataSrcs = reactive(datas);

onMounted(() => {
    fetchDataConnectors();
    fetchDataSources(deviceId);
});

function toggle() {
    open.value = !open.value;
}

function fetchDataConnectors() {
    const url: string = Config.host + "/api/devices/" + deviceId + "/modules";
    let response = axios.get(url).then(r => {
                            console.log("fetchDataConnectors:");
                            console.log(r.data);

                            if(r.data instanceof Array) {
                                for(const k in connectorsViewModel) {
                                    delete connectorsViewModel[k];
                                }
                                for(let c of r.data) {
                                    connectorsViewModel[c.id] = {
                                        isSelected: false,
                                        name: c.id,
                                        data: c
                                    };
                                }
                            }
                        })
                        .catch(e => console.log("fetchDataConnectors - error: " + e));
}

function fetchDataSources(deviceId: string | string[]) {
    const url = Config.host + "/api/devices/" + deviceId +`/dataSources`;
    let response = axios.get(url)
        .then(r => {
            console.log("fetchDataSources:");
            console.log(r.data);
            if(r.data instanceof Array) {
                for(let k in dataSrcListViewModel) {
                    delete dataSrcListViewModel[k];
                }

                for(let ds of r.data) {
                    dataSrcListViewModel[ds.name] = {
                        isSelected: false,
                        name: ds.name,
                        data: ds
                    }
                }
            }
        })
        .catch(e => console.log("fetchDataSources - Error: " + e));
}

function addDataSource(deviceId: string | string[]) {
    const url = Config.host + "/api/devices/" + deviceId + "/dataSources";

    dataSrcAddViewModel.data.type = connectorsViewModel[dataSrcAddViewModel.data.connector].data.type;
     
    let response = axios.post(url, JSON.stringify(dataSrcAddViewModel.data), {
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    })
    .then(r => {
        console.log("addDataSource:");
        console.log(r.data)
        toggle();
        dataSrcAddViewModel.data = initDataSrc();

        fetchDataSources(deviceId);
    })
    .catch(e => console.log("Error: " + e));
}

function getUrls(id: string | string[], connectorType: string, connector:string, dataSrcName: string): string {
  return "/edge-gateways/" + id + "/data-sources/" + connectorType + "/" + connector + "/" + dataSrcName;
}


</script>

<template>
  <!--relative is for reference height for the right side-editor-panel-->
  <div class="relative flex flex-auto">
    <div class="flex flex-col flex-auto px-5">
        <!--h1 class="font-medium pt-3 pb-0.5">Data Sources</h1-->
        <div class="flex space-x-2 mt-3">
            <button @click="toggle()" class="flex items-center pl-2 pr-3 py-1.5 rounded-sm hover:bg-gray-200">
                <span class="mr-1"><i class="las la-plus la-lg"></i></span>
                <span>Add</span>
            </button>
            <button class="flex items-center pl-2 pr-3 py-1.5 rounded-sm hover:bg-gray-200">
                <span class="mr-1"><i class="lar la-edit la-lg"></i></span>
                <span>Edit</span>
            </button>
            <button class="flex items-center pl-2 pr-3 py-1.5 rounded-sm hover:bg-gray-200">
                <span class="mr-1"><i class="las la-trash la-lg"></i></span>
                <span>Delete</span>
            </button>
            <button @click="fetchDataSources(deviceId)" class="flex items-center pl-2 pr-3 py-1.5 rounded-sm hover:bg-gray-200">
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
                    <th class="text-left px-5 min-w-max">Name</th>
                    <th class="text-left px-5">Connector</th>
                    <th class="text-left px-5">Connector Type</th>
                    <th class="text-left px-5">Tags</th>
                    <th class="text-left px-5">Description</th>
                </tr>
                <tr v-for="(v, k, i) in dataSrcListViewModel" :key="i" class="border-b h-10 hover:bg-slate-200 ">
                    <td class="w-6">
                        <span class="flex justify-center">
                            <input v-model="v.isSelected" type="checkbox" class="checkbox" />
                        </span>
                    </td>
                    <td class="px-5">
                        <router-link :to="getUrls(deviceId, v.data.type, v.data.connector, v.data.name)" class="text-blue-600 underline decoration-blue-600 ">{{ v.data.name }}</router-link>
                    </td>
                    <td class="px-5">{{ v.data.connector }}</td>
                    <td class="px-5">{{ v.data.type}}</td>
                    <td class="px-5">{{ v.data.tags }}</td>
                    <td class="px-5">{{ v.data.description }}</td>
                </tr>
            </table>
        </div>
    </div>
    <!--Side panel-->
    <div id="side-editor-panel" :class="['absolute h-full right-0 overflow-hidder bg-slate-50 shadow-2xl transition duration-1000', open? 'flex' : 'hidden']">
        <div class="flex flex-col flex-auto">
            <!--form class="flex flex-col flex-auto" @submit.prevent="addDataSource($route.params.id)"-->
                <div class="flex flex-col border-b border-gray-400 px-5 py-1">
                    <div class="flex flex-auto">
                        <span class="grow"></span>
                        <button @click="toggle()" class="justify-items-end hover:bg-slate-100 p-1">
                            <i class="las la-times la-lg"></i>
                        </button>
                    </div>
                    <h1 class="grow text-lg font-semibold ">Add data source</h1>    
                    <p class="text-sm text-gray-600">Setup data collection module and get data from where it is produced.</p>
                </div>
                <div class="grow flex flex-col px-5 py-5 space-y-5">
                    <div class="flex flex-col">
                        <label for="name" class="font-semibold pb-1.5">Name <span class="text-red-600">*</span></label>
                        <input id="name" v-model="dataSrcAddViewModel.data.name" class="p-1 border border-gray-400 outline-none" />
                    </div>
                    <div class="flex flex-col">
                        <label for="driver-type" class="font-semibold pb-1.5">Connector<span class="text-red-600">*</span></label>
                        <select id="driver-type" v-model="dataSrcAddViewModel.data.connector" class="p-1 border border-gray-400 outline-none">
                            <option v-for="(v, k, i) in connectorsViewModel" :key="i" :value="k" class="outline-none px-2 py-5" >
                                <span >{{v.data.type}}@<span class="font-semibold">{{v.data.id}}</span></span> 
                            </option>
                        </select>
                    </div>   
                    <!--div class="flex flex-col">
                        <label for="driver-type" class="font-semibold pb-1.5">Connector type <span class="text-red-600">*</span></label>
                        <select id="driver-type" v-model="dataSrc.type" class="p-1 border border-gray-400 outline-none">
                            <option value="opc-ua" class="outline-none">OpcUa</option>
                            <option value="modbus" class="outline-none">Modbus</option>
                            <option value="ethnet-ip" class="outline-none">Ethernet/IP</option>
                            <option value="bacnet" class="outline-none">BacNet</option>
                        </select>
                    </div-->     
                    <div class="flex flex-col">
                        <label for="tags" class="font-semibold pb-1.5">Tags</label>
                        <input id="tags" v-model="dataSrcAddViewModel.data.tags" class="p-1 border border-gray-400 outline-none" />
                    </div>
                    <div class="flex flex-col">
                        <label for="description" class="font-semibold pb-1.5">Description</label>
                        <textarea id="description" v-model="dataSrcAddViewModel.data.description" class="p-1 border border-gray-400 outline-none"></textarea>
                    </div>           
                    <span class="grow"></span>
                </div>
                <div class="flex">
                    <button @click="addDataSource(deviceId)" class="w-20  py-1 border border-gray-600 bg-slate-800 text-white mb-5 ml-5">Save</button>
                </div>
            <!--/form-->
        </div>
    </div>
  </div>
</template>
