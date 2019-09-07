from flask import Flask
from natasha import NamesExtractor

app = Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def index():

    return 'Test'

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
