APP_NAME=via.easyflow
VERSION=0.0.1
ENV_FILE=env.yml

.PHONY: build
build:
	export EASYFLOW_IMAGE=$(APP_NAME):$(VERSION); \
	docker compose --profile deploy --env-file $(ENV_FILE) build

.PHONY: start
start:
	EASYFLOW_IMAGE=$(APP_NAME):$(VERSION) docker compose --profile deploy --env-file $(ENV_FILE) up

.PHONY: clean
clean:
	docker compose stop easyflow-blue && docker compose rm easyflow-blue

.PHONY: test
test:
	./gradlew test

.PHONY: download-openapi-generator
download-openapi-generator:
	wget https://repo1.maven.org/maven2/org/openapitools/openapi-generator-cli/7.9.0/openapi-generator-cli-7.9.0.jar -O openapi-generator-cli.jar

.PHONY: openapi-fetch
openapi-fetch:
	chmod +x scripts/manual_update_api_v2.sh
	sh scripts/manual_update_api_v2.sh

.PHONY: openapi-generate
openapi-generate:
	chmod +x ./scripts/delete_generated_files.sh
	bash ./scripts/delete_generated_files.sh
	java -jar openapi-generator-cli.jar generate -g kotlin-spring -i openapi.yaml -t src/main/resources/templates -c openapi-config.json
	chmod +x ./scripts/move_generated_files.sh
	bash ./scripts/move_generated_files.sh
