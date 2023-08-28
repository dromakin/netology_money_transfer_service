FROM node:10.13.0-alpine
# Create Directory for the Container
WORKDIR /usr/src/app
# Only copy the package.json file to work directory
COPY card-transfer/package.json .
# Install all Packages
RUN npm install
# Copy all other source code to work directory
ADD card-transfer /usr/src/app
# TypeScript
RUN npm i
# Start
CMD [ "npm", "run", "start" ]
EXPOSE 5500