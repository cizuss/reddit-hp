docker build -t cizuss/reddithp:latest -t cizuss/reddithp:$SHA .
docker push cizuss/reddithp:latest
docker push cizuss/reddithp:$SHA
kubectl apply -f k8s
kubectl set image deployments/server-deployment server=cizuss/reddithp:$SHA