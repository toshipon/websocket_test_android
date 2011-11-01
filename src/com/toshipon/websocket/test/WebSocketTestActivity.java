package com.toshipon.websocket.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import de.roderick.weberknecht.WebSocketEventHandler;
import de.roderick.weberknecht.WebSocketMessage;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class WebSocketTestActivity extends Activity {
	private static int DISPLAY_TIME = 2; // second
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        
        if(hasFocus){
            final ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(DISPLAY_TIME);
                    }
                    catch (InterruptedException e) {
                        // pass
                    }
                    finally{
                    	Intent intent = new Intent(getApplicationContext(), SendMessageActivity.class);
         				startActivity(intent);
         				finish();
                    }
                }
            });
        }
    }
}