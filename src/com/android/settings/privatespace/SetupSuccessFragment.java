package com.android.settings.privatespace;

import android.app.ActivityManager;
import android.app.role.RoleManager;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.FragmentActivity;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;
import com.android.settingslib.widget.LottieColorUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SetupSuccessFragment extends InstrumentedFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2063;
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (!Flags.allowPrivateProfile() || !android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            return null;
        }
        GlifLayout glifLayout =
                (GlifLayout)
                        layoutInflater.inflate(
                                R.layout.private_space_setup_success, viewGroup, false);
        ((FooterBarMixin) glifLayout.getMixin(FooterBarMixin.class))
                .setPrimaryButton(
                        new FooterButton(
                                getContext().getString(R.string.private_space_done_label),
                                new View.OnClickListener() { // from class:
                                    // com.android.settings.privatespace.SetupSuccessFragment$$ExternalSyntheticLambda0
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        SetupSuccessFragment setupSuccessFragment =
                                                SetupSuccessFragment.this;
                                        FragmentActivity activity =
                                                setupSuccessFragment.getActivity();
                                        if (activity != null) {
                                            setupSuccessFragment.mMetricsFeatureProvider.action(
                                                    setupSuccessFragment.getContext(),
                                                    1893,
                                                    new Pair[0]);
                                            Intent intent =
                                                    new Intent("android.intent.action.ALL_APPS");
                                            ResolveInfo resolveActivityAsUser =
                                                    activity.getPackageManager()
                                                            .resolveActivityAsUser(
                                                                    new Intent(
                                                                                    "android.intent.action.MAIN")
                                                                            .addCategory(
                                                                                    "android.intent.category.HOME"),
                                                                    1048576,
                                                                    activity.getUserId());
                                            if (resolveActivityAsUser != null
                                                    && ((RoleManager)
                                                                    setupSuccessFragment
                                                                            .getContext()
                                                                            .getSystemService(
                                                                                    RoleManager
                                                                                            .class))
                                                            .getRoleHolders("android.app.role.HOME")
                                                            .contains(
                                                                    resolveActivityAsUser
                                                                            .activityInfo
                                                                            .packageName)) {
                                                intent.setPackage(
                                                        resolveActivityAsUser
                                                                .activityInfo
                                                                .packageName);
                                                intent.setComponent(
                                                        resolveActivityAsUser.activityInfo
                                                                .getComponentName());
                                            }
                                            activity.setTheme(2132084380);
                                            if (intent.getPackage() != null) {
                                                Toast.makeCustomToastWithIcon(
                                                                setupSuccessFragment.getContext(),
                                                                null,
                                                                setupSuccessFragment
                                                                        .getContext()
                                                                        .getString(
                                                                                R.string
                                                                                        .private_space_scrolldown_to_access),
                                                                0,
                                                                setupSuccessFragment
                                                                        .getContext()
                                                                        .getDrawable(
                                                                                R.drawable
                                                                                        .ic_private_space_icon))
                                                        .show();
                                                setupSuccessFragment.startActivity(intent);
                                            }
                                            Log.i(
                                                    "SetupSuccessFragment",
                                                    "Private space setup complete");
                                            Iterator<ActivityManager.AppTask> it =
                                                    ((ActivityManager)
                                                                    activity.getSystemService(
                                                                            ActivityManager.class))
                                                            .getAppTasks()
                                                            .iterator();
                                            while (it.hasNext()) {
                                                it.next().finishAndRemoveTask();
                                            }
                                        }
                                    }
                                },
                                5,
                                2132083805));
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new AnonymousClass1(true));
        LottieColorUtils.applyDynamicColors(
                getContext(), (LottieAnimationView) glifLayout.findViewById(R.id.lottie_animation));
        return glifLayout;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.privatespace.SetupSuccessFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends OnBackPressedCallback {
        @Override // androidx.activity.OnBackPressedCallback
        public final void handleOnBackPressed() {}
    }
}
