package fall2018.csc2017.GameCentre;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Scores {

    static final int name = 0;
    static final int scores = 1;

    private ArrayList<String> accountNames;
    private ArrayList<String> accountScores;
    private UserAccManager userAccManager;


    /**
     * Construct all the scores for a specific game.
     */
    public Scores(String game, Context context){
        this.userAccManager = (UserAccManager) FileSaver.loadFromFile(context, LoginActivity.ACC_INFO);
        ArrayList<UserAccount> accList = sortingAccountsScores(game, loadAllAccountInfo());
        List<ArrayList<String>> names_and_scores = accountsGetNamesScores(accList, game);
        accountNames = names_and_scores.get(name);
        accountScores = names_and_scores.get(scores);
    }

    /**
     * Return the accountNames
     * @return An array list of all account names that have played  and won the game
     */
    public ArrayList<String> getAccountNames(){return accountNames;}

    /**
     * Return the accountScores
     * @return An array list of all the scores of the accounts in accountNames
     */
    public ArrayList<String> getAccountScores(){return accountScores;}


    /**
     * Load all the account information
     *
     * @return an array list of all user accounts
     */
    ArrayList<UserAccount> loadAllAccountInfo(){
        HashMap<String, UserAccount> map = userAccManager.getAccountMap();
        ArrayList<UserAccount> accountList = new ArrayList<>(map.size());
        for(String name: map.keySet()){
            accountList.add(map.get(name));
        }
        return accountList;
    }

    /**
     * Get user names and their scores in descending order.
     *
     * @param accounts the ArrayList of UserAccount
     * @param gametype the type of the game
     *
     * @return an List of 2 ArrayLists, one representing the name and one representing the scores
     */
    List<ArrayList<String>> accountsGetNamesScores(ArrayList<UserAccount> accounts,
                                                   String gametype){
        ArrayList<String> names = new ArrayList<>(accounts.size());
        ArrayList<String> scores = new ArrayList<>(accounts.size());
        for(UserAccount acc: accounts){
            names.add(acc.getName());
            scores.add(acc.getScores(gametype).toString());
        }
        List<ArrayList<String>> result = new ArrayList<>(2);
        result.add(names);
        result.add(scores);
        return result;
    }

    /**
     * Return a sorted array list of accounts based on the scores from
     * lowest to highest of a particular game.
     *
     * @param game the name of the game.
     * @param accounts all the accounts of GameCenter
     * @return The sorted list of accounts based on their scores of the game
     */
    ArrayList<UserAccount> sortingAccountsScores(String game, ArrayList<UserAccount> accounts){
        ArrayList<UserAccount> players = playersOfGame(game, accounts);
        ArrayList<UserAccount> ranks = new ArrayList<>(players.size());
        ArrayList<Integer> playersScoresSorted = new ArrayList<>(players.size());
        //get a sorted list of player's scores
        for(UserAccount acc: players){
            playersScoresSorted.add(acc.getScores().get(game));
        }
        Collections.sort(playersScoresSorted, Collections.<Integer>reverseOrder());
        for(Integer score: playersScoresSorted){
            for(UserAccount player: players){
                if(player.getScores().get(game).equals(score) && !ranks.contains(player)){
                    ranks.add(player);
                }
            }
        }
        return ranks;

    }

    /**
     * Return a ArrayList of UserAccount that have played the game
     * @param game the game type
     * @param accounts all the accounts of GameCenter
     * @return An array list of accounts that have played this game.
     */
    ArrayList<UserAccount> playersOfGame(String game, ArrayList<UserAccount> accounts){
        ArrayList<UserAccount> players = new ArrayList<>(accounts.size());
        for(UserAccount acc: accounts){
            if(acc.getScores().get(game) != -1){
                players.add(acc);
            }
        }
        return players;
    }



}
