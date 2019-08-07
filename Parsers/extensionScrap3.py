from bs4 import BeautifulSoup
import requests
import pandas as pd
import csv

#For getting list of Developer files
# temp = []
name = []
# char = '1abcdefghijklmnopqrstuvwxyz'
# for i in char:
#     url = 'https://www.reviversoft.com/file-extensions/?char=' + i
#     response = requests.get(url, timeout=5)
#     content = BeautifulSoup(response.content, "html.parser")
#     for x in content.findAll('li', attrs={"class":"dll_li"}):
#         temp = x.select('a')
#         for y in temp:
#             name.append(y.get_text())
# # Saving extensionlist in pandas packet    
# pack = pd.DataFrame({
#         "Extension":name,               
#         })
   
# # For exporting saved exteionlist to csv file
# pack.to_csv('ExtensionList3.csv')
with open("ExtensionList3.csv", "r") as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    for lines in csv_reader:
      name.append(lines[1])
info = []
apps = []
developer = []
fileType = []
# Function to get various details about the fileextension

def details(a):
    print(a)
    subapp = []
    url = 'https://www.reviversoft.com/file-extensions/' + a
    response = requests.get(url, timeout=5)
    content = BeautifulSoup(response.content, "html.parser")

    leftBox = content.find('div', attrs={"class":"left_side"})
    infoclass = leftBox.select('div:nth-of-type(2)')
    about = [x.get_text() for x in infoclass]
    for y in range(0,len(about)):
        about[y] = about[y].strip()
    info.append(about[0])
    print(about[0])

    for app in content.findAll('div', attrs={"class":"prog_title"}):
        subapp.append((app.text).strip())
    apps.append(subapp)
    print(subapp)
    
    xtra = content.find('div', attrs={"class":"ext_desc"})
    dev = xtra.select('div')
    file = xtra.select('div ~ div')
    develop = [x.get_text() for x in dev]
    for y in range(0,len(develop)):
        develop[y] = develop[y].strip()
    developer.append(develop[0][32:])

    fileT = [x.get_text() for x in file]
    for y in range(0,len(fileT)):
        fileT[y] = fileT[y].strip()
    fileType.append(fileT[0][32:])
# Reading input from extensionlist.csv file and calling details function to get info        
# with open('ExtensionList3.csv','rt') as f:
#   data = list(csv.reader(f))
for row in name:
    details(row[1][1:])

# For packing all details in one packet
packed = pd.DataFrame({
        "1 Extension":name
        })
packed2 = pd.DataFrame({
        "2 FileType":fileType
        })
packed3 = pd.DataFrame({
        "4 Description":info
        })
packed4 = pd.DataFrame({
        "5 Programs":apps
        })
packed5 = pd.DataFrame({
        "3 Developer":developer})
#print(packed)
#print(packed2)
#print(packed3)
#print(packed4)    
# For exporting all the information in one csv file
#packed.to_csv('ExtensionData3-1.csv')
#packed2.to_csv('ExtensionData3-2.csv')
#packed3.to_csv('ExtensionData3-3.csv')
#packed4.to_csv('ExtensionData3-4.csv')
#packed5.to_csv('ExtensionData3-5.csv')