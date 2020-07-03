<template>
<exo-drawer ref="pollDrawer" max-width="600px" right class="" >
    <template slot="title">
        {{poll.poll.name}}
    </template><br><br>
    <template slot="content">
            <v-card class="mx-auto" max-width="1500" color="gray">
  
    <v-stepper v-model="e1" vertical>
    <div  v-for="(question,n) in poll.questions"   :key="question.id">
    <v-stepper-step :complete="e1 > n+1" :step="n+1">
      {{question.question.question}}
    </v-stepper-step>

   <v-stepper-content :step="n+1">
       <v-card class="mb-12"
            color="grey lighten-2"
            height="150px">
          <v-radio-group text-align="center" v-model="question.response" >
          <v-radio v-for="(response, index) in question.responses" :key="index" :label="response.response" :value="response">
          </v-radio>
        </v-radio-group>
        </v-card> 
      <v-btn color="primary" @click="e1 = n+2">Continue</v-btn>
      <v-btn  @click="e1 = n">back</v-btn>
    </v-stepper-content>
   </div>

   >
         <div class="text-center">

      <v-btn color="primary"  @click="save">Send</v-btn> 
   <!-- <v-alert :value="alert" color="primary" dark transition="scale-transition"  > Response sent </v-alert> -->

      <v-btn  @click="lastStep(n)">Cancel</v-btn></div>
    </v-stepper>

  </v-card>

    </template>
    <template slot="footer">
    </template>
</exo-drawer>
</template>


<script>


  export default  {
    props: ['poll'],
    data () {
      return {
        alert: true,
        steps:null,
        e1:1,
        e13: 1,
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

              cancel() {
            this.$refs.pollDrawer.close()
        },
        open() {
            this.$refs.pollDrawer.open()
        },
        save(){
          
            fetch(`/portal/rest/pollsmanagement/userresponse`, {
                    method: 'post',
                    credentials: 'include',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(this.poll),
                })
                .then((result) => {
                    if (!result.ok) {
                        throw result;
                    }
                })
                .then((response) => {
                    this.displaySusccessMessage('poll created');
                })
                .catch((result) => {
                    result.text().then((body) => {
                        this.displayErrorMessage(body);
                    });
                
                });
        }
      
    }
  }

</script>
<style scoped>

.drawerContent {
    padding: 15px 27px;
}
.buttons{
  text-align: center;
  margin: 20px;
}
</style>