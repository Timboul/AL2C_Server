-- Last modification date: 05/05/17


-- Table: UTILISATEUR
CREATE TABLE UTILISATEUR (
    id int  AUTO_INCREMENT,
    nom varchar(30) NOT NULL,
    prenom varchar(30) NOT NULL,
    adresse_mail varchar(70) NOT NULL,
    mot_de_passe varchar(70) NOT NULL,
    CONSTRAINT pk_utilisateur PRIMARY KEY (id)
);

-- Table: CONTACT
CREATE TABLE CONTACT (
    id int  AUTO_INCREMENT,
    nom varchar(30) NOT NULL,
    prenom varchar(30),
    utilisateur_id int NOT NULL,
    CONSTRAINT pk_contact PRIMARY KEY (id),
	CONSTRAINT fk_contact_utilisateur_id FOREIGN KEY (utilisateur_id) REFERENCES UTILISATEUR(id)
);

-- Table: CANAL
CREATE TABLE CANAL (
    id int  AUTO_INCREMENT,
    valeur varchar(80),
    type_canal ENUM('SMS', 'MAIL', 'FACEBOOK', 'CONTACT'),
    contact_id int NOT NULL,
    CONSTRAINT pk_canal PRIMARY KEY (id),
	CONSTRAINT fk_canal_contact_id FOREIGN KEY (contact_id) REFERENCES CONTACT(id)
);



-- Table: EVENEMENT
CREATE TABLE EVENEMENT (
    id int  AUTO_INCREMENT,
    intitule varchar(50) NOT NULL,
    description text,
	etat_evenement ENUM('A VENIR', 'EN COURS', 'PASSE', 'ANNULE'),
    date_debut date,
    date_fin date,
    lieu text,
    nombre_invites int,
    utilisateur_id int,
	message_invitation text,
    CONSTRAINT pk_evenement PRIMARY KEY (id),
	CONSTRAINT fk_evenement_utilisateur_id FOREIGN KEY (utilisateur_id) REFERENCES UTILISATEUR(id)
);

-- Table: MESSAGE
CREATE TABLE MESSAGE (
    id int  AUTO_INCREMENT,
    contenu text,
    utilisateur_id int,
    CONSTRAINT pk_message PRIMARY KEY (id),
	CONSTRAINT fk_message_utilisateur_id FOREIGN KEY (utilisateur_id) REFERENCES UTILISATEUR(id)
);

-- Table: INVITATION
CREATE TABLE INVITATION (
    evenement_id int,
    contact_id int,
    presence boolean,
    nombre_participants int,
    CONSTRAINT pk_invitation PRIMARY KEY (evenement_id,contact_id),
	CONSTRAINT fk_invitation_evenement_id FOREIGN KEY (evenement_id) REFERENCES EVENEMENT(id),
	CONSTRAINT fk_invitation_contact_id FOREIGN KEY (contact_id) REFERENCES CONTACT(id)
);

-- Table: TAG
CREATE TABLE TAG (
    id int AUTO_INCREMENT,
    libelle varchar(50) NOT NULL,
    utilisateur_id int NOT NULL,
    CONSTRAINT pk_tag PRIMARY KEY (id),
	CONSTRAINT fk_tag_utilisateur_id FOREIGN KEY (utilisateur_id) REFERENCES UTILISATEUR(id)
);

-- tables
-- Table: AFFECTATION_TAG
CREATE TABLE AFFECTATION_TAG (
    contact_id int,
    tag_id int,
    CONSTRAINT pk_affectation_tag PRIMARY KEY (contact_id,tag_id),
	CONSTRAINT fk_affectation_tag_contact_id FOREIGN KEY (contact_id) REFERENCES CONTACT(id),
	CONSTRAINT fk_affectation_tag_tag_id FOREIGN KEY (tag_id) REFERENCES TAG(id)
);

-- End of file.

