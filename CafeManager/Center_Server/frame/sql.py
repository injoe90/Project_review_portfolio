class Sql:

    # 1. 회원 개인정보 #
    userlist = 'SELECT * FROM users';
    userlistone = "SELECT * FROM users WHERE userid='%s'";
    userinsert = "INSERT INTO users VALUES (null,'%s','%s','%s')";
    userdelete = "DELETE FROM users WHERE userid= '%s'";
    userupdate = "UPDATE users SET userpwd='%s', username='%s' WHERE userid='%s'";



    # 2. 유저의 주문이력 정보 #
    orderlistone = "SELECT * FROM orderlist WHERE usernum=%d ORDER BY ordernum DESC LIMIT 1";
    orderlistinsert = "INSERT INTO orderlist VALUES (%d,%d,%d)";



    # 4. 테이블의 사용자의 장바구니 정보 #

    cartSel = "SELECT c.cart_num, c.table_id, c.menu_name, m.menu_eng, m.price, " \
           "c.cart_count, c.temp_option, c.size_option, m.price*c.cart_count as cart_price" \
           "  FROM cart c INNER JOIN menu m ON c.menu_name = m.menu_name where c.table_id = 2"; # table_id 2로 고정. 2번 테이블 장바구니 내역 조회
    cartInsert = "INSERT INTO cart VALUES (cart_num, 2, '%s', %d, %d, %d)";  # table_id 2로 고정. 메뉴이름, 수량, 온도(핫/아이스), 사이즈옵션
    cartDelete = "DELETE FROM carts WHERE cart_num= %d";
    cartUpdate = "UPDATE cart SET temp_option=%d, size_option = %d WHERE cart_num = %d";





    # cartlist = 사용자구분 없이 들어온 장바구니 총 정보

    # 5. 사용자의 테이블별 주문/결제 결과
    buyinsert = "INSERT INTO buy VALUES (buy_id, %d, CURRENT_TIMESTAMP)";
    buyselect = "SELECT b.buy_id, cl.table_id, cl.menu_name, cl.price, cl.cart_count," \
                " cl.temp_option, cl.size_option, cl.cart_price, b.buy_date " \
                "FROM buy b INNER JOIN cartlist cl ON b.cart_id = cl.cart_num WHERE cl.table_id = %d"





    # 6. 관리자의 접수된 전체 주문 정보 #
    insertorder = "INSERT INTO orderdetail VALUES (order_id, %d)"
    orderselect = "SELECT * FROM orderlist ORDER BY buy_date ASC";





    # 관리자의 보유 재고 정보 #


    # 1. 전체 재고 조회
    totalSel = "SELECT tc.storage_id, sc.storage_name, tc.name, tc.s_count, tc.s_date, tc.shelf, tc.expire " \
               "FROM total_stock tc INNER JOIN storage_cafe sc ON tc.storage_id = sc.storage_id"




    # 1-1 전체품목 소진임박 순 재고수량 조회 / 유통기한 만기일순 조회
    totalCountSel = "SELECT tc.storage_id, tc.name, tc.s_count FROM total_stock tc INNER JOIN " \
                    "storage_cafe sc ON tc.storage_id = sc.storage_id ORDER BY s_count ASC;"
    expireSel = "SELECT tc.storage_id, tc.name, tc.s_count FROM total_stock tc INNER JOIN " \
                "storage_cafe sc ON tc.storage_id = sc.storage_id ORDER BY expire ASC;"



    # 2. XCoSel = 품목별 소진임박순 재고수량 조회 / XExSel 유통기한 만기일순 조회
    macCoSel = "SELECT name, s_count, expire FROM macaron_frozen ORDER BY s_count ASC"
    macExSel = "SELECT name, s_count, expire FROM macaron_frozen ORDER BY expire ASC"

    beanCoSel = "SELECT name, s_count, expire FROM stock_bean ORDER BY s_count ASC"
    beanExSel = "SELECT name, s_count, expire FROM stock_bean ORDER BY expire ASC"

    dairyCoSel = "SELECT name, s_count, expire FROM stock_dairy ORDER BY s_count ASC"
    dairyExSel = "SELECT name, s_count, expire FROM stock_dairy ORDER BY expire ASC"

    dessertCoSel = "SELECT name, s_count, expire FROM stock_dessert ORDER BY s_count ASC"
    dessertExSel = "SELECT name, s_count, expire FROM stock_dessert ORDER BY expire ASC"

    fruitCoSel = "SELECT name, s_count, expire FROM stock_fruit ORDER BY s_count ASC"
    fruitExSel = "SELECT name, s_count, expire FROM stock_fruit ORDER BY expire ASC"





    #
    #
    #
    # itemlist = """SELECT i.itemnum, i.itemname, a.authorname, i.price, i.itemdate, i.sells
    #               FROM items i LEFT OUTER JOIN authors a
    #               ON i.authornum = a.authornum """;
    # categoryAll = "WHERE catenum IS NOT NULL ";
    # category = "WHERE catenum = %d ";
    #
    # searchAll = "AND CONCAT (itemname, a.authorname) LIKE '%"
    # searchWithTitle = "AND itemname LIKE '%"
    # searchWithAuthor = "AND a.authorname LIKE '%"
    # ordercon1 = "ORDER BY itemnum DESC ";
    # ordercon2 = "ORDER BY itemdate DESC ";
    # ordercon3 = "ORDER BY sells DESC ";
    # ordercon4 = "ORDER BY itemname ASC ";
    # ordercon5 = "ORDER BY itemname DESC ";
    # ordercon6 = "ORDER BY price ASC ";
    # ordercon7 = "ORDER BY price DESC ";
    # page = " LIMIT %d OFFSET %d";
    #
    #
    # itemlistcount = """SELECT COUNT(itemnum) FROM items i LEFT OUTER JOIN authors a ON i.authornum = a.authornum """;
    # itemlistone = """SELECT i.itemnum, i.catenum, i.itemname, i.price, i.itemdate, i.iteminfo, i.sells, i.series, a.authorname, a.authorinfo
    #                  FROM items i LEFT OUTER JOIN authors a ON i.authornum = a.authornum
    #                  WHERE itemnum = %d""";
    #
    #
    # iteminsert = """INSERT INTO items (catenum, authornum, itemname, price, itemdate, iteminfo, sells, series)
    #                 VALUES (%d, %d, '%s', %d, '%s', '%s', %d, %d)""";
    #
    #
    # itemautoincre = "SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = 'items' AND table_schema = DATABASE()";
    # itemdelete = "DELETE FROM items WHERE itemnum=%d";
    # itemupdate = "UPDATE items SET catenum=%d, authornum=%d, itemname='%s', price=%d, itemdate='%s', iteminfo='%s', series=%d WHERE itemnum=%d";
    #
    # recentPublished = "SELECT itemnum, catenum, itemname, itemdate, price, series FROM items WHERE authornum = %d ORDER BY itemnum DESC LIMIT %d"
    # searchAuthorNameFront = "SELECT authorname, authornum FROM authors WHERE authorname LIKE '%"
    # searchAuthorNameRear = "%' ORDER BY authornum ASC"
    # authorinsert = "INSERT INTO authors VALUES (NULL, '%s', '%s')";
    #
    # sellitem = "UPDATE items SET sells=sells+1 WHERE itemnum=%d"; # 결제후 해당 상품의 판매량 +1
    #
