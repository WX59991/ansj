package com.unicom.cn.mytrie;

import java.util.*;

public class MyTrie {

    public static void main(String[] args) {
        MyTrie myTrie=new MyTrie();
        myTrie.insertWord("我","n");
        myTrie.insertWord("我们","n");
        myTrie.insertWord("都","a");
        myTrie.insertWord("爱","v");
        myTrie.insertWord("北京","n");
        myTrie.insertWord("天安门","n");
        for(Result result:myTrie.anasyWord("我们都爱北京天安门")){
            System.out.println(result);
        }
    }

    //根头节点
    private TrieNode head=new TrieNode(-1,new ArrayList<TrieNode>(),false);

    /**
     * key  关键字 value 层数,位移
     */
    private Map<Character,List<Integer>> directy=new HashMap<Character, List<Integer>>();

    /**
     * 词性字典
     * @param word
     */
    private Map<String,String> natures=new HashMap<String, String>();

    public void insertWord(String word,String nature){
        natures.put(word,nature);
        char[] chars=word.toCharArray();
        TrieNode node=head;
        //第1层，头节点下的直接子节点层
        int len=1;
        for(char temp:chars){
            if(directy.get(temp)!=null && directy.get(temp).get(0)==len){
                //存在下一个字符
                node=node.getSon().get(directy.get(temp).get(1));
            }else{
                //加入字典目录
                directy.put(temp,Arrays.asList(len,node.getSon().size()));
                //不存在就创建
                TrieNode newNode=new TrieNode(0,new ArrayList<TrieNode>(),false,temp);
                //加入为子节点
                node.getSon().add(newNode);
                node=newNode;
            }
            len++;
        }
    }

    public List<Result> anasyWord(String word){
        List<Result> list=new ArrayList<Result>();
        char[] chars=word.toCharArray();
        TrieNode node=head;
        StringBuilder stringBuilder=new StringBuilder();
        Integer len=1;
        for(char temp:chars){
            if(directy.get(temp)!=null && directy.get(temp).get(0)==len){
                stringBuilder.append(temp);
                //存在下一个字符
                node=node.getSon().get(directy.get(temp).get(1));
                len++;
            }else{
                //添加至结果
                if(!stringBuilder.toString().equals("")){
                    Result result=new Result(stringBuilder.toString(),natures.get(stringBuilder.toString()));
                    list.add(result);
                }
                //清空
                stringBuilder.setLength(0);
                //不存在就重新指回头节点
                node=head;
                len=1;
                if(directy.get(temp)!=null && directy.get(temp).get(0)==len){
                    stringBuilder.append(temp);
                    //存在下一个字符
                    node=node.getSon().get(directy.get(temp).get(1));
                    len++;
                    continue;
                }
                System.out.println("con not find "+temp);
                break;
            }
        }
        if(!stringBuilder.toString().equals("")){
            Result result=new Result(stringBuilder.toString(),natures.get(stringBuilder.toString()));
            list.add(result);
        }
        return list;
    }

}

class Result{
    //字段名
    private String value;
    //词性
    private String nature;
    public Result(String value,String nature){
        this.value=value;
        this.nature=nature;
    }

    public String toString(){
        return value+" /"+nature;
    }
}
class TrieNode{
    private int num;//有多少单词通过这个节点,即由根至该节点组成的字符串模式出现的次数
    private List<TrieNode> son;//所有的儿子节点
    private boolean isEnd;//是不是最后一个节点
    private char val;//节点的值

    public TrieNode(){}

    public TrieNode(int num,List<TrieNode> son,boolean isEnd,char val){
        this.num=num;
        this.son=son;
        this.isEnd=isEnd;
        this.val=val;
    }

    //头节点
    public TrieNode(int num,List<TrieNode> son,boolean isEnd){
        this.num=num;
        this.son=son;
        this.isEnd=isEnd;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<TrieNode> getSon() {
        return son;
    }

    public void setSon(List<TrieNode> son) {
        this.son = son;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public char getVal() {
        return val;
    }

    public void setVal(char val) {
        this.val = val;
    }
}