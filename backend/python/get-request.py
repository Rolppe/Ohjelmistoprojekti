import datetime
import requests
import constant
from defusedxml.ElementTree import fromstring

def editTimePeriods(time_now):
  time = time_now + datetime.timedelta(days = 1)
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

def combineShite():
  current_datetime = datetime.datetime.now()
  start_period, end_period = editTimePeriods(current_datetime)
  print("Start period: " + start_period)
  print("End period: " + end_period + "\n")

  request_url = editUrl(constant.TOKEN, start_period, end_period)
  print("Formatted URL: " + request_url + "\n")

  datapoints = fetchPriceData(request_url)
  if (len(datapoints) > 1):
    print(datapoints)
  else:
    print("No datapoints to print\n")

print('\nWaiting to call the timed function...\n')

while 1:
  time = datetime.datetime.now()

  if (time.strftime("%H") == '12' and time.strftime("%M") == '28'):
    combineShite()
    break
