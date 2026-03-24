#Node 20, alpine linux
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm install

#Copy entire project into /app
COPY . .
RUN npm run build

#nginx web server, alpine linux
FROM nginx:stable-alpine

#copies the app build into nginx
COPY --from=build /app/build /usr/share/nginx/html

#copy custom nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

#Start Nginx
CMD ["nginx", "-g", "daemon off;"]