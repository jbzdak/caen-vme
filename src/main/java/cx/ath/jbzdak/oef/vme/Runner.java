package cx.ath.jbzdak.oef.vme;

import cx.ath.jbzdak.oef.vme.caen.AdressModifier;
import cx.ath.jbzdak.oef.vme.caen.BoardType;
import cx.ath.jbzdak.oef.vme.caen.CaenVMEWrapper;

/**
 * User: Jacek Bzdak jbzdak@gmail.com
 * Date: May 25, 2010
 */
public class Runner {


   public static void main(String[] args){
      CaenVMEWrapper wrapper = new CaenVMEWrapper();
      wrapper.open(BoardType.V2718, 0, 0);
      wrapper.write(0xFFF002, new byte[]{8}, AdressModifier.NO_MODIFIER);
      wrapper.write(0xFFF002, new byte[]{0,0}, AdressModifier.NO_MODIFIER);      
   }
}
