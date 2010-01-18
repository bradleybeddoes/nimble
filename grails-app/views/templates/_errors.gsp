<g:hasErrors bean="${bean}">
  <div class="error rounded">
    <strong><g:message code="nimble.label.error" /></strong>
    <g:renderErrors bean="${bean}" as="list"/>
  </div>
</g:hasErrors>