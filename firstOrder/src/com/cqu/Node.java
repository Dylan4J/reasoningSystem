package com.cqu;

public class Node {
        String parameter;
        boolean paraValue;

    public void set(Object[] obj){
        this.parameter = (String) obj[0];
        this.paraValue = (boolean)obj[1];
    }
    public void set(String parameter,boolean paraValue) {
        this.parameter = parameter;
        this.paraValue = paraValue;
    }

    @Override
    public String toString() {
        return "Node{" +
                "parameter='" + parameter + '\'' +
                ", paraValue=" + paraValue +
                '}';
    }

    /**
     * 这个函数是用来比较节点的参数名称是否一样的
     * @param object 需要进行比较的节点
     * @return
     */
    public boolean equalsParameter(Object object) {
        Node node = (Node) object;
        if (this.parameter.equals(node.parameter)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 这个函数是用来比较节点的参数值是否一样的
     * @param object 需要进行比较的节点
     * @return
     */
    public boolean equalsValue(Object object) {
        Node node = (Node) object;
        if (this.paraValue == node.paraValue) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 比较两个节点是否完全一样
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object){
        Node node = (Node) object;
        if (equalsParameter(node) && equalsValue(node)) {
            return true;
        } else {
            return false;
        }
    }
}
