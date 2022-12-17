package com.arorashivoy.tank_star.Objects;

import com.arorashivoy.tank_star.Helper.CustomConstants;
import com.arorashivoy.tank_star.Main;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.arorashivoy.tank_star.Helper.CustomConstants;

public class Bullets
{
     private final Main app;
     private boolean player;
     private boolean isVisible = false;
     Body body;
    //private size, type, texture,
    public Bullets(Main app, boolean player, World world, float SCALE)
    {
        this.player = player;
        this.app = app;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(0 / CustomConstants.PPM, 0 / CustomConstants.PPM);
        def.fixedRotation = true;
        body = world.createBody(def);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(CustomConstants.BULLET_WIDTH / (SCALE * CustomConstants.PPM), CustomConstants.BULLET_HEIGHT / (SCALE * CustomConstants.PPM));

        body.createFixture(polygonShape, 1f);
        polygonShape.dispose();
    }


}
