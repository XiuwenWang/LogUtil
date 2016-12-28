package com.mingxiu.logutils;



/**
 * Created by pengwei08 on 2015/7/16.
 * 日志管理器
 */
public final class LogUtils {

    private static Logger printer = new Logger();
    private static LogConfigImpl logConfig = LogConfigImpl.getInstance();

    /**
     * 选项配置
     *
     * @return
     */
    public static LogConfig getLogConfig() {
        return logConfig;
    }

    public static Printer tag(String tag) {
        return printer.setTag(tag);
    }

    public static void v(Object object) {
        printer.v(object);
    }

    public static void d(Object object) {
        printer.d(object);
    }

    public static void i(Object object) {
        printer.i(object);
    }

    public static void w(Object object) {
        printer.w(object);
    }

    public static void e(Object object) {
        printer.e(object);
    }

    public static void wtf(Object object) {
        printer.wtf(object);
    }

    /**
     * 打印json
     *
     * @param json
     */
    public static void json(String json) {
        printer.json(json);
    }

    /**
     * 输出xml
     * @param xml
     */
    public static void xml(String xml) {
        printer.xml(xml);
    }
}
