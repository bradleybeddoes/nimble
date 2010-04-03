document.observe("dom:loaded", function() {
    <g:if test="${flash.message != null && flash.message.length() > 0}">
	  <g:if test="${flash.type != null && flash.type.length() > 0}">
	    growl("${flash.type.encodeAsHTML()}", "${flash.message.encodeAsHTML()}", 5000);
	  </g:if>
	  <g:else>
	    growl("info", "${flash.message.encodeAsHTML()}", 5000);
	  </g:else>
  </g:if>
});