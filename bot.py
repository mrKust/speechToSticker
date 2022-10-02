# bot = telebot.TeleBot('5667635481:AAFXsE4kGoPFmniOXGk5_3ldA6mxs1rO1PA')
import telebot
import uuid
import os
import speech_recognition as sr
import json
from pyffmpeg import FFmpeg
import translate
from transformers import RobertaTokenizerFast, TFRobertaForSequenceClassification, pipeline

language = 'ru_RU'
TOKEN = '5667635481:AAFXsE4kGoPFmniOXGk5_3ldA6mxs1rO1PA'
bot = telebot.TeleBot(TOKEN)
r = sr.Recognizer()
tokenizer = RobertaTokenizerFast.from_pretrained("arpanghoshal/EmoRoBERTa")
model = TFRobertaForSequenceClassification.from_pretrained("arpanghoshal/EmoRoBERTa")

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
            return "Sorry.. run again..."

@bot.message_handler(content_types=['voice'])
def voice_processing(message):
    filename = str(uuid.uuid4())
    file_name_full = "./voice/"+filename+".ogg"
    file_name_full_converted = "./ready/"+filename+".wav"
    file_info = bot.get_file(message.voice.file_id)
    downloaded_file = bot.download_file(file_info.file_path)
    with open(file_name_full, 'wb') as new_file:
        new_file.write(downloaded_file)
    # os.system("ffmpeg -i "+file_name_full+"  "+file_name_full_converted)
    inp = file_name_full
    out = file_name_full_converted
    ff = FFmpeg()
    ff.convert(inp, out)
    text = recognise(out)
    new_message = translate.transl(text, emotion)
    bot.reply_to(message, new_message)
    # os.remove(file_name_full)
    # os.remove(file_name_full_converted)


@bot.message_handler(content_types=['text'])
def text_processing(message):
    message_from_user = json.dumps({'Text': message.text}, indent=4, ensure_ascii=False).encode('UTF8')
    print(message.text)
    print(message_from_user.decode())
    new_message = translate.transl(message.text, emotion)
    bot.reply_to(message, new_message)


bot.polling()
