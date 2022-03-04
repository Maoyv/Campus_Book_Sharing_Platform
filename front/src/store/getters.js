const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  userId: state => state.user.userId,
  name: state => state.user.name,
  role: state => state.user.role,
  roles: state => state.user.roles,
  bookCategory: state=> state.user.bookCategory,
  permission_routes: state=>state.permission.routes,
}
export default getters
