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

package intient.nimble.domain

import intient.nimble.domain.Permission

/**
 * Represents a WildcardPermission in the data repository.
 * Each level of a wildcard permission (upto 6) is able to be represented by
 * this object. Support for auto creation of targets and populating of levels from UI
 *
 * @author Bradley Beddoes
 */
class LevelPermission extends Permission {

  private final String tokenSep = ","
  private final String levelSep = ":"

  static hasMany = [
          first: String,
          second: String,
          third: String,
          fourth: String,
          fifth: String,
          sixth: String
  ]

  static mapping = {
    cache usage: 'read-write', include: 'all'
  }

  static constraints = {
    first(nullable: false, minSize: 1)
    second(nullable: true)
    third(nullable: true)
    fourth(nullable: true)
    fifth(nullable: true)
    sixth(nullable: true)
  }

  def LevelPermission() {
    type = "intient.nimble.auth.WildcardPermission"
  }

  def buildTarget() {
    def target = ""                                         

    first.eachWithIndex {token, i ->
      target = target + token
      if (i != (first.size() - 1))
        target = target + tokenSep   //Add level token seperator
    }

    if (second && second.size() > 0) {
      target = target + levelSep

      second.eachWithIndex {token, i ->
        target = target + token
        if (i != (second.size() - 1))
          target = target + tokenSep   //Add level token seperator
      }

      if (third && third.size() > 0) {
        target = target + levelSep

        third.eachWithIndex {token, i ->
          target = target + token
          if (i != (third.size() - 1))
            target = target + tokenSep   //Add level token seperator
        }

        if (fourth && fourth.size() > 0) {
          target = target + levelSep

          fourth.eachWithIndex {token, i ->
            target = target + token
            if (i != (fourth.size() - 1))
              target = target + tokenSep   //Add level token seperator
          }

          if (fifth && fifth.size() > 0) {
            target = target + levelSep

            fifth.eachWithIndex {token, i ->
              target = target + token
              if (i != (fifth.size() - 1))
                target = target + tokenSep   //Add level token seperator

            }

            if (sixth && sixth.size() > 0) {
              target = target + levelSep

              sixth.eachWithIndex {token, i ->
                target = target + token
                if (i != (sixth.size() - 1))
                  target = target + tokenSep   //Add level token seperator

              }
            }
          }
        }
      }
    }

    this.target = target
  }

  /**
   * Populates this LevelPermission with data represented by passed in values. Removes any previously
   * set level values in this instance. Each sector should be a individual string and NOT contain sector seperators
   *
   * @first First sector represented as string, may contain token seperators
   * @second Second sector represented as string, may contain token seperators
   * @third Third sector represented as string, may contain token seperators
   * @fourth Fourth sector represented as string, may contain token seperators
   * @fifth Fifth sector represented as string, may contain token seperators
   * @sixth Sixth sector represented as string, may contain token seperators
   *
   * @return void - Will populate errors if problems found in any sector, does not persist object
   */
  public populate(first, second, third, fourth, fifth, sixth) {

    if (first == null || first.contains(this.levelSep)) {
      this.errors.rejectValue('target', 'levelpermission.invalid.first.sector')
      return
    }
    this.first = first.split(this.tokenSep) as List

    if (second) {
      if (second.contains(this.levelSep)) {
        this.errors.rejectValue('target', 'levelpermission.invalid.second.sector')
        return
      }

      this.second = second.split(this.tokenSep) as List

      if (third) {
        if (third.contains(this.levelSep)) {
          this.errors.rejectValue('target', 'levelpermission.invalid.third.sector')
          return
        }

        this.third = third.split(this.tokenSep) as List

        if (fourth) {
          if (fourth.contains(this.levelSep)) {
            this.errors.rejectValue('target', 'levelpermission.invalid.fourth.sector')
            return
          }

          this.fourth = fourth.split(this.tokenSep) as List

          if (fifth) {
            if (fifth.contains(this.levelSep)) {
              this.errors.rejectValue('target', 'levelpermission.invalid.fifth.sector')
              return
            }

            this.fifth = fifth.split(this.tokenSep) as List

            if (sixth) {
              if (sixth.contains(this.levelSep)) {
                this.errors.rejectValue('target', 'levelpermission.invalid.sixth.sector')
                return
              }

              this.sixth = sixth.split(this.tokenSep) as List
            }
          }
        }
      }
    }

    buildTarget()
  }
}
