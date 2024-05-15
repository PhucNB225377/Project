package map;

import java.util.Arrays;
import java.util.TreeMap;

import bIO.BasicIO;
import bIO.BasicNumber;
import bIO.BasicSprite;
import bIO.BasicWall;
import bIO.BoundingBox;
import bIO.Vec2f;

public class ground extends BasicWall {
	private static final TreeMap<String, BasicSprite> state_machine = new TreeMap<String, BasicSprite>() {{
		put("idle", new BasicSprite (
				"src/map2.png",
				Arrays.asList(0),
				Arrays.asList(0),
				Arrays.asList(200),
				Arrays.asList(20)
			));
	}};
	@Override
	public BasicSprite getSprite() { return state_machine.get(getState()); }
	
	private Vec2f velocity;
	
	public ground(BasicIO io) {
		super(io);
		setState("idle");
		setPosition(new Vec2f(0,0));
		setBBox(new BoundingBox(200, 20));
		setBBoxOrigin(new Vec2f(0, 0));
		setBBoxDrawFlag(true);
		
		velocity = new Vec2f(0,0);
	}
	public void setVelocity(float a, float b) {
		velocity = new Vec2f(a,b);
	}
	
	@Override
	public void fixedUpdate() {
		Vec2f mov_step = velocity.mul(new BasicNumber(getIO().getUnitStep(1f)));
		Vec2f old_pos = getPosition();
		setPosition(getPosition().add(mov_step));
		getIO().quadUpdateObject(this, old_pos);
	}

	@Override
	public void postUpdate() {
		bboxUpdate();
		if (getPosition().getX().lt(new BasicNumber(-200))) {
			getIO().removeObject(this);
		}
	}
}