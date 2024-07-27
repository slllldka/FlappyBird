package flappyBird;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Map extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected FlappyBird frame;
	
	protected static ScoreBoard scoreBoard = new ScoreBoard();
	protected static JLabel playButton;
	protected boolean playButtonEntered = false, playButtonPressed = false;
	
	protected Random random = new Random();
	
	protected static int xSize = 450, ySize = 600;

	protected static final int TITLE = 0, START = 1, PLAYING = 2, SCORE = 3;
	protected static int status = TITLE;
	protected boolean isJumped = false;
	
	protected static LinkedList<Pillar> pillarList = new LinkedList<Pillar>();
	
	//Map Pos
	protected static double xPos = 0;
	protected static int yGround = 525;
	protected double mapVelocity = 300;
	
	//Tile
	protected double tileFirstX = 0;
	
	//Score
	protected static int score = 0;
	protected static int bestScore = 0;
	
	//FPS
	protected int saved_FPS = 0;
	protected int calculate_FPS = 0;
	protected long saved_time = 0;
	protected long prev_time = 0;
	protected long current_time = 0;
	protected long saved_milli_sec = 0;
	
	public Map(FlappyBird _frame) {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					if(status == START) {
						status = PLAYING;
						FlappyBird.bird.velocity = Bird.firstVelocity;
						FlappyBird.bird.currentPillar = pillarList.getFirst();
						FlappyBird.bird.update(System.currentTimeMillis());
					}
					else if(status == PLAYING) {
						if(!isJumped) {
							jump();
						}
					}
				}
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					if(status == TITLE) {
						status = START;
						playButton.setVisible(false);
					}
					else if(status == START){
						status = PLAYING;
						FlappyBird.bird.velocity = Bird.firstVelocity;
						FlappyBird.bird.currentPillar = pillarList.getFirst();
						FlappyBird.bird.update(System.currentTimeMillis());
					}
					else if(status == PLAYING) {
						if(!isJumped) {
							jump();
						}
					}
				}
			}
		});
		
		frame = _frame;
		setSize(new Dimension(xSize, ySize));
		setPreferredSize(new Dimension(xSize, ySize));
		setBorder(null);
		setLayout(null);
		setFocusTraversalKeysEnabled(false);
		
		playButton = new JLabel();
		playButton.setBounds(155, 455, 140, 80);
		playButton.setVisible(true);
		playButton.setIcon(FlappyBird.resource.play);
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				playButtonEntered = true;
				if(!playButtonPressed) {
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				playButtonEntered = false;
				if(!playButtonPressed) {
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					playButtonPressed = true;
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					if(playButtonEntered) {
						if(status == TITLE) {
							status = START;
							playButton.setVisible(false);
						}
						if(status == SCORE) {
							restart();
						}
					}
				}
			}
		});
		
		add(scoreBoard);
		add(playButton);
	}
	
	public void jump() {
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				new Music(FlappyBird.resource.sfx_wing, 1).play();
				isJumped = true;
				FlappyBird.bird.update(System.currentTimeMillis());
				FlappyBird.bird.velocity = Bird.firstVelocity;
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				isJumped = false;
			}
		};
		FlappyBird.threadPool.submit(runnable);
	}
	
	public void restart() {
		playButtonPressed = false;
		status = START;
		scoreBoard.close();
		open();
	}
	
	@Override
	public void paint(Graphics g) {
		frame.pack();
		Graphics2D g2 = (Graphics2D) g;
		Image image = createImage(getWidth(), getHeight());
		
		long time = System.currentTimeMillis();
		if (prev_time == 0) {
			prev_time = time;
		} else {
			prev_time = current_time;
		}
		current_time = time;
		
		update();
		FlappyBird.bird.update(current_time);
		if(image != null) {
			Draw(image.getGraphics());
			g2.drawImage(image, 0, 0, this);
		}
		
		repaint();
	}
	
	public void Draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		//BackGround
		g2.drawImage(FlappyBird.resource.noon.getImage(), 0, 0, xSize, ySize, this);
		
		//Tile
		for(int i=0;tileFirstX + 215 * i < 450;i++) {
			g2.drawImage(FlappyBird.resource.tile.getImage(), (int)tileFirstX + 215 * i, yGround, 215, 75, this);
		}
		
		//Pillar
		synchronized(pillarList) {
			for(Pillar pillar : pillarList) {
				g2.drawImage(Pillar.pillarUp, pillar.x + (int)xPos, 0
						, pillar.x + Pillar.width + (int)xPos, pillar.yStart
						, 0, 266 - pillar.yStart, Pillar.width, 266, this);
				g2.drawImage(Pillar.pillarDown, pillar.x + (int)xPos, pillar.yEnd
						, pillar.x + Pillar.width + (int)xPos, yGround
						, 0, 0, Pillar.width, yGround - pillar.yEnd, this);
			}
		}
		
		//Bird
		AffineTransform old = g2.getTransform();
		g2.rotate(Math.toRadians(FlappyBird.bird.angle), FlappyBird.bird.xCenter() , FlappyBird.bird.yCenter());
		g2.drawImage(FlappyBird.bird.image.getImage(), FlappyBird.bird.xLeft(), FlappyBird.bird.yTop()
				, Bird.width, Bird.height, this);
		g2.setTransform(old);
		
		//Score
		if(status != TITLE) {
			drawScore(g, score, 225, 20, 30, 45, this);
		}
		
		//Title
		if(status == TITLE) {
			g2.drawImage(FlappyBird.resource.title.getImage(), 105, 100, 240, 60, this);
		}
		
		//Get Ready, Tap
		if(status == START) {
			g2.drawImage(FlappyBird.resource.ready.getImage(), 105, 100, 240, 60, this);
			g2.drawImage(FlappyBird.resource.tap.getImage(), 150, 250, 150, 135, this);
			g2.drawImage(FlappyBird.resource.spacebar.getImage(), 175, 400, 100, 45, this);
		}
		
		//GameOver
		if(status == SCORE) {
			g2.drawImage(FlappyBird.resource.gameOver.getImage(), 105, 100, 240, 60, this);
		}
		
		//ScoreBoard
		paintComponents(g);
	}
	
	public static void drawScore(Graphics g, int num, int xCenter, int y, int xSize, int ySize, ImageObserver ob) {
		int first = 0, second = 0, third = 0;
		if(num < 10) {
			first = num;
			drawSingleNum(g, first, xCenter - xSize / 2, y, xSize, ySize, ob);
		}
		else if(num < 100) {
			first = num / 10;
			second = num - first * 10;
			drawSingleNum(g, first, xCenter - xSize, y, xSize, ySize, ob);
			drawSingleNum(g, second, xCenter, y, xSize, ySize, ob);
		}
		else if(num < 1000) {
			first = num / 100;
			second = (num - first * 100) / 10;
			third = num - first * 100 - second * 10;
			drawSingleNum(g, first, xCenter - xSize * 3 / 2, y, xSize, ySize, ob);
			drawSingleNum(g, second, xCenter - xSize / 2, y, xSize, ySize, ob);
			drawSingleNum(g, third, xCenter + xSize / 2, y, xSize, ySize, ob);
		}
	}
	public static void drawSingleNum(Graphics g, int num, int x, int y, int xSize, int ySize, ImageObserver ob) {
		g.drawImage(FlappyBird.resource.nums.getImage(), x, y, x+xSize, y+ySize, 60*num, 0, 60*num+55, 90, ob);
	}
	
	public void update() {
		if(status == PLAYING) {
			tileFirstX -= mapVelocity * (current_time - prev_time) / 1000;
			if(tileFirstX <= -215) {
				tileFirstX += 215;
			}

			xPos -= mapVelocity * (current_time - prev_time) / 1000;
			if(xPos <= -450) {
				xPos += 450;
				synchronized(pillarList) {
					Iterator<Pillar> pillarIter = pillarList.iterator();
					Pillar pillar = null;
					int lastPillarX = 0, removedNum = 0;
					while (pillarIter.hasNext()) {
						pillar = pillarIter.next();
						pillar.x -= 450;
						lastPillarX = pillar.x;
						if(pillar.x + Pillar.width < 0) {
							pillarIter.remove();
							removedNum++;
						}
					}
					
					Pillar newPillar = null;
					for(int i=0;i<removedNum;i++) {
						newPillar = FlappyBird.pillarPool.getPillar(lastPillarX + 250 * (i + 1), 115 + random.nextInt(145));
						pillarList.add(newPillar);
					}
				}
			}
		}
	}
		
	public void open() {
		frame.setContentPane(this);
		frame.pack();
		setFocusable(true);
		grabFocus();
		
		xPos = 0;
		tileFirstX = 0;
		score = 0;
		
		FlappyBird.bird.setDefault();
		
		pillarList.clear();
		pillarList.add(FlappyBird.pillarPool.getPillar(500, 115 + random.nextInt(145)));
		pillarList.add(FlappyBird.pillarPool.getPillar(750, 115 + random.nextInt(145)));
		pillarList.add(FlappyBird.pillarPool.getPillar(1000, 115 + random.nextInt(145)));
		pillarList.add(FlappyBird.pillarPool.getPillar(1250, 115 + random.nextInt(145)));
		pillarList.add(FlappyBird.pillarPool.getPillar(1500, 115 + random.nextInt(145)));
		pillarList.add(FlappyBird.pillarPool.getPillar(1750, 115 + random.nextInt(145)));
	}
	
}
