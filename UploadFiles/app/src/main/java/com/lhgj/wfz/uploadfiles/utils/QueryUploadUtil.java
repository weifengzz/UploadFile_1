package com.lhgj.wfz.uploadfiles.utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 *
 * Created by WFZ on 2015/12/7.
 */
public class QueryUploadUtil {
    private String filename, base64string;

    public QueryUploadUtil(String base64str, String fileName) {
        this.filename = fileName;
        this.base64string = base64str;
    }
    // 需要实现Callable的Call方法
    public String call(String wsdl,String url,String methodName) throws Exception {
        String str = "上传失败";
        // TODO Auto-generated method stub
        try {
            //创建SoapObject对象，并指定WebService的命名空间和调用的方法名
            SoapObject rpc = new SoapObject("http://tempuri.org/",
                    methodName);
            //设置WebService方法的参数
            rpc.addProperty("base64string", base64string);
            rpc.addProperty("fileName1", filename);
            //第3步：创建SoapSerializationEnvelope对象，并指定WebService的版本
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            // 设置bodyOut属性
            envelope.bodyOut = rpc;
            envelope.dotNet = true;
            envelope.setOutputSoapObject(rpc);
            //创建HttpTransportSE对象，并指定WSDL文档的URL
            /**
             * 创建WSDL文档有三种方法：
             * 在.NET中有三种方式生成WSDL：
             * 1．在Web Service的URL后面加上WDSL需求，如下：
             *  http://localhost/webExamples/simpleService.asmx?WSDL
             * 2．使用disco.exe。在命令行中写下如下的命令：
             *  disco http://localhost/webExamples/simpleService.asmx
             *3．使用System.Web.Services.Description命名空间下提供的类
             * */
//            HttpTransportSE ht = new HttpTransportSE(
//                    "http://192.168.15.4:1122/service/vedios/GetVedios.asmx?WSDL");
            HttpTransportSE ht = new HttpTransportSE(wsdl);
            ht.debug = false;
            //调用WebService
//            ht.call("http://192.168.15.4:1122/service/vedios/GetVedios.asmx/FileUploadByBase64String",
//                    envelope);
            ht.call(url, envelope);
            //使用getResponse方法获得WebService方法的返回结果
            String result = String.valueOf(envelope.getResponse());
            //这个地方是我自己设置的从webservice返回的结果，“1”表示上传成功。
            if (result.toString().equals("1"))
                str = "上传成功";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            str = "上传错误";
        }
        return str;
    }
}
