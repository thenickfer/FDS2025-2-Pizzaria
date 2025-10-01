create table if not exists clientes(
  cpf varchar(15) not null primary key,
  nome varchar(100) not null,
  celular varchar(20) not null,
  endereco varchar(255) not null,
  email varchar(255) not null
);

create table if not exists ingredientes (
  id bigint primary key,
  descricao varchar(255) not null
);

create table if not exists itensEstoque(
    id bigint primary key,
    quantidade int,
    ingrediente_id bigint,
    foreign key (ingrediente_id) references ingredientes(id)
);

-- Tabela Receita
create table if not exists receitas (
  id bigint primary key,
  titulo varchar(255) not null
);

-- Tabela de relacionamento entre Receita e Ingrediente
create table if not exists receita_ingrediente (
  receita_id bigint not null,
  ingrediente_id bigint not null,
  primary key (receita_id, ingrediente_id),
  foreign key (receita_id) references receitas(id),
  foreign key (ingrediente_id) references ingredientes(id)
);

-- Tabela de Produtos
create table if not exists produtos (
  id bigint primary key,
  descricao varchar(255) not null,
  preco bigint
);

-- Tabela de relacionamento entre Produto e Receita
create table if not exists produto_receita (
  produto_id bigint not null,
  receita_id bigint not null,
  primary key (produto_id,receita_id),
  foreign key (produto_id) references produtos(id),
  foreign key (receita_id) references receitas(id)
);

-- Tabela de Cardapios
create table if not exists cardapios (
  id bigint primary key,
  titulo varchar(255) not null
);

-- Tabela de relacionamento entre Cardapio e Produto
create table if not exists cardapio_produto (
  cardapio_id bigint not null,
  produto_id bigint not null,
  primary key (cardapio_id,produto_id),
  foreign key (cardapio_id) references cardapios(id),
  foreign key (produto_id) references produtos(id)
);

--Tabela de Pedidos
create table if not exists pedidos (
  id bigint primary key,
  estado varchar(50) not null,
  data_hora_pagamento timestamp,
  valor bigint not null,
  imposto bigint not null,
  desconto bigint not null,
  valor_cobrado bigint not null
);

--Tabela de relacionamento entre Pedido e Cliente
create table if not exists pedido_cliente(
  pedido_id bigint not null,
  cliente_id bigint not null,
  primary key(pedido_id, cliente_id)
  foreign key (pedido_id) references pedidos(id),
  foreign key (cliente_id) references clientes(id)
);

--Tabela de itemPedido
create table if not exists itemPedido(
  id bigint primary key,
  quantidade bigint not null
);

--Tabela de relacionamento entre itemPedido e Produto
create table if not exists itemPedido_produto(
  id_itemPedido bigint not null,
  id_produto bigint not null,
  primary key(id_itemPedido, id_produto),
  foreign key(id_itemPedido) references itemPedido(id),
  foreign key(id_produto) references produtos(id)
);

--Tabela de relacionamento entre Pedido e ItemPedido
create table if not exists pedido_itemPedido(
  id_pedido bigint not null,
  id_itemPedido bigint not null,
  primary key(id_pedido, id_itemPedido),
  foreign key(id_pedido) references pedidos(id),
  foreign key(id_itemPedido) references itemPedido(id)
);