from flask import Flask, request
import pyrebase
from flask_cors import CORS

choice_dict = {1:"Hardware", 2: "Mobile", 3: "Web", 4: "Javascript"}

config = {"apiKey": "Enter your own API Key here",
    "authDomain": "expalk.firebaseapp.com",
    "databaseURL": "https://expalk-default-rtdb.firebaseio.com",
    "projectId": "expalk",
    "storageBucket": "expalk.appspot.com",
    "messagingSenderId": "466641708586",
    "appId": "1:466641708586:web:9250f43efa073db3bc8553"
}

firebase = pyrebase.initialize_app(config)
auth = firebase.auth()
db = firebase.database()

app = Flask(__name__)
CORS(app)

@app.route("/")
def home():
  return "hello, world!"

@app.route('/signup', methods =['POST'])
def signup():
    register = request.get_json()
    name = register['name']
    email = register['email']
    password = register['password']

    test = auth.create_user_with_email_and_password(email, password)
    db.child("Users").child(test['localId']).child("name").set(name)
    db.child("Users").child(test['localId']).child("email").set(email)
    db.child("Users").child(test['localId']).child("token").set(5)

    return {"status": 1, "email": email, "password": password}

@app.route('/signin/<string:email>/<string:password>', methods = ['GET'])
def signin(email, password):
  try:
    result = auth.sign_in_with_email_and_password(email, password)
    global userId
    userId = result['localId']
    get_token = db.child("Users").child(userId).get()
    global token 
    token = get_token.val()['token']
    name = get_token.val()['name']
    return{"token": token, "status": 1, "name": name}
    
  except: 
    return {"status": 0}

@app.route('/speaker/<int:choice>', methods = ["GET"]) #Mentor
def speaker(choice):
  try:
    users = db.child("Online").child("Mentor").child(choice_dict[choice]).get()
    print()

    uid = ""
    flag = True

    for key in users.val():
      if flag == True:
        uid = key
        flag = False


    db.child("Users").child(uid).child("token").set(token+5)
    db.child("Online").child("Mentor").child(choice_dict[choice]).child(uid).remove()

    return {"channel_name": uid, "status":1}

  except:
    return {"message": "No Mentee available. Try reconnecting later.", "status":0}

@app.route('/listner/<int:choice>', methods = ["GET"]) #Mentee
def push_listner(choice):
  db.child("Online").child("Mentor").child(choice_dict[choice]).child(userId).child("status").set("0")
  db.child("Online").child("Mentor").child(choice_dict[choice]).child(userId).child("uid").set(userId)
  db.child("Users").child(userId).child("token").set(token-1)
  return {"status" : 1, "message": "You will be connected to a Mentor shortly.", "uid": userId}


if __name__ == '__main__':
    app.run(debug = True)
