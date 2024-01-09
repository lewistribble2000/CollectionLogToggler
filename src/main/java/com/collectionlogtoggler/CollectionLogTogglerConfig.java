package com.collectionlogtoggler;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("CollectionLogToggler")
public interface CollectionLogTogglerConfig extends Config
{
	@ConfigItem(
			position = 0,
			keyName = "Sire",
			name = "Abyssal Sire",
			description = "Modify the sire tab"
	)
	default String Sire()
	{
		return "";
	}
	@ConfigItem(
			position = 1,
			keyName = "Hydra",
			name = "Alchemical Hydra",
			description = "Modify the hydra tab"
	)
	default String Hydra()
	{
		return "";
	}

	@ConfigItem(
			position = 2,
			keyName = "Barrows",
			name = "Barrows Chests",
			description = "Modify the barrows tab"
	)
	default String Barrows()
	{
		return "";
	}

	@ConfigItem(
			position = 3,
			keyName = "Bryophyta",
			name = "Bryophyta",
			description = "Modify the bryophyta tab"
	)
	default String Bryophyta()
	{
		return "";
	}

	@ConfigItem(
			position = 4,
			keyName = "Callisto",
			name = "Callisto and Artio",
			description = "Modify the callsito and artio tab"
	)
	default String Callisto()
	{
		return "";
	}

	@ConfigItem(
			position = 5,
			keyName = "Cerberus",
			name = "Cerberus",
			description = "Modify the cerberus tab"
	)
	default String Cerberus()
	{
		return "";
	}

	@ConfigItem(
			position = 6,
			keyName = "ChaosElemental",
			name = "Chaos Elemental",
			description = "Modify the chaos elemental tab"
	)
	default String ChaosElemental()
	{
		return "";
	}

	@ConfigItem(
			position = 7,
			keyName = "ChaosFanatic",
			name = "Chaos Fantaic",
			description = "Modify the chaos fanatic tab"
	)
	default String ChaosFanatic()
	{
		return "";
	}

	//Different tab tests, shuffle into correct order when entire col log is done
	@ConfigItem(
			position = 8,
			keyName = "Misc",
			name = "Miscellaneous",
			description = "Modify the miscellaneous tab"
	)
	default String Misc()
	{
		return "";
	}

	@ConfigItem(
			position = 9,
			keyName = "Duke",
			name = "Duke Sucellus",
			description = "Modify the duke tab"
	)
	default String Duke()
	{
		return "";
	}

	@ConfigItem(
			position = 10,
			keyName = "Levi",
			name = "The Leviathan",
			description = "Modify the leviathan tab"
	)
	default String Levi()
	{
		return "";
	}

	@ConfigItem(
			position = 11,
			keyName = "Vard",
			name = "Vardorvis",
			description = "Modify the vardorvis tab"
	)
	default String Vard()
	{
		return "";
	}

	@ConfigItem(
			position = 12,
			keyName = "Whisper",
			name = "The Whisperer",
			description = "Modify the whisperer tab"
	)
	default String Whisper()
	{
		return "";
	}

	@ConfigItem(
			position = 13,
			keyName = "EasyClue",
			name = "Easy Clue Scrolls",
			description = "Modify the easy clue tab"
	)
	default String EasyClue()
	{
		return "";
	}

	@ConfigItem(
			position = 14,
			keyName = "HardClue",
			name = "Hard Clue Scrolls",
			description = "Modify the hard clue tab"
	)
	default String HardClue()
	{
		return "";
	}
}
