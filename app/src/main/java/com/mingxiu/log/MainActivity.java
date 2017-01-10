package com.mingxiu.log;

import android.content.Intent;
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

    @OnClick({R.id.btn_String, R.id.btn_Object, R.id.btn_list, R.id.btn_listString, R.id.btn_json, R.id.btn_xml, R.id.btn_intent})
    public void onClick(View view) {
        LogUtils.getLogConfig()
                .configAllowLog(mBtnConfigAllowLog.isChecked())
                .configShowBorders(mBtnConfigShowBorders.isChecked())
                .configTagPrefix(TextUtils.isEmpty(mEtTag.getText().toString()) ? "測試Tag" : mEtTag.getText().toString())
                .configLevel(getTYPE(string));
        LogUtils.tag(TextUtils.isEmpty(mEtTag.getText().toString()) ? "" : mEtTag.getText().toString());

        switch (view.getId()) {
            case R.id.btn_String:
                startActivityForResult(new Intent(this, OtherActivity.class), 520);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.d("requestCode = " + requestCode);
    }

    //打印字符串
    private void LogWriterString() {

        String s = "{\"state_code\":411,\"message\":\"https://my.oschina.net/swords/blog/117357\"}";
        LogUtils.json(s);
        LogUtils.d("s.length() = " + s.length());
        String str = "";
        LogUtils.d("str.length() = " + str.length());
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
        LogUtils.json("{\"state_code\":447,\"message\":\"每位代付人只能邀请一次\"}");
        LogUtils.json("{\"state_code\":447,\"message\":\"每位代付人只能邀请一次\",\"messages\":\"每位代付人只能邀请一次\",\"messagess\":\"每位代付人只能邀请一次\"}");
        LogUtils.json("{\"bidder\":{\"id\":2594,\"demandId\":2941,\"userId\":5492,\"price\":11111,\"createTime\":1481595489000,\"state\":2,\"stateName\":\"中标\",\"desc\":\"很多时候上课上的看法\\n说的分手大师翻领设计的弗兰克\\n说的分手的分手的空间上\\n说的分手的分手放得开；\\n水电费水电费水电费的司法考试的\\n水淀粉\",\"userName\":\"浪里个浪浪\",\"userHeadImg\":\"https://img.paralworld.com/upload/avatars/5492/medium.jpg?1644883338\",\"totalBidNumber\":null,\"invoiceSample\":\"\",\"companyName\":\"\",\"coupon\":null,\"agent\":false,\"useCoupon\":false},\"detail\":{\"demandId\":2941,\"orderNo\":\"20161212140825957141\",\"createTime\":1481522899000,\"title\":\"测试TextScrollView bar\",\"type\":1,\"typeName\":\"道路\",\"parentId\":9,\"parentTypeName\":\"效果图\",\"state\":12,\"stateName\":\"进行中\",\"isInvoice\":0,\"budget\":123456.0,\"price\":11111.0,\"employerUid\":6503,\"employerName\":\"UID6503\",\"employerHeadImg\":\"https://img.paralworld.com/upload/avatars/default/default_girl.png\",\"facilitatorUid\":5492,\"facilitatorName\":\"浪里个浪浪\",\"facilitatorHeadImg\":\"https://img.paralworld.com/upload/avatars/5492/medium.jpg?1644883338\",\"bidNumber\":2,\"biddingMode\":1,\"biddingModeName\":\"招标\",\"desc\":\"hhshs民工您婆媳嘻嘻嘻嘻XP我婆婆送您破POSXPXP明你OK您婆婆上hhshs民工您婆媳嘻嘻嘻嘻XP我婆婆送您破POSXPXP明你OK您婆婆上午无语中mins午无语中minhhshs民工您婆媳嘻嘻嘻嘻XP我婆婆送您破POSXPXP明你OK您婆婆上hhshs民工您婆媳嘻嘻嘻嘻XP我婆婆送您破POSXPXP明你OK您婆婆上hhshs民工您婆媳嘻嘻嘻嘻XP我婆婆送您破POSXPXP明你OK您婆婆上hhshs民工hhshs民工您婆媳嘻嘻嘻嘻XP我婆婆送您破POSXPXP明你婆媳嘻嘻嘻嘻XP我婆婆送您破POSXPXP明你OK您婆婆上午无语中mins午无语中mins午无语中mins午无语中minss\",\"publishFileSrc\":\"http://o6nc2brpt.bkt.clouddn.com/1481523477698.jpg|http://o6nc2brpt.bkt.clouddn.com/1481523086787.jpg|http://o6nc2brpt.bkt.clouddn.com/1481523642002.jpg|http://o6nc2brpt.bkt.clouddn.com/1481523538590.jpg|http://o6nc2brpt.bkt.clouddn.com/1481523832415.jpg\",\"inviteIds\":\"\",\"completeFileSrc\":\"\",\"isBid\":1,\"isBidSelf\":false,\"inteval\":-9,\"intevalSec\":null,\"intevalCloseSec\":null,\"collectId\":0,\"isCollected\":0,\"isBoutique\":null,\"applyCloseTime\":null,\"completeTime\":null,\"bidEndTime\":1484204400000,\"workEndTime\":1513062000000,\"qzState\":null,\"applyChangeTime\":null,\"isGuarantee\":false,\"isMaker\":false,\"ckState\":0,\"ckStateName\":\"\",\"changedPrice\":0.0,\"descNew\":\"\",\"publishFileSrcNew\":\"\",\"descEffect\":\"hhshs民工您婆媳嘻嘻嘻嘻XP我婆婆送您破POSXPXP明你OK您婆婆上hhshs民工您婆媳嘻嘻嘻嘻XP我婆婆送您破POSXPXP明你OK您婆婆上午无语中mins午无语中minhhshs民工您婆媳嘻嘻嘻嘻XP我婆婆送您破POSXPXP明你OK您婆婆上hhshs民工您婆媳嘻嘻嘻嘻XP我婆婆送您破POSXPXP明你OK您婆婆上hhshs民工您婆媳嘻嘻嘻嘻XP我婆婆送您破POSXPXP明你OK您婆婆上hhshs民工hhshs民工您婆媳嘻嘻嘻嘻XP我婆婆送您破POSXPXP明你婆媳嘻嘻嘻嘻XP我婆婆送您破POSXPXP明你OK您婆婆上午无语中mins午无语中mins午无语中mins午无语中minss\",\"publishFileSrcEffect\":\"\",\"demandChangeCounts\":1,\"invoiceTitle\":null,\"invoiceCompanyName\":null,\"afterTaxAmount\":null,\"deliverState\":0,\"afterTaxAmountTotal\":0,\"taxRate\":null,\"bidPrice\":0.0,\"invoiceSample\":\"\",\"companyName\":\"\",\"couponMoney\":0,\"couponAccount\":0,\"staysNumber\":3,\"staysContent\":\"各地热||iOS哦傻X\",\"contractType\":1,\"isStage\":true,\"staysNumberChanging\":0,\"staysContentChanging\":\"\",\"isStageChanging\":false,\"agent\":false,\"useCoupon\":false},\"state_code\":200,\"message\":\"查询订单信息成功\",\"list\":[{\"id\":2591,\"demandId\":2941,\"userId\":5641,\"price\":123456,\"createTime\":1482137733000,\"state\":3,\"stateName\":\"被淘汰\",\"desc\":\"哦POSXP我是我是形容orz是我婆婆日子微信婆婆婆婆婆婆in哦POSXP我是我是形容orz是我婆婆日子微信婆婆婆婆婆婆in哦POSXP我是我是形容orz是我婆婆日子微信哦POSXP我是我是形容orz是我婆婆日子微信婆婆婆婆婆婆in哦POSXP我是我是形容orz是我婆婆哦POSXP我是我是形容orz是我婆婆日子微信婆婆婆婆婆婆in哦POSXP我是我是形容orz是我哦POSXPs\\n阿达大师大师大师vvvvhhhhh\",\"userName\":\"13872829574\",\"userHeadImg\":\"https://img.paralworld.com/upload/avatars/default/default_girl.png\",\"totalBidNumber\":null,\"invoiceSample\":\"\",\"companyName\":\"\",\"coupon\":null,\"agent\":false,\"useCoupon\":false}]}");
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
