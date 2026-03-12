#Anime Timetable
Anime Timetable is a full-stack web application that allows users to view ongoing seasonal anime and discover new enjoyable anime based on popularity or their favorite genre!
This project integrates the MyAnimeList API and requires an API key to run locally.

##Tech Stack
###Backend
-Java
-Spring Boot
-PostgreSQL
-Maven

###Frontend
-Javascript
-React
-TailwindCSS

###Infrastructure
-AWS EC2
-Docker
-Github Actions

##Project Structure
anime_recommender/ -> backend
anime_frontend/ -> frontend
.github/ -> github CI/CD workflows

##Features
-Browse currently airing anime in a weekly format
-Find information of seasonal anime such as release time, release day, rating, etc.
-Filter anime by genre 
-Cached API responses for largely static files
-Responsive UI

##Running Locally 
###1. Clone the Repository
```bash
git clone https://github.com/Appleseed17/anime-timetable.git
cd anime-timetable
```

###2. Run the backend
```bash
cd anime_recommender
./mvnw spring-boot:run 
```

This runs on:
```
https://localhost:8080
```

###3. Run the frontend 
```bash
cd anime-frontend
npm install
npm start
```

###Docker (Optional)
Build and run containers:
```bash
cd back/frontend
docker compose up --build
```

##Future Improvements
###Technology
-Workflow to automatically copy new Docker images into AWS EC2 instance
-Organize Anime model into separate files
-More sophisticated caching with redis

###Features
-Previous seasons of anime/top ranking past anime
-Possible User accounts + their plan to watch/currently watching shows
-Possible User community features (Polls of weekly favorite anime character/show/etc.)

##Author
Jonathan Morgan Nishimura