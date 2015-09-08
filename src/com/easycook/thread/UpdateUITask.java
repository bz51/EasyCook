package com.easycook.thread;

import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.easycook.core.Config;
import com.easycook.listener.MySpeechSynthesizerListener;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class UpdateUITask extends AsyncTask<String, Integer, String> {
	private Context context;
	private TextView timeTextView;
	
	public UpdateUITask(Context context,TextView timeTextView){
		this.context = context;
		this.timeTextView = timeTextView;
	}
	
	@Override
	protected String doInBackground(String... params) {
		
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	
	

}
