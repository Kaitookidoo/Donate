package com.example.agm.donate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by GM on 08-Mar-18.
 */


public class SlideAdapter extends PagerAdapter{

   Context context;
   LayoutInflater layoutInflater;
   private int position=0;

   public SlideAdapter(Context context)
   {
       this.context=context;
   }

   public int [] slide_images={
           R.drawable.food3,
           R.drawable.food4,R.drawable.slider3
   };

   public String[] slide_headings={
           "DONATE",
           "COLLECT",
           "ABOUT"
   };
   public String[] slide_desc={
           "  Donate the food " + " It helps someone  " , " Collect food " + " from anyone "," Know about " + " us "
   };
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(RelativeLayout) object;
    }


    public Object instantiateItem(ViewGroup container,int position){
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View  view=layoutInflater.inflate(R.layout.slide,container,false);

        ImageView slide =(ImageView) view.findViewById(R.id.imageView);
        TextView textView=(TextView) view.findViewById(R.id.textView5);
        TextView textView1=(TextView) view.findViewById(R.id.textView9);

        slide.setImageResource(slide_images[position]);
        textView.setText(slide_headings[position]);
       textView1.setText(slide_desc[position]);

        container.addView(view);
        return view;

    }

public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView((RelativeLayout)object);
}

}
