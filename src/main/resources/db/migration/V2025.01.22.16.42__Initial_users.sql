CREATE TABLE IF NOT EXISTS users
(
    document jsonb
);

CREATE UNIQUE INDEX ON users ((document ->> 'userId'));


INSERT INTO users(document)
VALUES ('{
  "userId": 1,
  "username": "user1",
  "email": "user1@example.com",
  "password": "password1",
  "createdAt": "2025-02-06T00:00:00Z"
}'::jsonb),
       ('{
         "userId": 2,
         "username": "user2",
         "email": "user2@example.com",
         "password": "password2",
         "createdAt": "2025-02-06T00:00:00Z"
       }'::jsonb),
       ('{
         "userId": 3,
         "username": "user3",
         "email": "user3@example.com",
         "password": "password3",
         "createdAt": "2025-02-06T00:00:00Z"
       }'::jsonb),
       ('{
         "userId": 4,
         "username": "user4",
         "email": "user4@example.com",
         "password": "password4",
         "createdAt": "2025-02-06T00:00:00Z"
       }'::jsonb),
       ('{
         "userId": 5,
         "username": "user5",
         "email": "user5@example.com",
         "password": "password5",
         "createdAt": "2025-02-06T00:00:00Z"
       }'::jsonb),
       ('{
         "userId": 6,
         "username": "user6",
         "email": "user6@example.com",
         "password": "password6",
         "createdAt": "2025-02-06T00:00:00Z"
       }'::jsonb),
       ('{
         "userId": 7,
         "username": "user7",
         "email": "user7@example.com",
         "password": "password7",
         "createdAt": "2025-02-06T00:00:00Z"
       }'::jsonb),
       ('{
         "userId": 8,
         "username": "user8",
         "email": "user8@example.com",
         "password": "password8",
         "createdAt": "2025-02-06T00:00:00Z"
       }'::jsonb),
       ('{
         "userId": 9,
         "username": "user9",
         "email": "user9@example.com",
         "password": "password9",
         "createdAt": "2025-02-06T00:00:00Z"
       }'::jsonb),
       ('{
         "userId": 10,
         "username": "user10",
         "email": "user10@example.com",
         "password": "password10",
         "createdAt": "2025-02-06T00:00:00Z"
       }'::jsonb);
