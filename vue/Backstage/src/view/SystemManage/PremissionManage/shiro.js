import Vue from 'vue'

/**权限指令**/
Vue.directive('has', {
  inserted: function (el, binding) {
    debugger
    if (!Vue.prototype.$_has(binding.value)) {
      el.parentNode.removeChild(el);
    }
  }
});

//权限检查方法
Vue.prototype.$_has = function (value) {
  debugger
  let isExist = false;
  let user = localStorage.getItem("user");
  if ("admin" == user) {
    return true;
  }
  let premissionsStr = localStorage.getItem("permissions");
  if (premissionsStr == undefined || premissionsStr == null) {
    return false;
  }
  if (premissionsStr.indexOf(value) > -1) {
    isExist = true;
  }
  return isExist;
};
