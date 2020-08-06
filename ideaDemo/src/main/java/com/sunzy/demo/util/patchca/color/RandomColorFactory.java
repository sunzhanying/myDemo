package com.sunzy.demo.util.patchca.color;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomColorFactory implements ColorFactory {

	private Color min;
	private Color max;
	private Color color;

	public RandomColorFactory() {
		min = new Color(20,40,80);
		max = new Color(21,50,140);
	}
	
	public void setMin(Color min) {
		this.min = min;
	}

	public void setMax(Color max) {
		this.max = max;
	}

	@Override
	public Color getColor(int index) {
		if (color == null) {
			ThreadLocalRandom r = ThreadLocalRandom.current();
			color = new Color( min.getRed() + r.nextInt((max.getRed() - min.getRed())),
					min.getGreen() + r.nextInt((max.getGreen() - min.getGreen())),
					min.getBlue() + r.nextInt((max.getBlue() - min.getBlue())));
		}
		return color;
	}

}
