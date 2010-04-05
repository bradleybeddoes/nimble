nimble.endpoints=$H(nimble.endpoints).merge({
 user: {
  'logins':'${createLink(action:'listlogins')}',
  'enableAPI':'${createLink(action:'enableapi')}',
  'disableAPI':'${createLink(action:'disableapi')}',
  'enable':'${createLink(action:'enable')}',
  'disable':'${createLink(action:'disable')}'
 }
}).toObject();

document.observe("dom:loaded", function() {
  nimble.listLogins('${user.id}');
  <g:if test="${user?.enabled}">
    $("enableuser").hide();
    $("enableduser").hide();
  </g:if>
  <g:else>
    $("disableuser").hide();
    $("disableduser").hide();
  </g:else>
  <g:if test="${user?.remoteapi}">
    $("disabledapi").hide();
    $("enableuserapi").hide();
  </g:if>
  <g:else>
    $("enabledapi").hide();
    $("disableuserapi").hide();
  </g:else>
});