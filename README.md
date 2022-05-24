# kmong-project
kmong-proejct

### project 환경
spring boot: 2.6.0  
java: open-jdk-11  
db: h2 in memory  
memory_db: redis(docker)  
api-docs: swagger3.0
#### 사용포트
Spring Boot Application : 9000 port  
docker Reids : 6379 port


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

### project test
1. Api-doc Swagger 접속 (http://localhost:9000/swagger-ui/index.html)
2. 인증관련 회원가입 API 호출(/api/v1/auth)
3. 이미 회원 가입 했다면 로그인 API 호출 (/api/v1/auth/login)
#### Responses...
```
{
  "id": 1,
  "email": {
    "value": "test@kmong.co.kr"
  },
  "jwtToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGttb25nLmNvLmtyIiwiaWF0IjoxNjUzMzczNzgzLCJleHAiOjE2NTMzNzQwODN9.oQ-qn9r3c4nzsWy8iwCh9hSUhYwqnvzpJIaFDKRF_5E"
}
```
4. 응답값에서 jwtToken값을 오른쪽 상단 Authorize 버튼을 클릭하여 입력
5. 상품조회, 상품주문, 회원주문 내역 조회, 로그아웃 순으로 API 호출
