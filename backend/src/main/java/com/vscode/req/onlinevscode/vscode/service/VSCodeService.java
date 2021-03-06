package com.vscode.req.onlinevscode.vscode.service;

import java.util.List;
import java.util.concurrent.TimeoutException;

import com.vscode.req.onlinevscode.utill.KubernetesResourceManagerUtill;
import com.vscode.req.onlinevscode.vscode.model.VSCodeModel;
import com.vscode.req.onlinevscode.vscode.repository.VSCodeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@org.springframework.stereotype.Service
public class VSCodeService {

    /** PREFIX */
    private static final String NAMESPACE_PREFIX="vscode-ns-";
    private static final String DEPLOYMENT_PREFIX="vscode-dp-";
    private static final String POD_PREFIX="vscode-pod-";
    private static final String SERVICE_PREFIX="vscode-svc-";
    private static final String INGRESS_PREFIX="vscode-ing-";
    private static final String VOLUME_PREFIX="vscode-volume-";
    private static final String VOLUME_PATH_PREFIX="/data/";

    /** availableReplicas */
    private static final Integer AVAILAVLE_REPLICAS=1;

    private static final String IMAGE_NAME="gitpod/openvscode-server";

    /** TIMEOUT */
    private static final Integer TIMEOUT=120;

    /**Domain */
    @Value("${custom.domain}")
    private String DOMAIN;

    @Autowired
    private KubernetesResourceManagerUtill kubernetesResourceManagerUtill;

    @Autowired
    private VSCodeRepository vscodeRepository;
    
    public String createVSCode(Long id) throws TimeoutException{

        VSCodeModel vscodeModel = new VSCodeModel();

        String namespaceName=NAMESPACE_PREFIX+id;
        String deploymentName=DEPLOYMENT_PREFIX+id;
        String podName=POD_PREFIX+id;
        String volumeName=VOLUME_PREFIX+id;
        String volumePath=VOLUME_PATH_PREFIX+id;
        String serviceName=SERVICE_PREFIX+id;
        String ingressName=INGRESS_PREFIX+id;
        String hostName=id+"."+DOMAIN;
        String token="token";

        /* create namespace */
        kubernetesResourceManagerUtill.createNamespace(namespaceName);
        /* create deployment */
        kubernetesResourceManagerUtill.createDeployment(deploymentName, namespaceName, podName, AVAILAVLE_REPLICAS, IMAGE_NAME, token, volumeName, volumePath);
        /* AVAILAVLE_REPLICAS ?????? Pod??? ??????????????? ????????? (TIMEOUT??? ??????) */
        kubernetesResourceManagerUtill.waitDeploymentIsValid(deploymentName, namespaceName, AVAILAVLE_REPLICAS, TIMEOUT);
        
        /* Log ?????? */
        String log = kubernetesResourceManagerUtill.getDeploymentLogs(deploymentName, namespaceName);

        /* tkn ?????? */
        vscodeModel.setTkn(log.substring(log.indexOf("?tkn="),log.length()).split("\n")[0]);

        /* create service */
        kubernetesResourceManagerUtill.createService(serviceName, namespaceName);

        /* create ingress */
        kubernetesResourceManagerUtill.createIngress(ingressName, namespaceName, serviceName, hostName);
        
        /* domain ?????? */
        vscodeModel.setURL(id+"."+DOMAIN);

        /* db??? ?????? */
        vscodeRepository.save(vscodeModel);
        return "";
    }

    public String getLogsVSCodePod(Long id){
        String namespaceName=NAMESPACE_PREFIX+id;
        String deploymentName=DEPLOYMENT_PREFIX+id;

         /* kubectl logs deployment/vscode-dp-{id} -n vscode-ns-{id} */
        return kubernetesResourceManagerUtill.getDeploymentLogs(deploymentName, namespaceName);
    }

    public String deleteVSCode(Long id){
        String namespaceName=NAMESPACE_PREFIX+id;

        /* delete namespace */
        return kubernetesResourceManagerUtill.deleteNamespace(namespaceName);
    }


    public List<VSCodeModel> getVSCodeList(){
        return vscodeRepository.findAll();
    }
}
