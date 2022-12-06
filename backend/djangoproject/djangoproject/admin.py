from django.contrib import admin
from .models import Luokannimi, DayPrices

# Registerin admin sites
admin.site.register(Luokannimi)
admin.site.register(DayPrices)