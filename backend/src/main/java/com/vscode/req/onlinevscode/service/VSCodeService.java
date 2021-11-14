package com.vscode.req.onlinevscode.service;

import org.springframework.beans.factory.annotation.Autowired;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.api.model.networking.v1.Ingress;
import io.fabric8.kubernetes.api.model.networking.v1.IngressBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;

@org.springframework.stereotype.Service
public class VSCodeService {

    @Autowired
    private KubernetesClient kubernetesClient;
    
    public String createVSCode(Long id){
        /* create namespace */
        Namespace ns = kubernetesClient
            .namespaces()
            .create(
                new NamespaceBuilder()
                    .withNewMetadata()
                        .withName("vscode-ns-"+id)
                        .addToLabels("app", "vscode")
                    .endMetadata()
                .build()
            );

        /* create deployment */
        Deployment dp = kubernetesClient
            .apps()
            .deployments()
            .inNamespace(ns.getMetadata().getName())
            .create(
                new DeploymentBuilder()
                    .withNewMetadata()
                        .withName("vscode-dp-"+id)
                        .addToLabels("app", "vscode")
                    .endMetadata()
                    .withNewSpec()
                        .withReplicas(1)
                        .withNewSelector()
                            .addToMatchLabels("app", "vscode")
                        .endSelector()
                        .withNewTemplate()
                            .withNewMetadata()
                                .addToLabels("app", "vscode")
                            .endMetadata()
                            .withNewSpec()
                                .addNewContainer()
                                    .withName("vscode-pod-"+id)
                                    .withImage("gitpod/openvscode-server")
                                    .addNewPort()
                                        .withContainerPort(3000)
                                    .endPort()
                                .endContainer()
                            .endSpec()
                        .endTemplate()
                    .endSpec()
                    .build()
            );
        /* create service */
        Service svc = kubernetesClient
            .services()
            .inNamespace(ns.getMetadata().getName())
            .create(
                new ServiceBuilder()
                    .withNewMetadata()
                        .withName("vscode-svc-"+id)
                    .endMetadata()
                    .withNewSpec()
                        .addToSelector("app", "vscode")
                        .addNewPort()
                            .withProtocol("TCP")
                            .withPort(80)
                            .withNewTargetPort(3000)
                        .endPort()
                    .endSpec()
                    .build()
            );

        /* create ingress */
        Ingress ing = kubernetesClient
            .network()
            .v1()
            .ingresses()
            .inNamespace(ns.getMetadata().getName())
            .create(
                new IngressBuilder()
                    .withNewMetadata()
                        .withName("vscode-ing-"+id)
                        .addToAnnotations("nginx.ingress.kubernetes.io/rewrite-target", "/$2")
                    .endMetadata()
                    .withNewSpec()
                        .withIngressClassName("nginx")
                        .addNewRule()
                            .withNewHttp()
                                .addNewPath()
                                    .withPath("/"+id)
                                    .withPathType("Prefix")
                                    .withNewBackend()
                                        .withNewService()
                                            .withName(svc.getMetadata().getName())
                                            .withNewPort()
                                                .withNumber(80)
                                            .endPort()
                                        .endService()
                                    .endBackend()
                                .endPath()
                            .endHttp()
                        .endRule()
                    .endSpec()
                .build()
            );

        return "";
    }
}
