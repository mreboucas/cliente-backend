# Descrição da API

Api para administrar os clientes, produtos favoritos e consultar os produtos.

## Tecnologias

<ul>

<li>Java 8</li>

<li>Spring boot</li>

<li>Swagger</li>

<li>RXJava</li>

<li>Maven</li>

<li>MongDb</li>

<li>Docker</li>

<li>Lombok</li>

<li>Junit</li>

</ul>

## Maven Comandos - Tests

<h4> Executa todos os testes unitários</h4>
 mvn test

<h4> Executa uma classe de teste específica.</h4>

 mvn -Dtest=ProdutoTest test

 mvn -Dtest=ClienteTest test

## Maven Comandos

<h4> Limpa, compila, roda os testes e empacota o projecto mvn clean package</h4>

mvn clean package

<h4> Limpa, compila e empacota o projecto, mas sem executar os testes</h4>

mvn clean package -DskipTests=true

## Swagger

http://localhost:8090/api/swagger-ui.html

## Checar qualidade do código

plugin sonar lint (eclipse, sts ou vs code)

# Criar a imagem docker
No terminal, no diretório raiz do projeto execute:

```
docker build -t cliente-api .
```

# Testar o Micro Serviço - Local host

<b>1)</b>  Acesse o diretório do projeto: <b>/src/main/resources/scripts</b> para inicializar o mongo


<h4>Execute esse arquivo:</h4>

```
./docker-init.sh 
```

<h4>Depois execute as seguintes linhas de comando no terminal para criar o banco, usuário e collection::</h4>

<i><b>Detalhe:</b> os passos a seguir serão executados apenas uma vez. Execute-os linha-a-linha no terminal.</i>

```
mongo -u user_root -p root admin

use luiza_db

db.createUser({user: "luizalabs_user", pwd: "SSDW/Thtywtu9vDYl0T9WHk9Ujze16WH0LUD9l+/1nuJXyOmMCG/KeZzmtoXoxL9BZ8MVgWf5ZEqj4bg", roles: [{ role: "readWrite", db: "luiza_db" }],mechanisms: ["SCRAM-SHA-1"]});

 
```

2) Subir o projeto

3) Olhar o swagger, pegar os examplos dos JSONS e fazer os devidos testes (postman, insomnia etc);

4) Autenticação do serviço:
