# Generated by Django 4.1.1 on 2022-11-10 14:10

import datetime
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ("djangoproject", "0003_alter_dayprices_t00_alter_dayprices_t01_and_more"),
    ]

    operations = [
        migrations.AddField(
            model_name="dayprices",
            name="Date",
            field=models.DateField(default=datetime.date.today, verbose_name="Date"),
        ),
    ]
