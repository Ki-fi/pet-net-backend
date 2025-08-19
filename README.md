# Installation Manual

*Manual for installing and running the Pet Net Backend project*
<br/>
<br/>

## **Project**

**Pet Net is a social platform that connects pet owners to local pet sitters, making pet sitting convenient, fast, and accessible. The Project consists of:**

- **A Spring Boot backend, offering a REST API**
- **A React frontend**
<br/>

## System requirements

| Language BE | Java 17 |
| --- | --- |
| Framework BE | Spring Boot 3.5.3 |
| Framework FE | React 19.1.0 |
| Dependency manager BE | Apache Maven 4.0.0 |
| Dependency manager FE | NPM |
| JDK | Amazon Coretto 24.0.1 |
| API testing platform | Postman |
| Database engine | PostgreSQL |
| Database management | pgAdmin 4 |
| IDE BE | IntelliJ IDEA |
| IDE FE | WebStorm IDEA |

<br/>

## Configuration & Installation

**Backend**

1. In pgAdmin, add a new PostgreSQL database
    
    
    | database | pet-net |
    | --- | --- |
    | username | postgres |
    | password | password |
2. Clone/Open the Pet-Net backend project in your IDE
3. Create an Env. file in the project root and paste the following variables:
    
    
    | DB_USERNAME=postgres |
    | --- |
    | DB_PASSWORD=password |
    | APP_SECURITY_SECRET_KEY=bWVvd01peDlMaXZlc1doaXNrZXJUd2lzdDQyIVB1cnJNYWNoaW5lUG91bmNlUGF3cw== |
4. Load .env in your environment, then run the app. The backend will start on [http://localhost:8080](http://localhost:8080/).
<br/>

**Frontend**

1. Clone/Open the Pet-Net frontend project in your IDE
2. Install node modules by running the following command in your terminal:
`npm install`
3. Run the app: `npm run dev`, the frontend will start on http://localhost:5173
<br/>

## Users & Roles

**Authentication**

| userId | email | password | role |
| --- | --- | --- | --- |
| 1 | tom@email.com | password | ADMIN |
| 2 | kitty@email.com | password | USER |
| 3 | leo@email.com | password | USER |

**Authorization**

| Feature | permission |
| --- | --- |
| Delete post | Post creator & ADMIN |
