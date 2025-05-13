CREATE TABLE IF NOT EXISTS cities (
  city_id UUID DEFAULT PRIMARY KEY,
  city_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS districts (
  district_id UUID DEFAULT PRIMARY KEY,
  city_id UUID NOT NULL,
  district_name VARCHAR(100) NOT NULL,
  FOREIGN KEY (city_id) REFERENCES cities(city_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS real_estate_types (
  real_estate_type_id SERIAL PRIMARY KEY,
  real_estate_type_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS apartments (
  apartment_id UUID DEFAULT PRIMARY KEY,
  district_id UUID NOT NULL,
  real_estate_type BIGINT NOT NULL,
  street VARCHAR(100) NOT NULL,
  room_count INT NOT NULL,
  area DECIMAL(10,2) NOT NULL,
  floor INT NOT NULL,
  series VARCHAR(500) NOT NULL,

  FOREIGN KEY (district_id) REFERENCES districts(district_id) ON DELETE CASCADE,
  FOREIGN KEY (real_estate_type) REFERENCES real_estate_types(real_estate_type_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS advertisements (
  advertisement_id UUID DEFAULT PRIMARY KEY,
  apartment_id UUID NOT NULL,
  advertisement_posting_date DATE NOT NULL,
  advertisement_removing_date DATE,
  price DECIMAL(15,2) NOT NULL,
  period VARCHAR(100) NOT NULL,
  transactionType VARCHAR(100) NOT NULL,
  FOREIGN KEY (apartment_id) REFERENCES apartments(apartment_id) ON DELETE CASCADE
);