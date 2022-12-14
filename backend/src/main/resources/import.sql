insert into cuisine (id, name) values (2, 'Indiana');
insert into cuisine (id, name) values (1, 'Tailandesa');
insert into cuisine (id, name) values (3, 'Argentina');
insert into cuisine (id, name) values (4, 'Brasileira');

insert into state (id, name) values (1, 'Minas Gerais');
insert into state (id, name) values (2, 'São Paulo');
insert into state (id, name) values (3, 'Ceará');

insert into city (id, name, state_id) values (1, 'Uberlândia', 1);
insert into city (id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into city (id, name, state_id) values (3, 'São Paulo', 2);
insert into city (id, name, state_id) values (4, 'Campinas', 2);
insert into city (id, name, state_id) values (5, 'Fortaleza', 3);


insert into restaurant (id, name, shipping_fee, cuisine_id ) values (1, 'Thai Delivery', 9.50, 1);
insert into restaurant (id, name, shipping_fee, cuisine_id ) values (2, 'Tuk Tuk Comida Indiana', 15, 2);
insert into restaurant (id, name, shipping_fee, cuisine_id ) values (3, 'Java Steakhouse', 12, 3);
insert into restaurant (id, name, shipping_fee, cuisine_id ) values (4, 'Lanchonete do Tio Sam', 11, 4);
insert into restaurant (id, name, shipping_fee, cuisine_id ) values (5, 'Bar da Maria', 6, 4);

insert into payment_method (id, description) values (1, 'Cartão de crédito');
insert into payment_method (id, description) values (2, 'Cartão de débito');
insert into payment_method (id, description) values (3, 'Dinheiro');

insert into permission (id, name, description) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permission (id, name, description) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurant_payment_methods (restaurants_id, payment_methods_id) VALUES (1,1), (1,2), (1,3), (2,3), (3,2), (3,3);
