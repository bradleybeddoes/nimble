<g:if test="${flash.message != null && flash.message.length() > 0}">
  <g:if test="${flash.type != null && flash.type.length() > 0}">
    <div class="${flash.type}">

    <g:if test="${flash.type.equals('success')}">
      <span class="icon icon_tick">&nbsp;</span>
    </g:if>

    <g:if test="${flash.type.equals('error')}">
      <span class="icon icon_cross">&nbsp;</span>
    </g:if>

    <g:if test="${flash.type.equals('info')}">
      <span class="icon icon_information">&nbsp;</span>
    </g:if>

    <g:if test="${flash.type.equals('help')}">
      <span class="icon icon_cross">&nbsp;</span>
    </g:if>

    <g:if test="${flash.type.equals('flagred')}">
      <span class="icon icon_flag_red">&nbsp;</span>
    </g:if>

    <g:if test="${flash.type.equals('flaggreen')}">
      <span class="icon icon_flag_green">&nbsp;</span>
    </g:if>

    <g:if test="${flash.type.equals('flagblue')}">
      <span class="icon icon_flag_blue">&nbsp;</span>
    </g:if>

    ${flash.message}
    </div>
  </g:if>
  <g:else>
    <div class="flash-${flash.type}">
      ${flash.message}
    </div>
  </g:else>
</g:if>