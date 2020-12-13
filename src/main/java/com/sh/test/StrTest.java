package com.sh.test;

public class StrTest {

    /**
     * 反转字符串（循环交换）
     * 其他字符串反转的方法
     * 1、java的api：StringBuffer的reverse方法
     * 2、利用栈的特性（先进后出）
     * 3、反向遍历字符串
     * @param from
     * @return
     */
    public static String reChange(String from){
        char[] froms = from.toCharArray();
        int length = froms.length;
        for (int i = 0; i < length/2; i++){
            char temp = froms[i];
            froms[i] = froms[length - 1 -i];
            froms[length - 1 -i] = temp;
        }
        return String.valueOf(froms);
    }

    /**
     * 循环左移index位字符串
     * 思想：先部分反转，后整体反转
     * @param from
     * @param index
     * @return
     */
    public static String leftMoveIndex(String from,int index){
        String first = from.substring(0,index);
        String second = from.substring(index);
        first = reChange(first);
        second = reChange(second);
        from = first + second;
        from = reChange(from);
        return from;
    }

    /**
     * 循环右移index位字符串
     * 思想：先整体反转，后部分反转
     * @param from
     * @param index
     * @return
     */
    public static String rightMoveIndex(String from,int index){
        from = reChange(from);
        String first = from.substring(0,index);
        String second = from.substring(index);
        first = reChange(first);
        second = reChange(second);
        from = first + second;
        return from;
    }

    /**
     *  将字符串中每个字母替换成它序号后N个字母
     *  eg: ('azZaxX1', 4) => edDebB1
     *  @author micomo
     *  @date 2020/12/13 11:02
     */
    public static String getNewStr(String str, Integer n) {
        StringBuffer sb = new StringBuffer();
        char arr[]=str.toCharArray();
        int index;
        for(int i = 0; i < arr.length; i++){
            if (arr[i] >= 65 && arr[i] <= 90) {
                index = arr[i] + n;
                sb.append((char)(index > 90 ? index - 26 : index));
            } else if (arr[i] >= 97 && arr[i] <= 122) {
                index = arr[i] + n;
                sb.append((char)(index > 122 ? index - 26 : index));
            } else {
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    /**
     *  将字符串中每个字母替换成它序号前N个字母
     *  eg: ('edDebB1', 4) => azZaxX1
     *  @author micomo
     *  @date 2020/12/13 11:02
     */
    public static String getOldStr(String str, Integer n) {
        StringBuffer sb = new StringBuffer();
        char arr[]=str.toCharArray();
        int index;
        for(int i = 0; i < arr.length; i++){
            if (arr[i] >= 65 && arr[i] <= 90) {
                index = arr[i] - n;
                sb.append((char)(index < 65 ? index + 26 : index));
            } else if (arr[i] >= 97 && arr[i] <= 122) {
                index = arr[i] - n;
                sb.append((char)(index < 97 ? index + 26 : index));
            } else {
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.println(getNewStr("getChannelADOrHy", 5));
        System.out.println(getOldStr(getNewStr("getChannelADOrHy", 5), 5));

    }
}
