---------File format and guide-------------------
1. File to be uploaded should be in csv or xls extension. Any other file type will be ignored.
2. The csv should have 3 columns that is comma separated e.g VRM,Make,Colour
3. Excel file should also have 3 columns in this order VRM, Make, Colour
4. By default, the first row of csv and excel file will be ignore as it is assumed to be the column header
5. Files can be added to directory - /src/main/java/resources/Files


--------Pre-requisite to run tests---------------
1. Maven should be available on PC.
2. PC is configured with maven home and Java home.


--------To run test via command prompt------------
1. Pull the framework to your local directory
2. Open command prompt.
3. cd to the directory where the framework is located.
4. Do "mvn compile" (to compile the framework and download all dependencies)
5. Do "mvn install test" (to run all tests)


-------Improvements / Nice to have---------------
1. This framework can be improved by using spring boot, and create service that would return the make and colour of a car based on request params. Also spring bean can be used to store all vehicle data.
2. At the moment accepted file types is csv and xls, this can be improved to also accept xlsx files.
