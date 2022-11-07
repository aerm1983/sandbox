# https://chat.googleapis.com/v1/spaces/A---------8/messages?key=AIz----------I&token=FF---------------D
from json import dumps
import sys
from httplib2 import Http
from datetime import datetime


branch=sys.argv[1]
user=sys.argv[2] +" "+ sys.argv[3]
micro=sys.argv[4]
is_release=sys.argv[5]

def main():
    url = 'https://chat.googleapis.com/v1/spaces/A------8/messages?key=A-----------I&token=F-----------D'
    now = datetime.now()
    dt_string = now.strftime("%d/%m/%Y %H:%M:%S")

    bot_message = {
        'text' : 
'''```
{} - Compiling {} - Branch: {} by {} - Release: {}
```'''.format(dt_string,micro,branch,user,is_release)}

    message_headers = {'Content-Type': 'application/json; charset=UTF-8'}

    http_obj = Http()

    response = http_obj.request(
        uri=url,
        method='POST',
        headers=message_headers,
        body=dumps(bot_message),
    )

if __name__ == '__main__':
    main()
