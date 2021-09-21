  DROP TABLE IF EXISTS student;

  CREATE TABLE student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL
  );

  INSERT INTO student (first_name, last_name, email) VALUES
  ('Pali', 'Nap', 'pali_nap@test.com'),
  ('Ödön', 'Tök', 'odon_tok@test.com');

  DROP TABLE IF EXISTS subject;

  CREATE TABLE subject (
      id INT AUTO_INCREMENT PRIMARY KEY,
      subject_name VARCHAR(250) NOT NULL,
      active BOOLEAN DEFAULT 'FALSE' NOT NULL
      );

  INSERT INTO subject (subject_name) VALUES
  ('Matematika'),
  ('Irodalom');
