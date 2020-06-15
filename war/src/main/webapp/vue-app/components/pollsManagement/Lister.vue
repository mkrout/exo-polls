<template>
  <div>
      <v-container>
        <v-flex xs-12>
          <v-layout>
            <v-flex class="md3 xs-3">
              <v-card>
                <v-app-bar color="primary" dense dark>
                                 

                   <v-icon>mdi-chart-line</v-icon>
                  <v-toolbar-title
                    ><div id="nom_s">{{ names }}</div></v-toolbar-title
                  >

                  <v-spacer></v-spacer>
                </v-app-bar>

                <v-list flat>
                  <v-list-item v-for="(item, i) in questions" :key="i">
                    <v-list-item-content>
                      <v-list-item-title
                        v-text="item.question"
                      ></v-list-item-title>
                    </v-list-item-content>
                  </v-list-item>
                </v-list>
              </v-card>
            </v-flex>
            <v-flex class="md9 xs-9">
              <v-card>
                <v-card class="mx-auto" max-width="1800">
                  <v-row align="center" justify="space-around">
                    <v-icon>fas fa-lock</v-icon>
                  </v-row>
                  <v-card-text>
                    <v-container>
                      <v-row>
                        <v-col cols="30" sm="15" md="8">
                          
                        </v-col>

                        <v-col cols="30" sm="15" md="8">
                          <v-textarea
                            v-model="currentQuestion.question"
                            background-color="grey lighten-4"
                            color="primary"
                            label="Question du sondage"
                          ></v-textarea>
                        </v-col>
                        <v-col cols="12" sm="4">
                          <v-overflow-btn
                            class="my-2"
                            :items="dropdown_font"
                            label="Liste déroulante"
                            target="#dropdown-example"
                          ></v-overflow-btn>
                        </v-col>
                      </v-row>
                    </v-container>
                    <v-container>
                      <v-row>
                        <v-col cols="17" sm="8" md="6">
                          <v-text-field
                            label="Saisir un choix de réponse"
                          ></v-text-field>
                        </v-col>

                        <v-col cols="4" sm="2">
                          <v-btn
                            class="mx-2"
                            fab
                            dark
                            small
                            color="primary"
                            @click="deleteItem(item)"
                          >
                            <v-icon>mdi-delete</v-icon>
                          </v-btn>
                        </v-col>
                      </v-row>
                    </v-container>

                    <v-container>
                      <v-row v-for="(input, k) in inputs" :key="k">
                        <v-col cols="17" sm="8" md="6">
                          <v-text-field
                            v-model="input.name"
                            label="Saisir un choix de réponse"
                          ></v-text-field>
                        </v-col>

                        <v-col cols="2" sm="1">
                          <v-btn
                            class="mx-2"
                            fab
                            dark
                            small
                            color="primary"
                            @click="add(k, input.name)"
                          >
                            <v-icon dark>mdi-plus</v-icon>
                          </v-btn>
                        </v-col>
                        <v-col cols="4" sm="2">
                          <v-btn
                            class="mx-2"
                            fab
                            dark
                            small
                            color="primary"
                            @click="deleteItem(k)"
                          >
                            <v-icon>mdi-delete</v-icon>
                          </v-btn>
                        </v-col>
                      </v-row>
                    </v-container>

                    <v-col class="text-center" cols="12" sm="4">
                      <div class="my-2">
                        <v-btn color="primary" @click="addQuestion()"
                          >Ajouter une question<v-icon dark
                            >mdi-plus</v-icon
                          ></v-btn
                        >
                      </div>
                    </v-col>
                  </v-card-text>

                  <v-divider></v-divider>

                </v-card>
              </v-card>
            </v-flex>
          </v-layout>
        </v-flex>
      </v-container>
    
  </div>
</template>

<script>
export default {

 props:['props','questions'],
 
 props: {
  names: {
      type: String,
      required: true,
      default: null,
    },
    },
  data: () => ({
    inputs: [{ name: "" }],
    currentResponses: [],
    currentQuestion: {
      question: "",
      responses: [
        {
          response: "",
          responseType: ""
        }
      ]
    },

    dropdown_font: [
      "Choix multiples",
      "Cases à cocher",
      "Liste déroulante",
      "evaluation par étoiles"
    ],
    dropdown_icon: [
      { text: "list", callback: () => console.log("list") },
      { text: "favorite", callback: () => console.log("favorite") },
      { text: "delete", callback: () => console.log("delete") }
    ],
    dropdown_edit: [
      { text: "100%" },
      { text: "75%" },
      { text: "50%" },
      { text: "25%" },
      { text: "0%" }
    ],
    props: ["names"]
  }),

  methods: {
    valid() {
      this.addQuestion()
      this.ok = false;
    },
    back() {
      this.show = false;
      this.$emit("showListerAction", this.show);
    },

    add(index,response) {
    console.log(index)
        console.log(response)

      this.inputs.push({ name: "" });
    },
    deleteItem: function(index) {
      if (this.inputs.length > 1) {this.inputs.splice(index, 1);}
      this.currentResponses.splice(index, 1);
    },
    addQuestion() {
      this.currentQuestion.responses=[]
      for(const i in this.inputs){
        this.currentQuestion.responses.push(this.inputs[i].name)
      }
     // this.currentQuestion.responses = this.currentResponses;
      this.questions.push(this.currentQuestion);
      this.currentQuestion = {};
      this.currentResponses = [];
      this.inputs = [{ name: "" }];
    }
  }
};
</script>
<style scoped>
.controls {
  position: relative;
}
</style>
