package com.nmq.arithmetic.hanoi;

/**
 *  汉诺塔问题：递归
 * ABC三个柱子
 * 1.N个盘片在A柱子(上到下是小盘到大盘)
 *
 * 2.要求把盘片移动到C柱子
 *
 * 3.移动过程中,柱子不能出现小盘在下面且一次只能移动一个
 *
 * 4.需求:请列出移动的过程,还有移动的次数;
 *
 * 先分析大过程,忽视细节
 *
 * 1. A柱子的N个盘子,两个盘子肯定借助C移动到B,完成一个大阶段:
 *
 * 2. 当A柱子的只剩下最大盘子,那么移动到C
 *
 * 3. B柱子的N-1个盘借助A移动到C
 *
 * 记住这三个大方向通过递归就可以解决汉诺塔问题了
 * Created by niemengquan on 2018/1/12.
 */
public class HanoiQuestion {
    static int countNum;
    public static void main(String[] args) {
        char a = 'A', b = 'B',c = 'C';
        //计数器
        countNum = 0;
        move(3,a,b,c);
        System.out.println(countNum);
    }

    /**
     *移动碟子
     * @param number 柱子上的碟子的个数
     * @param a 原柱子
     * @param b 辅助柱
     * @param c 目标柱子
     */
    public static void move(int number,char a,char b,char c){//由a借助b移动到c上
        countNum++;
        if(number==1){
            System.out.println("from # " + a + " move " + number + " to " + c);
        }else{
            //将number-1的移动到了b上,下面就是将最大的移动到c上就行了
            move(number-1,a,c,b);
            System.out.println("from # " + a + " move " + number + " to " + c);
            move(number-1,b,a,c);
        }

    }
}
