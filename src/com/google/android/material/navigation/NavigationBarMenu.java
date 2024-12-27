package com.google.android.material.navigation;

import android.content.Context;
import android.view.SubMenu;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class NavigationBarMenu extends MenuBuilder {
    public final Class viewClass;

    public NavigationBarMenu(Context context, Class cls) {
        super(context);
        this.viewClass = cls;
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder
    public final MenuItemImpl addInternal(int i, int i2, int i3, CharSequence charSequence) {
        stopDispatchingItemsChanged();
        MenuItemImpl addInternal = super.addInternal(i, i2, i3, charSequence);
        addInternal.setExclusiveCheckable(true);
        startDispatchingItemsChanged();
        return addInternal;
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder, android.view.Menu
    public final SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        throw new UnsupportedOperationException(
                this.viewClass.getSimpleName().concat(" does not support submenus"));
    }
}
