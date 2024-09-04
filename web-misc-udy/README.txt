REMINDER:

(1) To inject variables from pom.xml into resources files:
(1.1) In pom.xml, plugins section, filtering should be enabled.
(1.2) Delimiter '@' is defined by SpringBoot2 maven plugin.
(1.3) Without it, delimiter is '${}' .

(2) JSP has limited support:
(2.1) JSP files are always read from "src/main/webapp/WEB-INF/jsp/" folder.
(2.2) Project packaging should be WAR.  Project can be standalone-run or server-deployed.
(2.3) Application properties "spring.mvc.view.prefix=/WEB-INF/jsp/" and "spring.mvc.view.suffix=.jsp" should be enabled.
(2.4) If project is packaged as JAR, JSPs are not read (see SpringBoot2 documentation, JSP limitaions).
