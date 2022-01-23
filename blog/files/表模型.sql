/**1、博客文章*/
CREATE TABLE IF NOT EXISTS BLOG_ARTICLE (
	BLOG_ID BIGINT AUTO_INCREMENT comment '博客主键',
	USER_ID VARCHAR(40) comment '用户ID',
	BLOG_THEME TEXT(1024) comment '博客主题',
	BLOG_CONTENT LONGTEXT comment '博客内容',
	BLOG_VIEWS BIGINT comment '博客浏览量',
	BLOG_LIKE BIGINT comment '博客点赞数',
	STATE_CD CHAR comment '博客状态：1有效，2删除',
	BLOG_COMMENT BIGINT comment '博客评论数',
	UPDATE_TIME DATETIME comment '更新时间',
	CREATE_TIME DATETIME comment '创建时间',
	PRIMARY KEY(BLOG_ID) );
ALTER TABLE BLOG_ARTICLE comment '博客文章';
CREATE INDEX INDEX_USER_ID ON BLOG_ARTICLE (USER_ID);
CREATE INDEX INDEX_BLOG_THEME ON BLOG_ARTICLE (BLOG_THEME);
CREATE INDEX INDEX_BLOG_VIEWS ON BLOG_ARTICLE (BLOG_VIEWS);
CREATE INDEX INDEX_BLOG_LIKE ON BLOG_ARTICLE (BLOG_LIKE);
CREATE INDEX INDEX_STATE_CD ON BLOG_ARTICLE (STATE_CD);
CREATE INDEX INDEX_BLOG_COMMENT ON BLOG_ARTICLE (BLOG_COMMENT);
CREATE INDEX INDEX_UPDATE_TIME ON BLOG_ARTICLE (UPDATE_TIME);
CREATE INDEX INDEX_CREATE_TIME ON BLOG_ARTICLE (CREATE_TIME);
/**2、博客评论*/
CREATE TABLE IF NOT EXISTS BLOG_COMMENT (
	COMMENT_ID BIGINT AUTO_INCREMENT comment '评论主键',
	BLOG_ID BIGINT(40) UNIQUE comment '博客ID',
	USER_ID VARCHAR(40) comment '评论用户ID',
	LIKE_NUM BIGINT comment '评论点赞数',
	COMMENT TEXT(1024) comment '评论内容',
	PARENT_COM_ID BIGINT comment '父评论ID',
	UPDATE_TIME DATETIME comment '更新时间',
	CREATE_TIME DATETIME comment '创建时间',
	PRIMARY KEY(COMMENT_ID) );
ALTER TABLE BLOG_COMMENT comment '博客评论';
CREATE INDEX INDEX_BLOG_ID ON BLOG_COMMENT (BLOG_ID);
CREATE INDEX INDEX_USER_ID ON BLOG_COMMENT (USER_ID);
CREATE INDEX INDEX_LIKE_NUM ON BLOG_COMMENT (LIKE_NUM);
CREATE INDEX INDEX_PARENT_COM_ID ON BLOG_COMMENT (PARENT_COM_ID);
CREATE INDEX INDEX_UPDATE_TIME ON BLOG_COMMENT (UPDATE_TIME);
CREATE INDEX INDEX_CREATE_TIME ON BLOG_COMMENT (CREATE_TIME);
/**3、博客收藏*/
CREATE TABLE IF NOT EXISTS BLOG_COLLECT (
    COLLECT_ID BIGINT AUTO_INCREMENT comment '博客收藏主键',
    BLOG_ID BIGINT(40) UNIQUE comment '博客ID',
    USER_ID VARCHAR(40) comment '用户ID',
    UPDATE_TIME DATETIME comment '更新时间',
    CREATE_TIME DATETIME comment '创建时间',
    PRIMARY KEY(COLLECT_ID) );
ALTER TABLE BLOG_COLLECT comment '博客收藏';
CREATE INDEX INDEX_BLOG_ID ON BLOG_COLLECT (BLOG_ID);
CREATE INDEX INDEX_USER_ID ON BLOG_COLLECT (USER_ID);
CREATE INDEX INDEX_UPDATE_TIME ON BLOG_COLLECT (UPDATE_TIME);
CREATE INDEX INDEX_CREATE_TIME ON BLOG_COLLECT (CREATE_TIME);
/**4、博客分类类型*/
CREATE TABLE IF NOT EXISTS CLASSIFICATION (
	CLASS_ID BIGINT AUTO_INCREMENT comment '分类主键',
    CLASS_CODE VARCHAR(256) comment '分类编码',
	CLASS_NAME VARCHAR(100) comment '分类名称',
	UPDATE_TIME DATETIME comment '更新时间',
	CREATE_TIME DATETIME comment '创建时间',
	PRIMARY KEY(CLASS_ID) );
ALTER TABLE CLASSIFICATION comment '博客分类类型';
CREATE INDEX INDEX_CLASS_CODE ON CLASSIFICATION (CLASS_CODE);
CREATE INDEX INDEX_CLASS_NAME ON CLASSIFICATION (CLASS_NAME);
/**5、博客分类信息*/
CREATE TABLE IF NOT EXISTS BLOG_CLASS (
	BC_ID BIGINT AUTO_INCREMENT comment '博客分类主键',
	BLOG_ID BIGINT comment '博客ID',
    USER_ID VARCHAR(40) comment '用户ID',
	CLASS_ID BIGINT comment '分类ID',
	UPDATE_TIME DATETIME comment '更新时间',
	CREATE_TIME DATETIME comment '创建时间',
	PRIMARY KEY(BC_ID) );
ALTER TABLE BLOG_CLASS comment '博客分类信息';
CREATE INDEX INDEX_BLOG_IDON ON BLOG_CLASS (BLOG_ID);
CREATE INDEX INDEX_CLASS_ID ON BLOG_CLASS (CLASS_ID);
CREATE INDEX INDEX_USER_ID ON BLOG_CLASS (USER_ID);
/**6、博客分组信息*/
CREATE TABLE IF NOT EXISTS BLOG_GROUP (
    BG_ID BIGINT AUTO_INCREMENT comment '博客分组主键',
    GROUP_NAME VARCHAR(256) comment '博客分组名称',
    USER_ID VARCHAR(40) comment '用户ID',
    UPDATE_TIME DATETIME comment '更新时间',
    CREATE_TIME DATETIME comment '创建时间',
    PRIMARY KEY(BG_ID) );
ALTER TABLE BLOG_GROUP comment '博客分组信息';
CREATE INDEX INDEX_BLOG_IDON ON BLOG_GROUP (USER_ID);
/**7、用户博客分组信息*/
CREATE TABLE IF NOT EXISTS USER_BLOG_GROUP (
    UBG_ID BIGINT AUTO_INCREMENT comment '用户博客分组主键',
    BG_ID BIGINT comment '博客分组主键',
    USER_ID VARCHAR(40) comment '用户ID',
    BLOG_ID BIGINT comment '博客ID',
    UPDATE_TIME DATETIME comment '更新时间',
    CREATE_TIME DATETIME comment '创建时间',
    PRIMARY KEY(UBG_ID) );
ALTER TABLE USER_BLOG_GROUP comment '用户博客分组信息';
CREATE INDEX INDEX_BG_ID ON USER_BLOG_GROUP (BG_ID);
CREATE INDEX INDEX_BLOG_ID ON USER_BLOG_GROUP (BLOG_ID);
CREATE INDEX INDEX_USER_ID ON USER_BLOG_GROUP (USER_ID);
/**8、用户粉丝信息*/
CREATE TABLE IF NOT EXISTS USER_FANS (
    UF_ID BIGINT AUTO_INCREMENT comment '用户粉丝信息主键',
    USER_ID VARCHAR(40) comment '用户ID',
    FANS_ID VARCHAR(40) comment '粉丝ID',
    UPDATE_TIME DATETIME comment '更新时间',
    CREATE_TIME DATETIME comment '创建时间',
    PRIMARY KEY(UF_ID) );
ALTER TABLE USER_FANS comment '用户粉丝信息';
CREATE INDEX INDEX_USER_ID ON USER_FANS (USER_ID);
CREATE INDEX INDEX_FANS_ID ON USER_FANS (FANS_ID);