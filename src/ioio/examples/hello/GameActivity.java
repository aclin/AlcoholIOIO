package ioio.examples.hello;

import game.GameDB;
import game.GameState;
import game.TreeGame;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

@SuppressLint("NewApi")
public class GameActivity extends Activity {
	
	private static final int TREE_TYPES = GameState.MAX_STATE+1; 
	private static final int MAX_COIN = GameState.MAX_COINS; 
	private ImageView[] tree_image = new ImageView[TREE_TYPES];
	private ImageView[] coin_image = new ImageView[MAX_COIN];
	private TreeGame treeGame=null;
	private GameDB gDB=null;
	private Animation appear_anim;
	private Animation disappear_anim;
	private Animation appear_anim_delay;
	private Animation disappear_anim_delay;
	
	
	public final static int START_DO_NOTHING = 0;
	public final static int START_GET_COIN = 1;
	public final static int START_LOSE_COIN = 2;
	
	private static int START_ACTION=START_DO_NOTHING;
	
	/*Setting the action when start the activity*/
	static public void setStartAction(int action){
		START_ACTION = action;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		gDB = new GameDB(this);
		GameState gState = gDB.getLatestGameState();
		Log.e(this.getClass().toString(), "state coin "+gState.state+" "+gState.coin);
		treeGame=new TreeGame(gState);
		initAnim();
		initTreeImage();
		initCoinImage();
		setImage();
		Log.e(this.getClass().toString(), "end init");
		if (START_ACTION == START_GET_COIN)
			getCoinDelay();
		else if (START_ACTION == START_LOSE_COIN)
			loseCoinDelay();
		START_ACTION = START_DO_NOTHING;
	}
	
	private void initAnim(){
		/*used for initializing animations*/
		appear_anim = new AlphaAnimation(0.f,1.f);
		appear_anim.setDuration(300);
		disappear_anim = new AlphaAnimation(1.f,0.f);
		disappear_anim.setDuration(300);
		appear_anim_delay = new AlphaAnimation(0.f,1.f);
		appear_anim_delay.setDuration(300);
		appear_anim_delay.setStartOffset(300);
		disappear_anim_delay = new AlphaAnimation(0.f,1.f);
		disappear_anim_delay.setDuration(300);
		disappear_anim_delay.setStartOffset(300);
	}
	
	
	private void initTreeImage(){
		/*used for initializing tree images*/
		tree_image[0] = (ImageView) findViewById(R.id.tree1);
		tree_image[1] = (ImageView) findViewById(R.id.tree2);
		tree_image[2] = (ImageView) findViewById(R.id.tree3);
		tree_image[3] = (ImageView) findViewById(R.id.tree4);
		tree_image[4] = (ImageView) findViewById(R.id.tree5);
		tree_image[5] = (ImageView) findViewById(R.id.tree6);
		tree_image[6] = (ImageView) findViewById(R.id.tree7);
	}
	private void initCoinImage(){
		/*used for initializing coin images*/
		coin_image[0] = (ImageView) findViewById(R.id.coin1);
		coin_image[1] = (ImageView) findViewById(R.id.coin2);
		coin_image[2] = (ImageView) findViewById(R.id.coin3);
		coin_image[3] = (ImageView) findViewById(R.id.coin4);
		coin_image[4] = (ImageView) findViewById(R.id.coin5);
	}	
	
	private void setImage(){
		/*set image visibility*/
		GameState gState=treeGame.getGameState();
		//Drawable bg = this.getResources().getDrawable(R.drawable.background);
		for (int i=0;i<TREE_TYPES;++i){
			tree_image[i].setVisibility(ImageView.INVISIBLE);
			//tree_image[i].setBackgroundDrawable(bg);
		}
		for (int i=0;i<MAX_COIN;++i)
			coin_image[i].setVisibility(ImageView.INVISIBLE);
		tree_image[gState.state].setVisibility(ImageView.VISIBLE);
		for (int i=0;i<gState.coin;++i)
			coin_image[i].setVisibility(ImageView.VISIBLE);
	}
	
	
	private void setImage(GameState oldState){
		/*set images with animation*/
		GameState gState=treeGame.getGameState();
		if (oldState.coin < gState.coin){
			for (int i=oldState.coin;i<gState.coin;++i){
				coin_image[i].setVisibility(ImageView.VISIBLE);
				coin_image[i].startAnimation(appear_anim);
			}
		}
		else if (oldState.coin > gState.coin){
			for (int i=gState.coin;i<oldState.coin;++i){
				coin_image[i].startAnimation(disappear_anim);
				coin_image[i].setVisibility(ImageView.INVISIBLE);
			}
		}
		if (oldState.state != gState.state){
			tree_image[oldState.state].startAnimation(disappear_anim);
			tree_image[oldState.state].setVisibility(ImageView.INVISIBLE);
			tree_image[gState.state].setVisibility(ImageView.VISIBLE);
			tree_image[gState.state].startAnimation(appear_anim);
		}
	}	
	
	private void setImageDelay(GameState oldState){
		/*set images with animation (used in onCreate())*/
		GameState gState=treeGame.getGameState();
		if (oldState.coin < gState.coin){
			for (int i=oldState.coin;i<gState.coin;++i){
				coin_image[i].setAlpha(0.f);
				coin_image[i].setVisibility(ImageView.VISIBLE);
				coin_image[i].startAnimation(appear_anim_delay);
				coin_image[i].setAlpha(1.f);
			}
		}
		else if (oldState.coin > gState.coin){
			for (int i=gState.coin;i<oldState.coin;++i){
				coin_image[i].setAlpha(1.f);
				coin_image[i].startAnimation(disappear_anim_delay);
				coin_image[i].setVisibility(ImageView.INVISIBLE);
			}
		}
		if (oldState.state != gState.state){
			tree_image[oldState.state].setAlpha(1.f);
			tree_image[oldState.state].startAnimation(disappear_anim_delay);
			tree_image[oldState.state].setVisibility(ImageView.INVISIBLE);
			tree_image[gState.state].setAlpha(0.f);
			tree_image[gState.state].setVisibility(ImageView.VISIBLE);
			tree_image[gState.state].startAnimation(appear_anim_delay);
			tree_image[gState.state].setAlpha(1.f);
		}
	}	
	
	public void getCoin(){
		/*When perform well, get a coin, update the record in database and set images*/
		GameState oldState = new GameState(treeGame.getGameState());
		treeGame.getCoin();
		gDB.updateState(treeGame.getGameState());
		this.setImage(oldState);
	}
	public void loseCoin(){
		/*When perform poorly, lose  a coin and update the record in database and set images*/
		GameState oldState = new GameState(treeGame.getGameState());
		treeGame.loseCoin();
		gDB.updateState(treeGame.getGameState());
		this.setImage(oldState);
	}
	
	/*Similar to getCoin(), but with delayed animation*/
	public void getCoinDelay(){
		GameState oldState = new GameState(treeGame.getGameState());
		treeGame.getCoin();
		gDB.updateState(treeGame.getGameState());
		this.setImageDelay(oldState);
	}
	/*Similar to loseCoin(), but with delayed animation*/
	public void loseCoinDelay(){
		GameState oldState = new GameState(treeGame.getGameState());
		treeGame.loseCoin();
		gDB.updateState(treeGame.getGameState());
		this.setImageDelay(oldState);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_game, menu);
		return true;
	}

}
