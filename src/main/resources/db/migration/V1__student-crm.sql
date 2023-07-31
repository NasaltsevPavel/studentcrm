-- Version: 1-- Version: 1
-- Description: Create courses and students tables
CREATE TABLE IF NOT EXISTS courses
(
    id            BIGSERIAL PRIMARY KEY,
    course_name   VARCHAR(35) NOT NULL,
    average_grade DOUBLE PRECISION

);

CREATE TABLE IF NOT EXISTS students
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(20)  NOT NULL,
    last_name  VARCHAR(20)  NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    average_grade DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS degrees
(
    id          BIGSERIAL PRIMARY KEY,
    degree_name VARCHAR(20) NOT NULL

);

CREATE TABLE IF NOT EXISTS grades
(
    id    BIGSERIAL PRIMARY KEY,
    grade DOUBLE PRECISION NOT NULL

);


CREATE TABLE IF NOT EXISTS grades_courses
(
    course_id BIGINT NOT NULL,
    grade_id  BIGINT NOT NULL,
    PRIMARY KEY (course_id, grade_id),
    CONSTRAINT fk_course_grade_course FOREIGN KEY (course_id)
        REFERENCES courses (id) ON DELETE CASCADE,
    CONSTRAINT fk_course_grade_grade FOREIGN KEY (grade_id)
        REFERENCES grades (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS grades_students
(
    student_id BIGINT NOT NULL,
    grade_id  BIGINT NOT NULL,
    PRIMARY KEY (student_id, grade_id),
    CONSTRAINT fk_students_grade_students FOREIGN KEY (student_id)
        REFERENCES students (id) ON DELETE CASCADE,
    CONSTRAINT fk_students_grade_grade FOREIGN KEY (grade_id)
        REFERENCES grades (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS degrees_courses
(
    course_id BIGINT NOT NULL,
    degree_id BIGINT NOT NULL,
    PRIMARY KEY (course_id, degree_id),
    CONSTRAINT fk_course_degree_course FOREIGN KEY (course_id)
        REFERENCES courses (id) ON DELETE CASCADE,
    CONSTRAINT fk_course_degree_degree FOREIGN KEY (degree_id)
        REFERENCES degrees (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS degrees_students
(
    student_id BIGINT NOT NULL,
    degree_id  BIGINT NOT NULL,
    PRIMARY KEY (student_id, degree_id),
    CONSTRAINT fk_student_degree_student FOREIGN KEY (student_id)
        REFERENCES students (id) ON DELETE CASCADE,
    CONSTRAINT fk_student_degree_degree FOREIGN KEY (degree_id)
        REFERENCES degrees (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS students_courses
(
    course_id  BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    PRIMARY KEY (course_id, student_id),
    CONSTRAINT fk_course_student_course FOREIGN KEY (course_id)
        REFERENCES courses (id) ON DELETE CASCADE,
    CONSTRAINT fk_course_student_student FOREIGN KEY (student_id)
        REFERENCES students (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS locations
(
    id            BIGSERIAL PRIMARY KEY,
    location_name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS rooms
(
    id          BIGSERIAL PRIMARY KEY,
    room_number integer,
    location_id BIGINT,
    CONSTRAINT fk_location_id FOREIGN KEY (location_id)
    REFERENCES locations (id) ON DELETE CASCADE


);



CREATE TABLE IF NOT EXISTS course_room
(
    course_id BIGINT NOT NULL,
    room_id   BIGINT NOT NULL,
    PRIMARY KEY (course_id, room_id),
    CONSTRAINT fk_course_room_course FOREIGN KEY (course_id)
        REFERENCES courses (id) ON DELETE CASCADE,
    CONSTRAINT fk_course_room_room FOREIGN KEY (room_id)
        REFERENCES rooms (id) ON DELETE CASCADE
);


