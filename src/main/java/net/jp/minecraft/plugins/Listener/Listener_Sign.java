package net.jp.minecraft.plugins.Listener;

import net.jp.minecraft.plugins.Listener.Listener_Gomibako;
import net.jp.minecraft.plugins.TeisyokuMenuIndex;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Listener_Sign implements Listener {
    //看板右クリックでゴミ箱を表示
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        else{
            if(block.getType().equals(Material.SIGN_POST) || block.getType().equals(Material.WALL_SIGN)){
                Sign sign = (Sign) block.getState();
                if (sign.getLine(0).equalsIgnoreCase("[GOMI]")){
                    Listener_Gomibako.openGomibako(player);
                }
                else if (sign.getLine(0).equalsIgnoreCase("[Teisyoku]") || sign.getLine(1).equalsIgnoreCase("[Teisyoku]")){
                    TeisyokuMenuIndex.getMenu(player);
                }
                else if (sign.getLine(0).equalsIgnoreCase("[Cart]") || sign.getLine(1).equalsIgnoreCase("[Cart]") || sign.getLine(2).equalsIgnoreCase("[Cart]") || sign.getLine(3).equalsIgnoreCase("[Cart]")) {
                    Msg.success(player, "マインカートをインベントリに追加しました");
                    ItemStack cart = new ItemStack(Material.MINECART);
                    ItemMeta cartmeta = cart.getItemMeta();
                    cartmeta.setDisplayName(TeisyokuPlugin2.getInstance().Local);//通常のMinecartはLocalとする
                    cart.setItemMeta(cartmeta);
                    player.getInventory().addItem(cart);
                }
                else if(sign.getLine(0).equalsIgnoreCase("[Warp]")){
                    if(sign.getLine(1) != null){
                        Bukkit.getServer().dispatchCommand(player, "warp " + sign.getLine(1));
                    }
                    else{
                        Bukkit.getServer().dispatchCommand(player, "warp");
                    }
                }
                //特殊なMinecartをゲットさせる
                else if(sign.getLine(0).equalsIgnoreCase("[minecart]")){
                	
                	Inventory inv = player.getInventory();
            		
            		if(sign.getLine(2).equalsIgnoreCase("shinkansen")){
            			
            			ItemStack shinkansen = new ItemStack(Material.MINECART);
            			
            			ItemMeta shinkansenmeta = shinkansen.getItemMeta();
            			
            			shinkansenmeta.setDisplayName(TeisyokuPlugin2.getInstance().Shinkansen);
            			
            			shinkansen.setItemMeta(shinkansenmeta);
            			
            			inv.addItem(shinkansen);

                        Msg.success(player, "マインカート(新幹線)をインベントリに追加しました");
            			
            			return;
            			
            		}
            		else if(sign.getLine(2).equalsIgnoreCase("express")){
            			
            			ItemStack express = new ItemStack(Material.MINECART);
            			
            			ItemMeta expressmeta = express.getItemMeta();
            			
            			expressmeta.setDisplayName(TeisyokuPlugin2.getInstance().Express);
            			
            			express.setItemMeta(expressmeta);
            			
            			inv.addItem(express);

                        Msg.success(player, "マインカート(急行)をインベントリに追加しました");
            			
            			return;
            			
            		}
            		else if(sign.getLine(2).equalsIgnoreCase("local")){
            			
            			ItemStack local = new ItemStack(Material.MINECART);
            			
            			ItemMeta localmeta = local.getItemMeta();
            			
            			localmeta.setDisplayName(TeisyokuPlugin2.getInstance().Local);
            			
            			local.setItemMeta(localmeta);
            			
            			inv.addItem(local);

                        Msg.success(player, "マインカート(各駅停車)をインベントリに追加しました");
            			
            			return;
            			
            		}
            		else if(sign.getLine(2).equalsIgnoreCase("sightseeing")){
            			
            			ItemStack Sightseeing = new ItemStack(Material.MINECART);
            			
            			ItemMeta Sightseeingmeta = Sightseeing.getItemMeta();
            			
            			Sightseeingmeta.setDisplayName(TeisyokuPlugin2.getInstance().Sightseeing);
            			
            			Sightseeing.setItemMeta(Sightseeingmeta);
            			
            			inv.addItem(Sightseeing);

                        Msg.success(player, "マインカート(観光鉄道)をインベントリに追加しました");
            			
            			return;
            			
            		}
            		else {

                        Msg.success(player, "看板の3行目に Shinkansen Express Local Sightseeing のどれかを記入してください");
            			
            			return;
            			
            		}
                }
            }
        }
    }
}
