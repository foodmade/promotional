import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    brands: [],
    articles: [],
    team: [],
    loading: false
  },
  mutations: {
    SET_BRANDS(state, brands) {
      state.brands = brands
    },
    SET_ARTICLES(state, articles) {
      state.articles = articles
    },
    SET_TEAM(state, team) {
      state.team = team
    },
    SET_LOADING(state, status) {
      state.loading = status
    }
  },
  actions: {
    async fetchBrands({ commit }) {
      commit('SET_LOADING', true)
      try {
        const response = await Vue.prototype.$http.get('/api/brands')
        commit('SET_BRANDS', response.data)
      } catch (error) {
        console.error('获取品牌数据失败:', error)
      } finally {
        commit('SET_LOADING', false)
      }
    },
    async fetchArticles({ commit }) {
      commit('SET_LOADING', true)
      try {
        const response = await Vue.prototype.$http.get('/api/articles')
        commit('SET_ARTICLES', response.data)
      } catch (error) {
        console.error('获取文章数据失败:', error)
      } finally {
        commit('SET_LOADING', false)
      }
    },
    async fetchTeam({ commit }) {
      commit('SET_LOADING', true)
      try {
        const response = await Vue.prototype.$http.get('/api/team')
        commit('SET_TEAM', response.data)
      } catch (error) {
        console.error('获取团队数据失败:', error)
      } finally {
        commit('SET_LOADING', false)
      }
    }
  },
  getters: {
    getBrands: state => state.brands,
    getArticles: state => state.articles,
    getTeam: state => state.team,
    isLoading: state => state.loading
  }
}) 