# Cryptotracker API

A simple mock API that demonstrates crypto asset monitoring.

## Features

1. Add transaction (received)
2. Monitor cumulative asset balance (every 1 hour)

## Tools and Services

1. Spring Framework
2. Google Cloud Platform

## Get Started

### Initial Setup

1. Cloud Tools: Google Cloud CLI

   The application is wired to some Google Cloud services, so initial `gcloud CLI` tool is required. Please see `manual/gcloud.md` for details.

2. Database: Google Cloud SQL

   The database used in this demo is `PostgreSQL` hosted on `Google Cloud SQL`. Please see `manual/cloud-sql.md` for details.

3. Environment Variables

   The application relies on a few environment variables that are currently predefined in `src/main/resources/application.properties`. There are 5 variables that have to be set up, however, feel free to customize according to your needs.

   **_Examples_**

   ```none
   POSTGRESQL_USERNAME=postgres
   POSTGRESQL_PASSWORD=a1b2c3d4e5
   PROJECT_ID=the-universe-12345
   INSTANCE_NAME=crypto-instance
   REGION=asia-southeast1
   ```

   **_Explanation_**

   `POSTGRESQL_USERNAME`  
   Cloud SQL - PostgreSQL user name. The default admin is `postgres`

   `POSTGRESQL_PASSWORD`  
   User password for particular SQL instance

   `PROJECT_ID`  
   Currently selected GCP project ID

   `INSTANCE_NAME`  
   Name of SQL instance

   `REGION`  
   Region where SQL instance is hosted

4. The application can be executed directly on `Intellij Idea` or prepacked as `jar` file.

5. To bundle the application as a single `jar` file

   ```none
   ./mvnw package -DskipTests
   ```

6. Finally, to execute the `jar` file

   ```none
   java -jar {NAME_OF_JAR_FILE}
   ```

### System Diagram

```none

         API Endpoint    <--->    Spring Application   <--->   Cloud SQL
                                            |                 (PostgreSQL)
                                            |
                                            V
                                      Cloud Pub/Sub
                                        (Publish)
                                            |
                                            |
                                            V
                                 Message subscription
                                  for other services
                                  (not implemented)
```

### API: Introduction

The live demo is temporarily hosted in `Compute Engine` for testing purpose only.
You can also access the endpoint to do live testing using your favourite API tool, such as `Postman`.

**_Note_**  
Both `Compute Engine` and `Cloud SQL` are at minimum hardware setup, so performance may be limited.

**_Endpoint_**

```none
http://35.240.183.63:9000/
```

**_API Definition_**

```none
http://35.240.183.63:9000/swagger-ui.html
```

### API: Methods

**_Get all transactions_**

---

Path

```none
GET /api/transactions
```

Header  
`Content-Type: application/json`

Body  
_not required_

Response

```json
[
  {
    "transaction_id": "196e5a31-d5b3-4618-bf34-4b1872bc4567",
    "datetime": "2021-11-21T03:09:59+01:00",
    "utc_datetime": "2021-11-21T02:09:59Z",
    "amount": 1.2
  },
  {
    "transaction_id": "cea7dafe-80fd-4a27-ab1a-155a7c3af029",
    "datetime": "2021-11-21T07:09:59+01:00",
    "utc_datetime": "2021-11-21T06:09:59Z",
    "amount": 0.24346
  }
]
```

**_Add transaction_**

---

Path

```none
POST /api/transaction
```

Body: _required_

```json
{
  "datetime": "2021-11-22T12:13:52+10:00",
  "amount": 12
}
```

|  Key Name  |          Format           |      Description      |
| :--------: | :-----------------------: | :-------------------: |
| `datetime` | `yyyy-MM-ddTHH:mm:ssxxxx` | Time zone is required |
|  `amount`  |         `xx.xxx`          |        Decimal        |

Response

```json
{
  "message": "Update successful",
  "timestamp": "2021-11-22 12:34:56"
}
```

**_Get Per-Hour Cumulative Asset_**

---

Path

```none
GET /api/balance
```

Body: _required_

```json
{
  "startDatetime": "2021-11-19T21:01:01+00:00",
  "endDatetime": "2021-11-22T23:48:02+02:00"
}
```

Response

```json
[
    {
        "datetime": "2021-11-19T22:00:00+00:00",
        "amount": 33.134757476
    },
    {
        "datetime": "2021-11-19T23:00:00+00:00",
        "amount": 33.134757476
    },
    {
        "datetime": "2021-11-20T00:00:00+00:00",
        "amount": 33.134757476
    },
    {
        "datetime": "2021-11-20T01:00:00+00:00",
        "amount": 33.134757476
    },
    ...
]
```
