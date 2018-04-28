package com.zs.tools;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;

/**
 * Created by Allen on 2015/6/5.
 */
public class StringTools {
    /**
     * 转换成财务显示样式
     * @param price
     * @return
     */
    public static String getFinancePrice(String price){
        if(StringUtils.isEmpty(price)){
            return "";
        }else{
            String integer = "", decimal = "";
            if(0 > price.indexOf(".")){
                integer = price;
            }else{
                integer = price.substring(0, price.indexOf("."));
                decimal = price.substring(price.indexOf("."), price.length());
            }
            DecimalFormat df = new DecimalFormat("##,###,###");
            integer = df.format(Double.parseDouble(integer));
            return integer + decimal;
        }
    }

    /**
     * 格式化浮点型，超过10位后不让其显示成科学计数法
     * @param value
     * @return
     */
    public static String formatNumber(Double value) {
        if(value != null){
            if(value.doubleValue() != 0.00){
                java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
                return df.format(value.doubleValue());
            }else{
                return "0.00";
            }
        }
        return "";
    }

    /**
     * 格式化浮点型，超过10位后不让其显示成科学计数法
     * @return
     */
    public static String changeCode(String str, String oldCode, String newCode) {
        if(!StringUtils.isEmpty(str)){
            try {
                return new String(str.getBytes(oldCode), newCode);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String getDecode(HttpServletRequest request,String name){
        String result="";
        try{
            String query=request.getQueryString();
            String[] a=query.split("\\&");
            String val="";
            for(String b:a){
                String[] c=b.split("\\=",2);
                if(c[0].equalsIgnoreCase(name)){
                    val=c[1];
                    break;
                }
            }
            result = URLDecoder.decode(val, "UTF-8");
        }catch(Throwable e){
            e.printStackTrace();
        }
        return result;
    }
}
