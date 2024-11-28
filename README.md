# 커머스
## Tech stack
- Kotlin 1.9.25
- Azul Zulu JDK 21.0.4
- Spring Boot 3.4.0
- MySQL 8.0.40
- Redis 7.2.6

---

## Code Architecture
### Flow
1. ProductApiController, ProductJobConfiguration
   - in adapter
   - DI ProductUseCase
2. ProductUseCase
   - in port
   - interface
3. ProductService
   - impl ProductUseCase
   - DI ProductPersistencePort, ProductCachePort, ...
4. ProductPersistencePort, ProductCachePort, ...
   - out port
   - interface
5. ProductJpaAdapter, ProductRedisAdapter, ...
   - out adapter
   - impl ProductPersistencePort, ProductCachePort, ...
   - DI ProductJpaRepository, ProductRedisRepository, ..
6. ProductJpaRepository, ProductRedisRepository, ...
   - access infrastructure


### Directory
- commerce-common
  - utilities : 공통 유틸리티 (DateUtility, ...)
  - domains
    - product : Product, ProductType (Enum), ...
- commerce-interfaces
  - api
    - adminapi, platformapi
      - apis
        - product : ProductApiController, ProductApiDomains
  - kafka
    - product : ProductKafkaProducer, ProductKafkaDomains
- commerce-service
  - product : ProductUseCase, ProductService
- commerce-infrastructure
  - persistence
    - jpa
      - product : ProductEntity, ProductPersistencePort, ProductJpaAdapter, ProductJpaRepository
  - cache
    - redis
      - product : ProductRedisEntity, ProductCachePort, ProductCacheAdapter, ProductRedisRepository
  - aws
    - utilities : AwsS3Utility, ...
- commerce-batch
  - jobs
    - product : ProductRegisterJobConfiguration

---

## 기능
### 사용자
- 회원가입
  - form
    - 이메일
    - 비밀번호
  - 구글로 회원가입 (TBD)
- 로그인 (TBD)
  - form
    - 이메일
    - 비밀번호
- 로그아웃 (TBD)
- 비밀번호 초기화 (TBD)
  - flow
    1. 이메일 입력 후 submit
    2. 이메일 발송
    3. 수신한 이메일 상의 링크 클릭
    4. 비밀번호 초기화
    5. 초기화된 비밀번호 정보를 이메일로 발송
- 비밀번호 변경 (TBD)
  - form
    - 기존 비밀번호
    - 변경할 비밀번호
    - 변경할 비밀번호 재입력

#### 사용자 > 판매자
- 회원가입
  - 사용자와 동일한 기능에 추가/변경
  - form
    - 셀러명

#### 사용자 > 구매자
- 회원가입
  - 사용자와 동일한 기능에 추가/변경
  - form
    - 이름

### 상품
- 상품 등록하기 (TBD)
  - form
    - 공통
      - 상품명
      - 상품 상세 정보
      - 상품 대표 이미지
      - 상품 상세 이미지 (0 ~ 5개)
    - 가격
      - 정가
      - 할인율 (판매가 자동 책정)
    - 배송
      - 배송사
      - 배송비
      - 산간 배송비
    - 옵션 (n개)
      - 옵션 항목
      - 옵션 값
      - 옵션 추가금액
      - 재고 수량
      - 판매 상태
    - 기타
      - 판매 상태
      - 1, 2, 3차 카테고리

- 상품 수정하기 (TBD)
  - form
    - 공통
      - 상품명
      - 상품 상세 정보
      - 상품 대표 이미지
      - 상품 상세 이미지 (0 ~ 5개)
    - 가격
      - 정가
      - 할인율 (판매가 자동 책정)
    - 배송
      - 배송사
      - 배송비
      - 산간 배송비
    - 기타
      - 판매 상태
    - 옵션 (n개)
      - 옵션 항목
      - 옵션 값
      - 옵션 추가금액
      - 재고 수량
      - 판매 상태

### 구매
- 상품 구매하기 (TBD)
  - flow
    1. 상품 상세 접속
    2. 옵션, 수량을 n개 선택
    3. 주문하기
    4. 주문 정보 입력
    5. 결제 수단 선택 후 주문
        - 무통장입금
          1. 주문완료 상태
          2. 주문자가 입금완료
          3. 결제완료 상태
        - 무통장입금외 (결제까지 이어서 진행)
          1. 주문완료 -> 결제완료 상태로 이어서 진행
    6. 주문 후
        - 구매자 변심
          1. 구매자가 주문을 취소해 주문취소 상태로 변경 (종결)
        - 판매자가 주문을 확인
          - 정상 판매 불가
            1. 정상 판매가 불가함 (재고부족...) 을 확인하고 주문을 취소해 주문취소 상태로 변경 (종결)
          - 정상 판매 가능
            1. 판매자가 물건을 포장 후 송장번호와 함께 배송준비중으로 상태 변경
            2. 배송사에서 상품을 전달받고 배송사의 상태를 배송중으로 변경
            3. 배송사의 상태를 확인하고 커머스의 상태를 배송중으로 연동
            4. 배송사에서 수령자에게 상품을 배송 완료하고 배송사의 상태를 배송완료로 변경
            5. 배송사의 상태를 확인하고 커머스의 상태를 배송완료로 연동
            6. 수령자가 정상 수령
                - 수동 구매확정
                  1. 주문자가 직접 구매확정 상태로 변경 (종결)
                - 자동 구매확정
                  1. 배송완료 후 배송완료 상태로 n일이 경과되면 자동으로 구매확정 상태로 변경 (종결)

  - 환불/교환 flow
    1. (배송준비중 ~ 구매확정) 상태에서 구매자가 환불/교환요청
        - 배송준비중이고 아직 배송사로 상품을 전달하지 않은 상태면
          - 환불
            1. 판매자가 환불요청에서 환불완료로 상태 변경 (종결)
          - 교환
            1. 교환품이 원주문 상품보다 가격이 높으면 고객과 소통으로 (구매자가 판매자에게 환불/교환 배송비를 입금해야하는 경우) 교환배송비 (편도) 와 함께 수기 입금
            2. 판매자가 교환품을 포장 후 송장번호와 함께 교환요청에서 교환배송준비중으로 상태 변경
            3. 배송사에서 교환품을 전달받고 배송사의 상태를 배송중으로 변경
            4. 배송사의 상태를 확인하고 커머스의 상태를 교환배송중으로 연동
            5. 배송사에서 수령자에게 교환품을 배송 완료하고 배송사의 상태를 배송완료로 변경
            6. 배송사의 상태를 확인하고 커머스의 상태를 교환배송완료로 연동
            7. 수령자가 정상 수령
                - 수동 교환완료
                  1. 주문자가 직접 교환완료 상태로 변경 (종결)
                - 자동 교환완료
                  1. 배송완료 후 교환배송완료 상태로 n일이 경과되면 자동으로 교환완료 상태로 변경 (종결)
        - 이미 배송사로 상품을 전달한 상태면
          - 환불
            - 환불 승인
              1. 구매자가 판매자에게 환불/교환 배송비를 입금해야하는 경우 고객과 소통으로 환불배송비 (편도) 수기 입금
              2. 판매자가 배송사를 통해 수거를 요청
              3. 배송사를 통해 상품을 수령 후 판매자가 환불수거완료로 변경
              4. 판매자가 환불배송비를 적용한 금액으로 환불 완료 처리
            - (고객 과실 등의 사유로) 판매자가 환불 거부, 고객과 소통 후 이후 처리
              - 재배송
                1. 배송비 등의 추가 금액은 소통으로 수기 처리, 일반 주문건의 (배송준비중 ~ 구매확정) 절차와 동일하게 진행 (종결)
              - 기타
                1. 환불 금액 등은 소통으로 수기 처리, 환불 승인 절차와 동일하게 환불 완료 (종결)
          - 교환
              - 교환 승인
                1. 교환품이 원주문 상품보다 가격이 높으면 고객과 소통으로 (구매자가 판매자에게 환불/교환 배송비를 입금해야하는 경우) 교환배송비 (왕복) 와 함꼐 수기 입금
                2. 판매자가 배송사를 통해 수거를 요청
                3. 배송사를 통해 상품을 수령 후 판매자가 교환수거완료로 변경
                4. 판매자가 교환품을 포장 후 송장번호와 함께 교환수거완료에서 교환배송준비중으로 상태 변경
                5. 배송사에서 교환품을 전달받고 배송사의 상태를 배송중으로 변경
                6. 배송사의 상태를 확인하고 커머스의 상태를 교환배송중으로 연동
                7. 배송사에서 수령자에게 교환품을 배송 완료하고 배송사의 상태를 배송완료로 변경
                8. 배송사의 상태를 확인하고 커머스의 상태를 교환배송완료로 연동
                9. 수령자가 정상 수령
                    - 수동 교환완료
                      1. 주문자가 직접 교환완료 상태로 변경 (종결)
                    - 자동 교환완료
                      1. 배송완료 후 교환배송완료 상태로 n일이 경과되면 자동으로 교환완료 상태로 변경 (종결)
            - (고객 과실 등의 사유로) 판매자가 교환 거부, 고객과 소통 후 이후 처리
              - 재배송
                1. 배송비 등의 추가 금액은 소통으로 수기 처리, 일반 주문건의 (배송준비중 ~ 구매확정) 절차와 동일하게 진행 (종결)
              - 기타
                1. 환불 금액 등은 소통으로 수기 처리, 환불 승인 절차와 동일하게 환불 완료 (종결)
    2. 재 환불/교환
      - 기존 환불/교환 주문건이 환불/교환 완료된 후에 가능
      - 환불/교환완료 -> 환불/교환요청으로 상태 변경 후 이후 동일하게 진행

---

## 도메인
### 사용자
- attribute
  - 이메일
  - 비밀번호
  - 이름
  - 상태 (정상)

#### 사용자 > 판매자
- relationship
  - : 사용자 = 1 : 1

#### 사용자 > 구매자
- relationship
  - : 사용자 = 1 : 1

#### 사용자 > 구매자 > 배송지
- relationship
  - : 구매자 = N : 1
  - : 주문 = 1 : N
- attribute
  - 수령자명
  - 주소
  - 상세주소
  - 우편번호
  - 전화번호1
  - 전화번호2
  - 기본 배송 메모
  - 기본 배송지 여부

### 상품 카테고리
- relationship
  - : 상품 카테고리 = N : M
  - : 상품 = 1 : N
- attribute
  - 상위 카테고리
  - depth
  - 카테고리명

### 상품
- relationship
  - : 판매자 = N : 1
- attribute
  - 공통
    - 상품명
    - 상품 상세 정보
    - 상품 대표 이미지
    - 상품 상세 이미지 (n개)
    - 3차 카테고리
  - 가격
    - 정가
    - 할인율
    - 판매가
  - 배송
    - 배송사
    - 배송비
    - 산간 배송비
  - 기타
    - 판매 상태 (판매중, 미판매)
    - 옵션 타입 (옵션없음, 옵션있음)

#### 옵션 항목
- attribute
  - 옵션 항목명 : 색상, 사이즈, ...

#### 옵션 값
- attribute
  - 옵션 값명 : red, XL, ...

#### 옵션
- relationship
  - : 상품 = N : 1
  - : 옵션 항목 = N : M
  - : 옵션 값 : N : M
- attribute
  - 옵션 추가금액
  - 재고
    - 재고 수량
    - 재고 상태 (재고있음, 품절)
  - 기타
    - 판매 상태 (판매중, 미판매)

### 주문
- relationship
  - : 구매자 = N : 1
  - : 옵션 = N : M
  - : 배송지 = N : 1
- attribute
  - 구매 옵션 (n개)
    - 주문 갯수
  - 배송
    - 배송 메모
    - 송장번호
  - 주문자
    - 주문자명
    - 전화번호
    - 이메일
  - 주문 상태
    - 주문완료 : 주문 완료 후 결제 전
    - 결제완료 : 결제 완료
    - 배송준비중 : 판매자가 주문을 확인하고 배송을 준비중
    - 배송중 : 배송사에서 배송중
    - 배송완료 : 수령자에게 배송이 완료
    - 구매확정 : 주문자가 구매를 확정
    - 주문취소 : 구매자/판매자가 주문을 취소함
    - 환불요청 : 주문자가 환불을 요청
    - 환불수거완료 : 구매 상품을 판매자가 수거 완료함
    - 환불완료 : 환불 완료됨
    - 교환요청 : 주문자가 교환을 요청
    - 교환수거완료 : 구매 상품을 판매자가 수거 완료함
    - 교환배송준비중 : 판매자가 교환품의 배송을 준비중
    - 교환배송중 : 교환품을 배송사에서 배송중
    - 교환배송완료 : 교환품의 배송이 완료
    - 교환완료 : 교환 완료됨

### 결제

### 장바구니

### 리뷰

### 쿠폰
