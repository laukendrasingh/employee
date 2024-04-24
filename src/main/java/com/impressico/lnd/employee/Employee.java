package com.impressico.lnd.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "employee")
public class Employee {
    @Id
    private Long id;
    @Column
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id: " + this.id + ", name: " + this.name;
    }
}
