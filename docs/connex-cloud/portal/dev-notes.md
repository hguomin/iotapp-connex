# Dev Notes

# Frontend  
## Add Tailwind CSS
```bash
npm install -D tailwindcss postcss autoprefixer
npx tailwindcss init -p # Generate tailwind.config.js and postcss.config.js files
```

https://tailwindcss.com/docs/guides/vite

## Use router in VUE to design pages  
https://router.vuejs.org/guide/essentials/nested-routes.html

## About VUE Component definition and usage  
[Vuejs script setup cannot contain ES module exports](https://stackoverflow.com/questions/71163741/vuejs-script-setup-cannot-contain-es-module-exports)    
[Component Registration](https://vuejs.org/guide/components/registration.html)  
[Understanding the new script setup with defineProps & defineEmits in Vue 3.2](https://www.netlify.com/blog/understanding-defineprops-and-defineemits-in-vue-3.2/)  
[vue "script setup"](https://vuejs.org/api/sfc-script-setup.html)  
[Vue3 script setup 语法糖详解](https://juejin.cn/post/7009282373476941831)

## Vue use data in html attribute  
https://stackoverflow.com/questions/43236848/vuejs-use-prop-as-data-attribute-value   
https://stackoverflow.com/questions/66222168/vue-bind-html-data-attribute-within-for-loop

## Drawer panel with Vue and Tailwind CSS  
https://medium.com/cstech/power-of-simplicity-sidebar-component-with-vue-and-tailwindcss-eb54fdd2710e

The key is use absolute positioning to make the div leave normal html document flow, then use display:none/flex to hide/show the panel

# Sevice backend  

## Spring enabling cross origin requests  
https://spring.io/guides/gs/rest-service-cors/  


## IoT Hub edge module management

https://github.com/Azure/azure-iot-sdk-java/blob/main/service/iot-service-samples/device-manager-sample/src/main/java/samples/com/microsoft/azure/sdk/iot/ModuleManagerSample.java


## Java tool for simplified coding  
https://www.jianshu.com/p/2ea9ff98f7d6

## JSON operation  
use fastjson

## Read document from Spring boot [resources] folder  

Use Resource
https://www.baeldung.com/spring-classpath-file-access  
https://www.baeldung.com/spring-load-resource-as-string  


## Vue form to submit create operation  
https://www.vuemastery.com/courses/intro-to-vue-3/forms-and-v-model-vue3/


## Telemetry Chart   
https://vue-chartjs.org/guide/#introduction  


## Modbus tools  
https://blog.csdn.net/xfx_1994/article/details/115618043  
https://www.dalescott.net/modbus-development/  

## In VUE, use mapped type instead of Map
https://timmousk.com/blog/typescript-map/   
https://blog.csdn.net/sinat_35626559/article/details/113869585
https://trungk18.com/experience/how-to-iterate-over-objects-in-typescript/  

## SockJS import error   
https://pjausovec.medium.com/how-to-fix-error-ts7016-could-not-find-a-declaration-file-for-module-xyz-has-an-any-type-ecab588800a8

## Sping boot configuration file  
https://blog.csdn.net/u011047968/article/details/113006584

## Other references:  
[VUE geting started](https://www.sitepoint.com/fetching-data-third-party-api-vue-axios/)