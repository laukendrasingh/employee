### OVERVIEW:
It's a containerized microservice app developed with spring-boot and java-11, using H2 in-memory DB. 

SEE: https://laukendrasingh.github.io/employee/

### CHECKOUT SOURCE-CODE:
```
cd employee
git remote add origin https://github.com/laukendrasingh/employee.git
git fetch --all 
git checkout main
```

### RUN H2-DB FROM BROWSER:
Click on link: http://localhost:8080/h2-console

### ENDPOINTS:
* [Get all employees](http://localhost:8080/employees/)
* [Get employee by id: 1](http://localhost:8080/employees/1)
* [Get employee 1 alerts](http://localhost:8080/employees/employeeId/1/alerts)

### BUILD & RUN DOCKER IMAGE AT LOCAL:
1. Install docker
2. Start docker using docker desktop
3. Got to IntelliJ project and set docker path:  export PATH="/Applications/Docker.app/Contents/Resources/bin:$PATH"
4. Build project: mvn clean install
5. Build docker image:  sudo docker build -t employee .
6. Run docker image: docker run -p 8080:8080 employee
7. Run from browser: http://localhost:8080/employees/

### CREATE RESOURCES AT AZURE PORTAL:
1. Resource group: RG-LnD-QA
2. ACR: To store docker images: ACRLnDQA
3. AKS: To run images in cluster: AKS-LnD-QA

### CREATE BUILD PIPELINE ON AZURE DEV PORTAL:
1. Git source: To checkout source code
2. Agent job: Ubuntu Latest. It's a VM on which thse steps executes
3. Maven pom.xml: To build java code
4. Build an image: with azure agent-Ubuntu Latest
5. Push an image: Into ACR
6. Push artifact
7. Triggers: Enable continuous integration on branch 'main'

### CREATE RELEASE PIPELINE ON AZURE:
1. Select artifact
2. Agent job: Ubuntu Latest
3. kubectl login: Login into AKS
4. kubectl apply: Fetch image from ACR and run on AKS

### APP-INSIGHTS:
1. Create Application Insights 'AI-Lnd-QA' then you will get 'Instrumentation Key' on Overview page
2. Add logback-spring.xml and define 'ApplicationInsightsAppender' -> It requires to push log message
3. Add 'azure.application-insights.instrumentation-key' in properties file
4. Add below dependency 'applicationinsights-spring-boot-starter' and 'applicationinsights-logging-logback'
5. Then run your app, you should start getting data in app-insights

### APIM:
1. Create APIM resource with name: APIM-Lnd-QA
2. Go to APIs and then 'Add API' in which you need to provide LoadBalancer public URL
3. You can also upload swagger.json rather than defining manual operations
4. **Employee end-points** 
5. http://apim-lnd-qa.azure-api.net/employees/employeeId/1/alerts?api-key=5b7c53f5e4504c8eafa59cb0e057cb48
6. http://apim-lnd-qa.azure-api.net/employees/1?api-key=5b7c53f5e4504c8eafa59cb0e057cb48
7. http://apim-lnd-qa.azure-api.net/employees/?api-key=5b7c53f5e4504c8eafa59cb0e057cb48
6. http://apim-lnd-qa.azure-api.net/employees/env?api-key=5b7c53f5e4504c8eafa59cb0e057cb48
7. ADD EMPLOYEE: curl --location 'http://52.234.243.1:80/employees/add' \
--header 'Content-Type: application/json' \
--data '{
    "id":5,
    "name":"Shivendra"
}'
6. **Alert end-points**
7. http://apim-lnd-qa.azure-api.net/alerts/1
8. http://apim-lnd-qa.azure-api.net/alerts/

### ENABLE SUBSCRIPTION ON APIM:
1. Go to 'Subscription' then Add subscription and copy secondary key
2. Go to APIs -> Settings -> Subscription required -> enabled and then add header name: api-key which you need to provide during API call

### MULTI-STAGE RELEASE PIPELINE:
1. Add variable group for QA and PROD
2. Add variable with name SPRING_PROFILES_ACTIVE and provide environment specific value
3. Link variable group with stage to pick the spring profile
4. NOTE: we are able to set SPRING_PROFILES_ACTIVE and Build.BuildId variable only with 'inline configuration'

### INGRESS:
1. Create ingress controller by running command
```
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.10.1/deploy/static/provider/cloud/deploy.yaml
```
2. Create ingress resource file 'ingress.yaml'
```
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: msv-ingress
  annotations:
    kubernetes.io/ingress.class: nginx
    nginx.ingress.kubernetes.io/use-regex: "true"
    nginx.ingress.kubernetes.io/rewrite-target: /$2
spec:
  ingressClassName: nginx
  rules:
    - http:
        paths:
          - backend:
              service:
                name: alert-clusterip
                port:
                  number: 80
            path: /alert(/|$)(.*)
            pathType: ImplementationSpecific
          - backend:
              service:
                name: employee-clusterip
                port:
                  number: 80
            path: /employee(/|$)(.*)
            pathType: ImplementationSpecific
```
3. Run below command 
``` 
kubectl create -f ingress.yaml --> First time while creating

kubectl apply -f ingress.yaml  --> To update existing ingress.yaml

kubectl describe ing           --> To see the ingress.yaml changes applied or not
```
4. Got to AKS -> Kubernetes resources -> Services and ingresses -> and get the External IP of nginx then you should be able to access your app using 
http://4.157.77.151:80/alert/alerts/ and http://4.157.77.151/employee/employees/

### AZURE FUNCTIONS:
1. Got to azure -> Function App -> Create -> Runtime Stack: .NET, Version: 6 (LTS) in-process model, Operating System: Windows
2. Got to your created Function App -> Overview -> Create -> Select a Template -> and follow the steps to create and test run the function

### CONFIG-MAP:
1. Create a file alert-config-map.yml in alert microservice inside resource folder
2. Put the data as key & value pair which you want in config-map
3. Define the env in azure-deployment.yml file for your keys which you want to read from config-map
4. Push your changes and run the build pipeline
5. Execute below commands then run release pipeline and then you should be able to access config-map values from API: http://172.212.127.98:80/alerts/configMap
```
kubectl get cm                          —> Get existing config maps
Kubectl delete cm alert-config-map      —> Delete config map by name
kubectl apply -f alert-config-map.yml   —> Create config map
kubectl describe cm alert-config-map    —> To see values in config map
```

### SQL DB: Done by Panwan

### KEY-VAULT: wip Rishu