-----------------------------
--1、用户信息
-----------------------------
CREATE TABLE IF NOT EXISTS SYS_USER (
  SU_ID                     BIGINT          AUTO_INCREMENT          COMMENT '用户主键ID',
  USER_ID                   VARCHAR(40)     NOT NULL UNIQUE         COMMENT '用户名',
  USER_NAME                 VARCHAR(100)    NOT NULL                COMMENT '用户昵称',
  PASSWORD                  VARCHAR(200)    NOT NULL                COMMENT '密码',
  PHONE_NUM                 VARCHAR(11)                             COMMENT '手机号',
  BIRTHDAY                  DATE                                    COMMENT '生日',
  SEX                       CHAR(1)                                 COMMENT '性别：1男0女',
  PHOTO                     VARCHAR(256)                            COMMENT '头像',
  EMAIL                     VARCHAR(100)                            COMMENT '邮箱',
  BRIEF                     VARCHAR(256)                            COMMENT '个人简介',
  STATE_CD                  CHAR(1)         NOT NULL DEFAULT '1'    COMMENT '用户状态：1有效，2注销，3封号',
  NOT_EXPIRED               CHAR(1)         NOT NULL DEFAULT '1'    COMMENT '是否未过期。默认为1未过期，0过期',
  NOT_LOCKED                CHAR(1)         NOT NULL DEFAULT '1'    COMMENT '账号是否未锁定。默认为1未锁定，0锁定',
  CREDENTIALS_NOT_EXPIRED   CHAR(1)         NOT NULL DEFAULT '1'    COMMENT '证书（密码）是否未过期。默认为1未过期，0过期',
  UPDATE_TIME               DATETIME                                COMMENT '更新时间',
  CREATE_TIME               DATETIME                                COMMENT '创建时间',
  PRIMARY KEY(SU_ID)
);
ALTER TABLE     SYS_USER            COMMENT '用户信息';
ALTER TABLE     SYS_USER            AUTO_INCREMENT=1000;
CREATE INDEX    INDEX_USER_ID       ON  SYS_USER (USER_ID);
CREATE INDEX    INDEX_USER_NAME     ON  SYS_USER (USER_NAME);
CREATE INDEX    INDEX_STATE_CD      ON  SYS_USER (STATE_CD);
-----------------------------
--2、角色信息
-----------------------------
CREATE TABLE IF NOT EXISTS SYS_ROLE (
  ROLE_ID                   BIGINT A        UTO_INCREMENT           COMMENT '角色主键',
  ROLE_CODE                 VARCHAR(100)    NOT NULL                COMMENT '角色编码',
  ROLE_NAME                 VARCHAR(256)    NOT NULL                COMMENT '角色名称',
  UPDATE_TIME               DATETIME                                COMMENT '更新时间',
  CREATE_TIME               DATETIME                                COMMENT '创建时间',
  PRIMARY KEY(ROLE_ID)
);
ALTER TABLE     SYS_ROLE            COMMENT '角色信息';
CREATE INDEX    INDEX_ROLE_CODE     ON  SYS_ROLE (ROLE_CODE);
-----------------------------
--3、用户角色信息
-----------------------------
CREATE TABLE IF NOT EXISTS SYS_USER_ROLE (
  SUR_ID                    BIGINT          AUTO_INCREMENT          COMMENT '用户角色主键',
  SU_ID                     VARCHAR(40)     NOT NULL                COMMENT '用户主键ID',
  ROLE_ID                   BIGINT                                  COMMENT '角色ID',
  UPDATE_TIME               DATETIME                                COMMENT '更新时间',
  CREATE_TIME               DATETIME                                COMMENT '创建时间',
  PRIMARY KEY(SUR_ID)
);
ALTER TABLE     SYS_USER_ROLE       COMMENT '用户角色信息';
CREATE INDEX    INDEX_USER_ID       ON  SYS_USER_ROLE (SU_ID);
CREATE INDEX    INDEX_ROLE_ID       ON  SYS_USER_ROLE (ROLE_ID);
-----------------------------
--4、客户端信息-oauth2需要
-----------------------------
CREATE TABLE IF NOT EXISTS OAUTH_CLIENT_DETAILS (
  CLIENT_ID                 VARCHAR(48)     NOT NULL UNIQUE         COMMENT '客户端ID',
  RESOURCE_IDS              VARCHAR(256)                            COMMENT '客户端拥有的资源列表,逗号分割',
  CLIENT_SECRET             VARCHAR(256)                            COMMENT '客户端密钥',
  SCOPE                     VARCHAR(256)                            COMMENT '允许的授权范围,逗号分割',
  AUTHORIZED_GRANT_TYPES    VARCHAR(256)                            COMMENT '认证范围,逗号分割',
  WEB_SERVER_REDIRECT_URI   VARCHAR(256)                            COMMENT '回调地址',
  AUTHORITIES               VARCHAR(256)                            COMMENT '权限',
  ACCESS_TOKEN_VALIDITY     INT(11)                                 COMMENT '令牌有效时间（秒）',
  REFRESH_TOKEN_VALIDITY    INT(11)                                 COMMENT '令牌刷新后有效时间（秒）',
  ADDITIONAL_INFORMATION    VARCHAR(4096)                           COMMENT '客户端描述信息',
  AUTOAPPROVE               VARCHAR(256)                            COMMENT '是否跳转授权页面',
  UPDATE_TIME               DATETIME                                COMMENT '更新时间',
  CREATE_TIME               DATETIME                                COMMENT '创建时间',
  PRIMARY KEY(CLIENT_ID)
);
ALTER TABLE     OAUTH_CLIENT_DETAILS    COMMENT '客户端信息';
-----------------------------
--5、授权码信息-oauth2需要
-----------------------------
CREATE TABLE IF NOT EXISTS OAUTH_CODE (
  CODE                      VARCHAR(48)                             COMMENT '授权码',
  AUTHENTICATION            BLOB                                    COMMENT '身份验证'
);
ALTER TABLE     OAUTH_CODE          COMMENT '授权码信息';
CREATE INDEX    INDEX_CODE          ON  OAUTH_CODE (CODE);
-----------------------------
--6、菜单信息
-----------------------------
CREATE TABLE IF NOT EXISTS SYS_MENU (
	MENU_ID                 BIGINT          AUTO_INCREMENT          COMMENT '菜单主键',
	MENU_CODE               VARCHAR(40)                             COMMENT '菜单编码',
    MENU_URL                VARCHAR(512)                            COMMENT '菜单路径',
	MENU_NAME               VARCHAR(100)                            COMMENT '菜单名称',
    VUE_PATH                VARCHAR(40)                             COMMENT 'router的path路径',
    SCOPE                   VARCHAR(512)                            COMMENT '允许的授权范围,逗号分割',
	PARENT_ID               BIGINT                                  COMMENT '父级菜单ID',
	MENU_ICON               VARCHAR(256)                            COMMENT '菜单图标',
	MENU_ORDER              INT                                     COMMENT '菜单序号',
    MENU_TYPE               CHAR(1)         DEFAULT '1'             COMMENT '菜单类型：1目录菜单，2请求路径，3vue路由',
    MODULE                  VARCHAR(100)                            COMMENT '菜单归属模块',
	IS_DELETE               CHAR(1)         DEFAULT '0'             COMMENT '是否删除：1删除，0未删除',
	UPDATE_TIME             DATETIME                                COMMENT '更新时间',
	CREATE_TIME             DATETIME                                COMMENT '创建时间',
	PRIMARY KEY(MENU_ID)
);
ALTER TABLE     SYS_MENU            COMMENT '菜单信息';
CREATE INDEX    INDEX_MENU_CODE     ON  SYS_MENU (MENU_CODE);
CREATE INDEX    INDEX_MENU_URL      ON  SYS_MENU (MENU_URL);
CREATE INDEX    INDEX_MENU_TYPE     ON  SYS_MENU (MENU_TYPE);
CREATE INDEX    INDEX_MENU_TYPE     ON  SYS_MENU (MODULE);
-----------------------------
--7、角色菜单信息
-----------------------------
CREATE TABLE IF NOT EXISTS SYS_ROLE_MENU (
	SRM_ID                  BIGINT          AUTO_INCREMENT          COMMENT '角色菜单主键',
	ROLE_ID                 BIGINT                                  COMMENT '角色ID',
	MENU_ID                 BIGINT                                  COMMENT '菜单ID',
	UPDATE_TIME             DATETIME                                COMMENT '更新时间',
	CREATE_TIME             DATETIME                                COMMENT '创建时间',
	PRIMARY KEY(SRM_ID)
);
ALTER TABLE     SYS_ROLE_MENU       COMMENT '角色菜单信息';
CREATE INDEX    INDEX_ROLE_ID       ON  SYS_ROLE_MENU (ROLE_ID);
CREATE INDEX    INDEX_MENU_ID       ON  SYS_ROLE_MENU (MENU_ID);
-----------------------------
--8、数据字典信息
-----------------------------
CREATE TABLE IF NOT EXISTS CODE_DATA (
    CODE_TYPE               VARCHAR(100)                            COMMENT 'code类型',
    CODE_NAME               VARCHAR(256)                            COMMENT 'code类型名称',
    CODE_KEY                VARCHAR(100)                            COMMENT 'code的key',
    CODE_VALUE              VARCHAR(100)                            COMMENT '码值',
    VALUE_NAME              VARCHAR(256)                            COMMENT '码值名称',
    IS_VALID                CHAR(1)         DEFAULT '1'             COMMENT '是否有效，1有效，0无效'
);
ALTER TABLE     CODE_DATA           COMMENT '数据字典信息';
CREATE INDEX    INDEX_CODE_TYPE     ON  CODE_DATA (CODE_TYPE);
CREATE INDEX    INDEX_CODE_KEY      ON  CODE_DATA (CODE_KEY);
CREATE INDEX    INDEX_CODE_VALUE    ON  CODE_DATA (CODE_VALUE);
CREATE INDEX    INDEX_IS_VALID      ON  CODE_DATA (IS_VALID);
-----------------------------
--9、监控信息
-----------------------------
CREATE TABLE IF NOT EXISTS MONITOR_INFO (
    MONITOR_ID              BIGINT          AUTO_INCREMENT          COMMENT '监控主键',
    MONITOR_NAME            VARCHAR(512)                            COMMENT '监控名称',
    MONITOR_ADDR            VARCHAR(512)                            COMMENT '监控地址',
    UPDATE_TIME             DATETIME                                COMMENT '更新时间',
    CREATE_TIME             DATETIME                                COMMENT '创建时间',
    PRIMARY KEY(MONITOR_ID)
);
ALTER TABLE     MONITOR_INFO        COMMENT '监控信息';
CREATE INDEX    INDEX_MONITOR_NAME  ON  MONITOR_INFO (MONITOR_NAME);