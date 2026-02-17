package entities.users;

import entities.characters.Entity;
import entities.characters.Character;

import java.util.ArrayList;
import java.util.SortedSet;

public class Account {
    private Information information;
    private ArrayList<Character> characters;
    private int gamesNumber;

    public Account() {

    }

    public Account(ArrayList<Character> characters, int gamesNumber, Information information) {
        this.characters = characters;
        this.gamesNumber = gamesNumber;
        this.information = information;
    }

    public Information getInformation() {
        return information;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public int getGamesNumber() {
        return gamesNumber;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public void setGamesNumber(int gamesNumber) {
        this.gamesNumber = gamesNumber;
    }

    @Override
    public String toString() {
        String obj = "";

        obj += information;
        obj += "\n";
        obj += characters;
        obj += "\n";
        obj += "number of games: ";
        obj += gamesNumber;

        return obj;
    }

    public static class Information {
        private Credentials credentials;
        private SortedSet<String> favGames;
        private String name;
        private String country;

        public Information(Credentials credentials, SortedSet<String> favGames,
                           String name, String country) {
            this.credentials = credentials;
            this.favGames = favGames;
            this.name = name;
            this.country = country;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public SortedSet<String> getFavGames() {
            return favGames;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        @Override
        public String toString() {
            String obj = "";

            obj += "CREDENTIALS: ";
            obj += credentials;
            obj += "\n";
            obj += "FAV GAMES: ";
            obj += favGames;
            obj += "\n";
            obj += "NAME: ";
            obj += name;
            obj += "COUNTRY: ";
            obj += " ";
            obj += country;

            return obj;
        }
    }
}
