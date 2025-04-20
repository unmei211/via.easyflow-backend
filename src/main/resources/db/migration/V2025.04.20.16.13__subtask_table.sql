CREATE TABLE IF NOT EXISTS subtask
(
    document jsonb
);

CREATE UNIQUE INDEX ON subtask ((document ->> 'subtaskId'));