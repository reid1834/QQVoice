package com.reid.qqvoice;

/**
 * Created by reid on 2018/3/20.
 */

public class VoiceTools {
    static {
        System.loadLibrary("changeVoice");
        System.loadLibrary("fmod");
        System.loadLibrary("fmodL");
    }
    public static native void changeVoice(String path, int mode);
}
