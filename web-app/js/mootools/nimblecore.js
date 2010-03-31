// Admins
function listAdministrators() {
    new Request({
        method: "POST",
        url: adminListEndpoint,
        onSuccess: function(res) {
            $("admins").empty();
            $("admins").set('html',res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

function searchAdministrators() {
    var dataString = "q=" + $('q').get('value');
    new Request({
        method: "POST",
        url: adminSearchEndpoint,
        data: dataString,
        onSuccess: function(res) {
            $("searchresponse").empty().set('html',res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

function deleteAdministrator(userID, username) {
    var dataString = 'id=' + userID;
    new Request({
        method: "POST",
        url: adminListEndpoint,
        data: dataString,
        onSuccess: function(res) {
            growl('success', res);
            listAdministrators();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

function grantAdministrator(userID, username) {
    var dataString = 'id=' + userID;
    new Request({
        method: "POST",
        url: adminGrantEndpoint,
        data: dataString,
        onSuccess: function(res) {
            searchAdministrators();
            listAdministrators();
            growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

// Users
function enableUser(id) {
    var dataString = "id=" + id;
    new Request({
        method: "POST",
        url: enableUserEndpoint,
        data: dataString,
        onSuccess: function(res) {
            $("enableuser").hide();
            $("enableduser").hide();
            $("disableuser").show();
            $("disableduser").show();
            growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

function disableUser(id) {
    var dataString = "id=" + id;
    new Request({
        method: "POST",
        url: disableUserEndpoint,
        data: dataString,
        onSuccess: function(res) {
            $("disableuser").hide();
            $("disableduser").hide();
            $("enableuser").show();
            $("enableduser").show();
            growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

function enableAPI(id) {
    var dataString = "id=" + id;
    new Request({
        method: "POST",
        url: enableAPIEndpoint,
        data: dataString,
        onSuccess: function(res) {
            $("disabledapi").hide();
            $("enabledapi").show();
            $("disableuserapi").show();
            $("enableuserapi").hide();
            growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

function disableAPI(id) {
    var dataString = "id=" + id;
    new Request({
        method: "POST",
        url: disableAPIEndpoint,
        data: dataString,
        onSuccess: function(res) {
            $("disabledapi").show();
            $("enabledapi").hide();
            $("disableuserapi").hide();
            $("enableuserapi").show();
            growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

function listLogins(userID) {
    var dataString = 'id=' + userID;
    new Request({
        method: "GET",
        url: userLoginsEndpoint,
        data: dataString,
        onSuccess: function(res) {
            $("loginhistory").empty().set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

// Permissions
function listPermissions(ownerID) {
    var dataString = 'id=' + ownerID;
    new Request({
        method: "GET",
        url: permissionListEndpoint,
        data: dataString,
        onSuccess: function(res) {
            $("currentpermission").empty().set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

function createPermission(ownerID) {
    var dataString = 'id=' + ownerID + '&first=' + $('first_p').get('value') + '&second=' + $('second_p').get('value') + '&third=' + $('third_p').get('value') + '&fourth=' + $('fourth_p').get('value');
    new Request({
        method: "POST",
        url: permissionCreateEndpoint,
        data: dataString,
        onSuccess: function(res) {
            $("addpermissionserror").empty();
            $('first_p').val('');
            $('second_p').val('');
            $('third_p').val('');
            $('fourth_p').val('');
            listPermissions(ownerID);
            growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            $("addpermissionserror").empty().set('html',xhr.responseText)
        }
    }).send();
}

function removePermission(ownerID, permID) {
    var dataString = 'id=' + ownerID + '&permID=' + permID;
    new Request({
        method: "POST",
        url: permissionRemoveEndpoint,
        data: dataString,
        onSuccess: function(res) {
            listPermissions(ownerID);
            growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

// Roles
function searchRoles(ownerID) {
    var dataString = "id=" + ownerID + "&q=" + $('qroles').get('value');
    new Request({
        method: "POST",
        url: roleSearchEndpoint,
        data: dataString,
        onSuccess: function(res) {
            $("rolesearchresponse").empty().hide().set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl("error", xhr.responseText);
        }
    }).send();
}

function listRoles(ownerID) {
    var dataString = 'id=' + ownerID;
    new Request({
        method: "GET",
        url: roleListEndpoint,
        data: dataString,
        onSuccess: function(res) {
            $("assignedroles").empty().set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

function grantRole(ownerID, roleID) {
    var dataString = 'id=' + ownerID + '&roleID=' + roleID;
    new Request({
        method: "POST",
        url: roleGrantEndpoint,
        data: dataString,
        onSuccess: function(res) {
            listRoles(ownerID);
            searchRoles(ownerID);
            growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

function removeRole(ownerID, roleID) {
    var dataString = 'id=' + ownerID + '&roleID=' + roleID;
    new Request({
        method: "POST",
        url: roleRemoveEndpoint,
        data: dataString,
        onSuccess: function(res) {
            listRoles(ownerID);
            growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

// Groups
function searchGroups(parentID) {
    var dataString = "id=" + parentID + "&q=" + $('qgroups').get('value');
    new Request({
        method: "POST",
        url: groupSearchEndpoint,
        data: dataString,
        onSuccess: function(res) {
            $("groupsearchresponse").empty().hide().set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl("error", xhr.responseText);
        }
    }).send();
}

function listGroups(parentID) {
    var dataString = 'id=' + parentID;
    new Request({
        method: "GET",
        url: groupListEndpoint,
        data: dataString,
        onSuccess: function(res) {
            $("assignedgroups").empty().set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

function grantGroup(parentID, groupID) {
    var dataString = 'id=' + parentID + '&groupID=' + groupID;
    new Request({
        method: "POST",
        url: groupGrantEndpoint,
        data: dataString,
        onSuccess: function(res) {
            listGroups(parentID);
            searchGroups(parentID);
            growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

function removeGroup(parentID, groupID) {
    var dataString = 'id=' + parentID + '&groupID=' + groupID;
    new Request({
        method: "POST",
        url: groupRemoveEndpoint,
        data: dataString,
        onSuccess: function(res) {
            listGroups(parentID);
            growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl('error', xhr.responseText);
        }
    }).send();
}

// Members
function searchMembers(ownerID) {
    var dataString = "id=" + ownerID + "&q=" + $('qmembers').get('value');
    new Request({
        method: "POST",
        url: memberSearchEndpoint,
        data: dataString,
        onSuccess: function(res) {
            $("membersearchresponse").empty().set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl("error", xhr.responseText);
        }
    }).send();
}

function searchGroupMembers(ownerID) {
    var dataString = "id=" + ownerID + "&q=" + $('qmembersgroup').get('value');
    new Request({
        method: "POST",
        url: memberGroupSearchEndpoint,
        data: dataString,
        onSuccess: function(res) {
            $("membergroupsearchresponse").empty();
            $("membergroupsearchresponse").set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl("error", xhr.responseText);
        }
    }).send();
}

function addMember(ownerID, userID, username) {
    var dataString = 'id=' + ownerID + '&userID=' + userID;
    new Request({
        method: "POST",
        url: memberAddEndpoint,
        data: dataString,
        onSuccess: function(res) {
            growl("success", res);
            listMembers(ownerID);
            searchMembers(ownerID);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl("error", xhr.responseText);
        }
    }).send();
}

function removeMember(ownerID, userID, username) {
    var dataString = 'id=' + ownerID + '&userID=' + userID;
    new Request({
        method: "POST",
        url: memberRemoveEndpoint,
        data: dataString,
        onSuccess: function(res) {
            growl("success", res);
            listMembers(ownerID);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl("error", xhr.responseText);
        }
    }).send();
}

function addGroupMember(ownerID, groupID, groupName) {
    var dataString = 'id=' + ownerID + '&groupID=' + groupID;
    new Request({
        method: "POST",
        url: memberAddGroupEndpoint,
        data: dataString,
        onSuccess: function(res) {
            growl("success", res);
            listMembers(ownerID);
            searchGroupMembers(ownerID);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl("error", xhr.responseText);
        }
    }).send();
}

function removeGroupMember(ownerID, groupID, groupName) {
    var dataString = 'id=' + ownerID + '&groupID=' + groupID;
    new Request({
        method: "POST",
        url: memberRemoveGroupEndpoint,
        data: dataString,
        onSuccess: function(res) {
            growl("success", res);
            listMembers(ownerID);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl("error", xhr.responseText);
        }
    }).send();
}

function listMembers(ownerID) {
    var dataString = 'id=' + ownerID;
    new Request({
        method: "GET",
        url: memberListEndpoint,
        data: dataString,
        onSuccess: function(res) {
            $("currentmembers").empty().hide();
            $("currentmembers").set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            growl("error", xhr.responseText);
        }
    }).send();
}