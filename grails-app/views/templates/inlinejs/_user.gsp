var userLoginsEndpoint = "${createLink(action:'listlogins')}"
var enableUserEndpoint = "${createLink(action:'enable')}";
var disableUserEndpoint = "${createLink(action:'disable')}";
var enableAPIEndpoint = "${createLink(action:'enableapi')}";
var disableAPIEndpoint = "${createLink(action:'disableapi')}";

$(function() {
	listLogins('${user.id}');
	<g:if test="${user?.enabled}">
	  $("#enableuser").hide();
	  $("#enableduser").hide();
	</g:if>
	<g:else>
	  $("#disableuser").hide();
	  $("#disableduser").hide();
	</g:else>

	<g:if test="${user?.remoteapi}">
	  $("#disabledapi").hide();
	  $("#enableuserapi").hide();
	</g:if>
	<g:else>
	  $("#enabledapi").hide();
	  $("#disableuserapi").hide();
	</g:else>
});