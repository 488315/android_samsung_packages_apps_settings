package com.android.settings.tts;

import android.app.Application;
import android.speech.tts.TextToSpeech;

import androidx.lifecycle.AndroidViewModel;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TextToSpeechViewModel extends AndroidViewModel {
    public final Application mApplication;
    public TextToSpeech mTts;

    public TextToSpeechViewModel(Application application) {
        super(application);
        this.mApplication = application;
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        this.mTts.shutdown();
        this.mTts = null;
    }
}
