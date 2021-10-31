INSERT INTO menu_cate VALUES (1,'Coffee');
INSERT INTO menu_cate VALUES (2,'Bread & Cake');
INSERT INTO menu_cate VALUES (3,'Dessert');
INSERT INTO menu_cate VALUES (4,'Juice');
INSERT INTO menu_cate VALUES (5,'Smoothie');

#CREATE TABLE menu_cate(
#	cate_id INT(5),
#	cate_name NVARCHAR(20)
#);

#CREATE TABLE menu(
#	menu_id INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
#	menu_name NVARCHAR(20),
#	cate_id INT(5),
#	price INT(10),
#	menu_state INT(2),
#	menu_title NVARCHAR(20)
#);

# menu_state = 1 --- 판매가능 , 2 이면 sold out
INSERT INTO menu VALUES (menu_id, '아메리카노', 1, 3900, 1, 'BEST');
INSERT INTO menu VALUES (menu_id, '카페라떼', 1, 4000, 1, NULL);
INSERT INTO menu VALUES (menu_id, '카페모카', 1, 4100, 1, NULL);

INSERT INTO menu VALUES (menu_id, '레드벨벳', 2, 3900, 1, 'BEST');
INSERT INTO menu VALUES (menu_id, '뉴욕치즈케이크', 2, 3900, 1, NULL);
INSERT INTO menu VALUES (menu_id, '생크림케이크', 2, 3900, 1, NULL);

INSERT INTO menu VALUES (menu_id, '딸기마카롱', 3, 2000, 1, NULL);
INSERT INTO menu VALUES (menu_id, '바나나마카롱', 3, 2200, 1, NULL);
INSERT INTO menu VALUES (menu_id, '얼그레이마카롱', 3, 2300, 1, 'BEST');

INSERT INTO menu VALUES (menu_id, '딸기쥬스', 4, 3900, 1, NULL);
INSERT INTO menu VALUES (menu_id, '블루베리쥬스', 4, 3900, 1, NULL);
INSERT INTO menu VALUES (menu_id, '망고쥬스', 4, 3900, 1, 'BEST');

INSERT INTO menu VALUES (menu_id, '딸기스무디', 5, 3900, 1, NULL);
INSERT INTO menu VALUES (menu_id, '블루베리스무드', 5, 3900, 1, NULL);
INSERT INTO menu VALUES (menu_id, '망고스무디', 5, 3900, 1, 'BEST');