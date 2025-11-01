#!/bin/bash
# update-collab.sh
# Script para mantener tu fork sincronizado con upstream

echo "Cambiando a la rama main..."
git checkout main

echo "Obteniendo cambios de upstream..."
git fetch upstream

echo "Mezclando cambios de upstream/main en main..."
git merge upstream/main

echo "Enviando cambios a tu fork..."
git push origin main

echo "✅ Repositorio sincronizado con upstream."
