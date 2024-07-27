package flappyBird;

import javax.swing.ImageIcon;

public class Bird {
	protected static final int width = 46, height = 32;
	
	protected final int x = 150;
	protected double y = 300;
	
	protected static final double firstVelocity = 500;
	protected double velocity;
	protected double gravityAccel = 1800;
	
	protected static long prevTime, currentTime;
	
	protected Pillar currentPillar = null;
	
	protected ImageIcon image = FlappyBird.resource.birdFly1;
	protected int imageNum = 1;
	
	protected int angle = 0;
	
	public Bird() {
		setDefault();
	}
	
	public int xLeft() {
		return x;
	}
	public int xCenter() {
		return x + width / 2;
	}
	public int xRight() {
		return x + width;
	}
	
	public int yTop() {
		return (int)y;
	}
	public int yCenter() {
		return (int)y + height / 2;
	}
	public int yBottom() {
		return (int)y + height;
	}
	
	public void setDefault() {
		y = 300;
		velocity = 0;
		prevTime = 0;
		currentTime = 0;
		image = FlappyBird.resource.birdFly1;
		imageNum = 1;
		angle = 0;
	}
	
	public void update(long time) {
		int imageSet = 0;
		if(Map.status == Map.PLAYING) {
			if(prevTime == 0) {
				prevTime = time;
				
			}
			else {
				prevTime = currentTime;
			}
			currentTime = time;
			
			long deltaTime = currentTime - prevTime;
			
			if(!checkCollision()) {
				new Music(FlappyBird.resource.sfx_hit, 1).play();
				Map.bestScore = Map.score > Map.bestScore ? Map.score : Map.bestScore;
				Map.status = Map.SCORE;
				Map.scoreBoard.open();
			}

			double prevVelocity = velocity;
			velocity -= gravityAccel * deltaTime / 1000;
			y -= (prevVelocity + velocity) / 2 * deltaTime / 1000;

			if(velocity >= 0) {
				angle = -15;
				if(imageSet == 0) {
					if(imageNum == 0) {
						image = FlappyBird.resource.birdFly1;
						imageNum = 1;
					}
					else if(imageNum == 1) {
						image = FlappyBird.resource.birdFly2;
						imageNum = 2;
					}
					else if(imageNum == 2) {
						image = FlappyBird.resource.birdFly0;
						imageNum = 0;
					}
				}
			}
			else {
				angle = -15 - (int)velocity / 5;
				image = FlappyBird.resource.birdFly1;
				if(angle > 90) {
					angle = 90;
				}
				imageNum = 1;
			}
			
			imageSet = (imageSet + 1) % 4;
			
			if(!currentPillar.isBirdEntered) {
				if(FlappyBird.bird.xLeft() > currentPillar.x + Map.xPos) {
					currentPillar.isBirdEntered = true;
				}
			}
			
			if(currentPillar.isBirdEntered) {
				if(!currentPillar.isScored) {
					if(FlappyBird.bird.xLeft() > currentPillar.x + Pillar.width / 2 + Map.xPos) {
						new Music(FlappyBird.resource.sfx_point, 1).play();
						Map.score++;
						currentPillar.isScored = true;
					}
				}
			}
		}
	}
	
	public boolean checkCollision() {
		if(Map.score >= 999) {
			return false;
		}
		if(yBottom() > Map.yGround + 5) {
			return false;
		}
		
		if(!currentPillar.checkCollision()) {
			return false;
		}
		
		return true;
	}
}
