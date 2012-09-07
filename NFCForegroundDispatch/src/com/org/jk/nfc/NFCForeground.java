package com.org.jk.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.widget.TextView;

public class NFCForeground extends Activity {

	TextView tagText;
	NfcAdapter nfcAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfcforeground);
        
        nfcAdapter = NfcAdapter.getDefaultAdapter();
        tagText = (TextView) findViewById(R.id.tagText);
    }
    
    @Override
    protected void onResume() {
    	IntentFilter[] intentFilter = { new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED) };
 		PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(
				this, NFCForeground.class)
				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    	nfcAdapter.enableForegroundDispatch(this, intent, intentFilter, null);
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	nfcAdapter.disableForegroundDispatch(this);
    	super.onPause();
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
    	super.onNewIntent(intent);
    	String tagData = "";
    	Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
    	Ndef ndef = Ndef.get(tag);

    	if(ndef!=null){
    		tagData += "Type: " + ndef.getType()+"\n";
    		tagData += "Max Size: " + ndef.getMaxSize() + "bytes";
    	}
    	tagText.setText(tagData);
    }
}
