package im.kitillt.yandexmoneyclient;

import com.yandex.money.api.net.ApiClient;
import com.yandex.money.api.net.DefaultApiClient;
import com.yandex.money.api.net.HostsProvider;

/**
 * Created by kirill on 15.09.15.
 */
public class DefaultApiMobileClient extends DefaultApiClient {

    public DefaultApiMobileClient(String clientId) {
        super(clientId);
    }

    public DefaultApiMobileClient(String clientId, boolean debugLogging) {
        super(clientId, debugLogging);
    }

    public DefaultApiMobileClient(String clientId, boolean debugLogging, String platform) {
        super(clientId, debugLogging, platform);
    }

    @Override
    public HostsProvider getHostsProvider() {
        return new HostsProvider(true);
    }
}
