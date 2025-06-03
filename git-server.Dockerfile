FROM debian:bullseye-slim

RUN apt-get update && \
    apt-get install -y git && \
    apt-get clean

EXPOSE 9418

ENTRYPOINT ["git", "daemon", "--verbose", "--export-all", "--enable=receive-pack", "--base-path=/srv/git", "--reuseaddr", "--listen=0.0.0.0", "--port=9418"]

