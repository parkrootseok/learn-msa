# 기반 이미지
FROM openjdk:17-ea-11-jdk-slim

# 가상 디렉터리
VOLUME /tmp

# 컨테이너로 복사할 파일 설정
COPY target/user-service-1.0.jar user-service.jar

# 컨테이에서 실행할 커맨드
ENTRYPOINT ["java", "-jar", "user-service.jar"]