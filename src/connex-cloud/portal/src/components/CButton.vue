<template>
    <div>
        <button @click="handleClick()">This is a button</button>
    </div>
</template>

<script setup lang="ts">
    let handleClick = (e) => {
        console.log(e);
        alert("Clicked me!");
    }
</script>

