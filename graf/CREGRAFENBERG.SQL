DROP DATABASE IF EXISTS grafenberg;

CREATE DATABASE IF NOT EXISTS grafenberg;
USE grafenberg;
# -----------------------------------------------------------------------------
#       TABLE : INTERVENTION
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS INTERVENTION
 (
   INT_NUMERO SMALLINT NOT NULL  ,
   TOP_CODE SMALLINT NOT NULL  ,
   INT_DATE DATE NULL  ,
   INT_NUIT CHAR(32) NULL  ,
   INT_DUREE CHAR(32) NULL  
   , PRIMARY KEY (INT_NUMERO) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE INTERVENTION
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_INTERVENTION_TYPE_OPERATION
     ON INTERVENTION (TOP_CODE ASC);

# -----------------------------------------------------------------------------
#       TABLE : CATEGORIE_PERSONNEL
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS CATEGORIE_PERSONNEL
 (
   CAT_CODE CHAR(32) NOT NULL  ,
   CAT_COUTHORAIRE DECIMAL(10,2) NULL  
   , PRIMARY KEY (CAT_CODE) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : BLOC
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS BLOC
 (
   BLO_NUM SMALLINT NOT NULL  ,
   BLO_CHARGES SMALLINT NULL  
   , PRIMARY KEY (BLO_NUM) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : PERSONNEL
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS PERSONNEL
 (
   PER_MATRICULE CHAR(32) NOT NULL  ,
   CAT_CODE CHAR(32) NOT NULL  ,
   PER_NOM CHAR(32) NULL  
   , PRIMARY KEY (PER_MATRICULE) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE PERSONNEL
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_PERSONNEL_CATEGORIE_PERSONNEL
     ON PERSONNEL (CAT_CODE ASC);

# -----------------------------------------------------------------------------
#       TABLE : TYPE_OPERATION
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS TYPE_OPERATION
 (
   TOP_CODE SMALLINT NOT NULL  ,
   BLO_NUM SMALLINT NOT NULL  ,
   TOP_CHARGES SMALLINT NULL  
   , PRIMARY KEY (TOP_CODE) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE TYPE_OPERATION
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_TYPE_OPERATION_BLOC
     ON TYPE_OPERATION (BLO_NUM ASC);

# -----------------------------------------------------------------------------
#       TABLE : INTERV_PERSONNEL
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS INTERV_PERSONNEL
 (
   PER_MATRICULE CHAR(32) NOT NULL  ,
   INT_NUMERO SMALLINT NOT NULL  
   , PRIMARY KEY (PER_MATRICULE,INT_NUMERO) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE INTERV_PERSONNEL
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_INTERV_PERSONNEL_PERSONNEL
     ON INTERV_PERSONNEL (PER_MATRICULE ASC);

CREATE  INDEX I_FK_INTERV_PERSONNEL_INTERVENTION
     ON INTERV_PERSONNEL (INT_NUMERO ASC);


# -----------------------------------------------------------------------------
#       CREATION DES REFERENCES DE TABLE
# -----------------------------------------------------------------------------


ALTER TABLE INTERVENTION 
  ADD FOREIGN KEY FK_INTERVENTION_TYPE_OPERATION (TOP_CODE)
      REFERENCES TYPE_OPERATION (TOP_CODE) ;


ALTER TABLE PERSONNEL 
  ADD FOREIGN KEY FK_PERSONNEL_CATEGORIE_PERSONNEL (CAT_CODE)
      REFERENCES CATEGORIE_PERSONNEL (CAT_CODE) ;


ALTER TABLE TYPE_OPERATION 
  ADD FOREIGN KEY FK_TYPE_OPERATION_BLOC (BLO_NUM)
      REFERENCES BLOC (BLO_NUM) ;


ALTER TABLE INTERV_PERSONNEL 
  ADD FOREIGN KEY FK_INTERV_PERSONNEL_PERSONNEL (PER_MATRICULE)
      REFERENCES PERSONNEL (PER_MATRICULE) ;


ALTER TABLE INTERV_PERSONNEL 
  ADD FOREIGN KEY FK_INTERV_PERSONNEL_INTERVENTION (INT_NUMERO)
      REFERENCES INTERVENTION (INT_NUMERO) ;

