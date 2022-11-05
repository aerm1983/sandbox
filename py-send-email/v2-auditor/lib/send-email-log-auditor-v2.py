import sys
import os
import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.base import MIMEBase
from email.encoders import encode_base64
from time import sleep
import logging
import logging.config
import configparser
# import argparse

def main(argv):


    print('argv: ' + str(argv))


    # config file(s)
    conf_smtp_login = configparser.ConfigParser()
    conf_smtp_login.read('../tmp' + '/' + argv[1] + '/' + 'conf.smtp.login.ini')
    conf_email = configparser.ConfigParser()
    conf_email.read('../tmp' + '/' + argv[1] + '/' + 'conf.email.ini')


    # login info
    user = conf_smtp_login['login']['user']
    password = ''
    try:
        # print( str(os.environ) ) # debug
        print('Attempting to read password from environment variable "' + conf_smtp_login['login']['password_env_var'] + '"')
        password = os.getenv(conf_smtp_login['smtp']['login']['password_env_var'])
        if password == '' or password == None : raise Exception()
    except:
        print('WARN: Email password should preferrable be set in environment variable.')
        print('WARN: Attempting to read password from config file (not recommended default execution)')
        sleep(2) # 2 seconds
        try:
            password = conf_smtp_login['login']['password']
            if password == '' or password == None : raise Exception()
        except:
            print('ERROR: Email password is not defined in environment variable nor in config file.')
            print('ERROR: exit')
            sys.exit(1)


    # smtp server
    smtp_server = conf_smtp_login['smtp']['smtp_server']


    # email content (choose string or file source)
    content_text_type = conf_email['email']['content_text_type'] # 'plain' | 'html'
    text = '''
    Simple email test 00 content from string, better overriden.
    '''
    file_email_content = open( '../tmp/' + argv[1] + '/' + conf_email['email']['content_file'] )
    text = file_email_content.read()


    # email headers
    _from = conf_email['email']['from']
    toList = conf_email['email']['to'].split(',')
    
    subject = ''
    print('Attempting to read {subject} from config file')
    try:
        subject = conf_email['email']['subject']
        if subject == '' or subject == None : raise Exception()
    except:
        print('ERROR: Subject not defined in config file')
        sys.exit(1)

    content = text


    # attachments
    # attach_file = conf_email['email']['archivo']+'20210817'+'.xlsx'
    # attach_file_list = conf_email['email']['attach_file_list'].split(',') # original

    attach_file_list = None
    print('Attempting to read {attach_file_list} from config file')
    try:
        attach_file_list = conf_email['email']['attach_file_list'].split(',')
        if attach_file_list == None or ( len(attach_file_list) == 1 and len(attach_file_list[0]) == 0 ) : # raise Exception()
            # attach_file_list = None # exception, fail
            pass
    except:
        print('This exception should never happen')
        sys.exit(1)
    print('email attach file list:' + str(attach_file_list))


    # email server host, port
    gmail = smtplib.SMTP(smtp_server, 587)


    # email cipher protocol
    gmail.starttls()


    # login
    gmail.login(user, password)


    # enable debugging, 1
    gmail.set_debuglevel(1)

    # email params
    header = MIMEMultipart()
    header['Subject'] = subject
    header['From'] = _from
    header['To'] = ", ".join(toList) # to[0]


    # email content
    content = MIMEText(content, content_text_type) # content-type:text/plain | content-type:text/html
    # content = MIMEText(content, 'html') # 
    header.attach(content)


    # attachments
    for f in attach_file_list:
        adjunto = None
        f = '../tmp/' + argv[1] + '/' + f
        if (os.path.isfile(f)):
            adjunto = MIMEBase('application', 'octet-stream')
            adjunto.set_payload(open(f, "rb").read())
            encode_base64(adjunto)
            adjunto.add_header('Content-Disposition', 'attachment; filename="%s"' % os.path.basename(f))
            header.attach(adjunto)


    # send email
    try:
        gmail.sendmail(_from, toList, header.as_string())
        print('Email succesfully sent')
    except:
        print('Error trying to send email')
    

    # close smtp connection
    gmail.quit()



if __name__ == '__main__' :
    main( sys.argv ) 
