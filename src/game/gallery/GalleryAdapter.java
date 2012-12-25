package game.gallery;

import ioio.examples.hello.R;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryAdapter extends BaseAdapter {
	private ArrayList<HashMap<String,Object>> list;
	private HashMap<String,Bitmap> bitmap_map;
	private Context context;
	private LayoutInflater inflater;
	
	public GalleryAdapter(ArrayList<HashMap<String,Object>> list, Context context){
		this.list = list;
		this.context = context;
		bitmap_map = new  HashMap<String,Bitmap>();
		inflater  = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		vTag v_tag = null;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.game_history, null);
			v_tag = new vTag(convertView);
			convertView.setTag(v_tag);
		}
		else
			v_tag = (vTag) convertView.getTag();
		
		HashMap<String, Object> item_info = list.get(position);
		
		Bitmap cur_bg = bitmap_map.get("bg"+String.valueOf(position));
		Bitmap cur_tree = bitmap_map.get("tree"+String.valueOf(position));
		
		int bg = (Integer) item_info.get("pic");
		int tree = (Integer) item_info.get("tree");
		int coin = (Integer) item_info.get("coin");
		String date = (String) item_info.get("date");
		
		if (cur_bg == null){
			Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), bg);
			cur_bg = Bitmap.createScaledBitmap(tmp, 256, 420, true);
			tmp.recycle();
			bitmap_map.put("bg"+String.valueOf(position),cur_bg);
		}
		if (cur_tree == null){
			Bitmap tmp = BitmapFactory.decodeResource(context.getResources(), tree);
			cur_tree = Bitmap.createScaledBitmap(tmp,192, 240, true);
			tmp.recycle();
			bitmap_map.put("tree"+String.valueOf(position),cur_tree);
		}
		v_tag.bg.setImageBitmap(cur_bg);
		v_tag.tree.setImageBitmap(cur_tree);
		v_tag.date.setText(date);
		for (int i=0;i<coin;++i){
			v_tag.c[i].setVisibility(View.VISIBLE);
		}
		for (int i=coin;i<4;++i){
			v_tag.c[i].setVisibility(View.INVISIBLE);
		}
		
		return convertView;
	}
	
	private class vTag{
		ImageView bg;
		ImageView tree;
		ImageView[] c;
		TextView date;
		public vTag(View convertView){
			
			bg = (ImageView) convertView.findViewById(R.id.gallery_background);
			tree = (ImageView) convertView.findViewById(R.id.gallery_tree);
			c = new ImageView[4];
			c[0] = (ImageView) convertView.findViewById(R.id.gallery_coin1);
			c[1] = (ImageView) convertView.findViewById(R.id.gallery_coin2);
			c[2] = (ImageView) convertView.findViewById(R.id.gallery_coin3);
			c[3] = (ImageView) convertView.findViewById(R.id.gallery_coin4);
			date = (TextView) convertView.findViewById(R.id.gallery_date);
		}
	}

	public void clearAll(){
		int len = list.size() + 1;
		for (int i=0;i<len;++i){
			Bitmap bg = bitmap_map.get("bg"+String.valueOf(i));
			Bitmap tree = bitmap_map.get("tree"+String.valueOf(i));
			if (bg != null){
				bitmap_map.remove("bg"+String.valueOf(i));
				Bitmap tmp = bg;
				tmp.recycle();
				bg = null;
			}
			if (tree != null){
				bitmap_map.remove("tree"+String.valueOf(i));
				Bitmap tmp = tree;
				tmp.recycle();
				tree = null;
			}
		}
		bitmap_map.clear();
	}

}