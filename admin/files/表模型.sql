/**5、菜单信息*/
CREATE TABLE IF NOT EXISTS SYS_MENU (
	MENU_ID BIGINT AUTO_INCREMENT comment '菜单主键',
	MENU_CODE VARCHAR(40) comment '菜单编码',
    MENU_URL VARCHAR(512) comment '菜单路径',
	MENU_NAME VARCHAR(100) comment '菜单名称',
    VUE_PATH VARCHAR(40) comment 'router的path路径',
	PARENT_ID BIGINT comment '父级菜单ID',
	MENU_ICON VARCHAR(256) comment '菜单图标',
	MENU_ORDER INT comment '菜单序号',
    MENU_TYPE CHAR(1) default '1' comment '菜单类型：1目录菜单，2请求路径，3vue路由',
    MODULE VARCHAR(100) comment '菜单归属模块',
	IS_DELETE CHAR(1) default '0' comment '是否删除：1删除，0未删除',
	SYS_UPDATE_TIME DATETIME comment '更新时间',
	SYS_CREATE_TIME DATETIME comment '创建时间',
	primary key(MENU_ID) );
alter table SYS_MENU comment '菜单信息';
CREATE INDEX INDEX_MENU_CODE ON SYS_MENU (MENU_CODE);
CREATE INDEX INDEX_MENU_URL ON SYS_MENU (MENU_URL);
CREATE INDEX INDEX_MENU_TYPE ON SYS_MENU (MENU_TYPE);
CREATE INDEX INDEX_MENU_TYPE ON SYS_MENU (MODULE);
/**6、角色菜单信息*/
CREATE TABLE IF NOT EXISTS SYS_ROLE_MENU (
	SRM_ID BIGINT AUTO_INCREMENT comment '角色菜单主键',
	ROLE_ID BIGINT comment '角色ID',
	MENU_ID BIGINT comment '菜单ID',
	SYS_UPDATE_TIME DATETIME comment '更新时间',
	SYS_CREATE_TIME DATETIME comment '创建时间',
	primary key(SRM_ID) );
alter table SYS_ROLE_MENU comment '角色菜单信息';
CREATE INDEX INDEX_ROLE_ID ON SYS_ROLE_MENU (ROLE_ID);
CREATE INDEX INDEX_MENU_ID ON SYS_ROLE_MENU (MENU_ID);


