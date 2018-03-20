//
// Created by Reid on 2018/3/20.
//
#include "com_reid_qqvoice_VoiceTools.h"
#include "fmod.hpp"
#include <unistd.h>
#include <android/log.h>

using namespace FMOD;

//定义五种不同到模式
#define MODE_NORMAL 0
#define MODE_LUOLI 1
#define MODE_DASHU 2
#define MODE_JINGSONG 3
#define MODE_GAOGUAI
#define MODE_KONGLING 5

#define LOGI(FORMAT, ...) __android_log_print(ANDROID_LOG_INFO,"axe",FORMAT,##__VA_ARGS__);
#define LOGE(FORMAT, ...) __android_log_print(ANDROID_LOG_ERROR,"axe",FORMAT,##__VA_ARGS__);
extern "C"

JNICALL
JNIEXPORT void JNICALL Java_com_reid_qqvoice_VoiceTools_changeVoice
(JNIEnv * jniEnv, jclass jclass1, jstring jstring1, jint mode) {
    //初始化fmod
    FMOD::System *system;
    FMOD::System_Create(&system);

    Sound *sound;
    //通道 （生硬是由多种音效组成）
    Channel *channel;
    //音频
    DSP *pDSP;
    //速度
    float frequency;

    system->init(32, FMOD_INIT_NORMAL, NULL);

    //将jstring转为char
    const char *path = jniEnv->GetStringUTFChars(jstring1, NULL);
    system->createSound(path, FMOD_DEFAULT, NULL, &sound);
}
