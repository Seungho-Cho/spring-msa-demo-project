---

# 스프링 마이크로서비스 데모 프로젝트

## 데모 서버
 - **LOCAL (dev)**: http://175.214.62.12:8081/
 - **LOCAL (Kubernetes)**: http://175.214.62.12:30001/
 - ~~**EC2 (minikube)**: http://3.34.126.6:8081/~~
 - **Docker Hub**: https://hub.docker.com/u/csh628

## 개요

이 프로젝트는 Spring Boot로 구축된 마이크로서비스를 구현하고자 하였으며,  
GitHub Actions을 통한 CI/CD 파이프라인, Docker 등 기존 기술 스택을 정리하고,

Minikube를 활용한 Kubernetes 클러스터 배포와 마이크로서비스, GraphQL 등 신규 기술 습득을 목표로 했습니다.  
또한 Thymeleaf 구현한 기본적인 UI 서비스도 포함되어 있습니다.

## 프로젝트 구조

```
spring-demo-project/
├── .github/workflows/ci-cd.yml          # GitHub Actions CI/CD 파이프라인
├── services/
│   ├── inventory-service/               # 인벤토리 서비스
│   │   ├── src/                         # 인벤토리 서비스 소스 코드
│   │   ├── build.gradle                 # Gradle 빌드 스크립트
│   │   ├── Dockerfile                   # 서비스의 Docker 컨테이너를 위한 Dockerfile
│   │   ├── inventory-deployment.yaml    # 인벤토리 서비스의 Kubernetes 배포 파일
│   │   ├── inventory-service.yaml       # 인벤토리 서비스의 Kubernetes 서비스 파일
│   │   └── ...
│   ├── user-service/                    # 유저 서비스 
│   │   ├── src/                         # 유저 서비스 소스 코드
│   │   ├── build.gradle                 # Gradle 빌드 스크립트
│   │   ├── Dockerfile                   # 서비스의 Docker 컨테이너를 위한 Dockerfile
│   │   ├── user-deployment.yaml         # 유저 서비스의 Kubernetes 배포 파일
│   │   ├── user-service.yaml            # 유저 서비스의 Kubernetes 서비스 파일
│   │   └── ...
│   ├── order-service/                   # 주문 서비스 
│   │   ├── src/                         # 주문 서비스 소스 코드
│   │   ├── build.gradle                 # Gradle 빌드 스크립트
│   │   ├── Dockerfile                   # 서비스의 Docker 컨테이너를 위한 Dockerfile
│   │   ├── order-deployment.yaml        # 주문 서비스의 Kubernetes 배포 파일
│   │   ├── order-service.yaml           # 주문 서비스의 Kubernetes 서비스 파일
│   │   └── ...
│   ├── shipping-service/                # 출고 서비스
│   │   ├── src/                         # 출고 서비스 소스 코드
│   │   ├── build.gradle                 # Gradle 빌드 스크립트
│   │   ├── Dockerfile                   # 서비스의 Docker 컨테이너를 위한 Dockerfile
│   │   ├── shipping-deployment.yaml     # 출고 서비스의 Kubernetes 배포 파일
│   │   ├── shipping-service.yaml        # 출고 서비스의 Kubernetes 서비스 파일
│   │   └── ...
│   └── ui/                              # UI 서비스
│       ├── src/                         # UI 소스 코드
│       ├── build.gradle                 # Gradle 빌드 스크립트
│       ├── Dockerfile                   # UI의 Docker 컨테이너를 위한 Dockerfile
│       ├── ui-deployment.yaml           # UI 서비스의 Kubernetes 배포 파일
│       ├── ui-service.yaml              # UI 서비스의 Kubernetes 서비스 파일
│       └── ...
└── settings.gradle                      # Gradle 설정 파일
```

<img width="800" alt="화면 캡처 2024-08-18 031235" src="https://github.com/user-attachments/assets/2ee5a235-4424-4558-9e0e-0c529ce8cd7e">


## 기술 스택

### 개발환경
- **IntelliJ IDEA**
- **SonarLint**: 코드 품질 관리

### 백엔드
- **Java 17**
- **Spring Boot 3.3.2**
- **Spring Data JPA**
- **GraphQL**: 상품 검색 서비스 구현
- **AWS SQS**: 주문/출고 서비스간 메시지 큐 구현
- **H2 Database**: 테스트를 위한 인메모리 데이터베이스
- **MySQL (AWS RDS)**: 배포 환경에서는 AWS RDS의 MySQL을 사용

### 프론트엔드
- **Thymeleaf**

### 테스트
- **JUnit 5**
- **Mockito**
- **H2 Database**

### DevOps & CI/CD
- **Docker**
- **GitHub Actions**
- **Docker Hub**
- **Kubernetes (Minikube)**

### 배포
- **AWS EC2**: Minikube 호스팅
- **AWS RDS (MySQL)**: 배포 환경에서의 데이터베이스

## CI/CD 파이프라인

CI/CD 파이프라인은 GitHub Actions를 사용하여 구현되었습니다.

### Git Actions 워크플로우 
https://github.com/Seungho-Cho/spring-msa-demo-project/blob/main/.github/workflows/ci-cd.yml
1. **빌드 및 테스트**: 각 마이크로서비스는 Gradle을 사용해 빌드 및 테스트됩니다.
2. **Docker 이미지 생성**: 빌드 및 테스트가 성공하면 Docker 이미지를 생성합니다.
3. **Docker Hub 푸시**: 생성된 Docker 이미지는 Docker Hub에 푸시됩니다.
4. **배포**:
    - 수동으로 트리거될 경우 모든 마이크로서비스가 배포됩니다.
    - 코드 변경으로 트리거될 경우 수정된 마이크로서비스만 재배포됩니다.


## 향후 개선 사항
- Spring Cloud config를 사용한 config 중앙화
- OAuth2 또는 JWT와 API Gateway 사용한 보안 강화.
- Amazon EKS 또는 Google Kubernetes Engine(GKE)을 사용한 프로덕션 환경 구축.
- Prometheus, Grafana와 같은 모니터링 구현.
