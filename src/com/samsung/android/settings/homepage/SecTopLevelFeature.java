package com.samsung.android.settings.homepage;

import android.content.Context;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.core.PreferenceXmlParserUtils;

import com.samsung.android.settings.Trace;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecTopLevelFeature {
    public static SecTopLevelFeature sInstance;
    public Hashtable mFeatureList;
    public List mTopLevelPreferenceXmlMeta;

    public static SecTopLevelFeature getInstance() {
        if (sInstance == null) {
            SecTopLevelFeature secTopLevelFeature = new SecTopLevelFeature();
            secTopLevelFeature.mFeatureList = new Hashtable();
            sInstance = secTopLevelFeature;
        }
        return sInstance;
    }

    public final boolean getBoolean(String str) {
        try {
            String str2 = (String) this.mFeatureList.get(str);
            if (str2 != null) {
                return Boolean.parseBoolean(str2);
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public final void loadTopLevelPreferenceXmlMeta(Context context) {
        Trace.beginSection("SecTopLevelCache#loadTopLevelPreferenceXmlMeta");
        if (this.mTopLevelPreferenceXmlMeta == null) {
            try {
                this.mTopLevelPreferenceXmlMeta =
                        PreferenceXmlParserUtils.extractMetadata(
                                context, R.xml.sec_top_level_settings, 12299);
            } catch (IOException | XmlPullParserException e) {
                Log.e(
                        "SecTopLevelFeature",
                        "Failed to parse preference xml for getting controllers",
                        e);
            }
            Log.i("SecTopLevelFeature", "loaded mTopLevelPreferenceXmlMeta");
        }
        Trace.endSection();
    }

    public final void setBoolean(String str, boolean z) {
        try {
            if (this.mFeatureList.containsKey(str)) {
                this.mFeatureList.replace(str, Boolean.toString(z));
            } else {
                this.mFeatureList.put(str, Boolean.toString(z));
            }
        } catch (Exception unused) {
        }
    }
}
