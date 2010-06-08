package cx.ath.jbzdak.oef.vme;

/**
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: Jun 8, 2010
 * Time: 12:39:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {

   public static void main(String[] args){
      byte ii = 1 << 5;
      System.out.println(ii);
      System.out.println((ii<<2));      
      System.out.println((ii & 32));
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
