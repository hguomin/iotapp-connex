<script setup lang="ts">
import { ref } from 'vue'
import { RouterLink, RouterView } from 'vue-router'

let open = ref(false);

function toggle() {
    open.value = !open.value;
}

function getUrls(id: string, dataSrcName: string): string {
  return "/edge-gateways/" + id + "/data-sources/" + dataSrcName;
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
            <button class="flex items-center pl-2 pr-3 py-1.5 rounded-sm hover:bg-gray-200">
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
                    <th class="text-left px-5">Driver Type</th>
                    <th class="text-left px-5">Created</th>
                    <th class="text-left px-5">Tags</th>
                    <th class="text-left px-5">Description</th>
                </tr>
                <tr class="border-b h-10 hover:bg-slate-200 ">
                    <td class="w-6">
                        <span class="flex justify-center">
                            <input type="checkbox" class="checkbox" />
                        </span>
                    </td>
                    <td class="px-5">
                        <router-link :to="getUrls($route.params.id, 'opcda-data')" class="text-blue-600 underline decoration-blue-600 ">opcda-data</router-link>
                    </td>
                    <td class="px-5">OPC UA</td>
                    <td class="px-5">May 7, 2022 3:33 PM</td>
                    <td class="px-5">OPC DA, OPC UA</td>
                    <td class="px-5">This is the data sources for geting OPC DA data</td>
                </tr>
                <tr class="border-b h-10 hover:bg-slate-200 ">
                    <td class="w-6">
                        <span class="flex justify-center">
                            <input type="checkbox" class="checkbox" />
                        </span>
                    </td>
                    <td class="px-5">
                        <router-link :to="getUrls($route.params.id, 'opcda-data2')" class="text-blue-600 underline decoration-blue-600 ">opcda-data-2</router-link>
                    </td>
                    <td class="px-5">OPC UA</td>
                    <td class="px-5">May 7, 2022 3:33 PM</td>
                    <td class="px-5">OPC DA, OPC UA</td>
                    <td class="px-5">This is the data sources for geting OPC DA data</td>
                </tr>
            </table>
        </div>
    </div>
    <!--Side panel-->
    <div id="side-editor-panel" :class="['absolute h-full right-0 overflow-hidder bg-slate-50 shadow-2xl transition duration-1000', open? 'flex' : 'hidden']">
        <div class="flex flex-col flex-auto">
            <div class="flex flex-col border-b border-gray-400 px-5 py-1">
                <div class="flex flex-auto">
                    <span class="grow"></span>
                    <button class="justify-items-end hover:bg-slate-100 p-1">
                        <i class="las la-times la-lg"></i>
                    </button>
                </div>
                <h1 class="grow text-lg font-semibold ">Add data source</h1>    
                <p class="text-sm text-gray-600">Setup data collection module and get data from where it is produced.</p>
            </div>
            <div class="grow flex flex-col px-5 py-5 space-y-5">
                <div class="flex flex-col">
                    <label for="name" class="font-semibold pb-1.5">Name <span class="text-red-600">*</span></label>
                    <input id="name" class="p-1 border border-gray-400 outline-none" />
                </div>
                <div class="flex flex-col">
                    <label for="driver-type" class="font-semibold pb-1.5">Driver type <span class="text-red-600">*</span></label>
                    <select id="driver-typpe" class="p-1 border border-gray-400 outline-none">
                        <option value="opc-ua" class="outline-none">OPC UA</option>
                        <option value="modbus" class="outline-none">Modbus</option>
                        <option value="ethnet-ip" class="outline-none">Ethnet/IP</option>
                        <option value="bacnet" class="outline-none">BACNET</option>
                    </select>
                </div>     
                <div class="flex flex-col">
                    <label for="tags" class="font-semibold pb-1.5">Tags</label>
                    <input id="tags" class="p-1 border border-gray-400 outline-none" />
                </div>
                <div class="flex flex-col">
                    <label for="description" class="font-semibold pb-1.5">Description <span class="text-red-600">*</span></label>
                    <textarea id="description" class="p-1 border border-gray-400 outline-none"></textarea>
                </div>           
                <span class="grow"></span>
            </div>
            <div class="flex">
                <button class="w-20  py-1 border border-gray-600 bg-slate-800 text-white mb-5 ml-5">OK</button>
            </div>
        </div>
    </div>
  </div>
</template>
