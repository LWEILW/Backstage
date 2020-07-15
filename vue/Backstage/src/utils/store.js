import Vue from 'vue'
import Vuex from 'vuex'
import fa from "element-ui/src/locale/lang/fa";

Vue.use(Vuex)

const state = {
  httpError: {
    hasError: false,
    status: '',
    statusText: ''
  },
  isCollapse: false
}


const mutations = {
  ON_HTTP_ERROR(state, payload) {
    state.httpError = payload
  },

  changeStatus(state, collapseStatus) {
    state.isCollapse = collapseStatus
  }
}

export default new Vuex.Store({
  state,
  mutations
})
