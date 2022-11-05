
import smtplib, os
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.base import MIMEBase
from email.encoders import encode_base64
import logging
import logging.config
import configparser
#import argparse

conf = configparser.ConfigParser()
conf.read('./conf.email.oaux.ini')

#Información del vendedor
user= conf['mail']['user']
password= conf['mail']['password']

#Servidor SMTP
smtp_server= conf['mail']['smtp_server']

#contenido del correo electrónico:
text='''
Cuadre contable cuenta por cuenta EUR
'''
file_email_content = open('email-content');


#Para las cabeceras del email
remitente = conf['mail']['remitente']
to = conf['mail']['to'].split(',')
asunto = conf['mail']['asunto']
mensaje = text
# archivo = conf['mail']['archivo']+'20210817'+'.xlsx'
archivo = conf['mail']['archivo']

#Host y puerto SMTP de Gmail
gmail = smtplib.SMTP(smtp_server, 587)

#protocolo de cifrado de datos utilizado por gmail
gmail.starttls()

#Credenciales
gmail.login(user, password)

#muestra la depuración de la operacion de envío 1=true
gmail.set_debuglevel(1)

header = MIMEMultipart()
header['Subject'] = asunto
header['From'] = remitente
header['To'] = to[0]

mensaje = MIMEText(mensaje, 'html') #Content-type:text/html
header.attach(mensaje)

if (os.path.isfile(archivo)):
 adjunto = MIMEBase('application', 'octet-stream')
 adjunto.set_payload(open(archivo, "rb").read())
 encode_base64(adjunto)
 adjunto.add_header('Content-Disposition', 'attachment; filename="%s"' % os.path.basename(archivo))
 header.attach(adjunto)

#Enviar email
try:
    gmail.sendmail(remitente, to, header.as_string())
    print('Felicitaciones, enviado con éxito')
except:
    print('Error de envío, inténtelo de nuevo. ')
#Cerrar la conexión SMTP
gmail.quit()
