package com.github.obsidianarch.gvengine;

import com.github.obsidianarch.gvengine.core.Scheduler;

/**
 * A 4x4x4 container of Chunks.
 * 
 * @author Austin
 */
public class Region {
    
    //
    // Fields
    //
    
    /** An array of all the chunks in this region. */
    public Chunk[]               chunks = new Chunk[ 64 ];
    
    /** The region's x coordinate. */
    public final int             x;
    
    /** The region's y coordinate. */
    public final int             y;
    
    /** The region's z coordinate. */
    public final int             z;
    
    /** Responsible for generating every chunk in this region. */
    private final ChunkGenerator generator;
    
    //
    // Constructors
    //
    
    /**
     * Creates a new region.
     * 
     * @param generator
     *            The chunk generator used to generate this region's chunks.
     */
    public Region( ChunkGenerator generator, int x, int y, int z ) {
        this.generator = generator;
        
        this.x = x;
        this.y = y;
        this.z = z;
        
        for ( int cX = 0; cX < 4; cX++ ) {
            for ( int cY = 0; cY < 4; cY++ ) {
                for ( int cZ = 0; cZ < 4; cZ++ ) {
                    Chunk c = new Chunk( cX, cY, cZ ); // create the chunk
                    generator.generateChunk( c ); // generate teh chunk's voxels
                    
                    int index = cX;
                    index += cY * 4;
                    index += cZ * 16;
                    chunks[ index ] = c; // add the chunk to the region
                }
            }
        }
    }
    
    //
    // Actions
    //
    
    /**
     * Regenerates all chunks based on the current seed.
     */
    public void regenerate() {
        for ( Chunk c : chunks ) {
            generator.generateChunk( c );
        }
    }
    
    /**
     * Schedules rebuilds for every chunk in this region.
     */
    public void rebuild() {
        for ( int i = 0; i < chunks.length; i++ ) {
            Scheduler.scheduleEvent( "buildMesh", chunks[ i ], i * 100 ); // a chunk in this region is rebuilt every 100 milliseconds 
        }
    }
    
    /**
     * Renders every chunk in this region.
     */
    public void render() {
        for ( Chunk c : chunks ) {
            c.render();
        }
    }
    
}
