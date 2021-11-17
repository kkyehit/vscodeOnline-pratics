package com.vscode.req.onlinevscode.service;

import java.util.List;
import java.util.concurrent.TimeoutException;

import com.vscode.req.onlinevscode.model.VSCodeModel;
import com.vscode.req.onlinevscode.repository.VSCodeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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

    /** PREFIX */
    private static final String NAMESPACE_PREFIX="vscode-ns-";
    private static final String DEPLOYMENT_PREFIX="vscode-dp-";
    private static final String POD_PREFIX="vscode-pod-";
    private static final String SERVICE_PREFIX="vscode-svc-";
    private static final String INGRESS_PREFIX="vscode-ing-";

    /** availableReplicas */
    private static final Integer AVAILAVLE_REPLICAS=1;

    /** TIMEOUT */
    private static final Integer TIMEOUT=120;

    /**Domain */
    @Value("${custom.domain}")
    private String DOMAIN;

    @Autowired
    private KubernetesClient kubernetesClient;

    @Autowired
    private VSCodeRepository vscodeRepository;
    
    public String createVSCode(Long id) throws TimeoutException{

        VSCodeModel vscodeModel = new VSCodeModel();

        /* create namespace */
        Namespace ns = kubernetesClient
            .namespaces()
            .create(
                new NamespaceBuilder()
                    .withNewMetadata()
                        .withName(NAMESPACE_PREFIX+id)
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
                        .withName(DEPLOYMENT_PREFIX+id)
                        .addToLabels("app", "vscode")
                    .endMetadata()
                    .withNewSpec()
                        .withReplicas(AVAILAVLE_REPLICAS)
                        .withNewSelector()
                            .addToMatchLabels("app", "vscode")
                        .endSelector()
                        .withNewTemplate()
                            .withNewMetadata()
                                .addToLabels("app", "vscode")
                            .endMetadata()
                            .withNewSpec()
                                .addNewContainer()
                                    .withName(POD_PREFIX+id)
                                    .withImage("gitpod/openvscode-server")
                                    .addNewPort()
                                        .withContainerPort(3000)
                                    .endPort()
                                    .addNewEnv()
                                        .withName("CONNECTION_TOKEN")
                                        .withValue(""+id)
                                    .endEnv()
                                    // .addNewVolumeMount()
                                    //     .withMountPath("/home/workspace/")
                                    //     .withName("vscode-volume-"+id)
                                    // .endVolumeMount()
                                .endContainer()
                                .addNewVolume()
                                    .withName("vscode-volume-"+id)
                                    .withNewHostPath()
                                        .withPath("/data/"+id)
                                    .endHostPath()
                                .endVolume()
                            .endSpec()
                        .endTemplate()
                    .endSpec()
                    .build()
            );

        /* AVAILAVLE_REPLICAS 만큼 Pod가 띄어지기를 기다림 (TIMEOUT초 만큼) */
        int time = 0;
        while(dp.getStatus() == null 
            || dp.getStatus().getAvailableReplicas() == null 
            || dp.getStatus().getAvailableReplicas().intValue() != AVAILAVLE_REPLICAS){

            dp = kubernetesClient
                .apps()
                .deployments()
                .inNamespace(ns.getMetadata().getName())
                .withName(DEPLOYMENT_PREFIX+id)
                .get();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(++time == TIMEOUT) throw new TimeoutException();
        };
        /* Log 확인 */
        String log = kubernetesClient
            .apps()
            .deployments()
            .inNamespace(NAMESPACE_PREFIX+id)
            .withName(DEPLOYMENT_PREFIX+id)
            .getLog();

        /* tkn 추출 */
        System.out.println(log.indexOf("?tkn="));
        System.out.println(log.substring(log.indexOf("?tkn="),log.length()));
        System.out.println(log.substring(log.indexOf("?tkn="),log.length()).split("\n")[0]);
        vscodeModel.setTkn(log.substring(log.indexOf("?tkn="),log.length()).split("\n")[0]);

        /* create service */
        Service svc = kubernetesClient
            .services()
            .inNamespace(ns.getMetadata().getName())
            .create(
                new ServiceBuilder()
                    .withNewMetadata()
                        .withName(SERVICE_PREFIX+id)
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
                        .withName(INGRESS_PREFIX+id)
                        //.addToAnnotations("nginx.ingress.kubernetes.io/rewrite-target", "/$2")
                    .endMetadata()
                    .withNewSpec()
                        .withIngressClassName("nginx")
                        .addNewRule()
                            .withHost(id+"."+DOMAIN)
                            .withNewHttp()
                                .addNewPath()
                                    .withPath("/")
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

        vscodeModel.setURL(id+"."+DOMAIN);

        vscodeRepository.save(vscodeModel);
        return "";
    }

    public String getLogsVSCodePod(Long id){
         /* kubectl logs deployment/vscode-dp-{id} -n vscode-ns-{id} */
        return kubernetesClient
            .apps()
            .deployments()
            .inNamespace(NAMESPACE_PREFIX+id)
            .withName(DEPLOYMENT_PREFIX+id)
            .getLog();
    }

    public String deleteVSCode(Long id){
        /* delete namespace */
        kubernetesClient
            .namespaces()
            .withName(NAMESPACE_PREFIX+id)
            .delete();
        return "";
    }


    public List<VSCodeModel> getVSCodeList(){
        return vscodeRepository.findAll();
    }
}
