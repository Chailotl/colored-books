package com.chai.coloredBooks;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class PreviewCommand implements CommandExecutor
{
	private Main plugin = JavaPlugin.getPlugin(Main.class);

	//@Override
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length == 0)
		{
			return false;
		}
		else if (args[0].equals("info"))
		{
			// Send message
			sender.sendMessage(ChatColor.YELLOW + "[ColoredBooks]" + ChatColor.GRAY + " Version " + plugin.getDescription().getVersion());
			return true;
		}
		else if (args[0].equals("preview"))
		{
			if (!sender.hasPermission("coloredbooks.preview"))
			{
				// Send message
				sender.sendMessage(ChatColor.YELLOW + "[ColoredBooks]" + ChatColor.RED + " You do not have permission to run this command.");
				return true;
			}
			else if (!(sender instanceof Player))
			{
				// Send message
				sender.sendMessage(ChatColor.YELLOW + "[ColoredBooks]" + ChatColor.RED + " This command cannot be run from the console.");
				return true;
			}

			Player ply = (Player) sender;

			// Do stuff
			ItemStack item = ply.getInventory().getItemInMainHand();

			if (!item.getType().equals(Material.WRITABLE_BOOK))
			{
				// Send message
				sender.sendMessage(ChatColor.YELLOW + "[ColoredBooks]" + ChatColor.RED + " Hold a book and quill in your main hand to view a preview of it.");
				return true;
			}

			// Modify meta
			BookMeta meta = (BookMeta) item.getItemMeta();
			meta.setGeneration(BookMeta.Generation.TATTERED);
			meta.setTitle("Preview");
			meta.setAuthor("");
			meta = Main.formatBook(meta);

			// Preview
			ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
			book.setItemMeta(meta);
			ply.openBook(book);

			return true;
		}
		else if (args[0].equals("copy"))
		{
			if (!sender.hasPermission("coloredbooks.copy"))
			{
				// Send message
				sender.sendMessage(ChatColor.YELLOW + "[ColoredBooks]" + ChatColor.RED + " You do not have permission to run this command.");
				return true;
			}
			else if (!(sender instanceof Player))
			{
				// Send message
				sender.sendMessage(ChatColor.YELLOW + "[ColoredBooks]" + ChatColor.RED + " This command cannot be run from the console.");
				return true;
			}

			String title = "Preview Copy";
			Player ply = (Player) sender;

			// Do stuff
			ItemStack item = ply.getInventory().getItemInMainHand();

			if (!item.getType().equals(Material.WRITABLE_BOOK))
			{
				// Send message
				sender.sendMessage(ChatColor.YELLOW + "[ColoredBooks]" + ChatColor.RED + " Hold a book and quill in your main hand to make a copy of it.");
				return true;
			}

			// Get title
			if (args.length > 1)
			{
				title = "";
				for (int i = 1; i < args.length; ++i)
				{
					title += args[i] + " ";
				}
				title = title.trim();

				if (title.length() > 16)
				{
					// Send message
					sender.sendMessage(ChatColor.YELLOW + "[ColoredBooks]" + ChatColor.RED + " Title cannot be more than 16 letters long.");
					return true;
				}
			}

			// Modify meta
			BookMeta meta = (BookMeta) item.getItemMeta();
			meta.setGeneration(BookMeta.Generation.COPY_OF_COPY);
			meta.setTitle(title);
			meta.setAuthor(ply.getName());
			meta = Main.formatBook(meta);

			// Preview
			ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
			book.setItemMeta(meta);
			ply.getInventory().addItem(book);

			return true;
		}
		else
		{
			return false;
		}
	}
}