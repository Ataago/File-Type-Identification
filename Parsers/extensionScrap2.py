from bs4 import BeautifulSoup
import requests
import pandas as pd
import csv
#For getting list of Developer files
url = 'https://www.webopedia.com/quick_ref/fileextensionsfull.asp'
response = requests.get(url, timeout=5)
content = BeautifulSoup(response.content, "html.parser")
table = content.find('table', attrs={"class":"style5"})
name = []
desc = []
temp = []
temp2 = []
for rows in table.findAll('tr'):
    temp = rows.select('td:nth-of-type(1)')
    temp2 = rows.select('td:nth-of-type(2)')
    name.append(list([x.get_text() for x in temp])[0])
    desc.append(list([x.get_text() for x in temp2]))
#print(name)

# temp = content.select('tr td')
# name = [x.get_text() for x in temp]
# temp2 = content.select('tr td ~ td')
# i = 0
# for x in temp2:
#     if (i%2==0):
#         desc.append(x.get_text())
#         cat.append('Developer File')
#     i+=1
    
# #saving extensionlist in pandas packet    
pack = pd.DataFrame({
         "dxtension":name,
         "eescription": desc
         })
print(pack)
# #For exporting saved exteionlist to csv file
pack.to_csv('ExtensionData2.csv')
