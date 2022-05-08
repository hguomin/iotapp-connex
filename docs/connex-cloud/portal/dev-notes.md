# Dev Notes
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

## Vue use data in html attribute  
https://stackoverflow.com/questions/43236848/vuejs-use-prop-as-data-attribute-value   
https://stackoverflow.com/questions/66222168/vue-bind-html-data-attribute-within-for-loop

## Drawer panel with Vue and Tailwind CSS  
https://medium.com/cstech/power-of-simplicity-sidebar-component-with-vue-and-tailwindcss-eb54fdd2710e

The key is use absolute positioning to make the div leave normal html document flow, then use display:none/flex to hide/show the panel

## Other references:  
[VUE geting started](https://www.sitepoint.com/fetching-data-third-party-api-vue-axios/)