# Car Loan 

Car Loan é um projeto exemplo (em breve terá um tutorial \o/ ), que implementa uma arquitetura de microserviços, utilizando Spring Boot, Spring Cloud, Kubernetes e Istio Service Mesh.

O proposito dele é exemplificar como definimos e desenhamos toda a arquitetura de MS no Banco BV.

## Serviços Funcionais

Para eleger os candidatos a microserviços, utilizamos o método Event Storming proposto por Alberto Brandolini...

[imagem da estrutura de serviços]

### Proposal Service

Serviço de proposta de financiamento, responsavel por listar os paremetros de taxas, prazos e cadastrar simulações de financiamento.

#### Eventos

| Tipo      | Recurso  | Nome                                  | Binding | Utilização                                                                              |
|-----------|----------|---------------------------------------|---------|-----------------------------------------------------------------------------------------|
| Listener  | Queue    | update.proposal.vehicle-market-value  | N/A     | Replica no serviço os dados de valores de mercado dos veiculos cadastrados no sistema   |
|-----------|----------|---------------------------------------|---------|-----------------------------------------------------------------------------------------|
| Producer  | Exchange | event.proposal.saved                  | N/A     | Notifica a criação de uma proposta de financiamento                                     |
|-----------|----------|---------------------------------------|---------|-----------------------------------------------------------------------------------------|

#### APIs


| ID | Method | Path              | Description                                          |
|----|--------|-------------------|------------------------------------------------------|
| 01 | POST   | /v1/proposal      | Cadastra a proposta de financiamento			     |
|----|--------|-------------------|------------------------------------------------------|
| 02 | POST   | /v1/proposal/loan | Recupera os parametros para calculo de financiamento |
|----|--------|-------------------|------------------------------------------------------|


Payload Exemplo = COLOCAR O PAYLOAD NO POSTMAN DE EXEMPLO

##### 01 - POST /v1/proposal

´´´´
{
  "vehicleVersionId": "7265276f-3dab-4840-bbcf-ba385db6676d",
  "loan": {
    "tax": 0.06777,
    "value": 85955,
    "monthlyInstallment": 3333.33,
    "downPayment": 45955,
    "paymentTerm": 12
  },
  "proponent": {
    "identity": "12345678909",
    "name": "João Francisco",
    "email": "email@provider.com"
  },
  "location": {
    "city": "São Paulo",
    "state": "SP"
  }
}
´´´´


##### 01 - POST /v1/proposal/loan

´´´´
{
  "vehicleVersionId": "7265276f-3dab-4840-bbcf-ba385db6676d",
  "proponentIdentity": "12345678909",
  "location": {
    "city": "São Paulo",
    "state": "SP"
  }
}
´´´´

### Notification Service

Serviço para envio de notificações, utilizado para envio de e-mails ao cliente sobre o status de uma proposta de financiamento.

#### Eventos

| Tipo      | Recurso  | Nome                                  | Binding | Utilização                                                        |
|-----------|----------|---------------------------------------|---------|-------------------------------------------------------------------|
| Listener  | Queue    | create.notification.proposal-received | N/A     | Comando para envio de e-mail informando a recepção da proposta.   |
|-----------|----------|---------------------------------------|---------|-------------------------------------------------------------------|


#### APIs

N/A

### Consumers Service

Serviço para gestão de clientes, possui todas as informações necesárias de um cliente que já tenha submetido uma proposta de financiamento.

#### Eventos

| Tipo      | Recurso  | Nome                                  | Binding | Utilização                                                           |
|-----------|----------|---------------------------------------|---------|----------------------------------------------------------------------|
| Listener  | Queue    | create.consumer                       | N/A     | Comando para cadastro de clientes depois da criação de um proposta.  |
|-----------|----------|---------------------------------------|---------|----------------------------------------------------------------------|


#### APIs

N/A


### Locales Service

Serviço utilizado para listagem de Estados e Cidades, os dados são providos de um sistema externo (atualmente IBGE).

#### Eventos

N/A

#### APIs

TODO

### Vehicles Service

Serviço para gestão dos dados de veiculos disponiveis para compra.

#### Eventos

| Tipo      | Recurso  | Nome                                  | Binding | Utilização                                           |
|-----------|----------|---------------------------------------|---------|------------------------------------------------------|
| Producer  | Exchange | event.vehicle.created                 | N/A     | Notifica o cadastro de um veiculo no sistema         |
|-----------|----------|---------------------------------------|---------|------------------------------------------------------|

#### APIs

TODO

## Infraestrutura

Para nossa infraestrutura defini alguns pré-requisitos, uma vez definidos analisei quais ferramentas atenderiam de forma mais leve possivel, possibilitando a execução de todo o sistema em um Notebook ou PC domestico.

### Requisitos

Os requisitos listados abaixo representam a execução de todo o sistema incluindo plataforma de execução, mesh e serviços.

**OS**: Windows, Linux ou Mac
**Memory**: 8Gb
**CPU**: 2cores 

### Ferramentas e Serviços

#### Kubernetes

#### Istio Service Mesh

Estamos utilizando o service mesh Istio, as features que estamos utilizando são: Virtual Service, Destination Rule,...

#### RabbitMQ

Estamos utilizando o RabbitMQ como broker de mensageria.

Vamos utilizar lazy-queues, para manter as mensagens em disco, com isso nós temos um menor consumo de memõria em detrimento ao troughput (a analise técnica da solução estará disponivel no tutorial).

Cada serviço que utilizar o RabbitMQ terá seu proprio usuario, assim conseguimos um controle de autorização mais granular, sobre quem acessa quais recursos.

Hoje temos as seguintes definições de filas e exchanges:

| Exchange              | Tipo   | Queue                                 | Binding | Utilização                                                                                     |
|-----------------------|--------|---------------------------------------|---------|------------------------------------------------------------------------------------------------|
| event.vehicle.created | Fanout | update.proposal.vehicle-market-value  | N/A     | Para o evento de criação de veiculo, replica os valores de mercado deste veiculo               |
|-----------------------|--------|---------------------------------------|---------|------------------------------------------------------------------------------------------------|
| event.proposal.saved  | Fanout | create.consumer                       | N/A     | Para o evento de cadastro de proposta cadastra o novo usuario no sistema.                      |
|-----------------------|--------|---------------------------------------|---------|------------------------------------------------------------------------------------------------|
| event.proposal.saved  | Fanout | create.notification.proposal-received | N/A     | Para o evento de cadastro de proposta envia uma notificação (via e-mail) de proposta recebida. |
|-----------------------|--------|---------------------------------------|---------|------------------------------------------------------------------------------------------------|
| error.all             | Fanout | error.all                             | N/A     | DLX e DLQ genéricas                                                                            |
|-----------------------|--------|---------------------------------------|---------|------------------------------------------------------------------------------------------------|

#### PostgreSQL

## Executando

Para executar todo o sistema, siga os passos abaixos de acordo com o local e a forma como serã executado:

### Docker (local sem istio)

#### RabbitMQ

Iniciar o RabbitMQ:

´´´´
docker run -d -m 300mb -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:management-alpine
´´´´

Importe o arquivo rabbitmq-definitions.json:

´´´´
	1. Acesse o console do RabbitMQ em http://localhost:5672
	2. Faça login com as credenciais: guest/guest
	3. Na pagina Overview, procure a opção Import Definitions.
	4. Selecione o arquivo (pasta resources/rabbitmq do repositório) e clique em Upload broker Definitions
´´´´


#### PostgreSQL

Iniciar o PostgreSQL

´´´´
docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=root -m 150mb --name postgresql postgres:10-alpine
´´´´

Execute os comandos em resources/postgres-init.sql:

´´´´
	1. No terminal digite: docker exec -ti postgresql bash
	2. Faça login no postgresql com: psql -U postgres -W (quando solicitada a senha digite root).
	3. Copie todos os comandos do arquivo postgres-init.sql e cole no terminal.
´´´´


#### Services

**Obs.:** A ordem da inicialização não é importante, uma vez que os serviços são 100% autonomos e resilientes.

Notification

´´´´
docker run -d --name notifications -m 180mb -p 9095:9090 --link rabbitmq:rabbitmq -e SPRING_RABBITMQ_HOST=rabbitmq cjcalmeida/car-loan-notifications
´´´´

Consumers

´´´´
docker run -d --name consumers -m 180mb -p 9100:9090 --link rabbitmq:rabbitmq -e SPRING_RABBITMQ_HOST=rabbitmq cjcalmeida/car-loan-consumers
´´´´

Proposal

´´´´
docker run -d --name proposals -m 180mb -p 8085:8080 -p 9105:9090 --link rabbitmq:rabbitmq -e SPRING_RABBITMQ_HOST=rabbitmq -e SPRING_LIQUIBASE_USER= -e SPRING_LIQUIBASE_PASSWORD= -e SPRING_DATASOURCE_PASSWORD= -e SPRING_DATASOURCE_USERNAME= -e SPRING_PROFILES_ACTIVE=cloud cjcalmeida/car-loan-proposals
´´´´


### Docker Compose (local sem istio)


### Minikube + Istio Mesh (local)

### GKE + Istio Mesh (cloud)

## Convenções e Nomenclaturas

#### Convenção para filas e exchanges:

Definicao Exchange:

Formato: **tipo.dominio.acao**


Definicao Queues

Formato: **acao.dominio.sub-dominio**


Campo       | Valores                    | Obrigatório |
------------|----------------------------|-------------|
tipo        | event / command / error    | Sim         |
------------|----------------------------|-------------|
dominio     | Dominio emissor do evento  | Sim         |
------------|----------------------------|-------------|
sub-dominio | Sub dominio afetado        | Não         |
------------|----------------------------|-------------|
ação        | Ver tabela de ações abaixo | Não         |
------------|----------------------------|-------------|



Tabela de ações

Ação    | Descrição                                      | Exemplo                            |
--------|------------------------------------------------|------------------------------------|
event   | Ação ocorrida  (verbo no passado)              | criado, excluido, atualizado       |
--------|------------------------------------------------|------------------------------------|
command | Operação a ser executada (verbo no infinitivo) | cadastrar, excluir, listar, buscar | 
--------|------------------------------------------------|------------------------------------|
error   | Erro que deve ser tratado                      | not-found, already-exists          |
--------|------------------------------------------------|------------------------------------|