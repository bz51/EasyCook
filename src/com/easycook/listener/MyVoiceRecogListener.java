package com.easycook.listener;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.voicerecognition.android.VoiceRecognitionClient;
import com.baidu.voicerecognition.android.VoiceRecognitionClient.VoiceClientStatusChangeListener;

public class MyVoiceRecogListener implements VoiceClientStatusChangeListener{
	private Context context;
	
	public MyVoiceRecogListener(Context context){
		this.context = context;
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
