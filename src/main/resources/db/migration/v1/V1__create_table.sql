CREATE TABLE jwt_keys (
                      id BINARY(16) PRIMARY KEY,
                      private_key BLOB NOT NULL,
                      public_key BLOB NOT NULL,
                      created_at TIMESTAMP NOT NULL,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      active BOOLEAN NOT NULL
);

CREATE TABLE room (
                      id BINARY(16) PRIMARY KEY,
                      name VARCHAR(20) NOT NULL,
                      description VARCHAR(100),
                      user_id BINARY(16) NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);