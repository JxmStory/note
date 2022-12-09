package com.sh.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * ip 转掩码位
 *
 */
public class Yanma {

    public static void main(String[] args) throws UnknownHostException {
        List<String> list = new ArrayList<>();
        String[] str = "11.24.253.200-11.24.253.255".split("-");

        run(str[0],str[1],list);

        list.forEach(li ->{
            System.out.println(li);
        });
    }

    /**
     * 二进制转换ip
     * @param strIp
     * @return
     */
    public static String longToIp(String strIp){
        int ip_1 = Integer.parseInt(strIp.substring(0, 8), 2);
        int ip_2 = Integer.parseInt(strIp.substring(8, 16), 2);
        int ip_3 = Integer.parseInt(strIp.substring(16, 24), 2);
        int ip_4 = Integer.parseInt(strIp.substring(24, 32), 2);
        return ip_1 + "." + ip_2 + "." +ip_3 + "." + ip_4;
    }

    /**
     * ip + 1
     * @param strIp
     * @return
     */
    public static String ipAdd(String strIp){
        String[] split = strIp.split("\\.");
        int ip_1 = Integer.valueOf(split[0]);
        int ip_2 = Integer.valueOf(split[1]);
        int ip_3 = Integer.valueOf(split[2]);
        int ip_4 = Integer.valueOf(split[3]);
        // ip_4  + 1
        if(++ip_4 > 255){
            ip_4 = 0;
            if(++ip_3 > 255){
                ip_3 = 0;
                if(++ip_2 > 255){
                    ip_2 = 0;
                    if(++ip_1 > 255){
                        ip_1 = 0;
                    }
                }
            }
        }

        return ip_1 + "." + ip_2 + "." +ip_3 + "." + ip_4;
    }

    /**
     * ip段范围的 网段
     * @param startIp
     * @param endIp
     * @param networkList
     * @return
     * @throws UnknownHostException
     */
    public static List<String> run(String startIp, String endIp, List<String> networkList) throws UnknownHostException {
        DecimalFormat decimalFormat = new DecimalFormat("00000000000000000000000000000000");
        // 开始ip
        byte[] startByte = InetAddress.getByName(startIp).getAddress();
        String startStr = decimalFormat.format(new BigDecimal(new BigInteger(1, startByte).toString(2)));
        // 结束ip
        byte[] endByte = InetAddress.getByName(endIp).getAddress();
        String endStr = decimalFormat.format(new BigDecimal(new BigInteger(1, endByte).toString(2)));
        // startIp == endIp
        if(startIp.equals(endIp)){
            networkList.add(startIp + "/32");
            return networkList;
        }

        if((startStr).compareTo(endStr) > 0) {
            return networkList;
        }

        // 最短掩码
        int net = startStr.lastIndexOf("1") + 1;
        for(int i = net; i <= 32; i++){
            // startip/net 表示的最大ip地址
            String substring = startStr.substring(0, i);
            String replace = startStr.substring(i).replace("0", "1");
            // 最大ip
            String maxNetwork = longToIp(substring + replace);
            // 如果最大ip小于end
            if((substring + replace).compareTo(endStr) <= 0 || i == 32) {
                networkList.add(startIp + "/" + i);
                // ip + 1
                run(ipAdd(maxNetwork), endIp, networkList);
                break;
            }
        }

        return networkList;
    }

}