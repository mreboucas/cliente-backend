# Descrição da API

Api Rest responsável por: <br>
<b>1) administrar os clientes e produtos favoritos;</b><br>
<b>2) Consultar a listagem de produtos por id e página;</b><br>
<b>3) Inserir usuários de autenticação.</b><br>

O tópico que possui os passos para o serviço ser testado é: <b>https://github.com/mreboucas/cliente-backend#testar-o-micro-servi%C3%A7o---local-host </b> que está abaixo.

## Tecnologias

<ul>

<li>Java 8</li>

<li>Spring boot</li>

<li>Swagger</li>

<li>RXJava</li>

<li>Maven</li>

<li>MongDb</li>

<li>Docker</li>

<li>Kuberntes/Minikube</li>

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

4) Autenticação do serviço (Basic Autentication):

<h3>Administrador</h3>

```
user: admin
passwor: admin123


```

<h3>Usuário padrão</h3>

```
user: user
passwor: user123


```

<b>Obs.:</b> vc pode verificar mais detalhes na collection 'user_auth' do mongo ou até mesmo criar outros pelo end-point que está documentado no swagger.


# Deployar no kubernets

<h4>É importante que as etapas a seguir sejam realizadas todas no mesmo terminal</h4>

<h3> Inicializar minikube</h3>

```
minikube start
```

<h3> Setar docker env (permitir o kubernets acessar/visualizar as imagens docker)</h3>

```
eval $(minikube docker-env)
```

<h3>Buildar a imagem no docker</h3>

```
docker build -t cliente-api-luizalabs .
```

<h3>Deployar a imagem docker no minikube</h3>

Acesse o diretório resource do projeto (src/main/resources) e execute:

```
kubectl apply -f deployment.yml
```

<h3> Checar o deploy</h3>

```
kubectl get deployments
```

<h3> Checar os pods</h3>

```
kubectl get pods
```
