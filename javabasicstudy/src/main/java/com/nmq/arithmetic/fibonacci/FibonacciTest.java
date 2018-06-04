package com.nmq.arithmetic.fibonacci;

/**
 * 斐波那契数列：F（n）=F(n-1)+F(n-2)（n≥2，n∈N*）
 * Created by niemengquan on 2018/1/15.
 */
public class FibonacciTest {
    public static void main(String[] args) {
        long beginTime=System.currentTimeMillis();
//        System.out.println(getFib(40));//耗时大约965
//        System.out.println(getFibonacci(40));//耗时大约470
//        System.out.println(getFibonacciByVariable(40));
        System.out.println(getFibonacciByArray(40));

        long endTime=System.currentTimeMillis();
        System.out.println(endTime-beginTime);
    }

    public static int getFib(int n) {
        if (n < 0)
            return -1;
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        if (n > 1) {
            return getFib(n - 1) + getFib(n - 2);
        }
        return -1;
    }

    /**
     * 递归的方式
     *
     * @param n
     * @return
     */
    public static int getFibonacci(int n) {
        if (n == 0)
            return 0;
        return n > 2 ? (getFibonacci(n - 1) + getFibonacci(n - 2)) : 1;
    }

    /**
     * 基于变量的方式的实现
     * @param n
     * @return
     */
    public static int getFibonacciByVariable(int n) {
        if (n < 0)
            return -1;
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;

        if (n > 1) {
            /**
             * a 等于F(n-1)
             * b 等于F(n-2)
             * c 当前的值
             */
            int c = 0, a = 0, b = 1;
            for (int i = 2; i <= n; i++) {
                c = a + b;
                a = b;
                b = c;
            }
            return c;

        }
        return -1;
    }

    /**
     * 基于数组的方式
     * @param n
     * @return
     */
    public static int getFibonacciByArray(int n) {
        if (n < 0)
            return -1;
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;

        if (n > 1) {
            Integer[] tempArray = new Integer[n + 1];
            tempArray[0] = 0;
            tempArray[1] = 1;
            for (int i = 2; i <= n; i++) {
                tempArray[i] = tempArray[i - 1] + tempArray[i - 2];
            }
            return tempArray[n];

        }
        return -1;
    }
}
