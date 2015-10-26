package tw.idv.chunhsin.hitmolegame;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button button,button2;
    SoundPool soundPool;
    int soundId1,soundId2;
    ImageView imageView;
    int[] moles = {
            R.drawable.hole,
            R.drawable.mole1,
            R.drawable.mole2,
            R.drawable.mole3,
            R.drawable.mole4
    };
    int moleIdx=0;
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

    }

    void findviews(){
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(onClickListener);
        button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(onClickListener);
        imageView=(ImageView)findViewById(R.id.imageView);
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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.button:
                    //soundPool.play(soundId1,1.0f,1.0f,0,0,1.0f);
                    break;
                case R.id.button2:
                    //soundPool.play(soundId2,1.0f,1.0f,0,0,1.0f);
                    playMole();
                    break;
            }
        }
    };

    void playMole(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           imageView.setImageResource(moles[moleIdx]);
                        }
                    },300);
                    moleIdx++;
                    if(moleIdx==moles.length){
                        moleIdx=0;
                    }
                }
            }
        }).start();
    }
}
