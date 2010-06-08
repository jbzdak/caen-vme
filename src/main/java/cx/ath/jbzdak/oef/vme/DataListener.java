package cx.ath.jbzdak.oef.vme;

/**
 * Created by IntelliJ IDEA.
 * User: jbzdak
 * Date: Jun 8, 2010
 * Time: 2:46:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DataListener<DATA> {

   void onNewData(DATA data);
}
