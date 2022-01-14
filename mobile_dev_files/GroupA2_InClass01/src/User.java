/*
    InClass01
    User.java
    Clifton Thorne
    Riley Halbert
 */

import java.util.Objects;

public class User {
    String firstname, lastname, email, gender, city, state;
    int age;

    public User(String[] arr) {
        firstname = arr[0];
        lastname =  arr[1];
        age = Integer.parseInt(arr[2]);
        email = arr[3];
        gender = arr[4];
        city = arr[5];
        state = arr[6];
    }

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", age=" + age + "\n" +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && firstname.equals(user.firstname) && lastname.equals(user.lastname) && email.equals(user.email) && gender.equals(user.gender) && city.equals(user.city) && state.equals(user.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, email, gender, city, state, age);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
