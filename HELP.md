
podman pull ankane/pgvector

podman run -e POSTGRES_USER=4real -e POSTGRES_PASSWORD=4real -e POSTGRES_DB=springai --name my_springai -p 5432:5432 -d ankane/pgvector

podman exec -it my_springai /bin/bash

psql -h localhost -U 4real -d springai -p 5432

CREATE EXTENSION vector;

SELECT * FROM pg_extension;)
