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
- curl -X POST localhost:8100/vscode/1
- curl -X GET localhost:8100/vscode/1

- curl -X POST localhost:8100/vscode/2
- curl -X GET localhost:8100/vscode/2

## 목록 확인
- curl -X GET localhost:8100/vscode/
## 접속
- `{host}/{id}?tkn=*****` 로 접속한 후 `{host}/{id}`로 다시 접속

## 삭제
- curl -X DELETE localhost:8100/vscode/1
- curl -X DELETE localhost:8100/vscode/2



## issue
- 영구 데이터를 위해 스토리지 사용 방법 고민 필요