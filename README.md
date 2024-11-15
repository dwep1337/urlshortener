# URL Shortener Service

A URL Shortener service built using Spring Boot that allows you to create short URLs and redirect users to the original URLs. The service supports automatic URL expiration and provides easy-to-use endpoints for shortening and redirection.

## Getting Started

Follow the instructions below to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Ensure you have the following installed on your machine:
- Java 21 or later
- Maven
- A PostgreSQL database (or modify for another database)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/dwep1337/urlshortener.git
   ```
2. Navigate to the project directory:
   ```bash
   cd urlshortener
   ```
3. Configure the database connection in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/urlshortener
   spring.datasource.username=yourusername
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   api.baseurl=http://localhost:8080
   ```
4. Build and run the application using Maven:
   ```bash
   mvn spring-boot:run
   ```

### Usage

Once the application is running, you can use the provided REST API endpoints to shorten URLs and handle redirections.

### Endpoints

#### 1. Create a Shortened URL
- **POST** `/shortener`
- **Request Body** (JSON):
  ```json
  {
    "destinationUrl": "https://example.com"
  }
  ```
- **Response**:
  ```json
  {
    "shortUrl": "http://localhost:8080/r/abc123"
  }
  ```

#### 2. Redirect to Original URL
- **GET** `/r/{slug}`
  - Redirects the user to the original URL if the slug is valid and not expired.



### Contributing

Contributions are welcome! If you would like to contribute, please fork the repository and submit a pull request. For major changes, open an issue to discuss what you would like to change.

---
