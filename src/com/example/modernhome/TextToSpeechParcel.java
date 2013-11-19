package com.example.modernhome;


import android.os.Parcel;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;

public class TextToSpeechParcel implements Parcelable {
	private TextToSpeech _tts;

	public TextToSpeechParcel(TextToSpeech source) {
		_tts =  source;
	}
	
	public TextToSpeechParcel(Parcel source)
	{
		_tts = (TextToSpeech) source.readValue(null);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(_tts);
		
	}
	
	public static final Parcelable.Creator<TextToSpeechParcel> CREATOR = new Creator<TextToSpeechParcel>() {
		
		@Override
		public TextToSpeechParcel[] newArray(int size) {
			return new TextToSpeechParcel[size];
		}
		
		@Override
		public TextToSpeechParcel createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new TextToSpeechParcel(source);
		}
	};

}
