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
mvnw clean package
docker-compose build --no-cache
docker-compose up -d

or

docker run -d redis:latest --name kmong_redis -p 6379:6379
mvnw clean install -U
mvnw spring-boot:run
```

### api-docs
http://localhost:9000/swagger-ui/index.html
