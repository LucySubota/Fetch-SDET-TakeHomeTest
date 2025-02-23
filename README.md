# Fetch-SDET-TakeHomeTest

## Project Overview
This project is a geocoding test suite that retrieves location and zip code information using an external geocoding API. Made by Liudmila Subota as a technical take home assignment from scratch.


## Running Instructions for Util
To retrieve location and zip code information, pass arguments in the following terminal command:

```java
# mvn test -Dtest=GeoCodingTest -Dlocations=""your-first-location-argument" "your-second-location-argument""
```

### **Accepted argument formats:**
```java
"argument"

“argument”

'argument'

"argument'

'argument"

“argument"

"argument”

“argument'

'argument”
```

### **Accepted Zip/Post Code formats:**
```java
12345

12345-4567
```

### **Accepted City/State formats:**
```java
New York, NY

29 Palms, CA

Long Beach-Lakewood, CA
```

### **Example command:**
```java
mvn test -Dtest=GeoCodingTest -Dlocations="“New York, NY” “12345” “Chicago, IL” “10001”"
```

You do not need to separate arguments with a comma, although this format will still be accepted.

The results of the search will be displayed on the console as log INFO - Location Results

## Running Instructions for Integration tests
To run all integration tests, pass the following terminal command:

```java
# mvn test -Dtest=GeoCodingIntegrationTest
```
