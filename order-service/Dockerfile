# 기반 이미지
FROM openjdk:17-ea-11-jdk-slim

# 가상 디렉터리
VOLUME /tmp

# 컨테이너로 복사할 파일 설정
COPY target/order-service-1.0.jar order-service.jar

# 컨테이너에서 실행할 커맨드
ENTRYPOINT ["java", "-jar", "order-service.jar"]