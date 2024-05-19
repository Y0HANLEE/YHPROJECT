--권한(all):sys에서 실행
GRANT CONNECT, RESOURCE, DBA TO project;

--현재 사용자 테이블 조회
select * from tabs;

-- 자신의 날짜 format형식 확인(날짜 포맷 확인)
select * from nls_session_parameters;

-- 날짜(0000-00-00)으로 포맷 설정
alter session set nls_date_format ='yyyy-MM-dd';

commit;


-- board 테이블 생성 : 게시판
create table board (
    bno number(10,0),
    title varchar2(100) not null,
    content clob not null,
    writer varchar2(10) not null,
    hit number(10,0) default 0,    
    regdate date default sysdate,
    updatedate date default sysdate,
    replycnt number default 0,
    type varchar2(10) default '1'
);
-- 시퀀스 생성
create sequence seq_board start with 1 increment by 1 minvalue 1 maxvalue 999999999999 nocycle nocache;
-- primary key 설정
alter table board add constraint pk_board primary key (bno);
-- foreign key 설정
alter table board add constraint fk_board_user foreign key (writer) references users(userid) on delete cascade; --wriiter → userid
-- 댓글bno와 게시글의bno를 일치시킨다.
update board set replycnt = (select count(rno) from board_reply where board_reply.bno=board.bno); 
-- 조회
select * from board;
commit;
rollback;


-- board_reply 테이블 생성 : 댓글
create table board_reply (
    rno number(10,0),
    bno number(10,0) not null,
    reply clob not null,
    replyer varchar2(10) not null,
    replydate date default sysdate,
    updatedate date default sysdate,
    type varchar2(10) default '1.1'
);
-- 시퀀스 생성
create sequence seq_board_reply start with 1 increment by 1 minvalue 1 maxvalue 999999999999 nocycle nocache;
-- primary key설정
alter table board_reply add constraint pk_board_reply primary key (rno);
-- foreign key설정
alter table board_reply add constraint fk_board_reply foreign key (bno) references board(bno) on delete cascade;
alter table board_reply add constraint fk_reply_board_user foreign key (replyer) references users(userid); --replyer → userid
-- 인덱스 설정
create index idx_board_reply on board_reply(bno desc, rno asc);
-- 조회
select * from board_reply;
commit;
rollback;


-- board_attach 테이블 생성 : 첨부파일 
create table board_attach(
    uuid varchar2(100) not null,
    uploadpath varchar(200) not null,
    fileName varchar(100) not null,
    filetype char(1) default 'I', --Image 혹은 일반으로 구분
    bno number(10,0)
);
-- primary key 설정
alter table board_attach add constraint pk_board_attach primary key(uuid);
-- foreign key 설정
alter table board_attach add constraint fk_board_attach foreign key (bno) references board(bno); 
-- 조회
select * from board_attach;
commit;
rollback;




-- album 테이블 생성 : 사진게시판
create table album(
    ano number(10,0),
    writer varchar2(10) not null,
    hit number(10,0) default 0,    
    replycnt number default 0,
    content clob not null,
    location varchar2 (100),
    title varchar2(100) not null,
    startdate date,
    enddate date,
    regdate date default sysdate,
    updatedate date default sysdate,
    type varchar2(10) default '2'
);
-- 시퀀스 생성
create sequence seq_album start with 1 increment by 1 minvalue 1 maxvalue 999999999999 nocycle nocache;
-- primary key 설정
alter table album add constraint pk_album primary key (ano);
-- foreign key 설정
alter table album add constraint fk_album_user foreign key (writer) references users(userid) on delete cascade; --wriiter → userid
-- 댓글ano와 게시글의ano를 일치시킨다.
update album set replycnt = (select count(rno) from album_reply where album_reply.ano=album.ano);
-- 조회
select * from album;
commit;
rollback;
delete from album;


-- album_reply 테이블 생성 : 댓글
create table album_reply (
    rno number(10,0),
    ano number(10,0) not null,
    reply clob not null,
    replyer varchar2(10) not null,
    replydate date default sysdate,
    updatedate date default sysdate,
    type varchar2(10) default '2.1'    
);
-- 시퀀스 생성
create sequence seq_album_reply start with 1 increment by 1 minvalue 1 maxvalue 999999999999 nocycle nocache;
-- primary key설정
alter table album_reply add constraint pk_album_reply primary key (rno);
-- foreign key설정
alter table album_reply add constraint fk_reply_album foreign key (ano) references album(ano); -- reply.ano → album.ano
alter table album_reply add constraint fk_reply_album_user foreign key (replyer) references users(userid); --replyer → userid
-- 인덱스 설정
create index idx_album_reply on album_reply(ano asc, rno asc);
-- 조회
select * from album_reply;
commit;
rollback;

-- album_attach 테이블 생성 : 첨부파일 
create table album_attach(
    uuid varchar2(100) not null,
    uploadpath varchar(200) not null,        
    filename varchar(100) not null,
    filetype char(1) default 'I', --Image 혹은 일반으로 구분
    ano number(10,0)
);
-- primary key 설정
alter table album_attach add constraint pk_album_attach primary key(uuid);
-- foreign key 설정
alter table album_attach add constraint fk_album_attach foreign key (ano) references album(ano); 
-- 조회
select * from album_attach;
commit;
rollback;

select * from album_attach where uploadPath = to_char(sysdate-6, 'yyyy\MM\dd');

--users 테이블생성 : 회원
create table users(
    uno number(10,0),
    userid varchar2(10) not null,
    userpw varchar2(100) not null,
    name varchar2(20) not null,    
    gender char(1) default 'M' not null,
    phone varchar2(20),    
    email varchar2(20) not null,       
    zonecode varchar2(10),
    address varchar2(100),    
    addressdetail varchar2(100),
    birth date,
    regdate date default sysdate,
    updatedate date default sysdate,
    enabled char(1) default '1'
);
-- 시퀀스 생성
create sequence seq_users start with 5 increment by 1 minvalue 1 maxvalue 999999999999 nocycle nocache;
-- primary key 설정
alter table users add constraint pk_users primary key(userid);
-- 조회
select * from users;    
commit;
rollback;

create table checkMail(
    email varchar2(20) not null,
    checkStr varchar2(10) not null
);
select * from checkMail;

select * from profile where userid = 'user002';

-- auth 테이블 생성 : 회원 권한
create table auth(
    userid varchar2(10) not null,
    auth varchar2(15) not null
);  
-- foriegn key 설정
alter table auth add constraint fk_users_auth foreign key(userid) references users(userid) on delete cascade;
-- 조회
select * from auth;
commit;
rollback;

-- persistent_logins 테이블 생성 : 자동로그인 세션유지상태 확인용
create table persistent_logins(
    username varchar(64) not null,
    series varchar2(64) primary key,
    token varchar2(64) not null,
    last_used timestamp not null
);
-- 조회
select * from persistent_logins;
commit;
rollback;

-- user/auth 함께 조회
select u.userid, userpw, name, gender, phone, email, address, birth, regdate, updatedate, auth 
from users u left outer join auth a on u.userid=a.userid where u.userid = 'admin1';

--board import
INSERT INTO board (bno, title, content, writer) SELECT seq_board.nextval, title, content, writer FROM im_board;
INSERT INTO album (ano, title, content, writer) SELECT seq_album.nextval, title, content, writer FROM im_album;


--intro table생성
create table intro (
    boardtype number(1) not null, -- 1:homePage / 2:introPage
    title_title varchar2(100) not null,
    title_intro clob,
    map_title varchar2(100),
    map_intro clob,
    map_caption varchar2(100),
    map_address varchar2(100),
    map_addressdetail varchar2(100),
    intro clob    
);
-- 업데이트 기능만 제공하기 때문에 기본 제목은 기입.
insert into intro (boardtype, title_title) values (1, '홈화면 임시제목입니다.');
insert into intro (boardtype, title_title) values (2, '인트로화면 임시제목입니다.');
-- 조회
select * from intro;
select * from intro_attach;


-- profile 테이블 생성
create table profile (
    userid varchar2(10) not null,
    uuid varchar2(100) not null,
    uploadpath varchar(200) not null,        
    filename varchar(100) not null,
    filetype char(1) default 'I'
);
-- primary key 설정
alter table profile add constraint pk_profile primary key (userid);
-- foreign key 설정
alter table profile add constraint fk_profile_users foreign key (userid) references users(userid) on delete cascade; --userid → userid
-- 조회
select * from profile;


-- intro_attach 테이블 생성
create table intro_attach (
    boardtype number(1) not null, -- 1:homePage / 2:introPage
    uuid varchar2(100) not null,
    uploadpath varchar(200) not null,        
    filename varchar(100) not null,
    filetype char(1) default 'I'
);
-- primary key 설정
alter table intro_attach add constraint pk_intro_attach primary key (uuid);
-- foreign key 설정
alter table intro_attach add constraint fk_intro_attach foreign key (boardtype) references intro(boardtype); --userid → userid
-- 조회
select * from intro_attach;