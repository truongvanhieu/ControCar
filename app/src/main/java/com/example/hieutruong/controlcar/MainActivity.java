package com.example.hieutruong.controlcar;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Button btnScan;
    ListView listDevice;
    //Bluetooth
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = (Button) findViewById(R.id.buttonScan);
        listDevice = (ListView) findViewById(R.id.listview);

        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if(myBluetooth == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
            finish();
        }
        else if(!myBluetooth.isEnabled()) {
            Intent BluetoothTurnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(BluetoothTurnOn, 1);
        }
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanDevicesList();
            }
        });
    }

    private void scanDevicesList(){
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList arrListDevice = new ArrayList();

        if(pairedDevices.size() > 0) {
            for(BluetoothDevice buetoothdevice : pairedDevices){
                arrListDevice.add(buetoothdevice.getName()+ "-" + buetoothdevice.getAddress());
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "No paired Bluetooth Devices found", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrListDevice);
        listDevice.setAdapter(adapter);
        //listDevice.setOnClickListener((View.OnClickListener) myListClickListener);
    }

    /*private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener(){
        public void onItemclick (AdapterView<?> av, View v, int arg2, long arg3)
        {
            // Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // Make an intent to start next activity.
            //Intent i = new Intent(DeviceList.this, ledControl.class);
        }
    }*/
}
