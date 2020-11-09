# WeatherDetailsComparision

This project provide automation solution that compares weather information from web & API
sources.

Technologies used : Selenium Webdriver, TestNG, RestAssured, Java, Page Object Model design pattern.

Flow : 

main folder : 
      base folder -> contains base class, which is used for initializing the driver and loading the config.properties file.
      config folder -> contains config.properties, where we stored our UI and API properties.
      constants folder -> contains Endpoint.java class, that contains API endpoints. 
      extentreport folder -> contains java files that will be useful for configuring extent report.
      model folder -> contains pojo classes, that can hold data.
      pages folder -> contains all common components and individual pages.
      util folder -> contains testUtil class which have common utilities methods.
      
resources : 
      log4j.properties -> for using logs 
      
test folder :
      contains page test classes.
      
pom.xml -> for managing dependencies, also helps running the testNG.xml files through maven.
testNG.xml -> for managing the test classes, running test execution in parallel. Used parameters for passing the data into testfiles.
extent.html -> report with screenshot.
      
How to run project : 

1. run testNG.xml file through intelliJ
2. execute command "mvn clean install", through cli (firstly navigate to pom.xml file)

Additional Info : 

Extent report will genearte amazing reports. (extent.html)
Failed test cases images will be attached in the report.
logs will be generated using log4J.
WebEventListener is used for listining and logging the events.

How can you pass the cityname and other comparable inputs ?
You can provide input values in testNG.xml file.


      
      
