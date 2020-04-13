package com.me.earthquake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView name,desc,date,cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );
        name=findViewById( R.id.title );
        desc=findViewById( R.id.desc );
        date=findViewById( R.id.date );
        cat=findViewById( R.id.cat );
        Intent intent=getIntent();
        String sname=intent.getStringExtra( "name" );
        String sdesc=intent.getStringExtra( "desc" );
        String sdate=intent.getStringExtra( "date" );
        String scat=intent.getStringExtra( "cat" );

        name.setText( "Title : "+sname );
        date.setText( "Date "+sdate );
        desc.setText( "Description "+sdesc );
        cat.setText( "Category "+scat );
    }
}
