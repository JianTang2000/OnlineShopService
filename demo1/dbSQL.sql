create table DEMO_USER
(
    USER_ID             NUMBER(9,0)          not null,  --主键 1001起，admin 1 gust 2
    USER_NAME           VARCHAR2(120)        not null,
    STATE               CHAR(1)              not null,--0 1 2
    CREATE_DATE         DATE                 not null,
    STATE_DATE          DATE                 not null,
    EMAIL               VARCHAR2(40)        not null,
    LOST_FOUND_KEY      NUMBER(9,0)    not null,
    PHONE_NUMBER        NUMBER(20,0)    not null,
    PASS_WORD           VARCHAR2(40)    not null,
    CONSTRAINT EMAIL UNIQUE (EMAIL),
    CONSTRAINT PHONE_NUMBER UNIQUE (PHONE_NUMBER),
    constraint PK_DEMO_USER primary key (USER_ID)
)
---------------------------------
create table DEMO_USER_DETAIL
(
    USER_ID                 NUMBER(9,0)          not null,
    USER_DETAIL             BLOB          not null,
    ADDRESS_1_LINE_1          VARCHAR2(120)    ,
    ADDRESS_1_LINE_2          VARCHAR2(120)    ,
    ADDRESS_1_POST_CODE     VARCHAR2(40)        ,
    ADDRESS_2_LINE_1          VARCHAR2(120)    ,
    ADDRESS_2_LINE_2          VARCHAR2(120)    ,
    ADDRESS_2_POST_CODE     VARCHAR2(40)      ,
    ADDRESS_3_LINE_1          VARCHAR2(120)    ,
    ADDRESS_3_LINE_2          VARCHAR2(120)    ,
    ADDRESS_3_POST_CODE     VARCHAR2(40)      ,
    constraint PK_DEMO_USER_DETAIL primary key (USER_ID),
    CONSTRAINT FK_USER_ID FOREIGN KEY(USER_ID) REFERENCES DEMO_USER(USER_ID)
)
--drop table DEMO_USER_DETAIL;
---------------------------------

create table DEMO_RESOURCE
(
    RESOURCE_ID         NUMBER(9,0)          not null,  --主键
    RESOURCE_NAME       VARCHAR2(120)        not null,
    USER_ID             NUMBER(9,0)          not null,
    USER_NAME           VARCHAR2(120)        not null,
    RESOURCE_DESC       VARCHAR2(1500),
    CREATE_DATE         DATE                 not null,
    DELIVER_TYPE        CHAR(1)              not null,--0 无需 1快递
    DELIVER_PRICE       NUMBER(5,0)          not null, --一元就是100，延长两位，以避免使用浮点数
    PRICE               NUMBER(9,0)          not null, --一元就是100，延长两位，以避免使用浮点数
    CLASSIFICATION      CHAR(2)              not null, --1--12，见下文注释
    constraint PK_RESOURCE_ID primary key (RESOURCE_ID),
    CONSTRAINT FK_DEMO_RESOURCE_USER_ID FOREIGN KEY(USER_ID) REFERENCES DEMO_USER(USER_ID)
)
--(1)男装/女装  CLASSIFICATION
--(2)鞋靴/箱包
--(3)童装玩具
--(4)家电/数码
--(5)美妆/洗护
--(6)美食/生鲜
--(7)运动/户外
--(8)工具/建材
--(9)家具/家饰
--(10)汽车/用品
--(11)百货/餐厨
--(12)学习/卡券

---------------------------------
create table DEMO_RESOURCE_DETAIL
(
    RESOURCE_ID         NUMBER(9,0)          not null,  --主键
    STOCK               NUMBER(9,0)         not null,
    HOT                 NUMBER(9,0),
    PHOTO_One             BLOB        ,
    PHOTO_Two           BLOB        ,
    PHOTO_Three             BLOB        ,
    PHOTO_Four             BLOB        ,
    PHOTO_Five             BLOB        ,
    PHOTO_DETAIL        BLOB        ,
    constraint PK_DEMO_RESOURCE_DETAIL primary key (RESOURCE_ID),
    CONSTRAINT FK_RESOURCE_DETAIL_RESOURCE_ID FOREIGN KEY(RESOURCE_ID) REFERENCES DEMO_RESOURCE(RESOURCE_ID)
)

---------------------------------
---------------------------------

create table USER_BASKET
(
    USER_ID            NUMBER(9,0)          not null,
    RESOURCE_ID        NUMBER(9,0)          not null,
    ITEM_NUM           NUMBER(4,0)          not null,
    CONSTRAINT FK_USER_BASKET_RESOURCE_ID FOREIGN KEY(RESOURCE_ID) REFERENCES DEMO_RESOURCE(RESOURCE_ID),
    CONSTRAINT FK_USER_BASKET_USER_ID FOREIGN KEY(USER_ID) REFERENCES DEMO_USER(USER_ID)
)
---------------------------------
create table PRE_ORDER
(
    USER_ID            NUMBER(9,0)          not null,
    RESOURCE_ID        NUMBER(9,0)          not null,
    ITEM_NUM           NUMBER(4,0)          not null,
    DELIVER_TYPE       VARCHAR2(20)             not null,
    DELIVER_TIME       VARCHAR2(20)              not null,
    ADDRESS_TYPE       VARCHAR2(20)          ,
    CONSTRAINT FK_USER_PRE_ORDER_RESOURCE_ID FOREIGN KEY(RESOURCE_ID) REFERENCES DEMO_RESOURCE(RESOURCE_ID),
    CONSTRAINT FK_USER_PRE_ORDER_USER_ID FOREIGN KEY(USER_ID) REFERENCES DEMO_USER(USER_ID)
);

---------------------------------
create table USER_ORDER
(
    USER_ID            NUMBER(9,0)          not null,
    RESOURCE_ID        NUMBER(9,0)          not null,
    ITEM_NUM           NUMBER(4,0)          not null,
    DELIVER_TYPE       VARCHAR2(20)         not null,
    DELIVER_TIME       VARCHAR2(20)         not null,
    ADDRESS_TYPE       VARCHAR2(20)         ,
    EXTRA_COST NUMBER(9,0)          ,
    DISCOUNT NUMBER(9,0)          ,
    ORDER_COMMENT VARCHAR2(200)          ,
    CONSTRAINT FK_USER_ORDER_RESOURCE_ID FOREIGN KEY(RESOURCE_ID) REFERENCES DEMO_RESOURCE(RESOURCE_ID),
    CONSTRAINT FK_USER_ORDER_USER_ID FOREIGN KEY(USER_ID) REFERENCES DEMO_USER(USER_ID)
);

---------------------------------
create table USER_ORDER_HIS
(
    USER_ID            NUMBER(9,0)          not null,
    RESOURCE_ID        NUMBER(9,0)          not null,
    ITEM_NUM           NUMBER(4,0)          not null,
    DELIVER_TYPE       VARCHAR2(20)         not null,
    DELIVER_TIME       VARCHAR2(20)         not null,
    ADDRESS_TYPE       VARCHAR2(20)         ,
    EXTRA_COST        NUMBER(9,0)          ,
    DISCOUNT          NUMBER(9,0)          ,
    ORDER_COMMENT   VARCHAR2(200)          ,
    STATE           VARCHAR2(10)       not null, --A saler canceled ;B user canceled ;C user paid ;D saler closed
    STATE_COMMENT_1 VARCHAR2(200)          ,
    STATE_COMMENT_2 VARCHAR2(200)          ,
    STATE_COMMENT_3 VARCHAR2(200)          ,
    STATE_COMMENT_4 VARCHAR2(200)          ,
    CREATE_DATE         DATE               ,
    CONSTRAINT FK_USER_ORDER_HIS_RESOURCE_ID FOREIGN KEY(RESOURCE_ID) REFERENCES DEMO_RESOURCE(RESOURCE_ID),
    CONSTRAINT FK_USER_ORDER__HIS_USER_ID FOREIGN KEY(USER_ID) REFERENCES DEMO_USER(USER_ID)
);

create sequence HIS_AND_PAID_ORDER_ID   --创建序列名称
    increment by 1  --增长幅度
    start with 100  --初始值
    maxvalue 9999999999;  --最大值
--select HIS_AND_PAID_ORDER_ID.nextval from dual;
--DROP SEQUENCE HIS_AND_PAID_ORDER_ID;

create sequence SEQ_USER_ID   --创建序列名称
    increment by 1  --增长幅度
    start with 2000  --初始值
    maxvalue 9999999999;  --最大值

create sequence SEQ_LOST_KEY   --创建序列名称
    increment by 1  --增长幅度
    start with 2100  --初始值
    maxvalue 9999999999;  --最大值

create sequence SEQ_RESOURCE_ID   --创建序列名称
    increment by 1  --增长幅度
    start with 2100  --初始值
    maxvalue 9999999999;  --最大值
select SEQ_RESOURCE_ID.nextval from dual;