package com.zs.tools;

import cn.com.infosec.icbc.ReturnValue;
import net.sf.json.JSONObject;

import java.io.FileInputStream;

/**
 * 银行支付工具类
 * Created by Allen on 2015/11/19.
 */
public class BankPayTools {

    /**
     * 请求银行接口前，获取签名字符串
     */
    public static JSONObject sign(String tranData, String merCertKeyPasswd, String signFilePath){
        JSONObject jsonObject = new JSONObject();

        String merSignMsg = "";
        String merCert = "" ;
        String tranDataBase64 = "" ;
        try{
//            byte[] base64 = ReturnValue.base64enc(tranData.getBytes("gbk"));
            byte[] base64 = ReturnValue.base64enc(tranData.getBytes());
            tranDataBase64 = new String(base64);

            FileInputStream f = new FileInputStream(signFilePath+"/pos.key");
            byte[] bs = new byte[f.available()];
            f.read(bs);
            f.close();
//            byte[] signature = ReturnValue.base64enc(ReturnValue.sign(
//                    tranData.getBytes("gbk"), tranData.getBytes("gbk").length, bs,
//                    merCertKeyPasswd.toCharArray()));
            byte[] signature = ReturnValue.base64enc(ReturnValue.sign(
                    tranData.getBytes(), tranData.getBytes().length, bs,
                    merCertKeyPasswd.toCharArray()));
            merSignMsg =  new String(signature,"iso-8859-1");
//            merSignMsg =  new String(signature,"gbk");

            FileInputStream fs = new FileInputStream(signFilePath+"/pos.crt");
            byte[] bsc = new byte[fs.available()];
            fs.read(bsc);
            fs.close();
            byte[] cert = ReturnValue.base64enc(bsc);
            merCert = new String(cert);

        }catch(Exception e) {
            e.printStackTrace();
        }

        jsonObject.put("tranDataBase64", tranDataBase64);
        jsonObject.put("merSignMsg", merSignMsg);
        jsonObject.put("merCert", merCert);
        return jsonObject;
    }

    /**
     * 银行返回结果接口前，解析签名字符串
     */
    public static JSONObject notify(String notifyData, String bankCertKeyPasswd, String signFilePath){
        JSONObject jsonObject = new JSONObject();
        String signMsg = "";
        try {
            FileInputStream f = new FileInputStream(signFilePath + "/pos.key");
            byte[] bs = new byte[f.available()];
            f.read(bs);
            f.close();
            byte[] base64 = ReturnValue.base64enc(notifyData.getBytes());
            byte[] signature = ReturnValue.base64enc(ReturnValue.sign(
                    notifyData.getBytes(), notifyData.getBytes().length, bs,
                    bankCertKeyPasswd.toCharArray()));

            notifyData = new String(base64);
            signMsg =  new String(signature);


            String base64_2 = notifyData;
            String signMsg2 = signMsg;
            byte[] notifyDataB = ReturnValue.base64dec(base64_2.getBytes());
            String notifyData2 = new String(notifyDataB);
            byte[] signature2 = ReturnValue.base64dec(signMsg2.getBytes());

            FileInputStream f2 = new FileInputStream(signFilePath + "/ebb2cpublic.crt");
            byte[] bs2 = new byte[f2.available()];
            f2.read(bs2);
            f2.close();
            int result = ReturnValue.verifySign(notifyDataB, notifyDataB.length, bs2, signature2);
            jsonObject.put("notifyData", ReturnValue.base64dec(notifyData.getBytes()));
            jsonObject.put("result", result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

}
