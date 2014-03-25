package com.sunita.quotesforall.vo;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class QuoteVo  implements Parcelable {
/*
<quote>
<text>You cannot dream yourself into a character; you must hammer and forge yourself one.</text>
<authby>Henry David Thoreau</authby>
</quote>
 */
	String text;
	String authby;
	
	public QuoteVo() {
		this.text = "text";
		this.authby = "authby";
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAuthby() {
		return authby;
	}
	public void setAuthby(String authby) {
		this.authby = authby;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		arg0.writeString(text);
		arg0.writeString(authby);
	}
	
	@Override
		public String toString() {
			return getText() +" -" + getAuthby();
		}
}
