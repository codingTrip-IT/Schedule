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