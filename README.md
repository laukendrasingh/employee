# EMPLOYEE MICRO-SERVICE

### CHECKOUT SOURCE-CODE
```
cd employee
git remote add origin https://git.impressicocrm.com/ezcorp/lnd/employee.git
git fetch --all 
git checkout main
```

### JAVA
```
open-jdk:17
```

### BUILD APP
```
mvn clean install
```

### RUN H2-DB FROM BROWSER
Click on link: http://localhost:8080/h2-console

### ENDPOINTS
* [Get all employees](http://localhost:8080/employees/)
* [Get employee by id: 1](http://localhost:8080/employees/1)
* [Get employee 1 alerts](http://localhost:8080/employees/employeeId/1/alerts)