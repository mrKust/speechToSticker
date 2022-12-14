import sys

import telebot
import traceback
import uuid
import os
import speech_recognition as sr
import json
from pyffmpeg import FFmpeg
from telebot import types
from googletrans import Translator
from transformers import RobertaTokenizerFast, TFRobertaForSequenceClassification, pipeline
import requests
import yadisk

params = []
params_str = '0, 1, 2, 3, 4, 5'
language = 'ru_RU'
TOKEN = os.environ["TELEGRAM_BOT_TOKEN"]
bot = telebot.TeleBot(TOKEN)
r = sr.Recognizer()
tokenizer = RobertaTokenizerFast.from_pretrained('arpanghoshal/EmoRoBERTa')
model = TFRobertaForSequenceClassification.from_pretrained('arpanghoshal/EmoRoBERTa')
y = yadisk.YaDisk(token=os.environ["YANDEX_DISK_TOKEN"])
all_files_on_disk = list(y.listdir("/cdn"))
emotion = pipeline('sentiment-analysis', model=model, tokenizer=tokenizer)
errorMessage = "Что-то внутри меня сломалось. Так что прости, в этот раз без ответа\nНадо логи чекать"
rebootMessage = "Да задушил ты меня. Так что я в ребут, бывай ихтиандр"


def transl(original_text, emotion):
    translator = Translator()
    translation = translator.translate(text=original_text, dest='en', src='ru')
#     print(translation.text)

    emotion_labels = emotion(translation.text)
    print(emotion_labels)
    return emotion_labels[0]['label'], translation.text

def recognise(filename):
    with sr.AudioFile(filename) as source:
        audio_text = r.listen(source)
        try:
            text = r.recognize_google(audio_text, language=language)
            print('Converting audio transcripts into text ...')
#             print(text)
            return text
        except:
            print('Sorry.. run again...')
            return 'Sorry.. run again...'


@bot.message_handler(content_types=['voice'])
def voice_processing(message):
    chatid = message.chat.id
    filename = str(uuid.uuid4())
    file_name_full = './opt/voice/' + filename + '.ogg'
    file_name_full_converted = 'opt/ready/' + filename + '.wav'
    file_name_full_converted_for_create = './' + file_name_full_converted
    file_info = bot.get_file(message.voice.file_id)
    downloaded_file = bot.download_file(file_info.file_path)
    with open(file_name_full, 'wb') as new_file:
        new_file.write(downloaded_file)
    with open(file_name_full_converted_for_create, 'w') as document: pass
    try:
        ff = FFmpeg()
        ff.convert(file_name_full, file_name_full_converted)
        text = recognise(file_name_full_converted)

        original_emotion, translated_text = transl(text, emotion)
        bot.reply_to(message, text=text+'. Перевод: '+translated_text)
#         print(original_emotion)
        bot.reply_to(message, original_emotion)
        new_message = original_emotion[:]
        if new_message == 'admiration' or new_message == 'amusement' or new_message == 'admiration' or \
                new_message == 'approval' or new_message == 'caring' or new_message == 'desire' or \
                new_message == 'excitement' or new_message == 'gratitude' or new_message == 'joy' or \
                new_message == 'love' or new_message == 'optimism' or new_message == 'pride' or new_message == 'relief':
            new_message = 'positive'
        if new_message == 'anger' or new_message == 'annoyance' or new_message == 'disappointment' or \
                new_message == 'disapproval' or new_message == 'disgust' or new_message == 'embarrassment' or \
                new_message == 'fear' or new_message == 'grief' or new_message == 'nervousness' or new_message == 'remorse'\
                or new_message == 'sadness':
            new_message = 'negative'
        if new_message == 'confusion' or new_message == 'curiosity' or new_message == 'realization' or \
                new_message == 'surprise' or new_message == 'neutral':
            new_message = 'neutral'
#         print(new_message)

        body = json.dumps({'paramOne': new_message, 'paramTwo': params_str})
        headers = {'content-type': 'application/json'}
        responce = requests.post('http://app:8080/api/getReaction', data=body, headers=headers)
        print(responce.text)
    except:
        print("Something go wrong in block of answers for voice message")
        traceback.print_exc()
        bot.reply_to(message, errorMessage)
    finally:
        os.remove(file_name_full)
        os.remove(file_name_full_converted)

    try:
        file_route = responce.text[:]
        params_json = file_route.split(':')
        namefile = ""
        if params_json[1].find("poety") != -1 or params_json[1].find("quote") != -1:
            namefile = str(params_json[2:])[2:-3]
            namefile = namefile.replace('\\n', '\n')
            namefile = namefile.replace("\\", '')
        else:
            namefile = file_route.split(',')[1].split(':')[1][1:-2]

        type = responce.text
        if type.find("poety") != -1:
            bot.reply_to(message, namefile)
        elif type.find("quote") != -1:
            bot.reply_to(message, namefile)
        else:
            fileurl = ''
            for i in range(len(all_files_on_disk)):
                if all_files_on_disk[i].FIELDS['name'] == namefile:
                    fileurl = all_files_on_disk[i].FIELDS['file']
                    break

            if type.find("picture") != -1:
                bot.send_photo(chat_id=chatid, photo=fileurl)
            elif type.find("gif") != -1:
                bot.send_animation(chat_id=chatid, animation=fileurl)
            elif type.find("mp4") != -1:
                bot.send_video(chat_id=chatid, video=fileurl)
            elif type.find("mp3") != -1:
                bot.send_audio(chat_id=chatid, audio=fileurl)
    except:
        print("Something go wrong in sending media for voice message")
        traceback.print_exc()
        bot.reply_to(message, rebootMessageMessage)
        sys.exit(5)


@bot.callback_query_handler(func=lambda call: True)
def callback_worker(call):
    global params
    global params_str
    if call.data == "0":
        if params.__contains__(0):
            bot.answer_callback_query(call.id, text="Гифки убраны")
            params.remove(0)
        else:
            bot.answer_callback_query(call.id, text="Выбраны гифки")
            params.append(0)
            params = list(set(params))
    if call.data == "1":
        if params.__contains__(1):
            bot.answer_callback_query(call.id, text="Картинки убраны")
            params.remove(1)
        else:
            bot.answer_callback_query(call.id, text="Выбраны картинки")
            params.append(1)
            params = list(set(params))
    if call.data == '2':
        if params.__contains__(2):
            bot.answer_callback_query(call.id, text="Стихи убраны")
            params.remove(2)
        else:
            bot.answer_callback_query(call.id, text="Выбраны стихи")
            params.append(2)
            params = list(set(params))
    if call.data == '3':
        if params.__contains__(3):
            bot.answer_callback_query(call.id, text="Цитаты убраны")
            params.remove(3)
        else:
            bot.answer_callback_query(call.id, text="Выбраны цитаты")
            params.append(3)
            params = list(set(params))
    if call.data == '4':
        if params.__contains__(4):
            bot.answer_callback_query(call.id, text="Песни убраны")
            params.remove(4)
        else:
            bot.answer_callback_query(call.id, text="Выбраны песни")
            params.append(4)
            params = list(set(params))
    if call.data == '5':
        if params.__contains__(5):
            bot.answer_callback_query(call.id, text="Видео убраны")
            params.remove(5)
        else:
            bot.answer_callback_query(call.id, text="Выбраны видео")
            params.append(5)
            params = list(set(params))
    if call.data == '6':
        params_str = str(set(params))[1:-1]
        bot.answer_callback_query(call.id, show_alert=True, text="Вы выбрали типы медиа, можете писать всё, что угодно!")
        print(params)
        return params_str
    # print(params)


@bot.message_handler(commands=['start'])
def get_types(message):
    keyboard = types.InlineKeyboardMarkup()  # наша клавиатура
    key_zero = types.InlineKeyboardButton(text='Гифки', callback_data='0')
    keyboard.add(key_zero)
    key_one = types.InlineKeyboardButton(text='Картинки', callback_data='1')
    keyboard.add(key_one)
    key_two = types.InlineKeyboardButton(text='Стихи', callback_data='2')
    keyboard.add(key_two)
    key_three = types.InlineKeyboardButton(text='Цитаты', callback_data='3')
    keyboard.add(key_three)
    key_four = types.InlineKeyboardButton(text='Песни', callback_data='4')
    keyboard.add(key_four)
    key_five = types.InlineKeyboardButton(text='Видео', callback_data='5')
    keyboard.add(key_five)
    key_six = types.InlineKeyboardButton(text='Всё выбрано, далее...', callback_data='6')
    types.KeyboardButtonPollType()
    keyboard.add(key_six)
    question = 'Какой контент вы хотите получать?'
    bot.send_message(message.chat.id, text=question, reply_markup=keyboard)


@bot.message_handler(content_types=['text'])
def text_processing(message):
    # message_from_user = json.dumps({'Text': message.text}, indent=4, ensure_ascii=False).encode('UTF8')
    global params_str
    chatid = message.chat.id
#     print(chatid)
#     print(message.text)
    try:
        tr = ''
        new_message, tr = transl(message.text, emotion)
        bot.reply_to(message, new_message)
        if new_message == 'admiration' or new_message == 'amusement' or new_message == 'admiration' or new_message == 'approval' or new_message == 'caring' or new_message == 'desire' or new_message == 'excitement' or new_message == 'gratitude' or new_message == 'joy' or new_message == 'love' or new_message == 'optimism' or new_message == 'pride' or new_message == 'relief':
            new_message = 'positive'
        if new_message == 'anger' or new_message == 'annoyance' or new_message == 'disappointment' or new_message == 'disapproval' or new_message == 'disgust' or new_message == 'embarrassment' or new_message == 'fear' or new_message == 'grief' or new_message == 'nervousness' or new_message == 'remorse' or new_message == 'sadness':
            new_message = 'negative'
        if new_message == 'confusion' or new_message == 'curiosity' or new_message == 'realization' or new_message == 'surprise' or new_message == 'neutral':
            new_message = 'neutral'
        print(new_message)
        body = json.dumps({'paramOne': new_message, 'paramTwo': params_str})
        headers = {'content-type': 'application/json'}
        responce = requests.post('http://app:8080/api/getReaction', data=body, headers=headers)
        print(responce.text)

        file_route = responce.text[:]
        params_json = file_route.split(':')
        namefile = ""
        if params_json[1].find("poety") != -1 or params_json[1].find("quote") != -1:
            namefile = str(params_json[2:])[2:-3]
            namefile = namefile.replace('\\n', '\n')
            namefile = namefile.replace("\\", '')
        else:
            namefile = file_route.split(',')[1].split(':')[1][1:-2]
    except:
        print("Something go wrong in block of answers for text message")
        traceback.print_exc()
        bot.reply_to(message, errorMessage)


    try:
        type = responce.text
        if type.find("poety") != -1:
            bot.reply_to(message, namefile)
        elif type.find("quote") != -1:
            bot.reply_to(message, namefile)
        else:
            fileurl = ''
            for i in range(len(all_files_on_disk)):
                if all_files_on_disk[i].FIELDS['name'] == namefile:
                    fileurl = all_files_on_disk[i].FIELDS['file']
                    break

            if type.find("picture") != -1:
                bot.send_photo(chat_id=chatid, photo=fileurl)
            elif type.find("gif") != -1:
                bot.send_animation(chat_id=chatid, animation=fileurl)
            elif type.find("mp4") != -1:
                bot.send_video(chat_id=chatid, video=fileurl)
            elif type.find("mp3") != -1:
                bot.send_audio(chat_id=chatid, audio=fileurl)
    except:
        print("Something go wrong in sending media for text message")
        traceback.print_exc()
        bot.reply_to(message, rebootMessageMessage)
        sys.exit(5)


bot.polling()
