
/* --------- documents ----------- */

INSERT INTO documents (code, doc_name) 
VALUES (03, 'Свидетельство о рождении');

INSERT INTO documents (code, doc_name) 
VALUES (07, 'Военный билет');

INSERT INTO documents (code, doc_name) 
VALUES (08, 'Временное удостоверение, выданное взамен военного билета');

INSERT INTO documents (code, doc_name) 
VALUES (10, 'Паспорт иностранного гражданина');

INSERT INTO documents (code, doc_name) 
VALUES (11, 'Свидетельство о рассмотрении ходатайства о признании лица беженцем на территории Российской Федерации по существу');

INSERT INTO documents (code, doc_name) 
VALUES (12, 'Вид на жительство в Российской Федерации');

INSERT INTO documents (code, doc_name) 
VALUES (13, 'Удостоверение беженца');

INSERT INTO documents (code, doc_name) 
VALUES (21, 'Паспорт гражданина Российской Федерации');

/* --------- countries ----------- */

INSERT INTO countries (code, country_name) 
VALUES (643, 'Российская Федерация');

INSERT INTO countries (code, country_name) 
VALUES (624, 'Республика Гвинея-Бисау');

INSERT INTO countries (code, country_name) 
VALUES (612, 'ПИТКЕРН');

INSERT INTO countries (code, country_name) 
VALUES (585, 'Республика Палау');

/* --------- organisations ----------- */

INSERT INTO organisations (name, full_name, inn, kpp, address, phone, is_active)
VALUES ('SomeOrg', 'Some Unknown Organisation', 123654789874, 321654987, 'Vologda, 1-ya Lenina st, 59B', 89057889878, TRUE);

INSERT INTO organisations (name, full_name, inn, kpp, address, phone, is_active)
VALUES ('MockOrg', 'Mocking Organisation', 542634789874, 322544987, 'Samara, Mashinostroenia st, 25A', 89107999878, TRUE);

INSERT INTO organisations (name, full_name, inn, kpp, address, phone, is_active)
VALUES ('MockName', 'Big Mocking Organisation', 54254689874, 322788987, 'Moscow, Blinnaia st, 24A', 89106559878, FALSE );

/* --------- offices ----------- */

INSERT INTO offices (org_id, name, address, phone, is_active)
VALUES (1, 'Central SO office', 'some address 01', 89107785878, true);

INSERT INTO offices (org_id, name, address, phone, is_active)
VALUES (1, 'Peripheral SO office', 'some address 02', 89456325878, true);

INSERT INTO offices (org_id, name, address, phone, is_active)
VALUES (2, 'Central MO office', 'some address 03', 89456987878, true);

INSERT INTO offices (org_id, name, address, phone, is_active)
VALUES (2, 'Peripheral MO office', 'some address 04', 897896545878, true);

/* --------- users ----------- */

INSERT INTO users (office_id, first_name, last_name, middle_name, position, phone, doc_code, doc_number, doc_date, citizenship_code, is_identified)
VALUES (1, 'Petr', 'Koshkin', 'Petrovich', 'director', 89105621245, 21, 4556654789, '2008-02-13', 643, true);

INSERT INTO users (office_id, first_name, last_name, middle_name, position, phone, doc_code, doc_number, doc_date, citizenship_code, is_identified)
VALUES (1, 'Mbanga', 'Gwele', 'Gwelewich', 'worker', 89105645445, 12, 45545454789, '2005-10-14', 624, true);

INSERT INTO users (office_id, first_name, last_name, middle_name, position, phone, doc_code, doc_number, doc_date, citizenship_code, is_identified)
VALUES (2, 'Vitaliy', 'Bogatov', 'Semenovich', 'director', 89105547845, 21, 4556654546, '2010-05-10', 643, true);

INSERT INTO users (office_id, first_name, last_name, middle_name, position, phone, doc_code, doc_number, doc_date, citizenship_code, is_identified)
VALUES (2, 'Ivan', 'Bednov', 'Ivanovich', 'engineer', 89145821245, 21, 4456657989, '2000-01-01', 643, true);

INSERT INTO users (office_id, first_name, last_name, middle_name, position, phone, doc_code, doc_number, doc_date, citizenship_code, is_identified)
VALUES (3, 'Cabron', 'Cabronov', 'Cabronovich', 'manager', 89112321245, 10, 4789657989, '2000-10-10', 585, true);

INSERT INTO users (office_id, first_name, last_name, middle_name, position, phone, doc_code, doc_number, doc_date, citizenship_code, is_identified)
VALUES (3, 'Denis', 'Davidov', 'Davidovich', 'creative designer', 88796521245, 07, 2136757989, '1980-10-10', 643, false);

INSERT INTO users (office_id, first_name, last_name, middle_name, position, phone, doc_code, doc_number, doc_date, citizenship_code, is_identified)
VALUES (4, 'Pahrom', 'Baranov', 'Baranovich', 'HR', 87865521245, 10, 2549857989, '1983-12-09', 612, true);

INSERT INTO users (office_id, first_name, last_name, middle_name, position, phone, doc_code, doc_number, doc_date, citizenship_code, is_identified)
VALUES (4, 'Abdula', 'Sharipov', 'Muhammedovich', 'driver', 87865789245, 13, 2872357989, '1980-10-07', 585, false);

/* --------- accounts ----------- */

INSERT INTO accounts (name, login, password_SHA2_hash, is_activated, activation_code)
VALUES ('Petr', 'petr2008', 'qwerty', TRUE, 'fghyetr54dfr');

INSERT INTO accounts (name, login, password_SHA2_hash, is_activated, activation_code)
VALUES ('Mbanga', 'bingobongo', 'djavaharlalneru', FALSE, 'fghyetr54dfgdfg');

INSERT INTO accounts (name, login, password_SHA2_hash, is_activated, activation_code)
VALUES ('Vitya', 'superVitya', 'pass', TRUE, 'fghye545423dfr');

INSERT INTO accounts (name, login, password_SHA2_hash, is_activated, activation_code)
VALUES ('Vanya', 'asdff', 'vanyapass', TRUE, 'fghye5454dfdffr');

INSERT INTO accounts (name, login, password_SHA2_hash, is_activated, activation_code)
VALUES ('Caby', 'capoeira', 'vivevenesuela', FALSE, 'fghye45jgjghffr');

INSERT INTO accounts (name, login, password_SHA2_hash, is_activated, activation_code)
VALUES ('Dinya', 'mimohojii', 'jenesaispas', FALSE, 'fghyet4545dfdfr');

INSERT INTO accounts (name, login, password_SHA2_hash, is_activated, activation_code)
VALUES ('Paha', 'baran', 'baran1983', TRUE, 'fghy45454dfr');

INSERT INTO accounts (name, login, password_SHA2_hash, is_activated, activation_code)
VALUES ('Dulia', 'mahmud', 'super80', TRUE, 'fghgfgfgf54dfr');
