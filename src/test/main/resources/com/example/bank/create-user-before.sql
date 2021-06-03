delete from LEGAL_ACCOUNT;

delete from LEGAL_PERSON;

delete from CARD;

delete from SCORE;

delete from PEOPLE;

insert into LEGAL_PERSON(address, type, title)
values ('56A49274940F5744', 'AAA', 'Tinkoff'),
       ('9389E5736D205746',  'OOO', 'Sberbank'),
       ('3483938W474947C5', 'OAO', 'Yandex');

insert into LEGAL_ACCOUNT(address, score_number, balance)
values ('56A49274940F5744', '763849348138478', 1488.88),
       ('9389E5736D205746', '018486147512746', 322.22),
       ('3483938W474947C5', '983467346237646', 1337.77);

insert into PEOPLE(passport, name, surname, patronymic, date_of_birth)
values ( '4020794086', 'Leonid', 'Koval', 'Pavlovich', '2001-02-12' );

insert into SCORE(passport, score_number)
values ( '4020794086', '1020304050506060707070605');

insert into CARD (CARD_NUMBER, VALIDITY, CVV, BALANCE, SCORE_NUMBER)
values ('4276160498345534', '2021-12-09',  773, 100.88, '1020304050506060707070605');

