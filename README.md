# ecommerce
셀러와 구매자 사이를 중개하는 커머스 서버를 구축하여 온라인 상품 판매 및 구매가 가능한 서비스입니다.

# 프로젝트 기능 및 설계

## 회원 기능 (공통)
- [ ] 회원 가입: 모든 사용자(고객, 셀러)는 고유한 아이디와 비밀번호를 사용하여 회원 가입할 수 있음
- [ ] 로그인: 사용자는 시스템에 로그인할 수 있음

## 고객 기능
- [ ] 상품 검색: 상품명을 통해 상품을 검색하고 조회할 수 있음
- [ ] 장바구니: 상품을 장바구니에 추가 및 확인이 가능하며, 장바구니 상품을 주문할 수 있음
- [ ] 주문 및 결제: 주문할 상품의 수량을 선택 후 결제를 진행할 수 있음

## 셀러 기능
- [ ] 스토어 관리: 스토어 이름 입력, 상품 등록, 상품 수정/삭제, 재고 관리 등의 기능

## 상품 관리
- [ ] 상품 등록: 셀러는 새로운 상품을 시스템에 등록 가능하며 각 상품에 대해 상품명, 카테고리, 가격, 재고 수량 정보를 입력해야 함
- [ ] 상품 수정/삭제: 셀러는 등록한 상품을 수정 및 삭제 가능
- [ ] 재고 관리: 셀러는 상품의 재고 수량을 관리할 수 있으며, 재고가 소진되면 추가하거나 상품을 내릴 수 있음

## 제약 사항
- [ ] 주문시에 재고가 부족한 경우 주문을 할 수 없다

## ERD (보완 필요)
![ecproject_erd](https://github.com/now1j/ecproject/assets/149587520/8d30aea5-b7f5-4d49-a70f-fc274ae291e9)

# 사용 기술
Java, Spring, MySql, Git
