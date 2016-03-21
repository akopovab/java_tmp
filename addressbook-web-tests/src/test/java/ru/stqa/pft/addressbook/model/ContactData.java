package ru.stqa.pft.addressbook.model;

public class ContactData {
    private int id;
    private  String firstName;
    private  String lastName;
    private  String address;
    private  String homePhone;
    private  String mobilePhone;
    private  String workPhone;
    private  String phoneNumber;
    private  String email;
    private String group;



    public ContactData withId(int id) {
        this.id=id;
        return this;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public ContactData withHomePhone(String homePhone){

        this.homePhone=homePhone;
        return this;
    }
    public String getMobilePhone() {
        return mobilePhone;
    }

    public ContactData withMobilePhone(String mobilePhone){

        this.mobilePhone=mobilePhone;
        return this;
    }


    public String getWorkPhone() {
        return workPhone;
    }

    public ContactData withWorkPhone(String workPhone){

        this.workPhone=workPhone;
        return this;
    }

    public ContactData(int id, String firstName, String lastName, String address, String phoneNumber,

                       String email, String group) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.group = group;
    }


    public ContactData( String firstName, String lastName, String address, String phoneNumber,
                        String email, String group) {
        this.id=Integer.MAX_VALUE; // this.id=0; может быть любое значение
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.group = group;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getEmail() {
        return email;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (id != that.id) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        return lastName != null ? lastName.equals(that.lastName) : that.lastName == null;

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    public int    getId() {
        return id;
    }

    public void setId(int id) {
          this.id = id;
    }


    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }


}
