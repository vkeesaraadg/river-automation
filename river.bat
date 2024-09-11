echo "River Automation suite execution"
mvn clean test -DresourceXmlPath=src/test/resources/runner -DsuiteXmlFileName=river-testng.xml
