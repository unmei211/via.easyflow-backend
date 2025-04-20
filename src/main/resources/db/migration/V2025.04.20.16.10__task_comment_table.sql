CREATE TABLE IF NOT EXISTS task_comment
(
    document jsonb
);

CREATE UNIQUE INDEX ON task_comment ((document ->> 'taskCommentId'));