package vb.practice.rest.spring.boot.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;

/**
 * Model class to hold Target definition.
 */
@Entity
public class Target {

    @Column
    @NonNull
    private final String name;
    @Column
    @NonNull
    private final Integer age;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    @Lob
    @Nullable
    private byte[] information;

    /**
     * constructor
     *
     * @param name name of the target, never null or empty
     * @param age  age of the target, never null
     */
    public Target(String name, Integer age) {
        if (name == null || name.trim().isEmpty() || age == null) {
            throw new NullPointerException("name or age attribute cannot be null");
        }
        this.name = name;
        this.age = age;
    }

    //getters and setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public byte[] getInformation() {
        return information;
    }

    public void setInformation(byte[] information) {
        this.information = information;
    }
}
