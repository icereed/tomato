package tomato.gfx;


import java.awt.image.BufferedImage;

import tomato.gfx.Art;

public class TestingImages {

	public static BufferedImage test_level_01 = Art.load("test_level_01.png");

	public static BufferedImage[][] tiles = Art.split(Art.load("tiles_16px.png"), 16, 16);
    
    
   
}
