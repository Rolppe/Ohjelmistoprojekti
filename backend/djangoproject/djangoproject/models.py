from django.db import models
import datetime


class Luokannimi(models.Model):
    muuttuja1 = models.CharField(max_length=200)
    muuttuja2 = models.CharField(max_length=200)

    def __str__(self):
        return self.muuttuja1 + ' ' + self.muuttuja2 # Lisää listanäkymään muuttujien nimet

class DayPrices(models.Model):
  
    Date = models.DateField(("Date"), default=datetime.date.today)
    H00 = models.FloatField()
    H01 = models.FloatField()
    H02 = models.FloatField()
    H03 = models.FloatField()
    H04 = models.FloatField()
    H05 = models.FloatField()
    H06 = models.FloatField()
    H07 = models.FloatField()
    H08 = models.FloatField()
    H09 = models.FloatField()
    H10 = models.FloatField()
    H11 = models.FloatField()
    H12 = models.FloatField()
    H13 = models.FloatField()
    H14 = models.FloatField()
    H15 = models.FloatField()
    H16 = models.FloatField()
    H17 = models.FloatField()
    H18 = models.FloatField()
    H19 = models.FloatField()
    H20 = models.FloatField()
    H21 = models.FloatField()
    H22 = models.FloatField()
    H23 = models.FloatField()
