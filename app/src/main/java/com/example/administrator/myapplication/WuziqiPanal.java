package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class WuziqiPanal extends View {
    private float mLineHeight;
    private int mPanalWidth;
    private int MAX_LINE = 13; //棋盘大小
    private int MAX_COUNT_IN_LINE = 5;
    private Paint mPaint = new Paint();
    //棋子
    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;
    //棋子相对于格子的缩放大小
    private float ratio = 3 * 1.0f / 4;
    //落子记录
    private boolean mIsWhite = true;
    private ArrayList<Point> mWhiteArray = new ArrayList<>();
    private ArrayList<Point> mBlackArray = new ArrayList<>();
    private boolean mIsGameOver;
    private boolean mIsWhiteWinner;
    public WuziqiPanal(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    //初始化
    private void init(){
        //设置画笔颜色
        mPaint.setColor(0x88000000);//线颜色
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);//画线
        //初始化棋子
        mWhitePiece = BitmapFactory.decodeResource(getResources(),R.mipmap.stone_w2);
        mBlackPiece = BitmapFactory.decodeResource(getResources(),R.mipmap.stone_b1);
    }
    //自定义View的测量问题
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heightSize);
        if(widthMode == MeasureSpec.UNSPECIFIED){
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED){
            width = widthSize;
        }
        setMeasuredDimension(width, width);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanalWidth = w;
        mLineHeight = mPanalWidth * 1.0f / MAX_LINE;

        int pieceWidth = (int) (mLineHeight * ratio);
        //定制棋子大小
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece,pieceWidth,pieceWidth,false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece,pieceWidth,pieceWidth,false);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //利用重载
        drawBoard(canvas);
        drawPiece(canvas);
        checkGameOver();
    }
    //判断谁赢
    private void checkGameOver() {

        boolean whiteWin = checkFiveInLine(mWhiteArray);
        boolean blackWin = checkFiveInLine(mBlackArray);

        if(whiteWin || blackWin){
            mIsGameOver = true;
            mIsWhiteWinner = whiteWin;

            String text = mIsWhiteWinner? "白棋胜利": "黑棋胜利";
            Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show();
        }
    }
    //判断棋子状态
    private boolean checkFiveInLine(List<Point> points) {

        for(Point p: points){
            int x = p.x;
            int y = p.y;
        //4种赢法
            boolean win = checkHorizontal(x, y, points);
            if(win)
                return true;
            win = checkVertical(x, y, points);
            if(win)
                return true;
            win = checkLeftDiagonal(x, y, points);
            if(win)
                return true;
            win = checkRightDiagonal(x, y, points);
            if(win)
                return true;
        }
        return false;
    }
    //绘制棋子
    private void drawPiece(Canvas canvas) {
        for(int i = 0, n = mWhiteArray.size(); i < n; i++ ) {
            Point whitePoint = mWhiteArray.get(i);
            canvas.drawBitmap(mWhitePiece,
                    (whitePoint.x + (1-ratio)/2)*mLineHeight,
                    (whitePoint.y + (1-ratio)/2)*mLineHeight, null);
        }

        for(int i = 0, n = mBlackArray.size(); i < n; i++ ) {
            Point blackPoint = mBlackArray.get(i);
            canvas.drawBitmap(mBlackPiece,
                    (blackPoint.x + (1-ratio)/2)*mLineHeight,
                    (blackPoint.y + (1-ratio)/2)*mLineHeight, null);
        }
    }
    //绘制棋盘
    private void drawBoard(Canvas canvas) {

        int w = mPanalWidth;
        float lineHeight = mLineHeight;

        for(int i = 0; i < MAX_LINE; i++){
            int startX = (int)(lineHeight/2);
            int endX = (int)(w-lineHeight/2);
            int y = (int) ((0.5+i) * lineHeight);
            //画线
            canvas.drawLine(startX,y,endX,y,mPaint);
            canvas.drawLine(y,startX,y,endX,mPaint);
        }
    }
    //响应下子动作
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //结束就不能在下了
        if(mIsGameOver)
            return false;

        int action = event.getAction();
        if(action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            Point p = getValidPoint(x, y);
            //判断当点是否可下
            if(mWhiteArray.contains(p) || mBlackArray.contains(p))
                return false;
            //存放下棋状态
            if(mIsWhite)
                mWhiteArray.add(p);
            else
                mBlackArray.add(p);

            invalidate(); // 请求重绘
            mIsWhite = !mIsWhite;//双方转换

        }
        return true;
    }
    //判断当点是否可下
    private Point getValidPoint(int x, int y) {

        return new Point((int)(x / mLineHeight), (int)(y / mLineHeight));
    }
    //横向判断是否5子
    private boolean checkHorizontal(int x, int y, List<Point> points) {
        int count = 1;
        for(int i = 1; i < MAX_COUNT_IN_LINE; i++){
            if(points.contains(new Point(x-i, y)))//左
                count++;
            else
                break;
        }
        if(count == MAX_COUNT_IN_LINE)
            return true;
        for(int i = 1; i < MAX_COUNT_IN_LINE; i++){
            if(points.contains(new Point(x+i, y)))//右
                count++;
            else
                break;
        }
        if(count == MAX_COUNT_IN_LINE)
            return true;
        return false;
    }
    //竖向判断是否5子
    private boolean checkVertical(int x, int y, List<Point> points) {

        int count = 1;
        for(int i = 1; i < MAX_COUNT_IN_LINE; i++){
            if(points.contains(new Point(x, y-i)))//上
                count++;
            else
                break;
        }

        if(count == MAX_COUNT_IN_LINE)
            return true;

        for(int i = 1; i < MAX_COUNT_IN_LINE; i++){
            if(points.contains(new Point(x, y+i)))//下
                count++;
            else
                break;
        }

        if(count == MAX_COUNT_IN_LINE)
            return true;
        return false;
    }
    //左斜判断是否5子
    private boolean checkLeftDiagonal(int x, int y, List<Point> points) {

        int count = 1;
        for(int i = 1; i < MAX_COUNT_IN_LINE; i++){
            if(points.contains(new Point(x-i, y+i)))
                count++;
            else
                break;
        }

        if(count == MAX_COUNT_IN_LINE)
            return true;

        for(int i = 1; i < MAX_COUNT_IN_LINE; i++){
            if(points.contains(new Point(x+i, y-i)))
                count++;
            else
                break;
        }

        if(count == MAX_COUNT_IN_LINE)
            return true;
        return false;
    }
    //右斜判断是否5子
    private boolean checkRightDiagonal(int x, int y, List<Point> points) {

        int count = 1;
        for(int i = 1; i < MAX_COUNT_IN_LINE; i++){
            if(points.contains(new Point(x-i, y-i)))
                count++;
            else
                break;
        }

        if(count == MAX_COUNT_IN_LINE)
            return true;

        for(int i = 1; i < MAX_COUNT_IN_LINE; i++){
            if(points.contains(new Point(x+i, y+i)))
                count++;
            else
                break;
        }

        if(count == MAX_COUNT_IN_LINE)
            return true;
        return false;
    }

    public void start(){
        mWhiteArray.clear();
        mBlackArray.clear();
        mIsGameOver = false;
        mIsWhiteWinner = false;
        invalidate();
    }

    //存储与恢复  用于横竖屏，或切除时，棋盘保存
    private static final  String INSTANCE = "instance";
    private static final  String INSTANCE_GAME_OVER = "instance_game_over";
    private static final  String INSTANCE_WHITE_ARRAY = "instance_white_array";
    private static final  String INSTANCE_BLACK_ARRAY = "instance_black_array";
    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER,mIsGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY, mWhiteArray);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY, mBlackArray);

        return bundle;
    }
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
       if(state instanceof Bundle){
           Bundle bundle = (Bundle) state;
           mIsGameOver = bundle.getBoolean(INSTANCE_GAME_OVER);
           mWhiteArray = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
           mBlackArray = bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);
           super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
           return;
       }
        super.onRestoreInstanceState(state);
    }
}
