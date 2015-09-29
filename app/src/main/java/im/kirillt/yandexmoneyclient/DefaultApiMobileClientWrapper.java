package im.kirillt.yandexmoneyclient;

import com.squareup.okhttp.OkHttpClient;
import com.yandex.money.api.net.ApiClient;
import com.yandex.money.api.net.DefaultApiClient;
import com.yandex.money.api.net.HostsProvider;
import com.yandex.money.api.net.UserAgent;
import com.yandex.money.api.utils.Language;

public class DefaultApiMobileClientWrapper implements ApiClient {//extends DefaultApiClient {

    private DefaultApiClient defaultApiClient;

    public DefaultApiMobileClientWrapper(DefaultApiClient apiClient) {
        this.defaultApiClient = apiClient;
    }

    @Override
    public String getClientId() {
        return defaultApiClient.getClientId();
    }

    @Override
    public OkHttpClient getHttpClient() {
        return defaultApiClient.getHttpClient();
    }

    @Override
    public HostsProvider getHostsProvider() {
        return new HostsProvider(true);
    }

    @Override
    public UserAgent getUserAgent() {
        return defaultApiClient.getUserAgent();
    }

    @Override
    public Language getLanguage() {
        return defaultApiClient.getLanguage();
    }
}
