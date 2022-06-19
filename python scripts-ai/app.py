
from typing import List
from flask import Flask
import numpy as np
from flask import jsonify,request
import joblib

import json
app=Flask(__name__)

#helper
def trim(encodings_classes):
    classes=[]
    for i in encodings_classes:
        classes.append(i.strip())
    return classes

@app.route('/')
def home():
    return "Hello"



# Actual
@app.route('/api/v1/preprocess',methods=['GET','POST'])



def predict():
    # print(encoderSymptom_1.i(request.form['Symptom1']))
    # print(encoderSymptom_2)

    symptom1=encoderSymptom_1.index(request.form['Symptom1'])
    print(request.form['Symptom1'])
    symptom2=encoderSymptom_2.index(request.form['Symptom2'])
    symptom3=encoderSymptom_3.index(request.form['Symptom3'])
    symptom4=encoderSymptom_4.index(request.form['Symptom4'])

    symptom_array=np.array([symptom1,symptom2,symptom3,symptom4]).reshape(1,-1)
    print(symptom_array)
    prediction = model.predict(symptom_array)
    probability=np.max(model.predict_proba(symptom_array))
    prrediction=disease_encoder.inverse_transform(prediction)[0]

    return jsonify({
        'prediction':prrediction,
        'probability':probability
    })


if __name__=='__main__':
    encoderSymptom_1=joblib.load('./encoderSymptom_1').classes_
    encoderSymptom_2=joblib.load('./encoderSymptom_2').classes_
    encoderSymptom_3=joblib.load('./encoderSymptom_3').classes_
    encoderSymptom_4=joblib.load('./encoderSymptom_4').classes_
    encoderSymptom_1=trim(encoderSymptom_1)
    encoderSymptom_2=trim(encoderSymptom_2)
    encoderSymptom_3=trim(encoderSymptom_3)
    encoderSymptom_4=trim(encoderSymptom_4)

    disease_encoder=joblib.load('./encoder_disease')
    model=joblib.load('./model_v2')

    app.run(debug=True,port=80)

