package imageprocessing1;


/*
 *Hunter Lloyd
 * Copyrite.......I wrote, ask permission if you want to use it outside of class. 
 * Dominik J pruss
 * Brandon Busby
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.awt.image.PixelGrabber;
import java.awt.image.MemoryImageSource;


class IMP implements MouseListener{
    
    MyPanel redPanel;
    MyPanel greenPanel;
    MyPanel bluePanel;
   JFrame frame;
   JPanel mp;
   JButton start;
   JScrollPane scroll;
   JMenuItem openItem, exitItem, resetItem;
   Toolkit toolkit;
   File pic;
   ImageIcon img;
   int colorX, colorY;
   int [] pixels;
   int [] results;
   JLabel label;
   //Instance Fields you will be using below
   
   //This will be your height and width of your 2d array
   int height=0, width=0, originalHeight =0, originalWidth=0;
   
   //your 2D array of pixels
    int picture[][];

    /* 
     * In the Constructor I set up the GUI, the frame the menus. The open pulldown 
     * menu is how you will open an image to manipulate. 
     */
   IMP()
   {
      toolkit = Toolkit.getDefaultToolkit();
      frame = new JFrame("Image Processing Software by Hunter");
      JMenuBar bar = new JMenuBar();
      JMenu file = new JMenu("File");
      JMenu functions = getFunctions();
      frame.addWindowListener(new WindowAdapter(){
            @Override
              public void windowClosing(WindowEvent ev){quit();}
            });
      openItem = new JMenuItem("Open");
      openItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ handleOpen(); }
           });
      resetItem = new JMenuItem("Reset");
      resetItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ reset(); }
           });     
      exitItem = new JMenuItem("Exit");
      exitItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ quit(); }
           });
      file.add(openItem);
      file.add(resetItem);
      file.add(exitItem);
      bar.add(file);
      bar.add(functions);
      frame.setSize(600, 600);
      mp = new JPanel();
      mp.setBackground(new Color(0, 0, 0));
      scroll = new JScrollPane(mp);
      frame.getContentPane().add(scroll, BorderLayout.CENTER);
      JPanel butPanel = new JPanel();
      butPanel.setBackground(Color.black);
      start = new JButton("start");
      start.setEnabled(false);
      butPanel.add(start);
      frame.getContentPane().add(butPanel, BorderLayout.SOUTH);
      frame.setJMenuBar(bar);
      frame.setVisible(true);
      start.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){ redPanel.drawImage();greenPanel.drawImage();bluePanel.drawImage(); }
           });
          
          
      
      
   }
   
   /* 
    * This method creates the pulldown menu and sets up listeners to selection of the menu choices. If the listeners are activated they call the methods 
    * for handling the choice, fun1, fun2, fun3, fun4, etc. etc. 
    */
   
  private JMenu getFunctions()
  {
     JMenu fun = new JMenu("Functions");
     JMenuItem firstItem = new JMenuItem("Rotate 90");
     JMenuItem secondItem = new JMenuItem("GreyScale");
     JMenuItem thirdItem = new JMenuItem("Blur");
     JMenuItem fourthItem = new JMenuItem("Edge Detection");
     JMenuItem fifthItem = new JMenuItem("Color Detection");
     JMenuItem sixthItem = new JMenuItem("Color Histogram");
     
    
     firstItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){rotateNinety();}
           });
        
     secondItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){greyScale();}
           });
     
      thirdItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){blur();}
           });
      
       fourthItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){edgeDetect();}
           });
       fifthItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){colorDetect();}
           });
       sixthItem.addActionListener(new ActionListener(){
            @Override
          public void actionPerformed(ActionEvent evt){colorHistogram();}
           });

       
      
      fun.add(firstItem);
      fun.add(secondItem);
      fun.add(thirdItem);
      fun.add(fourthItem);
      fun.add(fifthItem);
      fun.add(sixthItem);
      
      return fun;   

  }
  
  /*
   * This method handles opening an image file, breaking down the picture to a one-dimensional array and then drawing the image on the frame. 
   * You don't need to worry about this method. 
   */
    private void handleOpen()
  {  
     img = new ImageIcon();
     JFileChooser chooser = new JFileChooser();
     int option = chooser.showOpenDialog(frame);
     if(option == JFileChooser.APPROVE_OPTION) {
       pic = chooser.getSelectedFile();
       img = new ImageIcon(pic.getPath());
      }
     width = img.getIconWidth();
     height = img.getIconHeight(); 
     originalWidth = width;
     originalHeight = height;
     label = new JLabel(img);
     label.addMouseListener(this);
     pixels = new int[width*height];
     
     results = new int[width*height];
  
          
     Image image = img.getImage();
        
     PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width );
     try{
         pg.grabPixels();
     }catch(InterruptedException e)
       {
          System.err.println("Interrupted waiting for pixels");
          return;
       }
     for(int i = 0; i<width*height; i++)
        results[i] = pixels[i];  
     turnTwoDimensional();
     mp.removeAll();
     mp.add(label);
     
     mp.revalidate();
     start.setEnabled(true);
  }
  
  /*
   * The libraries in Java give a one dimensional array of RGB values for an image, I thought a 2-Dimensional array would be more usefull to you
   * So this method changes the one dimensional array to a two-dimensional. 
   */
  private void turnTwoDimensional()
  {
     picture = new int[height][width];
     for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
          picture[i][j] = pixels[i*width+j];
      
     
  }
  /*
   *  This method takes the picture back to the original picture
   */
  private void reset()
  {
        for(int i = 0; i<width*height; i++)
             pixels[i] = results[i]; 
       Image img2 = toolkit.createImage(new MemoryImageSource(originalWidth, originalHeight, pixels, 0, originalWidth)); 
       label = new JLabel(new ImageIcon(img2));
       mp.removeAll();
       height = originalHeight;
       width = originalWidth;
       turnTwoDimensional();
       mp.removeAll();
       mp.add(label);
      
       mp.revalidate(); 
    }
  /*
   * This method is called to redraw the screen with the new image. 
   */
  private void resetPicture()
  {
  
       for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
          pixels[i*width+j] = picture[i][j];
       Image img2 = toolkit.createImage(new MemoryImageSource(width, height, pixels, 0, width)); 
   
       JLabel label2 = new JLabel(new ImageIcon(img2));    
       mp.removeAll();
       mp.add(label2);
       
       mp.revalidate(); 
   
    }
    /*
     * This method takes a single integer value and breaks it down doing bit manipulation to 4 individual int values for A, R, G, and B values
     */
  private int [] getPixelArray(int pixel)
  {
      int temp[] = new int[4];
      temp[0] = (pixel >> 24) & 0xff;
      temp[1]   = (pixel >> 16) & 0xff;
      temp[2] = (pixel >>  8) & 0xff;
      temp[3]  = (pixel      ) & 0xff;
      return temp;
      
    }
    /*
     * This method takes an array of size 4 and combines the first 8 bits of each to create one integer. 
     */
  private int getPixels(int rgb[])
  {
         int alpha = 0;
         int rgba = (rgb[0] << 24) | (rgb[1] <<16) | (rgb[2] << 8) | rgb[3];
        return rgba;
  }
  
  public void getValue()
  {
      int pix = picture[colorY][colorX];
      int temp[] = getPixelArray(pix);
      System.out.println("Color value " + temp[0] + " " + temp[1] + " "+ temp[2] + " " + temp[3]);
    }
  
  /**************************************************************************************************
   * This is where you will put your methods. Every method below is called when the corresponding pulldown menu is 
   * used. As long as you have a picture open first the when your fun1, fun2, fun....etc method is called you will 
   * have a 2D array called picture that is holding each pixel from your picture. 
   *************************************************************************************************/
   /*
    * Example function that just removes all red values from the picture. 
    * Each pixel value in picture[i][j] holds an integer value. You need to send that pixel to getPixelArray the method which will return a 4 element array 
    * that holds A,R,G,B values. Ignore [0], that's the Alpha channel which is transparency, we won't be using that, but you can on your own.
    * getPixelArray will breaks down your single int to 4 ints so you can manipulate the values for each level of R, G, B. 
    * After you make changes and do your calculations to your pixel values the getPixels method will put the 4 values in your ARGB array back into a single
    * integer value so you can give it back to the program and display the new picture. 
    */
  private void rotateNinety()
  {
     int rotateArray[][] = new int[width][height];
     
    for(int i=0; i<height; i++){
       for(int j=0; j<width; j++)
       {   
          
         rotateArray[j][height-i-1] = picture[i][j];
        
        } 
    }
     picture = rotateArray;
     int oldHeight = height;
     int oldWidth = width;
     height = oldWidth;
     width = oldHeight;
     mp.removeAll();
     resetPicture();
  }

  private void blur()
  {
     greyScale();
     int newblur[][] = new int[height][width];
     
    for(int i=1; i<height-1; i++)
       for(int j=1; j<width-1; j++)
       {   
           int rgbArray[] = new int[4];
           rgbArray = getPixelArray(picture[i][j]);
           
           int zero[], one[],two[],three[],four[],five[],six[],seven[];
           zero= getPixelArray(picture[i-1][j-1]);
           one= getPixelArray(picture[i-1][j]);
           two= getPixelArray(picture[i-1][j+1]); 
           three= getPixelArray(picture[i][j-1]); 
           four= getPixelArray(picture[i][j+1]); 
           five= getPixelArray(picture[i+1][j-1]);
           six= getPixelArray(picture[i+1][j]);
           seven= getPixelArray(picture[i+1][j+1]);
           
           rgbArray[1]= ((rgbArray[1]+zero[1]+one[1]+two[1]+three[1]+four[1]+five[1]+six[1]+seven[1])/9) ;
           rgbArray[2]= rgbArray[1];
           rgbArray[3]=rgbArray[1];
           
            newblur[i][j] = getPixels(rgbArray);
        } 
     picture= newblur;
     resetPicture();
  }

  private void greyScale()
  {
     
    for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
       {   
          int rgbArray[] = new int[4];
         
          //get three ints for R, G and B
          rgbArray = getPixelArray(picture[i][j]);
         int average;
         average = (int)(  (double)(rgbArray[2]*.72)+  (double)(rgbArray[1]*.21) +(double)(rgbArray[3]*.07)/3);
        rgbArray[1] =  average; // red
        rgbArray[2] =  average; // green
        rgbArray[3] =  average; // blue
        
           
           //take three ints for R, G, B and put them back into a single int
           picture[i][j] = getPixels(rgbArray);
        } 
     resetPicture();
  }

private void edgeDetect()
  {
     
    greyScale();
     int newedge[][] = new int[height][width];
     
    for(int i=2; i<height-2; i++)
       for(int j=2; j<width-2; j++)
       {   
           int rgbArray[] = new int[4];
           rgbArray = getPixelArray(picture[i][j]);
           
           int zero[], one[],two[],three[],four[],five[],six[],seven[], eight[],nine[],ten[],eleven[], twelve[], thirteen[], fourteen[], fifteen[];
           zero= getPixelArray(picture[i-2][j-2]);
           one= getPixelArray(picture[i-2][j-1]);
           two= getPixelArray(picture[i-2][j]); 
           three= getPixelArray(picture[i-2][j+1]); 
           four= getPixelArray(picture[i-2][j+2]); 
           five= getPixelArray(picture[i-1][j+2]);
           six= getPixelArray(picture[i][j+2]);
           seven= getPixelArray(picture[i+1][j+2]);
           eight= getPixelArray(picture[i+2][j+2]);
           nine= getPixelArray(picture[i+2][j+1]);
           ten= getPixelArray(picture[i+2][j]);
           eleven= getPixelArray(picture[i+2][j-1]);
           twelve= getPixelArray(picture[i+2][j-2]);
           thirteen= getPixelArray(picture[i+1][j-2]);
           fourteen= getPixelArray(picture[i][j-2]);
           fifteen= getPixelArray(picture[i-1][j-2]);
           
           rgbArray[1]= (((-16*rgbArray[1])+zero[1]+one[1]+two[1]+three[1]+four[1]+five[1]+six[1]+seven[1]+eight[1]+nine[1]+ten[1]+eleven[1]+twelve[1]+thirteen[1]+fourteen[1]+fifteen[1])/17) ;
           rgbArray[2]= rgbArray[1];
           rgbArray[3]= rgbArray[1];
           
            newedge[i][j] = getPixels(rgbArray);
        } 
     picture= newedge;
     resetPicture();
  }
private void colorHistogram()
  {

      int redArray[]= new int[256];
      int greenArray[]= new int[256];
      int blueArray[]= new int[256];
      int redMin, redMax, greenMin, greenMax, blueMin, blueMax;
      redMin=0;
      redMax=255;
      greenMin=0;
      greenMax =255;
      blueMin=0;
      blueMax=255;
  
      
      
    for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
       {   
          int rgbArray[] = new int[4];
         
          //get three ints for R, G and B
          rgbArray = getPixelArray(picture[i][j]);
          // this will 
          ++redArray[rgbArray[1]];
          ++greenArray[rgbArray[2]];
          ++blueArray[rgbArray[3]];
          // this will count the number of frames everywhere
           } 
    // normalization  starts here
   for(int i =0; i<256;i++){
       if(redMax == 255  && redArray[i] !=0 ){
           redMax = redArray[i]; // this will be looking for the max
       }
       if(greenMax == 255  && greenArray[i] !=0 ){
           greenMax = greenArray[i]; // this will be looking for the max
       }
       if(blueMax == 255  && blueArray[i] !=0 ){
           blueMax = blueArray[i]; // this will be looking for the max
       }
   }
   //we now should have the min and max values for all colors so we can begin normalizing
   // first step is to see how big of a factor we have to scale the colors (if the color range is 40, we don't need all 256 range
   
   int redFactor,greenFactor,blueFactor;
   redFactor = (600/redMax)+1;
   greenFactor =(600/greenMax)+1;
   blueFactor = (600/blueMax)+1;
      
       
       
     
           
       
   
   
 
  JPanel red = new JPanel();
  JPanel green = new JPanel();
  JPanel blue = new JPanel();
   
  JFrame redFrame = new JFrame("Red");
  redFrame.setSize(305, 600);
  redFrame.setLocation(800, 0);
  JFrame greenFrame = new JFrame("Green");
  greenFrame.setSize(305, 600);
  greenFrame.setLocation(1100, 0);
  JFrame blueFrame = new JFrame("blue");
  blueFrame.setSize(305, 600);
  blueFrame.setLocation(1400, 0);
  redPanel = new MyPanel(redArray,redFactor);
  greenPanel = new MyPanel(greenArray,greenFactor);
  bluePanel = new MyPanel(blueArray,blueFactor);
  redFrame.getContentPane().add(redPanel, BorderLayout.CENTER);
  redFrame.setVisible(true);
  greenFrame.getContentPane().add(greenPanel, BorderLayout.CENTER);
  greenFrame.setVisible(true);
  blueFrame.getContentPane().add(bluePanel, BorderLayout.CENTER);
  blueFrame.setVisible(true);
  
   
    
    
   
  }
  public void colorDetect()
  {
      for(int i=0; i<height; i++)
       for(int j=0; j<width; j++)
       {   
          
          int rgbArray[] = new int[4];
          rgbArray = getPixelArray(picture[i][j]);
         //255,165,0 for orange
         
         if(rgbArray[1] < 130 || (rgbArray[2] < 75 || rgbArray[2] > 200) || (rgbArray[3] > 55) )
         {
             
            rgbArray[1] = 0;
            rgbArray[2] = 0;
            rgbArray[3] = 0;
             
         }
         
         
         
         picture[i][j] = getPixels(rgbArray);
         
        } 
     resetPicture();
  }
 
  
 
  private void quit()
  {  
     System.exit(0);
  }

    @Override
   public void mouseEntered(MouseEvent m){}
    @Override
   public void mouseExited(MouseEvent m){}
    @Override
   public void mouseClicked(MouseEvent m){
        colorX = m.getX();
        colorY = m.getY();
        System.out.println(colorX + "  " + colorY);
        getValue();
        start.setEnabled(true);
    }
    @Override
   public void mousePressed(MouseEvent m){}
    @Override
   public void mouseReleased(MouseEvent m){}
   
   public static void main(String [] args)
   {
      IMP imp = new IMP();
   }
   
   
 
    
   
}