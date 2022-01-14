package net.coolsimulations.EffectsLeft;

import java.net.URL;
import java.util.Scanner;

import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class EffectsLeftUpdateHandler {
	
	private static String latestVersion;
	private static String latestVersionInfo;
	public static boolean isOld = false;
	public static TranslatableComponent updateInfo = null;
	public static TextComponent updateVersionInfo = null;
	
	public static void init() {
		
		try {
            URL url = new URL("https://coolsimulations.net/mcmods/effectsleft/versionchecker118.txt");
            Scanner s = new Scanner(url.openStream());
            latestVersion = s.next();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		try {
            URL url = new URL("https://coolsimulations.net/mcmods/effectsleft/updateinfo118.txt");
            Scanner s = new Scanner(url.openStream());
            latestVersionInfo = s.nextLine();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		if(latestVersion != null) {
			
			if(latestVersion.equals("ended")) {
				
				isOld = true;
				
				TextComponent el = new TextComponent(ELReference.MOD_NAME);
				el.withStyle(ChatFormatting.BLUE);
				
				TextComponent MCVersion = new TextComponent(SharedConstants.getCurrentVersion().getName());
				MCVersion.withStyle(ChatFormatting.BLUE);
				
				updateInfo = new TranslatableComponent("el.update.display3", new Object[] {el, MCVersion});
				updateInfo.withStyle(ChatFormatting.YELLOW);
			}
			
			if(!latestVersion.equals(ELReference.VERSION) && !latestVersion.equals("ended")) {
				
				isOld = true;
				
				TextComponent el = new TextComponent(ELReference.MOD_NAME);
				el.withStyle(ChatFormatting.BLUE);
				
				TextComponent version = new TextComponent(latestVersion);
				version.withStyle(ChatFormatting.BLUE);
				
				updateInfo = new TranslatableComponent("el.update.display1", new Object[] {el, version});
				updateInfo.withStyle(ChatFormatting.YELLOW);
				
				if(latestVersionInfo != null) {
					
					updateVersionInfo = new TextComponent(latestVersionInfo);
					updateVersionInfo.withStyle(ChatFormatting.DARK_AQUA, ChatFormatting.BOLD);
				}
				
			}
			
		}
	}

}
