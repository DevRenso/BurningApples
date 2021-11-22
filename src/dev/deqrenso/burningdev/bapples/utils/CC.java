package dev.deqrenso.burningdev.bapples.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.ChatColor;

public class CC {
    private static long MINUTE;
    private static long HOUR;
    static {
        CC.MINUTE = TimeUnit.MINUTES.toMillis(1L);
        CC.HOUR = TimeUnit.HOURS.toMillis(1L);
    }
    public static String getRemaining(final long millis, final boolean milliseconds) {
        return getRemainingg(millis, milliseconds, true);
    }
    
    public static String getRemainingg(final long duration, final boolean milliseconds, final boolean trail) {
        if (milliseconds && duration < CC.MINUTE) {
            return String.valueOf((trail ? DateTimeFormats.REMAINING_SECONDS_TRAILING : DateTimeFormats.REMAINING_SECONDS).get().format(duration * 0.001)) + 's';
        }
        return DurationFormatUtils.formatDuration(duration, String.valueOf((duration >= CC.HOUR) ? "HH:" : "") + "mm:ss");
    }
	public static String translate(String c){
		return ChatColor.translateAlternateColorCodes('&', c);
	}
    public static Integer tryParse(String string) {
        try {
            return Integer.parseInt(string);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
	public static List<String> translate(List<String> msg) {
		for(int i =0;i<msg.size();i++){
            msg.set(i, translate(msg.get(i)));
		}
		return msg;
	}
}
