CREATE TABLE commerce.customers (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id BIGINT UNSIGNED NOT NULL COMMENT '사용자 아이디',
    name VARCHAR(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '이름',
    created_at DATETIME NOT NULL COMMENT '등록 일시',
    created_by BIGINT UNSIGNED NOT NULL COMMENT '등록 사용자 아이디',
    updated_at DATETIME NOT NULL COMMENT '수정 일시',
    updated_by BIGINT UNSIGNED NOT NULL COMMENT '수정 사용자 아이디',
    PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci
COMMENT='구매자'
;


CREATE TABLE commerce.customers_aud (
    rev BIGINT UNSIGNED NOT NULL,
    revtype TINYINT NOT NULL,
    id BIGINT UNSIGNED NOT NULL,
    user_id BIGINT UNSIGNED NOT NULL COMMENT '사용자 아이디',
    name VARCHAR(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '이름',
    created_at DATETIME NOT NULL COMMENT '등록 일시',
    created_by BIGINT UNSIGNED NOT NULL COMMENT '등록 사용자 아이디',
    updated_at DATETIME NOT NULL COMMENT '수정 일시',
    updated_by BIGINT UNSIGNED NOT NULL COMMENT '수정 사용자 아이디',
    PRIMARY KEY (rev, id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci
COMMENT='구매자 이력'
;
