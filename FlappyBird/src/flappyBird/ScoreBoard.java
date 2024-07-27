package flappyBird;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;

public class ScoreBoard extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	public ScoreBoard() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					FlappyBird.map1.restart();
				}
			}
		});
		setSize(360, 180);
		setVisible(false);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int xCenter = 0;
		g.drawImage(FlappyBird.resource.scoreBoard.getImage(), 0, 0, this);
		if(Map.score < 10) {
			xCenter = 325 - 10;
		}
		else if(Map.score < 100) {
			xCenter = 325 - 20;
		}
		else if(Map.score < 1000) {
			xCenter = 325 - 30;
		}
		Map.drawScore(g, Map.score, xCenter, 55, 20, 30, this);

		if(Map.bestScore < 10) {
			xCenter = 325 - 10;
		}
		else if(Map.bestScore < 100) {
			xCenter = 325 - 20;
		}
		else if(Map.bestScore < 1000) {
			xCenter = 325 - 30;
		}
		Map.drawScore(g, Map.bestScore, xCenter, 122, 20, 30, this);
	}
	
	public void open() {
		setVisible(true);
		setLocation(45, 600);
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				new Music(FlappyBird.resource.sfx_swooshing, 1).play();
				while(getY() != 210) {
					setLocation(getX(), getY()-6);
					try {
						Thread.sleep(4);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				Map.playButton.setVisible(true);
				grabFocus();
			}
		};
		FlappyBird.threadPool.submit(runnable);
	}
	public void close() {
		setVisible(false);
		Map.playButton.setVisible(false);
	}
}