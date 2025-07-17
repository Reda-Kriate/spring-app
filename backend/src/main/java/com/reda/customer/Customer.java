package com.reda.customer;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "customer_email_unique",
                        columnNames = "email"
                )
        }
)
public class Customer{

    @Id
    @SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String gender;

    public Customer(){}

    public Customer(int id, String name, int age, String email , String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.gender = gender;
    }
    public Customer( String name, int age, String email, String gender) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.gender = gender;
    }


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(age, customer.age) && Objects.equals(email, customer.email) && Objects.equals(gender, customer.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, gender);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
