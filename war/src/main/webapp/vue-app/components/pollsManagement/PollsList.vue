<template>
  <div>
            <v-card v-if="!showAdd">
                <v-data-table
                  :headers="headers"
                  :items="polls"
                  sort-by="datecre"
                  class="elevation-1"
                >
                  <template v-slot:item.etat="{ item }">
                    <v-switch v-model="item.etat"></v-switch>
                  </template>

                  <template v-slot:top>
                    <v-toolbar flat color="white">
                      <v-toolbar-title>Poll Archive</v-toolbar-title>
                      <v-divider class="mx-4" inset vertical></v-divider>
                      <v-spacer></v-spacer>
                      <v-btn color="primary" dark class="mb-2"  @click="showAddForm()">Create new Poll</v-btn>
                    </v-toolbar>
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
            <add-poll  v-if="showAdd" />
    </div>
</template>
<script>
import AddPoll from './AddPoll.vue';
export default {
      components: {
        AddPoll,
    },
 
  data: (vm) => ({
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
      {text: 'State', value: 'active'},
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
      //confirm('Are you sure you want to delete this item?') && this.polls.splice(index, 1);
    },

    showAddForm() {
      this.showAdd = !this.showAdd;
    },
  },

};
</script>
