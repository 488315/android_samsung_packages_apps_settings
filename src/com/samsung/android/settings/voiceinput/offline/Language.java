package com.samsung.android.settings.voiceinput.offline;

import android.content.Intent;
import android.net.Uri;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class Language {
    private String mLangPackage;
    private String mLanguageName;
    private Locale mLocale;
    private int mStatus = 1;
    private Intent mStoreIntent;

    public Language(Locale locale, String str, String str2) {
        this.mLocale = locale;
        this.mLanguageName = str;
        this.mLangPackage = str2;
        Intent intent = new Intent();
        this.mStoreIntent = intent;
        intent.setData(Uri.parse("samsungapps://ProductDetail/" + str2));
        this.mStoreIntent.putExtra("type", "cover");
        this.mStoreIntent.addFlags(335544352);
    }

    public final String getLangPackage() {
        return this.mLangPackage;
    }

    public final String getLanguageName() {
        return this.mLanguageName;
    }

    public final Locale getLocale() {
        return this.mLocale;
    }

    public final int getStatus() {
        return this.mStatus;
    }

    public final Intent getStoreIntent() {
        return this.mStoreIntent;
    }

    public final void setStatus(int i) {
        this.mStatus = i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Language{mLanguageName='");
        sb.append(this.mLanguageName);
        sb.append("', mLangPackage='");
        sb.append(this.mLangPackage);
        sb.append("', mStoreIntent=");
        sb.append(this.mStoreIntent);
        sb.append(", mStatus=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.mStatus, '}');
    }
}
