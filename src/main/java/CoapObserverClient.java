
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;

/**
 * @author ebruno
 */
public class CoapObserverClient {

    class AsynchListener implements CoapHandler {
        @Override
        public void onLoad(CoapResponse response) {
            System.out.println( response.getResponseText() );
        }

        @Override
        public void onError() {
            System.err.println("Error");
        }
    }

    public static void main(String[] args) {
        CoapObserverClient client = new CoapObserverClient();
    }
    
    public CoapObserverClient() {
        CoapClient client = 
                new CoapClient("coap://localhost:5683/temp");

        // observe
        AsynchListener asynchListener = new AsynchListener();
        CoapObserveRelation observation = 
                client.observe(asynchListener);
        
        // User presses ENTER to exit
        waitForKeypress();
        System.out.println("Exiting...");
        observation.proactiveCancel();
    }
    
    private void waitForKeypress() {
        System.out.println("Press ENTER to exit...");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try { br.readLine(); } catch (IOException e) { }
    }
    
    
}
