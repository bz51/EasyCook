package com.easycook;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.easycook.listener.MySpeechSynthesizerListener;

public class TTSActivity extends ActionBarActivity{
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tts);
		
		this.textView = (TextView) findViewById(R.id.ttsText);
		
		// 注：第二个参数当前请传入任意非空字符串即可
		SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(getApplicationContext(), "holder", new MySpeechSynthesizerListener());
		// 注：your-apiKey 和 your-secretKey 需要换成在百度开发者中心注册应用得到的对应值
		speechSynthesizer.setApiKey("QN9uYivTwHgjDCgyVntlUG0q", "7c68f3f2fd3ccdf69ae9b0b70bbc6d3e");
		speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER,SpeechSynthesizer.SPEAKER_FEMALE);
		speechSynthesizer.speak("柴博周,你是个大帅哥!");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tt, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
