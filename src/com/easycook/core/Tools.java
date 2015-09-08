package com.easycook.core;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.voicerecognition.android.VoiceRecognitionClient;
import com.baidu.voicerecognition.android.VoiceRecognitionConfig;
import com.baidu.voicerecognition.android.VoiceRecognitionClient.VoiceClientStatusChangeListener;
import com.easycook.R;
import com.easycook.listener.MySpeechSynthesizerListener;

public class Tools implements VoiceClientStatusChangeListener {
	private Context context;
	private String intent = null;
	
	/**
	 * 让机器说话
	 * @param str
	 * @return
	 */
	public static boolean speak(String str,Context context){
		if(str!=null && !"".equals(str)){
			// 注：第二个参数当前请传入任意非空字符串即可
			SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(context, "holder", new MySpeechSynthesizerListener());
			// 注：your-apiKey 和 your-secretKey 需要换成在百度开发者中心注册应用得到的对应值
			speechSynthesizer.setApiKey("QN9uYivTwHgjDCgyVntlUG0q", "7c68f3f2fd3ccdf69ae9b0b70bbc6d3e");
			speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER,SpeechSynthesizer.SPEAKER_FEMALE);
			speechSynthesizer.speak(str);
			return true;
		}else
			return false;
	}
	
	
	/**
	 * 开始识别用户说的话
	 * @return
	 */
	public String recognize(Context context){
		this.context = context;
		VoiceRecognitionClient mRecognitionClient = VoiceRecognitionClient.getInstance(context);
        mRecognitionClient.setTokenApis("QN9uYivTwHgjDCgyVntlUG0q", "7c68f3f2fd3ccdf69ae9b0b70bbc6d3e");

		VoiceRecognitionConfig config = new VoiceRecognitionConfig();
		// 设置识别开始提示音
		config.enableBeginSoundEffect(R.raw.bdspeech_recognition_start);
		// 设置说话结束提示音
		config.enableEndSoundEffect(R.raw.bdspeech_speech_end);
		int code = mRecognitionClient.startVoiceRecognition(this, config);
	    if(code == VoiceRecognitionClient.START_WORK_RESULT_WORKING){
	         //启动成功
	    	Log.i("my", "启动成功!");
	    	return null;
	    } else {
	        //启动失败，错误码描述参见VoiceRecognitionClient START_WORK_RESULT_前缀的常量定义
	    	Log.i("my", "启动失败!");
	    	return Config.ERROR;
	    }
	
	}
	
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
            Log.i("my", "语音识别完成");
        	ArrayList<String> list = (ArrayList)obj;
        	for(String str : list){
        		Log.i("my", str);
        	}
        	if(list.contains("下一步")){
        		this.intent = Config.NEXT;
        	}
//            if(currentVoiceType == VOICE_TYPE_SEARCH){
//                mStatusTextView.setText(R.string.finished);
//                // obj是一个ArrayList<String>，里面有多个候选词
//            }else if(currentVoiceType== VOICE_TYPE_INPUT){
//                // obj是一个List<List<Candidate>>，里面有多个候选词
//            }
            break;
        case VoiceRecognitionClient.CLIENT_STATUS_UPDATE_RESULTS:
            //多句模式会有部分结果（一个分句）返回
        	ArrayList<String> list1 = (ArrayList)obj;
        	for(String str : list1){
        		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        	}
//            if(currentVoiceType == VOICE_TYPE_SEARCH){
//                //obj是一个ArrayList<String>，里面有多个候选词
//            } else if(currentVoiceType == VOICE_TYPE_INPUT){
//                //obj是一个List<List<Candidate>>，里面有多个候选词
//                List<List<Candidate>> result =(List<List<Candidate>>)obj;
//            }
            break;
        //用户已取消
        case VoiceRecognitionClient.CLIENT_STATUS_USER_CANCELED:
        	Log.i("my", "用户已取消");
            break;
        default:
            break;
    }
	}


	@Override
	public void onError(int errorType, int errorCode) {
		Toast.makeText(context, "erroType="+errorCode+",errorCode="+errorCode, Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onNetworkStatusChange(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
