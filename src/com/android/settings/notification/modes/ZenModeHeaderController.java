package com.android.settings.notification.modes;

import android.R;
import android.app.Flags;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.preference.Preference;

import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.LayoutPreference;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeHeaderController extends AbstractZenModePreferenceController {
    public final DashboardFragment mFragment;
    public EntityHeaderController mHeaderController;

    public static void $r8$lambda$FnGwAu2EoquyeWkeO6q1AN_ibsE(
            ZenModeHeaderController zenModeHeaderController, Drawable drawable) {
        EntityHeaderController entityHeaderController = zenModeHeaderController.mHeaderController;
        Context context = zenModeHeaderController.mContext;
        Drawable mutate = drawable.mutate();
        mutate.setTintList(Utils.getColorAttr(context, R.attr.colorControlNormal));
        entityHeaderController.setIcon(mutate);
        entityHeaderController.done(false);
    }

    public ZenModeHeaderController(
            Context context, DashboardFragment dashboardFragment, ZenModesBackend zenModesBackend) {
        super(context, "header", zenModesBackend);
        this.mFragment = dashboardFragment;
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return Flags.modesApi();
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, ZenMode zenMode) {
        DashboardFragment dashboardFragment = this.mFragment;
        if (dashboardFragment == null) {
            return;
        }
        preference.setSelectable(false);
        if (this.mHeaderController == null) {
            this.mHeaderController =
                    new EntityHeaderController(
                            dashboardFragment.getActivity(),
                            dashboardFragment,
                            ((LayoutPreference) preference)
                                    .mRootView.findViewById(
                                            com.android.settings.R.id.entity_header));
        }
        ListenableFuture icon = zenMode.getIcon(this.mContext, IconLoader.getInstance());
        Consumer consumer =
                new Consumer() { // from class:
                                 // com.android.settings.notification.modes.ZenModeHeaderController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ZenModeHeaderController.$r8$lambda$FnGwAu2EoquyeWkeO6q1AN_ibsE(
                                ZenModeHeaderController.this, (Drawable) obj);
                    }
                };
        icon.addListener(
                new Futures.CallbackListener(icon, new FutureUtil$1(consumer, new Object[0])),
                this.mContext.getMainExecutor());
    }
}
