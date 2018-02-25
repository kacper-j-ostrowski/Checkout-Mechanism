insert into item (product_code, name, price) values (1000, 'parowka', 2.99);
insert into item (product_code, name, price) values (1001, 'bigos', 5.55);
insert into item (product_code, name, price) values (1002, 'piwo', 3.99);
insert into item (product_code, name, price) values (1003, 'chipsy', 2.50);
insert into item (product_code, name, price) values (1004, 'sok', 2.59);
insert into item (product_code, name, price) values (1005, 'woda', 0.79);
---
insert into quantity_promotion (id, product_code, quantity, special_price)
values (1, 1000, 5, 10.00);

insert into quantity_promotion (id, product_code, quantity, special_price)
values (2, 1001, 3, 12.00);

insert into quantity_promotion (id, product_code, quantity, special_price)
values (3, 1002, 6, 20.00);
---
insert into combined_promotion (id, first_product_code, new_price, second_product_code)
values (1, 1002, 3.00, 1003);

insert into combined_promotion (id, first_product_code, new_price, second_product_code)
values (2, 1004, 2.00, 1005);