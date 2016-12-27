package com.mingxiu.log;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.mingxiu.log.bean.User;
import com.mingxiu.logutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_configAllowLog)
    CheckBox mBtnConfigAllowLog;
    @BindView(R.id.btn_configShowBorders)
    CheckBox mBtnConfigShowBorders;

    @BindView(R.id.et_tag)
    EditText mEtTag;
    @BindView(R.id.btn_String)
    Button mBtnString;
    @BindView(R.id.btn_Object)
    Button mBtnObject;
    //    @BindView(R.id.btn_list)
//    Button mBtnList;
    @BindView(R.id.btn_listString)
    Button mBtnListString;
    @BindView(R.id.btn_json)
    Button mBtnJson;
    @BindView(R.id.btn_xml)
    Button mBtnXml;
    private int TYPE;
    private String string;
    private String[] strings;
    String str = "\n                                                                    ┏┓    ┏┓         \n" +
            "                                                                ┏━┛┻━━┛┻┓       \n" +
            "                                                                ┃              ┃       \n" +
            "                                                                ┃    ━━━    ┃       \n" +
            "                                                                ┃              ┃       \n" +
            "                                                                ┃  ┳┛  ┗┳  ┃       \n" +
            "                                                                ┃              ┃       \n" +
            "                                                                ┃    ━┻━    ┃       \n" +
            "                                                                ┃              ┃       \n" +
            "                                                                ┗━┓     ┏━┛       \n" +
            "                                                                    ┃     ┃神兽保佑     \n" +
            "                                                                    ┃     ┃代码无BUG！   \n" +
            "                                                                    ┃     ┗━━━━━━━━┓     \n" +
            "                                                                    ┃                       ┣┓    \n" +
            "                                                                    ┃                       ┏┛    \n" +
            "                                                                    ┗━┓┓┏━━━━┳┓┏┛     \n" +
            "                                                                        ┃┫┫        ┃┫┫        \n" +
            "                                                                        ┗┻┛        ┗┻┛ ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        LogUtils.getLogConfig()
//                .configAllowLog(true)
//                .configShowBorders(true)
//                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")
//                .configTagPrefix("測試")
//                .configLevel(LogLevel.TYPE_VERBOSE);

        setUp();
    }

    private void setUp() {
        AppCompatSpinner mSpConfigLevel = (AppCompatSpinner) findViewById(R.id.sp_configLevel);
        mBtnConfigAllowLog.setChecked(true);
        mBtnConfigShowBorders.setChecked(true);
        strings = new String[]{"TYPE_VERBOSE", "TYPE_DEBUG", "TYPE_INFO", "TYPE_WARM", "TYPE_ERROR", "TYPE_WTF"};
        mSpConfigLevel.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strings));
        mSpConfigLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                string = strings[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick({R.id.btn_String, R.id.btn_Object, R.id.btn_list, R.id.btn_listString, R.id.btn_json, R.id.btn_xml,R.id.btn_intent})
    public void onClick(View view) {
        LogUtils.getLogConfig()
                .configAllowLog(mBtnConfigAllowLog.isChecked())
                .configShowBorders(mBtnConfigShowBorders.isChecked())
//                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")
//                .configTagPrefix(TextUtils.isEmpty(mEtTag.getText().toString()) ? "測試Tag" : mEtTag.getText().toString())
                .configLevel(getTYPE(string));
        LogUtils.tag(TextUtils.isEmpty(mEtTag.getText().toString()) ? "" : mEtTag.getText().toString());

        switch (view.getId()) {
            case R.id.btn_String:
                LogWriterString();
                break;
            case R.id.btn_Object:
                LogWriterObject();
                break;
            case R.id.btn_intent:
                LogWriterIntent();
                break;
            case R.id.btn_list:
                LogWriterList();
                break;
            case R.id.btn_listString:
                LogWriterListString();
                break;
            case R.id.btn_json:
                LogWriterJson();
                break;
            case R.id.btn_xml:
                LogWriterXml();
                break;
        }
    }


    //打印字符串
    private void LogWriterString() {
        LogUtils.d("春运火车票今开售 腊月二十八的票最难抢到");
//        com.apkfuns.logutils.LogUtils.getLogConfig()
//                .configAllowLog(true)
//                .configTagPrefix("MyAppName")
//                .configShowBorders(true)
//                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")
//                .configLevel(LogLevel.TYPE_VERBOSE);
//        com.apkfuns.logutils.LogUtils.d("春运火车票今开售 腊月二十八的票最难抢到",new User("小明", "13872829574"),new User("小明", "13872829574"),new User("小明", "13872829574"));
    }

    //打印Object
    private void LogWriterObject() {
        LogUtils.d(new User("小明", "13872829574"));
    }
    //打印Intent
    private void LogWriterIntent() {
        LogUtils.d(getIntent());
    }

    //打印集合
    private void LogWriterList() {
        List<User> users = new ArrayList<>();
        users.add(new User("小明", "13872829574"));
        users.add(new User("小明", "13872829574"));
        users.add(new User("小明", "13872829574"));
        LogUtils.d(users);
    }

    //打印數組
    private void LogWriterListString() {
        LogUtils.d(strings);
    }

    //打印Json
    private void LogWriterJson() {
        LogUtils.json("{\"bidder\":{\"id\":2728,\"demandId\":3075,\"userId\":5641,\"price\":123456,\"createTime\":1482480234000,\"state\":2,\"stateName\":\"中标\",\"desc\":\"呵呵黑得给的呵呵呵\",\"userName\":\"13872829574\",\"userHeadImg\":\"https://img.paralworld.com/upload/avatars/default/default_girl.png\",\"totalBidNumber\":null,\"invoiceSample\":\"\",\"companyName\":\"\",\"coupon\":null,\"useCoupon\":false,\"agent\":false},\"detail\":{\"demandId\":3075,\"orderNo\":\"20161223160230108924\",\"createTime\":1482480149000,\"title\":\"测试需求变更\",\"type\":1,\"typeName\":\"道路\",\"parentId\":9,\"parentTypeName\":\"效果图\",\"state\":12,\"stateName\":\"进行中\",\"isInvoice\":0,\"budget\":123456.0,\"price\":123456.0,\"employerUid\":6503,\"employerName\":\"UID6503\",\"employerHeadImg\":\"https://img.paralworld.com/upload/avatars/default/default_girl.png\",\"facilitatorUid\":5641,\"facilitatorName\":\"13872829574\",\"facilitatorHeadImg\":\"https://img.paralworld.com/upload/avatars/default/default_girl.png\",\"bidNumber\":1,\"biddingMode\":1,\"biddingModeName\":\"招标\",\"desc\":\"呵呵黑得和的活动很多很多很多话的和\",\"publishFileSrc\":\"http://o6nc2brpt.bkt.clouddn.com/1482481282073.jpg|http://o6nc2brpt.bkt.clouddn.com/1482480572013.jpg\",\"inviteIds\":\"\",\"completeFileSrc\":\"\",\"isBid\":1,\"isBidSelf\":false,\"inteval\":3,\"intevalSec\":null,\"intevalCloseSec\":null,\"collectId\":0,\"isCollected\":0,\"isBoutique\":null,\"applyCloseTime\":null,\"completeTime\":null,\"bidEndTime\":1485162000000,\"workEndTime\":1482566712000,\"qzState\":null,\"applyChangeTime\":null,\"isGuarantee\":false,\"isMaker\":false,\"ckState\":0,\"ckStateName\":\"\",\"changedPrice\":0.0,\"descNew\":\"\",\"publishFileSrcNew\":\"\",\"descEffect\":\"呵呵黑得和的活动很多很多很多话的和\",\"publishFileSrcEffect\":\"http://o6nc2brpt.bkt.clouddn.com/1482481282073.jpg|http://o6nc2brpt.bkt.clouddn.com/1482480572013.jpg|http://o6nc2brpt.bkt.clouddn.com/1482485687301.jpg\",\"demandChangeCounts\":3,\"invoiceTitle\":null,\"invoiceCompanyName\":null,\"afterTaxAmount\":null,\"deliverState\":0,\"afterTaxAmountTotal\":0,\"taxRate\":0.0,\"bidPrice\":0.0,\"invoiceSample\":\"\",\"companyName\":\"\",\"couponMoney\":0,\"couponAccount\":0,\"staysNumber\":3,\"staysContent\":\"\",\"contractType\":1,\"isStage\":false,\"staysNumberChanging\":0,\"staysContentChanging\":\"\",\"isStageChanging\":false,\"useCoupon\":false,\"agent\":false},\"state_code\":200,\"message\":\"查询订单信息成功\",\"list\":[]}");
    }

    //打印Xml
    private void LogWriterXml() {
        LogUtils.xml(" <Button\n" +
                "        android:id=\"@+id/btn_Object\"\n" +
                "        android:layout_width=\"match_parent\"\n" +
                "        android:layout_height=\"wrap_content\"\n" +
                "        android:textAllCaps=\"false\"\n" +
                "        android:text=\"Object\"/>");
    }

    static void drawHeart(int n) {
        int i, j;
        for (i = 1 - (n >> 1); i <= n; i++)
            if (i > 0) {
                for (j = 0; j < i; j++)
                    System.out.print("  ");
                for (j = 1; j <= 2 * (n - i) + 1; j++)
                    if (j == 1 || j == 2 * (n - i) + 1)
                        System.out.print(" *");
                    else
                        System.out.print("  ");
                System.out.println("");
            } else if (i == 0) {
                System.out.print(" *");
                for (j = 1; j < n; j++)
                    System.out.print("  ");
                System.out.print(" *");
                for (j = 1; j < n; j++)
                    System.out.print("  ");
                System.out.print(" *");
                System.out.println("");
            } else {
                for (j = i; j < 0; j++)
                    System.out.print("  ");
                for (j = 1; j <= n + 2 * i + 1; j++)
                    if (i == 1 - (n >> 1))
                        System.out.print(" *");
                    else if (j == 1 || j == n + 2 * i + 1)
                        System.out.print(" *");
                    else
                        System.out.print("  ");
                for (j = 1; j <= -1 - 2 * i; j++)
                    System.out.print("  ");
                for (j = 1; j <= n + 2 * i + 1; j++)
                    if (i == 1 - (n >> 1))
                        System.out.print(" *");
                    else if (j == 1 || j == n + 2 * i + 1)
                        System.out.print(" *");
                    else
                        System.out.print("  ");
                System.out.println("");
            }
    }

    private void right(String str) {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(100 * 3);
                String str1 = " ";
                for (int j = 0; j < i; j++) {
                    str1 += "　    ";
                }
                LogUtils.d(str.replace("\n", "\n " + str1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        left(str);
    }

    private void left(String str) {
        for (int i = 10; i > 0; i--) {
            try {
                Thread.sleep(100 * 3);
                String str1 = " ";
                for (int j = 0; j < i; j++) {
                    str1 += "　    ";
                }
                LogUtils.d(str.replace("\n", "\n " + str1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        right(str);
    }

    private int getTYPE(String type) {
        for (int i = 0; i < strings.length; i++) {
            if (strings[i] == type) {
                return i + 1;
            }
        }
        return 0;
    }


}
