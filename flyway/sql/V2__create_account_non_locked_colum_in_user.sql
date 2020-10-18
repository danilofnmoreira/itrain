
ALTER TABLE IF EXISTS "user"
    ADD COLUMN "account_non_locked" BOOLEAN NOT NULL DEFAULT true;