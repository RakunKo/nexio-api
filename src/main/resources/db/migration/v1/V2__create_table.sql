CREATE TABLE user (
                      id BINARY(16) PRIMARY KEY,
                      name VARCHAR(50) NOT NULL,
                      email VARCHAR(100) NOT NULL,
                      provider_id VARCHAR(100) NOT NULL,
                      profile_img VARCHAR(255),
                      status VARCHAR(20) NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);