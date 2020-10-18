CREATE SEQUENCE "sq_gym_address_id"
    START 1 INCREMENT 1;

CREATE SEQUENCE "sq_gym_contact_id"
    START 1 INCREMENT 1;

CREATE SEQUENCE "sq_personal_trainer_address_id"
    START 1 INCREMENT 1;

CREATE SEQUENCE "sq_personal_trainer_contact_id"
    START 1 INCREMENT 1;

CREATE SEQUENCE "sq_sport_id"
    START 1 INCREMENT 1;

CREATE SEQUENCE "sq_student_address_id"
    START 1 INCREMENT 1;

CREATE SEQUENCE "sq_student_contact_id"
    START 1 INCREMENT 1;

CREATE SEQUENCE "sq_user_id"
    START 1 INCREMENT 1;

CREATE TABLE "gym" (
    "id" INT8 NOT NULL,
    "biography" VARCHAR(2000),
    "instagram" VARCHAR(400),
    "registered_at" TIMESTAMP NOT NULL,
    "sports" VARCHAR(1000),
    "updated_at" TIMESTAMP NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE "gym_address" (
    "id" INT8 NOT NULL,
    "city" VARCHAR(300),
    "complement" VARCHAR(300),
    "district" VARCHAR(300),
    "federal_unit" VARCHAR(300),
    "public_place" VARCHAR(300),
    "zip_code" VARCHAR(50),
    "gym_id" INT8 NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE "gym_contact" (
    "id" INT8 NOT NULL,
    "email" VARCHAR(500),
    "name" VARCHAR(500),
    "phone" VARCHAR(50),
    "is_whatsapp" BOOLEAN,
    "gym_id" INT8 NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE "gym_gallery_picture" (
    "gym-entity_id" INT8 NOT NULL,
    "gallery_picture_url" VARCHAR(2500) NOT NULL,
    PRIMARY KEY ("gym-entity_id", "gallery_picture_url")
);

CREATE TABLE "personal_trainer" (
    "id" INT8 NOT NULL,
    "biography" VARCHAR(2000),
    "instagram" VARCHAR(400),
    "registered_at" TIMESTAMP NOT NULL,
    "sports" VARCHAR(1000),
    "updated_at" TIMESTAMP NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE "personal_trainer_address" (
    "id" INT8 NOT NULL,
    "city" VARCHAR(300),
    "complement" VARCHAR(300),
    "district" VARCHAR(300),
    "federal_unit" VARCHAR(300),
    "public_place" VARCHAR(300),
    "zip_code" VARCHAR(50),
    "personal_trainer_id" INT8 NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE "personal_trainer_contact" (
    "id" INT8 NOT NULL,
    "email" VARCHAR(500),
    "name" VARCHAR(500),
    "phone" VARCHAR(50),
    "is_whatsapp" BOOLEAN,
    "personal_trainer_id" INT8 NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE "personal_trainer_gallery_picture" (
    "personal-trainer-entity_id" INT8 NOT NULL,
    "gallery_picture_url" VARCHAR(2500) NOT NULL,
    PRIMARY KEY ("personal-trainer-entity_id", "gallery_picture_url")
);

CREATE TABLE "sport" (
    "id" INT8 NOT NULL,
    "name" VARCHAR(400) NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE "student" (
    "id" INT8 NOT NULL,
    "registered_at" TIMESTAMP NOT NULL,
    "updated_at" TIMESTAMP NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE "student_address" (
    "id" INT8 NOT NULL,
    "city" VARCHAR(300),
    "complement" VARCHAR(300),
    "district" VARCHAR(300),
    "federal_unit" VARCHAR(300),
    "public_place" VARCHAR(300),
    "zip_code" VARCHAR(50),
    "student_id" INT8 NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE "student_contact" (
    "id" INT8 NOT NULL,
    "email" VARCHAR(500),
    "name" VARCHAR(500),
    "phone" VARCHAR(50),
    "is_whatsapp" BOOLEAN,
    "student_id" INT8 NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE "user" (
    "id" INT8 NOT NULL,
    "password" VARCHAR(500) NOT NULL,
    "profile_picture_url" VARCHAR(2500),
    "registered_at" TIMESTAMP NOT NULL,
    "roles" VARCHAR(255) NOT NULL,
    "updated_at" TIMESTAMP NOT NULL,
    "username" VARCHAR(500) NOT NULL,
    PRIMARY KEY ("id")
);

ALTER TABLE IF EXISTS "user"
    ADD CONSTRAINT "uq_user_username" UNIQUE ("username");

ALTER TABLE IF EXISTS "gym_address"
    ADD CONSTRAINT "fk_gym_address_gym_id" FOREIGN KEY ("gym_id") REFERENCES "gym";

ALTER TABLE IF EXISTS "gym_contact"
    ADD CONSTRAINT "fk_gym_contact_gym_id" FOREIGN KEY ("gym_id") REFERENCES "gym";

ALTER TABLE IF EXISTS "gym_gallery_picture"
    ADD CONSTRAINT "fk_gym_gallery_picture_gym_id" FOREIGN KEY ("gym-entity_id") REFERENCES "gym";

ALTER TABLE IF EXISTS "personal_trainer_address"
    ADD CONSTRAINT "fk_personal_trainer_address_personal_trainer_id" FOREIGN KEY ("personal_trainer_id") REFERENCES "personal_trainer";

ALTER TABLE IF EXISTS "personal_trainer_contact"
    ADD CONSTRAINT "fk_personal_trainer_contact_personal_trainer_id" FOREIGN KEY ("personal_trainer_id") REFERENCES "personal_trainer";

ALTER TABLE IF EXISTS "personal_trainer_gallery_picture"
    ADD CONSTRAINT "fk_personal_trainer_gallery_picture_personal_trainer_id" FOREIGN KEY ("personal-trainer-entity_id") REFERENCES "personal_trainer";

ALTER TABLE IF EXISTS "student_address"
    ADD CONSTRAINT "fk_student_address_student_id" FOREIGN KEY ("student_id") REFERENCES "student";

ALTER TABLE IF EXISTS "student_contact"
    ADD CONSTRAINT "fk_student_contact_student_id" FOREIGN KEY ("student_id") REFERENCES "student";
