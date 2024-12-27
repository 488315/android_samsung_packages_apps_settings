package com.samsung.android.settings.inputmethod;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManager;
import android.hardware.input.KeyboardLayout;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.InputDevice;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceCategory;
import androidx.preference.SecPreferenceScreen;
import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.utils.ThreadUtils;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class PhysicalKeyboardFragment$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PhysicalKeyboardFragment f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ PhysicalKeyboardFragment$$ExternalSyntheticLambda0(PhysicalKeyboardFragment physicalKeyboardFragment, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = physicalKeyboardFragment;
        this.f$1 = obj;
    }

    /* JADX WARN: Type inference failed for: r0v24, types: [com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment$4] */
    @Override // java.lang.Runnable
    public final void run() {
        String string;
        int i = 1;
        switch (this.$r8$classId) {
            case 0:
                PhysicalKeyboardFragment physicalKeyboardFragment = this.f$0;
                Context context = (Context) this.f$1;
                BaseSearchIndexProvider baseSearchIndexProvider = PhysicalKeyboardFragment.SEARCH_INDEX_DATA_PROVIDER;
                physicalKeyboardFragment.getClass();
                ArrayList arrayList = new ArrayList();
                InputManager inputManager = (InputManager) context.getSystemService(InputManager.class);
                if (inputManager == null) {
                    arrayList = new ArrayList();
                } else {
                    for (int i2 : InputDevice.getDeviceIds()) {
                        InputDevice device = InputDevice.getDevice(i2);
                        if (device != null && !device.isVirtual() && device.isFullKeyboard()) {
                            String name = device.getName();
                            InputDeviceIdentifier identifier = device.getIdentifier();
                            String currentKeyboardLayoutForInputDevice = inputManager.getCurrentKeyboardLayoutForInputDevice(device.getIdentifier());
                            if (currentKeyboardLayoutForInputDevice == null) {
                                string = context.getString(R.string.keyboard_layout_default_label);
                            } else {
                                KeyboardLayout keyboardLayout = inputManager.getKeyboardLayout(currentKeyboardLayoutForInputDevice);
                                string = keyboardLayout == null ? context.getString(R.string.keyboard_layout_default_label) : TextUtils.emptyIfNull(keyboardLayout.getLabel());
                            }
                            arrayList.add(new PhysicalKeyboardFragment.HardKeyboardDeviceInfo(name, identifier, string, device.getBluetoothAddress()));
                        }
                    }
                    final Collator collator = Collator.getInstance();
                    arrayList.sort(new Comparator() { // from class: com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment$$ExternalSyntheticLambda4
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            Collator collator2 = collator;
                            PhysicalKeyboardFragment.HardKeyboardDeviceInfo hardKeyboardDeviceInfo = (PhysicalKeyboardFragment.HardKeyboardDeviceInfo) obj;
                            PhysicalKeyboardFragment.HardKeyboardDeviceInfo hardKeyboardDeviceInfo2 = (PhysicalKeyboardFragment.HardKeyboardDeviceInfo) obj2;
                            BaseSearchIndexProvider baseSearchIndexProvider2 = PhysicalKeyboardFragment.SEARCH_INDEX_DATA_PROVIDER;
                            int compare = collator2.compare(hardKeyboardDeviceInfo.mDeviceName, hardKeyboardDeviceInfo2.mDeviceName);
                            if (compare != 0) {
                                return compare;
                            }
                            int compareTo = hardKeyboardDeviceInfo.mDeviceIdentifier.getDescriptor().compareTo(hardKeyboardDeviceInfo2.mDeviceIdentifier.getDescriptor());
                            return compareTo != 0 ? compareTo : collator2.compare(hardKeyboardDeviceInfo.mLayoutLabel, hardKeyboardDeviceInfo2.mLayoutLabel);
                        }
                    });
                }
                ThreadUtils.postOnMainThread(new PhysicalKeyboardFragment$$ExternalSyntheticLambda0(physicalKeyboardFragment, arrayList, i));
                break;
            default:
                final PhysicalKeyboardFragment physicalKeyboardFragment2 = this.f$0;
                List<PhysicalKeyboardFragment.HardKeyboardDeviceInfo> list = (List) this.f$1;
                BaseSearchIndexProvider baseSearchIndexProvider2 = PhysicalKeyboardFragment.SEARCH_INDEX_DATA_PROVIDER;
                if (physicalKeyboardFragment2.getContext() != null && !Objects.equals(physicalKeyboardFragment2.mLastHardKeyboards, list)) {
                    physicalKeyboardFragment2.mLastHardKeyboards.clear();
                    physicalKeyboardFragment2.mLastHardKeyboards.addAll(list);
                    physicalKeyboardFragment2.mRelativeLinkView = null;
                    PreferenceScreen preferenceScreen = physicalKeyboardFragment2.getPreferenceScreen();
                    preferenceScreen.removeAll();
                    SecPreferenceCategory secPreferenceCategory = new SecPreferenceCategory(physicalKeyboardFragment2.getPrefContext());
                    secPreferenceCategory.setTitle(R.string.builtin_keyboard_settings_title);
                    secPreferenceCategory.setOrder(0);
                    preferenceScreen.addPreference(secPreferenceCategory);
                    for (final PhysicalKeyboardFragment.HardKeyboardDeviceInfo hardKeyboardDeviceInfo : list) {
                        Preference preference = new Preference(physicalKeyboardFragment2.getPrefContext());
                        preference.setTitle(hardKeyboardDeviceInfo.mDeviceName);
                        ArrayList arrayList2 = new ArrayList();
                        Context context2 = physicalKeyboardFragment2.getContext();
                        InputMethodManager inputMethodManager = physicalKeyboardFragment2.mImm;
                        int myUserId = UserHandle.myUserId();
                        ArrayList arrayList3 = new ArrayList();
                        for (InputMethodInfo inputMethodInfo : inputMethodManager.getEnabledInputMethodListAsUser(UserHandle.of(myUserId))) {
                            Iterator<InputMethodSubtype> it = inputMethodManager.getEnabledInputMethodSubtypeList(inputMethodInfo, true).iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                } else if (it.next().isSuitableForPhysicalKeyboardLayoutMapping()) {
                                    arrayList3.add(inputMethodInfo.loadLabel(context2.getPackageManager()).toString());
                                }
                            }
                        }
                        arrayList2.addAll(arrayList3);
                        if (arrayList2.isEmpty()) {
                            preference.setSummary(hardKeyboardDeviceInfo.mLayoutLabel);
                        } else {
                            StringBuilder sb = new StringBuilder((String) arrayList2.get(0));
                            for (int i3 = 1; i3 < arrayList2.size(); i3++) {
                                sb.append(", ");
                                sb.append((String) arrayList2.get(i3));
                            }
                            preference.setSummary(sb.toString());
                        }
                        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment$$ExternalSyntheticLambda5
                            @Override // androidx.preference.Preference.OnPreferenceClickListener
                            public final boolean onPreferenceClick(Preference preference2) {
                                BaseSearchIndexProvider baseSearchIndexProvider3 = PhysicalKeyboardFragment.SEARCH_INDEX_DATA_PROVIDER;
                                PhysicalKeyboardFragment physicalKeyboardFragment3 = PhysicalKeyboardFragment.this;
                                physicalKeyboardFragment3.getClass();
                                physicalKeyboardFragment3.showEnabledLocalesKeyboardLayoutList(hardKeyboardDeviceInfo.mDeviceIdentifier);
                                return true;
                            }
                        });
                        secPreferenceCategory.addPreference(preference);
                    }
                    physicalKeyboardFragment2.mKeyboardAssistanceCategory.setOrder(1);
                    preferenceScreen.addPreference(physicalKeyboardFragment2.mKeyboardAssistanceCategory);
                    physicalKeyboardFragment2.updateShowVirtualKeyboardSwitch();
                    if (Rune.IFW_WIRELESS_KEYBOARD_MOUSE_SHARE) {
                        boolean isExistPogoKeyboard = PhysicalKeyboardFragment.isExistPogoKeyboard(physicalKeyboardFragment2.getContext());
                        physicalKeyboardFragment2.mWirelessKeyboardSharing.setVisible(isExistPogoKeyboard);
                        if (isExistPogoKeyboard) {
                            physicalKeyboardFragment2.mWirelessKeyboardSharing.setOrder(2);
                            preferenceScreen.addPreference(physicalKeyboardFragment2.mWirelessKeyboardSharing);
                            physicalKeyboardFragment2.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                            SecPreferenceScreen secPreferenceScreen = (SecPreferenceScreen) physicalKeyboardFragment2.findPreference("share_keyboard");
                            physicalKeyboardFragment2.mShareKeyboard = secPreferenceScreen;
                            secPreferenceScreen.setSummary(R.string.not_connected);
                            if (physicalKeyboardFragment2.mWirelessKeyboardShareUiHandler == null) {
                                physicalKeyboardFragment2.mWirelessKeyboardShareUiHandler = 
                                /*  JADX ERROR: Method code generation error
                                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0150: IPUT 
                                      (wrap:??:0x014d: CONSTRUCTOR (r3v2 'physicalKeyboardFragment2' com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment A[DONT_INLINE]) A[MD:(com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment):void (m), WRAPPED] (LINE:334) call: com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment.4.<init>(com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment):void type: CONSTRUCTOR)
                                      (r3v2 'physicalKeyboardFragment2' com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment)
                                     (LINE:337) com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment.mWirelessKeyboardShareUiHandler com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment$4 in method: com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment$$ExternalSyntheticLambda0.run():void, file: classes4.dex
                                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                                    	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:267)
                                    	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:84)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment, state: PROCESS_STARTED
                                    	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:305)
                                    	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:487)
                                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                    	... 45 more
                                    */
                                /*
                                    Method dump skipped, instructions count: 520
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.inputmethod.PhysicalKeyboardFragment$$ExternalSyntheticLambda0.run():void");
                            }
                        }
