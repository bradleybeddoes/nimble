window.nimble = window.nimble || {};
var nimble = window.nimble;

// all of the endpoints that may be referenced in this script
if(!nimble.endpoints)
nimble.endpoints = $H({
    'admin': { 'list':null, 'search':null, 'remove':null, 'grant':null },
    'user': { 'logins':null, 'enableAPI':null, 'disableAPI':null, 'enable':null, 'disable':null },
    'role': { 'list':null, 'search':null, 'remove':null, 'grant':null },
    'group': { 'list':null, 'search':null, 'remove':null, 'grant':null },
    'permission': { 'list':null, 'remove':null, 'create':null },
    'member': { 'list':null, 'search':null, 'remove':null, 'add':null, 'groupSearch':null, 'groupAdd':null, 'groupRemove':null }
});

// Admins
nimble.listAdministrators = function() {
    new Ajax.Request(nimble.endpoints.admin.list,{
        method: "POST",
        onSuccess: function(xhr) {
            $("admins").clear().insert( xhr.responseText);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.searchAdministrators = function() {
    var dataString = "q=" + $('q').getValue();
    new Ajax.Request(nimble.endpoints.admin.search,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            $("searchresponse").clear().insert(xhr.responseText);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.deleteAdministrator = function(userID) {
    var dataString = 'id=' + userID;
    new Ajax.Request(nimble.endpoints.admin.remove,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            nimble.growl('success', xhr.responseText);
            nimble.listAdministrators();
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.grantAdministrator = function(userID) {
    var dataString = 'id=' + userID;
    new Ajax.Request(nimble.endpoints.admin.grant,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            nimble.searchAdministrators();
            nimble.listAdministrators();
            nimble.growl('success', xhr.responseText);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

// Users
nimble.enableUser = function(id) {
    var dataString = "id=" + id;
    new Ajax.Request(nimble.endpoints.user.enable,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            $("enableuser").hide();
            $("enableduser").hide();
            $("disableuser").show();
            $("disableduser").show();
            nimble.growl('success', xhr.responseText);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.disableUser = function(id) {
    var dataString = "id=" + id;
    new Ajax.Request(nimble.endpoints.user.disable,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            $("disableuser").hide();
            $("disableduser").hide();
            $("enableuser").show();
            $("enableduser").show();
            nimble.growl('success', xhr.responseText);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.enableAPI = function(id) {
    var dataString = "id=" + id;
    new Ajax.Request(nimble.endpoints.user.enableAPI,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            $("disabledapi").hide();
            $("enabledapi").show();
            $("disableuserapi").show();
            $("enableuserapi").hide();
            nimble.growl('success', xhr.responseText);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.disableAPI = function(id) {
    var dataString = "id=" + id;
    new Ajax.Request(nimble.endpoints.user.disableAPI,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            $("disabledapi").show();
            $("enabledapi").hide();
            $("disableuserapi").hide();
            $("enableuserapi").show();
            nimble.growl('success', xhr.responseText);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.listLogins = function(userID) {
    var dataString = 'id=' + userID;
    new Ajax.Request(nimble.endpoints.user.logins,{
        method: "GET",
        parameters: dataString,
        onSuccess: function(xhr) {
            $("loginhistory").clear().insert(xhr.responseText).show();
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

// Permissions
nimble.listPermissions = function(ownerID) {
    var dataString = 'id=' + ownerID;
    new Ajax.Request(nimble.endpoints.permission.list,{
        method: "GET",
        parameters: dataString,
        onSuccess: function(xhr) {
            $("currentpermission").clear().insert(xhr.responseText).show();
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.createPermission = function(ownerID) {
    var dataString = 'id=' + ownerID + '&first=' + $('first_p').getValue() + '&second=' + $('second_p').getValue() + '&third=' + $('third_p').getValue() + '&fourth=' + $('fourth_p').getValue();
    new Ajax.Request(nimble.endpoints.permission.create,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            $("addpermissionserror").clear();
            $('first_p').setValue('');
            $('second_p').setValue('');
            $('third_p').setValue('');
            $('fourth_p').setValue('');
            nimble.listPermissions(ownerID);
            nimble.growl('success', xhr.responseText);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.removePermission = function(ownerID, permID) {
    var dataString = 'id=' + ownerID + '&permID=' + permID;
    new Ajax.Request(nimble.endpoints.permission.remove,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            nimble.listPermissions(ownerID);
            nimble.growl('success', xhr.responseText);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

// Roles
nimble.searchRoles = function(ownerID) {
    var dataString = "id=" + ownerID + "&q=" + $('qroles').getValue();
    new Ajax.Request(nimble.endpoints.role.search,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            $("rolesearchresponse").clear().hide().insert(xhr.responseText).show();
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.listRoles = function(ownerID) {
    var dataString = 'id=' + ownerID;
    new Ajax.Request(nimble.endpoints.role.list,{
        method: "GET",
        parameters: dataString,
        onSuccess: function(xhr) {
            $("assignedroles").clear().insert(xhr.responseText).show();
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.grantRole = function(ownerID, roleID) {
    var dataString = 'id=' + ownerID + '&roleID=' + roleID;
    new Ajax.Request(nimble.endpoints.role.grant,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            nimble.listRoles(ownerID);
            nimble.searchRoles(ownerID);
            nimble.growl('success', xhr.responseText);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.removeRole = function(ownerID, roleID) {
    var dataString = 'id=' + ownerID + '&roleID=' + roleID;
    new Ajax.Request(nimble.endpoints.role.remove,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            nimble.listRoles(ownerID);
            nimble.growl('success', xhr.responseText);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

// Groups
nimble.searchGroups = function(parentID) {
    var dataString = "id=" + parentID + "&q=" + $('qgroups').getValue();
    new Ajax.Request(nimble.endpoints.group.search,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            $("groupsearchresponse").clear().hide().insert(xhr.responseText).show();
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.listGroups = function(parentID) {
    var dataString = 'id=' + parentID;
    new Ajax.Request(nimble.endpoints.group.list,{
        method: "GET",
        parameters: dataString,
        onSuccess: function(xhr) {
            $("assignedgroups").clear().insert(xhr.responseText).show();
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.grantGroup = function(parentID, groupID) {
    var dataString = 'id=' + parentID + '&groupID=' + groupID;
    new Ajax.Request(nimble.endpoints.group.grant,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            nimble.listGroups(parentID);
            nimble.searchGroups(parentID);
            nimble.growl('success', xhr.responseText);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.removeGroup = function(parentID, groupID) {
    var dataString = 'id=' + parentID + '&groupID=' + groupID;
    new Ajax.Request(nimble.endpoints.group.remove,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            nimble.listGroups(parentID);
            nimble.growl('success', xhr.responseText);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

// Members
nimble.searchMembers = function(ownerID) {
    var dataString = "id=" + ownerID + "&q=" + $('qmembers').getValue();
    new Ajax.Request(nimble.endpoints.member.search,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            $("membersearchresponse").clear().insert(xhr.responseText).show();
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.searchGroupMembers = function(ownerID) {
    var dataString = "id=" + ownerID + "&q=" + $('qmembersgroup').getValue();
    new Ajax.Request(nimble.endpoints.member.groupSearch,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            $("membergroupsearchresponse").clear().insert(xhr.responseText).show();
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.addMember = function(ownerID, userID) {
    var dataString = 'id=' + ownerID + '&userID=' + userID;
    new Ajax.Request(nimble.endpoints.member.add,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            nimble.growl("success", xhr.responseText);
            nimble.listMembers(ownerID);
            nimble.searchMembers(ownerID);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.removeMember = function(ownerID, userID) {
    var dataString = 'id=' + ownerID + '&userID=' + userID;
    new Ajax.Request(nimble.endpoints.member.remove,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            nimble.growl("success", xhr.responseText);
            nimble.listMembers(ownerID);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.addGroupMember = function(ownerID, groupID) {
    var dataString = 'id=' + ownerID + '&groupID=' + groupID;
    new Ajax.Request(nimble.endpoints.member.groupAdd,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            nimble.growl("success", xhr.responseText);
            nimble.listMembers(ownerID);
            nimble.searchGroupMembers(ownerID);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.removeGroupMember = function(ownerID, groupID) {
    var dataString = 'id=' + ownerID + '&groupID=' + groupID;
    new Ajax.Request(nimble.endpoints.member.groupRemove,{
        method: "POST",
        parameters: dataString,
        onSuccess: function(xhr) {
            nimble.growl("success", xhr.responseText);
            nimble.listMembers(ownerID);
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

nimble.listMembers = function (ownerID) {
    var dataString = 'id=' + ownerID;
    new Ajax.Request(nimble.endpoints.member.list,{
        method: "GET",
        parameters: dataString,
        onSuccess: function(xhr) {
            $("currentmembers").clear().hide();
            $("currentmembers").insert(xhr.responseText).show();
        },
        onFailure: function (xhr) {
            if(xhr.transport.status == 403) return;
            nimble.growl('error', xhr.transport.responseText);
        }
    });
};

Ajax.Responders.register({
    onComplete: function(xhr) {
        if ((xhr.transport.status == 403) && (xhr.transport.getResponseHeader("X-Nim-Session-Invalid") != null)) {
            var title=$('sessionterminatedtitle').getValue();
            var msg=$('sessionterminatedmsg').getValue();
            var login=$('sessionterminatedlogin').getValue();
            var content=
              '<p id="sessionterminatedcontent">'+msg+'</p>'+
              '<div class="buttons">'+
              '   <a href="#" onClick="window.location.reload();return false;" id="sessionterminatedbtn" class="button icon icon_flag_blue">'+login+'</a>'+
              '</div>';
            new SimpleDialog({'id':'confirmationdialog','title':title,'content':content}).show();
        }
    }
});
