from django.urls import path
from Center_Server import views

urlpatterns = [
    path('', views.index, name="index"),
    path('coffee', views.CoffeeStock, name="coffee"),
    path('dairy', views.DairyStock, name="dairy"),
    path('dessert', views.DessertStock, name="dessert"),
    path('fruit', views.FruitStock, name="fruit"),
    path('macaron', views.MacaronStock, name="macaron"),
    path('input', views.inputStorage, name="input"),
    path('delete', views.deleteStorage, name="delete"),
    path('update', views.updateStorage, name="update"),
]
