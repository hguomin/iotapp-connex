<template>
  <div class="relative flex flex-col flex-auto px-5">
    <h1 class="text-lg font-medium pt-3 pb-0.5">
      {{ $route.params.dataSrcName }}
    </h1>
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
                <span>{{ dataSrcViewModel.data.name }}</span>
              </div>
              <div class="flex flex-col">
                <span class="font-semibold">Driver type</span>
                <span>{{ dataSrcViewModel.data.type }}</span>
              </div>
              <div class="flex flex-col">
                <span class="font-semibold">Tags</span>
                <span>{{ dataSrcViewModel.data.tags }}</span>
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
            <button
              @click="toggleDriverDetailsEditor()"
              class="px-1 hover:bg-slate-100"
            >
              <i class="las la-edit la-lg"></i>
            </button>
          </div>
          <div class="relative flex flex-auto px-3">
            <div class="grow flex flex-col space-y-3.5 py-2">
              <div class="flex flex-col">
                <span class="font-semibold">Modbus slave node</span>
                <div class="flex flex-auto">
                  <div class="flex w-1/2">
                    <label class="font-semibold mr-2">Slave connection:</label>
                    <span>{{
                      dataSrcViewModel.data.configuration.SlaveConnection
                    }}</span>
                  </div>
                  <div class="flex">
                    <label class="font-semibold mr-2">Port:</label>
                    <span>502</span>
                  </div>
                </div>
              </div>
              <div class="flex flex-col">
                <span class="font-semibold">Settings</span>
                <div class="flex flex-auto">
                  <div class="flex w-1/2">
                    <label class="font-semibold mr-2">Version:</label>
                    <span>{{
                      dataSrcViewModel.data.configuration.Version
                    }}</span>
                  </div>
                  <div class="flex">
                    <label class="font-semibold mr-2">PublishInterval:</label>
                    <span>{{
                      dataSrcViewModel.data.configuration.PublishInterval
                    }}</span>
                  </div>
                </div>
              </div>
              <div class="flex flex-col">
                <span class="font-semibold">Driver Information</span>
                <div class="flex flex-auto">
                  <div class="flex w-1/2">
                    <label class="font-semibold mr-2">Image:</label>
                    <span class="underline">--</span>
                  </div>
                  <div class="flex">
                    <label class="font-semibold mr-2">Version:</label>
                    <span>--</span>
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
            <button class="px-1 hover:bg-slate-100">
              <i class="las la-edit la-lg"></i>
            </button>
          </div>
          <div class="relative flex px-3">
            <p class="py-2">This is the description</p>
          </div>
        </div>
      </div>
      <!--data points-->
      <div class="grow flex flex-col bg-slate-50 shadow">
        <div class="flex font-semibold border-b border-gray-500 py-1.5 pr-1">
          <span class="grow px-3">Data points</span>
          <div class="flex space-x-3 px-1">
            <button
              @click="toggleAddDataPointEditor()"
              class="flex px-1 items-center rounded-sm hover:bg-gray-200"
            >
              <span class="mr-1"><i class="las la-plus la-lg"></i></span>
              <span>Add</span>
            </button>
            <button class="flex px-1 items-center rounded-sm hover:bg-gray-200">
              <span class="mr-1"><i class="lar la-edit la-lg"></i></span>
              <span>Edit</span>
            </button>
            <button
              @click="deleteDataPoints()"
              class="flex px-1 items-center rounded-sm hover:bg-gray-200"
            >
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
              <th class="text-left font-semibold px-5">Display Name</th>
              <th class="text-left font-semibold px-5">Start Address</th>
              <th class="text-left font-semibold px-5">Count</th>
              <th class="text-left font-semibold px-5">Polling Interval</th>
            </tr>
            <tr
              v-for="(v, k, i) in dataPointListViewModel"
              :key="i"
              class="border-b h-6 hover:bg-slate-200"
            >
              <td class="w-6">
                <span class="flex justify-center">
                  <input
                    v-model="v.isSelected"
                    type="checkbox"
                    class="checkbox"
                  />
                </span>
              </td>
              <td class="px-5">
                <router-link
                  to="/"
                  class="text-blue-600 underline decoration-blue-600"
                  >{{ k }}</router-link
                >
              </td>
              <!--td class="px-5">{{p.Id}}</td-->
              <td class="px-5">{{ v.data.DisplayName }}</td>
              <td class="px-5">{{ v.data.StartAddress }}</td>
              <td class="px-5">{{ v.data.Count }}</td>
              <td class="px-5">{{ v.data.PollingInterval }}</td>
            </tr>
          </table>
        </div>
      </div>
    </div>

    <!--Side panel: Edit driver details-->
    <div
      id="side-panel-add"
      :class="[
        'absolute h-full w-2/5 right-0 overflow-hidder bg-slate-50 shadow-2xl transition duration-1000',
        driverDetailsEditor ? 'flex' : 'hidden',
      ]"
    >
      <div class="flex flex-col flex-auto">
        <div class="flex flex-col border-b border-gray-400 px-5 py-1">
          <div class="flex flex-auto">
            <span class="grow"></span>
            <button
              @click="toggleDriverDetailsEditor()"
              class="justify-items-end hover:bg-slate-100 p-1"
            >
              <i class="las la-times la-lg"></i>
            </button>
          </div>
          <h1 class="grow text-lg font-semibold">Edit driver details</h1>
          <p class="text-sm text-gray-600">
            Edit driver details to start connect.
          </p>
        </div>
        <div class="grow flex flex-col px-5 py-5 space-y-5">
          <div class="flex flex-col">
            <label for="host" class="font-semibold pb-1.5"
              >Host <span class="text-red-600">*</span></label
            >
            <input
              id="host"
              v-model="dataSrcConfEditViewModel.data.SlaveConnection"
              class="p-1 border border-gray-400 outline-none"
            />
          </div>
          <!--div class="flex flex-col">
                            <label for="display-name" class="font-semibold pb-1.5">Port <span class="text-red-600">*</span></label>
                            <input id="display-name" class="p-1 border border-gray-400 outline-none" />
                        </div-->
          <div class="flex flex-col">
            <label for="username" class="font-semibold pb-1.5"
              >Publish Interval <span class="text-red-600">*</span></label
            >
            <input
              id="username"
              v-model="dataSrcConfEditViewModel.data.PublishInterval"
              class="p-1 border border-gray-400 outline-none"
            />
          </div>
          <div class="flex flex-col">
            <label for="password" class="font-semibold pb-1.5"
              >Version <span class="text-red-600">*</span></label
            >
            <input
              id="password"
              v-model="dataSrcConfEditViewModel.data.Version"
              class="p-1 border border-gray-400 outline-none"
            />
          </div>
        </div>
        <span class="grow border-b"></span>
        <div class="flex justify-end space-x-5 p-5">
          <button
            @click="editDataSourceConfiguration()"
            class="
              w-20
              py-1
              border border-gray-600
              bg-slate-800
              text-white
              rounded-sm
            "
          >
            Save
          </button>
          <button
            @click="toggleDriverDetailsEditor()"
            class="
              w-20
              py-1
              border border-gray-600
              bg-slate-800
              text-white
              rounded-sm
            "
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
    <!--End of Side panel: Edit driver details-->

    <!--Side panel: Add data point-->
    <div
      id="side-panel-add"
      :class="[
        'absolute h-full right-0 overflow-hidder bg-slate-50 shadow-2xl transition duration-1000',
        addDataPointEditor ? 'flex' : 'hidden',
      ]"
    >
      <div class="flex flex-col flex-auto">
        <div class="flex flex-col border-b border-gray-400 px-5 py-1">
          <div class="flex flex-auto">
            <span class="grow"></span>
            <button
              @click="toggleAddDataPointEditor()"
              class="justify-items-end hover:bg-slate-100 p-1"
            >
              <i class="las la-times la-lg"></i>
            </button>
          </div>
          <h1 class="grow text-lg font-semibold">Add data point</h1>
          <p class="text-sm text-gray-600">
            Add a opc ua data point for publishing to cloud.
          </p>
        </div>
        <div class="grow flex flex-col px-5 py-5 space-y-5">
          <div class="flex flex-col">
            <label for="Id" class="font-semibold pb-1.5"
              >Id <span class="text-red-600">*</span></label
            >
            <input
              id="id"
              v-model="dataPointAddViewModel.name"
              class="p-1 border border-gray-400 outline-none"
            />
          </div>
          <!--div class="flex flex-col">
                        <label for="display-name" class="font-semibold pb-1.5">Display name</label>
                        <input id="display-name" class="p-1 border border-gray-400 outline-none" />
                    </div-->
          <div class="flex flex-col">
            <label for="sample-interval" class="font-semibold pb-1.5"
              >Display Name</label
            >
            <input
              id="sample-interval"
              v-model="dataPointAddViewModel.data.DisplayName"
              class="p-1 border border-gray-400 outline-none"
            />
          </div>
          <div class="flex flex-col">
            <label for="publish-interval" class="font-semibold pb-1.5"
              >Start Address</label
            >
            <input
              id="publish-interval"
              v-model="dataPointAddViewModel.data.StartAddress"
              class="p-1 border border-gray-400 outline-none"
            />
          </div>
          <div class="flex flex-col">
            <label for="publish-interval" class="font-semibold pb-1.5"
              >Count</label
            >
            <input
              id="publish-interval"
              v-model="dataPointAddViewModel.data.Count"
              class="p-1 border border-gray-400 outline-none"
            />
          </div>
          <div class="flex flex-col">
            <label for="publish-interval" class="font-semibold pb-1.5"
              >Polling Interval</label
            >
            <input
              id="publish-interval"
              v-model="dataPointAddViewModel.data.PollingInterval"
              class="p-1 border border-gray-400 outline-none"
            />
          </div>
        </div>
        <span class="grow border-b"></span>
        <div class="flex justify-end space-x-5 p-5">
          <button
            @click="addDataPoint()"
            class="
              w-20
              py-1
              border border-gray-600
              bg-slate-800
              text-white
              rounded-sm
            "
          >
            Save
          </button>
          <button
            @click="toggleAddDataPointEditor()"
            class="
              w-20
              py-1
              border border-gray-600
              bg-slate-800
              text-white
              rounded-sm
            "
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
    <!--End of Side panel: Add data point-->
  </div>
</template>

<script setup lang="ts">
import { onBeforeMount, onMounted, reactive, ref, } from 'vue'
import { RouterLink, RouterView, useRoute } from 'vue-router'
import axios from 'axios'

let driverType = 'modbus';

let route = useRoute();

let addDataPointEditor = ref(false);
let driverDetailsEditor = ref(false)

//General data interfaces
interface EdgeDataSource<TConf> {
    name: string,
    type: string,
    tags: string,
    description: string,
    configuration: TConf
};

interface DataViewModel<TData> {
    isSelected?: boolean,
    name?: string,
    data: TData
};

//
//A mapped type is a type built on the syntax of the index signature. It is very useful when you don’t know the keys of an object ahead of time, 
//and particularly useful in our case since we are working on an indexed object.
//Ref: https://timmousk.com/blog/typescript-map/ 
//
interface DataListViewModel<TData> {
    [key: string]: DataViewModel<TData>
};

//For modbus
interface ModbusDataPoint{
    UnitId: string,
    DisplayName: string,
    StartAddress: string,
    Count: number,
    PollingInterval: number,
    CorrelationId: string
};


interface ModbusOperations{
    [id: string]: ModbusDataPoint
}

interface ModbusDataSourceConfiguration {
    PublishInterval: number,
    Version: string,
    SlaveConnection: string,
    HwId: string,
    RetryCount: number,
    RetryInterval: number,
    Operations: ModbusOperations
}

type ModbusDataSource = EdgeDataSource<ModbusDataSourceConfiguration>

//For saving data to backend
let dataSrcViewModel: DataViewModel<ModbusDataSource> = reactive({
    data: initDataSource()
});

//For editing the driver configuration
let dataSrcConfEditViewModel: DataViewModel<ModbusDataSourceConfiguration> = reactive({
    data: initDataSourceConfiguration()
})

//For add/edit/delete data points
let dataPointAddViewModel: DataViewModel<ModbusDataPoint> = reactive({
    name: "",
    data: initDataPoint()
});
let dataPointListViewModel: DataListViewModel<ModbusDataPoint> = reactive({});

function initDataPoint(): ModbusDataPoint {
    return {
        UnitId: "",
        DisplayName: "",
        StartAddress: "",
        Count: 0,
        PollingInterval: 1000,
        CorrelationId: "MessageType1"
    };
}

function initDataSourceConfiguration(): ModbusDataSourceConfiguration {
    return {
        SlaveConnection: "",
        HwId: "",
        RetryCount: 0,
        RetryInterval: 0,
        PublishInterval: 0,
        Version: "",
        Operations: {}
    }
}

function initDataSource(): ModbusDataSource {
    return {
        name: "",
        type: "",
        tags: "",
        description: "",
        configuration: initDataSourceConfiguration()
    }
}

onMounted(() => {
    fetchDataSource();
})

function fetchDataSource() {
    const url = `http://localhost:8080/api/devices/` + route.params.id + '/dataSources/' + driverType + '/' + route.params.dataSrcName;
    axios.get(url)
        .then(r => {
            if( 'object' === typeof r.data) {
                console.log("fetchDataSource:" + JSON.stringify(r.data));
                //for data save
                Object.assign(dataSrcViewModel.data, r.data);

                //for driver configuration
                Object.assign(dataSrcConfEditViewModel.data, r.data.configuration);

                //for data point list
                for( let k in r.data.configuration.Operations) {
                    dataPointListViewModel[k] = {
                        isSelected: false,
                        name: k,
                        data: r.data.configuration.Operations[k]
                    }
                }
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
    //Sync data points
    let ops: ModbusOperations = {};
    for( let k in dataPointListViewModel) {
        ops[k] = dataPointListViewModel[k].data;
    }

    dataSrcViewModel.data.configuration.Operations = ops;

    const url = `http://localhost:8080/api/devices/` + route.params.id +`/dataSources`
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

function editDataSourceConfiguration() {
    //Sync configuration data
    dataSrcViewModel.data.configuration.SlaveConnection = dataSrcConfEditViewModel.data.SlaveConnection;
    dataSrcViewModel.data.configuration.Version = dataSrcConfEditViewModel.data.Version;
    dataSrcViewModel.data.configuration.PublishInterval = dataSrcConfEditViewModel.data.PublishInterval;

    toggleDriverDetailsEditor();
}

function addDataPoint() {

    dataPointListViewModel[dataPointAddViewModel.name as string] = {
        isSelected: false,
        name : dataPointAddViewModel.name,
        data: initDataPoint()
    };    

    Object.assign(dataPointListViewModel[dataPointAddViewModel.name as string].data, dataPointAddViewModel.data);

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