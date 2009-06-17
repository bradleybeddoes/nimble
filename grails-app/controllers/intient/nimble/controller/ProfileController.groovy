/*
 *  Nimble, an extensive application base for Grails
 *  Copyright (C) 2009 Intient Pty Ltd
 *
 *  Open Source Use - GNU Affero General Public License, version 3
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Commercial/Private Use
 *
 *  You may purchase a commercial version of this software which
 *  frees you from all restrictions of the AGPL by visiting
 *  http://intient.com/products/nimble/licenses
 *
 *  If you have purchased a commercial version of this software it is licensed
 *  to you under the terms of your agreement made with Intient Pty Ltd.
 */
package intient.nimble.controller

import org.apache.ki.SecurityUtils

import intient.nimble.domain.User
import intient.nimble.domain.Profile
import intient.nimble.domain.Status
import intient.nimble.domain.Phone

import intient.nimble.service.ProfileService


class ProfileController {

    def profileService
    def userService

    def index = { redirect(action: 'show', params: params) }

    def show = {
        def user

        if(params.id == null) {
            log.debug("Didn't locate id in profile request, attempting to load profile of logged in user")
            user = User.get(SecurityUtils.getSubject()?.getPrincipal())     
        }
        else{
            log.debug("Attempting to load profile for user id $params.id")
            user = User.get(params.id)
        }

        if (!user) {
            log.warn("User was not located when attempting to show profile")
            response.sendError(500)
            return
        }

        log.debug("Showing profile for user [$user.id]$user.username")
        return [user: user, profile: user.profile]
    }

    def miniprofile = {
        def user

        if(params.id == null) {
            log.debug("Didn't locate id in profile request, attempting to load profile of logged in user")
            user = User.get(SecurityUtils.getSubject()?.getPrincipal())
        }
        else{
            log.debug("Attempting to load profile for user id $params.id")
            user = User.get(params.id)
        }

        if (!user) {
            log.warn("User was not located when attempting to show profile")
            response.sendError(500)
            return
        }

        log.debug("Showing profile for user [$user.id]$user.username")
        return [user: user, profile: user.profile]
    }

    /*
     * TODO: Complete social account integration work, disabled for initial release
     * 
    def editsocial = {
    def user = modificationPermitted(params.id)
    if(!user)
    return

    return [user:user]
    }
     */

    def editaccount = {
        def user = modificationPermitted(params.id)
        if(!user)
        return
        
        return [user:user]
    }

    def updateaccount = {
        def user = modificationPermitted(params.id)
        if(!user)
        return

        

        if(params.storeDOB) {
            log.info("Storing dob for user [$user.id]$user.username")
            user.profile.properties['fullName', 'nickName', 'bio', 'gender', 'dob'] = params
        }
        else {
            log.info("Not storing dob for user [$user.id]$user.username")
            user.profile.properties['fullName', 'nickName', 'bio', 'gender'] = params
            user.profile.dob = null
        }

        if(user.profile.validate()) {
            profileService.updateProfile(user.profile)
            log.info("Successfully updated common profile details for user [$user.id]$user.username")

            flash.type = "success"
            flash.message = "Profile account details updated"
            redirect (controller: 'profile', action: 'show', id: user.id)
            return
        }

        log.debug("Updated common profile details for user [$user.id]$user.username are invalid")
        user.errors.each {
            log.debug it
        }

        user.discard()
        render view: 'editcommon', model: [user: user]
        return
    }

    def displayphoto = {
        def user = User.get(params.id)

        if(!user){
            log.warn("User was not located when attempting to show profile photo for $params.id")
            response.sendError(500)
            return
        }

        if(user.profile.photo == null) {
            log.warn("User [$user.id]$user.username does not have a profile photo")
            response.sendError(404)
            return
        }

        response.setContentType(user.profile.photoType)
        response.setContentLength(user.profile.photo.size())
        OutputStream out = response.getOutputStream();
        out.write(user.profile.photo);
        out.close();
    }

    def editphoto = {
        def user = modificationPermitted(params.id)
        if(!user)
        return

        return [user:user]
    }

    def uploadphoto = {
        def user = modificationPermitted(params.id)
        if(!user)
        return

        def photo = request.getFile('photo')
        def validMimeTypes= ['image/png', 'image/jpeg', 'image/gif']
        if (! validMimeTypes.contains(photo.contentType)) {
            log.info("User [$user.id]$user.username is attempting to upload a photo with invalid mime type ($photo.contentType)")
            user.profile.errors.rejectValue('photo', 'photo.format.invalid', 'The format of the supplied photo is invalid')
            render(view:'editphoto', model:[user:user])
            return
        }

        user.profile.photo = photo.getBytes()
        user.profile.photoType = photo.getContentType()
        user.profile.gravatar = false
        if(user.profile.validate()) {     
            profileService.updateProfile(user.profile)
            log.debug("Profile of user [$user.id]$user.username was updated with a new photo")

            flash.type = "success"
            flash.message = "Profile updated with new photo"

            redirect action: 'editphoto', id: user.id
            return
        }

        log.debug("Profile of user [$user.id]$user.username was invalid when trying to commit photo change")
        render(view:'editphoto', model:[user:user])
    }

    def enablegravatar = {
        def user = modificationPermitted(params.id)
        if(!user)
        return

        user.profile.gravatar = true
        profileService.updateProfile(user.profile)

        log.debug("User [$user.id]$user.username enabled Gravatar for their profile")
        render(template: "/templates/profile/photo", contextPath: pluginContextPath, model: [profile: user.profile, size:180])
        return
    }

    def disablegravatar = {
        def user = modificationPermitted(params.id)
        if(!user)
        return

        user.profile.gravatar = false
        profileService.updateProfile(user.profile)

        log.debug("User [$user.id]$user.username disabled Gravatar on their profile")
        render(template: "/templates/profile/photo", contextPath: pluginContextPath, model: [profile: user.profile, size:180])
        return
    }

    def editcontact = {
        def user = modificationPermitted(params.id)
        if(!user)
        return

        return [user:user]
    }

    def newphone = {
        def user = modificationPermitted(params.id)
        if(!user)
        return

        def phone = new Phone(number:params.number, type:params.type)
        def processedPhone = profileService.newPhone(user.profile, phone)
        if(processedPhone.hasErrors()) {
            log.debug("New phone record for user [$user.id]$user.username is invalid")
            processedPhone.errors.each {
                log.debug it
            }
            
            render template:"/templates/errors", contextPath: pluginContextPath, model: [bean:processedPhone]
            response.setStatus(500)
            return
        }

        render (template: "/templates/profile/phone_edit_list", contextPath: pluginContextPath, model: [user:user])
        return
    }

    def deletephone = {
        def user = modificationPermitted(params.id)
        if(!user)
        return

        def phone = Phone.get(params.phoneID)
        if (!phone) {
            log.warn("Phone record was not located when attempting to delete phone record for user [$user.id]$user.username")
            response.sendError(500)
            return null
        }

        profileService.deletePhone(user.profile, phone)
        render (template: "/templates/profile/phone_edit_list", contextPath: pluginContextPath, model: [user:user])
        return
    }

    def clearstatus = {
        def user = modificationPermitted(params.id)
        if(!user)
        return
        
        profileService.clearStatus(user.profile)
        render "cleared"
        return
    }

    def updateemail = {
        def user = modificationPermitted(params.id)
        if(!user)
        return

        def existingUserProfile = Profile.findByEmail(params.newemail)
        if(existingUserProfile) {
            log.info("Attempt by user [$user.id]$user.username to utilize email $params.newemail but this is in use by user [$existingUserProfile.owner.id]$existingUserProfile.owner.username")
            user.profile.errors.rejectValue('email', 'user.profile.email.duplicate', 'Email address is already assocated with another account')
        }

        user.profile.nonVerifiedEmail = ''
        user.profile.nonVerifiedEmail = params.newemail // this input is invalid say 'hello'
        
        // TODO - We need to figure out why validating profile will give a correct set of
        // error messages here but validating user will not. Cascade issue?        
        if(existingUserProfile == null && user.profile.validate()) {
            profileService.updateProfile(user.profile)
            userService.generateValidationHash(user)
            user.save()
            
            log.info("Sending address change email to $user.profile.nonVerifiedEmail with subject $grailsApplication.config.nimble.messaging.changeemail.subject")

            sendMail {
                to user.profile.nonVerifiedEmail
                subject grailsApplication.config.nimble.messaging.changeemail.subject
                html g.render(template: "/templates/nimble/mail/emailchange_email", model: [user: user])
            }

            render template:"/templates/nimble/profile/emailupdated"
            return
        }

        render template:"/templates/errors", contextPath: pluginContextPath, model: [bean:user.profile]
        response.setStatus(500)
        return
    }

    def validateemail = {
        def user = modificationPermitted(params.id)
        if(!user)
        return

        if (user.actionHash != null && user.actionHash.equals(params.activation)) {
            user.actionHash = null
            user.profile.email = user.profile.nonVerifiedEmail
            user.profile.nonVerifiedEmail = null
            profileService.updateProfile(user.profile)

            // Reset the hash
            userService.generateValidationHash(user)
            user.save()

            if (user.hasErrors()) {
                log.warn("Unable to update email for user [$user.id]$user.username after successful action hash validation")
                user.errors.each {
                    log.debug(it)
                }

                flash.type = "error"
                flash.message = "Error when attempting to validate new email address please try again later"
                redirect action: 'show', id: user.id
                return
            }

            log.info("Successfully validated new email address for user [$user.id]$user.username")

            flash.type = "success"
            flash.message = "Your new email address has been confirmed"
            redirect action: 'show', id: user.id
            return
        }

        log.warn("Attempt to validate an email address for user [$user.id]$user.username but hash is invalid")
        flash.type = "error"
        flash.message = "Error when attempting to validate new email address please try again later"
        redirect action: 'show', id: user.id
        return
    }
    
    def updatestatus = {
        def user = modificationPermitted(params.id)
        if(!user)
        return

        def newStatus = new Status()
        newStatus.status = params.newstatus

        def processedStatus = profileService.updateStatus(user.profile, newStatus)
        if(processedStatus.hasErrors()) {
            processedStatus.errors.each {
                log.debug it
            }
            response.sendError(500)
            return
        }
        render (template: "/templates/nimble/profile/currentstatus", model: [profile: user.profile, clear: true])
        return
    }

    private User modificationPermitted(def id) {
        def authUser = User.get(SecurityUtils.getSubject()?.getPrincipal())
        if (!authUser) {
            log.error("Authenticated user was not able to be obtained when performing profile action")
            response.sendError(403)
            return null
        }

        def user = User.get(id)
        if (!user) {
            log.warn("User was not located when attempting to edit profile")
            response.sendError(403)
            return null
        }

        if(SecurityUtils.getSubject().isPermitted(ProfileService.editPermission + user.id)) {
            return user
        }
        else {
            log.warn("Security model denied attempt by user [$authUser.id]$authUser.username to modify profile of user [$user.id]$user.username")
            response.sendError(403)
            return
        }
    }
}
