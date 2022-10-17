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

    conf_profile = configparser.ConfigParser()
    conf_profile.read('./conf.profile.ini')

    conf_email = configparser.ConfigParser()
    conf_email.read(conf_profile['profile']['conf_file'])

    # login info
    user = conf_email['mail']['user']
    password = ''
    try:
        # print( str(os.environ) ) # debug
        print('Attempting to read password from environment variable "' + conf_email['mail']['password_env_var'] + '"')
        password = os.getenv(conf_email['mail']['password_env_var'])
        if password == '' or password == None : raise Exception()
    except:
        print('ERROR: Email password should preferrable be set in environment variable.')
        print('ERROR: Attempting to read password from config file (not recommended default execution)')
        sleep(2) # 2 seconds
        try:
            password = conf_email['mail']['password']
            if password == '' or password == None : raise Exception()
        except:
            print('ERROR: Email password is not defined in environment variable nor in config file.')
            print('ERROR: exit')
            sys.exit(1)

    # smtp server
    smtp_server = conf_email['mail']['smtp_server']

    # email content (choose string or file source)
    content_text_type = conf_email['mail']['content_text_type'] # 'plain' | 'html'
    text = '''
    Simple email test 00 content from string, better overriden.
    '''
    file_email_content = open( conf_email['mail']['content_file'] )
    text = file_email_content.read()


    # email headers
    _from = conf_email['mail']['from']
    toList = conf_email['mail']['to'].split(',')
    
    subject = ''
    try:
        print('Attempting to read subject from file "' + conf_email['mail']['subject_file'] + '"')
        file_email_subject = open( conf_email['mail']['subject_file'] )
        subject = file_email_subject.read()
        if subject == '' or subject == None : raise Exception()
    except:
        print('ERROR: Read subject from file failed')
        print('ERROR: Attempting to read subject from config file')
        try:
            subject = conf_email['mail']['subject']
            if subject == '' or subject == None : raise Exception()
        except:
            print('Subject not defined in txt file, nor in config file')
            sys.exit(1)

    content = text






    # attach_file = conf_email['mail']['archivo']+'20210817'+'.xlsx'
    # attach_file_list = conf_email['mail']['attach_file_list'].split(',') # original
    attach_file_list = None
    try:
        print('Attempting to read attach_file_list from file "' + conf_email['mail']['attach_file_list_file'] + '"')
        file_email_atach_file_list = open( conf_email['mail']['attach_file_list_file'] )
        attach_file_list = str( file_email_atach_file_list.read() ).split(',')
        if attach_file_list == None or ( len(attach_file_list) == 1 and len(attach_file_list[0]) == 0 ) : raise Exception()
    except:
        print('ERROR: Read attach_file_list from file failed') # WARN?
        print('ERROR: Attempting to read attach_file_list from config file') # WARN?
        try:
            attach_file_list = conf_email['mail']['attach_file_list'].split(',')
            if attach_file_list == None or ( len(attach_file_list) == 1 and len(attach_file_list[0]) == 0 ) : # raise Exception()
                # attach_file_list = None # fails
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
