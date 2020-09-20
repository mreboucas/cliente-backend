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

## License
[MIT](https://choosealicense.com/licenses/mit/)