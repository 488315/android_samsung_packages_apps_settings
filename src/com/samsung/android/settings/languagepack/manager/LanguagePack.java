package com.samsung.android.settings.languagepack.manager;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LanguagePack {

    @SerializedName("ondevicePkgName")
    String mAsrPkgName;

    @SerializedName("languageDisplayName")
    String mDisplayName;

    @SerializedName("extraTransPkgNames")
    List<String> mExtraTranslationPkgNames;

    @SerializedName("standardLang")
    boolean mIsStandardLanguageCode;

    @SerializedName("languageCode")
    String mLanguageCode;

    @SerializedName("order")
    int mOrder;

    @SerializedName("s2tPkgName")
    String mS2tPkgName;

    @SerializedName("showLanguageName")
    boolean mShowLanguageName;

    @SerializedName("showLocale")
    boolean mShowLocale;

    @SerializedName("supportS2T")
    boolean mSupportS2T;

    @SerializedName("transPkgName")
    String mTranslationPkgName;

    @SerializedName("ondeviceTransType")
    String mTranslationType;

    @SerializedName("ttsPkgs")
    List<TtsInfo> mTtsPkgInfoList;
}
