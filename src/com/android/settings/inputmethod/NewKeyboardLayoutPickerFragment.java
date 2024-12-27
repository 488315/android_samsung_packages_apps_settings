package com.android.settings.inputmethod;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputManager;
import android.hardware.input.KeyboardLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.R;
import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NewKeyboardLayoutPickerFragment extends Fragment {
    public InputManager mInputManager;
    public ImageView mKeyboardLayoutPreview;
    public TextView mKeyboardLayoutPreviewText;
    public final AnonymousClass1 mKeyboardLayoutSelectedCallback = new AnonymousClass1();
    public final NewKeyboardLayoutPickerFragment$$ExternalSyntheticLambda0
            mControllerUpdateCallback =
                    new NewKeyboardLayoutPickerFragment$$ExternalSyntheticLambda0(this);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.inputmethod.NewKeyboardLayoutPickerFragment$1, reason: invalid class name */
    public final class AnonymousClass1
            implements NewKeyboardLayoutPickerController.KeyboardLayoutSelectedCallback {
        public AnonymousClass1() {}

        public final void onSelected(KeyboardLayout keyboardLayout) {
            NewKeyboardLayoutPickerFragment newKeyboardLayoutPickerFragment =
                    NewKeyboardLayoutPickerFragment.this;
            InputManager inputManager = newKeyboardLayoutPickerFragment.mInputManager;
            if (inputManager == null
                    || newKeyboardLayoutPickerFragment.mKeyboardLayoutPreview == null
                    || newKeyboardLayoutPickerFragment.mKeyboardLayoutPreviewText == null
                    || keyboardLayout == null) {
                return;
            }
            Drawable keyboardLayoutPreview =
                    inputManager.getKeyboardLayoutPreview(keyboardLayout, 1630, 540);
            newKeyboardLayoutPickerFragment.mKeyboardLayoutPreview.setVisibility(
                    keyboardLayoutPreview == null ? 8 : 0);
            newKeyboardLayoutPickerFragment.mKeyboardLayoutPreviewText.setVisibility(
                    keyboardLayoutPreview == null ? 8 : 0);
            if (keyboardLayoutPreview != null) {
                newKeyboardLayoutPickerFragment.mKeyboardLayoutPreviewText.setText(
                        keyboardLayout.getLabel());
                newKeyboardLayoutPickerFragment.mKeyboardLayoutPreview.setImageDrawable(
                        keyboardLayoutPreview);
            }
        }
    }

    public final void initUI(ViewGroup viewGroup) {
        LinearLayout linearLayout =
                (LinearLayout) viewGroup.findViewById(R.id.keyboard_layout_picker_container);
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, 0);
        linearLayout.setLayoutParams(layoutParams);
        FrameLayout frameLayout =
                (FrameLayout) viewGroup.findViewById(R.id.keyboard_layout_preview_container);
        frameLayout.semSetRoundedCorners(15);
        frameLayout.semSetRoundedCornerColor(
                15, getContext().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        int listHorizontalPadding = Utils.getListHorizontalPadding(getContext());
        LinearLayout.LayoutParams layoutParams2 =
                (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams2.setMargins(listHorizontalPadding, 0, listHorizontalPadding, 0);
        frameLayout.setLayoutParams(layoutParams2);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initUI((ViewGroup) getActivity().findViewById(R.id.keyboard_layout_picker_container));
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mInputManager = (InputManager) requireContext().getSystemService(InputManager.class);
        getResources().getConfiguration();
        ViewGroup viewGroup2 =
                (ViewGroup)
                        layoutInflater.inflate(R.layout.keyboard_layout_picker, viewGroup, false);
        this.mKeyboardLayoutPreview =
                (ImageView) viewGroup2.findViewById(R.id.keyboard_layout_preview);
        this.mKeyboardLayoutPreviewText =
                (TextView) viewGroup2.findViewById(R.id.keyboard_layout_preview_name);
        initUI(viewGroup2);
        FragmentManagerImpl supportFragmentManager = getActivity().getSupportFragmentManager();
        supportFragmentManager.getClass();
        BackStackRecord backStackRecord = new BackStackRecord(supportFragmentManager);
        backStackRecord.replace(
                R.id.keyboard_layout_title, new NewKeyboardLayoutPickerTitle(), null);
        backStackRecord.commitInternal(false);
        NewKeyboardLayoutPickerContent newKeyboardLayoutPickerContent =
                new NewKeyboardLayoutPickerContent();
        newKeyboardLayoutPickerContent.mControllerUpdateCallback = this.mControllerUpdateCallback;
        newKeyboardLayoutPickerContent.setArguments(getArguments());
        FragmentManagerImpl supportFragmentManager2 = getActivity().getSupportFragmentManager();
        supportFragmentManager2.getClass();
        BackStackRecord backStackRecord2 = new BackStackRecord(supportFragmentManager2);
        backStackRecord2.replace(R.id.keyboard_layouts, newKeyboardLayoutPickerContent, null);
        backStackRecord2.commitInternal(false);
        return viewGroup2;
    }
}
