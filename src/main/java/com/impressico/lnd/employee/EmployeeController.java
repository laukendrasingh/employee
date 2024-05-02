package com.impressico.lnd.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private Logger log = LoggerFactory.getLogger(EmployeeApplication.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${alert.service.url}")
    private String alertUrl;
    @Value("${spring.profiles.active}")
    private String env;
    @Value("${env.specific.variable}")
    private String envVariable;

    @PostMapping("/add")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }
    @GetMapping("/env")
    public ResponseEntity<String> findEnv() {
        log.info("INVOKE: find env: {} -> {}", env, envVariable);
        return new ResponseEntity<>(env + " -> " + envVariable, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Employee>> findAllEmployee() {
        log.info("INVOKE: find all employee");
        return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable("id") Long id) {
        log.info("INVOKE: find employee by id: {}", id);
        return new ResponseEntity<>(employeeRepository.findById(id).orElse(null), HttpStatus.OK);
    }

    @GetMapping("/employeeId/{employeeId}/alerts")
    public ResponseEntity<String> findEmployeeAlerts(@PathVariable("employeeId") Integer employeeId) {
        log.info("INVOKE: find alerts of employee: {}", employeeId);
        String url = alertUrl + "/alerts/employeeId/" + employeeId;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } catch (Exception ex) {
            log.error("ERROR: failed to fetch alerts from url: {}", url, ex);
            return new ResponseEntity<>("ERROR: failed to fetch alerts from url: " + url, HttpStatus.OK);
        }
    }
}
