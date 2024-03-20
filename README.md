[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=otavioa_mfr&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=otavioa_mfr) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=otavioa_mfr&metric=coverage)](https://sonarcloud.io/summary/new_code?id=otavioa_mfr)

# Market Financial Ratios (MFR) API

**Market Financial Ratios (MFR)** is a straightforward API designed to retrieve market financial ratios from Brazillian Companies and the United States. The data is sourced from [StatusInvest](https://statusinvest.com.br/).

## How It Works

The API retrieves essential data from [StatusInvest](https://statusinvest.com.br/) and saves it into a internal MongoDB database. Subsequently, users can access this information via the local API and retrieve the specific data they require.

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
- Alternatively, you can set these variables as Java Arguments when starting the application.

4. **Start the Application:**
- Run the `br.com.mfr.Application.class` as Java Application.
- Or use the Maven command `mvn spring-boot:run`.

5. **Access the API:**
- The application will be available on port 5000 (`localhost:5000/`).
- The documentation is available at `/`.

6. **Populate the Database:**
- Call `http://localhost:5000/data/populate` to populate the database.
- It is recommended that the data be refreshed whenever it is used, within a timeframe that does not affect the analysis of the API user. For example: Once a day.

## Running Tests

The application is thoroughly tested. Execute tests manually using your IDE or run the `mvn test`.


**_Feel free to explore and contribute to the project!_**
