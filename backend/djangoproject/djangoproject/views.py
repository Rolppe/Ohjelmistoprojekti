from django.http import JsonResponse
from .models import Luokannimi, DayPrices
from .serializers import LuokannimiSerializer, PricesSerializer
from .python.get_request import  combineShite, updateHandler
from django.db import models
import datetime



def LuokannimiLista(request): # TÄMÄ ON REQUEST HANDLER
    # Get all Luokannimi items
    luokat = Luokannimi.objects.all()

    # Serialize them
    serializer = LuokannimiSerializer(luokat, many=True)

    # return json
    return JsonResponse({"Lista": serializer.data}, safe=False) 
    # Safe false sallii muiden kun listojen muuttamisen jsoniksi

def PriceRequestHandler(request):
    
    # Update database
    updateHandler()
    
    # Get all DayPrices items
    all_prices = DayPrices.objects.all()

    # Serialize them
    serializer = PricesSerializer(all_prices, many=True)

    # return json
    return JsonResponse({"Prices": serializer.data}, safe=False) 
    # Safe false sallii muiden kun listojen muuttamisen jsoniksi