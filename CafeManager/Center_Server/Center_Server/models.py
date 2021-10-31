from django.db import models


# 관리자 앱 : 1. 커피 재고
class CoffeeBean(models.Model):
    storage_id = models.IntegerField(db_column='storage_id', primary_key=True)
    bean_name = models.CharField(db_column='bean_name', max_length=20)
    bean_count = models.IntegerField(db_column='bean_count', )
    bean_date = models.DateField(db_column='bean_date', )
    bean_shelf = models.DateField(db_column='bean_shelf', )

    class Meta:
        managed = False
        db_table = 'stock_bean'

    def __str__(self):
        return "품목명 : " + self.bean_name + "재고 수량 : " + str(self.bean_count)


# 관리자 앱 : 2. 유제품 재고
class Dairy(models.Model):
    storage_id = models.IntegerField(db_column='storage_id', primary_key=True)
    dairy_name = models.CharField(db_column='dairy_name', max_length=20)
    dairy_count = models.IntegerField(db_column='dairy_count', )
    dairy_date = models.DateField(db_column='dairy_date', )
    dairy_shelf = models.DateField(db_column='dairy_shelf', )

    class Meta:
        managed = False
        db_table = 'stock_dairy'

    def __str__(self):
        return "품목명 : " + self.dairy_name + "재고 수량 : " + str(self.dairy_count)


# 관리자 앱 : 3. 디저트 재고
class Dessert(models.Model):
    storage_id = models.IntegerField(db_column='storage_id', primary_key=True)
    dessert_name = models.CharField(db_column='dessert_name', max_length=20)
    dessert_count = models.IntegerField(db_column='dessert_count', )
    dessert_date = models.DateField(db_column='dessert_date', )
    dessert_shelf = models.DateField(db_column='dessert_shelf', )

    class Meta:
        managed = False
        db_table = 'stock_dessert'

    def __str__(self):
        return "품목명 : " + self.dessert_name + "재고 수량 : " + str(self.dessert_count)


# 관리자 앱 : 4. 생과일 재고
class Fruit(models.Model):
    storage_id = models.IntegerField(db_column='storage_id', primary_key=True)
    fruit_name = models.CharField(db_column='fruit_name', max_length=20)
    fruit_count = models.IntegerField(db_column='fruit_count', )
    fruit_date = models.DateField(db_column='fruit_date', )
    fruit_shelf = models.DateField(db_column='fruit_shelf', )

    class Meta:
        managed = False
        db_table = 'stock_fruit'

    def __str__(self):
        return "품목명 : " + self.fruit_name + "재고 수량 : " + str(self.fruit_count)


# 관리자 앱 : 5. 마카롱 재고
class Macaron(models.Model):
    storage_id = models.IntegerField(db_column='storage_id', primary_key=True)
    mac_name = models.CharField(db_column='mac_name', max_length=20)
    mac_count = models.IntegerField(db_column='mac_count', )
    mac_date = models.DateField(db_column='mac_date', )
    mac_shelf = models.DateField(db_column='mac_shelf', )

    class Meta:
        managed = False
        db_table = 'macaron_frozen'

    def __str__(self):
        return "품목명 : " + self.mac_name + "재고 수량 : " + str(self.mac_count)
