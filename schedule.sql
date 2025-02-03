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
# deleted 컬럼 추가
USE schedule;
DROP TABLE schedule;
CREATE TABLE schedule
(
    schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    todo VARCHAR(100) NOT NULL COMMENT '할 일',
    writer_id BIGINT COMMENT '작성자 식별자'
        REFERENCES writer(writer_id) ON UPDATE CASCADE,
    password VARCHAR(100) NOT NULL COMMENT '비밀번호',
    createdAt DATETIME COMMENT '생성일',
    updatedAt DATETIME COMMENT '수정일',
    deleted BOOLEAN DEFAULT FALSE COMMENT '논리적 삭제여부'

);

DROP TABLE writer;
CREATE TABLE writer
(
    writer_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '작성자 식별자',
    name VARCHAR(100) NOT NULL COMMENT '작성자명',
    email  VARCHAR(100) NOT NULL COMMENT '이메일',
    createdAt DATETIME COMMENT '생성일',
    updatedAt DATETIME COMMENT '수정일',
    deleted BOOLEAN DEFAULT FALSE COMMENT '논리적 삭제여부'

);