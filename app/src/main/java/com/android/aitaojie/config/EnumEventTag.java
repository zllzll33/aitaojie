package com.android.aitaojie.config;

public enum EnumEventTag
{

	GO_HOME_FRAGMENT,
	Change_NICK,
	CHANG_SIGN,
	CHAGE_HEADER,
	REFRESH_ADDRESS_LIST,
	CHAT_NEW_MESSAGE,
	CHAT_MESSAGE_READ,
	DELETE_CHAT,
	CHANGE_LIVE_CITY,
	NEW_CHAT_USER,
	REFRESH_COLLECT_STORE_LIST,
	REFRESH_COLLECT_GOOD_LIST,
	GET_HOME_CHAT,
	WX_PAY_ORDER,
	CART_ORDER,
	SCAN,ADDRESS;
	public static EnumEventTag valueOf(int index)
	{
		if (index >= 0 && index < values().length)
		{
			return values()[index];
		} else
		{
			return null;
		}
	}
}
