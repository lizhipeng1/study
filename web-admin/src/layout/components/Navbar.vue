<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb class="breadcrumb-container" />

    <div class="tenant-container">
      <div v-for="tenant in userTenantList" :key="tenant.tenantId" class="tenant-item" :class="{ active: tenant.nowTenant }" @click="switchTenant(tenant)">{{ tenant.tenantName }}</div>
    </div>

    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar">
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <el-dropdown-item>
            <div class="user-info">
              <img :src="avatar+'?imageView2/1/w/80/h/80'">
              <div>
                <div id="name">{{ name }}</div>
                <div id="username">{{ username }}</div>
              </div>
            </div>
          </el-dropdown-item>
          <router-link to="/">
            <el-dropdown-item>
              <i class="el-icon-s-home">&nbsp;首页</i>
            </el-dropdown-item>
          </router-link>
          <router-link to="/system/user/profile">
            <el-dropdown-item>
              <i class="el-icon-user-solid">&nbsp;个人中心</i>
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout"><i class="el-icon-switch-button">&nbsp;退出登录</i></span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import { loginUserTenantSelect } from '@/api/system/tenant/index'
import { switchNowTenant } from '@/api/system/user/index'

export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  data() {
    return {
      userTenantList: []
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'name',
      'username'
    ])
  },
  created() {
    this.getUserTenant()
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    logout() {
      this.$confirm('确定退出登录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(() => {
        this.$store.dispatch('user/logout').then(() => {
          this.$router.push(`/login?redirect=${this.$route.fullPath}`)
        }).catch(() => {

        })
      }).catch(() => {

      })
    },
    getUserTenant() {
      loginUserTenantSelect().then(res => {
        this.userTenantList = res.data
      })
    },
    switchTenant(tenant) {
      const _this = this
      this.confirm('确定切换?', function() {
        switchNowTenant(tenant.tenantId).then(res => {
          if (res.respCode === '0000') {
            _this.alert('切换租户成功', function() {
              _this.$router.push('/')
              location.reload()
            })
          } else {
            _this.msgError(res.respMsg)
          }
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 64px;
  line-height: 64px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 60px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 64px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        height: 64px;
        padding-top: 12px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
.user-info {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding: 10px 0;
  img {
    width: 30px;
    height: 30px;
    margin-right: 15px;
  }
  #username{
    font-size: 12px;
    color: #adb1b3;
    line-height: 15px;
  }
  #name{
    line-height: 25px;
    overflow: hidden;
    font-size: 16px;
    color: #444;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}
.tenant-container{
  height: 64px;
  float: left;
  margin-left: 64px;
  display: flex;

  .tenant-item{
    margin: 17px 0px;
    height: 30px;
    line-height: 28px;
    padding: 0px 15px;
    border-radius: 15px;
    color: #333;
    font-size: 14px;
    background-color: #fff;
    cursor: pointer;
    margin-right: 10px;
    border: 1px double #409EFF;
  }

  .tenant-item.active{
    color: #fff;
    background-color: #409EFF;
  }
}
</style>
