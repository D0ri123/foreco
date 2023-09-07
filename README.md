# 🍀 Wiz-backend
  쉬운 분리배출로 더 나은 세상을 만들자. Wiz의 Server를 책임지는 Repository입니다.

## 🚀 지향점
  저희는 ___ 를 목표로 작업을 진행합니다. 
  1. 최적화된 성능
     - JPA 사용 시, 불필요한 쿼리가 발생하지 않도록 꼼꼼한 코드리뷰를 수행합니다.
  2. 가독성 좋은 코드
     - checkstyle을 도입하여, google style guide를 따라 코딩컨벤션을 지킵니다.
     - 어떤 동작을 수행하는 메서드인지 메서드명을 명확히 표현합니다.
     - 주석을 간략히 작성함으로써 협업하는 사람의 이해를 원활히 돕습니다.
  3. 사용자 경험 향상 기능 제공
     - 무중단 배포로 업데이트나 유지보수를 하는 경우에도 서비스 중단을 최소로 합니다.
     - 지속적인 AI 모델 학습을 위해 사용자의 이미지 데이터를 aws DynamoDB에 저장합니다.
  4. 효율적인 프로젝트 관리 및 협업
     - 기능별로 이슈 및 브랜치를 생성하여 협업을 용이하게 합니다.
     - squash & merge 방식으로 커밋을 깔끔하게 관리합니다.

## ✅ 기술스택
![image](https://github.com/foreco-ibaji/Wiz-backend/assets/85207194/7e99937b-29cd-43ce-a7a2-b960a0862df2)

## ✅ 아키텍처
![아키텍처](https://github.com/foreco-ibaji/Wiz-backend/assets/85207194/2bef0f31-c845-4dbd-895f-014e3ce4eada)

## ✅ 패키지 구조 설계
````
├── foreco
│   ├── HealthController.java
│   ├── category
│   ├── common
│   ├── disposal
│   ├── jwt
│   ├── member
│   ├── mission
│   ├── mock
│   ├── region
│   └── trash
├── global
│   ├── exception
│   ├── response
│   ├── security
│   ├── swagger
│   └── util
└── infra
    ├── dynamodb
    ├── feign
    ├── redis
    └── s3
````
패키지 구조는 도메인별로 나눠서 작업을 진행했습니다.
- foreco: 서비스 기능 관련 도메인에 대한 로직들이 있습니다.
- global: 모든 도메인에 공통으로 적용되는 response, request, exception 형식을 지정해놓습니다. 
- infra: 외부 서비스와 통신하는 라이브러리에 관련된 로직들이 있습니다.

## ✅ ERD
![image](https://github.com/foreco-ibaji/Wiz-backend/assets/85207194/cf5327a2-dffb-4ff4-8647-1fcb8ddb33a9)
📌 Category(쓰레기 분류 카테고리) 테이블과 Trash(카테고리에 속하는 세부품목) 테이블을 따로 설계했을 경우, 지역 기반으로 쓰레기 정보를 조회할 때마다 join이 빈번히 일어났습니다.
- 이에 대한 해결방안으로 Category 테이블과 Trash 테이블을 하나의 테이블로 합쳐서 계층 구조 테이블(Trash)로 설계했습니다.
  카테고리 데이터가 저장될 때에는 parent_id가 null이 되고, 쓰레기 품목 데이터가 저장될 때에는 parent_id에 카테고리 데이터의 PK가 저장됩니다.

## 🧑‍💻 향후 계획
1. 새로운 기능 추가를 빠르게 대응하기 위해서 단일 모듈에서 헥사고날 아키텍처를 적용할 예정입니다.
2. 코드의 품질을 확보하기 위해 테스트 코드를 작업할 예정입니다.
3. ci/cd 수행 시간을 단축시켜 빠른 빌드와 배포가 이뤄지도록 최적화할 예정입니다.
4. 멤버 로그인 기능에서 보안을 강화하여 안정적인 서비스를 구축할 예정입니다.

**🔗Wiz 서비스의 다른 소스 코드 확인하기**
> - 🖱️[Wiz-App 레포지토리 가기](https://github.com/foreco-ibaji/Wiz-App-version1)
> - 🖱️[Wiz-AI 레포지토리 가기](https://github.com/foreco-ibaji/Wiz-AI)
