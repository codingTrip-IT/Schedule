use schedule;

# 일정 테이블 삭제
DROP TABLE schedule;
# 일정 테이블 생성
CREATE TABLE schedule
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    user_id BIGINT COMMENT '작성자 식별자'
        REFERENCES user(id) ON UPDATE CASCADE,
    todo VARCHAR(300) NOT NULL COMMENT '할 일',
    password VARCHAR(100) NOT NULL COMMENT '비밀번호',
    created_at DATETIME COMMENT '생성일',
    updated_at DATETIME COMMENT '수정일',
    deleted BOOLEAN DEFAULT FALSE COMMENT '논리적 삭제여부'
);

# 사용자(작성자) 테이블 삭제
DROP TABLE user;
# 사용자(작성자) 테이블 생성
CREATE TABLE user
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '작성자 식별자',
    username VARCHAR(100) NOT NULL COMMENT '작성자명',
    email  VARCHAR(100) NOT NULL COMMENT '이메일',
    created_at DATETIME COMMENT '생성일',
    updated_at DATETIME COMMENT '수정일',
    deleted BOOLEAN DEFAULT FALSE COMMENT '논리적 삭제여부'
);