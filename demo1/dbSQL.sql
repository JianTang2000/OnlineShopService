create table DEMO_USER
(
    USER_ID             NUMBER(9,0)          not null,  --主键
    USER_NAME           VARCHAR2(120)        not null,
    STATE               CHAR(1)              not null,--0 1 2
    CREATE_DATE         DATE                 not null,
    STATE_DATE          DATE                 not null,
    EMAIL               VARCHAR2(120)        not null,
    LOST_FOUND_KEY      NUMBER(9,0)    not null,
    PHONE_NUMBER        NUMBER(11,0)    not null,
    constraint PK_DEMO_USER primary key (USER_ID)
)
alter table DEMO_USER add (PASS_WORD VARCHAR2(20) );
alter table DEMO_USER modify PASS_WORD not null;


ALTER TABLE DEMO_USER ADD CONSTRAINT EMAIL UNIQUE (EMAIL);  --创建唯一约束
ALTER TABLE DEMO_USER ADD CONSTRAINT PHONE_NUMBER UNIQUE (PHONE_NUMBER);  --创建唯一约束
select * from DEMO_USER;
insert into DEMO_USER
(USER_ID,USER_NAME, STATE, CREATE_DATE, STATE_DATE, EMAIL, LOST_FOUND_KEY,PHONE_NUMBER)
values(1,'admin','A', to_date('2019-06-08 20:20:59','yyyy-mm-dd hh24:mi:ss'),
       to_date('2019-06-08 20:20:59','yyyy-mm-dd hh24:mi:ss'), 'admin@163.com', 201314947, 07720824472);
insert into DEMO_USER
(USER_ID,USER_NAME, STATE, CREATE_DATE, STATE_DATE, EMAIL, LOST_FOUND_KEY,PHONE_NUMBER)
values(2,'gust','A', to_date('2019-06-08 20:20:59','yyyy-mm-dd hh24:mi:ss'),
       to_date('2019-06-08 20:20:59','yyyy-mm-dd hh24:mi:ss'), 'gust@163.com', 201314948, 07720824473);
insert into DEMO_USER
(USER_ID,USER_NAME, STATE, CREATE_DATE, STATE_DATE, EMAIL, LOST_FOUND_KEY,PHONE_NUMBER)
values(1001,'Clark Kent','A', to_date('2019-06-08 20:20:59','yyyy-mm-dd hh24:mi:ss'),
       to_date('2019-06-08 20:20:59','yyyy-mm-dd hh24:mi:ss'), 'jiantang2000@163.com', 201314949, 07720824471);
insert into DEMO_USER
(USER_ID,USER_NAME, STATE, CREATE_DATE, STATE_DATE, EMAIL, LOST_FOUND_KEY,PHONE_NUMBER)
values(1002,'Jian Tang','A', to_date('2019-06-08 20:20:59','yyyy-mm-dd hh24:mi:ss'),
       to_date('2019-06-08 20:20:59','yyyy-mm-dd hh24:mi:ss'), '641228269@qq.com', 201314950, 17712914087);
---------------------------------
create table DEMO_USER_DETAIL
(
    USER_ID                 NUMBER(9,0)          not null,
    USER_DETAIL             BLOB          not null,
    ADDRESS_1_LINE_1          VARCHAR2(120)    ,
    ADDRESS_1_LINE_2          VARCHAR2(120)    ,
    ADDRESS_1_POST_CODE     VARCHAR2(7)        ,
    ADDRESS_2_LINE_1          VARCHAR2(120)    ,
    ADDRESS_2_LINE_2          VARCHAR2(120)    ,
    ADDRESS_2_POST_CODE     VARCHAR2(120)      ,
    ADDRESS_3_LINE_1          VARCHAR2(120)    ,
    ADDRESS_3_LINE_2          VARCHAR2(120)    ,
    ADDRESS_3_POST_CODE     VARCHAR2(120)      ,
    constraint PK_DEMO_USER_DETAIL primary key (USER_ID),
    CONSTRAINT FK_USER_ID FOREIGN KEY(USER_ID) REFERENCES DEMO_USER(USER_ID)
)
drop table DEMO_USER_DETAIL;
select * from DEMO_USER_DETAIL;
---------------------------------
create table DEMO_RESOURCE
(
    RESOURCE_ID         NUMBER(9,0)          not null,  --主键
    RESOURCE_NAME       VARCHAR2(120)        not null,
    USER_ID             NUMBER(9,0)          not null,
    USER_NAME           VARCHAR2(120)        not null,
    RESOURCE_DESC       VARCHAR2(1000),
    CREATE_DATE         DATE                 not null,
    DELIVER_TYPE        CHAR(1)              not null,--0 无需 1快递
    DELIVER_PRICE       NUMBER(5,0)          not null, --延长两位，以避免使用浮点数
    PRICE               NUMBER(9,0)          not null,
    constraint PK_RESOURCE_ID primary key (RESOURCE_ID),
    CONSTRAINT FK_DEMO_RESOURCE_USER_ID FOREIGN KEY(USER_ID) REFERENCES DEMO_USER(USER_ID)
)
alter table DEMO_RESOURCE add (CLASSIFICATION CHAR(2) );
alter table DEMO_RESOURCE modify CLASSIFICATION not null;
select * from  DEMO_RESOURCE;
drop table DEMO_RESOURCE;
delete  DEMO_RESOURCE where RESOURCE_ID > 100;
alter table DEMO_RESOURCE modify RESOURCE_DESC VARCHAR2(1000);

insert into DEMO_RESOURCE
(RESOURCE_ID,RESOURCE_NAME, USER_ID, USER_NAME, RESOURCE_DESC, CREATE_DATE, DELIVER_TYPE,DELIVER_PRICE, PRICE)
values(1101,'伸缩网线',1002, 'Jian Tang', '伸缩网线，1.5米长，办公神器',
       to_date('2019-06-08 20:20:59','yyyy-mm-dd hh24:mi:ss'), '1', 0, 100);
insert into DEMO_RESOURCE
(RESOURCE_ID,RESOURCE_NAME, USER_ID, USER_NAME, RESOURCE_DESC, CREATE_DATE, DELIVER_TYPE,DELIVER_PRICE, PRICE)
values(1102,'超极本网线转接口',1002, 'Jian Tang', '网线转接口，包含三个USB接口和一个网线接口，适用于各种超极本，包括MAC，DELL XPS等',
       to_date('2019-06-08 20:20:59','yyyy-mm-dd hh24:mi:ss'), '1', 0, 500);
insert into DEMO_RESOURCE
(RESOURCE_ID,RESOURCE_NAME, USER_ID, USER_NAME, RESOURCE_DESC, CREATE_DATE, DELIVER_TYPE,DELIVER_PRICE, PRICE)
values(1103,'家庭版黄焖鸡米饭',1001, 'Clark Kent', '现做家庭版黄么鸡米饭，纯鸡腿肉，一人份量，请提前一小时约定以便制作，上门自取',
       to_date('2019-06-08 20:20:59','yyyy-mm-dd hh24:mi:ss'), '1', 0, 500);
insert into DEMO_RESOURCE
(RESOURCE_ID,RESOURCE_NAME, USER_ID, USER_NAME, RESOURCE_DESC, CREATE_DATE, DELIVER_TYPE,DELIVER_PRICE, PRICE)
values(1000,'网站使用申请',1, 'admin', '获取一个网站会员账号，使用此账号登录网站，使用网站的购买/出售商品等功能',
       to_date('2019-06-08 20:20:59','yyyy-mm-dd hh24:mi:ss'), '1', 0, 0);
insert into DEMO_RESOURCE
(RESOURCE_ID,RESOURCE_NAME, USER_ID, USER_NAME, RESOURCE_DESC, CREATE_DATE, DELIVER_TYPE,DELIVER_PRICE, PRICE)
values(1001,'建站申请',1, 'admin', '获取一个管理员账号，使用网站全部功能，买卖商品，审核上新商品，封禁/解封账号，开创新会员账号等。还可以获取网站源码/在公网内的搭建方式，可直接获取开发人员搭建好的网站域名，可根据个人需求，使用不同的主题/皮肤/风格，自由的增删改网站功能。总的来说就是从代码级别获取一个你自己的电商网站，有开发人员协助搭建修改，做你想做的事情',
       to_date('2019-06-08 20:20:59','yyyy-mm-dd hh24:mi:ss'), '1', 0, 50000);
---------------------------------
create table DEMO_RESOURCE_DETAIL
(
    RESOURCE_ID         NUMBER(9,0)          not null,  --主键
    STOCK               NUMBER(9,0)         not null,
    HOT                 NUMBER(9,0),
    PHOTO_1             BLOB        ,
    PHOTO_2             BLOB        ,
    PHOTO_3             BLOB        ,
    PHOTO_4             BLOB        ,
    PHOTO_5             BLOB        ,
    PHOTO_DETAIL        BLOB        ,
    constraint PK_DEMO_RESOURCE_DETAIL primary key (RESOURCE_ID),
    CONSTRAINT FK_RESOURCE_DETAIL_RESOURCE_ID FOREIGN KEY(RESOURCE_ID) REFERENCES DEMO_RESOURCE(RESOURCE_ID)
)
drop table DEMO_RESOURCE_DETAIL;
SELECT * FROM DEMO_RESOURCE_DETAIL;
---------------------------------
create table COMMENT_DETAIL
(
    COMMENT_ID          NUMBER(9,0)          not null,
    RESOURCE_ID         NUMBER(9,0)          not null,
    USER_ID             NUMBER(9,0)          not null,
    COMMENT_LEVEL       CHAR(1)              not null, -- 0-5星
    COMMENT_CONTENT     VARCHAR2(500)       not null, -- 0-5星
    constraint PK_COMMENT primary key (COMMENT_ID),
    CONSTRAINT FK_COMMENT_RESOURCE_ID FOREIGN KEY(RESOURCE_ID) REFERENCES DEMO_RESOURCE(RESOURCE_ID),
    CONSTRAINT FK_COMMENT_USER_ID FOREIGN KEY(USER_ID) REFERENCES DEMO_USER(USER_ID)
)
SELECT * FROM COMMENT_DETAIL;
---------------------------------
create table USER_BASKET
(
    BASKET_ID          NUMBER(9,0)          not null,
    USER_ID            NUMBER(9,0)          not null,
    RESOURCE_ID        NUMBER(9,0)          not null,
    ITEM_NUM           NUMBER(4,0)          not null,
    constraint PK_USER_BASKET primary key (BASKET_ID),
    CONSTRAINT FK_USER_BASKET_RESOURCE_ID FOREIGN KEY(RESOURCE_ID) REFERENCES DEMO_RESOURCE(RESOURCE_ID),
    CONSTRAINT FK_USER_BASKET_USER_ID FOREIGN KEY(USER_ID) REFERENCES DEMO_USER(USER_ID)
)
---------------------------------
create table USER_ORDER
(
    ORDER_ID          NUMBER(9,0)        not null,
    USER_ID           NUMBER(9,0)       not null,
    RESOURCE_ID       NUMBER(9,0)       not null,
    CREATE_DATE       DATE             not null,
    DELIVER_INFO     VARCHAR2(100)       not null,
    ADDRESS_INFO     VARCHAR2(100)       not null,
    constraint PK_USER_ORDER primary key (ORDER_ID),
    CONSTRAINT FK_USER_ORDER_RESOURCE_ID FOREIGN KEY(RESOURCE_ID) REFERENCES DEMO_RESOURCE(RESOURCE_ID),
    CONSTRAINT FK_USER_ORDER_USER_ID FOREIGN KEY(USER_ID) REFERENCES DEMO_USER(USER_ID)
)
---------------------------------
create table USER_ORDER_HIS
(
    ORDER_ID          NUMBER(9,0)        not null,
    USER_ID           NUMBER(9,0)       not null,
    RESOURCE_ID       NUMBER(9,0)       not null,
    CREATE_DATE       DATE             not null,
    FINISH_DATA       DATE             not null,
    DELIVER_INFO     VARCHAR2(100)       not null,
    ADDRESS_INFO     VARCHAR2(100)       not null
)