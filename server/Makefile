TAG = ${TAG_NAME}

build:
	./mvnw -Dmaven.test.skip -DskipTests clean package

build-docker:
	./mvnw -Dmaven.test.skip -DskipTests clean package
	docker build -t yggdrasil:${TAG} --label yggdrasil .

publish-docker:
	docker image tag yggdrasil:${TAG} docker.io/nixen/yggdrasil:${TAG}
	docker push nixen/yggdrasil:${TAG}

run:
	java -jar target/server-0.0.1-SNAPSHOT.jar