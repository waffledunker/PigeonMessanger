package it.polimi.deib.p2pchat.discovery.actionlisteners;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import it.polimi.deib.p2pchat.discovery.Configuration;
import it.polimi.deib.p2pchat.discovery.TabFragment;
import it.polimi.deib.p2pchat.discovery.services.ServiceList;
import it.polimi.deib.p2pchat.discovery.services.WiFiP2pService;
import it.polimi.deib.p2pchat.discovery.services.WiFiP2pServicesFragment;
import it.polimi.deib.p2pchat.discovery.services.WiFiServicesAdapter;

/**
 * A custom Bonjour's DnsSdServiceResponseListener used to update the UI when a service is available.
 * <p></p>
 * This class use Bonjour Prot
 * <p></p>
 * Created by Stefano Cappa on 28/02/15.
 */
public class CustomDnsServiceResponseListener implements WifiP2pManager.DnsSdServiceResponseListener {

    private static final String TAG = "DnsRespListener";

    @Override
    public void onDnsSdServiceAvailable(String instanceName, String registrationType, WifiP2pDevice srcDevice) {
        // A service has been discovered. Is this our app?
        if (instanceName.equalsIgnoreCase(Configuration.SERVICE_INSTANCE)) {

            // update the UI and add the item the discovered device.
            WiFiP2pServicesFragment fragment = TabFragment.getWiFiP2pServicesFragment();
            if (fragment != null) {
                WiFiServicesAdapter adapter = fragment.getMAdapter();
                WiFiP2pService service = new WiFiP2pService();
                service.setDevice(srcDevice);
                service.setInstanceName(instanceName);
                service.setServiceRegistrationType(registrationType);

                ServiceList.getInstance().addServiceIfNotPresent(service);

                if (adapter != null) {
                    adapter.notifyItemInserted(ServiceList.getInstance().getSize() - 1);
                }
                Log.d(TAG, "onDnsSdServiceAvailable " + instanceName);
            }
        }
    }
}