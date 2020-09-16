FROM pub-docker-scm.citictrust.com.cn/8-jdk-fonts:v2

WORKDIR /home/app

COPY demo/ ./demo
RUN chmod -R 777 /home/app

CMD cd demo && ./bin/boot.sh start

EXPOSE 80
