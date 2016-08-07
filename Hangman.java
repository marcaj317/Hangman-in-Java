/*
 * Marc Abou Jaoude
 * Hangman Game
 * 6/17/2013
 */
package hangman;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.Graphics;

public class Hangman extends JPanel{
    JTextField input = new JTextField(""); //input text box
    JLabel prompt = new JLabel("Enter a Letter:"); //prompt the user
    JButton ok = new JButton("OK"); //ok button
    JButton exit = new JButton("Exit"); //exit button
    JButton BtnnewGame = new JButton("New Game"); //new game button
    JButton Rules = new JButton("Rules"); //rules button
    JLabel usedLetters = new JLabel("");
    boolean isCorrect = false; //variable to check if the user is correct
    
    //available words
    String[] word = {
        "native", "country", "color", "example", "helper",
        "favorite", "charcoal", "smoke", "interest", "video",
        "language", "drink", "homework","shell", "sympathy",
        "define", "specify", "drawing", "picture", "frame",
        "nutshell", "polygon", "circle", "rectangle", "sphere",
        "sherry", "lotion", "shoes", "trowsers", "belt",
        "blouse", "nightgown", "cowboy", "engineer", "waiter",
        "wheel", "engine", "pedal", "street", "navigate",
        "sailing", "skiing", "outboard", "runner", "dancer",
        "hero", "helpless", "pseudonym", "lioness", "integrity",
        "computer", "laptop", "keyboard", "home", "guitar",
        "type", "player", "project", "graphics", "field"
    };
    
    int rnd = (int)(Math.random() * word.length - 1); //random number for the array
    ArrayList<String> correct = new ArrayList(); //when user is correct
    JPanel DrawHanger = new Hangman.hanger(); //panel to draw hanger
    JPanel DrawWords = new Hangman.Drawing(); //panel to draw the words
    int NumOfGuesses = 0; //the number of guesses
    JPanel guesser = new Hangman.gui(); //guesses
    
    Hangman(){       
        super(true);
        DrawWords.setPreferredSize(new Dimension(word[rnd].length()*10,40)); //set the size
        setLayout(new GridLayout(0,1)); //set the layout
        add(DrawHanger); //add the hanger panel
        add(DrawWords); //add the words panel
        add(guesser); //add the guesses panel
    }
    
    class Drawing extends JPanel{
        @Override
        public void paintComponent(Graphics DrawArea){
            super.paintComponent(DrawArea);
            int area = 100 / (word[rnd].length() * 10); //assign the area a value according to the word
            
            //draw area settings
            DrawArea.clearRect(0, 0, this.getPreferredSize().width,this.getPreferredSize().height); //clear everything
            DrawArea.setColor(Color.BLACK); //set the color to black
            DrawArea.drawString("",0,0);
            
            System.out.println(area);
            for(int i = 0; i < word[rnd].length(); i++){
                boolean show = false; //letters are not shown
                for(int j = 0; j < correct.size(); j++){
                    if(correct.get(j).equalsIgnoreCase(word[rnd].substring(i, i + 1))){ //if input is correct
                         show = true;//show letter
                    }                       
                }
                if(word[rnd].substring(i, i + 1).equals("")){ //if no input, do nothing
                    //nothing is done here
                }
                else{
                    if(show){ //if the letter is shown
                        DrawArea.drawString(word[rnd].substring(i, i + 1), i * 10, 20); //draw the letter
                    }else{
                        DrawArea.drawLine(i * 10, 20, (i + 1) * 10 - 5, 20); //draw line/spaces
                    }
                }
            }
        }
    } 
     //draw the hanger
     class hanger extends JPanel{
        hanger(){
            setPreferredSize(new Dimension(100,100));
        }
        @Override
        public void paintComponent(Graphics DrawArea){
            super.paintComponent(DrawArea);
            DrawArea.setColor(Color.BLUE); //set color to blue
                  
             //draw the hanger
            DrawArea.drawRect(10,70, 50, 29);
            DrawArea.drawLine(35, 70, 35, 5);
            DrawArea.drawLine(35,5,70,5);
            
            //draw the man
            if(NumOfGuesses > 0)
            {DrawArea.drawOval(60, 10, 20, 20);} //draw head
            if(NumOfGuesses >= 2)
            {DrawArea.drawLine(70, 30, 70, 60);} //draw body
            if(NumOfGuesses >= 3)
            {DrawArea.drawLine(70, 60, 50, 65);} //draw leg
            if(NumOfGuesses >= 4)
            {DrawArea.drawLine(70, 60, 90, 65);} //draw second leg
            if(NumOfGuesses >= 5)
            {DrawArea.drawLine(70, 30, 50, 25);} //draw arm
            if(NumOfGuesses >= 6){ //draw other arm, start new game
                DrawArea.drawLine(70, 30, 90, 25);
                JOptionPane.showMessageDialog(null, "You Lose! The word was " + word[rnd]);
                rnd = (int)(Math.random() * word.length - 1); //re-set word
                isCorrect = false; //user is incorrect
                input.setText(""); //clear text field
                correct.clear(); //clear corrct guesses
                NumOfGuesses = 0; //clear wrong guesses
                usedLetters.setText(""); //clear used letters
                DrawHanger.repaint(); //re-paint hanger
                DrawWords.repaint(); //re-paint words
            }
        }
    }

    
    //draw the words
   
    class gui extends JPanel{
        gui(){
            setLayout(new FlowLayout()); //set the layout
            add(prompt); //add the prompt
            
            //set the settings for the input text box
            input.setColumns(4); 
            input.setSize(5,5);
            add(input); //add the textbox
            
                       
            //rules
            Rules.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae){
                    //display the rules
                    JOptionPane.showMessageDialog(null, " Welcome to Hangman! \n Enter the letter which you think is correct "
                            + " in the provided text field and click 'OK'.\n You have 6 guesses to get the word right. \n Clicking 'New Game' will reset the word and guesses"
                            + "\n Note: if you enter more than one letter, only the first one will be counted as a guess. \n GOOD LUCK ! \n - Marc Abou Jaoude 2013");
                }
            });
            
            //to start a new game
            BtnnewGame.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae){
                    rnd = (int)(Math.random() * word.length - 1); //re-set word
                    isCorrect = false; //user is incorrect
                    input.setText(""); //clear text field
                    correct.clear(); //clear corrct guesses
                    NumOfGuesses = 0; //clear wrong guesses
                    usedLetters.setText(""); //clear used letters
                    DrawHanger.repaint(); //re-paint hanger
                    DrawWords.repaint(); //re-paint words
                }
            });
             
            //if the exit button is clicked...
            exit.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae){
                    System.exit(0); //exits system
                }
            });
            
            //if the OK button is clicked...
            ok.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                   try{
                       JLabel hello = new JLabel(input.getText(0, 1)); //create new label for used letters
                       add(hello); //display the letters
                       DrawHanger.repaint(); //re-draw the hanger
                       DrawWords.repaint(); //re-draw words
                       String letter = input.getText(0, 1); //get the input
                       for(int i = 0; i < word[rnd].length(); i++){
                           if(letter.equalsIgnoreCase(word[rnd].substring(i, i + 1))){ //if input is in the word
                                isCorrect = true; //user is correct 
                            }
                       }
                        if(isCorrect){ //if the user is incorrect
                             for(int i = 0; i < correct.size(); i++){ //loop
                                if(correct.get(i).equalsIgnoreCase(letter)) //if input is already used
                                {isCorrect = false;} //user is incorrect
                            }
                            if(isCorrect)//if user is correct
                            {correct.add(letter);} //add the input 
                        }
                        else{
                            NumOfGuesses++; //add 1 to guesses
                        }
                        if(isCorrect = false){//if the user is not correct
                            NumOfGuesses++; //add 1 to guesses
                        }
                        usedLetters.setText(letter);
                        add(usedLetters);
                   }
                   catch(Exception ex){}
                   
                }
                
            }); 
            add(ok); //add ok button
            add(BtnnewGame); //add new game button
            add(Rules); //add rules button
            add(exit); //add the exit button
        }
    }
    public static void main(String[] args){ //main
        JFrame frame = new JFrame(); //declare frame 
        frame.setVisible(true); 
        frame.setTitle("Marc Abou Jaoude - Hangman Game"); //title
        frame.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit on close
        frame.add(new Hangman()); //add the constructor
        frame.pack(); 
    }
}