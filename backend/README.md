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