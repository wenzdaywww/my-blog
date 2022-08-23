-----------------------------
--1、博客文章
-----------------------------
CREATE TABLE IF NOT EXISTS BLOG_ARTICLE (
	BLOG_ID         BIGINT          AUTO_INCREMENT      COMMENT '博客主键',
	USER_ID         VARCHAR(40)                         COMMENT '用户ID',
	TITLE           TEXT(1024)                          COMMENT '博客题目',
    SUMMARY         TEXT(256)                           COMMENT '博客摘要',
    COVER_IMG       VARCHAR(256)                        COMMENT '博客封面图片',
	CONTENT         LONGTEXT                            COMMENT '博客内容',
	BROWSE          BIGINT                              COMMENT '博客浏览量',
	PRAISE          BIGINT                              COMMENT '博客点赞数',
	STATE_CD        CHAR(1)                             COMMENT '博客状态：1有效，2删除',
	COMMENT         BIGINT                              COMMENT '博客评论数',
    COLLECT         BIGINT                              COMMENT '博客收藏数',
	UPDATE_TIME     DATETIME                            COMMENT '更新时间',
	CREATE_TIME     DATETIME                            COMMENT '创建时间',
	PRIMARY KEY(BLOG_ID)
);
ALTER TABLE     BLOG_ARTICLE        COMMENT '博客文章';
CREATE INDEX    INDEX_USER_ID       ON  BLOG_ARTICLE (USER_ID);
CREATE INDEX    INDEX_BROWSE        ON  BLOG_ARTICLE (BROWSE);
CREATE INDEX    INDEX_PRAISE        ON  BLOG_ARTICLE (PRAISE);
CREATE INDEX    INDEX_STATE_CD      ON  BLOG_ARTICLE (STATE_CD);
CREATE INDEX    INDEX_COMMENT       ON  BLOG_ARTICLE (COMMENT);
CREATE INDEX    INDEX_COLLECT       ON  BLOG_ARTICLE (COLLECT);
CREATE INDEX    INDEX_UPDATE_TIME   ON  BLOG_ARTICLE (UPDATE_TIME);
CREATE INDEX    INDEX_CREATE_TIME   ON  BLOG_ARTICLE (CREATE_TIME);
-----------------------------
--2、博客评论
-----------------------------
CREATE TABLE IF NOT EXISTS BLOG_COMMENT (
	COMMENT_ID      BIGINT          AUTO_INCREMENT      COMMENT '评论主键',
	BLOG_ID         BIGINT(40)      UNIQUE              COMMENT '博客ID',
	USER_ID         VARCHAR(40)                         COMMENT '评论用户ID',
	PRAISE          BIGINT                              COMMENT '评论点赞数',
	COMMENT         TEXT(256)                           COMMENT '评论内容',
	PARENT_COM_ID   BIGINT                              COMMENT '父评论ID',
    REPLY_COM_ID    BIGINT                              COMMENT '回复评论ID',
	UPDATE_TIME     DATETIME                            COMMENT '更新时间',
	CREATE_TIME     DATETIME                            COMMENT '创建时间',
	PRIMARY KEY(COMMENT_ID)
);
ALTER TABLE     BLOG_COMMENT        COMMENT '博客评论';
CREATE INDEX    INDEX_BLOG_ID       ON  BLOG_COMMENT (BLOG_ID);
CREATE INDEX    INDEX_USER_ID       ON  BLOG_COMMENT (USER_ID);
CREATE INDEX    INDEX_PRAISE        ON  BLOG_COMMENT (PRAISE);
CREATE INDEX    INDEX_PARENT_COM_ID ON  BLOG_COMMENT (PARENT_COM_ID);
CREATE INDEX    INDEX_REPLY_COM_ID  ON  BLOG_COMMENT (REPLY_COM_ID);
CREATE INDEX    INDEX_UPDATE_TIME   ON  BLOG_COMMENT (UPDATE_TIME);
CREATE INDEX    INDEX_CREATE_TIME   ON  BLOG_COMMENT (CREATE_TIME);
-----------------------------
--3、博客收藏
-----------------------------
CREATE TABLE IF NOT EXISTS BLOG_COLLECT (
    COLLECT_ID      BIGINT          AUTO_INCREMENT      COMMENT '博客收藏主键',
    BLOG_ID         BIGINT(40)      UNIQUE              COMMENT '博客ID',
    USER_ID         VARCHAR(40)                         COMMENT '用户ID',
    CG_ID           BIGINT                              COMMENT '收藏分组ID',
    UPDATE_TIME     DATETIME                            COMMENT '更新时间',
    CREATE_TIME     DATETIME                            COMMENT '创建时间',
    PRIMARY KEY(COLLECT_ID)
);
ALTER TABLE     BLOG_COLLECT        COMMENT '博客收藏';
CREATE INDEX    INDEX_BLOG_ID       ON  BLOG_COLLECT (BLOG_ID);
CREATE INDEX    INDEX_USER_ID       ON  BLOG_COLLECT (USER_ID);
CREATE INDEX    INDEX_UPDATE_TIME   ON  BLOG_COLLECT (UPDATE_TIME);
CREATE INDEX    INDEX_CREATE_TIME   ON  BLOG_COLLECT (CREATE_TIME);
-----------------------------
--4、标签信息
-----------------------------
CREATE TABLE IF NOT EXISTS TAG_INFO (
    TAG_ID          BIGINT          AUTO_INCREMENT      COMMENT '标签主键',
    TAG_NAME        VARCHAR(100)                        COMMENT '标签名称',
	UPDATE_TIME     DATETIME                            COMMENT '更新时间',
	CREATE_TIME     DATETIME                            COMMENT '创建时间',
	PRIMARY KEY(TAG_ID)
);
ALTER TABLE     TAG_INFO            COMMENT '博客分类类型';
CREATE INDEX    INDEX_TAG_NAME      ON  TAG_INFO (TAG_NAME);
-----------------------------
--5、博客分类信息
-----------------------------
CREATE TABLE IF NOT EXISTS BLOG_TAG (
	BT_ID           BIGINT          AUTO_INCREMENT      COMMENT '博客分类主键',
	BLOG_ID         BIGINT                              COMMENT '博客ID',
    USER_ID         VARCHAR(40)                         COMMENT '用户ID',
    TAG_ID          BIGINT                              COMMENT '标签主键ID',
	UPDATE_TIME     DATETIME                            COMMENT '更新时间',
	CREATE_TIME     DATETIME                            COMMENT '创建时间',
	PRIMARY KEY(BT_ID)
);
ALTER TABLE     BLOG_TAG            COMMENT '博客分类信息';
CREATE INDEX    INDEX_BLOG_IDON     ON  BLOG_TAG (BLOG_ID);
CREATE INDEX    INDEX_TAG_ID        ON  BLOG_TAG (TAG_ID);
CREATE INDEX    INDEX_USER_ID       ON  BLOG_TAG (USER_ID);
-----------------------------
--6、博客分组信息
-----------------------------
CREATE TABLE IF NOT EXISTS GROUP_INFO (
    GROUP_ID        BIGINT          AUTO_INCREMENT      COMMENT '博客分组主键',
    GROUP_NAME      VARCHAR(256)                        COMMENT '博客分组名称',
    USER_ID         VARCHAR(40)                         COMMENT '用户ID',
    UPDATE_TIME     DATETIME                            COMMENT '更新时间',
    CREATE_TIME     DATETIME                            COMMENT '创建时间',
    PRIMARY KEY(GROUP_ID)
);
ALTER TABLE     GROUP_INFO          COMMENT '博客分组信息';
CREATE INDEX    INDEX_USER_ID       ON  GROUP_INFO (USER_ID);
-----------------------------
--7、用户博客分组信息
-----------------------------
CREATE TABLE IF NOT EXISTS BLOG_GROUP (
    BG_ID           BIGINT          AUTO_INCREMENT      COMMENT '博客分组主键',
    GROUP_ID        BIGINT                              COMMENT '分组ID',
    USER_ID         VARCHAR(40)                         COMMENT '用户ID',
    BLOG_ID         BIGINT                              COMMENT '博客ID',
    UPDATE_TIME     DATETIME                            COMMENT '更新时间',
    CREATE_TIME     DATETIME                            COMMENT '创建时间',
    PRIMARY KEY(BG_ID)
);
ALTER TABLE     BLOG_GROUP          COMMENT '博客分组信息';
CREATE INDEX    INDEX_GROUP_ID      ON  BLOG_GROUP (GROUP_ID);
CREATE INDEX    INDEX_BLOG_ID       ON  BLOG_GROUP (BLOG_ID);
CREATE INDEX    INDEX_USER_ID       ON  BLOG_GROUP (USER_ID);
-----------------------------
--8、用户粉丝信息
-----------------------------
CREATE TABLE IF NOT EXISTS USER_FANS (
    UF_ID           BIGINT          AUTO_INCREMENT      COMMENT '用户粉丝信息主键',
    USER_ID         VARCHAR(40)                         COMMENT '用户ID',
    FANS_ID         VARCHAR(40)                         COMMENT '粉丝ID',
    UPDATE_TIME     DATETIME                            COMMENT '更新时间',
    CREATE_TIME     DATETIME                            COMMENT '创建时间',
    PRIMARY KEY(UF_ID)
);
ALTER TABLE     USER_FANS           COMMENT '用户粉丝信息';
CREATE INDEX    INDEX_USER_ID       ON  USER_FANS (USER_ID);
CREATE INDEX    INDEX_FANS_ID       ON  USER_FANS (FANS_ID);
-----------------------------
--9、收藏分组信息
-----------------------------
CREATE TABLE IF NOT EXISTS COLLECT_GROUP (
    CG_ID           BIGINT          AUTO_INCREMENT      COMMENT '收藏分组主键',
    COLLECT_NAME    VARCHAR(256)                        COMMENT '收藏分组名称',
    USER_ID         VARCHAR(40)                         COMMENT '用户ID',
    UPDATE_TIME     DATETIME                            COMMENT '更新时间',
    CREATE_TIME     DATETIME                            COMMENT '创建时间',
    PRIMARY KEY(CG_ID)
);
ALTER TABLE     GROUP_INFO          COMMENT '收藏分组信息';
CREATE INDEX    INDEX_USER_ID       ON  COLLECT_GROUP (USER_ID);
-----------------------------
--10、博客点赞
-----------------------------
CREATE TABLE IF NOT EXISTS BLOG_PRAISE (
    BP_ID           BIGINT          AUTO_INCREMENT      COMMENT '博客点赞主键',
    BLOG_ID         BIGINT(40)      UNIQUE              COMMENT '博客ID',
    USER_ID         VARCHAR(40)                         COMMENT '用户ID',
    UPDATE_TIME     DATETIME                            COMMENT '更新时间',
    CREATE_TIME     DATETIME                            COMMENT '创建时间',
    PRIMARY KEY(BP_ID)
);
ALTER TABLE     BLOG_PRAISE         COMMENT '博客点赞';
CREATE INDEX    INDEX_BLOG_ID       ON  BLOG_PRAISE (BLOG_ID);
CREATE INDEX    INDEX_USER_ID       ON  BLOG_PRAISE (USER_ID);
CREATE INDEX    INDEX_UPDATE_TIME   ON  BLOG_PRAISE (UPDATE_TIME);
CREATE INDEX    INDEX_CREATE_TIME   ON  BLOG_PRAISE (CREATE_TIME);