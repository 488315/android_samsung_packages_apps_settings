package com.google.android.material.appbar.model;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u001a\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0004\b\u0007\u0010\b¨\u0006"
                + "\t"
        },
        d2 = {
            "Lcom/google/android/material/appbar/model/ButtonListModel;",
            ApnSettings.MVNO_NONE,
            "Lcom/google/android/material/appbar/model/ButtonStyle;",
            "buttonStyle",
            ApnSettings.MVNO_NONE,
            "Lcom/google/android/material/appbar/model/ButtonModel;",
            "buttonModels",
            "<init>",
            "(Lcom/google/android/material/appbar/model/ButtonStyle;Ljava/util/List;)V",
            "material_release"
        },
        k = 1,
        mv = {1, 8, 0})
/* loaded from: classes3.dex */
public final class ButtonListModel {
    public final List buttonModels;
    public final ButtonStyle buttonStyle;

    public ButtonListModel(ButtonStyle buttonStyle, List<? extends ButtonModel> buttonModels) {
        Intrinsics.checkNotNullParameter(buttonStyle, "buttonStyle");
        Intrinsics.checkNotNullParameter(buttonModels, "buttonModels");
        this.buttonStyle = buttonStyle;
        this.buttonModels = buttonModels;
    }
}
