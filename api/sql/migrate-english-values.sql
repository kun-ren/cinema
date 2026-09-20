-- Optional migration for an existing installation; this does not create tables.
-- Back up the database and review these changes before running them.
-- Run against the cinema database while the applications are stopped.
-- Hex literals contain legacy UTF-8 values so this script stays English-only.
-- Free-text content (titles, messages, reviews, names) is left unchanged.

START TRANSACTION;

-- Translate t_film.region.
UPDATE `t_film` SET `region` = 'Mainland China' WHERE `region` = CONVERT(0xe4b8ade59bbde5a4a7e99986 USING utf8mb4);
UPDATE `t_film` SET `region` = 'Mainland China' WHERE `region` = CONVERT(0xe58685e59cb0 USING utf8mb4);
UPDATE `t_film` SET `region` = 'Hong Kong' WHERE `region` = CONVERT(0xe4b8ade59bbde9a699e6b8af USING utf8mb4);
UPDATE `t_film` SET `region` = 'Hong Kong' WHERE `region` = CONVERT(0xe9a699e6b8af USING utf8mb4);
UPDATE `t_film` SET `region` = 'Taiwan' WHERE `region` = CONVERT(0xe4b8ade59bbde58fb0e6b9be USING utf8mb4);
UPDATE `t_film` SET `region` = 'Taiwan' WHERE `region` = CONVERT(0xe58fb0e6b9be USING utf8mb4);
UPDATE `t_film` SET `region` = 'USA' WHERE `region` = CONVERT(0xe7be8ee59bbd USING utf8mb4);
UPDATE `t_film` SET `region` = 'South Korea' WHERE `region` = CONVERT(0xe99fa9e59bbd USING utf8mb4);
UPDATE `t_film` SET `region` = 'Japan' WHERE `region` = CONVERT(0xe697a5e69cac USING utf8mb4);
UPDATE `t_film` SET `region` = 'Thailand' WHERE `region` = CONVERT(0xe6b3b0e59bbd USING utf8mb4);
UPDATE `t_film` SET `region` = 'India' WHERE `region` = CONVERT(0xe58db0e5baa6 USING utf8mb4);
UPDATE `t_film` SET `region` = 'France' WHERE `region` = CONVERT(0xe6b395e59bbd USING utf8mb4);
UPDATE `t_film` SET `region` = 'UK' WHERE `region` = CONVERT(0xe88bb1e59bbd USING utf8mb4);
UPDATE `t_film` SET `region` = 'Germany' WHERE `region` = CONVERT(0xe5beb7e59bbd USING utf8mb4);
UPDATE `t_film` SET `region` = 'Other' WHERE `region` = CONVERT(0xe585b6e4bb96 USING utf8mb4);

-- Translate t_film.type.
UPDATE `t_film` SET `type` = 'Romance' WHERE `type` = CONVERT(0xe788b1e68385 USING utf8mb4);
UPDATE `t_film` SET `type` = 'Comedy' WHERE `type` = CONVERT(0xe5969ce589a7 USING utf8mb4);
UPDATE `t_film` SET `type` = 'Science fiction' WHERE `type` = CONVERT(0xe7a791e5b9bb USING utf8mb4);
UPDATE `t_film` SET `type` = 'Animation' WHERE `type` = CONVERT(0xe58aa8e794bb USING utf8mb4);
UPDATE `t_film` SET `type` = 'Horror' WHERE `type` = CONVERT(0xe68190e68096 USING utf8mb4);
UPDATE `t_film` SET `type` = 'Mystery' WHERE `type` = CONVERT(0xe682ace79691 USING utf8mb4);
UPDATE `t_film` SET `type` = 'Adventure' WHERE `type` = CONVERT(0xe58692e999a9 USING utf8mb4);
UPDATE `t_film` SET `type` = 'Action' WHERE `type` = CONVERT(0xe58aa8e4bd9c USING utf8mb4);
UPDATE `t_film` SET `type` = 'Crime' WHERE `type` = CONVERT(0xe78aafe7bdaa USING utf8mb4);
UPDATE `t_film` SET `type` = 'History' WHERE `type` = CONVERT(0xe58e86e58fb2 USING utf8mb4);
UPDATE `t_film` SET `type` = 'Period drama' WHERE `type` = CONVERT(0xe58fa4e8a385 USING utf8mb4);
UPDATE `t_film` SET `type` = 'War' WHERE `type` = CONVERT(0xe68898e4ba89 USING utf8mb4);
UPDATE `t_film` SET `type` = 'Documentary' WHERE `type` = CONVERT(0xe7baaae5bd95e78987 USING utf8mb4);
UPDATE `t_film` SET `type` = 'Family' WHERE `type` = CONVERT(0xe5aeb6e5baad USING utf8mb4);
UPDATE `t_film` SET `type` = 'Biography' WHERE `type` = CONVERT(0xe4bca0e8aeb0 USING utf8mb4);
UPDATE `t_film` SET `type` = 'Martial arts' WHERE `type` = CONVERT(0xe6ada6e4bea0 USING utf8mb4);
UPDATE `t_film` SET `type` = 'Children' WHERE `type` = CONVERT(0xe584bfe7aba5 USING utf8mb4);
UPDATE `t_film` SET `type` = 'Short film' WHERE `type` = CONVERT(0xe79fade78987 USING utf8mb4);
UPDATE `t_film` SET `type` = 'Thriller' WHERE `type` = CONVERT(0xe6838ae6829a USING utf8mb4);
UPDATE `t_film` SET `type` = 'Other' WHERE `type` = CONVERT(0xe585b6e4bb96 USING utf8mb4);

-- Translate t_user.gender.
UPDATE `t_user` SET `gender` = 'Male' WHERE `gender` = CONVERT(0xe794b7 USING utf8mb4);
UPDATE `t_user` SET `gender` = 'Male' WHERE `gender` = CONVERT(0xe794b7e7949f USING utf8mb4);
UPDATE `t_user` SET `gender` = 'Female' WHERE `gender` = CONVERT(0xe5a5b3 USING utf8mb4);
UPDATE `t_user` SET `gender` = 'Female' WHERE `gender` = CONVERT(0xe5a5b3e7949f USING utf8mb4);

-- Translate t_worker.gender.
UPDATE `t_worker` SET `gender` = 'Male' WHERE `gender` = CONVERT(0xe794b7 USING utf8mb4);
UPDATE `t_worker` SET `gender` = 'Male' WHERE `gender` = CONVERT(0xe794b7e7949f USING utf8mb4);
UPDATE `t_worker` SET `gender` = 'Female' WHERE `gender` = CONVERT(0xe5a5b3 USING utf8mb4);
UPDATE `t_worker` SET `gender` = 'Female' WHERE `gender` = CONVERT(0xe5a5b3e7949f USING utf8mb4);

-- Translate t_worker.department.
UPDATE `t_worker` SET `department` = 'Customer Support' WHERE `department` = CONVERT(0xe5aea2e69c8de983a8 USING utf8mb4);
UPDATE `t_worker` SET `department` = 'Operations' WHERE `department` = CONVERT(0xe8bf90e890a5e983a8 USING utf8mb4);
UPDATE `t_worker` SET `department` = 'Marketing' WHERE `department` = CONVERT(0xe5aea3e4bca0e983a8 USING utf8mb4);
UPDATE `t_worker` SET `department` = 'Planning' WHERE `department` = CONVERT(0xe7ad96e58892e983a8 USING utf8mb4);
UPDATE `t_worker` SET `department` = 'Human Resources' WHERE `department` = CONVERT(0xe4babae4ba8be983a8 USING utf8mb4);
UPDATE `t_worker` SET `department` = 'Supervision' WHERE `department` = CONVERT(0xe79b91e7aea1e983a8 USING utf8mb4);

-- Translate t_worker_evaluate.type.
UPDATE `t_worker_evaluate` SET `type` = 'Very satisfied' WHERE `type` = CONVERT(0xe99d9ee5b8b8e6bba1e6848f USING utf8mb4);
UPDATE `t_worker_evaluate` SET `type` = 'Satisfied' WHERE `type` = CONVERT(0xe6bba1e6848f USING utf8mb4);
UPDATE `t_worker_evaluate` SET `type` = 'Dissatisfied' WHERE `type` = CONVERT(0xe4b88de6bba1e6848f USING utf8mb4);

-- Translate legacy screening formats to stable English-independent values.
UPDATE `t_arrangement` SET `type` = '2D' WHERE `type` = CONVERT(0x3244e694bee698a0 USING utf8mb4);
UPDATE `t_arrangement` SET `type` = '3D' WHERE `type` = CONVERT(0x3344e694bee698a0 USING utf8mb4);

-- Normalize dates written by the old film edit form.
UPDATE `t_film`
SET `release_time` = REPLACE(REPLACE(REPLACE(`release_time`,
    CONVERT(0xe5b9b4 USING utf8mb4), ''),
    CONVERT(0xe69c88 USING utf8mb4), ''),
    CONVERT(0xe697a5 USING utf8mb4), '')
WHERE `release_time` LIKE CONCAT('%', CONVERT(0xe5b9b4 USING utf8mb4), '-%');

COMMIT;
