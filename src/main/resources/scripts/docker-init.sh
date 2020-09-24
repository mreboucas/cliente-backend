#!/bin/bash

echo "################### INÍCIO DOCKER COMPOSE #############################"

docker-compose down
docker-compose up -d

echo "################### FIM DOCKER COMPOSE #############################"

echo "-------------------------------------------------------------------------------------------"
echo ">>>>>>>>>>>> PRONTO, MONGO RODANDO -> AGORA VOCÊ PODERÁ TESTAR O MICROSERVIÇO <<<<<<<<<<<<<"
echo "-------------------------------------------------------------------------------------------"


