<template>
    <div class="flex flex-auto justify-center bg-blue-100 p-2">
    <!--container-->
        <div :class="['flex bg-blue-300 p-1', t.vertical.value? 'flex-col':'flex-row' ]">
            <div class="bg-green-100">div1</div>
            <div class="bg-green-200">div2</div>
            <div class="bg-green-300">{{t.msg}}</div>
            <div class="bg-green-400">{{t.vertical}}</div>
        </div>
        <button @click="t.handleClick">Change me</button>
        <label for="">Direction:</label>
        <input type="checkbox" v-model="t.vertical.value"/>
    </div>
</template>


<script lang="ts">
import { defineComponent, ref, computed } from 'vue'

export default defineComponent({
    props: {
        align: String
    },
    data() {
        return {
            message: 'hello world'
        }
    },
    setup(props, context) {
        const vertical = ref(true);
        let msg:string = 'msg';
        let handleClick = () => {
            vertical.value = false;
            alert("hello");
        }
        let t = {
            msg,
            vertical,
            handleClick
        }

        return {
           t 
        }
    },
})
</script>

