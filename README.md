# vote-session-manager-api
API criada para o desafio da South System
<br/>
<br/>
Autor: Fernando Meireles
Contato: fernando.meireles.filho@gmail.com
<br/>
<br/>
<br/>
Principais tecnologias utilizadas:
<br/>
Escrito em: Kotlin.
<br/>
Framework utilizado: Spring.
<br/>
Middleware para mensageria: Kafka.
<br/>
Base de dados: MySql.
<br/>
Documentação api: Swagger.
<br/>
Construção de ambiente: Docker e Docker Compose.
<br/>
<br/>
<br/>
Pré-requisitos-ambiente-execução:
<br/>
-Java version 1.8
<br/>
-Docker version 20.10.7 (ou superior)
<br/>
-Docker Compose version 1.25.0 (ou superior)
<br/>
-Portas utilizadas:
<br/>
9095 (app)
<br/>
29092 (kafka)
<br/>
22181 (zookeeper)
<br/>
3306 (mysql)
<br/>
-SO: Distribuições Linux, devido a conteinerização
<br/>
<br/>
<br/>
Arquitetura da solução:
![arquitetura-simplificada](https://user-images.githubusercontent.com/29004741/138739147-aa29aa70-7261-4a9c-8523-76454938b0d5.png)
<br/>
<br/>
<br/>
Observações e decisões gerais:
<br/>
1- Design de código -> A nomenclatura e organização se assemelha ao proposta no clean architecture, porém foi customizada e flexibilizada, pois para que frameworks, controllers, user cases e entities, fossem totalmente desacoplados geraria um enorme esforço, sendo que a solução é de pequeno porte, não possuindo grande cargas de negocios e não sendo planejada para que as camadas de frameworks, drivers, mude.
<br/>
2- Pensando em um ambiente em nuvem foi utilizado containers, a fim de facilitar a construção do ambiente e garantir o correto funcionamento do mesmo.
<br/>
3- Devido a possibilidade de utilizar o spring framework e boot, junto ao kotlin, mantendo os recursos do java e aumentando a produtividade, o mesmo foi utilizado, porém todas as operações realizadas podem ser realizadas no java junto ao spring.
<br/>
4- A base de dados é inicializada via containers, porém suas tabelas são criadas via migração, através da lib flyway ao iniciar a aplicação.
<br/>
5- A aplicação possui testes unitarios, integrados e integrados focados em performance.
<br/>
6- Para questão 1 do projeto foi utilizado o serviço https://gerador.app capaz de gerar e validar cpfs, conforme solicitado.
<br/>
7- Para questão 2 foi utilizado o Kafka como mensageria, apenas um broken simples, sendo que a aplicação apenas produz eventos, não possui consumers.
<br/>
8- Para questão 3 foi utilizado duas estrategias de performance.
<br/>
8.1- Evitar o maximo possível querys complexas, que não utilizem o id(index), como referência. Toda a arquitetura de comunicação com a base de dados foi pensada par ser simples, assim não criando carga-los de escritas ou leitura na applicação.
<br/>
8.2 - Para evitar que milhares de requisições sejam executadas simultaneamente ou em curto espaço de tempo na api foi utilizado a lib Bucket4j, que define capacidade de requisição por minuto, e renovando a capacidade caso não seja utilizada.
<br/>
8.3 - O ideal para performance no endpoint de votos, seria todo o serviço ser separado e a persistência possuir um topico\fila de mensageria em sua frente, assim todo o processo de validação seria assincrono e dividido em "validação se o voto pode ocorrer" e "persistẽncia do voto", pois por meio de um serviço de mensageria seria possível controlar "quando persistir" e caso a aplicação principal caia a outra continuaria no ar, a relação de instancias também seria quebrada e poderia existir em N para N, conforme necessidade de escala. Esta solução não foi desenvolvida, pois provavelmente seria considerada "over engineering"
<br/>
9- O versionamento da api inicialmente pode ser realizada via contexto dos endpoints, exemplo v1/user/create, v2/user/create, porém em grandes escalas usar a versão do endpoint como parametro também pode ser utilizado.
<br/>
<br/>
<br/>
Instruções para execução
<br/>
1-Na raiz do projeto execute no terminal: docker-compose up
<br/>
2-Para separar os logs e facilitar a visualização em outra aba, após os containers estarem up: sh start-api.sh
<br/>
3-Para verificar se a aplicação esta no ar, acesse: http://localhost:9095/actuator/health
<br/>
4-Com a aplicação up, acesse para visualizar os endpoints: http://localhost:9095/swagger-ui.html
<br/>
<br/>
<br/>
Regras implementadas:
<br/>
Usuario:
<br/>
-Ambos endpoints (com e sem cpf) de criação de usuário gerão o mesmo sem precisar de informações.
<br/>
Sessão:
<br/>
-Assuntos inativos ou inexistentes não podem abrir sessão.
Voto:
<br/>
-Apenas usuários ativos.
<br/>
-Voto unico por usuario por sessão.
<br/>
-Sessões fechadas (tempo expirado) não podem ser votadas.
<br/>
-Usuarios não existem não podem votar.
<br/>
-Solicitação maximo por minuto de 900 requisições.
<br/>
Resultado de sessão:
<br/>
-Apenas sessões fechadas (tempo expirado), podem possuir resultado extraido, assim impedindo que resultados incosistentes sejam gerados.
