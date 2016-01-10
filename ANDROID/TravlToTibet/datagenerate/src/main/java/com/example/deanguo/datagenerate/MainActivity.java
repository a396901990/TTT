package com.example.deanguo.datagenerate;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.deanguo.util.PrepareFile;

import org.json.JSONArray;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

public class MainActivity extends AppCompatActivity {

    private final static String BMOB_APPLICATION_ID = "1ac4c82c189eb0d80711885ed3ad05ba";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, BMOB_APPLICATION_ID);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BmobQuery<PrepareFile> bmobQuery = new BmobQuery<PrepareFile>();
                bmobQuery.addWhereEqualTo("fileName", "attention");
                bmobQuery.findObjects(getApplication(), new FindListener<PrepareFile>() {
                    @Override
                    public void onSuccess(List<PrepareFile> list) {
                        Toast.makeText(getApplicationContext(), list.get(0).getFile().getFileUrl(getApplicationContext()), Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
