#VERSION 4.0 IVA
CREATE TABLE `tipo_actividad_economica` (
  `id` int(14) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(6) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `empresa_id` int(14) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

ALTER TABLE tipo_actividad_economica MODIFY codigo varchar(6) NOT NULL;
alter table factura add column   tipo_actividad_economica_id int(11) default null;

CREATE TABLE `tarifa_iva` (
  `id` int(14) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL,
  `codigo` varchar(2) NOT NULL,
  `empresa_id` int(14) NOT NULL,
  porcentaje float not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

ALTER TABLE tarifa_iva ADD porcentaje float not NULL;

insert into tarifa_iva values (1,'Tarifa 0% (Exento)','01',1,0);
insert into tarifa_iva values (2,'Tarifa reducida 1%','02',1,1);
insert into tarifa_iva values (3,'Tarifa reducida 2%','03',1,2);
insert into tarifa_iva values (4,'Tarifa reducida 4%','04',1,4);
insert into tarifa_iva values (5,'Transitorio 0%','05',1,0);
insert into tarifa_iva values (6,'Transitorio 4%','06',1,4);
insert into tarifa_iva values (7,'Transitorio 8%','07',1,8);
insert into tarifa_iva values (8,'Tarifa general 13%','08',1,13);

alter table factura_detalle add column tarifa_iva_id int(11) default null;
alter table factura_detalle add column medida_id int(11) null;


#VERSION 3.16.0
alter table confirma_rechaza_documento add bill_sender_id int(11) default null;
alter table confirma_rechaza_documento add numero_consecutivo_receptor varchar(32) default null;
alter table confirma_rechaza_documento add tipo varchar(32) default null;
alter table confirma_rechaza_documento add mensaje varchar(32) default null;


CREATE TABLE `medida` (
  `id` int(14) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL,
  `simbolo` varchar(12) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

ALTER TABLE medida MODIFY simbolo varchar(12) NOT NULL;
ALTER TABLE medida ADD categoria varchar(32) DEFAULT 'MERCANCIA' NULL;
update medida set categoria = 'SERVICIO' where simbolo in ('Al','Alc','Cm', 'I','Os','Sp','Spe','St', 'd');
update medida set categoria = 'MERCANCIA' where simbolo = 'cm' and descripcion like 'cent%';



#Nota de creditos iva + medida
alter table nota_credito add column   tipo_actividad_economica_id int(11) default null;
alter table nota_credito_detalle add column tarifa_iva_id int(11) default null;
alter table nota_credito_detalle add column medida_id int(11) null;


#Inserts de tipo de actividad economica.
# -- -----------------------------------

ALTER TABLE tipo_actividad_economica MODIFY codigo varchar(6) NOT NULL;
-- select id, key_path, cedula, nombre from empresa where nombre like '%PABLO UGALDE ROJAS%';
insert into tipo_actividad_economica(codigo, descripcion, empresa_id) values ('729001','OTRAS ACTIVIDADES DE INFORMATICA',4);

-- select id, key_path, cedula, nombre from empresa where nombre like '%Andres Alejandro Madriz Miranda%';
insert into tipo_actividad_economica(codigo, descripcion, empresa_id) values ('452003','MANTENIMINETO, REPARACION Y APLIACIONES DE EDIFICIOS, APARTAMENTOS, CONDOMINIOS Y CASAS',51);
insert into tipo_actividad_economica(codigo, descripcion, empresa_id) values ('741402','ASESORAMIENTO EMPRESARIAL Y EN MARERIA DE GESTION (HANDLING)',51);

-- select id, key_path, cedula, nombre from empresa where key_path like '%310153809112%'
insert into tipo_actividad_economica(codigo, descripcion, empresa_id) values ('743006','PUBLICDAD A TRAVEZ DE MEDIOS ELECTRONICOS',8);

-- select id, key_path, cedula, nombre from empresa where key_path like '%310151521412%'
insert into tipo_actividad_economica(codigo, descripcion, empresa_id) values ('701004','ALQUILER DE EDIFICIOS Y PROPIEDADES DIFERENTES A CASAS DE HABITACION',9);
insert into tipo_actividad_economica(codigo, descripcion, empresa_id) values ('743004','SERVICIOS DE PUBLICIDAD',9);

-- select id, key_path, cedula, nombre from empresa where key_path like '%310139858306%'
insert into tipo_actividad_economica(codigo, descripcion, empresa_id) values ('743006','PUBLICDAD A TRAVEZ DE MEDIOS ELECTRONICOS',52);
insert into tipo_actividad_economica(codigo, descripcion, empresa_id) values ('515201','VENTA AL POR MAYOR DE EQUIPO DE COMPUTO, SUS PARTES Y ACCESORIOS',52);

-- select id, key_path, cedula, nombre from empresa where key_path like '%310134509723%'
insert into tipo_actividad_economica(codigo, descripcion, empresa_id) values ('743001','PUBLICIDAD',6);

-- 310104916202
-- select id, key_path, cedula, nombre from empresa where key_path like '%310104916202%';
insert into tipo_actividad_economica(codigo, descripcion, empresa_id) values ('701003','COMPRA Y VENTA DE PROPIEDADES (INVERSIONISTAS)',82);

-- select id, key_path, cedula, nombre from empresa where key_path like '%011031092905%';
insert into tipo_actividad_economica(codigo, descripcion, empresa_id) values ('743001','PUBLICIDAD',5);
insert into tipo_actividad_economica(codigo, descripcion, empresa_id) values ('749903','SERVICIOS SECRETARIALES Y/O ODICINISTA',5);

-- select id, key_path, cedula, nombre from empresa where key_path like '%310158992332%';
insert into tipo_actividad_economica(codigo, descripcion, empresa_id) values ('515001','VENTA AL POR MAYOR DE MAQUINARIA Y EQUPO INDUSTRIAL DE CONSTRUCCION, INGENIERIA CIVIL Y OTROS, ASI COMO SUS ACCESORIOS',58);

-- LA CEDULA QUE VIENE NO COINCIDE CON LAS QUE ESTAN EN LA BASE DE DATOS -101360975
-- select id, key_path, cedula, nombre from empresa where key_path like '%310136085716%';
insert into tipo_actividad_economica(codigo, descripcion, empresa_id) values ('523402','VENTA AL POR MENOR ARTICULOS DE FERRETERIA PINTURAS MADERA Y MATERIALES PARA LA CONTRSUCCION',57);

-- select id, key_path, cedula, nombre from empresa where key_path like '%1071801%';
insert into tipo_actividad_economica(codigo, descripcion, empresa_id) values ('741203','ACTIVIDAD DE CONTABILIDAD (CONTADORES), TENEDURIA DE LIBROS, AUTDITORIA Y ASESOR FISCAL',32);






# Unidades de medida
#----------------------
insert into medida(simbolo, descripcion) values('Sp','Servicios Profesionales');
insert into medida(simbolo, descripcion) values('Spe','Servicios personales');
insert into medida(simbolo, descripcion) values('Unid','Unidad');
insert into medida(simbolo, descripcion) values('St','Servicios técnicos');
insert into medida(simbolo, descripcion) values('Al','Alquiler de uso habitacional');
insert into medida(simbolo, descripcion) values('Alc','Alquiler de uso comercial');
insert into medida(simbolo, descripcion) values('Cm','Comisiones');
insert into medida(simbolo, descripcion) values('I','Intereses');
insert into medida(simbolo, descripcion) values('Os','Otro tipo de servicio');
insert into medida(simbolo, descripcion) values('1','uno (indice de refracción)');
insert into medida(simbolo, descripcion) values('\'','minuto');
insert into medida(simbolo, descripcion) values('\'\'','segundo');
insert into medida(simbolo, descripcion) values('°C','grado Celsius');
insert into medida(simbolo, descripcion) values('1/m','1 por metro');
insert into medida(simbolo, descripcion) values('A','Ampere');
insert into medida(simbolo, descripcion) values('A/m','ampere por metro');
insert into medida(simbolo, descripcion) values('A/m2','ampere por metro cuadrado bel');
insert into medida(simbolo, descripcion) values('B','Becquerel');
insert into medida(simbolo, descripcion) values('Bq','coulomb');
insert into medida(simbolo, descripcion) values('C','coulomb por kilogramo coulomb por metro cuadrado');
insert into medida(simbolo, descripcion) values('C/kg','coulomb por metro cúbico');
insert into medida(simbolo, descripcion) values('C/m2','Candela');
insert into medida(simbolo, descripcion) values('C/m3','candela por metro cuadrado centímetro');
insert into medida(simbolo, descripcion) values('cd','Candela');
insert into medida(simbolo, descripcion) values('cd/m2','candela por metro cuadrado');
insert into medida(simbolo, descripcion) values('cm','centímetro');
insert into medida(simbolo, descripcion) values('d','día');
insert into medida(simbolo, descripcion) values('eV','electronvolt');
insert into medida(simbolo, descripcion) values('F','farad');
insert into medida(simbolo, descripcion) values('F/m','farad por metro');
insert into medida(simbolo, descripcion) values('g','Gramo');
insert into medida(simbolo, descripcion) values('Gal','Galón');
insert into medida(simbolo, descripcion) values('Gy','gray');
insert into medida(simbolo, descripcion) values('Gy/s','gray por segundo');
insert into medida(simbolo, descripcion) values('H','henry');
insert into medida(simbolo, descripcion) values('h','hora');
insert into medida(simbolo, descripcion) values('H/m','henry por metro');
insert into medida(simbolo, descripcion) values('Hz','hertz');
insert into medida(simbolo, descripcion) values('J','Joule');
insert into medida(simbolo, descripcion) values('J/(kg·K)','joule por kilogramo kelvin');
insert into medida(simbolo, descripcion) values('J/(mol·K)','joule por mol kelvin');
insert into medida(simbolo, descripcion) values('J/K','joule por kelvin');
insert into medida(simbolo, descripcion) values('J/kg','joule por kilogramo');
insert into medida(simbolo, descripcion) values('J/m3','joule por metro cúbico');
insert into medida(simbolo, descripcion) values('J/mol','joule por mol');
insert into medida(simbolo, descripcion) values('K','Kelvin');
insert into medida(simbolo, descripcion) values('kat','katal');
insert into medida(simbolo, descripcion) values('kat/m3','katal por metro cúbico');
insert into medida(simbolo, descripcion) values('kg','Kilogramo');
insert into medida(simbolo, descripcion) values('kg/m3','kilogramo por metro cúbico');
insert into medida(simbolo, descripcion) values('Km','Kilometro');
insert into medida(simbolo, descripcion) values('Kw','kilovatios');
insert into medida(simbolo, descripcion) values('L','litro');
insert into medida(simbolo, descripcion) values('lm','lumen');
insert into medida(simbolo, descripcion) values('ln','pulgada');
insert into medida(simbolo, descripcion) values('lx','lux');
insert into medida(simbolo, descripcion) values('m','Metro');
insert into medida(simbolo, descripcion) values('m/s','metro por segundo');
insert into medida(simbolo, descripcion) values('m/s2','metro por segundo cuadrado');
insert into medida(simbolo, descripcion) values('m2','metro cuadrado');
insert into medida(simbolo, descripcion) values('m3','metro cúbico');
insert into medida(simbolo, descripcion) values('min','minuto');
insert into medida(simbolo, descripcion) values('mL','mililitro');
insert into medida(simbolo, descripcion) values('mm','Milímetro');
insert into medida(simbolo, descripcion) values('mol','Mol');
insert into medida(simbolo, descripcion) values('mol/m3','mol por metro cúbico');
insert into medida(simbolo, descripcion) values('N','newton');
insert into medida(simbolo, descripcion) values('N/m','newton por metro');
insert into medida(simbolo, descripcion) values('N·m','newton metro');
insert into medida(simbolo, descripcion) values('Np','neper');
insert into medida(simbolo, descripcion) values('o','grado');
insert into medida(simbolo, descripcion) values('Oz','Onzas');
insert into medida(simbolo, descripcion) values('Pa','pascal');
insert into medida(simbolo, descripcion) values('Pa·s','pascal segundo');
insert into medida(simbolo, descripcion) values('rad','radián');
insert into medida(simbolo, descripcion) values('rad/s','radián por segundo');
insert into medida(simbolo, descripcion) values('rad/s2','radián por segundo cuadrado');
insert into medida(simbolo, descripcion) values('s','Segundo');
insert into medida(simbolo, descripcion) values('S','siemens');
insert into medida(simbolo, descripcion) values('sr','estereorradián');
insert into medida(simbolo, descripcion) values('Sv','sievert');
insert into medida(simbolo, descripcion) values('T','tesla');
insert into medida(simbolo, descripcion) values('t','tonelada');
insert into medida(simbolo, descripcion) values('u','unidad de masa atómica unificada');
insert into medida(simbolo, descripcion) values('ua','unidad astronómica');
insert into medida(simbolo, descripcion) values('V','volt');
insert into medida(simbolo, descripcion) values('V/m','volt por metro');
insert into medida(simbolo, descripcion) values('W','Watt');
insert into medida(simbolo, descripcion) values('W/(m·K)','watt por metro kevin');
insert into medida(simbolo, descripcion) values('W/(m2·sr)','watt por metro cuadrado estereorradián');
insert into medida(simbolo, descripcion) values('W/m2','watt por metro cuadrado');
insert into medida(simbolo, descripcion) values('W/sr','watt por estereorradián');
insert into medida(simbolo, descripcion) values('Wb','weber');
insert into medida(simbolo, descripcion) values('oh','ohm');
insert into medida(simbolo, descripcion) values('Otros','Se debe indicar la descripción de la medida a utilizar');



#select  factura_id, empresa_id, tipo_actividad_economica_id from factura where factura_id in (431, 432,445, 446);
#select id, tipo_actividad_economica_id from nota_credito where factura_id in (431, 432);

#select id, medida_id, tarifa_iva_id from factura_detalle where factura_id in (431, 432);
#select id, nota_credito_id, medida_id, tarifa_iva_id  from nota_credito_detalle where nota_credito_id in (247, 248);