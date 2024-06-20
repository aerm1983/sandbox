2024-06-19:

Multiple DispatcherServlets, Strategy 01:
Keep default DispatcherServlet, add multiple secondary DispatcherServlets.


Steps:
- Default DispatcherServlet enabled
- server.servlet.context-path='/' # affects everything: default DispatcherServlet, actuator, swagger... 
- spring.mvc.servlet.path: '/api' # affects only default DispatcherServlet
- Add secondary DispatcherServlets using ServletRegistrationBean


Tests:
curl -si 'http://localhost:16001/api/ftp'
curl -si 'http://localhost:16001/api/fileprocessor'
curl -si 'http://localhost:16001/oauth/token'
curl -si 'http://localhost:16001/oauth/check_token'

