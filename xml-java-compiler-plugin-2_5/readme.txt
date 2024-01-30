XJC-2.5.0 MAVEN PLUGIN SANDBOX
2023-01-30 04:50


Sandbox to generate Java Pojo from Xsd.


With XJC-1.6   -> JAXB-2.2.7.  
With XJC-2.5.0 -> JAXB-2.3.2.


Execute:
mvn clean jaxb2:xjc


Optionally add parameter (seems unnecessary for XJC-2.5.0):
mvn clean jaxb2:xjc -Djavax.xml.accessExternalSchema=all


Look into "target/generated-sources/jaxb/generated/" folder (this could change).


For jaxb2 maven plugin help:
mvn jaxb2:help


pom.xml:
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>jaxb2-maven-plugin</artifactId>
    <version>2.5.0</version>
    <executions>
        <execution>
            <goals>
                <goal>xjc</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <sources>
            <source>src/main/resources</source>
        </sources>
        <packageName>com.yourcompany.xsd</packageName>
    </configuration>
</plugin>


reference link:
https://www.mojohaus.org/jaxb2-maven-plugin/Documentation/v2.5.0/example_xjc_basic.html


stack-overflow link:
https://stackoverflow.com/questions/33731896/how-to-create-pojo-classes-from-xsd

