-- Project
CREATE TABLE IF NOT EXISTS project
(
    document jsonb
);
CREATE UNIQUE INDEX ON project ((document ->> 'projectId'));

-- ProjectOwner
CREATE TABLE IF NOT EXISTS project_owner
(
    document jsonb
);
CREATE UNIQUE INDEX ON project_owner ((document ->> 'projectId'));
CREATE UNIQUE INDEX ON project_owner ((document ->> 'projectOwnerId'));

-- ProjectMember
CREATE TABLE IF NOT EXISTS project_member
(
    document jsonb
);
CREATE UNIQUE INDEX ON project_member ((document ->> 'projectMemberId'));
CREATE UNIQUE INDEX ON project_owner ((document ->> 'projectId'));

-- ProjectRole
CREATE TABLE IF NOT EXISTS project_role
(
    document jsonb
);

CREATE UNIQUE INDEX ON project_role ((document ->> 'projectRoleId'));

--ProjectMemberRole
CREATE TABLE IF NOT EXISTS project_member_role
(
    document jsonb
);

CREATE UNIQUE INDEX ON project_member_role ((document ->> 'projectMemberRoleId'));
