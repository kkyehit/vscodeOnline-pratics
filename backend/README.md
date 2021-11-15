[fabric8io/kubernetes-client](https://github.com/fabric8io/kubernetes-client)
[spring-cloud-kubernetes](https://docs.spring.io/spring-cloud-kubernetes/docs/current/reference/html/#discoveryclient-for-kubernetes)
[Baeldung](https://www.baeldung.com/spring-cloud-kubernetes)


# k8s 자원 동적 생성/삭제
## deployment
- name : vscode-dp-{id}
- image : gitpod/openvscode-server
- replicas : 1
- port : 3000
## service
- name : vscode-svc-{id}
- port : 80
- targetport : 3000
## ingress
- name : vscode-ing-{id}
- host : *
- path : /{id}
- class : nginx
## 생성 & 조회
- curl -X POST localhost:8080/vscode/1
- curl -X GET localhost:8080/vscode/1

- curl -X POST localhost:8080/vscode/2
- curl -X GET localhost:8080/vscode/2

## 접속
- `{host}/{id}?tkn=*****` 로 접속한 후 `{host}/{id}`로 다시 접속

## 삭제
- curl -X DELETE localhost:8080/vscode/1
- curl -X DELETE localhost:8080/vscode/2



## issue
- 컨테이너간 격리안됨 ( {host}/ 환경 공유됨 )
- 한명 한명 다른 IP 사용하면 되긴함
- 도메인을 이용해 분리하는 방법 고민 해보자