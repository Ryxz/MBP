package com.yuyue.mbp.global.utils;

import com.yuyue.mbp.global.GlobalContext;

public class ResourceUtil {
	
	private static int getIdentifier(String paramString, String defType) {
		return GlobalContext.getInstance().getResources().getIdentifier(paramString, defType,
				GlobalContext.getInstance().getPackageName());
	}

	public static int getLayoutId(String paramString) {
		return getIdentifier(paramString, "layout");
	}

	public static int getStringId(String paramString) {
		return getIdentifier(paramString, "string");
	}

	public static int getDrawableId(String paramString) {
		return getIdentifier(paramString, "drawable");
	}

	public static int getStyleId(String paramString) {
		return getIdentifier(paramString, "style");
	}

	public static int getId(String paramString) {
		return getIdentifier(paramString, "id");
	}

	public static int getColorId(String paramString) {
		return getIdentifier(paramString, "color");
	}

	public static int getStyleableId(String paramString) {
		return getIdentifier(paramString, "styleable");
	}

	public static int getAnimId(String paramString) {
		return getIdentifier(paramString, "anim");
	}

    public static String getString(String paramString) {
        return GlobalContext.getInstance().getString(getStringId(paramString));
    }
	
}
