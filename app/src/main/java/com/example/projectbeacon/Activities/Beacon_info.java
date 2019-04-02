package com.example.projectbeacon.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.projectbeacon.Beacon.TheBeacon;
import com.example.projectbeacon.Database.DatabaseHandler;
import com.example.projectbeacon.R;

public class Beacon_info extends AppCompatActivity {
    private TheBeacon beacon;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_info);

        db = new DatabaseHandler(this);
        Intent intent = getIntent();
        this.beacon = (TheBeacon) intent.getSerializableExtra("beacon");

        init();
    }

    private void init(){
         TextView beacon_name = (TextView)findViewById(R.id.beacon_info_name);
         TextView UUID = (TextView)findViewById(R.id.UUID_selectText);
         TextView major = (TextView)findViewById(R.id.major_selectText);
         TextView minor = (TextView)findViewById(R.id.minor_selectText);
         TextView RSSI = (TextView)findViewById(R.id.RSSI_selectText);
         TextView TX = (TextView)findViewById(R.id.TX_selectText);
         TextView RoomNameInCard = (TextView)findViewById(R.id.RoomName_inCard);
         TextView RangingInCard  = (TextView)findViewById(R.id.ranging_inCard);

         TextView roomName = (TextView)findViewById(R.id.roomInfo);
         TextView floor = (TextView)findViewById(R.id.floorInfo);
         TextView homeName = (TextView)findViewById(R.id.homeNameInfo);

         beacon_name.setText(beacon.getBeaconName());
         UUID.setText(""+beacon.getBeacon().getId1());
         major.setText("Major "+beacon.getBeacon().getId2());
         minor.setText("Minor "+beacon.getBeacon().getId3());
         RSSI.setText("RSSI "+beacon.getBeacon().getRssi()+" dBm");
         TX.setText("Tx "+beacon.getBeacon().getTxPower()+" dBm");
         RoomNameInCard.setText(beacon.getRoomName());
         RangingInCard.setText("ระยะทาง: "+ String.format("%.2f",beacon.getBeacon().getDistance())+" เมตร");

        roomName.setText(beacon.getRoomName());
        floor.setText(beacon.getFloor());
        homeName.setText(beacon.getHomeName());

    }
}