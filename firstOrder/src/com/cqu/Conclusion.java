package com.cqu;

public class Conclusion {
    private int id;
    private String name;
    private  boolean sex;
    private int age;
    private int height;
    private int weight;
    private String conclusion;

    public int getId() {
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

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    @Override
    public String toString() {
        String sex = "";
        if (this.sex = true) {
            sex = "男";
        } else {
            sex = "女";
        }
        return "id=" + id +"\t"+
                name + '\t' +
                sex +"\t"+
                age +"\t"+
                height +"\t"+
                weight +"\t"+
                conclusion ;
    }
}
