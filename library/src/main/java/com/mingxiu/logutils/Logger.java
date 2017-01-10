package com.mingxiu.logutils;

import android.text.TextUtils;
import android.util.Log;

import com.mingxiu.logutils.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import static com.mingxiu.logutils.LogLevel.TYPE_DEBUG;
import static com.mingxiu.logutils.LogLevel.TYPE_ERROR;
import static com.mingxiu.logutils.LogLevel.TYPE_INFO;
import static com.mingxiu.logutils.LogLevel.TYPE_VERBOSE;
import static com.mingxiu.logutils.LogLevel.TYPE_WARM;
import static com.mingxiu.logutils.LogLevel.TYPE_WTF;
import static com.mingxiu.logutils.utils.ObjectUtil.objectToString;
import static com.mingxiu.logutils.utils.Utils.DIVIDER_BOTTOM;
import static com.mingxiu.logutils.utils.Utils.DIVIDER_CENTER;
import static com.mingxiu.logutils.utils.Utils.DIVIDER_NORMAL;
import static com.mingxiu.logutils.utils.Utils.DIVIDER_TOP;
import static com.mingxiu.logutils.utils.Utils.generateTag;
import static com.mingxiu.logutils.utils.Utils.getTopStackInfo;
import static com.mingxiu.logutils.utils.Utils.largeStringToList;
import static com.mingxiu.logutils.utils.Utils.printDividingLine;

/**
 * ----------BigGod be here!----------/
 * ***┏┓******┏┓*********
 * *┏━┛┻━━━━━━┛┻━━┓*******
 * *┃             ┃*******
 * *┃     ━━━     ┃*******
 * *┃             ┃*******
 * *┃  ━┳┛   ┗┳━  ┃*******
 * *┃             ┃*******
 * *┃     ━┻━     ┃*******
 * *┃             ┃*******
 * *┗━━━┓     ┏━━━┛*******
 * *****┃     ┃神兽保佑*****
 * *****┃     ┃代码无BUG！***
 * *****┃     ┗━━━━━━━━┓*****
 * *****┃              ┣┓****
 * *****┃              ┏┛****
 * *****┗━┓┓┏━━━━┳┓┏━━━┛*****
 * *******┃┫┫****┃┫┫********
 * *******┗┻┛****┗┻┛*********
 * ━━━━━━神兽出没━━━━━━
 * 版权所有：个人
 * 作者：Created by a.wen.
 * 创建时间：2016/12/21
 * Email：13872829574@163.com
 * 内容描述：
 * 修改人：a.wen
 * 修改时间：${DATA}
 * 修改备注：
 * 修订历史：1.0
 */
public class Logger implements Printer {

    private LogConfigImpl mLogConfig;

    Logger() {
        this.mLogConfig = LogConfigImpl.getInstance();
        mLogConfig.addParserClass(Constant.DEFAULT_PARSE_CLASS);
    }

    /**
     * 设置临时tag
     *
     * @param tag String
     * @return Printer
     */
    Printer setTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            Utils.localTags.set(tag);
        }
        return this;
    }

    /**
     * 打印对象
     *
     * @param type   int
     * @param object Object
     */
    private void logObject(@LogLevel.LogLevelType int type, Object object) {
        if (object instanceof String) {
            logOneLineString(type, (String) object);
        } else if(object instanceof String[]||object instanceof int[]||object instanceof int[]){
            logOneLineString(type, objectToString(object));
        }else {
            logSomeLineString(type, objectToString(object));
        }
    }

    //单行
    private void logOneLineString(@LogLevel.LogLevelType int type, String msg) {
        if (mLogConfig.isEnable() && type > mLogConfig.getLogLevel()) {
            String tag = generateTag();
            for (String subMsg : largeStringToList(msg)) {
                printLog(type, tag, printDividingLine(DIVIDER_NORMAL) + getTopStackInfo() + ": " + subMsg);
            }
        }
    }

    //多行
    private void logSomeLineString(@LogLevel.LogLevelType int type, String msg) {
        if (mLogConfig.isEnable() && type > mLogConfig.getLogLevel()) {
            String tag = generateTag();
            if (mLogConfig.isShowBorder()) {//显示边界
                printLog(type, tag, printDividingLine(DIVIDER_TOP));
                printLog(type, tag, printDividingLine(DIVIDER_NORMAL) + getTopStackInfo());
                printLog(type, tag, printDividingLine(DIVIDER_CENTER));
                for (String sub : msg.split(Constant.BR)) {
                    for (String subMsg : largeStringToList(sub)) {
                        printLog(type, tag, printDividingLine(DIVIDER_NORMAL) + subMsg);
                    }
                }
                printLog(type, tag, printDividingLine(DIVIDER_BOTTOM));
            } else {                       //不显示边界
                printLog(type, tag, printDividingLine(DIVIDER_NORMAL) + getTopStackInfo() + ":");
                for (String sub : msg.split(Constant.BR)) {
                    for (String subMsg : largeStringToList(sub)) {
                        printLog(type, tag, printDividingLine(DIVIDER_NORMAL) + subMsg);
                    }
                }
            }
        }
    }

    @Override
    public void d(Object object) {
        logObject(TYPE_DEBUG, object);
    }

    @Override
    public void e(Object object) {
        logObject(TYPE_ERROR, object);
    }

    @Override
    public void w(Object object) {
        logObject(TYPE_WARM, object);
    }

    @Override
    public void i(Object object) {
        logObject(TYPE_INFO, object);
    }

    @Override
    public void v(Object object) {
        logObject(TYPE_VERBOSE, object);
    }

    @Override
    public void wtf(Object object) {
        logObject(TYPE_WTF, object);
    }

    /**
     * 采用orhanobut/logger的json解析方案
     *
     * @param json String
     */
    @Override
    public void json(String json) {
        int indent = 4;
        if (TextUtils.isEmpty(json)) {
            d("JSON{json is empty}");
            return;
        }
        if (json.length() < 80) {
            d(json);
            return;
        }
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String msg = jsonObject.toString(indent);
                logSomeLineString(TYPE_DEBUG, msg.replace("\\/","/"));
            } else if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String msg = jsonArray.toString(indent);
                logSomeLineString(TYPE_DEBUG, msg);
            }
        } catch (JSONException e) {
            e(e.toString() + "\n\njson = " + json);
        }
    }

    /**
     * 采用orhanobut/logger的xml解析方案
     *
     * @param xml String
     */
    @Override
    public void xml(String xml) {
        if (TextUtils.isEmpty(xml)) {
            d("XML{xml is empty}");
            return;
        }
        if (xml.length() < 80) {
            d(xml);
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            e(e.toString() + "\n\nxml = " + xml);
        }
    }

    /**
     * 打印日志
     *
     * @param type int
     * @param tag  String
     * @param msg  String
     */
    private void printLog(@LogLevel.LogLevelType int type, String tag, String msg) {
        switch (type) {
            case TYPE_VERBOSE:
                Log.v(tag, msg);
                break;
            case TYPE_DEBUG:
                Log.d(tag, msg);
                break;
            case TYPE_INFO:
                Log.i(tag, msg);
                break;
            case TYPE_WARM:
                Log.w(tag, msg);
                break;
            case TYPE_ERROR:
                Log.e(tag, msg);
                break;
            case TYPE_WTF:
                Log.wtf(tag, msg);
                break;
            default:
                break;
        }
    }

}
