DO
$do$
    BEGIN
        IF NOT EXISTS (SELECT
                       FROM pg_catalog.pg_roles
                       WHERE rolname = 'admin') THEN
            CREATE ROLE "admin" LOGIN PASSWORD 'password';
        END IF;
    END
$do$;

CREATE USER "user" WITH ENCRYPTED PASSWORD 'password';
CREATE DATABASE "task-management" OWNER "user";
CREATE DATABASE "task-management-test" OWNER "user";