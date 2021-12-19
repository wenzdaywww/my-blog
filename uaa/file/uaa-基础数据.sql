/**1、插入用户信息 **/
DELETE FROM SYS_USER WHERE 1=1;
INSERT INTO SYS_USER (USER_ID, USER_NAME, PASS_WORD,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('admin', '超级管理员', '$2a$10$.jycUozQpxha24tWaUGwFOKMI.BpZk1jw1vqydu7Q.uAnvc9wkVwy',now(),now());/**www362412**/
INSERT INTO SYS_USER (USER_ID, USER_NAME, PASS_WORD,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('www', 'wenzday', '$2a$10$.jycUozQpxha24tWaUGwFOKMI.BpZk1jw1vqydu7Q.uAnvc9wkVwy',now(),now());/**www362412**/

/**2、插入角色信息 **/
DELETE FROM SYS_ROLE WHERE 1=1;
INSERT INTO SYS_ROLE (ROLE_CODE,ROLE_NAME,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('admin','管理员',now(),now());
INSERT INTO SYS_ROLE (ROLE_CODE,ROLE_NAME,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('user','用户',now(),now());
INSERT INTO SYS_ROLE (ROLE_CODE,ROLE_NAME,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('test','测试',now(),now());

/**3、插入用户角色信息 **/
DELETE FROM SYS_USER_ROLE WHERE 1=1;
INSERT INTO SYS_USER_ROLE (SU_ID, ROLE_ID,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ((select c.SU_ID from SYS_USER c where c.USER_ID='admin'), (select c.ROLE_ID from sys_role c where c.ROLE_CODE='admin'),now(),now());
INSERT INTO SYS_USER_ROLE (SU_ID, ROLE_ID,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ((select c.SU_ID from SYS_USER c where c.USER_ID='www'), (select c.ROLE_ID from sys_role c where c.ROLE_CODE='user'),now(),now());
/**4、插入用户角色信息 **/
DELETE FROM OAUTH_CLIENT_DETAILS WHERE 1=1;
INSERT INTO OAUTH_CLIENT_DETAILS (CLIENT_ID, RESOURCE_IDS,CLIENT_SECRET,SCOPE,AUTHORIZED_GRANT_TYPES,
                                  WEB_SERVER_REDIRECT_URI,ACCESS_TOKEN_VALIDITY,REFRESH_TOKEN_VALIDITY,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('blog','my-blog','$2a$10$LHRecr6QgeDNU5WAHw6QCOgXRlhpuHyx9Gczyj0vv17kMcqjVctIO','all','authorization_code,password,client_credentials,implicit,refresh_token',
        '',3600,604800,now(),now());
INSERT INTO OAUTH_CLIENT_DETAILS (CLIENT_ID, RESOURCE_IDS,CLIENT_SECRET,SCOPE,AUTHORIZED_GRANT_TYPES,
                                  WEB_SERVER_REDIRECT_URI,ACCESS_TOKEN_VALIDITY,REFRESH_TOKEN_VALIDITY,SYS_UPDATE_TIME,SYS_CREATE_TIME)
VALUES ('admin','my-admin','$2a$10$LHRecr6QgeDNU5WAHw6QCOgXRlhpuHyx9Gczyj0vv17kMcqjVctIO','all','authorization_code,password,client_credentials,implicit,refresh_token',
        '',3600,604800,now(),now());