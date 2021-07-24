# Trial project.
At current stage app allows user to store data for journey and current weather, exctract data for all journies or specific one, update or delete journey.

Requests URL examples:
POST - "{BaseURL}/journeys",
PUT - "{BaseURL}/journeys/{id}",
GET (all records) -"{BaseURL}/journeys",
GET (specific record) -"{BaseURL}/journeys/{id}",
DELETE - "{BaseURL}/journeys/{id}",

Request body examples:
 - for POST:
   {
    "name": "User_80",
    "year": "3000",
    "country": "Minsk,BY",
    "description": "Here we go"
    }
 - for PUT:
   {
    "name": "User_80",
    "country": "Minsk,BY",
    "year": 3000,
    "description": "Here we go",
    "weather": {
        "id": 1,
        "temperature": 11.86,
        "humidity": 75.0,
        "windSpeed": 3.54,
        "windDirection": 293.0
    }
   }


Technologies used during the development:
- JAVA;
- Spring Boot;
- JPA;
- maven;
- H2 database as in-memory DB;
- REST;
- Lombook plugin(needs to be installed in your IDE, otherwise you will not be able to manipulate user Entity object);
- Tomcat;
