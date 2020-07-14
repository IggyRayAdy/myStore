insert into usr (activation_code, active, email, password, username)
    values (null, true,'oxy.lusha@gmail.com','$2a$05$FzR0Yh9z6aSX30c7UAyfleFUD3tfqMJqbkr75uyqbD5x.g.1eTx.a','q');
insert into cart (cash_balance, usr_id)
    values ('150', '1');
insert into usr_role (usr_id, user_role)
    values ('1', 'ADMIN');
