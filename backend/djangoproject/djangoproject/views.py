from django.http import JsonResponse
from .models import Luokannimi
from .serializers import LuokannimiSerializer

def LuokannimiLista(request):

    # Get all Luokannimi items
    luokat = Luokannimi.objects.all()

    # Serialize them
    serializer = LuokannimiSerializer(luokat, many=True)

    # return json
    return JsonResponse({"Lista": serializer.data}, safe=False) 
    # Safe false sallii muiden kun listojen muuttamisen jsoniksi