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

/**
 * Provides generic, mostly UI related tags to the Nimble application
 *
 * @author Bradley Beddoes
 */
class NimbleTagLib {

  static namespace = "n"

  /**
   * Enables growl like message popup in the page
   */
  def growl = {attrs, body ->
    out << render(template: "/templates/growl", contextPath: pluginContextPath)
  }

  /**
   * Enables growl message popup when the Grails application stores a message in flash scope
   */
  def flashgrowl = {attrs, body ->
    out << render(template: "/templates/flashgrowl", contextPath: pluginContextPath)
  }

  /**
   * Provides an inline output of the Grails application message in flash scope
   */
  def flashembed = {attrs, body ->
    out << render(template: "/templates/flashembed", contextPath: pluginContextPath)
  }

  /**
   * Imports JQuery Javascript to make the JQuery library available to the current page
   */
  def jquery = {attrs, body ->
    out << render(template: "/templates/jquerysetup", contextPath: pluginContextPath)
  }

  /**
   * Imports the JS required to create a Nimble menu
   */
  def menu = {attrs, body ->
    out << javascript(src: "nimblemenu.js")
  }

  /**
   * provides markup to render grails error messages for beans
   */
  def errors = {attrs, body ->
    def bean = attrs['bean']
    if (bean)
      out << render(template: "/templates/errors", contextPath: pluginContextPath, model: [bean: bean])
    else
      out << render("Error: Details not supplied to generate error content")
  }

  /**
   * Provides markup to render the default password policy
   */
  def passwordpolicy = {attrs, body ->
    out << render(template: "/templates/help/passwordpolicy", contextPath: pluginContextPath)
  }

  /**
   * Provides markup to render the default username policy
   */
  def usernamepolicy = {attrs, body ->
    out << render(template: "/templates/help/usernamepolicy", contextPath: pluginContextPath)
  }

  /**
   * Provides markup to render the default account creation policy
   */
  def accountcreationpolicy = {attrs, body ->
    out << render(template: "/templates/help/accountcreationpolicy", contextPath: pluginContextPath)
  }

}

