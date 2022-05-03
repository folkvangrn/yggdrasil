build:
	./mvnw clean package

build-docker:
	./mvnw clean package
	docker build -t yggdrasil:0.1 --label yggdrasil .

publish-docker:
	docker image tag yggdrasil:0.1 docker.io/nixen/yggdrasil:0.1
	docker push nixen/yggdrasil:0.1

run:
	java -jar target/server-0.0.1-SNAPSHOT.jar