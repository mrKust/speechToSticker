from googletrans import Translator

from transformers import RobertaTokenizerFast, TFRobertaForSequenceClassification, pipeline


def transl(original_text, emotion):
    translator = Translator()
    translation = translator.translate(text=original_text, dest='en', src='ru')
    print(translation.text)

    emotion_labels = emotion(translation.text)
    print(emotion_labels)
    return emotion_labels[0]['label'], translation.text

