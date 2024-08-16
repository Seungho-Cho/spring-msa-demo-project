아래는 테스트 코드에 대한 기술 스택을 추가한 `README.md` 파일입니다.

---

# 스프링 마이크로서비스 데모 프로젝트

## 개요

이 프로젝트는 Spring Boot로 구축된 마이크로서비스를 구현하고자 하였으며,  
GitHub Actions을 통한 CI/CD 파이프라인, Docker 등 기존 기술 스택을 정리하고,
Minikube를 이용한 Kubernetes 클러스터 배포와 마이크로서비스 등 신규 기술 습득을 목표로 했습니다.  
또한 마이크로서비스와 상호작용할 수 있는 기본적인 UI도 포함되어 있습니다.

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
│   │   └── ...
│   ├── user-service/                    # 유저 서비스 
│   │   ├── src/                         # 유저 서비스 소스 코드
│   │   ├── build.gradle                 # Gradle 빌드 스크립트
│   │   ├── Dockerfile                   # 서비스의 Docker 컨테이너를 위한 Dockerfile
│   │   ├── inventory-deployment.yaml    # 유저 서비스의 Kubernetes 배포 파일
│   │   └── ...
│   ├── order-service/                   # 주문 서비스
│   │   ├── src/                         # 주문 서비스 소스 코드
│   │   ├── build.gradle                 # Gradle 빌드 스크립트
│   │   ├── Dockerfile                   # 서비스의 Docker 컨테이너를 위한 Dockerfile
│   │   ├── inventory-deployment.yaml    # 주문 서비스의 Kubernetes 배포 파일
│   │   └── ...
│   └── ui/                              # UI 서비스
│       ├── src/                         # UI 소스 코드
│       ├── build.gradle                 # Gradle 빌드 스크립트
│       ├── Dockerfile                   # UI의 Docker 컨테이너를 위한 Dockerfile
│       ├── ui-deployment.yaml           # UI 서비스의 Kubernetes 배포 파일
│       └── ...
└── settings.gradle                      # Gradle 설정 파일
```

## 기술 스택

### 백엔드
- **Java 17**
- **Spring Boot 3.3.2**
- **Spring Data JPA**
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

### 워크플로우 개요
1. **빌드 및 테스트**: 각 마이크로서비스는 Gradle을 사용해 빌드 및 테스트됩니다.
2. **Docker 이미지 생성**: 빌드 및 테스트가 성공하면 Docker 이미지를 생성합니다.
3. **Docker Hub 푸시**: 생성된 Docker 이미지는 Docker Hub에 푸시됩니다.
4. **배포**:
    - 수동으로 트리거될 경우 모든 마이크로서비스가 배포됩니다.
    - 코드 변경으로 트리거될 경우 수정된 마이크로서비스만 재배포됩니다.


## 향후 개선 사항
- OAuth2 또는 JWT를 이용한 보안 강화.
- Amazon EKS 또는 Google Kubernetes Engine(GKE)을 사용한 프로덕션 환경 구축.
- Prometheus, Grafana와 같은 모니터링 구현.
