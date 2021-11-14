package com.vscode.req.onlinevscode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

@Configuration
public class KuberentesConfig {
    
    @Bean
    public KubernetesClient kubernetesClient(){
        return new DefaultKubernetesClient();
    }
}
