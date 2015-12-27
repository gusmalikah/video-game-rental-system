/*
Hamza Usmani

Description: This program is created as a solution to the culminating activity challenge. It is a basic inventory purchasing system for a video game store.
The program allows users to search through a (small) catalogue, add and delete games to their cart, with varying quantities. The program displays a total with tax, and quits.
Users also have the option for convenience to save their shopping cart, and load it at a later time if needed.
The majority of this program relies on array use, looping, searching and sorting, as well as a Graphics-based interface.
*/

//importing all the neccessary libraries
import javax.swing.*;
import java.applet.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.io.*;


public class vidgame extends Applet implements MouseListener
{ //global variables delcared
    String games[] = {"Spiderman", "Deadpool", "The Last of Us", "Splinter Cell", "Call of Duty Black Ops", "Battlefield 3", "Battlefield 4", "NHL 13", "FIFA 13", "Far Cry", "Need for Speed Carbon", "Gran Tourismo 5", "Grand Theft Auto 4", "NFL 13", "NBA 2K13"};
    String games2[] = {"Spiderman", "Deadpool", "The Last of Us", "Splinter Cell", "Call of Duty Black Ops", "Battlefield 3", "Battlefield 4", "NHL 13", "FIFA 13", "Far Cry", "Need for Speed Carbon", "Gran Tourismo 5", "Grand Theft Auto 4", "NFL 13", "NBA 2K13"};
    //all global arrays declared
    String bought[] = new String [15];

    double prices[] = {29.99, 40, 25.99, 39.99, 35, 45.99, 50, 40, 69.99, 69.99, 59.99, 55, 50, 59.50, 79.99};
    double prices2[] = {29.99, 40, 25.99, 39.99, 35, 45.99, 50, 40, 69.99, 69.99, 59.99, 55, 50, 59.50, 79.99};

    int quantity[] = new int [15];
    //global images, graphic, integers, doubles, Strings and decimal format delcared
    Image title, toolbar, shopper, cart, search, savescreen, loadscreen;
    Graphics g;
    int screen = 0, sorting = 1, x, y, num2, h, counter = 0;
    String name, num;
    double total;
    DecimalFormat df = new DecimalFormat ("##.00");

    public void init ()  //start of init method
    {
 resize (660, 490); //resize screen
 setBackground (Color.white);
 //initialize images and graphics
 title = getImage (getDocumentBase (), "title.jpg"); //initialize all images
 toolbar = getImage (getDocumentBase (), "bar.jpg");
 shopper = getImage (getDocumentBase (), "shopper.jpg");
 cart = getImage (getDocumentBase (), "cart.jpg");
 savescreen = getImage (getDocumentBase (), "save.jpg");
 loadscreen = getImage (getDocumentBase (), "load.jpg");
 addMouseListener (this); //add mouse listener to get mouse input
 g = getGraphics ();
    }


    public void mouseClicked (MouseEvent e)  //these methods are needed for mousePressed method to work
    {
    }


    public void mouseExited (MouseEvent e)
    {
    }


    public void mouseReleased (MouseEvent e)
    {
    }


    public void mouseEntered (MouseEvent e)
    {
    }


    public void mousePressed (MouseEvent e)  //mouse pressed method
    {
 //get the x and y co-ordinates of the mouse
 x = e.getX ();
 y = e.getY ();

 if (x > 174 && y > 449 && x < 301 && y < 486) //if catalogue is clicked
     screen = 1;


 if (x > 9 && y > 449 && x < 136 && y < 485) //if menu is clicked
     screen = 0;

 if (x > 342 && y > 450 && x < 471 && y < 485) //if save cart is clicked
     screen = 4;

 if (x > 512 && y > 450 && x < 641 && y < 485) //if load cart is clicked
     screen = 5;

 if (screen == 1) //catalogue screen
 {
     if (x > 516 && y > 134 && x < 648 && y < 186) //if buy game is clicked
  buy ();

     if (x > 516 && y > 231 && x < 648 && y < 300) //if go to cart is clicked
  screen = 2;

     if (x > 516 && y > 327 && x < 648 && y < 373) //if sorting is clicked
     {
  Object[] options = {"YES", "NO"}; //confirm the user has nothing in cart
  int reply = JOptionPane.showOptionDialog (null, "Please do not sort if you have already added item(s) to your cart. Have you?", "Do you have anything in your cart", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options [0]);

  if (reply == JOptionPane.YES_OPTION) //if they do have something in cart
      JOptionPane.showMessageDialog (null, "Do not sort, or your cart will be emptied", "Complete purchase first", JOptionPane.INFORMATION_MESSAGE);

  else if (reply == JOptionPane.NO_OPTION) //if they don't have anything in cart, and are allowed to sort
      sort ();
     }
 }

 if (screen == 2) //shopping cart screen
 {
     if (x > 516 && y > 134 && x < 648 && y < 187) //if use clicks complete transaction
     {
  if (total != 0) //if user has something worth anything above zero in their cart
  {
      JOptionPane.showMessageDialog (null, "Your total is $" + df.format (total) + "\nYour purchase receipt is ready.\nPlease proceed to pay the cashier\nExiting program and resetting cart.", "Thank you and have a good day", JOptionPane.INFORMATION_MESSAGE);
      System.exit (0);
  }
  else //if the user has nothing in cart
      JOptionPane.showMessageDialog (null, "You have not purchased anything!", "No items in cart", JOptionPane.ERROR_MESSAGE);
     }

     if (x > 516 && y > 304 && x < 648 && y < 357) //If the user clicks modify cart
     {
  String[] possibleValues = {"Add a new game", "Delete a game", "Change quantity of a game"}; //ask what they want to do
  String reply = (String) JOptionPane.showInputDialog (null, "What would you like to do?", "Please Choose", JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues [0]);

  if (reply.equals ("Add a new game")) //add a game
      buy ();

  else if (reply.equals ("Delete a game")) //delete game
      delete ();

  else if (reply.equals ("Change quantity of a game")) //change quantity
      change ();
     }
 }

 if (screen == 4) //saving cart
 {
     if (x > 262 && y > 157 && x < 395 && y < 203)   //press save
     {
  try //saving game to text file
  {
      PrintWriter fileoutput;
      fileoutput = new PrintWriter (new FileWriter ("games.txt"));

      for (int i = 0 ; i < bought.length ; i++)
      {
   if (bought [i] == games [i])
   {
       counter++; //counter to be used in loading
   }
      }
      fileoutput.println (counter); //print out counter in text file

      for (int i = 0 ; i < bought.length ; i++)
      {
   if (bought [i] == games [i]) //if cart items match any item in catalogue
   {
       fileoutput.println (bought [i]); //print them out, and their cart quantities
       fileoutput.println (quantity [i]);
   }
      }
      fileoutput.close ();    
  }
  catch (java.io.IOException w)  //catch the exception
  {
  }

  JOptionPane.showMessageDialog (null, "Your current shopping cart has successfully been saved.", "Successful!", JOptionPane.INFORMATION_MESSAGE); //display confirmation
     }

     if (x > 265 && y > 225 && x < 395 && y < 272) //if user clicks go to shopping cart
  screen = 2;
 }


 if (screen == 5) //if user presses load cart
 {
     if (x > 262 && y > 157 && x < 395 && y < 203) //press load
     {
  try //load the saved text file
  {
      String line, line1, line2;
      BufferedReader fileinput;
      fileinput = new BufferedReader (new FileReader ("games.txt"));
      line = fileinput.readLine ();
      counter = Integer.valueOf (line).intValue (); //only search until array is full


      for (int i = 0 ; i < counter ; i++)
      {
   line1 = fileinput.readLine ();
   bought [i] = line1;
   line2 = fileinput.readLine ();
   quantity [i] = Integer.valueOf (line2).intValue ();
      }
      fileinput.close ();
  }

  catch (java.io.IOException w)  //catch exception
  {
  }
  JOptionPane.showMessageDialog (null, "Your previous shopping cart has successfully been loaded.", "Successful!", JOptionPane.INFORMATION_MESSAGE); //display confirmation
     }

     if (x > 265 && y > 225 && x < 395 && y < 272) //if user clicks go to shopping cart
  screen = 2;
 }

 repaint (); //repaint graphics
    }


    public void sort ()  //sort method which dictates what type of sort method will be called
    {

 Object[] options = {"BY NAME", "BY PRICE"};
 int reply = JOptionPane.showOptionDialog (null, "How will you sort the game catalogue (ACSENDING)", "SORT GAMES", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options [0]);

 if (reply == JOptionPane.YES_OPTION) //Sort alphabetically
 {
     alpha ();
 }
 else if (reply == JOptionPane.NO_OPTION) //sort by price
     numerical ();
    }


    public void alpha ()  //sort alphabetically (bubble sort)
    {
 sorting = 0;
 int j;
 boolean flag = true;  // will determine when the sort is finished
 String temp;
 double temp2;

 while (flag) //bubble sort below
 {
     flag = false; //flag variable used to stop/start sort
     for (j = 0 ; j < games.length - 1 ; j++)
     {
  if (games [j].compareToIgnoreCase (games [j + 1]) > 0)
  { // ascending sort
      temp = games [j];
      temp2 = prices [j];

      games [j] = games [j + 1]; // swapping
      prices [j] = prices [j + 1];

      games [j + 1] = temp;
      prices [j + 1] = temp2;
      flag = true;
  }
     }
 }
    }


    public void numerical ()  //sorting by price
    {

 sorting = 1; //selection sort
 int i, j, smallest;
 double temp;
 String temp2;
 for (i = 0 ; i < prices2.length ; i++) //selection sort
 {
     smallest = i;
     for (j = i ; j < prices2.length ; j++)
     {
  if (prices2 [j] < prices2 [smallest])
      smallest = j;
     }
     temp = prices2 [i]; //store in temporary variable
     temp2 = games2 [i];

     prices2 [i] = prices2 [smallest];
     games2 [i] = games2 [smallest];

     prices2 [smallest] = temp; //swap back
     games2 [smallest] = temp2;
 }

    }


    public void buy ()  //if user chooses to buy a game
    {
 name = JOptionPane.showInputDialog ("Enter the name of the game you wish to add to your cart from the catalogue (exactly as spelt in the catalogue, and cAsE sensitive) ");

 int index = -1; // not found
 for (h = 0 ; h < games.length ; h++) //sequential search
 {
     if (name.equals (bought [h])) //if game is already in cart
     {
  index = -2;
  break;
     }
     if (games [h].equals (name)) //if game is found in catalogue, and is not in cart already
     {
  index = h;
  bought [h] = games [h];
  break; // stop looking
     }
 }
 if (index == -2) //if they have already added the game
 {
     JOptionPane.showMessageDialog (null, "You have already added this game to your cart", "Error", JOptionPane.ERROR_MESSAGE);
 }
 else if (index > 0) //it has been found and can be added
 {
     num = JOptionPane.showInputDialog ("How many copies of " + name + " will you buy?");
     num2 = Integer.parseInt (num);
     quantity [h] = num2;
 }
 else if (index == -1) //cannot find game
 {
     JOptionPane.showMessageDialog (null, "Game Not Found", "Error", JOptionPane.ERROR_MESSAGE);
 }
    }


    public void delete ()  //method when user chooses to delete a game from cart
    {
 String name2 = JOptionPane.showInputDialog ("Enter the name of the game you wish to delete from your cart (exactly as spelt in your cart) ");

 for (h = 0 ; h < bought.length ; h++) //sequential search
 {
     if (name2.equals (bought [h])) //check the user input with cart, if found then:
     {
  bought [h] = null; //delete the item in the cart
  JOptionPane.showMessageDialog (null, "Item successfully deleted", "Deleted!", JOptionPane.INFORMATION_MESSAGE); //dispay confirmation
  break; //stop loop
     }

 }
    }


    public void change ()  //if user chooses to change gae quantity in the cart
    {
 String name3 = JOptionPane.showInputDialog ("Enter the name of the game whose quantity you wish to change (exactly as spelt in your cart) ");
 for (h = 0 ; h < bought.length ; h++) //sequential search
 {
     if (name3.equals (bought [h])) //if game is found in cart
     {
  String amount = JOptionPane.showInputDialog ("There are currently " + quantity [h] + " copies of " + bought [h] + " in your cart.\nWhat quantity would you like to change this to?");
  int amount2 = Integer.valueOf (amount).intValue ();
  quantity [h] = amount2; //change quantity to user's new desired quantity
  JOptionPane.showMessageDialog (null, "Quantity successfully modified", "Modified!", JOptionPane.INFORMATION_MESSAGE);
  break; //stop loop
     }
 }
    }


    public void update (Graphics g)
    { //Overide the regular update method so it doesn't clear the screen before it draws (Gets rid of annoying white flicker)
 paint (g);
    }


    public void paint (Graphics g)  //Method where screens/images are actually painted
    {
 g.drawImage (toolbar, 0, 445, this);
 showStatus ("Copyright Hamza Usmani");

 if (screen == 0) //menu screen
 {
     g.drawImage (title, 0, 0, this); //draw title
 }

 if (screen == 1)  //catalogue screen
 {
     g.drawImage (shopper, 0, 0, this); //draw shopping screen
     int a = 105, a2 = 378, b = 68;
     g.setFont (new Font ("Arial", Font.PLAIN, 16)); //set font

     if (sorting == 0)
     {
  for (int i = 0 ; i < games.length ; i++) //loop to print out names and prices
  {
      g.drawString (games [i] + "", a, b);
      g.drawString ("$" + df.format (prices [i]), a2, b);
      b += 26; //add to vertical value
  }
     }

     else if (sorting == 1)
     {
  for (int i = 0 ; i < games2.length ; i++) //loop to print out names and prices
  {
      g.drawString (games2 [i] + "", a, b);
      g.drawString ("$" + df.format (prices2 [i]), a2, b);
      b += 26; //add to vertical value
  }
     }
 }


 if (screen == 2) //Cart screen
 {
     g.drawImage (cart, 0, 0, this); //draw cart screen
     g.setFont (new Font ("Arial", Font.PLAIN, 16)); //set font
     double subtotal = 0, tax; //declare subtotal, tax variables
     int y2 = 62;

     for (int i = 0 ; i < games.length ; i++) //loop
     {
  if (bought [i] != null) //if game is found in catalogue equal to the one in cart
  {
      g.drawString (bought [i] + "", 111, y2); //print out game, quantity and price
      g.drawString (quantity [i] + "", 12, y2);
      g.drawString ("$" + prices [i], 377, y2);
      subtotal += ((quantity [i] * prices [i]));
      y2 += 22;
  }
     }

     tax = subtotal * 0.13; //calculate tax, subtotal, and total
     total = tax + subtotal;
     g.drawString (df.format (tax) + "$", 213, 390); //print out total and tax
     g.drawString (df.format (total) + "$", 170, 428);
 }

 if (screen == 4) //clicking save cart
     g.drawImage (savescreen, 0, 0, this);

 if (screen == 5) //clicking load cart
     g.drawImage (loadscreen, 0, 0, this);




    }
}


