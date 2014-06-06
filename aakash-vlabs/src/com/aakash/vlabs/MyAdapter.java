package com.aakash.vlabs;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<String>{
	
	private Context context;
	private ArrayList<String> values;
	static public int j = -1;

	public MyAdapter(Context context, ArrayList<String> arr) {
	    super(context, R.layout.custom_list, arr);
	    this.context = context;
	    this.values = arr;
	  }
	
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    
		View rowView = convertView;
		//LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    //View rowView = inflater.inflate(R.layout.custom_list, parent, false);
	    
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.custom_list,parent, false);
        }
		
		TextView textView1 = (TextView) rowView.findViewById(R.id.headin);
		TextView textView2 = (TextView) rowView.findViewById(R.id.desc);
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
	    textView1.setText(values.get(position));

	    textView2.setText(JSONdata.ExperimentsDesc.get(j).get(position));
	    
	    imageView.setImageResource(R.drawable.ic_launcher);
	    
//	    rowView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Class ourClass = Class.forName("com.aakash.vlabs.ShowExp");
//				
//				Intent intent = new Intent(getContext(), ourClass);
//
////				intent.putExtra("class_no", class_no);
////				intent.putExtra("subject", subject);
////				intent.putExtra("exp_name", exp_name);
////				intent.putExtra("exp_no", exp_no);
//				startActivity(intent);
//				
//			}
//		});
	    
	    return rowView;
	  }

}
