# vote-session-manager-api
<h2>API criada para o desafio da South System</h2>
<br/>
<br/>
Autor: Fernando Meireles
<br/>
Contato: fernando.meireles.filho@gmail.com
<br/>
<br/>
<br/>
<h2>Principais tecnologias utilizadas:</h2>
<br/>
<ul>
<li>1. Escrito em: Kotlin.</li>
<br/>
<li>2. Framework utilizado: Spring.</li>
<br/>
<li>3. Middleware para mensageria: Kafka.</li>
<br/>
<li>4. Base de dados: MySql.</li>
<br/>
<li>5. Documentação api: Swagger.</li>
<br/>
<li>6. Construção de ambiente: Docker e Docker Compose.</li>
</ul>
<br/>
<br/>
<br/>
<h2>Pré-requisitos-ambiente-execução:</h2>
<br/>
<ul>
<li>1. Java version 1.8</li>
<br/>
<li>2. Docker version 20.10.7 (ou superior)</li>
<br/>
<li>3. Docker Compose version 1.25.0 (ou superior)</li>
<br/>
<li>4. Portas utilizadas:</li>
<br/>
  <ul>
  <li>4.1. 9095 (app)</li>
<br/>
 <li>4.2. 29092 (kafka)</li>
<br/>
  <li>4.3. 22181 (zookeeper)</li>
<br/>
 <li> 4.4 3306 (mysql)</li>
 </ul>   
<br/>
<li>5. SO: Distribuições Linux, devido a conteinerização</li>
</ul>
<br/>
<br/>
<br/>
<h2>Arquitetura da solução:</h2>

![arquitetura-simplificada](https://user-images.githubusercontent.com/29004741/138742626-2a702e70-fc18-4b23-996c-c58c2a68b09f.png)


<br/>
<br/>
<br/>
<h2>Observações e decisões gerais:</h2>
<br/>
<ul>
<li>1. Design de código -> A nomenclatura e organização se assemelha ao proposta no clean architecture, porém foi customizada e flexibilizada, pois para que frameworks, controllers, user cases e entities, fossem totalmente desacoplados geraria um enorme esforço, sendo que a solução é de pequeno porte, não possuindo grande cargas de negocios e não sendo planejada para que as camadas de frameworks, drivers, mude.</li>
<br/>
<li>2. Pensando em um ambiente em nuvem foi utilizado containers, a fim de facilitar a construção do ambiente e garantir o correto funcionamento do mesmo.</li>
<br/>
<li>3. Devido a possibilidade de utilizar o spring framework e boot, junto ao kotlin, mantendo os recursos do java e aumentando a produtividade, o mesmo foi utilizado, porém todas as operações realizadas podem ser realizadas no java junto ao spring.</li>
<br/>
<li>4. A base de dados é inicializada via containers, porém suas tabelas são criadas via migração, através da lib flyway ao iniciar a aplicação.</li>
<br/>
<li>5. A aplicação possui testes unitarios, integrados e integrados focados em performance.</li>
<br/>
<li>6. Para questão 1 do projeto foi utilizado o serviço https://gerador.app capaz de gerar e validar cpfs, conforme solicitado.</li>
<br/>
<li>7. Para questão 2 foi utilizado o Kafka como mensageria, apenas um broken simples, sendo que a aplicação apenas produz eventos, não possui consumers.</li>
<br/>
<li>8. Para questão 3 foi utilizado duas estrategias de performance.</li>
<br/><ul>
<li>8.1- Evitar o maximo possível querys complexas, que não utilizem o id(index), como referência. Toda a arquitetura de comunicação com a base de dados foi pensada par ser simples, assim não criando carga-los de escritas ou leitura na applicação.</li>
<br/>
<li>8.2. Para evitar que milhares de requisições sejam executadas simultaneamente ou em curto espaço de tempo na api foi utilizado a lib Bucket4j, que define capacidade de requisição por minuto, e renovando a capacidade caso não seja utilizada.</li>
<br/>
<li>8.3. O ideal para performance no endpoint de votos, seria todo o serviço ser separado e a persistência possuir um topico\fila de mensageria em sua frente, assim todo o processo de validação seria assincrono e dividido em "validação se o voto pode ocorrer" e "persistẽncia do voto", pois por meio de um serviço de mensageria seria possível controlar "quando persistir" e caso a aplicação principal caia a outra continuaria no ar, a relação de instancias também seria quebrada e poderia existir em N para N, conforme necessidade de escala. Esta solução não foi desenvolvida, pois provavelmente seria considerada "over engineering"
</li><br/></ul>
<li>9. O versionamento da api inicialmente pode ser realizada via contexto dos endpoints, exemplo v1/user/create, v2/user/create, porém em grandes escalas usar a versão do endpoint como parametro também pode ser utilizado.</li></li>
</ul>
<br/>
<br/>
<br/>
<h2>Instruções para execução</h2>
<br/><ul>
<li>1. Na raiz do projeto execute no terminal: docker-compose up</li>
<br/>
<li>2. Para separar os logs e facilitar a visualização em outra aba, após os containers estarem up: sh start-api.sh</li>
<br/>
<li>3. Para verificar se a aplicação esta no ar, acesse: http://localhost:9095/actuator/health</li>
<br/>
<li>4. Com a aplicação up, acesse para visualizar os endpoints: http://localhost:9095/swagger-ui.html</li>
<br/></ul>
<br/>
<br/>
<h2>Regras implementadas:</h2>
<br/><ul>
Usuario:
<br/>
-Ambos endpoints (com e sem cpf) de criação de usuário gerão o mesmo sem precisar de informações.
<br/>
Sessão:
<br/>
-Assuntos inativos ou inexistentes não podem abrir sessão.
<br/>
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
</ul>
