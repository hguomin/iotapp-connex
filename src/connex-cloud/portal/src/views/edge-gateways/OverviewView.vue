<script setup lang="ts">
import axios from "axios";

interface EdgeDataDriver {
    deviceId: string;
    driverType: string;
    driverId: string;
};

function deployDataDriver() {
    let data: EdgeDataDriver = {
        deviceId: "connex-edge-device-01",
        driverType: "opc-ua",
        driverId: "opc-driver-08"
    };

    const url: string = `http://localhost:8080/api/devices/` + data.deviceId + "/modules";
    let response = axios.post(url, 
                            JSON.stringify(data), 
                            {
                                headers: {
                                    'Content-Type': 'application/json;charset=UTF-8'
                                }
                            })
                        .then(r => {
                            console.log(r.data)
                        })
                        .catch(e => console.log("Error: " + e));
}
</script>

<template>
    <div>
        <button @click="deployDataDriver()" id="deployModule" class="px-5 py-2">Deploy Module</button>    
    </div>
</template>