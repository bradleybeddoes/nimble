// Admins
function listAdministrators() {
    $(function() {
      $.ajax({
        type: "POST",
        url: adminListEndpoint,
        success: function(res) {
          $("#admins").empty();
          $("#admins").append(res);
        },
        error: function (xhr, ajaxOptions, thrownError) {
          growl('error', xhr.responseText);
        }
      });
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
      growl('error', xhr.responseText);
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
      growl('success', res);
      listAdministrators();
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', xhr.responseText);
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
      searchAdministrators();
      listAdministrators();
	  growl('success', res);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', xhr.responseText);
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
      growl('success', res);

    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', xhr.responseText);
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
      growl('success', res);

    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', xhr.responseText);
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
      growl('success', res);

    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', xhr.responseText);
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
      growl('success', res);

    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', xhr.responseText);
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
      growl('error', xhr.responseText);
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
      growl('error', xhr.responseText);
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
      growl("error", xhr.responseText);
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
      growl('error', xhr.responseText);
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
      growl('success', res);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', xhr.responseText);
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
      growl('success', res);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', xhr.responseText);
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
      growl("error", xhr.responseText);
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
      growl('error', xhr.responseText);
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
      growl('success', res);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', xhr.responseText);
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
      growl('success', res);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl('error', xhr.responseText);
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
      growl("error", xhr.responseText);
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
      growl("error", xhr.responseText);
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
      growl("success", res);
      listMembers(ownerID);
      searchMembers(ownerID);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl("error", xhr.responseText);
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
      growl("success", res);
      listMembers(ownerID);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl("error", xhr.responseText);
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
      growl("success", res);
      listMembers(ownerID);
      searchGroupMembers(ownerID);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl("error", xhr.responseText);
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
      growl("success", res);
      listMembers(ownerID);
    },
    error: function (xhr, ajaxOptions, thrownError) {
      growl("error", xhr.responseText);
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
      growl("error", xhr.responseText);
    }
  });
}