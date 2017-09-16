package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

class GameView extends View {
    int[][] mymap = new int[20][20]; //存放地图文件中图片位置坐标
    int[] map;           //存放地图文件中的图片编号
    String mapstr;       //存放地图文件数据
    Bitmap men;   //工人
    Bitmap greenBox;  //草地
    Bitmap box;    //箱子
    Bitmap wall;
    Bitmap result;
    Bitmap gray;
    Paint paint;
    int tx = 120;  //箱子的坐标
    int ty = 120;

    String mapdata = "";

    public GameView(Context context) {
        super(context);
        paint = new Paint();
        men = BitmapFactory.decodeResource(getResources(), R.drawable.men);  //工人
        greenBox = BitmapFactory.decodeResource(getResources(), R.drawable.greenbox); //草地
        box = BitmapFactory.decodeResource(getResources(), R.drawable.box); //箱子
        wall = BitmapFactory.decodeResource(getResources(),R.drawable.wall);//墙
        result = BitmapFactory.decodeResource(getResources(),R.drawable.result);//终点
        gray = BitmapFactory.decodeResource(getResources(),R.drawable.gray);
    }

    String map()  //读取地图文件
    {

        try {
            InputStream in_file = getResources().openRawResource(R.raw.map2);  //输入流对象读取地图文件
            int length = in_file.available();    //获取数据流的字节数
            byte[] buffer = new byte[length];
            int bytes = in_file.read(buffer);   //产生读取数据的动作
            mapdata = new String(buffer, 0, bytes);  //把地图文件数据转换为字符串存放到mapdata中
            mapdata = mapdata.replaceAll("\n|\r","");
        } catch (IOException e) {
        }
        return mapdata;
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.CYAN);//设置背景颜色
        paint.setColor(Color.argb(255, 220, 220, 220)); //灰白色
        this.myDraw(canvas);
    }

    public void myDraw(Canvas canvas)  //绘制图形
    {
        if(mapdata.length() > 0){
            mapstr = new String(mapdata);
        }
        else{
            mapstr = map();
        }
        byte[] d = mapstr.getBytes();  //把字符串生成字节数组
        int len = mapstr.length();
        map = new int[len];
        for (int i = 0; i < mapstr.length(); i++) {
            map[i] = d[i] - 48;   //字节数组中存放的ASCII码值，转换为数字要减48
            //数字0所对应的ASCII码值为48，数字1的ASCII码值为49，…………
        }
        int k = 0;
        for (int i = 0; i < 20; i++)  //将地图中的数据与图片编号对应
        {
            for (int j = 0; j < 20; j++) {
                mymap[i][j] = map[k];
                if (mymap[i][j] == 0)  //草地
                {
                    tx = 2 + 75 * j;
                    ty = 2 + 75 * i;
                    canvas.drawBitmap(gray, tx, ty, paint);  //绘制图形
                }
                if (mymap[i][j] == 1)  //草地
                {
                    tx = 2 + 75 * j;
                    ty = 2 + 75 * i;
                    canvas.drawBitmap(wall, tx, ty, paint);  //绘制图形
                }
                if (mymap[i][j] == 2)  //草地
                {
                    tx = 2 + 75 * j;
                    ty = 2 + 75 * i;
                    canvas.drawBitmap(greenBox, tx, ty, paint);  //绘制图形
                }
                if (mymap[i][j] == 3)  //箱子
                {
                    tx = 2 + 75 * j;
                    ty = 2 + 75 * i;
                    canvas.drawBitmap(box, tx, ty, paint);  //绘制图形
                }
                if (mymap[i][j] == 4)  //工人
                {
                    tx = 2 + 75 * j;
                    ty = 2 + 75 * i;
                    canvas.drawBitmap(result, tx, ty, paint);  //绘制图形
                }
                if (mymap[i][j] == 5)  //工人
                {
                    tx = 2 + 75 * j;
                    ty = 2 + 75 * i;
                    canvas.drawBitmap(men, tx, ty, paint);  //绘制图形
                }
                k++;
            }
        }
    }
    int x1=0,y1=0,x2=0,y2=0;//鼠标坐标
   public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            x1 = (int)event.getX();
            y1 = (int)event.getY();
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            x2 = (int)event.getX();
            y2 = (int)event.getY();
            if((x2-x1)>50)//右移
            {
                mapdata= changefive(mapdata,4);
                invalidate();
                return true;
            }
           else if((y2-y1)>50)//下移
            {
                mapdata= changefive(mapdata,2);
                invalidate();
                return true;
            }
            else  if((x1-x2)>50)//左移
            {
                mapdata=changefive(mapdata,3);
                invalidate();
                return true;
            }
            else  if((y1-y2)>50)//上移
            {
                mapdata= changefive(mapdata,1);
                invalidate();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private String changefive(String old,int forward)//当鼠标移动时，判断移动鼠标的方向的工人的下一个字符，调用字符串里的字符对换方法
    {
        int a ;int b;
        a=getfiveX(old,"5");
        b=getfiveY(old,"5");
        if(forward==1)//向上
        {
            String temp = getStation(old,a,b-1);//获取下一个的字符类型
            String temp_result = getStation(old,a,b-2);//获取下下一个的字符类型
            //草地时移动，并且当箱子全部移动到终点时，工人不再移动
            if(temp.equals("2")&&!cc_result(old)){
                return cc_greenbox(a,b,a,b-1,old);
            }
            //移动箱子，遇到下下一个是墙壁或者是箱子时，不再移动
            else if((temp.equals("3"))&&(!temp_result.equals("1"))&&(!temp_result.equals("3"))){
                return cc_box(a,b,a,b-1,a,b-2,old);
            }
            //下一个遇到墙壁或者是终点时不移动
            else{
                return cc_greenbox(a,b,a,b,old);
            }
           // return cc(a,b,a,b-1,old);
        }
        if(forward==2)///向下
        {
            String temp = getStation(old,a,b+1);//获取下一个的字符类型
            String temp_result = getStation(old,a,b+2);//获取下下一个的字符类型
            //草地时移动，并且当箱子全部移动到终点时，工人不再移动
            if(temp.equals("2")&&!cc_result(old)){
                return cc_greenbox(a,b,a,b+1,old);
            }
            //移动箱子，遇到下下一个是墙壁或者是箱子时，不再移动
            else if((temp.equals("3"))&&(!temp_result.equals("1"))&&(!temp_result.equals("3"))){
                return cc_box(a,b,a,b+1,a,b+2,old);
            }
            //下一个遇到墙壁或者是终点时不移动
            else{
                return cc_greenbox(a,b,a,b,old);
            }
            //return cc(a,b,a,b+1,old);
        }
        if(forward==3)//向左
        {
            String temp = getStation(old,a-1,b);//获取下一个的字符类型
            String temp_result = getStation(old,a-2,b);//获取下下一个的字符类型
            //草地时移动，并且当箱子全部移动到终点时，工人不再移动
            if(temp.equals("2")&&!cc_result(old)){
                return cc_greenbox(a,b,a-1,b,old);
            }
            //移动箱子，遇到下下一个是墙壁或者是箱子时，不再移动
            else if((temp.equals("3"))&&(!temp_result.equals("1"))&&(!temp_result.equals("3"))){
                return cc_box(a,b,a-1,b,a-2,b,old);
            }
            //下一个遇到墙壁或者是终点时不移动
            else{
                return cc_greenbox(a,b,a,b,old);
            }
           // return cc(a,b,a-1,b,old);
        }
        if(forward==4)//向右
        {
            String temp = getStation(old,a+1,b);//获取下一个的字符类型
            String temp_result = getStation(old,a+2,b);//获取下下一个的字符类型
            //草地时移动，并且当箱子全部移动到终点时，工人不再移动
            if(temp.equals("2")&&!cc_result(old)){
                return cc_greenbox(a,b,a+1,b,old);
            }
            //移动箱子，遇到下下一个是墙壁或者是箱子时，不再移动
            else if((temp.equals("3"))&&(!temp_result.equals("1"))&&(!temp_result.equals("3"))){
                return cc_box(a,b,a+1,b,a+2,b,old);
            }
            //下一个遇到墙壁或者是终点时不移动
            else{
                return cc_greenbox(a,b,a,b,old);
            }
           // return cc(a,b,a+1,b,old);
        }
        return null;
    }
    //获取字符为5时，即工人的位置
    private int getfiveSation(String old,String whats)
    {
       return old.indexOf("5");
    }
    //获取工人的位置的横坐标
    private int getfiveX(String old,String whats)
    {
        return getfiveSation(old, whats)%20;
    }
    //获取工人的位置的纵坐标坐标
    private int getfiveY(String old,String whats)
    {
        return getfiveSation(old,whats)/20;
    }
    //获取要移动的方向的下一个字符
    private String getStation(String old,int x,int y) { return old.substring(y*20+x,y*20+x+1); }

    //在草地时移动，先把mapdata的string类型转换为char数组类型，再进行字符的对调
    private String cc_greenbox(int x1,int y1,int x2,int y2,String old)
    {
        int e=y1*20+x1;
        int r=y2*20+x2;
        char f;
        char[] cs_greenbox = old.toCharArray();
        f=cs_greenbox[e];
        cs_greenbox[e]=cs_greenbox[r];
        cs_greenbox[r]=f;
        return new String(cs_greenbox);
    }
    //移动箱子，先把mapdata的string类型转换为char数组类型，再进行字符的对调
    private String cc_box(int x1,int y1,int x2,int y2,int x3,int y3,String old)
    {
        int e=y1*20+x1;
        int r=y2*20+x2;
        int s=y3*20+x3;
        char f;
        char temp = '2';
        char[] cs_box = old.toCharArray();
        f=cs_box[r];
        cs_box[r]=cs_box[e];
        cs_box[s]=f;
        cs_box[e] = temp;
        return new String(cs_box);
    }
    //判断是否所有的箱子都到终点
    public boolean cc_result(String old){
        char[] cs_result = old.toCharArray();
        int[] end = new int[10];
        int[] box = new int[10];
        int m = 0,n = 0;
        for(int i=0;i<cs_result.length;i++){
            if(cs_result[i]=='4'){
                end[m]=i;
                m++;
            }
            if(cs_result[i]=='3'){
                box[n]=i;
                n++;
            }
        }
        for(int i = 0;i< m ;i++){
            if(end[i]!=box[i]){
                return false;
            }
            else{
                return true;
            }
        }
        return true;
    }
}
