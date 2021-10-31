DROP TABLE IF EXISTS storage_cafe;
DROP TABLE IF EXISTS macaron_frozen;
DROP TABLE IF EXISTS stock_bean;
DROP TABLE IF EXISTS stock_fruit;
DROP TABLE IF EXISTS stock_dessert;
DROP TABLE IF EXISTS stock_dairy;

CREATE TABLE storage_cafe(
	storage_id INT,
	storage_name NVARCHAR(20)
);


CREATE TABLE macaron_frozen(
	storage_id INT,
	mac_name NVARCHAR(20),
	mac_count INT,
	mac_date DATE,
	mac_shelf DATE,
	mac_expire INT
);


CREATE TABLE stock_bean(
	storage_id INT,
	bean_name NVARCHAR(20),
	bean_count INT,
	bean_date DATE,
	bean_shelf DATE,
	bean_expire INT
);


CREATE TABLE stock_fruit(
	storage_id INT,
	fruit_name NVARCHAR(20),
	fruit_count INT,
	fruit_date DATE,
	fruit_shelf DATE,
	fruit_expire INT
);


CREATE TABLE stock_dessert(
	storage_id INT,
	dessert_name NVARCHAR(20),
	dessert_count INT,
	dessert_date DATE,
	dessert_shelf DATE,
	dessert_expire INT
);


CREATE TABLE stock_dairy(
	storage_id INT,
	dairy_name NVARCHAR(20),
	dairy_count INT,
	dairy_date DATE,
	dairy_shelf DATE,
	dairy_expire INT
);


#------------------ PRIMARY KEY 설정------------------
ALTER TABLE storage_cafe ADD PRIMARY KEY (storage_id);

#------------------ FOREIGN KEY 설정------------------





 






