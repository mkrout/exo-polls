import pollsManagementApp from './components/PollsManagementApp.vue';
Vue.use(Vuetify);
const vueInstance = new Vue({
  el: '#pollsManagementApp',
  render: (h) => h(pollsManagementApp),
});
