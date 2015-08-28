package org.saarang.instieventsapp.Activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import org.saarang.instieventsapp.Adapters.ImageAdapter;
import org.saarang.instieventsapp.R;

/**
 * Created by MIHIR on 28-08-2015.
 */
public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        Toolbar tool=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        GridView gridView = (GridView)findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));

        getSupportActionBar().setTitle("About Us");
        tool.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_left));
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView textView =(TextView)findViewById(R.id.text_report_bugs);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='https://play.google.com/store/apps/details?id=iitm.satan.messmenu'> Report Bugs </a>";
        textView.setText(Html.fromHtml(text));

    }

    public void rateUs(View view){

    }
}

