package net.tnemc.core.common.transaction.type;

import net.tnemc.core.TNE;
import net.tnemc.core.economy.transaction.TransactionAffected;
import net.tnemc.core.economy.transaction.result.TransactionResult;

/**
 * The New Economy Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by Daniel on 8/24/2017.
 */
public class TransactionTake implements TNETransactionType {
  @Override
  public String name() {
    return "take";
  }

  @Override
  public boolean console() {
    return true;
  }

  @Override
  public TransactionResult success() {
    return TNE.transactionManager().getResult("lost");
  }

  @Override
  public TransactionResult fail() {
    return TNE.transactionManager().getResult("failed");
  }

  @Override
  public TransactionAffected affected() {
    return TransactionAffected.RECIPIENT;
  }
}