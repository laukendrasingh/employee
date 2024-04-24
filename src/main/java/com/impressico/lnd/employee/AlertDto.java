package com.impressico.lnd.employee;

public class AlertDto {
    private Long id;
    private Integer employeeId;
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "id: " + id + ", employeeId: " + employeeId + ", message: " + message;
    }
}
