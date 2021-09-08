insert into table_u (id, age, email, name, password) value (1, 1, 'admin', 'admin', '$2a$05$YVTbu74su4/MeWZ3cjgzX.H.fv5DToJ1OVcJzJcXzMnF9E4DS3Bmq');
insert into table_roles (id, role) value (1, 'ADMIN');
insert into table_u_table_roles (User_id, roles_id) value (1, 1);