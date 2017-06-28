package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.CustomFonts;
import com.mygdx.game.DemoTexturePacker;
import com.mygdx.game.GdxUtil;
import com.mygdx.game.SpriteRendering;
import com.mygdx.game.com.mygdx.game.sticks.WalkingStick;
import com.mygdx.game.com.mygdx.game.tiles.GameClassDemo;
import com.mygdx.game.desktop.com.mygdx.game.desktop.utility.CopyFiles;

public class DesktopLauncher {
	public static void main (String[] arg) {

		try {
			CopyFiles copyFileUtil = new CopyFiles();
			String [] inputImageFolders = {"C:/D Drive/Projects/libGDX/images/sticks1"
											,"C:/D Drive/Projects/libGDX/images/sticks2"};
			String commonImageFolder = "C:/D Drive/Projects/libGDX/proTest1/android/assets/allimages";
			String [] fileTypes = {"png","jpg"};
			String destForAtlas = "C:/D Drive/Projects/libGDX/proTest1/android/assets/stick";

			for(String inFolder: inputImageFolders) {
				copyFileUtil.copy(fileTypes, inFolder, commonImageFolder);
			}

			GdxUtil.pack(commonImageFolder,
					destForAtlas,
					"WalkingStick");
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			new LwjglApplication(new GameClassDemo(), config);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
