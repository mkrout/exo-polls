<template>
  <div>
            <v-card v-if="!showAdd">

                <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
                <v-data-table
                  :headers="headers"
                  :items="polls"
                  sort-by="datecre"
                  class="elevation-1"
                  :search="search"

                >
                  
                  <template v-slot:top>
                    <v-toolbar flat color="white">
                      <v-toolbar-title>Poll Archive</v-toolbar-title>
                      <v-divider class="mx-3" inset vertical></v-divider>
                      <v-spacer></v-spacer>
                              <v-dialog v-model="dialog" max-width="500px">
          
          <v-card>
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.name" label="Poll name"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.createdDateFormatted" label="Creation date"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.publicationDateFormatted" label="Publication date"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.expirationDateFormatted" label="Expiration date"></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
              <v-btn color="blue darken-1" text @click="save()">Save</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
                      <v-btn color="primary" dark class="mb-2"  @click="showAddForm()">Create new Poll</v-btn>
                    </v-toolbar>
                  </template>
                    <template v-slot:item.active="{ item }">
                      <v-switch v-model="item.active"></v-switch>
                  </template>
                  <template v-slot:item.actions="{ item }">
                    <v-icon small class="mr-2" color="primary" @click="editItem(item)">mdi-pencil</v-icon>
                    <v-icon small color="primary" @click="deleteItem(item)">mdi-delete</v-icon>
                  </template>
                  <template v-slot:no-data>
                    <v-btn color="primary" dark class="mb-2"  @click="showAddForm()">Create New Poll</v-btn>
                  </template>
                </v-data-table>
            </v-card>
            <AddPoll    v-if="showAdd" ></AddPoll>
    </div>
</template>
<script>
import AddPoll from './AddPoll.vue';
export default {
      components: {
        AddPoll,
    },
   props: {
    showAdd: {
      type: Boolean,
      required: true,
      default: null,
    }
   },
 
  data: (vm) => ({
    dialog: false,
    search:'',
    showAdd:false,
    loaded: false,
    chartdata: null,
    names: '',
     show1: false,
     showLister: false,
    dialog: false,
    headers: [
      {
        text: 'Poll Name',
        align: 'start',
        sortable: false,
        value: 'name',
      },
      {text: 'Creation Date', value: 'createdDateFormatted'},
      {text: 'Publication Date', value: 'publicationDateFormatted'},
      {text: 'Expiration Date', value: 'expirationDateFormatted'},
      {text: 'Response Number', value: 'nbrrep'},
      {text: 'Active', value: 'active'},
      {text: 'Actions', value: 'actions', sortable: false},
    ],
    polls: [],
    editedIndex: -1,
    editedItem: {
      name: '',
      datecre: 0,
      dateexp: 0,
      nbrrep: 0,
      etat: 0,
    },
    defaultItem: {
      name: '',
      datecre: 0,
      dateexp: 0,
      nbrrep: 0,
      etat: 0,
    },
  }),

  created() {
    this.initialize();
  },
 computed: {
      formTitle () {
        return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
      },
    },
 watch: {
      dialog (val) {
       // val || this.close()
      },
    },

    created () {
      this.initialize()
    },
  methods: {
    initialize() {
       
      fetch(`/portal/rest/pollsmanagement/polls`, {
                    credentials: 'include',
                })
                .then((resp) => resp.json())
                .then((resp) => {
                    this.polls = resp;
                });
         
    },
      

    deleteItem(item) {
      const index = this.polls.indexOf(item);
        fetch(`/portal/rest/pollsmanagement/polls/${item.id}`, {
                     credentials: 'include',
                      method: 'DELETE'
                });
    //  confirm('Are you sure you want to delete this item?') && this.polls.splice(index, 1);
    },
    editItem(item) {
      this.editedIndex = this.polls.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },
   
    showAddForm() {
      this.showAdd = !this.showAdd;
    },
   close () {
        this.dialog = false
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        })
      },

      save () {
        if (this.editedIndex > -1) {
          Object.assign(this.polls[this.editedIndex], this.editedItem)
        } else {
          this.polls.push(this.editedItem)
        }
        this.close()
      },
    
  },

};
</script>
