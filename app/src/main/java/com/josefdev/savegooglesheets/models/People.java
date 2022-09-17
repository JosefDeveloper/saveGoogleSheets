package com.josefdev.savegooglesheets.models;

public class People {
    String id;
    String name;
    String surname;
    String age;

    public People(String id, String name, String surname, String age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAge() {
        return age;
    }
}
