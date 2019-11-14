package com.cqu;

import com.cqu.JDBC.JDBCConstant;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

public class Rule {
    private Integer id;
    private Date insert_time;
    private Boolean mul_choice;
    private Boolean final_conclusion;
    private String conclusion;
    private String primise1;
    private String primise2;
    private String primise3;
    private String primise4;
    private String primise5;
    private String primise6;
    private String primise7;
    private String primise8;
    private String primise9;
    private String primise10;
    private String primise11;
    private String primise12;
    private String primise13;
    private String primise14;
    private String primise15;



    /**
     * 获取一条规则的前提
     * @param
     * @return
     */
    @Test
    public ArrayList<Node> getPrimises() throws IllegalAccessException {
        Field[] fields = this.getClass().getDeclaredFields();
        ArrayList<Node> primises = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().contains("primise")&&fields[i].get(this)!=null) {
                try {
                    Node node = new Node();
                    node.set(split((String) (fields[i].get(this))));
                    primises.add(node);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return primises;

    }

    /**
     * 分离一个条件或结论中的参数名与参数值
     * @param
     * @return
     */
    @Test
    public static Object[] split(String str){
        Object[] objects = new Object[2];
        boolean value = false;
        String para = "";
        if (str.contains("true")) {
            value = Boolean.parseBoolean(str.substring(str.indexOf("t")));
            para = str.substring(0, str.indexOf("t"));
        } else if (str.contains("false")) {
            value = Boolean.parseBoolean(str.substring(str.indexOf("f")));
            para = str.substring(0, str.indexOf("f"));
        } else {
            value = true;
            para = str;
        }
        objects[0] = para;
        objects[1] = value;
        return objects;
    }

    @Override
    public String toString() {
        StringBuilder prim = new StringBuilder();
        String[] primises = new String[]{
                 primise1,primise2,primise3
                ,primise4,primise5,primise6
                ,primise7,primise8,primise9
                ,primise10,primise11,primise12
                ,primise13,primise14,primise15
                };
        for (int i = 0; i < JDBCConstant.primiseNum; i++) {
            if (primises[i] == null) {
                break;
            } else if (i == 0) {
                prim.append(primises[i]);
            } else {
                prim.append("and"+primises[i]);
            }
        }
        return "id=" + id +
                ", insert_time=" + insert_time +
                ", mul_choice=" + mul_choice +
                ", final_conclusion=" + final_conclusion +
                "  " +
                prim.toString() +
                "--->"+
                conclusion
                ;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(Date insert_time) {
        this.insert_time = insert_time;
    }

    public Boolean getMul_choice() {
        return mul_choice;
    }

    public void setMul_choice(Boolean mul_choice) {
        this.mul_choice = mul_choice;
    }

    public Boolean getFinal_conclusion() {
        return final_conclusion;
    }

    public void setFinal_conclusion(Boolean final_conclusion) {
        this.final_conclusion = final_conclusion;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getPrimise1() {
        return primise1;
    }

    public void setPrimise1(String primise1) {
        this.primise1 = primise1;
    }

    public String getPrimise2() {
        return primise2;
    }

    public void setPrimise2(String primise2) {
        this.primise2 = primise2;
    }

    public String getPrimise3() {
        return primise3;
    }

    public void setPrimise3(String primise3) {
        this.primise3 = primise3;
    }

    public String getPrimise4() {
        return primise4;
    }

    public void setPrimise4(String primise4) {
        this.primise4 = primise4;
    }

    public String getPrimise5() {
        return primise5;
    }

    public void setPrimise5(String primise5) {
        this.primise5 = primise5;
    }

    public String getPrimise6() {
        return primise6;
    }

    public void setPrimise6(String primise6) {
        this.primise6 = primise6;
    }

    public String getPrimise7() {
        return primise7;
    }

    public void setPrimise7(String primise7) {
        this.primise7 = primise7;
    }

    public String getPrimise8() {
        return primise8;
    }

    public void setPrimise8(String primise8) {
        this.primise8 = primise8;
    }

    public String getPrimise9() {
        return primise9;
    }

    public void setPrimise9(String primise9) {
        this.primise9 = primise9;
    }

    public String getPrimise10() {
        return primise10;
    }

    public void setPrimise10(String primise10) {
        this.primise10 = primise10;
    }

    public String getPrimise11() {
        return primise11;
    }

    public void setPrimise11(String primise11) {
        this.primise11 = primise11;
    }

    public String getPrimise12() {
        return primise12;
    }

    public void setPrimise12(String primise12) {
        this.primise12 = primise12;
    }

    public String getPrimise13() {
        return primise13;
    }

    public void setPrimise13(String primise13) {
        this.primise13 = primise13;
    }

    public String getPrimise14() {
        return primise14;
    }

    public void setPrimise14(String primise14) {
        this.primise14 = primise14;
    }

    public String getPrimise15() {
        return primise15;
    }

    public void setPrimise15(String primise15) {
        this.primise15 = primise15;
    }
}
