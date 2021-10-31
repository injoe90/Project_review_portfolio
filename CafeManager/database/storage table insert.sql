#storage id
#마카롱 1 / 원두 2 / 과일 3 / 디저트 4 / 유제품 5

-- storage_id INT,
-- dairy_name NVARCHAR(20),
-- dairy_count INT,
-- dairy_date DATE, -> 입고날짜
-- dairy_shelf DATE, -> 유통기한
-- dairy_expire INT -> 남은 기한

INSERT INTO storage_cafe VALUES (1, '마카롱창고');
INSERT INTO storage_cafe VALUES (2, '원두창고');
INSERT INTO storage_cafe VALUES (3, '과일창고');
INSERT INTO storage_cafe VALUES (4, '디저트창고');
INSERT INTO storage_cafe VALUES (5, '유제품창고');

INSERT INTO macaron_frozen VALUES (1,'딸기 마카롱', 10, CURRENT_DATE, CURRENT_DATE+4, CURRENT_DATE+4 - CURRENT_DATE);
INSERT INTO macaron_frozen VALUES (1,'포도 마카롱', 10, CURRENT_DATE, CURRENT_DATE+3, CURRENT_DATE+3 - CURRENT_DATE);
INSERT INTO macaron_frozen VALUES (1,'레몬 마카롱', 10, CURRENT_DATE, CURRENT_DATE+4, CURRENT_DATE+4 - CURRENT_DATE);


INSERT INTO stock_bean VALUES (2, '에티오피아 예가체프', 8, "2021-02-18", "2021-05-18", 88);
INSERT INTO stock_bean VALUES (2, '콜롬비아 수프리모', 10, "2021-02-18", "2021-05-30", 100);

INSERT INTO stock_fruit VALUES (3, '블루베리', 10, CURRENT_DATE, CURRENT_DATE+3, CURRENT_DATE+3 - CURRENT_DATE );
INSERT INTO stock_fruit VALUES (3, '딸기', 15, CURRENT_DATE, CURRENT_DATE+4, CURRENT_DATE+4 - CURRENT_DATE );
INSERT INTO stock_fruit VALUES (3, '청포도', 7, CURRENT_DATE, CURRENT_DATE+4, CURRENT_DATE+4 - CURRENT_DATE );
INSERT INTO stock_fruit VALUES (3, '망고', 25, CURRENT_DATE, CURRENT_DATE+4, CURRENT_DATE+4 - CURRENT_DATE );

INSERT INTO stock_dessert VALUES (4, '치즈번', 3, CURRENT_DATE, "2021-04-30", 4);
INSERT INTO stock_dessert VALUES (4, '모카번', 5, CURRENT_DATE, "2021-05-01", 5);
INSERT INTO stock_dessert VALUES (4, '허니버터브레드', 7, CURRENT_DATE, "2021-04-30", 4);
INSERT INTO stock_dessert VALUES (4, '레드벨벳', 5, CURRENT_DATE, "2021-05-02", 6);
INSERT INTO stock_dessert VALUES (4, '뉴욕치즈케이크', 5, CURRENT_DATE, "2021-05-01", 5);
INSERT INTO stock_dessert VALUES (4, '생크림케이크', 3, CURRENT_DATE, "2021-05-29", 3);
INSERT INTO stock_dessert VALUES (4, '사탕', 15, CURRENT_DATE, "2021-05-10", 14);
INSERT INTO stock_dessert VALUES (4, '막대과자', 10, CURRENT_DATE, "2021-05-10", 14);
INSERT INTO stock_dessert VALUES (4, '초콜렛', 5, CURRENT_DATE, "2021-05-10", 14);


INSERT INTO stock_dairy VALUES (5, '우유', 15, CURRENT_DATE, "2021-05-03", 7);
INSERT INTO stock_dairy VALUES (5, '생크림', 15, CURRENT_DATE, "2021-05-06", 10);
INSERT INTO stock_dairy VALUES (5, '요구르트', 13, CURRENT_DATE, "2021-05-04", 8);

