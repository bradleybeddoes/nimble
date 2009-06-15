<%@ page import="intient.nimble.service.ProfileService" %>

<div class="left">

  <n:hasPermission target="${ProfileService.editPermission + profile.owner.id}">
    <div id="profilephoto" class="photo">
      <g:link action="editphoto" id="${user.id}" class=""><n:photo id="${user.id}" size="180"/></g:link>
    </div>
  </n:hasPermission>

  <n:lacksPermission target="${ProfileService.editPermission + profile.owner.id}">
    <div id="profilephoto" class="photo">
      <n:photo id="${user.id}" size="180"/>
    </div>
  </n:lacksPermission>

  <g:if test="${profile.bio != null}">
    <div class="bio">${profile.bio}</div>
  </g:if>

  <n:hasPermission target="${ProfileService.editPermission + profile.owner.id}">
    <div class="controls">
      <ul>
        <li>
        <g:link action="editphoto" id="${user.id}" class="icon icon_image">Change Photo</g:link>
        </li>
        <li>
        <g:link action="editaccount" id="${user.id}" class="icon icon_user_edit">Edit Account Details</g:link>
        </li>
        <li>
        <g:link action="editcontact" id="${user.id}" class="icon icon_phone">Edit Contact Details</g:link>
        </li>
        <!--
        <li>
        <g:link action="editsocial" id="${user.id}" class="icon icon_transmit">Edit Social Services</g:link>
        </li>
        -->
      </ul>
    </div>
  </n:hasPermission>

</div>
