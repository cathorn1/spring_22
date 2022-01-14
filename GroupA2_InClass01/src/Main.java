/*
    InClass01
    Main.java
    Clifton Thorne
    Riley Halbert
 */

import java.sql.Array;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        //The following ArrayLists are used in the method tests.
        //Un-comment the functions below question number to test.
        ArrayList<User> userList1 = new ArrayList<>();
        userList1 = getParsedUsers(Data.users);
        ArrayList<User> userList2 = new ArrayList<>();
        userList2 = getParsedUsers(Data.otherUsers);

        //Q1
        //fizzBuzz();

        //Q2
        //isEven(2);

        //Q3
        //Integer[] arr ={4,6,23,5,1};
        //getMinimum(arr);

        //Q4 - function is implemented above ^
        //System.out.println(userList1);

        //Q5
        //printUsers_OMN(userList1);

        //Q6
        //printUsersSortedByAge(userList1);

        //Q7
        //printUsersOldest10(userList1);

        //Q8
        //printUserStateStats(userList1);

        //Q9
        //printUserStateStats(Data.words_1, Data.words_2);

        //Q10
        //printUserStateStats(userList1, userList2);
    }

    //Question 1
    public static void fizzBuzz(){
        for (int i = 1; i <=20; i++){
            if((i % 3 == 0) && (i%5==0)) {
                System.out.println("FizzBuzz");
            }
            else if(i % 3 == 0){
                System.out.println("Fizz");
            }
            else if(i % 5 == 0){
                System.out.println("Buzz");
            }
            else{
                System.out.println(i);
            }

        }
    }

    //Question 2
    public static boolean isEven(Integer num){
        boolean bool = true;

        if (num % 2 ==0){
            bool = true;
            System.out.println(bool);
            return bool;
        }
        else{
            bool = false;
            System.out.println(bool);
            return bool;
        }

    }

    //Question 3
    public static Integer getMinimum(Integer[] numbers){
        int lowest = Integer.MAX_VALUE;
        if (numbers == null){
            return null;
        }
        else{
            for (int i = 0; i < numbers.length; i++){
                if (numbers[i] < lowest){
                    lowest = numbers[i];
                }
            }
        }
        System.out.println("lowest = " + lowest);
        return lowest;

    }


    //Question 4
    public static ArrayList<User> getParsedUsers(String[] strings){

        ArrayList<User> userObjects = new ArrayList<User>();

        for (int i = 0; i < strings.length; i++) {
            String[] userArray = strings[i].split(",");
            User userObj = new User(userArray);
            userObjects.add(userObj);
        }
        return userObjects;
    }

    //Question 5
    public static void printUsers_OMN(ArrayList<User> users){
        for (User user: users){
            if(user.firstname.charAt(0) == 'O' || user.firstname.charAt(0) == 'M' || user.firstname.charAt(0) == 'N'){
                System.out.println(user.firstname);
            }
        }
    }


    //Question 6
    public static void printUsersSortedByAge(ArrayList<User> users){

        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.age - o2.age;
            }
        });

        for(User user: users){
            System.out.println(user);
        }
    }

    //Question 7
    public static void printUsersOldest10(ArrayList<User> users){

        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.age - o2.age;
            }
        });

        for(int i = users.size() - 1; i > users.size()-11; i--){
            System.out.println(users.get(i));
        }

    }

    //Question 8
    public static void printUserStateStats(ArrayList<User> users){

        HashMap<String, Integer> stateMap = new HashMap<>();

        for (User user : users){
            String state = user.getState();
            if(stateMap.containsKey(state)) {
                stateMap.put(state, stateMap.get(state)+1);
            } else {
                stateMap.put(state, 1);
            }
        }

        System.out.println(stateMap);

    }

    //Question 9
    public static Set<String> printUserStateStats(String[] listA, String[] listB){
        Set<String> wordSet = new HashSet<>();
        Set<String> setA = new HashSet<>();
        Set<String> setB = new HashSet<>();

        for(String word:listA){
            setA.add(word);
        }

        for(String word: listB){
            setB.add(word);
        }

        for(String word : setA){
            if(setB.contains(word)){
                wordSet.add(word);
            }
        }

        System.out.println(wordSet);
        return wordSet;
    }

    //Question 10
    public static ArrayList<User> printUserStateStats(ArrayList<User> usersA, ArrayList<User> usersB){

        ArrayList<User> userSet = new ArrayList<>(usersA);

        userSet.retainAll(usersB);

        for(User user: userSet){
            System.out.println(user);
        }

       return userSet;
    }

}


