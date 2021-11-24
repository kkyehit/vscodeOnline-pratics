package com.vscode.req.onlinevscode.utill;

import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.api.model.networking.v1.Ingress;
import io.fabric8.kubernetes.api.model.networking.v1.IngressBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;

@Component
public class KubernetesResourceManagerUtill {
    
    @Autowired
    private KubernetesClient kubernetesClient;

    /* craete namespace */
    public Namespace createNamespace(String namespaceName){
        return kubernetesClient
            .namespaces()
            .create(
                new NamespaceBuilder()
                    .withNewMetadata()
                        .withName(namespaceName)
                        .addToLabels("app", "vscode")
                    .endMetadata()
                .build()
            );
    }
    /* create deployment */
    public Deployment createDeployment(String deploymentName, String namespaceName, String podName, int availableReplicas, String image, String token, String volumeName, String volumePath){
        return kubernetesClient
            .apps()
            .deployments()
            .inNamespace(namespaceName)
            .create(
                new DeploymentBuilder()
                    .withNewMetadata()
                        .withName(deploymentName)
                        .addToLabels("app", "vscode")
                    .endMetadata()
                    .withNewSpec()
                        .withReplicas(availableReplicas)
                        .withNewSelector()
                            .addToMatchLabels("app", "vscode")
                        .endSelector()
                        .withNewTemplate()
                            .withNewMetadata()
                                .addToLabels("app", "vscode")
                            .endMetadata()
                            .withNewSpec()
                                .addNewContainer()
                                    .withName(podName)
                                    .withImage(image)
                                    .addNewPort()
                                        .withContainerPort(3000)
                                    .endPort()
                                    .addNewEnv()
                                        .withName("CONNECTION_TOKEN")
                                        .withValue(token)
                                    .endEnv()
                                    // .addNewVolumeMount()
                                    //     .withMountPath("/home/workspace/")
                                    //     .withName("vscode-volume-"+id)
                                    // .endVolumeMount()
                                .endContainer()
                                .addNewVolume()
                                    .withName(volumeName)
                                    .withNewHostPath()
                                        .withPath(volumeName)
                                    .endHostPath()
                                .endVolume()
                            .endSpec()
                        .endTemplate()
                    .endSpec()
                    .build()
            );
    }

    /* create service */
    public Service createService(String serviceName, String namespaceName){
        return kubernetesClient
        .services()
        .inNamespace(namespaceName)
        .create(
            new ServiceBuilder()
                .withNewMetadata()
                    .withName(serviceName)
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
    }
    /* create ingress */
    public Ingress createIngress(String ingressName, String namespaceName, String serviceName,String hostName){
        return kubernetesClient
            .network()
            .v1()
            .ingresses()
            .inNamespace(namespaceName)
            .create(
                new IngressBuilder()
                    .withNewMetadata()
                        .withName(ingressName)
                        //.addToAnnotations("nginx.ingress.kubernetes.io/rewrite-target", "/$2")
                    .endMetadata()
                    .withNewSpec()
                        .withIngressClassName("nginx")
                        .addNewRule()
                            .withHost(hostName)
                            .withNewHttp()
                                .addNewPath()
                                    .withPath("/")
                                    .withPathType("Prefix")
                                    .withNewBackend()
                                        .withNewService()
                                            .withName(serviceName)
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
    }
    /* get deployment log*/
    public String getDeploymentLogs(String deploymentName, String namespaceName){
        return kubernetesClient
        .apps()
        .deployments()
        .inNamespace(namespaceName)
        .withName(deploymentName)
        .getLog();
    }

    /* deployment timeout check */
    public Deployment waitDeploymentIsValid(String deploymentName, String namespaceName, int availableReplicas, int timeout) throws TimeoutException{
        Deployment dp = null;
        int time = 0;
        while(!isVaildDeployment(dp, availableReplicas)) {
            if(++time == timeout) throw new TimeoutException();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            dp = getDeployment(deploymentName, namespaceName);
        } 
        
        return dp;
    }

    /* get deployment */
    public Deployment getDeployment(String deploymentName, String namespaceName){
        return kubernetesClient
        .apps()
        .deployments()
        .inNamespace(namespaceName)
        .withName(deploymentName)
        .get();
    }
    /* check deployment */
    private boolean isVaildDeployment(Deployment dp, int availableReplicas){
        return (dp != null 
            && dp.getStatus() != null 
            && dp.getStatus().getAvailableReplicas() != null 
            && dp.getStatus().getAvailableReplicas().intValue() == availableReplicas);
    }
    
    /* delete namespace */
    public String deleteNamespace(String namespaceName){
        kubernetesClient
        .namespaces()
        .withName(namespaceName)
        .delete();
        return "delete namepsace["+namespaceName+"]";
    }

}
