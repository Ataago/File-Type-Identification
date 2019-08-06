from bs4 import BeautifulSoup
import requests
import pandas as pd
import csv
#For getting list of Developer files
url = 'https://fileinfo.com/filetypes/developer'
response = requests.get(url, timeout=5)
content = BeautifulSoup(response.content, "html.parser")
temp = content.select('tr td a')
name = [x.get_text() for x in temp]
temp2 = content.select('tr td ~ td')
desc = []
cat = []
i = 0
for x in temp2:
    if (i%2==0):
        desc.append(x.get_text())
        cat.append('Developer File')
    i+=1
    
#For getting list of Database files
url = 'https://fileinfo.com/filetypes/database'
response = requests.get(url, timeout=5)
content = BeautifulSoup(response.content, "html.parser")
temp3 = content.select('tr td a')
for x in temp3:
    name.append(x.get_text())
temp4 = content.select('tr td ~ td')
i = 0
for x in temp4:
    if (i%2==0):
        desc.append(x.get_text())
        cat.append('Database File')
    i+=1


#For getting list of Text files
url = 'https://fileinfo.com/filetypes/text'
response = requests.get(url, timeout=5)
content = BeautifulSoup(response.content, "html.parser")
temp3 = content.select('tr td a')
for x in temp3:
    name.append(x.get_text())
temp4 = content.select('tr td ~ td')
i = 0
for x in temp4:
    if (i%2==0):
        desc.append(x.get_text())
        cat.append('Text File')
    i+=1

#For getting list of Executable files
url = 'https://fileinfo.com/filetypes/executable'
response = requests.get(url, timeout=5)
content = BeautifulSoup(response.content, "html.parser")
temp3 = content.select('tr td a')
for x in temp3:
    name.append(x.get_text())
temp4 = content.select('tr td ~ td')
i = 0
for x in temp4:
    if (i%2==0):
        desc.append(x.get_text())
        cat.append('Executable File')
    i+=1

#saving extensionlist in pandas packet    
pack = pd.DataFrame({
        "Extension":name,               
        })
    
#For exporting saved exteionlist to csv file
pack.to_csv('ExtensionList.csv')

about = []
apps = []

#Function to get various details about the fileextension
def details(a):
    subapp = []
    url = 'https://fileinfo.com/extension/'+a
    response = requests.get(url, timeout=5)
    content = BeautifulSoup(response.content, "html.parser")
    infoclass = content.find('span', attrs={"itemprop":"description"}).text
    about.append(infoclass)
    for app in content.findAll('table', attrs={"class":"apps"}):
        roughList = list((app.text).split('\n'))
        subapp.append(roughList[1:len(roughList)-1])
    apps.append(subapp)
    
#reading input from extensionlist.csv file and calling details function to get info        
with open('ExtensionList.csv','rt')as f:
  data = list(csv.reader(f))
  for row in range(1,len(data)):
        details(data[row][1][1:])

#for packing all details in one packet
packed = pd.DataFrame({
        "1 Extension":name,
        "2 Category":cat,
        "3 Type":desc,
        "4 Description":about,
        "5 Programs":apps
        })

#for exporting all the information in one csv file
packed.to_csv('ExtensionData.csv')