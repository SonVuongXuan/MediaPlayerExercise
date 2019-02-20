package com.vxs.demomediaplayer;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyBoundService extends Service {
    MediaPlayer mediaPlayer;
    @Override
    public void onCreate() {
        Log.i("MyBound --------------", "onCreate");
        super.onCreate();
    }

    public void startPlayer(int song){
        mediaPlayer=MediaPlayer.create(getApplicationContext(),song);
        if(mediaPlayer!=null){
            mediaPlayer.start();
        }
        Log.i("MyBound --------------", "Start player");

    }

    public void  pausePlayer(){
        if (mediaPlayer!=null&& mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }


    public void  resumePlayer(){
        if (mediaPlayer!=null&& !mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }
    public class MyBinder extends Binder{
        public Service getService(){
            return MyBoundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        Log.i("MyBoundServive", "onBind");
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {

        Log.i("MyBoundServive", "onDestroy");
        super.onDestroy();

    }
}

