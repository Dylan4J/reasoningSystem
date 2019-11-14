package com.cqu;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Control control = new Control(new RuleDB(),new User());

//        control.addRule("先减脂","偏胖");
//        control.addRule("先减脂","重度肥胖");
//        control.addRule("先增肌","较瘦");
//        control.addRule("选择先增肌或先减脂","可以自由选择");
//        control.addRule("可以自由选择","体重合理");
//        control.addRule("适合以有氧训练为主","先减脂");
//        control.addRule("适合以器械训练为主","先增肌");
//        control.addRule("有健身史","经常光顾健身房");
//        control.addRule("有健身史false","经常光顾健身房false");
//        control.addRule("有过专业器械训练经历","经常光顾健身房");
//        control.addRule("可以自由选择false","有疾病史");
//        control.addRule("有想锻炼的部位","有疾病史false");
//        control.addRule("选择胸部肌肉或背部肌肉或腹部肌肉或腿部肌肉或肩部肌肉","有想锻炼部位");
//        control.addRule("适合做高强度器械训练","适合以器械训练为主","有健身史","有过专业器械训练经历","先增肌");
//        control.addRule("适合做一般强度器械训练","适合以器械训练为主","有健身史","有过专业器械训练经历false","先增肌");
//        control.addRule("适合做中强度器械训练","适合以器械训练为主","有健身史","有过专业器械训练经历","先减脂");
//        control.addRule("适合做一般强度器械训练","适合以器械训练为主","有健身史","有过专业器械训练经历false","先减脂");
//        control.addRule("适合做低强度器械训练","适合以器械训练为主","有健身史false");
//        control.addRule("适合做高时长有氧训练","适合以有氧训练为主","有健身史","身体素质较好","先减脂");
//        control.addRule("适合做中时长有氧训练","适合以有氧训练为主","有健身史false","身体素质较好","先减脂");
//        control.addRule("适合做一般时长有氧训练","适合以有氧训练为主","有健身史","身体素质一般","先减脂");
//        control.addRule("适合做低时长有氧训练","适合以有氧训练为主","有健身史false","身体素质一般","先减脂");
//        control.addRule("适合做一般时长有氧训练","适合以器械训练为主","有健身史false","先减脂");
//        control.addRule("适合做中时长有氧训练","适合以器械训练为主","有健身史","先减脂");
//        control.addRule("适合做一般时长有氧训练","适合以器械训练为主","有健身史","先增肌");
//
//        control.addRule("适合轻量长时间无氧运动和短时间有氧运动时间比为7:3","偏胖","有健身史false","先增肌");
//        control.addRule("适合轻量长时间无氧运动和短时间有氧运动时间比为4:6","偏胖","有健身史false","先减脂");
//        control.addRule("适合极限量长时间无氧运动和短时间有氧运动时间比为7:3","偏胖","有健身史","先增肌");
//        control.addRule("适合极限量长时间无氧运动和短时间有氧运动时间比为4:6","偏胖","有健身史","先减脂");
//        control.addRule("适合中等重量长时间无氧运动和较长时间有氧运动时间比为5:5，并降低高热量食物的摄入，多摄入饱腹感强，热量低的食物","重度肥胖","有健身史false","先减脂");
//        control.addRule("适合极限量长时间无氧运动和长时间有氧运动时间比为5:5，并降低高热量食物的摄入，多摄入饱腹感强，热量低的食物","重度肥胖","有健身史","先减脂");
//        control.addRule("适合慢跑、瑜伽等轻量运动","偏胖","有健身史","先增肌","有疾病史");
//        control.addRule("适合慢跑、瑜伽等轻量运动","偏胖","有健身史false","先增肌","有疾病史");
//        control.addRule("适合慢跑、瑜伽等轻量运动，并降低高热量食物的摄入，多摄入饱腹感强，热量低的食物","偏胖","有健身史false","先减脂","有疾病史");
//        control.addRule("适合慢跑、瑜伽等轻量运动，并降低高热量食物的摄入，多摄入饱腹感强，热量低的食物","重度肥胖","有健身史false","先减脂","有疾病史");
//
//        //来自张杰的贡献
//        control.addRule("适合练习哑铃卧推","适合以器械训练为主","有健身史","先增肌","有想锻炼的部位","胸部肌肉");
//        control.addRule("适合练习平地俯卧撑","适合以有氧训练为主","有健身史false","先增肌","有想锻炼的部位","胸部肌肉");
//        control.addRule("适合练习腹部轮训练","适合以器械训练为主","有健身史","先增肌","有想锻炼的部位","腹部肌肉");
//        control.addRule("适合练习仰卧起坐","适合以有氧训练为主","有健身史false","先增肌","有想锻炼的部位","腹部肌肉");
//        control.addRule("适合练习杠铃划船","适合以器械训练为主","有健身史","先增肌","有想锻炼的部位","背部肌肉");
//        control.addRule("适合练习引体向上","适合以有氧训练为主","有健身史false","先增肌","有想锻炼的部位","背部肌肉");
//        control.addRule("适合练习哑铃斜托弯举","适合以器械训练为主","有健身史","先增肌","有想锻炼的部位","臂部肌肉");
//        control.addRule("适合练习平地俯卧撑","适合以有氧训练为主","有健身史false","先增肌","有想锻炼的部位","臂部肌肉");
//        control.addRule("适合练习杠铃深蹲","适合以器械训练为主","有健身史","先增肌","有想锻炼的部位","腿部肌肉");
//        control.addRule("适合练习无负重深蹲","适合以有氧训练为主","有健身史false","先增肌","有想锻炼的部位","腿部肌肉");
//        control.addRule("适合练习T型杠硬拉","适合以器械训练为主","有健身史","先增肌","有想锻炼的部位","臀部肌肉");
//        control.addRule("适合练习跪蹲","适合以有氧训练为主","有健身史false","先增肌","有想锻炼的部位","臀部肌肉");
//        control.addRule("适合练习哑铃前平举","适合以器械训练为主","有健身史","先增肌","有想锻炼的部位","肩部肌肉");
//        control.addRule("适合练习平地俯卧撑","适合以有氧训练为主","有健身史false","先增肌","有想锻炼的部位","肩部肌肉");
        System.out.println("尊敬的先生/女士，欢迎您使用keep健身教练！\n接下来我会问您几个问题，为了帮助您获取更为准确的健身计划" +
                "请您提供真实的信息，谢谢您的配合！");
        System.out.println("请输入您的姓名、性别、年龄、身高、体重：（格式  xxx--男--23--178--70）");
        setBasicInf(control);
        control.ask("您是否经常光顾健身房呢（请输入y/n）");
        control.ask("您是否有疾病史呢（请输入y/n）");
        control.ask("您是否有想锻炼的部位呢（请输入y/n）");
        control.compara();


    }

    /**
     * 设置基本信息
     * @param control
     */
    public static void setBasicInf(Control control){
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String[] strings = s.split("--");
        control.getUser().setName(strings[0]);
        if (strings[1].equals("男")) {//男性：true
            control.getUser().setSex(true);
        } else {
            control.getUser().setSex(false);
        }
        control.getUser().setAge(Integer.parseInt(strings[2]));
        control.getUser().setHeight(Integer.parseInt(strings[3]));
        control.getUser().setWeight(Integer.parseInt(strings[4]));
    }
    @Test
    public void calculate(){
        int max = 5;
        int min = 1;
        int boxA = 10;
        int[] z = new int[6];
        ArrayList<int[]> conclusion = null;
        int a1= 0;
        int a2= 0;
        int a3= 0;
        int a4= 0;
        int a5= 0;
        int a6= 0;
        int k = 0;
        System.out.println("\t\ta1\t\ta2\t\ta3\t\ta4\t\ta5\t\ta6");
        for (a1 = min; a1 < max; a1++) {
            for (a2 = min; a2 < max; a2++) {
                for (a3 = min; a3 < max;a3 ++) {
                    for (a4 = min; a4 < max; a4++) {
                        for (a5 = min; a5 < max;a5 ++) {
                            for (a6 = min;a6  < max; a6++) {
                                if (a1 + a2 + a3 + a4 + a5 + a6 == 20 ) {
                                    if (a1 + a2 + a3 == boxA) {
                                        z[0] = a1;
                                        z[1] = a2;
                                        z[2] = a3;
                                        z[3] = a4;
                                        z[4] = a5;
                                        z[5] = a6;
                                        k++;
                                        System.out.println("case"+k+"、\t"
                                                +a1+"\t||\t"
                                                +a2+"\t||\t"
                                                +a3+"\t||\t"
                                                +a4+"\t||\t"
                                                +a5+"\t||\t"
                                                +a6);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


    }
}
