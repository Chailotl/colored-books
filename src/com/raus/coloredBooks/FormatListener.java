package com.raus.coloredBooks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

public class FormatListener implements Listener
{
	@EventHandler
	public void onCloseBook(PlayerEditBookEvent event)
	{
		if (!event.isSigning()) { return; }
		
		// Get book
		BookMeta book = event.getNewBookMeta();
		
		// Modify
		book = Main.formatBook(book);
		
		// Update
		event.setNewBookMeta(book);
	}
}
