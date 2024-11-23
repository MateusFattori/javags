## Integrantes

Bianca Barreto RM:551848

Julia Akemi RM:98032

Pedro Baraldi Sá RM:98060

Mateus Fattori RM:97904

## Descrição do Problema:
As mudanças climáticas são intensificadas pela acumulação de gases de efeito estufa na atmosfera, com o setor de transporte contribuindo significativamente para essas emissões. Veículos pessoais, que comumente utilizam combustíveis fósseis como gasolina e diesel, são responsáveis por uma parcela considerável dessas emissões. Por exemplo, em média, um veículo de passageiro emite cerca de 4,6 toneladas métricas de CO2 por ano, com cada galão de gasolina queimada produzindo cerca de 8.887 gramas de CO2. Além disso, o setor de transporte foi responsável por 28% do total de emissões de gases de efeito estufa nos EUA em 2022


## Descrição da Solução:
O aplicativo proposto é uma ferramenta que tem como objetivo ajudar os usuários a gerenciar e reduzir suas emissões de carbono ao dirigir. Este aplicativo permite que os usuários insiram detalhes específicos sobre suas viagens, como, modelo do carro, tipo de combustível utilizado, quilometragem por litro, e percurso. Para o percurso terá uma integração com a API do Google Maps, dessa forma podemos calcular as distâncias percorridas. 
Com base nessas informações, o aplicativo calcula as emissões de CO2 para cada viagem, considerando a eficiência do combustível do veículo e a distância. Todas essas viagens são registradas em uma seção "Viagens", onde os usuários podem visualizar o histórico completo de suas emissões, o que facilita o monitoramento e a gestão de suas pegadas de carbono.
Como a Solução Ajuda as Pessoas:
Esta solução não apenas aumenta a conscientização dos usuários sobre o impacto ambiental de suas escolhas de transporte, mas também os incentiva a adotar práticas mais sustentáveis. Ao fornecer dados detalhados sobre as emissões geradas, o aplicativo pode motivar os usuários a optar por rotas mais curtas ou mais eficientes, escolher modos de transporte alternativos ou mesmo melhorar a manutenção do veículo para aumentar a eficiência do combustível. Com essa ferramenta, os usuários têm um papel ativo na redução das emissões de gases de efeito estufa, contribuindo para a luta global contra as mudanças climáticas e promovendo uma maior sustentabilidade no transporte pessoal.
Desenho da arquitetura:

## Desenho de arquiterura 

https://media.discordapp.net/attachments/1085296607567814727/1309658376434614342/arquitetura.png?ex=674261e9&is=67411069&hm=df5ef1e084a6573d8c1e228a1bcbbdcac7fc6aa33fb8e286010ddf2b0fd4496d&=&format=webp&quality=lossless&width=806&height=671

## DDL das tabelas 

-- Script 01: CriaÃ§Ã£o das Tabelas Principais

-- Remover tabelas existentes 

DROP TABLE USUARIO CASCADE CONSTRAINTS;
DROP TABLE COMBUSTIVEL CASCADE CONSTRAINTS;
DROP TABLE VEICULO CASCADE CONSTRAINTS;
DROP TABLE VIAGEM CASCADE CONSTRAINTS;
DROP TABLE EMISSAO CASCADE CONSTRAINTS;

-- Criar tabela USUARIO

CREATE TABLE USUARIO (
    ID_USUARIO INTEGER PRIMARY KEY,
    NOME VARCHAR2(100),
    EMAIL VARCHAR2(50),
    SENHA VARCHAR2(255),
    DATA_CADASTRO DATE
);

-- Criar tabela COMBUSTIVEL

CREATE TABLE COMBUSTIVEL (
    ID_COMBUSTIVEL INTEGER PRIMARY KEY,
    TIPO_COMBUSTIVEL VARCHAR2(20),
    FATOR_EMISSAO NUMBER
);

-- Criar tabela VEICULO

CREATE TABLE VEICULO (
    ID_VEICULO INTEGER PRIMARY KEY,
    MARCA VARCHAR2(50),
    MODELO VARCHAR2(50),
    ANO_FABRICACAO NUMBER(4),
    CONSUMO_POR_KM NUMBER(10,2),
    ID_USUARIO INTEGER,
    ID_COMBUSTIVEL INTEGER,
    CONSTRAINT FK_VEICULO_USUARIO FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID_USUARIO),
);

-- Criar tabela VIAGEM

CREATE TABLE VIAGEM (
    ID_VIAGEM INTEGER PRIMARY KEY,
    DATA_VIAGEM DATE,
    LOCAL_ORIGEM VARCHAR2(50),
    LOCAL_DESTINO VARCHAR2(50),
    DISTANCIA_KM NUMBER(10,2),
);

-- Criar tabela EMISSAO

CREATE TABLE EMISSAO (
    ID_EMISSAO INTEGER PRIMARY KEY,
    ID_VIAGEM INTEGER,
    LITROS_CONSUMIDOS NUMBER(10,2),
    EMISSAO_CO2_KG NUMBER(10,2),
);

-- Script 02: CriaÃ§Ã£o das SequÃªncias e Triggers para Auto-Incremento

-- Remover sequencias existentes 

DROP SEQUENCE SEQ_USUARIO;
DROP SEQUENCE SEQ_COMBUSTIVEL;
DROP SEQUENCE SEQ_VEICULO;
DROP SEQUENCE SEQ_VIAGEM;
DROP SEQUENCE SEQ_EMISSAO;

-- Criar sequencias

CREATE SEQUENCE SEQ_USUARIO;
CREATE SEQUENCE SEQ_COMBUSTIVEL;
CREATE SEQUENCE SEQ_VEICULO;
CREATE SEQUENCE SEQ_VIAGEM;
CREATE SEQUENCE SEQ_EMISSAO;


----- Criar TRIGGERS para auto-incremento do ID  ------


-- Trigger para USUARIO
CREATE OR REPLACE TRIGGER TRG_USUARIO_ID
BEFORE INSERT ON USUARIO
FOR EACH ROW
BEGIN
    SELECT SEQ_USUARIO.NEXTVAL INTO :NEW.ID_USUARIO FROM DUAL;
END;
/


-- Trigger para COMBUSTIVEL
CREATE OR REPLACE TRIGGER TRG_COMBUSTIVEL_ID
BEFORE INSERT ON COMBUSTIVEL
FOR EACH ROW
BEGIN
    SELECT SEQ_COMBUSTIVEL.NEXTVAL INTO :NEW.ID_COMBUSTIVEL FROM DUAL;
END;
/


-- Trigger para VEICULO
CREATE OR REPLACE TRIGGER TRG_VEICULO_ID
BEFORE INSERT ON VEICULO
FOR EACH ROW
BEGIN
    SELECT SEQ_VEICULO.NEXTVAL INTO :NEW.ID_VEICULO FROM DUAL;
END;
/


-- Trigger para VIAGEM
CREATE OR REPLACE TRIGGER TRG_VIAGEM_ID
BEFORE INSERT ON VIAGEM
FOR EACH ROW
BEGIN
    SELECT SEQ_VIAGEM.NEXTVAL INTO :NEW.ID_VIAGEM FROM DUAL;
END;
/


-- Trigger para EMISSAO
CREATE OR REPLACE TRIGGER TRG_EMISSAO_ID
BEFORE INSERT ON EMISSAO
FOR EACH ROW
BEGIN
    SELECT SEQ_EMISSAO.NEXTVAL INTO :NEW.ID_EMISSAO FROM DUAL;
END;
/


-- Script 03: CriaÃ§Ã£o das Tabelas e Triggers de Auditoria

-- Dropando tabelas auditoria
DROP TABLE USUARIO_AUD CASCADE CONSTRAINTS;
DROP TABLE VEICULO_AUD CASCADE CONSTRAINTS;


-- Dropando sequencias auditoria
DROP SEQUENCE SEQ_USUARIO_AUD;
DROP SEQUENCE SEQ_COMBUSTIVEL_AUD;
DROP SEQUENCE SEQ_VEICULO_AUD;
DROP SEQUENCE SEQ_VIAGEM_AUD;
DROP SEQUENCE SEQ_EMISSAO_AUD;


-- Auditoria da tabela USUARIO

-- Criar tabela de auditoria USUARIO_AUD

CREATE TABLE USUARIO_AUD (
    AUDIT_ID INTEGER PRIMARY KEY,
    ID_USUARIO INTEGER,
    NOME VARCHAR2(100),
    EMAIL VARCHAR2(50),
    SENHA VARCHAR2(255),
    DATA_CADASTRO DATE,
    OPERACAO VARCHAR2(10),
    DATA_OPERACAO DATE
);

-- Criar sequÃªncia para USUARIO_AUD
CREATE SEQUENCE SEQ_USUARIO_AUD;


-- Trigger para auto-incremento de USUARIO_AUD_ID
CREATE OR REPLACE TRIGGER TRG_USUARIO_AUD_ID
BEFORE INSERT ON USUARIO_AUD
FOR EACH ROW
BEGIN
    SELECT SEQ_USUARIO_AUD.NEXTVAL INTO :NEW.AUDIT_ID FROM DUAL;
END;
/

-- Trigger de auditoria para USUARIO
CREATE OR REPLACE TRIGGER TRG_AUDITORIA_USUARIO
AFTER INSERT OR UPDATE OR DELETE ON USUARIO
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO USUARIO_AUD (ID_USUARIO, NOME, EMAIL, SENHA, DATA_CADASTRO, OPERACAO, DATA_OPERACAO)
        VALUES (:NEW.ID_USUARIO, :NEW.NOME, :NEW.EMAIL, :NEW.SENHA, :NEW.DATA_CADASTRO, 'INSERT', SYSDATE);
    ELSIF UPDATING THEN
        INSERT INTO USUARIO_AUD (ID_USUARIO, NOME, EMAIL, SENHA, DATA_CADASTRO, OPERACAO, DATA_OPERACAO)
        VALUES (:NEW.ID_USUARIO, :NEW.NOME, :NEW.EMAIL, :NEW.SENHA, :NEW.DATA_CADASTRO, 'UPDATE', SYSDATE);
    ELSIF DELETING THEN
        INSERT INTO USUARIO_AUD (ID_USUARIO, NOME, EMAIL, SENHA, DATA_CADASTRO, OPERACAO, DATA_OPERACAO)
        VALUES (:OLD.ID_USUARIO, :OLD.NOME, :OLD.EMAIL, :OLD.SENHA, :OLD.DATA_CADASTRO, 'DELETE', SYSDATE);
    END IF;
END;
/


-- Auditoria da tabela COMBUSTIVEL

-- Criar tabela de auditoria COMBUSTIVEL_AUD

CREATE TABLE COMBUSTIVEL_AUD (
    AUDIT_ID INTEGER PRIMARY KEY,
    ID_COMBUSTIVEL INTEGER,
    TIPO_COMBUSTIVEL VARCHAR2(20),
    FATOR_EMISSAO NUMBER,
    OPERACAO VARCHAR2(10),
    DATA_OPERACAO DATE
);

-- Criar sequÃªncia para COMBUSTIVEL_AUD
CREATE SEQUENCE SEQ_COMBUSTIVEL_AUD;


-- Trigger para auto-incremento de COMBUSTIVEL_AUD_ID
CREATE OR REPLACE TRIGGER TRG_COMBUSTIVEL_AUD_ID
BEFORE INSERT ON COMBUSTIVEL_AUD
FOR EACH ROW
BEGIN
    SELECT SEQ_COMBUSTIVEL_AUD.NEXTVAL INTO :NEW.AUDIT_ID FROM DUAL;
END;
/


-- Trigger de auditoria para COMBUSTIVEL
CREATE OR REPLACE TRIGGER TRG_AUDITORIA_COMBUSTIVEL
AFTER INSERT OR UPDATE OR DELETE ON COMBUSTIVEL
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO COMBUSTIVEL_AUD (ID_COMBUSTIVEL, TIPO_COMBUSTIVEL, FATOR_EMISSAO, OPERACAO, DATA_OPERACAO)
        VALUES (:NEW.ID_COMBUSTIVEL, :NEW.TIPO_COMBUSTIVEL, :NEW.FATOR_EMISSAO, 'INSERT', SYSDATE);
    ELSIF UPDATING THEN
        INSERT INTO COMBUSTIVEL_AUD (ID_COMBUSTIVEL, TIPO_COMBUSTIVEL, FATOR_EMISSAO, OPERACAO, DATA_OPERACAO)
        VALUES (:NEW.ID_COMBUSTIVEL, :NEW.TIPO_COMBUSTIVEL, :NEW.FATOR_EMISSAO, 'UPDATE', SYSDATE);
    ELSIF DELETING THEN
        INSERT INTO COMBUSTIVEL_AUD (ID_COMBUSTIVEL, TIPO_COMBUSTIVEL, FATOR_EMISSAO, OPERACAO, DATA_OPERACAO)
        VALUES (:OLD.ID_COMBUSTIVEL, :OLD.TIPO_COMBUSTIVEL, :OLD.FATOR_EMISSAO, 'DELETE', SYSDATE);
    END IF;
END;
/


-- Auditoria da tabela VEICULO

-- Criar tabela de auditoria VEICULO_AUD
CREATE TABLE VEICULO_AUD (
    AUDIT_ID INTEGER PRIMARY KEY,
    MARCA VARCHAR2(50),
    MODELO VARCHAR2(50),
    ANO_FABRICACAO NUMBER(4),
    CONSUMO_POR_KM NUMBER(10,2),
    ID_USUARIO INTEGER,
    ID_COMBUSTIVEL INTEGER,
    OPERACAO VARCHAR2(10),
    DATA_OPERACAO DATE
);

-- Criar sequÃªncia para VEICULO_AUD
CREATE SEQUENCE SEQ_VEICULO_AUD;


-- Trigger para auto-incremento de VEICULO_AUD_ID
CREATE OR REPLACE TRIGGER TRG_VEICULO_AUD_ID
BEFORE INSERT ON VEICULO_AUD
FOR EACH ROW
BEGIN
    SELECT SEQ_VEICULO_AUD.NEXTVAL INTO :NEW.AUDIT_ID FROM DUAL;
END;
/


-- Trigger de auditoria para VEICULO
CREATE OR REPLACE TRIGGER TRG_AUDITORIA_VEICULO
AFTER INSERT OR UPDATE OR DELETE ON VEICULO
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO VEICULO_AUD (ID_VEICULO, MARCA, MODELO, ANO_FABRICACAO, CONSUMO_POR_KM, ID_USUARIO, ID_COMBUSTIVEL, OPERACAO, DATA_OPERACAO)
        VALUES (:NEW.ID_VEICULO, :NEW.MARCA, :NEW.MODELO, :NEW.ANO_FABRICACAO, :NEW.CONSUMO_POR_KM, :NEW.ID_USUARIO, :NEW.ID_COMBUSTIVEL, 'INSERT', SYSDATE);
    ELSIF UPDATING THEN
        INSERT INTO VEICULO_AUD (ID_VEICULO, MARCA, MODELO, ANO_FABRICACAO, CONSUMO_POR_KM, ID_USUARIO, ID_COMBUSTIVEL, OPERACAO, DATA_OPERACAO)
        VALUES (:NEW.ID_VEICULO, :NEW.MARCA, :NEW.MODELO, :NEW.ANO_FABRICACAO, :NEW.CONSUMO_POR_KM, :NEW.ID_USUARIO, :NEW.ID_COMBUSTIVEL, 'UPDATE', SYSDATE);
    ELSIF DELETING THEN
        INSERT INTO VEICULO_AUD (ID_VEICULO, MARCA, MODELO, ANO_FABRICACAO, CONSUMO_POR_KM, ID_USUARIO, ID_COMBUSTIVEL, OPERACAO, DATA_OPERACAO)
        VALUES (:OLD.ID_VEICULO, :OLD.MARCA, :OLD.MODELO, :OLD.ANO_FABRICACAO, :OLD.CONSUMO_POR_KM, :OLD.ID_USUARIO, :OLD.ID_COMBUSTIVEL, 'DELETE', SYSDATE);
    END IF;
END;
/


-- Auditoria da tabela VIAGEM

-- Criar tabela de auditoria VIAGEM_AUD
CREATE TABLE VIAGEM_AUD (
    AUDIT_ID INTEGER PRIMARY KEY,
    ID_VIAGEM INTEGER,
    DATA_VIAGEM DATE,
    LOCAL_ORIGEM VARCHAR2(50),
    LOCAL_DESTINO VARCHAR2(50),
    DISTANCIA_KM NUMBER(10,2),
    OPERACAO VARCHAR2(10),
    DATA_OPERACAO DATE
);

-- Criar sequÃªncia para VIAGEM_AUD
CREATE SEQUENCE SEQ_VIAGEM_AUD;


-- Trigger para auto-incremento de VIAGEM_AUD_ID
CREATE OR REPLACE TRIGGER TRG_VIAGEM_AUD_ID
BEFORE INSERT ON VIAGEM_AUD
FOR EACH ROW
BEGIN
    SELECT SEQ_VIAGEM_AUD.NEXTVAL INTO :NEW.AUDIT_ID FROM DUAL;
END;
/


-- Trigger de auditoria para VIAGEM
CREATE OR REPLACE TRIGGER TRG_AUDITORIA_VIAGEM
AFTER INSERT OR UPDATE OR DELETE ON VIAGEM
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO VIAGEM_AUD (ID_VIAGEM, DATA_VIAGEM, LOCAL_ORIGEM, LOCAL_DESTINO, DISTANCIA_KM, ID_VEICULO, OPERACAO, DATA_OPERACAO)
        VALUES (:NEW.ID_VIAGEM, :NEW.DATA_VIAGEM, :NEW.LOCAL_ORIGEM, :NEW.LOCAL_DESTINO, :NEW.DISTANCIA_KM, :NEW.ID_VEICULO, 'INSERT', SYSDATE);
    ELSIF UPDATING THEN
        INSERT INTO VIAGEM_AUD (ID_VIAGEM, DATA_VIAGEM, LOCAL_ORIGEM, LOCAL_DESTINO, DISTANCIA_KM, ID_VEICULO, OPERACAO, DATA_OPERACAO)
        VALUES (:NEW.ID_VIAGEM, :NEW.DATA_VIAGEM, :NEW.LOCAL_ORIGEM, :NEW.LOCAL_DESTINO, :NEW.DISTANCIA_KM, :NEW.ID_VEICULO, 'UPDATE', SYSDATE);
    ELSIF DELETING THEN
        INSERT INTO VIAGEM_AUD (ID_VIAGEM, DATA_VIAGEM, LOCAL_ORIGEM, LOCAL_DESTINO, DISTANCIA_KM, ID_VEICULO, OPERACAO, DATA_OPERACAO)
        VALUES (:OLD.ID_VIAGEM, :OLD.DATA_VIAGEM, :OLD.LOCAL_ORIGEM, :OLD.LOCAL_DESTINO, :OLD.DISTANCIA_KM, :OLD.ID_VEICULO, 'DELETE', SYSDATE);
    END IF;
END;
/



-- Auditoria da tabela EMISSAO

-- Criar tabela de auditoria EMISSAO_AUD
CREATE TABLE EMISSAO_AUD (
    AUDIT_ID INTEGER PRIMARY KEY,
    ID_EMISSAO INTEGER,
    ID_VIAGEM INTEGER,
    LITROS_CONSUMIDOS NUMBER(10,2),
    EMISSAO_CO2_KG NUMBER(10,2),
    OPERACAO VARCHAR2(10),
    DATA_OPERACAO DATE
);


-- Criar sequÃªncia para EMISSAO_AUD
CREATE SEQUENCE SEQ_EMISSAO_AUD;


-- Trigger para auto-incremento de EMISSAO_AUD_ID
CREATE OR REPLACE TRIGGER TRG_EMISSAO_AUD_ID
BEFORE INSERT ON EMISSAO_AUD
FOR EACH ROW
BEGIN
    SELECT SEQ_EMISSAO_AUD.NEXTVAL INTO :NEW.AUDIT_ID FROM DUAL;
END;
/


-- Trigger de auditoria para EMISSAO
CREATE OR REPLACE TRIGGER TRG_AUDITORIA_EMISSAO
AFTER INSERT OR UPDATE OR DELETE ON EMISSAO
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO EMISSAO_AUD (ID_EMISSAO, ID_VIAGEM, LITROS_CONSUMIDOS, EMISSAO_CO2_KG, OPERACAO, DATA_OPERACAO)
        VALUES (:NEW.ID_EMISSAO, :NEW.ID_VIAGEM, :NEW.LITROS_CONSUMIDOS, :NEW.EMISSAO_CO2_KG, 'INSERT', SYSDATE);
    ELSIF UPDATING THEN
        INSERT INTO EMISSAO_AUD (ID_EMISSAO, ID_VIAGEM, LITROS_CONSUMIDOS, EMISSAO_CO2_KG, OPERACAO, DATA_OPERACAO)
        VALUES (:NEW.ID_EMISSAO, :NEW.ID_VIAGEM, :NEW.LITROS_CONSUMIDOS, :NEW.EMISSAO_CO2_KG, 'UPDATE', SYSDATE);
    ELSIF DELETING THEN
        INSERT INTO EMISSAO_AUD (ID_EMISSAO, ID_VIAGEM, LITROS_CONSUMIDOS, EMISSAO_CO2_KG, OPERACAO, DATA_OPERACAO)
        VALUES (:OLD.ID_EMISSAO, :OLD.ID_VIAGEM, :OLD.LITROS_CONSUMIDOS, :OLD.EMISSAO_CO2_KG, 'DELETE', SYSDATE);
    END IF;
END;
/




------------------------------------------------------ SCRIPT 04: CriaÃ§Ã£o dos Pacotes PL/SQL  ------------------------------------------------------

-- Pacote PKG_UTILS

CREATE OR REPLACE PACKAGE PKG_UTILS AS
    FUNCTION CALCULAR_LITROS_CONSUMIDOS (
        P_DISTANCIA_KM     IN NUMBER,
        P_CONSUMO_POR_KM   IN NUMBER
    ) RETURN NUMBER;

    FUNCTION CALCULAR_EMISSAO_CO2 (
        P_LITROS_CONSUMIDOS NUMBER,
        P_FATOR_EMISSAO     NUMBER
    ) RETURN NUMBER;
END PKG_UTILS;
/

CREATE OR REPLACE PACKAGE BODY PKG_UTILS AS
    FUNCTION CALCULAR_LITROS_CONSUMIDOS (
        P_DISTANCIA_KM     IN NUMBER,
        P_CONSUMO_POR_KM   IN NUMBER
    ) RETURN NUMBER IS
        V_LITROS_CONSUMIDOS NUMBER;
    BEGIN
        -- ValidaÃ§Ã£o dos parÃ¢metros
        IF P_DISTANCIA_KM <= 0 THEN
            RAISE_APPLICATION_ERROR(-20026, 'DISTANCIA_KM deve ser um nÃºmero positivo.');
        END IF;

        IF P_CONSUMO_POR_KM <= 0 THEN
            RAISE_APPLICATION_ERROR(-20003, 'CONSUMO_POR_KM deve ser um nÃºmero positivo.');
        END IF;

        V_LITROS_CONSUMIDOS := P_DISTANCIA_KM / P_CONSUMO_POR_KM;
        RETURN V_LITROS_CONSUMIDOS;
    END;

    FUNCTION CALCULAR_EMISSAO_CO2 (
        P_LITROS_CONSUMIDOS NUMBER,
        P_FATOR_EMISSAO     NUMBER
    ) RETURN NUMBER IS
        V_EMISSAO_CO2_KG NUMBER(10,2);
    BEGIN
        V_EMISSAO_CO2_KG := P_LITROS_CONSUMIDOS * P_FATOR_EMISSAO;
        RETURN V_EMISSAO_CO2_KG;
    END;

END PKG_UTILS;
/

-- Pacote PKG_USUARIO

CREATE OR REPLACE PACKAGE PKG_USUARIO AS
    PROCEDURE INSERIR_USUARIO (
        P_NOME          IN USUARIO.NOME%TYPE,
        P_EMAIL         IN USUARIO.EMAIL%TYPE,
        P_SENHA         IN USUARIO.SENHA%TYPE,
        P_DATA_CADASTRO IN USUARIO.DATA_CADASTRO%TYPE DEFAULT SYSDATE
    );
END PKG_USUARIO;
/

CREATE OR REPLACE PACKAGE BODY PKG_USUARIO AS

    PROCEDURE INSERIR_USUARIO (
        P_NOME          IN USUARIO.NOME%TYPE,
        P_EMAIL         IN USUARIO.EMAIL%TYPE,
        P_SENHA         IN USUARIO.SENHA%TYPE,
        P_DATA_CADASTRO IN USUARIO.DATA_CADASTRO%TYPE
    ) AS
    BEGIN
        -- ValidaÃ§Ã£o do formato do e-mail usando expressÃ£o regular
        IF NOT REGEXP_LIKE(P_EMAIL, '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$') THEN
            RAISE_APPLICATION_ERROR(-20020, 'Formato de e-mail invÃ¡lido.');
        END IF;

        -- ValidaÃ§Ã£o do nome
        IF P_NOME IS NULL OR TRIM(P_NOME) = '' THEN
            RAISE_APPLICATION_ERROR(-20021, 'O nome nÃ£o pode ser vazio.');
        ELSIF REGEXP_LIKE(P_NOME, '[0-9]') THEN
            RAISE_APPLICATION_ERROR(-20023, 'O nome nÃ£o pode conter nÃºmeros.');
        END IF;

        -- ValidaÃ§Ã£o da senha
        IF LENGTH(P_SENHA) < 6 THEN
            RAISE_APPLICATION_ERROR(-20022, 'A senha deve ter pelo menos 6 caracteres.');
        END IF;

        INSERT INTO USUARIO (NOME, EMAIL, SENHA, DATA_CADASTRO)
        VALUES (P_NOME, P_EMAIL, P_SENHA, P_DATA_CADASTRO);

        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            RAISE;
    END;

END PKG_USUARIO;
/

-- Pacote PKG_VEICULO

CREATE OR REPLACE PACKAGE PKG_VEICULO AS
    PROCEDURE INSERIR_VEICULO (
        P_MARCA             IN VEICULO.MARCA%TYPE,
        P_MODELO            IN VEICULO.MODELO%TYPE,
        P_ANO_FABRICACAO    IN VEICULO.ANO_FABRICACAO%TYPE,
        P_CONSUMO_POR_KM    IN VEICULO.CONSUMO_POR_KM%TYPE,
        P_ID_USUARIO        IN VEICULO.ID_USUARIO%TYPE,
        P_ID_COMBUSTIVEL    IN VEICULO.ID_COMBUSTIVEL%TYPE
    );
END PKG_VEICULO;
/

CREATE OR REPLACE PACKAGE BODY PKG_VEICULO AS

    PROCEDURE INSERIR_VEICULO (
        P_MARCA             IN VEICULO.MARCA%TYPE,
        P_MODELO            IN VEICULO.MODELO%TYPE,
        P_ANO_FABRICACAO    IN VEICULO.ANO_FABRICACAO%TYPE,
        P_CONSUMO_POR_KM    IN VEICULO.CONSUMO_POR_KM%TYPE,
        P_ID_USUARIO        IN VEICULO.ID_USUARIO%TYPE,
        P_ID_COMBUSTIVEL    IN VEICULO.ID_COMBUSTIVEL%TYPE
    ) AS
        V_TEMP INTEGER;
    BEGIN
        -- ValidaÃ§Ã£o da Marca
        IF NOT REGEXP_LIKE(P_MARCA, '^[A-Za-z ]+$') THEN
            RAISE_APPLICATION_ERROR(-20024, 'A marca deve conter apenas letras.');
        END IF;

        -- ValidaÃ§Ã£o do Consumo por Km
        IF P_CONSUMO_POR_KM <= 0 THEN
            RAISE_APPLICATION_ERROR(-20025, 'O consumo por km deve ser um nÃºmero positivo.');
        END IF;

        -- Verificar se o combustÃ­vel existe
        SELECT 1 INTO V_TEMP FROM COMBUSTIVEL WHERE ID_COMBUSTIVEL = P_ID_COMBUSTIVEL;

        INSERT INTO VEICULO (MARCA, MODELO, ANO_FABRICACAO, CONSUMO_POR_KM, ID_USUARIO, ID_COMBUSTIVEL)
        VALUES (P_MARCA, P_MODELO, P_ANO_FABRICACAO, P_CONSUMO_POR_KM, P_ID_USUARIO, P_ID_COMBUSTIVEL);

        COMMIT;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20010, 'CombustÃ­vel nÃ£o encontrado.');
        WHEN OTHERS THEN
            ROLLBACK;
            RAISE;
    END;

END PKG_VEICULO;
/

-- Pacote PKG_VIAGEM

CREATE OR REPLACE PACKAGE PKG_VIAGEM AS
    PROCEDURE INSERIR_VIAGEM (
        P_DATA_VIAGEM   IN VIAGEM.DATA_VIAGEM%TYPE,
        P_LOCAL_ORIGEM  IN VIAGEM.LOCAL_ORIGEM%TYPE,
        P_LOCAL_DESTINO IN VIAGEM.LOCAL_DESTINO%TYPE,
        P_DISTANCIA_KM  IN VIAGEM.DISTANCIA_KM%TYPE,
        P_ID_VEICULO    IN VIAGEM.ID_VEICULO%TYPE
    );

    PROCEDURE EXPORTAR_VIAGENS_JSON;
END PKG_VIAGEM;
/

CREATE OR REPLACE PACKAGE BODY PKG_VIAGEM AS

    PROCEDURE INSERIR_VIAGEM (
        P_DATA_VIAGEM   IN VIAGEM.DATA_VIAGEM%TYPE,
        P_LOCAL_ORIGEM  IN VIAGEM.LOCAL_ORIGEM%TYPE,
        P_LOCAL_DESTINO IN VIAGEM.LOCAL_DESTINO%TYPE,
        P_DISTANCIA_KM  IN VIAGEM.DISTANCIA_KM%TYPE,
        P_ID_VEICULO    IN VIAGEM.ID_VEICULO%TYPE
    ) AS
        V_TEMP INTEGER;
    BEGIN
        -- Verificar se o veÃ­culo existe
        SELECT 1 INTO V_TEMP FROM VEICULO WHERE ID_VEICULO = P_ID_VEICULO;

        INSERT INTO VIAGEM (DATA_VIAGEM, LOCAL_ORIGEM, LOCAL_DESTINO, DISTANCIA_KM, ID_VEICULO)
        VALUES (P_DATA_VIAGEM, P_LOCAL_ORIGEM, P_LOCAL_DESTINO, P_DISTANCIA_KM, P_ID_VEICULO);

        COMMIT;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20011, 'VeÃ­culo nÃ£o encontrado.');
        WHEN OTHERS THEN
            ROLLBACK;
            RAISE;
    END;

    PROCEDURE EXPORTAR_VIAGENS_JSON AS
        V_JSON_CLOB CLOB := '[';
        V_FIRST_RECORD BOOLEAN := TRUE;
    BEGIN
        FOR REC IN (
            SELECT V.ID_VIAGEM, TO_CHAR(V.DATA_VIAGEM, 'YYYY-MM-DD') AS DATA_VIAGEM,
                   V.LOCAL_ORIGEM, V.LOCAL_DESTINO, V.DISTANCIA_KM,
                   VEI.ID_VEICULO, VEI.MARCA, VEI.MODELO, VEI.ANO_FABRICACAO, VEI.CONSUMO_POR_KM,
                   U.ID_USUARIO, U.NOME, U.EMAIL
            FROM VIAGEM V
            JOIN VEICULO VEI ON V.ID_VEICULO = VEI.ID_VEICULO
            JOIN USUARIO U ON VEI.ID_USUARIO = U.ID_USUARIO
        ) LOOP
            IF NOT V_FIRST_RECORD THEN
                V_JSON_CLOB := V_JSON_CLOB || ',';
            ELSE
                V_FIRST_RECORD := FALSE;
            END IF;
            V_JSON_CLOB := V_JSON_CLOB || '{"ID_VIAGEM":' || REC.ID_VIAGEM ||
                           ',"DATA_VIAGEM":"' || REC.DATA_VIAGEM ||
                           '","LOCAL_ORIGEM":"' || REC.LOCAL_ORIGEM ||
                           '","LOCAL_DESTINO":"' || REC.LOCAL_DESTINO ||
                           '","DISTANCIA_KM":' || REC.DISTANCIA_KM ||
                           ',"VEICULO":{"ID_VEICULO":' || REC.ID_VEICULO ||
                           ',"MARCA":"' || REC.MARCA ||
                           '","MODELO":"' || REC.MODELO ||
                           '","ANO_FABRICACAO":' || REC.ANO_FABRICACAO ||
                           ',"CONSUMO_POR_KM":' || REC.CONSUMO_POR_KM ||
                           ',"USUARIO":{"ID_USUARIO":' || REC.ID_USUARIO ||
                           ',"NOME":"' || REC.NOME ||
                           '","EMAIL":"' || REC.EMAIL || '"}}}';
        END LOOP;
        V_JSON_CLOB := V_JSON_CLOB || ']';

        -- Exibir o JSON no console
        DBMS_OUTPUT.PUT_LINE(V_JSON_CLOB);

    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro ao exportar dados: ' || SQLERRM);
    END;

END PKG_VIAGEM;
/

-- Pacote PKG_EMISSAO

CREATE OR REPLACE PACKAGE PKG_EMISSAO AS
    PROCEDURE INSERIR_EMISSAO (P_ID_VIAGEM IN INTEGER);
END PKG_EMISSAO;
/

CREATE OR REPLACE PACKAGE BODY PKG_EMISSAO AS

    PROCEDURE INSERIR_EMISSAO (P_ID_VIAGEM IN INTEGER) AS
        V_DISTANCIA_KM        NUMBER(10,2);
        V_CONSUMO_POR_KM      NUMBER(10,2);
        V_LITROS_CONSUMIDOS   NUMBER(10,2);
        V_FATOR_EMISSAO       NUMBER(10,6);
        V_EMISSAO_CO2_KG      NUMBER(10,2);
        V_ID_VEICULO          INTEGER;
        V_ID_COMBUSTIVEL      INTEGER;
    BEGIN
        -- Obter DISTANCIA_KM e ID_VEICULO da tabela VIAGEM
        SELECT DISTANCIA_KM, ID_VEICULO
        INTO V_DISTANCIA_KM, V_ID_VEICULO
        FROM VIAGEM
        WHERE ID_VIAGEM = P_ID_VIAGEM;

        -- Obter CONSUMO_POR_KM e ID_COMBUSTIVEL da tabela VEICULO
        SELECT CONSUMO_POR_KM, ID_COMBUSTIVEL
        INTO V_CONSUMO_POR_KM, V_ID_COMBUSTIVEL
        FROM VEICULO
        WHERE ID_VEICULO = V_ID_VEICULO;

        -- Calcular LITROS_CONSUMIDOS
        V_LITROS_CONSUMIDOS := PKG_UTILS.CALCULAR_LITROS_CONSUMIDOS(V_DISTANCIA_KM, V_CONSUMO_POR_KM);

        -- Obter FATOR_EMISSAO da tabela COMBUSTIVEL
        SELECT FATOR_EMISSAO
        INTO V_FATOR_EMISSAO
        FROM COMBUSTIVEL
        WHERE ID_COMBUSTIVEL = V_ID_COMBUSTIVEL;

        V_EMISSAO_CO2_KG := PKG_UTILS.CALCULAR_EMISSAO_CO2(V_LITROS_CONSUMIDOS, V_FATOR_EMISSAO);

        INSERT INTO EMISSAO (ID_VIAGEM, LITROS_CONSUMIDOS, EMISSAO_CO2_KG)
        VALUES (P_ID_VIAGEM, V_LITROS_CONSUMIDOS, V_EMISSAO_CO2_KG);

        COMMIT;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20001, 'Dados nÃ£o encontrados para o ID_VIAGEM fornecido.');
        WHEN OTHERS THEN
            ROLLBACK;
            RAISE;
    END;

END PKG_EMISSAO;
/



------------------------------------------------------ SCRIPT 05: INSERÃÃO DE DADOS INICIAIS  ------------------------------------------------------


-- ATIVAR A SAIDA DO SERVIDOR
SET SERVEROUTPUT ON SIZE UNLIMITED;

-- ALTERAR SEPARADOR NUMERICO PARA '.'
ALTER SESSION SET NLS_NUMERIC_CHARACTERS = '.,';


-- Inserir dados na tabela COMBUSTIVEL = DADOS FIXOS -> Cada tipo de combustivel possui um fator emissao, e isso nao muda, sendo dispensavel 
-- a necessidade de uma procedure

INSERT INTO COMBUSTIVEL (TIPO_COMBUSTIVEL, FATOR_EMISSAO)
VALUES ('Gasolina', 2.31);

INSERT INTO COMBUSTIVEL (TIPO_COMBUSTIVEL, FATOR_EMISSAO)
VALUES ('Diesel', 2.68);

INSERT INTO COMBUSTIVEL (TIPO_COMBUSTIVEL, FATOR_EMISSAO)
VALUES ('Etanol', 1.86);

INSERT INTO COMBUSTIVEL (TIPO_COMBUSTIVEL, FATOR_EMISSAO)
VALUES ('GNV', 2.00);

INSERT INTO COMBUSTIVEL (TIPO_COMBUSTIVEL, FATOR_EMISSAO)
VALUES ('ElÃ©trico', 0);

COMMIT;

-- Inserir usuÃ¡rios usando o pacote PKG_USUARIO

BEGIN
    PKG_USUARIO.INSERIR_USUARIO('JoÃ£o Pereira', 'joao.pereira@example.com', 'senha123');
    PKG_USUARIO.INSERIR_USUARIO('Ana Souza', 'ana.souza@example.com', 'senha456');
    PKG_USUARIO.INSERIR_USUARIO('Carlos Lima', 'carlos.lima@example.com', 'senha789');
    PKG_USUARIO.INSERIR_USUARIO('Beatriz Costa', 'beatriz.costa@example.com', 'senha101112');
    PKG_USUARIO.INSERIR_USUARIO('Fernando Alves', 'fernando.alves@example.com', 'senha131415');
    PKG_USUARIO.INSERIR_USUARIO('Mariana Ribeiro', 'mariana.ribeiro@example.com', 'senha161718');
    PKG_USUARIO.INSERIR_USUARIO('Pedro Santos', 'pedro.santos@example.com', 'senha192021');
    PKG_USUARIO.INSERIR_USUARIO('Larissa Martins', 'larissa.martins@example.com', 'senha222324');
    PKG_USUARIO.INSERIR_USUARIO('Lucas Oliveira', 'lucas.oliveira@example.com', 'senha252627');
    PKG_USUARIO.INSERIR_USUARIO('Gabriela Fernandes', 'gabriela.fernandes@example.com', 'senha282930');
END;
/

-- Inserir veÃ­culos usando o pacote PKG_VEICULO

BEGIN
    PKG_VEICULO.INSERIR_VEICULO('Toyota', 'Corolla', 2018, 12.0, 1, 1);
    PKG_VEICULO.INSERIR_VEICULO('Honda', 'Civic', 2019, 11.0, 2, 1);
    PKG_VEICULO.INSERIR_VEICULO('Ford', 'Focus', 2017, 10.5, 3, 2);
    PKG_VEICULO.INSERIR_VEICULO('Volkswagen', 'Golf', 2020, 13.0, 4, 1);
    PKG_VEICULO.INSERIR_VEICULO('Chevrolet', 'Onix', 2019, 14.0, 5, 3);
    PKG_VEICULO.INSERIR_VEICULO('Hyundai', 'HB20', 2021, 13.5, 6, 3);
    PKG_VEICULO.INSERIR_VEICULO('Nissan', 'Sentra', 2016, 11.5, 7, 1);
    PKG_VEICULO.INSERIR_VEICULO('Renault', 'Clio', 2015, 12.5, 8, 3);
    PKG_VEICULO.INSERIR_VEICULO('Fiat', 'Uno', 2014, 15.0, 9, 3);
    PKG_VEICULO.INSERIR_VEICULO('Peugeot', '208', 2018, 13.0, 10, 1);
END;
/

-- Inserir viagens usando o pacote PKG_VIAGEM

BEGIN
    PKG_VIAGEM.INSERIR_VIAGEM(SYSDATE - 10, 'SÃ£o Paulo', 'Campinas', 100, 1);
    PKG_VIAGEM.INSERIR_VIAGEM(SYSDATE - 9, 'Rio de Janeiro', 'NiterÃ³i', 30, 2);
    PKG_VIAGEM.INSERIR_VIAGEM(SYSDATE - 8, 'Belo Horizonte', 'Contagem', 20, 3);
    PKG_VIAGEM.INSERIR_VIAGEM(SYSDATE - 7, 'Curitiba', 'Joinville', 130, 4);
    PKG_VIAGEM.INSERIR_VIAGEM(SYSDATE - 6, 'Porto Alegre', 'Caxias do Sul', 120, 5);
    PKG_VIAGEM.INSERIR_VIAGEM(SYSDATE - 5, 'BrasÃ­lia', 'GoiÃ¢nia', 200, 6);
    PKG_VIAGEM.INSERIR_VIAGEM(SYSDATE - 4, 'Salvador', 'Feira de Santana', 100, 7);
    PKG_VIAGEM.INSERIR_VIAGEM(SYSDATE - 3, 'Recife', 'Olinda', 15, 8);
    PKG_VIAGEM.INSERIR_VIAGEM(SYSDATE - 2, 'Fortaleza', 'Caucaia', 25, 9);
    PKG_VIAGEM.INSERIR_VIAGEM(SYSDATE - 1, 'Manaus', 'Iranduba', 30, 10);
END;
/

-- Inserir emissÃµes usando o pacote PKG_EMISSAO

BEGIN
    PKG_EMISSAO.INSERIR_EMISSAO(1);
    PKG_EMISSAO.INSERIR_EMISSAO(2);
    PKG_EMISSAO.INSERIR_EMISSAO(3);
    PKG_EMISSAO.INSERIR_EMISSAO(4);
    PKG_EMISSAO.INSERIR_EMISSAO(5);
    PKG_EMISSAO.INSERIR_EMISSAO(6);
    PKG_EMISSAO.INSERIR_EMISSAO(7);
    PKG_EMISSAO.INSERIR_EMISSAO(8);
    PKG_EMISSAO.INSERIR_EMISSAO(9);
    PKG_EMISSAO.INSERIR_EMISSAO(10);
END;
/

-- Commit final
COMMIT;

SELECT * FROM USUARIO;
SELECT * FROM VEICULO;
SELECT * FROM COMBUSTIVEL;
SELECT * FROM VIAGEM;
SELECT * FROM EMISSAO;

SELECT * FROM USUARIO_AUD;
SELECT * FROM VEICULO_AUD;
SELECT * FROM COMBUSTIVEL_AUD;
SELECT * FROM VIAGEM_AUD;
SELECT * FROM EMISSAO_AUD;


------------------------------------------------------ SCRIPT 06: SCRIPTS DE TESTE E UTILIZACAO DAS PROCEDURES  ------------------------------------------------------


-- Ativar a saÃ­da do servidor
SET SERVEROUTPUT ON SIZE UNLIMITED;
ALTER SESSION SET NLS_NUMERIC_CHARACTERS = '.,';

------------------------------------------------------
-- Teste das funÃ§Ãµes do pacote PKG_UTILS
------------------------------------------------------

DECLARE
    V_RESULTADO NUMBER(10,2);
BEGIN
    -- Teste com valores vÃ¡lidos
    V_RESULTADO := PKG_UTILS.CALCULAR_LITROS_CONSUMIDOS(430, 12.5);
    DBMS_OUTPUT.PUT_LINE('Litros Consumidos (vÃ¡lido): ' || V_RESULTADO);

    -- Teste com valor negativo para distÃ¢ncia
    BEGIN
        V_RESULTADO := PKG_UTILS.CALCULAR_LITROS_CONSUMIDOS(-100, 12.5);
        DBMS_OUTPUT.PUT_LINE('Litros Consumidos (distÃ¢ncia negativa): ' || V_RESULTADO);
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro (distÃ¢ncia negativa): ' || SQLERRM);
    END;

    -- Teste com valor zero para consumo por km
    BEGIN
        V_RESULTADO := PKG_UTILS.CALCULAR_LITROS_CONSUMIDOS(100, 0);
        DBMS_OUTPUT.PUT_LINE('Litros Consumidos (consumo zero): ' || V_RESULTADO);
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro (consumo zero): ' || SQLERRM);
    END;
END;
/


------------------------------------------------------
-- Teste da procedure INSERIR_USUARIO (PKG_USUARIO) ForÃ§ando erros
------------------------------------------------------

BEGIN

    -- Teste adicional: Inserir usuÃ¡rio com nome vazio
    BEGIN
        PKG_USUARIO.INSERIR_USUARIO('', 'nome.vazio@example.com', 'senha123');
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro (nome vazio): ' || SQLERRM);
    END;

    -- Teste com e-mail invÃ¡lido
    BEGIN
        PKG_USUARIO.INSERIR_USUARIO('UsuÃ¡rio Email InvÃ¡lido', 'email-invalido', 'senha123');
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro (email invÃ¡lido): ' || SQLERRM);
    END;

    -- Teste com nome contendo nÃºmeros
    BEGIN
        PKG_USUARIO.INSERIR_USUARIO('UsuÃ¡rio123', 'usuario123@example.com', 'senha123');
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro (nome com nÃºmeros): ' || SQLERRM);
    END;

    -- Teste com senha curta
    BEGIN
        PKG_USUARIO.INSERIR_USUARIO('UsuÃ¡rio Senha Curta', 'senha.curta@example.com', '123');
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro (senha curta): ' || SQLERRM);
    END;
END;
/


------------------------------------------------------
-- Teste da procedure INSERIR_VEICULO (PKG_VEICULO) ForÃ§ando erros
------------------------------------------------------

BEGIN
    -- Teste com dados vÃ¡lidos
    PKG_VEICULO.INSERIR_VEICULO('Marca VÃ¡lida', 'Modelo VÃ¡lido', 2021, 15.0, 1, 1);
    DBMS_OUTPUT.PUT_LINE('VeÃ­culo inserido com sucesso (dados vÃ¡lidos).');
    
    
    -- Teste adicional: Inserir veÃ­culo com ano de fabricaÃ§Ã£o invÃ¡lido
    BEGIN
        PKG_VEICULO.INSERIR_VEICULO('Marca Teste', 'Modelo Teste', 1899, 15.0, 1, 1);
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro (ano de fabricaÃ§Ã£o invÃ¡lido): ' || SQLERRM);
    END;


    -- Teste com marca contendo nÃºmeros
    BEGIN
        PKG_VEICULO.INSERIR_VEICULO('Marca123', 'Modelo Teste', 2021, 15.0, 1, 1);
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro (marca com nÃºmeros): ' || SQLERRM);
    END;

    -- Teste com consumo negativo
    BEGIN
        PKG_VEICULO.INSERIR_VEICULO('Marca Teste', 'Modelo Teste', 2021, -10.0, 1, 1);
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro (consumo negativo): ' || SQLERRM);
    END;

    -- Teste com ID_COMBUSTIVEL inexistente
    BEGIN
        PKG_VEICULO.INSERIR_VEICULO('Marca Teste', 'Modelo Teste', 2021, 15.0, 1, 999);
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro (combustÃ­vel inexistente): ' || SQLERRM);
    END;
END;
/
------------------------------------------------------
-- Teste da procedure INSERIR_VIAGEM (PKG_VIAGEM) ForÃ§ando erros
------------------------------------------------------

BEGIN
    -- Teste com dados vÃ¡lidos
    PKG_VIAGEM.INSERIR_VIAGEM(SYSDATE, 'Origem Teste', 'Destino Teste', 100, 1);
    DBMS_OUTPUT.PUT_LINE('Viagem inserida com sucesso (dados vÃ¡lidos).');

    -- Teste com ID_VEICULO inexistente
    BEGIN
        PKG_VIAGEM.INSERIR_VIAGEM(SYSDATE, 'Origem Teste', 'Destino Teste', 100, 999);
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro (veÃ­culo inexistente): ' || SQLERRM);
    END;
END;
/
------------------------------------------------------
-- Teste da procedure INSERIR_EMISSAO (PKG_EMISSAO) ForÃ§ando erros
------------------------------------------------------

BEGIN
    -- Teste com dados vÃ¡lidos
    PKG_EMISSAO.INSERIR_EMISSAO(1);
    DBMS_OUTPUT.PUT_LINE('EmissÃ£o inserida com sucesso (dados vÃ¡lidos).');

    -- Teste com ID_VIAGEM inexistente
    BEGIN
        PKG_EMISSAO.INSERIR_EMISSAO(999);
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Erro (viagem inexistente): ' || SQLERRM);
    END;
END;
/
------------------------------------------------------
-- Teste da procedure EXPORTAR_VIAGENS_JSON (PKG_VIAGEM)
------------------------------------------------------

BEGIN
    PKG_VIAGEM.EXPORTAR_VIAGENS_JSON;
END;
/


## Print da Tarefa finalizada

https://media.discordapp.net/attachments/966309364002615319/1309689912408146001/Captura_de_tela_2024-11-22_221944.png?ex=67427f47&is=67412dc7&hm=41151706ca825d9f8a2aea8c0d913af7d385fae0fdbf6492f8ebed56b70b0279&=&format=webp&quality=lossless&width=550&height=317


