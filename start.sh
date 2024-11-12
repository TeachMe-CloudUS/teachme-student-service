#!/bin/bash

mvn spring-boot:build-image

docker compose up --build