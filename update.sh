#!/usr/bin/env bash
cd /root/codeforfun/codeforfun-webhooks
mvn clean install
docker-compose rm -s -f
docker-compose up -d