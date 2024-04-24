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
