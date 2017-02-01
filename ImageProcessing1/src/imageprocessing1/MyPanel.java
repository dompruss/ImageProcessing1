/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageprocessing1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.awt.image.PixelGrabber;
import java.awt.image.MemoryImageSource;
import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
/**
 *
 * @author b53t457
 */
public class MyPanel extends JPanel{
    //instance fields
BufferedImage grid;
 Graphics2D gc;
///PaintComponent Method
 public void paintComponent(Graphics g)
    { 
         super.paintComponent(g);  
         Graphics2D g2 = (Graphics2D)g;
         if(grid == null){
            int w = this.getWidth();
            int h = this.getHeight();
            grid = (BufferedImage)(this.createImage(w,h));
            gc = grid.createGraphics();
            
         }
         g2.drawImage(grid, null, 0, 0);
         
    }
}
