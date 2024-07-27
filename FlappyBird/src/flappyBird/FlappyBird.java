package flappyBird;

import java.awt.EventQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;

public class FlappyBird extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected static Resource resource = new Resource();
	
	protected static Bird bird = new Bird();
	
	protected static Map map1;

	//Pillar Pool
	protected static PillarPool pillarPool;
	
	//Thread Pool
	protected static ExecutorService threadPool;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlappyBird frame = new FlappyBird();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public FlappyBird() {
		makePillars();
		makeThreads();
		
		setTitle("Flappy Bird");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		makeMaps();
		
		map1.open();
		new Music(resource.bgm, -1).play();
	}
	
	public void makePillars() {
		pillarPool = new PillarPool();
	}
	
	public void makeThreads() {
		threadPool = Executors.newFixedThreadPool(1000);
	}
	
	public void makeMaps() {
		map1 = new Map(this);
	}
}