# Generated by Django 4.1.1 on 2022-11-10 13:24

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ("djangoproject", "0001_initial"),
    ]

    operations = [
        migrations.CreateModel(
            name="DayPrices",
            fields=[
                (
                    "id",
                    models.BigAutoField(
                        auto_created=True,
                        primary_key=True,
                        serialize=False,
                        verbose_name="ID",
                    ),
                ),
                ("T00", models.BooleanField()),
                ("T01", models.BooleanField()),
                ("T02", models.BooleanField()),
                ("T03", models.BooleanField()),
                ("T04", models.BooleanField()),
                ("T05", models.BooleanField()),
                ("T06", models.BooleanField()),
                ("T07", models.BooleanField()),
                ("T08", models.BooleanField()),
                ("T09", models.BooleanField()),
                ("T10", models.BooleanField()),
                ("T11", models.BooleanField()),
                ("T12", models.BooleanField()),
                ("T13", models.BooleanField()),
                ("T14", models.BooleanField()),
                ("T15", models.BooleanField()),
                ("T16", models.BooleanField()),
                ("T17", models.BooleanField()),
                ("T18", models.BooleanField()),
                ("T19", models.BooleanField()),
                ("T20", models.BooleanField()),
                ("T21", models.BooleanField()),
                ("T22", models.BooleanField()),
                ("T23", models.BooleanField()),
            ],
        ),
    ]
