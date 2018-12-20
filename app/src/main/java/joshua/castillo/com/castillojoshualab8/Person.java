package joshua.castillo.com.castillojoshualab8;

public class Person {
    String name, age, gender;

    public Person() {
    }

    public Person(String name, String age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}
