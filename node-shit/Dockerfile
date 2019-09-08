FROM ubuntu
RUN apt -yq update
RUN apt -yq upgrade
RUN apt -yq install nodejs npm
WORKDIR /data
ADD src/package.json package.json
RUN npm install
ENV NODE_PATH /data/node_modules/
WORKDIR /app
ENTRYPOINT ["node"]
CMD ["app"]

