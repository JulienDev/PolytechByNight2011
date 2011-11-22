package fr.polytechbynight.divers;



import java.io.IOException;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;

    public class SponsorsAdapteur extends BaseAdapter {
        private Context mContext;
        private String[] mImageList;
        private String mPath;
        private AssetManager assetManager;
        
        public SponsorsAdapteur(Context c, String mPath) {
            this.mContext = c;
            this.mPath = mPath;
            assetManager = mContext.getResources().getAssets();

            try {
                mImageList = assetManager.list(mPath);
                Log.d("size", "s:" + mImageList.length);
            } catch (IOException e) {
                Log.e( "Partenaires", "I/O Exception List" + e.getMessage());
            }
        }

        public int getCount() {
            return mImageList.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
                    	
        	ImageView i = new ImageView(mContext);
                        
            try {
                i.setImageBitmap(BitmapFactory.decodeStream(mContext.getResources().getAssets().open(this.mPath+"/"+mImageList[position])));
            } catch (IOException e) {
                Log.e( "Partenaires", "I/O Exception Decode" + e.getMessage());
            }
            
            i.setScaleType(ScaleType.FIT_START);
        	i.setLayoutParams(new ListView.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 100));
        	i.setPadding(5, 10, 0, 10);
            
            return i;
        }
    }