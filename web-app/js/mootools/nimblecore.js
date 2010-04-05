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
    new Request({
        method: "POST",
        url: nimble.endpoints.admin.list,
        onSuccess: function(res) {
            $("admins").empty();
            $("admins").set('html',res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

nimble.searchAdministrators = function() {
    var dataString = "q=" + $('q').get('value');
    new Request({
        method: "POST",
        url: nimble.endpoints.admin.search,
        data: dataString,
        onSuccess: function(res) {
            $("searchresponse").empty().set('html',res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

nimble.deleteAdministrator = function(userID) {
    var dataString = 'id=' + userID;
    new Request({
        method: "POST",
        url: nimble.endpoints.admin.remove,
        data: dataString,
        onSuccess: function(res) {
            nimble.growl('success', res);
            nimble.listAdministrators();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

nimble.grantAdministrator = function(userID) {
    var dataString = 'id=' + userID;
    new Request({
        method: "POST",
        url: nimble.endpoints.admin.grant,
        data: dataString,
        onSuccess: function(res) {
            nimble.searchAdministrators();
            nimble.listAdministrators();
            nimble.growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

// Users
nimble.enableUser = function(id) {
    var dataString = "id=" + id;
    new Request({
        method: "POST",
        url: nimble.endpoints.user.enable,
        data: dataString,
        onSuccess: function(res) {
            $("enableuser").hide();
            $("enableduser").hide();
            $("disableuser").show();
            $("disableduser").show();
            nimble.growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

nimble.disableUser = function(id) {
    var dataString = "id=" + id;
    new Request({
        method: "POST",
        url: nimble.endpoints.user.disable,
        data: dataString,
        onSuccess: function(res) {
            $("disableuser").hide();
            $("disableduser").hide();
            $("enableuser").show();
            $("enableduser").show();
            nimble.growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

nimble.enableAPI = function(id) {
    var dataString = "id=" + id;
    new Request({
        method: "POST",
        url: nimble.endpoints.user.enableAPI,
        data: dataString,
        onSuccess: function(res) {
            $("disabledapi").hide();
            $("enabledapi").show();
            $("disableuserapi").show();
            $("enableuserapi").hide();
            nimble.growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

nimble.disableAPI = function(id) {
    var dataString = "id=" + id;
    new Request({
        method: "POST",
        url: nimble.endpoints.user.disableAPI,
        data: dataString,
        onSuccess: function(res) {
            $("disabledapi").show();
            $("enabledapi").hide();
            $("disableuserapi").hide();
            $("enableuserapi").show();
            nimble.growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

nimble.listLogins = function(userID) {
    var dataString = 'id=' + userID;
    new Request({
        method: "GET",
        url: nimble.endpoints.user.logins,
        data: dataString,
        onSuccess: function(res) {
            $("loginhistory").empty().set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

// Permissions
nimble.listPermissions = function(ownerID) {
    var dataString = 'id=' + ownerID;
    new Request({
        method: "GET",
        url: nimble.endpoints.permission.list,
        data: dataString,
        onSuccess: function(res) {
            $("currentpermission").empty().set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

nimble.createPermission = function(ownerID) {
    var dataString = 'id=' + ownerID + '&first=' + $('first_p').get('value') + '&second=' + $('second_p').get('value') + '&third=' + $('third_p').get('value') + '&fourth=' + $('fourth_p').get('value');
    new Request({
        method: "POST",
        url:nimble.endpoints.permission.create,
        data: dataString,
        onSuccess: function(res) {
            $("addpermissionserror").empty();
            $('first_p').set('value','');
            $('second_p').set('value','');
            $('third_p').set('value','');
            $('fourth_p').set('value','');
            nimble.listPermissions(ownerID);
            nimble.growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            $("addpermissionserror").empty().set('html',xhr.responseText)
        }
    }).send();
};

nimble.removePermission = function(ownerID, permID) {
    var dataString = 'id=' + ownerID + '&permID=' + permID;
    new Request({
        method: "POST",
        url: nimble.endpoints.permission.remove,
        data: dataString,
        onSuccess: function(res) {
            nimble.listPermissions(ownerID);
            nimble.growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

// Roles
nimble.searchRoles = function(ownerID) {
    var dataString = "id=" + ownerID + "&q=" + $('qroles').get('value');
    new Request({
        method: "POST",
        url: nimble.endpoints.role.search,
        data: dataString,
        onSuccess: function(res) {
            $("rolesearchresponse").empty().hide().set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl("error", xhr.responseText);
        }
    }).send();
};

nimble.listRoles = function(ownerID) {
    var dataString = 'id=' + ownerID;
    new Request({
        method: "GET",
        url: nimble.endpoints.role.list,
        data: dataString,
        onSuccess: function(res) {
            $("assignedroles").empty().set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

nimble.grantRole = function(ownerID, roleID) {
    var dataString = 'id=' + ownerID + '&roleID=' + roleID;
    new Request({
        method: "POST",
        url: nimble.endpoints.role.grant,
        data: dataString,
        onSuccess: function(res) {
            nimble.listRoles(ownerID);
            nimble.searchRoles(ownerID);
            nimble.growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

nimble.removeRole = function(ownerID, roleID) {
    var dataString = 'id=' + ownerID + '&roleID=' + roleID;
    new Request({
        method: "POST",
        url: nimble.endpoints.role.remove,
        data: dataString,
        onSuccess: function(res) {
            nimble.listRoles(ownerID);
            nimble.growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

// Groups
nimble.searchGroups = function(parentID) {
    var dataString = "id=" + parentID + "&q=" + $('qgroups').get('value');
    new Request({
        method: "POST",
        url: nimble.endpoints.group.search,
        data: dataString,
        onSuccess: function(res) {
            $("groupsearchresponse").empty().hide().set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl("error", xhr.responseText);
        }
    }).send();
};

nimble.listGroups = function(parentID) {
    var dataString = 'id=' + parentID;
    new Request({
        method: "GET",
        url: nimble.endpoints.group.list,
        data: dataString,
        onSuccess: function(res) {
            $("assignedgroups").empty().set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

nimble.grantGroup = function(parentID, groupID) {
    var dataString = 'id=' + parentID + '&groupID=' + groupID;
    new Request({
        method: "POST",
        url: nimble.endpoints.group.grant,
        data: dataString,
        onSuccess: function(res) {
            nimble.listGroups(parentID);
            nimble.searchGroups(parentID);
            nimble.growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

nimble.removeGroup = function(parentID, groupID) {
    var dataString = 'id=' + parentID + '&groupID=' + groupID;
    new Request({
        method: "POST",
        url: nimble.endpoints.group.remove,
        data: dataString,
        onSuccess: function(res) {
            nimble.listGroups(parentID);
            nimble.growl('success', res);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl('error', xhr.responseText);
        }
    }).send();
};

// Members
nimble.searchMembers = function(ownerID) {
    var dataString = "id=" + ownerID + "&q=" + $('qmembers').get('value');
    new Request({
        method: "POST",
        url: nimble.endpoints.member.search,
        data: dataString,
        onSuccess: function(res) {
            $("membersearchresponse").empty().set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl("error", xhr.responseText);
        }
    }).send();
};

nimble.searchGroupMembers = function(ownerID) {
    var dataString = "id=" + ownerID + "&q=" + $('qmembersgroup').get('value');
    new Request({
        method: "POST",
        url: nimble.endpoints.member.groupSearch,
        data: dataString,
        onSuccess: function(res) {
            $("membergroupsearchresponse").empty();
            $("membergroupsearchresponse").set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl("error", xhr.responseText);
        }
    }).send();
};

nimble.addMember = function(ownerID, userID) {
    var dataString = 'id=' + ownerID + '&userID=' + userID;
    new Request({
        method: "POST",
        url: nimble.endpoints.member.add,
        data: dataString,
        onSuccess: function(res) {
            nimble.growl("success", res);
            nimble.listMembers(ownerID);
            nimble.searchMembers(ownerID);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl("error", xhr.responseText);
        }
    }).send();
};

nimble.removeMember = function(ownerID, userID) {
    var dataString = 'id=' + ownerID + '&userID=' + userID;
    new Request({
        method: "POST",
        url: nimble.endpoints.member.remove,
        data: dataString,
        onSuccess: function(res) {
            nimble.growl("success", res);
            nimble.listMembers(ownerID);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl("error", xhr.responseText);
        }
    }).send();
};

nimble.addGroupMember = function(ownerID, groupID) {
    var dataString = 'id=' + ownerID + '&groupID=' + groupID;
    new Request({
        method: "POST",
        url: nimble.endpoints.member.groupAdd,
        data: dataString,
        onSuccess: function(res) {
            nimble.growl("success", res);
            nimble.listMembers(ownerID);
            nimble.searchGroupMembers(ownerID);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl("error", xhr.responseText);
        }
    }).send();
};

nimble.removeGroupMember = function(ownerID, groupID) {
    var dataString = 'id=' + ownerID + '&groupID=' + groupID;
    new Request({
        method: "POST",
        url: nimble.endpoints.member.groupRemove,
        data: dataString,
        onSuccess: function(res) {
            nimble.growl("success", res);
            nimble.listMembers(ownerID);
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl("error", xhr.responseText);
        }
    }).send();
};

nimble.listMembers = function(ownerID) {
    var dataString = 'id=' + ownerID;
    new Request({
        method: "GET",
        url: nimble.endpoints.member.list,
        data: dataString,
        onSuccess: function(res) {
            $("currentmembers").empty().hide();
            $("currentmembers").set('html',res).show();
        },
        onFailure: function (xhr) {
            if (this.parent(xhr)) return;
            nimble.growl("error", xhr.responseText);
        }
    }).send();
};
