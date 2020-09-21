#!/bin/bash

echo "Início docker compose"

docker-compose down
docker-compose up -d

echo "Fim docker compose"

