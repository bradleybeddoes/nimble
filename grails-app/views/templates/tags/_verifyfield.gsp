<input type="text" size="30" id="${id}" name="${name}" value="${value}" class="${cssclass}" onBlur="verifyUnique('#${id}', '#${id}status', '${createLink(controller: controller, action:action)}', '${validmsg}', '${invalidmsg}');"/>

<g:if test="${required}">
	<span class="icon icon_bullet_green">&nbsp;</span>
</g:if>

<span id="${id}status" class="icon">&nbsp;</span>