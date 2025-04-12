APP_NAME=via.easyflow

ENV_FILE=env.yml
.PHONY: start-local

start-local:
	 docker compose up -d --build

.PHONY: clean
clean:
	docker compose down -v
