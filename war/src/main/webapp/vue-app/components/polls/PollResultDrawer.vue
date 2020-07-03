<template>
<exo-drawer ref="pollResultDrawer" right class="" >
    <template slot="title" color="primary">
        Poll analytics
    </template>
    <template slot="content">
      <center>
              <v-card class="mx-auto" max-width="1500" max-height="1500" color="gray">
                <v-title><h1></h1></v-title>
                
              <v-card-title>
                <v-col
      class="text-center"
      cols="12"
      color="primary"
    >
               <strong>{{poll.name}}</strong></v-col>
                <v-spacer></v-spacer>      <v-spacer></v-spacer>
                
                   <br><br>
                   </v-card-title><v-spacer></v-spacer>
                <div >
                  
                  
                  
                    <div class="col-lg-8 col-md-8">
                      <strong>{{Question.Qname}}</strong>
                      <card type="chart" cardCol>
                        
                        <column-chart
                          :data="[
                          ['Response 1', 100],
                          ['Response 2', 100],
                          ['Response 3', 150],
                          ['Response 4', 120],
                          ]"
                        ></column-chart>
                        </card>
                    </div>
                    <div class="col-lg-8 col-md-8">
                      <strong>{{Question.Qname}}</strong>
                      <card type="chart" cardCol>
                        
                        <column-chart
                          :data="[
                          ['Response 1', 100],
                          ['Response 2', 100],
                          ['Response 3', 150],
                          ['Response 4', 120],
                          ]"
                        ></column-chart>
                        </card>
                    </div>
                    <div class="col-lg-8 col-md-8">
                      <strong>{{Question.Qname}}</strong>
                      <card type="chart" cardCol>
                        
                        <column-chart
                          :data="[
                          ['Response 1', 100],
                          ['Response 2', 100],
                          ['Response 3', 150],
                          ['Response 4', 120],
                          ]"
                        ></column-chart>
                        </card>
                    </div>
                    
                    <strong> Rapport de {{poll.name}} : </strong>
                    <p> {{poll.name}} se découpe en {{Qnbr.Quesnbr}} questions</p>
                    
                </div>
              </v-card>
    </center>


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

    Question:{
          Qname:'Question1'
    },
    Qnbr:{
          Quesnbr:'4'
    },
      }
    },

    methods:{
              cancel() {
            this.$refs.pollResultDrawer.close()
        },
        open() {
            this.$refs.pollResultDrawer.open()
        },
       
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