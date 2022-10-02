from django.db import models

class Luokannimi(models.Model):
    muuttuja1 = models.CharField(max_length=200)
    muuttuja2 = models.CharField(max_length=200)

    def __str__(self):
        return self.muuttuja1 + ' ' + muuttuja2 # Lisää listanäkymään muuttujien nimet