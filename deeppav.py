# from deeppavlov import configs
# from deeppavlov import build_model
#
# model = build_model("deeppavlov/configs/classifiers/rusentiment_elmo_twitter_cnn.json", download=True)
#
# # get predictions for 'input_text1', 'input_text2'
# model(['Мне очень нравится наш код'])

import deeppavlov
from deeppavlov.utils.telegram import interact_model_by_telegram

interact_model_by_telegram(model_config=deeppavlov.configs.classifiers.rusentiment_elmo_twitter_cnn, token='5667635481:AAFXsE4kGoPFmniOXGk5_3ldA6mxs1rO1PA')

