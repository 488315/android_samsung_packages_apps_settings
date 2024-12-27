package com.android.settings.inputmethod;

import android.app.AlertDialog;
import android.app.Dialog;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.inputmethod.ModifierKeysPickerDialogFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ModifierKeysPickerDialogFragment extends DialogFragment {
    public FragmentActivity mActivity;
    public KeyboardSettingsFeatureProviderImpl mFeatureProvider;
    public String mKeyDefaultName;
    public String mKeyFocus;
    public SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public final List mRemappableKeyList = new ArrayList(Arrays.asList(new int[]{115}, new int[]{113, 114}, new int[]{117, 118}, new int[]{57, 58}));
    public final Map mRemappableKeyMap = new HashMap();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ModifierKeyAdapter extends BaseAdapter {
        public int mCurrentItem = 0;
        public final List mList;

        public ModifierKeyAdapter(List list) {
            this.mList = list;
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return this.mList.size();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return this.mList.get(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(ModifierKeysPickerDialogFragment.this.mActivity).inflate(R.layout.modifier_key_item, (ViewGroup) null);
            }
            TextView textView = (TextView) view.findViewById(R.id.modifier_key_text);
            ImageView imageView = (ImageView) view.findViewById(R.id.modifier_key_check_icon);
            textView.setText((CharSequence) this.mList.get(i));
            if (this.mCurrentItem == i) {
                ModifierKeysPickerDialogFragment.this.mKeyFocus = (String) this.mList.get(i);
                textView.setTextColor(Utils.getColorAttrDefaultColor(ModifierKeysPickerDialogFragment.this.mActivity, android.R.^attr-private.materialColorPrimaryFixedDim));
                imageView.setImageAlpha(255);
                view.setBackground(ModifierKeysPickerDialogFragment.this.mActivity.getDrawable(R.drawable.modifier_key_lisetview_background));
                ModifierKeysPickerDialogFragment.this.getClass();
            } else {
                textView.setTextColor(Utils.getColorAttrDefaultColor(ModifierKeysPickerDialogFragment.this.mActivity, android.R.attr.textColorPrimary));
                imageView.setImageAlpha(0);
                view.setBackground(null);
                ModifierKeysPickerDialogFragment.this.getClass();
            }
            return view;
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        super.onCreateDialog(bundle);
        this.mActivity = getActivity();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mFeatureProvider = (KeyboardSettingsFeatureProviderImpl) featureFactoryImpl.keyboardSettingsFeatureProvider$delegate.getValue();
        final InputManager inputManager = (InputManager) this.mActivity.getSystemService(InputManager.class);
        this.mKeyDefaultName = getArguments().getString("default_key");
        this.mKeyFocus = getArguments().getString("delection_key");
        if (bundle != null) {
            this.mKeyFocus = bundle.getString("delection_key");
        }
        final ArrayList arrayList = new ArrayList(Arrays.asList(this.mActivity.getString(R.string.modifier_keys_caps_lock), this.mActivity.getString(R.string.modifier_keys_ctrl), this.mActivity.getString(R.string.modifier_keys_meta), this.mActivity.getString(R.string.modifier_keys_alt)));
        for (int i = 0; i < arrayList.size(); i++) {
            ((HashMap) this.mRemappableKeyMap).put((String) arrayList.get(i), (int[]) ((ArrayList) this.mRemappableKeyList).get(i));
        }
        this.mFeatureProvider.getClass();
        View inflate = LayoutInflater.from(this.mActivity).inflate(R.layout.modifier_key_picker_dialog, (ViewGroup) null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mActivity);
        builder.setView(inflate);
        ((TextView) inflate.findViewById(R.id.modifier_key_picker_summary)).setText(this.mActivity.getString(R.string.modifier_keys_picker_summary, new Object[]{this.mKeyDefaultName}));
        final ModifierKeyAdapter modifierKeyAdapter = new ModifierKeyAdapter(arrayList);
        ListView listView = (ListView) inflate.findViewById(R.id.modifier_key_picker);
        listView.setAdapter((ListAdapter) modifierKeyAdapter);
        if (arrayList.indexOf(this.mKeyFocus) == -1) {
            modifierKeyAdapter.mCurrentItem = arrayList.indexOf(this.mKeyDefaultName);
        } else {
            modifierKeyAdapter.mCurrentItem = arrayList.indexOf(this.mKeyFocus);
        }
        modifierKeyAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.settings.inputmethod.ModifierKeysPickerDialogFragment.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j) {
                ModifierKeyAdapter modifierKeyAdapter2 = ModifierKeyAdapter.this;
                modifierKeyAdapter2.mCurrentItem = i2;
                modifierKeyAdapter2.notifyDataSetChanged();
            }
        });
        AlertDialog create = builder.create();
        ((Button) inflate.findViewById(R.id.modifier_key_done_button)).setOnClickListener(new View.OnClickListener() { // from class: com.android.settings.inputmethod.ModifierKeysPickerDialogFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ModifierKeysPickerDialogFragment modifierKeysPickerDialogFragment = ModifierKeysPickerDialogFragment.this;
                List list = arrayList;
                ModifierKeysPickerDialogFragment.ModifierKeyAdapter modifierKeyAdapter2 = modifierKeyAdapter;
                InputManager inputManager2 = inputManager;
                modifierKeysPickerDialogFragment.getClass();
                String str = (String) list.get(modifierKeyAdapter2.mCurrentItem);
                if (modifierKeysPickerDialogFragment.mKeyDefaultName.equals("Caps lock")) {
                    modifierKeysPickerDialogFragment.mMetricsFeatureProvider.action(modifierKeysPickerDialogFragment.mActivity, 1841, str);
                }
                if (modifierKeysPickerDialogFragment.mKeyDefaultName.equals("Ctrl")) {
                    modifierKeysPickerDialogFragment.mMetricsFeatureProvider.action(modifierKeysPickerDialogFragment.mActivity, 1842, str);
                }
                if (modifierKeysPickerDialogFragment.mKeyDefaultName.equals("Action key")) {
                    modifierKeysPickerDialogFragment.mMetricsFeatureProvider.action(modifierKeysPickerDialogFragment.mActivity, 1843, str);
                }
                if (modifierKeysPickerDialogFragment.mKeyDefaultName.equals("Alt")) {
                    modifierKeysPickerDialogFragment.mMetricsFeatureProvider.action(modifierKeysPickerDialogFragment.mActivity, 1844, str);
                }
                if (str.equals(modifierKeysPickerDialogFragment.mKeyDefaultName)) {
                    SpannableString spannableString = new SpannableString(modifierKeysPickerDialogFragment.mActivity.getString(R.string.modifier_keys_default_summary));
                    spannableString.setSpan(new ForegroundColorSpan(Utils.getColorAttrDefaultColor(modifierKeysPickerDialogFragment.mActivity, android.R.attr.textColorSecondary)), 0, spannableString.length(), 0);
                    for (int i2 : (int[]) ((HashMap) modifierKeysPickerDialogFragment.mRemappableKeyMap).get(modifierKeysPickerDialogFragment.mKeyDefaultName)) {
                        inputManager2.remapModifierKey(i2, i2);
                    }
                } else {
                    SpannableString spannableString2 = new SpannableString(str);
                    spannableString2.setSpan(new ForegroundColorSpan(Utils.getColorAttrDefaultColor(modifierKeysPickerDialogFragment.mActivity, android.R.^attr-private.materialColorPrimaryFixedDim)), 0, spannableString2.length(), 0);
                    int[] iArr = (int[]) ((HashMap) modifierKeysPickerDialogFragment.mRemappableKeyMap).get(modifierKeysPickerDialogFragment.mKeyDefaultName);
                    int[] iArr2 = (int[]) ((HashMap) modifierKeysPickerDialogFragment.mRemappableKeyMap).get(str);
                    if (modifierKeysPickerDialogFragment.mKeyDefaultName.equals(modifierKeysPickerDialogFragment.mActivity.getString(R.string.modifier_keys_caps_lock))) {
                        inputManager2.remapModifierKey(iArr[0], iArr2[0]);
                    }
                    if (!modifierKeysPickerDialogFragment.mKeyDefaultName.equals(modifierKeysPickerDialogFragment.mActivity.getString(R.string.modifier_keys_caps_lock)) && str.equals(modifierKeysPickerDialogFragment.mActivity.getString(R.string.modifier_keys_caps_lock))) {
                        inputManager2.remapModifierKey(iArr[0], iArr2[0]);
                        inputManager2.remapModifierKey(iArr[1], iArr2[0]);
                    }
                    if (!modifierKeysPickerDialogFragment.mKeyDefaultName.equals(modifierKeysPickerDialogFragment.mActivity.getString(R.string.modifier_keys_caps_lock)) && !str.equals(modifierKeysPickerDialogFragment.mActivity.getString(R.string.modifier_keys_caps_lock))) {
                        inputManager2.remapModifierKey(iArr[0], iArr2[0]);
                        inputManager2.remapModifierKey(iArr[1], iArr2[1]);
                    }
                }
                modifierKeysPickerDialogFragment.dismissInternal(false, false);
                modifierKeysPickerDialogFragment.mActivity.recreate();
            }
        });
        ((Button) inflate.findViewById(R.id.modifier_key_cancel_button)).setOnClickListener(new View.OnClickListener() { // from class: com.android.settings.inputmethod.ModifierKeysPickerDialogFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ModifierKeysPickerDialogFragment.this.dismissInternal(false, false);
            }
        });
        create.getWindow().setType(2008);
        return create;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putString("delection_key", this.mKeyFocus);
        super.onSaveInstanceState(bundle);
    }
}
