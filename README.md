# Book Publishing House
Provides API for managing authors and books.
Admins can add new authors while authors can publish new books.
Public audience can retrieve list of all authors and books.

Follow the steps below to run the application locally in your environment with your settings.

## Configuration
Configuration can be found in `application.yml`. 
Before running application, create new database `book_publishing_house`.

PostgreSQL related properties have to be changed to run the application in your local environment.
In `application.yml`  change properties `spring.datasource.username` and `spring.datasource.password` to match yours.

Same has to be done in `liquibase.properties` file for properties `username` and `password`. 

Change datasource url port if it's not the same as in yml (`jdbc:postgresql://localhost:5432/book_publishing_house`)

## Running the application
Application can be run with the command `mvn spring-boot:run` on port 8090.

## API documentation
Open `http://localhost:8090/swagger-ui/` in your browser to see documented endpoints,
 input parameters and return models.
For testing authorized API (starting with `/api/auth`) use 
[Spring-JWT-Authorization-Server](https://github.com/dmorozin/Spring-JWT-Authorization-Server) 
to get the token with required scope:

Admin:
`http://localhost:9000/token?scope=admin`

Author:
`http://localhost:9000/token?scope=author`

In Swagger, click on Authorize and paste your token value as `Bearer <token>`

## Report generation
Every hour scheduler generates Jasper report with the list of the ISBNs of the latest added books since the last run.
If generation is successful, the report is sent by email. 
The `job` table tracks all run jobs and their success.

Email sending is implemented using Gmail SMTP.
To send the report to your email, change the `email-to` property in `application.yml`.
