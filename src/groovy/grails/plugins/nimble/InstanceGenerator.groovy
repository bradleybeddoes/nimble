/*
 *  Nimble, an extensive application base for Grails
 *  Copyright (C) 2010 Bradley Beddoes
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
package grails.plugins.nimble

import org.codehaus.groovy.grails.commons.ConfigurationHolder

import grails.plugins.nimble.core.UserBase
import grails.plugins.nimble.core.ProfileBase

/**
 * Determines correct version of class to load for classes commonly overloaded by host applications
 *
 * @author Bradley Beddoes
 */
class InstanceGenerator {

    static user = { 
    	try { 
    		if(ConfigurationHolder.config?.nimble?.implementation?.user)
    			InstanceGenerator.class.classLoader.loadClass(ConfigurationHolder.config.nimble.implementation.user).newInstance()
    		else
    			UserBase.newInstance()
    	} catch(ClassNotFoundException e){ UserBase.newInstance() } 
    }
    
    static profile = { 
    	try { 
    		if(ConfigurationHolder.config?.nimble?.implementation?.profile)
    			InstanceGenerator.class.classLoader.loadClass(ConfigurationHolder.config.nimble.implementation.profile).newInstance()
    		else
    			ProfileBase.newInstance()
    	} catch(ClassNotFoundException e){ ProfileBase.newInstance() } 
    }
}

