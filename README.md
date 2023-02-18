# assessment
1.BDD Framework is used to write the code
2.POM is followed to write elements and methods for particular webpage in src/main/java/pages
3.Feature flow is present inside src/test/java/features that describes the behavior of application and scenarios under test which are written in Given When and Then
4.Runner Class is present to run the code and ExtentCucumberAdapter plugin used to generate the report
5.Code for Steps is defined under step_definition 
6.Configuration.properties in present under configs folder where all the hardcoded values are defined like email address, password, application url 
7.To read the values from configuration.properties, ConfigFileReader class is created inside dataProvider
8.chrome_driver.exe is present inside drivers folder for chrome version 110 
9.Properties for Extent reporting can be modified in extent.properties which is present inside src/test/resources
10.Once framework is run,report gets generated inside test-output folder which further contains folder SpartReport d-MMM-YY HH-mm-ss. Report folder contains with Spark.html and Screenshots are present in Screenshots folder.
11.Code can be run by right clicking on Runner file and click Run as Junit
