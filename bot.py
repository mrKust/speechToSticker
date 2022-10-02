# bot = telebot.TeleBot('5667635481:AAFXsE4kGoPFmniOXGk5_3ldA6mxs1rO1PA')
import telebot
import uuid
import os
import speech_recognition as sr
import json
from pyffmpeg import FFmpeg
import translate
from transformers import RobertaTokenizerFast, TFRobertaForSequenceClassification, pipeline
import requests

language = 'ru_RU'
TOKEN = '5667635481:AAFXsE4kGoPFmniOXGk5_3ldA6mxs1rO1PA'
bot = telebot.TeleBot(TOKEN)
r = sr.Recognizer()
tokenizer = RobertaTokenizerFast.from_pretrained('arpanghoshal/EmoRoBERTa')
model = TFRobertaForSequenceClassification.from_pretrained('arpanghoshal/EmoRoBERTa')

emotion = pipeline('sentiment-analysis', model=model, tokenizer=tokenizer)

def recognise(filename):
    with sr.AudioFile(filename) as source:
        audio_text = r.listen(source)
        try:
            text = r.recognize_google(audio_text, language=language)
            print('Converting audio transcripts into text ...')
            print(text)
            return text
        except:
            print('Sorry.. run again...')
            return 'Sorry.. run again...'

@bot.message_handler(content_types=['voice'])
def voice_processing(message):
    filename = str(uuid.uuid4())
    file_name_full = './voice/'+filename+'.ogg'
    file_name_full_converted = './ready/'+filename+'.wav'
    file_info = bot.get_file(message.voice.file_id)
    downloaded_file = bot.download_file(file_info.file_path)
    with open(file_name_full, 'wb') as new_file:
        new_file.write(downloaded_file)
    # os.system('ffmpeg -i '+file_name_full+'  '+file_name_full_converted)
    inp = file_name_full
    out = file_name_full_converted
    ff = FFmpeg()
    ff.convert(inp, out)
    text = recognise(out)
    new_message = translate.transl(text, emotion)
    if new_message == 'admiration' or new_message == 'amusement' or new_message == 'admiration' or new_message == 'approval' or new_message == 'caring' or new_message =='desire' or new_message =='excitement' or new_message =='gratitude' or new_message =='joy' or new_message =='love' or new_message =='optimism' or new_message =='pride' or new_message =='relief':
        new_message = 'positive'
    if new_message == 'anger' or new_message == 'annoyance' or new_message == 'disappointment' or new_message == 'disapproval' or new_message == 'disgust' or new_message =='embarrassment' or new_message =='fear' or new_message =='grief' or new_message =='nervousness' or new_message =='remorse' or new_message =='sadness':
        new_message = 'negative'
    if new_message == 'confusion' or new_message == 'curiosity' or new_message == 'realization' or new_message == 'surprise' or new_message == 'neutral':
        new_message = 'neutral'
    print(new_message)
    body = json.dumps({'paramOne': new_message, 'paramTwo': '3, 4'})
    headers = {'content-type': 'application/json'}
    responce = requests.post('http://localhost:8080/api/getReaction', data=body, headers=headers)
    # print(responce.request.body)
    #responce = requests.get('http://localhost:8080/tags/all')
    print(responce.text)
    # bot.reply_to(message, new_message)
    namefile = responce.text.split(',')[1].split(':')[1][1:-2]
    print(namefile)
    type =responce.text
    print(type)
    bot.reply_to(message, new_message)
    bot.reply_to(message, namefile)
    # bot.reply_to(message, new_message)
    # os.remove(file_name_full)
    # os.remove(file_name_full_converted)


@bot.message_handler(content_types=['text'])
def text_processing(message):
    message_from_user = json.dumps({'Text': message.text}, indent=4, ensure_ascii=False).encode('UTF8')
    # chatid = message.chatId
    # print(chatid)
    print(message.text)
    print(message_from_user.decode())
    new_message = translate.transl(message.text, emotion)
    if new_message == 'admiration' or new_message == 'amusement' or new_message == 'admiration' or new_message == 'approval' or new_message == 'caring' or new_message =='desire' or new_message =='excitement' or new_message =='gratitude' or new_message =='joy' or new_message =='love' or new_message =='optimism' or new_message =='pride' or new_message =='relief':
        new_message = 'positive'
    if new_message == 'anger' or new_message == 'annoyance' or new_message == 'disappointment' or new_message == 'disapproval' or new_message == 'disgust' or new_message =='embarrassment' or new_message =='fear' or new_message =='grief' or new_message =='nervousness' or new_message =='remorse' or new_message =='sadness':
        new_message = 'negative'
    if new_message == 'confusion' or new_message == 'curiosity' or new_message == 'realization' or new_message == 'surprise' or new_message == 'neutral':
        new_message = 'neutral'
    print(new_message)
    body = json.dumps({'paramOne': new_message, 'paramTwo': '2, 3'})
    headers = {'content-type': 'application/json'}
    responce = requests.post('http://localhost:8080/api/getReaction', data=body, headers=headers)
    # print(responce.request.body)
    #responce = requests.get('http://localhost:8080/tags/all')
    print(responce.text)
    bot.reply_to(message, new_message)
    namefile = responce.text.split(',')[1].split(':')[1][1:-2]
    print(namefile)
    type =responce.text
    print(type)
    bot.reply_to(message, namefile)

    # if type.find("picture") != -1:
    #     bot.send_photo(chat_id=chatid, photo=namefile)
    # elif namefile.find("gif")!=-1:
    #     bot.send_animation(chat_id=chatid,animation=namefile)
    # elif type ==

bot.polling()
