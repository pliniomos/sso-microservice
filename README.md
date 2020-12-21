# Single Sign On Microservice

Desenvolvido como parte do Desafio para ingresso na MV/SA em 2019.

Aplicação para exposição de API RESTFul para geração de token no padrão Oauth2 e autenticação de usuário.

  - Variáveis de configuração por ambiente persistidas na AWS Parameter Store
  - Processo de Build via Maven
  - Parsistência com Hibernate
  - Framework Spring (boot - versão 2.1.4.RELEASE)
  - Servlet Containner Tomcat imbutido na aplicação
  - Java 8
  - JWT como Token
  - Testes Unitários com JUnit
  - As senhas dos usuários cadastrados são persistidas criptografadas
  - Lombok foi utilizado

### Build

A aplicação foi desenvolvida com Spring Boot.

Para realizar o processo de Build e instalar as dependências do projeto, deve-se executar o Maven:

```sh
$ cd sso-microservice
$ mvn clean install
```

Execução da aplicação após Build.

```sh
$ cd sso-microservice
$ cd target
$ java -jar sso-microservice-0.0.1-SNAPSHOT.jar
```
A aplicação será iniciada na porta 8090 do Host:

http://localhost:8091

#### Rotas

| Descrição | Endereço 							|
| --------- | --------------------------------	|
| SignIn    | [http://localhost:8091/signin] 	|
| SignOut   | [http://localhost:8091/signout] 	|
| Authorize | [http://localhost:8091/authorize] |

### Principais Guias, Ferramentas e Bibliotecas utilizados no desenvolvimento	

| Referências |
| ------ |
| https://maven.apache.org/guides/index.html |
| https://spring.io/guides/gs/securing-web/ 
| https://www.baeldung.com/spring-security-custom-filter |
| http://modelmapper.org/getting-started/ |
| https://projectlombok.org/ |
| https://spring.io/guides/gs/authenticating-ldap/ |
| https://spring.io/guides/gs/rest-service/ |
| https://spring.io/guides/gs/serving-web-content/ |
| https://spring.io/guides/gs/accessing-data-rest/ | 
| https://spring.io/guides/gs/accessing-neo4j-data-rest/ |
| https://spring.io/guides/gs/spring-boot-docker/ |
| https://medium.com/@msealvial/validando-requisi%C3%A7%C3%B5es-e-tratando-exce%C3%A7%C3%B5es-no-spring-boot-1750ddb1e1cc |
| https://www.baeldung.com/sha-256-hashing-java |
| https://www.baeldung.com/spring-security-custom-filter |
| http://andreybleme.com/2016-11-27/cors-spring/ |
| http://wiremock.org/docs/getting-started/ |
| https://www.programcreek.com/java-api-examples/?api=org.mockserver.client.server.MockServerClient |

