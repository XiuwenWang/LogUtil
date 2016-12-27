package com.mingxiu.logutils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.MissingFormatArgumentException;

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
class Logger implements Printer {

    private LogConfigImpl mLogConfig;
    private final ThreadLocal<String> localTags = new ThreadLocal<>();

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
            localTags.set(tag);
        }
        return this;
    }

    /**
     * 打印字符串
     *
     * @param type int
     * @param msg  String
     * @param args Object...
     */
    private synchronized void logString(@LogLevel.LogLevelType int type, String msg, Object... args) {
        logSomeLineString(type,msg,args);
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
        } else {
            logSomeLineString(type, objectToString(object));
        }
    }


    //单行
    private void logOneLineString(@LogLevel.LogLevelType int type, String msg, Object... args) {
        if (mLogConfig.isEnable() && type > mLogConfig.getLogLevel()) {
            if (args.length > 0) {
                try {
                    msg = String.format(msg, args);
                } catch (MissingFormatArgumentException e) {
                    e(e);
                }
            }
            String tag = generateTag();
            if (msg.length() > Constant.LINE_MAX) {
                for (String subMsg : largeStringToList(msg)) {
                    printLog(type, tag, printDividingLine(DIVIDER_NORMAL) + getTopStackInfo() + ": " + subMsg);
                }
            } else {
                printLog(type, tag, printDividingLine(DIVIDER_NORMAL) + getTopStackInfo() + ": " + msg);
            }
        }
    }
    //多行
    private void logSomeLineString(@LogLevel.LogLevelType int type, String msg, Object... args) {
        if (args.length > 0) {
            try {
                msg = String.format(msg, args);
            } catch (MissingFormatArgumentException e) {
                e(e);
            }
        }

        if (mLogConfig.isEnable() && type > mLogConfig.getLogLevel()) {
            String tag = generateTag();
            if (mLogConfig.isShowBorder()) {//显示边界
                printLog(type, tag, printDividingLine(DIVIDER_TOP));
                printLog(type, tag, printDividingLine(DIVIDER_NORMAL) + getTopStackInfo());
                printLog(type, tag, printDividingLine(DIVIDER_CENTER));
                for (String subMsg : largeStringToList(msg)) {
                    for (String sub : subMsg.split(Constant.BR)) {
                        printLog(type, tag, printDividingLine(DIVIDER_NORMAL) + sub);
                    }
                }
                printLog(type, tag, printDividingLine(DIVIDER_BOTTOM));
            } else {                       //不显示边界
                for (String subMsg : largeStringToList(msg)) {
                    for (String sub : subMsg.split(Constant.BR)) {
                        printLog(type, tag, printDividingLine(DIVIDER_NORMAL) + sub);
                    }

                }
            }
        }

    }




    /**
     * 自动生成tag
     *
     * @return String LogUtils
     */
    private String generateTag() {
        String tempTag = localTags.get();
        if (!TextUtils.isEmpty(tempTag)) {
            localTags.remove();
            return tempTag;
        }
        return mLogConfig.getTagPrefix();
    }

    /**
     * 获取当前activity栈信息
     *
     * @return caller
     */
    private StackTraceElement getCurrentStackTrace() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int stackOffset = getStackOffset(trace, LogUtils.class);
        if (stackOffset == -1) {
            stackOffset = getStackOffset(trace, Logger.class);
            if (stackOffset == -1) {
                return null;
            }
        }
        return trace[stackOffset];
    }

    /**
     * 获取最顶部stack信息
     * str＝str.substring(int beginIndex);
     * 截取掉str从首字母起长度为beginIndex的字符串，将剩余字符串赋值给str；
     * str＝str.substring(int beginIndex，int endIndex);
     * 截取str中从beginIndex开始至endIndex结束时的字符串，并将其赋值给str;
     *
     * @return String ║ MainActivity$override.LogWriterString(MainActivity.java:141):
     */
    private String getTopStackInfo() {
        String customTag = mLogConfig.getFormatTag(getCurrentStackTrace());
        if (customTag != null) {
            return customTag;
        }
        StackTraceElement caller = getCurrentStackTrace();
        String stackTrace;
        String tag = "%s.%s%s";
        if (caller != null) {
            stackTrace = caller.toString();
            stackTrace = stackTrace.substring(stackTrace.lastIndexOf('('), stackTrace.length());
            String callerClazzName = caller.getClassName();
            callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
            tag = String.format(tag, callerClazzName, caller.getMethodName(), stackTrace);
        }

        return tag;
    }

    private int getStackOffset(StackTraceElement[] trace, Class cla) {
        for (int i = Constant.MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (cla.equals(Logger.class) && i < trace.length - 1 && trace[i + 1].getClassName()
                    .equals(Logger.class.getName())) {
                continue;
            }
            if (name.equals(cla.getName())) {
                return ++i;
            }
        }
        return -1;
    }

    @Override
    public void d(String message, Object... args) {
        logOneLineString(TYPE_DEBUG, message, args);
    }

    @Override
    public void d(Object object) {
        logObject(TYPE_DEBUG, object);
    }

    @Override
    public void e(String message, Object... args) {
        logString(TYPE_ERROR, message, args);
    }

    @Override
    public void e(Object object) {
        logObject(TYPE_ERROR, object);
    }

    @Override
    public void w(String message, Object... args) {
        logString(TYPE_WARM, message, args);
    }

    @Override
    public void w(Object object) {
        logObject(TYPE_WARM, object);
    }

    @Override
    public void i(String message, Object... args) {
        logString(TYPE_INFO, message, args);
    }

    @Override
    public void i(Object object) {
        logObject(TYPE_INFO, object);
    }

    @Override
    public void v(String message, Object... args) {
        logString(TYPE_VERBOSE, message, args);
    }

    @Override
    public void v(Object object) {
        logObject(TYPE_VERBOSE, object);
    }

    @Override
    public void wtf(String message, Object... args) {
        logString(TYPE_WTF, message, args);
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
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String msg = jsonObject.toString(indent);
                logSomeLineString(TYPE_DEBUG, msg);
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
