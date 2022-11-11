from django.http import JsonResponse
from .models import Luokannimi, DayPrices
from .serializers import LuokannimiSerializer, PricesSerializer

def LuokannimiLista(request): # TÄMÄ ON REQUEST HANDLER


    # Get all Luokannimi items
    luokat = Luokannimi.objects.all()

    # Serialize them
    serializer = LuokannimiSerializer(luokat, many=True)

    # return json
    return JsonResponse({"Lista": serializer.data}, safe=False) 
    # Safe false sallii muiden kun listojen muuttamisen jsoniksi

def PriceRequestHandler(request):
    price_instance = DayPrices.objects.create(
        H00 = 10, 
        H01 = 20,
        H02 = 30,
        H03 = 40,
        H04 = 50,
        H05 = 60,
        H06 = 70,
        H07 = 80,
        H08 = 90,
        H09 = 80,
        H10 = 70,
        H11 = 60,
        H12 = 50,
        H13 = 40,
        H14 = 30,
        H15 = 20,
        H16 = 10,
        H17 = 20,
        H18 = 30,
        H19 = 40,
        H20 = 50,
        H21 = 60,
        H22 = 70,
        H23 = 80
    )
    # Get all Luokannimi items
    all_prices = DayPrices.objects.all()

    # Serialize them
    serializer = PricesSerializer(all_prices, many=True)

    # return json
    return JsonResponse({"Prices": serializer.data}, safe=False) 
    # Safe false sallii muiden kun listojen muuttamisen jsoniksi