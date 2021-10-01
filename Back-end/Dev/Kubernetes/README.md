# Kubernetes

## 등장 배경

- **자원 격리 기술 (LINUX)**
    - 사용이 어렵다.
- **가상화 기술 (VM)**
    - VM 가상화를 하기 위해 무거운 OS를 띄워야 한다.  
    가벼운 서비스를 하나 띄우기 위해 이보다 더 큰 OS를 띄워야 하는 경우도 생기게 된다.
- **가상화 기술 (Contianer)**
    - 컨테이너 가상화 기술은 서비스 간 자원 격리를 하는데 OS를 별도로 안 띄워도 되고,  
    OS 기동시간이 없기 때문에 자동화 시 빠르며, 자원 효율도 매우 높다.  
    Docker 자체는 하나의 서비스를 컨테이너로 가상화 시켜 배포를 할 뿐,  
    많은 서비스들을 운영할 때 일일이 배포하고 운영하는 역할을 해주진 않는다.
- **오케스트레이터 (Container)**
    - 컨테이너 오케스트레이터는 여러 컨테이너들을 관리해주는 솔루션이다.
- **클라우드 서비스 (Kubernetes)**

## Kubernetes

- Admin: Kubernetes 클러스터를 운영하는 운영자
- User: Kubernetes 기능을 사용해 자신의 서비스를 배포하는 사용자