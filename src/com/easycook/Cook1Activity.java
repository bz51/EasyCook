package com.easycook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.SpeechError;
import com.baidu.voicerecognition.android.VoiceRecognitionClient;
import com.baidu.voicerecognition.android.VoiceRecognitionClient.VoiceClientStatusChangeListener;
import com.baidu.voicerecognition.android.VoiceRecognitionConfig;
import com.easycook.core.Config;
import com.easycook.listener.MySpeechSynthesizerListener;

public class Cook1Activity extends ActionBarActivity implements VoiceClientStatusChangeListener,SpeechSynthesizerListener{
	//材料清单
	private LinearLayout tableLinearLayout;
	private RelativeLayout imageLinearLayout;
	private ImageView imageView;
	private TextView textView;
	private TextView timeTextView;
	
	private VoiceRecognitionClient mRecognitionClient;
	private SpeechSynthesizer speechSynthesizer ;
	private VoiceRecognitionConfig config;
	private String[] steps = Config.STEPS;
	private int curStep = 0;//当前是第几步
	private Map<Integer,Integer> map_step = new HashMap<Integer,Integer>();
	private int[] images = {R.drawable.pic1,R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,R.drawable.pic7,R.drawable.pic8,R.drawable.pic9,R.drawable.pic10,R.drawable.pic11,R.drawable.pic12,R.drawable.pic13,R.drawable.pic14,R.drawable.pic15,R.drawable.pic16,R.drawable.pic17,R.drawable.pic18,R.drawable.pic19,R.drawable.pic20};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_cook1);
		
		for(int i=0;i<steps.length;i++){
			map_step.put(i, 0);
		}
		
		findViews();
		setListener();
		
		startCook();
	}

	private void findViews() {
		this.tableLinearLayout = (LinearLayout) findViewById(R.id.tableLinearLayout);
		this.imageLinearLayout = (RelativeLayout) findViewById(R.id.picLinearLayout);
		this.imageView = (ImageView) findViewById(R.id.imageView);
		this.textView = (TextView) findViewById(R.id.textView);
		this.timeTextView = (TextView) findViewById(R.id.timeTextView);
	}

	private void setListener() {
		
	}

	private void startCook() {
		// 注：第二个参数当前请传入任意非空字符串即可
		speechSynthesizer = new SpeechSynthesizer(getApplicationContext(), "holder",this);
		// 注：your-apiKey 和 your-secretKey 需要换成在百度开发者中心注册应用得到的对应值
		speechSynthesizer.setApiKey("QN9uYivTwHgjDCgyVntlUG0q","7c68f3f2fd3ccdf69ae9b0b70bbc6d3e");
		speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER,SpeechSynthesizer.SPEAKER_FEMALE);
		
		//说完之后，立马开始监听
		config = new VoiceRecognitionConfig();
		// 设置识别开始提示音
		config.enableBeginSoundEffect(R.raw.bdspeech_recognition_start);
		// 设置说话结束提示音
		config.enableEndSoundEffect(R.raw.bdspeech_speech_end);
		//设置蓝牙录音
		config.setUseBlueTooth(true);
		mRecognitionClient = VoiceRecognitionClient.getInstance(this);
        mRecognitionClient.setTokenApis("QN9uYivTwHgjDCgyVntlUG0q", "7c68f3f2fd3ccdf69ae9b0b70bbc6d3e");
        
		speak(speechSynthesizer,mRecognitionClient);
		
	}


	private void speak(SpeechSynthesizer speechSynthesizer,VoiceRecognitionClient mRecognitionClient) {
		//若现在是第二步，把食材清单隐藏掉，显示出图片和文字
		if(this.curStep == 1){
			this.imageLinearLayout.setVisibility(LinearLayout.VISIBLE);
			this.tableLinearLayout.setVisibility(LinearLayout.GONE);
		}
		
		//从第二步开始，显示图片
		if(this.curStep >= 1){
			imageView.setBackgroundResource(images[this.curStep]);
			textView.setText(steps[this.curStep]);
		}
		
		
		speechSynthesizer.speak(this.steps[this.curStep]);
	}

	/**-------------------语音识别------------------------------*/
	@Override
	public void onClientStatusChange(int status, Object obj) {
		switch(status) {
		//录音开始，用户可以开始进行语音输入
        case VoiceRecognitionClient.CLIENT_STATUS_START_RECORDING:
        	Log.i("my", "录音开始，用户可以开始进行语音输入");
            break;
        //检测到语音起点，但还未检测到语音终点
        case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_START:
        	Log.i("my", "检测到语音起点，但还未检测到语音终点");
            break;
        //有音频数据输出
        case VoiceRecognitionClient.CLIENT_STATUS_AUDIO_DATA:
        	Log.i("my", "有音频数据输出");
            if (obj!= null && obj instanceof byte[]) {
                // 处理数据
            }
            break;
         // 已经检测到语音终点，等待网络返回
        case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_END:
            Log.i("my", "已经检测到语音终点，等待网络返回");
            break;
            
        //语音识别完成
        case VoiceRecognitionClient.CLIENT_STATUS_FINISH:
            Log.i("my", "语音识别完成ddd");
        	ArrayList<String> list = (ArrayList)obj;
        	for(String str : list){
        		Log.i("myccc", str);
        	}
        	
        	//若用户说的话中包含了“下一步”，那么进入下一步
        	String result = list.toString();
        	if(result.contains("下一步")){
        		Toast.makeText(this, "识别结果包含下一步", Toast.LENGTH_SHORT).show();
        		this.curStep++;
        		speak(this.speechSynthesizer,this.mRecognitionClient);
        	}else{
        		mRecognitionClient.stopVoiceRecognition();
        		int code = mRecognitionClient.startVoiceRecognition(this, config);
        		if(code == VoiceRecognitionClient.START_WORK_RESULT_WORKING){
        			//启动成功
        			Log.i("my", "启动成功!");
        		} else {
        			//启动失败，错误码描述参见VoiceRecognitionClient START_WORK_RESULT_前缀的常量定义
        			Log.i("my", "启动失败!");
        		}
        	}
            break;
            
            
        /**
         * 这里的代码会被不停地调用，所以可能我只说了一句“下一步”，但被调用了10次，所以curStep就会增加10
         * 改进办法：创建一个Map，记录当前步骤和当前步骤被调用的次数；每次调用都判断当前步骤的次数是否超过了1，如果超过1就不要再调用了，如果为0就调用
         */
        case VoiceRecognitionClient.CLIENT_STATUS_UPDATE_RESULTS:
            //多句模式会有部分结果（一个分句）返回
        	ArrayList<String> list1 = (ArrayList)obj;
        	String result_update = list1.toString();
        	//////////////////////////
        		for(String str : list1){
        			Log.i("my", "update result:"+str);
        		}
            //////////////////////////
        	if(result_update.contains("下一步")){
        		//获取当前步骤中，识别到下一步的次数
        		int curStepCount = map_step.get(this.curStep);
        		Log.i("my", "curStepCount="+curStepCount);
        		if(curStepCount==0){
        			map_step.put(this.curStep, ++curStepCount);
        			Log.i("my", "map:"+map_step.toString());
        			//一旦识别到“下一步”，结束本次识别，进入下一轮循环
        			mRecognitionClient.stopVoiceRecognition();
        			Toast.makeText(this, "识别结果包含下一步", Toast.LENGTH_SHORT).show();
        			this.curStep++;
        			Log.i("my", "curStep="+this.curStep);
        			speak(this.speechSynthesizer,this.mRecognitionClient);
        		}
        	}
            break;
        //用户已取消
        case VoiceRecognitionClient.CLIENT_STATUS_USER_CANCELED:
        	Log.i("my", "用户已取消");
            break;
            
        case VoiceRecognitionClient.CLIENT_STATUS_ERROR:
        	Log.i("my", "CLIENT_STATUS_ERROR");
        	mRecognitionClient.stopVoiceRecognition();
        	int code = mRecognitionClient.startVoiceRecognition(this, config);
    		if(code == VoiceRecognitionClient.START_WORK_RESULT_WORKING){
    			//启动成功
    			Log.i("my", "启动成功!");
    		} else if(code==VoiceRecognitionClient.START_WORK_RESULT_NET_UNUSABLE){
    			Log.i("my", "启动失败！START_WORK_RESULT_NET_UNUSABLE");
    		}else if(code==VoiceRecognitionClient.START_WORK_RESULT_NULL_LISTENER){
    			Log.i("my", "启动失败！START_WORK_RESULT_NULL_LISTENER");
    		}else if(code==VoiceRecognitionClient.START_WORK_RESULT_RECOGNITING){
    			Log.i("my", "启动失败！START_WORK_RESULT_RECOGNITING");
    		}else if(code==VoiceRecognitionClient.START_WORK_RESULT_RECORDER_UNUSABLE){
    			Log.i("my", "启动失败！START_WORK_RESULT_RECORDER_UNUSABLE");
    		}else if(code==VoiceRecognitionClient.START_WORK_RESULT_RELEASED){
    			Log.i("my", "启动失败！START_WORK_RESULT_RELEASED");
    		}else {
    			//启动失败，错误码描述参见VoiceRecognitionClient START_WORK_RESULT_前缀的常量定义
    			Log.i("my", "启动失败!");
    		}
        default:
        	Log.i("my", "ddddddddddddddddddd");
            break;
    }
	}


	@Override
	public void onError(int errorType, int errorCode) {
		switch (errorCode) {
		case VoiceRecognitionClient.ERROR_CLIENT_UNKNOWN:
			Log.i("my", "Error:ERROR_CLIENT_UNKNOWN");
			break;
		case VoiceRecognitionClient.ERROR_CLIENT_NO_SPEECH:
			Log.i("my", "ERROR_CLIENT_NO_SPEECH");
			break;
		case VoiceRecognitionClient.ERROR_CLIENT_TOO_SHORT:
			Log.i("my", "ERROR_CLIENT_TOO_SHORT");
			break;
		case VoiceRecognitionClient.ERROR_CLIENT_JNI_EXCEPTION:
			Log.i("my", "ERROR_CLIENT_JNI_EXCEPTION");
			break;
		case VoiceRecognitionClient.ERROR_CLIENT_WHOLE_PROCESS_TIMEOUT:
			Log.i("my", "ERROR_CLIENT_WHOLE_PROCESS_TIMEOUT");
			break;

		default:
			Log.i("my", "其他错误原因");
			break;
		}
		
//		mRecognitionClient.stopVoiceRecognition();
//		mRecognitionClient.releaseInstance();
//		config = new VoiceRecognitionConfig();
//		// 设置识别开始提示音
//		config.enableBeginSoundEffect(R.raw.bdspeech_recognition_start);
//		// 设置说话结束提示音
//		config.enableEndSoundEffect(R.raw.bdspeech_speech_end);
//		mRecognitionClient = VoiceRecognitionClient.getInstance(this);
//        mRecognitionClient.setTokenApis("QN9uYivTwHgjDCgyVntlUG0q", "7c68f3f2fd3ccdf69ae9b0b70bbc6d3e");
		
//		int code = mRecognitionClient.startVoiceRecognition(this, config);
//		if(code == VoiceRecognitionClient.START_WORK_RESULT_WORKING){
//			//启动成功
//			Log.i("my", "启动成功!");
//		} else if(code==VoiceRecognitionClient.START_WORK_RESULT_NET_UNUSABLE){
//			Log.i("my", "启动失败！START_WORK_RESULT_NET_UNUSABLE");
//		}else if(code==VoiceRecognitionClient.START_WORK_RESULT_NULL_LISTENER){
//			Log.i("my", "启动失败！START_WORK_RESULT_NULL_LISTENER");
//		}else if(code==VoiceRecognitionClient.START_WORK_RESULT_RECOGNITING){
//			Log.i("my", "启动失败！START_WORK_RESULT_RECOGNITING");
//		}else if(code==VoiceRecognitionClient.START_WORK_RESULT_RECORDER_UNUSABLE){
//			Log.i("my", "启动失败！START_WORK_RESULT_RECORDER_UNUSABLE");
//		}else if(code==VoiceRecognitionClient.START_WORK_RESULT_RELEASED){
//			Log.i("my", "启动失败！START_WORK_RESULT_RELEASED");
//		}else {
//			//启动失败，错误码描述参见VoiceRecognitionClient START_WORK_RESULT_前缀的常量定义
//			Log.i("my", "启动失败!");
//		}
	}


	@Override
	public void onNetworkStatusChange(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	/**-------------------语音识别------------------------------*/

	/**-------------------语音合成------------------------------*/
	@Override
	public void onBufferProgressChanged(SpeechSynthesizer arg0, int arg1) {
		
	}

	@Override
	public void onCancel(SpeechSynthesizer arg0) {
		
	}

	@Override
	public void onError(SpeechSynthesizer arg0, SpeechError arg1) {
		
	}

	@Override
	public void onNewDataArrive(SpeechSynthesizer arg0, byte[] arg1,boolean arg2) {
		
	}

	@Override
	public void onSpeechFinish(SpeechSynthesizer arg0) {
		//判断当前步骤说完后是否需要倒计时
		if(Config.isDaojishi[this.curStep]>0){
			int time = Config.isDaojishi[this.curStep];
			try {
				Thread.sleep(time*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			for(int i=0;i<time;i++){
//				this.timeTextView.setText((i+1)+"");
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				// 注：第二个参数当前请传入任意非空字符串即可
//				SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(this, "holder",new MySpeechSynthesizerListener());
//				// 注：your-apiKey 和 your-secretKey 需要换成在百度开发者中心注册应用得到的对应值
//				speechSynthesizer.setApiKey("QN9uYivTwHgjDCgyVntlUG0q","7c68f3f2fd3ccdf69ae9b0b70bbc6d3e");
//				speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER,SpeechSynthesizer.SPEAKER_FEMALE);
//				speechSynthesizer.speak((i+1)+"");
//			}
			mRecognitionClient.stopVoiceRecognition();
			this.curStep++;
			speak(this.speechSynthesizer,this.mRecognitionClient);
		}
		else if(Config.isDaojishi[this.curStep]==-1){
			// 注：第二个参数当前请传入任意非空字符串即可
			SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(this, "holder",new MySpeechSynthesizerListener());
			// 注：your-apiKey 和 your-secretKey 需要换成在百度开发者中心注册应用得到的对应值
			speechSynthesizer.setApiKey("QN9uYivTwHgjDCgyVntlUG0q","7c68f3f2fd3ccdf69ae9b0b70bbc6d3e");
			speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER,SpeechSynthesizer.SPEAKER_FEMALE);
			speechSynthesizer.speak("恭喜你，顺利学会了一道菜");
		}
		else{
			//机器说话一结束，就开始监听
			int code = mRecognitionClient.startVoiceRecognition(this, config);
			Toast.makeText(this, "onSpeechFinish：VoiceRecognitionClient.START_WORK_RESULT_WORKING,code="+code, Toast.LENGTH_SHORT).show();
			if(code == VoiceRecognitionClient.START_WORK_RESULT_WORKING){
				//启动成功
				Log.i("my", "识别启动成功!");
				Toast.makeText(this, "识别成功", Toast.LENGTH_SHORT).show();
			} else {
				//启动失败，错误码描述参见VoiceRecognitionClient START_WORK_RESULT_前缀的常量定义
				Log.i("my", "识别启动失败!");
			}
		}
	}

	@Override
	public void onSpeechPause(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechProgressChanged(SpeechSynthesizer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechResume(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechStart(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartWorking(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSynthesizeFinish(SpeechSynthesizer arg0) {
		Toast.makeText(this, "onSynthesizeFinish", Toast.LENGTH_SHORT).show();
		
	}
	/**-------------------语音合成------------------------------*/
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cook1, menu);
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
