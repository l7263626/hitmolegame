package tw.idv.chunhsin.hitmolegame;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    SoundPool soundPool;
    int soundId1,soundId2,soundId3;
    boolean active=false;
    ImageView imageView,imageView2,imageView3;
    int[] moles = {
            R.drawable.hole,
            R.drawable.mole1,
            R.drawable.mole2,
            R.drawable.mole3,
    };
    //int moleIdx,moleIdx2;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
        initSoundPool();
        findviews();
    }

    void initSoundPool(){
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        soundId1 = soundPool.load(this,R.raw.start,1);
        soundId2 = soundPool.load(this,R.raw.err,1);
        soundId3 = soundPool.load(this,R.raw.flourish,1);

    }

    void findviews(){
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setClickable(false);
                playMole();
            }
        });
        imageView=(ImageView)findViewById(R.id.imageView);
        imageView2=(ImageView)findViewById(R.id.imageView2);
        imageView3=(ImageView)findViewById(R.id.imageView3);
        textView=(TextView)findViewById(R.id.textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class Moles implements Runnable{
        ImageView image;
        int moleIdx,delay;

        public Moles(ImageView imageView) {
            image = imageView;
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (moleIdx == 0 || moleIdx == 1) {
                        soundPool.play(soundId1, 1.0f, 1.0f, 0, 0, 1.0f);
                    } else if (moleIdx == 2 || moleIdx == 3) {
                        soundPool.play(soundId2, 1.0f, 1.0f, 0, 0, 1.0f);
                        image.setImageResource(R.drawable.mole4);
                        moleIdx = moles.length;
                    }
                }
            });
        }

        @Override
        public void run() {
            if(active){
                moleIdx++;
                if(moleIdx>=moles.length){
                    moleIdx=0;
                }
                image.setImageResource(moles[moleIdx]);
                delay = getRandom();
                image.postDelayed(this,delay);
            }
        }

        public int getRandom(){
            return 200+((int)(Math.random()*5)+1)*100;
        }
    }


    void playMole(){
        active=true;
        soundPool.play(soundId3, 1.0f, 1.0f, 0, 0, 1.0f);
        countDownTimer.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                imageView.post(new Moles(imageView));
                imageView2.post(new Moles(imageView2));
                imageView3.post(new Moles(imageView3));
                /*
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moleIdx++;
                        if(moleIdx==moles.length){
                            moleIdx=0;
                        }
                        imageView.setImageResource(moles[moleIdx]);
                        playMole();
                    }
                },300);
                */
            }
        }).start();
    }

    CountDownTimer countDownTimer = new CountDownTimer(10*1000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            textView.setText(String.valueOf(millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            textView.setText(String.valueOf(0));
            active=false;
            soundPool.stop(soundId3);
            remoteMolesClickListener();
            button.setClickable(true);
        }
    };

    void remoteMolesClickListener(){
        imageView.setOnClickListener(null);
        imageView2.setOnClickListener(null);
        imageView3.setOnClickListener(null);
    }
/*
    void playMole2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moleIdx2++;
                        if(moleIdx2==moles.length){
                            moleIdx2=0;
                        }
                        imageView2.setImageResource(moles[moleIdx2]);
                        playMole2();
                    }
                },500);
            }
        }).start();
    }
    */
}
