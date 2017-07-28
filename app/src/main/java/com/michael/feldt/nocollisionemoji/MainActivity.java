package com.michael.feldt.nocollisionemoji;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, Chronometer.OnChronometerTickListener {

    Timer timer=new Timer();
    long startTime=System.currentTimeMillis();
    ImageView smile,hurt,mad,laugh,sick,cool,money,slow,fast;
    Rect smileRect, hurtRect, madRect, laughRect, sickRect, coolRect, moneyRect;

    TextView stayMad,scoreTotal;
    Button button;

    SoundPool soundpool;
    int mySound=-1;

    int smileX=600, smileY=600;
    int speed=3;
    int smileDirectionX=4, smileDirectionY=4;

    int hurtX=10, hurtY=50;
    int hurtDirectionX=3, hurtDirectionY=2;

    int madX=10, madY=200;
    int madDirectionX=-1, madDirectionY=-4;

    int laughX=100, laughY=40;
    int laughDirectionX=1, laughDirectionY=-3;

    int sickX=40, sickY=60;
    int sickDirectionX=3, sickDirectionY=-4;

    int coolX=50, coolY=0;
    int coolDirectionX=2, coolDirectionY=-2;

    int moneyX=9, moneyY=30;
    int moneyDirectionX=2, moneyDirectionY=4;

    int fastX=20, fastY=50;
    int fastDirectionX=5, fastDirectionY=3;

    int slowX=20, slowY=50;
    int slowDirectionX=2, slowDirectionY=3;

    int screenX,screenY;

    private GestureDetector gdetect;

    TextView tv2;
    Chronometer chrono,ingameChrono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv2=(TextView) findViewById(R.id.textView2);

        smile=(ImageView) findViewById(R.id.mad);
        hurt=(ImageView) findViewById(R.id.hurt);
        mad=(ImageView) findViewById(R.id.smile);
        laugh=(ImageView) findViewById(R.id.laugh);
        sick=(ImageView) findViewById(R.id.sick);
        cool=(ImageView) findViewById(R.id.cool);
        money=(ImageView) findViewById(R.id.money);
        slow=(ImageView) findViewById(R.id.slow);
        fast=(ImageView) findViewById(R.id.boost);
        stayMad=(TextView) findViewById(R.id.stayMad);
        scoreTotal=(TextView) findViewById(R.id.scoreTotal);
        button=(Button) findViewById(R.id.restart);
        chrono=(Chronometer) findViewById(R.id.chronometer);
        ingameChrono=(Chronometer) findViewById(R.id.chronometer2);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundpool=new SoundPool(20,AudioManager.STREAM_MUSIC,0);
        AssetManager assetmanager=getAssets();

        try {
            AssetFileDescriptor descriptor= assetmanager.openFd("un.wav");
            mySound=soundpool.load(descriptor,1);
        }catch(IOException e){
            e.printStackTrace();
        }


        slow.setVisibility(View.INVISIBLE);
        fast.setVisibility(View.INVISIBLE);
        stayMad.setVisibility(View.INVISIBLE);
        scoreTotal.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        chrono.setVisibility(View.INVISIBLE);
        ingameChrono.setOnChronometerTickListener(this);

        gdetect=new GestureDetector(this,this);

        chrono.start();
        ingameChrono.start();

        final int FPS=40;
        TimerTask updateGame=new updateGameTask();
        timer.scheduleAtFixedRate(updateGame,0,1000/FPS);



        Display display=getWindowManager().getDefaultDisplay();
        Point size=new Point();
        display.getSize(size);
        screenX=size.x;
        screenY=size.y;


    }
    public void fast(View view){
        speed=speed*2;
        fast.setVisibility(View.INVISIBLE);
    }
    public void slow(View view){
        speed=speed-1;
        slow.setVisibility(View.INVISIBLE);
    }
    public void reset(View view){
        hurt.setVisibility(View.VISIBLE);
        smile.setVisibility(View.VISIBLE);
        mad.setVisibility(View.VISIBLE);
        laugh.setVisibility(View.VISIBLE);
        sick.setVisibility(View.VISIBLE);
        cool.setVisibility(View.VISIBLE);
        money.setVisibility(View.VISIBLE);

        tv2.setVisibility(View.VISIBLE);
        scoreTotal.setVisibility(View.INVISIBLE);
        stayMad.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        chrono.setVisibility(View.INVISIBLE);
        ingameChrono.setVisibility(View.VISIBLE);

        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();
        ingameChrono.setBase(SystemClock.elapsedRealtime());
        ingameChrono.start();

        smileX=600;
        smileY=600;
        speed=3;
        smileDirectionX=4;
        smileDirectionY=4;

        hurtX=10;
        hurtY=50;
        hurtDirectionX=3;
        hurtDirectionY=2;

        madX=10;
        madY=200;
        madDirectionX=-1;
        madDirectionY=-4;

        laughX=100;
        laughY=40;
        laughDirectionX=1; laughDirectionY=-3;

        sickX=40; sickY=60;
        sickDirectionX=3; sickDirectionY=-4;

        coolX=50; coolY=0;
        coolDirectionX=2; coolDirectionY=-2;

        moneyX=9; moneyY=30;
        moneyDirectionX=2; moneyDirectionY=4;

        fastX=20; fastY=50;
        fastDirectionX=5; fastDirectionY=3;

        slowX=20; slowY=50;
        slowDirectionX=2; slowDirectionY=3;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent begin, MotionEvent end, float velocityX, float velocityY) {
        float deltaX=end.getRawX()-begin.getRawX();
        float deltaY=end.getRawY()-begin.getRawY();
        float adeltaX=Math.abs(deltaX);
        float adeltaY=Math.abs(deltaY);

        if (adeltaX>adeltaY) {
            if (begin.getRawX() > end.getRawX()) {
                smileDirectionX = -4;
                smileDirectionY = 0;
            }else{
                smileDirectionX=4;
                smileDirectionY=0;
            }
        }else {

            if (begin.getRawY() < end.getRawY()) {
                smileDirectionY = 4;
                smileDirectionX = 0;
            } else {
                smileDirectionY = -4;
                smileDirectionX = 0;
            }
        }
        return false;
    }
    public boolean onTouchEvent(MotionEvent me){
        return gdetect.onTouchEvent(me);
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        if ("00:05".equals(chronometer.getText()))
        {
            fast.setVisibility(View.VISIBLE);
        }
        if("00:08".equals(chronometer.getText()))
        {
            fast.setVisibility(View.INVISIBLE);
        }
        if ("00:10".equals(chronometer.getText()))
        {
            slow.setVisibility(View.VISIBLE);
            soundpool.play(mySound,1,1,0,0,1);
        }
        if("00:12".equals(chronometer.getText()))
        {
            slow.setVisibility(View.INVISIBLE);
        }
        if ("00:15".equals(chronometer.getText()))
        {
            fast.setVisibility(View.VISIBLE);
        }
        if("00:20".equals(chronometer.getText()))
        {
            fast.setVisibility(View.INVISIBLE);
        }
        if ("00:25".equals(chronometer.getText()))
        {
            slow.setVisibility(View.VISIBLE);
        }
        if("00:30".equals(chronometer.getText()))
        {
            slow.setVisibility(View.INVISIBLE);
        }
    }

    class updateGameTask extends TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    smileRect=new Rect(smileX,smileY,smileX+smile.getWidth(), smileY+ smile.getHeight());
                    hurtRect=new Rect(hurtX,hurtY,hurtX+hurt.getWidth(), hurtY+ hurt.getHeight());
                    madRect=new Rect(madX,madY,madX+mad.getWidth(), madY+ mad.getHeight());
                    laughRect=new Rect(laughX,laughY,laughX+laugh.getWidth(), laughY+ laugh.getHeight());
                    sickRect=new Rect(sickX,sickY,sickX+sick.getWidth(), sickY+ sick.getHeight());
                    coolRect=new Rect(coolX,coolY,coolX+cool.getWidth(), coolY+ cool.getHeight());
                    moneyRect=new Rect(moneyX,moneyY,moneyX+money.getWidth(), moneyY+ money.getHeight());




                    smileX+=(speed*smileDirectionX);
                    smileY+=(speed*smileDirectionY);

                    hurtX+=(speed*hurtDirectionX);
                    hurtY+=(speed*hurtDirectionY);

                    madX+=(speed*madDirectionX);
                    madY+=(speed*madDirectionY);

                    laughX+=(speed*laughDirectionX);
                    laughY+=(speed*laughDirectionY);


                    sickX+=(speed*sickDirectionX);
                    sickY+=(speed*sickDirectionY);

                    coolX+=(speed*coolDirectionX);
                    coolY+=(speed*coolDirectionY);

                    moneyX+=(speed*moneyDirectionX);
                    moneyY+=(speed*moneyDirectionY);

                    fastX+=(speed*fastDirectionX);
                    fastY+=(speed*fastDirectionY);

                    slowX+=(speed*slowDirectionX);
                    slowY+=(speed*slowDirectionY);

                    smile.setX(smileX);
                    smile.setY(smileY);

                    fast.setX(fastX);
                    fast.setY(fastY);

                    slow.setX(slowX);
                    slow.setY(slowY);

                    hurt.setX(hurtX);
                    hurt.setY(hurtY);
                    mad.setX(madX);
                    mad.setY(madY);
                    laugh.setX(laughX);
                    laugh.setY(laughY);
                    sick.setX(sickX);
                    sick.setY(sickY);
                    cool.setX(coolX);
                    cool.setY(coolY);
                    money.setX(moneyX);
                    money.setY(moneyY);


                    if (Rect.intersects(smileRect,hurtRect) || Rect.intersects(smileRect,madRect) || Rect.intersects(smileRect,laughRect) ||Rect.intersects(smileRect,sickRect) || Rect.intersects(smileRect,coolRect) || Rect.intersects(smileRect,moneyRect)  ){

                        hurt.setVisibility(View.INVISIBLE);
                        smile.setVisibility(View.INVISIBLE);
                        mad.setVisibility(View.INVISIBLE);
                        laugh.setVisibility(View.INVISIBLE);
                        sick.setVisibility(View.INVISIBLE);
                        cool.setVisibility(View.INVISIBLE);
                        money.setVisibility(View.INVISIBLE);
                        tv2.setVisibility(View.INVISIBLE);
                        scoreTotal.setVisibility(View.VISIBLE);
                        chrono.stop();
                        ingameChrono.stop();
                        ingameChrono.setVisibility(View.INVISIBLE);
                        chrono.setVisibility(View.VISIBLE);
                        stayMad.setVisibility(View.VISIBLE);
                        button.setVisibility(View.VISIBLE);
                        fast.setVisibility(View.INVISIBLE);
                        slow.setVisibility(View.INVISIBLE);
                    }


                    if ((smileX + smile.getWidth())>screenX || smileX<0){
                        smileDirectionX=smileDirectionX*-1;
                    }
                    if ((smileY+ smile.getHeight())>screenY|| smileY<0){
                        smileDirectionY=smileDirectionY*-1;
                    }

                    if ((hurtX + hurt.getWidth())>screenX || hurtX<0){
                        hurtDirectionX=hurtDirectionX*-1;
                    }
                    if ((hurtY+ hurt.getHeight())>screenY|| hurtY<0){
                        hurtDirectionY=hurtDirectionY*-1;
                    }

                    if ((madX + mad.getWidth())>screenX || madX<0){
                        madDirectionX=madDirectionX*-1;
                    }
                    if ((madY+ mad.getHeight())>screenY|| madY<0){
                        madDirectionY=madDirectionY*-1;
                    }

                    if ((laughX + laugh.getWidth())>screenX || laughX<0){
                        laughDirectionX=laughDirectionX*-1;
                    }
                    if ((laughY+ laugh.getHeight())>screenY|| laughY<0){
                        laughDirectionY=laughDirectionY*-1;
                    }

                    if ((sickX + sick.getWidth())>screenX || sickX<0){
                        sickDirectionX=sickDirectionX*-1;
                    }
                    if ((sickY+ sick.getHeight())>screenY|| sickY<0){
                        sickDirectionY=sickDirectionY*-1;
                    }

                    if ((coolX + cool.getWidth())>screenX || coolX<0){
                        coolDirectionX=coolDirectionX*-1;
                    }
                    if ((coolY+ cool.getHeight())>screenY|| coolY<0){
                        coolDirectionY=coolDirectionY*-1;
                    }

                    if ((moneyX + money.getWidth())>screenX || moneyX<0){
                        moneyDirectionX=moneyDirectionX*-1;
                    }
                    if ((moneyY+ money.getHeight())>screenY|| moneyY<0){
                        moneyDirectionY=moneyDirectionY*-1;
                    }

                    if ((fastX + fast.getWidth())>screenX || fastX<0){
                        fastDirectionX=fastDirectionX*-1;
                    }
                    if ((fastY+ fast.getHeight())>screenY|| fastY<0){
                        fastDirectionY=fastDirectionY*-1;
                    }

                    if ((slowX + slow.getWidth())>screenX || slowX<0){
                        slowDirectionX=slowDirectionX*-1;
                    }
                    if ((slowY+ slow.getHeight())>screenY|| slowY<0){
                        slowDirectionY=slowDirectionY*-1;
                    }


                }
            });
        }
    }
}
