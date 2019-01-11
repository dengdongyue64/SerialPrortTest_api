package com.example.serialprorttest_api;

import java.io.IOException;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends SerialPortActivity {
	private EditText mReception;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.console);

		mReception = (EditText) findViewById(R.id.EditTextReception);

		EditText Emission = (EditText) findViewById(R.id.EditTextEmission);
		Emission.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				int i;
				CharSequence t = v.getText();
				char[] text = new char[t.length()];
				for (i = 0; i < t.length(); i++) {
					text[i] = t.charAt(i);
				}
				try {
					mOutputStream.write(new String(text).getBytes());
					mOutputStream.write('\n');
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			}
		});
	}

	@Override
	protected void onDataReceived(final byte[] buffer, final int size) {
		final StringBuffer sb = new StringBuffer();
		runOnUiThread(new Runnable() {
			public void run() {
				if (mReception != null) {
					mReception.append(new String(buffer, 0, size));
					sb.append(new String(buffer, 0, size));
				}
			}
		});
		Log.i("hehe", sb.toString());
	}
}
