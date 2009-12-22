// Admins
function listAdministrators() {
  $.ajax({
    type: "POST",
    url: adminListEndpoint,
    success: function(res) {
      $("#admins").empty();
      $("#admins").append(res);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'Failed to list administrators');
    }
  });
}

function searchAdministrators() {
  var dataString = "q=" + $('#q').val();
  $.ajax({
    type: "POST",
    url: adminSearchEndpoint,
    data: dataString,
    success: function(res) {
      $("#searchresponse").empty();
      $("#searchresponse").append(res);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'No users found');
    }
  });
}

function deleteAdministrator(userID, username) {
  var dataString = 'id=' + userID;
  $.ajax({
    type: "POST",
    url: adminDeleteEndpoint,
    data: dataString,
    success: function(res) {
      growl('success', 'Revoked administrator rights from ' + username);
      listAdministrators();
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'Failed to remove administrator rights from ' + username);
    }
  });
}

function grantAdministrator(userID, username) {
  var dataString = 'id=' + userID;
  $.ajax({
    type: "POST",
    url: adminGrantEndpoint,
    data: dataString,
    success: function(res) {
      growl('success', 'Granted administration rights to ' + username);
      searchAdministrators();
      listAdministrators();
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'Failed to grant administration rights to ' + username);
    }
  });
}

// Users
function enableUser(id) {
  var dataString = "id="+id;
  $.ajax({
    type: "POST",
    url: enableUserEndpoint,
    data: dataString,
    success: function(res) {
      $("#enableuser").hide();
      $("#enableduser").hide();
      $("#disableuser").show();
      $("#disableduser").show();
      growl('success', 'Account enabled');

    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'There was an internal error when attempting to enable this account');
    }
  });
}

function disableUser(id) {
  var dataString = "id="+id;
  $.ajax({
    type: "POST",
    url: disableUserEndpoint,
    data: dataString,
    success: function(res) {
      $("#disableuser").hide();
      $("#disableduser").hide();
      $("#enableuser").show();
      $("#enableduser").show();
      growl('success', 'Account disabled');

    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'There was an internal error when attempting to disable this account');
    }
  });
}

function enableAPI(id) {
  var dataString = "id="+id;
  $.ajax({
    type: "POST",
    url: enableAPIEndpoint,
    data: dataString,
    success: function(res) {
      $("#disabledapi").hide();
      $("#enabledapi").show();

      $("#disableuserapi").show();
      $("#enableuserapi").hide();
      growl('success', 'Remote API access enabled');

    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'There was an internal error when attempting to enable remote api access account');
    }
  });
}

function disableAPI(id) {
  var dataString = "id="+id;
  $.ajax({
    type: "POST",
    url: disableAPIEndpoint,
    data: dataString,
    success: function(res) {
      $("#disabledapi").show();
      $("#enabledapi").hide();

      $("#disableuserapi").hide();
      $("#enableuserapi").show();
      growl('success', 'Remote API access disabled');

    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'There was an internal error when attempting to disable remote api access for this account');
    }
  });
}

function listLogins(userID) {
  var dataString = 'id=' + userID;
  $.ajax({
    type: "GET",
    url: userLoginsEndpoint,
    data: dataString,
    success: function(res) {
      $("#loginhistory").empty().append(res).show();
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'Failed to list recent user logins');
    }
  });
}

// Permissions
function listPermissions(ownerID) {
  var dataString = 'id=' + ownerID;
  $.ajax({
    type: "GET",
    url: permissionListEndpoint,
    data: dataString,
    success: function(res) {
      $("#currentpermission").empty().append(res).show();
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'Failed to list permissions');
    }
  });
}

function createPermission(ownerID) {
  var dataString = 'id=' + ownerID + '&first=' + $('#first_p').val() + '&second=' + $('#second_p').val() + '&third=' + $('#third_p').val() + '&fourth=' + $('#fourth_p').val();
  $.ajax({
    type: "POST",
    url: permissionCreateEndpoint,
    data: dataString,
    success: function(res) {
      $("#addpermissionserror").empty();
	  $('#first_p').val('');
	  $('#second_p').val('');
	  $('#third_p').val('');
	  $('#fourth_p').val('');
      listPermissions(ownerID);
      growl('success', res);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      $("#addpermissionserror").empty().append(xhr.responseText)
      growl('error', 'Failed to create permission');
    }
  });
}

function removePermission(ownerID, permID) {
  var dataString = 'id=' + ownerID + '&permID=' + permID;
  $.ajax({
    type: "POST",
    url: permissionRemoveEndpoint,
    data: dataString,
    success: function(res) {
      listPermissions(ownerID);
      growl('success', res);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', xhr.responseText);
    }
  });
}

// Roles
function searchRoles(ownerID) {
  var dataString = "id=" + ownerID + "&q=" + $('#qroles').val();
  $.ajax({
    type: "POST",
    url: roleSearchEndpoint,
    data: dataString,
    success: function(res) {
      $("#rolesearchresponse").empty().hide();
      $("#rolesearchresponse").append(res).show();
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl("error", "There was an error executing your search");
    }
  });
}

function listRoles(ownerID) {
  var dataString = 'id=' + ownerID;
  $.ajax({
    type: "GET",
    url: roleListEndpoint,
    data: dataString,
    success: function(res) {
      $("#assignedroles").empty().append(res).show();
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'Failed to list roles');
    }
  });
}

function grantRole(ownerID, roleID) {
  var dataString = 'id=' + ownerID + '&roleID=' + roleID;
  $.ajax({
    type: "POST",
    url: roleGrantEndpoint,
    data: dataString,
    success: function(res) {
      listRoles(ownerID);
      searchRoles(ownerID);
      growl('success', 'Role was assigned');
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'Failed to assign role');
    }
  });
}

function removeRole(ownerID, roleID) {
  var dataString = 'id=' + ownerID + '&roleID=' + roleID;
  $.ajax({
    type: "POST",
    url: roleRemoveEndpoint,
    data: dataString,
    success: function(res) {
      listRoles(ownerID);
      growl('success', 'Role removed');
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'Failed to remove role');
    }
  });
}

// Groups
function searchGroups(parentID) {
  var dataString = "id=" + parentID + "&q=" + $('#qgroups').val();
  $.ajax({
    type: "POST",
    url: groupSearchEndpoint,
    data: dataString,
    success: function(res) {
      $("#groupsearchresponse").empty().hide();
      $("#groupsearchresponse").append(res).show();
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl("error", "There was an error executing your search");
    }
  });
}

function listGroups(parentID) {
  var dataString = 'id=' + parentID;
  $.ajax({
    type: "GET",
    url: groupListEndpoint,
    data: dataString,
    success: function(res) {
      $("#assignedgroups").empty().append(res).show();
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'Failed to list groups');
    }
  });
}

function grantGroup(parentID, groupID) {
  var dataString = 'id=' + parentID + '&groupID=' + groupID;
  $.ajax({
    type: "POST",
    url: groupGrantEndpoint,
    data: dataString,
    success: function(res) {
      listGroups(parentID);
      searchGroups(parentID);
      growl('success', 'Group was assigned');
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'Failed to assign group');
    }
  });
}

function removeGroup(parentID, groupID) {
  var dataString = 'id=' + parentID + '&groupID=' + groupID;
  $.ajax({
    type: "POST",
    url: groupRemoveEndpoint,
    data: dataString,
    success: function(res) {
      listGroups(parentID);
      growl('success', 'Group removed');
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', 'Failed to remove group');
    }
  });
}

// Members
function searchMembers(ownerID) {
  var dataString = "id=" + ownerID + "&q=" + $('#qmembers').val();
  $.ajax({
    type: "POST",
    url: memberSearchEndpoint,
    data: dataString,
    success: function(res) {
      $("#membersearchresponse").empty();
      $("#membersearchresponse").append(res).show();
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl("error", "There was an error executing your search");
    }
  });
}

function searchGroupMembers(ownerID) {
  var dataString = "id=" + ownerID + "&q=" + $('#qmembersgroup').val();
  $.ajax({
    type: "POST",
    url: memberGroupSearchEndpoint,
    data: dataString,
    success: function(res) {
      $("#membergroupsearchresponse").empty();
      $("#membergroupsearchresponse").append(res).show();
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl("error", "There was an error executing your search");
    }
  });
}

function addMember(ownerID, userID, username) {
  var dataString = 'id=' + ownerID + '&userID=' + userID;
  $.ajax({
    type: "POST",
    url: memberAddEndpoint,
    data: dataString,
    success: function(res) {
      growl("success", "Added the user " + username + " as a member");
      listMembers(ownerID);
      searchMembers(ownerID);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl("error", "Failed to add user as a member");
    }
  });
}

function removeMember(ownerID, userID, username) {
  var dataString = 'id=' + ownerID + '&userID=' + userID;
  $.ajax({
    type: "POST",
    url: memberRemoveEndpoint,
    data: dataString,
    success: function(res) {
      growl("success", "Removed membership of the user " + username);
      listMembers(ownerID);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl("error", "Failed to remove users membership");
    }
  });
}

function addGroupMember(ownerID, groupID, groupName) {
  var dataString = 'id=' + ownerID + '&groupID=' + groupID;
  $.ajax({
    type: "POST",
    url: memberAddGroupEndpoint,
    data: dataString,
    success: function(res) {
      growl("success", "Added the group " + groupName + " as a member");
      listMembers(ownerID);
      searchGroupMembers(ownerID);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl("error", "Failed to add group as a member");
    }
  });
}

function removeGroupMember(ownerID, groupID, groupName) {
  var dataString = 'id=' + ownerID + '&groupID=' + groupID;
  $.ajax({
    type: "POST",
    url: memberRemoveGroupEndpoint,
    data: dataString,
    success: function(res) {
      growl("success", "Removed membership of the group " + groupName);
      listMembers(ownerID);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl("error", "Failed to remove groups membership");
    }
  });
}

function listMembers(ownerID) {
  var dataString = 'id=' + ownerID;
  $.ajax({
    type: "GET",
    url: memberListEndpoint,
    data: dataString,
    success: function(res) {
      $("#currentmembers").empty().hide();
      $("#currentmembers").append(res).show();
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl("error", "Failed to list members");
    }
  });
}