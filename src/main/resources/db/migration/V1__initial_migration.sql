CREATE DATABASE IF NOT EXISTS school_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE school_management;


-- -----------------------------------------------------
-- Table: users
-- -----------------------------------------------------
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       phone VARCHAR(45),
                       password VARCHAR(255) NOT NULL,
                       role ENUM('ADMIN', 'TEACHER', 'PARENT') NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- -----------------------------------------------------
-- Table: parents
-- -----------------------------------------------------
CREATE TABLE parents (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         user_id INT NOT NULL UNIQUE,
                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Table: teachers
-- -----------------------------------------------------
CREATE TABLE teachers (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          user_id INT NOT NULL UNIQUE,
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Table: students
-- -----------------------------------------------------
CREATE TABLE students (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          first_name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          gender VARCHAR(10),
                          date_of_birth DATE,
                          parent_id INT,
                          active BOOLEAN DEFAULT TRUE,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (parent_id) REFERENCES parents(id) ON DELETE SET NULL
);

-- -----------------------------------------------------
-- Table: classes
-- -----------------------------------------------------
CREATE TABLE classes (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         type ENUM('ARABIC', 'QURAN') NOT NULL,
                         level VARCHAR(50),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- -----------------------------------------------------
-- Join Table: students_classes
-- -----------------------------------------------------
CREATE TABLE students_classes (
                                  student_id INT NOT NULL,
                                  class_id INT NOT NULL,
                                  PRIMARY KEY (student_id, class_id),
                                  FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
                                  FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Join Table: teachers_classes
-- -----------------------------------------------------
CREATE TABLE teachers_classes (
                                  teacher_id INT NOT NULL,
                                  class_id INT NOT NULL,
                                  PRIMARY KEY (teacher_id, class_id),
                                  FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE,
                                  FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Table: lessons
-- -----------------------------------------------------
CREATE TABLE lessons (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         class_id INT NOT NULL,
                         date DATE NOT NULL,
                         topic VARCHAR(255),
                         FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Join Table: teachers_lessons
-- -----------------------------------------------------
CREATE TABLE teachers_lessons (
                                  teacher_id INT NOT NULL,
                                  lesson_id INT NOT NULL,
                                  PRIMARY KEY (teacher_id, lesson_id),
                                  FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE,
                                  FOREIGN KEY (lesson_id) REFERENCES lessons(id) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Table: attendances
-- -----------------------------------------------------
CREATE TABLE attendances (
                             lesson_id INT NOT NULL,
                             student_id INT NOT NULL,
                             status ENUM('PRESENT', 'ABSENT') NOT NULL,
                             PRIMARY KEY (lesson_id, student_id),
                             FOREIGN KEY (lesson_id) REFERENCES lessons(id) ON DELETE CASCADE,
                             FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Table: payments
-- -----------------------------------------------------
CREATE TABLE payments (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          student_id INT NOT NULL,
                          amount DECIMAL(10, 2) NOT NULL,
                          payment_date DATE NOT NULL,
                          due_date DATE,
                          note VARCHAR(255),
                          FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);


