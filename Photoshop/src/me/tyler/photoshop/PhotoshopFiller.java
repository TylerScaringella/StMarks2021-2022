package me.tyler.photoshop;

// Photoshop program that can run several manipulations on
// an image
// filler code by Mr. David

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class PhotoshopFiller extends Component {

    // the name of the output file. will be determined by which methods are called
    private String outputName;

    // the 2d array of colors representing the image
    private Color[][] pixels;

    // the width and height of the image
    private int w,h;


    // this method increases each color's rgb value by a given amount.
    // don't forget that rgb values are limited to the range [0,255]
    public void brighten(int amount) {
        outputName = "brightened_" + outputName;

        // your code here
        for(int row=0; row<pixels.length; row++) {
            for(int col=0; col<pixels[0].length; col++) {
                Color c = pixels[row][col];
                int r = Math.max(0, Math.min(c.getRed()+amount, 255));
                int g = Math.max(0, Math.min(c.getGreen()+amount, 255));
                int b = Math.max(0, Math.min(c.getBlue()+amount, 255));
                pixels[row][col] = new Color(r, g, b);
            }
        }
    }

    // flip an image either horizontally or vertically.
    public void flip(boolean horizontally) {
        outputName = (horizontally?"h":"v") + "_flipped_" + outputName;

        Color[][] newPixels = new Color[pixels.length][pixels[0].length];

        // your code here
        if(horizontally) {
            // row, col

            for(int row=0; row<newPixels.length; row++) {
                int last = newPixels[row].length-1;
                for(int col=0; col<newPixels[row].length; col++) {
                    newPixels[row][col] = pixels[row][last];
                    last--;
                }
            }
        } else {
            for(int col=0; col<pixels[0].length; col++) {
                int last = pixels.length-1;
                for(int row=0; row<pixels.length; row++) {
                    newPixels[row][col] = pixels[last][col];
                    last--;
                }
            }
        }

        this.pixels = newPixels;
    }

    // negates an image
    // to do this: subtract each pixel's rgb value from 255
    // and use this as the new value
    public void negate() {
        outputName = "negated_" + outputName;

        // your code here
    }

    // this makes the image 'simpler' by redrawing it using only a few colors
    // to do this: for each pixel, find the color in the list that is closest to
    // the pixel's rgb value.
    // use this predefined color as the rgb value for the changed image.
    public void simplify() {

        // the list of colors to compare to. Feel free to change/add colors
        Color[] colorList = {Color.BLUE, Color.RED,Color.ORANGE, Color.MAGENTA,
                Color.BLACK, Color.WHITE, Color.GREEN, Color.YELLOW, Color.CYAN};
        outputName = "simplified_" + outputName;

        // your code here

    }

    // optional helper method (recommended) that finds the 'distance'
    // between two colors.
    // use the 3d distance formula to calculate
    public double distance(Color c1, Color c2) {

        return 0;	// fix this
    }

    // this blurs the image
    // to do this: at each pixel, sum the 8 surrounding pixels' rgb values
    // with the current pixel's own rgb value.
    // divide this sum by 9, and set it as the rgb value for the blurred image
    public void blur() {
        outputName = "blurred_" + outputName;

        // your code here
        for(int row=0; row<pixels.length; row++) {
            for(int col=0; col<pixels[0].length; col++) {
                int sumR = 0, sumG = 0, sumB = 0;
                for(int i=row-1; i<=row+1 && i>0 && i<pixels.length-1; i++) {
                    for(int x=col-1; x<=col+1 && x>0 && x<pixels[0].length-1; x++) {
                        sumR+= pixels[i][x].getRed();
                        sumG+= pixels[i][x].getGreen();
                        sumB+= pixels[i][x].getBlue();
                    }
                }
                pixels[row][col] = new Color(sumR/9, sumG/9, sumB/9);
            }
        }
    }

    // this highlights the edges in the image, turning everything else black.
    // to do this: at each pixel, sum the 8 surrounding pixels' rgb values.
    // now, multiply the current pixel's rgb value by 8, then subtract the sum.
    // this value is the rgb value for the 'edged' image
    public void edge() {
        outputName = "edged_" + outputName;

        // your code here
    }


    // *************** DON'T MESS WITH THE BELOW CODE **************** //

    // feel free to check it out, but don't change it unless you've consulted
    // with Mr. David and understand what the code's doing



    public void run() {
        JFileChooser fc = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+ "Images");
        fc.setCurrentDirectory(workingDirectory);
        fc.showOpenDialog(null);
        File my_file = fc.getSelectedFile();
        if (my_file == null)
            System.exit(-1);

        // reads the image file and creates our 2d array
        BufferedImage image;
        try {
            image = ImageIO.read(my_file);

            BufferedImage new_image = new BufferedImage(image.getWidth(),
                    image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            create_pixel_array(image);
            outputName = my_file.getName();

            // runs the manipulations determined by the user
            System.out.println("Enter the manipulations you would like to run on the image.\nYour "
                    + "choices are: brighten, flip, negate, blur, edge, or simplify.\nEnter each "
                    + "manipulation you'd like to run, then type in 'done'.");
            Scanner in = new Scanner(System.in);
            String action = in.next().toLowerCase();
            while (!action.equals("done")) {
                try {
                    if (action.equals("brighten")) {
                        System.out.println("enter an amount to increase the brightness by");
                        int brightness = in.nextInt();
                        Method m = getClass().getDeclaredMethod(action, int.class);
                        m.invoke(this, brightness);
                    }
                    else if (action.equals("flip")) {
                        System.out.println("enter \"h\" to flip horizontally, anything else to flip vertically.");
                        Method m = getClass().getDeclaredMethod(action, boolean.class);
                        m.invoke(this, in.next().equals("h"));
                    }
                    else {
                        Method m = getClass().getDeclaredMethod(action);
                        m.invoke(this, new Object[0]);
                    }
                    System.out.println("done. enter another action, or type 'done'");
                }
                catch (NoSuchMethodException e) {
                    System.out.println("not a valid action, try again");
                } catch (IllegalAccessException e) {e.printStackTrace();System.exit(1);}
                catch (IllegalArgumentException e) {e.printStackTrace();System.exit(1);}
                catch (InvocationTargetException e) {e.printStackTrace();System.exit(1);}

                action = in.next().toLowerCase();
            }
            in.close();

            // turns our 2d array of colors into a new png file
            create_new_image(new_image);
            File output_file = new File("C:\\Users\\tjsca\\Documents\\cs\\Photoshop\\images\\" + outputName);
            ImageIO.write(new_image, "png", output_file);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }


    public void create_pixel_array(BufferedImage image) {
        w = image.getWidth();
        h = image.getHeight();
        pixels = new Color[h][];
        for (int i = 0; i < h; i++) {
            pixels[i] = new Color[w];
            for (int j = 0; j < w; j++) {
                pixels[i][j] = new Color(image.getRGB(j,i));
            }
        }
    }

    public void create_new_image(BufferedImage new_image) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                new_image.setRGB(j, i, pixels[i][j].getRGB());
            }
        }
    }

    public static void main(String[] args) {
        new PhotoshopFiller();
    }

    public PhotoshopFiller() {
        run();
    }
}