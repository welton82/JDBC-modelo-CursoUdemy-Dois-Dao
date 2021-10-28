package model.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Seller implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Date birthdate;
    private Double baseSalary;
    private String email;
    private Department department;

    public Seller() {
    }
//OBS: recebí uma data como string apenas transformei em util date e depois date recebe util date
    //Não foi necessário aterar para sql o retorno date nem fazer nenhuma formatação da data que é tipo date
    public Seller(Integer id, String name, String birthdate, Double baseSalary, Department department, String email) throws ParseException {
        this.id = id;
        this.name = name;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date dt = sdf.parse(birthdate);

        this.birthdate = dt;
        this.baseSalary = baseSalary;
        this.department = department;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Department getDepartmen() {
        return department;
    }

    public void setDepartmen(Department department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seller)) return false;
        Seller seller = (Seller) o;
        return getId().equals(seller.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

                s.append("id: " + id +" - Name: " + name + " - Birthdate: " + new SimpleDateFormat("dd/MM/yyyy").format(birthdate));
                s.append("\nBaseSalary: " + String.format("%.2f", baseSalary) + " - Email: " + email + "\nDepartment: " + department );
               return s.toString();
    }
}
