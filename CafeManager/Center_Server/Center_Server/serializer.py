
from rest_framework import serializers

#################
from Center_Server.models import CoffeeBean, Dairy, Dessert, Fruit, Macaron


class CoffeeSerializer(serializers.ModelSerializer):
    class Meta:
        model = CoffeeBean
        fields = ['storage_id', 'bean_name', 'bean_count', 'bean_date', 'bean_shelf']


class DairySerializer(serializers.ModelSerializer):
    class Meta:
        model = Dairy
        fields = ['storage_id', 'dairy_name', 'dairy_count', 'dairy_date', 'dairy_shelf']


class DessertSerializer(serializers.ModelSerializer):
    class Meta:
        model = Dessert
        fields = ['storage_id', 'dessert_name', 'dessert_count', 'dessert_date', 'dessert_shelf']


class FruitSerializer(serializers.ModelSerializer):
    class Meta:
        model = Fruit
        fields = ['storage_id', 'fruit_name', 'fruit_count', 'fruit_date', 'fruit_shelf']


class MacaronSerializer(serializers.ModelSerializer):
    class Meta:
        model = Macaron
        fields = ['storage_id', 'mac_name', 'mac_count', 'mac_date', 'mac_shelf']