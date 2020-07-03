<template>
<v-card class="mx-auto" max-width="1000" max-width="1500" color="gray">
  <v-footer padless>
    <v-col
      class="text-center"
      cols="12"
      color="primary"
    >
     <strong>{{poll.name}}</strong>
    </v-col>
  </v-footer>
    <v-stepper v-model="e1" vertical>
    <div  v-for="(question,n) in questions"   :key="question.id">
    <v-stepper-step :complete="e1 > n+1" :step="n+1">
      {{question.question.question}}
    </v-stepper-step>

   <v-stepper-content :step="n+1">
       <v-card class="mb-12"
            color="grey lighten-1"
            height="150px">
          <v-radio-group text-align="center">
          <v-radio v-for="(response, index) in question.responses" :key="index" :label="response.response" :value="response.id">
          </v-radio>
        </v-radio-group>
        </v-card> 
      <v-btn color="primary" @click="e1 = n+2">Continue</v-btn>
      <v-btn  @click="e1 = n">back</v-btn>
    </v-stepper-content>
   </div>
    <v-col cols="12" md="6">
        <v-textarea
          outlined
          name="input-7-4"
          label="Have you any comments ?"
          value=""
        ></v-textarea>
      </v-col>
       
      <v-btn color="primary" >Send</v-btn> 
      
      <v-btn color="primary" @click="lastStep(n)">Cancel</v-btn>
    </v-stepper>
  



    
  </v-card>

</template>

<script>


  export default  {
    data () {
      return {
        steps:null,
        e1:1,
        e13: 1,
        poll:{
          name:'test',
          names:'app'
         },
        questions:[
          { id: 'q1',
            question:{
              question:'question1'
            },
            responses:[
              {
                id: 'r-id-1',
                response:'r1'
              },
              {
                id: 'r-id-2',
                response:'r2'
              },
              {
                id: 'r-id-3',
                response:'r3'
              },
              {
                id: 'r-id-4',
                response:'r4'
              }
            ]
          },
          { id: 'q2',
            question:{
              question:'question2'
            },
            responses:[
              {
                id: 'r-id-1',
                response:'r1'
              },
              {
                id: 'r-id-2',
                response:'r2'
              },
              {
                id: 'r-id-3',
                response:'r3'
              },
              {
                id: 'r-id-4',
                response:'r4'
              }
            ]
          },
         
        ]
      }
    },

    methods:{
            nextStep (n) {
        if (n === this.steps) {
          this.e1 = 1
        } else {
          this.e1 = n + 1
        }
      },
            lastStep (n) {
        if (n === this.question) {
          this.e1 = 1
        } else {
          this.e1 = n - 1
        }
      },
      
    }
  }

</script>
<style scoped>

.v-alert.success.v-sheet.theme--light {
    width: 30% !important;
    height: 50px;
    font-size: 24px;
    padding: 30px;
    border: 1px solid;
}
.buttons{
  text-align: center;
  margin: 20px;
}

</style>