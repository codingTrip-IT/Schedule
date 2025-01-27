use schedule;

CREATE TABLE schedule
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    todo VARCHAR(100) NOT NULL COMMENT '할 일',
    writer VARCHAR(100) NOT NULL COMMENT '작성자명',
    password VARCHAR(100) NOT NULL COMMENT '비밀번호',
    createdAt DATETIME COMMENT '생성일',
    updatedAt DATETIME COMMENT '수정일'

);

# writer 테이블 추가
USE schedule;
DROP TABLE schedule;
DROP TABLE writer;
CREATE TABLE schedule
(
    scheduleId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    todo VARCHAR(100) NOT NULL COMMENT '할 일',
    writerId BIGINT COMMENT '작성자 식별자'
        REFERENCES writer(writerId),
    password VARCHAR(100) NOT NULL COMMENT '비밀번호',
    createdAt DATETIME COMMENT '생성일',
    updatedAt DATETIME COMMENT '수정일'

);

CREATE TABLE writer
(
    writerId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '작성자 식별자',
    name VARCHAR(100) NOT NULL COMMENT '작성자명',
    email  VARCHAR(100) NOT NULL COMMENT '이메일',
    createdAt DATETIME COMMENT '생성일',
    updatedAt DATETIME COMMENT '수정일'

);