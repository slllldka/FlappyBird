package flappyBird;

public class PillarPool {
	public Pillar[] pillars;
	
	int curIdx = 0;
	
	public PillarPool() {
		pillars = new Pillar[6];
		for(int i=0;i<6;i++) {
			pillars[i] = new Pillar(0, 0);
		}
	}
	
	public Pillar getPillar(int _x, int _yStart) {
		Pillar ret = pillars[curIdx];
		ret.setDefault(_x, _yStart);
		curIdx = (curIdx + 1) % 6;
		return ret;
	}
}
