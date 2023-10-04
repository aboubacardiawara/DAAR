import requests
i=2000
j=50
status = set()
while  i <2100:
    url = 'https://www.gutenberg.org/files/'+str(i)+'/'+str(i)+'-8.txt'
    response = requests.get(url)
    if response.status_code!=404: 
        local_filename = 'test_files/testfile'+str(j)+'.txt'
        with open(local_filename, 'wb') as file:
            file.write(response.content)
        j=j+1
    else:
         url = 'https://www.gutenberg.org/files/'+str(i)+'/'+str(i)+'-0.txt'
         response = requests.get(url)
         if response.status_code!=404: 
             local_filename = 'test_files/testfile'+str(j)+'.txt'
             with open(local_filename, 'wb') as file:
                file.write(response.content)
             j=j+1
    i=i+1
             

