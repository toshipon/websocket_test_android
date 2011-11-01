package com.toshipon.websocket.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import de.roderick.weberknecht.WebSocketEventHandler;
import de.roderick.weberknecht.WebSocketMessage;

public class SendMessageActivity extends Activity {
	
	private static final String WS_URI = "ws://XXX.XX.XX.XXX:XXXX/";
	private static final String NULLPO_KEY = "nullpo";
	private static final String GATT_KEY = "gatt";
	
	private static final String NULLPO_TEXT = "�ʂ�� ( �L�́M)";
	private static final String GATT_TEXT = "�ޯ (�R'��`)";
	
	private Handler handler = new Handler();
	private Activity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Log.d("DEBUG", "opened message window");
	    setContentView(R.layout.message);
	    activity = this;
	    
	    // �ʂ�ۃ{�^���������̋���
	    Button nullpoBtn = (Button) findViewById(R.id.btn_null_btn);
	    nullpoBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("DEBUG", "nullpo button clecked");
				WebSocketManager.send(NULLPO_KEY);
				setMessage(NULLPO_TEXT, Color.BLUE);
			}
		});

	    // �K�b�{�^���������̋���
	    Button gattBtn = (Button) findViewById(R.id.btn_ga_btn);
	    gattBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("DEBUG", "gatt button clecked");
				WebSocketManager.send(GATT_KEY);
				setMessage(GATT_TEXT, Color.GREEN);
			}
		});
	    
	    // WebSocket�ʐM�J�n
	    WebSocketManager.connect(WS_URI, new WebSocketEventHandler() {
 			
 			@Override
 			public void onOpen() {
 				Log.d("DEBUG", "websocket connect open");
 			}
 			
 			@Override
 			public void onMessage(WebSocketMessage message) {
 				Log.d("DEBUG", "websocket message");
 				
 				if (NULLPO_KEY.equals(message.getText())) {
					setMessage(NULLPO_TEXT, Color.RED);
 				} else if (GATT_KEY.equals(message.getText())) {
 					setMessage(GATT_TEXT, Color.YELLOW);
 				}
 			}
 			
 			@Override
 			public void onClose() {
 				Log.d("DEBUG", "websocket connect close");
 			}
 		});
	}
	
	private void setMessage(final String message, final int color) {
		Log.d("DEBUG", "send message");
		
		// WebSocketHandler��onMessage�͕ʃX���b�h�Ȃ̂�handler��p����view�̏����������s��
		handler.post(new Runnable(){
	        @Override
	        public void run() {
	            TextView messageArea = (TextView) activity.findViewById(R.id.message_area);
	    		messageArea.setText(message);
	    		messageArea.setTextColor(color);
	        }
	    });
		
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		WebSocketManager.close();
		return super.clone();
	}
}
