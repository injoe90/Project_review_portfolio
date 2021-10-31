from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from rest_framework.parsers import JSONParser

from Center_Server.models \
    import CoffeeBean, Dairy, Dessert, Fruit, Macaron
from Center_Server.serializer \
    import CoffeeSerializer, DairySerializer, DessertSerializer, FruitSerializer, MacaronSerializer


def index(request):
    return render(request, 'index.html')


### DB CRUD: Retrieve ###


def CoffeeStock(request):
    datalist = CoffeeBean.objects.all()
    if request.method == 'GET':
        serializer = CoffeeSerializer(datalist, many=True)
        return JsonResponse(serializer.data, safe=False,
                            json_dumps_params={'ensure_ascii': False})


def DairyStock(request):
    datalist = Dairy.objects.all()
    if request.method == 'GET':
        serializer = DairySerializer(datalist, many=True)
        return JsonResponse(serializer.data, safe=False,
                            json_dumps_params={'ensure_ascii': False})


def DessertStock(request):
    datalist = Dessert.objects.all()
    if request.method == 'GET':
        serializer = DessertSerializer(datalist, many=True)
        return JsonResponse(serializer.data, safe=False,
                            json_dumps_params={'ensure_ascii': False})


def FruitStock(request):
    datalist = Fruit.objects.all()
    if request.method == 'GET':
        serializer = FruitSerializer(datalist, many=True)
        return JsonResponse(serializer.data, safe=False,
                            json_dumps_params={'ensure_ascii': False})


def MacaronStock(request):
    datalist = Macaron.objects.all()
    if request.method == 'GET':
        serializer = MacaronSerializer(datalist, many=True)
        return JsonResponse(serializer.data, safe=False,
                            json_dumps_params={'ensure_ascii': False})


### DB CRUD: Create(Insert) ###

def inputStorage(request):
    if request.method == 'POST':
        rawData = JSONParser().parse(request)
        print(rawData)
        insertItemDB(rawData)
        return JsonResponse("DB Insert process OK...", safe=False,
                            json_dumps_params={'ensure_ascii': False})


def insertItemDB(rawData):
    idNumber = rawData['storage_id']
    if (idNumber // 10) == 1:
        macaronDB = Macaron()
        macaronDB.storage_id = rawData['storage_id']
        macaronDB.mac_name = rawData['mac_name']
        macaronDB.mac_count = rawData['mac_count']
        macaronDB.mac_date = rawData['mac_date']
        macaronDB.mac_shelf = rawData['mac_shelf']
        macaronDB.save()

    if (idNumber // 10) == 2:
        coffeeDB = CoffeeBean()
        coffeeDB.storage_id = rawData['storage_id']
        coffeeDB.bean_name = rawData['bean_name']
        coffeeDB.bean_count = rawData['bean_count']
        coffeeDB.bean_date = rawData['bean_date']
        coffeeDB.bean_shelf = rawData['bean_shelf']
        coffeeDB.save()

    if (idNumber // 10) == 3:
        fruitDB = Fruit()
        fruitDB.storage_id = rawData['storage_id']
        fruitDB.fruit_name = rawData['fruit_name']
        fruitDB.fruit_count = rawData['fruit_count']
        fruitDB.fruit_date = rawData['fruit_date']
        fruitDB.fruit_shelf = rawData['fruit_shelf']
        fruitDB.save()

    if (idNumber // 10) == 4:
        dessertDB = Dessert()
        dessertDB.storage_id = rawData['storage_id']
        dessertDB.dessert_name = rawData['dessert_name']
        dessertDB.dessert_count = rawData['dessert_count']
        dessertDB.dessert_date = rawData['dessert_date']
        dessertDB.dessert_shelf = rawData['dessert_shelf']
        dessertDB.save()

    if (idNumber // 10) == 5:
        dairyDB = Dairy()
        dairyDB.storage_id = rawData['storage_id']
        dairyDB.dairy_name = rawData['dairy_name']
        dairyDB.dairy_count = rawData['dairy_count']
        dairyDB.dairy_date = rawData['dairy_date']
        dairyDB.dairy_shelf = rawData['dairy_shelf']
        dairyDB.save()


### DB CRUD: Delete ###


def deleteStorage(request):
    rawData = JSONParser().parse(request)
    print(rawData)
    if request.method == 'DELETE':
        deleteItemDB(rawData)
    return JsonResponse("Delete process is done!", safe=False,
                        json_dumps_params={'ensure_ascii': False})


def deleteItemDB(rawData):
    idNumber = rawData['storage_id']
    print(idNumber)
    if (idNumber // 10) == 1:
        dataList = Macaron.objects.get(storage_id=idNumber)
        print(dataList)
        dataList.delete()

    if (idNumber // 10) == 2:
        dataList = CoffeeBean.objects.get(storage_id=idNumber)
        print(dataList)
        dataList.delete()

    if (idNumber // 10) == 3:
        dataList = Fruit.objects.get(storage_id=idNumber)
        print(dataList)
        dataList.delete()

    if (idNumber // 10) == 4:
        dataList = Dessert.objects.get(storage_id=idNumber)
        print(dataList)
        dataList.delete()

    if (idNumber // 10) == 5:
        dataList = Dairy.objects.get(storage_id=idNumber)
        print(dataList)
        dataList.delete()


### DB CRUD: Update ###


def updateStorage(request):
    rawData = JSONParser().parse(request)

    if request.method == 'PUT':
        updateItemDB(rawData)
        return JsonResponse("DB Update process Ok...", safe=False,
                            json_dumps_params={'ensure_ascii': False})


def updateItemDB(rawData):
    idNumber = rawData['storage_id']
    if (idNumber // 10) == 1:
        dataList = Macaron.objects.get(storage_id=idNumber)
        dataList.mac_name = rawData['mac_name']
        dataList.mac_count = rawData['mac_count']
        dataList.mac_date = rawData['mac_date']
        dataList.mac_shelf = rawData['mac_shelf']
        dataList.save()

    if (idNumber // 10) == 2:
        dataList = CoffeeBean.objects.get(storage_id=idNumber)
        dataList.bean_name = rawData['bean_name']
        dataList.bean_count = rawData['bean_count']
        dataList.bean_date = rawData['bean_date']
        dataList.bean_shelf = rawData['bean_shelf']
        dataList.save()

    if (idNumber // 10) == 3:
        dataList = Fruit.objects.get(storage_id=idNumber)
        dataList.fruit_name = rawData['fruit_name']
        dataList.fruit_count = rawData['fruit_count']
        dataList.fruit_date = rawData['fruit_date']
        dataList.fruit_shelf = rawData['fruit_shelf']
        dataList.save()

    if (idNumber // 10) == 4:
        dataList = Dessert.objects.get(storage_id=idNumber)
        dataList.dessert_name = rawData['dessert_name']
        dataList.dessert_count = rawData['dessert_count']
        dataList.dessert_date = rawData['dessert_date']
        dataList.dessert_shelf = rawData['dessert_shelf']
        dataList.save()

    if (idNumber // 10) == 5:
        dataList = Dairy.objects.get(storage_id=idNumber)
        dataList.dairy_name = rawData['dairy_name']
        dataList.dairy_count = rawData['dairy_count']
        dataList.dairy_date = rawData['dairy_date']
        dataList.dairy_shelf = rawData['dairy_shelf']
        dataList.save()
