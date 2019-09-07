FROM python:3.7.4-stretch
WORKDIR /app
EXPOSE 5000
ADD requirements.txt requirements.txt
RUN pip3 install -r requirements.txt
ENTRYPOINT ["python3", "app.py"]

