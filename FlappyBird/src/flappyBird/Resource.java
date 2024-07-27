package flappyBird;

import java.net.URL;

import javax.swing.ImageIcon;

public class Resource {
	protected ImageIcon play = new ImageIcon(getClass().getClassLoader().getResource("bg_button.png"));
	protected ImageIcon gameOver = new ImageIcon(getClass().getClassLoader().getResource("bg_gameOver.png"));
	protected ImageIcon noon = new ImageIcon(getClass().getClassLoader().getResource("bg_noon.png"));
	protected ImageIcon pillarDown = new ImageIcon(getClass().getClassLoader().getResource("bg_pillarDown.png"));
	protected ImageIcon pillarUp = new ImageIcon(getClass().getClassLoader().getResource("bg_pillarUp.png"));
	protected ImageIcon ready = new ImageIcon(getClass().getClassLoader().getResource("bg_ready.png"));
	protected ImageIcon scoreBoard = new ImageIcon(getClass().getClassLoader().getResource("bg_score_board.png"));
	protected ImageIcon spacebar = new ImageIcon(getClass().getClassLoader().getResource("bg_spacebar.png"));
	protected ImageIcon tap = new ImageIcon(getClass().getClassLoader().getResource("bg_tap.png"));
	protected ImageIcon tile = new ImageIcon(getClass().getClassLoader().getResource("bg_tile.png"));
	protected ImageIcon title = new ImageIcon(getClass().getClassLoader().getResource("bg_title.png"));
	protected ImageIcon birdFly0 = new ImageIcon(getClass().getClassLoader().getResource("bird1_1.png"));
	protected ImageIcon birdFly1 = new ImageIcon(getClass().getClassLoader().getResource("bird1_2.png"));
	protected ImageIcon birdFly2 = new ImageIcon(getClass().getClassLoader().getResource("bird1_3.png"));
	protected ImageIcon nums = new ImageIcon(getClass().getClassLoader().getResource("nums.png"));

	protected URL bgm = getClass().getClassLoader().getResource("sfx_bgm.wav");	
	protected URL sfx_die = getClass().getClassLoader().getResource("sfx_die.wav");	
	protected URL sfx_hit = getClass().getClassLoader().getResource("sfx_hit.wav");	
	protected URL sfx_point = getClass().getClassLoader().getResource("sfx_point.wav");	
	protected URL sfx_swooshing = getClass().getClassLoader().getResource("sfx_swooshing.wav");	
	protected URL sfx_wing = getClass().getClassLoader().getResource("sfx_wing.wav");
}
