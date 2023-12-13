[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=otavioa_b3-financial&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=otavioa_b3-financial) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=otavioa_b3-financial&metric=coverage)](https://sonarcloud.io/summary/new_code?id=otavioa_b3-financial)

# Market Financial Ratios (MFR) API

**Market Financial Ratios (MFR)** is a straightforward API designed to retrieve market financial ratios of companies from Brazil and the United States. The data is sourced from [StatusInvest](https://statusinvest.com.br/).

## How It Works

The API extracts necessary data from [StatusInvest](https://statusinvest.com.br/) and stores it in a MongoDB database. Users can then access the API to retrieve information from the database.

## Getting Started

To get started with the application, follow these steps:

1. **Prepare the Environment:**
  - Install Java 17 or later.
  - Configure your IDE with [Lombok](https://projectlombok.org/).
  - Set up a MongoDB instance for the application.

2. **Clone the Repository:**
   ```bash
   git clone \<repository-url\>
    ```
   
3. **Set Environment Variables:**
- **MONGO_URI**: mongodb+srv://\<user\>:\<password\>@\<mongo_url\>/?retryWrites=true&w=majority
- **MONGO_DATABASE**: mfr_collection (*for example*)
You can set these variables as Java Arguments when starting the application.

4. **Start the Application:**
- Run the `br.com.mfr.Application.class` class as a Java Application.
- Alternatively, use the Maven command `mvn spring-boot:run`.

4. **Access the API:**
- The application will be available on port 5000 (`localhost:5000/`).
- A documentation is available at `/`.

5. **Populate the Database:**
- Call `http://localhost:5000/data/charge` to populate the database.


## Running Tests

The application is thoroughly tested. Execute tests manually using your IDE or run the `mvn test`.


**_Feel free to explore and contribute to the project!_**
