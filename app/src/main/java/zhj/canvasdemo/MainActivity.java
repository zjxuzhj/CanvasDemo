package zhj.canvasdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PieView pieView = (PieView) findViewById(R.id.PieView);

        ArrayList<Pie> pieArrayList=new ArrayList<>();

        Pie pie=new Pie(50,"JAVA需求",getResources().getColor(R.color.chart_color_1));
        pieArrayList.add(pie);

        Pie pie1=new Pie(30,"H5需求",getResources().getColor(R.color.chart_color_2));
        pieArrayList.add(pie1);

        Pie pie2=new Pie(10,"iOS需求",getResources().getColor(R.color.chart_color_3));
        pieArrayList.add(pie2);

        Pie pie3=new Pie(10,"Android需求",getResources().getColor(R.color.chart_color_4));
        pieArrayList.add(pie3);

        pieView.SetPie(pieArrayList);


        //第二个饼图
        PieView pieView2 = (PieView) findViewById(R.id.PieView2);
        ArrayList<Pie> pieArrayList2=new ArrayList<>();
        pieArrayList2.add(new Pie(20,"Android高级工程师",getResources().getColor(R.color.red)));
        pieArrayList2.add(new Pie(80,"Android小白",getResources().getColor(R.color.blue)));
        pieView2.SetPie(pieArrayList2);
    }
}
