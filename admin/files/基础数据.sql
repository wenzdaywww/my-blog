/**1、插入用户信息 **/
DELETE FROM SYS_USER WHERE 1=1;
INSERT INTO SYS_USER (USER_ID, USER_NAME, PASS_WORD,STATE_CD,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('admin', '管理员', '$2a$10$.jycUozQpxha24tWaUGwFOKMI.BpZk1jw1vqydu7Q.uAnvc9wkVwy','1',now(),now());/**www362412**/
INSERT INTO SYS_USER (USER_ID, USER_NAME, PASS_WORD,STATE_CD,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('www', 'wenzday', '$2a$10$.jycUozQpxha24tWaUGwFOKMI.BpZk1jw1vqydu7Q.uAnvc9wkVwy','1',now(),now());/**www362412**/

/**2、插入角色信息 **/
DELETE FROM SYS_ROLE WHERE 1=1;
INSERT INTO SYS_ROLE (ROLE_NAME,DESCRIPTION,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('admin','管理员',now(),now());
INSERT INTO SYS_ROLE (ROLE_NAME,DESCRIPTION,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('user','用户',now(),now());
INSERT INTO SYS_ROLE (ROLE_NAME,DESCRIPTION,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('test','测试',now(),now());

/**3、插入用户角色信息 **/
DELETE FROM SYS_USER_ROLE WHERE 1=1;
INSERT INTO SYS_USER_ROLE (USER_ID, ROLE_ID,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('admin', (select c.ROLE_ID from sys_role c where c.ROLE_NAME='admin'),now(),now());
INSERT INTO SYS_USER_ROLE (USER_ID, ROLE_ID,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('www', (select c.ROLE_ID from sys_role c where c.ROLE_NAME='user'),now(),now());

/**4、插入菜单信息 **/
DELETE FROM SYS_MENU WHERE 1=1;
/**页面菜单**/
INSERT INTO SYS_MENU (MENU_CODE,MENU_NAME,PARENT_ID,MENU_ICON,MENU_URL,MENU_ORDER,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('home','首页',null,'el-icon-lx-home','/home',0,now(),now());
INSERT INTO SYS_MENU (MENU_CODE,MENU_NAME,PARENT_ID,MENU_ICON,MENU_URL,MENU_ORDER,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('userInfo','个人中心',null,'el-icon-s-custom','/user',1,now(),now());
INSERT INTO SYS_MENU (MENU_CODE,MENU_NAME,PARENT_ID,MENU_ICON,MENU_URL,MENU_ORDER,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('userList','用户列表',null,'el-icon-user','/table',2,now(),now());
INSERT INTO SYS_MENU (MENU_CODE,MENU_NAME,PARENT_ID,MENU_ICON,MENU_URL,MENU_ORDER,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('menuManage','菜单管理',null,'el-icon-menu','/menu',3,now(),now());
/**请求路径**/
INSERT INTO SYS_MENU (MENU_CODE,MENU_NAME,PARENT_ID,MENU_ICON,MENU_URL,MENU_ORDER,MENU_TYPE,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('/test/**',null,null,null,'/test/**',0,'2',now(),now());
INSERT INTO SYS_MENU (MENU_CODE,MENU_NAME,PARENT_ID,MENU_ICON,MENU_URL,MENU_ORDER,MENU_TYPE,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('/user/**',null,null,null,'/user/**',0,'2',now(),now());
INSERT INTO SYS_MENU (MENU_CODE,MENU_NAME,PARENT_ID,MENU_ICON,MENU_URL,MENU_ORDER,MENU_TYPE,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('/common/**',null,null,null,'/common/**',0,'2',now(),now());
INSERT INTO SYS_MENU (MENU_CODE,MENU_NAME,PARENT_ID,MENU_ICON,MENU_URL,MENU_ORDER,MENU_TYPE,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('/menu/**',null,null,null,'/menu/**',0,'2',now(),now());

/**5、插入角色菜单信息 **/
DELETE FROM SYS_ROLE_MENU WHERE 1=1;
/**页面菜单**/
/** admin角色菜单配置 **/
INSERT INTO SYS_ROLE_MENU (ROLE_ID,MENU_ID,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ((SELECT C.ROLE_ID FROM SYS_ROLE C WHERE C.ROLE_NAME='admin'),
        (SELECT MENU_ID FROM SYS_MENU WHERE MENU_CODE='home'),now(),now());
INSERT INTO SYS_ROLE_MENU (ROLE_ID,MENU_ID,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ((SELECT C.ROLE_ID FROM SYS_ROLE C WHERE C.ROLE_NAME='admin'),
        (SELECT MENU_ID FROM SYS_MENU WHERE MENU_CODE='userInfo'),now(),now());
INSERT INTO SYS_ROLE_MENU (ROLE_ID,MENU_ID,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ((SELECT C.ROLE_ID FROM SYS_ROLE C WHERE C.ROLE_NAME='admin'),
        (SELECT MENU_ID FROM SYS_MENU WHERE MENU_CODE='userList'),now(),now());
INSERT INTO SYS_ROLE_MENU (ROLE_ID,MENU_ID,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ((SELECT C.ROLE_ID FROM SYS_ROLE C WHERE C.ROLE_NAME='admin'),
        (SELECT MENU_ID FROM SYS_MENU WHERE MENU_CODE='menuManage'),now(),now());
/** user角色菜单配置 **/
INSERT INTO SYS_ROLE_MENU (ROLE_ID,MENU_ID,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ((SELECT C.ROLE_ID FROM SYS_ROLE C WHERE C.ROLE_NAME='user'),
        (SELECT MENU_ID FROM SYS_MENU WHERE MENU_CODE='home'),now(),now());
INSERT INTO SYS_ROLE_MENU (ROLE_ID,MENU_ID,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ((SELECT C.ROLE_ID FROM SYS_ROLE C WHERE C.ROLE_NAME='user'),
        (SELECT MENU_ID FROM SYS_MENU WHERE MENU_CODE='userInfo'),now(),now());
/**请求路径**/
/** admin角色请求路径配置 **/
INSERT INTO SYS_ROLE_MENU (ROLE_ID,MENU_ID,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ((SELECT C.ROLE_ID FROM SYS_ROLE C WHERE C.ROLE_NAME='admin'),
        (SELECT MENU_ID FROM SYS_MENU WHERE MENU_CODE='/test/**'),now(),now());
INSERT INTO SYS_ROLE_MENU (ROLE_ID,MENU_ID,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ((SELECT C.ROLE_ID FROM SYS_ROLE C WHERE C.ROLE_NAME='admin'),
        (SELECT MENU_ID FROM SYS_MENU WHERE MENU_CODE='/user/**'),now(),now());
INSERT INTO SYS_ROLE_MENU (ROLE_ID,MENU_ID,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ((SELECT C.ROLE_ID FROM SYS_ROLE C WHERE C.ROLE_NAME='admin'),
        (SELECT MENU_ID FROM SYS_MENU WHERE MENU_CODE='/common/**'),now(),now());
INSERT INTO SYS_ROLE_MENU (ROLE_ID,MENU_ID,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ((SELECT C.ROLE_ID FROM SYS_ROLE C WHERE C.ROLE_NAME='admin'),
        (SELECT MENU_ID FROM SYS_MENU WHERE MENU_CODE='/menu/**'),now(),now());
/** user角色请求路径配置 **/
INSERT INTO SYS_ROLE_MENU (ROLE_ID,MENU_ID,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ((SELECT C.ROLE_ID FROM SYS_ROLE C WHERE C.ROLE_NAME='user'),
        (SELECT MENU_ID FROM SYS_MENU WHERE MENU_CODE='/user/**'),now(),now());
