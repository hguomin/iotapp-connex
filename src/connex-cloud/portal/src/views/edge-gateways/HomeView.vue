<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'

function getUrls(id: string | string[], page: string): string {
  return "/edge-gateways/" + id + "/" + page;
}

</script>
<template>
    <div class="grow flex flex-col">
      <div class="flex flex-col px-5 py-3 bg-slate-800">
        <h1 class="text-xl text-slate-50 font-semibold">Gateway: {{ $route.params.id }}</h1>
        <h2 class="text-slate-300">Device details</h2>
      </div>
      <div>
        <ul class="flex px-5 h-10 mt-2 space-x-5 border-b border-neutral-600">
          <li class="flex flex-col justify-center px-3  hover:border-b-2 hover:border-slate-900 hover:bg-gray-300 hover:bg-opacity-20">
            <router-link :to="getUrls($route.params.id, 'overview')" class="font-semibold text-slate-700 ">Overview</router-link>
          </li>
          <li class="flex flex-col justify-center px-3 bg-gray-300 border-b-2 border-blue-900 hover:border-b-2 hover:border-slate-900 hover:bg-gray-300 hover:bg-opacity-20">
            <router-link :to="getUrls($route.params.id, 'data-sources/list')" class="font-semibold text-slate-700 ">Data Sources</router-link>
          </li>
          <li class="flex flex-col justify-center px-3 hover:border-b-2 hover:border-slate-900 hover:bg-gray-300 hover:bg-opacity-20">
            <router-link :to="getUrls($route.params.id, 'configuration')" class="font-semibold text-slate-700 ">Configurations</router-link>
          </li>
          <li class="flex flex-col justify-center px-3 hover:border-b-2 hover:border-slate-900 hover:bg-gray-300 hover:bg-opacity-20">
            <router-link :to="getUrls($route.params.id, 'telemetries')" class="font-semibold text-slate-700 ">Raw telemetries</router-link>
          </li>
          <li class="flex flex-col justify-center px-3 hover:border-b-2 hover:border-slate-900 hover:bg-gray-300 hover:bg-opacity-20">
            <router-link :to="getUrls($route.params.id, 'actions')" class="font-semibold text-slate-700 ">Actions</router-link>
          </li>
          <li class="flex flex-col justify-center px-3 hover:border-b-2 hover:border-slate-900 hover:bg-gray-300 hover:bg-opacity-20">
            <router-link :to="getUrls($route.params.id, 'logs')" class="font-semibold text-slate-700 ">Logs</router-link>
          </li>
        </ul>
      </div>
      <router-view />
    </div>
</template>

<style>

</style>
