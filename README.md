---------File format and guide-------------------
1. File to be uploaded should be in csv or xls extension. Any other file type will be ignored.
2. The csv should have 3 columns that is comma separated e.g VRM,Make,Colour
3. Excel file should also have 3 columns in this order VRM, Make, Colour
4. By default, the first row of csv and excel file will be ignore as it is assumed to be the column header
5. Files can be added to directory - {user.dir}/files


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
1. This framework can be improved by using spring boot, and create web service that would return the make and colour of a car based on request params. Also spring bean can be used to store all vehicle data loaded from csvs.
2. At the moment accepted file types is .csv and .xls, this needs to be improved to also accept xlsx files as well for more flexibility.
3. Also logging needs to be added to when tests fail to help track and debug test failures quicker.