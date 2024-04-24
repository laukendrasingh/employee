package com.impressico.lnd.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${alert.service.url}")
    private String alertUrl;

    @GetMapping("/")
    public ResponseEntity<List<Employee>> getAll() {
        return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getAll(@PathVariable("id") Long id) {
        return new ResponseEntity<>(employeeRepository.findById(id).orElse(null), HttpStatus.OK);
    }

    @GetMapping("/employeeId/{employeeId}/alerts")
    public ResponseEntity<String> getEmployeeAlerts(@PathVariable("employeeId") Integer employeeId) {
        String result;
        String url = alertUrl + "/alerts/employeeId/" + employeeId;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            result = response.getBody();
        } else {
            result = "ERROR: Failed to fetch alerts from url: " + url;
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
