package com.mingxiu.logutils.utils;


import android.text.TextUtils;

import com.mingxiu.logutils.Constant;
import com.mingxiu.logutils.LogConfigImpl;
import com.mingxiu.logutils.LogUtils;
import com.mingxiu.logutils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengwei on 16/4/19.
 */
public class Utils {

    // 分割线方位
    public static final int DIVIDER_TOP = 1;
    public static final int DIVIDER_BOTTOM = 2;
    public static final int DIVIDER_CENTER = 4;
    public static final int DIVIDER_NORMAL = 3;
    public final static ThreadLocal<String> localTags = new ThreadLocal<>();


    /**
     * 打印分割线
     *
     * @param dir
     * @return
     */
    public static String printDividingLine(int dir) {
        switch (dir) {
            case DIVIDER_TOP:
                return "╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════";
            case DIVIDER_BOTTOM:
                return "╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════";
            case DIVIDER_NORMAL:
                return "║ ";
            case DIVIDER_CENTER:
                return "╟───────────────────────────────────────────────────────────────────────────────────────────────────────────────────";
            default:
                break;
        }
        return "";
    }


    /**
     * 长字符串转化为List
     *
     * @param msg
     * @return
     */
    public static List<String> largeStringToList(String msg) {
        List<String> stringList = new ArrayList<>();
        int index = 0;
        int maxLength = Constant.LINE_MAX;
        int countOfSub = msg.length() / maxLength;
        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + maxLength);
                stringList.add(sub);
                index += maxLength;
            }
            stringList.add(msg.substring(index, msg.length()));
        } else {
            stringList.add(msg);
        }
        return stringList;
    }
    /**
     * 自动生成tag
     *
     * @return String LogUtils
     */
    public static String generateTag() {
        String tempTag = localTags.get();
        if (!TextUtils.isEmpty(tempTag)) {
            localTags.remove();
            return tempTag;
        }
        return LogConfigImpl.getInstance().getTagPrefix();
    }

    /**
     * 获取当前activity栈信息
     *
     * @return caller
     */
    private static StackTraceElement getCurrentStackTrace() {
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
    public static String getTopStackInfo() {
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


    private static int getStackOffset(StackTraceElement[] trace, Class cla) {
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

}
