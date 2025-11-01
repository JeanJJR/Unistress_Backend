FROM ubuntu:latest
LABEL authors="josfe"

ENTRYPOINT ["top", "-b"]