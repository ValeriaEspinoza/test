# crud-users
This microservice create a systems users. 
Operions: Create,Read,Update,Delete

# DataBase
Use mongo db in docker, not use emblemed mongoDb.

# Docker MongoDb Utilities
1. Install Docker.
2. docker pull mongo -> download Image MongoDb.
3. docker image ls -> to show the image downloaded. 
4. docker run -d -p 27017-27019:27017-27019 --name mongodb mongo:latest -> Create a container from your image. 
5. docker ps -a for show all containers status.
6. docker start CONTAINER_ID -> for start your container (stop,and restart) 
7. docker exec -it mongodb bash to open the mongo shell

Utilis:
docker exec -it mongodb bash open mongo console. 
mongo -> run instance.
show dbs 
use lakatuna for the katana db.
