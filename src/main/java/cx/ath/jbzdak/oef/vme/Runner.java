package cx.ath.jbzdak.oef.vme;

import cx.ath.jbzdak.oef.vme.caen.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: Jacek Bzdak jbzdak@gmail.com
 * Date: May 25, 2010
 */
public class Runner {






   public static void main(String[] args){
      final Lock lock = new ReentrantLock();

      final Condition condition = lock.newCondition();
      CaenVMEWrapper wrapper = new CaenVMEWrapper();
      try {
         wrapper.open(BoardType.V2718, 0, 0);
//         int res;
//         if((res = wrapper.getNat().WriteCycle(wrapper.getHandle(), 0xFFF002, new byte[]{0,8}, 0x39, 0x2))!=0){
//            throw new VmeException("res" + ErrorCodes.getCodeFromInt(res));
//         }
         wrapper.write(0xFFF006, new byte[]{0,-128}, AdressModifier.NO_PRIVILEGED_DATA_24);
         wrapper.write(0xFFF006, new byte[]{0,0}, AdressModifier.NO_PRIVILEGED_DATA_24);
         wrapper.write(0xFFF002, new byte[]{28,0}, AdressModifier.NO_PRIVILEGED_DATA_24);
         wrapper.write(0xFFF004, new byte[]{3,8}, AdressModifier.NO_PRIVILEGED_DATA_24);


         byte[] result  = wrapper.read(0xFFF002, DataWidth.TWO_BYTES, AdressModifier.NO_PRIVILEGED_DATA_24);
            System.out.println("SR1:\t"+Byte.toString(result[1])+" "+Byte.toString(result[0]));
         result  = wrapper.read(0xFFF004, DataWidth.TWO_BYTES, AdressModifier.NO_PRIVILEGED_DATA_24);
            System.out.println("SR2:\t"+Byte.toString(result[1])+" "+Byte.toString(result[0]));
         result  = wrapper.read(0xFFF006, DataWidth.TWO_BYTES, AdressModifier.NO_PRIVILEGED_DATA_24);
            System.out.println("SR3:\t"+Byte.toString(result[1])+" "+Byte.toString(result[0]));

         result  = wrapper.read(0xFFF002, DataWidth.TWO_BYTES, AdressModifier.NO_PRIVILEGED_DATA_24);
            System.out.println("SR1:\t"+Byte.toString(result[1])+" "+Byte.toString(result[0]));
         
         wrapper.write(0xFFF000, new byte[]{0,0}, AdressModifier.NO_PRIVILEGED_DATA_24);
         //byte[]
         int quit = 0;
           //while (quit <50)
           {
               result = wrapper.read(0xFFF002, DataWidth.TWO_BYTES, AdressModifier.NO_PRIVILEGED_DATA_24);
            System.out.println(Byte.toString(result[0]) + " XXX "  + (result[0] & 32));
            while ((result[0] & 32) == 0 ){
               try{
                  lock.lock();
                  condition.await(100, TimeUnit.MILLISECONDS);
                  result  = wrapper.read(0xFFF002, DataWidth.TWO_BYTES, AdressModifier.NO_PRIVILEGED_DATA_24);
                  System.out.println(Byte.toString(result[0]) + " XXX "  + (result[0] & (byte)32));
               } catch (InterruptedException e) {
                  e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
               } finally {
                  lock.unlock();
               }
            }
            for (int i=0;i<64;i++)
            {
               result  = wrapper.read(0xFFF000, DataWidth.TWO_BYTES, AdressModifier.NO_PRIVILEGED_DATA_24);
               System.out.println(i+"=\t" + Byte.toString(result[1])+" "+Byte.toString(result[0]));
            }

            wrapper.write(0xFFF002, new byte[]{0x1C,0}, AdressModifier.NO_PRIVILEGED_DATA_24);

            result  = wrapper.read(0xFFF002, DataWidth.TWO_BYTES, AdressModifier.NO_PRIVILEGED_DATA_24);
               System.out.println("SR1:\t"+Byte.toString(result[1])+" "+Byte.toString(result[0]));

              quit++;
           }



      } catch (VmeException e) {
         e.printStackTrace();
         wrapper.close();
      }
   }

        private static String getBits( int value )
         {
         int displayMask = 1 << 31;
         StringBuffer buf = new StringBuffer( 35 );

         for ( int c = 1; c <= 32; c++ )
             {
             buf.append( ( value & displayMask ) == 0 ? '0' : '1' );
             value <<= 1;

             if ( c % 8 == 0 )
             buf.append( ' ' );
         }

         return buf.toString();
     }
}

