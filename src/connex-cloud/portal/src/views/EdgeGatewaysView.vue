<script setup lang="ts">
import { onMounted  } from 'vue';
import { RouterLink, RouterView } from 'vue-router'
import gateways from '../data/gateways.json'
import { Config } from '@/config'
import axios from 'axios';

interface IoTEdgeDevice {
    id: string;
    displayName: string;
    key: string;
    status: string;
    connectionState: string;
}

let deviceList: IoTEdgeDevice[] = [];

onMounted(() => {
    const url = Config.host + "/api/devices";
    axios.get(url)
        .then(r => {
            console.log("fetchDevices:");
            console.log(r.data);

            deviceList.length = 0;
            for(let c of r.data) {
                deviceList.push(c);
            }
        })
        .catch(e => console.log("fetchDevices - error: " + e));
});

function handleClick(gateway: any) {
    alert(gateway.name);
}

function getGatewayUrl(id: string): string {
    return "/edge-gateways/" + id + "/overview";
}
</script>
<template>
    <div class="flex flex-grow">
        <div id="sidebar" class="flex flex-col w-64 border-r-2 bg-slate-50">
            <!--Use flex vertical container to center text in vertical direction-->
            <div class="flex items-baseline px-3 my-4">
                <span class="grow text-lg font-semibold">Gateways</span>
            </div>
            <div class="flex flex-col grow px-3">
                <ul> 
                    <li v-for="d in deviceList " :key="d.id" class="px-3 py-2 rounded-sm hover:bg-gray-100">
                        <router-link :to="getGatewayUrl(d.id)">
                            <div class="flex items-center">
                                <span class="ml-1 gw.id">{{d.displayName}}</span>
                            </div>
                        </router-link>
                    </li>
                </ul>
                <div class="grow">
                </div>

                <!--div class="flexhover:bg-gray-100 px-4 py-3">
                    <router-link to="/edge-gateways/add" class="flex justify-center">
                        <span class="mr-2"><i class="la la-plus la-lg"></i></span>
                        <span class="font-semibold">New Gateway</span>
                    </router-link>
                </div-->
            </div>
        </div>

        <router-view />
    </div>
</template>

<style>

</style>