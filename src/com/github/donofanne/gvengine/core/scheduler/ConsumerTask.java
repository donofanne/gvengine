package com.github.donofanne.gvengine.core.scheduler;

import java.util.function.Consumer;

/**
 * A task which uses a Consumer (which is passed an Object[], as a workaround).
 */
public class ConsumerTask extends Task
{

    //
    // Fields
    //

    /**
     * The consumer to execute.
     */
    public final Consumer< Object[] > consumer;

    /**
     * The parameters to pass to the consumer.
     */
    public final Object[] parameters;

    //
    // Constructor
    //

    /**
     * @param consumer
     *         The consumer to execute.
     * @param parameters
     *         The parameters to pass to the consumer.
     */
    public ConsumerTask( double time, Consumer< Object[] > consumer, Object[] parameters )
    {
        super( time );
        this.consumer = consumer;
        this.parameters = parameters;
    }

    //
    // Overrides
    //

    @Override
    public void execute()
    {
        consumer.accept( parameters );
    }
}
