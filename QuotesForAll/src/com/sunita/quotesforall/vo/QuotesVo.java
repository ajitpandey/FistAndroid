package com.sunita.quotesforall.vo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class QuotesVo  implements Parcelable {
	String topic;
	List<QuoteVo> quoteVoList;
	
	public QuotesVo() {
		this.topic = "topic";
	}
	
	public void addQuoteVo(QuoteVo quoteVo){
		if(quoteVoList == null){
			quoteVoList =  new ArrayList<QuoteVo>();
		}
		quoteVoList.add(quoteVo);
	}

	public List<QuoteVo> getQuoteVoList(){
		return quoteVoList;
	}
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(topic);
		dest.writeList(quoteVoList);
	}
	
	
}
