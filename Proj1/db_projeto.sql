DROP TABLE pedido_fotos;
DROP TABLE pedido;
DROP TABLE contato_entidade;
DROP TABLE suspensao;
DROP TABLE estado;
DROP TABLE funcionario;
DROP TABLE funcao;
DROP TABLE subcategoria;
DROP TABLE categoria;
DROP TABLE entidade;
DROP TABLE utilizador;
DROP TABLE localidade;

DROP VIEW entidade_pedidos;
DROP VIEW historico_funcionario;
DROP TRIGGER atualiza_estado;

CREATE TABLE localidade(
    cod_postal NUMBER,
    localidade VARCHAR2(30) NOT NULL,
    CONSTRAINT pk_cp PRIMARY KEY(cod_postal)
);


CREATE TABLE utilizador(
    id_utilizador NUMBER,
    estado VARCHAR2(20),
    email VARCHAR2(254) NOT NULL,
    dt_nasc DATE,
    password VARCHAR(30) NOT NULL,
    nome_util VARCHAR2(20) NOT NULL,
    cod_postal NUMBER,
    rua VARCHAR2(200),
    n_porta NUMBER,
    CONSTRAINT constraint_email UNIQUE(email),
    CONSTRAINT constraint_uti UNIQUE(nome_util),
    CONSTRAINT pk_id PRIMARY KEY(id_utilizador),
    CONSTRAINT fk_utl_loc FOREIGN KEY(cod_postal) REFERENCES localidade(cod_postal)
);

CREATE TABLE entidade(
    id_entidade NUMBER,
    nome VARCHAR2(30) NOT NULL,
    descricao VARCHAR2(255),
    CONSTRAINT pk_ent PRIMARY KEY(id_entidade)
);

CREATE TABLE categoria(
    id_categoria NUMBER,
    nome VARCHAR2(30) NOT NULL,
    id_entidade NUMBER,
    CONSTRAINT pk_cat PRIMARY KEY(id_categoria),
    CONSTRAINT fk_cat_ent FOREIGN KEY(id_entidade) REFERENCES entidade(id_entidade)
);

CREATE TABLE subcategoria(
    id_subcategoria NUMBER,
    nome VARCHAR2(30) NOT NULL,
    id_categoria NOT NULL,
    descricao VARCHAR2(254),
    CONSTRAINT pk_subcat PRIMARY KEY(id_subcategoria),
    CONSTRAINT fk_subcat_cat FOREIGN KEY(id_categoria) REFERENCES categoria(id_categoria)
);

CREATE TABLE funcao(
    id_funcao NUMBER,
    descricao VARCHAR2(25),
    CONSTRAINT pk_funcao PRIMARY KEY(id_funcao)
);

CREATE TABLE funcionario(
    id_funcionario NUMBER,
    email VARCHAR2(254) NOT NULL,
    nome VARCHAR2(30) NOT NULL,
    password VARCHAR2(30) NOT NULL,
    id_administrador NUMBER,
    id_entidade NUMBER,
    id_funcao NUMBER,
    CONSTRAINT unique_email_func UNIQUE(email),
    CONSTRAINT pk_func PRIMARY KEY(id_funcionario),
    CONSTRAINT fk_func_admin FOREIGN KEY(id_administrador) REFERENCES funcionario(id_funcionario),
    CONSTRAINT fk_func_ent FOREIGN KEY(id_entidade) REFERENCES entidade(id_entidade),
    CONSTRAINT fk_func_func FOREIGN KEY(id_funcao) REFERENCES funcao(id_funcao)
);

CREATE TABLE estado(
    id_estado NUMBER,
    descricao VARCHAR2(25),
    CONSTRAINT pk_estado PRIMARY KEY(id_estado)
);

CREATE TABLE suspensao(
    id_suspencao NUMBER,
    id_utilizador NUMBER,
    id_administrador NUMBER,
    dt_inicio DATE NOT NULL,
    dt_fim DATE,
    obs VARCHAR2(255),
    CONSTRAINT pk_susp PRIMARY KEY(id_suspencao),
    CONSTRAINT fk_susp_utl FOREIGN KEY(id_utilizador) REFERENCES utilizador(id_utilizador),
    CONSTRAINT fk_susp_admin FOREIGN KEY(id_administrador) REFERENCES funcionario(id_funcionario)
);

CREATE TABLE contato_entidade(
    id_contato NUMBER ,
    id_entidade NUMBER,
    contato NUMBER NOT NULL,
    CONSTRAINT pk_cent PRIMARY KEY(id_contato, id_entidade),
    CONSTRAINT fk_cent_ent FOREIGN KEY(id_entidade) REFERENCES entidade(id_entidade)
);


CREATE TABLE pedido(
    id_pedido NUMBER,
    id_utilizador NUMBER NOT NULL,
    descricao VARCHAR2(254),
    dt_criacao DATE,
    cod_postal NUMBER,
    rua VARCHAR2(150),
    n_porta NUMBER,
    coordenadas VARCHAR2(50),
    id_estado NUMBER,
    id_subcategoria NUMBER,
    id_administrador NUMBER,
    dt_aprovacao DATE,
    obs_admin VARCHAR2(200),
    urgencia NUMBER,
    id_funcionario NUMBER,
    dt_inicio_res DATE,
    dt_fim_res DATE,
    obs_res VARCHAR2(200),
    CONSTRAINT pk_ped PRIMARY KEY(id_pedido),
    CONSTRAINT fk_ped_uti FOREIGN KEY(id_utilizador) REFERENCES utilizador(id_utilizador),
    CONSTRAINT fk_ped_admin FOREIGN KEY(id_administrador) REFERENCES funcionario(id_funcionario),
    CONSTRAINT fk_ped_func FOREIGN KEY(id_funcionario) REFERENCES funcionario(id_funcionario),
    CONSTRAINT fk_ped_subcat FOREIGN KEY(id_subcategoria) REFERENCES subcategoria(id_subcategoria),
    CONSTRAINT fk_ped_estado FOREIGN KEY(id_estado) REFERENCES estado(id_estado),
    CONSTRAINT fk_ped_loc FOREIGN KEY(cod_postal) REFERENCES localidade(cod_postal)
);

CREATE TABLE pedido_fotos(
    id_foto NUMBER ,
    id_pedido NUMBER ,
    path VARCHAR2(255) NOT NULL,
    CONSTRAINT pk_pedfot PRIMARY KEY(id_foto, id_pedido),
    CONSTRAINT fk_pedfot_ped FOREIGN KEY(id_pedido) REFERENCES pedido(id_pedido)
);




--Utilizador N�o Registado--
    --Caso de uso: Registar utilizador
    Select COUNT(*)from utilizador where ( (email='banana@gmail.com') OR (nome_util='Gabriel'));
    INSERT INTO localidade(cod_postal,localidade) VALUES (4900,'Viana do Castelo');
    INSERT INTO utilizador(id_utilizador,estado,email,dt_nasc,password,nome_util,cod_postal,rua,n_porta) VALUES (1,'Disponivel','banana@gmail.com',TO_DATE('16-02-1997','DD-MM-YYYY'),'1234','Gabriel',4900,'Rua das bananeiras',666);

--Utilizador--

     --Caso de uso: Registar Reclama��o--
     INSERT INTO estado VALUES(1,'Por Resolver');
     INSERT INTO entidade VALUES(1, 'Ruas � conosco', 'Tudo a haver com ruas');
     INSERT INTO Categoria VALUES(1,'Ruas',1);
     INSERT INTO subcategoria VALUES(1,'Esgotos',1,'Esgotos');
     INSERT INTO funcao VALUES(1,'Teste');
     INSERT INTO funcionario VALUES(1,'blabl@BLUMPACLAM.RU','Gabriel',1234,1,1,1);
    INSERT INTO pedido (id_pedido,id_utilizador,descricao,dt_criacao,cod_postal,rua,n_porta,id_estado,id_subcategoria,id_administrador)
    VALUES (1,1,'Tampa de esgoto est� fora do sitio',TO_DATE('04-12-2018','DD-MM-YYYY'),4900,'Rua das Cabritas',666,1,1,1);
     
    --Caso de uso: Ver Hist�rico de reclama��es
    SELECT id_pedido, descricao, dt_criacao, cod_postal, rua, n_porta, CASE WHEN dt_aprovacao is not NULL then 'APROVADO' else 'NAO APROVADO' END estado
    FROM pedido where id_utilizador=1;


   -- Caso de uso: Login--
    Select count(*) as sucesso from utilizador
    where nome_util='Gabriel' AND password=1234;
    
            
    --Caso de uso: Cancelar uma reclama��o pendente
    DELETE from pedido where id_pedido=1 AND dt_aprovacao IS NULL;

--Administrador--

    --Caso de uso: Ver reclama��es pendentes
    Select p.id_pedido , p.descricao, p.dt_criacao, p.cod_postal, p.rua, p.n_porta,u.nome_util,c.nome,s.nome,l.localidade from pedido p
    inner join localidade l on (p.cod_postal=l.cod_postal)
    inner join subcategoria s on (p.id_subcategoria=s.id_subcategoria)
    inner join categoria c on (s.id_categoria=c.id_categoria)
    inner join utilizador u on (u.id_utilizador=p.id_utilizador)
    where p.dt_aprovacao IS NULL;
    
    
    --Caso de uso: Avaliar reclama��es Pendentes
    update pedido set dt_aprovacao = to_date('18-12-2018','DD-MM-YYYY'), obs_admin='Nada a apontar',urgencia=5
    where id_pedido=1 AND dt_aprovacao is NULL;
        
        
    --Caso de uso: Finalizar Processo de Reclama��o
    update pedido set id_funcionario=1,dt_inicio_res = to_date('18-12-2018','DD-MM-YYYY'), dt_fim_res = to_date('18-12-2018','DD-MM-YYYY'),obs_res='TA TUDO EM ORDEM CHEFE'
    where id_pedido=1;
        
    
    --Caso de uso: Criar entidade--
    insert into entidade VALUES(2,'Fast VRUM','Limpa Ruas');


    --Caso de uso: Gerir utilizadores da plataforma--
    
        --Suspender Utilizador--
        Insert into suspensao(id_suspencao,id_utilizador, id_administrador, dt_inicio, dt_fim, obs) values(1,1, 1, TO_DATE('19-01-2019', 'dd-mm-yyyy'), TO_DATE('19-01-2020', 'dd-mm-yyyy'), 'Uso indeviduo da aplica��o');
    
        --Banir Utilizador--
        Insert into suspensao(id_suspencao,id_utilizador, id_administrador, dt_inicio, obs) values(2,1, 1, TO_DATE('19-01-2019', 'dd-mm-yyyy'), 'Uso indeviduo da aplica��o');
    
        --Reabilitar Utilizador--
        Delete from suspensao where id_utilizador = 1;
    
        --Alterar data de fim da suspens�o
        Update suspensao set dt_fim = TO_DATE('10-09-2019', 'DD-MM-YYYY') where id_utilizador = 1;
    
    
    --Caso de uso: Ver utilizadores--
        Select u.*, l.localidade from utilizador u
        inner join localidade l on u.cod_postal = l.cod_postal;
    
    
    --Caso de uso: Ver hist�rico de resolu��es--
        --Hisorico geral--
        Select f.id_funcionario, f.nome, fun.descricao, p.descricao, p.dt_inicio_res, p.dt_fim_res, est.descricao estado, l.localidade, sc.nome, c.nome  funcionario 
        FROM funcionario f
        inner join pedido p on (p.id_funcionario = f.id_funcionario)
        inner join estado est on (est.id_estado = p.id_estado)
        inner join funcao fun on (fun.id_funcao = f.id_funcao)
        inner join entidade ent on (ent.id_entidade = f.id_entidade)
        inner join localidade l on (p.cod_postal = l.cod_postal)
        inner join subcategoria sc on (sc.id_subcategoria = p.id_subcategoria)
        inner join categoria c on (sc.id_categoria = c.id_categoria);
    
    --Hisorico de um funcionario--
        Select f.id_funcionario, f.nome, fun.descricao, p.descricao, p.dt_inicio_res, p.dt_fim_res, e.descricao estado, l.localidade, sc.nome, c.nome  funcionario 
        FROM funcionario f
        inner join pedido p on (p.id_funcionario = f.id_funcionario)
        inner join estado e on (e.id_estado = p.id_estado)
        inner join funcao fun on (fun.id_funcao = f.id_funcao)
        inner join entidade ent on (ent.id_entidade = f.id_entidade)
        inner join localidade l on (p.cod_postal = l.cod_postal)
        inner join subcategoria sc on (sc.id_subcategoria = p.id_subcategoria)
        inner join categoria c on (sc.id_categoria = c.id_categoria)
        where f.id_funcionario = 3;
    
    --Caso de uso: Gerir entidade
        --Eliminar entidade
        update funcionario set id_entidade = 2 where id_entidade = 1;
        update categoria set id_entidade = 2 where id_entidade = 1;
        delete from entidade where id_entidade = 1;
    
        --Criar nova entidade
        insert into entidade(id_entidade, nome, descricao) values(3, 'Canil', 'Canil da cidade');
    
        --Atualizar dados da entidade
        update entidade set nome='Canil', descricao='Entidade relatica ao canil da cidade' where id_entidade = 3;
    
        --Atribuir nova categoria a entidade
        insert into categoria(id_categoria, nome, id_entidade) values(2, 'Animais', 3);
    
       --Atribuir categoria existente a entidade
        update categoria set id_entidade = 2 where id_categoria = 1;
    
       --Adicionar nova subcategoria a categoria
        insert into subcategoria(id_subcategoria, nome, id_categoria, descricao) values(2, 'Animal solto', 1, 'Animal solto que represente um problema para o bem esta dos cidadaos');
    
       --Alterar categoria de subcategoria
        update subcategoria set id_categoria = 2 where id_subcategoria = 1;


--views-- 

    --Pedidos atribuidos a entidade--
    create view entidade_pedidos(id_entidade, nome, descricao_entidade, descricao_pedido, dt_criacao, rua, cod_postal, localizacao, subcategoria, categoria, dt_inicio_res, dt_finalizacao, estado) as Select e.id_entidade, e.nome, e.descricao, p.descricao, p.dt_criacao, p.rua, p.cod_postal, l.localidade, sc.nome, c.nome, p.dt_inicio_res, p.dt_fim_res, est.descricao 
        from entidade e
        inner join categoria c on (c.id_entidade = e.id_entidade)
        inner join subcategoria sc on (sc.id_categoria = c.id_categoria)
        inner join pedido p on (p.id_subcategoria = sc.id_subcategoria)
        inner join localidade l on (p.cod_postal = l.cod_postal)
        inner join estado est on (est.id_estado = p.id_estado);
    
    --Historico de resolu��es dos funcionarios
    create view historico_funcionario(id_funcionario, nome, descricao, descricao_pedido, dt_inicio_res, dt_finalizacao, estado, localizacao, subcategoria, categoria) as Select f.id_funcionario, f.nome, fun.descricao, p.descricao, p.dt_inicio_res, p.dt_fim_res, est.descricao estado, l.localidade, sc.nome, c.nome funcionario
    FROM  funcionario f
        inner join pedido p on (p.id_funcionario = f.id_funcionario)
        inner join estado est on (est.id_estado = p.id_estado)
        inner join funcao fun on (fun.id_funcao = f.id_funcao)
        inner join entidade ent on (ent.id_entidade = f.id_entidade)
        inner join localidade l on (p.cod_postal = l.cod_postal)
        inner join subcategoria sc on (sc.id_subcategoria = p.id_subcategoria)
        inner join categoria c on (sc.id_categoria = c.id_categoria);



--TRIGGERS--

  CREATE TRIGGER atualiza_estado
    AFTER INSERT OR UPDATE on suspensao
    FOR EACH ROW
    BEGIN
    UPDATE utilizador SET
        estado='Suspendido'
        WHERE id_utilizador=:NEW.id_utilizador;
        end;
drop procedure DesuspenderUtilizador;
--procedures--
    --suspender utilizador--
select * from utilizador;
SELECT * from suspensao;

create or replace PROCEDURE DesuspenderUtilizador(id_user number)
        IS estado_atual VARCHAR2(20); 
           Data_fim date;
    BEGIN
        SELECT estado INTO estado_atual FROM utilizador WHERE id_utilizador = id_user;
        SELECT DT_FIM INTO Data_fim FROM suspensao WHERE id_utilizador = id_user;
        IF estado_atual='Suspendido' AND TO_DATE(SYSDATE,'DD-MM-YYYY') > TO_DATE(Data_fim,'DD-MM-YYYY') THEN 
            UPDATE utilizador SET estado='Disponivel' WHERE id_utilizador=id_user;
        ELSE 
            UPDATE utilizador SET estado=estado WHERE id_utilizador=id_user;
        END IF;
    END;