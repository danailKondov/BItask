
/* --------- documents ----------- */

INSERT INTO documents (code, doc_name, version)
VALUES (03, 'Свидетельство о рождении', 0);

INSERT INTO documents (code, doc_name, version)
VALUES (07, 'Военный билет', 0);

INSERT INTO documents (code, doc_name, version)
VALUES (08, 'Временное удостоверение, выданное взамен военного билета', 0);

INSERT INTO documents (code, doc_name, version)
VALUES (10, 'Паспорт иностранного гражданина', 0);

INSERT INTO documents (code, doc_name, version)
VALUES (11, 'Свидетельство о рассмотрении ходатайства о признании лица беженцем на территории Российской Федерации по существу', 0);

INSERT INTO documents (code, doc_name, version)
VALUES (12, 'Вид на жительство в Российской Федерации', 0);

INSERT INTO documents (code, doc_name, version)
VALUES (13, 'Удостоверение беженца', 0);

INSERT INTO documents (code, doc_name, version)
VALUES (21, 'Паспорт гражданина Российской Федерации', 0);

/* --------- countries ----------- */

INSERT INTO countries (code, country_name, version)
VALUES (643, 'Российская Федерация', 0);

INSERT INTO countries (code, country_name, version)
VALUES (624, 'Республика Гвинея-Бисау', 0);

INSERT INTO countries (code, country_name, version)
VALUES (612, 'ПИТКЕРН', 0);

INSERT INTO countries (code, country_name, version)
VALUES (585, 'Республика Палау', 0);

/* --------- organisations ----------- */

INSERT INTO organisations (version, name, full_name, inn, kpp, address, phone, is_active)
VALUES (0, 'SomeOrg', 'Some Unknown Organisation', '123654789874', '321654987', 'Vologda, 1-ya Lenina st, 59B', '89057889878', TRUE);

INSERT INTO organisations (version, name, full_name, inn, kpp, address, phone, is_active)
VALUES (0, 'MockOrg', 'Mocking Organisation', '542634789874', '322544987', 'Samara, Mashinostroenia st, 25A', '89107999878', TRUE);

INSERT INTO organisations (version, name, full_name, inn, kpp, address, phone, is_active)
VALUES (0, 'MockName', 'Big Mocking Organisation', '54254689874', '322788987', 'Moscow, Blinnaia st, 24A', '89106559878', FALSE );

/* --------- offices ----------- */

INSERT INTO offices (version, org_id, name, address, phone, is_active)
VALUES (0, 1, 'Central SO office', 'some address 01', 89107785878, true);

INSERT INTO offices (version, org_id, name, address, phone, is_active)
VALUES (0, 1, 'Peripheral SO office', 'some address 02', 89456325878, true);

INSERT INTO offices (version, org_id, name, address, phone, is_active)
VALUES (0, 2, 'Central MO office', 'some address 03', 89456987878, true);

INSERT INTO offices (version, org_id, name, address, phone, is_active)
VALUES (0, 2, 'Peripheral MO office', 'some address 04', 897896545878, true);

/* --------- users ----------- */

INSERT INTO users (version, office_id, first_name, last_name, middle_name, position, phone, doc_code, doc_number, doc_date, citizenship_code, is_identified)
VALUES (0, 1, 'Petr', 'Koshkin', 'Petrovich', 'director', 89105621245, 21, 4556654789, '2008-02-13', 643, true);

INSERT INTO users (version, office_id, first_name, last_name, middle_name, position, phone, doc_code, doc_number, doc_date, citizenship_code, is_identified)
VALUES (0, 1, 'Mbanga', 'Gwele', 'Gwelewich', 'worker', 89105645445, 12, 45545454789, '2005-10-14', 624, true);

INSERT INTO users (version, office_id, first_name, last_name, middle_name, position, phone, doc_code, doc_number, doc_date, citizenship_code, is_identified)
VALUES (0, 2, 'Vitaliy', 'Bogatov', 'Semenovich', 'director', 89105547845, 21, 4556654546, '2010-05-10', 643, true);

INSERT INTO users (version, office_id, first_name, last_name, middle_name, position, phone, doc_code, doc_number, doc_date, citizenship_code, is_identified)
VALUES (0, 2, 'Ivan', 'Bednov', 'Ivanovich', 'engineer', 89145821245, 21, 4456657989, '2000-01-01', 643, true);

INSERT INTO users (version, office_id, first_name, last_name, middle_name, position, phone, doc_code, doc_number, doc_date, citizenship_code, is_identified)
VALUES (0, 3, 'Cabron', 'Cabronov', 'Cabronovich', 'manager', 89112321245, 10, 4789657989, '2000-10-10', 585, true);

INSERT INTO users (version, office_id, first_name, last_name, middle_name, position, phone, doc_code, doc_number, doc_date, citizenship_code, is_identified)
VALUES (0, 3, 'Denis', 'Davidov', 'Davidovich', 'creative designer', 88796521245, 07, 2136757989, '1980-10-10', 643, false);

INSERT INTO users (version, office_id, first_name, last_name, middle_name, position, phone, doc_code, doc_number, doc_date, citizenship_code, is_identified)
VALUES (0, 4, 'Pahrom', 'Baranov', 'Baranovich', 'HR', 87865521245, 10, 2549857989, '1983-12-09', 612, true);

INSERT INTO users (version, office_id, first_name, last_name, middle_name, position, phone, doc_code, doc_number, doc_date, citizenship_code, is_identified)
VALUES (0, 4, 'Abdula', 'Sharipov', 'Muhammedovich', 'driver', 87865789245, 13, 2872357989, '1980-10-07', 585, false);

/* --------- accounts ----------- */

INSERT INTO accounts (version, name, login, password_SHA2_hash, is_activated, activation_code)
VALUES (0, 'Petr', 'petr2008@gmail.com', 'qwerty', TRUE, 'fghyetr54dfr');

INSERT INTO accounts (version, name, login, password_SHA2_hash, is_activated, activation_code)
VALUES (0, 'Mbanga', 'bingobongo@gmail.com', 'djavaharlalneru', FALSE, 'fghyetr54dfgdfg');

INSERT INTO accounts (version, name, login, password_SHA2_hash, is_activated, activation_code)
VALUES (0, 'Vitya', 'superVitya@gmail.com', 'pass', TRUE, 'fghye545423dfr');

INSERT INTO accounts (version, name, login, password_SHA2_hash, is_activated, activation_code)
VALUES (0, 'Vanya', 'asdff@gmail.com', 'vanyapass', TRUE, 'fghye5454dfdffr');

INSERT INTO accounts (version, name, login, password_SHA2_hash, is_activated, activation_code)
VALUES (0, 'Caby', 'capoeira@gmail.com', 'vivevenesuela', FALSE, 'fghye45jgjghffr');

INSERT INTO accounts (version, name, login, password_SHA2_hash, is_activated, activation_code)
VALUES (0, 'Dinya', 'mimohojii@gmail.com', 'jenesaispas', FALSE, 'fghyet4545dfdfr');

INSERT INTO accounts (version, name, login, password_SHA2_hash, is_activated, activation_code)
VALUES (0, 'Paha', 'baran@gmail.com', 'baran1983', TRUE, 'fghy45454dfr');

INSERT INTO accounts (version, name, login, password_SHA2_hash, is_activated, activation_code)
VALUES (0, 'Dulia', 'mahmud@gmail.com', 'super80', TRUE, 'fghgfgfgf54dfr');
