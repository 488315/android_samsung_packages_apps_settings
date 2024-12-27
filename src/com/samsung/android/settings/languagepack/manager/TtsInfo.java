package com.samsung.android.settings.languagepack.manager;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TtsInfo {

    @SerializedName("engine")
    String mEngine;

    @SerializedName("name")
    String mName;

    @SerializedName("target")
    String mTarget;

    @SerializedName("type")
    String mType;

    @SerializedName("variant")
    List<String> mVariants;
}
