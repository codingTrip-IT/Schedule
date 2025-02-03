## 👉🏼 일정관리(Schedule) 개인과제

### 🙋‍♀️ 일정관리(Schedule) 과제 설명
- **일정관리(Schedule) 과제**는 **<u>Spring을 활용하여 일정 관리 앱을 구현</u>** 한 과제입니다.
- **작성자 : 조예인**


## 🛠 목차

1. [👩🏻‍🏫 API 명세](#-API-명세)
2. [👩🏻‍🏫 ERD](#-ERD)
1. [👩🏻‍🏫 레벨별 설명](#-레벨별-설명)
    - [Lv 1](#Lv-1)
    - [Lv 2](#Lv-2)
    - [Lv 3](#Lv-3)
    - [Lv 4](#Lv-4)
    - [Lv 5](#Lv-5)
    - [Lv 6](#Lv-6)

2. [💥 한계점](#-한계점)
3. [📚 STACKS](#-STACKS)
4. [📞 Contact](#-Contact)

<br>   

## 👩🏻‍🏫 API 명세
### 일정 API
 기능           | Method | URL                     |Request| Response | 상태코드      
|--------------|--------|-------------------------|---|----------|-----------|
| 일정 생성        | POST   | /schedules              |요청 body| 등록 정보    | 201: 정상등록 |
| 전체 일정 조회     | GET    | /schedules              |요청 param| 다건 응답 정보 | 200: 정상조회 |
| 전체 일정 조회 페이징 | GET    | /schedules/page         |요청 param| 다건 응답 정보 | 200: 정상조회 |
| 선택 일정 조회     | GET    | /schedules/{scheduleId} |요청 param| 단건 응답 정보 | 200: 정상조회 |
| 선택 일정 수정     | PATCH  | /schedules/{scheduleId} |요청 body| 수정 정보    | 200: 정상수정 |
| 선택 일정 삭제     | DELETE | /schedules/{scheduleId} |요청 param| -        | 200: 정상삭제 |

### 작성자 API
 기능       | Method | URL               |Request| Response | 상태코드      
|----------|--------|-------------------|---|----------|-----------|
| 작성자 생성   | POST   | /users            |요청 body| 등록 정보    | 201: 정상등록 |
| 전체 작성자 조회 | GET    | /users            |요청 param| 다건 응답 정보 | 200: 정상조회 |
| 선택 작성자 조회 | GET    | /users/{userId}   |요청 param| 단건 응답 정보 | 200: 정상조회 |
| 선택 작성자 수정 | PATCH    | /users/{userId} |요청 body| 수정 정보    | 200: 정상수정 |
| 선택 작성자 삭제 | DELETE    | /users/{userId} |요청 param| -        | 200: 정상삭제 |

## 👩🏻‍🏫 ERD
![ERD](/ERD.PNG)





## 👩🏻‍🏫 API 명세
### Lv 1
- 기본적인 키오스크 기능 구현하기
- 클래스 : `Main`
- Scanner, 조건문, 반복문을 이용해 입력 데이터 처리
- 실행 예시
```java
[ SHAKESHACK MENU ]
1. ShackBurger   | W 6.9 | 토마토, 양상추, 쉑소스가 토핑된 치즈버거
2. SmokeShack    | W 8.9 | 베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거
3. Cheeseburger  | W 6.9 | 포테이토 번과 비프패티, 치즈가 토핑된 치즈버거
4. Hamburger     | W 5.4 | 비프패티를 기반으로 야채가 들어간 기본버거
0. 종료      | 종료
1
1. ShackBurger   | W 6.9 | 토마토, 양상추, 쉑소스가 토핑된 치즈버거

[ SHAKESHACK MENU ]
1. ShackBurger   | W 6.9 | 토마토, 양상추, 쉑소스가 토핑된 치즈버거
2. SmokeShack    | W 8.9 | 베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거
3. Cheeseburger  | W 6.9 | 포테이토 번과 비프패티, 치즈가 토핑된 치즈버거
4. Hamburger     | W 5.4 | 비프패티를 기반으로 야채가 들어간 기본버거
0. 종료      | 종료
0
프로그램을 종료합니다.

Process finished with exit code 0
```


### Lv 2
- 객체 지향 설계를 적용해 햄버거 메뉴를 클래스로 관리하기
- 클래스 : `Main`, `MenuItem`
- Scanner, 조건문, 반복문을 이용해 입력 데이터 처리
- 햄버거 메뉴를 MenuItem 클래스와 List를 통해 관리
- 실행 예시
```java
[ SHAKESHACK MENU ]
1. ShackBurger | W 6.9 | 토마토, 양상추, 쉑소스가 토핑된 치즈버거
2. SmokeShack | W 8.9 | 베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거
3. Cheeseburger | W 6.9 | 포테이토 번과 비프패티, 치즈가 토핑된 치즈버거
4. Hamburger | W 5.4 | 비프패티를 기반으로 야채가 들어간 기본버거
0. 종료      | 종료
2
2. SmokeShack    | W 8.9 | 베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거
주문이 왼료되었습니다.

[ SHAKESHACK MENU ]
1. ShackBurger | W 6.9 | 토마토, 양상추, 쉑소스가 토핑된 치즈버거
2. SmokeShack | W 8.9 | 베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거
3. Cheeseburger | W 6.9 | 포테이토 번과 비프패티, 치즈가 토핑된 치즈버거
4. Hamburger | W 5.4 | 비프패티를 기반으로 야채가 들어간 기본버거
0. 종료      | 종료
0
프로그램을 종료합니다.

Process finished with exit code 0
```
### Lv 3
- 객체 지향 설계를 적용해 순서 제어를 클래스로 관리하기
- 클래스 : `Main`, `MenuItem`, `Kiosk`
- Scanner, 조건문, 반복문을 이용해 입력 데이터 처리
- 햄버거 메뉴를 MenuItem 클래스와 List를 통해 관리
- main 함수에서 관리하던 전체 순서 제어를 Kiosk 클래스를 통해 관리
- 실행 예시
```java
[ SHAKESHACK MENU ]
1. ShackBurger | W 6.9 | 토마토, 양상추, 쉑소스가 토핑된 치즈버거
2. SmokeShack | W 8.9 | 베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거
3. Cheeseburger | W 6.9 | 포테이토 번과 비프패티, 치즈가 토핑된 치즈버거
4. Hamburger | W 5.4 | 비프패티를 기반으로 야채가 들어간 기본버거
0. 종료      | 종료
3
3. Cheeseburger  | W 6.9 | 포테이토 번과 비프패티, 치즈가 토핑된 치즈버거
주문이 왼료되었습니다.

[ SHAKESHACK MENU ]
1. ShackBurger | W 6.9 | 토마토, 양상추, 쉑소스가 토핑된 치즈버거
2. SmokeShack | W 8.9 | 베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거
3. Cheeseburger | W 6.9 | 포테이토 번과 비프패티, 치즈가 토핑된 치즈버거
4. Hamburger | W 5.4 | 비프패티를 기반으로 야채가 들어간 기본버거
0. 종료      | 종료
0
프로그램을 종료합니다.

Process finished with exit code 0
```
### Lv 4
- 객체 지향 설계를 적용해 음식 메뉴와 주문 내역을 클래스 기반으로 관리하기
- 클래스 : `Main`, `MenuItem`, `Kiosk`, `Menu`
- Scanner, 조건문, 반복문을 이용해 입력 데이터 처리
- 햄버거 메뉴를 MenuItem 클래스와 List를 통해 관리
- MenuItem 클래스를 관리하는 클래스인 Menu 클래스 추가
- 실행 예시

- 선택한 메인 메뉴에 상세 메뉴가 없는 경우
```java
[ MAIN MENU ]
1. Burgers
2. Drinks
3. Desserts
0. 종료      | 종료
2
[ DRINKS MENU ]
상세 내역이 없는 메뉴입니다. 다시 입력해주세요.
```

### Lv 5
- 캡슐화 적용하기
-  `MenuItem`, `Menu` 그리고 `Kiosk` 클래스의 필드에 직접 접근하지 못하도록 설정
-  Getter와 Setter 메서드를 사용해 데이터를 관리
- Lv4에 캡슐화를 적용했으므로 동일함

### Lv 6
- Lv 1. 장바구니 및 구매하기 기능을 추가하기
- Lv 2. Enum, 람다 & 스트림을 활용한 주문 및 장바구니 관리
- mvc패턴 활용 :
    - Model : `MenuItem`, `Menu` , `Cart`, `User`
    - View : `KioskView`
    - Controller : `KioskController`
    - `Main`
- Scanner, 조건문, 반복문을 이용해 입력 데이터 처리
- 햄버거 메뉴를 MenuItem 클래스와 List를 통해 관리
- 장바구니 생성 및 관리 기능, 장바구니 출력 및 금액 계산, 장바구니 담기 기능, 주문 기능
- Enum을 활용한 사용자 유형별 할인율 관리하기
- 람다 & 스트림을 활용한 장바구니 조회 및 선택한 메뉴 아이템 삭제 기능
- 실행 예시

## 💥 한계점

<details>
<summary>람다 & 스트림 활용</summary>

```Java 
cartList.stream()
        .filter(cartItem -> cartList.indexOf(cartItem) + 1 == chooseMenuItemNumber)
        .findFirst()
        .ifPresent(cartItem -> {
                        cartList.remove(cartItem);
                        System.out.println(cartItem.getName() + " 해당 메뉴가 장바구니에서 삭제되었습니다.\n");
         });
```
</details>

- 좀 더 다양한 예제를 통해 연습해보고 싶습니다.
  <br>

## 📚 STACKS
<div align=center> 
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
</div>

## 📞 Contact
- [🚗 Visit codingTrip blog](https://codingtrip.tistory.com/)
- [🙋‍♂️ Visit codingTrip's Github](https://github.com/codingTrip-IT)


