## Halloween Costume Recommender App
The project aims to create an interactive web application that recommends Halloween costumes based on user preferences and real-time weather conditions.

## How It's Made:

**Tech used:**

***Frontend stack:*** Next.js, React, TypeScript, TailwindCSS, Next Font API, Next/Image, Fetch API, CSS files, Client Components

***Backend stack:*** Java, SpringBoot, MySQL, H2, Maven, JUnit, Spring Test, MockMvc, Scheduled Tasks, Static File Storage

This project is fully containerized and requires Docker and Docker Compose to run.

Make sure Docker and Docker Compose are installed on your machine before starting the application.

**Running the Project**

1. Start the application using Docker Compose:
```
  docker-compose up -d
```
2. Frontend should start on localhost: 3000
3. After the finishing program, following script cleans up containers, volumes and images:
```
    sh clean-up.sh
```

