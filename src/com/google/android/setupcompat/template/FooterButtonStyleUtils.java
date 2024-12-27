package com.google.android.setupcompat.template;

import android.content.Context;
import android.content.res.ColorStateList;
import android.widget.Button;

import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class FooterButtonStyleUtils {
    public static final HashMap defaultTextColor = new HashMap();

    /* JADX WARN: Removed duplicated region for block: B:46:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01de  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void applyButtonPartnerResources(
            android.content.Context r11,
            android.widget.Button r12,
            boolean r13,
            boolean r14,
            com.google.android.setupcompat.internal.FooterButtonPartnerConfig r15) {
        /*
            Method dump skipped, instructions count: 555
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.setupcompat.template.FooterButtonStyleUtils.applyButtonPartnerResources(android.content.Context,"
                    + " android.widget.Button, boolean, boolean,"
                    + " com.google.android.setupcompat.internal.FooterButtonPartnerConfig):void");
    }

    public static void updateButtonTextDisabledColorWithPartnerConfig(
            Context context, Button button, PartnerConfig partnerConfig) {
        if (PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig)) {
            int color = PartnerConfigHelper.get(context).getColor(context, partnerConfig);
            if (color != 0) {
                button.setTextColor(ColorStateList.valueOf(color));
                return;
            }
            return;
        }
        HashMap hashMap = defaultTextColor;
        if (!hashMap.containsKey(Integer.valueOf(button.getId()))) {
            throw new IllegalStateException("There is no saved default color for button");
        }
        button.setTextColor((ColorStateList) hashMap.get(Integer.valueOf(button.getId())));
    }
}
