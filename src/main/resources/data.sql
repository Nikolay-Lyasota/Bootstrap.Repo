begin;
insert into table_u (age, email, name, password) values (1,  'admin@admin.ru', 'admin', '$2a$05$YVTbu74su4/MeWZ3cjgzX.H.fv5DToJ1OVcJzJcXzMnF9E4DS3Bmq');
insert into table_roles (role) values ('ADMIN');
insert into table_u_roles (User_id, roles_id) values (1, 1);
insert into table_roles (role) values ('USER');
insert into table_u_roles (User_id, roles_id) values (1, 2);
commit;
begin;
insert into table_u (age, email, name, password) values (1,  'user@user.ru', 'user', '$2a$10$fIOMiEncvCffQvS5d08mJeXl1EPAgzkr3Ar6CIJba9KvXs/XH22EK');
insert into table_u_roles (User_id, roles_id) values (2, 2);
commit;