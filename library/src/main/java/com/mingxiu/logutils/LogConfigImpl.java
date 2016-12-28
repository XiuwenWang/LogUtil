package com.mingxiu.logutils;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengwei on 16/3/4.
 * Log config
 */
public class LogConfigImpl implements LogConfig {

    private boolean enable = true;                  //是否允许日志输出
    private String tagPrefix = "LogUtils";          //日志log的前缀
    private boolean showBorder = true;              //是否显示边界
    @LogLevel.LogLevelType
    private int logLevel = LogLevel.TYPE_VERBOSE;   //日志显示等级
    private List<Parser> parseList;                 //自定义对象打印

    private static LogConfigImpl singleton;

    private LogConfigImpl() {
        parseList = new ArrayList<>();
    }

    public static LogConfigImpl getInstance() {
        if (singleton == null) {
            synchronized (LogConfigImpl.class) {
                if (singleton == null) {
                    singleton = new LogConfigImpl();
                }
            }
        }
        return singleton;
    }

    @Override
    public LogConfig configAllowLog(boolean allowLog) {
        this.enable = allowLog;
        return this;
    }

    @Override
    public LogConfig configTagPrefix(String prefix) {
        this.tagPrefix = prefix;
        return this;
    }


    @Override
    public LogConfig configShowBorders(boolean showBorder) {
        this.showBorder = showBorder;
        return this;
    }

    @Override
    public LogConfig configLevel(@LogLevel.LogLevelType int logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    @Override
    public LogConfig addParserClass(Class<? extends Parser>... classes) {
        // TODO: 16/3/12 判断解析类的继承关系
        for (Class<? extends Parser> cla : classes) {
            try {
                parseList.add(0, cla.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public boolean isEnable() {
        return enable;
    }

    public String getTagPrefix() {
        return tagPrefix;
    }

    public boolean isShowBorder() {
        return showBorder;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public List<Parser> getParseList() {
        return parseList;
    }
}
