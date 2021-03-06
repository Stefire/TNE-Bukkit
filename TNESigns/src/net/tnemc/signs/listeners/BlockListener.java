package net.tnemc.signs.listeners;

import net.tnemc.core.TNE;
import net.tnemc.core.common.api.IDFinder;
import net.tnemc.signs.SignsData;
import net.tnemc.signs.SignsManager;
import net.tnemc.signs.SignsModule;
import net.tnemc.signs.signs.SignType;
import net.tnemc.signs.signs.TNESign;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.SignChangeEvent;

import java.sql.SQLException;

/**
 * The New Economy Minecraft Server Plugin
 * <p>
 * Created by Daniel on 5/29/2018.
 * <p>
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
public class BlockListener implements Listener {

  private TNE plugin;

  public BlockListener(TNE plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockBreakEvent(final BlockBreakEvent event) {
    try {
      final TNESign sign = (event.getBlock().getType().equals(Material.SIGN) || event.getBlock().getType().equals(Material.WALL_SIGN)) ?
          SignsData.loadSign(event.getBlock().getLocation())
          : SignsData.loadSignAttached(event.getBlock().getLocation());

      if (sign != null) {
        final Sign signBlock = (event.getBlock().getType().equals(Material.SIGN) || event.getBlock().getType().equals(Material.WALL_SIGN)) ?
            (Sign) event.getBlock().getState() : SignsManager.getAttachedSign(event.getBlock());
        if (signBlock != null && !SignsModule.manager().getType(sign.getType()).onSignDestroy(sign.getOwner(), event.getPlayer().getUniqueId())) {
          event.setCancelled(true);
        } else {
          if (signBlock != null) {
            SignsData.deleteSign(signBlock.getBlock().getLocation());
          }
        }
      }
    } catch(SQLException e) {
      e.printStackTrace();
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onChange(SignChangeEvent event) {
    if(SignsManager.validSign(event.getLine(0))) {
      SignType type = SignsModule.manager().getType(event.getLine(0));
      if(type != null) {
        Block attached = (event.getBlock().getType().equals(Material.WALL_SIGN))?
            event.getBlock().getRelative(((org.bukkit.material.Sign)event.getBlock().getState().getData()).getAttachedFace()) : null;
        if (type.create(event, attached, IDFinder.getID(event.getPlayer()))) {
          event.setLine(0, type.success() + event.getLine(0));
        } else {
          event.setLine(0, ChatColor.RED + "Failed");
        }
      }
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockBurn(final BlockBurnEvent event) {
    final Sign sign = (event.getBlock().getType().equals(Material.SIGN) || event.getBlock().getType().equals(Material.WALL_SIGN))?
        (Sign)event.getBlock().getState() : SignsManager.getAttachedSign(event.getBlock());

    if(sign != null) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockIgnite(final BlockIgniteEvent event) {
    final Sign sign = (event.getBlock().getType().equals(Material.SIGN) || event.getBlock().getType().equals(Material.WALL_SIGN))?
        (Sign)event.getBlock().getState() : SignsManager.getAttachedSign(event.getBlock());

    if(sign != null) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onPistonExtend(final BlockPistonExtendEvent event) {
    final Sign sign = (event.getBlock().getType().equals(Material.SIGN) || event.getBlock().getType().equals(Material.WALL_SIGN))?
        (Sign)event.getBlock().getState() : SignsManager.getAttachedSign(event.getBlock());

    if(sign != null) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onPistonRetract(final BlockPistonRetractEvent event) {
    final Sign sign = (event.getBlock().getType().equals(Material.SIGN) || event.getBlock().getType().equals(Material.WALL_SIGN))?
        (Sign)event.getBlock().getState() : SignsManager.getAttachedSign(event.getBlock());

    if(sign != null) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockExplode(final BlockExplodeEvent event) {
    final Sign sign = (event.getBlock().getType().equals(Material.SIGN) || event.getBlock().getType().equals(Material.WALL_SIGN))?
        (Sign)event.getBlock().getState() : SignsManager.getAttachedSign(event.getBlock());

    if(sign != null) {
      event.setCancelled(true);
    }
  }
}