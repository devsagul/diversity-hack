from flask_wtf import FlaskForm
from wtforms import TextAreaField
from wtforms.widgets import TextArea

class NatashaForm(FlaskForm):
    text = TextAreaField('Text', widget=TextArea())

