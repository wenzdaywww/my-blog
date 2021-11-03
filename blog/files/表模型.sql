/**1、博客文章*/
DROP TABLE IF EXISTS BLOG_ARTICLE;
CREATE TABLE IF NOT EXISTS BLOG_ARTICLE (	
	BLOG_ID BIGINT AUTO_INCREMENT comment '博客主键',
	USER_ID VARCHAR(40) comment '用户ID',
	BLOG_THEME TEXT comment '博客主题',
	BLOG_CONTENT LONGTEXT comment '博客内容',
	BLOG_VIEWS BIGINT comment '博客浏览量',
	BLOG_LIKE BIGINT comment '博客点赞数',
	STATE_CD CHAR comment '博客状态：1有效，2删除，3封号',
	BLOG_COMMENT BIGINT comment '博客评论数',
	SYS_UPDATE_TIME DATETIME comment '更新时间',
	SYS_CREATE_TIME DATETIME comment '创建时间',
	primary key(BLOG_ID) );
alter table BLOG_ARTICLE comment '博客文章';
/**2、博客评论*/
DROP TABLE IF EXISTS BLOG_COMMENT;
CREATE TABLE IF NOT EXISTS BLOG_COMMENT (	
	COMMENT_ID BIGINT AUTO_INCREMENT comment '评论主键',
	BLOG_ID BIGINT(40) UNIQUE comment '博客ID',
	USER_ID VARCHAR(40) comment '评论用户ID',
	LIKE_NUM BIGINT comment '评论点赞数',
	COMMENT TEXT comment '评论内容',
	PARENT_COM_ID BIGINT comment '父评论ID',
	SYS_UPDATE_TIME DATETIME comment '更新时间',
	SYS_CREATE_TIME DATETIME comment '创建时间',
	primary key(COMMENT_ID) );
alter table BLOG_COMMENT comment '博客评论';
/**3、博客分类类型*/
DROP TABLE IF EXISTS CLASSIFY;
CREATE TABLE IF NOT EXISTS CLASSIFY (	
	CLASS_ID BIGINT AUTO_INCREMENT comment '分类主键',
	CLASS_NAME VARCHAR(100) comment '分类名称',
	CLASS_ALIAS VARCHAR(256) comment '分类描述',
	SYS_UPDATE_TIME DATETIME comment '更新时间',
	SYS_CREATE_TIME DATETIME comment '创建时间',
	primary key(CLASS_ID) );
alter table CLASSIFY comment '博客分类类型';
/**4、博客分类信息*/
DROP TABLE IF EXISTS BLOG_CLASS;
CREATE TABLE IF NOT EXISTS BLOG_CLASS (	
	BC_ID BIGINT AUTO_INCREMENT comment '博客分类主键',
	BLOG_ID BIGINT comment '博客ID',
	CLASS_ID BIGINT comment '分类ID',
	SYS_UPDATE_TIME DATETIME comment '更新时间',
	SYS_CREATE_TIME DATETIME comment '创建时间',
	primary key(BC_ID) );
alter table BLOG_CLASS comment '博客分类信息';
