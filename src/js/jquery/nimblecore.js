window.nimble = window.nimble || {};
var nimble = window.nimble;

// all of the endpoints that may be referenced in this script
if(!nimble.endpoints)
nimble.endpoints = {
    admin: { 'list':null, 'search':null, 'remove':null, 'grant':null },
    user: { 'logins':null, 'enableAPI':null, 'disableAPI':null, 'enable':null, 'disable':null },
    role: { 'list':null, 'search':null, 'remove':null, 'grant':null },
    group: { 'list':null, 'search':null, 'remove':null, 'grant':null },
    permission: { 'list':null, 'remove':null, 'create':null },
    member: { 'list':null, 'search':null, 'remove':null, 'add':null, 'groupSearch':null, 'groupAdd':null, 'groupRemove':null }
};

// Admins
nimble.listAdministrators = function() {
    $(function() {
      $.ajax({
        type: "POST",
        url: nimble.endpoints.admin.list,
        success: function(res) {
          $("#admins").empty();
          $("#admins").append(res);
        },
        error: function (xhr) {
          nimble.growl('error', xhr.responseText);
        }
      });
    });
};

nimble.searchAdministrators = function() {
  var dataString = "q=" + $('#q').val();
  $.ajax({
    type: "POST",
    url: nimble.endpoints.admin.search,
    data: dataString,
    success: function(res) {
      $("#searchresponse").empty();
      $("#searchresponse").append(res);
    },
    error: function (xhr) {
      nimble.growl('error', xhr.responseText);
    }
  });
};

nimble.deleteAdministrator = function(userID) {
  var dataString = 'id=' + userID;
  $.ajax({
    type: "POST",
    url: nimble.endpoints.admin.remove,
    data: dataString,
    success: function(res) {
      nimble.growl('success', res);
      nimble.listAdministrators();
    },
    error: function (xhr) {
      nimble.growl('error', xhr.responseText);
    }
  });
};

nimble.grantAdministrator = function(userID) {
  var dataString = 'id=' + userID;
  $.ajax({
    type: "POST",
    url: nimble.endpoints.admin.grant,
    data: dataString,
    success: function(res) {
      nimble.searchAdministrators();
      nimble.listAdministrators();
	  nimble.growl('success', res);
    },
    error: function (xhr) {
      nimble.growl('error', xhr.responseText);
    }
  });
};

// Users
nimble.enableUser = function(id) {
  var dataString = "id="+id;
  $.ajax({
    type: "POST",
    url: nimble.endpoints.user.enable,
    data: dataString,
    success: function(res) {
      $("#enableuser").hide();
      $("#enableduser").hide();
      $("#disableuser").show();
      $("#disableduser").show();
      nimble.growl('success', res);

    },
    error: function (xhr) {
      nimble.growl('error', xhr.responseText);
    }
  });
};

nimble.disableUser = function(id) {
  var dataString = "id="+id;
  $.ajax({
    type: "POST",
    url: nimble.endpoints.user.disable,
    data: dataString,
    success: function(res) {
      $("#disableuser").hide();
      $("#disableduser").hide();
      $("#enableuser").show();
      $("#enableduser").show();
      nimble.growl('success', res);

    },
    error: function (xhr) {
      nimble.growl('error', xhr.responseText);
    }
  });
};

nimble.enableAPI = function(id) {
  var dataString = "id="+id;
  $.ajax({
    type: "POST",
    url: nimble.endpoints.user.enableAPI,
    data: dataString,
    success: function(res) {
      $("#disabledapi").hide();
      $("#enabledapi").show();

      $("#disableuserapi").show();
      $("#enableuserapi").hide();
      nimble.growl('success', res);

    },
    error: function (xhr) {
      nimble.growl('error', xhr.responseText);
    }
  });
};

nimble.disableAPI = function(id) {
  var dataString = "id="+id;
  $.ajax({
    type: "POST",
    url: nimble.endpoints.user.disableAPI,
    data: dataString,
    success: function(res) {
      $("#disabledapi").show();
      $("#enabledapi").hide();

      $("#disableuserapi").hide();
      $("#enableuserapi").show();
      nimble.growl('success', res);

    },
    error: function (xhr) {
      nimble.growl('error', xhr.responseText);
    }
  });
};

nimble.listLogins = function(userID) {
  var dataString = 'id=' + userID;
  $.ajax({
    type: "GET",
    url: nimble.endpoints.user.logins,
    data: dataString,
    success: function(res) {
      $("#loginhistory").empty().append(res).show();
    },
    error: function (xhr) {
      nimble.growl('error', xhr.responseText);
    }
  });
};

// Permissions
nimble.listPermissions = function(ownerID) {
  var dataString = 'id=' + ownerID;
  $.ajax({
    type: "GET",
    url: nimble.endpoints.permission.list,
    data: dataString,
    success: function(res) {
      $("#currentpermission").empty().append(res).show();
    },
    error: function (xhr) {
      nimble.growl('error', xhr.responseText);
    }
  });
};

nimble.createPermission = function(ownerID) {
  var dataString = 'id=' + ownerID + '&first=' + $('#first_p').val() + '&second=' + $('#second_p').val() + '&third=' + $('#third_p').val() + '&fourth=' + $('#fourth_p').val();
  $.ajax({
    type: "POST",
    url: nimble.endpoints.permission.create,
    data: dataString,
    success: function(res) {
      $("#addpermissionserror").empty();
	  $('#first_p').val('');
	  $('#second_p').val('');
	  $('#third_p').val('');
	  $('#fourth_p').val('');
      nimble.listPermissions(ownerID);
      nimble.growl('success', res);
    },
    error: function (xhr) {
      $("#addpermissionserror").empty().append(xhr.responseText)
    }
  });
};

nimble.removePermission = function(ownerID, permID) {
  var dataString = 'id=' + ownerID + '&permID=' + permID;
  $.ajax({
    type: "POST",
    url: nimble.endpoints.permission.remove,
    data: dataString,
    success: function(res) {
      nimble.listPermissions(ownerID);
      nimble.growl('success', res);
    },
    error: function (xhr) {
      nimble.growl('error', xhr.responseText);
    }
  });
};

// Roles
nimble.searchRoles = function(ownerID) {
  var dataString = "id=" + ownerID + "&q=" + $('#qroles').val();
  $.ajax({
    type: "POST",
    url: nimble.endpoints.role.search,
    data: dataString,
    success: function(res) {
      $("#rolesearchresponse").empty().hide();
      $("#rolesearchresponse").append(res).show();
    },
    error: function (xhr) {
      nimble.growl("error", xhr.responseText);
    }
  });
};

nimble.listRoles = function(ownerID) {
  var dataString = 'id=' + ownerID;
  $.ajax({
    type: "GET",
    url: nimble.endpoints.role.list,
    data: dataString,
    success: function(res) {
      $("#assignedroles").empty().append(res).show();
    },
    error: function (xhr) {
      nimble.growl('error', xhr.responseText);
    }
  });
};

nimble.grantRole = function(ownerID, roleID) {
  var dataString = 'id=' + ownerID + '&roleID=' + roleID;
  $.ajax({
    type: "POST",
    url: nimble.endpoints.role.grant,
    data: dataString,
    success: function(res) {
      nimble.listRoles(ownerID);
      nimble.searchRoles(ownerID);
      nimble.growl('success', res);
    },
    error: function (xhr) {
      nimble.growl('error', xhr.responseText);
    }
  });
};

nimble.removeRole = function(ownerID, roleID) {
  var dataString = 'id=' + ownerID + '&roleID=' + roleID;
  $.ajax({
    type: "POST",
    url: nimble.endpoints.role.remove,
    data: dataString,
    success: function(res) {
      nimble.listRoles(ownerID);
      nimble.growl('success', res);
    },
    error: function (xhr) {
      nimble.growl('error', xhr.responseText);
    }
  });
};

// Groups
nimble.searchGroups = function(parentID) {
  var dataString = "id=" + parentID + "&q=" + $('#qgroups').val();
  $.ajax({
    type: "POST",
    url: nimble.endpoints.group.search,
    data: dataString,
    success: function(res) {
      $("#groupsearchresponse").empty().hide();
      $("#groupsearchresponse").append(res).show();
    },
    error: function (xhr) {
      nimble.growl("error", xhr.responseText);
    }
  });
};

nimble.listGroups = function(parentID) {
  var dataString = 'id=' + parentID;
  $.ajax({
    type: "GET",
    url: nimble.endpoints.group.list,
    data: dataString,
    success: function(res) {
      $("#assignedgroups").empty().append(res).show();
    },
    error: function (xhr) {
      nimble.growl('error', xhr.responseText);
    }
  });
};

nimble.grantGroup = function(parentID, groupID) {
  var dataString = 'id=' + parentID + '&groupID=' + groupID;
  $.ajax({
    type: "POST",
    url: nimble.endpoints.group.grant,
    data: dataString,
    success: function(res) {
      nimble.listGroups(parentID);
      nimble.searchGroups(parentID);
      nimble.growl('success', res);
    },
    error: function (xhr) {
      nimble.growl('error', xhr.responseText);
    }
  });
};

nimble.removeGroup = function(parentID, groupID) {
  var dataString = 'id=' + parentID + '&groupID=' + groupID;
  $.ajax({
    type: "POST",
    url: nimble.endpoints.group.remove,
    data: dataString,
    success: function(res) {
      nimble.listGroups(parentID);
      nimble.growl('success', res);
    },
    error: function (xhr) {
      nimble.growl('error', xhr.responseText);
    }
  });
};

// Members
nimble.searchMembers = function(ownerID) {
  var dataString = "id=" + ownerID + "&q=" + $('#qmembers').val();
  $.ajax({
    type: "POST",
    url: nimble.endpoints.member.search,
    data: dataString,
    success: function(res) {
      $("#membersearchresponse").empty();
      $("#membersearchresponse").append(res).show();
    },
    error: function (xhr) {
      nimble.growl("error", xhr.responseText);
    }
  });
};

nimble.searchGroupMembers = function(ownerID) {
  var dataString = "id=" + ownerID + "&q=" + $('#qmembersgroup').val();
  $.ajax({
    type: "POST",
    url: nimble.endpoints.member.groupSearch,
    data: dataString,
    success: function(res) {
      $("#membergroupsearchresponse").empty();
      $("#membergroupsearchresponse").append(res).show();
    },
    error: function (xhr) {
      nimble.growl("error", xhr.responseText);
    }
  });
};

nimble.addMember = function(ownerID, userID) {
  var dataString = 'id=' + ownerID + '&userID=' + userID;
  $.ajax({
    type: "POST",
    url: nimble.endpoints.member.add,
    data: dataString,
    success: function(res) {
      nimble.growl("success", res);
      nimble.listMembers(ownerID);
      nimble.searchMembers(ownerID);
    },
    error: function (xhr) {
      nimble.growl("error", xhr.responseText);
    }
  });
};

nimble.removeMember = function(ownerID, userID) {
  var dataString = 'id=' + ownerID + '&userID=' + userID;
  $.ajax({
    type: "POST",
    url: nimble.endpoints.member.remove,
    data: dataString,
    success: function(res) {
      nimble.growl("success", res);
      nimble.listMembers(ownerID);
    },
    error: function (xhr) {
      nimble.growl("error", xhr.responseText);
    }
  });
};

nimble.addGroupMember = function(ownerID, groupID) {
  var dataString = 'id=' + ownerID + '&groupID=' + groupID;
  $.ajax({
    type: "POST",
    url: nimble.endpoints.member.groupAdd,
    data: dataString,
    success: function(res) {
      nimble.growl("success", res);
      nimble.listMembers(ownerID);
      nimble.searchGroupMembers(ownerID);
    },
    error: function (xhr) {
      nimble.growl("error", xhr.responseText);
    }
  });
};

nimble.removeGroupMember = function(ownerID, groupID) {
  var dataString = 'id=' + ownerID + '&groupID=' + groupID;
  $.ajax({
    type: "POST",
    url: nimble.endpoints.member.groupRemove,
    data: dataString,
    success: function(res) {
      nimble.growl("success", res);
      nimble.listMembers(ownerID);
    },
    error: function (xhr) {
      nimble.growl("error", xhr.responseText);
    }
  });
};

nimble.listMembers = function(ownerID) {
  var dataString = 'id=' + ownerID;
  $.ajax({
    type: "GET",
    url: nimble.endpoints.member.list,
    data: dataString,
    success: function(res) {
      $("#currentmembers").empty().hide();
      $("#currentmembers").append(res).show();
    },
    error: function (xhr) {
      nimble.growl("error", xhr.responseText);
    }
  });
};