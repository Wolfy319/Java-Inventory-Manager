import discord
import os
import smtplib
from replit import db

client = discord.Client()

def add_order(order_id,email):
  order_id = str(order_id)
  if order_id in db.keys():
    return
  else:
    db[order_id] = email
    
def delete_order(order_id):
  order_id = str(order_id)
  if order_id in db.keys():
    del db[order_id]


def send_confirmation(user, password, recipient, subject, body):

    gmail_user = user
    gmail_pwd = password
    FROM = user
    TO = recipient if type(recipient) is list else [recipient]
    SUBJECT = subject
    TEXT = body

    # Prepare actual message
    message = """From: %s\nTo: %s\nSubject: %s\n\n%s
    """ % (FROM, ", ".join(TO), SUBJECT, TEXT)

    server = smtplib.SMTP("smtp.gmail.com", 587)
    server.ehlo()
    server.starttls()
    server.login(gmail_user, gmail_pwd)
    server.sendmail(FROM, TO, message)
    server.close()


@client.event
async def on_ready():
    print('We have logged in as {0.user}'.format(client))


@client.event
async def on_message(message):
    if message.author == client.user:
        return

    if message.content.startswith('-hello'):
        await message.channel.send('Hello!')

    if message.content.startswith('-orders'):
      await message.channel.send('order list')

    if message.content.startswith('-addO'):
      user_input = message.content.split()
      add_order(user_input[1],user_input[2])
      await message.channel.send(user_input[0] + " User added with email " + user_input[2])

    if message.content.startswith('-Add'):
      user_input = message.content.split()
      tempEmail = user_input[1]
      orderNumber = user_input[2]
      send_confirmation("testemail19872614@gmail.com" , "Abc123614!" , tempEmail , "Confirmation" , "Hello, " + tempEmail + ", Order Number: " + orderNumber + " order confirmed.")
      print ("Email Sent")

    if message.content.startswith('-delt'):
      user_input = message.content.split()
      temp = user_input[1]
      delete_order(user_input[1])
      await message.channel.send("User " + temp + " deleted.")
    
    await message.channel.send(len(db))






client.run(os.getenv('TOKEN'))