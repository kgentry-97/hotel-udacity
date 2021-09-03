package com.kgentry.model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private final String emailPattern = "^(.+)@(.+).(.+)$";

    public Customer(String firstName, String lastName, String email) {
        emailCheck(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    private void emailCheck(String email) {
        Pattern pattern = Pattern.compile(emailPattern);
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("invalid email");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("Customer name: %s %s email: %s", getFirstName(), getLastName(), getEmail());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }
}
