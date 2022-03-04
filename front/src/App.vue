<template>
  <div id="app">
<!--重载路由刷新页面-->
<!--<router-view v-if="isRouterAlive"/>-->
    <!--控制变量刷新页面-->
    <router-view :key="$store.state.key"/>
  </div>
</template>

<script>
export default {
  name: 'App',
  // 重载路由刷新页面
  // provide () {
  //   return {
  //     reload: this.reload
  //   }
  // },
  // data () {
  //   return {
  //     isRouterAlive: true
  //   }
  // },
  // methods: {
  //   reload () {
  //     this.isRouterAlive = false
  //     this.$nextTick(function() {
  //       this.isRouterAlive = true
  //     })
  //   }
  // },
}
</script>
