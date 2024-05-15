package map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.Random;

import bIO.BasicIO;
import bIO.BasicNumber;
import bIO.BasicObject;
import bIO.BasicSprite;
import bIO.BasicTimer;
import bIO.BoundingBox;
import bIO.Vec2f;
import enemy.test02.EnemyTest02Bullet;

public class map extends BasicObject {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", null);
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	private BasicTimer ground_timer;
	private boolean ground_flag;

	private Random random = new Random();	
	
	public map(BasicIO io) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(100, 150));
		setBBox(new BoundingBox(0, 0));
		setBBoxOrigin(new Vec2f(0, 0));
		setBBoxDrawFlag(true);
		setSpriteOrigin(new Vec2f(0, 0));
		setScale(1f);
		
		ground_timer = new BasicTimer(25000, new Runnable() {
			public void run() {
				ground_flag = true;
			}
		});
		ground_timer.setup();
		ground_flag=false;
	}
	
	@Override
	public void fixedUpdate() {
		ground_timer.run();
		if (ground_flag) {
			ground_timer.stop();
			Vec2f pos = new Vec2f(640, 200+random.nextFloat(50)-100);
			boolean flip = getHFlip();
			getIO().addObject(new ground(getIO()) {{
				setPosition(pos);
				setVelocity(flip?1000:-1500, 0);
			}});
			ground_flag = false;
			ground_timer.setup();
		}
	}
}