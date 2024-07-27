package flappyBird;

import java.awt.Image;

public class Pillar {
	protected static final int width = 80;
	
	protected static Image pillarUp = FlappyBird.resource.pillarUp.getImage().getScaledInstance(
			80, 266, Image.SCALE_SMOOTH);
	protected static Image pillarDown = FlappyBird.resource.pillarDown.getImage().getScaledInstance(
			80, 266, Image.SCALE_SMOOTH);
	
	
	protected boolean isBirdEntered, isScored;
	protected int x, yStart, yEnd;
	
	public Pillar(int _x, int _yStart) {
		setDefault(_x, _yStart);
	}
	
	public void setDefault(int _x, int _yStart) {
		isBirdEntered = false;
		isScored = false;
		x = _x;
		yStart = _yStart;
		yEnd = yStart + 150;
	}
	
	public boolean checkCollision() {
		if((FlappyBird.bird.xRight() > x + Map.xPos + 10) && (FlappyBird.bird.xLeft() < x + Map.xPos + width - 10)) {
			if((FlappyBird.bird.yTop() < yStart - 5) || (FlappyBird.bird.yBottom() > yEnd + 5)) {
				return false;
			}
			else {
				return true;
			}
		}
		else {
			if(FlappyBird.bird.xLeft() > x + Map.xPos + width) {
				FlappyBird.bird.currentPillar = Map.pillarList.get(Map.pillarList.indexOf(FlappyBird.bird.currentPillar)+1);
			}
			return true;
		}
	}
}
