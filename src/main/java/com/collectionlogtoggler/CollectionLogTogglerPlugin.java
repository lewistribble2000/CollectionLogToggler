package com.collectionlogtoggler;

import com.google.common.base.Splitter;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.ScriptID;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ScriptPostFired;
import net.runelite.api.widgets.ComponentID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import okhttp3.Call;

import java.security.interfaces.DSAKey;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@PluginDescriptor(
	name = "CollectionLogToggler"
)
public class CollectionLogTogglerPlugin extends Plugin
{
	private static final Splitter ID_SPLITTER = Splitter.on(',').omitEmptyStrings().trimResults();
	private static final int COL_LOG_ENTRY_HEADER_TITLE_INDEX = 0;
	@Inject
	private Client client;

	@Inject
	private CollectionLogTogglerConfig config;

	private List<String> SireIDs;
	private List<String> HydraIDs;
	private List<String> BarrowsIDs;
	private List<String> BryophytaIDs;
	private List<String> CallistoIDs;
	private List<String> CerberusIDs;
	private List<String> ChaosElementalIDs;
	private List<String> ChaosFanaticIDs;

	//Different tab tests, shuffle into correct order when entire col log is done
	private List<String> MiscIDs;
	private List<String> DukeIDs;
	private List<String> LeviIDs;
	private List<String> VardIDs;
	private List<String> WhisperIDs;
	private List<String> EasyClueIDs;
	private List<String> HardClueIDs;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Starting collection log splitter");

		SireIDs = ID_SPLITTER.splitToList(config.Sire());

		HydraIDs = ID_SPLITTER.splitToList(config.Hydra());

		BarrowsIDs = ID_SPLITTER.splitToList(config.Barrows());

		BryophytaIDs = ID_SPLITTER.splitToList(config.Bryophyta());

		CallistoIDs = ID_SPLITTER.splitToList(config.Callisto());

		CerberusIDs = ID_SPLITTER.splitToList(config.Cerberus());

		ChaosElementalIDs = ID_SPLITTER.splitToList(config.ChaosElemental());

		ChaosFanaticIDs = ID_SPLITTER.splitToList(config.ChaosFanatic());

		MiscIDs = ID_SPLITTER.splitToList(config.Misc());

		DukeIDs = ID_SPLITTER.splitToList(config.Duke());

		LeviIDs = ID_SPLITTER.splitToList(config.Levi());

		VardIDs = ID_SPLITTER.splitToList(config.Vard());

		WhisperIDs = ID_SPLITTER.splitToList(config.Whisper());

		EasyClueIDs = ID_SPLITTER.splitToList(config.EasyClue());

		HardClueIDs = ID_SPLITTER.splitToList(config.HardClue());
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{

	}

	@Subscribe
	public void onScriptPostFired(ScriptPostFired scriptPostFired)
	{
		if(scriptPostFired.getScriptId() != ScriptID.COLLECTION_DRAW_LIST)
		{
			return;
		}

		ModifyTab("Abyssal Sire", SireIDs);
		ModifyTab("Alchemical Hydra", HydraIDs);
		ModifyTab("Barrows Chests", BarrowsIDs);
		ModifyTab("Bryophyta", BryophytaIDs);
		ModifyTab("Callisto and Artio", CallistoIDs);
		ModifyTab("Cerberus", CerberusIDs);
		ModifyTab("Chaos Elemental", ChaosElementalIDs);
		ModifyTab("Chaos Fanatic", ChaosFanaticIDs);
		ModifyTab("Miscellaneous", MiscIDs);
		ModifyTab("Duke Sucellus", DukeIDs);
		ModifyTab("The Leviathan", LeviIDs);
		ModifyTab("Vardorvis", VardIDs);
		ModifyTab("The Whisperer", WhisperIDs);
		ModifyTab("Easy Treasure Trails", EasyClueIDs);
		ModifyTab("Hard Treasure Trails", HardClueIDs);
	}

	private void ModifyTab(String TabName, List<String> IDList)
	{
		log.info("Modifying tab");
		Widget ColHeader = client.getWidget(ComponentID.COLLECTION_LOG_ENTRY_HEADER);
		if(ColHeader != null && ColHeader.getChildren() != null)
		{
			int ModifiedCount = 0;

			Widget Title = ColHeader.getChild(COL_LOG_ENTRY_HEADER_TITLE_INDEX);
			if(Title.getText().equals(TabName))
			{
				Widget Items = client.getWidget(ComponentID.COLLECTION_LOG_ENTRY_ITEMS);
				for(Widget Child : Items.getChildren())
				{
					boolean isNumberInArray = Arrays.stream(StringListToIntArray(IDList)).anyMatch(num -> num == Child.getIndex());
					if(isNumberInArray)
					{
						Child.setOpacity(0);
						ModifiedCount+= 1;
					}
				}
			}

			ModifyObtainedAmount(ModifiedCount);
		}
	}

	private void ModifyObtainedAmount(int Amount)
	{
		Pattern pattern = Pattern.compile("Obtained: <col=[^>]+>([^<]+)</col>");
		Widget Obtained = client.getWidget(ComponentID.COLLECTION_LOG_ENTRY_HEADER);
		Widget Next = Obtained.getChild(1);
		Matcher matcher = pattern.matcher(Next.getText());
		if(matcher.find())
		{
			String fractionPart = matcher.group(1);

			// Split the fraction into numerator and denominator
			String[] parts = fractionPart.split("/");

			// Update the first number (numerator) while leaving the second one (denominator) unchanged
			int updatedNumerator = Integer.parseInt(parts[0]) + Amount; // You can modify this part as needed

			// Reconstruct the modified fraction
			String modifiedFraction = updatedNumerator + "/" + parts[1];

			log.debug(modifiedFraction);

			Next.setText("Obtained: <col=ffff00>"+ modifiedFraction + "</col>");
		}

	}

	private static int[] StringListToIntArray(List<String> Strings)
	{
		int[] IntArray = new int[Strings.size()];

		for(int i = 0; i < Strings.size(); i++)
		{
			IntArray[i] = Integer.parseInt(Strings.get(i));
		}
		return IntArray;
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		String key = event.getKey();
		switch(key)
		{
			case "Sire":
				SireIDs = ID_SPLITTER.splitToList(config.Sire());
				break;
			case "Hydra":
				HydraIDs = ID_SPLITTER.splitToList(config.Hydra());
				break;
			case "Barrows":
				BarrowsIDs = ID_SPLITTER.splitToList(config.Barrows());
				break;
			case "Bryophyta":
				BryophytaIDs = ID_SPLITTER.splitToList(config.Bryophyta());
				break;
			case "Callisto":
				CallistoIDs = ID_SPLITTER.splitToList(config.Callisto());
				break;
			case "Cerberus":
				CerberusIDs = ID_SPLITTER.splitToList(config.Cerberus());
				break;
			case "ChaosElemental":
				ChaosElementalIDs = ID_SPLITTER.splitToList(config.ChaosElemental());
				break;
			case "ChaosFanatic":
				ChaosFanaticIDs = ID_SPLITTER.splitToList(config.ChaosFanatic());
				break;
			case "Misc":
				MiscIDs = ID_SPLITTER.splitToList(config.Misc());
				break;
			case "Duke":
				DukeIDs = ID_SPLITTER.splitToList(config.Duke());
				break;
			case "Levi":
				LeviIDs = ID_SPLITTER.splitToList(config.Levi());
				break;
			case "Vard":
				VardIDs = ID_SPLITTER.splitToList(config.Vard());
				break;
			case "Whisper":
				WhisperIDs = ID_SPLITTER.splitToList(config.Whisper());
				break;
			case "EasyClue":
				EasyClueIDs = ID_SPLITTER.splitToList(config.EasyClue());
				break;
			case "HardClue":
				HardClueIDs = ID_SPLITTER.splitToList(config.HardClue());
				break;
		}
	}

	@Provides
	CollectionLogTogglerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CollectionLogTogglerConfig.class);
	}
}
