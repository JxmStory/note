package com.sh.utils;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A class that enables to get an IP range from CIDR specification. It supports
 * both IPv4 and IPv6.
 */
public class CidrUtil {
    private final String cidr;

    private InetAddress inetAddress;
    private InetAddress startAddress;
    private InetAddress endAddress;
    private InetAddress maskAddress;
    private final int prefixLength;

    public static void main(String[] args) throws Exception{

        System.out.println(isIpv6Cidr("2001:0db8:85a3:0000:0000:8a2e:0370:7335/64"));
        System.out.println(isIpv6Cidr("2001:db8:85a3::/164"));
        System.out.println(isIpv6Cidr("153.146.19.1915/25"));
//        CidrUtil util1 = new CidrUtil("153.146.19.1915/25");
//        System.out.println(util1.getStartAddress());
//        System.out.println(util1.getEndAddress());
//        System.out.println(util1.isInRange("153.146.19.129"));
//        System.out.println(util1.isInRange("153.146.19.127"));
//
//        CidrUtil util2 = new CidrUtil("2402:b8c0:100:ff01::/122");
//        System.out.println(util2.getStartAddress());
//        System.out.println(util2.getEndAddress());
//        System.out.println(util2.isInRange("2402:B8C0:0100:FF01:0000:0000:0000:003F"));
//        System.out.println(util2.isInRange("2402:B8C0:0100:FF01:0000:0000:0000:0041"));
    }


    public static boolean isIpv6Cidr(String cidr) {

        String regex = "^((\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*)(\\/(([1-9])|([1-9][0-9])|(1[0-1][0-9]|12[0-8]))){0,1})*$";
        return Pattern.matches(regex, cidr);
    }

    public CidrUtil(String cidr) throws UnknownHostException {

        this.cidr = cidr;

        /* split CIDR to address and prefix part */
        if (this.cidr.contains("/")) {
            int index = this.cidr.indexOf("/");
            String addressPart = this.cidr.substring(0, index);
            String networkPart = this.cidr.substring(index + 1);

            inetAddress = InetAddress.getByName(addressPart);
            prefixLength = Integer.parseInt(networkPart);

            calculate();
        } else {
            throw new IllegalArgumentException("not an valid CIDR format!");
        }
    }


    private void calculate() throws UnknownHostException {

        ByteBuffer maskBuffer;
        int targetSize;
        if (inetAddress.getAddress().length == 4) {
            maskBuffer = ByteBuffer.allocate(4).putInt(-1);
            targetSize = 4;
        } else {
            maskBuffer = ByteBuffer.allocate(16).putLong(-1L).putLong(-1L);
            targetSize = 16;
        }

        BigInteger mask = (new BigInteger(1, maskBuffer.array())).not().shiftRight(prefixLength);

        ByteBuffer buffer = ByteBuffer.wrap(inetAddress.getAddress());
        BigInteger ipVal = new BigInteger(1, buffer.array());

        BigInteger startIp = ipVal.and(mask);
        BigInteger endIp = startIp.add(mask.not());

        byte[] startIpArr = toBytes(startIp.toByteArray(), targetSize);
        byte[] endIpArr = toBytes(endIp.toByteArray(), targetSize);

        this.startAddress = InetAddress.getByAddress(startIpArr);
        this.endAddress = InetAddress.getByAddress(endIpArr);

    }

    private byte[] toBytes(byte[] array, int targetSize) {
        int counter = 0;
        List<Byte> newArr = new ArrayList<Byte>();
        while (counter < targetSize && (array.length - 1 - counter >= 0)) {
            newArr.add(0, array[array.length - 1 - counter]);
            counter++;
        }

        int size = newArr.size();
        for (int i = 0; i < (targetSize - size); i++) {
            newArr.add(0, (byte) 0);
        }

        byte[] ret = new byte[newArr.size()];
        for (int i = 0; i < newArr.size(); i++) {
            ret[i] = newArr.get(i);
        }
        return ret;
    }

    public String getStartAddress() {
        return this.startAddress.getHostAddress();
    }

    public String getEndAddress() {
        return this.endAddress.getHostAddress();
    }

    public boolean isInRange(String ipAddress) throws UnknownHostException {
        InetAddress address = InetAddress.getByName(ipAddress);
        BigInteger start = new BigInteger(1, this.startAddress.getAddress());
        BigInteger end = new BigInteger(1, this.endAddress.getAddress());
        BigInteger target = new BigInteger(1, address.getAddress());

        int st = start.compareTo(target);
        int te = target.compareTo(end);

        return (st == -1 || st == 0) && (te == -1 || te == 0);
    }
}