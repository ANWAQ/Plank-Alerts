package ANWAQ.plankalerts;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PlankAlertsPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PlankAlertsPlugin.class);
		RuneLite.main(args);
	}
}