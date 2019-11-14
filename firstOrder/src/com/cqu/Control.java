package com.cqu;

import com.cqu.JDBC.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Control {
    private RuleDB ruleDB;
    private User user;
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDatasource());

    public Control(RuleDB ruleDB,User user) {
        this.ruleDB = ruleDB;
        this.user = user;
        this.ruleDB.setJdbcTemplate(jdbcTemplate);
    }

    public User getUser() {
        return user;
    }

    public static  void showConclusion(){

    }
    /**
     * 通过名字获取该人所有的结论
     * @param name
     * @return
     */
    public ArrayList<Conclusion> getSuggestionsByName(String name) {
        String sql = "SELECT users.*,conclusion.conclusion FROM users LEFT OUTER JOIN conclusion ON users.`id` = conclusion.user_id WHERE users.name =?";
        List<Conclusion> conclusions = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Conclusion>(Conclusion.class),name);
        for (Conclusion conclusion : conclusions) {
            System.out.println(conclusion);
        }
        return (ArrayList<Conclusion>)conclusions;
    }

    /**
     * 进行推理
     */
    public void compara(){
        Node node = new Node();
        //1、获取bmi指数，判断体形
        double BMI = AIUtils.getBMI(user.getWeight(),user.getHeight());
        String bodyShape = "";
        if (BMI < 18.5) { bodyShape = "较瘦"; }
        else if (BMI < 23.9) { bodyShape = "体重合理"; }
        else if (BMI < 26.9) { bodyShape = "偏胖"; }
        else { bodyShape = "重度肥胖"; }
        node.set(bodyShape,true);
        //2、添加第一个节点：体形节点
        user.add(node);
        //3、开始比较
            //3.1获取规则库加载到数组中
        ArrayList<Rule> allRules = ruleDB.getAllRules();
            //3.2对每一条规则进行匹配
        while (true) {
            boolean update = false;//判断一次扫描匹配是否有修改
            int updataNum = 0;//一次扫描过程中user状态节点修改的次数
            for (Rule rule : allRules) {
                try {
                    boolean binGo = true;//匹配成功标志
                    int m = 0;
                    ArrayList<Node> primises = rule.getPrimises();
                    for (Node p:primises) {
                        if (user.stateContains(p)) {//user状态节点中包含有当前条件

                        } else {//user状态节点中不满足当前条件
                            binGo = false;
                            break;
                        }
                    }
                    if (binGo) {//匹配到了规则
                        // 3.3匹配到规则后检查规则是否为选择性结论
                        if (rule.getConclusion().contains("选择")&&rule.getConclusion().contains("或")) {
                            Node n = new Node();
                            n.set(rule.split(rule.getConclusion()));
                            if (user.add(n)) {//如果更新成功
                                update = true;
                                String conclusion = rule.getConclusion();

                                int a = conclusion.indexOf("择") + 1;
                                int b = conclusion.length();
                                String substring = conclusion.substring(a,b);
                                String[] choices = substring.split("或");//结论中给出的选项
                                StringBuilder choice = new StringBuilder("");
                                choice.append("请问您是要选择");
                                for (int j = 0; j < choices.length; j++) {
                                    if (j == choices.length - 1) {
                                        choice.append(choices[j]);
                                    } else {
                                        choice.append(choices[j] + "还是");
                                    }
                                }
                                choice.append("呢(选择第一项请输入1、选择第二项请输入2....以此类推,多项输入请按照格式 1--2--3 输入)");
                                ask(choice.toString());
                            }

                        } else {//非选择性结论直接添加
                            Node n = new Node();
                            n.set(rule.split(rule.getConclusion()));
                            update = user.add(n);
                        }
                        if (update) {
                            updataNum++;
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (updataNum == 0) {//扫描不再修改状态节点，则退出扫描
                break;
            }
        }

        //4、将状态节点中包含“适合”的结论存放到conclusion中
        user.collectConclusion();
        //5、将结论建表，存入数据库中
        user.storeInDB();
        //6、打印结论
        user.printConclusion();
    }

    /**
     * 询问指定的问题
     * @param question
     * @return
     */
    public boolean ask(String question){
        boolean inputR;//记录咨询者每一次回答是否正常
        boolean[] feedback =new boolean[2];//
        while (true) {
            System.out.println(question);
            Scanner scanner = new Scanner(System.in);
            String feedcack = scanner.nextLine();
            inputR = true;
            int a = 0;
            int b = 0;
            int c = 0;
            /*
            系统询问的方式有两种，一种为：“您是否xxx呢”
            另外一种为：“请问您是要选择xxx还是xxx呢”
             */
            if (question.contains("您是否")) {//“您是否xxx呢”
                a = question.indexOf("否") + 1;
                b = question.indexOf("呢");
                String str = question.substring(a,b);
                if (feedcack.equals("y")) {
                    Node node  = new Node();
                    node.set(str,true);
                    user.add(node);
                } else if (feedcack.equals("n")) {
                    Node node  = new Node();
                    node.set(str,false);
                    user.add(node);
                } else {
                    inputR = false;
                    try {
                        throw new Exception("输入有误！");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("您输入有误，请重新输入！");
                    }
                }

            } else if (question.contains("您是要选择")) {//“您是要选择xxx还是xxx还是xxx还是xxx呢”
                a = question.indexOf("择") + 1;
                b = question.indexOf("呢");
                String choice = question.substring(a,b);
                String[] choices = choice.split("还是");
                String[] mulChoice = feedcack.split("--");//存储的用户输入的选项
                int[] feedbackNum = new int[mulChoice.length];
                for (int i = 0; i < mulChoice.length; i++) {
                    feedbackNum[i] = Integer.parseInt(mulChoice[i]);
                    if (feedbackNum[i] <= choices.length && feedbackNum[i] > 0) {
                        Node node  = new Node();
                        node.set(choices[feedbackNum[i]-1],true);
                        user.add(node);
                    } else  {//输入了指定数字以外的内容
                        inputR = ask("是否觉得当前选项不满足您的要求呢（请输入y/n，如果您是输入错误，请输入n）");
                        break;
                    }

                }

            } else if (question.contains("觉得当前选项不满足您的要求")) {
                if (feedcack.equals("y")) {
                    System.out.println("非常抱歉，keep健身教练目前知道的还比较少，暂时不能就当前问题给您足够的建议呢");
                } else if (feedcack.equals("n")) {
                    System.out.println("接下来我再问您一遍哦，这一次您可要看清楚了再进行输入哦");
                    return false;
                } else {//输入了非“y/n”以外的字母
                    System.out.println("您输入有误，请重新输入！");
                    return false;
                }
            }
            if (inputR) {//询问后咨询者返回的值在正常范围内才跳出循环，否则重复询问
                break;
            }
        }

        return inputR;
    }

    /**
     * 打印用户状态节点
     */
    public void showUser(){
        for (Node node :user.getState() ) {
            System.out.println(node);
        }
    }
    /**
     * 添加规则
     * @param conclusion
     * @param primise
     */
    public void addRule(String conclusion,String ...primise){
        try {
            ruleDB.add(conclusion,primise);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除规则
     * @param conclusion
     * @param primise
     */
    public void deleteRule(String conclusion,String ...primise){
        try {
            ruleDB.delete(conclusion,primise);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询规则
     * @param primise
     */
    public ArrayList<Rule> getRule(String ...primise){
        List<Rule> rules = null;
        try {
            rules = ruleDB.getRule(primise);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  (ArrayList<Rule>)rules;
    }

    /**
     * 获取规则库大小
     * @return
     */
    public int getSize(){
        return ruleDB.getSize();
    }

    /**
     * 获取规则库所有规则
     * @return
     */
    public ArrayList<Rule> getAllRules(){
        return ruleDB.getAllRules();
    }

    /**
     * 显示规则库所有规则
     */
    public void showRuleDB(){
        ArrayList<Rule> rules = getAllRules();
        for (Rule rule : rules) {
            System.out.println(rule);
        }
    }
}
