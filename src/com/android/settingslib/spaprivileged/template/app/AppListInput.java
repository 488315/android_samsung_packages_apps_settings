package com.android.settingslib.spaprivileged.template.app;

import androidx.compose.ui.unit.Dp;

import com.android.settingslib.spaprivileged.model.app.AppListModel;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppListInput {
    public final float bottomPadding;
    public final AppListConfig config;
    public final Function2 header;
    public final AppListModel listModel;
    public final String noItemMessage;
    public final AppListState state;

    public AppListInput(
            AppListConfig appListConfig,
            AppListModel listModel,
            AppListState appListState,
            Function2 header,
            String str,
            float f) {
        Intrinsics.checkNotNullParameter(listModel, "listModel");
        Intrinsics.checkNotNullParameter(header, "header");
        this.config = appListConfig;
        this.listModel = listModel;
        this.state = appListState;
        this.header = header;
        this.noItemMessage = str;
        this.bottomPadding = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppListInput)) {
            return false;
        }
        AppListInput appListInput = (AppListInput) obj;
        return Intrinsics.areEqual(this.config, appListInput.config)
                && Intrinsics.areEqual(this.listModel, appListInput.listModel)
                && Intrinsics.areEqual(this.state, appListInput.state)
                && Intrinsics.areEqual(this.header, appListInput.header)
                && Intrinsics.areEqual(this.noItemMessage, appListInput.noItemMessage)
                && Dp.m585equalsimpl0(this.bottomPadding, appListInput.bottomPadding);
    }

    public final int hashCode() {
        int hashCode =
                (this.header.hashCode()
                                + ((this.state.hashCode()
                                                + ((this.listModel.hashCode()
                                                                + (this.config.hashCode() * 31))
                                                        * 31))
                                        * 31))
                        * 31;
        String str = this.noItemMessage;
        return Float.hashCode(this.bottomPadding)
                + ((hashCode + (str == null ? 0 : str.hashCode())) * 31);
    }

    public final String toString() {
        return "AppListInput(config="
                + this.config
                + ", listModel="
                + this.listModel
                + ", state="
                + this.state
                + ", header="
                + this.header
                + ", noItemMessage="
                + this.noItemMessage
                + ", bottomPadding="
                + Dp.m586toStringimpl(this.bottomPadding)
                + ")";
    }
}
