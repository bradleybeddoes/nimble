
package intient.nimble

import intient.nimble.domain.User
import intient.nimble.domain.Profile

class InstanceGenerator {

    static user = { try { InstanceGenerator.class.classLoader.loadClass("User").newInstance()} catch(ClassNotFoundException e){User.newInstance()} }
    static profile = { try { InstanceGenerator.class.classLoader.loadClass("Profile").newInstance()} catch(ClassNotFoundException e){Profile.newInstance()} }

}

