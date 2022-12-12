import datetime
import requests
from . import constant
from defusedxml.ElementTree import fromstring
from ..models import Luokannimi, DayPrices
from django.db import models


# create start and end timeperiods for HTTP GET query
def editTimePeriods(param_time):
  time = param_time
  timeYesterday = param_time - datetime.timedelta(days = 1) # decrement param_time by one day

  # create price data starting time period
  period_start = timeYesterday.strftime("%Y") + timeYesterday.strftime("%m") + timeYesterday.strftime("%d") + '0000'
  # create price data ending time period
  period_end = time.strftime("%Y") + time.strftime("%m") + time.strftime("%d") + '2300'

  return period_start, period_end

# format HTTP GET query URL
def editUrl(token, start_p, end_p):
  formatted_url = 'https://web-api.tp.entsoe.eu/api?securityToken={}\
&documentType=A44&in_Domain=10YFI-1--------U&out_Domain=10YFI-1--------U\
&periodStart={}&periodEnd={}'.format(token, start_p, end_p) # format data from parameters into empty brackets

  return formatted_url

# query the ENTSO-E API with HTTP GET method
def fetchPriceData(url):
  datapoints = [] # temporary array for the data processing
  ns = {"wtf": "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0"} # namespace required for XML parsing
  x = requests.get(url) # make the HTTP GET request to the ENTSO-E API

  if (x.status_code == 200): # if response is OK
    ET = fromstring(x.text) # response as text for the ElementTree

    for child in ET.findall(".//wtf:Point", ns): # dig up all values with key 'Point'
      if child.tag == '{urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0}timeInterval': # found the right child in the XML
        continue
      if len(child) == 2: # append pricepoints to temporary array
        datapoints.append({'hour': child[0].text, 'price': child[1].text})
  else: # error "handling"
    print("An error occured while attempting to call the API")

  return datapoints

# save price data
def addNewEntry(datapoints,date):
  dp = [] # temporary array for the data processing
  for item in datapoints: # loop through price data
    temp = float(item['price']) # cast to float

    # MWh -> kWh and € -> c, + alv (10%)
    temp = round(temp / 10 * constant.ALV_MULTIPLIER, 2)
    dp.append(temp) # append data to the temporary array in wanted shape

  # delete unwanted pricepoints (because of daylight savings)
  for _ in range(23):
    dp.pop(0) # pop out 23 first ones...

  dp.pop(23) # ...and the last one

  # save the correct day prices to DayPrice object
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

# function that handles the calling of other functions
def combineShite(date):
  if (date == "tomorrow"): # tomorrow's datetime for the search
    datetime_search = datetime.datetime.now()+ datetime.timedelta(days = 1)
  elif (date == "yesterday"): # yesterday's datetime for the search
      datetime_search = datetime.datetime.now()- datetime.timedelta(days = 1)
  else: # today's datetime for the search
    datetime_search = datetime.datetime.now()

  # get start and end periods from function
  start_period, end_period = editTimePeriods(datetime_search)
  print("Start period: " + start_period)
  print("End period: " + end_period + "\n")

  # get URL address from function
  request_url = editUrl(constant.TOKEN, start_period, end_period)
  print("Formatted URL: " + request_url + "\n")

  # get price data from function
  datapoints = fetchPriceData(request_url)

  # check if data was fetched successfully
  if (len(datapoints) > 1): # no problem
    addNewEntry(datapoints,datetime_search)
  else: # yes problem
    print("No datapoints to print/save\n")

# return datapoints
def updateHandler():
  try:
    latest = DayPrices.objects.latest('id')
    latest = latest.Date
  except:
    latest = False

  current_time = datetime.datetime.now() + datetime.timedelta(hours=2) # take current datetime

  if (datetime.date.today() == latest): # if today's data is already in the database
    if (current_time.hour == 14 and current_time.minute >= 30): # if time is 14:30 - 14:59
      combineShite(date = "tomorrow")# fetch tomorrow's price data to the database
    elif(current_time.hour >= 15): # if time is 15:00 or more
      combineShite(date = "tomorrow") # fetch tomorrow's price data to the database
    else: # if time is less than 14:30
      pass # skip
  elif(datetime.date.today() + datetime.timedelta(days = 1) == latest): # tomorrow's data already in database
    pass # skip
  else: # data in database is out of date
    if (current_time.hour == 14 and current_time.minute >= 30): # if time is 14:30 - 14:59
      combineShite(date = "today") # fetch today's price data
      combineShite(date = "tomorrow") # fetch tomorrow's price data
    elif(current_time.hour >= 15): # if time is 15:00 or more
      combineShite(date = "today") # fetch today's price data
      combineShite(date = "tomorrow") # fetch tomorrow's price data
    else: # if time is less than 14:30
      combineShite(date = "today") # fetch today's price data
