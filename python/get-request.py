
import datetime
import requests
from defusedxml.ElementTree import fromstring

def editRequest(**dateArgs):
  year = str(dateArgs['year'])
  month = dateArgs['month']
  day = dateArgs['day']
  global period_start
  global period_end

  if (month == 10 and day == 31):
    month = '11'
    day = '01'

  period_start = year + month + day + '0000'
  period_end = year + month + day + '2300'
  print("periodStart: " + period_start + "\nperiodEnd: " + period_end + '\n')

TOKEN = '8523bda7-9f7c-4b73-8bea-d83f4fdb9159'
period_start = '201701010000'
period_end = '201701012300'

requestUrl = 'https://web-api.tp.entsoe.eu/api?securityToken={}\
&documentType=A44&in_Domain=10YFI-1--------U&out_Domain=10YFI-1--------U\
&periodStart={}&periodEnd={}'.format(TOKEN, period_start, period_end)

datapoints = []
ns = {"wtf": "urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0"}

print("\nperiodStart: " + period_start + "\nperiodEnd: " + period_end + '\n')

x = requests.get(requestUrl)

if (x.status_code == 200):
  ET = fromstring(x.text)

  for child in ET.findall(".//wtf:Point", ns):
    if child.tag == '{urn:iec62325.351:tc57wg16:451-3:publicationdocument:7:0}timeInterval':
      continue
    if len(child) == 2:
      datapoints.append({'hour': child[0].text, 'price': child[1].text})
else:
  print("An error occured while attempting to call the API")

if (len(datapoints) > 1):
  print(datapoints)

print('\nWaiting to call the timed function...\n')

while 1:
  time = datetime.datetime.now()

  if (time.strftime("%H") == '15' and time.strftime("%M") == '49'):
    editRequest(year = time.year, month = time.month, day = time.day)
    break