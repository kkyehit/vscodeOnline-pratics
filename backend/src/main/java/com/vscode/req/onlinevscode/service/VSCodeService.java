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


        return "";
    }
}
