package com.github.obsidianarch.gvengine.tests;

import static org.lwjgl.opengl.GL11.*;

import java.util.Scanner;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * Tests controller input.
 * 
 * @author Austin
 */
public class ControllerTest {
    
    //
    // Fields
    //
    
    /** Controller giving input */
    private static Controller controller;
    
    private static float      redX   = 0.5f;
    private static float      redY   = 0.5f;
    
    private static float      whiteX = 0.5f;
    private static float      whiteY = 0.5f;
    
    //
    // Methods
    //
    
    public static void main( String[] args ) throws LWJGLException {
        Controllers.create();
        Controllers.poll();
        
        for ( int i = 0; i < Controllers.getControllerCount(); i++ ) {
            controller = Controllers.getController( i );
            System.out.println( "(" + i + ") " + controller.getName() );
        }
        
        System.out.println(); // blank line
        
        Scanner scanner = new Scanner( System.in );
        
        System.out.print( "Which controller index?  " );
        int answer = scanner.nextInt();
        
        scanner.close();
        System.out.println();
        
        controller = Controllers.getController( answer );
        
        for ( int i = 0; i < controller.getAxisCount(); i++ ) {
            System.out.println( "Axis detected:  (" + i + ") " + controller.getAxisName( i ) );
        }
        
        for ( int i = 0; i < controller.getButtonCount(); i++ ) {
            System.out.println( "Button detected:  (" + i + ") " + controller.getButtonName( i ) );
        }
        
        System.out.println( "Detected " + controller.getRumblerCount() + " rumbler(s)" );
        
        setup();
        
        while ( ! Display.isCloseRequested() ) {
            controller.poll();
            update();
            
            for ( int i = 0; i < controller.getRumblerCount(); i++ ) {
                controller.setRumblerStrength( i, 1f );
            }
            
            if ( controller.getAxisValue( 4 ) != 0 ) {
                System.out.println( controller.getAxisValue( 4 ) ); // right trigger is [-1,0), left (0,1]
            }
            
            Display.update();
            Display.sync( 60 );
        }
        
        destroy();
    }
    
    public static void update() {
        float[] input = getInputs( 1, 0 );
        
        redX += input[ 0 ];
        redY -= input[ 1 ];
        
        input = getInputs( 3, 2 );
        whiteX += input[ 0 ];
        whiteY -= input[ 1 ];
        
        // using immediate mode to keep the example simple
        glBegin( GL_POINTS );
        {
            glColor3f( 1, 0, 0 );
            glVertex2f( redX, redY );
            
            glColor3f( 1, 1, 1 );
            glVertex2f( whiteX, whiteY );
        }
        glEnd();
        
        // draw the button inputs
        glBegin( GL_QUADS );
        {
            for ( int i = 0; i < controller.getButtonCount(); i++ ) {
                changeColor( i );
                
                float xn = 0.01f;
                float xp = 0.1f;
                float yn = ( i / ( float ) controller.getButtonCount() );
                float yp = ( ( i + 1 ) / ( float ) controller.getButtonCount() );
                
                glVertex2f( xn, yn );
                glVertex2f( xp, yn );
                glVertex2f( xp, yp );
                glVertex2f( xn, yp );
            }
        }
        glEnd();
        
        glColor3f( 0, 0, 0 );
    }
    
    private static float[] getInputs( int x, int y ) {
        float inputX = controller.getAxisValue( x );
        float inputY = controller.getAxisValue( y );
        
        if ( Keyboard.isKeyDown( Keyboard.KEY_SPACE ) ) {
            System.out.println( inputX + ", " + inputY );
        }
        
        if ( Math.abs( inputX ) < 0.1f ) inputX = 0;
        if ( Math.abs( inputY ) < 0.1f ) inputY = 0;
        
        inputX /= 100f;
        inputY /= 100f;
        
        return new float[ ] { inputX, inputY };
    }
    
    private static void changeColor( int button ) {
        boolean down = controller.isButtonPressed( button );
        
        if ( down ) {
            glColor3f( 0, 1, 0 );
        }
        else {
            glColor3f( 1, 0, 0 );
        }
    }
    
    public static void setup() throws LWJGLException {
        Display.setTitle( "Controller Test" );
        Display.setDisplayMode( new DisplayMode( 640, 480 ) );
        Display.create();
        
        glOrtho( 0, 1, 0, 1, - 1, 1 );
    }
    
    public static void destroy() {
        Display.destroy();
    }
    
}