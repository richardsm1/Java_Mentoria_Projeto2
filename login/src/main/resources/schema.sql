create table if not exists usuarios (
    id int not null auto_increment primary key,
    usuario varchar(99) not null,
    senha varchar(99),
    funcao varchar(50)
    );
