#!/bin/bash

set -e

VERSION="1.0.5"
IMAGE="directpurchasebr/direct-purchase-api"

echo "🔧 Verificando se buildx está configurado..."
docker buildx inspect mybuilder > /dev/null 2>&1 || {
  echo "🛠️  Criando builder 'mybuilder'..."
  docker buildx create --name mybuilder --use
}

echo "🚀 Fazendo build multi-plataforma e push das imagens..."
docker buildx build \
  --platform linux/amd64,linux/arm64 \
  --tag $IMAGE:$VERSION \
  --tag $IMAGE:latest \
  --push \
  .
