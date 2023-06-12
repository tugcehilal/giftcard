# giftcard

To make it running first run the following comments:

docker run -d --name axonserver -p 8024:8024 -p 8124:8124 axoniq/axonserver

docker run -it --rm --name postgres -p 5432:5432 -e POSTGRES_USER=giftcard -e POSTGRES_PASSWORD=secret postgres:9.6
