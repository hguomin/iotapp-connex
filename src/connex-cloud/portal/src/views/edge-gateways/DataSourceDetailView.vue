<template>
    <div class="relative flex flex-col flex-auto px-5">
        <h1 class="text-lg font-medium pt-3 pb-0.5">{{$route.params.dataSrcName}}</h1>
        <div class="flex space-x-5 mt-2">
            <button class="px-1 py-2 focus:border-b-2 focus:border-slate-800 hover:bg-slate-200">Details</button>
            <button class="px-1 py-2 focus:border-b-2 focus:border-slate-800 hover:bg-slate-200">Data Points</button>
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
                                <span>{{$route.params.dataSrcName}}</span>
                            </div>
                            <div class="flex flex-col">
                                <span class="font-semibold">Driver type</span>
                                <span>OPC UA</span>
                            </div>
                            <div class="flex flex-col">
                                <span class="font-semibold">Tags</span>
                                <span>Tag1, Tag2, Tag3</span>
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
                        <button class="px-1 hover:bg-slate-100"><i class="las la-edit la-lg"></i></button>
                    </div>
                    <div class="relative flex flex-auto px-3">
                        <div class="grow flex flex-col space-y-3.5 py-2">
                            <div class="flex flex-col">
                                <span class="font-semibold">OPC UA server</span>
                                <div class="flex flex-auto">
                                    <div class="flex w-1/2">
                                        <label class="font-semibold mr-2">Host:</label>
                                        <span>--</span>
                                    </div>
                                    <div class="flex">
                                        <label class="font-semibold mr-2">Port:</label>
                                        <span>--</span>
                                    </div>
                                </div>
                            </div>
                            <div class="flex flex-col">
                                <span class="font-semibold">Authentication</span>
                                <div class="flex flex-auto">
                                    <div class="flex w-1/2">
                                        <label class="font-semibold mr-2">Username:</label>
                                        <span>--</span>
                                    </div>
                                    <div class="flex">
                                        <label class="font-semibold mr-2">Password:</label>
                                        <span>--</span>
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
                        <p class="py-2">This is the description</p>
                    </div>
                </div>
            </div>
            <!--data points-->
            <div class="grow flex flex-col bg-slate-50 shadow">
                <div class="flex font-semibold border-b border-gray-500 py-1.5 pr-1">
                    <span class="grow px-3">Data points</span>
                    <div class="flex space-x-3 px-1">
                        <button @click="toggle()" class="flex px-1 items-center rounded-sm hover:bg-gray-200">
                            <span class="mr-1"><i class="las la-plus la-lg"></i></span>
                            <span>Add</span>
                        </button>
                        <button class="flex px-1 items-center rounded-sm hover:bg-gray-200">
                            <span class="mr-1"><i class="lar la-edit la-lg"></i></span>
                            <span>Edit</span>
                        </button>
                        <button class="flex px-1 items-center rounded-sm hover:bg-gray-200">
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
                            <th class="text-left font-semibold px-5">Sample Interval</th>
                            <th class="text-left font-semibold px-5">Publish Interval</th>
                        </tr>
                        <tr class="border-b h-6 hover:bg-slate-200 ">
                            <td class="w-6">
                                <span class="flex justify-center">
                                    <input type="checkbox" class="checkbox" />
                                </span>
                            </td>
                            <td class="px-5">
                                <router-link to="/" class="text-blue-600 underline decoration-blue-600 ">i=2258</router-link>
                            </td>
                            <td class="px-5">Temperature</td>
                            <td class="px-5">5</td>
                            <td class="px-5">5</td>
                        </tr>
                        <tr class="border-b h-8 hover:bg-slate-200 ">
                            <td class="w-6">
                                <span class="flex justify-center">
                                    <input type="checkbox" class="checkbox" />
                                </span>
                            </td>
                            <td class="px-5">
                                <router-link to="/" class="text-blue-600 underline decoration-blue-600 ">i=2259</router-link>
                            </td>
                            <td class="px-5">Humidity</td>
                            <td class="px-5">5</td>
                            <td class="px-5">5</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <!--Side panel: Add data point-->
        <div id="side-panel-add" :class="['absolute h-full right-0 overflow-hidder bg-slate-50 shadow-2xl transition duration-1000', open? 'flex' : 'hidden']">
            <div class="flex flex-col flex-auto">
                <div class="flex flex-col border-b border-gray-400 px-5 py-1">
                    <div class="flex flex-auto">
                        <span class="grow"></span>
                        <button @click="toggle()" class="justify-items-end hover:bg-slate-100 p-1">
                            <i class="las la-times la-lg"></i>
                        </button>
                    </div>
                    <h1 class="grow text-lg font-semibold ">Add data point</h1>    
                    <p class="text-sm text-gray-600">Add a opc ua data point for publishing to cloud.</p>
                </div>
                <div class="grow flex flex-col px-5 py-5 space-y-5">
                    <div class="flex flex-col">
                        <label for="Id" class="font-semibold pb-1.5">Id <span class="text-red-600">*</span></label>
                        <input id="id" class="p-1 border border-gray-400 outline-none" />
                    </div>
                    <div class="flex flex-col">
                        <label for="display-name" class="font-semibold pb-1.5">Display name</label>
                        <input id="display-name" class="p-1 border border-gray-400 outline-none" />
                    </div>   
                    <div class="flex flex-col">
                        <label for="sample-interval" class="font-semibold pb-1.5">Sample interval</label>
                        <input id="sample-interval" class="p-1 border border-gray-400 outline-none" />
                    </div>     
                    <div class="flex flex-col">
                        <label for="publish-interval" class="font-semibold pb-1.5">Publish interval</label>
                        <input id="publish-interval" class="p-1 border border-gray-400 outline-none" />
                    </div> 
                </div>
                <span class="grow border-b"></span>
                <div class="flex justify-end space-x-5 p-5">
                    <button class="w-20 py-1 border border-gray-600 bg-slate-800 text-white rounded-sm">Save</button>
                    <button @click="toggle()" class="w-20 py-1 border border-gray-600 bg-slate-800 text-white rounded-sm">Cancel</button>
                </div>
            </div>
        </div>
    </div>
</template>

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