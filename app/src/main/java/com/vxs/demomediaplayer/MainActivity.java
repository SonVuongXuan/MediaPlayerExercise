package com.vxs.demomediaplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvSong;
    Button btnPre, btnStart, btnNext;
    ArrayList<Integer> DSBH;
    ArrayAdapter adapter;
    MyBoundService audio;
    int song;
    int positionSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvSong= findViewById(R.id.lvSongs);
        btnNext= findViewById(R.id.btnNext);
        btnPre= findViewById(R.id.btnPre);
        btnStart= findViewById(R.id.btnStart);
        audio= null;
        song=R.raw.phutbandau;
        positionSong= 0;
        DSBH= new ArrayList<>();
        DSBH.add(R.raw.phutbandau);
        DSBH.add(R.raw.nguoitacothuongminhdau);
        DSBH.add(R.raw.anhdechcanginhieungoaiem);

        adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,DSBH);
        lvSong.setAdapter(adapter);

        Intent intent= new Intent(MainActivity.this, MyBoundService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);



        lvSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                song= DSBH.get(position);
                positionSong= position;

            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.getText().equals("Start")){
                    audio.pausePlayer();
                    if(audio!=null){
                        audio.startPlayer(song);
                        btnStart.setText("Pause");
                    }
                }
                else if(btnStart.getText().equals("Pause")){
                    audio.pausePlayer();
                    btnStart.setText("Resume");
                }
                else {
                    audio.resumePlayer();
                    btnStart.setText("Pause");
                }
            }
        });

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.pausePlayer();

                if((positionSong-1)<0){
                    positionSong=DSBH.size()-1;
                    song= DSBH.get(positionSong);

                }
                else {
                    song= DSBH.get(positionSong-1);
                    positionSong--;
                }

                audio.startPlayer(song);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.pausePlayer();
                if((positionSong+1)>=DSBH.size()){
                    positionSong=0;
                    song= DSBH.get(positionSong);

                }
                else {
                    song=DSBH.get(positionSong+1);
                    positionSong++;
                }
                audio.startPlayer(song);
            }
        });

    }

    ServiceConnection serviceConnection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBoundService.MyBinder myBinder= (MyBoundService.MyBinder) service;
            audio= (MyBoundService) myBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
