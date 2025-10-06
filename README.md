# Exemplo de API Serverless com AWS Lambda

Este projeto demonstra como implementar uma API CRUD serverless utilizando Java, AWS Lambda e API Gateway. O deploy pode ser realizado tanto via [SAM CLI](https://github.com/awslabs/aws-sam-cli) quanto diretamente pelo console web da AWS, facilitando o entendimento para fins didáticos.

## Sumário

- [Pré-requisitos](#pré-requisitos)
- [Passo a passo de implementação](#passo-a-passo-de-implementação)
- [Testando localmente](#testando-localmente)
- [Deploy na AWS via Console Web](#deploy-na-aws-via-console-web)
- [Testando na AWS](#testando-na-aws)
- [Referências](#referências)

## Pré-requisitos

- Conta na AWS
- [Java 11+](https://www.oracle.com/java/technologies/downloads/)
- [Gradle](https://gradle.org/) ou [Maven](https://maven.apache.org/)
- [SAM CLI](https://github.com/awslabs/aws-sam-cli) (opcional para testes locais)
- [AWS CLI](https://aws.amazon.com/cli/) (opcional)

## Passo a passo de implementação

1. **Criação do Projeto**
   - Gere o projeto base usando o archetype serverless da AWS:
     ```bash
     mvn archetype:generate -DartifactId=aws-lambda-example -DarchetypeGroupId=com.amazonaws.serverless.archetypes -DarchetypeArtifactId=aws-serverless-jersey-archetype -DarchetypeVersion=2.0.2 -DgroupId=org.example -Dversion=1.0-SNAPSHOT -Dinteractive=false
     cd aws-lambda-example
     ```

2. **Implementação das Operações CRUD**
   - Crie uma classe de modelo (exemplo: `Pet.java`).
   - Implemente um recurso REST (exemplo: `PetResource.java`) com métodos para:
     - `GET /pets` - Listar todos
     - `GET /pets/{id}` - Buscar por ID
     - `POST /pets` - Criar novo
     - `PUT /pets/{id}` - Atualizar
     - `DELETE /pets/{id}` - Remover

   - Exemplo de método:
     ```java
     @POST
     @Path("/pets")
     public Response createPet(Pet pet) {
         // lógica para salvar o pet
         return Response.status(Response.Status.CREATED).entity(pet).build();
     }
     ```

3. **Configuração do Handler Lambda**
   - Certifique-se que o handler (`StreamLambdaHandler.java`) está configurado para expor os recursos REST.

4. **Build do Projeto**
   - Compile o projeto:
     ```bash
     sam build
     ```

## Testando localmente

- Inicie a API localmente:
  ```bash
  sam local start-api
  ```
- Teste os endpoints usando `curl`:
  ```bash
  curl -X POST http://127.0.0.1:3000/pets -d '{"name":"Rex"}' -H "Content-Type: application/json"
  curl http://127.0.0.1:3000/pets
  ```

## Deploy na AWS via Console Web

1. **Empacote o projeto**
   - Gere o arquivo `.jar` usando Maven ou Gradle.

2. **Console AWS**
   - Acesse o [AWS Lambda](https://console.aws.amazon.com/lambda).
   - Crie uma nova função Lambda:
     - Escolha "Autor do zero".
     - Runtime: Java 11.
     - Faça upload do arquivo `.jar` gerado.
   - Configure o API Gateway:
     - Crie uma nova API REST.
     - Integre os métodos HTTP aos endpoints Lambda correspondentes.
     - Defina os métodos e paths conforme o CRUD.

3. **Permissões**
   - Ajuste as permissões da função Lambda conforme necessário (exemplo: acesso ao DynamoDB, se utilizado).

## Testando na AWS

- Após o deploy, copie a URL gerada pelo API Gateway.
- Teste os endpoints usando `curl` ou Postman:
  ```bash
  curl -X POST https://<api-id>.execute-api.<region>.amazonaws.com/Prod/pets -d '{"name":"Rex"}' -H "Content-Type: application/json"
  curl https://<api-id>.execute-api.<region>.amazonaws.com/Prod/pets
  ```

## Referências

- [AWS Lambda](https://docs.aws.amazon.com/lambda/latest/dg/welcome.html)
- [API Gateway](https://docs.aws.amazon.com/apigateway/latest/developerguide/welcome.html)
- [aws-serverless-java-container](https://github.com/aws/serverless-java-container)

---
> Este projeto é para fins didáticos e pode ser expandido conforme sua necessidade.
