This is a try to write a small test <b>backend</b> 
application in <b>kotlin</b> using <b>ktor</b> framework 
to create <b>REST API</b> endpoints upon the <b>tomcat</b> 
server.

It has been tested to run on <b>Fedora 34</b> linux 
machine with <b>PostgreSQL</b> database image loaded 
into <b>Docker Compose</b>. 

It also uses <b>JDBC Expose to connect database tables and 
JVM object Entities</b>

To run postgresql database on docker compose container:
<code>

>sudo su

>docker-compose up &

>exit
</code>

To start backend:

run ApplicationKt from IntelliJ IDEa

To test HTTP REST API using curl:

<code>

>./FirstNameTest.http

</code>