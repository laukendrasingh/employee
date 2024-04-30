### OVERVIEW:
It's a containerized microservice app developed with spring-boot and java-11, using H2 in-memory DB.

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
4. Now you can access API with http://apim-lnd-qa.azure-api.net/employees/env

### ENABLE SUBSCRIPTION ON APIM:
1. Go to 'Subscription' then Add subscription and copy secondary key
2. Go to APIs -> Settings -> Subscription required -> enabled and then add header name: api-key which you need to provide during API call

### MULTI-STAGE RELEASE PIPELINE:
1. Add variable group for QA and PROD
2. Add variable with name SPRING_PROFILES_ACTIVE and provide environment specific value
3. Link variable group with stage to pick the spring profile
4. NOTE: we are able to set SPRING_PROFILES_ACTIVE and Build.BuildId variable only with 'inline configuration'

### CONFIG-MAP: wip

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
```
3. Run below command 
``` 
kubectl create -f ingress.yaml 
```
4. Run command: Kubectl get services to get ingress IP then you should be able to access your app using http://4.157.77.151:80/alert/alerts/

