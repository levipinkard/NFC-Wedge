package com.levipinkard.nfcwedge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import java.util.Random;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        //setContentView(R.layout.activity_main);
            if(getIntent() != null) {
                Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
                if (tag != null) {
                    Log.d(TAG, "tag ID = " + bytesToHex(tag.getId()).toLowerCase());
                    ClipData uid = ClipData.newPlainText("simple text", bytesToHex(tag.getId()).toLowerCase());
                    clipboard.setPrimaryClip(uid);
                    this.moveTaskToBack(true);
                    SystemClock.sleep(5000);
                    Random randomizer = new Random(System.currentTimeMillis());
                    for(int i = 0; i < 20; i++) {
                        uid = ClipData.newPlainText("simple text", Integer.toString(randomizer.nextInt()));
                        clipboard.setPrimaryClip(uid);
                    }
                    this.finishAndRemoveTask();
                }
            }


    }
    @Override protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
    protected static String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = ("0123456789ABCDEF").toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}