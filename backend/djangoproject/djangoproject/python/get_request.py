import datetime
import requests
from . import constant
from defusedxml.ElementTree import fromstring
from ..models import Luokannimi, DayPrices
from django.db import models



def editTimePeriods(param_time):
  time = param_time
  period_start = time.strftime("%Y") + time.strftime("%m") + time.strftime("%d") + '0000'
  period_end = time.strftime("%Y") + time.strftime("%m") + time.strftime("%d") + '2300'

  return period_start, period_end

def editUrl(token, start_p, end_p):
  formatted_url = 'https://web-api.tp.entsoe.eu/api?securityToken={}\
&documentType=A44&in_Domain=10YFI-1--------U&out_Domain=10YFI-1--------U\
&periodStart={}&periodEnd={}'.format(token, start_p, end_p)

  return formatted_url

def fetchPriceData(url):
  datapoints = []
  ns = {"wtf": "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0"}
  x = requests.get(url)

  if (x.status_code == 200):
    ET = fromstring(x.text)

    for child in ET.findall(".//wtf:Point", ns):
      if child.tag == '{urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0}timeInterval':
        continue
      if len(child) == 2:
        datapoints.append({'hour': child[0].text, 'price': child[1].text})
  else:
    print("An error occured while attempting to call the API")

  return datapoints

def addNewEntry(datapoints,date):
  dp = []
  for item in datapoints:
    temp = float(item['price'])
    temp = round(temp / 1000  * 100 * 1.1, 2) # MWh -> kWh, € -> c, + alv
    dp.append(temp)

  price_instance = DayPrices.objects.create(
        Date = date,
        H00 = dp[0],
        H01 = dp[1],
        H02 = dp[2],
        H03 = dp[3],
        H04 = dp[4],
        H05 = dp[5],
        H06 = dp[6],
        H07 = dp[7],
        H08 = dp[8],
        H09 = dp[9],
        H10 = dp[10],
        H11 = dp[11],
        H12 = dp[12],
        H13 = dp[13],
        H14 = dp[14],
        H15 = dp[15],
        H16 = dp[16],
        H17 = dp[17],
        H18 = dp[18],
        H19 = dp[19],
        H20 = dp[20],
        H21 = dp[21],
        H22 = dp[22],
        H23 = dp[23]
    )

def combineShite(date):
  if (date == "tomorrow"):
    datetime_search = datetime.datetime.now()+ datetime.timedelta(days = 1)
  else:
    datetime_search = datetime.datetime.now()

  start_period, end_period = editTimePeriods(datetime_search)
  print("Start period: " + start_period)
  print("End period: " + end_period + "\n")

  request_url = editUrl(constant.TOKEN, start_period, end_period)
  print("Formatted URL: " + request_url + "\n")
  datapoints = fetchPriceData(request_url)

  

  if (len(datapoints) > 1):
    addNewEntry(datapoints,datetime_search)
  else:
    print("No datapoints to print\n")
  
  #return datapoints
def updateHandler():
  try:
    latest = DayPrices.objects.latest('id')
    latest = latest.Date
  except:
    latest = False
  
  current_time = datetime.datetime.now() +datetime.timedelta(hours=3) 

  ###HUOM KESÄAIKA

  if (datetime.date.today() == latest): # jos tämänpäivän tiedot löytyy kannasta
    if (current_time.hour == 14 and current_time.minute >= 30): # Jos kello on 14.30 - 15.00
      combineShite(date = "tomorrow")# Haetaan kantaan huomisen hinnat
    elif(current_time.hour >= 15): # Jos kello on yli 15
      combineShite(date = "tomorrow")# Haetaan kantaan huomisen hinnat# Haetaan kantaan huomisen hinnat
    else: # Jos kello on alle 14:30
      pass
  elif(datetime.date.today()+ datetime.timedelta(days = 1) == latest): # kannasta löytyy huomisen hintatiedot
    pass
  else: # kannan hintatiedot ovat kokonaan vanhentuneet 
    if (current_time.hour == 14 and current_time.minute >= 30): # Jos kello on 14.30 - 15.00
      combineShite(date = "today")# Haetaan kantaan tämän päivän huomisen hinnat
      combineShite(date = "tomorrow")# Haetaan kantaan huomisen hinnat
    elif(current_time.hour >= 15): # Jos kello on yli 15
      combineShite(date = "today")# Haetaan kantaan tämän päivän huomisen hinnat
      combineShite(date = "tomorrow")# Haetaan kantaan huomisen hinnat
    else: # Jos kello on alle 14:30
      combineShite(date = "today")# Haetaan kantaan tämän päivän huomisen hinnat


#print('\nWaiting to call the timed function...\n')

# while 1:
#   time = datetime.datetime.now()

#   if (time.strftime("%H") == '14' and time.strftime("%M") == '45'):
#     combineShite()
#     break
