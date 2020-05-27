<template>
<div>
    <v-list disabled>
        <v-list-item
          v-for="(item, i) in questions"
          :key="i"
        >
 
 <v-container fluid>
    <p>{{ item.question }}</p>
      <v-radio-group v-model="radioGroup">
      <v-radio
        v-for="n in item.responses"
        :key="n"
        :label="n"
        :value="n"
      ></v-radio>
    </v-radio-group>
      </v-container>
        </v-list-item>
    </v-list>
</div>
</template>
<script>
export default {
props:['questions'],
 data: () => ({
   radioGroup:""
 }),
created(){
    console.log(this.ok);
},

}
</script>