package com.zou.roundviewlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CircleView extends AppCompatImageView {
    Bitmap bitmap;
    Paint mPaint;
    private int mCircleColor;
    private float mCircleWidth;
    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.zrCircleView);
        if (ta != null){
            mCircleColor = ta.getColor(R.styleable.zrCircleView_circle_color,0xffffffff);
            mCircleWidth = ta.getDimension(R.styleable.zrCircleView_circle_width,3f);

            ta.recycle();
        }
    }

    Rect src ;
    Rect dst ;
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        BitmapDrawable bd = (BitmapDrawable) getDrawable();
        if (bitmap == null){
            bitmap = bd.getBitmap();
        }


        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        int r = getWidth() / 2;

        canvas.drawCircle(r, r, r, mPaint);
        //使用CLEAR作为PorterDuffXfermode绘制蓝色的矩形
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        src = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        dst = new Rect(0,0,getWidth(),getHeight());

        canvas.drawBitmap(bitmap,src,dst,mPaint);

        //最后将画笔去除Xfermode
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);

        mPaint.setColor(mCircleColor);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2-mCircleWidth/2,mPaint);

    }
    public void setUrl(final String url){

        //this.url = url;

       // myThread.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL myurl = new URL(url);
                    // 获得连接
                    HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
                    conn.setConnectTimeout(6000);//设置超时
                    conn.setDoInput(true);
                    conn.setUseCaches(false);//不缓存
                    conn.connect();
                    InputStream is = conn.getInputStream();//获得图片的数据流
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    handler.post(runnable);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

     }

     android.os.Handler handler = new android.os.Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

}
