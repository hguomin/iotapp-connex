<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from '@/components/HelloWorld.vue'
</script>

<template>
  <!--
  <header>
    <img alt="Vue logo" class="logo" src="@/assets/logo.svg" width="125" height="125" />

    <div class="wrapper">
      <HelloWorld msg="You did it!" />

      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/about">About</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView />
  -->
  <!--0 如何占满高度: Use "flex-grow: 1" to fill all height of the flex col container in body-->
  <div id="page" class="flex-auto flex bg-slate-100 font-inter">
      <nav id="nav" class="w-64 flex flex-col bg-slate-800 px-3 py-1.5">
          <div class="logo flex flex-col justify-center bg-opacity-30 px-3 h-14">
              <a class="flex" href="/">
                  <!--Use flex vertical container to center text in vertical direction-->
                  <span class="flex flex-col justify-center text-white text-xl">
                      <div class="flex">
                        <i class="lab la-gg la-2x text-blue-400"></i>
                        <p class="flex flex-col justify-center pl-2 pb-0.5 text-slate-50">CONNEX</p></div>
                  </span>
              </a>
          </div>
          <!--使用padding调整菜单项间距、菜单项内的图标和文字间距等；使用hover:更改鼠标移动到菜单项上时的背景色（比原来淡一级）-->
          <div class="nav-items space-y-8 mt-6 px-0.5">
            <ul>
              <li class="px-3 py-2 rounded-sm mb-0.5 bg-slate-900">
                <router-link to="/dashboard" class="block text-slate-200 hover:text-white truncate transition duration-150 active">
                  <div class="flex items-center">
                      <span class="mr-2"><i class="la la-home la-lg"></i></span>
                      <span class="ml-1 font-medium">Dashboard</span>
                  </div>
                </router-link>
              </li>
              <!--li class="px-3 py-2 rounded-sm mb-0.5 hover:bg-slate-700 ">
                <router-link to="/" class="block text-slate-200 hover:text-white truncate transition duration-150 active">
                  <div class="flex items-center">
                      <span class="mr-2"><i class="la la-list-ul la-lg "></i></span>
                      <span class="ml-1 font-medium">Devices</span>
                  </div>
                </router-link>
              </li-->
              <li class="px-3 py-2 rounded-sm mb-0.5 hover:bg-slate-700">
                <router-link to="/edge-gateways" class="block text-slate-200 hover:text-white truncate transition duration-150 active">
                  <div class="flex items-center">
                      <span class="mr-2"><i class="las la-sitemap la-lg"></i></span>
                      <span class="ml-1 font-medium">Edge Gateways</span>
                  </div>
                </router-link>
              </li>
            </ul>
          </div>
      </nav>

      <!--1 如何占满剩余宽度: Use "flex-grow: 1" to fill rest of the width-->
      <!--2 如何固定header? footer始终在底部？中间内容如何占满剩余空间？-->
      <!--2.1 Use "flex-direction: column" to arrange content from top to down-->
      <div id="wrapper" class="flex flex-col flex-grow">

          <!--如何在垂直方向居中：flex flex-col justify-center => 父容器在垂直主轴居中对齐，于是其唯一子容器toolbar就垂直居中了-->
          <header class="flex flex-col justify-center pl-6 pr-4 h-14 border-b-2 bg-white">
              <div id="toolbar" class="flex">
                  <span><i class="las la-bars text-2xl"></i></span>
                  <!--occupy all space in the middle-->
                  <span class="flex-auto"></span>
                  <span class="flex flex-col justify-center"><input type="search" name="search" placeholder="Search" class="px-3 py-1 w-60 border-0 focus:ring-2 focus:outline-none outline-none"/></span>
                  <span class="flex flex-col justify-center"><i class="las la-search text-2xl ml-4"></i></span>
                  <span class="flex flex-col justify-center"><i class="las la-compress text-2xl ml-4"></i></span>
                  <span class="flex flex-col justify-center"><i class="las la-envelope text-2xl ml-4"></i></span>
                  <span class="flex flex-col justify-center"><i class="lar la-user text-3xl ml-4"></i></span>
              </div>
          </header>

          <!--2.2 Use "flex-grow: 1" in "flex-auto" to full fill all space except the header and footer,
              then the footer will be move to the bottom automatically-->
          <div id="main" class="flex flex-auto">
              <!-- this will be replaced by view from subpage-->
              <div id="content" class="flex flex-auto bg-gray-100">
                  <router-view></router-view>
                  <!--div>View content place here.</div-->
                  <!--div class=" ml-4 mt-10">
                      <div class="mb-3 pt-0">
                          <input type="text" placeholder="Placeholder" class="px-3 py-4 placeholder-blueGray-300 text-blueGray-600 relative bg-white rounded text-sm border-0 shadow outline-none focus:outline-none focus:ring w-full"/>
                      </div>
                  </div-->
              </div>
          </div>

          <div id="footer" class="flex flex-col justify-center px-6 h-12 border-t-2 bg-white">
              <div id="footer-info" class="flex">
                  <p class="text-gray-600">2021<i class="las la-copyright"></i>The DPS IoT Team</p>
                  <!--occupy all space in the middle-->
                  <span class="flex-auto"></span>
                  <p class="text-gray-600">About</p>
                  <p class="text-gray-600 ml-3">Support</p>
              </div>
          </div>
      </div>
  </div>
</template>

<style>
@import "@/assets/site.css";
@import "@/assets/line-awesome-1.3.0/1.3.0/css/line-awesome.min.css"
/*
@import '@/assets/base.css';

#app {
  max-width: 1280px;
  margin: 0 auto;
  padding: 2rem;

  font-weight: normal;
}

header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

a,
.green {
  text-decoration: none;
  color: hsla(160, 100%, 37%, 1);
  transition: 0.4s;
}

@media (hover: hover) {
  a:hover {
    background-color: hsla(160, 100%, 37%, 0.2);
  }
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  body {
    display: flex;
    place-items: center;
  }

  #app {
    display: grid;
    grid-template-columns: 1fr 1fr;
    padding: 0 2rem;
  }

  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
*/
</style>
