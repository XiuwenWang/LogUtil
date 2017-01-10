package com.mingxiu.log;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingxiu.log.widget.CustomBarChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
 * 创建时间：2016/12/28
 * Email：13872829574@163.com
 * 内容描述：
 * 修改人：a.wen
 * 修改时间：${DATA}
 * 修改备注：
 * 修订历史：1.0
 */

public class OtherActivity extends AppCompatActivity {

    @BindView(R.id.CustomBarChart)
    LinearLayout mCustomBarChart;
    @BindView(R.id.btn_back)
    TextView mBtnBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ohter);
        ButterKnife.bind(this);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String strTxt = "aaaaa";
        String source = "<p><font color=\"#aabb00\">颜色</font><font color=\"#aabb00\">" + strTxt + "</font><font color=\"#000000\">颜色</font></p>";
        mBtnBack.setText(Html.fromHtml(source));
        strTxt= "haha";
        mBtnBack.setText(Html.fromHtml(source));
        String[] xLabel = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
                "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
                "28", "29", "30", "31"};
        String[] yLabel = {"0", "100", "200", "300", "400", "500", "600", "700", "800", "900"};
        int[] data1 = {300, 500, 550, 500, 300, 700, 800, 750, 550, 600, 400, 300, 400, 600, 500,
                700, 300, 500, 550, 500, 300, 700, 800, 750, 550, 600, 400, 300, 400, 600, 500};
        List<int[]> data = new ArrayList<>();
        data.add(data1);
        List<Integer> color = new ArrayList<>();
        color.add(android.R.color.white);
        color.add(android.R.color.white);
        color.add(android.R.color.white);
        mCustomBarChart.addView(new CustomBarChart(this, xLabel, yLabel, data, color));

    }
}
