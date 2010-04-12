package grails.plugins.nimble.js

import org.codehaus.groovy.grails.plugins.web.taglib.JavascriptProvider
import org.codehaus.groovy.grails.plugins.web.taglib.JavascriptValue
/**
 * An implementation for the Mootools javascript library
 *
 * @author Ford Guo
 */
class MootoolsProvider implements JavascriptProvider {
  
  //TODO how to process the onLoaded event??
  def doRemoteFunction(taglib,attrs, out) { 
    if(attrs.onLoading) {
      out << "${attrs.remove('onLoading')};"
    }		
    out << "new Request.HTML({"
    def url
    def jsParams = attrs.params?.findAll { it.value instanceof JavascriptValue }
    
    jsParams?.each { attrs.params?.remove(it.key) }    
    
    if(attrs.url) {
      url = taglib.createLink(attrs.url)
    } else {
      url = taglib.createLink(attrs)
    }
    
    if(!attrs.params) attrs.params = [:]
    jsParams?.each { attrs.params[it.key] = it.value }
    
    def i = url?.indexOf('?')
    out<<"url:'"
    if(i >-1) {
      if(attrs.params instanceof String) {
        attrs.params += "+'&${url[i+1..-1].encodeAsJavaScript()}'"                
      }else if(attrs.params instanceof Map) {
        def params = createQueryString(attrs.params)
        attrs.params = "'${params}${params ? '&' : ''}${url[i+1..-1].encodeAsJavaScript()}'"
      } else {
        attrs.params = "'${url[i+1..-1].encodeAsJavaScript()}'"
      }
      out << url[0..i-1]
    } else {
      out << url
    }
    out << "'"
    
    if(attrs.update) {
      out << ",update:'"
      out <<(attrs.update instanceof Map ? attrs.update.success : attrs.update)
      out << "'"       
    }
    if(attrs.onSuccess) {
      out << ",onSuccess: function(responseText, responseXML) {${attrs.remove('onSuccess')};}"
    }
	if(attrs.onComplete) {
      out << ",onComplete: function(responseTree, responseElements, responseHTML, responseJavaScript){${attrs.remove('onComplete')};}"
    }
  	if(attrs.onFailure ||
       (attrs.update instanceof Map && attrs.update.failure )) {
      out << ",onFailure: function(xmlHttpRequest){"
      if (attrs.onFailure) { 
        out << "${attrs.remove('onFailure')} ;"
      }
      if (attrs.update instanceof Map && attrs.update.failure) {
        out << "\$('" <<(attrs.update.failure)<<"').set('text',xmlHttpRequest.responseText) ;"
      } 
      out<< "}"
    }
    attrs.remove("update")
    
    // process options
    out << getAjaxOptions(attrs)
    // close
    out << "}).send();"
    attrs.remove('params')
  }
  
  private String createQueryString(params) {
    def allParams = []
    for (entry in params) {
      def value = entry.value
      def key = entry.key
        if (value instanceof JavascriptValue) {
          allParams << "${key.encodeAsURL()}='+${value.value}+'"
        }else {
          allParams << "${key.encodeAsURL()}=${value.encodeAsURL()}".encodeAsJavaScript()
        }
    }
    if(allParams.size() == 1) {
      return allParams[0]
    }else {
      return allParams.join('&')
    }
  }
  
  // helper function to build ajax options
  def getAjaxOptions(options) {
    def ajaxOptions = []
      
    // necessary defaults
    def optionsAttr = options.remove('options')
    def async = optionsAttr?.remove('asynchronous')
    if( async != null)
    ajaxOptions << "async:${async}"
    else
    ajaxOptions << "async:true"
    
    def eval = optionsAttr?.remove('evalScripts')
    if(eval != null)
    ajaxOptions << "evalScripts:${eval}"
    else
    ajaxOptions << "evalScripts:true" 
    
    def method = optionsAttr?.remove('mothod')
    if(method != null)
    ajaxOptions << "method:'${method}'"
    else
    ajaxOptions << "method:'post'" 

    if(options) {
      if(options.params) {
        def params = options.remove('params')
        if (params instanceof Map) {
          params = createQueryString(params)
        }
        ajaxOptions << "data:${params}"
      }
    }
    // remaining options
    optionsAttr?.each { k, v ->
      if(k!='url') {
        switch(v) {
        case 'true': ajaxOptions << "${k}:${v}"; break;
        case 'false': ajaxOptions << "${k}:${v}"; break;
        case ~/\s*function(\w*)\s*/: ajaxOptions << "${k}:${v}"; break;
        default:ajaxOptions << "${k}:'${v}'"; break;
        }
      }
    }
    
    return ",${ajaxOptions.join(',')}"
  }
  
  def prepareAjaxForm(attrs) {
    if(!attrs.forSubmitTag) attrs.forSubmitTag = ""
    attrs.params = "\$(this${attrs.remove('forSubmitTag')})"
  }
}
