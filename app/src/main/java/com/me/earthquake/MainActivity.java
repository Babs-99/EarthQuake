package com.me.earthquake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<RssItem> rssItems;
    RecyclerView recyclerView;
    GroupAdatper adatper;
    ProgressDialog progressDialog;
    EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        progressDialog=new ProgressDialog( MainActivity.this );
        progressDialog.setCancelable( false );
        progressDialog.setMessage( "Loading..." );
        recyclerView=findViewById(R.id.recycler);
        progressDialog.show();
        new Thread(new Runnable(){
            @Override
            public void run() {
                rssItems=new RssFeedProvider().doInBackground( "http://quakes.bgs.ac.uk/feeds/WorldSeismology.xml" );
                if(rssItems.size()>0)
                {
                    adatper=new GroupAdatper( MainActivity.this,rssItems );
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setCard( adatper );
                        }
                    });

                    Log.i( "Run","Yes" );
                }else {
                    Log.i( "Run","No" );
                }


            }
        }).start();

        input=findViewById( R.id.input );
        //EditText Listner
        input.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0)
                {
                    adatper=new GroupAdatper( MainActivity.this,rssItems );
                    setCard( adatper );
                    progressDialog.dismiss();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                String query=input.getText().toString().toLowerCase().trim();
                serchName( query );

            }
        } );






    }
    private void serchName(String query)
    {
        progressDialog.show();
        List<RssItem> searchList=new ArrayList<>(  );

        for(RssItem model : rssItems)
        {
            if(model.getDate().toLowerCase().contains(query))
            {
                searchList.add( model );
                adatper=new GroupAdatper( MainActivity.this,searchList );
                setCard( adatper );
                progressDialog.dismiss();
            }
            else
            {
                progressDialog.dismiss();
                // Toast.makeText( this, "Not Found !", Toast.LENGTH_SHORT ).show();
            }
        }
    }
    private void setCard(GroupAdatper adapter)
    {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation( LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        progressDialog.dismiss();

    }
}
