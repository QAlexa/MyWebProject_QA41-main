package model;

import java.io.*;
import java.util.Objects;

public class Contact {

    private String name;
    private String LastName;
    private String phone;
    private String email;
    private String address;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Contact(String name, String lastName, String phone, String email, String address, String description) {
        this.name = name;
        LastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
    }

    public Contact() {
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", LastName='" + LastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) && Objects.equals(LastName, contact.LastName) && Objects.equals(phone, contact.phone) && Objects.equals(email, contact.email) && Objects.equals(address, contact.address) && Objects.equals(description, contact.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, LastName, phone, email, address, description);
    }

    public static  void serializeContact(Contact contact, String filename) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename));
        outputStream.writeObject(contact);
    }

    public static Contact deserializeContact(String fileName) throws IOException {
        try (
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName));) {
            return (Contact) inputStream.readObject();
        } catch ( IOException| ClassNotFoundException e) {
            System.err.println("Error deserializing contact");
            return null;
            //throw new RuntimeException(e);
        }


    }
}
