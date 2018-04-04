package com.example.agm.donate;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private TextView[] mdots;
   // private String = ... ;

    private SlideAdapter sliderAdapter;
    private Button next;
    private Button back;
    private int currentpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        next =(Button) findViewById(R.id.next);
        back =(Button) findViewById(R.id.prev);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        linearLayout=(LinearLayout)findViewById(R.id.linear);

        sliderAdapter=new SlideAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        addDots(0);
        viewPager.addOnPageChangeListener(viewListener);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentpage+1);
                finish();
                Intent i= new Intent(Main2Activity.this,Main3Activity.class);
                startActivity(i);
            }
        });


    }

    public void addDots(int position){
        mdots=new TextView[3];
        linearLayout.removeAllViews();
        for(int i=0; i<mdots.length;i++){
            mdots[i]=new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.iron));

            linearLayout.addView(mdots[i]);
        }
        if(mdots.length>0){
            mdots[position].setTextColor(getResources().getColor(R.color.iron));
        }
    }
    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentpage =position;
            if(position==0){
                next.setEnabled(true);
                back.setEnabled(false);
                back.setVisibility(View.INVISIBLE);

                next.setText("Next");
                back.setText("");
            }

            else if(position==mdots.length-1 ){
                next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);

                next.setText("Finish");
                back.setText("Back");
            }

            else{
                next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);

                next.setText("Next");
                back.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
