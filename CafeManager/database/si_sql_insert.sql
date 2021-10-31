# 다음 테이블에 대한 Insert 쿼리

-- CREATE TABLE tab (
-- tab_num INT
-- );
-- 



# 오토 인크리먼트 설정. 반복실행으로 새로운 tab_num 생성
INSERT INTO tab VALUES (tab_num);

-- CREATE TABLE recipe (
-- recipe_num INT,
-- recipe_menu NVARCHAR(20),
-- item1_name NVARCHAR(20),
-- item1_count INT,
-- item2_name NVARCHAR(20),
-- item2_count INT,
-- item3_name NVARCHAR(20),
-- item3_count INT,
-- item4_name NVARCHAR(20),
-- item4_count INT
-- );
-- 
# recipe Insert
INSERT INTO recipe VALUES (recipe_num, '아메리카노', '원두', 15, null, null, null, null, null, null);
INSERT INTO recipe VALUES (recipe_num, '카페라떼', '원두', 15, '우유', 200, null, null, null, null);
INSERT INTO recipe VALUES (recipe_num, '딸기스무디', '딸기', 200,  '우유', 50, '요구르트', 15, null, null);
INSERT INTO recipe VALUES (recipe_num, '망고스무디', '망고', 250,  '우유', 50, '요구르트', 15, null, null);
INSERT INTO recipe VALUES (recipe_num, '블루베리스무디', '블루베리', 300,  '우유', 50, '요구르트', 10, null, null);
INSERT INTO recipe VALUES (recipe_num, '딸기주스', '딸기', 200,null, null, null, null, null, null);
INSERT INTO recipe VALUES (recipe_num, '망고주스', '망고', 250, null, null, null, null, null, null);


-- CREATE TABLE cart (
-- table_id INT NOT NULL,
-- menu_id INT NOT NULL,
-- menu_name NVARCHAR(20) NOT NULL,
-- cart_count INT NOT NULL,
-- temp_option INT NOT NULL,   0:Hot , 1: Ice
-- size_option INT NOT NULL     0: 톨, 1:벤티, 2:그란데
-- );

# 
-- CREATE TABLE cart (
-- cart_num INT NOT NULL,
-- table_id INT NOT NULL,
-- menu_name NVARCHAR(20) NOT NULL,
-- cart_count INT NOT NULL,
-- temp_option INT NOT NULL,
-- size_option INT NOT NULL
-- );
-- 	

INSERT INTO cart VALUES (cart_num, 1 , '아메리카노', 2, 1, 3, 5000);
INSERT INTO cart VALUES (cart_num, 7 , '카페라떼', 1, 0, 1, 4000);
#



# insert order_detail - 반복실행
INSERT INTO order_detail VALUES (order_id, 10, 1, '아메리카노', 1);

# insert buycart
INSERT INTO buy VALUES (buy_id, 12, 2, '카페라떼', 2);



menu_cate