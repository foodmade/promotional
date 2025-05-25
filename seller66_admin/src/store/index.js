import { createStore } from 'vuex'
import { getToken, setToken, removeToken } from '../utils/auth'

const store = createStore({
  state: {
    token: getToken(),
    user: JSON.parse(localStorage.getItem('user')) || null,
    sidebar: {
      opened: localStorage.getItem('sidebarStatus') ? !!+localStorage.getItem('sidebarStatus') : true,
      withoutAnimation: false
    }
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_USER: (state, user) => {
      state.user = user
      localStorage.setItem('user', JSON.stringify(user))
    },
    TOGGLE_SIDEBAR: (state) => {
      state.sidebar.opened = !state.sidebar.opened
      state.sidebar.withoutAnimation = false
      if (state.sidebar.opened) {
        localStorage.setItem('sidebarStatus', 1)
      } else {
        localStorage.setItem('sidebarStatus', 0)
      }
    },
    CLOSE_SIDEBAR: (state, withoutAnimation) => {
      localStorage.setItem('sidebarStatus', 0)
      state.sidebar.opened = false
      state.sidebar.withoutAnimation = withoutAnimation
    }
  },
  actions: {
    // 用户登录
    login({ commit }, userInfo) {
      const { username, password } = userInfo
      return new Promise((resolve, reject) => {
        // 这里应该是API调用，但我们模拟登录逻辑
        setTimeout(() => {
          if (username === 'admin' && password === '123456') {
            const token = 'admin-token'
            const user = {
              id: 1,
              username: 'admin',
              name: '管理员',
              avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif'
            }
            commit('SET_TOKEN', token)
            commit('SET_USER', user)
            setToken(token)
            resolve()
          } else {
            reject(new Error('用户名或密码错误'))
          }
        }, 1000)
      })
    },
    
    // 退出登录
    logout({ commit }) {
      return new Promise((resolve) => {
        commit('SET_TOKEN', '')
        commit('SET_USER', null)
        removeToken()
        resolve()
      })
    }
  },
  getters: {
    token: state => state.token,
    user: state => state.user,
    sidebar: state => state.sidebar
  }
})

export default store 