package com.easycook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.baidu.voicerecognition.android.VoiceRecognitionClient;
import com.baidu.voicerecognition.android.VoiceRecognitionConfig;
import com.easycook.listener.MyVoiceRecogListener;



public class MainActivity extends ActionBarActivity{
	private Button startButton;
	private Button endButton;
	private Button ttsButton;
	private Button cook1Button;
	private VoiceRecognitionClient mRecognitionClient;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListener();
        
        mRecognitionClient = VoiceRecognitionClient.getInstance(this);
        mRecognitionClient.setTokenApis("QN9uYivTwHgjDCgyVntlUG0q", "7c68f3f2fd3ccdf69ae9b0b70bbc6d3e");
        
    }


	private void findViews() {
//		this.startButton = (Button) this.findViewById(R.id.startButton);
//		this.endButton = (Button) this.findViewById(R.id.endButton);
//		this.ttsButton = (Button) this.findViewById(R.id.ttsButton);
		this.cook1Button = (Button) this.findViewById(R.id.cook1Button);
	}
    private void setListener() {
    	
    	/**
		this.startButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				VoiceRecognitionConfig config = new VoiceRecognitionConfig();
				// 设置识别开始提示音
				config.enableBeginSoundEffect(R.raw.bdspeech_recognition_start);
				// 设置说话结束提示音
				config.enableEndSoundEffect(R.raw.bdspeech_speech_end);
				int code = mRecognitionClient.startVoiceRecognition(new MyVoiceRecogListener(MainActivity.this), config);
			    if(code == VoiceRecognitionClient.START_WORK_RESULT_WORKING){
			         //启动成功
			    	Log.i("my", "启动成功!");
			    } else {
			        //启动失败，错误码描述参见VoiceRecognitionClient START_WORK_RESULT_前缀的常量定义
			    	Log.i("my", "启动失败!");
			    }
			}
		});
		this.endButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 mRecognitionClient.speakFinish();
			}
		});
		this.ttsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this,TTSActivity.class);
				startActivity(intent);
			}
		});
		*/
		
		this.cook1Button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this,Cook1Activity.class);
				startActivity(intent);
			}
		});
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
