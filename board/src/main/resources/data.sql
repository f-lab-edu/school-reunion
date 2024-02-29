-- School 데이터 삽입
INSERT INTO school (id, name) VALUES (1, 'School A');
INSERT INTO school (id, name) VALUES (2, 'School B');

-- Member 데이터 삽입
INSERT INTO member (id, email, name) VALUES (1, 'member1@example.com', 'Member 1');
INSERT INTO member (id, email, name) VALUES (2, 'member2@example.com', 'Member 2');

-- SchoolMember 데이터 삽입
INSERT INTO school_member (id, school_id, member_id) VALUES (1, 1, 1);
INSERT INTO school_member (id, school_id, member_id) VALUES (2, 2, 2);

-- Reunion 데이터 삽입
INSERT INTO reunion (id, school_id, grade, year) VALUES (1, 1, 10, 2023);
INSERT INTO reunion (id, school_id, grade, year) VALUES (2, 2, 12, 2022);

-- GradeYear 데이터 삽입
INSERT INTO grade_year (id, school_member_id, grade, year) VALUES (1, 1, 10, 2023);
INSERT INTO grade_year (id, school_member_id, grade, year) VALUES (2, 2, 12, 2022);

-- Board 데이터 삽입
INSERT INTO board (id, reunion_id, member_id, title, content) VALUES (1, 1, 1, 'Title 1', 'Content 1');
INSERT INTO board (id, reunion_id, member_id, title, content) VALUES (2, 2, 2, 'Title 2', 'Content 2');