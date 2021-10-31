
# 카트 CRUD SQL구문

SELECT c.table_id, c.menu_name, m.menu_eng, m.price, c.cart_count, c.temp_option, c.size_option, m.price*c.cart_count as cart_price  FROM cart c INNER JOIN menu m ON c.menu_name = m.menu_name where table_id = 1;

UPDATE cart SET temp_option= 2, size_option = 2 WHERE cart_num = 2;

select * from cart;

INSERT INTO cart VALUES (cart_num, 2, '딸기쥬스', 1, 1, 1);

DELETE FROM cartlist WHERE cart_num=7;



CREATE TABLE cartlist as 
	SELECT c. cart_num, c.table_id, c.menu_name, m.menu_eng, m.price, c.cart_count, c.temp_option, c.size_option, m.price*c.cart_count as cart_price  
	FROM cart c INNER JOIN menu m ON c.menu_name = m.menu_name;


SELECT * from cartlist WHERE table_id = 7;

# buy, order_detail CRUD SQL구문

INSERT INTO buy VALUES (buy_id, 7, CURRENT_TIMESTAMP)




CREATE TABLE orderlist as
	SELECT b.buy_id, cl.table_id, cl.menu_name, cl.price, cl.cart_count, cl.temp_option, cl.size_option, cl.cart_price, b.buy_date  
	FROM buy b INNER JOIN cartlist cl ON b.cart_id = cl.cart_num;

CREATE TABLE buylist as
	SELECT b.buy_id, cl.table_id, cl.menu_name, cl.price, cl.cart_count, cl.temp_option, cl.size_option, cl.cart_price, b.buy_date FROM buy b INNER JOIN cartlist cl ON b.cart_id = cl.cart_num WHERE cl.table_id = 2;



# 관리자용 재고 조회


CREATE TABLE total_stock as 
	SELECT storage_id, name, s_count, s_date, shelf, expire FROM macaron_frozen
	UNION
	SELECT storage_id, name, s_count, s_date, shelf, expire FROM stock_bean
	UNION 
	SELECT storage_id, name, s_count, s_date, shelf, expire FROM stock_dairy
	UNION 
	SELECT storage_id, name, s_count, s_date, shelf, expire FROM stock_dessert
	UNION 
	SELECT storage_id, name, s_count, s_date, shelf, expire FROM stock_fruit
	ORDER BY storage_id;

SELECT * FROM totalstock;
cartlist
CREATE TABLE totalstock as 
	SELECT tc.storage_id, sc.storage_name, tc.name, tc.s_count, tc.s_date, tc.shelf, tc.expire FROM total_stock tc INNER JOIN storage_cafe sc ON tc.storage_id = sc.storage_id;

SELECT tc.storage_id, tc.name, tc.s_count FROM total_stock tc INNER JOIN storage_cafe sc ON tc.storage_id = sc.storage_id ORDER BY s_count ASC;

SELECT name, s_count, expire FROM macaron_frozen ORDER BY s_count ASC;
SELECT name, s_count, expire FROM macaron_frozen ORDER BY expire ASC;

SELECT name, s_count, expire FROM macaron_frozen ORDER BY s_count ASC;
SELECT name, s_count, expire FROM macaron_frozen ORDER BY expire ASC;

SELECT name, s_count, expire FROM stock_bean ORDER BY s_count ASC;
SELECT name, s_count, expire FROM stock_bean ORDER BY expire ASC;

SELECT name, s_count, expire FROM stock_dairy ORDER BY s_count ASC;
SELECT name, s_count, expire FROM stock_dairy ORDER BY expire ASC;

SELECT name, s_count, expire FROM stock_dessert ORDER BY s_count ASC;
SELECT name, s_count, expire FROM stock_dessert ORDER BY expire ASC;
    
SELECT name, s_count, expire FROM stock_fruit ORDER BY s_count ASC;
SELECT name, s_count, expire FROM stock_fruit ORDER BY expire ASC;