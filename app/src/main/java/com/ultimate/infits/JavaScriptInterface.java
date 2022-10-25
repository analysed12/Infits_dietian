package com.ultimate.infits;

import android.webkit.JavascriptInterface;

public class JavaScriptInterface {
    LiveAct liveAct;
    JavaScriptInterface(LiveAct callAct){
        this.liveAct = callAct;
    }
    @JavascriptInterface
    public void onPeerConnected(){
        liveAct.onPeerConnected();
    }
}
