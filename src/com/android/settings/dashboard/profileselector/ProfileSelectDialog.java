package com.android.settings.dashboard.profileselector;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;

import com.android.internal.widget.DialogTitle;
import com.android.internal.widget.GridLayoutManager;
import com.android.internal.widget.RecyclerView;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.dashboard.DashboardFeatureProviderImpl;
import com.android.settings.homepage.SettingsHomepageActivity;
import com.android.settings.homepage.TopLevelHighlightMixin;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.drawer.Tile;

import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.homepage.HomepageUtils;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ProfileSelectDialog extends DialogFragment implements UserAdapter.OnClickListener {
    public static final boolean DEBUG = Log.isLoggable("ProfileSelectDialog", 3);
    public DialogInterface.OnCancelListener mOnCancelListener;
    public DialogInterface.OnDismissListener mOnDismissListener;
    public DialogInterface.OnShowListener mOnShowListener;
    public Tile mSelectedTile;
    public int mSourceMetricCategory;

    public static AlertDialog createDialog(
            Context context, List list, UserAdapter.OnClickListener onClickListener) {
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(LayoutInflater.class);
        DialogTitle inflate =
                layoutInflater.inflate(R.layout.sec_user_select_title, (ViewGroup) null);
        inflate.setText(R.string.sec_select_profile);
        View inflate2 = layoutInflater.inflate(R.layout.sec_user_select, (ViewGroup) null);
        RecyclerView findViewById = inflate2.findViewById(R.id.list);
        int i = UserAdapter.$r8$clinit;
        findViewById.setAdapter(
                new RecyclerView
                        .Adapter() { // from class:
                                     // com.android.settings.dashboard.profileselector.UserAdapter.1
                    public final /* synthetic */ OnClickListener val$onClickListener;

                    public AnonymousClass1(OnClickListener onClickListener2) {
                        r2 = onClickListener2;
                    }

                    public final int getItemCount() {
                        return UserAdapter.this.mUserDetails.size();
                    }

                    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2) {
                        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
                        UserDetails userDetails =
                                (UserDetails) UserAdapter.this.mUserDetails.get(i2);
                        viewHolder2.mIconView.setImageDrawable(userDetails.mIcon);
                        TextView textView = viewHolder2.mTitleView;
                        String str = userDetails.mTitle;
                        textView.setText(str);
                        View view = viewHolder2.mButtonView;
                        if (view != null) {
                            view.setContentDescription(str);
                        }
                    }

                    public final RecyclerView.ViewHolder onCreateViewHolder(
                            ViewGroup viewGroup, int i2) {
                        View m =
                                MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0
                                        .m(
                                                viewGroup,
                                                R.layout.sec_user_select_item,
                                                viewGroup,
                                                false);
                        final OnClickListener onClickListener2 = r2;
                        final ViewHolder viewHolder = new ViewHolder(m);
                        View view = viewHolder.mButtonView;
                        if (view != null) {
                            view.setOnClickListener(
                                    new View
                                            .OnClickListener() { // from class:
                                                                 // com.android.settings.dashboard.profileselector.UserAdapter$ViewHolder$$ExternalSyntheticLambda0
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view2) {
                                            onClickListener2.onClick(
                                                    UserAdapter.ViewHolder.this
                                                            .getAdapterPosition());
                                        }
                                    });
                        }
                        return viewHolder;
                    }
                });
        findViewById.setLayoutManager(new GridLayoutManager(context, list.size() != 2 ? 3 : 2));
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.P.mCustomTitleView = inflate;
        builder.setView(inflate2);
        AlertDialog create = builder.create();
        create.getWindow().setType(VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_403);
        return create;
    }

    public static void show(
            FragmentManagerImpl fragmentManagerImpl,
            Tile tile,
            int i,
            TopLevelHighlightMixin topLevelHighlightMixin,
            TopLevelHighlightMixin topLevelHighlightMixin2,
            TopLevelHighlightMixin topLevelHighlightMixin3) {
        ProfileSelectDialog profileSelectDialog = new ProfileSelectDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("selectedTile", tile);
        bundle.putInt("sourceMetricCategory", i);
        profileSelectDialog.setArguments(bundle);
        profileSelectDialog.mOnShowListener = topLevelHighlightMixin;
        profileSelectDialog.mOnDismissListener = topLevelHighlightMixin2;
        profileSelectDialog.mOnCancelListener = topLevelHighlightMixin3;
        profileSelectDialog.show(fragmentManagerImpl, "select_profile");
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        DialogInterface.OnCancelListener onCancelListener = this.mOnCancelListener;
        if (onCancelListener != null) {
            onCancelListener.onCancel(dialogInterface);
        }
    }

    @Override // com.android.settings.dashboard.profileselector.UserAdapter.OnClickListener
    public final void onClick(int i) {
        UserHandle userHandle = (UserHandle) this.mSelectedTile.userHandle.get(i);
        if (!this.mSelectedTile.pendingIntentMap.isEmpty()) {
            PendingIntent pendingIntent =
                    (PendingIntent) this.mSelectedTile.pendingIntentMap.get(userHandle);
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl
                    .getMetricsFeatureProvider()
                    .logSettingsTileClickWithProfile(
                            this.mSourceMetricCategory,
                            this.mSelectedTile.getKey(getContext()),
                            i == 1);
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                Log.w(
                        "ProfileSelectDialog",
                        "Failed executing pendingIntent. " + pendingIntent.getIntent(),
                        e);
            }
        } else {
            Intent intent = new Intent(this.mSelectedTile.mIntent);
            FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
            if (featureFactoryImpl2 == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            SettingsMetricsFeatureProvider metricsFeatureProvider =
                    featureFactoryImpl2.getMetricsFeatureProvider();
            int i2 = this.mSourceMetricCategory;
            boolean z = i == 1;
            metricsFeatureProvider.getClass();
            ComponentName component = intent.getComponent();
            metricsFeatureProvider.logSettingsTileClickWithProfile(
                    i2, component != null ? component.flattenToString() : intent.getAction(), z);
            intent.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_UID);
            if (i == 0 && (getActivity() instanceof SettingsHomepageActivity)) {
                FeatureFactoryImpl featureFactoryImpl3 = FeatureFactoryImpl._factory;
                if (featureFactoryImpl3 == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                DashboardFeatureProviderImpl dashboardFeatureProvider =
                        featureFactoryImpl3.getDashboardFeatureProvider();
                FragmentActivity activity = getActivity();
                dashboardFeatureProvider.getDashboardKeyForTile(this.mSelectedTile);
                ArrayList arrayList = HomepageUtils.SEPARATORS;
                HomepageUtils.startActivity(
                        activity,
                        intent,
                        (Utils.isLaunchModeSingleInstance(activity, intent)
                                        || (intent.getFlags() & 268435456) != 0)
                                ? -1
                                : CustomDeviceManager.QUICK_PANEL_ALL,
                        userHandle);
            } else {
                getActivity().startActivityAsUser(intent, userHandle);
            }
        }
        dismissInternal(false, false);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle requireArguments = requireArguments();
        this.mSelectedTile = (Tile) requireArguments.getParcelable("selectedTile", Tile.class);
        this.mSourceMetricCategory = requireArguments.getInt("sourceMetricCategory");
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        return createDialog(getContext(), this.mSelectedTile.userHandle, this);
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        DialogInterface.OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialogInterface);
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        DialogInterface.OnShowListener onShowListener = this.mOnShowListener;
        if (onShowListener != null) {
            onShowListener.onShow(this.mDialog);
        }
    }
}
