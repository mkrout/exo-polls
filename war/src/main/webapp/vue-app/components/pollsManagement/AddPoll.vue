<template>
<div>

<v-stepper v-model="e1">
    <v-stepper-header>

      <v-stepper-step :complete="e1 > 1" step="1">Poll Details</v-stepper-step>

      <v-divider></v-divider>

      <v-stepper-step :complete="e1 > 2" step="2">Questions</v-stepper-step>

      <v-divider></v-divider>

      <v-stepper-step :complete="e1 > 3" step="3">Review</v-stepper-step>
    </v-stepper-header>

    <v-stepper-items>
      <v-stepper-content step="1">
        <v-card flat>
              <v-card class="mx-auto" max-width="1000">
                <v-form ref="form">
                  <v-text-field
                    id="nom_s"
                    v-model="editedItem.name"
                    :counter="30"
                    label="Poll Name"
                    :append-icon="show1 ? 'mdi-pencil' : 'mdi-lead-pencil'"
                    required
                  ></v-text-field>
                     <v-container> 
                       <v-row>
                         <v-col cols="5" lg="4">
        <v-menu
          ref="menu1"
          v-model="menu1"
          :close-on-content-click="false"
          transition="scale-transition"
          offset-y
          max-width="290px"
          min-width="290px"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
              v-model="dateFormatted1"
              label="Creation date"
              hint="MM/DD/YYYY format"
              persistent-hint
              prepend-icon="event"
              v-bind="attrs"
              @blur="date = parseDate(dateFormatted)"
              v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker v-model="date1" no-title @input="menu1 = false"></v-date-picker>
        </v-menu>
       </v-col>
         <v-col cols="5" lg="4">
        <v-menu
          v-model="menu2"
          :close-on-content-click="false"
          transition="scale-transition"
          offset-y
          max-width="290px"
          min-width="290px"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
              v-model="dateFormatted2"
              label="Publication date"
              hint="MM/DD/YYYY format"
              persistent-hint
              prepend-icon="event"
              v-bind="attrs"
              v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker v-model="date2" no-title @input="menu2 = false"></v-date-picker>
        </v-menu>
      </v-col>
      <v-col cols="5" lg="4">
      <v-menu
          ref="menu3"
          v-model="menu3"
          :close-on-content-click="false"
          transition="scale-transition"
          offset-y
          max-width="290px"
          min-width="290px"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
              v-model="dateFormatted3"
              label=" date"
              hint="MM/DD/YYYY format"
              persistent-hint
              prepend-icon="event"
              v-bind="attrs"
              @blur="date = parseDate(dateFormatted)"
              v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker v-model="date3" no-title @input="menu3 = false"></v-date-picker>
        </v-menu></v-col>

                       </v-row>
                        </v-container>
                    


                

                  <v-container fluid>
                    <v-radio-group v-model="radios">
                      <template v-slot:label>
                        <div>
                          <strong>choose a format to answer the poll</strong>
                        </div>
                      </template>
                      <v-radio value="Google">
                        <template v-slot:label>
                          <div>
                            Type 1:
                            <strong class="primary--text">Classic</strong>
                          </div>
                        </template>
                      </v-radio>
                      <v-radio value="Duckduckgo">
                        <template v-slot:label>
                          <div>
                            Type 2:
                            <strong class="primary--text"
                              >Question by question</strong>
                          </div>
                        </template>
                      </v-radio>
                    </v-radio-group>
                  </v-container>
                </v-form>
              </v-card>
            </v-card>

        <v-btn
          color="primary"
          @click="e1 = 2"
        >
          Continue
        </v-btn>

        <v-btn text @click="e1 = 1">Cancel</v-btn>
      </v-stepper-content>

      <v-stepper-content step="2">
        <Lister
      :names="names"
      :questions="questions"
      @showListerAction="showHelloWord()"
    ></Lister>

        <v-btn
          color="primary"
          @click="e1 = 3"
        >
          Continue
        </v-btn>

        <v-btn text @click="e1 = 1">Cancel</v-btn>
      </v-stepper-content>

      <v-stepper-content step="3">
        <v-card>
        <Analyse :questions="questions"></Analyse>
        </v-card>

        <v-btn
          color="primary"
          @click="save()"
        >
          Save
        </v-btn>

        <v-btn text @click="e1 = 2" >Cancel </v-btn>
      </v-stepper-content>
    </v-stepper-items>
  </v-stepper>
  </div>
</template>
<script>
import Lister from "./Lister.vue";
import Analyse from "./Analyse.vue";
export default {
  components: {
      
    Lister,
    Analyse
  },
    
  data: (vm) => ({
    e1: 1,
    loaded: false,
    questions: [],
    chartdata: null,
    tab: null,
    date1: new Date().toISOString().substr(0, 10),
        date2: new Date().toISOString().substr(0, 10),
            date3: new Date().toISOString().substr(0, 10),
      dateFormatted1: vm.formatDate(new Date().toISOString().substr(0, 10)),
            dateFormatted2: vm.formatDate(new Date().toISOString().substr(0, 10)),
                  dateFormatted3: vm.formatDate(new Date().toISOString().substr(0, 10)),
      menu1: false,
      menu2: false,
      menu3: false,
names: "",
    checkbox: false,
    radios: "Duckduckgo",
    switchMe: false,
    show1: false,
    showLister: false,
    dialog: false,
    editedIndex: -1,
    editedItem: {
      name: "",
        names: "app",
      active: true,
    }
  }),

  computed: {
  
    computedDateFormatted1 () {
        return this.formatDate(this.date1)
      },
          computedDateFormatted2 () {
        return this.formatDate(this.date2)
      },
    computedDateFormatted3 () {
        return this.formatDate(this.date3)
      },
    formTitle() {
      return this.editedIndex === -1
        ? "Creér un nouveau sondage "
        : "Edit Item";
    },
},

  watch: {
 
       date (val) {
        this.dateFormatted1 = this.formatDate(this.date1)
      },
        date (val) {
        this.dateFormatted2 = this.formatDate(this.date2)
      },
        date (val) {
        this.dateFormatted3 = this.formatDate(this.date3)
      },
    dialog(val) {
     // val || this.close();
    },
  
  },



  methods: {
 formatDate (date) {
        if (!date){return null}

        const [year, month, day] = date.split('-')
        return `${month}/${day}/${year}`
      },
      parseDate (date) {
        if (!date) {return null}

        const [month, day, year] = date.split('/')
        return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`
      },
        save() {
            this.editedItem.createdDateFormatted=this.dateFormatted
            this.editedItem.publicationDateFormatted=this.dateFormatted
            this.editedItem.expirationDateFormatted=this.dateFormatted
            fetch(`/portal/rest/pollsmanagement/polls`, {
                    method: 'post',
                    credentials: 'include',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(this.editedItem),
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
        },
    
  },
};
</script>
