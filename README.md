# Fetch-SDET-TakeHomeTest

## Project Overview
A Java-based utility that retrieves location information using OpenWeatherMap's Geocoding API. This project was developed as a technical take-home assignment by Liudmila Subota.

## Features
- Supports multiple location queries in a single command, do not need to separate each with a comma, but is supposted
- Handles both city/state combinations and ZIP codes as separate arguments within a single command
- Provides detailed US location results including:
  - Place name
  - Country
  - State (where applicable)
  - Latitude and Longitude
  - ZIP code (where applicable)

## Running Instructions for Util
To retrieve location and zip code information, pass arguments in the following terminal command:

```java
# mvn test -Dtest=GeoCodingTest -Dlocations="'your-first-location-argument' 'your-second-location-argument'"
```

### Input Format Requirements
#### Quotation Marks
The command accepts both curly double quotes (smart quotes) and single quotes:


**Double Quotes:**

✅ Correct: “Madison, WI”  (using curly/smart quotes)

❌ Incorrect: "Madison, WI" (using straight/keyboard quotes)


**Single Quotes:**

✅ Correct: 'Madison, WI'


Note: To type curly quotes:
- Mac: 
  - Opening quote (“); Option + [
  - Closing quote (”): Option + Shift + [
- Windows:
  - Opening quote ("): Alt + 0147
  - Closing quote ("): Alt + 0148

```java
“argument”

'argument'

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

### **Example commands:**
```java
mvn test -Dtest=GeoCodingTest -Dlocations="“New York, NY” “Chicago, IL” '10001' “12345”"
mvn test -Dtest=GeoCodingTest -Dlocations="“New York, NY” 'Chicago, IL'"
mvn test -Dtest=GeoCodingTest -Dlocations="“10001” “12345”"
```

## Notes

You do not need to separate arguments with a comma, although this format will still be accepted.

The results of the search will be displayed on the console as log INFO - Location Results

The Zip/Post code and city/state arguments are independent, user can pass as many independent arguments in single command as they would like.

## Running Instructions for Integration tests
To run all integration tests, pass the following terminal command:

```java
# mvn test -Dtest=GeoCodingIntegrationTest
```
