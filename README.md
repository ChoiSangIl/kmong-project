# kmong-project
kmong-proejct

### project 환경
spring boot: 2.6.0  
java: open-jdk-11  
db: h2 in memory  
memory_db: redis(docker)  
api-docs: swagger3.0


### porject 시작
```
git clone https://github.com/ChoiSangIl/kmong-project.git
chmod +x mvnw (리눅스 permission denied가 날경우)

## 방법1.
mvnw clean package
docker-compose build --no-cache
docker-compose up -d

## 방법2.
docker run -d -p 6379:6379 --name kmong_redis redis:latest
mvnw clean install -U
mvnw spring-boot:run

## 방법3.
http://sang12.iptime.org:9000/swagger-ui/index.html
```
### api-docs
http://localhost:9000/swagger-ui/index.html

### h2(in memory db)
http://localhost:9000/h2-console   
Driver Class:org.h2.Driver  
JDBC URL:jdbc:h2:mem:kmong  
User Name:sa  
Password:  
