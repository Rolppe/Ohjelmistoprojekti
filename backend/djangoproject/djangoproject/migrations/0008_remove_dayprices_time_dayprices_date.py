# Generated by Django 4.1.1 on 2022-11-11 12:23

import datetime
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ("djangoproject", "0007_rename_hate_dayprices_time"),
    ]

    operations = [
        migrations.RemoveField(
            model_name="dayprices",
            name="Time",
        ),
        migrations.AddField(
            model_name="dayprices",
            name="Date",
            field=models.DateField(default=datetime.date.today, verbose_name="Date"),
        ),
    ]