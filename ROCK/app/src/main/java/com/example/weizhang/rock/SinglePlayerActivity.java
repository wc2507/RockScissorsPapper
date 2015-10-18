package com.example.weizhang.rock;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Timer;

import java.util.TimerTask;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SinglePlayerActivity extends Activity implements View.OnClickListener{
    private ImageView aiFace;
    private ImageView myFace;
    MediaPlayer bgm;
    @Override
    protected void onPause(){
        super.onPause();
        bgm.release();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);
        View ppB = findViewById(R.id.ppB );
        View prB = findViewById(R.id.prB);
        View psB = findViewById(R.id.psB);
        aiFace = (ImageView) findViewById(R.id.profileAI);
        myFace = (ImageView) findViewById(R.id.profileUser);
        View exitGame = findViewById(R.id.exitGame);
        ppB.setOnClickListener(this);
        prB.setOnClickListener(this);
        psB.setOnClickListener(this);
        exitGame.setOnClickListener(this);
        bgm = MediaPlayer.create(SinglePlayerActivity.this,R.raw.xiuluojie);
        bgm.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_player, menu);
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
    @Override
    public void onClick(View v){
        final ImageView myImage = (ImageView) findViewById(R.id.imageViewUser);
        aiFace.setImageResource(R.drawable.ali_normal);
        myFace.setImageResource(R.drawable.me_normal);
        int gameindex = 0;
        switch(v.getId()){
            case R.id.prB:
                //myImage.setImageResource(R.drawable.pr);
                myImage.setBackground(getDrawable(R.drawable.rani));
                gameindex=0;
                break;
            case R.id.psB:
                //myImage.setImageResource(R.drawable.ps);
                myImage.setBackground(getDrawable(R.drawable.sani));
                gameindex=1;
                break;
            case R.id.ppB:
                //myImage.setImageResource(R.drawable.pp);
                myImage.setBackground(getDrawable(R.drawable.pani));
                gameindex=2;
                break;
            case R.id.exitGame:
                finish();
                break;
        }
        myImage.post(new Runnable() {
            @Override
            public void run() {
                ((AnimationDrawable) myImage.getBackground()).start();
            }
        });
        final int aiResult = ai();
        final int myResult = gameindex;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                play(aiResult,myResult);
            }
        },1000);
       // play(ai(), gameindex);


    }

    private int ai(){
        final ImageView aiImage = (ImageView) findViewById(R.id.imageViewAI);
        int ram = (int)(Math.random()*3);
        switch (ram){
            case 0:
                aiImage.setBackground(getDrawable(R.drawable.raniui));
                //aiImage.setImageResource(R.drawable.pr);
                break;
            case 1:
                aiImage.setBackground(getDrawable(R.drawable.sesaniui));
                //aiImage.setImageResource(R.drawable.ps);
                break;
            case 2:
                aiImage.setBackground(getDrawable(R.drawable.paniui));
               // aiImage.setImageResource(R.drawable.pp);
                break;
            default:
                break;
        }
        aiImage.post(new Runnable() {
            @Override
            public void run() {
                ((AnimationDrawable) aiImage.getBackground()).start();
            }
        });
    return ram;
    }

    private void play(int user,int comp){
        TextView result = (TextView) findViewById(R.id.textView);
        if(comp == user){
            result.setText("It's a Tie");
            aiFace.setImageResource(R.drawable.ali_normal);
            myFace.setImageResource(R.drawable.me_normal);
        }else if (comp==0&&user==2 ||
                  comp==2&&user==1 ||
                 comp==1&&user==0){
            result.setText("You Lose");
            aiFace.setImageResource(R.drawable.ali_happy);
            myFace.setImageResource(R.drawable.me_angry);

        }else {
            result.setText("You Win");
            aiFace.setImageResource(R.drawable.ali_cry);
            myFace.setImageResource(R.drawable.me_happy);
        }
        ;

    }


}
