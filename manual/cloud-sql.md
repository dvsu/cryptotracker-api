# Google Cloud SQL

## DB Instance Setup

**_CLI Command_**

```none
gcloud sql instances create {INSTANCE_ID} --database-version={POSTGRESQL_VERSION} --region={REGION} --cpu={vCPU_COUNT} --memory={MEMORY_GB} --root-password={ROOT_PASSWORD}
```

`INSTANCE_ID`

Name of the database instance

`POSTGRESQL_VERSION`

PostgreSQL version to be installed in the instance

- `POSTGRESQL_10`
- `POSTGRESQL_11`
- `POSTGRESQL_12`
- `POSTGRESQL_13`

`REGION`

Region where the instance is hosted

- `asia-east1`
- ...
- `asia-northeast1`
- ...
- `asia-south1`
- ...
- `asia-southeast1`
- ...
- `australia-southeast1`
- ...
- `europe-west1`
- ...
- `northamerica-northeast1`
- ...
- `us-central1`
- ...

`vCPU_COUNT`

Number of vCPU core: `1, 2, 4, 8, 16 ... 96`

`MEMORY_GB`

Size of memory: `4G, ... 104G`

At least 3840MiB. Memory size lower than 3840MiB requires manual setup on GCP Console

`ROOT_PASSWORD`

Default password for admin user `postgres`

**_Example_**

Parameters:

- Instance ID: `crypto-instance`
- PostgreSQL version: `13`
- Region: `asia-southeast1` (Singapore)
- CPU: `1` core
- Memory: `4` GB
- Root password: `testpassword`

```none
gcloud sql instances create crypto-instance --database-version=POSTGRES_13 --region=asia-southeast1 --cpu=1 --memory=4G --root-password=testpassword
```

## Database Setup

**_CLI Command_**

```none
gcloud sql databases create {DATABASE_NAME} --instance={SQL_INSTANCE_NAME}
```

`DATABASE_NAME`

Name of database hosted inside targeted SQL instance

`SQL_INSTANCE_NAME`

Name of targeted SQL instance

**_Example_**

Parameters:

- Database name: `transactions`
- Target SQL instance: `crypto-instance`

```none
gcloud sql databases create transactions --instance=crypto-instance
```

## Connection Setup

Make sure `PostgreSQL` is installed in local machine and registered as `PATH` variable.

**_Guides_**

[Connecting using a database client from a local machine or Compute Engine](https://cloud.google.com/sql/docs/postgres/connect-admin-ip)

For `Windows` machine, the `psql` has to be registered as `PATH` variable

`psql` path on `Windows` machine

```none
C:\Program Files\PostgreSQL\{POSTGRESQL_VERSION}\bin
```

To confirm that `psql` is successfully registered, check the version on `cmd`

```none
psql --version
```

To access `PostgreSQL` using `psql`, use the following command and input your `admin` password

```none
psql -U postgres
```

Once ready, initiate connection to `Cloud SQL`

```none
gcloud sql connect {INSTANCE_ID}
```

**_Example_**

Parameter:

- Target SQL instance: `crypto-instance`

```none
gcloud sql connect crypto-instance
```

Then provide your GCP SQL instance `postgres` admin password.  
**_Warning_** Not your local `postgres` admin password.

## Database Table and Fields/ Columns Setup

Make sure the connection is still active. Then, change to newly created database

```none
\c {DATABASE_NAME}
```

**_Example_**

```none
\c crypto-db
```

**_Create table and fields_**

Columns:

- `id`: stores unique id for each valid insertion
- `transaction_id`: unique id of a transaction
- `timestamp`: stores datetime value obtained from epoch to timestamp conversion at UTC
- `amount`: amount of cryptocurrency received at particular time

```sql
CREATE TABLE transactions (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    transaction_id VARCHAR(255) NOT NULL,
    datetime VARCHAR(255) NOT NULL,
    utc_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    amount NUMERIC NOT NULL
);
```
