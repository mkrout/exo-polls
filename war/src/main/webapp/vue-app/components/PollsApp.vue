<template>
<v-app class="VuetifyApp" color="transaprent" id="pollsApp">
    <main>
        <v-card class="mx-auto" max-width="300" tile>
            <v-list>
                <v-subheader><strong> Current Polls</strong></v-subheader>
                <v-list-item-group v-model="poll" color="primary">
                    <v-list-item v-for="(poll, i) in polls" :key="i"   
                      @click="getQuestions(poll.id)">
                        <v-list-item-icon>
                            <v-icon>mdi-poll-box-outline</v-icon>
                        </v-list-item-icon>
                        <v-list-item-content>
                            <v-list-item-title v-text="poll.name"></v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </v-list-item-group>
            </v-list>
        </v-card>

        <poll-drawer ref="pollDrawer" :poll="selectedPoll"/>

    </main>
</v-app>
</template>

<script>
import pollDrawer from './polls/PollDrawer.vue';
export default {
    components: {
        pollDrawer
    },
    data: () => ({
        alert: false,
        message: '',
        alert_type: '',
        alertIcon: '',
        polls: [],
        selectedPoll:{}

    }),

    created() {
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
        openPollDrawer() {
            this.$refs.pollDrawer.open()
        },
        getQuestions(id){
         fetch(`/portal/rest/pollsmanagement/polls/`+id, {
                    credentials: 'include',
                })
                .then((resp) => resp.json())
                .then((resp) => {
                    this.selectedPoll = resp;
                    this.openPollDrawer()
                });   
        }
    },
};
</script>

<style>

</style>
