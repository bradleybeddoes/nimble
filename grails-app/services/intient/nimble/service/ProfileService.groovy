/*
 *  Nimble, an extensive application base for Grails
 *  Copyright (C) 2009 Intient Pty Ltd
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package intient.nimble.service

import intient.nimble.domain.Phone
import intient.nimble.domain.Profile

class ProfileService {

    public static String editPermission = "Profile:Edit:"

    boolean transactional = true

    /**
     * Sets a users current status.
     *
     * @pre User object and associated profile must be valid
     *
     * @param profile A populated user profile whose status should be set
     * @param status A populated status instance to assign to the profile
     *
     * @return The status object with errors if provided value was invalid.
     *
     * @throws RuntimeException When internal state requires transaction rollback
     */
    def updateStatus(def profile, def status) {
        log.debug("Adding new status with message $status.status to user [$profile.owner.id]$profile.owner.username")

        status.owner = profile
        if(!status.validate()) {
            log.warn("Invalid status supplied by user [$profile.owner.id]$profile.owner.username")
            return status
        }

        status.status = encodeStatus(status.status)
        profile.currentStatus = status;
        profile.addToStatuses(status)

        profile.save()
        if(profile.hasErrors()) {
            log.error("Unable to add new status with message $status.status to user [$profile.owner.id]$profile.owner.username")
            profile.errors.each {
                log.error it
            }

            throw new RuntimeException("Unable to add new status with message $status.status to user [$profile.owner.id]$profile.owner.username")
        }

        log.info("Added new status with message $status.status to user [$profile.owner.id]$profile.owner.username")
        return status
    }

    /**
     * Clears a users current status.
     *
     * @pre User object and associated profile must be valid
     *
     * @param profile A populated user profile whose status should be cleared
     *
     * @throws RuntimeException When internal state requires transaction rollback
     */
    def clearStatus(def profile) {
        log.debug("Clearing status for user [$profile.owner.id]$profile.owner.username")
        profile.currentStatus = null
        profile.save()

        if(profile.hasErrors()) {
            log.error("Unable to remove status for user [$profile.owner.id]$profile.owner.username")
            profile.errors.each {
                log.error it
            }

            throw new RuntimeException("Unable to remove status for user [$profile.owner.id]$profile.owner.username")
        }
        log.debug("Cleared status for user [$profile.owner.id]$profile.owner.username")
    }

    /**
     * Adds a new phone entry to a users profile.
     *
     * @param profile A populated user profile whose status should be set
     * @param phone A populated phone instance to assign to the profile
     *
     * @return The phone object with errors if provided value was invalid.
     *
     * @throws RuntimeException When internal state requires transaction rollback
     */
    def newPhone(def profile, def phone) {
        log.debug("Attempting to add new phone entry for user [$profile.owner.id]$profile.owner.username")

        phone.owner = profile
        if(!phone.validate()) {
            log.debug("New phone user [$profile.owner.id]$profile.owner.username details for are not valid")
            phone.errors.each {
                log.debug it
            }

            return phone 
        }
        profile.addToPhoneNumbers(phone)

        profile.save()
        if(profile.hasErrors()) {
            log.error("Unable to add new phone entry user [$profile.owner.id]$profile.owner.username")
            profile.errors.each {
                log.error it
            }

            throw new RuntimeException("Unable to add new phone entry user [$profile.owner.id]$profile.owner.username")
        }

        log.info("Added new phone entry with number $phone.number to user [$profile.owner.id]$profile.owner.username")
        return phone
    }

    /**
     * Deletes a phone entry from a users profile
     *
     * @param profile A populated user profile whose status should be set
     * @param phone A phone instance to remove from this profile
     *
     * @throws RuntimeException When internal state requires transaction rollback
     */
    def deletePhone(def profile, def phone) {
        log.debug("Attempting to delete phone entry $phone.id from user [$profile.owner.id]$profile.owner.username")

        profile.removeFromPhoneNumbers(phone)
        profile.save()
        if(profile.hasErrors()) {
            log.error("Unable to delete phone entry $phone.id from user [$profile.owner.id]$profile.owner.username")
            profile.errors.each {
                log.error it
            }

            throw new RuntimeException("Unable to delete phone entry $phone.id from user [$profile.owner.id]$profile.owner.username")
        }

        phone.delete()
        log.info("Deleted phone entry [$phone.id]$phone.number from user [$profile.owner.id]$profile.owner.username")

    }

    /**
     * Saves a user object after profile modifications.
     *
     * @pre Profile object must be valid and belong to a valid user
     *
     * @param profile A populated profile object to persist
     *
     * @throws RuntimeException When internal state requires transaction rollback
     */
    def updateProfile(Profile profile) {
        log.debug("Saving profile changes for user [$profile.owner.id]$profile.owner.username")
        profile.save()

        if(profile.hasErrors()) {
            log.error("Unable to save profile changes for user [$profile.owner.id]$profile.owner.username")
            profile.errors.each {
                log.error it
            }

            throw new RuntimeException("Unable to save profile changes for user [$profile.owner.id]$profile.owner.username")
        }
        log.debug("Saved profile changes for user [$profile.owner.id]$profile.owner.username")
    }

    /*
     * Ensures user status input it safely HTML encoded when stored in DB so users can't try and pull
     * any nasty stuff.
     *
     * Additionally where this function locates a valid URL in the user input it will create
     * a clickable html link.
     *
     * @param status Raw user inputted status string
     *
     * @return Safely encoded status string with addition of html links if appropriate
     */
    private def encodeStatus(def status) {
        def encodedStatus = status.encodeAsHTML()
        def pattern = /(?:(?:(http|https):\/\/)|(?:www\.))+(?:(?:[\w\._-]+\.[a-zA-Z]{2,6}))(?:\/[\w\&amp;%_\.\/-~-\+\#]*)?/
        def matcher = encodedStatus =~ pattern
        encodedStatus = encodedStatus.replaceAll( pattern,
            { Object[] it ->
                if(it[1] == null)
                return "<a href=\"http://${it[0]}\" alt=\"status link\">${it[0]}</a>"
                else
                return "<a href=\"${it[0]}\" alt=\"status link\">${it[0]}</a>"
            })
        return encodedStatus
    }
}
