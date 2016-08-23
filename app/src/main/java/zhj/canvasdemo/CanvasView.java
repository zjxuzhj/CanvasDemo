package zhj.canvasdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by HongJay on 2016/8/22.
 */
public class CanvasView extends View {

    private Paint mPaint;

    //在代码中定义和使用时用到的
    public CanvasView(Context context) {
        super(context);
        init();
    }

    //在xml中定义我们的自定义属性时用到
    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //在构造函数中初始化画笔
    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
    }

    //在View中画出我们需要的内容
    @Override
    protected void onDraw(Canvas canvas) {

        RectF rectF = new RectF(100, 100, 400, 400);
        mPaint.setAntiAlias(true); //抗锯齿
        mPaint.setStrokeWidth(5);//设置线宽
        mPaint.setColor(0xFFCCFF00);//设置颜色
        mPaint.setStyle(Paint.Style.FILL);//默认设置画笔为填充模式

        canvas.drawArc(rectF, 0, 110, true, mPaint);
        mPaint.setColor(0xff8bc5ba);//设置颜色
        canvas.drawArc(rectF, 110, 50, true, mPaint);
        mPaint.setColor( 0xFF800000);//设置颜色
        canvas.drawArc(rectF, 160, 80, true, mPaint);
        mPaint.setColor(0xFFFF8C69);//设置颜色
        canvas.drawArc(rectF, 240, 120, true, mPaint);
    }
}
