package fr.polytechbynight.outils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyVerticalScrollView extends ScrollView {

	public MyVerticalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyVerticalScrollView mvsvLink;

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		
		if(mvsvLink != null){
			mvsvLink.scrollTo(l, t);
		}
	}
}