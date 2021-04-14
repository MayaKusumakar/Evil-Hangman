import java.io.*;
import java.util.*;

public class EvilHangman //maya
{  
   int guessesUsed;
   int wordLength;
   int numGuesses;
   boolean show;
   String word;
   ArrayList<String> wordsRemaining;
   
   public EvilHangman(int w, int n, boolean b, ArrayList<String> al) throws IOException{
      guessesUsed = 0;
      word = "";
      wordLength = w;
      numGuesses = n;
      show = b;
      wordsRemaining = al;
      for(int i = 0; i < wordLength; i++){
         word += "-";
      }
      
      Map<String, Integer> map = new TreeMap<>();
      readInData("dictionary.txt", map);
      
      for(String s : map.keySet()){
         if(map.get(s) == wordLength){
            wordsRemaining.add(s);
         }
      }

   }
   public int getGuessesUsed(){
      return guessesUsed;
   }
   
   public ArrayList<String> getWordsRemaining(){
      return wordsRemaining;
   }
   
   public String getWord(){
      return word;
   }
   
   
   public int getWordLength(){
      return wordLength;
   }
   
   public int getNumGuesses(){
      return numGuesses;
   }
   
   public boolean getShow(){
      return show;
   }
   
   public void updateDisplay(char letter, ArrayList<String> lg){//maya and cole
      
      
      if(updateWord(letter)){
         System.out.println(letter + " is in the word!");
      }
      else{
      guessesUsed++;
      System.out.println("Sorry, that letter is not in the word");
      }
      System.out.println();
      System.out.println(word);
      
      int guessesRemaining = numGuesses-guessesUsed;
      System.out.println();
      System.out.println("you have " + guessesRemaining + " guesses remaining");
      System.out.println("Letters Guessed: " + lg.toString());
      if(show){
         System.out.println("Number of words left: " + wordsRemaining.size());
      }
      
      
   }   
   public String getFamily(String word, char c)//maya
   {
          String family = "";
          ArrayList<Integer> locsOfLetter = findLocation(c, word);
          
          for(int i = 0; i < word.length(); i++){
            if(locsOfLetter.contains(i)){
               family = family + c + "";
            } 
            else{
               family = family + "-";
            }
          }
       
       return family;
    
   }	
   
	public Map<String, ArrayList<String>> sortWordsToFamily(char c){//maya and cole
      Map<String, ArrayList<String>> map = new TreeMap<String, ArrayList<String>>();
      
      for(String s : wordsRemaining){
         String fam = getFamily(s, c);
         if(map.containsKey(fam)){
            ArrayList<String> al = map.get(fam);
            al.add(s);
            map.put(fam, al);
         } else{
            ArrayList<String> al = new ArrayList<String>();
            al.add(s);
            map.put(fam, al);
         }
      }
      
      return map;
      
   }
   
   public boolean updateWord(char c){//maya
      Map<String, ArrayList<String>> map = sortWordsToFamily(c);
      String key = "";
      int max = 0;
      for(String s : map.keySet()){
         int l = map.get(s).size();
         if(l > max){
            key = s;
            max = l;
         }
      }
      
      wordsRemaining = map.get(key);
      
     
     
    
     
      
      int length =word.length();
      String w = word;
      word = "";
      for(int i = 0; i < length; i++){
         String keyLetter = key.substring(i,i+1);
         String wordLetter = w.substring(i,i+1);
         if(!keyLetter.equals(wordLetter)){
            if(keyLetter.equals("-")){
               word+=wordLetter;
            }
            else if(wordLetter.equals("-")){
               word+=keyLetter;
            }
            else {
               System.out.println("error at find biggest Fam, updating word");
            }
         } else{
            word+= "-";
         }
         
      }
      
      if(word.equals(w)){
         return false;
      }
      return true;
    
      }
            
   
   
   public ArrayList<Integer> findLocation(char letter, String word){//maya and cole
      int count = 0; 
      ArrayList<Integer> listOfLocs = new ArrayList<Integer>();
      
      for(char c : word.toCharArray()){
         if(letter == c){
            listOfLocs.add(count);
         }
         count++;
      }
      return listOfLocs;
   }
   public static void main(String[] args)throws IOException//maya and cole
   {
      boolean show = false;
      ArrayList<String> list = new ArrayList<>();
      ArrayList<String> lettersGuessed = new ArrayList<String>();
      Scanner input = new Scanner(System.in);
      
      int wordLength=-1;
      while(wordLength==-1){
         System.out.println("Number of letters in the word you would like"); //maya and cole
         int len=input.nextInt(); 
         if(len<30&&len!=27&&len!=26){
            wordLength=len;
         }
      }
      int guesses =-1;
      while(guesses ==-1){
         System.out.println("Number of guesses you would like"); //maya and cole
         int leng=input.nextInt(); 
         if(leng<27){
            guesses=leng;
         }
      }
      
      //Maya
      input.nextLine();
      System.out.println("Do you want a running total of the number of words remaining? Y or N");
      String ans = input.nextLine();
      if(ans.equals("Y")){
         show = true;
      }
      
      EvilHangman ev = new EvilHangman(wordLength, guesses, show, list);
      
      
      
      //maya
      while(true){
         
         System.out.println("Please enter a letter");
         String letter = input.nextLine();
         while(lettersGuessed.contains(letter)){
            System.out.println("You have already guessed that letter, please enter a different letter");
            letter = input.nextLine();
         }
         lettersGuessed.add(letter);
         
         
         ev.updateDisplay(letter.charAt(0), lettersGuessed);
         
         if(ev.getGuessesUsed() == ev.getNumGuesses())
         {
            System.out.println("Sorry, you have used all of your guesses.");
            int rand = (int)(Math.random()*ev.getWordsRemaining().size());
            System.out.println("The word was: " + ev.getWordsRemaining().get(rand));
            System.out.println("Do you want to play again? \"yes\" or \"no\"");
            String pa = input.nextLine();
            if(pa.equals("yes")){
               lettersGuessed = new ArrayList<String>();
               main(args);
            }
            else {
               System.exit(0);
            }
         }
         
         if(ev.getWord().indexOf("-") == -1){
            System.out.println("You guessed the word: " + ev.getWord() + ", Congratulations!");
            System.out.println("Do you want to play again? \"yes\" or \"no\"");
            String pa = input.nextLine();
            if(pa.equals("yes")){
               lettersGuessed = new ArrayList<String>();
               main(args);
            }
            else {
               System.exit(0);
            }
         }

            
            
         
         
      }
      
   }
   
   //Maya
   public void readInData(String fileName, Map<String, Integer> map) throws IOException{
      Scanner scan = new Scanner(new File(fileName));
      while(scan.hasNext()){
         String line = scan.nextLine();
         map.put(line, line.length());
      } 
   }
   
   }
