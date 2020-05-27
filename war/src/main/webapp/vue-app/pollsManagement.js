import pollsManagementApp from './components/PollsManagementApp.vue';
import './../css/main.less';
import CKEditor from '@ckeditor/ckeditor5-vue';
Vue.use(CKEditor);
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
          render: (h) => h(pollsManagementApp),
          i18n,
          vuetify
      }).$mount('#pollsManagementApp');
      Vue.prototype.$vueT = Vue.prototype.$t;
      Vue.prototype.$t = (key, defaultValue) => {
          const translation = vueApp.$vueT(key);
          return translation !== key && translation || defaultValue;
      }
  });
});