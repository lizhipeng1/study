module.exports = {

  title: '全优学堂',

  /**
   * @type {boolean} true | false
   * @description Whether fix the header
   */
  fixedHeader: false,

  /**
   * @type {boolean} true | false
   * @description Whether show the logo in sidebar
   */
  sidebarLogo: true,

  tagsView: true,

  oauth2: {
    url: process.env.VUE_APP_SSO_SERVER_URL + '/oauth/authorize',
    clientId: process.env.VUE_APP_OAUTH2_CLIENT_ID,
    responseType: 'code',
    redirectUrl: process.env.VUE_APP_BUSINESS_SERVER_URL + '/oauth2/callback',
    scope: process.env.VUE_APP_OAUTH2_SCOPE,
    state: process.env.VUE_APP_FRONT_WEB_URL
  }
}
