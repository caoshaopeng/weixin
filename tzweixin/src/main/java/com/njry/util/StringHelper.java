package com.njry.util;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sun.misc.BASE64Encoder;

public final class StringHelper {  
    
    /** 
     * 判断一个字符串是否为 null 或 空串 或 空白 
     * @param source 需要判断的字符串 
     * @return 当字符串为 null 或 为 空白、空串 时返回 true 
     */  
    public static boolean empty( String source ) {  
        return source == null || source.trim().isEmpty()  ;  
    }  
      
    /** 
     * 判断一个字符串是否不是null且不是空串、不是空白 
     * @param source 需要判断的字符串 
     * @return 当 字符串是不是null且不是空串也不是空白时返回 true 
     */  
    public static boolean notEmpty( String source ) {  
        return source != null && source.trim().length() > 0 ;  
    }  
      
    /** 
     * 判断一个字符串变量是否为 null 
     * @param source 需要判断的字符串 
     * @return 当 字符串变量 为 null 时返回 true 
     */  
    public static boolean isNull( String source ) {  
        return source == null ;  
    }  
      
    /** 
     * 判断一个字符串是否为 空串 
     * @param source 需要判断的字符串 
     * @return 当字符串中的值是 空串 或 空白 串时返回 true 
     */  
    public static boolean emptyString( String source ) {  
        return ( source != null ) && source.length() == source.trim().length() ;  
    }  
      
    /** 
     * 判断一个字符串是否为 空白 串 
     * @param source 需要判断的字符串 
     * @return 当字符串中的值是 空白 串时返回 true 
     */  
    public static boolean blank( String source ){  
        return ( source != null ) && source.length() > source.trim().length()  ;  
    }  
      
    /** 
     * 比较两个非空(不是null，不是空串、不是空白)字符串是否"相等" 
     * @param one 第一个需要比较的字符串 
     * @param theOther 另一个参与比较的字符串 
     * @return 当 两个字符串 都不为空串 且 内容完全一致 (剔除首尾空白后、大小写也一致)时返回 true 
     */  
    public static boolean equals( String one , String theOther) {  
        return equals(one, theOther,true,false);  
    }  
      
    /** 
     * 比较两个字符串是否 "相等" 
     * @param one 参与比较的第一个字符串 
     * @param theOther 参与比较的另一个字符串 
     * @param escapeSpace 是否需要剔除首尾空白 ( true 表示需要剔除首尾空白，false 表示不剔除 ) 
     * @param ignoreCase 是否忽略大小写 ( true 表示忽略大小写 ，false 表示不忽略大小写 ) 
     * @return 
     */  
    public static boolean equals( String one , String theOther , boolean escapeSpace , boolean ignoreCase) {  
          
        if( one == null || theOther == null ){  
            return false ;  
        }  
          
        if( escapeSpace ){  
            one = one.trim();  
            theOther = theOther.trim();  
        }  
          
        return ignoreCase ? one.equalsIgnoreCase( theOther ) : one.equals( theOther ) ;  
    }  
      
    /** 
     * 随机生成一个 32 位长度的 字符串( UUID ) 
     * @return 
     */  
    public static String random(){  
        UUID uuid = UUID.randomUUID();//36位长度(包含了 四个 - )  
        String uuidString = uuid.toString();  
        uuidString = uuidString.replace("-", "");  
        uuidString = uuidString.toUpperCase();  
        return uuidString;  
    }  
      
    /** 
     * 通过 SHA1 对字符串进行加密 
     * @param source 
     * @return 
     */  
    public static String sha1(String source ){  
        try{  
            MessageDigest md = MessageDigest.getInstance("SHA1");  
            BASE64Encoder encoder = new BASE64Encoder();  
            return encoder.encode( md.digest( source.getBytes() ) );  
        }catch(NoSuchAlgorithmException e){  
            e.printStackTrace();  
            return null;  
        }  
    }  
      
    /** 
     * 对 给定字符串 进行 md5 加密 
     * @param source 待加密的字符串 
     * @return 
     */  
    protected static String md524(String source ){  
        try{  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            BASE64Encoder encoder = new BASE64Encoder();  
            return encoder.encode( md.digest(source.getBytes()) );  
        }catch(NoSuchAlgorithmException e){  
            e.printStackTrace();  
            return null;  
        }  
    }  
      
    /** 
     * 对字符串进行MD5加密 
     * @param source 需要加密的字符串 
     * @return 返回加密后的字符串 
     */  
    public static final String MD5(String source){  
        if(source != null){  
            StringBuffer md5 = new StringBuffer();  
            MessageDigest md = null;  
            try{  
                md = MessageDigest.getInstance("MD5");  
                md.update(source.getBytes());  
                byte[] mdBytes = md.digest();  
                  
                for(int i = 0;i < mdBytes.length;i++){  
                    int temp;  
                    if(mdBytes[i] < 0){  
                        temp = 256+mdBytes[i];  
                    }else{  
                        temp = mdBytes[i];  
                    }  
                    if(temp < 16){  
                        md5.append("0");  
                    }  
                    md5.append(Integer.toString(temp,16 ));  
                }  
            }catch(NoSuchAlgorithmException e){  
                e.printStackTrace();  
            }  
            return md5.toString().toUpperCase();  
        }  
        return null;  
    }  
    /** 
     * 检测邮箱合法性 
     *  
     * @param email 
     * @return 
     */  
    public static boolean isEmail(String email) {  
        if ((email == null) || (email.trim().length() == 0)) {  
            return false;  
        }  
        String regEx = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";  
        Pattern p = Pattern.compile(regEx);  
        Matcher m = p.matcher(email.trim().toLowerCase());  
   
        return m.find();  
    }  
  
   
    /** 
     * Double进行四舍五入 
     *  
     * @param v 
     * @param scale 
     * @return 
     */  
    public static double getDouble(Double v, int scale) {  
   
        if (scale < 0) {  
            scale = 0;  
        }  
        BigDecimal b = new BigDecimal(v);  
        BigDecimal one = new BigDecimal("1");  
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();  
    }  
   
    /** 
     * 得到指定位数的小数 
     * @param v 
     * @param scale 
     * @return 
     */  
    public static String getDecimals(Double v, int scale) {  
   
        return String.format("%." + String.valueOf(scale) + "f", v);  
   
    }  
    /** 
     * 根据Unicode编码完美的判断中文汉字和符号 
     *  
     * @param c 
     * @return 
     */  
    private static boolean isChinese(char c) {  
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B  
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS  
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {  
            return true;  
        }  
        return false;  
    }  
   
    /** 
     * 判断是否包含中文汉字 
     *  
     * @param strName 
     * @return 
     */  
    public static boolean isChineseHave(String strName) {  
        char[] ch = strName.toCharArray();  
        for (int i = 0; i < ch.length; i++) {  
            char c = ch[i];  
            if (isChinese(c)) {  
                return true;  
            }  
        }  
        return false;  
    }  
   
    /** 
     * 判断是否只有中文汉字 
     *  
     * @param strName 
     * @return 
     */  
    public static boolean isChineseAll(String strName) {  
        char[] ch = strName.toCharArray();  
        for (int i = 0; i < ch.length; i++) {  
            char c = ch[i];  
            if (!isChinese(c)) {  
                return false;  
            }  
        }  
        return true;  
    }  
   
    /** 
     * 判断邮政编码 
     *  
     * @param str 
     * @return 
     */  
    public static boolean isCard(String str) {  
        Pattern p = Pattern.compile("[1-9]\\d{5}(?!\\d)");  
        Matcher m = p.matcher(str);  
        return m.matches();  
    }  
   
}
