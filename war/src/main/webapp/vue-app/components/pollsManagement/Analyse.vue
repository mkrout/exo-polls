<template>
<div class="review-poll">

    <v-list disabled>
        <v-list-item
          v-for="(item, i) in questions"
          :key="i"
        >
 
 <v-container fluid>
   
    <p>{{ item.question.question }}</p>
    
      <v-radio-group v-model="radioGroup">
      <v-radio
        v-for="n in item.responses"
        :key="n"
        :label="n.response"
        :value="n.response"
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
<style scoped>
.review-poll {
  
    max-width: 50%;
    padding: 20px;
    margin: auto;
    text-align: center;
}
.v-alert.success.v-sheet.theme--light {
    width: 30% !important;
    height: 50px;
    font-size: 24px;
    padding: 30px;
    border: 1px solid;
}
</style>