# Generated by Django 4.1.1 on 2022-11-10 14:17

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ("djangoproject", "0004_dayprices_date"),
    ]

    operations = [
        migrations.AlterField(
            model_name="dayprices",
            name="Date",
            field=models.DateTimeField(auto_now_add=True),
        ),
    ]
