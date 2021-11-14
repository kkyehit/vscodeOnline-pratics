# openvscode-server
```
docker run -it --init -p 3000:3000 -v "$(pwd):/home/workspace:cached" gitpod/openvscode-server

```
[출처](https://github.com/gitpod-io/openvscode-server)

# ingress-controller
```
# Add the ingress-nginx repository
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update

# Use Helm to deploy an NGINX ingress controller
helm install nginx-ingress ingress-nginx/ingress-nginx ^
    --create-namespace --namespace ingress-basic ^
    --set controller.replicaCount=2 ^
    --set controller.nodeSelector."kubernetes\.io/os"=linux ^
    --set controller.image.image=ingress-nginx/controller ^
    --set controller.image.tag=v0.48.1
```
[출처](https://docs.microsoft.com/ko-kr/azure/aks/ingress-basic)