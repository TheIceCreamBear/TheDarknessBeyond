package com.joseph.thedarknessbeyond.engine;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.joseph.thedarknessbeyond.gameobject.GameObject;
import com.joseph.thedarknessbeyond.gameobject.RenderLockObject;
import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.gui.IGuiElement;
import com.joseph.thedarknessbeyond.gui.windows.ConsoleWindow;
import com.joseph.thedarknessbeyond.gui.windows.EventWindow;
import com.joseph.thedarknessbeyond.gui.windows.PauseMenuWindow;
import com.joseph.thedarknessbeyond.gui.windows.ScreenSelectionWindow;
import com.joseph.thedarknessbeyond.gui.windows.StorageWindow;
import com.joseph.thedarknessbeyond.handlers.GKELAH;
import com.joseph.thedarknessbeyond.interfaces.IDrawable;
import com.joseph.thedarknessbeyond.interfaces.IUpdateable;
import com.joseph.thedarknessbeyond.reference.Reference;
import com.joseph.thedarknessbeyond.reference.ScreenRefrence;
import com.joseph.thedarknessbeyond.resource.StorageManager;
import com.joseph.thedarknessbeyond.threads.EventThread;
import com.joseph.thedarknessbeyond.threads.RenderThread;
import com.joseph.thedarknessbeyond.threads.ShutdownThread;

/**
 * Class responsible for doing all the heavy lifting in the game. Hold the engine 
 * Algorithm and references to all objects used by the game.
 * @author David Santamaria - Original Author
 * @author Joseph Terribile - Current Maintainer
 */
public class GameEngine {

	/**
	 * boolean that expressed the state of the engine, whether it is
	 * <code> running </code> or not
	 */
	private static boolean running = true;
	/**
	 * The instance of the GameEngine
	 */
	private static GameEngine instance;
	/**
	 * Displayed at the top of the screen. Expresses the fps, and time and other
	 * such things
	 */
	private static String stats = "";

	/**
	 * Used to display the screen
	 */
	private JFrame frame;

	/**
	 * First graphics instance
	 */
	private Graphics g;
	/**
	 * BufferedImage graphics instance
	 */
	private Graphics g2;
	/**
	 * Image that is displayed on the screen
	 */
	private BufferedImage i;
	
	private FontRenderContext frc;

	// Threads
	private RenderLockObject rlo;
	private RenderThread rtInstance;
	private ShutdownThread sdtInstance;
	private EventThread et;
	
	/**
	 * Instance of {@link GKELAH GKELAH} stored to keep a reference to it.
	 */
	private GKELAH keyHandlerInstance;
	
	private boolean handCursor;

	/**
	 * ArrayList of GameObjects - to be looped over to update and draw
	 */
	private static ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	/**
	 * Only updatable objects, looped through to update
	 */
	private static ArrayList<IUpdateable> updateable = new ArrayList<IUpdateable>();
	/**
	 * Drawable only objects
	 */
	private static ArrayList<IDrawable> drawable = new ArrayList<IDrawable>();
	private static ArrayList<IGuiElement> guiElements = new ArrayList<IGuiElement>();
	private static ArrayList<AbstractButton> buttons = new ArrayList<AbstractButton>();

	/**
	 * 
	 * @return the instance of the GameEngine
	 */
	public static GameEngine getInstance() {
		return instance;
	}

	/**
	 * 
	 * @return state of {@link GameEngine#running GameEngine.running}
	 */
	public static boolean isRunning() {
		return running;
	}

	public static void main(String[] args) {
		if (Reference.DEBUG_MODE) {
			System.out.println(Runtime.getRuntime().maxMemory());
			System.err.println("x: " + ScreenRefrence.WIDTH + "y: " + ScreenRefrence.HEIGHT);
		}
		instance = new GameEngine();
		instance.run();
	}

	/**
	 * Starts the GameEngine
	 */
	public static void startGameEngine() {
		instance = new GameEngine();
		instance.run();
	}

	/**
	 * Initializes and instantiates
	 */
	private GameEngine() {
		initialize();
	}

	/**
	 * Initializes all the stuff
	 */
	private void initialize() {
		instance = this;
		if ((System.getProperty("os.name").contains("Windows") || System.getProperty("os.name").contains("windows")) && !System.getProperty("user.home").contains("AppData")) {
			System.setProperty("user.home", System.getProperty("user.home") + "/AppData/Roaming");
		}
		
		ScreenRefrence.doScreenCalc();
		
		Reference.Fonts.init();
		
		this.sdtInstance = new ShutdownThread();
		Runtime.getRuntime().addShutdownHook(sdtInstance);
		

		this.frame = new JFrame("Game Template");
//		this.frame.setBounds(-1, -1, 1, 1); // Trolololololol hehehehhe
		this.frame.setBounds(0, 0, ScreenRefrence.WIDTH, ScreenRefrence.HEIGHT);
		this.frame.setResizable(false);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setUndecorated(true);
		this.frame.setVisible(true);

		this.rlo = new RenderLockObject();
		this.rtInstance = new RenderThread("RenderThread", this.rlo, this);
		this.rtInstance.start();
		
		this.keyHandlerInstance = new GKELAH();
		this.frame.addKeyListener(keyHandlerInstance);

		this.i = new BufferedImage(ScreenRefrence.WIDTH, ScreenRefrence.HEIGHT, BufferedImage.TYPE_INT_RGB);
		this.g2 = this.i.createGraphics();
		this.g = frame.getGraphics();
		// Turn on AntiAliasing
		if (g2 instanceof Graphics2D) {
			((Graphics2D)g2).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
		
		this.frc = ((Graphics2D) g2).getFontRenderContext();
		
		this.et = new EventThread();
		this.et.start();
		
		// Start adding here
		new StorageManager();
		this.addNewElement(new ScreenSelectionWindow(510, 0, ScreenRefrence.WIDTH / ScreenRefrence.scale, ScreenRefrence.HEIGHT - 1));
		this.addNewElement(new StorageWindow());
		this.addNewElement(new EventWindow());
		this.addNewElement(new ConsoleWindow(0));
		this.addNewElement(new PauseMenuWindow());
		com.joseph.thedarknessbeyond.util.FileSaveSystem.init();

		System.gc();
		
		this.releaseFocous();
	}
	
	public void addButton(AbstractButton b) {
		if (buttons.contains(b)) {
			return;
		}
		
		this.frame.add(b);
	}
	
	private void addNewElement(IGuiElement e) {
		if (guiElements.contains(e)) {
			return;
		}
		
		guiElements.add(e);
		if (e instanceof AbstractButton) {
			this.addButton((AbstractButton) e);
		}
	}

	/**
	 * Loops through all the updatables and updates them
	 * 
	 * @param deltaTime - Time between each frame (used to evaluate things within
	 *            update methods of each object)
	 */
	private void update(double deltaTime) {
		for (GameObject gameObject : gameObjects) {
			gameObject.update(deltaTime);
		}

		for (IUpdateable upject : updateable) {
			upject.update(deltaTime);
		}
		
		for (IGuiElement gui : guiElements) {
			gui.updateUpdateableElements(deltaTime);
		}
	}

	/**
	 * Loops through all the Drawables and draws them
	 * 
	 * @param g - Graphics instance to draw upon
	 * @param observer - observer to put graphics instance upon
	 */
	private void render(Graphics g, ImageObserver observer) {
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, ScreenRefrence.WIDTH, ScreenRefrence.HEIGHT);

		for (GameObject gameObject : gameObjects) {
			gameObject.draw(g2, observer);
		}

		for (IDrawable iDrawable : drawable) {
			iDrawable.draw(g2, observer);
		}

		for (IGuiElement iGuiOverlay : guiElements) {
			iGuiOverlay.drawBackground(g2, observer);
			iGuiOverlay.drawUpdateableElements(g2, observer);
		}

		if (Reference.DEBUG_MODE) {
			g2.setColor(Color.GREEN);
			if (ScreenRefrence.scale == 2) {
				g2.setFont(Reference.Fonts.SCALED_UP_FONT);
			} else {
				g2.setFont(Reference.Fonts.DEFAULT_FONT);
			}
			g2.drawString(stats, 25, 60);
			
			Point p = getMouseLocation();
			if (p != null) {
				String s = p.toString();
				Rectangle2D r;
				if (ScreenRefrence.scale == 2) {
					g.setFont(Reference.Fonts.SCALED_UP_FONT);
					r = Reference.Fonts.SCALED_UP_FONT.getStringBounds(s, frc);
				} else {
					g.setFont(Reference.Fonts.DEFAULT_FONT);
					r = Reference.Fonts.DEFAULT_FONT.getStringBounds(s, frc);
				}
				int yOff = (int) r.getHeight();
//				System.err.println(s + "," + p.x + "," + (p.y+ yOff));
				g2.drawString(s, p.x, p.y + yOff);
			}
		}

		g.drawImage(this.i, 0, 0, this.frame);
	}

	/**
	 * Short hand for {@link GameEngine#render(Graphics, ImageObserver)}, 
	 * used by {@link com.joseph.thedarknessbeyond.threads.RenderThread RenderThread}
	 * to render the game onto the frame.
	 */
	public void render() {
		render(g, frame);
	}

	/**
	 * Runs the GameEngine
	 */
	private void run() {
		long time = System.nanoTime();
		final double tick = 60.0;
		double ms = 1000000000 / tick;
		double deltaTime = 0;
		int ticks = 0;
		int fps = 0;
		long timer = System.currentTimeMillis();
		long frameLimit = 80;
		long currentTime;
		int seconds = 0;
		int minutes = 0;
		int hours = 0;

		while (running) {
			currentTime = System.nanoTime();
			deltaTime += (currentTime - time) / ms;
			time = currentTime;

			if (deltaTime >= 1) {
				ticks++;
				update(deltaTime);
				deltaTime--;
			}

			synchronized (rlo) {
				rlo.setWasNotified(true);
				rlo.notify();
			}
			fps++;

			while (deltaTime < frameLimit) {
				currentTime = System.nanoTime();
				deltaTime += (currentTime - time) / ms;
				time = currentTime;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				seconds++;
				if (seconds > 60) {
					seconds %= 60;
					minutes++;

					if (minutes > 60) {
						minutes %= 60;
						hours++;
					}
				}

				// GT stands for GameTime.
				stats = "Ticks: " + ticks + ", FPS: " + fps + ", GT: " + ((hours < 10) ? "0" + hours : hours) + ":"
						+ ((minutes < 10) ? "0" + minutes : minutes) + ":" + ((seconds < 10) ? "0" + seconds : seconds);
				if (Reference.DEBUG_MODE) {
					System.out.println(stats);
				}
				ticks = 0;
				fps = 0;
				if (Reference.DEBUG_MODE) {
					System.out.println(Runtime.getRuntime().freeMemory());
				}
				System.gc();
				if (Reference.DEBUG_MODE) {
					System.out.println(Runtime.getRuntime().freeMemory());
				}
			}
		}
	}
	
	public void setSelectMouse() {
		if (handCursor) {
			return;
		}
		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.handCursor = true;
	}
	
	public void setDefaultMouse() {
		if (!handCursor) {
			return;
		}
		this.frame.setCursor(Cursor.getDefaultCursor());
		this.handCursor = false;
	}
	
	public Point getMouseLocation() {
		return this.frame.getContentPane().getMousePosition();
	}
	
	public void releaseFocous() {
		this.frame.requestFocus();
	}
	
	public FontRenderContext getFrc() {
		return this.frc;
	}
}
/*
 * -XX:+UnlockCommercialFeatures -XX:+FlightRecorder
 * -XX:FlightRecorderOptions=stackdepth=2048
 * -XX:StartFlightRecording=duration=60m,filename=GameTemplate.jfr
 */