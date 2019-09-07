FROM ubuntu

MAINTAINER S.Guliaev <gulyaevsa@mai.ru>
ENV LC_CTYPE en_US.UTF-8
ENV LANG en_US.UTF-8
RUN apt update && apt upgrade -y
RUN apt install -y libreoffice-script-provider-python
RUN apt install -y python3-uno
RUN apt install -y libglib2.0-0 libsm6 libxext6 libxrender-dev libmagickwand-dev unoconv
RUN apt install -y python3 python3-dev
RUN apt install -y python3-pip
RUN apt install -y libjpeg62-dev libjpeg62-dev
COPY ./app /app
WORKDIR /app
ADD requirements.txt requirements.txt
RUN pip3 install -r requirements.txt
RUN pip3 install --upgrade setuptools wheel
ENTRYPOINT ["python3"]
CMD ["app.py"]
