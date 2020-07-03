import pollsApp from './components/PollsApp.vue';
import Chartkick from 'vue-chartkick'
import './../css/main.less';
import Chart from 'chart.js'

Vue.use(Chartkick.use(Chart))
Vue.use(Vuetify);


const vuetify = new Vuetify({
    dark: true,
    iconfont: 'mdi',
});

$(document).ready(() => {
    const lang = eXo && eXo.env && eXo.env.portal && eXo.env.portal.language;
    const url = `${eXo.env.portal.context}/${eXo.env.portal.rest}/i18n/bundle/locale.addon.Polls-${lang}.json`;

    exoi18n.loadLanguageAsync(lang, url).then(i18n => {
        const vueApp = new Vue({
            render: (h) => h(pollsApp),
            i18n,
            vuetify
        }).$mount('#pollsApp');
        Vue.prototype.$vueT = Vue.prototype.$t;
        Vue.prototype.$t = (key, defaultValue) => {
            const translation = vueApp.$vueT(key);
            return translation !== key && translation || defaultValue;
        }
    });
});