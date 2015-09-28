package im.kirillt.yandexmoneyclient.events;

public class WebAuthResultEvent {
    public final String login;
    public final String token;
    public final String errorDescription;

    public WebAuthResultEvent(String login, String token, String errorDescription) {
        this.login = login;
        this.token = token;
        this.errorDescription = errorDescription;
    }
}
