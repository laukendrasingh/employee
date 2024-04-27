### OVERVIEW:
It's a containerized microservice app developed with spring-boot and java-11, using H2 in-memory DB.

### CHECKOUT SOURCE-CODE
```
cd employee
git remote add origin https://github.com/laukendrasingh/employee.git
git fetch --all 
git checkout main
```

### RUN H2-DB FROM BROWSER
Click on link: http://localhost:8080/h2-console

### ENDPOINTS
* [Get all employees](http://localhost:8080/employees/)
* [Get employee by id: 1](http://localhost:8080/employees/1)
* [Get employee 1 alerts](http://localhost:8080/employees/employeeId/1/alerts)

### Use app-insights
1. Create Application Insights 'AI-Lnd-QA' then you will get 'Instrumentation Key' on Overview page
2. Add logback-spring.xml and define 'ApplicationInsightsAppender' -> It requires to push log message
3. Add 'azure.application-insights.instrumentation-key' in properties file
4. Add below dependency 'applicationinsights-spring-boot-starter' and 'applicationinsights-logging-logback'
5. Then run your app, you should start getting data in app-insights