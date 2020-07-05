<template>
<v-app class="VuetifyApp" color="transaprent" id="pollsApp">
    <main>
        <v-card class="mx-auto" max-width="600" title>
            <v-list>
                <v-footer padless color="grey">
                    <v-col class="text-center" cols="12">

                        <strong>POLLS</strong>
                    </v-col>
                </v-footer>
                <v-list-item-group v-model="poll" color="primary">
                    <v-list-item v-for="(poll, i) in polls" :key="i" @click="openDrawer(poll)">
                        <v-list-item-icon>
                            <v-icon color="blue">mdi-chart-bar</v-icon>
                        </v-list-item-icon>
                        <v-list-item-content>
                            <strong>
                                <v-list-item-title v-text="poll.name"></v-list-item-title>
                            </strong>
                        </v-list-item-content>
                    </v-list-item>
                </v-list-item-group>
            </v-list>
        </v-card>

        <poll-drawer ref="pollDrawer" :poll="selectedPoll" />
        <poll-result-drawer ref="pollResultDrawer" :results="results" :poll="currentPoll" />
    </main>
</v-app>
</template>

<script>
import pollDrawer from './polls/PollDrawer.vue';
import pollResultDrawer from './polls/PollResultDrawer.vue';
export default {
    components: {
        pollDrawer,
        pollResultDrawer,
    },
    data: () => ({
        alert: false,
        message: '',
        alert_type: '',
        alertIcon: '',
        polls: [],
        selectedPoll: {},
        results: {},
        currentPoll: {}
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
        openDrawer(poll) {
            if (poll.responded) {
                this.currentPoll = poll
                this.getResult(poll.id);
            } else {
                this.getQuestions(poll.id);
            }
        },
        openPollDrawer() {
            this.$refs.pollDrawer.open();
        },
        openPollResultDrawer() {
            this.$refs.pollResultDrawer.open();
        },
        getQuestions(id) {
            fetch(`/portal/rest/pollsmanagement/polls/` + id, {
                    credentials: 'include',
                })
                .then((resp) => resp.json())
                .then((resp) => {
                    this.selectedPoll = resp;
                    this.openPollDrawer();
                });
        },
        getResult(id) {
            fetch(`/portal/rest/pollsmanagement/responsenumber/` + id, {
                    credentials: 'include',
                })
                .then((resp) => resp.json())
                .then((resp) => {
                    const results = resp;
                    const data_ = []
                    for (let i = 0; i < results.length; i++) {
                        const qu = {}
                        qu.question = results[i].question

                        const respList = []
                        for (let j = 0; j < results[i].responses.length; j++) {
                            const resp_ = [results[i].responses[j].response, results[i].responses[j].number]
                            respList.push(resp_)
                        }

                        qu.responses = respList;
                        data_.push(qu)
                    }

                    this.results = data_
                    this.openPollResultDrawer();
                });
        },
    },
};
</script>

<style>
.element.style {
    max-width: 600px;
}
</style>
