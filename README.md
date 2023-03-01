# PhoneNumberIdentifier

This project will help you to determine possible countries or international organisations by phone number. 
To do this, just go to http://localhost:8088 and print phone number to check in the text field and press "Identify country" button!
Application gets phone number information from wiki (https://en.wikipedia.org/wiki/List_of_country_calling_codes#Alphabetical_listing_by_country_or_region)

### Notes
- Sometimes, application can't define unambiguously the country for your phone number. In such cases, it will show you more than 1 country (For example: both Canada and United States country phone codes start with +1). 
- There are some unassigned phone number (For example: +99912486542)
- This application can work correctly only with ITU standard. It specifies that international telephone numbers are represented by prefixing the country code with a + sign

### Prerequisites
- JDK 17
- Docker
- Maven

### Run & Test
General:
1. Download the project
2. Go to the project main directory

To run it locally:
1. `docker compose up`
2. `mvn clean install`
3. `java -jar ./target/demo-0.0.1-SNAPSHOT.jar`

To run tests with reports:
1. `docker compose up`
2. `mvn clean test`
