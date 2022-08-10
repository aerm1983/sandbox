package localhost.udemy01;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.google.gson.Gson;

@Component
public class UserRequestInterceptor implements HandlerInterceptor {
	
	private static final Logger log = LoggerFactory.getLogger(UserRequestInterceptor.class);
	private static Gson gson = new Gson();
	

    @Override
    public boolean preHandle(
      HttpServletRequest request, 
      HttpServletResponse response, 
      Object handler) {
    	
    	ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
    	
    	byte[] byteArray = requestWrapper.getContentAsByteArray();
    	
    	Map<String,String[]> map = requestWrapper.getParameterMap();
    	
    	String contentString = new String(byteArray);
    	
    	// log.info("ALBERTO look at me!!!!" + gson.toJson(map));
    	// log.debug("ALBERTO request map: " + map);
    	log.debug("ALBERTO contentString: " + contentString);
    	
    	request.setAttribute("myReqAtt", "this_is_a_request_attribute");
    	
        return true;
    }

    @Override
    public void afterCompletion(
      HttpServletRequest request, 
      HttpServletResponse response, 
      Object handler, 
      Exception ex) {
        //
    }
}