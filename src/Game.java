import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	// Classes
	public static Entity[][] allEntities;
	public static Entity[] oneDimensionAllEntities;

	// Variables to be used for the class references
	public static int maxWidthOfEntities = 80;
	public static int maxHeightOfEntities = 60;

	// Main Components
	public static final int WIDTH = 640, HEIGHT = 480;
	public Dimension gameDim = new Dimension(WIDTH, HEIGHT);
	BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	JFrame frame;

	// FPS and UPS
	int frames = 0;
	int ticks = 0;
	int FPS = 0;
	int UPS = 0;
	double delta = 0D;

	// Misc

	public static void main(String[] args) {
		new Game().start();
	}

	public void run() {
		long currentTime = System.currentTimeMillis();
		while (true) {
			tick();
			render();
			frames++;
			long finalTime = System.currentTimeMillis();
			if (finalTime - currentTime >= 1000) {
				currentTime = finalTime;
				FPS = frames;
				frames = 0;
			}

			try {
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public synchronized void start() {
		Thread thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		System.exit(0);
	}

	public Game() {
		init();
		createEntities();
	}

	private void createEntities() {
		int i = 0;

		for (int x = 0; x < maxWidthOfEntities; x++) {
			for (int y = 0; y < maxHeightOfEntities; y++) {
				int randomID = new Random().nextInt(2);

				Entity e = new Entity(x * 8, y * 8, randomID);
				allEntities[x][y] = e;
				oneDimensionAllEntities[i] = e;

				i++;
			}
		}
	}

	private void init() {
		frame = new JFrame("FPS: " + FPS);
		setMinimumSize(gameDim);
		setMaximumSize(gameDim);
		setPreferredSize(gameDim);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		allEntities = new Entity[maxWidthOfEntities + 2][maxHeightOfEntities + 2];
		oneDimensionAllEntities = new Entity[maxWidthOfEntities * maxHeightOfEntities];

		requestFocus();
	}

	public void tick() {
		frame.setTitle("FPS: " + FPS);

		for (Entity e : oneDimensionAllEntities) {
			EntityBehaviors.viewSurroundingEntities(e, allEntities);
		}

		for (Entity e : oneDimensionAllEntities) {
			e.tick();
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

		for (Entity e : oneDimensionAllEntities) {
			e.render(g);
		}

		g.dispose();
		bs.show();
	}
}