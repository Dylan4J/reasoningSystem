package com.cqu;

import com.cqu.JDBC.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private boolean sex;
    private int age;
    private int height;
    private int weight;
    private ArrayList<Node> state;
    private ArrayList<String> conclusion;

    public static final boolean male = true;
    public static final boolean female = false;

    public User() {
        this.state = new ArrayList<>();
        this.conclusion = new ArrayList<>();
    }

    /**
     * 将基础信息与结论加载进数据库中保存
     * @return
     */
    public boolean storeInDB(){
        Connection connection = null;
        PreparedStatement preps = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            connection.setTransactionIsolation(8);
            connection.setAutoCommit(false);//开启事务
            preps = connection.prepareStatement("INSERT INTO users (name,sex,age,height,weight) VALUES (?,?,?,?,?)");
            preps.setString(1,this.name);
            preps.setBoolean(2,this.sex);
            preps.setInt(3,this.age);
            preps.setInt(4,this.height);
            preps.setInt(5,this.weight);
            preps.executeUpdate();

            preps = connection.prepareStatement("select @@identity ");
            resultSet = preps.executeQuery();
            resultSet.next();
            int key = resultSet.getInt("@@identity");

            for (String con :
                    conclusion) {
                preps = connection.prepareStatement("INSERT INTO conclusion (user_id,name,conclusion) VALUES (?,?,?)");
                preps.setInt(1,key);
                preps.setString(2,this.name);
                preps.setString(3,con);
                preps.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(resultSet,preps,connection);
        }
        return true;
    }

    /**
     * 将布尔值男女转换成字符串男女
     * @param sex
     * @return
     */
    public static String sexChange(boolean sex){
        if (sex == male) {
            return "男";
        } else {
            return "女";
        }
    }

    /**
     * 打印一次推理过后的结论
     */
    public void printConclusion(){
        System.out.println("-----------------------------");
        System.out.println("姓名："+this.name+"   "
                + "性别："+sexChange(this.sex)+"   "
                +"年龄："+this.age+"   "
                +"体重："+this.weight+"KG"+"   "
                +"身高："+this.height+"cm");
        System.out.println("-----------------------------");
        for (String con :
                conclusion) {
            System.out.println((conclusion.indexOf(con)+1)+"、"+con);
        }
    }
    /**
     * 收集状态列表中含有“适合”的结论
     */
    public void collectConclusion(){
        for (Node node : state) {
            if (node.parameter.contains("适合")){

                if (node.paraValue == true) {
                    conclusion.add(node.parameter);
                } else {
                    conclusion.add("不建议"+node.parameter);
                }
            }
        }
    }

    /**
     * user状态节点中是否含有指定节点
     * @param node
     * @return
     */
    public boolean stateContains(Node node){
        for (Node node1 : state) {
            if (node.equals(node1)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 添加一个节点
     * @param node
     */
    public boolean add(Node node) {
        //1、添加前先判断是否有节点名重复
        boolean found = false;
        boolean update = false;
        for (int i = 0; i < state.size(); i++) {
            if (node.equalsParameter(state.get(i))) {
                found = true;
                //2、判断参数值是否重复
                if (node.equalsValue(state.get(i))) {
                    break;
                } else {
                    update = true;
                    state.set(i,node);
                }
            }
        }
        if (!found) {
            update =true;
            state.add(node);
        }
        return update;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public ArrayList<Node> getState() {
        return state;
    }

    public ArrayList<String> getConclusion() {
        return conclusion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


}
