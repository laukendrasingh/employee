package com.impressico.lnd.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
//    @Autowired
//    private AlertClient alertClient;

    @GetMapping("/")
    public ResponseEntity<List<Employee>> getAll() {
        return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getAll(@PathVariable("id") Long id) {
        return new ResponseEntity<>(employeeRepository.findById(id).orElse(null), HttpStatus.OK);
    }

    /*@GetMapping("/employeeId/{employeeId}/alerts")
    public ResponseEntity<List<AlertDto>> getEmployeeAlerts(@PathVariable("employeeId") Integer employeeId) {
        return new ResponseEntity<>(alertClient.getEmployeeAlerts(employeeId), HttpStatus.OK);
    }*/
}
