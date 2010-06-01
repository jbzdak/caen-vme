package cx.ath.jbzdak.oef.vme;

import cx.ath.jbzdak.oef.vme.caen.*;

/**
 * User: Jacek Bzdak jbzdak@gmail.com
 * Date: May 25, 2010
 */
public class Runner {


   public static void main(String[] args){
      CaenVMEWrapper wrapper = new CaenVMEWrapper();
      try {
         wrapper.open(BoardType.V2718, 0, 0);
         int res;
         if((res = wrapper.getNat().WriteCycle(wrapper.getHandle(), 0xFFF002, new byte[]{0,8}, 0x39, 0x2))!=0){
            throw new VmeException("res" + ErrorCodes.getCodeFromInt(res));
         }
         wrapper.write(0xFFF002, new byte[]{0,8}, AdressModifier.NO_PRIVILEGED_DATA_24);
         wrapper.write(0xFFF002, new byte[]{0,0}, AdressModifier.NO_PRIVILEGED_DATA_24);
      } catch (VmeException e) {
         e.printStackTrace();
         wrapper.close();
      }
   }
}
