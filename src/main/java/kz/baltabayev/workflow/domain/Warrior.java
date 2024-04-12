package kz.baltabayev.workflow.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Warrior implements Serializable {
    private String name;
    private String gender;
    private Integer hp;
    private Boolean isAlive;

    private static final long serialVersionUID = 1L;

    public Warrior() {
    }

    public Warrior(Integer hp, Boolean isAlive, String name, String gender) {
        this.hp = hp;
        this.isAlive = isAlive;
        this.name = name;
        this.gender = gender;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
