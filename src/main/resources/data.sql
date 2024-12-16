-- Creación de roles
INSERT INTO public.role
(id, "name")
VALUES
    (1, 'ADMIN'),
    (2, 'MANAGER'),
    (3, 'OPERATOR'),
    (4, 'VIEWER');

-- Creación de privilegios sobre cada entidad
INSERT INTO public.privilege
("action", entity_type)
VALUES
    ('CREATE', 'TASK'),	 ('READ', 'TASK'),  ('UPDATE', 'TASK'),  ('DELETE', 'TASK'),
    ('CREATE', 'CHECK'), ('READ', 'CHECK'), ('UPDATE', 'CHECK'), ('DELETE', 'CHECK'),
    ('CREATE', 'USER'),  ('READ', 'USER'),  ('UPDATE', 'USER'),  ('DELETE', 'USER'),
    ('CREATE', 'ROLE'),	 ('READ', 'ROLE'),  ('UPDATE', 'ROLE'),  ('DELETE', 'ROLE'),
    ('CREATE', 'TAG'),   ('READ', 'TAG'),   ('UPDATE', 'TAG'),   ('DELETE', 'TAG');

-- Creación de privilegios delegados al rol 'ADMIN'
INSERT INTO public.roles_privileges (role_id, privilege_id)
SELECT r.id, p.id
FROM public."role" r
         JOIN public."privilege" p ON r."name" = 'ADMIN'
WHERE (p."action", p.entity_type) IN
      (('CREATE', 'TASK'),  ('READ', 'TASK'),  ('UPDATE', 'TASK'),  ('DELETE', 'TASK'),
       ('CREATE', 'CHECK'), ('READ', 'CHECK'), ('UPDATE', 'CHECK'), ('DELETE', 'CHECK'),
       ('CREATE', 'USER'),  ('READ', 'USER'),  ('UPDATE', 'USER'),  ('DELETE', 'USER'),
       ('CREATE', 'ROLE'),  ('READ', 'ROLE'),  ('UPDATE', 'ROLE'),  ('DELETE', 'ROLE'),
       ('CREATE', 'TAG'),   ('READ', 'TAG'),   ('UPDATE', 'TAG'),   ('DELETE', 'TAG'));

-- Creación de privilegios delegados al rol 'MANAGER'
INSERT INTO public.roles_privileges (role_id, privilege_id)
SELECT r.id, p.id
FROM public."role" r
         JOIN public."privilege" p ON r."name" = 'MANAGER'
WHERE (p."action", p.entity_type) IN
      (('CREATE', 'TASK'),  ('READ', 'TASK'),  ('UPDATE', 'TASK'),  ('DELETE', 'TASK'),
       ('CREATE', 'CHECK'), ('READ', 'CHECK'), ('UPDATE', 'CHECK'), ('DELETE', 'CHECK'),
       ('CREATE', 'USER'),  ('READ', 'USER'),  ('UPDATE', 'USER'),
       ('CREATE', 'TAG'),   ('READ', 'TAG'),   ('UPDATE', 'TAG'),   ('DELETE', 'TAG'));

-- Creación de privilegios delegados al rol 'OPERATOR'
INSERT INTO public.roles_privileges (role_id, privilege_id)
SELECT r.id, p.id
FROM public."role" r
         JOIN public."privilege" p ON r."name" = 'OPERATOR'
WHERE (p."action", p.entity_type) IN
      (                     ('READ', 'TASK'),  ('UPDATE', 'TASK'),  ('DELETE', 'TASK'),
                            ('READ', 'CHECK'), ('UPDATE', 'CHECK'), ('DELETE', 'CHECK'),
                            ('READ', 'USER'),
                            ('READ', 'ROLE'),
                            ('CREATE', 'TAG'),   ('READ', 'TAG'),   ('UPDATE', 'TAG'));

-- Creación de privilegios delegados al rol 'ADMIN'
INSERT INTO public.roles_privileges (role_id, privilege_id)
SELECT r.id, p.id
FROM public."role" r
         JOIN public."privilege" p ON r."name" = 'VIEWER'
WHERE (p."action", p.entity_type) IN
      (                     ('READ', 'TASK'),
                            ('READ', 'CHECK'),
                            ('READ', 'USER'),
                            ('READ', 'ROLE'),
                            ('READ', 'TAG'));
