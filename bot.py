# bot = telebot.TeleBot('5667635481:AAFXsE4kGoPFmniOXGk5_3ldA6mxs1rO1PA')
import telebot
import uuid
import os
import speech_recognition as sr
import json
from pyffmpeg import FFmpeg
from telebot import types

import translate
from transformers import RobertaTokenizerFast, TFRobertaForSequenceClassification, pipeline
import requests
# import endpoint
import yadisk

params = []
params_str = ''
language = 'ru_RU'
TOKEN = '5667635481:AAFXsE4kGoPFmniOXGk5_3ldA6mxs1rO1PA'
bot = telebot.TeleBot(TOKEN)
r = sr.Recognizer()
tokenizer = RobertaTokenizerFast.from_pretrained('arpanghoshal/EmoRoBERTa')
model = TFRobertaForSequenceClassification.from_pretrained('arpanghoshal/EmoRoBERTa')
y = yadisk.YaDisk(token="y0_AgAAAAAEnjtHAAh2ZQAAAADQQpFVwaI_1VTaSxKYF4jqPsFnYcvzgHA")
all_files_on_disk = list(y.listdir("/cdn"))
# homeDirectory = "cdn/"
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
    file_name_full = './voice/' + filename + '.ogg'
    file_name_full_converted = './ready/' + filename + '.wav'
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
    original_emotion = translate.transl(text, emotion)
    print(original_emotion)
    bot.reply_to(message, original_emotion)
    new_message = original_emotion[:]
    if new_message == 'admiration' or new_message == 'amusement' or new_message == 'admiration' or new_message == 'approval' or new_message == 'caring' or new_message == 'desire' or new_message == 'excitement' or new_message == 'gratitude' or new_message == 'joy' or new_message == 'love' or new_message == 'optimism' or new_message == 'pride' or new_message == 'relief':
        new_message = 'positive'
    if new_message == 'anger' or new_message == 'annoyance' or new_message == 'disappointment' or new_message == 'disapproval' or new_message == 'disgust' or new_message == 'embarrassment' or new_message == 'fear' or new_message == 'grief' or new_message == 'nervousness' or new_message == 'remorse' or new_message == 'sadness':
        new_message = 'negative'
    if new_message == 'confusion' or new_message == 'curiosity' or new_message == 'realization' or new_message == 'surprise' or new_message == 'neutral':
        new_message = 'neutral'
    print(new_message)
    body = json.dumps({'paramOne': new_message, 'paramTwo': '0, 1, 2, 3, 4'})
    headers = {'content-type': 'application/json'}
    responce = requests.post('http://localhost:8080/api/getReaction', data=body, headers=headers)
    # print(responce.request.body)
    # responce = requests.get('http://localhost:8080/tags/all')
    print(responce.text)

    # bot.reply_to(message, new_message)
    fileRoute = responce.text[:]
    paramsJson = fileRoute.split(':')
    namefile = ""
    if paramsJson[1].find("poety") != -1 or paramsJson[1].find("quote") != -1:
        namefile = str(paramsJson[2:])[2:-3]
        namefile = namefile.replace('\\n', '\n')
        namefile = namefile.replace("\\", '')
    else:
        namefile = fileRoute.split(',')[1].split(':')[1][1:-2]

    type = responce.text
    if type.find("poety") != -1:
        bot.reply_to(message, namefile)
    elif type.find("quote") != -1:
        bot.reply_to(message, namefile)
    else:
        # namefile = endpoint.getPicture(namefile)
        namefile = url + namefile
        print(namefile)
        bot.reply_to(message, namefile)
    # bot.reply_to(message, new_message)
    # bot.reply_to(message, namefile)
    # bot.reply_to(message, new_message)
    # os.remove(file_name_full)
    # os.remove(file_name_full_converted)


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
    # key_four = types.InlineKeyboardButton(text='Песни', callback_data='4')
    # keyboard.add(key_four)
    # key_five = types.InlineKeyboardButton(text='Видео', callback_data='5')
    # keyboard.add(key_five)
    key_six = types.InlineKeyboardButton(text='Всё выбрано, далее...', callback_data='6')
    types.KeyboardButtonPollType()
    keyboard.add(key_six)
    question = 'Какой контент вы хотите получать?'
    bot.send_message(message.chat.id, text=question, reply_markup=keyboard)


@bot.message_handler(content_types=['text'])
def text_processing(message):
    # message_from_user = json.dumps({'Text': message.text}, indent=4, ensure_ascii=False).encode('UTF8')
    chatid = message.chat.id
    print(chatid)
    print(message.text)
    new_message = translate.transl(message.text, emotion)
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
    responce = requests.post('http://localhost:6868/api/getReaction', data=body, headers=headers)
    # print(responce.request.body)
    # responce = requests.get('http://localhost:8080/tags/all')
    print(responce.text)
    # bot.reply_to(message, new_message)
    fileRoute = responce.text[:]
    paramsJson = fileRoute.split(':')
    namefile = ""
    if paramsJson[1].find("poety") != -1 or paramsJson[1].find("quote") != -1:
        namefile = str(paramsJson[2:])[2:-3]
        namefile = namefile.replace('\\n', '\n')
        namefile = namefile.replace("\\", '')
    else:
        namefile = fileRoute.split(',')[1].split(':')[1][1:-2]

    # type = responce.text
    if type.find("poety") != -1:
        bot.reply_to(message, namefile)
    elif type.find("quote") != -1:
        bot.reply_to(message, namefile)
    else:
        # namefile = type.text
        # namefile = endpoint.getPicture(namefile)
        names_and_links = ()
        fileurl = ''
        for i in range(len(all_files_on_disk)):
            if all_files_on_disk[i].FIELDS['name'] == namefile:
                fileurl = all_files_on_disk[i].FIELDS['file']
                break
        # namefile = namefile.split('.')[0]
        # if namefile == 'angryDog':
        #     namefile = 'AgACAgIAAxkBAAICIGM5j77QSOvWgtoTVVxdcmYwKTjjAAIbwzEb3orQSTOEtSIhwEEXAQADAgADcwADKgQ'
        # if namefile == 'doraCute':
        #     namefile = 'AgACAgIAAxkBAAICIWM5kAT3Tdg6jORJX4W-lBzTyDP9AAIdwzEb3orQSVZ_ymjkG5otAQADAgADcwADKgQ'
        # if namefile == 'goodCat':
        #     namefile = 'AgACAgIAAxkBAAICImM5kDMnH3Alwk0OFEHXXcGawoY-AAIewzEb3orQSdaiJR6MD_CcAQADAgADcwADKgQ'
        # if namefile == 'sadCat':
        #     namefile = 'AgACAgIAAxkBAAICI2M5kFz6RD59ceKT5Sm1ttwMwc3rAAIfwzEb3orQSROIHnD6_vldAQADAgADcwADKgQ'
        # if namefile == 'smokingCat':
        #     namefile = 'AgACAgIAAxkBAAICJGM5kKL2Kxa-pwHrC9vj8wMvmVe4AAIgwzEb3orQSaq2KUkwE-kqAQADAgADcwADKgQ'
        # if namefile == 'batemanCool':
        #     namefile = 'CgACAgIAAxkBAAICM2M5lLI2HU6kmvvuAgbhPgWFUpurAAJHHgAC3orQSZdixCiKx7pfKgQ'
        # if namefile == 'batemanNeutral':
        #     namefile = 'CgACAgIAAxkBAAICNGM5lLvvX8E2b8TBvch_NtGOlU-YAAJIHgAC3orQSfvYBK-G_ej-KgQ'
        # if namefile == 'batemanOUU':
        #     namefile = 'CgACAgIAAxkBAAICNWM5lQABgfE07IXkXCJsD1FGT1enlgACSh4AAt6K0El6N1sKCzpYSCoE'
        # if namefile == 'batemanAngry':
        #     namefile = 'CgACAgIAAxkBAAICNmM5lRzFxiUV9tb-i-_VdC03mqBZAAJMHgAC3orQSboRRmOVH6HPKgQ'
        # print(namefile)
        # bot.reply_to(message, namefile)
        # base_url = 'https://cloud-api.yandex.net/v1/disk/public/resources/download?'
        # link = namefile  # Сюда вписываете вашу ссылку
        #
        # # Получаем загрузочную ссылку
        # final_url = base_url + 'public_key=' + link
        # response = requests.get(final_url)
        # print(response)
        # download_url = response.json()['href']
        #
        # download_response = requests.get(download_url)
        # if (namefile.find("mp4") != 1):
        #     with open('video.mp4', 'wb') as f:
        #         f.write(download_response.content)
        #         f.close()
        # if (namefile.find("mp3") != 1):
        #     with open('audio.mp3', 'wb') as f:
        #         f.write(download_response.content)
        #         f.close()
        # if (namefile.find("jpg") != 1):
        #     with open('picture.jpg', 'wb') as f:
        #         f.write(download_response.content)
        #         f.close()
        # if (namefile.find("gif") != 1):
        #     with open('gif.gif', 'wb') as f:
        #         f.write(download_response.content)
        #         f.close()
        #
        # print(type)
        # # bot.reply_to(message, namefile)
        #
        if type.find("picture") != -1:
            bot.send_photo(chat_id=chatid, photo=fileurl)
        elif type.find("gif") != -1:
            bot.send_animation(chat_id=chatid, animation=fileurl)
        elif type.find("mp4") != -1:
            bot.send_video(chat_id=chatid, video=fileurl)
        elif type.find("mp3") != -1:
            bot.send_audio(chat_id=chatid, audio=fileurl)


bot.polling()
