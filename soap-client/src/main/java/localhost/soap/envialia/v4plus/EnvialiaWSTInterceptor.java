package localhost.soap.envialia.v4plus;

import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.http.Header;

import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.transport.WebServiceConnection;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpComponentsConnection;
import org.springframework.ws.transport.http.HttpUrlConnection;

import org.springframework.beans.factory.annotation.Configurable;


@Configurable
public class EnvialiaWSTInterceptor implements ClientInterceptor{
	
	private static Logger log = LogManager.getLogger(EnvialiaWSTInterceptor.class);

	@Override
	public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
	  
		WebServiceConnection webServiceConnection = TransportContextHolder.getTransportContext().getConnection();
		HttpComponentsConnection HCConnection = null;
		HttpUrlConnection HUConnection = null;
  	
      	if (webServiceConnection instanceof HttpComponentsConnection ) {
      		HCConnection = (HttpComponentsConnection) webServiceConnection;
      		log.info("WebServiceTemplate - message sender is: {}", HCConnection.getClass());
      	} else if (webServiceConnection instanceof HttpUrlConnection) {
      		HUConnection = (HttpUrlConnection) webServiceConnection;
      		log.info("WebServiceTemplate - message sender is: {}", HUConnection.getClass());
	              	}

	              	return true;
            	}

				@Override
				public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
					return true;
				}

				@Override
				public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
					return true;
				}
				
				@Override
				public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {
  
					WebServiceConnection webServiceConnection = TransportContextHolder.getTransportContext().getConnection();
					HttpComponentsConnection HCConnection = null;
					HttpUrlConnection HUConnection = null;

					if (webServiceConnection instanceof HttpComponentsConnection ) {
	
						HCConnection = (HttpComponentsConnection) webServiceConnection;
	
						String requestHeaders = "";
			for(Header header : HCConnection.getHttpPost().getAllHeaders()) {
				requestHeaders += header.getName() + ": " + header.getValue() + " --- ";
			}

			String responseHeaders = "";
			for(Header header : HCConnection.getHttpResponse().getAllHeaders()) {
				responseHeaders += header.getName() + ": " + header.getValue() + " --- ";
			}

			log.info("SOAP - http request headers: {}", requestHeaders);
			log.info("SOAP - http response headers: {}", responseHeaders);

		} else if (webServiceConnection instanceof HttpUrlConnection) {

			HUConnection = (HttpUrlConnection) webServiceConnection;
			HttpURLConnection jnConnection = HUConnection.getConnection();
			
			Map<String,List<String>> requestMap = jnConnection.getRequestProperties();
			Set<String> requestKeysSet = requestMap.keySet();
			Iterator<String> requestKeyIterator = requestKeysSet.iterator();
			
			String request = "";
			String reqKey = null;
			List<String> reqKeyList = null;
			
			while ( requestKeyIterator != null & requestKeyIterator.hasNext() ) {
				reqKey = requestKeyIterator.next();
				request = reqKey + ": ";
			
				reqKeyList = requestMap.get(reqKey);
				for (String reqKeyListElem : reqKeyList) {
					request += reqKeyListElem + ", ";
				}

				request += " ; ";
			}
			
			log.info("http request headers: {}", request);
		}
	}
}