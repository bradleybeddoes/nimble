package intient.nimble.controller

import grails.plugin.spock.*

class AdminsControllerSpecification extends ControllerSpecification {
	
	def "validate 200 on index"() {
		
		when:
		controller.index()
		
		then:
		mockResponse.status == 200
	}
	
}