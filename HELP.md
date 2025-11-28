https://medium.com/@adarsh.ajay/setting-up-postgresql-with-pgvector-in-docker-a-step-by-step-guide-d4203f6456bd


podman pull ankane/pgvector

podman run -e POSTGRES_USER=4real -e POSTGRES_PASSWORD=4real -e POSTGRES_DB=springai --name my_springai -p 5432:5432 -d ankane/pgvector

podman exec -it my_springai /bin/bash

psql -h localhost -U 4real -d springai -p 5432

CREATE EXTENSION vector;

SELECT * FROM pg_extension;)



https://hemanthcse1.medium.com/integrating-postgresql-with-docker-in-your-spring-boot-project-869049a821d2


~docker run --name postgres-spring -e POSTGRES_PASSWORD=mypassword -e POSTGRES_USER=myuser -e POSTGRES_DB=mydatabase -p 5432:5432 -d postgres

spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
spring.datasource.username=myuser
spring.datasource.password=mypassword

~docker exec -it postgres-spring psql -U myuser -d mydatabase

~\dt

