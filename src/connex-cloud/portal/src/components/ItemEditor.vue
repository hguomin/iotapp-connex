<template>
    <!--Step 1: Use these mouse event to show/hide the component add button-->
    <div @mouseover="handleMouseOver($event, 'over')" 
         @mouseenter="handleMouseOver($event, 'enter')" 
         @mouseleave="handleMouseOver($event, 'leave')"
         class="flex flex-col flex-auto">
        <!--@click.prevent: VUE event modifiers-->
        <!--Step 0: Use mouse click to show/hide the edit panel-->
        <div @click.prevent="handleClick($event)" :class="[classes.background, classes.height]" >
            This is the item: {{name}} 
        </div>
        
        <!--Notify parent component to add components-->
        <!--Step 2: Use $emit to emit the AddItem event and parent component will handle this event to add a component to its list-->
        <!--button v-if="showAddPanel" @click="$emit('AddItem', name)" ><i class="las la-plus la-lg"></i></button-->
        <div v-if="showEditPanel" class="absolute h-full top-0 right-0 bg-slate-50 transition duration-1000">
            <label for="name">Name: </label>
            <input type="text" name="name" v-model="name"/>
            <br/>
            <label for="class">Background: </label>
            <input type="text" name="class" v-model="classes.background"/>
            <br/>
            <label for="height">Height: </label>
            <input type="text" name="height" v-model="classes.height"/>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'

export default defineComponent({
    setup() {
        const height = ref('');
        const classes = ref({
            background: 'bg-red',
            height: 'h-10'
        });
        const name = ref("editor");
        const showEditPanel = ref(false);
        
        const handleClick = (e: Event) => {
            name.value = "Guomin";
            showEditPanel.value = !showEditPanel.value;
        }

        //Show/hide the add button
        const showAddPanel = ref(false);
        const handleMouseOver = (e: Event, status: string) => {
            switch(status)
            {
                case "enter":
                case "over":
                    {
                        showAddPanel.value = true;
                    }break;
                case "leave":
                    {
                        showAddPanel.value = false;
                    }break;
                default:
                    break;
            }
            
        }

        return {
            height,
            classes,
            name,
            showEditPanel,
            handleClick,

            showAddPanel,
            handleMouseOver
        }
    },
})
</script>

<style scoped>
.bg-aqua {
    background-color: aqua;
}

.bg-aqua-2 {
    background-color: blueviolet;
}

.bg-green-100 {
    background-color: green;
}

.h-480 {
    height: 20rem;
}
</style>
