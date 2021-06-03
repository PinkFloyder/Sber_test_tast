drop table if exists CARD;
drop table if exists SCORE;
drop table if exists PEOPLE;

drop table if exists LEGAL_ACCOUNT;
drop table if exists LEGAL_PERSON;


create table PEOPLE
(
    PASSPORT      VARCHAR not null,
    NAME          VARCHAR,
    SURNAME       VARCHAR,
    PATRONYMIC    VARCHAR,
    DATE_OF_BIRTH DATE,
    constraint PK_PEOPLE_PASSPORT
        primary key (PASSPORT)
);

create table SCORE
(
    PASSPORT     VARCHAR not null,
    SCORE_NUMBER VARCHAR not null,
    constraint PK_PHYSICAL_ACCOUNT
        primary key (PASSPORT, SCORE_NUMBER),
    constraint FK_PHYSICAL_ACCOUNT_PASSPORT
        foreign key (PASSPORT) references PEOPLE (PASSPORT)
);

create table CARD
(
    CARD_NUMBER  VARCHAR not null,
    VALIDITY     DATE,
    CVV          INT,
    BALANCE      FLOAT8,
    SCORE_NUMBER VARCHAR,
    constraint PK_CARD_NUMBER
        primary key (CARD_NUMBER),
    constraint FK_CARD_NUMBER_SCORE
        foreign key (SCORE_NUMBER) references SCORE (SCORE_NUMBER)
);

create table LEGAL_PERSON
(
    ADDRESS VARCHAR not null,
    TYPE    VARCHAR,
    TITLE   VARCHAR,
    constraint PK_LEGAL_PERSON_ADDRESS
        primary key (ADDRESS)
);

create table LEGAL_ACCOUNT
(
    ADDRESS      VARCHAR,
    SCORE_NUMBER VARCHAR not null,
    BALANCE      FLOAT8,
    constraint PK_LEGAL_ACCOUNT_SCORE_NUMBER
        primary key (SCORE_NUMBER),
    constraint FK_LEGAL_ACCOUNT_LEGAL_PERSON_ADDRESS
        foreign key (ADDRESS) references LEGAL_PERSON (ADDRESS)
);