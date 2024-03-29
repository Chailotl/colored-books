package com.chai.coloredBooks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		// Register command
		this.getCommand("coloredbooks").setExecutor(new PreviewCommand());

		// Listeners
		getServer().getPluginManager().registerEvents(new FormatListener(), this);
	}

	@Override
	public void onDisable()
	{

	}

	public static BookMeta formatBook(BookMeta book)
	{
		// Get
		List<String> pages = book.getPages();
		List<String> newPages = new ArrayList<>();

		// Format
		for (String page : pages)
		{
			newPages.add(ChatColor.translateAlternateColorCodes('&', page));
		}

		book.setPages(newPages);
		return book;
	}
}