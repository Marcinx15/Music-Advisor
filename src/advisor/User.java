package advisor;

public class User {
    private boolean authorizedToSpotify;

    public User() {
        authorizedToSpotify = false;
    }

    public boolean isAuthorizedToSpotify() {
        return authorizedToSpotify;
    }

    public void setAuthorizedToSpotify(boolean authorizedToSpotify) {
        this.authorizedToSpotify = authorizedToSpotify;
    }
}
