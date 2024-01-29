XJC-1.6 MAVEN PLUGIN SANDBOX
2023-01-29 17:00

Sandbox to generate Java Pojo from Xsd.

Execute:
mvn clean jaxb2:xjc -Djavax.xml.accessExternalSchema=all

Look into "target/generated-sources/jaxb/generated/" folder (this could change).

For jaxb2 maven plugin help:
mvn jaxb2:help
