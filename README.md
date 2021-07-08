# READ ME

|Member Name | Student # | % worked |
|------------|:---------:|---------:|
|James Kirwan| ------- | 33% |
|Caoimhe Tiernan| ----- | 33%|
| Martynas Jagutis| ----- | 33% |
## SQL Setup

1. In your MySQL server,  create a database for this project ( the one we used was called 'sse')
```SQL
CREATE DATABASE sse;
```
2. Then create a user with a password for the database, we used the name java
```SQL
CREATE USER 'java'@'localhost' IDENTIFIED BY 'password';
```
3. Give them permissions for the database.
```SQL
GRANT ALL ON sse.* TO 'java'@'localhost' IDENTIFIED BY 'password';
```
4. In src/main/resources/application.properties file : <br />
change spring.datasource.url=" " to source your database
```
spring.datasource.url=jdbc:mysql://localhost:3306/sse
```
5. Set the username and password to the ones you created earlier
```
spring.datasource.username=java
spring.datasource.password=password
```

6. Lastly, load the schema and data from the CLI
```SQL
mysql -u java -p sse < src/main/resources/schema.sql
mysql -u java -p sse < src/main/resources/data.sql
```

# TO RUN THE PROJECT
In the root directory of the project, run:
```mvn
mvn spring-boot:run
```


