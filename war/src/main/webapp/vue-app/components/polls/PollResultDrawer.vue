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
                <v-spacer></v-spacer>
                
                   <br><br>
                   </v-card-title><v-spacer></v-spacer>
                 
                  
                  
                    <div v-for="(item, index) in results" :key="index" class="col-lg-8 col-md-8">
                      <strong>{{item.question}}</strong>
                      <card type="chart" cardCol>
                        
                        <column-chart
                          :data="item.responses"
                        ></column-chart>
                        </card>
                
                    
                    <strong> Rapport de {{poll.name}} : </strong>
                    <p> {{poll.name}} se découpe en {{results.size}} questions</p>
                    
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
    props: ['results','poll'],
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