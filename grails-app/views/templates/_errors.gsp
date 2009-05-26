<g:hasErrors bean="${bean}">
  <div class="error rounded">
    <strong>Oops there were some errors..</strong>
    <g:renderErrors bean="${bean}" as="list"/>
  </div>
</g:hasErrors>