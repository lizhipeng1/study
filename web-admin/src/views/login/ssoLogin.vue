<template>
  <iframe ref="iframe" :src="loginUrl" frameborder="0" scrolling="no" />
</template>

<script>
import defaultSettings from '@/settings'

console.log(defaultSettings)
export default {
  name: 'SsoLogin',
  data() {
    return {
      url: defaultSettings.oauth2.url,
      clientId: defaultSettings.oauth2.clientId,
      responseType: defaultSettings.oauth2.responseType,
      redirectUrl: defaultSettings.oauth2.redirectUrl,
      scope: defaultSettings.oauth2.scope,
      state: defaultSettings.oauth2.state,

      iframeWin: null
    }
  },
  computed: {
    loginUrl() {
      return this.url + '?client_id=' + this.clientId + '&response_type=' + this.responseType + '&scope=' + this.scope +
        '&redirect_uri=' + this.redirectUrl +
        '&state=' + this.state
    }
  },
  watch: {

  },
  mounted() {
    window.addEventListener('message', this.handleMessage)
    this.iframeWin = this.$refs.iframe.contentWindow
  },
  created() {
    const redirect = this.$route.query.redirect

    if (redirect !== undefined && typeof redirect === 'string') {
      console.log(redirect)
      localStorage.setItem('loginSuccessRedirect', redirect)
    }
  },
  methods: {
    handleMessage: function(event) {
      if (event.data.type && event.data.type === 'tokenSet') {
        const accessToken = event.data.t

        const loginSuccessRedirect = localStorage.getItem('loginSuccessRedirect')
        this.$store.dispatch('user/loginSuccess', accessToken)
        this.$router.push({ path: loginSuccessRedirect || '/' })
      } else if (event.data.type && event.data.type === 'refresh') {
        location.reload()
      }
    }
  }
}
</script>

<style scoped lang="scss">
  iframe {
    position: absolute;
    top: 0px;
    left: 0px;
    right: 0px;
    height: 100%;
    width: 100%;
  }
</style>
