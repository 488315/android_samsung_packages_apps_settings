package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.content.Context;
import android.util.AttributeSet;

import com.android.settings.ProgressCategory;
import com.android.settings.R;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioStreamsProgressCategoryPreference extends ProgressCategory {
    public AudioStreamsProgressCategoryPreference(Context context) {
        super(context);
        this.mEmptyTextRes = R.string.audio_streams_empty;
    }

    public final List getAllAudioStreamPreferences() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < getPreferenceCount(); i++) {
            if (getPreference(i) instanceof AudioStreamPreference) {
                arrayList.add((AudioStreamPreference) getPreference(i));
            }
        }
        return arrayList;
    }

    public AudioStreamsProgressCategoryPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEmptyTextRes = R.string.audio_streams_empty;
    }

    public AudioStreamsProgressCategoryPreference(
            Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mEmptyTextRes = R.string.audio_streams_empty;
    }

    public AudioStreamsProgressCategoryPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mEmptyTextRes = R.string.audio_streams_empty;
    }
}
