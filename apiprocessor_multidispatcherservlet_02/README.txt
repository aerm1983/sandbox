2024-06-19:

Multiple DispatcherServlets, Strategy 02:
Disable default DispatcherServlet, add multiple secondary DispatcherServlets.


Steps:
- Disable default DispatcherServlet (see "exclude" along @SpringBootApplication annotation)
- server.servlet.context-path='/' # affects everything: default DispatcherServlet, actuator, swagger...
- spring.mvc.servlet.path='/' # affects only default DispatcherServlet
- Add DispatcherServlets using ServletRegistrationBean


Tests:
curl -si 'http://localhost:16002/api/ftp'
curl -si 'http://localhost:16002/api/fileprocessor'
curl -si 'http://localhost:16002/oauth/token'
curl -si 'http://localhost:16002/oauth/check_token'

