from rest_framework import serializers
from .models import Luokannimi

class LuokannimiSerializer(serializers.ModelSerializer):
    class Meta:
        model = Luokannimi
        fields = ['id', 'muuttuja1', 'muuttuja2']