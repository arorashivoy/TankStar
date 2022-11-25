package com.arorashivoy.tank_star.Helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class TiledObjectBox {
	public static void parseTiledObjectLayer(World world, MapObjects objects) {
		for(MapObject object : objects) {
			Shape shape;

			if(object instanceof PolylineMapObject) {
				shape = createPolyline((PolylineMapObject) object);
			} else {
				continue;
			}

			Body body;
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.StaticBody;
			body = world.createBody(bodyDef);
			body.createFixture(shape, 1.0f);
			shape.dispose();
		}
	}

	private static ChainShape createPolyline(PolylineMapObject polyline) {
		float[] vertices = polyline.getPolyline().getTransformedVertices();
		Vector2[] worldVertices = new Vector2[vertices.length / 2];

		for(int i = 0; i < worldVertices.length; i++) {
			worldVertices[i] = new Vector2(vertices[i * 2] / CustomConstants.PPM, vertices[i * 2 + 1] / CustomConstants.PPM);
		}
		ChainShape chainShape = new ChainShape();
		chainShape.createChain(worldVertices);
		return chainShape;
	}
}
