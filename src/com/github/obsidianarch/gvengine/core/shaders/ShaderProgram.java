package com.github.obsidianarch.gvengine.core.shaders;

import static org.lwjgl.opengl.ARBShaderObjects.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * An object representation of the low-level OpenGL shader programs. Allows for easily attaching shaders and enabling and disabling the shader.
 *
 * @author Austin
 * @version 14.03.30
 * @since 14.03.30
 */
public class ShaderProgram
{

    //
    // Fields
    //

    /**
     * The unique ID of this shader program.
     */
    private final int programID;

    /**
     * If the shaders were successfully linked to the shader program.
     */
    private boolean linked;

    /**
     * If the program validated successfully.
     */
    private boolean validated;

    //
    // Constructors
    //

    /**
     * Constructs a new ShaderProgram.
     *
     * @since 14.03.30
     */
    public ShaderProgram()
    {
        programID = glCreateProgramObjectARB();
        linked = false;
        validated = false;
    }

    //
    // Actions
    //

    /**
     * Enables the use of this ShaderProgram.
     *
     * @since 14.03.30
     */
    public void enable()
    {
        glUseProgramObjectARB( programID );
    }

    /**
     * Delets the ShaderProgram from OpenGL.
     *
     * @since 14.03.30
     */
    public void delete()
    {
        glDeleteObjectARB( programID );
    }

    /**
     * Attachs a shader to this program.
     *
     * @param shader
     *         The shader to attach to this program.
     *
     * @since 14.03.30
     */
    public void attachShader( Shader shader )
    {
        glAttachObjectARB( programID, shader.getShaderID() );
    }

    /**
     * Links the shaders to this program.
     *
     * @since 14.03.30
     */
    public void link()
    {
        glLinkProgramARB( programID );
        linked = ( glGetObjectParameteriARB( programID, GL_OBJECT_LINK_STATUS_ARB ) == GL_TRUE );
    }

    /**
     * Checks the validity of this ShaderProgram.
     *
     * @since 14.03.30
     */
    public void validate()
    {
        glValidateProgramARB( programID );
        validated = ( glGetObjectParameteriARB( programID, GL_OBJECT_VALIDATE_STATUS_ARB ) == GL_TRUE );
    }

    //
    // Getters
    //

    /**
     * @return If the ShaderProgram was successfully validated.
     */
    public boolean isValidated()
    {
        return validated;
    }

    /**
     * @return If the ShaderProgram was successfully linked to the Shaders.
     */
    public boolean isLinked()
    {
        return linked;
    }

    //
    // Static
    //

    /**
     * Disables all active ShaderPrograms.
     *
     * @since 14.03.30
     */
    public static void disable()
    {
        glUseProgramObjectARB( 0 );
    }

}
