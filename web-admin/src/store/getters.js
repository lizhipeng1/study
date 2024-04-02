import { constantRoutes } from '@/router'
const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  username: state => state.user.username,
  menuList: state => constantRoutes.concat(state.user.menuList),
  permissions: state => state.user.permissions
}
export default getters
