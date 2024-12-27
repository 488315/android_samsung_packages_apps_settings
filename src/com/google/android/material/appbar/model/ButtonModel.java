package com.google.android.material.appbar.model;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0016\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B-\b\u0007\u0012\n"
                + "\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\n"
                + "\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\n"
                + "\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0007\u0010\b¨\u0006"
                + "\t"
        },
        d2 = {
            "Lcom/google/android/material/appbar/model/ButtonModel;",
            ApnSettings.MVNO_NONE,
            ApnSettings.MVNO_NONE,
            "text",
            "Lcom/google/android/material/appbar/model/AppBarModel$OnClickListener;",
            "clickListener",
            "contentDescription",
            "<init>",
            "(Ljava/lang/String;Lcom/google/android/material/appbar/model/AppBarModel$OnClickListener;Ljava/lang/String;)V",
            "material_release"
        },
        k = 1,
        mv = {1, 8, 0})
/* loaded from: classes3.dex */
public class ButtonModel {
    public final AppBarModel.OnClickListener clickListener;
    public final String contentDescription;
    public final String text;

    public ButtonModel() {
        this(null, null, null, 7, null);
    }

    public ButtonModel(String str) {
        this(str, null, null, 6, null);
    }

    public ButtonModel(String str, AppBarModel.OnClickListener onClickListener) {
        this(str, onClickListener, null, 4, null);
    }

    public ButtonModel(String str, AppBarModel.OnClickListener onClickListener, String str2) {
        this.text = str;
        this.clickListener = onClickListener;
        this.contentDescription = str2;
    }

    public /* synthetic */ ButtonModel(
            String str,
            AppBarModel.OnClickListener onClickListener,
            String str2,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(
                (i & 1) != 0 ? null : str,
                (i & 2) != 0 ? null : onClickListener,
                (i & 4) != 0 ? null : str2);
    }
}
