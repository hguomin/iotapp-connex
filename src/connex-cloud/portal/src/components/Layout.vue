<template>
    <div class="relative flex-auto flex flex-row px-10 py-5">
        <div class="grow">
            <draggable :list="comps" item-key="id">
                <template #item = "{ element  }">
                    <div class="flex flex-col">
                        <div class="flex flex-row">

                            <button name="AddLayout" class="bg-blue-100 justify-items-end">
                                <i class="las la-plus la-lg"></i>
                            </button>
                            <!--Step 4: Handle the "AddItem" event emitted by this component to add a new component-->
                            <component v-bind:is="element.item" @add-item="addComp($event)" class="cursor-move"></component>

                        </div>
                        <button @click="addComp" ><i class="las la-plus"></i></button>
                    </div>
                </template>
            </draggable>
        </div>
        <div class="flex flex-col ml-5">
            <div id="editPanel" class="grow">
                <label for="id">ID:</label>
                <input name="id" class="grow"/>
            </div>
            <button @click="addComp">Add component</button>
        </div>
    </div>
</template>

<script lang="ts" setup>

import draggable from 'vuedraggable'
import CButton from '@/components/CButton.vue'
import CEdit from '@/components/CEdit.vue'
import CBox from '@/components/CBox.vue'
import ItemEditor from '@/components/ItemEditor.vue'

import { reactive, ref } from '@vue/reactivity';


//https://vuejs.org/api/sfc-script-setup.html#using-components

let comps:Object[] = reactive([]);
let aa = reactive(['111', '222', '333', '444', '555']);
let drag = ref('false');
let id = 1;
let cc = {
    comps,
    id
};
function addComp(e: string) {
    
    let data = {
        type: 'object',
        model: 'device:temperatureSensor',
        properties: {
            'id': {
                type: 'string',
                displayName: 'Device ID'
            },
            'key': {
                type: 'string',
                displayName: 'Device Key'
            },
            'temperature': {
                type: 'float',
                displayName: 'Temperature',
                options: {
                    'frequency': {
                        value: 10, 
                        unit: 'ms'
                    }
                }
            },
            'humidity': {
                type: 'float',
                displayName: 'Humidity',
                options: {
                    'frequency': {
                        value: 10,
                        unit: 'ms'
                    }
                }
            }
        },
        methods: {
            'setFrequency': {
                parameters: {
                    'telemetry': {
                        type: 'string'
                    },
                    'frequency': {
                        type: 'int'
                    }
                }
            }
        },
        views: [
            {
                'id': {
                    edit: 'text',
                    option: {
                        label: 'default',
                        placeholder: 'Input the device id',
                        validation: {

                        }
                    }
                }
            },
            {
                'temperature': {
                    edit: 'text',
                    scope: '0-50'
                }
            },
            {
                'humidity': {
                    edit: 'slider'
                }
            },
            'key'
        ]
    };

    let vv = document.getElementsByTagName('div');
    let vvl = [...vv];
    let keys = Object.keys(data);

    for (const key in data) {
        const val = data[key];
        console.log(val)
    }
    for( let k in keys ) {
        console.log(k)
        let v = data[k];
        console.log(v)
    }

    console.log(e);
    comps.push({
        "id": id,
        "item": ItemEditor
    });
    ++id;
}
</script>


