<template>
    <div class="relative flex flex-col flex-auto px-5">
        <h1 class="text-lg font-medium pt-3 pb-0.5">{{$route.params.dataSrcName}}</h1>
        <div class="flex space-x-3 mt-2">
            <button @click="saveDataSource()" class="px-1 py-2 hover:bg-slate-200">
                <span class="mr-1"><i class="las la-save la-lg"></i></span>
                <span>Save</span>
            </button>
            <button class="px-1 py-2 hover:bg-slate-200">
                <span class="mr-1"><i class="las la-sync la-lg"></i></span>
                <span>Refresh</span>
            </button>
        </div>
        <div class="flex flex-auto mt-3 mb-5 space-x-5">
            <div class="flex flex-col w-1/2 space-y-5">
                <!--card: overview-->
                <div class="flex flex-col bg-slate-50 shadow">
                    <div class="font-semibold border-b border-gray-500 py-1.5">
                        <span class="px-3">Details</span>
                    </div>
                    <div class="relative flex px-3">
                        <div class="flex flex-col w-1/2 space-y-3.5 py-2">
                            <div class="flex flex-col">
                                <span class="font-semibold">Name</span>
                                <span>{{dataSrcViewModel.data.name}}</span>
                            </div>
                            <div class="flex flex-col">
                                <span class="font-semibold">Driver type</span>
                                <span>{{dataSrcViewModel.data.type}}</span>
                            </div>
                            <div class="flex flex-col">
                                <span class="font-semibold">Tags</span>
                                <span>{{dataSrcViewModel.data.tags}}</span>
                            </div>
                        </div>
                        <div class="grow flex flex-col space-y-3 py-2">
                            <div class="flex flex-col">
                                <span class="font-semibold">Created by</span>
                                <span>Guomin Huang</span>
                            </div>
                            <div class="flex flex-col">
                                <span class="font-semibold">Created by</span>
                                <span>May 7, 2022 10:41AM</span>
                            </div>
                        </div>
                    </div>
                </div>
                <!--card: Driver setup-->
                <div class="flex flex-col bg-slate-50 shadow">
                    <div class="flex font-semibold border-b border-gray-500 py-1.5 pr-1">
                        <span class="grow px-3">Driver details</span>
                        <button @click="toggleDriverDetailsEditor()" class="px-1 hover:bg-slate-100"><i class="las la-edit la-lg"></i></button>
                    </div>
                    <div class="relative flex flex-auto px-3">
                        <div class="grow flex flex-col space-y-3.5 py-2">
                            <div class="flex flex-col">
                                <span class="font-semibold">OPC UA server</span>
                                <div class="flex flex-auto">
                                    <div class="flex w-full">
                                        <label class="font-semibold mr-2">Host:</label>
                                        <span>{{dataSrcViewModel.data.configuration.EndpointUrl}}</span>
                                    </div>
                                    <!--div class="flex">
                                        <label class="font-semibold mr-2">Port:</label>
                                        <span>--</span>
                                    </div-->
                                </div>
                            </div>
                            <div class="flex flex-col">
                                <span class="font-semibold">Authentication</span>
                                <div class="flex flex-auto">
                                    <div class="flex w-1/2">
                                        <label class="font-semibold mr-2">Username:</label>
                                        <span>{{dataSrcViewModel.data.configuration.Username}}</span>
                                    </div>
                                    <div class="flex">
                                        <label class="font-semibold mr-2">Password:</label>
                                        <span>{{dataSrcViewModel.data.configuration.Password}}</span>
                                    </div>
                                </div>
                            </div>
                            <div class="flex flex-col">
                                <span class="font-semibold">Driver Information</span>
                                <div class="flex flex-auto">
                                    <div class="flex w-1/2">
                                        <label class="font-semibold mr-2">Image:</label>
                                        <span class="underline">gmdevcr.azurecr.io/iot/opcua-publisher:2.5.6</span>
                                    </div>
                                    <div class="flex">
                                        <label class="font-semibold mr-2">Version:</label>
                                        <span>2.5.6</span>
                                    </div>
                                </div>
                            </div>
                            <div class="flex flex-col">
                                <span class="font-semibold">Status</span>
                                <span>Running</span>
                            </div>
                        </div>
                    </div>
                </div>

                <!--card: Description-->
                <div class="flex flex-col bg-slate-50 shadow">
                    <div class="flex font-semibold border-b border-gray-500 py-1.5 pr-1">
                        <span class="grow px-3">Description</span>
                        <button class="px-1 hover:bg-slate-100"><i class="las la-edit la-lg"></i></button>
                    </div>
                    <div class="relative flex px-3">
                        <p class="py-2">{{dataSrcViewModel.data.description}}</p>
                    </div>
                </div>
            </div>
            <!--data points-->
            <div class="grow flex flex-col bg-slate-50 shadow">
                <div class="flex font-semibold border-b border-gray-500 py-1.5 pr-1">
                    <span class="grow px-3">Data points</span>
                    <div class="flex space-x-3 px-1">
                        <button @click="toggleAddDataPointEditor()" class="flex px-1 items-center rounded-sm hover:bg-gray-200">
                            <span class="mr-1"><i class="las la-plus la-lg"></i></span>
                            <span>Add</span>
                        </button>
                        <button class="flex px-1 items-center rounded-sm hover:bg-gray-200">
                            <span class="mr-1"><i class="lar la-edit la-lg"></i></span>
                            <span>Edit</span>
                        </button>
                        <button @click="deleteDataPoints()" class="flex px-1 items-center rounded-sm hover:bg-gray-200">
                            <span class="mr-1"><i class="las la-trash la-lg"></i></span>
                            <span>Delete</span>
                        </button>
                        <button class="flex px-1 items-center rounded-sm hover:bg-gray-200">
                            <span class="mr-1"><i class="las la-sync la-lg"></i></span>
                            <span>Refresh</span>
                        </button>
                        <button class="flex px-1 items-center rounded-sm hover:bg-gray-200">
                            <span class="mr-1"><i class="las la-filter la-lg"></i></span>
                            <span>Filter</span>
                        </button>
                    </div>
                </div>
                <div class="grow relative flex flex-col px-3">
                    <table class="table-auto w-full">
                        <tr class="border-b h-8 w-full">
                            <th class="w-6">
                                <span class="flex justify-center">
                                    <input type="checkbox" class="checkbox" />
                                </span>
                            </th>
                            <th class="text-left font-semibold px-5 min-w-max">Id</th>
                            <!--th class="text-left font-semibold px-5">Display Name</th-->
                            <th class="text-left font-semibold px-5">Sample Interval</th>
                            <th class="text-left font-semibold px-5">Publish Interval</th>
                        </tr>
                        <tr v-for="(v, k, i) in dataPointListViewModel" :key="i"  class="border-b h-6 hover:bg-slate-200 ">
                            <td class="w-6">
                                <span class="flex justify-center">
                                    <input v-model="v.isSelected" type="checkbox" class="checkbox" />
                                </span>
                            </td>
                            <td class="px-5">
                                <router-link to="/" class="text-blue-600 underline decoration-blue-600 ">{{v.data.Id}}</router-link>
                            </td>
                            <!--td class="px-5">{{p.Id}}</td-->
                            <td class="px-5">{{v.data.OpcSamplingInterval}}</td>
                            <td class="px-5">{{v.data.OpcPublishingInterval}}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

        <!--Side panel: Edit driver details-->
        <div id="side-panel-add" :class="['absolute h-full w-2/5 right-0 overflow-hidder bg-slate-50 shadow-2xl transition duration-1000', driverDetailsEditor? 'flex' : 'hidden']">
            <div class="flex flex-col flex-auto">
                <div class="flex flex-col border-b border-gray-400 px-5 py-1">
                    <div class="flex flex-auto">
                        <span class="grow"></span>
                        <button @click="toggleDriverDetailsEditor()" class="justify-items-end hover:bg-slate-100 p-1">
                            <i class="las la-times la-lg"></i>
                        </button>
                    </div>
                    <h1 class="grow text-lg font-semibold ">Edit driver details</h1>    
                    <p class="text-sm text-gray-600">Edit driver details to start connect.</p>
                </div>
                <div class="grow flex flex-col px-5 py-5 space-y-5">
                    <div class="flex flex-col">
                        <label for="host" class="font-semibold pb-1.5">Host <span class="text-red-600">*</span></label>
                        <input id="host" v-model="dataSrcConfEditViewModel.data.EndpointUrl" class="p-1 border border-gray-400 outline-none" />
                    </div>
                        <!--div class="flex flex-col">
                            <label for="display-name" class="font-semibold pb-1.5">Port <span class="text-red-600">*</span></label>
                            <input id="display-name" class="p-1 border border-gray-400 outline-none" />
                        </div-->   
                    <div class="flex flex-col">
                        <label for="username" class="font-semibold pb-1.5">Username <span class="text-red-600">*</span></label>
                        <input id="username" v-model="dataSrcConfEditViewModel.data.Username" class="p-1 border border-gray-400 outline-none" />
                    </div>     
                    <div class="flex flex-col">
                        <label for="password" class="font-semibold pb-1.5">Password <span class="text-red-600">*</span></label>
                        <input id="password" v-model="dataSrcConfEditViewModel.data.Password" class="p-1 border border-gray-400 outline-none" />
                    </div> 
                </div>
                <span class="grow border-b"></span>
                <div class="flex justify-end space-x-5 p-5">
                    <button @click="editDriverDetails()" class="w-20 py-1 border border-gray-600 bg-slate-800 text-white rounded-sm">Save</button>
                    <button @click="toggleDriverDetailsEditor()" class="w-20 py-1 border border-gray-600 bg-slate-800 text-white rounded-sm">Cancel</button>
                </div>
            </div>
        </div>
        <!--End of Side panel: Edit driver details-->

        <!--Side panel: Add data point-->
        <div id="side-panel-add" :class="['absolute h-full right-0 overflow-hidder bg-slate-50 shadow-2xl transition duration-1000', addDataPointEditor? 'flex' : 'hidden']">

            <div class="flex flex-col flex-auto">
                <div class="flex flex-col border-b border-gray-400 px-5 py-1">
                    <div class="flex flex-auto">
                        <span class="grow"></span>
                        <button @click="toggleAddDataPointEditor()" class="justify-items-end hover:bg-slate-100 p-1">
                            <i class="las la-times la-lg"></i>
                        </button>
                    </div>
                    <h1 class="grow text-lg font-semibold ">Add data point</h1>    
                    <p class="text-sm text-gray-600">Add a opc ua data point for publishing to cloud.</p>
                </div>
                <div class="grow flex flex-col px-5 py-5 space-y-5">
                    <div class="flex flex-col">
                        <label for="Id" class="font-semibold pb-1.5">Id <span class="text-red-600">*</span></label>
                        <input id="id" v-model="dataPointAddViewModel.data.Id" class="p-1 border border-gray-400 outline-none" />
                    </div>
                    <!--div class="flex flex-col">
                        <label for="display-name" class="font-semibold pb-1.5">Display name</label>
                        <input id="display-name" class="p-1 border border-gray-400 outline-none" />
                    </div-->   
                    <div class="flex flex-col">
                        <label for="sample-interval" class="font-semibold pb-1.5">Sample interval</label>
                        <input id="sample-interval" v-model="dataPointAddViewModel.data.OpcSamplingInterval" class="p-1 border border-gray-400 outline-none" />
                    </div>     
                    <div class="flex flex-col">
                        <label for="publish-interval" class="font-semibold pb-1.5">Publish interval</label>
                        <input id="publish-interval" v-model="dataPointAddViewModel.data.OpcPublishingInterval" class="p-1 border border-gray-400 outline-none" />
                    </div> 
                </div>
                <span class="grow border-b"></span>
                <div class="flex justify-end space-x-5 p-5">
                    <button @click="addDataPoint()" class="w-20 py-1 border border-gray-600 bg-slate-800 text-white rounded-sm">Save</button>
                    <button @click="toggleAddDataPointEditor()" class="w-20 py-1 border border-gray-600 bg-slate-800 text-white rounded-sm">Cancel</button>
                </div>
            </div>

        </div>
        <!--End of Side panel: Add data point-->
    </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref, } from 'vue'
import { RouterLink, RouterView, useRoute } from 'vue-router'
import axios from 'axios'

let route = useRoute();
let addDataPointEditor = ref(false);
let driverDetailsEditor = ref(false)

//General data interfaces
interface EdgeDataSource<TConf> {
    name: string;
    type: string;
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

//Data models
interface OpcDataPoint {
    Id: string,
    OpcSamplingInterval: number,
    OpcPublishingInterval: number
};

interface OpcDataSourceConfiguration {
    EndpointUrl: string,
    Username: string,
    Password: string,
    UseSecurity: boolean,
    OpcAuthenticationMode: string,
    OpcNodes: OpcDataPoint[]
};

type OpcDataSource = EdgeDataSource<OpcDataSourceConfiguration>

function initDataPoint(): OpcDataPoint {
    return {
        Id: "",
        OpcSamplingInterval: 1000,
        OpcPublishingInterval: 1000,
    };
}

function initDataSourceConfiguration(): OpcDataSourceConfiguration {
    return {
        EndpointUrl: "",
        UseSecurity: false,
        OpcAuthenticationMode: "UsernamePassword",
        Username: "",
        Password: "",
        OpcNodes: []
    }
}

function initDataSource(): OpcDataSource {
    return {
        name: "",
        type: "",
        tags: "",
        description: "",
        configuration: initDataSourceConfiguration()
    }
}

//For saving data to backend
let dataSrcViewModel: DataViewModel<OpcDataSource> = reactive({
    data: initDataSource()
});

//For editing the driver configuration
let dataSrcConfEditViewModel: DataViewModel<OpcDataSourceConfiguration> = reactive({
    data: initDataSourceConfiguration()
})

//For add/edit/delete data points
let dataPointAddViewModel: DataViewModel<OpcDataPoint> = reactive({
    name: "",
    data: initDataPoint()
});
let dataPointListViewModel: DataListViewModel<OpcDataPoint> = reactive({});

onMounted(() => {
    fetchDataSource();
})

function fetchDataSource() {
    const url = `http://localhost:8080/api/devices/` + route.params.id + "/dataSources/opc-ua/" + route.params.dataSrcName;
    axios.get(url)
        .then(r => {
            if( 'object' === typeof r.data) {
                console.log("fetchDataSource:" + JSON.stringify(r.data));

                //for data save
                Object.assign(dataSrcViewModel.data, r.data);

                //for driver configuration
                Object.assign(dataSrcConfEditViewModel.data, r.data.configuration);

                //for data point list
                r.data.configuration.OpcNodes.forEach((node: any) => {
                    dataPointListViewModel[node.Id] = {
                        isSelected: false,
                        name: node.Id,
                        data: node
                    };
                });
            }
            else {
                console.log("No such data source in the module, or no this kind of connector was deployed.");
            }
        })
        .catch(e => {
            console.log("Error: " + e);
        })
}

function saveDataSource() {
    const url = `http://localhost:8080/api/devices/` + route.params.id +`/dataSources`

    let opcNodes: OpcDataPoint[] = [];
    for(let k in dataPointListViewModel) {
        opcNodes.push(dataPointListViewModel[k].data);
    }

    dataSrcViewModel.data.configuration.OpcNodes = opcNodes;

    axios.put(url, JSON.stringify(dataSrcViewModel.data), {
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    })
    .then(r => {
        console.log(r)
    })
    .catch(e => { 
        console.log("Error: " + e) 
    });
}

function toggleAddDataPointEditor() {
    addDataPointEditor.value = !addDataPointEditor.value;
}

function toggleDriverDetailsEditor() {
    driverDetailsEditor.value = !driverDetailsEditor.value;
}

function editDriverDetails() {
    //Sync configuration data
    dataSrcViewModel.data.configuration.EndpointUrl = dataSrcConfEditViewModel.data.EndpointUrl;
    dataSrcViewModel.data.configuration.Username = dataSrcConfEditViewModel.data.Username;
    dataSrcViewModel.data.configuration.Password = dataSrcConfEditViewModel.data.Password;

    toggleDriverDetailsEditor();
    /*
    const url = `http://localhost:8080/api/devices/` + route.params.id + "/dataSources/" + route.params.driverType + "/" + route.params.dataSrcName + "/config";

    let response = axios.post(url, JSON.stringify(driverDetails), {
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    })
    .then(r => {
        console.log("Edit driver details ok.")
    })
    .catch(e => { 
        console.log("Error: " + e);
    });
    console.log(route.params.id);
    console.log(route.params.dataSrcName);
    */
}

function addDataPoint() {

    dataPointListViewModel[dataPointAddViewModel.data.Id] = Object.assign({}, dataPointAddViewModel);

    //Reset add view model
    dataPointAddViewModel.isSelected = false;
    dataPointAddViewModel.name = "";
    dataPointAddViewModel.data = initDataPoint();

    toggleAddDataPointEditor();
}

function deleteDataPoints() {
    for(const k  in dataPointListViewModel) {
        if(dataPointListViewModel[k].isSelected) {
            delete dataPointListViewModel[k];
        }
    }
}

function getUrls(id: string, dataSrcName: string): string {
  return "/edge-gateways/" + id + "/data-sources/" + dataSrcName;
}

</script>