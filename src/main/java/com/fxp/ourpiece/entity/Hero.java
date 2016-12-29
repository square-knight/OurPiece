package com.fxp.ourpiece.entity;

import java.util.Collection;

import com.fxp.ourpiece.util.GameUtils;

public class Hero {
	private Integer id;
	private String userName;
//	��ɫ���ɱ�
	private final String hexColor;
//	����λ�ÿɱ�
	private Direction direction;
	private Location location;
	
	public Hero(Integer id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
		this.hexColor = GameUtils.getRandomHexColor();
		resetState();
	}

	private void resetState() {
		this.setDirection(Direction.NONE);		
		this.location = GameUtils.getRandomLocation();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHexColor() {
		return hexColor;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
    public synchronized void update(Collection<Hero> heros) throws Exception {
        Location nextLocation = location.getAdjacentLocation(direction);

        if (nextLocation.x >= GameUtils.PLAYFIELD_WIDTH) {
            nextLocation.x = 0;
        }
        if (nextLocation.y >= GameUtils.PLAYFIELD_HEIGHT) {
            nextLocation.y = 0;
        }
        if (nextLocation.x < 0) {
            nextLocation.x = GameUtils.PLAYFIELD_WIDTH;
        }
        if (nextLocation.y < 0) {
            nextLocation.y = GameUtils.PLAYFIELD_HEIGHT;
        }
      //������ײ,�����λ�ý��ᷢ����ײ��ǰ����λ��
        if(!willCrash(nextLocation,heros)){
        	location = nextLocation;
        }
        
//        if (direction != Direction.NONE) {
//        	location = nextLocation;
        	
//        }

//        handleCollisions(heros);
    }

	private boolean willCrash(Location nextLocation, Collection<Hero> heros) {
		for(Hero hero:heros){
			if(hero.getId()!=id&&Math.abs(hero.getLocation().x-nextLocation.x)<Location.GRID_SIZE&&Math.abs(hero.getLocation().y-nextLocation.y)<Location.GRID_SIZE){
				return true;
			}
		}
		return false;
	}


}