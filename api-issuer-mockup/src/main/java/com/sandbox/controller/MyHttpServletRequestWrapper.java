package com.sandbox.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {
    
	private static final Logger log = LoggerFactory.getLogger(MyHttpServletRequestWrapper.class);
	private final String requestBodyStr;

    public MyHttpServletRequestWrapper(HttpServletRequest httpServletRequest) throws Exception {
        super(httpServletRequest);
        
        char[] charArray = new char[2048];
        Integer i = httpServletRequest.getReader().read(charArray);
        this.requestBodyStr = new String( charArray );
        // body = IOUtils.toString(request.getReader());
        log.info("charArray 1 body ; length; i : : " + charArray.toString() + " ; " + charArray.length + " ; " + i );
        log.info("requestBodyStr : " + requestBodyStr );
    }
    
    /*
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getBody().getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }

        };
        return servletInputStream;
    }
    */

    public String getRequestBodyStr() {
        return this.requestBodyStr;
    }
}