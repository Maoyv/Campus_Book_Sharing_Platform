import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import router, { resetRouter } from '@/router'
import store from "@/store";

const getDefaultState = () => {
  return {
    token: getToken(),
    userId:'',
    name: '',
    avatar: '',
    role:'',
    roles:[],
    bookCategory:[],
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_USERID: (state, userId) => {
    state.userId = userId
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  SET_ROLE: (state, role) => {
    state.role = role
  },
  SET_BOOK_CATEGORY: (state, bookCategory) =>{
    state.bookCategory = bookCategory
  },
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { userId, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ userId: userId, password: password }).then(async response => {
        const {data} = response
        // 存入全局变量
        commit('SET_USERID',data.userId)
        commit('SET_TOKEN', data.token)
        commit('SET_NAME', data.name)
        commit('SET_AVATAR', data.avatar)
        commit('SET_ROLE', data.role)
        commit('SET_ROLES', data.permissions)
        commit('SET_BOOK_CATEGORY', data.bookCategory)
        setToken(data.token)

        const accessRoutes = await store.dispatch('permission/generateRoutes', data.permissions)

        router.options.routes = store.getters.permission_routes

        router.addRoutes(accessRoutes)

        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit}) {
    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const { data } = response

        if (!data) {
          return reject('验证失败，请重新登录.')
        }
        commit('SET_USERID',data.userId)
        commit('SET_NAME', data.name)
        commit('SET_AVATAR', data.avatar)
        commit('SET_ROLE', data.role)
        commit('SET_ROLES', data.permissions)
        commit('SET_BOOK_CATEGORY', data.bookCategory)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        removeToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  },

  // 更改全局变量中的用户名
  setUserName({ commit },data){
    commit('SET_NAME', data)
  },

  // 更改全局变量中的书籍类型
  setBookCategory({ commit },data){
    commit('SET_BOOK_CATEGORY', data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

