package com.virtualis.exp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualis.Global;
import com.virtualis.R;
import com.virtualis.exp.quiz.QuizPreStart;

@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
public class ShowExp extends TabActivity implements Global {
	
	
	Button theory,procedure,videos,simulation,quiz,resources;
	TabHost tabHost;
	int mycolor = Color.rgb(211, 211, 211);//33CCFF #3399FF
	TextView mytitle;
	
	private static String testIP = "10.4.225.170";
	String class_no,subject,exp_name,exp_no,exp_icon, view_mode,saved_status;
	String TheoryUrl,ProcedureUrl,ResourceUrl,SimulatinUrl,QuizUrl,ExpDesc, VideoUrls;
	int no_vid = 0;
	
	boolean err = false;
	String err_msg = "";
	
	
	File extStorageAppBasePath,extStorageAppExpPath,myExpFilesDir;
	File externalStorageDir = Environment.getExternalStorageDirectory();
	File expDir;
	
	
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog pDialog;
    
    Menu menu;
    MenuItem saved_btn,del_btn; 
    
    int total_files = 3, completed = 0;
    String exp_message = "Offline Experiment Files Saved";
    boolean del = false;
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.one_optionmeu, menu);
	    
	    
	    this.menu = menu;
		this.saved_btn = menu.findItem(R.id.saveExp);
		this.del_btn = menu.findItem(R.id.delete);
		
		if(view_mode.equals("offline")){
			saved_btn.setTitle("Offline Mode");
			saved_btn.setIcon(android.R.drawable.ic_menu_info_details);
			del_btn.setVisible(false);
			Toast.makeText(getApplicationContext(), "You are now in offline mode", Toast.LENGTH_LONG).show();
		}
		else if(view_mode.equals("online") && saved_status.equals("no")){
			//saved_btn.setTitle("Save Exp");
			del_btn.setVisible(false);
			exp_message = "Offline Experiment Files Saved";
			Toast.makeText(getApplicationContext(), "You are in online mode", Toast.LENGTH_LONG).show();
		}
		else if(view_mode.equals("online") && saved_status.equals("yes")){
			
			saved_btn.setIcon(android.R.drawable.ic_menu_rotate);
			saved_btn.setTitle("Update Experiment");
			exp_message = "Offline Experiment Files Updated";
			Toast.makeText(getApplicationContext(), "You are viewing saved experimnet in online mode", Toast.LENGTH_LONG).show();
		}
		
		super.onCreateOptionsMenu(menu);
	    return true;
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		 switch (item.getItemId()) {
	        case R.id.saveExp:
	        	if(view_mode.equals("offline")) return true;
	        	else return saveExp();
	            //return true;
	        case R.id.delete:
	        	if(view_mode.equals("online") && saved_status.equals("yes")) return delExp();
	        	else return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }

	}
	
	
	private boolean delExp() {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder confDel = new AlertDialog.Builder(ShowExp.this);
		confDel.setTitle("Confirmation");
		confDel.setCancelable(false);
		confDel.setMessage(Html.fromHtml("<center><p>Are you sure, do you want to Delete this Experiment ? </p></center>"));  
		confDel.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//tmpQuizStart.finish();
				if(deleteDirectory(expDir)){
					
					saved_btn.setIcon(android.R.drawable.ic_menu_save);
					saved_btn.setTitle("Save Experiment");
					del_btn.setVisible(false);
					del = true;
				}
				else {
					Toast.makeText(getApplicationContext(), "Unable to Delete Experiment", Toast.LENGTH_SHORT).show();
					del = false;
				}
				return; 
			}
		});
		confDel.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface my, int arg1) {
				// TODO Auto-generated method stub
				my.dismiss();
				return;
			}
		});
		confDel.show(); 
		return del;
	}

	public static boolean deleteDirectory(File path) {
	    if( path.exists() ) {
	      File[] files = path.listFiles();
	      if (files == null) {
	          return true;
	      }
	      for(int i=0; i<files.length; i++) {
	         if(files[i].isDirectory()) {
	           deleteDirectory(files[i]);
	         }
	         else {
	           files[i].delete();
	         }
	      }
	    }
	    return( path.delete() );
	  }


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.one_showexp);
		
		class_no = getIntent().getExtras().getString("class_no");
		subject = getIntent().getExtras().getString("subject");
		exp_name = getIntent().getExtras().getString("exp_name");
		exp_no = getIntent().getExtras().getString("exp_no");
		ExpDesc = getIntent().getExtras().getString("exp_desc");
		exp_icon = getIntent().getExtras().getString("exp_icon");
		
		view_mode = getIntent().getExtras().getString("view_mode");
		saved_status = getIntent().getExtras().getString("saved_status");
		
		TheoryUrl = getIntent().getExtras().getString("theory_url");
		ProcedureUrl = getIntent().getExtras().getString("procedure_url");
		ResourceUrl = getIntent().getExtras().getString("resource_url");
		
		SimulatinUrl = getIntent().getExtras().getString("simulation_url");
		QuizUrl = getIntent().getExtras().getString("quiz_url");
	
		VideoUrls = getIntent().getExtras().getString("video_urls");
		
		expDir = new File(BASEDIR + "ExPdaTA"
						+File.separator+class_no
						+File.separator+subject
						+File.separator+exp_no);
		
		this.setTitle(Html.fromHtml("<b> Class "+class_no+" - "+subject+" - "+exp_no+". "+exp_name+"</b>"));
		
		
		
		theory = (Button) findViewById(R.id.theory);
		procedure = (Button) findViewById(R.id.procedure);
		videos = (Button) findViewById(R.id.videos);
		simulation = (Button) findViewById(R.id.simulation);
		quiz = (Button) findViewById(R.id.quiz);
		resources = (Button) findViewById(R.id.resources);
		
		tabHost = getTabHost();
		
		// Tab for Tab1
		TabSpec tab1 = tabHost.newTabSpec("theory");
		// setting Title and Icon for the Tab
		tab1.setIndicator("theory");
		Intent intent1 = new Intent(this, Theory.class);
		intent1.putExtra("exp_desc",ExpDesc );
		intent1.putExtra("theroy_url",TheoryUrl );
		tab1.setContent(intent1);
		
		// Tab for Tab2
		TabSpec tab2 = tabHost.newTabSpec("procedure");
		tab2.setIndicator("procedure");
		Intent intent2 = new Intent(this, Procedure.class);
		intent2.putExtra("procedure_url",ProcedureUrl );
		tab2.setContent(intent2);

		// Tab for Tab3
		TabSpec tab3 = tabHost.newTabSpec("videos");
		tab3.setIndicator("videos");
		Intent intent3 = new Intent(this, Videos.class);
		intent3.putExtra("video_urls",VideoUrls );
		tab3.setContent(intent3);
		
		
		// Tab for tab4
		TabSpec tab4 = tabHost.newTabSpec("simulation");
		// setting Title and Icon for the Tab
		tab4.setIndicator("simulation");
		Intent intent4 = new Intent(this, Simulation.class);
		tab4.setContent(intent4);
		
		// Tab for tab5
		TabSpec tab5 = tabHost.newTabSpec("quiz");
		tab5.setIndicator("quiz");
		Intent intent5 = new Intent(this, QuizPreStart.class);
		intent5.putExtra("view_mode", view_mode);
		intent5.putExtra("offline_url", QuizUrl);
		tab5.setContent(intent5);

		// Tab for tab6
		TabSpec tab6 = tabHost.newTabSpec("resources");
		tab6.setIndicator("resources");
		Intent intent6 = new Intent(this, Resources.class);
		intent6.putExtra("resource_url",ResourceUrl );
		tab6.setContent(intent6);
		
		
		// Adding all TabSpec to TabHost
		tabHost.addTab(tab1); // Adding tab1 tab
		tabHost.addTab(tab2); // Adding tab2 tab
		tabHost.addTab(tab3); // Adding tab3 tab
		tabHost.addTab(tab4); // Adding tab4 tab
		tabHost.addTab(tab5); // Adding tab5 tab
		tabHost.addTab(tab6); // Adding tab6 tab
		
		
	
		tabHost.setCurrentTab(0);
		makeTransperent();
		theory.setBackgroundColor(mycolor);
	}
	
	public void makeTransperent(){
		theory.setBackgroundColor(Color.TRANSPARENT);
		procedure.setBackgroundColor(Color.TRANSPARENT);
		videos.setBackgroundColor(Color.TRANSPARENT);
		simulation.setBackgroundColor(Color.TRANSPARENT);
		quiz.setBackgroundColor(Color.TRANSPARENT);
		resources.setBackgroundColor(Color.TRANSPARENT);
	}
	
	public void tabHandler(View target) {
		
		theory.setSelected(false);
		procedure.setSelected(false);
		videos.setSelected(false);
		simulation.setSelected(false);
		quiz.setSelected(false);
		resources.setSelected(false);

		makeTransperent();
		
		if (target.getId() == R.id.theory) {
			tabHost.setCurrentTab(0);
			theory.setSelected(true);
			theory.setBackgroundColor(mycolor);
		} else if (target.getId() == R.id.procedure) {
			tabHost.setCurrentTab(1);
			procedure.setSelected(true);
			procedure.setBackgroundColor(mycolor);
		} else if (target.getId() == R.id.videos) {
			tabHost.setCurrentTab(2);
			videos.setSelected(true);
			videos.setBackgroundColor(mycolor);
		} else if (target.getId() == R.id.simulation) {
			tabHost.setCurrentTab(3);
			simulation.setSelected(true);
			simulation.setBackgroundColor(mycolor);
		} else if (target.getId() == R.id.quiz) {
			tabHost.setCurrentTab(4);
			quiz.setSelected(true);
			quiz.setBackgroundColor(mycolor);
		} else if (target.getId() == R.id.resources) {
			tabHost.setCurrentTab(5);
			resources.setSelected(true);
			resources.setBackgroundColor(mycolor);
		}
	};
	
	@Override
	protected Dialog onCreateDialog(int id) {
		
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Saving files " + (++id) +" of "+total_files+" Please wait...");
		pDialog.setIndeterminate(false);
		pDialog.setMax(100);
		//pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pDialog.setCancelable(false);
		pDialog.show();
		return pDialog;
	}
	
	

	public boolean saveExp(){
		
		extStorageAppBasePath = new File(externalStorageDir.getAbsolutePath() +
				File.separator + "Android" + File.separator + "data" +
				File.separator + getPackageName() + File.separator + "ExPdaTA");
		
		myExpFilesDir = new File(extStorageAppBasePath.getAbsolutePath()
				+ File.separator  + class_no + File.separator 
				+ File.separator  + subject + File.separator
				+ File.separator  + exp_no + File.separator);
		
		boolean cont = true;
		if(!myExpFilesDir.exists()){
			if(myExpFilesDir.mkdirs()){
				Log.d("Sub directories ---", "created");
				cont = true;
			}
			else {
				err = true;
				cont = false;
			}
		}
		
		if(cont){
			
        	if(completed == 0) {
        		new DownloadFileFromURL().execute(exp_icon);
        	}
			
		}
		return false;
	}
	


	public void DownloadFile(String path){
		try {
			URL url = new URL(path);
			
			String fileName = path.substring( path.lastIndexOf('/')+1, path.length() );

			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			urlConnection.connect();
			File file = new File(myExpFilesDir,fileName);
			FileOutputStream fileOutput = new FileOutputStream(file);
			InputStream inputStream = urlConnection.getInputStream();
			@SuppressWarnings("unused")
			int downloadedSize = 0;
			byte[] buffer = new byte[1024];
			int bufferLength = 0;
			
			while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
				fileOutput.write(buffer, 0, bufferLength);
				downloadedSize += bufferLength;
			}
			fileOutput.close();
	
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class DownloadFileFromURL extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread
		 * Show Progress Bar Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(completed);
		}

		/**
		 * Downloading file in background thread
		 * */
		@Override
		protected String doInBackground(String... f_url) {
			int count;
	        try {
	        	String path = f_url[0];
	        	String fileName = path.substring( path.lastIndexOf('/')+1, path.length() );
				
	            URL url = new URL(f_url[0]);
	            URLConnection conection = url.openConnection();
	            conection.connect();
	            // getting file length
	            int lenghtOfFile = conection.getContentLength();

	            // input stream to read file - with 8k buffer
	            InputStream input = new BufferedInputStream(url.openStream(), 8192);
	            
	            // Output stream to write file
	            File file = new File(myExpFilesDir,fileName);
	            OutputStream output = new FileOutputStream(file);

	            byte data[] = new byte[1024];

	            long total = 0;

	            while ((count = input.read(data)) != -1) {
	                total += count;
	                // publishing the progress....
	                // After this onProgressUpdate will be called
	                publishProgress(""+(int)((total*100)/lenghtOfFile));
	                
	                // writing data to file
	                output.write(data, 0, count);
	            }

	            // flushing output
	            output.flush();
	            
	            // closing streams
	            output.close();
	            input.close();
	            
	        } catch (Exception e) {
	        	Log.e("Error: ", e.getMessage());
	        }
	        
	        return null;
		}
		
		/**
		 * Updating progress bar
		 * */
		protected void onProgressUpdate(String... progress) {
			// setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
       }

		/**
		 * After completing background task
		 * Dismiss the progress dialog
		 * **/
		@Override
		protected void onPostExecute(String file_url) {
			dismissDialog(completed);
			completed++;
			
			if(completed != total_files){
				
				if(completed == 1){
					Log.d("urls - ", QuizUrl);
					new DownloadFileFromURL().execute(QuizUrl);
				}
				else if(completed == 2){
					SimulatinUrl = SimulatinUrl.split("@@@")[1];
					SimulatinUrl = "http://"+testIP+"/newAakashSiteMergeUpload/assets/csvFiles/" + SimulatinUrl;
					new DownloadFileFromURL().execute(SimulatinUrl);
				}
					
			}
			else {
				String icon_fileName = exp_icon.substring( exp_icon.lastIndexOf('/')+1, exp_icon.length() );
				icon_fileName = myExpFilesDir.getAbsolutePath() + File.separator + icon_fileName ;
				String quiz_fileName = QuizUrl.substring( QuizUrl.lastIndexOf('/')+1, QuizUrl.length() );
				quiz_fileName = myExpFilesDir.getAbsolutePath() + File.separator + quiz_fileName ;
				String simulation_fileName = SimulatinUrl.substring( SimulatinUrl.lastIndexOf('/')+1, SimulatinUrl.length() );
				simulation_fileName = myExpFilesDir.getAbsolutePath() + File.separator +simulation_fileName ;
				try {
					FileOutputStream expJson = new FileOutputStream(myExpFilesDir.getAbsolutePath() + File.separator + "expData.json");
					String mydata= "[{ "
							+ " \"class_no\" : \""+class_no+"\", \"subject\" : \""+subject+"\", "
							+ "\"exp_name\" : \""+exp_name+"\", "
							+ "\"exp_no\" : \""+exp_no+"\",\"exp_desc\" : \""+ExpDesc+"\", "
							+ "\"thumb\" : \""+icon_fileName+"\", \"simulation\" : \""+simulation_fileName+"\", "
							+ "\"resources\" : \""+ResourceUrl+"\",\"videos\" : \""+VideoUrls+"\", "
							+ "\"theory\" : \""+TheoryUrl+"\", \"procedure\" : \""+ProcedureUrl+"\", "
							+ "\"gift\" : \""+quiz_fileName+"\"}]";
					byte[] buf = mydata.getBytes();
					expJson.write(buf);
					expJson.close();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				saved_btn.setIcon(android.R.drawable.ic_menu_rotate);
				saved_btn.setTitle("Update Experiment");
				del_btn.setVisible(true);
				saved_status="yes";
				completed = 0;
				Toast.makeText(getApplicationContext(), exp_message , Toast.LENGTH_LONG).show();
			}
		}
	}

}
