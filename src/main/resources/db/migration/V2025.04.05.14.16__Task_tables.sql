CREATE TABLE IF NOT EXISTS task
(
    document jsonb
);

CREATE UNIQUE INDEX ON task ((document ->> 'taskId'));