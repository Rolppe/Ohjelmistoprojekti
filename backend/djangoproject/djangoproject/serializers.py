from rest_framework import serializers
from .models import Luokannimi, DayPrices

class LuokannimiSerializer(serializers.ModelSerializer):
    class Meta:
        model = Luokannimi
        fields = ['id', 'muuttuja1', 'muuttuja2']

class PricesSerializer(serializers.ModelSerializer):
    class Meta:
        model = DayPrices
        fields = ['id','Date', 'H00','H01','H02','H03','H04','H05','H06','H07','H08','H09','H10','H11','H12','H13','H14','H15','H16','H17','H18','H19','H20','H21','H22','H23']