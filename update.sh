#!/usr/bin/env bash
cd /root/codeforfun/codeforfun-webhooks
docker-compose build
docker-compose rm -s -f
docker-compose up -d