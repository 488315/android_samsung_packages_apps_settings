package com.samsung.android.settings.share;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.ActionMenuView;
import androidx.arch.core.util.Function;

import com.samsung.android.settings.share.structure.ShareComponent;

import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SelectAppActivity$$ExternalSyntheticLambda0
        implements Function, ActionMenuView.OnMenuItemClickListener {
    public final /* synthetic */ Object f$0;

    @Override // androidx.arch.core.util.Function
    public Object apply(Object obj) {
        int i = SelectAppActivity.$r8$clinit;
        return new ArrayList(
                Arrays.asList(
                        (ShareComponent[])
                                ((Bundle) this.f$0)
                                        .getParcelableArray((String) obj, ShareComponent.class)));
    }

    @Override // androidx.appcompat.widget.ActionMenuView.OnMenuItemClickListener
    public boolean onMenuItemClick(MenuItem menuItem) {
        int i = SelectAppActivity.$r8$clinit;
        return ((SelectAppActivity) this.f$0).selectEditMenuItem$1(menuItem);
    }
}
