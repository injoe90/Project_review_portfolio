#DROP TABLE recipe;

# 기본 테이블 
CREATE TABLE tab (
	tab_num INT
);

CREATE TABLE recipe (
	recipe_num INT,
	recipe_menu NVARCHAR(20) NOT NULL,
	item1_name NVARCHAR(20) NOT NULL,
	item1_count INT NOT NULL,
	item2_name NVARCHAR(20) NOT NULL,
	item2_count INT NOT NULL,
	item3_name NVARCHAR(20) NOT NULL,
	item3_count INT NOT NULL,
	item4_name NVARCHAR(20) NOT NULL,
	item4_count INT NOT NULL
);

CREATE TABLE cart (
	cart_num INT,
	table_id INT NOT NULL,
	menu_name NVARCHAR(20) NOT NULL,
	cart_count INT NOT NULL,
	temp_option INT NOT NULL,
	size_option INT NOT NULL,
	cart_price INT NOT NULL
);
	
CREATE TABLE employee (
	emp_id INT,
	emp_name NVARCHAR(20) NOT NULL
);

CREATE TABLE order_detail (
	order_id INT,
	cart_id INT,
	table_id INT NOT NULL,
	menu_name2 NVARCHAR(20) NOT NULL,
	emp_id INT NOT NULL
);


CREATE TABLE buy (
	buy_id INT,
	cart_id INT,
	table_id INT NOT NULL,
	menu_name2 NVARCHAR(20) NOT NULL,
	payment_price INT NOT NULL
);

# 제약조건 
# primary key, unique
ALTER TABLE tab ADD CONSTRAINT tab_num_pk PRIMARY KEY (tab_num);
ALTER TABLE recipe ADD CONSTRAINT recipe_num_pk PRIMARY KEY (recipe_num);
ALTER TABLE cart ADD CONSTRAINT cart_num_pk PRIMARY KEY (cart_num);
ALTER TABLE employee ADD CONSTRAINT employee_primary PRIMARY KEY (emp_id);
ALTER TABLE order_detail ADD CONSTRAINT order_primary PRIMARY KEY (order_id);
ALTER TABLE buy ADD CONSTRAINT buy_primary PRIMARY KEY (buy_id);



# auto increment
ALTER TABLE tab MODIFY tab_num INT NOT NULL AUTO_INCREMENT;
ALTER TABLE recipe MODIFY recipe_num INT NOT NULL AUTO_INCREMENT;
ALTER TABLE cart MODIFY cart_num INT NOT NULL AUTO_INCREMENT;
ALTER TABLE order_detail MODIFY order_id INT NOT NULL AUTO_INCREMENT;
ALTER TABLE buy MODIFY buy_id INT NOT NULL AUTO_INCREMENT;

# 외래키 
ALTER TABLE buy ADD CONSTRAINT buy_cart_fk FOREIGN KEY(cart_id) REFERENCES cart(cart_num);
ALTER TABLE order_detail ADD CONSTRAINT order_detail_cart_fk FOREIGN KEY(cart_id) REFERENCES cart(cart_num);
