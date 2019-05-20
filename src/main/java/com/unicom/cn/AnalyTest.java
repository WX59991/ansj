package com.unicom.cn;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;
import java.util.List;

/**
 * 分词测试
 */
public class AnalyTest {

    public static void main(String[] args) {
        fileDirec();
        Ambiguit("码上购买");
    }

    /**
     * 解决歧义词问题
     * 比如:  定义自定义词:码上购   输入：码上购买  分词结果: 码上 购买
     * 使用歧义词后:  码上购 买
     * @param data
     */
    public static void Ambiguit(String data){
        System.out.println(ToAnalysis.parse(data));
    }

    /**
     * 读取用户自定义的词典进行匹配
     */
    public static void fileDirec(){
        Forest forest = null;
        try {
            forest=Library.makeForest(AnalyTest.class.getResourceAsStream("/library/userLibrary.dic"));//加载字典文件
            String str = "古城新区锦绣嘉园";
            Result result= ToAnalysis.parse(str,forest);//传入forest
            List<Term> termList=result.getTerms();
            for(Term term:termList){
                System.out.println(term.getName()+":"+term.getNatureStr());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态往词典中添加和删除词
     */
    public static void dynamicWord(){
        String str = "大家好，我叫王霞" ;
        DicLibrary.insert(DicLibrary.DEFAULT,"大家好");
        DicLibrary.insert(DicLibrary.DEFAULT,"我叫王霞");
        System.out.println(ToAnalysis.parse(str));
        //不能引入用户自定义字典
        System.out.println(BaseAnalysis.parse(str));
        //从字典里移除
        DicLibrary.delete(DicLibrary.DEFAULT,"我叫王霞");
        System.out.println(ToAnalysis.parse(str));
    }

    /**
     *简单示例
     */
    public static void simpleAnaly(){
        String str = "大家好，我叫王霞" ;
        //精准分词  用户自定义词典	数字识别	人名识别	机构名识别	新词发现
        System.out.println(ToAnalysis.parse(str));
        //基本分词 数字识别
        System.out.println(BaseAnalysis.parse(str));
    }

}
