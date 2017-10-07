--Após ter criado as colunas date e timestamp
-- Esses 4 alter table são para melhorar e dividir a data_time

ALTER TABLE points ALTER COLUMN date TYPE DATE 
using to_date(date_time, 'YYYY-MM-DD');

ALTER TABLE points ALTER COLUMN date_time_timestamp TYPE TIMESTAMP
using to_timestamp(date_time, 'YYYY-MM-DD HH24:MI:SS');

--ALTER TABLE points ALTER COLUMN time TYPE TIME
--using to_time(date_time_timestamp, 'HH24:MI:SS');

ALTER TABLE points ALTER COLUMN time TYPE TEXT
using to_char(cast(date_time_timestamp as time), 'HH24:MI:SS')


ALTER TABLE points ALTER COLUMN tempo TYPE TIME
using to_timestamp(time, 'HH24:MI:SS')

--FIM--

--Criação da tabela teste
CREATE TABLE pteste
(
  taxi_id integer,
  date_time text,
  longitude numeric,
  latitude numeric,
  date date,
  date_time_timestamp timestamp without time zone,
  "time" text,
  tempo time without time zone
)

--Comando abaixo será utilizado para criar uma tabela teste com 10000 tuplas
INSERT INTO pteste SELECT * from points limit 10000;

select date, COUNT(date) from points group by date;

SELECT * FROM pteste WHERE date_part('hour', tempo) >= 20 AND date_part('hour', tempo) <= 22

SELECT * FROM pteste WHERE date_part('hour', tempo) >= extract(hour from time '20:38:40');


select date_part('hour', date_time) from points where taxi_id = 1013

select EXTRACT(DAY FROM date_time) as dia,
	EXTRACT(MONTH FROM date_time) as mes,
	EXTRACT(YEAR FROM date_time) as ano,
	EXTRACT(HOUR FROM date_time) as hora,
	EXTRACT(MINUTE FROM date_time) as minuto,
	EXTRACT(SECOND FROM date_time) as segundo from points

select EXTRACT(DAY FROM date) as dia from points where taxi_id = 1013