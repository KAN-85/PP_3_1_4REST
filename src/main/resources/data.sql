INSERT INTO users (id, first_name, last_name, email, username, password) VALUES ('1','Alexey','Kuznetsov','alex228@yandex.ru', 'admin', 'admin');
INSERT INTO users (id, first_name, last_name, email, username, password) VALUES ('2','Semen','Shniperson','tsahalforever@gmail.com','user', 'user');
INSERT INTO roles (id, name) VALUES ('1','ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES ('2','ROLE_USER');
INSERT INTO users_roles (user_id, role_id) VALUES ('1','1');
INSERT INTO users_roles (user_id, role_id) VALUES ('1','2');
INSERT INTO users_roles (user_id, role_id) VALUES ('2','2');