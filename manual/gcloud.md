# Google Cloud Platform Setup

## Installing Cloud SDK

https://cloud.google.com/sdk/docs/install

## Get Started

Open `terminal` and execute the following command

```none
gcloud init
```

## Check Existing Credentials

```none
gcloud auth list
```

## Login Credential

```none
gcloud auth login [EMAIL_NAME]
```

Example

```none
gcloud auth login
```

Expected output:

> This will allow `Google Cloud SDK` to:
>
> 1. See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account.
>
> 2. View and manage your Google Compute Engine resources
>
> 3. View and manage your applications deployed on Google App Engine
>
> Click `Allow`

## Set project (optional)

If project is not set, you may want to run this

```none
gcloud config set project {PROJECT_ID}
```

## Check existing configurations

```none
gcloud config configurations list
```

or

```none
gcloud config list
```

## Setup Wizard

```none
gcloud init
```

## Setup manually

```none
gcloud config set project {PROJECT_NAME}
```

```none
gcloud config set compute/region {REGION_CODE}
```

```none
gcloud config set compute/zone {ZONE_CODE}
```

## Revoke Unused Credentials

```none
gcloud auth revoke {EMAIL_NAME}
```

## Setup Existing Application Default Credentials (ADC)

```none
gcloud auth application-default login
```

1. Login using your Gmail account
2. Allow and authorize `Google Auth Library`

Expected output:

> This will allow Google Auth Library to:
>
> See, edit, configure, and delete your Google Cloud data and see the email address for your Google Account.
>
> Click `Allow`

Next message:

> `You are now authenticated with the Cloud SDK!`

It will create `application_default_credentials.json`
in local machine

## Revoke Existing Application Default Credentials (ADC)

The following command will remove `application_default_credentials.json` that is stored in local machine

```none
gcloud auth application-default revoke
```
