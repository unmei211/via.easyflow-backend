CREATE TABLE IF NOT EXISTS task_history
(
    document jsonb
);

CREATE UNIQUE INDEX ON task_history ((document ->> 'taskHistoryId'));