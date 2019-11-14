package com.cqu;

import com.cqu.JDBC.JDBCConstant;
import com.cqu.JDBC.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RuleDB {
    private JdbcTemplate jdbcTemplate;


    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 获取规则库规则数量
     * @return
     */
    public int getSize(){
        String sql1 = "select count(*) from rules";
        int size = jdbcTemplate.queryForObject(sql1, int.class);
        return size;
    }

    /**
     * 增加规则
     * @param conclusion
     * @param primise
     */
    public void add(String conclusion,String ...primise) throws Exception {
        int lenth = 1+primise.length+ JDBCConstant.conNum+JDBCConstant.flagNum;
        Object[] para = new Object[lenth];//每次插入数据库的参数列表

        boolean b1;//检查结论中是否含有“适合”
        Boolean b2;//检查结论中是否含有“选择”
        StringBuilder stringBuilder1 = new StringBuilder();//拼接字符串primise1，primise2，...
        StringBuilder stringBuilder2 = new StringBuilder();

        if (conclusion.contains("适合")) { b1 = true; } else { b1 = false; }
        if (conclusion.contains("选择")) { b2 = true; } else { b2 = false; }

        para[0] = null;//id
        para[1] = null;//insert_time
        para[2] = b2;//mul_choice
        para[3] = b1;//fianl_choice
        para[4] = conclusion;
        System.arraycopy(primise,0,para,1+JDBCConstant.flagNum+JDBCConstant.conNum,primise.length);

        for (int i = 0; i < primise.length; i++) {
            stringBuilder1.append(",primise" + (i + 1));
        }
        for (int i = 0; i < lenth; i++) {
            if (i == lenth - 1) {
                stringBuilder2.append("?");
            }else {
                stringBuilder2.append("?,");
            }
        }

        String sql = "insert into rules (" +
                "id" +
                ",insert_time" +
                ",mul_choice" +
                ",final_conclusion" +
                ",conclusion"+
                stringBuilder1.toString()+ ") values ("+ stringBuilder2.toString()+ ")";

        if (getSize() != 0) {//如果数据库不为空，则需要进行重复性检查
            String locate = getLocate(conclusion,primise);//根据结论和条件生成唯一定位字符串
            Object[] paras = getLocParaObjs(conclusion,primise);//根据给定的结论和前提生成对应的object[]数组
            String sql2 = "select * from rules "+locate;
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql2, paras);
            if (maps.size() == 0) {//未查到相同的数据
                int i = jdbcTemplate.update(sql, para);
                if (i == 0) {
                    System.out.println("数据库添加失败");
                } else {
                    System.out.println("数据库添加成功");
                }
            } else {
                    throw new Exception("知识库已存在此条规则");
            }
        } else {//数据库为空，直接添加
            int i = jdbcTemplate.update(sql,para);
            if (i == 0) {
                System.out.println("数据库添加失败");
            } else {
                System.out.println("数据库添加成功");
            }
        }
    }

    public void delete(String conclusion,String ...primise) throws Exception {
        String locate = getLocate(conclusion,primise);//根据结论和条件生成唯一定位字符串
        Object[] paras = getLocParaObjs(conclusion,primise);//根据给定的结论和前提生成对应的object[]数组
        String sql = "delete from rules "+locate;
        int i = jdbcTemplate.update(sql,paras);
        if (i == 0) {
            throw new Exception("数据库删除失败");
        } else {
            System.out.println("数据库删除成功");
        }
    }

    /**
     * 查询规则
     * @param primise
     * @return
     */
    public List<Rule> getRule(String ...primise) throws Exception {
        String sql = "select * from rules "+getLocate(primise);
        Object[] para= getLocParaObjs(primise);
        List<Rule> rules = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Rule>(Rule.class),para);
        if (rules.size() == 0) {
            throw new Exception("未查询到此规则");
        }
        return rules;
    }

    /**
     * 条件生成唯一定位字符串
     * @param primise
     * @return
     */
    private static String getLocate(String ...primise){
        StringBuilder stringBuilder1 = new StringBuilder();//拼接字符串 where conclusion = ? and primise1 = ? and primise2 = ?，...
        stringBuilder1.append("where");
        for (int i = 0; i < primise.length; i++) {
            if (i == primise.length - 1) {
                stringBuilder1.append(" primise"+(i+1)+" = ?");
            } else {
                stringBuilder1.append(" primise"+(i+1)+" = ?,");
            }
        }
        return stringBuilder1.toString();
    }

    /**
     * 根据条件生成对应的object[]数组
     * @param primise
     * @return
     */
    private static Object[] getLocParaObjs(String... primise) {
        int lenth = primise.length;
        Object[] para = new Object[lenth];//每次进行删除操作数据库的参数列表
        System.arraycopy(primise,0,para,0,primise.length);
        return para;
    }

    /**
     * 根据结论和条件生成唯一定位字符串
     * @param conclusion
     * @param primise
     * @return
     */
    private static String getLocate(String conclusion,String ...primise){
        StringBuilder stringBuilder1 = new StringBuilder();//拼接字符串 where conclusion = ? and primise1 = ? and primise2 = ?，...
        stringBuilder1.append("where conclusion = ?");
        for (int i = 0; i < primise.length; i++) {
            stringBuilder1.append(" and primise"+(i+1)+" = ?");
        }
        return stringBuilder1.toString();
    }

    /**
     * 根据给定的结论和前提生成对应的object[]数组
     * @param conclusion
     * @param primise
     * @return
     */
    private static Object[] getLocParaObjs(String conclusion,String ...primise){
        int lenth = primise.length+JDBCConstant.conNum;
        Object[] para = new Object[lenth];//每次进行删除操作数据库的参数列表

        para[0] = conclusion;
        System.arraycopy(primise,0,para,JDBCConstant.conNum,primise.length);
        return para;
    }

    /**
     查询规则库所有的规则
     */
    public ArrayList<Rule> getAllRules(){
        String sql = "select * from rules";
        List<Rule> rules = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Rule>(Rule.class));
        return (ArrayList<Rule>) rules;
    }
}


