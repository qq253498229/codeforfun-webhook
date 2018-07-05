#!/usr/bin/env bash
cd /root/codeforfun/codeforfun-webhooks
rm -rf target
mkdir target
cp /root/codeforfun/codeforfun-jenkins/data/workspace/codeforfun-webhooks/target/webhooks.jar ./target
docker-compose build
docker-compose rm -s -f
docker-compose up -d