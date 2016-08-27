package zhj.canvasdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HongJay on 2016/8/23.
 */
public class PieView extends View {

    private Paint mPaint;
    //饼图和矩形的距离
    private final int PIE_RECT_PADDING = getResources().getDimensionPixelSize(R.dimen.pie_rect_padding);
    //矩形的宽度
    private final int RECT_WIDTH = getResources().getDimensionPixelSize(R.dimen.rect_width);
    //矩形和文字的距离
    private final int RECT_TEXT_PADDING = getResources().getDimensionPixelSize(R.dimen.rect_text_padding);
    //文字的大小
    private final int TEXT_SIZE = getResources().getDimensionPixelSize(R.dimen.text_size);
    //文字的垂直距离
    private final int TEXT_VERTICAL_PADDING = getResources().getDimensionPixelSize(R.dimen.text_vertical_padding);
    //得到文字颜色
    private final int TEXT_COLOR = getResources().getColor(R.color.gray_dark);
    //第一个文字和控件顶部的距离
    private float mPadding;
    //饼图的半径
    private int mPieRadios;
    //所有数值的总和
    private int mMaxValue;
    //饼图开始的角度
    private int mStartAngle;
    //文字的宽度
    private int mTextWidth;
    //控件半高
    private int mControlHalfHeight;
    //当前索引
    private int mCurrentIndex;
    //左边距
    private int mRectMarginLeft;
    private int mTextMarginLeft;
    //当前颜色
    private int mCurrentColor;
    //圆的范围
    private RectF oval;
    //最长的字符串
    private String mMaxString;

    //集合
    private List<Integer> mPieColorList;
    private List<Integer> mPieValue;
    private List<String> mSrtingList;
    private ArrayList<Pie> mPieArrayList;

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPieColorList = new ArrayList<>();
        mPieValue = new ArrayList<>();
        mSrtingList = new ArrayList<>();
        mPaint = new Paint();
        mMaxString = "";
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(20);//画笔宽度
        mPaint.setAntiAlias(true);//抗锯齿

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mTextWidth = (int) mPaint.measureText(mMaxString);

        mControlHalfHeight = h / 2;
        //饼图半径
        mPieRadios = mControlHalfHeight - 5;
        //控件内容宽度
        int contentWidth = mPieRadios * 2 + PIE_RECT_PADDING + RECT_WIDTH + RECT_TEXT_PADDING + mTextWidth;
        //内容的左边距
        int contentMarginLeft = (w - contentWidth) / 2;
        //矩形的左边距
        mRectMarginLeft = contentMarginLeft + mPieRadios * 2 + PIE_RECT_PADDING;
        //文字的左边距
        mTextMarginLeft = mRectMarginLeft + RECT_WIDTH + RECT_TEXT_PADDING;
        //第一个文字和控件顶部的距离
        mPadding = h / mPieArrayList.size() * 0.8f;
        //控制画圆的范围，圆心为（左边距+圆半径，半高）
        oval = new RectF(contentMarginLeft, mControlHalfHeight - mPieRadios,
                contentMarginLeft + mPieRadios * 2, mControlHalfHeight + mPieRadios);
    }

    public void SetPie(ArrayList<Pie> pieArrayList) {
        mPieArrayList = pieArrayList;
        //遍历需要绘制的扇形的集合，得到数据
        for (Pie mPie : mPieArrayList) {
            mPieColorList.add(mPie.PieColor);
            mPieValue.add(mPie.PieValue);
            mSrtingList.add(mPie.PieString);

            //得到最长的字符串
            if (mMaxString.length() < mPie.PieString.length())
                mMaxString = mPie.PieString;


        }

        //使用postInvalidate可以直接在主线程中更新界面
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mStartAngle = -90;
        mCurrentIndex = 0;
        mMaxValue = 100;
        mPaint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < mPieValue.size(); i++) {
            mCurrentColor = mPieColorList.get(mCurrentIndex);
            Log.i("mCurrentColor", "onDraw: " + mCurrentColor);
            drawPie(canvas, mPieValue.get(mCurrentIndex));
            drawRect(canvas);
            drawText(canvas, mSrtingList.get(mCurrentIndex));
            mCurrentIndex++;
        }

    }

    //绘制饼图
    private void drawPie(Canvas canvas, int amount) {
        mPaint.setColor(mCurrentColor);
        mPaint.setStyle(Paint.Style.FILL);
        int angle = (int) (360f * amount / mMaxValue);
        Log.d("angle", "drawPie: " + angle);
        canvas.drawArc(oval, mStartAngle, angle, true, mPaint);
        mStartAngle += angle;
    }

    //绘制矩形
    private void drawRect(Canvas canvas) {

        if (mCurrentIndex == 0) {
            RectF rect = new RectF(mRectMarginLeft, mPadding,
                    mRectMarginLeft + RECT_WIDTH, mPadding + RECT_WIDTH);
            canvas.drawRect(rect, mPaint);
        } else {
            RectF rect = new RectF(mRectMarginLeft, (mCurrentIndex) * TEXT_VERTICAL_PADDING + mPadding,
                    mRectMarginLeft + RECT_WIDTH, (mCurrentIndex) * TEXT_VERTICAL_PADDING + mPadding + RECT_WIDTH);
            canvas.drawRect(rect, mPaint);
        }
    }

    //绘制文字
    private void drawText(Canvas canvas, String text) {

        mPaint.setColor(TEXT_COLOR);
        if (mCurrentIndex == 0) {
            canvas.drawText(text, mTextMarginLeft, mPadding + TEXT_SIZE * 0.8f, mPaint);
        } else {
            canvas.drawText(text, mTextMarginLeft, (mCurrentIndex) * TEXT_VERTICAL_PADDING + mPadding + TEXT_SIZE * 0.8f, mPaint);
        }
    }
}
