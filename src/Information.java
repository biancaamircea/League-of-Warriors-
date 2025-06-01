import java.util.ArrayList;
import java.util.List;

public class Information {
    private final String name;
    private final String email;
    private final String country;
    private final List<String> favoriteGames;

    private Information(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.country = builder.country;
        this.favoriteGames = builder.favoriteGames;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public List<String> getFavoriteGames() {
        return favoriteGames;
    }


    public static class Builder {
        private String name;
        private String email;
        private String country;
        private List<String> favoriteGames = new ArrayList<>();

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setFavoriteGames(List<String> favoriteGames) {
            this.favoriteGames = favoriteGames;
            return this;
        }

        public Builder addFavoriteGame(String game) {
            this.favoriteGames.add(game);
            return this;
        }

        public Information build() {
            return new Information(this);
        }
    }
}

