package com.android.settings.network.telephony;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.network.SubscriptionUtil;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RenameMobileNetworkDialogFragment extends InstrumentedDialogFragment {
    public Spinner mColorSpinner;
    public Color[] mColors;
    public Map mLightDarkMap;
    public EditText mNameView;
    public int mSubId;
    public SubscriptionManager mSubscriptionManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Color {
        public final int mColor;
        public final ShapeDrawable mDrawable;
        public final String mLabel;

        public Color(String str, int i, int i2, int i3) {
            this.mLabel = str;
            this.mColor = i;
            ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
            this.mDrawable = shapeDrawable;
            shapeDrawable.setIntrinsicHeight(i2);
            shapeDrawable.setIntrinsicWidth(i2);
            shapeDrawable.getPaint().setStrokeWidth(i3);
            shapeDrawable.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
            shapeDrawable.getPaint().setColor(i);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ColorAdapter extends ArrayAdapter {
        public final Context mContext;
        public final int mItemResId;

        public ColorAdapter(Context context, Color[] colorArr) {
            super(context, R.layout.dialog_mobile_network_color_picker_item, colorArr);
            this.mContext = context;
            this.mItemResId = R.layout.dialog_mobile_network_color_picker_item;
        }

        @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter,
                  // android.widget.SpinnerAdapter
        public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return getView(i, view, viewGroup);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) this.mContext.getSystemService("layout_inflater");
            if (view == null) {
                view = layoutInflater.inflate(this.mItemResId, (ViewGroup) null);
            }
            boolean z =
                    (RenameMobileNetworkDialogFragment.this.getResources().getConfiguration().uiMode
                                    & 48)
                            == 32;
            ImageView imageView = (ImageView) view.findViewById(R.id.color_icon);
            Color color = (Color) getItem(i);
            if (z) {
                Paint paint = color.mDrawable.getPaint();
                Map map = RenameMobileNetworkDialogFragment.this.mLightDarkMap;
                int i2 = color.mColor;
                Integer valueOf = Integer.valueOf(i2);
                Object valueOf2 = Integer.valueOf(i2);
                Object obj = ((ImmutableMap) map).get(valueOf);
                if (obj != null) {
                    valueOf2 = obj;
                }
                paint.setColor(((Integer) valueOf2).intValue());
            }
            imageView.setImageDrawable(color.mDrawable);
            ((TextView) view.findViewById(R.id.color_label)).setText(((Color) getItem(i)).mLabel);
            return view;
        }
    }

    public Spinner getColorSpinnerView() {
        return this.mColorSpinner;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1642;
    }

    public EditText getNameView() {
        return this.mNameView;
    }

    public SubscriptionManager getSubscriptionManager(Context context) {
        return (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
    }

    public TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager) context.getSystemService(TelephonyManager.class);
    }

    @Override // com.android.settings.core.instrumentation.InstrumentedDialogFragment,
              // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        getTelephonyManager(context);
        this.mSubscriptionManager = getSubscriptionManager(context);
        this.mSubId = getArguments().getInt("subscription_id");
        Resources resources = context.getResources();
        int i = ImmutableMap.$r8$clinit;
        ImmutableMap.Builder builder = new ImmutableMap.Builder(4);
        builder.put(
                Integer.valueOf(resources.getInteger(R.color.SIM_color_cyan)),
                Integer.valueOf(resources.getInteger(R.color.SIM_dark_mode_color_cyan)));
        builder.put(
                Integer.valueOf(resources.getInteger(R.color.SIM_color_blue800)),
                Integer.valueOf(resources.getInteger(R.color.SIM_dark_mode_color_blue)));
        builder.put(
                Integer.valueOf(resources.getInteger(R.color.SIM_color_green800)),
                Integer.valueOf(resources.getInteger(R.color.SIM_dark_mode_color_green)));
        builder.put(
                Integer.valueOf(resources.getInteger(R.color.SIM_color_purple800)),
                Integer.valueOf(resources.getInteger(R.color.SIM_dark_mode_color_purple)));
        builder.put(
                Integer.valueOf(resources.getInteger(R.color.SIM_color_pink800)),
                Integer.valueOf(resources.getInteger(R.color.SIM_dark_mode_color_pink)));
        builder.put(
                Integer.valueOf(resources.getInteger(R.color.SIM_color_orange)),
                Integer.valueOf(resources.getInteger(R.color.SIM_dark_mode_color_orange)));
        this.mLightDarkMap = builder.buildOrThrow();
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Resources resources = getContext().getResources();
        int[] intArray = resources.getIntArray(R.array.sim_color_light);
        String[] stringArray = resources.getStringArray(R.array.color_picker);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.color_swatch_size);
        int dimensionPixelSize2 =
                resources.getDimensionPixelSize(R.dimen.color_swatch_stroke_width);
        int length = intArray.length;
        Color[] colorArr = new Color[length];
        for (int i = 0; i < length; i++) {
            colorArr[i] =
                    new Color(stringArray[i], intArray[i], dimensionPixelSize, dimensionPixelSize2);
        }
        this.mColors = colorArr;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View inflate =
                ((LayoutInflater) builder.P.mContext.getSystemService(LayoutInflater.class))
                        .inflate(R.layout.dialog_mobile_network_rename, (ViewGroup) null);
        populateView(inflate);
        builder.setTitle(R.string.mobile_network_sim_label_color_title);
        builder.setView(inflate);
        builder.setPositiveButton(
                R.string.mobile_network_sim_name_rename,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.network.telephony.RenameMobileNetworkDialogFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        RenameMobileNetworkDialogFragment renameMobileNetworkDialogFragment =
                                RenameMobileNetworkDialogFragment.this;
                        renameMobileNetworkDialogFragment.mSubscriptionManager.setDisplayName(
                                renameMobileNetworkDialogFragment.mNameView.getText().toString(),
                                renameMobileNetworkDialogFragment.mSubId,
                                2);
                        Spinner spinner = renameMobileNetworkDialogFragment.mColorSpinner;
                        renameMobileNetworkDialogFragment.mSubscriptionManager.setIconTint(
                                (spinner == null
                                                ? renameMobileNetworkDialogFragment.mColors[0]
                                                : renameMobileNetworkDialogFragment
                                                        .mColors[spinner.getSelectedItemPosition()])
                                        .mColor,
                                renameMobileNetworkDialogFragment.mSubId);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        return builder.create();
    }

    public void populateView(View view) {
        this.mNameView = (EditText) view.findViewById(R.id.name_edittext);
        List<SubscriptionInfo> availableSubscriptionInfoList =
                this.mSubscriptionManager.getAvailableSubscriptionInfoList();
        if (availableSubscriptionInfoList != null) {
            for (SubscriptionInfo subscriptionInfo : availableSubscriptionInfoList) {
                if (subscriptionInfo.getSubscriptionId() == this.mSubId) {
                    break;
                }
            }
        }
        subscriptionInfo = null;
        if (subscriptionInfo == null) {
            Log.w("RenameMobileNetwork", "got null SubscriptionInfo for mSubId:" + this.mSubId);
            return;
        }
        CharSequence uniqueSubscriptionDisplayName =
                SubscriptionUtil.getUniqueSubscriptionDisplayName(getContext(), subscriptionInfo);
        this.mNameView.setText(uniqueSubscriptionDisplayName);
        if (!TextUtils.isEmpty(uniqueSubscriptionDisplayName)) {
            this.mNameView.setSelection(uniqueSubscriptionDisplayName.length());
        }
        this.mColorSpinner = (Spinner) view.findViewById(R.id.color_spinner);
        this.mColorSpinner.setAdapter(
                (SpinnerAdapter) new ColorAdapter(getContext(), this.mColors));
        Spinner spinner = this.mColorSpinner;
        int iconTint = subscriptionInfo.getIconTint();
        int[] intArray = getContext().getResources().getIntArray(17236473);
        int i = -1;
        for (int i2 = 0; i2 < intArray.length; i2++) {
            if (intArray[i2] == iconTint) {
                i = i2;
            }
        }
        if (i == -1) {
            int i3 = 0;
            while (true) {
                Color[] colorArr = this.mColors;
                if (i3 >= colorArr.length) {
                    break;
                }
                if (colorArr[i3].mColor == iconTint) {
                    i = i3;
                }
                i3++;
            }
        }
        spinner.setSelection(i != -1 ? i : 0);
    }
}
