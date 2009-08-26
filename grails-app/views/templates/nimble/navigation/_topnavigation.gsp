
<div>
  <ul id="topnavigation_" class="horizmenu mlhorizmenu">

    <n:isAdministrator>
      <li class="${controllerName.equals('admins') ? 'current' : ''}">
      <g:link controller="admins" action="index" class="icon icon_user_suit">Administrators</g:link>
      <ul class="submenu">
        <li>
          <g:link controller="admins" action="index" class="icon icon_user_suit">Manage Administrators</g:link>
        </li>
      </ul>
      </li>

      <li class="${controllerName.equals('user') ? 'current' : ''}">
      <g:link controller="user" action="list" class="icon icon_user">Users</g:link>
        <ul class="submenu">
          <li><g:link controller="user" action="list" class="icon icon_user">List Users</g:link></li>
          <li><g:link controller="user" action="create" class="icon icon_user_add">Create User</g:link></li>
        </ul>
      </li>

      <li class="${controllerName.equals('role') ? 'current' : ''}">
        <g:link controller="role" action="list" class="icon icon_cog">Roles</g:link>
        <ul class="submenu">
          <li><g:link controller="role" action="list" class="icon icon_cog">List Roles</g:link></li>
          <li><g:link controller="role" action="create" class="icon icon_cog_add">Create Role</g:link></li>
        </ul>
      </li>

      <li class="${controllerName.equals('group') ? 'current' : ''}">
        <g:link controller="group" action="list" class="icon icon_group">Groups</g:link>
        <ul class="submenu">
          <li><g:link controller="group" action="list" class="icon icon_group">List Groups</g:link></li>
          <li><g:link controller="group" action="create" class="icon icon_group_add">Create Group</g:link></li>
        </ul>
      </li>

      <li class="${controllerName.equals('auth') ? 'current' : ''}">
        <g:link controller="auth" action="logout" class="icon icon_cross">Logout</g:link>
      </li>
    </n:isAdministrator>
  </ul>
  
</div>
