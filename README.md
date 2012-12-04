Sistema de Controle Eleitoral
=============================

Programa para gerência básica de eleições e Simulação de Votação.
Imagens e Sons capturados utilizando sistema de barras do Sistema Operacional Windows.

Banco de dados
--------------------

- Utilize o PostgreSQL
- Configure o arquivo bdconfig.txt 

Neste banco de dados execute a seguinte query:

""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

CREATE TABLE configuracoes
(
  horarioAtivo BOOLEAN DEFAULT (TRUE) NOT NULL
);

INSERT INTO configuracoes VALUES(true);
 
CREATE TABLE cargo
(
  nome character varying(30) NOT NULL,
  "qtdDigitos" integer,
  CONSTRAINT pk_cargo PRIMARY KEY (nome)
);

CREATE TABLE partido
(
  sigla character varying(10) NOT NULL,
  numero integer NOT NULL,
  CONSTRAINT pk_partido PRIMARY KEY (numero),
  CONSTRAINT partido_sigla_key UNIQUE (sigla)
);
 
CREATE TABLE candidato
(
  nome character varying(100),
  partido character varying(10),
  numero integer NOT NULL,
  cargo character varying(20),
  diretorio character varying(255),
  CONSTRAINT candidato_pkey PRIMARY KEY (numero,cargo),
  CONSTRAINT cargo FOREIGN KEY (cargo)
      REFERENCES cargo (nome) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT partido FOREIGN KEY (partido)
      REFERENCES partido (sigla) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE SEQUENCE seq_pesquisa START WITH 1;

CREATE TABLE pesquisa (
	id integer NOT NULL UNIQUE,
	cargo character varying(30),
	data_inicio DATE,
	data_fim DATE,
	nulos_brancos integer,
	indecisos integer,
	entrevistadas integer,
	municipios integer,
	CONSTRAINT fk_cargo FOREIGN KEY (cargo) REFERENCES cargo(nome) ON DELETE CASCADE,
	CONSTRAINT pk_pesquisa PRIMARY KEY (cargo,data_inicio,data_fim)
);

CREATE TABLE candidato_pesquisa
(
  idPesquisa integer NOT NULL,
  numero integer NOT NULL,
  intencaoVoto integer NOT NULL,

  CONSTRAINT fk_id_pesquisa FOREIGN KEY (idPesquisa) REFERENCES pesquisa(id) ON DELETE CASCADE,
  CONSTRAINT pk_numero PRIMARY KEY (idPesquisa,numero)
);

CREATE TABLE votacao_cargos
(
  nome character varying(30) NOT NULL,
  "qtdDigitos" integer,
  CONSTRAINT pk_votacao_cargo PRIMARY KEY (nome)
);

CREATE TABLE votacao_candidatos_votos
(
  nome character varying(30),
  numero integer NOT NULL,
  cargo character varying(30) NOT NULL
);

""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

Se tudo estiver correto o arquivo tp1too.jar poderá ser executado perfeitamente.

Créditos: Mateus Ferreira Silva , Vinicius Lehmann
Contato: mtsferreirasilva@gmail.com