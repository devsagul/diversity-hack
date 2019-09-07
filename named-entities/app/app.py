import secrets
from flask import Flask, request, render_template
from natasha import (
        NamesExtractor,
        LocationExtractor,
        OrganisationExtractor,
        DatesExtractor,
        MoneyExtractor,
)
from forms import NatashaForm
from flask_wtf.csrf import CSRFProtect

app = Flask(__name__)
csrf = CSRFProtect()
app.config['SECRET_KEY'] = secrets.token_urlsafe(16)

@app.route('/', methods=['GET', 'POST'])
def index():
    form = NatashaForm(request.form)
    if request.method == 'POST':
        return natasha_results(form.data['text'])
    return render_template('index.html', form=form)

@app.route('/natasha', methods=['POST'])
def get_diff():
    return different_entities(request.data['texts'])

def dates_mapper(date):
    arr = []
    if date.fact.day is not None:
        arr.append(date.fact.day)
    if date.fact.month is not None:
        arr.append(date.fact.month)
    if date.fact.year is not None:
        arr.append(date.fact.year)
    return "/".join(map(str, arr))

@app.route('/results')
def natasha_results(text):
    names_extractor = NamesExtractor()
    location_extractor = LocationExtractor()
    organisation_extractor = OrganisationExtractor()
    dates_extractor = DatesExtractor()
    money_extractor = MoneyExtractor()
    names_mapper = lambda x: text[x.span[0] : x.span[1]]
    location_mapper = names_mapper
    org_mapper = names_mapper
    money_mapper = names_mapper
    res = {
        'names' : list(set(map(names_mapper, names_extractor(text)))),
        'locations' : list(set(map(location_mapper, location_extractor(text)))),
        'organisations' : list(set(map(org_mapper, organisation_extractor(text)))),
        'dates' : list(set(map(dates_mapper, dates_extractor(text)))),
        'money' : list(set(map(money_mapper, money_extractor(text)))),
    }
    return res

def natasha_res(text):
    names_extractor = NamesExtractor()
    location_extractor = LocationExtractor()
    organisation_extractor = OrganisationExtractor()
    dates_extractor = DatesExtractor()
    money_extractor = MoneyExtractor()
    names_mapper = lambda x: text[x.span[0] : x.span[1]]
    location_mapper = names_mapper
    org_mapper = names_mapper
    money_mapper = names_mapper
    res = {
        'names' : set(map(names_mapper, names_extractor(text))),
        'locations' : set(map(location_mapper, location_extractor(text))),
        'organisations' : set(map(org_mapper, organisation_extractor(text))),
        'dates' : set(map(dates_mapper, dates_extractor(text))),
        'money' : set(map(money_mapper, money_extractor(text))),
    }
    return res

def different_entities(texts):
    natasha_results = [natasha_res(text) for text in texts]
    common = {
        'names' : set.intersection(*(item['names'] for item in natasha_results)),
        'locations' : set.intersection(*(item['locations'] for item in natasha_results)),
        'organisations' : set.intersection(*(item['organisations'] for item in natasha_results)),
        'dates' : set.intersection(*(item['dates'] for item in natasha_results)),
        'money' : set.intersection(*(item['money'] for item in natasha_results)),
    }
    res = [
            {
                'names' : item['names'] - common['names'],
                'locations' : item['locations'] - common['locations'],
                'organisations' : item['organisations'] - common['organisations'],
                'dates' : item['dates'] - common['dates'],
                'money' : item['money'] - common['money'],
            }
            for item in natasha_results
    ]
    return res

if __name__ == '__main__':
    csrf.init_app(app)
    app.run(debug=True, host='0.0.0.0')
