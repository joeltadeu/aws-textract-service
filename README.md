# Image to Text Service

## Overview

This microservice is designed to extract text from images by leveraging the AWS Textract service. Users can upload an image, and the service will return the extracted text in a structured format.

[Amazon Textract](https://aws.amazon.com/textract/?nc2=h_ql_prod_ml_text) is a machine learning service provided by AWS that automatically extracts text, handwriting, and data from scanned documents and images. Unlike traditional optical character recognition (OCR) software, Textract goes beyond simple text extraction to identify and understand the context of the information within documents, such as tables, forms, and complex layouts. It can recognize and organize different types of content, making it easier to process and analyze data from a wide range of documents, including invoices, contracts, and receipts.

## Endpoint

### Image to Text

| EndPoint                 | Method | Description                |
|--------------------------|:------:|----------------------------|
| /v1/images/image-to-text |  POST  | Extract text from an image |

#### Example

> POST /v1/images/image-to-text

#### Request

> curl --location 'localhost:8080/v1/images/image-to-text' \
--header 'Content-Type: image/png' \
--data '@/C:/example.png'

#### Response

`200 - OK`

````json lines
{
  "blocks": [
    {
      "geometry": {
        "width": 0.6967111,
        "height": 0.011160103,
        "top": 0.32619655,
        "left": 0.15268661
      },
      "text": "O trio de arbitragem será composto pelo árbitro principal Raphael Claus, e pelos"
    },
    {
      "geometry": {
        "width": 0.49870822,
        "height": 0.008962118,
        "top": 0.33852282,
        "left": 0.15256107
      },
      "text": "assistentes Danilo Ricardo Simon Manis e Neuza lnes Back"
    },
    {
      "geometry": {
        "width": 0.6609247,
        "height": 0.0103672845,
        "top": 0.35514486,
        "left": 0.15222631
      },
      "text": "Acompanhe tudo sobre O jogo e fique por dentro dos melhores lances desse"
    }
  ]
}
````

## Tests

Execute the unit tests using the command bellow:

```bash
mvn test 
```

## Documentation and Examples

### Swagger

For the documentation of the APIs, access the link
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Postman collection

> :information_source: Postman collection can be found in the folder [postman](_assets/postman/aws-textract-service.postman_collection.json)

## Build & Run

### Local

```bash
mvn clean install
```
Run the command below to run the application.
```bash
java -jar aws-textract-service.jar
```

## Technologies Used

- Spring Boot 3.3.3
- Java 17
- AWS Textract


