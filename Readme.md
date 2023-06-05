# Currency Conversion from Euro to British Pound

All the tests were written under src/test/java

** BaseScript.java**

_package: com.xe.currencyconverter.basescript_

This script initiates the driver based on the browser value we pass in properties/application.properties file. 

The possible value is chrome or firefox or edge. 

It also initiates extent reports objects in @BeforeMethod. 

In @AfterMethod, if any test fails it captures the screenshots. 

When the test is completed, in @AfterTest method, it flushes the report and report will be created.

**CurrencyConverterPage.java**

_package: com.xe.currencyconverter.pages_

All the element identification techniques will be under this page.

**DateUtils.java & ExcelUtils.java**

_package: com.xe.currencyconverter.utils_

DateUtils.java - Contains method which returns date and timestamp in the format yyyy_MM_dd_HH_mm_ss

ExcelUtils.java - Contains method which returns data in an excel int he form of Map<Integer, Map<String, String>>

**CurrencyConversions.java & CurrencyConversions2.java**

CurrencyConversions.java - This script will execute with the number of rows of data we give in excel sheet(CurrencConversion.xlsx) under data folder. It is one test and it will be data driven for the number of rows in the excel. Problem with this script is if one iteration fails because of some false data in the excel, it will stop there and the further iterations won't be executed.

CurrencyConversions2.java - This script will execute with the number of rows of data we give in excel sheet(CurrencConversion.xlsx) under data folder. These are different tests for the number of rows in the excel. If there is some false data in the excel, it will fail for that particular row of data but will proceed with other data.

**data folder**

This folder contains data sheet which is CurrencyConversion.xlsx where the data is placed.

**properties folder**

This folder contains properties file which is application.properties. Currently it contains only one setting which is browser setting

```
\# Possible values chrome or firefox or edge
browser=chrome
```

**reports folder**

This is where all the extent reports will be stored. The reports will be under the folder report_yyyy_MM_dd_HH_mm_ss.

**servers folder**

In this folder all the driver.exe (chromedriver.exe, gechodriver.exe and msedgedriver.exe) files are stored.


# Running the scripts using testng configuration files.

There are two testng configuration files (testng.xml and testng2.xml) are created.

testng.xml - for running the CurrencyConversions.java script.

testng2.xml - for running the CurrencyConversions2.java script.

Right click on any of the above files, click on "Run As" and click on "TestNG Suite" and it will execute the corresponding script.

# Running the scripts using maven (pom.xml)

In pom.xml file two profiles are created with ids "eurotopound" and "eurotopound2". 

Open Command Prompt and navigate to the folder where we have the pom.xml.

Run the following command to execute CurrencyConversions.java script

```
mvn test -P eurotopound
```

Run the following command to execute CurrencyConversions2.java script

```
mvn test -P eurotopound2
```