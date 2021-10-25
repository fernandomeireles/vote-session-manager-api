create table if not exists `user` (

  id_user int(11) not null auto_increment,
  des_cpf varchar(20) null default '',
  dat_creation datetime,
  dat_update datetime,
  flg_active smallint,
  primary key  (id_user),
  index(id_user)

);


create table if not exists `subject` (

  id_subject int(11) not null auto_increment,
  des_subject varchar(20) null default '',
  dat_creation datetime,
  dat_update datetime,
  flg_active smallint,
  primary key  (id_subject),
  index(id_subject)

);

create table if not exists `session` (

  id_session int(11) not null auto_increment,
  id_subject int(11) not null,
  num_duration int(11) not null,
  dat_end_session datetime,
  dat_creation datetime,
  dat_update datetime,
  flg_active smallint,
  primary key  (id_session),
  index(id_session),
  foreign key (id_subject)
  references subject(id_subject)

);


create table if not exists vote (

  id_vote int(11) not null auto_increment,
  id_user int(11) not null,
  id_session int(11) not null,
  flg_valid smallint,
  flg_approved_session smallint,
  dat_creation datetime,
  dat_update datetime,
  primary key  (id_vote),
  index(id_vote),
  foreign key (id_session)
  references session(id_session),
  foreign key (id_user)
  references user(id_user)

);


create table if not exists result_vote_session (

  id_result_session int(11) not null auto_increment,
  id_session int(11) not null,
  num_positive_vote int(11),
  num_negative_vote int(11),
  result_session varchar(50) not null,
  dat_creation datetime,
  dat_update datetime,
  flg_active smallint,
  primary key  (id_result_session),
  index(id_result_session),
  foreign key (id_session)
  references session(id_session)

);