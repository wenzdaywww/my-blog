/**1、用户信息*/
DROP TABLE IF EXISTS SYS_USER;
CREATE TABLE IF NOT EXISTS SYS_USER (	
	SU_ID BIGINT AUTO_INCREMENT comment '用户主键',
	USER_ID VARCHAR(40) NOT NULL UNIQUE comment '用户ID',
	USER_NAME VARCHAR(100) NOT NULL comment '用户昵称',
	PASS_WORD VARCHAR(200) NOT NULL comment '密码',
	PHONE_NUM VARCHAR(11) comment '手机号',
	BIRTHDAY DATE comment '生日',
	SEX CHAR(1) comment '性别：1男0女',
	PHOTO VARCHAR(256) NOT NULL comment '头像',
	E_MAIL VARCHAR(100) comment '邮箱',
	STATE_CD VARCHAR(1) NOT NULL default '1' comment '用户状态：1有效，2注销，3封号',
	SYS_UPDATE_TIME DATETIME comment '更新时间',
	SYS_CREATE_TIME DATETIME comment '创建时间',
	primary key(SU_ID) );
alter table SYS_USER comment '用户信息';
/**2、用户好友信息*/
DROP TABLE IF EXISTS USER_FRIENDS;
CREATE TABLE IF NOT EXISTS USER_FRIENDS (	
	FRIENDS_ID BIGINT AUTO_INCREMENT comment '好友主键',
	USER_ID VARCHAR(40) comment '用户ID',
	FRIEND_ID VARCHAR(40) comment '好友ID',
	FRIEND_NAME VARCHAR(100) comment '好友备注',
	SYS_UPDATE_TIME DATETIME comment '更新时间',
	SYS_CREATE_TIME DATETIME comment '创建时间',
	primary key(FRIENDS_ID) );
alter table USER_FRIENDS comment '用户好友信息';
/**3、角色信息*/
DROP TABLE IF EXISTS SYS_ROLE;
CREATE TABLE IF NOT EXISTS SYS_ROLE (	
ROLE_ID BIGINT AUTO_INCREMENT comment '角色主键',
	ROLE_NAME VARCHAR(100) NOT NULL comment '角色昵称',
	DESCRIPTION VARCHAR(256) NOT NULL comment '角色描述',
	SYS_UPDATE_TIME DATETIME comment '更新时间',
	SYS_CREATE_TIME DATETIME comment '创建时间', 
	primary key(ROLE_ID) );
alter table SYS_ROLE comment '角色信息';
/**4、用户角色信息*/
DROP TABLE IF EXISTS SYS_USER_ROLE;
CREATE TABLE IF NOT EXISTS SYS_USER_ROLE (	
	SUR_ID BIGINT AUTO_INCREMENT comment '用户角色主键',
	USER_ID VARCHAR(40) NOT NULL comment '用户ID',
	ROLE_ID BIGINT comment '角色ID',
	SYS_UPDATE_TIME DATETIME comment '更新时间',
	SYS_CREATE_TIME DATETIME comment '创建时间',
	primary key(SUR_ID) );
alter table SYS_USER_ROLE comment '用户角色信息';
/**5、菜单信息*/
DROP TABLE IF EXISTS SYS_MENU;
CREATE TABLE IF NOT EXISTS SYS_MENU (	
	MENU_ID BIGINT AUTO_INCREMENT comment '菜单主键',
	MENU_CODE VARCHAR(40) comment '菜单编码',
	MENU_NAME VARCHAR(100) comment '菜单名称',
	PARENT_ID BIGINT comment '父级菜单ID',
	MENU_ICON VARCHAR(256) comment '菜单图标',
	MENU_URL VARCHAR(512) comment '菜单路径',
	MENU_ORDER INT comment '菜单序号',
	MENU_ICON VARCHAR(256) comment '菜单图标',
	IS_DELETE CHAR(1) comment '是否删除：1删除，0未删除',
	SYS_UPDATE_TIME DATETIME comment '更新时间',
	SYS_CREATE_TIME DATETIME comment '创建时间',
	primary key(MENU_ID) );
alter table SYS_MENU comment '菜单信息';
/**6、角色菜单信息*/
DROP TABLE IF EXISTS SYS_ROLE_MENU;
CREATE TABLE IF NOT EXISTS SYS_ROLE_MENU (	
	SRM_ID BIGINT AUTO_INCREMENT comment '角色菜单主键',
	ROLE_ID BIGINT comment '角色ID',
	MENU_ID BIGINT comment '菜单ID',
	SYS_UPDATE_TIME DATETIME comment '更新时间',
	SYS_CREATE_TIME DATETIME comment '创建时间',
	primary key(SRM_ID) );
alter table SYS_ROLE_MENU comment '角色菜单信息';


