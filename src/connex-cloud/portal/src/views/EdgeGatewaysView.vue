<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import gateways from '../data/gateways.json'

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
                    <li v-for="gw in gateways.gateways" :key="gw.id" class="px-3 py-2 rounded-sm hover:bg-gray-100">
                        <router-link :to="getGatewayUrl(gw.id)">
                            <div class="flex items-center">
                                <span class="ml-1 gw.id">{{gw.name}}</span>
                            </div>
                        </router-link>
                    </li>
                </ul>
                <div class="grow">
                </div>

                <div class="flexhover:bg-gray-100 px-4 py-3">
                    <router-link to="/edge-gateways/add" class="flex justify-center">
                        <span class="mr-2"><i class="la la-plus la-lg"></i></span>
                        <span class="font-semibold">New Gateway</span>
                    </router-link>
                </div>
            </div>
        </div>

        <router-view />
    </div>
</template>

<style>

</style>